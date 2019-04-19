package btf.objects.blocks.tiles;


import btf.main.Main;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemStackHandler;

import javax.annotation.Nullable;

public class TileFurnaceMultiBlock extends TileEntity implements ITickable {

	private boolean hasMaster, isMaster;
	private int masterX, masterY, masterZ;

	private ItemStackHandler itemStackHandler = new ItemStackHandler(6) {
		@Override
		protected void onContentsChanged(int slot) {
			TileFurnaceMultiBlock.this.markDirty();
		}
	};

	@Override
	public void update() {
		if (!world.isRemote) {
			if (hasMaster()) {
				if (isMaster() && checkMultiBlockForm()) {
					//Do stuff
				}
			} else {
				if (checkMultiBlockForm())
					setupStructure();
			}
			if (isMaster())
				Main.LOGGER.info("I'm the master!: " + pos.toString());
		}
	}

	public boolean checkMultiBlockForm() {
		int i = 0;
		for (int x = pos.getX() - 1; x < pos.getX() + 2; x++) {
			for (int y = pos.getY(); y < pos.getY() + 3; y++) {
				for (int z = pos.getZ() - 1; z < pos.getZ() + 2; z++) {
					TileEntity tile = world.getTileEntity(new BlockPos(x, y, z));
					if (tile != null) {
						if (tile instanceof TileFurnaceMultiBlock) {
							if (this.isMaster()) {
								if (((TileFurnaceMultiBlock) tile).hasMaster())
									i++;
							} else if (!((TileFurnaceMultiBlock) tile).hasMaster())
								i++;
						}
					}
				}
			}
		}
		Main.LOGGER.info("I is : " + i);
		return i > 25 && world.isAirBlock(pos.add(0, 1, 0));
	}

	public void setupStructure() {
		for (int x = pos.getX() - 1; x < pos.getX() + 2; x++) {
			for (int y = pos.getY(); y < pos.getY() + 3; y++) {
				for (int z = pos.getZ() - 1; z < pos.getZ() + 2; z++) {
					TileEntity tile = world.getTileEntity(new BlockPos(x, y, z));
					boolean master = (x == pos.getX() && y == pos.getY() && z == pos.getZ());
					if (tile != null) {
						if (tile instanceof TileFurnaceMultiBlock) {
							((TileFurnaceMultiBlock) tile).setMasterCoords(pos.getX(), pos.getY(), pos.getZ());
							((TileFurnaceMultiBlock) tile).setHasMaster(true);
							((TileFurnaceMultiBlock) tile).setIsMaster(master);
						}
					}
				}
			}
		}
	}

	public void reset() {
		masterX = 0;
		masterY = 0;
		masterZ = 0;
		hasMaster = false;
		isMaster = false;
	}

	public boolean checkForMaster() {
		TileEntity tile = world.getTileEntity(new BlockPos(masterX, masterY, masterZ));
		return tile != null && tile instanceof TileFurnaceMultiBlock;
	}

	public void resetStructure() {
		for (int x = pos.getX() - 1; x < pos.getX() + 2; x++) {
			for (int y = pos.getY(); y < pos.getY() + 3; y++) {
				for (int z = pos.getZ() - 1; z < pos.getZ() + 2; z++) {
					TileEntity tile = world.getTileEntity(new BlockPos(x, y, z));
					if (tile != null) {
						if (tile instanceof TileFurnaceMultiBlock) {
							((TileFurnaceMultiBlock) tile).reset();
						}
					}
				}
			}
		}
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound) {
		compound.setInteger("masterX", masterX);
		compound.setInteger("masterY", masterY);
		compound.setInteger("masterZ", masterZ);
		compound.setBoolean("hasMaster", hasMaster);
		compound.setBoolean("isMaster", isMaster);
		compound.setTag("items", itemStackHandler.serializeNBT());
		if (hasMaster && isMaster) {
			//Save other data
		}
		return super.writeToNBT(compound);
	}

	@Override
	public void readFromNBT(NBTTagCompound compound) {
		super.readFromNBT(compound);
		if (compound.hasKey("items")) {
			itemStackHandler.deserializeNBT((NBTTagCompound) compound.getTag("items"));
		}
		masterX = compound.getInteger("masterX");
		masterY = compound.getInteger("masterY");
		masterZ = compound.getInteger("masterZ");
		hasMaster = compound.getBoolean("hasMaster");
		isMaster = compound.getBoolean("isMaster");
	}

	@Override
	public SPacketUpdateTileEntity getUpdatePacket() {
		NBTTagCompound nbt = new NBTTagCompound();
		this.writeToNBT(nbt);
		int metadata = getBlockMetadata();
		return new SPacketUpdateTileEntity(this.pos, metadata, nbt);
	}

	@Override
	public void onDataPacket(NetworkManager net, SPacketUpdateTileEntity pkt) {
		this.readFromNBT(pkt.getNbtCompound());
	}

	@Override
	public NBTTagCompound getUpdateTag() {
		NBTTagCompound nbt = new NBTTagCompound();
		this.writeToNBT(nbt);
		return nbt;
	}

	@Override
	public void handleUpdateTag(NBTTagCompound tag) {
		this.readFromNBT(tag);
	}

	public boolean hasMaster() {
		return hasMaster;
	}

	public boolean isMaster() {
		return isMaster;
	}

	public int getMasterX() {
		return masterX;
	}

	public int getMasterY() {
		return masterY;
	}

	public int getMasterZ() {
		return masterZ;
	}

	public void setHasMaster(boolean hasMaster) {
		this.hasMaster = hasMaster;
	}

	public void setIsMaster(boolean isMaster) {
		this.isMaster = isMaster;
	}

	public void setMasterCoords(int x, int y, int z) {
		masterX = x;
		masterY = y;
		masterZ = z;
	}

	@Override
	public boolean hasCapability(Capability<?> capability, @Nullable EnumFacing facing) {
		if (capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
			return true;
		}
		return super.hasCapability(capability, facing);
	}

	@Nullable
	@Override
	public <T> T getCapability(Capability<T> capability, @Nullable EnumFacing facing) {
		if (capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
			return CapabilityItemHandler.ITEM_HANDLER_CAPABILITY.cast(itemStackHandler);
		}
		return super.getCapability(capability, facing);
	}
}
