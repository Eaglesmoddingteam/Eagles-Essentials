package btf.objects.tools;


import java.util.jar.Attributes;

import btf.main.Main;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Enchantments;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.network.datasync.EntityDataManager.DataEntry;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;
import scala.collection.generic.GenericClassTagTraversableTemplate;

public class ImPossibiliumSword extends ItemSword {

	public ImPossibiliumSword(ToolMaterial material, String name) {
		super(material);
		 setUnlocalizedName(name);
	        setRegistryName(name);
	        setCreativeTab(Main.itemstab);
	}
	
	@Override
	public boolean onLeftClickEntity(ItemStack stack, EntityPlayer player, Entity entity) {
		NBTTagCompound compound = player.getActiveItemStack().getTagCompound();
		if(compound == null) {
			player.getActiveItemStack().setTagCompound(new NBTTagCompound());
			compound = player.getActiveItemStack().getTagCompound();
		}
		if(compound.hasKey("uses")) {
			int i = compound.getInteger("uses");
			int level = compound.getInteger("level");
			if(i >= (10 + (5 * level))) {
				i = 0;
				compound.setInteger("uses", 0);
				level++;
				compound.setInteger("level", level);
				player.sendStatusMessage(new TextComponentString("Your sword is now level " + level + "!"), true);
			}
			
				entity.attackEntityFrom(DamageSource.ON_FIRE, (float) ((Math.random() * 10 + 1) * level + 1));
			compound.setInteger("uses", i + 1);
		} else {
			compound.setInteger("uses", 0);
			compound.setInteger("level", 0);
		}
		return super.onLeftClickEntity(stack, player, entity);
	}

}
