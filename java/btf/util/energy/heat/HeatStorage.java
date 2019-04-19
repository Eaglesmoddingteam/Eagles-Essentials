package btf.util.energy.heat;

import net.minecraft.nbt.NBTTagCompound;

public class HeatStorage implements IHeatStorage {
	private int HEAT, MAX_IN, MAX_OUT, CAPACITY = 0;
	private boolean CAN_RECEIVE, CAN_EXTRACT = false;

	public HeatStorage(int maxIn, int maxOut, int capacity) {
		this.MAX_IN = maxIn;
		this.MAX_OUT = maxOut;
		this.CAN_EXTRACT = (maxOut > 0);
		this.CAN_RECEIVE = (maxIn > 0);
		this.CAPACITY = capacity;
	}

	@Override
	public int receiveHeat(int maxReceive, boolean simulate) {
		return CAN_RECEIVE ? receive(maxReceive, simulate) : 0;
	}

	private int receive(int maxReceive, boolean simulate) {
		int amount = Math.min(MAX_IN, (maxReceive < (CAPACITY - HEAT) ? maxReceive : CAPACITY - HEAT));
		if (!simulate)
			HEAT += amount;
		return amount;
	}

	@Override
	public int extractHeat(int maxExtract, boolean simulate) {
		return CAN_EXTRACT ? extract(maxExtract, simulate) : 0;
	}

	private int extract(int maxExtract, boolean simulate) {
		int amount = maxExtract < HEAT ? Math.min(maxExtract, MAX_OUT) : Math.min(MAX_OUT, HEAT);
		if (!simulate)
			HEAT -= amount;
		return amount;
	}

	@Override
	public int getHeatstored() {
		return HEAT;
	}

	@Override
	public int getHeatCapacity() {
		return CAPACITY;
	}

	@Override
	public boolean canExtract() {
		return CAN_RECEIVE;
	}

	@Override
	public boolean canReceive() {
		return CAN_EXTRACT;
	}

	public void readNBT(NBTTagCompound compound) {
		int[] data = compound.getIntArray("heatvalues");
		this.CAPACITY = data[0];
		this.HEAT = data[1];
		this.MAX_IN = data[2];
		this.MAX_OUT = data[3];
		this.CAN_EXTRACT = data[4] == 1;
		this.CAN_RECEIVE = data[5] == 1;
	}

	public void writeNBT(NBTTagCompound compound) {
		compound.setIntArray("heatvalues", new int[]{
				this.CAPACITY,
				this.HEAT,
				this.MAX_IN,
				this.MAX_OUT,
				this.CAN_EXTRACT ? 1 : 0,
				this.CAN_RECEIVE ? 1 : 0
		});
	}

	public int recieveInternal(int amount, boolean simulate) {
		int recieve = Math.min(amount, (CAPACITY - HEAT));
		if (!simulate)
			HEAT += recieve;
		return recieve;
	}

	public boolean isEmpty() {
		return HEAT <= 0;
	}
}
