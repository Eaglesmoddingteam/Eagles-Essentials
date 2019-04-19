package btf.util.helpers.block;

import net.minecraft.util.ResourceLocation;

import java.util.ArrayList;

public interface ISlab {

	boolean isCompatibleWith(ISlab slab);

	Enum<? extends EnumSlabTypes> getInstanceSlabTypes();

	ArrayList<ResourceLocation> getTextures();

	enum SlabPos {
		TOP, BOTTOM;

		public static SlabPos fromMeta(int meta) {
			return values()[meta];
		}

		public int toMeta() {
			return this.ordinal();
		}
	}
}
