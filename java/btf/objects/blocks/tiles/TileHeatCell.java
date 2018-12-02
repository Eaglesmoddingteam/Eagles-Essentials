package btf.objects.blocks.tiles;

import btf.main.Main;
import btf.util.energy.heat.CapabilityHeat;
import btf.util.energy.heat.HeatStorage;
import btf.util.energy.heat.IHeatStorage;
import net.minecraft.init.Blocks;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.common.capabilities.Capability;

public class TileHeatCell extends TileEntity implements ITickable {

	int tick = 0;
	BlockPos[] surroundings;
	boolean initialized = false;
	Capability<IHeatStorage> HEAT_CAPABILITY = CapabilityHeat.HEAT_CAPABILITY;
	HeatStorage HEAT_STORAGE = new HeatStorage(20, 20, 2000);

	@Override
	public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
		return capability == HEAT_CAPABILITY;
	}

	@Override
	public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
		return capability == HEAT_CAPABILITY ? (T) HEAT_STORAGE : null;
	}

	private void init() {
		this.initialized = true;
		this.surroundings = new BlockPos[] { this.pos.west(), this.pos.east(), this.pos.north(), this.pos.south(),
				this.pos.down(), this.pos.up() };
	}

	@Override
	public void update() {
		if (!initialized)
			init();

		tick++;
		if ((tick % 10) == 0) {
			if(this.HEAT_STORAGE.getHeatstored() > 0)
			for (BlockPos pos : surroundings) {
				if (canburn(pos))
					world.setBlockState(pos, Blocks.FIRE.getDefaultState());
			}
		}

		if (tick >= 100) {
			tick = 0;
			System.out.println("The HeatCell at" + pos.toString() + "has decayed "
					+ this.HEAT_STORAGE.extractHeat(1, false) + " heat from itsself");
		}
	}
	
	private boolean canburn(BlockPos pos) {
		return (!world.isAirBlock(pos.down(1))) || world.isAirBlock(pos);
	}

	public int getHeat() {
		// TODO Auto-generated method stub
		return HEAT_STORAGE.getHeatstored();
	}
}
