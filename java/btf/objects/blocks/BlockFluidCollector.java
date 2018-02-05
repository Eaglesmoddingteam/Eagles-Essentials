package btf.objects.blocks;

import btf.objects.blocks.tiles.TileBlockBreaker;
import btf.objects.blocks.tiles.TileFluidCollector;
import btf.objects.blocks.tiles.TileHarvesterTicker;
import btf.util.handlers.MachineHandler.MachineTypes;
import net.minecraft.block.Block;
import net.minecraft.block.BlockRedstoneWire;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;
import net.minecraftforge.fluids.FluidTank;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockFluidCollector extends BlockBase implements ITileEntityProvider {

	public BlockFluidCollector(String name, Material materialIn, CreativeTabs tab, int harvestlevel) {
		super(name, materialIn, tab, harvestlevel);
	}

	@Override
	public boolean isOpaqueCube(net.minecraft.block.state.IBlockState state) {
		return true;
	}

	public boolean isFullCube(IBlockState state) {
		return false;
	}

	@SideOnly(Side.CLIENT)
	@Override
	public net.minecraft.util.BlockRenderLayer getBlockLayer() {
		if (Minecraft.getMinecraft().isFancyGraphicsEnabled())
			return BlockRenderLayer.TRANSLUCENT;
		return BlockRenderLayer.CUTOUT_MIPPED;
	}

	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta) {
		return new TileFluidCollector();
	}

	@Override
	public boolean hasTileEntity(IBlockState state) {
		return true;
	}

	@Override
	public TileEntity createTileEntity(World world, IBlockState state) {
		return new TileFluidCollector();
	}

	@Override
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn,
			EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
		IFluidHandler t = worldIn.getTileEntity(pos).getCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY,
				null);
		FluidTank tank = (FluidTank) t;
		playerIn.sendStatusMessage(new TextComponentString("you stored " + tank.getFluidAmount() +" mb!!"), true);
		return super.onBlockActivated(worldIn, pos, state, playerIn, hand, facing, hitX, hitY, hitZ);
	}

	@Override
	public void neighborChanged(IBlockState stateIn, World worldIn, BlockPos pos, Block blockIn, BlockPos fromPos) {
		IBlockState state = worldIn.getBlockState(fromPos);
		if (state.getBlock() == Blocks.REDSTONE_WIRE) {
			BlockRedstoneWire neigbour = (BlockRedstoneWire) state.getBlock();
			if (state.getValue(neigbour.POWER) > 1) {
				TileEntity te = worldIn.getTileEntity(pos);
				TileFluidCollector fluidCollector = (TileFluidCollector) te;
				fluidCollector.activate();
			}
		}
	}
}
