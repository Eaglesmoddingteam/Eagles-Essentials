package btf.objects.blocks;

import javax.annotation.Nonnull;

import btf.main.Main;
import btf.objects.blocks.tiles.TileHeater;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class BlockHeaterBase extends BlockBase implements ITileEntityProvider {

	public TEManager manager;

	public BlockHeaterBase(String name, @Nonnull TEManager tile) {
		super(name, Material.ROCK, Main.blocksTab, 2);
		this.manager = tile;
	}

	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta) {
		return manager.newTE();
	}

	@Override
	public boolean hasTileEntity() {
		return true;
	}

	private interface TEManager {
		public TileEntity newTE();
	}

	public static BlockHeaterBase withArgs(String name, int maxTransfer, int genRate, Item... burnables) {
		return new BlockHeaterBase(name, () -> new TileHeater(maxTransfer, genRate, burnables));
	}
}
