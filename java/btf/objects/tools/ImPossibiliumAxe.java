package btf.objects.tools;


import btf.main.Main;
import com.google.common.collect.Sets;
import net.minecraft.block.Block;
import net.minecraft.block.BlockLog;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemTool;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.Set;

public class ImPossibiliumAxe extends ItemTool {
	private static final Set<Block> EFFECTIVE_ON = Sets.newHashSet(Blocks.PLANKS, Blocks.BOOKSHELF, Blocks.LOG, Blocks.LOG2, Blocks.CHEST, Blocks.PUMPKIN, Blocks.LIT_PUMPKIN, Blocks.MELON_BLOCK, Blocks.LADDER, Blocks.WOODEN_BUTTON, Blocks.WOODEN_PRESSURE_PLATE);

	public ImPossibiliumAxe(String name, ToolMaterial material) {
		super(material, EFFECTIVE_ON);
		setUnlocalizedName(name);
		setRegistryName(name);
		setCreativeTab(Main.itemstab);
	}

	@Override
	public boolean onBlockDestroyed(ItemStack stack, World worldIn, IBlockState state, BlockPos pos,
	                                EntityLivingBase entityLiving) {
		super.onBlockDestroyed(stack, worldIn, state, pos, entityLiving);
		int y = 0;
		IBlockState BROKEN = null;
		BlockPos nextBlock = new BlockPos(pos.getX(), pos.getY() + 1 + y, pos.getZ());
		while (worldIn.getBlockState(nextBlock).getBlock() instanceof BlockLog) {
			IBlockState OLD = worldIn.getBlockState(nextBlock);
			if (worldIn.getBlockState(nextBlock) != BROKEN)
				BROKEN = worldIn.getBlockState(nextBlock);
			worldIn.setBlockToAir(nextBlock);
			y++;
			nextBlock = new BlockPos(pos.getX(), pos.getY() + 1 + y, pos.getZ());
		}
		if (BROKEN != null && y >= 1) {
			ItemStack dropstack = new ItemStack(Item.getItemFromBlock(BROKEN.getBlock()), y, BROKEN.getBlock().getMetaFromState(BROKEN));
			if (!worldIn.isRemote) {
				worldIn.spawnEntity(new EntityItem(worldIn, pos.getX(), pos.getY(), pos.getZ(), dropstack));
			}
		}
		return true;
	}
}