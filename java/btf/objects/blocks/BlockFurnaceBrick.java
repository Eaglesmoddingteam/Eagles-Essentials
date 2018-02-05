package btf.objects.blocks;


import btf.main.Main;
import btf.objects.blocks.tiles.TileFurnaceMultiBlock;
import btf.util.handlers.GuiHandler;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class BlockFurnaceBrick extends BlockBase implements ITileEntityProvider {

    public BlockFurnaceBrick(String name, Material materialIn, CreativeTabs tab) {
        super(name, materialIn, tab);
    }

    @Override
    public void onNeighborChange(IBlockAccess world, BlockPos pos, BlockPos neighbor) {
        TileEntity tile = world.getTileEntity(pos);
        if (tile != null) {
            if (tile instanceof TileFurnaceMultiBlock) {
                TileFurnaceMultiBlock multiBlock = (TileFurnaceMultiBlock) tile;
                if (multiBlock.hasMaster()) {
                    if (multiBlock.isMaster()) {
                        if (!multiBlock.checkMultiBlockForm())
                            multiBlock.resetStructure();
                    } else {
                        if (!multiBlock.checkForMaster()) {
                            TileFurnaceMultiBlock master = (TileFurnaceMultiBlock)
                                    world.getTileEntity(new BlockPos(multiBlock.getMasterX(), multiBlock.getMasterY(),
                                            multiBlock.getMasterZ()));
                            if (master != null)
                                master.resetStructure();
                        }
                    }
                }
            }
        }
        super.onNeighborChange(world, pos, neighbor);
    }

    @Nullable
    @Override
    public TileEntity createNewTileEntity(World worldIn, int meta) {
        return new TileFurnaceMultiBlock();
    }

    @Nullable
    @Override
    public TileEntity createTileEntity(World world, IBlockState state) {
        return new TileFurnaceMultiBlock();
    }

    @Override
    public boolean hasTileEntity() {
        return true;
    }

    @Override
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        TileEntity tile = worldIn.getTileEntity(pos);
        if (tile != null) {
            if (tile instanceof TileFurnaceMultiBlock) {
                TileFurnaceMultiBlock furnaceMultiBlock = (TileFurnaceMultiBlock) tile;
                if (furnaceMultiBlock.hasMaster()) {
                    if (furnaceMultiBlock.isMaster()) {
                        if (!worldIn.isRemote && !playerIn.isSneaking())
                            playerIn.openGui(Main.instance, GuiHandler.furnace, worldIn,
                                    pos.getX(), pos.getY(), pos.getZ());
                    } else {
                        TileFurnaceMultiBlock master = (TileFurnaceMultiBlock)
                                worldIn.getTileEntity(new BlockPos(furnaceMultiBlock.getMasterX(),
                                        furnaceMultiBlock.getMasterY(), furnaceMultiBlock.getMasterZ()));
                        if (master != null) {
                            if (!worldIn.isRemote && !playerIn.isSneaking())
                                playerIn.openGui(Main.instance, GuiHandler.furnace, worldIn,
                                        master.getPos().getX(), master.getPos().getY(), master.getPos().getZ());
                        }
                    }
                }
            }
        }
        return super.onBlockActivated(worldIn, pos, state, playerIn, hand, facing, hitX, hitY, hitZ);
    }
}
