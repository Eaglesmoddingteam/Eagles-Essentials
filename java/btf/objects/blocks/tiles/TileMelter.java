package btf.objects.blocks.tiles;

import btf.util.energy.heat.CapabilityHeat;
import btf.util.energy.heat.HeatStorage;
import btf.util.energy.heat.IHeatStorage;
import com.google.common.collect.Maps;
import com.google.gson.Gson;
import mcp.MethodsReturnNonnullByDefault;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.fluids.FluidTank;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;

import javax.annotation.Nullable;
import java.util.Map;

public class TileMelter extends TileEntity implements ITickable {

	/* static variables*/
	private static Gson gson = new Gson();
	private static Capability<IHeatStorage> heatStorageCapability = CapabilityHeat.HEAT_CAPABILITY;
	private static Capability<IItemHandler> itemHandlerCapability = CapabilityItemHandler.ITEM_HANDLER_CAPABILITY;
	private static Capability<IFluidHandler> fluidHandlerCapability = CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY;

	/* instance variables */
	private Configuration configuration;
	private HeatStorage heatStorage = new HeatStorage(100, 0, 1000);
	private IItemHandler itemStorage = new ItemStackHandler(1);
	private IFluidHandler fluidStorage = new FluidTank(1000);

	/**
	 * checks if capabilities are null, and if so, reassigns them
	 * <p>
	 * forge inserts some capabilities later than this class initializes, but doesn't inject into these fields whose values are still null.
	 */
	public static void checkCapabilities() {
		if (heatStorageCapability == null) {
			heatStorageCapability = CapabilityHeat.HEAT_CAPABILITY;
		}
		if (itemHandlerCapability == null) {
			itemHandlerCapability = CapabilityItemHandler.ITEM_HANDLER_CAPABILITY;
		}
		if (fluidHandlerCapability == null) {
			fluidHandlerCapability = CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY;
		}
	}

	/**
	 * Like the old updateEntity(), except more generic.
	 */
	@Override
	public void update() {

	}

	/**
	 * deserializes this object from NBT
	 *
	 * @param compound the NBT to read from
	 */
	@Override
	public void readFromNBT(NBTTagCompound compound) {
		configuration = gson.fromJson(compound.getString(""), Configuration.class);
		super.readFromNBT(compound);
	}

	@SuppressWarnings("unchecked")
	@Nullable
	@Override
	public <T> T getCapability(Capability<T> capability, @Nullable EnumFacing facing) {
		if (capability == heatStorageCapability) {
			return (T) heatStorage;
		} else if (capability == itemHandlerCapability) {
			return (T) itemStorage;
		} else if (capability == fluidHandlerCapability) {
			return (T) fluidStorage;
		}
		return null;
	}

	@Override
	public boolean hasCapability(Capability<?> capability, @Nullable EnumFacing facing) {
		return capability == heatStorageCapability || capability == itemHandlerCapability || capability == fluidHandlerCapability;
	}

	/**
	 * serializes this object into NBT
	 *
	 * @param compound the NBT to write into
	 * @return the NBT written to, same as the compound parameter
	 */
	@Override
	@MethodsReturnNonnullByDefault
	public NBTTagCompound writeToNBT(NBTTagCompound compound) {
		super.writeToNBT(compound);
		compound.setString("configuration", gson.toJson(configuration));
		return compound;
	}

	/**
	 * Object storing the configuration of the machine, filled with helper methods
	 * just here to make (de)serializing with gson possible
	 */
	public static class Configuration {

		private Map<EnumFacing, SideType> sideTypeMap = Maps.newEnumMap(EnumFacing.class);

		public void setSideType(EnumFacing side, SideType type) {
			sideTypeMap.replace(side, type);
		}

		public void rotateRight(EnumFacing side) {
			setSideType(side, sideTypeMap.get(side).right());
		}

		public void rotateLeft(EnumFacing side) {
			setSideType(side, sideTypeMap.get(side).left());
		}

		public EnumFacing[] getSidesOfType(SideType type) {
			return sideTypeMap.entrySet().stream().filter(e -> e.getValue() == type).map(Map.Entry::getKey).toArray(EnumFacing[]::new);
		}

		public EnumFacing[] getInputs() {
			return getSidesOfType(SideType.ITEM_IN);
		}

		public EnumFacing[] getOutputs() {
			return getSidesOfType(SideType.FLUID_OUT);
		}

		public enum SideType {

			NONE, ITEM_IN, FLUID_OUT;

			public SideType left() {
				int index = this.ordinal() - 1;
				return SideType.values()[index >= 0 ? index : 2];
			}

			public SideType right() {
				int index = this.ordinal() + 1;
				return SideType.values()[index < 3 ? index : 0];
			}
		}

	}

}
