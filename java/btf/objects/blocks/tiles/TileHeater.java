package btf.objects.blocks.tiles;

import javax.annotation.Nullable;

import btf.util.energy.heat.CapabilityHeat;
import btf.util.energy.heat.HeatStorage;
import btf.util.energy.heat.IHeatStorage;
import btf.util.helpers.NBTHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;

public abstract class TileHeater extends TileEntity implements ITickable {

	protected static Capability<IItemHandler> ITEM_HANDLER = CapabilityItemHandler.ITEM_HANDLER_CAPABILITY;
	protected static Capability<IHeatStorage> HEAT = CapabilityHeat.HEAT_CAPABILITY;
	public IItemHandler inventory = new ItemStackHandler(1);
	protected HeatStorage heatStorage = new HeatStorage(0, getMaxout(), 4000);
	public ItemStack burning = ItemStack.EMPTY;
	int burntimeLeft = 0;
	int genRate;
	int maxout;
	public long lastChangeTime;

	public TileHeater() {
		this.maxout = getMaxout();
		this.genRate = getGenRate();
	}

	public abstract int getMaxout();

	public abstract int getGenRate();

	@Override
	public void update() {
		if (canBurn()) {
			if (isBurning()) {
				this.heatStorage.recieveInternal(genRate, false);
			} else {
				tryBurn();
			}
		}
		if (!heatStorage.isEmpty()) {
			exportEnergy(heatStorage.getHeatstored());
		}
	}

	private void exportEnergy(int energyStored) {
		int out = 0;
		for (EnumFacing face : EnumFacing.VALUES) {
			TileEntity tile = world.getTileEntity(pos.offset(face, 1));
			if (tile != null && tile.hasCapability(HEAT, face.getOpposite()) && out < maxout) {
				IHeatStorage storage = tile.getCapability(HEAT, face.getOpposite());
				int maxOut = heatStorage.extractHeat(maxout - out, true);
				int maxAccept = storage.receiveHeat(maxOut, false);
				out += heatStorage.extractHeat(maxAccept, false);
			}
		}
	}

	private boolean canBurn() {
		return this.heatStorage.getHeatstored() + genRate < 4000;
	}

	private void tryBurn() {
		int fuel = getFuel();
		if (fuel > 0) {
			markBurn(fuel);
			useFuel();
		}
	}

	public abstract void useFuel();

	/**
	 * @return 0 if there's no fuel in the machine, else return the fuel's burntime
	 */
	public abstract int getFuel();

	private void markBurn(int burnTime) {
		this.burntimeLeft = burnTime;
	}

	private boolean isBurning() {
		return burntimeLeft > 0;
	}

	@Nullable
	@Override
	public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
		if (capability == ITEM_HANDLER) {
			return (T) this.inventory;
		} else if (capability == HEAT) {
			return (T) this.heatStorage;
		}
		return null;
	}

	@Override
	public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
		return capability == ITEM_HANDLER || capability == HEAT;
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound) {
		if (HEAT == null) {
			HEAT = CapabilityHeat.HEAT_CAPABILITY;
		}
		compound.setInteger("burntimeleft", burntimeLeft);
		compound.setTag("burning", NBTHelper.toNBT(burning));
		compound.setTag("heat", HEAT.writeNBT(heatStorage, EnumFacing.DOWN));
		compound.setTag("inv", ITEM_HANDLER.writeNBT(inventory, EnumFacing.DOWN));
		return super.writeToNBT(compound);
	}

	@Override
	public void readFromNBT(NBTTagCompound compound) {
		if (HEAT == null) {
			HEAT = CapabilityHeat.HEAT_CAPABILITY;
		}
		burntimeLeft = compound.getInteger("burntimeleft");
		burning = (ItemStack) NBTHelper.fromNBT((NBTTagCompound) compound.getTag("burning"));
		HEAT.readNBT(heatStorage, EnumFacing.DOWN, compound.getTag("heat"));
		ITEM_HANDLER.readNBT(inventory, EnumFacing.DOWN, compound.getTag("inv"));
		super.readFromNBT(compound);
	}

}
