package btf.util.handlers;

import btf.event.EventGetFuelBurntime;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class BurnTimeHandler {

	@SubscribeEvent
	public void getBurnime(EventGetFuelBurntime event) {
		if (event.getFluid() == FluidRegistry.LAVA) {
			event.setBurnTime(1000);
		}
	}

}
