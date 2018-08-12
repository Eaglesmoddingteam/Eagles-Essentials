package btf.objects.blocks.tiles;

import btf.init.BlockInit;
import net.minecraft.block.Block;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.BlockPos;

public class TileAccumulator extends TileEntity implements ITickable {
	private boolean hasMaster = false;
	int tick = 0;

	@Override
	public void update() {
		if (!hasMaster) {
			tick++;
			if (tick == 20) {
				tick = 0;
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
				if (flag) {
					TileEntity[] TEs = { //
							world.getTileEntity(basePos.west()), //
							world.getTileEntity(basePos.north()), //
							world.getTileEntity(basePos.east()), //
							world.getTileEntity(basePos.south()) //
					};
					boolean flag2 = true;
					for (TileEntity te : TEs) {
						if (te instanceof TileAccumulator) {
							((TileAccumulator) te).setHasMaster(true);
							flag2 &= true;
						} else {
							flag2 = false;
						}
					}
					if (flag2) {
						TileAccumulatorMaster master = new TileAccumulatorMaster();
						world.setTileEntity(pos, master);
						this.markDirty();
					}
				}
			}
		}
	}

	public void setHasMaster(boolean hasMaster) {
		this.hasMaster = hasMaster;
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound) {
		compound.setBoolean("hasmaster", this.hasMaster);
		return super.writeToNBT(compound);
	}

	@Override
	public void readFromNBT(NBTTagCompound compound) {
		this.hasMaster = compound.getBoolean("hasmaster");
		super.readFromNBT(compound);
	}

}
