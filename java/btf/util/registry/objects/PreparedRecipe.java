/**
 * 
 */
package btf.util.registry.objects;

import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;

/**
 * @author Ewanarends
 * @author Kjmaster
 */
public class PreparedRecipe {
	private final IRecipe source;
	private final ItemStack[] ingredients;

	public PreparedRecipe(IRecipe from) {
		ingredients = (ItemStack[]) from.getIngredients().toArray();
		source = from;
	}

	public boolean getIsInGrid(InventoryCrafting inv) {
		return source.getCraftingResult(inv) == source.getCraftingResult(inv);
	}

	public IRecipe getSource() {
		return source;
	}

	public ItemStack[] getIngredients() {
		return ingredients;
	}
}
