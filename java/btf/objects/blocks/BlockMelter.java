package btf.objects.blocks;

import btf.main.Main;
import btf.objects.blocks.tiles.TileMelter;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class BlockMelter extends BlockBase implements ITileEntityProvider {

    public BlockMelter() {
        super("melter", Material.IRON, Main.blocksTab, 2);
    }

    /**
     * Returns a new instance of a block's tile entity class. Called on placing the block.
     *
     * @param worldIn
     * @param meta
     */
    @Nullable
    @Override
    public TileEntity createNewTileEntity(World worldIn, int meta) {
        return new TileMelter();
    }


}
