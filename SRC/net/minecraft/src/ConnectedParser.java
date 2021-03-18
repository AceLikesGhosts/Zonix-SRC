package net.minecraft.src;

import java.util.*;
import net.minecraft.block.*;
import net.minecraft.world.biome.*;

public class ConnectedParser
{
    private String context;
    private static final MatchBlock[] NO_MATCH_BLOCKS;
    
    public ConnectedParser(final String context) {
        this.context = null;
        this.context = context;
    }
    
    public String parseName(final String path) {
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
    
    public String parseBasePath(final String path) {
        final int pos = path.lastIndexOf(47);
        return (pos < 0) ? "" : path.substring(0, pos);
    }
    
    public MatchBlock[] parseMatchBlocks(final String propMatchBlocks) {
        if (propMatchBlocks == null) {
            return null;
        }
        final ArrayList list = new ArrayList();
        final String[] blockStrs = Config.tokenize(propMatchBlocks, " ");
        for (int mbs = 0; mbs < blockStrs.length; ++mbs) {
            final String blockStr = blockStrs[mbs];
            final MatchBlock[] mbs2 = this.parseMatchBlock(blockStr);
            if (mbs2 == null) {
                return ConnectedParser.NO_MATCH_BLOCKS;
            }
            list.addAll(Arrays.asList(mbs2));
        }
        final MatchBlock[] var7 = list.toArray(new MatchBlock[list.size()]);
        return var7;
    }
    
    public MatchBlock[] parseMatchBlock(String blockStr) {
        if (blockStr == null) {
            return null;
        }
        blockStr = blockStr.trim();
        if (blockStr.length() <= 0) {
            return null;
        }
        final String[] parts = Config.tokenize(blockStr, ":");
        String domain = "minecraft";
        final boolean blockIndex = false;
        byte var14;
        if (parts.length > 1 && this.isFullBlockName(parts)) {
            domain = parts[0];
            var14 = 1;
        }
        else {
            domain = "minecraft";
            var14 = 0;
        }
        final String blockPart = parts[var14];
        final String[] params = Arrays.copyOfRange(parts, var14 + 1, parts.length);
        final Block[] blocks = this.parseBlockPart(domain, blockPart);
        if (blocks == null) {
            return null;
        }
        final MatchBlock[] datas = new MatchBlock[blocks.length];
        for (int i = 0; i < blocks.length; ++i) {
            final Block block = blocks[i];
            final int blockId = Block.getIdFromBlock(block);
            int[] metadatas = null;
            if (params.length > 0) {
                metadatas = this.parseBlockMetadatas(block, params);
                if (metadatas == null) {
                    return null;
                }
            }
            final MatchBlock bd = new MatchBlock(blockId, metadatas);
            datas[i] = bd;
        }
        return datas;
    }
    
    public boolean isFullBlockName(final String[] parts) {
        if (parts.length < 2) {
            return false;
        }
        final String part1 = parts[1];
        return part1.length() >= 1 && !this.startsWithDigit(part1) && !part1.contains("=");
    }
    
    public boolean startsWithDigit(final String str) {
        if (str == null) {
            return false;
        }
        if (str.length() < 1) {
            return false;
        }
        final char ch = str.charAt(0);
        return Character.isDigit(ch);
    }
    
    public Block[] parseBlockPart(final String domain, final String blockPart) {
        if (this.startsWithDigit(blockPart)) {
            final int[] var8 = this.parseIntList(blockPart);
            if (var8 == null) {
                return null;
            }
            final Block[] var9 = new Block[var8.length];
            for (int var10 = 0; var10 < var8.length; ++var10) {
                final int id = var8[var10];
                final Block block1 = Block.getBlockById(id);
                if (block1 == null) {
                    this.warn("Block not found for id: " + id);
                    return null;
                }
                var9[var10] = block1;
            }
            return var9;
        }
        else {
            final String fullName = domain + ":" + blockPart;
            final Block block2 = Block.getBlockFromName(fullName);
            if (block2 == null) {
                this.warn("Block not found for name: " + fullName);
                return null;
            }
            final Block[] blocks = { block2 };
            return blocks;
        }
    }
    
    public int[] parseBlockMetadatas(final Block block, final String[] params) {
        if (params.length <= 0) {
            return null;
        }
        final String param0 = params[0];
        if (this.startsWithDigit(param0)) {
            final int[] mds = this.parseIntList(param0);
            return mds;
        }
        this.warn("Invalid block metadata: " + param0);
        return null;
    }
    
    public BiomeGenBase[] parseBiomes(final String str) {
        if (str == null) {
            return null;
        }
        final String[] biomeNames = Config.tokenize(str, " ");
        final ArrayList list = new ArrayList();
        for (int biomeArr = 0; biomeArr < biomeNames.length; ++biomeArr) {
            final String biomeName = biomeNames[biomeArr];
            final BiomeGenBase biome = this.findBiome(biomeName);
            if (biome == null) {
                this.warn("Biome not found: " + biomeName);
            }
            else {
                list.add(biome);
            }
        }
        final BiomeGenBase[] var7 = list.toArray(new BiomeGenBase[list.size()]);
        return var7;
    }
    
    public BiomeGenBase findBiome(String biomeName) {
        biomeName = biomeName.toLowerCase();
        if (biomeName.equals("nether")) {
            return BiomeGenBase.hell;
        }
        final BiomeGenBase[] biomeList = BiomeGenBase.getBiomeGenArray();
        for (int i = 0; i < biomeList.length; ++i) {
            final BiomeGenBase biome = biomeList[i];
            if (biome != null) {
                final String name = biome.biomeName.replace(" ", "").toLowerCase();
                if (name.equals(biomeName)) {
                    return biome;
                }
            }
        }
        return null;
    }
    
    public int parseInt(final String str) {
        if (str == null) {
            return -1;
        }
        final int num = Config.parseInt(str, -1);
        if (num < 0) {
            this.warn("Invalid number: " + str);
        }
        return num;
    }
    
    public int parseInt(final String str, final int defVal) {
        if (str == null) {
            return defVal;
        }
        final int num = Config.parseInt(str, -1);
        if (num < 0) {
            this.warn("Invalid number: " + str);
            return defVal;
        }
        return num;
    }
    
    public int[] parseIntList(final String str) {
        if (str == null) {
            return null;
        }
        final ArrayList list = new ArrayList();
        final String[] intStrs = Config.tokenize(str, " ,");
        for (int ints = 0; ints < intStrs.length; ++ints) {
            final String i = intStrs[ints];
            if (i.contains("-")) {
                final String[] val = Config.tokenize(i, "-");
                if (val.length != 2) {
                    this.warn("Invalid statusUpdateInterval: " + i + ", when parsing: " + str);
                }
                else {
                    final int min = Config.parseInt(val[0], -1);
                    final int max = Config.parseInt(val[1], -1);
                    if (min >= 0 && max >= 0 && min <= max) {
                        for (int n = min; n <= max; ++n) {
                            list.add(n);
                        }
                    }
                    else {
                        this.warn("Invalid statusUpdateInterval: " + i + ", when parsing: " + str);
                    }
                }
            }
            else {
                final int var12 = Config.parseInt(i, -1);
                if (var12 < 0) {
                    this.warn("Invalid number: " + i + ", when parsing: " + str);
                }
                else {
                    list.add(var12);
                }
            }
        }
        final int[] var13 = new int[list.size()];
        for (int var14 = 0; var14 < var13.length; ++var14) {
            var13[var14] = list.get(var14);
        }
        return var13;
    }
    
    public void dbg(final String str) {
        Config.dbg("" + this.context + ": " + str);
    }
    
    public void warn(final String str) {
        Config.warn("" + this.context + ": " + str);
    }
    
    public RangeListInt parseRangeListInt(final String str) {
        if (str == null) {
            return null;
        }
        final RangeListInt list = new RangeListInt();
        final String[] parts = Config.tokenize(str, " ,");
        for (int i = 0; i < parts.length; ++i) {
            final String part = parts[i];
            final RangeInt ri = this.parseRangeInt(part);
            if (ri == null) {
                return null;
            }
            list.addRange(ri);
        }
        return list;
    }
    
    private RangeInt parseRangeInt(final String str) {
        if (str == null) {
            return null;
        }
        if (str.indexOf(45) >= 0) {
            final String[] val1 = Config.tokenize(str, "-");
            if (val1.length != 2) {
                this.warn("Invalid range: " + str);
                return null;
            }
            final int min = Config.parseInt(val1[0], -1);
            final int max = Config.parseInt(val1[1], -1);
            if (min >= 0 && max >= 0) {
                return new RangeInt(min, max);
            }
            this.warn("Invalid range: " + str);
            return null;
        }
        else {
            final int val2 = Config.parseInt(str, -1);
            if (val2 < 0) {
                this.warn("Invalid integer: " + str);
                return null;
            }
            return new RangeInt(val2, val2);
        }
    }
    
    public static boolean parseBoolean(final String str) {
        return str != null && str.toLowerCase().equals("true");
    }
    
    public static int parseColor(String str, final int defVal) {
        if (str == null) {
            return defVal;
        }
        str = str.trim();
        try {
            final int e = Integer.parseInt(str, 16) & 0xFFFFFF;
            return e;
        }
        catch (NumberFormatException var3) {
            return defVal;
        }
    }
    
    static {
        NO_MATCH_BLOCKS = new MatchBlock[0];
    }
}
