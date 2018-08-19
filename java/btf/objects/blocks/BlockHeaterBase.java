package btf.objects.blocks;

import btf.main.Main;
import btf.util.blockstate.entries.BlockStateVariantHeater;
import btf.util.blockstate.registry.EntryManager;
import btf.util.helpers.block.ISlab;
import btf.util.obj.HeaterType;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public abstract class BlockHeaterBase extends BlockBase implements ITileEntityProvider, ISlab{
	
	public BlockHeaterBase(HeaterType type) {
		super("Block_heater_" + EntryManager.getEntryByClass(BlockStateVariantHeater.class).getKeyForType(type) , Material.ROCK, Main.blocksTab, 2);
	}
	
	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta) {
		return null;
	}
	
	@Override
	public boolean hasTileEntity() {
		return true;
	}
	
	@Override
	public TileEntity createTileEntity(World world, IBlockState state) {
		return super.createTileEntity(world, state);
	}

}
