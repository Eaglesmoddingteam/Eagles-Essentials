package btf.objects.blocks.tiles;

import org.apache.logging.log4j.Level;

import btf.main.Main;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidEvent.FluidSpilledEvent;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;

public class TileSqueezer extends TileEntity implements ITickable {

	int ticksIn = 0;

	@Override
	public void update() {
		ticksIn++;
		if (ticksIn == 20) {
			ticksIn = 0;

			if (canwork()) {
				TileEntity top = world.getTileEntity(pos.up());
				FluidStack outcome = //
						processitems(top.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, EnumFacing.DOWN),
								false);
				if (outcome != null) {
					TileEntity bottom = world.getTileEntity(pos.down());
					IFluidHandler fluidHandler = //
							bottom.getCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY, EnumFacing.UP);
					int i = fluidHandler.fill(outcome, false);
					if (i == outcome.amount) {
						fluidHandler.fill(outcome, true);
						processitems(top.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, EnumFacing.DOWN),
								true);
					}
				}
			}
		}
	}

	private boolean canwork() {
		if (world.getTileEntity(pos.up()) != null && world.getTileEntity(pos.down()) != null) {
			boolean flag = world.getTileEntity(pos.down())
					.hasCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY, EnumFacing.UP);
			boolean flag2 = world.getTileEntity(pos.up()).hasCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY,
					EnumFacing.DOWN);
			return flag && flag2;
		}
		return false;
	}

	private FluidStack processitems(IItemHandler inventory, boolean doExtract) {
		for (int i = 0; i < inventory.getSlots(); i++) {
			ItemStack stack = inventory.getStackInSlot(i);

			if (stack.getItem() == Items.MAGMA_CREAM && stack.getCount() > 2) {
				if (doExtract)
					inventory.extractItem(i, 3, false);
				return new FluidStack(FluidRegistry.lookupFluidForBlock(Blocks.LAVA), 500);
			}
		}

		return null;
	}

}
