package btf.util.blockstate.types.heater;

import java.util.ArrayList;
import java.util.Optional;

import btf.util.obj.HeaterType;
import btf.util.obj.ItemType;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

public class HeaterBlazeing extends HeaterType{

	@Override
	public ItemType getBurnType() {
		return new Type();
	}

	@Override
	public boolean canBeWith() {
		return false;
	}

	@Override
	public int getBurnTime() {
		return 300;
	}

	@Override
	public int getGenSpeed() {
		return 20;
	}
	
	@Override
	public int getBurnTimeForItem(ItemStack stack) {
		if(stack.getItem() == Items.BLAZE_ROD) {
			return 1000;
		}
		return getBurnTime();
	}
	
	public class Type extends ItemType{

		@Override
		public Optional<ArrayList<ItemStack>> getAcceptedItems() {
			return Optional.empty();
		}

		@Override
		public boolean includesItem(ItemStack item) {
			return item.getItem() == Items.BLAZE_POWDER || item.getItem() == Items.BLAZE_ROD;
		}

		@Override
		public String toString() {
			return "type_" + HeaterBlazeing.class.toString() + "_itemTypeDEFAULT";
		}
	}

}
