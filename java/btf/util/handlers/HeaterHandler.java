package btf.util.handlers;

import java.util.HashMap;

import btf.util.blockstate.entries.BlockStateVariantHeater;
import btf.util.blockstate.registry.EntryManager;
import btf.util.blockstate.types.heater.HeaterBlazeing;
import btf.util.obj.HeaterType;
import btf.util.obj.IBlockStateVariantEntry;

public class HeaterHandler {

	static boolean init = false;
	public static HashMap<Class, Integer> STANDARD_KEYS = new HashMap<>();

	private HeaterHandler() {
	}

	public static void init() {
		if (init) {
			throw new IllegalStateException("The handler " + HeaterHandler.class.toString()
					+ " already finished INIT this is NOT an error caused by Eagles Essentials!! /n"
					+ "this is in most cases caused by a mod trying to call our init function, which it shouldn't!!");
		}
		init = false;
		BlockStateVariantHeater heater = new BlockStateVariantHeater();
		EntryManager.addEntry(heater);
	}

	private void addDefaultTypes(BlockStateVariantHeater variantHeater) {
		STANDARD_KEYS.put(HeaterBlazeing.class, variantHeater.add(new HeaterBlazeing()));
	}

	public void addType(HeaterType type) {
		IBlockStateVariantEntry entry = EntryManager.getEntryByClass(BlockStateVariantHeater.class);
		if (entry != null)
			if (entry instanceof BlockStateVariantHeater) {
				BlockStateVariantHeater variantHeater = (BlockStateVariantHeater) entry;
				variantHeater.add(type);
			}
	}

}
