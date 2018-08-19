package btf.util.obj;

import net.minecraft.item.ItemStack;

public interface IBlockStateVariantEntry {

	public Type[] getTypes();

	public int getKeyForType(Type type);

	public Type getTypeByKey(int key);

	public boolean hasKey(int key);

	public Class<? extends Type> getTypeBaseClass();

	public boolean hasType(Type type);

	public interface Type {

		@Override
		public String toString();

		@Override
		public int hashCode();

		@Override
		public boolean equals(Object arg0);
	
	}
}
