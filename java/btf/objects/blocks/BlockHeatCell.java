package btf.objects.blocks;

import btf.main.Main;
import btf.objects.blocks.tiles.TileHeatCell;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockHeatCell extends BlockBase implements ITileEntityProvider {

	public BlockHeatCell() {
		super("heatcell", Material.ROCK, Main.blocksTab, 2);
	}

	@Override
	public boolean hasTileEntity() {
		return true;
	}

	@Override
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn,
			EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
		if (worldIn.isRemote)
			return false;
		TileEntity t = worldIn.getTileEntity(pos);
		if (t == null)
			return false;
		if (t instanceof TileHeatCell)
			playerIn.sendStatusMessage(
					new TextComponentString(String.format("%d energy Stored!!", ((TileHeatCell) t).getHeat())), true);
		return super.onBlockActivated(worldIn, pos, state, playerIn, hand, facing, hitX, hitY, hitZ);
	}

	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta) {
		return new TileHeatCell();
	}
}
