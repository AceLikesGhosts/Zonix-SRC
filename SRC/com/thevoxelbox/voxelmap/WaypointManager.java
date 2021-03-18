package com.thevoxelbox.voxelmap;

import com.thevoxelbox.voxelmap.interfaces.*;
import net.minecraft.client.*;
import net.minecraft.client.entity.*;
import java.text.*;
import net.minecraft.util.*;
import com.thevoxelbox.voxelmap.util.*;
import java.io.*;
import net.minecraft.entity.*;
import net.minecraft.world.*;
import java.util.*;

public class WaypointManager implements IWaypointManager
{
    public MapSettingsManager options;
    IVoxelMap master;
    private ArrayList<Waypoint> wayPts;
    private ArrayList<Waypoint> old2dWayPts;
    private ArrayList<Waypoint> updatedPts;
    private String currentSubWorldName;
    private String currentSubWorldHash;
    private String currentSubWorldDescriptor;
    private EntityWaypointContainer entityWaypointContainer;
    private File settingsFile;
    private Object lock;
    
    public WaypointManager(final IVoxelMap master) {
        this.options = null;
        this.wayPts = new ArrayList<Waypoint>();
        this.old2dWayPts = new ArrayList<Waypoint>();
        this.currentSubWorldName = "";
        this.currentSubWorldHash = "";
        this.currentSubWorldDescriptor = "";
        this.entityWaypointContainer = null;
        this.lock = new Object();
        this.master = master;
        this.options = master.getMapOptions();
    }
    
    @Override
    public ArrayList<Waypoint> getWaypoints() {
        return this.wayPts;
    }
    
    @Override
    public void handleDeath() {
        final TreeSet<Integer> toDel = new TreeSet<Integer>();
        for (final Waypoint pt : this.wayPts) {
            if (pt.name.equals("Latest Death")) {
                pt.name = "Previous Death";
            }
            if (pt.name.startsWith("Previous Death")) {
                if (this.options.deathpoints > 1) {
                    int num = 0;
                    try {
                        if (pt.name.length() > 15) {
                            num = Integer.parseInt(pt.name.substring(15));
                        }
                    }
                    catch (Exception e) {
                        num = 0;
                    }
                    final Waypoint waypoint = pt;
                    waypoint.red -= (pt.red - 0.5f) / 8.0f;
                    final Waypoint waypoint2 = pt;
                    waypoint2.green -= (pt.green - 0.5f) / 8.0f;
                    final Waypoint waypoint3 = pt;
                    waypoint3.blue -= (pt.blue - 0.5f) / 8.0f;
                    pt.name = "Previous Death " + (num + 1);
                }
                else {
                    toDel.add(this.wayPts.indexOf(pt));
                }
            }
        }
        if (this.options.deathpoints < 2 && toDel.size() > 0) {
            final TreeSet<Integer> toDelReverse = (TreeSet<Integer>)(TreeSet)toDel.descendingSet();
            for (final Integer index : toDelReverse) {
                this.deleteWaypoint(index);
            }
        }
        if (this.options.deathpoints > 0) {
            final EntityClientPlayerMP thePlayer = Minecraft.getMinecraft().thePlayer;
            final TreeSet<Integer> dimensions = new TreeSet<Integer>();
            dimensions.add(Minecraft.getMinecraft().thePlayer.dimension);
            this.addWaypoint(new Waypoint("Latest Death", (thePlayer.dimension != -1) ? GameVariableAccessShim.xCoord() : (GameVariableAccessShim.xCoord() * 8), (thePlayer.dimension != -1) ? GameVariableAccessShim.zCoord() : (GameVariableAccessShim.zCoord() * 8), GameVariableAccessShim.yCoord() - 1, true, 1.0f, 1.0f, 1.0f, "skull", this.currentSubWorldName, dimensions));
        }
    }
    
    @Override
    public void newWorld(final int dimension) {
        synchronized (this.lock) {
            for (final Waypoint pt : this.wayPts) {
                if (pt.dimensions.size() == 0 || pt.dimensions.contains(dimension)) {
                    pt.inDimension = true;
                }
                else {
                    pt.inDimension = false;
                }
            }
            this.injectWaypointEntity();
        }
    }
    
    public void newSubWorldDescriptor(final String descriptor) {
        this.currentSubWorldDescriptor = descriptor;
        synchronized (this.lock) {
            final String currentSubWorldDescriptorScrubbed = this.scrubName(descriptor);
            for (final Waypoint pt : this.wayPts) {
                if (currentSubWorldDescriptorScrubbed == "" || pt.world == "" || currentSubWorldDescriptorScrubbed.equals(pt.world)) {
                    pt.inWorld = true;
                }
                else {
                    pt.inWorld = false;
                }
            }
        }
    }
    
    @Override
    public void newSubWorldName(final String name) {
        this.newSubWorldDescriptor(this.currentSubWorldName = name);
    }
    
    @Override
    public void newSubWorldHash(final String hash) {
        this.currentSubWorldHash = hash;
        if (this.currentSubWorldName.equals("")) {
            this.newSubWorldDescriptor(this.currentSubWorldHash);
        }
    }
    
    @Override
    public String getCurrentSubworldDescriptor() {
        return this.currentSubWorldDescriptor;
    }
    
    @Override
    public void saveWaypoints() {
        String worldNameSave = this.master.getMap().getCurrentWorldName();
        if (worldNameSave.endsWith(":25565")) {
            final int portSepLoc = worldNameSave.lastIndexOf(":");
            if (portSepLoc != -1) {
                worldNameSave = worldNameSave.substring(0, portSepLoc);
            }
        }
        worldNameSave = this.scrubFileName(worldNameSave);
        File homeDir = FilesystemUtils.getAppDir("minecraft", true).getAbsoluteFile();
        final File mcDir = Minecraft.getMinecraft().mcDataDir.getAbsoluteFile();
        if (!homeDir.equals(mcDir)) {
            String localDirName = "";
            if (Minecraft.getMinecraft().isIntegratedServerRunning()) {
                localDirName = mcDir.getName();
                if ((localDirName.equalsIgnoreCase("minecraft") || localDirName.equalsIgnoreCase(".")) && mcDir.getParentFile() != null) {
                    localDirName = mcDir.getParentFile().getName();
                }
                localDirName = "~" + localDirName;
            }
            this.saveWaypointsTo(this.settingsFile = new File(FilesystemUtils.getAppDir("minecraft/mods/VoxelMods/voxelMap", true), worldNameSave + localDirName + ".points"));
            homeDir = new File(Minecraft.getMinecraft().mcDataDir, "/mods/VoxelMods/voxelMap/");
            if (!homeDir.exists()) {
                homeDir.mkdirs();
            }
            this.saveWaypointsTo(this.settingsFile = new File(Minecraft.getMinecraft().mcDataDir, "/mods/VoxelMods/voxelMap/" + worldNameSave + ".points"));
        }
        else {
            this.saveWaypointsTo(this.settingsFile = new File(FilesystemUtils.getAppDir("minecraft/mods/VoxelMods/voxelMap", true), worldNameSave + ".points"));
        }
    }
    
    public void saveWaypointsTo(final File settingsFile) {
        try {
            final PrintWriter out = new PrintWriter(new FileWriter(settingsFile));
            final Date now = new Date();
            final String timestamp = new SimpleDateFormat("yyyyMMddHHmm").format(now);
            out.println("filetimestamp:" + timestamp);
            for (final Waypoint pt : this.wayPts) {
                if (pt.isAutomated) {
                    continue;
                }
                if (pt.name.startsWith("^")) {
                    continue;
                }
                String dimensionsString = "";
                for (final Integer dimension : pt.dimensions) {
                    dimensionsString = dimensionsString + "" + dimension + "#";
                }
                if (dimensionsString.equals("")) {
                    dimensionsString = "-1#0#";
                }
                out.println("name:" + this.scrubName(pt.name) + ",x:" + pt.x + ",z:" + pt.z + ",y:" + pt.y + ",enabled:" + Boolean.toString(pt.enabled) + ",red:" + pt.red + ",green:" + pt.green + ",blue:" + pt.blue + ",suffix:" + pt.imageSuffix + ",world:" + this.scrubName(pt.world) + ",dimensions:" + dimensionsString);
            }
            out.close();
        }
        catch (Exception local) {
            ChatUtils.chatInfo(EnumChatFormatting.YELLOW + "Error Saving Waypoints");
        }
    }
    
    public String scrubName(String input) {
        input = input.replace(":", "~colon~");
        input = input.replace(",", "~comma~");
        return input;
    }
    
    private String descrubName(String input) {
        input = input.replace("~colon~", ":");
        input = input.replace("~comma~", ",");
        return input;
    }
    
    public String scrubFileName(String input) {
        input = input.replace("<", "~less~");
        input = input.replace(">", "~greater~");
        input = input.replace(":", "~colon~");
        input = input.replace("\"", "~quote~");
        input = input.replace("/", "~slash~");
        input = input.replace("\\", "~backslash~");
        input = input.replace("|", "~pipe~");
        input = input.replace("?", "~question~");
        input = input.replace("*", "~star~");
        return input;
    }
    
    @Override
    public void loadWaypoints() {
        synchronized (this.lock) {
            boolean loaded = false;
            this.wayPts = new ArrayList<Waypoint>();
            String worldNameStandard = this.master.getMap().getCurrentWorldName();
            if (worldNameStandard.endsWith(":25565")) {
                final int portSepLoc = worldNameStandard.lastIndexOf(":");
                if (portSepLoc != -1) {
                    worldNameStandard = worldNameStandard.substring(0, portSepLoc);
                }
            }
            worldNameStandard = this.scrubFileName(worldNameStandard);
            final String worldNameWithPort = this.scrubFileName(this.master.getMap().getCurrentWorldName());
            String worldNameWithoutPort = this.master.getMap().getCurrentWorldName();
            final int portSepLoc2 = worldNameWithoutPort.lastIndexOf(":");
            if (portSepLoc2 != -1) {
                worldNameWithoutPort = worldNameWithoutPort.substring(0, portSepLoc2);
            }
            worldNameWithoutPort = this.scrubFileName(worldNameWithoutPort);
            final String worldNameWithDefaultPort = this.scrubFileName(worldNameWithoutPort + "~colon~25565");
            loaded = this.loadWaypointsExtensible(worldNameStandard);
            if (!loaded) {
                loaded = this.loadOldWaypoints(worldNameWithoutPort, worldNameWithDefaultPort, worldNameWithPort);
            }
            if (!loaded) {
                loaded = this.findReiWaypoints(worldNameWithoutPort);
            }
            if (!loaded) {
                ChatUtils.chatInfo("§ENo waypoints exist for this world/server.");
            }
            else {
                this.populateOld2dWaypoints();
            }
        }
        this.newSubWorldDescriptor(this.currentSubWorldDescriptor);
    }
    
    private boolean loadWaypointsExtensible(final String worldNameStandard) {
        final File homeDir = FilesystemUtils.getAppDir("minecraft", false).getAbsoluteFile();
        final File mcDir = Minecraft.getMinecraft().mcDataDir.getAbsoluteFile();
        if (!homeDir.equals(mcDir)) {
            long homeDate = -1L;
            long localDate = -1L;
            String localDirName = "";
            if (Minecraft.getMinecraft().isIntegratedServerRunning()) {
                localDirName = mcDir.getName();
                if ((localDirName.equalsIgnoreCase("minecraft") || localDirName.equalsIgnoreCase(".")) && mcDir.getParentFile() != null) {
                    localDirName = mcDir.getParentFile().getName();
                }
                localDirName = "~" + localDirName;
            }
            final File settingsFileLocal = new File(Minecraft.getMinecraft().mcDataDir, "/mods/VoxelMods/voxelMap/" + worldNameStandard + ".points");
            localDate = this.getDateFromSave(settingsFileLocal);
            File settingsFileHome = new File(FilesystemUtils.getAppDir("minecraft/mods/VoxelMods/voxelMap", false), worldNameStandard + localDirName + ".points");
            if (!settingsFileHome.exists() && !settingsFileLocal.exists()) {
                settingsFileHome = new File(FilesystemUtils.getAppDir("minecraft/mods/VoxelMods/voxelMap", false), worldNameStandard + ".points");
            }
            homeDate = this.getDateFromSave(settingsFileHome);
            if (!settingsFileHome.exists() && !settingsFileLocal.exists()) {
                return false;
            }
            if (!settingsFileHome.exists()) {
                this.settingsFile = settingsFileLocal;
            }
            else if (!settingsFileLocal.exists()) {
                this.settingsFile = settingsFileHome;
            }
            else {
                this.settingsFile = ((homeDate > localDate) ? settingsFileHome : settingsFileLocal);
            }
        }
        else {
            this.settingsFile = new File(FilesystemUtils.getAppDir("minecraft/mods/VoxelMods/voxelMap", false), worldNameStandard + ".points");
        }
        if (this.settingsFile.exists()) {
            try {
                final BufferedReader in = new BufferedReader(new FileReader(this.settingsFile));
                String sCurrentLine;
                while ((sCurrentLine = in.readLine()) != null) {
                    if (!sCurrentLine.startsWith("filetimestamp")) {
                        final String[] curLine = sCurrentLine.split(",");
                        String name = "";
                        int x = 0;
                        int z = 0;
                        int y = -1;
                        boolean enabled = false;
                        float red = 0.5f;
                        float green = 0.0f;
                        float blue = 0.0f;
                        String suffix = "";
                        String world = "";
                        final TreeSet<Integer> dimensions = new TreeSet<Integer>();
                        for (int t = 0; t < curLine.length; ++t) {
                            final String[] keyValuePair = curLine[t].split(":");
                            if (keyValuePair.length == 2) {
                                final String key = keyValuePair[0];
                                final String value = keyValuePair[1];
                                if (key.equals("name")) {
                                    name = this.descrubName(value);
                                }
                                else if (key.equals("x")) {
                                    x = Integer.parseInt(value);
                                }
                                else if (key.equals("z")) {
                                    z = Integer.parseInt(value);
                                }
                                else if (key.equals("y")) {
                                    y = Integer.parseInt(value);
                                }
                                else if (key.equals("enabled")) {
                                    enabled = Boolean.parseBoolean(value);
                                }
                                else if (key.equals("red")) {
                                    red = Float.parseFloat(value);
                                }
                                else if (key.equals("green")) {
                                    green = Float.parseFloat(value);
                                }
                                else if (key.equals("blue")) {
                                    blue = Float.parseFloat(value);
                                }
                                else if (key.equals("suffix")) {
                                    suffix = value;
                                }
                                else if (key.equals("world")) {
                                    world = this.descrubName(value);
                                }
                                else if (key.equals("dimensions")) {
                                    final String[] dimensionStrings = value.split("#");
                                    for (int s = 0; s < dimensionStrings.length; ++s) {
                                        dimensions.add(Integer.parseInt(dimensionStrings[s]));
                                    }
                                    if (dimensions.size() == 0) {
                                        dimensions.add(0);
                                        dimensions.add(-1);
                                    }
                                }
                            }
                        }
                        if (name.equals("")) {
                            continue;
                        }
                        this.loadWaypoint(name, x, z, y, enabled, red, green, blue, suffix, world, dimensions);
                    }
                }
                in.close();
            }
            catch (Exception local) {
                ChatUtils.chatInfo(EnumChatFormatting.YELLOW + "Error Loading Waypoints");
                System.err.println("waypoint load error: " + local.getLocalizedMessage());
                return false;
            }
            return true;
        }
        return false;
    }
    
    private long getDateFromSave(final File settingsFile) {
        if (settingsFile.exists()) {
            try {
                final BufferedReader in = new BufferedReader(new FileReader(settingsFile));
                final String sCurrentLine = in.readLine();
                final String[] curLine = sCurrentLine.split(":");
                in.close();
                if (curLine[0].equals("filetimestamp")) {
                    return Long.parseLong(curLine[1]);
                }
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
        return -1L;
    }
    
    private boolean loadOldWaypoints(final String worldNameWithPort, final String worldNameWithDefaultPort, final String worldNameWithoutPort) {
        try {
            this.settingsFile = new File(FilesystemUtils.getAppDir("minecraft/mods/zan", false), worldNameWithPort + ".points");
            if (!this.settingsFile.exists()) {
                this.settingsFile = new File(FilesystemUtils.getAppDir("minecraft/mods/zan", false), worldNameWithDefaultPort + ".points");
            }
            if (!this.settingsFile.exists()) {
                this.settingsFile = new File(FilesystemUtils.getAppDir("minecraft/mods/zan", false), worldNameWithoutPort + ".points");
            }
            if (!this.settingsFile.exists()) {
                this.settingsFile = new File(FilesystemUtils.getAppDir("minecraft", false), worldNameWithoutPort + ".points");
            }
            if (this.settingsFile.exists()) {
                final TreeSet<Integer> dimensions = new TreeSet<Integer>();
                dimensions.add(-1);
                dimensions.add(0);
                final BufferedReader in = new BufferedReader(new FileReader(this.settingsFile));
                String sCurrentLine;
                while ((sCurrentLine = in.readLine()) != null) {
                    final String[] curLine = sCurrentLine.split(":");
                    if (curLine.length == 4) {
                        this.loadWaypoint(curLine[0], Integer.parseInt(curLine[1]), Integer.parseInt(curLine[2]), -1, Boolean.parseBoolean(curLine[3]), 0.0f, 1.0f, 0.0f, "", "", dimensions);
                    }
                    else if (curLine.length == 7) {
                        this.loadWaypoint(curLine[0], Integer.parseInt(curLine[1]), Integer.parseInt(curLine[2]), -1, Boolean.parseBoolean(curLine[3]), Float.parseFloat(curLine[4]), Float.parseFloat(curLine[5]), Float.parseFloat(curLine[6]), "", "", dimensions);
                    }
                    else if (curLine.length == 8) {
                        if (curLine[3].contains("true") || curLine[3].contains("false")) {
                            this.loadWaypoint(curLine[0], Integer.parseInt(curLine[1]), Integer.parseInt(curLine[2]), -1, Boolean.parseBoolean(curLine[3]), Float.parseFloat(curLine[4]), Float.parseFloat(curLine[5]), Float.parseFloat(curLine[6]), curLine[7], "", dimensions);
                        }
                        else {
                            this.loadWaypoint(curLine[0], Integer.parseInt(curLine[1]), Integer.parseInt(curLine[2]), Integer.parseInt(curLine[3]), Boolean.parseBoolean(curLine[4]), Float.parseFloat(curLine[5]), Float.parseFloat(curLine[6]), Float.parseFloat(curLine[7]), "", "", dimensions);
                        }
                    }
                    else {
                        if (curLine.length != 9) {
                            continue;
                        }
                        this.loadWaypoint(curLine[0], Integer.parseInt(curLine[1]), Integer.parseInt(curLine[2]), Integer.parseInt(curLine[3]), Boolean.parseBoolean(curLine[4]), Float.parseFloat(curLine[5]), Float.parseFloat(curLine[6]), Float.parseFloat(curLine[7]), curLine[8], "", dimensions);
                    }
                }
                in.close();
                return true;
            }
            return false;
        }
        catch (Exception local) {
            ChatUtils.chatInfo(EnumChatFormatting.YELLOW + "Error Loading Waypoints");
            System.err.println("waypoint load error: " + local.getLocalizedMessage());
            return false;
        }
    }
    
    private boolean findReiWaypoints(final String worldNameWithoutPort) {
        boolean foundSome = false;
        this.settingsFile = new File(FilesystemUtils.getAppDir("minecraft/mods/rei_minimap", false), worldNameWithoutPort + ".points");
        if (!this.settingsFile.exists()) {
            this.settingsFile = new File(Minecraft.getMinecraft().mcDataDir, "/mods/rei_minimap/" + worldNameWithoutPort + ".points");
        }
        if (this.settingsFile.exists()) {
            this.loadReiWaypoints(this.settingsFile, 0);
            foundSome = true;
        }
        else {
            for (int t = -25; t < 25; ++t) {
                this.settingsFile = new File(FilesystemUtils.getAppDir("minecraft/mods/rei_minimap", false), worldNameWithoutPort + ".DIM" + t + ".points");
                if (!this.settingsFile.exists()) {
                    this.settingsFile = new File(Minecraft.getMinecraft().mcDataDir, "/mods/rei_minimap/" + worldNameWithoutPort + ".DIM" + t + ".points");
                }
                if (this.settingsFile.exists()) {
                    foundSome = true;
                    this.loadReiWaypoints(this.settingsFile, t);
                }
            }
        }
        return foundSome;
    }
    
    private void loadReiWaypoints(final File settingsFile, final int dimension) {
        try {
            if (settingsFile.exists()) {
                final TreeSet<Integer> dimensions = new TreeSet<Integer>();
                dimensions.add(dimension);
                final BufferedReader in = new BufferedReader(new FileReader(settingsFile));
                String sCurrentLine;
                while ((sCurrentLine = in.readLine()) != null) {
                    final String[] curLine = sCurrentLine.split(":");
                    if (curLine.length == 6) {
                        final int color = Integer.parseInt(curLine[5], 16);
                        final float red = (color >> 16 & 0xFF) / 255.0f;
                        final float green = (color >> 8 & 0xFF) / 255.0f;
                        final float blue = (color >> 0 & 0xFF) / 255.0f;
                        int x = Integer.parseInt(curLine[1]);
                        int z = Integer.parseInt(curLine[3]);
                        if (dimension == -1) {
                            x *= 8;
                            z *= 8;
                        }
                        this.loadWaypoint(curLine[0], x, z, Integer.parseInt(curLine[2]), Boolean.parseBoolean(curLine[4]), red, green, blue, "", "", dimensions);
                    }
                }
                in.close();
            }
        }
        catch (Exception e) {
            ChatUtils.chatInfo(EnumChatFormatting.YELLOW + "Error Loading Old Rei Waypoints");
            System.err.println("waypoint load error: " + e.getLocalizedMessage());
        }
    }
    
    public void loadWaypoint(final String name, final int x, final int z, final int y, final boolean enabled, final float red, final float green, final float blue, final String suffix, final String world, final TreeSet<Integer> dimensions) {
        final Waypoint newWaypoint = new Waypoint(name, x, z, y, enabled, red, green, blue, suffix, world, dimensions);
        this.wayPts.add(newWaypoint);
    }
    
    public void populateOld2dWaypoints() {
        this.old2dWayPts = new ArrayList<Waypoint>();
        for (final Waypoint wpt : this.wayPts) {
            if (wpt.getY() <= 0) {
                this.old2dWayPts.add(wpt);
            }
        }
    }
    
    @Override
    public void check2dWaypoints() {
        if (Minecraft.getMinecraft().thePlayer.dimension == 0 && this.old2dWayPts.size() > 0) {
            this.updatedPts = new ArrayList<Waypoint>();
            for (final Waypoint pt : this.old2dWayPts) {
                if (Math.abs(pt.getX() - GameVariableAccessShim.xCoord()) < 400 && Math.abs(pt.getZ() - GameVariableAccessShim.zCoord()) < 400 && Minecraft.getMinecraft().thePlayer.worldObj.getChunkFromBlockCoords(pt.getX(), pt.getZ()).isChunkLoaded) {
                    pt.setY(Minecraft.getMinecraft().thePlayer.worldObj.getHeightValue(pt.getX(), pt.getZ()));
                    this.updatedPts.add(pt);
                    this.saveWaypoints();
                }
            }
            this.old2dWayPts.removeAll(this.updatedPts);
            System.out.println("remaining old 2d waypoints: " + this.old2dWayPts.size());
        }
    }
    
    private void deleteWaypoint(final int i) {
        this.old2dWayPts.remove(this.wayPts.get(i));
        this.entityWaypointContainer.removeWaypoint(this.wayPts.get(i));
        this.wayPts.remove(i);
        this.saveWaypoints();
    }
    
    @Override
    public void deleteWaypoint(final Waypoint point) {
        this.old2dWayPts.remove(point);
        this.entityWaypointContainer.removeWaypoint(point);
        this.wayPts.remove(point);
        this.saveWaypoints();
    }
    
    @Override
    public void addWaypoint(final Waypoint newWaypoint) {
        this.wayPts.add(newWaypoint);
        this.entityWaypointContainer.addWaypoint(newWaypoint);
        this.saveWaypoints();
    }
    
    private void purgeWaypointEntity() {
        final List entities = Minecraft.getMinecraft().theWorld.getLoadedEntityList();
        for (int t = 0; t < entities.size(); ++t) {
            final Entity entity = entities.get(t);
            if (entity instanceof EntityWaypointContainer) {
                entity.setDead();
            }
        }
    }
    
    public void injectWaypointEntity() {
        this.entityWaypointContainer = new EntityWaypointContainer(Minecraft.getMinecraft().theWorld);
        for (final Waypoint wpt : this.wayPts) {
            this.entityWaypointContainer.addWaypoint(wpt);
        }
        Minecraft.getMinecraft().theWorld.spawnEntityInWorld(this.entityWaypointContainer);
    }
    
    @Override
    public void moveWaypointEntityToBack() {
        final List entities = Minecraft.getMinecraft().theWorld.getLoadedEntityList();
        synchronized (entities) {
            final int t = entities.indexOf(this.entityWaypointContainer);
            if (t == -1) {
                this.purgeWaypointEntity();
                this.injectWaypointEntity();
                return;
            }
            if (t != entities.size() - 1) {
                Collections.swap(entities, t, entities.size() - 1);
            }
        }
    }
}
