package btf.util.blockstate.registry;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.annotation.Nullable;

import btf.main.Main;
import btf.util.obj.IBlockStateVariantEntry;
import net.minecraft.client.settings.GameSettings;

public class EntryManager {

	private static List<IBlockStateVariantEntry> ENTRIES = new ArrayList<>();

	@Nullable
	public static IBlockStateVariantEntry getEntryByClass(Class<? extends IBlockStateVariantEntry> clazz) {
		Optional<IBlockStateVariantEntry> optional = ENTRIES.stream()//
				.filter(entry -> entry.getClass() == clazz)//
				.findFirst();
		return optional.isPresent() ? optional.get() : null;
	}

	public static boolean hasEntry(Class<? extends IBlockStateVariantEntry> clazz) {
		Optional<IBlockStateVariantEntry> optional = ENTRIES.stream()//
				.filter(entry -> entry.getClass() == clazz)//
				.findFirst();
		return optional.isPresent();
	}

	public static void addEntry(IBlockStateVariantEntry entry) {
		if (!hasEntry(entry.getClass())) {
			ENTRIES.add(entry);
			return;
		}
		Main.LOGGER.error("entry already existed!!", entry, ENTRIES);
	}

}
