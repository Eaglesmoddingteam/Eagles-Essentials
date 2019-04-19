package btf.objects.blocks;

import btf.main.Main;
import btf.objects.blocks.tiles.TileAssembler;
import btf.objects.blocks.tiles.TileBlockBreaker;
import btf.objects.blocks.tiles.TileCrafterMachine;
import btf.objects.blocks.tiles.TileHarvesterTicker;
import btf.util.handlers.MachineHandler;
import btf.util.handlers.MachineHandler.MachineTypes;
import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class Machine extends BlockBase implements ITileEntityProvider {
	public static final PropertyDirection FACING = PropertyDirection.create("facing");
	MachineHandler.MachineTypes typeIn;

	public Machine(String name, Material materialIn, CreativeTabs tab, MachineHandler.MachineTypes machinetype) {
		super(name, materialIn, tab);
		typeIn = machinetype;
		this.setDefaultState(this.blockState.getBaseState().withProperty(FACING, EnumFacing.NORTH));
	}

	/**
	 * EXTREMELY DEPRECATED use (String name, Material materialIn, CreativeTabs tab,
	 * MachineTypes machinetype) instead
	 **/
	@Deprecated
	public Machine(String name, Material materialIn, CreativeTabs tab) {

		super(name, materialIn, tab);
		Main.LOGGER.warn("Registering Machines Without a MACHINETYPE!! this is ABSOLUTELY Deprecated");
	}

	@Override
	public boolean canConnectRedstone(IBlockState state, IBlockAccess world, BlockPos pos, EnumFacing side) {
		switch (typeIn) {
			case FACTORYTABLE: {
				return side == EnumFacing.EAST || side == EnumFacing.WEST || side == EnumFacing.NORTH || side == EnumFacing.SOUTH;
			}
			case BLOCKBREAKER:
				return side == EnumFacing.DOWN;

			case HARVESTER:
				return side == EnumFacing.DOWN;

			case TELEPORTER:
				return false;

			case WOODENCASING:
				return false;

			case ASSEMBLER:
				return false;

		}
		return false;
	}

	@Override
	public TileEntity createTileEntity(World world, IBlockState state) {
		switch (typeIn) {
			case WOODENCASING: {
				break;
			}
			case BLOCKBREAKER: {
				return new TileBlockBreaker();
			}
			case FACTORYTABLE: {
				return new TileCrafterMachine();
			}
			case HARVESTER: {
				return new TileHarvesterTicker();
			}
			case TELEPORTER: {
				break;
			}
			case ASSEMBLER: {
				return new TileAssembler();
			}
		}
		return super.createTileEntity(world, state);
	}

	@Override
	public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer,
	                            ItemStack stack) {
		if (this.typeIn == MachineHandler.MachineTypes.HARVESTER
				|| this.typeIn == MachineHandler.MachineTypes.BLOCKBREAKER) {
			worldIn.setBlockState(pos, this.getBlockState().getBaseState().withProperty(FACING,
					placer.getHorizontalFacing().getOpposite()));
		}
		super.onBlockPlacedBy(worldIn, pos, state, placer, stack);
	}

	@Override
	protected BlockStateContainer createBlockState() {
		return new BlockStateContainer(this, FACING);
	}

	@Override
	public int getMetaFromState(IBlockState state) {
		return state.getValue(FACING).getIndex();
	}

	@Override
	public IBlockState getStateFromMeta(int meta) {
		return this.getDefaultState().withProperty(FACING, EnumFacing.getFront(meta & 7));
	}

	@Override
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn,
	                                EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
		MachineHandler.OnBlockActivated(worldIn, pos, state, playerIn, hand, typeIn);
		return super.onBlockActivated(worldIn, pos, state, playerIn, hand, facing, hitX, hitY, hitZ);
	}

	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta) {
		if (typeIn == MachineHandler.MachineTypes.BLOCKBREAKER)
			return new TileBlockBreaker();
		if (typeIn == MachineHandler.MachineTypes.HARVESTER)
			return new TileHarvesterTicker();
		if (typeIn == MachineTypes.ASSEMBLER)
			return new TileAssembler();
		return null;
	}

	@Override
	public void neighborChanged(IBlockState stateIn, World worldIn, BlockPos pos, Block blockIn, BlockPos fromPos) {
		IBlockState state = worldIn.getBlockState(fromPos);
		if (worldIn.isBlockPowered(pos)) {
			TileEntity te = worldIn.getTileEntity(pos);
			if (typeIn == MachineTypes.HARVESTER) {
				TileHarvesterTicker tec = (TileHarvesterTicker) te;
				tec.scan();
			} else if (typeIn == MachineTypes.BLOCKBREAKER) {
				TileBlockBreaker tec = (TileBlockBreaker) te;
				tec.scan();
			}
		}
	}
}
