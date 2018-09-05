package btf.util.blockstate.entries;

import java.util.ArrayList;
import java.util.List;

import btf.util.obj.IBlockStateVariantEntry;;

public class BlockStateVariantHeater implements IBlockStateVariantEntry {


	List<Type> TYPES = new ArrayList();

	public int add(Type type){
		TYPES.add(type);
		return TYPES.lastIndexOf(type);
	}

	public void remove(Type type) {
		TYPES.remove(type);
	}
	
	@Override
	public Type[] getTypes() {
		return this.TYPES.toArray(new Type[TYPES.size()]);
	}

	@Override
	public int getKeyForType(Type type) {
		return this.TYPES.lastIndexOf(type);
	}

	@Override
	public Type getTypeByKey(int key) {
		return TYPES.get(key);
	}

	@Override
	public boolean hasKey(int key) {
		return TYPES.size() > key;
	}

	@Override
	public Class<? extends Type> getTypeBaseClass() {
		return Type.class;
	}

	@Override
	public boolean hasType(Type type) {
		return TYPES.contains(type);
	}

}