package btf.util.registry.objects;


import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public abstract class CustomFurnaceRecipe {
	private final ItemStack[] inputs;
	private final ItemStack output;

	public CustomFurnaceRecipe(ItemStack output, ItemStack... input) {
		inputs = input;
		this.output = output;
	}

	public boolean checkRecipe(ItemStack[] inputs) {
		int i = 0;
		for (ItemStack requiredIn : this.inputs) {
			for (ItemStack inInput : this.inputs) {
				if (requiredIn.equals(inInput) || (requiredIn.getItem() == inInput.getItem()) && (requiredIn.getCount() <= inInput.getCount())) {
				} else {
					return false;
				}
			}
		}
		return true;
	}

	public abstract void craft(BlockPos blockAt, World worldAt, EntityPlayer achiever);
}
