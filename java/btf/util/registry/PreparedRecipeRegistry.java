package btf.util.registry;

import java.util.ArrayList;
import java.util.Iterator;

import btf.util.registry.objects.PreparedRecipe;
import net.minecraft.item.crafting.IRecipe;
import net.minecraftforge.fml.common.registry.ForgeRegistries;

public class PreparedRecipeRegistry {
	private ArrayList<PreparedRecipe> recipes = new ArrayList<>(1000);
	
	private final static PreparedRecipeRegistry INSTANCE = new PreparedRecipeRegistry();
	
	public static void addRecipe(IRecipe recipe) {
		INSTANCE.recipes.add(new PreparedRecipe(recipe));
	}
	
	public static void postinit() {
		Iterator i = ForgeRegistries.RECIPES.iterator();
		while(i.hasNext()) {
			IRecipe recipe =  (IRecipe) i.next();
			addRecipe(recipe);
		}
	}

	public ArrayList<PreparedRecipe> getRecipes() {
		return recipes;
	}
}
