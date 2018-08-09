package btf.objects.items;

import org.lwjgl.opengl.GL11;

import btf.init.BlockInit;
import btf.main.Main;
import net.minecraft.block.BlockLiquid;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
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
		super("teleporting_wand", 64, Main.itemstab);
	}
	
	@Override
	public EnumActionResult onItemUse(EntityPlayer player, World worldIn, BlockPos pos, EnumHand hand,
			EnumFacing facing, float hitX, float hitY, float hitZ) {
		if(worldIn.isRemote) {
		if(worldIn.getBlockState(pos).getBlock() == BlockInit.telepad) {
			player.sendStatusMessage(new TextComponentString("Set Teleporter!!"), true);
			if(!player.getHeldItem(hand).hasTagCompound()) {
				player.getHeldItem(hand).setTagCompound(new NBTTagCompound());
			}
			player.getHeldItem(hand).getTagCompound().setIntArray("position_teleporter", new int[]{ pos.getX(), pos.getY(), pos.getZ() });
		}
		}
		return EnumActionResult.SUCCESS;
	}
	
	@Override
	public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn) {
		if(playerIn.getHeldItem(handIn).hasTagCompound()) {
			if(playerIn.getHeldItem(handIn).getTagCompound().hasKey("position_teleporter")) {
				int[] vertices = playerIn.getHeldItem(handIn).getTagCompound().getIntArray("position_teleporter");
				BlockPos pos = new BlockPos(vertices[0], vertices[1], vertices[2]);
				if(worldIn.getBlockState(pos).getBlock() == BlockInit.telepad) {
					pos = pos.add(0.5, 1, 0.5);
					if(playerIn.attemptTeleport(pos.getX(), pos.getY(), pos.getZ())) {
						return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, playerIn.getHeldItem(handIn));
					} else {
						playerIn.sendStatusMessage(new TextComponentString("Failed to teleport you"), true);
					}
				}
			}
		}
		
		return new ActionResult<ItemStack>(EnumActionResult.PASS, playerIn.getHeldItem(handIn));
	}

}
