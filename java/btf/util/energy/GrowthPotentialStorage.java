package btf.util.energy;

/**
 * Reference implementation of {@link IGrowthPotentialStorage}.
 */

public class GrowthPotentialStorage implements IGrowthPotentialStorage {

    protected int growthPotential;
    protected int capacity;
    protected int maxReceive;
    protected int maxExtract;

    public GrowthPotentialStorage(int capacity)
    {
        this(capacity, capacity, capacity, 0);
    }

    public GrowthPotentialStorage(int capacity, int maxTransfer)
    {
        this(capacity, maxTransfer, maxTransfer, 0);
    }

    public GrowthPotentialStorage(int capacity, int maxReceive, int maxExtract)
    {
        this(capacity, maxReceive, maxExtract, 0);
    }

    public GrowthPotentialStorage(int capacity, int maxReceive, int maxExtract, int energy)
    {
        this.capacity = capacity;
        this.maxReceive = maxReceive;
        this.maxExtract = maxExtract;
        this.growthPotential = Math.max(0 , Math.min(capacity, energy));
    }

    @Override
    public int receiveGP(int maxReceive, boolean simulate)
    {
        if (!canReceive())
            return 0;

        int GPReceived = Math.min(capacity - growthPotential, Math.min(this.maxReceive, maxReceive));
        if (!simulate)
            growthPotential += GPReceived;
        return GPReceived;
    }

    @Override
    public int extractGP(int maxExtract, boolean simulate)
    {
        if (!canExtract())
            return 0;

        int GPExtracted = Math.min(growthPotential, Math.min(this.maxExtract, maxExtract));
        if (!simulate)
            growthPotential -= GPExtracted;
        return GPExtracted;
    }

    @Override
    public int getGPStored()
    {
        return growthPotential;
    }

    @Override
    public int getGPCapacity()
    {
        return capacity;
    }

    @Override
    public boolean canExtract()
    {
        return this.maxExtract > 0;
    }

    @Override
    public boolean canReceive()
    {
        return this.maxReceive > 0;
    }
}
