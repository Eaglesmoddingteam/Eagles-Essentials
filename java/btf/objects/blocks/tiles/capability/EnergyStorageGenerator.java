package btf.objects.blocks.tiles.capability;

import net.minecraftforge.energy.EnergyStorage;

public class EnergyStorageGenerator extends EnergyStorage {

	public EnergyStorageGenerator(int capacity, int maxReceive, int maxExtract, int energy) {
		super(capacity, maxReceive, maxExtract, energy);
	}

	public int recieveInternal(int maxAmount, boolean simulate) {
		int max_amnt = capacity - energy;
		int amount = Math.min(maxAmount, max_amnt);
		if(!simulate) {
			this.energy+=amount;
		}
		return amount;
	}

	public boolean isEmpty() {
		return energy > 0;
	}
	
}
