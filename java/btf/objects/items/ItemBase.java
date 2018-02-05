package btf.objects.items;


import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;

public class ItemBase extends Item {

	public ItemBase(String name, CreativeTabs tab) {
		this(name, 64, tab);
	}

	public ItemBase(String name, int stackSize, CreativeTabs tab) {
		setUnlocalizedName(name);
		setRegistryName(name);
		setMaxStackSize(stackSize);
		setCreativeTab(tab);
	}
}
