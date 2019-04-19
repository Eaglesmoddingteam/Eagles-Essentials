package btf.objects.blocks.tiles.heaters;

import btf.client.TESRHeater;
import btf.main.Main;
import btf.objects.blocks.BlockHeaterBase;
import btf.objects.blocks.tiles.TileHeater;
import btf.packet.MessageRequestUpdate;
import btf.packet.MessageUpdateTE;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class HeaterBlazeing extends TileHeater {

	public static final Block BLOCK = new BlockHeaterBase("heater_blazeing") {

		@Override
		public TileEntity createNewTileEntity(World worldIn, int meta) {
			return new HeaterBlazeing();
		}

		public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn,
		                                EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
			HeaterBlazeing heater = (HeaterBlazeing) worldIn.getTileEntity(pos);
			if (heater == null) {
				return false;
			}
			if (!worldIn.isRemote) {
				ItemStack stack = playerIn.getHeldItem(hand);
				ItemStack inv = heater.inventory.getStackInSlot(0);
				if (stack.isEmpty()) {
					ItemStack extracted = heater.inventory.extractItem(0, playerIn.isSneaking() ? inv.getCount() : 1,
							false);
					if (!playerIn.inventory.addItemStackToInventory(extracted)) {
						InventoryHelper.spawnItemStack(worldIn, playerIn.posX, playerIn.posY, playerIn.posZ, extracted);
					}
				} else {
					if (stack.isItemEqual(inv) || inv.isEmpty()) {
						if (inv.getMaxStackSize() >= inv.getCount() + stack.getCount()) {
							if (playerIn.isSneaking()) {
								heater.inventory.insertItem(0, stack.copy(), false);
								stack.setCount(0);
							} else {
								ItemStack copy = stack.copy();
								copy.setCount(1);
								stack.shrink(1);
								heater.inventory.insertItem(0, copy, false);

							}
						} else {
							int left = inv.getMaxStackSize() - inv.getCount();
							ItemStack copy = stack.copy();
							copy.setCount(left);
							stack.shrink(left);
							heater.inventory.insertItem(0, copy, false);
						}
					} else {
						return false;
					}
				}
			}
			return true;
		}

	};
	public static TESRHeater<HeaterBlazeing> TESR = new TESRHeater<>();

	public HeaterBlazeing() {
		super();
	}

	@Override
	public int getMaxout() {
		return 200;
	}

	@Override
	public int getGenRate() {
		return 10;
	}

	@Override
	public void useFuel() {
		ItemStack stack = inventory.getStackInSlot(0);
		this.burning = stack.copy();
		burning.setCount(1);
		stack.shrink(1);
		markDirty();
	}

	@Override
	public int getFuel() {
		ItemStack stack = inventory.getStackInSlot(0);
		if (stack.getItem() == Items.BLAZE_ROD) {
			return 900;
		}
		if (stack.getItem() == Items.BLAZE_POWDER) {
			return 300;
		}
		if (stack.getItem() == Items.FIRE_CHARGE) {
			return 1000;
		}
		if (!burning.isEmpty()) {
			burning = ItemStack.EMPTY;
			markDirty();
		}

		return 0;
	}

	@Override
	public void markDirty() {
		if (!world.isRemote)
			Main.NETWORK.sendToAll(new MessageUpdateTE(writeToNBT(new NBTTagCompound())));
		super.markDirty();
	}

	@Override
	public void onLoad() {
		super.onLoad();
		if (pos != null) {
			Main.NETWORK.sendToServer(new MessageRequestUpdate(pos));
		}
	}
}