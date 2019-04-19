package btf.objects.blocks;


import btf.main.Vars;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.util.ResourceLocation;

public class BlockBase extends Block {

	public BlockBase(String name, Material materialIn, CreativeTabs tab) {
		super(materialIn);
		setUnlocalizedName(name);
		setRegistryName(new ResourceLocation(Vars.MOD_ID, name));
		setCreativeTab(tab);
		setHarvestLevel("pickaxe", 0);
		setHardness(1);
	}

	public BlockBase(String name, Material materialIn, CreativeTabs tab, int harvestlevel) {
		this(name, materialIn, tab);
		setHarvestLevel("pickaxe", harvestlevel);
		setHardness(1.5f * harvestlevel + 1);
	}

	public Item createItemBlock() {
		return new ItemBlock(this).setRegistryName(getRegistryName());
	}
}
