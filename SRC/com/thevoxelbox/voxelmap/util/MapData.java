package com.thevoxelbox.voxelmap.util;

import java.util.*;
import net.minecraft.client.*;
import net.minecraft.world.biome.*;

public class MapData
{
    private static int DATABITS;
    private static int HEIGHTPOS;
    private static int MATERIALPOS;
    private static int METADATAPOS;
    private static int TINTPOS;
    private static int LIGHTPOS;
    private static int OCEANFLOORHEIGHTPOS;
    private static int OCEANFLOORMATERIALPOS;
    private static int OCEANFLOORMETADATAPOS;
    private static int OCEANFLOORTINTPOS;
    private static int OCEANFLOORLIGHTPOS;
    private static int TRANSPARENTHEIGHTPOS;
    private static int TRANSPARENTIDPOS;
    private static int TRANSPARENTMETADATAPOS;
    private static int TRANSPARENTTINTPOS;
    private static int TRANSPARENTLIGHTPOS;
    private static int BIOMEIDPOS;
    public Point[][] points;
    public ArrayList<Segment> segments;
    private int width;
    private int height;
    private Object dataLock;
    private Object labelLock;
    private int[] data;
    private ArrayList<BiomeLabel> labels;
    
    public MapData(final int width, final int height) {
        this.dataLock = new Object();
        this.labelLock = new Object();
        this.labels = new ArrayList<BiomeLabel>();
        this.width = width;
        this.height = height;
        this.data = new int[width * height * MapData.DATABITS];
    }
    
    public int getHeight(final int x, final int z) {
        return this.getData(x, z, MapData.HEIGHTPOS);
    }
    
    public int getMaterial(final int x, final int z) {
        return this.getData(x, z, MapData.MATERIALPOS);
    }
    
    public int getMetadata(final int x, final int z) {
        return this.getData(x, z, MapData.METADATAPOS);
    }
    
    public int getBiomeTint(final int x, final int z) {
        return this.getData(x, z, MapData.TINTPOS);
    }
    
    public int getLight(final int x, final int z) {
        return this.getData(x, z, MapData.LIGHTPOS);
    }
    
    public int getOceanFloorHeight(final int x, final int z) {
        return this.getData(x, z, MapData.OCEANFLOORHEIGHTPOS);
    }
    
    public int getOceanFloorMaterial(final int x, final int z) {
        return this.getData(x, z, MapData.OCEANFLOORMATERIALPOS);
    }
    
    public int getOceanFloorMetadata(final int x, final int z) {
        return this.getData(x, z, MapData.OCEANFLOORMETADATAPOS);
    }
    
    public int getOceanFloorBiomeTint(final int x, final int z) {
        return this.getData(x, z, MapData.OCEANFLOORTINTPOS);
    }
    
    public int getOceanFloorLight(final int x, final int z) {
        return this.getData(x, z, MapData.OCEANFLOORLIGHTPOS);
    }
    
    public int getTransparentHeight(final int x, final int z) {
        return this.getData(x, z, MapData.TRANSPARENTHEIGHTPOS);
    }
    
    public int getTransparentId(final int x, final int z) {
        return this.getData(x, z, MapData.TRANSPARENTIDPOS);
    }
    
    public int getTransparentMetadata(final int x, final int z) {
        return this.getData(x, z, MapData.TRANSPARENTMETADATAPOS);
    }
    
    public int getTransparentBiomeTint(final int x, final int z) {
        return this.getData(x, z, MapData.TRANSPARENTTINTPOS);
    }
    
    public int getTransparentLight(final int x, final int z) {
        return this.getData(x, z, MapData.TRANSPARENTLIGHTPOS);
    }
    
    public int getBiomeID(final int x, final int z) {
        return this.getData(x, z, MapData.BIOMEIDPOS);
    }
    
    public int getData(final int x, final int z, final int bit) {
        final int index = (x + z * this.width) * MapData.DATABITS + bit;
        return this.data[index];
    }
    
    public void setHeight(final int x, final int z, final int value) {
        this.setData(x, z, MapData.HEIGHTPOS, value);
    }
    
    public void setMaterial(final int x, final int z, final int value) {
        this.setData(x, z, MapData.MATERIALPOS, value);
    }
    
    public void setMetadata(final int x, final int z, final int value) {
        this.setData(x, z, MapData.METADATAPOS, value);
    }
    
    public void setBiomeTint(final int x, final int z, final int value) {
        this.setData(x, z, MapData.TINTPOS, value);
    }
    
    public void setLight(final int x, final int z, final int value) {
        this.setData(x, z, MapData.LIGHTPOS, value);
    }
    
    public void setOceanFloorHeight(final int x, final int z, final int value) {
        this.setData(x, z, MapData.OCEANFLOORHEIGHTPOS, value);
    }
    
    public void setOceanFloorMaterial(final int x, final int z, final int value) {
        this.setData(x, z, MapData.OCEANFLOORMATERIALPOS, value);
    }
    
    public void setOceanFloorMetadata(final int x, final int z, final int value) {
        this.setData(x, z, MapData.OCEANFLOORMETADATAPOS, value);
    }
    
    public void setOceanFloorBiomeTint(final int x, final int z, final int value) {
        this.setData(x, z, MapData.OCEANFLOORTINTPOS, value);
    }
    
    public void setOceanFloorLight(final int x, final int z, final int value) {
        this.setData(x, z, MapData.OCEANFLOORLIGHTPOS, value);
    }
    
    public void setTransparentHeight(final int x, final int z, final int value) {
        this.setData(x, z, MapData.TRANSPARENTHEIGHTPOS, value);
    }
    
    public void setTransparentId(final int x, final int z, final int value) {
        this.setData(x, z, MapData.TRANSPARENTIDPOS, value);
    }
    
    public void setTransparentMetadata(final int x, final int z, final int value) {
        this.setData(x, z, MapData.TRANSPARENTMETADATAPOS, value);
    }
    
    public void setTransparentBiomeTint(final int x, final int z, final int value) {
        this.setData(x, z, MapData.TRANSPARENTTINTPOS, value);
    }
    
    public void setTransparentLight(final int x, final int z, final int value) {
        this.setData(x, z, MapData.TRANSPARENTLIGHTPOS, value);
    }
    
    public void setBiomeID(final int x, final int z, final int value) {
        this.setData(x, z, MapData.BIOMEIDPOS, value);
    }
    
    public void setData(final int x, final int z, final int bit, final int value) {
        final int index = (x + z * this.width) * MapData.DATABITS + bit;
        this.data[index] = value;
    }
    
    public void moveX(final int offset) {
        synchronized (this.dataLock) {
            if (offset > 0) {
                System.arraycopy(this.data, offset * MapData.DATABITS, this.data, 0, this.data.length - offset * MapData.DATABITS);
            }
            else if (offset < 0) {
                System.arraycopy(this.data, 0, this.data, -offset * MapData.DATABITS, this.data.length + offset * MapData.DATABITS);
            }
        }
    }
    
    public void moveZ(final int offset) {
        synchronized (this.dataLock) {
            if (offset > 0) {
                System.arraycopy(this.data, offset * this.width * MapData.DATABITS, this.data, 0, this.data.length - offset * this.width * MapData.DATABITS);
            }
            else if (offset < 0) {
                System.arraycopy(this.data, 0, this.data, -offset * this.width * MapData.DATABITS, this.data.length + offset * this.width * MapData.DATABITS);
            }
        }
    }
    
    public void segmentBiomes() {
        this.points = new Point[this.width][this.height];
        this.segments = new ArrayList<Segment>();
        for (int x = 0; x < this.width; ++x) {
            for (int z = 0; z < this.height; ++z) {
                this.points[x][z] = new Point(x, z, this.getBiomeID(x, z));
            }
        }
        synchronized (this.dataLock) {
            for (int x2 = 0; x2 < this.width; ++x2) {
                for (int z2 = 0; z2 < this.height; ++z2) {
                    if (!this.points[x2][z2].inSegment) {
                        final Segment segment = new Segment(this.points[x2][z2]);
                        this.segments.add(segment);
                        segment.flood();
                    }
                }
            }
        }
    }
    
    public void findCenterOfSegments() {
        if (this.segments != null) {
            for (int t = 0; t < this.segments.size(); ++t) {
                final Segment segment = this.segments.get(t);
                if (segment.biome != -1) {
                    segment.calculateCenter();
                }
            }
        }
        synchronized (this.labelLock) {
            this.labels.clear();
            if (this.segments != null) {
                for (int t2 = 0; t2 < this.segments.size(); ++t2) {
                    final Segment segment2 = this.segments.get(t2);
                    if (segment2.biome != -1) {
                        final BiomeLabel label = new BiomeLabel();
                        label.biomeInt = segment2.biome;
                        label.size = segment2.memberPoints.size();
                        label.x = segment2.centerX;
                        label.z = segment2.centerZ;
                        this.labels.add(label);
                    }
                }
            }
        }
    }
    
    public ArrayList<BiomeLabel> getBiomeLabels() {
        final ArrayList<BiomeLabel> labelsToReturn = new ArrayList<BiomeLabel>();
        synchronized (this.labelLock) {
            labelsToReturn.addAll(this.labels);
        }
        return labelsToReturn;
    }
    
    static {
        MapData.DATABITS = 16;
        MapData.HEIGHTPOS = 0;
        MapData.MATERIALPOS = 1;
        MapData.METADATAPOS = 2;
        MapData.TINTPOS = 3;
        MapData.LIGHTPOS = 4;
        MapData.OCEANFLOORHEIGHTPOS = 5;
        MapData.OCEANFLOORMATERIALPOS = 6;
        MapData.OCEANFLOORMETADATAPOS = 7;
        MapData.OCEANFLOORTINTPOS = 8;
        MapData.OCEANFLOORLIGHTPOS = 9;
        MapData.TRANSPARENTHEIGHTPOS = 10;
        MapData.TRANSPARENTIDPOS = 11;
        MapData.TRANSPARENTMETADATAPOS = 12;
        MapData.TRANSPARENTTINTPOS = 13;
        MapData.TRANSPARENTLIGHTPOS = 14;
        MapData.BIOMEIDPOS = 15;
    }
    
    private class Point
    {
        public int x;
        public int z;
        public boolean inSegment;
        public boolean isCandidate;
        public boolean isEroded;
        public int layer;
        public int biome;
        
        public Point(final int x, final int z, final int biome) {
            this.inSegment = false;
            this.isCandidate = false;
            this.isEroded = false;
            this.layer = -1;
            this.biome = -1;
            this.x = x;
            this.z = z;
            this.biome = biome;
        }
    }
    
    public class Segment
    {
        public ArrayList<Point> memberPoints;
        public int biome;
        public int centerX;
        public int centerZ;
        ArrayList<Point> currentShell;
        
        public Segment(final Point point) {
            this.centerX = 0;
            this.centerZ = 0;
            this.biome = point.biome;
            (this.memberPoints = new ArrayList<Point>()).add(point);
            this.currentShell = new ArrayList<Point>();
        }
        
        public void flood() {
            final ArrayList<Point> candidatePoints = new ArrayList<Point>();
            candidatePoints.add(this.memberPoints.remove(0));
            while (candidatePoints.size() > 0) {
                final Point point = candidatePoints.remove(0);
                point.isCandidate = false;
                if (point.biome == this.biome) {
                    this.memberPoints.add(point);
                    point.inSegment = true;
                    boolean edge = false;
                    if (point.x < MapData.this.width - 1) {
                        final Point neighbor = MapData.this.points[point.x + 1][point.z];
                        if (!neighbor.inSegment && !neighbor.isCandidate) {
                            candidatePoints.add(neighbor);
                            neighbor.isCandidate = true;
                        }
                        if (neighbor.biome != point.biome) {
                            edge = true;
                        }
                    }
                    else {
                        edge = true;
                    }
                    if (point.x > 0) {
                        final Point neighbor = MapData.this.points[point.x - 1][point.z];
                        if (!neighbor.inSegment && !neighbor.isCandidate) {
                            candidatePoints.add(neighbor);
                            neighbor.isCandidate = true;
                        }
                        if (neighbor.biome != point.biome) {
                            edge = true;
                        }
                    }
                    else {
                        edge = true;
                    }
                    if (point.z < MapData.this.height - 1) {
                        final Point neighbor = MapData.this.points[point.x][point.z + 1];
                        if (!neighbor.inSegment && !neighbor.isCandidate) {
                            candidatePoints.add(neighbor);
                            neighbor.isCandidate = true;
                        }
                        if (neighbor.biome != point.biome) {
                            edge = true;
                        }
                    }
                    else {
                        edge = true;
                    }
                    if (point.z > 0) {
                        final Point neighbor = MapData.this.points[point.x][point.z - 1];
                        if (!neighbor.inSegment && !neighbor.isCandidate) {
                            candidatePoints.add(neighbor);
                            neighbor.isCandidate = true;
                        }
                        if (neighbor.biome != point.biome) {
                            edge = true;
                        }
                    }
                    else {
                        edge = true;
                    }
                    if (!edge) {
                        continue;
                    }
                    point.layer = 0;
                    this.currentShell.add(point);
                }
            }
        }
        
        public void calculateCenter() {
            this.calculateCenterOfMass();
            this.morphologicallyErode();
        }
        
        public void calculateCenterOfMass() {
            this.calculateCenterOfMass(this.memberPoints);
        }
        
        public void calculateCenterOfMass(final Collection<Point> points) {
            this.centerX = 0;
            this.centerZ = 0;
            for (final Point point : points) {
                this.centerX += point.x;
                this.centerZ += point.z;
            }
            this.centerX /= points.size();
            this.centerZ /= points.size();
        }
        
        public void calculateClosestPointToCenter(final Collection<Point> points) {
            int distanceSquared = 131072;
            Point centerPoint = null;
            for (final Point point : points) {
                final int pointDistanceSquared = (point.x - this.centerX) * (point.x - this.centerX) + (point.z - this.centerZ) * (point.z - this.centerZ);
                if (pointDistanceSquared < distanceSquared) {
                    distanceSquared = pointDistanceSquared;
                    centerPoint = point;
                }
            }
            this.centerX = centerPoint.x;
            this.centerZ = centerPoint.z;
        }
        
        public void morphologicallyErode() {
            final float labelWidth = (float)(Minecraft.getMinecraft().fontRenderer.getStringWidth(BiomeGenBase.getBiomeGenArray()[this.biome].biomeName) + 8);
            final float multi = (float)(MapData.this.width / 32);
            final float shellWidth = 2.0f;
            float labelPadding;
            int layer;
            for (labelPadding = labelWidth / 16.0f * multi / shellWidth, layer = 0; this.currentShell.size() > 0 && layer < labelPadding; ++layer, this.currentShell = this.getNextShell(this.currentShell, layer)) {}
            if (this.currentShell.size() > 0) {
                final ArrayList<Point> remainingPoints = new ArrayList<Point>();
                for (final Point point : this.memberPoints) {
                    if (point.layer < 0 || point.layer == layer) {
                        remainingPoints.add(point);
                    }
                }
                this.calculateClosestPointToCenter(remainingPoints);
            }
        }
        
        public ArrayList<Point> getNextShell(final Collection<Point> pointsToCheck, final int layer) {
            final ArrayList<Point> nextShell = new ArrayList<Point>();
            for (final Point point : pointsToCheck) {
                if (point.x < MapData.this.width - 2) {
                    final Point neighbor1 = MapData.this.points[point.x + 1][point.z];
                    final Point neighbor2 = MapData.this.points[point.x + 2][point.z];
                    if (neighbor1.biome == point.biome && neighbor1.layer < 0 && neighbor2.biome == point.biome && neighbor2.layer < 0) {
                        neighbor1.layer = layer;
                        neighbor2.layer = layer;
                        nextShell.add(neighbor2);
                    }
                    else if (neighbor1.biome == point.biome && neighbor1.layer < 0) {
                        neighbor1.layer = layer;
                        nextShell.add(neighbor1);
                    }
                    else if (neighbor2.biome == point.biome && neighbor2.layer < 0) {
                        neighbor2.layer = layer;
                        nextShell.add(neighbor2);
                    }
                }
                if (point.x > 1) {
                    final Point neighbor1 = MapData.this.points[point.x - 1][point.z];
                    final Point neighbor2 = MapData.this.points[point.x - 2][point.z];
                    if (neighbor1.biome == point.biome && neighbor1.layer < 0 && neighbor2.biome == point.biome && neighbor2.layer < 0) {
                        neighbor1.layer = layer;
                        neighbor2.layer = layer;
                        nextShell.add(neighbor2);
                    }
                    else if (neighbor1.biome == point.biome && neighbor1.layer < 0) {
                        neighbor1.layer = layer;
                        nextShell.add(neighbor1);
                    }
                    else if (neighbor2.biome == point.biome && neighbor2.layer < 0) {
                        neighbor2.layer = layer;
                        nextShell.add(neighbor2);
                    }
                }
                if (point.z < MapData.this.height - 1) {
                    final Point neighbor3 = MapData.this.points[point.x][point.z + 1];
                    if (neighbor3.biome == point.biome && neighbor3.layer < 0) {
                        neighbor3.layer = layer;
                        nextShell.add(neighbor3);
                    }
                }
                if (point.z > 0) {
                    final Point neighbor3 = MapData.this.points[point.x][point.z - 1];
                    if (neighbor3.biome != point.biome || neighbor3.layer >= 0) {
                        continue;
                    }
                    neighbor3.layer = layer;
                    nextShell.add(neighbor3);
                }
            }
            if (nextShell.size() > 0) {
                return nextShell;
            }
            this.calculateCenterOfMass(pointsToCheck);
            this.calculateClosestPointToCenter(pointsToCheck);
            return nextShell;
        }
    }
    
    public class BiomeLabel
    {
        public int biomeInt;
        public int size;
        public int x;
        public int z;
        
        public BiomeLabel() {
            this.biomeInt = -1;
            this.size = 0;
            this.x = 0;
            this.z = 0;
        }
    }
}
