package btf.objects.blocks;

import btf.main.Main;
import btf.objects.blocks.tiles.TileSqueezer;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class BlockSqueezer extends BlockBase implements ITileEntityProvider {

	public BlockSqueezer() {
		super("squeezer", Material.ROCK, Main.blocksTab, 2);
	}

	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta) {
		return new TileSqueezer();
	}

}
