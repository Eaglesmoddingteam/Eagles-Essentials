package btf.objects.blocks;

import java.util.ArrayList;

import btf.main.Main;
import btf.objects.blocks.tiles.TileHeater;
import btf.util.blockstate.entries.BlockStateVariantHeater;
import btf.util.blockstate.registry.EntryManager;
import btf.util.helpers.block.EnumSlabTypes;
import btf.util.helpers.block.ISlab;
import btf.util.obj.HeaterType;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

public class BlockHeaterBase extends BlockBase implements ITileEntityProvider, ISlab{
	
	HeaterType type;
	
	public BlockHeaterBase(HeaterType type) {
		super("Block_heater_" + EntryManager.getEntryByClass(BlockStateVariantHeater.class).getKeyForType(type) , Material.ROCK, Main.blocksTab, 2);
		this.type = type;
	}
	
	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta) {
		return new TileHeater(type);
	}
	
	@Override
	public boolean hasTileEntity() {
		return true;
	}
	
	@Override
	public TileEntity createTileEntity(World world, IBlockState state) {
		return new TileHeater(type);
	}

	@Override
	public boolean isCompatibleWith(ISlab slab) {
		return false;
	}

	@Override
	public Enum<? extends EnumSlabTypes> getInstanceSlabTypes() {
		return null;
	}

	@Override
	public ArrayList<ResourceLocation> getTextures() {
		return null;
	}

}
