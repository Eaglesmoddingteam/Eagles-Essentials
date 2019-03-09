package btf.objects.tools;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.Nullable;

import btf.main.Main;
import btf.objects.entity.EntityHeatBall;
import btf.objects.items.ItemBase;
import btf.util.energy.heat.CapabilityHeat;
import btf.util.energy.heat.HeatStorage;
import btf.util.energy.heat.IHeatStorage;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;

public class ItemMeltingTool extends ItemBase {

	static Capability<IHeatStorage> HEAT = CapabilityHeat.HEAT_CAPABILITY;

	public enum Mode {

		AUTO_MELT, FAST, NORMAL, INSANE, OFF;

		public String getDisplaySentence() {
			switch (this) {
			case AUTO_MELT:
				return "integrated furnace!";
			case FAST:
				return "not that fast";
			case INSANE:
				return "almost insane, but yet closer to pretty OK";
			case NORMAL:
				return "would like to call it 'normal', but it's rather slow";
			case OFF:
				return "don't feel like working today";
			default:
				return "nothin' really, what you up to?";
			}
		}

		public int getSpeedModifier() {
			switch (this) {
			case AUTO_MELT:
			case NORMAL:
				return 1;
			case FAST:
				return 2;
			case INSANE:
				return 5;
			default:
			case OFF:
				return 0;
			}
		}

		public static Mode get(ItemStack stack) {
			if (stack.isEmpty() || !stack.hasTagCompound()) {
				return Mode.OFF;
			} else {
				NBTTagCompound compound = stack.getTagCompound();
				if (!compound.hasKey("mode")) {
					return Mode.OFF;
				}
				String name = compound.getString("mode");
				try {
					return Mode.valueOf(name.toUpperCase());
				} catch (IllegalArgumentException e) {
					Main.LOGGER.catching(e);
					return Mode.OFF;
				}
			}
		}
	}

	public ItemMeltingTool() {
		super("melting_tool", 1, Main.itemstab);
	}
	
	@Override
	public boolean onBlockStartBreak(ItemStack itemstack, BlockPos pos, EntityPlayer player) {
		if(Mode.get(itemstack) == Mode.AUTO_MELT) {
			handleCustomBreak(itemstack, pos, player);
			return true;
		}
		return false;
	}
	
	/**
	 * handles the breaking of a block with auto melt enabled, breaks the block and drops the molen items
	 * @param itemstack the stack of the tool
	 * @param pos the position of the to be broken block
	 * @param player the player that wields the tool
	 */
	private static void handleCustomBreak(ItemStack itemstack, BlockPos pos, EntityPlayer player) {
		World world = player.world;
		if(world.isRemote) {
			return;
		}
		IBlockState state = world.getBlockState(pos);
		
		NonNullList<ItemStack> drops = NonNullList.create();
		state.getBlock().getDrops(drops, world, pos, state, 0);

		world.setBlockToAir(pos);

		FurnaceRecipes recipes = FurnaceRecipes.instance();
		
		for(ItemStack stack : drops) {
			ItemStack result = recipes.getSmeltingResult(stack).copy();
			if(!result.isEmpty()) {
				result.setCount(stack.getCount());
				InventoryHelper.spawnItemStack(world, player.posX, player.posY, player.posZ, result);
			} else {
				InventoryHelper.spawnItemStack(world, player.posX, player.posY, player.posZ, stack);
			}
		}
	}

	@Override
	public int getHarvestLevel(ItemStack stack, String toolClass, EntityPlayer player, IBlockState blockState) {
		checkTagCompound(stack);
		Mode mode = Mode.get(stack);
		if (stack.hasCapability(HEAT, EnumFacing.DOWN) && "pickaxe".equals(toolClass)) {
			int amount = stack.getCapability(HEAT, EnumFacing.DOWN).getHeatstored();
			if (amount > 4 * Math.max(mode.getSpeedModifier(), 1)) {
				return 15;
			}
		}
		return super.getHarvestLevel(stack, toolClass, player, blockState);
	}

	@Override
	public Set<String> getToolClasses(ItemStack stack) {
		Set<String> s = new HashSet<>();
		s.add("pickaxe");
		s.add("melter");
		return s;
	}

	@Override
	public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn) {
		ItemStack stack = playerIn.getHeldItem(handIn);
		checkTagCompound(stack);
		Mode mode = Mode.get(stack);
		if (!playerIn.world.isRemote && stack.hasCapability(HEAT, EnumFacing.DOWN) && !playerIn.isSneaking()) {
			IHeatStorage storage = stack.getCapability(HEAT, EnumFacing.DOWN);
			if (storage.getHeatstored() < 20 || mode == Mode.OFF) {
				return new ActionResult(EnumActionResult.PASS, stack);
			}
			EntityHeatBall entityHeatBall = new EntityHeatBall(playerIn);
			playerIn.world.spawnEntity(entityHeatBall);
			storage.extractHeat(20, false);
			return new ActionResult(EnumActionResult.FAIL, stack);
		}
		if (playerIn.isSneaking()) {
			int newOrdinal = mode.ordinal() + 1;
			Mode[] modes = Mode.values();
			setMode(modes[newOrdinal == modes.length ? 0 : newOrdinal], stack);
			mode = Mode.get(stack);
			playerIn.sendStatusMessage(
					new TextComponentString(String.format("Set current mode to %s", mode.toString().toLowerCase())),
					true);
		}
		return new ActionResult(EnumActionResult.FAIL, stack);
	}

	private void setMode(Mode mode, ItemStack stack) {
		stack.getTagCompound().setString("mode", mode.toString());
	}

	private void checkTagCompound(ItemStack stack) {
		NBTTagCompound tagCompound;
		if (!stack.hasTagCompound()) {
			tagCompound = new NBTTagCompound();
		} else {
			tagCompound = stack.getTagCompound();
		}
		if (!tagCompound.hasKey("mode")) {
			tagCompound.setString("mode", Mode.OFF.toString());
			stack.setTagCompound(tagCompound);
		}
	}

	@Override
	public boolean onEntityItemUpdate(EntityItem entityItem) {
		/*
		 * implements the charging mechanism when thrown upon an block that can output
		 * heat
		 */
		BlockPos posEntity = new BlockPos(entityItem);
		BlockPos posUnderneath = posEntity.down(1);
		World worldIn = entityItem.world;
		if (!worldIn.isRemote) {
			IHeatStorage storage$entityItem = entityItem.getItem().getCapability(HEAT, EnumFacing.DOWN);
			TileEntity tile$posEntity = worldIn.getTileEntity(posEntity);
			TileEntity tile$posUnderneath = worldIn.getTileEntity(posUnderneath);
			if (tile$posEntity != null && tile$posEntity.hasCapability(HEAT, EnumFacing.UP)) {
				IHeatStorage storage$tile = tile$posEntity.getCapability(HEAT, EnumFacing.UP);
				int in = storage$tile.extractHeat(//
						storage$entityItem.getHeatCapacity() - storage$entityItem.getHeatstored(), //
						false);
				storage$entityItem.receiveHeat(in, false);
			} else if (tile$posUnderneath != null && tile$posUnderneath.hasCapability(HEAT, EnumFacing.UP)) {
				IHeatStorage storage$tile = tile$posUnderneath.getCapability(HEAT, EnumFacing.UP);
				int in = storage$tile.extractHeat(//
						storage$entityItem.getHeatCapacity() - storage$entityItem.getHeatstored(), //
						false);
				storage$entityItem.receiveHeat(in, false);
			}
		}
		return super.onEntityItemUpdate(entityItem);
	}

	@Override
	public boolean canHarvestBlock(IBlockState state, ItemStack stack) {
		System.out.println("test");
		checkTagCompound(stack);
		Mode mode = Mode.get(stack);
		return mode != Mode.AUTO_MELT || mode.getSpeedModifier() <= 0;
	}

	@Override
	public boolean onBlockDestroyed(ItemStack stack, World worldIn, IBlockState state, BlockPos pos,
			EntityLivingBase entityLiving) {
		IHeatStorage storage = stack.getCapability(HEAT, EnumFacing.DOWN);
		if (storage != null) {
			storage.extractHeat(4, false);
		}
		return true;
	}

	@Override
	public ICapabilityProvider initCapabilities(ItemStack stack, NBTTagCompound nbt) {
		return new HeatProvider().appendNBT(nbt);
	}

	@Override
	public int getRGBDurabilityForDisplay(ItemStack stack) {
		return 0xfeb301;
	}

	@Override
	public boolean isDamageable() {
		return true;
	}

	@Override
	public boolean showDurabilityBar(ItemStack stack) {
		return true;
	}

	@Override
	public double getDurabilityForDisplay(ItemStack stack) {
		if (!stack.hasCapability(HEAT, EnumFacing.DOWN))
			return 0;
		IHeatStorage storage = stack.getCapability(HEAT, EnumFacing.DOWN);
		double energy = storage.getHeatstored();
		double max = storage.getHeatCapacity();
		return 1.0d - energy / max;
	}

	@Override
	public float getDestroySpeed(ItemStack stack, IBlockState state) {
		checkTagCompound(stack);
		Mode mode = Mode.get(stack);
		int harvestlevel = state.getBlock().getHarvestLevel(state);
		if (harvestlevel > getHarvestLevel(stack, state.getBlock().getHarvestTool(state), null, state)) {
			return 1;
		}
		return mode.getSpeedModifier() * 20.2f + Math.min(mode.getSpeedModifier(), 1) * (3 * harvestlevel);
	}

	@Override
	public void addInformation(ItemStack stack, World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
		checkTagCompound(stack);
		StringBuilder builder = new StringBuilder("Energy : ");
		if (stack.hasCapability(HEAT, EnumFacing.DOWN)) {
			IHeatStorage heatStorage = stack.getCapability(HEAT, EnumFacing.DOWN);
			builder.append(heatStorage.getHeatstored());
			builder.append('/');
			builder.append(heatStorage.getHeatCapacity());
			builder.append("HT");
		} else {
			builder.append("ERROR");
		}
		tooltip.add(builder.toString());
		tooltip.add(Mode.get(stack).getDisplaySentence());
		super.addInformation(stack, worldIn, tooltip, flagIn);
	}
	
	

	private static class HeatProvider implements ICapabilitySerializable<NBTTagCompound> {

		IHeatStorage storage = new HeatStorage(1000, 1000, 1000);

		public HeatProvider appendNBT(@Nullable NBTTagCompound compound) {
			if (compound != null) {
				NBTTagCompound nbt$heatcapability = (NBTTagCompound) compound.getTag("heat");
				if (nbt$heatcapability != null) {
					HEAT.readNBT(storage, EnumFacing.DOWN, nbt$heatcapability);
				}
			}
			return this;
		}

		@Override
		public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
			return capability == HEAT && facing == EnumFacing.DOWN;
		}

		@Override
		public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
			return hasCapability(capability, facing) ? (T) storage : null;
		}

		@Override
		public NBTTagCompound serializeNBT() {
			NBTTagCompound compound = new NBTTagCompound();
			compound.setTag("heat", HEAT.writeNBT(storage, EnumFacing.DOWN));
			return compound;
		}

		@Override
		public void deserializeNBT(NBTTagCompound nbt) {
			if (nbt != null) {
				NBTTagCompound nbt$heatcapability = (NBTTagCompound) nbt.getTag("heat");
				if (nbt$heatcapability != null) {
					HEAT.readNBT(storage, EnumFacing.DOWN, nbt$heatcapability);
				}
			}
		}

	}
}
