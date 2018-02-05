package btf.util.furnace;


import btf.util.registry.objects.CustomFurnaceRecipe;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.ArrayList;

public class MainHandler {
	private static CustomFurnaceRecipe currentRecipe;
	private static ArrayList<CustomFurnaceRecipe> Recipes;
	public static void parserecipes(BlockPos blockAt, World worldAt, EntityPlayer achiever, ItemStack[] input) {
		for (int i = 0; i <= Recipes.size(); i++) {
			currentRecipe = Recipes.get(i);
			if(currentRecipe.checkRecipe(input)) {
				currentRecipe.craft(blockAt, worldAt, achiever);
				i = Recipes.size() + 1;
			}
		}
	}
	public void setRecipes(ArrayList<CustomFurnaceRecipe> recipes) {
		Recipes = recipes;
	}
	
	
}
