package btf.util.obj;

import btf.util.handlers.HeaterHandler;
import btf.util.obj.IBlockStateVariantEntry.Type;
import net.minecraft.item.ItemStack;

public abstract class HeaterType implements Type {

	public HeaterType() {
	}

	public abstract ItemType getBurnType();

	public abstract boolean canBeWith();

	public abstract int getBurnTime();

	public int getBurnTimeForItem(ItemStack stack) {
		return getBurnType().includesItem(stack) ? getBurnTime() * stack.getCount() : 0;
	}

	public abstract int getGenSpeed();

}
