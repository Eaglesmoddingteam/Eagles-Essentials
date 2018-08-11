package btf.util.registry;

import java.util.ArrayList;
import java.util.List;

import btf.util.registry.objects.AccumulatorRecipe;
import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;

public class AccumulatorRegistry {
	public List<AccumulatorRecipe> recipes = new ArrayList<>();
	public static AccumulatorRegistry INSTANCE = new AccumulatorRegistry();

	public void addRecipe(ItemStack input, Block output){
		recipes.add(new AccumulatorRecipe(input, output));
	}
	
	public static Block getOutcome(ItemStack input){
		for(AccumulatorRecipe recipe : INSTANCE.recipes) {
			if(recipe.canRun(input)) {
				return recipe.getOutput();
			}
		}
		return null;
	}
}
