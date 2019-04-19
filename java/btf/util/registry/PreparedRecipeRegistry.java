package btf.util.registry;

import btf.util.registry.objects.PreparedRecipe;
import net.minecraft.item.crafting.IRecipe;
import net.minecraftforge.fml.common.registry.ForgeRegistries;

import java.util.ArrayList;
import java.util.Iterator;

public class PreparedRecipeRegistry {
	private final static PreparedRecipeRegistry INSTANCE = new PreparedRecipeRegistry();
	private ArrayList<PreparedRecipe> recipes = new ArrayList<>(1000);

	public static void addRecipe(IRecipe recipe) {
		INSTANCE.recipes.add(new PreparedRecipe(recipe));
	}

	public static void postinit() {
		Iterator<IRecipe> i = ForgeRegistries.RECIPES.iterator();
		while (i.hasNext()) {
			IRecipe recipe = i.next();
			addRecipe(recipe);
		}
	}

	public ArrayList<PreparedRecipe> getRecipes() {
		return recipes;
	}
}
