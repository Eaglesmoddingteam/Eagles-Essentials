package btf.objects.blocks;

import btf.objects.blocks.tiles.TileShower;
import net.minecraft.block.Block;
import net.minecraft.block.BlockRedstoneWire;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class MachineShower extends BlockBase implements ITileEntityProvider{

	public MachineShower(String name, Material materialIn, CreativeTabs tab, int harvestlevel) {
		super(name, materialIn, tab, harvestlevel);
	}

	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta) {
		return new TileShower();
	}
	
	@Override
	public boolean hasTileEntity() {
		return true;
	}
	
	@Override
	public TileEntity createTileEntity(World world, IBlockState state) {
		return new TileShower();
	}
	
	@Override
	public void neighborChanged(IBlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos fromPos) {
		IBlockState neighbor = worldIn.getBlockState(fromPos);
		if(worldIn.isBlockPowered(pos)) {
				TileShower tile = (TileShower) worldIn.getTileEntity(pos);
				if(tile != null)
				tile.fire(worldIn);
		}
	}

}
