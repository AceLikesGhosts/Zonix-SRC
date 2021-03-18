package net.minecraft.world.biome;

import net.minecraft.entity.passive.*;
import java.util.*;
import net.minecraft.init.*;
import net.minecraft.block.*;
import net.minecraft.world.*;
import net.minecraft.world.gen.feature.*;

public class BiomeGenJungle extends BiomeGenBase
{
    private boolean field_150614_aC;
    private static final String __OBFID = "CL_00000175";
    
    public BiomeGenJungle(final int p_i45379_1_, final boolean p_i45379_2_) {
        super(p_i45379_1_);
        this.field_150614_aC = p_i45379_2_;
        if (p_i45379_2_) {
            this.theBiomeDecorator.treesPerChunk = 2;
        }
        else {
            this.theBiomeDecorator.treesPerChunk = 50;
        }
        this.theBiomeDecorator.grassPerChunk = 25;
        this.theBiomeDecorator.flowersPerChunk = 4;
        if (!p_i45379_2_) {
            this.spawnableMonsterList.add(new SpawnListEntry(EntityOcelot.class, 2, 1, 1));
        }
        this.spawnableCreatureList.add(new SpawnListEntry(EntityChicken.class, 10, 4, 4));
    }
    
    @Override
    public WorldGenAbstractTree func_150567_a(final Random p_150567_1_) {
        return (p_150567_1_.nextInt(10) == 0) ? this.worldGeneratorBigTree : ((p_150567_1_.nextInt(2) == 0) ? new WorldGenShrub(3, 0) : ((!this.field_150614_aC && p_150567_1_.nextInt(3) == 0) ? new WorldGenMegaJungle(false, 10, 20, 3, 3) : new WorldGenTrees(false, 4 + p_150567_1_.nextInt(7), 3, 3, true)));
    }
    
    @Override
    public WorldGenerator getRandomWorldGenForGrass(final Random p_76730_1_) {
        return (p_76730_1_.nextInt(4) == 0) ? new WorldGenTallGrass(Blocks.tallgrass, 2) : new WorldGenTallGrass(Blocks.tallgrass, 1);
    }
    
    @Override
    public void decorate(final World p_76728_1_, final Random p_76728_2_, final int p_76728_3_, final int p_76728_4_) {
        super.decorate(p_76728_1_, p_76728_2_, p_76728_3_, p_76728_4_);
        final int var5 = p_76728_3_ + p_76728_2_.nextInt(16) + 8;
        int var6 = p_76728_4_ + p_76728_2_.nextInt(16) + 8;
        int var7 = p_76728_2_.nextInt(p_76728_1_.getHeightValue(var5, var6) * 2);
        new WorldGenMelon().generate(p_76728_1_, p_76728_2_, var5, var7, var6);
        final WorldGenVines var8 = new WorldGenVines();
        for (var6 = 0; var6 < 50; ++var6) {
            var7 = p_76728_3_ + p_76728_2_.nextInt(16) + 8;
            final short var9 = 128;
            final int var10 = p_76728_4_ + p_76728_2_.nextInt(16) + 8;
            var8.generate(p_76728_1_, p_76728_2_, var7, var9, var10);
        }
    }
}
