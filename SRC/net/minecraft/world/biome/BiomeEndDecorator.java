package net.minecraft.world.biome;

import net.minecraft.init.*;
import net.minecraft.world.gen.feature.*;
import net.minecraft.entity.boss.*;
import net.minecraft.entity.*;

public class BiomeEndDecorator extends BiomeDecorator
{
    protected WorldGenerator spikeGen;
    private static final String __OBFID = "CL_00000188";
    
    public BiomeEndDecorator() {
        this.spikeGen = new WorldGenSpikes(Blocks.end_stone);
    }
    
    @Override
    protected void func_150513_a(final BiomeGenBase p_150513_1_) {
        this.generateOres();
        if (this.randomGenerator.nextInt(5) == 0) {
            final int var2 = this.chunk_X + this.randomGenerator.nextInt(16) + 8;
            final int var3 = this.chunk_Z + this.randomGenerator.nextInt(16) + 8;
            final int var4 = this.currentWorld.getTopSolidOrLiquidBlock(var2, var3);
            this.spikeGen.generate(this.currentWorld, this.randomGenerator, var2, var4, var3);
        }
        if (this.chunk_X == 0 && this.chunk_Z == 0) {
            final EntityDragon var5 = new EntityDragon(this.currentWorld);
            var5.setLocationAndAngles(0.0, 128.0, 0.0, this.randomGenerator.nextFloat() * 360.0f, 0.0f);
            this.currentWorld.spawnEntityInWorld(var5);
        }
    }
}
