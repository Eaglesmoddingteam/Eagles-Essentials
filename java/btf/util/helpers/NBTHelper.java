package btf.util.helpers;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.common.registry.ForgeRegistries;

public class NBTHelper {

	@Nullable
	public static NBTBase toNBT(@Nonnull Object o) {
		if(o instanceof Item) {
			return toNBT((Item) o);
		}
		if(o instanceof Block) {
			return toNBT((Block) o);
		}
		if(o instanceof BlockPos) {
			return toNBT((BlockPos) o);
		}
		return null;
	}
	
	private static NBTBase toNBT(Item i) {
		NBTTagCompound compound = new NBTTagCompound();
		ResourceLocation location = ForgeRegistries.ITEMS.getKey(i);
		compound.setString("modid", location.getResourceDomain());
		compound.setString("resourcename", location.getResourcePath());
		byte type = 0;
		compound.setByte("type", type);
		return compound;
	}
	
	private static NBTBase toNBT(BlockPos pos) {
		NBTTagCompound compound = new NBTTagCompound();
		compound.setIntArray("pos", new int[]{
			pos.getX(),
			pos.getY(),
			pos.getZ()
		});
		byte type = 2;
		compound.setByte("type", type);
		return compound;
	}
	
	private static NBTBase toNBT(Block b) {
		NBTTagCompound compound = new NBTTagCompound();
		ResourceLocation location = ForgeRegistries.BLOCKS.getKey(b);
		compound.setString("modid", location.getResourceDomain());
		compound.setString("resourcename", location.getResourcePath());
		byte type = 1;
		compound.setByte("type", type);
		return compound;
	}
	
	@Nullable
	public static Object fromNBT(@Nonnull NBTTagCompound compound) {
		switch(compound.getByte("type")) {
		case 0:
			return ForgeRegistries.ITEMS.getValue(new ResourceLocation(compound.getString("modid"), compound.getString("resourcename")));
		case 1:
			return ForgeRegistries.BLOCKS.getValue(new ResourceLocation(compound.getString("modid"), compound.getString("resourcename")));
		case 2:
			int[] pos = compound.getIntArray("pos");
			return new BlockPos(pos[0], pos[1], pos[2]);
		default:
			return null;
		}
	}
	
}
