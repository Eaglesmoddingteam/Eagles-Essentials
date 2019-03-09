package btf.objects.blocks.tiles.heaters;

import btf.client.TESRHeater;
import btf.main.Main;
import btf.objects.blocks.BlockHeaterBase;
import btf.objects.blocks.tiles.TileHeater;
import btf.packet.MessageRequestUpdate;
import btf.packet.MessageUpdateTE;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class HeaterEnder extends TileHeater {

	public HeaterEnder() {
		super();
	}
	
	@Override
	public int getMaxout() {
		return 160;
	}

	@Override
	public int getGenRate() {
		return 80;
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
		Item item = stack.getItem();
		if (item == Items.ENDER_PEARL) {
			return 300;
		}

		if (item == Items.ENDER_EYE) {
			return 1000;
		}

		if (item == Item.getItemFromBlock(Blocks.CHORUS_PLANT)) {
			return 1700;
		}
		this.burning = ItemStack.EMPTY;
		markDirty();
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

	public static TESRHeater<HeaterEnder> TESR = new TESRHeater<>();

	public static final Block BLOCK = new BlockHeaterBase("heater_ender") {

		@Override
		public TileEntity createNewTileEntity(World worldIn, int meta) {
			return new HeaterEnder();
		}

	};
}
