package btf.init;

import btf.util.registry.AccumulatorRegistry;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;

public class AccumulatorRecipeinit {

	private AccumulatorRecipeinit(){

	}

	public static void register() {
		AccumulatorRegistry.INSTANCE.addRecipe(new ItemStack(Blocks.DIRT), Blocks.LAVA);
	}

}
