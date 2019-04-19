package btf.objects.blocks.tiles;

import btf.init.BlockInit;
import btf.init.ItemInit;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;

public class TileAssembler extends TileEntity {
	public ItemStack[] inv = new ItemStack[3];

	public ItemStack getInv(int meta) {
		return this.inv[meta];
	}

	public void setInv(ItemStack inv, int meta) {
		this.inv[meta] = inv;
	}

	public void refresh() {
		if (inv[0].getItem() == Items.DIAMOND_HOE && inv[1].getItem() == ItemInit.copperGear) {
			if (world.getBlockState(pos.add(0, 1, 0)).getBlock() == BlockInit.casingWooden) {
				world.setBlockState(pos.add(0, 1, 0), BlockInit.harvester.getDefaultState());
				inv[0] = null;
				inv[1] = null;
			}
		} else if (inv[2].getItem() == Items.DIAMOND_PICKAXE && inv[3].getItem() == ItemInit.ironGear) {
			if (world.getBlockState(pos.add(0, 1, 0)).getBlock() == BlockInit.casingWooden) {
				world.setBlockState(pos.add(0, 1, 0), BlockInit.blockbreaker.getDefaultState());
				inv[2] = null;
				inv[3] = null;
			}
		}

	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound) {
		// TODO Auto-generated method stub
		return super.writeToNBT(compound);
	}

	@Override
	public void readFromNBT(NBTTagCompound compound) {
		// TODO Auto-generated method stub
		super.readFromNBT(compound);
	}


}
