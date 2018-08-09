package btf.objects.blocks;

import btf.main.Main;
import btf.objects.blocks.tiles.TileHeatCell;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockHeatCell extends BlockBase implements ITileEntityProvider{

	public BlockHeatCell() {
		super("heatcell", Material.ROCK, Main.blocksTab, 2);
	}
	
	@Override
	public boolean hasTileEntity() {
		return true;
	}

	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta) {
		return new TileHeatCell();
	}
}
