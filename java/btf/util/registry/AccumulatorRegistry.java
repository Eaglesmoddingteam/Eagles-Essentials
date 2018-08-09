package btf.util.registry;

import java.util.ArrayList;
import java.util.HashMap;

import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;

public class AccumulatorRegistry {
	private HashMap<ArrayList<ItemStack>,Block> RECIPES = new HashMap<>(100);
	public static AccumulatorRegistry INSTANCE = new AccumulatorRegistry();

	public void addRecipe(ArrayList<ItemStack> input, Block output){
		this.RECIPES.put(input, output);
	}
	
	public static Block getOutcome(ArrayList<ItemStack> input){
		if(INSTANCE.RECIPES.containsKey(input)) {
			return INSTANCE.RECIPES.get(input);
		} else {
			return null;
		}
	}
}
