package btf.util.registry;

import btf.main.Main;
import btf.util.registry.objects.CustomFurnaceRecipe;

import java.util.ArrayList;

public class FurnaceRegisty {
	private static ArrayList<CustomFurnaceRecipe> recipes = new ArrayList();
	private static CustomFurnaceRecipe[] finalizedRecipes;

	public static void addRecipe(CustomFurnaceRecipe recipe) {
		if (!(recipe == null))
			FurnaceRegisty.recipes.add(recipe);
	}

	public static void postInit() {
		Main.furnaceHelper.setRecipes(recipes);
	}
}
