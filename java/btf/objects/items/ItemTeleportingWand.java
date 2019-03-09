package btf.objects.items;

import java.util.List;

import btf.init.BlockInit;
import btf.main.Main;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;

public class ItemTeleportingWand extends ItemBase {

	public ItemTeleportingWand() {
		super("teleporting_wand", 1, Main.itemstab);
	}

	@Override
	public EnumActionResult onItemUse(EntityPlayer player, World worldIn, BlockPos pos, EnumHand hand,
			EnumFacing facing, float hitX, float hitY, float hitZ) {
		ItemStack stack = player.getHeldItem(hand);
		if (!player.isSneaking()) {
			if (!stack.hasTagCompound()) {
				stack.setTagCompound(new NBTTagCompound());
				stack.getTagCompound().setInteger("colour", 0);
			}
			NBTTagCompound compound = stack.getTagCompound();
			StringBuilder keyBuilder = new StringBuilder("tele_pos");
			keyBuilder.append(compound.getInteger("colour"));
			String key = keyBuilder.toString();
			if (testTeleporter(pos, worldIn)) {
				compound.setIntArray(key, new int[] { pos.getX(), pos.getY(), pos.getZ() });
				player.sendStatusMessage(new TextComponentString("Teleporter set!"), true);
			}
		} else if (stack.hasTagCompound()) {
			NBTTagCompound compound = stack.getTagCompound();
			StringBuilder keyBuilder = new StringBuilder("tele_pos");
			keyBuilder.append(compound.getInteger("colour"));
			String key = keyBuilder.toString();
			if (compound.hasKey(key)) {
				int[] vec = compound.getIntArray(key);
				if (vec.length == 3 && testTeleporter(new BlockPos(vec[0], vec[1], vec[2]), worldIn))
					if (!player.attemptTeleport(vec[0] + 0.5, vec[1] + 1.1, vec[2] + 0.5)) {
						player.sendStatusMessage(new TextComponentString("Unable to teleport you!"), true);
					}
			} else {
				player.sendStatusMessage(new TextComponentString(//
						new StringBuilder("No teleportation reciever set on the colour ")
								.append(EnumDyeColor.byMetadata(compound.getInteger("colour")).name().toLowerCase())
								.toString()),
						true);
			}
		}

		return EnumActionResult.SUCCESS;
	}

	@Override
	public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn) {
		ItemStack stack = playerIn.getHeldItem(handIn);
		if (playerIn.isSneaking()) {
			if (!stack.hasTagCompound()) {
				stack.setTagCompound(new NBTTagCompound());
				stack.getTagCompound().setInteger("colour", 0);
			}
			NBTTagCompound compound = stack.getTagCompound();
			int oldColour = compound.getInteger("colour");
			int newColour = oldColour == 15 ? 0 : oldColour + 1;
			compound.setInteger("colour", newColour);
			playerIn.sendStatusMessage(new TextComponentString(new StringBuffer("Set active colour to ")
					.append(EnumDyeColor.byMetadata(compound.getInteger("colour")).name().toLowerCase()).toString()),
					true);
		} else if (stack.hasTagCompound()) {
			NBTTagCompound compound = stack.getTagCompound();
			StringBuilder keyBuilder = new StringBuilder("tele_pos");
			keyBuilder.append(compound.getInteger("colour"));
			String key = keyBuilder.toString();
			if (compound.hasKey(key)) {
				int[] vec = compound.getIntArray(key);
				if (vec.length == 3 && testTeleporter(new BlockPos(vec[0], vec[1], vec[2]), worldIn))
					if (!playerIn.attemptTeleport(vec[0] + 0.5, vec[1] + 1.1, vec[2] + 0.1 + 0.5)) {
						playerIn.sendStatusMessage(new TextComponentString("Unable to teleport you!"), true);
					}
			} else {
				playerIn.sendStatusMessage(new TextComponentString(//
						new StringBuilder("No teleportation reciever set on the colour ")
								.append(EnumDyeColor.byMetadata(compound.getInteger("colour")).name().toLowerCase())
								.toString()),
						true);
			}
		}
		return new ActionResult<>(EnumActionResult.PASS, playerIn.getHeldItem(handIn));
	}

	@Override
	public void addInformation(ItemStack stack, World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
		if (stack.hasTagCompound() && stack.getTagCompound().hasKey("colour")) {
			int colour = stack.getTagCompound().getInteger("colour");
			tooltip.add("currently set to: " + EnumDyeColor.byMetadata(colour).toString().toLowerCase());
		}
		super.addInformation(stack, worldIn, tooltip, flagIn);
	}

	private boolean testTeleporter(BlockPos blockPos, World w) {
		return w.getBlockState(blockPos).getBlock() == BlockInit.telepad;
	}

}
