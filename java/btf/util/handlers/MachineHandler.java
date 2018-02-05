package btf.util.handlers;


import btf.init.ItemInit;
import btf.main.Main;
import btf.objects.blocks.Machine;
import btf.objects.blocks.tiles.TileAssembler;
import btf.objects.blocks.tiles.TileCrafterMachine;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.MoverType;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class MachineHandler {
	public static enum MachineTypes {
	     BLOCKBREAKER, FACTORYTABLE, HARVESTER, TELEPORTER, WOODENCASING, ASSEMBLER
	}
	private boolean initialized = false;
	private BlockPos breaking;
	private int dropx;
	private int dropy;
	private int dropz;
	
	public static void OnBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn,
										EnumHand hand, MachineTypes typeIn) {
		switch (typeIn) {
		case WOODENCASING: 
		{
			
			break;
		}
		case BLOCKBREAKER:
		{
			break;
		}
		case FACTORYTABLE:
		{
			if (playerIn.getActiveItemStack().getItem() == Items.DIAMOND_HOE) {
				//null
			}
			break;
		}
		case HARVESTER:
		{
			break;
		}
		case TELEPORTER:
		{
			if (!playerIn.isSneaking()) {
				BlockPos posNew = new BlockPos(pos.getX(), pos.getY()+1, pos.getZ());
				if(worldIn.getBlockState(posNew) == Blocks.AIR.getDefaultState()) {
					BlockPos playerPos = playerIn.getPosition().add(0, -1, 0);
					if (worldIn.getBlockState(playerPos) == state)
						playerIn.move(MoverType.SELF, 0, 1.0, 0);
					worldIn.setBlockToAir(pos);
					worldIn.setBlockState(posNew, state);
				
				}
				
			}else {
					BlockPos posNew = new BlockPos(pos.getX(), pos.getY()-1, pos.getZ());
					if(worldIn.getBlockState(posNew) == Blocks.AIR.getDefaultState()) {
					worldIn.setBlockToAir(pos);
					worldIn.setBlockState(posNew, state);
					}
			}
			}
		case ASSEMBLER:
		{
			ItemStack inv;
			inv = playerIn.getActiveItemStack().copy();
			inv.setCount(1);
			int meta=0;
			ItemStack[] acceptedItems = new ItemStack[] {
					new ItemStack(Items.DIAMOND_HOE),
					new ItemStack(ItemInit.copperGear),
					new ItemStack(Items.DIAMOND_PICKAXE),
					new ItemStack(ItemInit.ironGear)
			};
			for ( ItemStack invcheck:acceptedItems) {
				meta++;
				if(invcheck.getItem() == inv.getItem()) {
					TileEntity TileentityIn = worldIn.getTileEntity(pos);
					if(TileentityIn instanceof TileAssembler) {
						TileAssembler tileIn = (TileAssembler)TileentityIn;
						tileIn.setInv(inv, meta);
						playerIn.getActiveItemStack().shrink(1);
					}
				}
			}
			TileEntity TileentityIn = worldIn.getTileEntity(pos);
			if(TileentityIn instanceof TileAssembler) {
			TileAssembler tileIn = (TileAssembler)worldIn.getTileEntity(pos);
			tileIn.refresh();
			}
		}
			break;
		}	
	}
	

}