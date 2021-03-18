package net.minecraft.src;

import net.minecraft.world.*;
import net.minecraft.client.renderer.*;
import net.minecraft.block.*;
import net.minecraft.world.biome.*;
import net.minecraft.client.renderer.texture.*;
import net.minecraft.util.*;
import net.minecraft.client.resources.*;
import java.io.*;
import java.util.zip.*;
import java.util.*;

public class ConnectedTextures
{
    private static ConnectedProperties[][] blockProperties;
    private static ConnectedProperties[][] tileProperties;
    private static boolean multipass;
    private static final int BOTTOM = 0;
    private static final int TOP = 1;
    private static final int EAST = 2;
    private static final int WEST = 3;
    private static final int NORTH = 4;
    private static final int SOUTH = 5;
    private static final int Y_NEG = 0;
    private static final int Y_POS = 1;
    private static final int Z_NEG = 2;
    private static final int Z_POS = 3;
    private static final int X_NEG = 4;
    private static final int X_POS = 5;
    private static final int Y_AXIS = 0;
    private static final int Z_AXIS = 1;
    private static final int X_AXIS = 2;
    private static final String[] propSuffixes;
    private static final int[] ctmIndexes;
    
    public static IIcon getConnectedTexture(final IBlockAccess blockAccess, final Block block, final int x, final int y, final int z, final int side, final IIcon icon) {
        final IIcon newIcon = getConnectedTexture0(blockAccess, block, x, y, z, side, icon);
        if (block.renderAsNormalBlock() && ((TextureAtlasSprite)newIcon).hasTransparency()) {
            return icon;
        }
        return newIcon;
    }
    
    private static IIcon getConnectedTexture0(final IBlockAccess blockAccess, final Block block, final int x, final int y, final int z, final int side, final IIcon icon) {
        if (blockAccess == null) {
            return icon;
        }
        final IIcon newIcon = getConnectedTextureSingle(blockAccess, block, x, y, z, side, icon, true);
        if (!ConnectedTextures.multipass) {
            return newIcon;
        }
        if (newIcon == icon) {
            return newIcon;
        }
        IIcon mpIcon = newIcon;
        for (int i = 0; i < 3; ++i) {
            final IIcon newMpIcon = getConnectedTextureSingle(blockAccess, block, x, y, z, side, mpIcon, false);
            if (newMpIcon == mpIcon) {
                break;
            }
            mpIcon = newMpIcon;
        }
        return mpIcon;
    }
    
    public static IIcon getConnectedTextureSingle(final IBlockAccess blockAccess, final Block block, final int x, final int y, final int z, final int side, final IIcon icon, final boolean checkBlocks) {
        if (!(icon instanceof TextureAtlasSprite)) {
            return icon;
        }
        final TextureAtlasSprite ts = (TextureAtlasSprite)icon;
        final int iconId = ts.getIndexInMap();
        int metadata = -1;
        if (ConnectedTextures.tileProperties != null && Tessellator.instance.defaultTexture && iconId >= 0 && iconId < ConnectedTextures.tileProperties.length) {
            final ConnectedProperties[] cps = ConnectedTextures.tileProperties[iconId];
            if (cps != null) {
                if (metadata < 0) {
                    metadata = blockAccess.getBlockMetadata(x, y, z);
                }
                final IIcon newIcon = getConnectedTexture(cps, blockAccess, block, x, y, z, side, ts, metadata);
                if (newIcon != null) {
                    return newIcon;
                }
            }
        }
        if (ConnectedTextures.blockProperties != null && checkBlocks) {
            final int blockId = Block.getIdFromBlock(block);
            if (blockId >= 0 && blockId < ConnectedTextures.blockProperties.length) {
                final ConnectedProperties[] cps2 = ConnectedTextures.blockProperties[blockId];
                if (cps2 != null) {
                    if (metadata < 0) {
                        metadata = blockAccess.getBlockMetadata(x, y, z);
                    }
                    final IIcon newIcon2 = getConnectedTexture(cps2, blockAccess, block, x, y, z, side, ts, metadata);
                    if (newIcon2 != null) {
                        return newIcon2;
                    }
                }
            }
        }
        return icon;
    }
    
    public static ConnectedProperties getConnectedProperties(final IBlockAccess blockAccess, final Block block, final int x, final int y, final int z, final int side, final IIcon icon) {
        if (blockAccess == null) {
            return null;
        }
        if (!(icon instanceof TextureAtlasSprite)) {
            return null;
        }
        final TextureAtlasSprite ts = (TextureAtlasSprite)icon;
        final int iconId = ts.getIndexInMap();
        int metadata = -1;
        if (ConnectedTextures.tileProperties != null && Tessellator.instance.defaultTexture && iconId >= 0 && iconId < ConnectedTextures.tileProperties.length) {
            final ConnectedProperties[] cps = ConnectedTextures.tileProperties[iconId];
            if (cps != null) {
                if (metadata < 0) {
                    metadata = blockAccess.getBlockMetadata(x, y, z);
                }
                final ConnectedProperties cp = getConnectedProperties(cps, blockAccess, block, x, y, z, side, ts, metadata);
                if (cp != null) {
                    return cp;
                }
            }
        }
        if (ConnectedTextures.blockProperties != null) {
            final int blockId = Block.getIdFromBlock(block);
            if (blockId >= 0 && blockId < ConnectedTextures.blockProperties.length) {
                final ConnectedProperties[] cps2 = ConnectedTextures.blockProperties[blockId];
                if (cps2 != null) {
                    if (metadata < 0) {
                        metadata = blockAccess.getBlockMetadata(x, y, z);
                    }
                    final ConnectedProperties cp2 = getConnectedProperties(cps2, blockAccess, block, x, y, z, side, ts, metadata);
                    if (cp2 != null) {
                        return cp2;
                    }
                }
            }
        }
        return null;
    }
    
    private static IIcon getConnectedTexture(final ConnectedProperties[] cps, final IBlockAccess blockAccess, final Block block, final int x, final int y, final int z, final int side, final IIcon icon, final int metadata) {
        for (int i = 0; i < cps.length; ++i) {
            final ConnectedProperties cp = cps[i];
            if (cp != null) {
                final IIcon newIcon = getConnectedTexture(cp, blockAccess, block, x, y, z, side, icon, metadata);
                if (newIcon != null) {
                    return newIcon;
                }
            }
        }
        return null;
    }
    
    private static ConnectedProperties getConnectedProperties(final ConnectedProperties[] cps, final IBlockAccess blockAccess, final Block block, final int x, final int y, final int z, final int side, final IIcon icon, final int metadata) {
        for (int i = 0; i < cps.length; ++i) {
            final ConnectedProperties cp = cps[i];
            if (cp != null) {
                final IIcon newIcon = getConnectedTexture(cp, blockAccess, block, x, y, z, side, icon, metadata);
                if (newIcon != null) {
                    return cp;
                }
            }
        }
        return null;
    }
    
    private static IIcon getConnectedTexture(final ConnectedProperties cp, final IBlockAccess blockAccess, final Block block, final int x, final int y, final int z, final int side, final IIcon icon, final int metadata) {
        if (y < cp.minHeight || y > cp.maxHeight) {
            return null;
        }
        if (cp.biomes != null) {
            final BiomeGenBase blockBiome = blockAccess.getBiomeGenForCoords(x, z);
            boolean biomeOk = false;
            for (int i = 0; i < cp.biomes.length; ++i) {
                final BiomeGenBase biome = cp.biomes[i];
                if (blockBiome == biome) {
                    biomeOk = true;
                    break;
                }
            }
            if (!biomeOk) {
                return null;
            }
        }
        int vertAxis = 0;
        int metadataCheck = metadata;
        if (block instanceof BlockRotatedPillar) {
            vertAxis = getWoodAxis(side, metadata);
            metadataCheck &= 0x3;
        }
        if (block instanceof BlockQuartz) {
            vertAxis = getQuartzAxis(side, metadata);
            if (metadataCheck > 2) {
                metadataCheck = 2;
            }
        }
        if (side >= 0 && cp.faces != 63) {
            int sideCheck = side;
            if (vertAxis != 0) {
                sideCheck = fixSideByAxis(side, vertAxis);
            }
            if ((1 << sideCheck & cp.faces) == 0x0) {
                return null;
            }
        }
        if (cp.metadatas != null) {
            final int[] mds = cp.metadatas;
            boolean metadataFound = false;
            for (int j = 0; j < mds.length; ++j) {
                if (mds[j] == metadataCheck) {
                    metadataFound = true;
                    break;
                }
            }
            if (!metadataFound) {
                return null;
            }
        }
        switch (cp.method) {
            case 1: {
                return getConnectedTextureCtm(cp, blockAccess, block, x, y, z, side, icon, metadata);
            }
            case 2: {
                return getConnectedTextureHorizontal(cp, blockAccess, block, x, y, z, vertAxis, side, icon, metadata);
            }
            case 6: {
                return getConnectedTextureVertical(cp, blockAccess, block, x, y, z, vertAxis, side, icon, metadata);
            }
            case 3: {
                return getConnectedTextureTop(cp, blockAccess, block, x, y, z, vertAxis, side, icon, metadata);
            }
            case 4: {
                return getConnectedTextureRandom(cp, x, y, z, side);
            }
            case 5: {
                return getConnectedTextureRepeat(cp, x, y, z, side);
            }
            case 7: {
                return getConnectedTextureFixed(cp);
            }
            case 8: {
                return getConnectedTextureHorizontalVertical(cp, blockAccess, block, x, y, z, vertAxis, side, icon, metadata);
            }
            case 9: {
                return getConnectedTextureVerticalHorizontal(cp, blockAccess, block, x, y, z, vertAxis, side, icon, metadata);
            }
            default: {
                return null;
            }
        }
    }
    
    private static int fixSideByAxis(final int side, final int vertAxis) {
        switch (vertAxis) {
            case 0: {
                return side;
            }
            case 1: {
                switch (side) {
                    case 0: {
                        return 2;
                    }
                    case 1: {
                        return 3;
                    }
                    case 2: {
                        return 1;
                    }
                    case 3: {
                        return 0;
                    }
                    default: {
                        return side;
                    }
                }
                break;
            }
            case 2: {
                switch (side) {
                    case 0: {
                        return 4;
                    }
                    case 1: {
                        return 5;
                    }
                    case 4: {
                        return 1;
                    }
                    case 5: {
                        return 0;
                    }
                    default: {
                        return side;
                    }
                }
                break;
            }
            default: {
                return side;
            }
        }
    }
    
    private static int getWoodAxis(final int side, final int metadata) {
        final int orient = (metadata & 0xC) >> 2;
        switch (orient) {
            case 1: {
                return 2;
            }
            case 2: {
                return 1;
            }
            default: {
                return 0;
            }
        }
    }
    
    private static int getQuartzAxis(final int side, final int metadata) {
        switch (metadata) {
            case 3: {
                return 2;
            }
            case 4: {
                return 1;
            }
            default: {
                return 0;
            }
        }
    }
    
    private static IIcon getConnectedTextureRandom(final ConnectedProperties cp, final int x, final int y, final int z, final int side) {
        if (cp.tileIcons.length == 1) {
            return cp.tileIcons[0];
        }
        final int face = side / cp.symmetry * cp.symmetry;
        final int rand = Config.getRandom(x, y, z, face) & Integer.MAX_VALUE;
        int index = 0;
        if (cp.weights == null) {
            index = rand % cp.tileIcons.length;
        }
        else {
            final int randWeight = rand % cp.sumAllWeights;
            final int[] sumWeights = cp.sumWeights;
            for (int i = 0; i < sumWeights.length; ++i) {
                if (randWeight < sumWeights[i]) {
                    index = i;
                    break;
                }
            }
        }
        return cp.tileIcons[index];
    }
    
    private static IIcon getConnectedTextureFixed(final ConnectedProperties cp) {
        return cp.tileIcons[0];
    }
    
    private static IIcon getConnectedTextureRepeat(final ConnectedProperties cp, final int x, final int y, final int z, final int side) {
        if (cp.tileIcons.length == 1) {
            return cp.tileIcons[0];
        }
        int nx = 0;
        int ny = 0;
        switch (side) {
            case 0: {
                nx = x;
                ny = z;
                break;
            }
            case 1: {
                nx = x;
                ny = z;
                break;
            }
            case 2: {
                nx = -x - 1;
                ny = -y;
                break;
            }
            case 3: {
                nx = x;
                ny = -y;
                break;
            }
            case 4: {
                nx = z;
                ny = -y;
                break;
            }
            case 5: {
                nx = -z - 1;
                ny = -y;
                break;
            }
        }
        nx %= cp.width;
        ny %= cp.height;
        if (nx < 0) {
            nx += cp.width;
        }
        if (ny < 0) {
            ny += cp.height;
        }
        final int index = ny * cp.width + nx;
        return cp.tileIcons[index];
    }
    
    private static IIcon getConnectedTextureCtm(final ConnectedProperties cp, final IBlockAccess blockAccess, final Block block, final int x, final int y, final int z, final int side, final IIcon icon, final int metadata) {
        final boolean[] borders = new boolean[6];
        switch (side) {
            case 0:
            case 1: {
                borders[0] = isNeighbour(cp, blockAccess, block, x - 1, y, z, side, icon, metadata);
                borders[1] = isNeighbour(cp, blockAccess, block, x + 1, y, z, side, icon, metadata);
                borders[2] = isNeighbour(cp, blockAccess, block, x, y, z + 1, side, icon, metadata);
                borders[3] = isNeighbour(cp, blockAccess, block, x, y, z - 1, side, icon, metadata);
                break;
            }
            case 2: {
                borders[0] = isNeighbour(cp, blockAccess, block, x + 1, y, z, side, icon, metadata);
                borders[1] = isNeighbour(cp, blockAccess, block, x - 1, y, z, side, icon, metadata);
                borders[2] = isNeighbour(cp, blockAccess, block, x, y - 1, z, side, icon, metadata);
                borders[3] = isNeighbour(cp, blockAccess, block, x, y + 1, z, side, icon, metadata);
                break;
            }
            case 3: {
                borders[0] = isNeighbour(cp, blockAccess, block, x - 1, y, z, side, icon, metadata);
                borders[1] = isNeighbour(cp, blockAccess, block, x + 1, y, z, side, icon, metadata);
                borders[2] = isNeighbour(cp, blockAccess, block, x, y - 1, z, side, icon, metadata);
                borders[3] = isNeighbour(cp, blockAccess, block, x, y + 1, z, side, icon, metadata);
                break;
            }
            case 4: {
                borders[0] = isNeighbour(cp, blockAccess, block, x, y, z - 1, side, icon, metadata);
                borders[1] = isNeighbour(cp, blockAccess, block, x, y, z + 1, side, icon, metadata);
                borders[2] = isNeighbour(cp, blockAccess, block, x, y - 1, z, side, icon, metadata);
                borders[3] = isNeighbour(cp, blockAccess, block, x, y + 1, z, side, icon, metadata);
                break;
            }
            case 5: {
                borders[0] = isNeighbour(cp, blockAccess, block, x, y, z + 1, side, icon, metadata);
                borders[1] = isNeighbour(cp, blockAccess, block, x, y, z - 1, side, icon, metadata);
                borders[2] = isNeighbour(cp, blockAccess, block, x, y - 1, z, side, icon, metadata);
                borders[3] = isNeighbour(cp, blockAccess, block, x, y + 1, z, side, icon, metadata);
                break;
            }
        }
        int index = 0;
        if (borders[0] & !borders[1] & !borders[2] & !borders[3]) {
            index = 3;
        }
        else if (!borders[0] & borders[1] & !borders[2] & !borders[3]) {
            index = 1;
        }
        else if (!borders[0] & !borders[1] & borders[2] & !borders[3]) {
            index = 12;
        }
        else if (!borders[0] & !borders[1] & !borders[2] & borders[3]) {
            index = 36;
        }
        else if (borders[0] & borders[1] & !borders[2] & !borders[3]) {
            index = 2;
        }
        else if (!borders[0] & !borders[1] & borders[2] & borders[3]) {
            index = 24;
        }
        else if (borders[0] & !borders[1] & borders[2] & !borders[3]) {
            index = 15;
        }
        else if (borders[0] & !borders[1] & !borders[2] & borders[3]) {
            index = 39;
        }
        else if (!borders[0] & borders[1] & borders[2] & !borders[3]) {
            index = 13;
        }
        else if (!borders[0] & borders[1] & !borders[2] & borders[3]) {
            index = 37;
        }
        else if (!borders[0] & borders[1] & borders[2] & borders[3]) {
            index = 25;
        }
        else if (borders[0] & !borders[1] & borders[2] & borders[3]) {
            index = 27;
        }
        else if (borders[0] & borders[1] & !borders[2] & borders[3]) {
            index = 38;
        }
        else if (borders[0] & borders[1] & borders[2] & !borders[3]) {
            index = 14;
        }
        else if (borders[0] & borders[1] & borders[2] & borders[3]) {
            index = 26;
        }
        if (index == 0) {
            return cp.tileIcons[index];
        }
        if (!Config.isConnectedTexturesFancy()) {
            return cp.tileIcons[index];
        }
        final boolean[] edges = new boolean[6];
        switch (side) {
            case 0:
            case 1: {
                edges[0] = !isNeighbour(cp, blockAccess, block, x + 1, y, z + 1, side, icon, metadata);
                edges[1] = !isNeighbour(cp, blockAccess, block, x - 1, y, z + 1, side, icon, metadata);
                edges[2] = !isNeighbour(cp, blockAccess, block, x + 1, y, z - 1, side, icon, metadata);
                edges[3] = !isNeighbour(cp, blockAccess, block, x - 1, y, z - 1, side, icon, metadata);
                break;
            }
            case 2: {
                edges[0] = !isNeighbour(cp, blockAccess, block, x - 1, y - 1, z, side, icon, metadata);
                edges[1] = !isNeighbour(cp, blockAccess, block, x + 1, y - 1, z, side, icon, metadata);
                edges[2] = !isNeighbour(cp, blockAccess, block, x - 1, y + 1, z, side, icon, metadata);
                edges[3] = !isNeighbour(cp, blockAccess, block, x + 1, y + 1, z, side, icon, metadata);
                break;
            }
            case 3: {
                edges[0] = !isNeighbour(cp, blockAccess, block, x + 1, y - 1, z, side, icon, metadata);
                edges[1] = !isNeighbour(cp, blockAccess, block, x - 1, y - 1, z, side, icon, metadata);
                edges[2] = !isNeighbour(cp, blockAccess, block, x + 1, y + 1, z, side, icon, metadata);
                edges[3] = !isNeighbour(cp, blockAccess, block, x - 1, y + 1, z, side, icon, metadata);
                break;
            }
            case 4: {
                edges[0] = !isNeighbour(cp, blockAccess, block, x, y - 1, z + 1, side, icon, metadata);
                edges[1] = !isNeighbour(cp, blockAccess, block, x, y - 1, z - 1, side, icon, metadata);
                edges[2] = !isNeighbour(cp, blockAccess, block, x, y + 1, z + 1, side, icon, metadata);
                edges[3] = !isNeighbour(cp, blockAccess, block, x, y + 1, z - 1, side, icon, metadata);
                break;
            }
            case 5: {
                edges[0] = !isNeighbour(cp, blockAccess, block, x, y - 1, z - 1, side, icon, metadata);
                edges[1] = !isNeighbour(cp, blockAccess, block, x, y - 1, z + 1, side, icon, metadata);
                edges[2] = !isNeighbour(cp, blockAccess, block, x, y + 1, z - 1, side, icon, metadata);
                edges[3] = !isNeighbour(cp, blockAccess, block, x, y + 1, z + 1, side, icon, metadata);
                break;
            }
        }
        if (index == 13 && edges[0]) {
            index = 4;
        }
        else if (index == 15 && edges[1]) {
            index = 5;
        }
        else if (index == 37 && edges[2]) {
            index = 16;
        }
        else if (index == 39 && edges[3]) {
            index = 17;
        }
        else if (index == 14 && edges[0] && edges[1]) {
            index = 7;
        }
        else if (index == 25 && edges[0] && edges[2]) {
            index = 6;
        }
        else if (index == 27 && edges[3] && edges[1]) {
            index = 19;
        }
        else if (index == 38 && edges[3] && edges[2]) {
            index = 18;
        }
        else if (index == 14 && !edges[0] && edges[1]) {
            index = 31;
        }
        else if (index == 25 && edges[0] && !edges[2]) {
            index = 30;
        }
        else if (index == 27 && !edges[3] && edges[1]) {
            index = 41;
        }
        else if (index == 38 && edges[3] && !edges[2]) {
            index = 40;
        }
        else if (index == 14 && edges[0] && !edges[1]) {
            index = 29;
        }
        else if (index == 25 && !edges[0] && edges[2]) {
            index = 28;
        }
        else if (index == 27 && edges[3] && !edges[1]) {
            index = 43;
        }
        else if (index == 38 && !edges[3] && edges[2]) {
            index = 42;
        }
        else if (index == 26 && edges[0] && edges[1] && edges[2] && edges[3]) {
            index = 46;
        }
        else if (index == 26 && !edges[0] && edges[1] && edges[2] && edges[3]) {
            index = 9;
        }
        else if (index == 26 && edges[0] && !edges[1] && edges[2] && edges[3]) {
            index = 21;
        }
        else if (index == 26 && edges[0] && edges[1] && !edges[2] && edges[3]) {
            index = 8;
        }
        else if (index == 26 && edges[0] && edges[1] && edges[2] && !edges[3]) {
            index = 20;
        }
        else if (index == 26 && edges[0] && edges[1] && !edges[2] && !edges[3]) {
            index = 11;
        }
        else if (index == 26 && !edges[0] && !edges[1] && edges[2] && edges[3]) {
            index = 22;
        }
        else if (index == 26 && !edges[0] && edges[1] && !edges[2] && edges[3]) {
            index = 23;
        }
        else if (index == 26 && edges[0] && !edges[1] && edges[2] && !edges[3]) {
            index = 10;
        }
        else if (index == 26 && edges[0] && !edges[1] && !edges[2] && edges[3]) {
            index = 34;
        }
        else if (index == 26 && !edges[0] && edges[1] && edges[2] && !edges[3]) {
            index = 35;
        }
        else if (index == 26 && edges[0] && !edges[1] && !edges[2] && !edges[3]) {
            index = 32;
        }
        else if (index == 26 && !edges[0] && edges[1] && !edges[2] && !edges[3]) {
            index = 33;
        }
        else if (index == 26 && !edges[0] && !edges[1] && edges[2] && !edges[3]) {
            index = 44;
        }
        else if (index == 26 && !edges[0] && !edges[1] && !edges[2] && edges[3]) {
            index = 45;
        }
        return cp.tileIcons[index];
    }
    
    private static boolean isNeighbour(final ConnectedProperties cp, final IBlockAccess iblockaccess, final Block block, final int x, final int y, final int z, final int side, final IIcon icon, final int metadata) {
        final Block neighbourBlock = iblockaccess.getBlock(x, y, z);
        if (cp.connect == 2) {
            if (neighbourBlock == null) {
                return false;
            }
            final int neighbourMetadata = iblockaccess.getBlockMetadata(x, y, z);
            IIcon neighbourIcon;
            if (side >= 0) {
                neighbourIcon = neighbourBlock.getIcon(side, neighbourMetadata);
            }
            else {
                neighbourIcon = neighbourBlock.getIcon(1, neighbourMetadata);
            }
            return neighbourIcon == icon;
        }
        else {
            if (cp.connect == 3) {
                return neighbourBlock != null && neighbourBlock.getMaterial() == block.getMaterial();
            }
            return neighbourBlock == block && iblockaccess.getBlockMetadata(x, y, z) == metadata;
        }
    }
    
    private static IIcon getConnectedTextureHorizontal(final ConnectedProperties cp, final IBlockAccess blockAccess, final Block block, final int x, final int y, final int z, final int vertAxis, final int side, final IIcon icon, final int metadata) {
        boolean left = false;
        boolean right = false;
        Label_0703: {
            switch (vertAxis) {
                case 0: {
                    switch (side) {
                        case 0:
                        case 1: {
                            return null;
                        }
                        case 2: {
                            left = isNeighbour(cp, blockAccess, block, x + 1, y, z, side, icon, metadata);
                            right = isNeighbour(cp, blockAccess, block, x - 1, y, z, side, icon, metadata);
                            break;
                        }
                        case 3: {
                            left = isNeighbour(cp, blockAccess, block, x - 1, y, z, side, icon, metadata);
                            right = isNeighbour(cp, blockAccess, block, x + 1, y, z, side, icon, metadata);
                            break;
                        }
                        case 4: {
                            left = isNeighbour(cp, blockAccess, block, x, y, z - 1, side, icon, metadata);
                            right = isNeighbour(cp, blockAccess, block, x, y, z + 1, side, icon, metadata);
                            break;
                        }
                        case 5: {
                            left = isNeighbour(cp, blockAccess, block, x, y, z + 1, side, icon, metadata);
                            right = isNeighbour(cp, blockAccess, block, x, y, z - 1, side, icon, metadata);
                            break;
                        }
                    }
                    break;
                }
                case 1: {
                    switch (side) {
                        case 2:
                        case 3: {
                            return null;
                        }
                        case 0: {
                            left = isNeighbour(cp, blockAccess, block, x + 1, y, z, side, icon, metadata);
                            right = isNeighbour(cp, blockAccess, block, x - 1, y, z, side, icon, metadata);
                            break;
                        }
                        case 1: {
                            left = isNeighbour(cp, blockAccess, block, x + 1, y, z, side, icon, metadata);
                            right = isNeighbour(cp, blockAccess, block, x - 1, y, z, side, icon, metadata);
                            break;
                        }
                        case 4: {
                            left = isNeighbour(cp, blockAccess, block, x, y + 1, z, side, icon, metadata);
                            right = isNeighbour(cp, blockAccess, block, x, y - 1, z, side, icon, metadata);
                            break;
                        }
                        case 5: {
                            left = isNeighbour(cp, blockAccess, block, x, y + 1, z, side, icon, metadata);
                            right = isNeighbour(cp, blockAccess, block, x, y - 1, z, side, icon, metadata);
                            break;
                        }
                    }
                    break;
                }
                case 2: {
                    switch (side) {
                        case 4:
                        case 5: {
                            return null;
                        }
                        case 2: {
                            left = isNeighbour(cp, blockAccess, block, x, y + 1, z, side, icon, metadata);
                            right = isNeighbour(cp, blockAccess, block, x, y - 1, z, side, icon, metadata);
                            break Label_0703;
                        }
                        case 3: {
                            left = isNeighbour(cp, blockAccess, block, x, y + 1, z, side, icon, metadata);
                            right = isNeighbour(cp, blockAccess, block, x, y - 1, z, side, icon, metadata);
                            break Label_0703;
                        }
                        case 0: {
                            left = isNeighbour(cp, blockAccess, block, x, y, z - 1, side, icon, metadata);
                            right = isNeighbour(cp, blockAccess, block, x, y, z + 1, side, icon, metadata);
                            break Label_0703;
                        }
                        case 1: {
                            left = isNeighbour(cp, blockAccess, block, x, y, z - 1, side, icon, metadata);
                            right = isNeighbour(cp, blockAccess, block, x, y, z + 1, side, icon, metadata);
                            break Label_0703;
                        }
                    }
                    break;
                }
            }
        }
        int index = 3;
        if (left) {
            if (right) {
                index = 1;
            }
            else {
                index = 2;
            }
        }
        else if (right) {
            index = 0;
        }
        else {
            index = 3;
        }
        return cp.tileIcons[index];
    }
    
    private static IIcon getConnectedTextureVertical(final ConnectedProperties cp, final IBlockAccess blockAccess, final Block block, final int x, final int y, final int z, final int vertAxis, final int side, final IIcon icon, final int metadata) {
        boolean bottom = false;
        boolean top = false;
        switch (vertAxis) {
            case 0: {
                if (side == 1 || side == 0) {
                    return null;
                }
                bottom = isNeighbour(cp, blockAccess, block, x, y - 1, z, side, icon, metadata);
                top = isNeighbour(cp, blockAccess, block, x, y + 1, z, side, icon, metadata);
                break;
            }
            case 1: {
                if (side == 3 || side == 2) {
                    return null;
                }
                bottom = isNeighbour(cp, blockAccess, block, x, y, z - 1, side, icon, metadata);
                top = isNeighbour(cp, blockAccess, block, x, y, z + 1, side, icon, metadata);
                break;
            }
            case 2: {
                if (side == 5 || side == 4) {
                    return null;
                }
                bottom = isNeighbour(cp, blockAccess, block, x - 1, y, z, side, icon, metadata);
                top = isNeighbour(cp, blockAccess, block, x + 1, y, z, side, icon, metadata);
                break;
            }
        }
        int index = 3;
        if (bottom) {
            if (top) {
                index = 1;
            }
            else {
                index = 2;
            }
        }
        else if (top) {
            index = 0;
        }
        else {
            index = 3;
        }
        return cp.tileIcons[index];
    }
    
    private static IIcon getConnectedTextureHorizontalVertical(final ConnectedProperties cp, final IBlockAccess blockAccess, final Block block, final int x, final int y, final int z, final int vertAxis, final int side, final IIcon icon, final int metadata) {
        final IIcon[] tileIcons = cp.tileIcons;
        final IIcon iconH = getConnectedTextureHorizontal(cp, blockAccess, block, x, y, z, vertAxis, side, icon, metadata);
        if (iconH != null && iconH != icon && iconH != tileIcons[3]) {
            return iconH;
        }
        final IIcon iconV = getConnectedTextureVertical(cp, blockAccess, block, x, y, z, vertAxis, side, icon, metadata);
        if (iconV == tileIcons[0]) {
            return tileIcons[4];
        }
        if (iconV == tileIcons[1]) {
            return tileIcons[5];
        }
        if (iconV == tileIcons[2]) {
            return tileIcons[6];
        }
        return iconV;
    }
    
    private static IIcon getConnectedTextureVerticalHorizontal(final ConnectedProperties cp, final IBlockAccess blockAccess, final Block block, final int x, final int y, final int z, final int vertAxis, final int side, final IIcon icon, final int metadata) {
        final IIcon[] tileIcons = cp.tileIcons;
        final IIcon iconV = getConnectedTextureVertical(cp, blockAccess, block, x, y, z, vertAxis, side, icon, metadata);
        if (iconV != null && iconV != icon && iconV != tileIcons[3]) {
            return iconV;
        }
        final IIcon iconH = getConnectedTextureHorizontal(cp, blockAccess, block, x, y, z, vertAxis, side, icon, metadata);
        if (iconH == tileIcons[0]) {
            return tileIcons[4];
        }
        if (iconH == tileIcons[1]) {
            return tileIcons[5];
        }
        if (iconH == tileIcons[2]) {
            return tileIcons[6];
        }
        return iconH;
    }
    
    private static IIcon getConnectedTextureTop(final ConnectedProperties cp, final IBlockAccess blockAccess, final Block block, final int x, final int y, final int z, final int vertAxis, final int side, final IIcon icon, final int metadata) {
        boolean top = false;
        switch (vertAxis) {
            case 0: {
                if (side == 1 || side == 0) {
                    return null;
                }
                top = isNeighbour(cp, blockAccess, block, x, y + 1, z, side, icon, metadata);
                break;
            }
            case 1: {
                if (side == 3 || side == 2) {
                    return null;
                }
                top = isNeighbour(cp, blockAccess, block, x, y, z + 1, side, icon, metadata);
                break;
            }
            case 2: {
                if (side == 5 || side == 4) {
                    return null;
                }
                top = isNeighbour(cp, blockAccess, block, x + 1, y, z, side, icon, metadata);
                break;
            }
        }
        if (top) {
            return cp.tileIcons[0];
        }
        return null;
    }
    
    public static void updateIcons(final TextureMap textureMap) {
        ConnectedTextures.blockProperties = null;
        ConnectedTextures.tileProperties = null;
        final IResourcePack[] rps = Config.getResourcePacks();
        for (int i = rps.length - 1; i >= 0; --i) {
            final IResourcePack rp = rps[i];
            updateIcons(textureMap, rp);
        }
        updateIcons(textureMap, Config.getDefaultResourcePack());
    }
    
    public static void updateIcons(final TextureMap textureMap, final IResourcePack rp) {
        final String[] names = collectFiles(rp, "mcpatcher/ctm/", ".properties");
        Arrays.sort(names);
        final List tileList = makePropertyList(ConnectedTextures.tileProperties);
        final List blockList = makePropertyList(ConnectedTextures.blockProperties);
        for (int i = 0; i < names.length; ++i) {
            final String name = names[i];
            Config.dbg("ConnectedTextures: " + name);
            try {
                final ResourceLocation locFile = new ResourceLocation(name);
                final InputStream in = rp.getInputStream(locFile);
                if (in == null) {
                    Config.warn("ConnectedTextures file not found: " + name);
                }
                else {
                    final Properties props = new Properties();
                    props.load(in);
                    final ConnectedProperties cp = new ConnectedProperties(props, name);
                    if (cp.isValid(name)) {
                        cp.updateIcons(textureMap);
                        addToTileList(cp, tileList);
                        addToBlockList(cp, blockList);
                    }
                }
            }
            catch (FileNotFoundException e2) {
                Config.warn("ConnectedTextures file not found: " + name);
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }
        ConnectedTextures.blockProperties = propertyListToArray(blockList);
        ConnectedTextures.tileProperties = propertyListToArray(tileList);
        ConnectedTextures.multipass = detectMultipass();
        Config.dbg("Multipass connected textures: " + ConnectedTextures.multipass);
    }
    
    private static List makePropertyList(final ConnectedProperties[][] propsArr) {
        final List list = new ArrayList();
        if (propsArr != null) {
            for (int i = 0; i < propsArr.length; ++i) {
                final ConnectedProperties[] props = propsArr[i];
                List propList = null;
                if (props != null) {
                    propList = new ArrayList(Arrays.asList(props));
                }
                list.add(propList);
            }
        }
        return list;
    }
    
    private static boolean detectMultipass() {
        final List propList = new ArrayList();
        for (int i = 0; i < ConnectedTextures.tileProperties.length; ++i) {
            final ConnectedProperties[] cps = ConnectedTextures.tileProperties[i];
            if (cps != null) {
                propList.addAll(Arrays.asList(cps));
            }
        }
        for (int i = 0; i < ConnectedTextures.blockProperties.length; ++i) {
            final ConnectedProperties[] cps = ConnectedTextures.blockProperties[i];
            if (cps != null) {
                propList.addAll(Arrays.asList(cps));
            }
        }
        final ConnectedProperties[] props = propList.toArray(new ConnectedProperties[propList.size()]);
        final Set matchIconSet = new HashSet();
        final Set tileIconSet = new HashSet();
        for (int j = 0; j < props.length; ++j) {
            final ConnectedProperties cp = props[j];
            if (cp.matchTileIcons != null) {
                matchIconSet.addAll(Arrays.asList(cp.matchTileIcons));
            }
            if (cp.tileIcons != null) {
                tileIconSet.addAll(Arrays.asList(cp.tileIcons));
            }
        }
        matchIconSet.retainAll(tileIconSet);
        return !matchIconSet.isEmpty();
    }
    
    private static ConnectedProperties[][] propertyListToArray(final List list) {
        final ConnectedProperties[][] propArr = new ConnectedProperties[list.size()][];
        for (int i = 0; i < list.size(); ++i) {
            final List subList = list.get(i);
            if (subList != null) {
                final ConnectedProperties[] subArr = subList.toArray(new ConnectedProperties[subList.size()]);
                propArr[i] = subArr;
            }
        }
        return propArr;
    }
    
    private static void addToTileList(final ConnectedProperties cp, final List tileList) {
        if (cp.matchTileIcons == null) {
            return;
        }
        for (int i = 0; i < cp.matchTileIcons.length; ++i) {
            final IIcon icon = cp.matchTileIcons[i];
            if (!(icon instanceof TextureAtlasSprite)) {
                Config.warn("IIcon is not TextureAtlasSprite: " + icon + ", name: " + icon.getIconName());
            }
            else {
                final TextureAtlasSprite ts = (TextureAtlasSprite)icon;
                final int tileId = ts.getIndexInMap();
                if (tileId < 0) {
                    Config.warn("Invalid tile ID: " + tileId + ", icon: " + ts.getIconName());
                }
                else {
                    addToList(cp, tileList, tileId);
                }
            }
        }
    }
    
    private static void addToBlockList(final ConnectedProperties cp, final List blockList) {
        if (cp.matchBlocks == null) {
            return;
        }
        for (int i = 0; i < cp.matchBlocks.length; ++i) {
            final int blockId = cp.matchBlocks[i];
            if (blockId < 0) {
                Config.warn("Invalid block ID: " + blockId);
            }
            else {
                addToList(cp, blockList, blockId);
            }
        }
    }
    
    private static void addToList(final ConnectedProperties cp, final List list, final int id) {
        while (id >= list.size()) {
            list.add(null);
        }
        List subList = list.get(id);
        if (subList == null) {
            subList = new ArrayList();
            list.set(id, subList);
        }
        subList.add(cp);
    }
    
    private static String[] collectFiles(final IResourcePack rp, final String prefix, final String suffix) {
        if (rp instanceof DefaultResourcePack) {
            return collectFilesDefault(rp);
        }
        if (!(rp instanceof AbstractResourcePack)) {
            return new String[0];
        }
        final AbstractResourcePack arp = (AbstractResourcePack)rp;
        final File tpFile = ResourceUtils.getResourcePackFile(arp);
        if (tpFile == null) {
            return new String[0];
        }
        if (tpFile.isDirectory()) {
            return collectFilesFolder(tpFile, "", prefix, suffix);
        }
        if (tpFile.isFile()) {
            return collectFilesZIP(tpFile, prefix, suffix);
        }
        return new String[0];
    }
    
    private static String[] collectFilesDefault(final IResourcePack rp) {
        final List list = new ArrayList();
        final String[] names = getDefaultCtmPaths();
        for (int i = 0; i < names.length; ++i) {
            final String name = names[i];
            final ResourceLocation loc = new ResourceLocation(name);
            if (rp.resourceExists(loc)) {
                list.add(name);
            }
        }
        final String[] nameArr = list.toArray(new String[list.size()]);
        return nameArr;
    }
    
    private static String[] getDefaultCtmPaths() {
        final List list = new ArrayList();
        final String defPath = "mcpatcher/ctm/default/";
        if (Config.isFromDefaultResourcePack(new ResourceLocation("textures/blocks/glass.png"))) {
            list.add(defPath + "glass.properties");
            list.add(defPath + "glasspane.properties");
        }
        if (Config.isFromDefaultResourcePack(new ResourceLocation("textures/blocks/bookshelf.png"))) {
            list.add(defPath + "bookshelf.properties");
        }
        if (Config.isFromDefaultResourcePack(new ResourceLocation("textures/blocks/sandstone_normal.png"))) {
            list.add(defPath + "sandstone.properties");
        }
        final String[] colors = { "white", "orange", "magenta", "light_blue", "yellow", "lime", "pink", "gray", "silver", "cyan", "purple", "blue", "brown", "green", "red", "black" };
        for (int i = 0; i < colors.length; ++i) {
            final String color = colors[i];
            if (Config.isFromDefaultResourcePack(new ResourceLocation("textures/blocks/glass_" + color + ".png"))) {
                list.add(defPath + i + "_glass_" + color + "/glass_" + color + ".properties");
                list.add(defPath + i + "_glass_" + color + "/glass_pane_" + color + ".properties");
            }
        }
        final String[] paths = list.toArray(new String[list.size()]);
        return paths;
    }
    
    private static String[] collectFilesFolder(final File tpFile, final String basePath, final String prefix, final String suffix) {
        final List list = new ArrayList();
        final String prefixAssets = "assets/minecraft/";
        final File[] files = tpFile.listFiles();
        if (files == null) {
            return new String[0];
        }
        for (int i = 0; i < files.length; ++i) {
            final File file = files[i];
            if (file.isFile()) {
                String name = basePath + file.getName();
                if (name.startsWith(prefixAssets)) {
                    name = name.substring(prefixAssets.length());
                    if (name.startsWith(prefix) && name.endsWith(suffix)) {
                        list.add(name);
                    }
                }
            }
            else if (file.isDirectory()) {
                final String dirPath = basePath + file.getName() + "/";
                final String[] names = collectFilesFolder(file, dirPath, prefix, suffix);
                for (int n = 0; n < names.length; ++n) {
                    final String name2 = names[n];
                    list.add(name2);
                }
            }
        }
        final String[] names2 = list.toArray(new String[list.size()]);
        return names2;
    }
    
    private static String[] collectFilesZIP(final File tpFile, final String prefix, final String suffix) {
        final List list = new ArrayList();
        final String prefixAssets = "assets/minecraft/";
        try {
            final ZipFile zf = new ZipFile(tpFile);
            final Enumeration en = zf.entries();
            while (en.hasMoreElements()) {
                final ZipEntry ze = en.nextElement();
                String name = ze.getName();
                if (name.startsWith(prefixAssets)) {
                    name = name.substring(prefixAssets.length());
                    if (!name.startsWith(prefix) || !name.endsWith(suffix)) {
                        continue;
                    }
                    list.add(name);
                }
            }
            zf.close();
            return list.toArray(new String[list.size()]);
        }
        catch (IOException e) {
            e.printStackTrace();
            return new String[0];
        }
    }
    
    public static int getPaneTextureIndex(final boolean linkP, final boolean linkN, final boolean linkYp, final boolean linkYn) {
        if (linkN && linkP) {
            if (linkYp) {
                if (linkYn) {
                    return 34;
                }
                return 50;
            }
            else {
                if (linkYn) {
                    return 18;
                }
                return 2;
            }
        }
        else if (linkN && !linkP) {
            if (linkYp) {
                if (linkYn) {
                    return 35;
                }
                return 51;
            }
            else {
                if (linkYn) {
                    return 19;
                }
                return 3;
            }
        }
        else if (!linkN && linkP) {
            if (linkYp) {
                if (linkYn) {
                    return 33;
                }
                return 49;
            }
            else {
                if (linkYn) {
                    return 17;
                }
                return 1;
            }
        }
        else if (linkYp) {
            if (linkYn) {
                return 32;
            }
            return 48;
        }
        else {
            if (linkYn) {
                return 16;
            }
            return 0;
        }
    }
    
    public static int getReversePaneTextureIndex(final int texNum) {
        final int col = texNum % 16;
        if (col == 1) {
            return texNum + 2;
        }
        if (col == 3) {
            return texNum - 2;
        }
        return texNum;
    }
    
    public static IIcon getCtmTexture(final ConnectedProperties cp, final int ctmIndex, final IIcon icon) {
        if (cp.method != 1) {
            return icon;
        }
        if (ctmIndex < 0 || ctmIndex >= ConnectedTextures.ctmIndexes.length) {
            return icon;
        }
        final int index = ConnectedTextures.ctmIndexes[ctmIndex];
        final IIcon[] ctmIcons = cp.tileIcons;
        if (index < 0 || index >= ctmIcons.length) {
            return icon;
        }
        return ctmIcons[index];
    }
    
    static {
        ConnectedTextures.blockProperties = null;
        ConnectedTextures.tileProperties = null;
        ConnectedTextures.multipass = false;
        propSuffixes = new String[] { "", "a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z" };
        ctmIndexes = new int[] { 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 0, 0, 0, 0, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 0, 0, 0, 0, 24, 25, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 0, 0, 0, 0, 36, 37, 38, 39, 40, 41, 42, 43, 44, 45, 46, 0, 0, 0, 0, 0 };
    }
}
