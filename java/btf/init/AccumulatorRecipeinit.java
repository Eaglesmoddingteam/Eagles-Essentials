package btf.init;

import com.google.common.collect.Lists;

import btf.util.registry.AccumulatorRegistry;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;

public class AccumulatorRecipeinit {

	public static void register() {
		AccumulatorRegistry.INSTANCE.addRecipe(Lists.newArrayList(new ItemStack(Blocks.DIRT)), Blocks.LAVA);
	}
	
}
