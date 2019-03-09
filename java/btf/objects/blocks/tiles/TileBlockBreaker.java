package btf.objects.blocks.tiles;

import btf.objects.blocks.Machine;
import net.minecraft.block.BlockCake;
import net.minecraft.block.BlockCommandBlock;
import net.minecraft.block.BlockMobSpawner;
import net.minecraft.block.BlockPane;
import net.minecraft.block.BlockShulkerBox;
import net.minecraft.block.BlockStainedGlassPane;
import net.minecraft.block.BlockStructure;
import net.minecraft.block.BlockStructureVoid;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;

public class TileBlockBreaker extends TileEntity {
	public int ticksIn = 0;
	private boolean initialized = false;
	private IBlockState statebehind;
	private BlockPos behind;
	private BlockPos breaking;
	private double dropx;
	private double dropy;
	private double dropz;

	private void init(BlockPos pos, EnumFacing facing) {
		initialized = true;
		switch (facing) {
		case DOWN:
			break;
		case EAST: {
			breaking = new BlockPos(pos.getX() + 1, pos.getY(), pos.getZ());
			dropx = pos.getX() - 0.5;
			dropy = pos.getY();
			dropz = pos.getZ() + 0.5;
			break;
		}

		case NORTH: {
			breaking = new BlockPos(pos.getX(), pos.getY(), pos.getZ() - 1);
			dropx = pos.getX() + 0.5;
			dropy = pos.getY();
			dropz = pos.getZ() + 1.5;
			break;
		}

		case SOUTH: {
			breaking = new BlockPos(pos.getX(), pos.getY(), pos.getZ() + 1);
			dropx = pos.getX() + 0.5;
			dropy = pos.getY();
			dropz = pos.getZ() - 0.5;
			break;
		}

		case UP:
			break;
		case WEST: {
			breaking = new BlockPos(pos.getX() - 1, pos.getY(), pos.getZ());
			dropx = pos.getX() + 1.5;
			dropy = pos.getY();
			dropz = pos.getZ() + 0.5;
			break;
		}
		default:
			break;

		}
		switch (world.getBlockState(pos).getValue(Machine.FACING)) {
		case DOWN: {
			break;
		}
		case EAST: {
			statebehind = world.getBlockState(new BlockPos(pos.getX() - 1, pos.getY(), pos.getZ()));
			behind = new BlockPos(pos.getX() - 1, pos.getY(), pos.getZ());
			break;
		}
		case NORTH: {
			statebehind = world.getBlockState(new BlockPos(pos.getX(), pos.getY(), pos.getZ() + 1));
			behind = new BlockPos(pos.getX(), pos.getY(), pos.getZ() + 1);
			break;
		}
		case SOUTH: {
			statebehind = world.getBlockState(new BlockPos(pos.getX(), pos.getY(), pos.getZ() - 1));
			behind = new BlockPos(pos.getX(), pos.getY(), pos.getZ() - 1);
			break;
		}
		case UP: {

		}
		case WEST: {
			statebehind = world.getBlockState(new BlockPos(pos.getX() + 1, pos.getY(), pos.getZ()));
			behind = new BlockPos(pos.getX() + 1, pos.getY(), pos.getZ());
			break;
		}
		}
	}

	public void scan() {
		if (!initialized)
			init(pos, world.getBlockState(pos).getValue(Machine.FACING));
		IBlockState breakingstate = world.getBlockState(breaking);
		if (breakingstate.getBlock() != Blocks.AIR && breakingstate.getBlock() != Blocks.BEDROCK
				&& !(breakingstate.getBlock() instanceof BlockShulkerBox)
				&& !(breakingstate.getBlock() instanceof BlockCommandBlock)
				&& !(breakingstate.getBlock() instanceof BlockCake) //
				&& !(breakingstate.getBlock() instanceof BlockPane)
				&& !(breakingstate.getBlock() instanceof BlockStainedGlassPane)
				&& !(breakingstate.getBlock() instanceof BlockStructure)
				&& !(breakingstate.getBlock() instanceof BlockStructureVoid)
				&& !(breakingstate.getBlock() instanceof BlockMobSpawner)) {
			world.setBlockToAir(breaking);
			if (!world.isRemote) {
				NonNullList<ItemStack> drops = NonNullList.create();
				breakingstate.getBlock().getDrops(drops, world, breaking, breakingstate, 0);
				for (ItemStack drop : drops) {
					EntityItem item = new EntityItem(world, dropx, dropy, dropz, drop);
					item.motionX = 0;
					item.motionY = 0;
					item.motionZ = 0;
					boolean spawned = false;
					if (world.getTileEntity(behind) != null && world.getTileEntity(behind)
							.hasCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null)) {
						IItemHandler itemHandler = world.getTileEntity(behind)
								.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null);
						for (int i = 0; i <= itemHandler.getSlots(); i++) {
							if (itemHandler.getStackInSlot(i).isEmpty() || //
									(itemHandler.getStackInSlot(i).getItem() == drop.getItem()
											&& (itemHandler.getStackInSlot(i).getCount() + drop.getCount()) <= 64)) {
								itemHandler.insertItem(i, drop, false);
								spawned = true;
								break;
							}
						}

					}
					if (statebehind.getBlock() == Blocks.AIR || !spawned) {
						world.spawnEntity(item);
					}
				}
			}
		}
	}

}
