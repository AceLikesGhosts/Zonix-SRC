package net.minecraft.src;

import net.minecraft.item.*;
import java.util.*;
import net.minecraft.client.renderer.texture.*;
import net.minecraft.util.*;

public class CustomItemProperties
{
    public String name;
    public String basePath;
    public int type;
    public int[] items;
    public String texture;
    public Map mapTextures;
    public RangeListInt damage;
    public boolean damagePercent;
    public int damageMask;
    public RangeListInt stackSize;
    public RangeListInt enchantmentIds;
    public RangeListInt enchantmentLevels;
    public NbtTagValue[] nbtTagValues;
    public int blend;
    public int speed;
    public int rotation;
    public int layer;
    public int duration;
    public int weight;
    public IIcon textureIcon;
    public Map mapTextureIcons;
    public static final int TYPE_UNKNOWN = 0;
    public static final int TYPE_ITEM = 1;
    public static final int TYPE_ENCHANTMENT = 2;
    public static final int TYPE_ARMOR = 3;
    
    public CustomItemProperties(final Properties props, final String path) {
        this.name = null;
        this.basePath = null;
        this.type = 1;
        this.items = null;
        this.texture = null;
        this.mapTextures = null;
        this.damage = null;
        this.damagePercent = false;
        this.damageMask = 0;
        this.stackSize = null;
        this.enchantmentIds = null;
        this.enchantmentLevels = null;
        this.nbtTagValues = null;
        this.blend = 1;
        this.speed = 0;
        this.rotation = 0;
        this.layer = 0;
        this.duration = 1;
        this.weight = 0;
        this.textureIcon = null;
        this.mapTextureIcons = null;
        this.name = parseName(path);
        this.basePath = parseBasePath(path);
        this.type = this.parseType(props.getProperty("type"));
        this.items = this.parseItems(props.getProperty("items"), props.getProperty("matchItems"));
        this.texture = parseTexture(props.getProperty("texture"), props.getProperty("tile"), props.getProperty("source"), path, this.basePath);
        this.mapTextures = parseTextures(props, this.basePath);
        final String damageStr = props.getProperty("damage");
        if (damageStr != null) {
            this.damagePercent = damageStr.contains("%");
            damageStr.replace("%", "");
            this.damage = this.parseRangeListInt(damageStr);
            this.damageMask = this.parseInt(props.getProperty("damageMask"), 0);
        }
        this.stackSize = this.parseRangeListInt(props.getProperty("stackSize"));
        this.enchantmentIds = this.parseRangeListInt(props.getProperty("enchantmentIDs"));
        this.enchantmentLevels = this.parseRangeListInt(props.getProperty("enchantmentLevels"));
        this.nbtTagValues = this.parseNbtTagValues(props);
        this.blend = Blender.parseBlend(props.getProperty("blend"));
        this.speed = this.parseInt(props.getProperty("speed"), 0);
        this.rotation = this.parseInt(props.getProperty("rotation"), 0);
        this.layer = this.parseInt(props.getProperty("layer"), 0);
        this.weight = this.parseInt(props.getProperty("weight"), 0);
        this.duration = this.parseInt(props.getProperty("duration"), 1);
    }
    
    private static String parseName(final String path) {
        String str = path;
        final int pos = path.lastIndexOf(47);
        if (pos >= 0) {
            str = path.substring(pos + 1);
        }
        final int pos2 = str.lastIndexOf(46);
        if (pos2 >= 0) {
            str = str.substring(0, pos2);
        }
        return str;
    }
    
    private static String parseBasePath(final String path) {
        final int pos = path.lastIndexOf(47);
        return (pos < 0) ? "" : path.substring(0, pos);
    }
    
    private int parseType(final String str) {
        if (str == null) {
            return 1;
        }
        if (str.equals("item")) {
            return 1;
        }
        if (str.equals("enchantment")) {
            return 2;
        }
        if (str.equals("armor")) {
            return 3;
        }
        Config.warn("Unknown method: " + str);
        return 0;
    }
    
    private int[] parseItems(String str, final String str2) {
        if (str == null) {
            str = str2;
        }
        if (str == null) {
            return null;
        }
        str = str.trim();
        final TreeSet setItemIds = new TreeSet();
        final String[] tokens = Config.tokenize(str, " ");
        for (int integers = 0; integers < tokens.length; ++integers) {
            final String ints = tokens[integers];
            final int i = Config.parseInt(ints, -1);
            if (i >= 0) {
                setItemIds.add(new Integer(i));
            }
            else {
                if (ints.contains("-")) {
                    final String[] itemObj = Config.tokenize(ints, "-");
                    if (itemObj.length == 2) {
                        final int item = Config.parseInt(itemObj[0], -1);
                        final int id = Config.parseInt(itemObj[1], -1);
                        if (item >= 0 && id >= 0) {
                            final int min = Math.min(item, id);
                            for (int max = Math.max(item, id), x = min; x <= max; ++x) {
                                setItemIds.add(new Integer(x));
                            }
                            continue;
                        }
                    }
                }
                final Object var16 = Item.itemRegistry.getObject(ints);
                if (!(var16 instanceof Item)) {
                    Config.dbg("Item not found: " + ints);
                }
                else {
                    final Item var17 = (Item)var16;
                    final int id = Item.getIdFromItem(var17);
                    if (id < 0) {
                        Config.dbg("Item not found: " + ints);
                    }
                    else {
                        setItemIds.add(new Integer(id));
                    }
                }
            }
        }
        final Integer[] var18 = (Integer[])setItemIds.toArray(new Integer[setItemIds.size()]);
        int i;
        int[] var19;
        for (var19 = new int[var18.length], i = 0; i < var19.length; ++i) {
            var19[i] = var18[i];
        }
        return var19;
    }
    
    private static String parseTexture(String texStr, final String texStr2, final String texStr3, final String path, final String basePath) {
        if (texStr == null) {
            texStr = texStr2;
        }
        if (texStr == null) {
            texStr = texStr3;
        }
        if (texStr != null) {
            final String str = ".png";
            if (texStr.endsWith(str)) {
                texStr = texStr.substring(0, texStr.length() - str.length());
            }
            texStr = fixTextureName(texStr, basePath);
            return texStr;
        }
        String str = path;
        final int pos = path.lastIndexOf(47);
        if (pos >= 0) {
            str = path.substring(pos + 1);
        }
        final int pos2 = str.lastIndexOf(46);
        if (pos2 >= 0) {
            str = str.substring(0, pos2);
        }
        str = fixTextureName(str, basePath);
        return str;
    }
    
    private static Map parseTextures(final Properties props, final String basePath) {
        final String prefix = "texture.";
        final Map mapProps = getMatchingProperties(props, prefix);
        if (mapProps.size() <= 0) {
            return null;
        }
        final Set keySet = mapProps.keySet();
        final LinkedHashMap mapTex = new LinkedHashMap();
        for (String key : keySet) {
            String val = mapProps.get(key);
            val = fixTextureName(val, basePath);
            if (key.startsWith(prefix)) {
                key = key.substring(prefix.length());
            }
            mapTex.put(key, val);
        }
        return mapTex;
    }
    
    private static String fixTextureName(String iconName, final String basePath) {
        iconName = TextureUtils.fixResourcePath(iconName, basePath);
        if (!iconName.startsWith(basePath) && !iconName.startsWith("textures/") && !iconName.startsWith("mcpatcher/")) {
            iconName = basePath + "/" + iconName;
        }
        if (iconName.endsWith(".png")) {
            iconName = iconName.substring(0, iconName.length() - 4);
        }
        final String pathBlocks = "textures/blocks/";
        if (iconName.startsWith(pathBlocks)) {
            iconName = iconName.substring(pathBlocks.length());
        }
        if (iconName.startsWith("/")) {
            iconName = iconName.substring(1);
        }
        return iconName;
    }
    
    private int parseInt(String str, final int defVal) {
        if (str == null) {
            return defVal;
        }
        str = str.trim();
        final int val = Config.parseInt(str, Integer.MIN_VALUE);
        if (val == Integer.MIN_VALUE) {
            Config.warn("Invalid integer: " + str);
            return defVal;
        }
        return val;
    }
    
    private RangeListInt parseRangeListInt(final String str) {
        if (str == null) {
            return null;
        }
        final String[] tokens = Config.tokenize(str, " ");
        final RangeListInt rangeList = new RangeListInt();
        for (int i = 0; i < tokens.length; ++i) {
            final String token = tokens[i];
            final RangeInt range = this.parseRangeInt(token);
            if (range == null) {
                Config.warn("Invalid range list: " + str);
                return null;
            }
            rangeList.addRange(range);
        }
        return rangeList;
    }
    
    private RangeInt parseRangeInt(String str) {
        if (str == null) {
            return null;
        }
        str = str.trim();
        final int countMinus = str.length() - str.replace("-", "").length();
        if (countMinus > 1) {
            Config.warn("Invalid range: " + str);
            return null;
        }
        final String[] tokens = Config.tokenize(str, "- ");
        final int[] vals = new int[tokens.length];
        for (int min = 0; min < tokens.length; ++min) {
            final String max = tokens[min];
            final int val = Config.parseInt(max, -1);
            if (val < 0) {
                Config.warn("Invalid range: " + str);
                return null;
            }
            vals[min] = val;
        }
        if (vals.length == 1) {
            final int min = vals[0];
            if (str.startsWith("-")) {
                return new RangeInt(-1, min);
            }
            if (str.endsWith("-")) {
                return new RangeInt(min, -1);
            }
            return new RangeInt(min, min);
        }
        else {
            if (vals.length == 2) {
                final int min = Math.min(vals[0], vals[1]);
                final int var8 = Math.max(vals[0], vals[1]);
                return new RangeInt(min, var8);
            }
            Config.warn("Invalid range: " + str);
            return null;
        }
    }
    
    private NbtTagValue[] parseNbtTagValues(final Properties props) {
        final Map mapNbt = getMatchingProperties(props, "nbt.");
        if (mapNbt.size() <= 0) {
            return null;
        }
        final ArrayList listNbts = new ArrayList();
        final Set keySet = mapNbt.keySet();
        for (final String key : keySet) {
            final String val = mapNbt.get(key);
            final NbtTagValue nbt = new NbtTagValue(key, val);
            listNbts.add(nbt);
        }
        final NbtTagValue[] nbts2 = listNbts.toArray(new NbtTagValue[listNbts.size()]);
        return nbts2;
    }
    
    private static Map getMatchingProperties(final Properties props, final String keyPrefix) {
        final LinkedHashMap map = new LinkedHashMap();
        final Set keySet = props.keySet();
        for (final String key : keySet) {
            final String val = props.getProperty(key);
            if (key.startsWith(keyPrefix)) {
                map.put(key, val);
            }
        }
        return map;
    }
    
    public boolean isValid(final String path) {
        if (this.name == null || this.name.length() <= 0) {
            Config.warn("No name found: " + path);
            return false;
        }
        if (this.basePath == null) {
            Config.warn("No base path found: " + path);
            return false;
        }
        if (this.type == 0) {
            Config.warn("No type defined: " + path);
            return false;
        }
        if ((this.type == 1 || this.type == 3) && this.items == null) {
            Config.warn("No items defined: " + path);
            return false;
        }
        if (this.texture == null && this.mapTextures == null) {
            Config.warn("No texture specified: " + path);
            return false;
        }
        return true;
    }
    
    public void updateIcons(final TextureMap textureMap) {
        if (this.texture != null) {
            this.textureIcon = registerIcon(this.texture, textureMap);
        }
        if (this.mapTextures != null) {
            this.mapTextureIcons = new LinkedHashMap();
            final Set keySet = this.mapTextures.keySet();
            for (final String key : keySet) {
                final String val = this.mapTextures.get(key);
                final IIcon icon = registerIcon(val, textureMap);
                this.mapTextureIcons.put(key, icon);
            }
        }
    }
    
    private static IIcon registerIcon(final String tileName, final TextureMap textureMap) {
        if (tileName == null) {
            return null;
        }
        String fullName = tileName;
        if (!tileName.contains("/")) {
            fullName = "textures/blocks/" + tileName;
        }
        final String fileName = fullName + ".png";
        final ResourceLocation loc = new ResourceLocation(fileName);
        final boolean exists = Config.hasResource(loc);
        if (!exists) {
            Config.warn("File not found: " + fileName);
        }
        final IIcon icon = textureMap.registerIcon(tileName);
        return icon;
    }
}
