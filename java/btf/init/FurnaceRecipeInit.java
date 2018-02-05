package btf.init;


import btf.util.registry.FurnaceRegisty;
import btf.util.registry.objects.CustomFurnaceRecipe;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class FurnaceRecipeInit {
			public static CustomFurnaceRecipe bronze = new CustomFurnaceRecipe(new ItemStack(ItemInit.bronze), new ItemStack(ItemInit.copper, 3), new ItemStack(ItemInit.tin)) {
				@Override
				public void craft(BlockPos blockAt, World worldAt, EntityPlayer achiever) {
				
				}};
			public static CustomFurnaceRecipe brass = new CustomFurnaceRecipe(new ItemStack(ItemInit.brass), new ItemStack(ItemInit.copper, 2), new ItemStack(ItemInit.tin, 2)) {
				@Override
				public void craft(BlockPos blockAt, World worldAt, EntityPlayer achiever) {
					
				}};
	public static void registerRecipes() {
	FurnaceRegisty.addRecipe(bronze);	
	FurnaceRegisty.addRecipe(brass);
	}
}
