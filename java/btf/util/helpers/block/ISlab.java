package btf.util.helpers.block;

import java.util.ArrayList;

import net.minecraft.util.ResourceLocation;

public interface ISlab {

	public enum SlabPos {
		TOP, BOTTOM;

		public int toMeta() {
			return this.ordinal();
		}
		
		public static SlabPos fromMeta(int meta) {
			return values()[meta];
		}
	}
	
	public boolean isCompatibleWith(ISlab slab);
	
	public Enum<? extends EnumSlabTypes> getInstanceSlabTypes();
	
	public ArrayList<ResourceLocation> getTextures();
}
