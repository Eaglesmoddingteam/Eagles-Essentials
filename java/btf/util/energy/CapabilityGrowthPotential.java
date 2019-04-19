package btf.util.energy;


import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagInt;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.CapabilityManager;

public class CapabilityGrowthPotential {

	@CapabilityInject(IGrowthPotentialStorage.class)
	public static Capability<IGrowthPotentialStorage> GROWTHPOTENTIAL = null;

	public static void register() {
		CapabilityManager.INSTANCE.register(IGrowthPotentialStorage.class, new Capability.IStorage<IGrowthPotentialStorage>() {
					@Override
					public NBTBase writeNBT(Capability<IGrowthPotentialStorage> capability, IGrowthPotentialStorage instance, EnumFacing side) {
						return new NBTTagInt(instance.getGPStored());
					}

					@Override
					public void readNBT(Capability<IGrowthPotentialStorage> capability, IGrowthPotentialStorage instance, EnumFacing side, NBTBase nbt) {
						if (!(instance instanceof GrowthPotentialStorage))
							throw new IllegalArgumentException("Can not deserialize to an instance that isn't the default implementation");
						((GrowthPotentialStorage) instance).growthPotential = ((NBTTagInt) nbt).getInt();
					}
				},
				() -> new GrowthPotentialStorage(1000));
	}
}
