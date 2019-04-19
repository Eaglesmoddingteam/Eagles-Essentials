package btf.util.energy;

/**
 * A growth potential storage is the unit of interaction with GrowthPotential inventories.
 * <p>
 * A reference implementation can be found at {@link GrowthPotentialStorage}.
 * <p>
 * Derived from the energy power system already present within forge.
 */
public interface IGrowthPotentialStorage {
	/**
	 * Adds growth potential to the storage. Returns quantity of growth potential that was accepted.
	 *
	 * @param maxReceive Maximum amount of growth potential to be inserted.
	 * @param simulate   If TRUE, the insertion will only be simulated.
	 * @return Amount of growth potential that was (or would have been, if simulated) accepted by the storage.
	 */
	int receiveGP(int maxReceive, boolean simulate);

	/**
	 * Removes growth potential from the storage. Returns quantity of growth potential that was removed.
	 *
	 * @param maxExtract Maximum amount of growth potential to be extracted.
	 * @param simulate   If TRUE, the extraction will only be simulated.
	 * @return Amount of growth potential that was (or would have been, if simulated) extracted from the storage.
	 */
	int extractGP(int maxExtract, boolean simulate);

	/**
	 * Returns the amount of growth potential currently stored.
	 */
	int getGPStored();

	/**
	 * Returns the maximum amount of growth potential that can be stored.
	 */
	int getGPCapacity();

	/**
	 * Returns if this storage can have growth potential extracted.
	 * If this is false, then any calls to extractGP will return 0.
	 */
	boolean canExtract();

	/**
	 * Used to determine if this storage can receive growth potential.
	 * If this is false, then any calls to receiveGP will return 0.
	 */
	boolean canReceive();
}