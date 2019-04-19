package btf.util.registry;

import btf.util.registry.objects.AccumulatorRecipe;
import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class AccumulatorRegistry {
	public static final AccumulatorRegistry INSTANCE = new AccumulatorRegistry();
	private List<AccumulatorRecipe> recipes = new ArrayList<>();

	public List<AccumulatorRecipe> getRecipes() {
		return recipes;
	}

	public static Block getOutcome(ItemStack input) {
		for (AccumulatorRecipe recipe : INSTANCE.recipes) {
			if (recipe.canRun(input)) {
				return recipe.getOutput();
			}
		}
		return null;
	}

	public void addRecipe(ItemStack input, Block output) {
		recipes.add(new AccumulatorRecipe(input, output));
	}
}
