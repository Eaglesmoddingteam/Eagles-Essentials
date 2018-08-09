package btf.objects.blocks.tiles;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import javax.annotation.Nullable;

import com.google.common.collect.Lists;

import btf.util.registry.AccumulatorRegistry;
import net.minecraft.block.Block;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;

public class TileAccumulator extends TileEntity implements ITickable {

	private final boolean HAS_BLOCK;
	private boolean IS_SLAVE = false;
	private BlockPos MASTER;
	private BlockPos[] SLAVES = new BlockPos[4];
	private boolean IS_COMPLETE = false;

	public TileAccumulator(boolean hasBlock) {
		super();
		this.HAS_BLOCK = hasBlock;
	}

	public void setMaster(BlockPos master) {
		this.MASTER = master;
	}

	public BlockPos getMaster() {
		return MASTER;
	}

	public void neighborChanged() {
		if (this.IS_SLAVE) {
			TileEntity master = world.getTileEntity(MASTER);
			if (master != null) {
				if (master instanceof TileAccumulator) {
					((TileAccumulator) master).neighborChanged();
				}
			} else {
				this.IS_SLAVE = false;
				this.MASTER = null;
			}
		}
	}

	private void checkMaster() {
		for (BlockPos possibility : this.getPossibleMasterPositions()) {
			TileEntity te = world.getTileEntity(possibility);
			if (te instanceof TileAccumulator) {
				TileAccumulator master = (TileAccumulator) te;
				master.attachSlave(this.pos);
			}
		}
	}

	private BlockPos[] getPossibleMasterPositions() {
		BlockPos basepos = pos.down(1);
		BlockPos[] positions = { basepos.north(), basepos.south(), basepos.west(), basepos.east() };
		return positions;
	}

	private void attachSlave(BlockPos pos) {
		for (int i = 0; i < 4; i++) {
			if (this.SLAVES[i] == null) {
				this.SLAVES[i] = pos;
				return;
			}
		}
	}

	private boolean detachSlave(BlockPos pos) {
		Stream s = Lists.newArrayList(this.SLAVES).stream().filter(c -> c.equals(pos));
		BlockPos[] matches = (BlockPos[]) s.toArray();
		if (matches.length > 0) {
			for (int i = 0; i < matches.length; i++) {
				for (BlockPos match : matches) {
					if (this.SLAVES[i].equals(match)) {
						this.SLAVES[i] = null;
					}
				}
			}
			((TileAccumulator)world.getTileEntity(pos)).IS_SLAVE = false;
			((TileAccumulator)world.getTileEntity(pos)).MASTER = null;
			return true;
		}
		return false;
	}

	void checkStructure() {
		checkMaster();
		if (Lists.newArrayList(this.SLAVES).stream().filter(c -> c == null).toArray().length == 0) {
			boolean flag = false;
			for (BlockPos slave : SLAVES) {
				if (world.getTileEntity(slave) instanceof TileAccumulator) {
					flag = true;
				} else {
					flag = false;
					break;
				}
			}
			this.IS_COMPLETE = flag;
		}
	}

	@Override
	public void update() {
		if (!IS_SLAVE) {
			checkStructure();
			if (IS_COMPLETE) {
				BlockPos pool = this.pos.up(1);
				if (world.getBlockState(pool).getBlock().equals(Blocks.WATER)) {
					List<EntityItem> itemEntities = world.getEntitiesWithinAABB(EntityItem.class, new AxisAlignedBB(pool));
					//
					ArrayList<ItemStack> items = new ArrayList<>(itemEntities.size());
					itemEntities.forEach(i -> items.add(i.getItem()));
					//
					@Nullable
					Block Output = AccumulatorRegistry.getOutcome(items);
					//
					if (Output != null) {
						if (!world.isRemote) {
							itemEntities.forEach(i -> i.setDead());
							world.setBlockState(pool, Output.getDefaultState());
						}
					}
				}
			}
		}
	}

}
