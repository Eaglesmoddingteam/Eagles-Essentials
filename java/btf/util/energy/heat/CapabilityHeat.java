package btf.util.energy.heat;

import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.CapabilityManager;

public class CapabilityHeat {

	public static Capability<IHeatStorage> HEAT_CAPABILITY = null;

	@CapabilityInject(IHeatStorage.class)
	private static void capRegistered(Capability<IHeatStorage> cap) {
		HEAT_CAPABILITY = cap;
	}

	public static void register() {
		CapabilityManager.INSTANCE.register(IHeatStorage.class, new Capability.IStorage<IHeatStorage>() {

			@Override
			public NBTBase writeNBT(Capability<IHeatStorage> capability, IHeatStorage instance, EnumFacing side) {
				NBTTagCompound compound = new NBTTagCompound();
				compound.setInteger("heat", instance.getHeatstored());
				return compound;
			}

			@Override
			public void readNBT(Capability<IHeatStorage> capability, IHeatStorage instance, EnumFacing side,
					NBTBase nbt) {
				if (!(instance instanceof HeatStorage))
					return;
				NBTTagCompound tagCompound = (NBTTagCompound) nbt;
				HeatStorage storage = (HeatStorage) instance;
				int toAdd = tagCompound.getInteger("heat") - storage.getHeatstored();
				storage.recieveInternal(toAdd, false);
			}
		}, () -> new HeatStorage(20, 20, 1000));
	}
}
