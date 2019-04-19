package btf.util.obj;

public interface IBlockStateVariantEntry {

	Type[] getTypes();

	int getKeyForType(Type type);

	Type getTypeByKey(int key);

	boolean hasKey(int key);

	Class<? extends Type> getTypeBaseClass();

	boolean hasType(Type type);

	interface Type {

		@Override
		String toString();

		@Override
		int hashCode();

		@Override
		boolean equals(Object arg0);

	}
}
