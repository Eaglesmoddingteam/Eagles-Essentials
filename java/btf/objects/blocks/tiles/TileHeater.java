package btf.objects.blocks.tiles;

import btf.util.obj.HeaterType;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.EnergyStorage;
import net.minecraftforge.energy.IEnergyStorage;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;

public class TileHeater extends TileEntity implements ITickable {

	Capability<IItemHandler> ITEM_HANDLER = CapabilityItemHandler.ITEM_HANDLER_CAPABILITY;
	Capability<IEnergyStorage> FE = CapabilityEnergy.ENERGY;
	IItemHandler inventory = new ItemStackHandler(1);
	EnergyStorage energy;
	HeaterType type;
	int ticks = 0;
	int burntimeLeft, genRate = 0;
	
	public TileHeater(HeaterType type) {
		this.type = type;
		this.energy = new EnergyStorage(4000, 0, type.getBurnTime(), 0);
	}

	@Override
	public void update() {
		if(canBurn()) {
			burntimeLeft--;
			this.energy.receiveEnergy(genRate, false);
		}
		ticks++;
		if(ticks == 2) {
			ticks = 0;
			run();
		}
	}
	
	private boolean canBurn() {
		return burntimeLeft > 0 && energy.getEnergyStored()  < energy.getMaxEnergyStored();
	}
	
	@Override
	public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
		if(capability == ITEM_HANDLER) {
			return (T) this.inventory;
		} else if(capability == FE){
			return (T) this.energy;
		}
		return null;
	}
	
	@Override
	public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
		return capability == ITEM_HANDLER || capability == FE;
	}
	
	private void run() {
		ItemStack stack = inventory.extractItem(0, 1, true);
		boolean flag = stack.isEmpty();
		if(!flag) {
			int totalburntime = this.type.getBurnTimeForItem(stack);
			if(totalburntime > 0) {
				burntimeLeft = totalburntime;
				genRate = type.getGenSpeed();
				inventory.extractItem(0, 1, false);
			}
		}
	}
	
	

}
