package btf.objects.items.book;


import btf.main.Main;
import btf.objects.items.ItemBase;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;

public class ItemBook extends ItemBase {

	public ItemBook(String name, int stackSize, CreativeTabs tab) {
		super(name, stackSize, tab);
	}
	
	@Override
	public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn) {
		playerIn.openGui(new Main(),1 , worldIn, (int) playerIn.posX, (int) playerIn.posX, (int) playerIn.posX);
		return super.onItemRightClick(worldIn, playerIn, handIn);
		}
}
