package com.thevoxelbox.voxelmap.util;

import net.minecraft.block.*;

public class BlockIDRepository
{
    public static int airID;
    public static int waterID;
    public static int flowingWaterID;
    public static int lavaID;
    public static int flowingLavaID;
    public static int iceID;
    public static int grassID;
    public static int leavesID;
    public static int tallGrassID;
    public static int reedsID;
    public static int vineID;
    public static int lilypadID;
    public static int leaves2ID;
    public static int tallFlowerID;
    public static int cobwebID;
    public static int redstoneID;
    public static int signID;
    public static int woodDoorID;
    public static int ladderID;
    public static int wallSignID;
    public static int ironDoorID;
    public static int stoneButtonID;
    public static int fenceID;
    public static int fenceGateID;
    public static int netherFenceID;
    public static int cobbleWallID;
    public static int woodButtonID;
    public static Integer[] shapedIDS;
    
    public static void getIDs() {
        BlockIDRepository.airID = Block.blockRegistry.getIDForObject(Block.blockRegistry.getObject("minecraft:air"));
        BlockIDRepository.waterID = Block.blockRegistry.getIDForObject(Block.blockRegistry.getObject("minecraft:water"));
        BlockIDRepository.flowingWaterID = Block.blockRegistry.getIDForObject(Block.blockRegistry.getObject("minecraft:flowing_water"));
        BlockIDRepository.lavaID = Block.blockRegistry.getIDForObject(Block.blockRegistry.getObject("minecraft:lava"));
        BlockIDRepository.flowingLavaID = Block.blockRegistry.getIDForObject(Block.blockRegistry.getObject("minecraft:flowing_lava"));
        BlockIDRepository.iceID = Block.blockRegistry.getIDForObject(Block.blockRegistry.getObject("minecraft:ice"));
        BlockIDRepository.grassID = Block.blockRegistry.getIDForObject(Block.blockRegistry.getObject("minecraft:grass"));
        BlockIDRepository.leavesID = Block.blockRegistry.getIDForObject(Block.blockRegistry.getObject("minecraft:leaves"));
        BlockIDRepository.tallGrassID = Block.blockRegistry.getIDForObject(Block.blockRegistry.getObject("minecraft:tallgrass"));
        BlockIDRepository.reedsID = Block.blockRegistry.getIDForObject(Block.blockRegistry.getObject("minecraft:reeds"));
        BlockIDRepository.vineID = Block.blockRegistry.getIDForObject(Block.blockRegistry.getObject("minecraft:vine"));
        BlockIDRepository.lilypadID = Block.blockRegistry.getIDForObject(Block.blockRegistry.getObject("minecraft:waterlily"));
        BlockIDRepository.leaves2ID = Block.blockRegistry.getIDForObject(Block.blockRegistry.getObject("minecraft:leaves2"));
        BlockIDRepository.tallFlowerID = Block.blockRegistry.getIDForObject(Block.blockRegistry.getObject("minecraft:double_plant"));
        BlockIDRepository.cobwebID = Block.blockRegistry.getIDForObject(Block.blockRegistry.getObject("minecraft:web"));
        BlockIDRepository.signID = Block.blockRegistry.getIDForObject(Block.blockRegistry.getObject("minecraft:standing_sign"));
        BlockIDRepository.woodDoorID = Block.blockRegistry.getIDForObject(Block.blockRegistry.getObject("minecraft:wooden_door"));
        BlockIDRepository.ladderID = Block.blockRegistry.getIDForObject(Block.blockRegistry.getObject("minecraft:ladder"));
        BlockIDRepository.wallSignID = Block.blockRegistry.getIDForObject(Block.blockRegistry.getObject("minecraft:wall_sign"));
        BlockIDRepository.ironDoorID = Block.blockRegistry.getIDForObject(Block.blockRegistry.getObject("minecraft:iron_door"));
        BlockIDRepository.stoneButtonID = Block.blockRegistry.getIDForObject(Block.blockRegistry.getObject("minecraft:stone_button"));
        BlockIDRepository.fenceID = Block.blockRegistry.getIDForObject(Block.blockRegistry.getObject("minecraft:fence"));
        BlockIDRepository.fenceGateID = Block.blockRegistry.getIDForObject(Block.blockRegistry.getObject("minecraft:fence_gate"));
        BlockIDRepository.netherFenceID = Block.blockRegistry.getIDForObject(Block.blockRegistry.getObject("minecraft:nether_brick_fence"));
        BlockIDRepository.cobbleWallID = Block.blockRegistry.getIDForObject(Block.blockRegistry.getObject("minecraft:cobblestone_wall"));
        BlockIDRepository.woodButtonID = Block.blockRegistry.getIDForObject(Block.blockRegistry.getObject("minecraft:wooden_button"));
        BlockIDRepository.shapedIDS = new Integer[] { BlockIDRepository.signID, BlockIDRepository.woodDoorID, BlockIDRepository.ladderID, BlockIDRepository.wallSignID, BlockIDRepository.ironDoorID, BlockIDRepository.stoneButtonID, BlockIDRepository.fenceID, BlockIDRepository.vineID, BlockIDRepository.fenceGateID, BlockIDRepository.netherFenceID, BlockIDRepository.cobbleWallID, BlockIDRepository.woodButtonID };
    }
    
    static {
        BlockIDRepository.airID = 0;
        BlockIDRepository.waterID = 9;
        BlockIDRepository.flowingWaterID = 8;
        BlockIDRepository.lavaID = 11;
        BlockIDRepository.flowingLavaID = 10;
        BlockIDRepository.iceID = 79;
        BlockIDRepository.grassID = 2;
        BlockIDRepository.leavesID = 18;
        BlockIDRepository.tallGrassID = 31;
        BlockIDRepository.reedsID = 83;
        BlockIDRepository.vineID = 106;
        BlockIDRepository.lilypadID = 111;
        BlockIDRepository.leaves2ID = 161;
        BlockIDRepository.tallFlowerID = 175;
        BlockIDRepository.cobwebID = 30;
        BlockIDRepository.redstoneID = 55;
        BlockIDRepository.signID = 63;
        BlockIDRepository.woodDoorID = 64;
        BlockIDRepository.ladderID = 65;
        BlockIDRepository.wallSignID = 68;
        BlockIDRepository.ironDoorID = 71;
        BlockIDRepository.stoneButtonID = 77;
        BlockIDRepository.fenceID = 85;
        BlockIDRepository.fenceGateID = 107;
        BlockIDRepository.netherFenceID = 113;
        BlockIDRepository.cobbleWallID = 139;
        BlockIDRepository.woodButtonID = 143;
        BlockIDRepository.shapedIDS = new Integer[] { BlockIDRepository.signID, BlockIDRepository.woodDoorID, BlockIDRepository.ladderID, BlockIDRepository.wallSignID, BlockIDRepository.ironDoorID, BlockIDRepository.stoneButtonID, BlockIDRepository.fenceID, BlockIDRepository.vineID, BlockIDRepository.fenceGateID, BlockIDRepository.netherFenceID, BlockIDRepository.cobbleWallID, BlockIDRepository.woodButtonID };
    }
}
