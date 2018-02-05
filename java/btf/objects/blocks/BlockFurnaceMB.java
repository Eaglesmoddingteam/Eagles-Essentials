package btf.objects.blocks;


import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BlockFurnaceMB extends BlockBase {
	public static final IProperty<Boolean> isMaster = PropertyBool.create("ismaster");
	public BlockFurnaceMB(String name, Material materialIn) {
		super(name, materialIn, CreativeTabs.BUILDING_BLOCKS);
		setDefaultState(this.blockState.getBaseState().withProperty(isMaster, Boolean.valueOf(false)));
	}
	
	@Override
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn,
									EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
		BlockPos bpos = new BlockPos(pos.getX()-1, pos.getY()-1, pos.getZ());
		boolean right = true;
		if (worldIn.getBlockState(bpos).getBlock() == this) {
			for (int x= 0; x<=3; x++){
				if (right) {
					for (int y= 0; x<=3; x++) {
						for (int z= 0; x<=3; x++) {
							if (worldIn.getBlockState(new BlockPos(pos.getX()+x, pos.getY() +y, pos.getZ()+z)).getBlock() == this) {
							
							} else {
								right = false;
							}
						}
					}
				}	
			}
		} else if (worldIn.getBlockState(new BlockPos(pos.getX(), pos.getY()-1, pos.getZ()-1)).getBlock() == this) {
			for (int x= 0; x<=3; x++){
				if (right) {
					for (int y= 0; x<=3; x++) {
						for (int z= 0; x<=3; x++) {
							if (worldIn.getBlockState(new BlockPos(pos.getX()+x, pos.getY() +y, pos.getZ()+z)).getBlock() == this) {
							
							} else {
								right = false;
							}
						}
					}
				}	
			}
		}
		if (right) {
			worldIn.setBlockState(pos, this.blockState.getBaseState().withProperty(isMaster, Boolean.valueOf(true)));
		}
		return super.onBlockActivated(worldIn, pos, state, playerIn, hand, facing, hitX, hitY, hitZ);
	}
	
	@Override
	public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer,
			ItemStack stack) {
		super.onBlockPlacedBy(worldIn, pos, state, placer, stack);
	}
	
	@Override
	protected BlockStateContainer createBlockState() {
		return new BlockStateContainer(this, isMaster);
	}

	@Override
	public int getMetaFromState(IBlockState state) {
		if(state.getValue(isMaster).booleanValue())
			return 0;
		else
			return 1;
	}

	@Override
	public IBlockState getStateFromMeta(int meta) {
		return this.getDefaultState().withProperty(isMaster, meta == 0);
	}
}
