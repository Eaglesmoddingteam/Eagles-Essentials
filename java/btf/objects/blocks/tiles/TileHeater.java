package btf.objects.blocks.tiles;

import static btf.util.helpers.ArrayHelper.includes;

import javax.annotation.Nullable;

import btf.objects.blocks.tiles.capability.EnergyStorageGenerator;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.IEnergyStorage;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;

public class TileHeater extends TileEntity implements ITickable {

	Capability<IItemHandler> ITEM_HANDLER = CapabilityItemHandler.ITEM_HANDLER_CAPABILITY;
	Capability<IEnergyStorage> FE = CapabilityEnergy.ENERGY;
	IItemHandler inventory = new ItemStackHandler(1);
	EnergyStorageGenerator energy;
	int burntimeLeft = 0;
	int genRate;
	int maxout;
	private Item[] burnables;

	public TileHeater(int maxout, int genRate, Item... burnables) {
		this.energy = new EnergyStorageGenerator(4000, 0, maxout, 0);
		this.maxout = maxout;
		this.genRate = genRate;
		this.burnables = burnables;
	}

	@Override
	public void update() {
		if (isBurning()) {
			if (canBurn()) {
				this.energy.recieveInternal(genRate, false);
			}
		}
		tryBurn();
		if (!energy.isEmpty()) {
			exportEnergy(energy.getEnergyStored());
		}
	}

	private void exportEnergy(int energyStored) {
		int out = 0;
		for (EnumFacing face : EnumFacing.VALUES) {
			TileEntity tile = world.getTileEntity(pos.offset(face, 1));
			if (tile != null) {
				if (tile.hasCapability(FE, face.getOpposite()) && out < maxout) {
					IEnergyStorage storage = tile.getCapability(FE, face.getOpposite());
					int maxOut = energy.extractEnergy(maxout - out, true);
					int maxAccept = storage.receiveEnergy(maxOut, false);
					out += energy.extractEnergy(maxAccept, false);
				}
			}
		}
	}

	private boolean canBurn() {
		return this.energy.getEnergyStored() + genRate < 4000;
	}

	private void tryBurn() {
		if (!inventory.getStackInSlot(0).isEmpty()) {
			if (includes(burnables, inventory.getStackInSlot(0).getItem())) {
				this.burntimeLeft = 1000;
			}
		}
	}

	private boolean isBurning() {
		return burntimeLeft > 0;
	}

	@Nullable
	@Override
	public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
		if (capability == ITEM_HANDLER) {
			return (T) this.inventory;
		} else if (capability == FE) {
			return (T) this.energy;
		}
		return null;
	}

	@Override
	public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
		return capability == ITEM_HANDLER || capability == FE;
	}

}
