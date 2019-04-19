package btf.event;

import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fml.common.eventhandler.Event;

public class EventGetFuelBurntime extends Event {

	private final Fluid fluid;
	private final int amount;
	private int burnTime = 0;

	public EventGetFuelBurntime(FluidStack stack) {
		fluid = stack.getFluid();
		amount = stack.amount;
	}

	public int getBurnTimeForAmount() {
		return (int) Math.floor(amount * (burnTime / 1000d));
	}

	public int getBurnTimeForAmount(int amount) {
		return (int) Math.floor(amount * (burnTime / 1000d));
	}

	/**
	 * gets the burntime for the fuel;<br>
	 * <ul>
	 * <li>0 means it cannot burn</li>
	 * <li>every value above zero is the amount of ticks it takes per bucket to
	 * burn.</li>
	 * </ul>
	 *
	 * @return the amount of ticks it takes to burn 1 bucket
	 */
	public int getBurnTime() {
		return burnTime;
	}

	/**
	 * sets the burntime for the fuel;<br>
	 * <ul>
	 * <li>0 means it cannot burn</li>
	 * <li>every value above zero is the amount of ticks it takes per bucket to
	 * burn.</li>
	 * </ul>
	 *
	 * @param burnTime the new value for burntime
	 */
	public void setBurnTime(int burnTime) {
		this.burnTime = burnTime;
	}

	/**
	 * @return the fluid
	 */
	public Fluid getFluid() {
		return fluid;
	}

	/**
	 * @return the amount of fuel in millibuckets
	 */
	public int getAmount() {
		return amount;
	}

}
