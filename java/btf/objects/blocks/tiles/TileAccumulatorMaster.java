package btf.objects.blocks.tiles;

import java.util.ArrayList;

import btf.init.BlockInit;
import btf.util.helpers.NBTHelper;
import btf.util.registry.AccumulatorRegistry;
import net.minecraft.block.Block;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;

public class TileAccumulatorMaster extends TileEntity implements ITickable {

	int ticks, progress = 0;
	Block processing = null;

	public TileAccumulatorMaster() {
		System.out.println("placed a new master TE!!");
	}

	@Override
	public void update() {
		ticks++;
		if (ticks == 5) {
			ticks = 0;
			execute();
			System.out.println("master executing at: " + pos.toString());
		}
	}

	private boolean isWorking() {
		return processing != null;
	}

	public boolean checkState() {
		BlockPos basePos = this.pos.up(1);
		Block[] blocks = { //
				world.getBlockState(basePos.west()).getBlock(), //
				world.getBlockState(basePos.north()).getBlock(), //
				world.getBlockState(basePos.east()).getBlock(), //
				world.getBlockState(basePos.south()).getBlock()//
		};
		boolean flag = true;
		for (Block b : blocks) {
			flag &= (b == BlockInit.impossibilium_Accumulator);
		}
		flag &= world.getBlockState(basePos) == Blocks.WATER.getDefaultState();
		return flag && world.getBlockState(pos).getBlock() == BlockInit.impossibilium_Accumulator;
	}

	private void execute() {
		if (checkState()) {
			if (isWorking()) {
				progress++;
				if (progress == 2000) {
					world.setBlockState(pos.up(), processing.getDefaultState());
					processing = null;
					progress = 0;
				}
			} else {
				ArrayList<ItemStack> items = new ArrayList();
				world.getEntitiesWithinAABB(EntityItem.class, new AxisAlignedBB(pos.up()))//
						.forEach(item -> {
							items.add(item.getItem());
						});
				if (!items.isEmpty()) {
					Block b = AccumulatorRegistry.getOutcome(items.get(0));
					if (b != null) {
						System.out.println("Staring processing the block: " + b.toString());
						processing = b;
						progress++;
					}
				}
			}
		}
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound) {
		if (isWorking()) {
			compound.setInteger("progress", this.progress);
			compound.setTag("processing", NBTHelper.toNBT(processing));
			compound.setBoolean("working", true);
		} else {
			compound.setBoolean("working", false);
		}
		return super.writeToNBT(compound);
	}

	@Override
	public void readFromNBT(NBTTagCompound compound) {
		boolean flag = compound.getBoolean("working");
		if (flag) {
			this.processing = (Block) NBTHelper.fromNBT((NBTTagCompound) compound.getTag("processing"));
			this.progress = compound.getInteger("progress");
		}
		super.readFromNBT(compound);
	}

}
