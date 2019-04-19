package btf.objects.blocks.tiles;

import btf.main.Main;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemDye;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidTank;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler;

import java.util.Random;

public class TileShower extends TileEntity {

	Capability<IFluidHandler> fluidHandler = CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY;
	Random r = new Random();
	BlockPos start;
	BlockPos growing;
	boolean initialized = false;
	int startX, startY, startZ;
	private FluidTank storage = new FluidTank(FluidRegistry.WATER, 0, 3000);

	public void fire(World worldIn) {
		if (!initialized)
			init();
		if (canShower()) {
			Shower();
			EntityPlayer player = worldIn.getClosestPlayer(pos.getX(), pos.getY(), pos.getZ(), 100, false);
			World world = !player.isServerWorld() ? player.world : worldIn;
			world.spawnParticle(EnumParticleTypes.DRIP_WATER, false, pos.getX(), pos.getY(), pos.getZ(), min(pos.getX(), growing.getX()), -3, min(pos.getZ(), growing.getZ()), 1);
		}
	}

	private void init() {
		int X = pos.getX();
		int Y = pos.getY();
		int Z = pos.getZ();
		startX = X - 1;
		startY = Y - 3;
		startZ = Z - 1;
		initialized = true;
		Main.LOGGER.info("" + startX + " " + startY + " " + startZ);
	}

	@Override
	public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
		return capability == fluidHandler ? (T) storage : null;
	}

	@Override
	public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
		return capability == fluidHandler;
	}

	private void Shower() {
		storage.drainInternal(200, true);
		growing = new BlockPos(startX + r.nextInt(3), startY, startZ + r.nextInt(3));
		ItemDye.applyBonemeal(new ItemStack(Items.DYE, 0), world, growing, null, null);
	}

	private boolean canShower() {
		return storage.getFluidAmount() > 200;
	}

	@Override
	public void readFromNBT(NBTTagCompound compound) {
		storage.readFromNBT(compound);
		super.readFromNBT(compound);
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound) {
		storage.writeToNBT(compound);
		return super.writeToNBT(compound);
	}

	private int min(int i, int i1) {
		return i - i1;
	}
}
