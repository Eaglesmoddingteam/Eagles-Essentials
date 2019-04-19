package btf.objects.tools;

import btf.main.Main;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.ItemPickaxe;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class ImPossibiliumPickaxe extends ItemPickaxe {
	int mode;

	public ImPossibiliumPickaxe(ToolMaterial material, String name) {
		super(material);
		setUnlocalizedName(name);
		setRegistryName(name);
		setCreativeTab(Main.itemstab);
	}

	@Override
	public boolean onBlockDestroyed(ItemStack stack, World worldIn, IBlockState state, BlockPos pos,
	                                EntityLivingBase entityLiving) {
		NBTTagCompound c = stack.getTagCompound();
		if (c != null) {
			if (c.hasKey("mode")) {
				mode = readNBT(c);
			} else {
				writeNBT(c, 0);
			}
		} else {
			writeNBT(c, 0);
		}
		super.onBlockDestroyed(stack, worldIn, state, pos, entityLiving);
		breakblocks(mode, worldIn, pos, entityLiving.getHorizontalFacing(), entityLiving);
		return true;
	}

	public int readNBT(NBTTagCompound compound) {
		return compound.getInteger("mode");

	}

	public NBTTagCompound writeNBT(@Nullable NBTTagCompound compound, int amount) {
		if (compound == null) {
			compound = new NBTTagCompound();
		}
		compound.setInteger("mode", amount);
		return compound;
	}

	public void breakblocks(Integer mode, World world, BlockPos breakpos, EnumFacing facing, Entity entity) {
		boolean inX = false;
		switch (facing) {
			case DOWN:
				break;
			case WEST:
			case EAST:
				breakpos = breakpos.add(0, -1, -1);
				break;
			case NORTH:
			case SOUTH:
				inX = true;
				breakpos = breakpos.add(-1, -1, 0);
				break;
			case UP:
				break;
		}
		if (inX)
			for (int x = 0; x < 3; x++) {
				for (int y = 0; y < 3; y++) {
					BlockPos tobreak = breakpos.add(x, y, 0);
					if (world.getBlockState(tobreak).getBlock() != Blocks.BEDROCK) {
						if (!world.isRemote)
							for (ItemStack stack : world.getBlockState(tobreak).getBlock().getDrops(world, tobreak,
									world.getBlockState(tobreak), 1))
								InventoryHelper.spawnItemStack(world, entity.posX, entity.posY, entity.posZ, stack);
						world.setBlockToAir(tobreak);
					}

				}
			}
		if (!inX) {
			for (int z = 0; z < 3; z++) {
				for (int y = 0; y < 3; y++) {
					BlockPos tobreak = breakpos.add(0, y, z);

					if (world.getBlockState(tobreak).getBlock() != Blocks.BEDROCK) {
						if (!world.isRemote)
							for (ItemStack stack : world.getBlockState(tobreak).getBlock().getDrops(world, tobreak,
									world.getBlockState(tobreak), 1))
								InventoryHelper.spawnItemStack(world, entity.posX, entity.posY, entity.posZ, stack);
						world.setBlockToAir(tobreak);
						if (!world.isRemote) {
							NonNullList<ItemStack> drops = NonNullList.create();
							world.getBlockState(tobreak).getBlock().getDrops(drops, world, tobreak,
									world.getBlockState(tobreak), 0);
							Main.LOGGER.info(drops);
							for (ItemStack stack : drops) {
								InventoryHelper.spawnItemStack(world, entity.posX, entity.posY, entity.posZ, stack);
							}
							world.setBlockToAir(tobreak);
						}
					}
				}
			}
		}
	}
}
