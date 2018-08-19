package btf.util.obj;

import java.util.ArrayList;
import java.util.Optional;

import net.minecraft.item.ItemStack;

public abstract class ItemType {	
	
	public abstract Optional<ArrayList<ItemStack>> getAcceptedItems();
	
	abstract boolean includesItem(ItemStack item);
	
	@Override
	public abstract String toString();
	
	@Override
	public int hashCode() {
		return 9 * 1 * toString().hashCode();
	}
	
	@Override
	public boolean equals(Object arg0) {
		return arg0.hashCode() == this.hashCode();
	}
}
