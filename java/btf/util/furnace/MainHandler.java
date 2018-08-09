package btf.util.furnace;

import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.stream.Stream;

import btf.util.registry.objects.CustomFurnaceRecipe;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class MainHandler {

	private static ArrayList<CustomFurnaceRecipe> Recipes;

	public static void parserecipes(BlockPos blockAt, World worldAt, EntityPlayer achiever, ItemStack[] input) {
			Stream<CustomFurnaceRecipe> s = Recipes.stream().filter(recipe -> recipe.checkRecipe(input));
			if(s.count() > 0L) {
				s.findFirst().get().craft(blockAt, worldAt, achiever);
			}
	}

	public void setRecipes(ArrayList<CustomFurnaceRecipe> recipes) {
		Recipes = recipes;
	}

}
