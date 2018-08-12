package btf.objects.blocks.tiles;

import btf.objects.blocks.Machine;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler;

public class TileFluidHopper extends TileEntity implements ITickable {

	Capability<IFluidHandler> FH = CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY;

	int ticks = 0;
	BlockPos in, out;
	EnumFacing facing;

	public TileFluidHopper() {
	}

	@Override
	public void update() {
		ticks++;
		if (ticks == 2) {
			ticks = 0;
			updateVars();
			execute();
		}
	}

	private void execute() {
		if(canWork()) {
			IFluidHandler in = world.getTileEntity(this.in).getCapability(FH, facing);
			IFluidHandler out = world.getTileEntity(this.out).getCapability(FH, facing);
			FluidStack stack = in.drain(20, false);
			stack.amount = out.fill(stack, true);
			if(stack.amount > 0) {
				in.drain(stack, true);
			}
		}
	}

	private boolean canWork() {
		return world.getTileEntity(in).hasCapability(FH, EnumFacing.DOWN)
				&& world.getTileEntity(out).hasCapability(FH, facing);
	}

	private void updateVars() {
		this.in = pos.up(1);
		facing = world.getBlockState(pos).getValue(Machine.FACING);
		this.out = pos.down(1).offset(facing);
	}

}
