package btf.objects.blocks.tiles.heaters;

import btf.event.EventGetFuelBurntime;
import btf.objects.blocks.BlockHeaterBase;
import btf.objects.blocks.tiles.TileHeater;
import net.minecraft.block.Block;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.event.ForgeEventFactory;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidTank;
import net.minecraftforge.fluids.FluidEvent.FluidSpilledEvent;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;

public class HeaterFluid extends TileHeater {

	FluidTank tank = new FluidTank(4000);

	@Override
	public int getMaxout() {
		return 500;
	}

	@Override
	public int getGenRate() {
		return 125;
	}

	@Override
	public void useFuel() {
		tank.drainInternal(1000, true);
	}

	@Override
	public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
		return capability == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY || capability == HEAT;
	}

	@Override
	public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
		if (capability == HEAT) {
			return super.getCapability(capability, facing);
		} else if (capability == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY) {
			return (T) tank;
		} else {
			return null;
		}
	}

	@Override
	public int getFuel() {
		if (tank.getFluidAmount() == 0) {
			return 0;
		}
		EventGetFuelBurntime burntime = new EventGetFuelBurntime(tank.getFluid());
		MinecraftForge.EVENT_BUS.post(burntime);
		return burntime.getBurnTime();
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound) {
		compound.setTag("fluid", CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY.writeNBT(tank, EnumFacing.DOWN));
		return super.writeToNBT(compound);
	}

	@Override
	public void readFromNBT(NBTTagCompound compound) {
		CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY.readNBT(tank, null, compound.getTag("fluid"));
		super.readFromNBT(compound);
	}

	public static final Block BLOCK = new BlockHeaterBase("heater_fluid") {

		@Override
		public TileEntity createNewTileEntity(World worldIn, int meta) {
			return new HeaterFluid();
		}

	};
}
