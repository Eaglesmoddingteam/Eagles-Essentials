package btf.objects.blocks;

import btf.main.Main;
import btf.objects.blocks.tiles.TileAccumulator;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class BlockAccumulator extends BlockBase implements ITileEntityProvider {

	public BlockAccumulator() {
		super("accumulator_impossibilium", Material.ROCK, Main.blocksTab, 2);
	}

	@Override
	public boolean hasTileEntity() {
		return true;
	}

	@Override
	public TileEntity createTileEntity(World world, IBlockState state) {
		return new TileAccumulator();
	}

	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta) {
		return new TileAccumulator();
	}

}
