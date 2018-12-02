package btf.objects.blocks.tiles;

import static btf.util.helpers.ArrayHelper.includes;
import static btf.util.helpers.NBTHelper.*;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nullable;

import btf.util.energy.heat.CapabilityHeat;
import btf.util.energy.heat.HeatStorage;
import btf.util.energy.heat.IHeatStorage;
import net.minecraft.item.Item;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;

public class TileHeater extends TileEntity implements ITickable {

	static public Capability<IItemHandler> ITEM_HANDLER = CapabilityItemHandler.ITEM_HANDLER_CAPABILITY;
	Capability<IHeatStorage> HEAT = CapabilityHeat.HEAT_CAPABILITY;
	IItemHandler inventory = new ItemStackHandler(1);
	HeatStorage heat;
	int burntimeLeft = 0;
	int genRate;
	int maxout;
	private Item[] burnables;

	public TileHeater() {
		
	}
	
	public TileHeater(int maxout, int genRate, Item... burnables) {
		this.heat = new HeatStorage(0, maxout, 4000);
		this.maxout = maxout;
		this.genRate = genRate;
		this.burnables = burnables;
	}

	@Override
	public void update() {
		if (isBurning()) {
			if (canBurn()) {
				this.heat.recieveInternal(genRate, false);
			}
		}
		tryBurn();
		if (!heat.isEmpty()) {
			exportEnergy(heat.getHeatstored());
		}
	}

	private void exportEnergy(int energyStored) {
		int out = 0;
		for (EnumFacing face : EnumFacing.VALUES) {
			TileEntity tile = world.getTileEntity(pos.offset(face, 1));
			if (tile != null) {
				if (tile.hasCapability(HEAT, face.getOpposite()) && out < maxout) {
					IHeatStorage storage = tile.getCapability(HEAT, face.getOpposite());
					int maxOut = heat.extractHeat(maxout - out, true);
					int maxAccept = storage.receiveHeat(maxOut, false);
					out += heat.extractHeat(maxAccept, false);
				}
			}
		}
	}

	private boolean canBurn() {
		return this.heat.getHeatstored() + genRate < 4000;
	}

	private void tryBurn() {
		if (!inventory.getStackInSlot(0).isEmpty()) {
			if (includes(burnables, inventory.getStackInSlot(0).getItem())) {
				this.burntimeLeft = 1000;
				this.inventory.getStackInSlot(0).shrink(1);
			}
		}
	}

	private boolean isBurning() {
		return burntimeLeft > 0;
	}

	@Nullable
	@Override
	public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
		if (capability == ITEM_HANDLER) {
			return (T) this.inventory;
		} else if (capability == HEAT) {
			return (T) this.heat;
		}
		return null;
	}

	@Override
	public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
		return capability == ITEM_HANDLER || capability == HEAT;
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound) {
		compound.setIntArray("data", genDataArr());
		compound.setTag("burnables", genBurnablesTag());
		compound.setTag("heat", HEAT.writeNBT(heat, EnumFacing.DOWN));
		compound.setTag("inv", ITEM_HANDLER.writeNBT(inventory, EnumFacing.DOWN));
		return super.writeToNBT(compound);
	}

	private NBTBase genBurnablesTag() {
		NBTTagCompound compound = new NBTTagCompound();
		compound.setInteger("length", burnables.length);
		int amount = 0;
		for (Item i : burnables) {
			compound.setTag(String.format("item_%d", amount++), toNBT(i));
		}
		return compound;
	}

	private int[] genDataArr() {
		return new int[] { maxout, genRate, burntimeLeft };
	}

	@Override
	public void readFromNBT(NBTTagCompound compound) {
		readDataArr(compound.getIntArray("data"));
		readBurnablesTag(compound.getTag("burnables"));
		heat = new HeatStorage(0, maxout, 4000);
		HEAT.readNBT(heat, EnumFacing.DOWN, compound.getTag("heat"));
		ITEM_HANDLER.readNBT(inventory, EnumFacing.DOWN, compound.getTag("inv"));
		super.readFromNBT(compound);
	}

	private void readBurnablesTag(NBTBase tag) {
		NBTTagCompound compound = (NBTTagCompound) tag;
		List<Item> items = new ArrayList();
		for (int i = 0; i < compound.getInteger("length"); i++) {
			items.add((Item) fromNBT((NBTTagCompound) compound.getTag(String.format("item_%d", i))));
		}
	}

	private void readDataArr(int[] arr) {
		this.maxout = arr[0];
		this.genRate = arr[1];
		this.burntimeLeft = arr[2];
	}

}
