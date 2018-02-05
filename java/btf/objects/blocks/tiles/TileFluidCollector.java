package btf.objects.blocks.tiles;

import javax.annotation.Nullable;

import net.minecraft.block.Block;
import net.minecraft.block.BlockStaticLiquid;
import net.minecraft.block.state.IBlockState;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler;

public class TileFluidCollector extends TileEntity implements ITickable{

	Capability<IFluidHandler> CFH = CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY;
	FluidTank storage = new FluidTank(4000);
	BlockPos under = null;

	BlockPos PosOnTop;

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound) {
		storage.writeToNBT(compound);
		return super.writeToNBT(compound);
	}

	@Override
	public void readFromNBT(NBTTagCompound compound) {
		super.readFromNBT(compound);
		storage.readFromNBT(compound);
	}

	@Override
	public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
		return capability == CFH;
	}

	@Nullable
	@Override
	public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
		return capability == CFH ? (T) storage : null;
	}

	public void activate() {
		PosOnTop = pos.add(0, 1, 0);
		IBlockState top = world.getBlockState(PosOnTop);
		if (top.getBlock() instanceof BlockStaticLiquid) {
			Block block = top.getBlock();
			BlockStaticLiquid liquid = (BlockStaticLiquid) block;
			Fluid fluid = FluidRegistry.lookupFluidForBlock(block);
			if (fluid != null) {
				int i = top.getPropertyKeys().contains(liquid.LEVEL) ? (int) top.getProperties().get(liquid.LEVEL) : 0;
				storage.fillInternal(new FluidStack(fluid, i == 0 ? 1000 : 20), true);
				world.setBlockToAir(PosOnTop);
			}
		}
	}

	@Override
	public void update() {
			if(under == null) {
				under = pos.add(0, -1, 0);
			}
			if(storage.getFluid() != null)
			if(world.getTileEntity(under) != null)
			if(world.getTileEntity(under).hasCapability(CFH, null)) {
				IFluidHandler storage = world.getTileEntity(under).getCapability(CFH, null);
				FluidStack toFill = new FluidStack(this.storage.getFluid().getFluid(), this.storage.getFluidAmount() > 200 ? 200 : this.storage.getFluidAmount());
				storage.fill(this.storage.drainInternal(storage.fill(toFill, false), true), true);
			}
	}

}
