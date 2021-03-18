package net.minecraft.world.biome;

import net.minecraft.entity.passive.*;
import java.util.*;
import net.minecraft.init.*;
import net.minecraft.world.gen.feature.*;
import net.minecraft.block.*;
import net.minecraft.world.*;

public class BiomeGenTaiga extends BiomeGenBase
{
    private static final WorldGenTaiga1 field_150639_aC;
    private static final WorldGenTaiga2 field_150640_aD;
    private static final WorldGenMegaPineTree field_150641_aE;
    private static final WorldGenMegaPineTree field_150642_aF;
    private static final WorldGenBlockBlob field_150643_aG;
    private int field_150644_aH;
    private static final String __OBFID = "CL_00000186";
    
    public BiomeGenTaiga(final int p_i45385_1_, final int p_i45385_2_) {
        super(p_i45385_1_);
        this.field_150644_aH = p_i45385_2_;
        this.spawnableCreatureList.add(new SpawnListEntry(EntityWolf.class, 8, 4, 4));
        this.theBiomeDecorator.treesPerChunk = 10;
        if (p_i45385_2_ != 1 && p_i45385_2_ != 2) {
            this.theBiomeDecorator.grassPerChunk = 1;
            this.theBiomeDecorator.mushroomsPerChunk = 1;
        }
        else {
            this.theBiomeDecorator.grassPerChunk = 7;
            this.theBiomeDecorator.deadBushPerChunk = 1;
            this.theBiomeDecorator.mushroomsPerChunk = 3;
        }
    }
    
    @Override
    public WorldGenAbstractTree func_150567_a(final Random p_150567_1_) {
        return ((this.field_150644_aH == 1 || this.field_150644_aH == 2) && p_150567_1_.nextInt(3) == 0) ? ((this.field_150644_aH != 2 && p_150567_1_.nextInt(13) != 0) ? BiomeGenTaiga.field_150641_aE : BiomeGenTaiga.field_150642_aF) : ((p_150567_1_.nextInt(3) == 0) ? BiomeGenTaiga.field_150639_aC : BiomeGenTaiga.field_150640_aD);
    }
    
    @Override
    public WorldGenerator getRandomWorldGenForGrass(final Random p_76730_1_) {
        return (p_76730_1_.nextInt(5) > 0) ? new WorldGenTallGrass(Blocks.tallgrass, 2) : new WorldGenTallGrass(Blocks.tallgrass, 1);
    }
    
    @Override
    public void decorate(final World p_76728_1_, final Random p_76728_2_, final int p_76728_3_, final int p_76728_4_) {
        if (this.field_150644_aH == 1 || this.field_150644_aH == 2) {
            for (int var5 = p_76728_2_.nextInt(3), var6 = 0; var6 < var5; ++var6) {
                final int var7 = p_76728_3_ + p_76728_2_.nextInt(16) + 8;
                final int var8 = p_76728_4_ + p_76728_2_.nextInt(16) + 8;
                final int var9 = p_76728_1_.getHeightValue(var7, var8);
                BiomeGenTaiga.field_150643_aG.generate(p_76728_1_, p_76728_2_, var7, var9, var8);
            }
        }
        BiomeGenTaiga.field_150610_ae.func_150548_a(3);
        for (int var5 = 0; var5 < 7; ++var5) {
            final int var6 = p_76728_3_ + p_76728_2_.nextInt(16) + 8;
            final int var7 = p_76728_4_ + p_76728_2_.nextInt(16) + 8;
            final int var8 = p_76728_2_.nextInt(p_76728_1_.getHeightValue(var6, var7) + 32);
            BiomeGenTaiga.field_150610_ae.generate(p_76728_1_, p_76728_2_, var6, var8, var7);
        }
        super.decorate(p_76728_1_, p_76728_2_, p_76728_3_, p_76728_4_);
    }
    
    @Override
    public void func_150573_a(final World p_150573_1_, final Random p_150573_2_, final Block[] p_150573_3_, final byte[] p_150573_4_, final int p_150573_5_, final int p_150573_6_, final double p_150573_7_) {
        if (this.field_150644_aH == 1 || this.field_150644_aH == 2) {
            this.topBlock = Blocks.grass;
            this.field_150604_aj = 0;
            this.fillerBlock = Blocks.dirt;
            if (p_150573_7_ > 1.75) {
                this.topBlock = Blocks.dirt;
                this.field_150604_aj = 1;
            }
            else if (p_150573_7_ > -0.95) {
                this.topBlock = Blocks.dirt;
                this.field_150604_aj = 2;
            }
        }
        this.func_150560_b(p_150573_1_, p_150573_2_, p_150573_3_, p_150573_4_, p_150573_5_, p_150573_6_, p_150573_7_);
    }
    
    @Override
    protected BiomeGenBase func_150566_k() {
        return (this.biomeID == BiomeGenBase.field_150578_U.biomeID) ? new BiomeGenTaiga(this.biomeID + 128, 2).func_150557_a(5858897, true).setBiomeName("Mega Spruce Taiga").func_76733_a(5159473).setTemperatureRainfall(0.25f, 0.8f).func_150570_a(new Height(this.minHeight, this.maxHeight)) : super.func_150566_k();
    }
    
    static {
        field_150639_aC = new WorldGenTaiga1();
        field_150640_aD = new WorldGenTaiga2(false);
        field_150641_aE = new WorldGenMegaPineTree(false, false);
        field_150642_aF = new WorldGenMegaPineTree(false, true);
        field_150643_aG = new WorldGenBlockBlob(Blocks.mossy_cobblestone, 0);
    }
}
