package biomesoplenty.common.world.features.trees;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.BlockSapling;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenAbstractTree;
import net.minecraftforge.common.util.ForgeDirection;

public class WorldGenBOPSwampTree extends WorldGenAbstractTree
{
	private Block wood;
	private Block leaves;
	
	private int woodMeta;
	private int leavesMeta;
	
    public WorldGenBOPSwampTree(Block wood, Block leaves, int woodMeta, int leavesMeta)
    {
        super(false);
        
        this.wood = wood;
        this.leaves = leaves;
        
        this.woodMeta = woodMeta;
        this.leavesMeta = leavesMeta;
    }

    @Override
	public boolean generate(World world, Random random, int x, int y, int z)
    {
        int l;

        for (l = random.nextInt(4) + 5; world.func_147439_a(x, y - 1, z).func_149688_o() == Material.field_151586_h; --y)
        {
            ;
        }

        boolean flag = true;

        if (y >= 1 && y + l + 1 <= 256)
        {
            int j1;
            int k1;

            for (int i1 = y; i1 <= y + 1 + l; ++i1)
            {
                byte b0 = 1;

                if (i1 == y)
                {
                    b0 = 0;
                }

                if (i1 >= y + 1 + l - 2)
                {
                    b0 = 3;
                }

                for (j1 = x - b0; j1 <= x + b0 && flag; ++j1)
                {
                    for (k1 = z - b0; k1 <= z + b0 && flag; ++k1)
                    {
                        if (i1 >= 0 && i1 < 256)
                        {
                            Block block = world.func_147439_a(j1, i1, k1);

                            if (!(block.isAir(world, j1, i1, k1) || block.isLeaves(world, j1, i1, k1)))
                            {
                                if (block != Blocks.water && block != Blocks.flowing_water)
                                {
                                    flag = false;
                                }
                                else if (i1 > y)
                                {
                                    flag = false;
                                }
                            }
                        }
                        else
                        {
                            flag = false;
                        }
                    }
                }
            }

            if (!flag)
            {
                return false;
            }
            else
            {
                Block block1 = world.func_147439_a(x, y - 1, z);

                boolean isSoil = block1.canSustainPlant(world, x, y - 1, z, ForgeDirection.UP, (BlockSapling)Blocks.sapling);
                if (isSoil && y < 256 - l - 1)
                {
                    block1.onPlantGrow(world, x, y - 1, z, x, y, z);
                    int l1;
                    int l2;
                    int k2;

                    for (k2 = y - 3 + l; k2 <= y + l; ++k2)
                    {
                        j1 = k2 - (y + l);
                        k1 = 2 - j1 / 2;

                        for (l2 = x - k1; l2 <= x + k1; ++l2)
                        {
                            l1 = l2 - x;

                            for (int i2 = z - k1; i2 <= z + k1; ++i2)
                            {
                                int j2 = i2 - z;

                                if ((Math.abs(l1) != k1 || Math.abs(j2) != k1 || random.nextInt(2) != 0 && j1 != 0) && world.func_147439_a(l2, k2, i2).canBeReplacedByLeaves(world, l2, k2, i2))
                                {
                                	//TODO:	setBlockAndMetadata()
                                    this.func_150516_a(world, l2, k2, i2, this.leaves, this.leavesMeta);
                                }
                            }
                        }
                    }

                    for (k2 = 0; k2 < l; ++k2)
                    {
                        Block block2 = world.func_147439_a(x, y + k2, z);

                        if (block2.isAir(world, x, y + k2, z) || block2.isLeaves(world, x, y + k2, z) || block2 == Blocks.flowing_water || block2 == Blocks.water)
                        {
                        	//TODO:	setBlockAndMetadata()
                            this.func_150516_a(world, x, y + k2, z, this.wood, this.woodMeta);
                        }
                    }

                    for (k2 = y - 3 + l; k2 <= y + l; ++k2)
                    {
                        j1 = k2 - (y + l);
                        k1 = 2 - j1 / 2;

                        for (l2 = x - k1; l2 <= x + k1; ++l2)
                        {
                            for (l1 = z - k1; l1 <= z + k1; ++l1)
                            {
                                if (world.func_147439_a(l2, k2, l1).isLeaves(world, l2, k2, l1))
                                {
                                    if (random.nextInt(4) == 0 && world.func_147439_a(l2 - 1, k2, l1).isAir(world, l2 - 1, k2, l1))
                                    {
                                        this.generateVines(world, l2 - 1, k2, l1, 8);
                                    }

                                    if (random.nextInt(4) == 0 && world.func_147439_a(l2 + 1, k2, l1).isAir(world, l2 + 1, k2, l1))
                                    {
                                        this.generateVines(world, l2 + 1, k2, l1, 2);
                                    }

                                    if (random.nextInt(4) == 0 && world.func_147439_a(l2, k2, l1 - 1).isAir(world, l2, k2, l1 - 1))
                                    {
                                        this.generateVines(world, l2, k2, l1 - 1, 1);
                                    }

                                    if (random.nextInt(4) == 0 && world.func_147439_a(l2, k2, l1 + 1).isAir(world, l2, k2, l1 + 1))
                                    {
                                        this.generateVines(world, l2, k2, l1 + 1, 4);
                                    }
                                }
                            }
                        }
                    }

                    return true;
                }
                else
                {
                    return false;
                }
            }
        }
        else
        {
            return false;
        }
    }

    // JAVADOC METHOD $$ func_76536_b
    private void generateVines(World par1World, int par2, int par3, int par4, int par5)
    {
        this.func_150516_a(par1World, par2, par3, par4, Blocks.vine, par5);
        int i1 = 4;

        while (true)
        {
            --par3;

            if (!(par1World.func_147439_a(par2, par3, par4).isAir(par1World, par2, par3, par4)) || i1 <= 0)
            {
                return;
            }

            this.func_150516_a(par1World, par2, par3, par4, Blocks.vine, par5);
            --i1;
        }
    }
}