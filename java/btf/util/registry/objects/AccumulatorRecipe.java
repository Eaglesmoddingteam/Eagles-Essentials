package btf.util.registry.objects;

import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;

public class AccumulatorRecipe {
	private final ItemStack in;
	private final Block out;

	public AccumulatorRecipe(ItemStack in, Block out) {
		this.in = in;
		this.out = out;
	}

	public ItemStack getInput() {
		return in;
	}

	public Block getOutput() {
		return out;
	}

	public boolean canRun(ItemStack stack) {
		boolean flag;
		flag = stack.getItem() == in.getItem();
		flag &= stack.getCount() == in.getCount();
		return flag;
	}
}
