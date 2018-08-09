package btf.util.energy.heat;

import btf.util.energy.GrowthPotentialStorage;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.CapabilityManager;

public class CapabilityHeat {
	
	@CapabilityInject(IHeatStorage.class)
    public static Capability<IHeatStorage> HEAT_CAPABILITY = null;
	
	public static void register() {
		CapabilityManager.INSTANCE.register(IHeatStorage.class, new Capability.IStorage<IHeatStorage>() {

			@Override
			public NBTBase writeNBT(Capability<IHeatStorage> capability, IHeatStorage instance, EnumFacing side) {
				return null;
			}

			@Override
			public void readNBT(Capability<IHeatStorage> capability, IHeatStorage instance, EnumFacing side,
					NBTBase nbt) {
				
			}
		}, () -> new HeatStorage(20, 20, 1000));
	}
}
