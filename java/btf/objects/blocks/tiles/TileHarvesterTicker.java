package btf.objects.blocks.tiles;

import java.util.Random;

import btf.objects.blocks.Machine;
import net.minecraft.block.BlockCrops;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;

public class TileHarvesterTicker extends TileEntity {
	BlockPos checkstart = null;
	IBlockState statebehind = null;
	BlockPos behind = null;
	boolean initialized = false;
	private double dropx;
	private double dropy;
	private double dropz;
	private int x = 0;
	private int y = 0;
	private int z = 0;

	public void scan() {
		if (!initialized)
			init();
		for (int x = 0; x <= 3; x++) {
			for (int z = 0; z <= 3; z++) {
				IBlockState state = world
						.getBlockState(new BlockPos(checkstart.getX() + x, checkstart.getY(), checkstart.getZ() + z));
				if (state.getBlock() instanceof BlockCrops) {
					BlockCrops stateCasted = (BlockCrops) state.getBlock();
					if (stateCasted.isMaxAge(state)) {
						world.setBlockState(
								new BlockPos(checkstart.getX() + x, checkstart.getY(), checkstart.getZ() + z),
								stateCasted.withAge(0));
						EntityItem item = new EntityItem(world, dropx, dropy, dropz,
								new ItemStack(state.getBlock().getItemDropped(state, new Random(), 2),
										(int) (Math.random() * Math.random() * 2 + 1)));
						item.motionX = 0;
						item.motionY = 0;
						item.motionZ = 0;
						boolean spawned = false;
						if (world.getTileEntity(behind) != null)
							if (world.getTileEntity(behind).hasCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY,
									null)) {
								IItemHandler ItemHandler = world.getTileEntity(behind)
										.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null);
								ItemStack stack = item.getItem();
								for (int i = 0; i <= ItemHandler.getSlots(); i++) {
									if (ItemHandler.getStackInSlot(i).isEmpty()) {
										ItemHandler.insertItem(i, stack, false);
										spawned = true;
										break;
									} else if (ItemHandler.getStackInSlot(i).getItem() == stack.getItem()
											&& (ItemHandler.getStackInSlot(i).getCount() + stack.getCount()) <= 64) {
										ItemHandler.insertItem(i, stack, false);
										spawned = true;
										break;
									}
								}

							}
						if (statebehind.getBlock() == Blocks.AIR && !spawned) {
							world.spawnEntity(item);
						} else if (!spawned) {
							item.posY += 2;
							world.spawnEntity(item);
						}
					}
				}
			}
		}
	}

	private void init() {
		initialized = true;
		switch (world.getBlockState(pos).getValue(Machine.FACING)) {
		case DOWN: {
			break;
		}
		case EAST: {
			dropx = pos.getX() - 0.5;
			dropy = pos.getY();
			dropz = pos.getZ() + 0.5;
			checkstart = new BlockPos(pos.getX() + 1, pos.getY(), pos.getZ() - 1);
			break;
		}
		case NORTH: {
			dropx = pos.getX() + 0.5;
			dropy = pos.getY();
			dropz = pos.getZ() + 1.5;
			checkstart = new BlockPos(pos.getX() - 1, pos.getY(), pos.getZ() - 3);
			break;
		}
		case SOUTH: {
			dropx = pos.getX() + 0.5;
			dropy = pos.getY();
			dropz = pos.getZ() - 0.5;
			checkstart = new BlockPos(pos.getX() - 1, pos.getY(), pos.getZ() + 1);
			break;
		}
		case UP: {

		}
		case WEST: {
			dropx = pos.getX() + 1.5;
			dropy = pos.getY();
			dropz = pos.getZ() + 0.5;
			checkstart = new BlockPos(pos.getX() - 3, pos.getY(), pos.getZ() - 1);
			break;
		}
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

}
