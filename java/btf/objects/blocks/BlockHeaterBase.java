package btf.objects.blocks;

import btf.main.Main;
import btf.util.helpers.block.ISlab;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;

public abstract class BlockHeaterBase extends BlockBase implements ITileEntityProvider, ISlab{

	public BlockHeaterBase(String name) {
		super(name, Material.ROCK, Main.blocksTab, 2);
	}

}
