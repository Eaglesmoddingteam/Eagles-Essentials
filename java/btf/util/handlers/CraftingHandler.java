package btf.util.handlers;


import btf.init.BlockInit;
import btf.init.ItemInit;
import btf.main.Vars;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.registries.ForgeRegistry;

public class CraftingHandler {


	public static void RegisterRecipes() {
		ForgeRegistry<IRecipe> recipeRegistry = (ForgeRegistry<IRecipe>) ForgeRegistries.RECIPES;
		GameRegistry.addSmelting(Item.getItemFromBlock(BlockInit.cobaltOre), new ItemStack(ItemInit.cobalt), 2);
		GameRegistry.addSmelting(Item.getItemFromBlock(BlockInit.tinOre), new ItemStack(ItemInit.tin), 2);
		GameRegistry.addSmelting(Item.getItemFromBlock(BlockInit.copperOre), new ItemStack(ItemInit.copper), 2);
		GameRegistry.addSmelting(Item.getItemFromBlock(BlockInit.leadOre), new ItemStack(ItemInit.lead), 2);
		GameRegistry.addSmelting(Item.getItemFromBlock(BlockInit.platinumOre), new ItemStack(ItemInit.platinum), 2);
		GameRegistry.addSmelting(Item.getItemFromBlock(BlockInit.zincOre), new ItemStack(ItemInit.zincShard), 2);
		GameRegistry.addShapedRecipe(new ResourceLocation(Vars.MOD_ID + ":" + "woodengear"), new ResourceLocation(Vars.MOD_ID + ":" + "gearupgrade"), new ItemStack(ItemInit.woodGear), "PSP", "SAS", "PSP", 'P', Item.getItemFromBlock(Blocks.PLANKS), 'S', Items.STICK, 'A', ItemInit.tin);
		GameRegistry.addShapedRecipe(new ResourceLocation(Vars.MOD_ID + ":" + "brassgear"), new ResourceLocation(Vars.MOD_ID + ":" + "gearupgrade"), new ItemStack(ItemInit.brassGear), "PSP", "SAS", "PSP", 'P', ItemInit.brass, 'S', Items.STICK, 'A', ItemInit.woodGear);
		GameRegistry.addShapedRecipe(new ResourceLocation(Vars.MOD_ID + ":" + "bronzegear"), new ResourceLocation(Vars.MOD_ID + ":" + "gearupgrade"), new ItemStack(ItemInit.bronzeGear), "PSP", "SAS", "PSP", 'P', ItemInit.bronze, 'S', Items.STICK, 'A', ItemInit.woodGear);
		GameRegistry.addShapedRecipe(new ResourceLocation(Vars.MOD_ID + ":" + "cobaltgear"), new ResourceLocation(Vars.MOD_ID + ":" + "gearupgrade"), new ItemStack(ItemInit.cobaltGear), "PSP", "SAS", "PSP", 'P', ItemInit.cobalt, 'S', Items.STICK, 'A', ItemInit.woodGear);
		GameRegistry.addShapedRecipe(new ResourceLocation(Vars.MOD_ID + ":" + "coppergear"), new ResourceLocation(Vars.MOD_ID + ":" + "gearupgrade"), new ItemStack(ItemInit.copperGear), "PSP", "SAS", "PSP", 'P', ItemInit.copper, 'S', Items.STICK, 'A', ItemInit.woodGear);
		GameRegistry.addShapedRecipe(new ResourceLocation(Vars.MOD_ID + ":" + "impossiblealloygear"), new ResourceLocation(Vars.MOD_ID + ":" + "gearupgrade"), new ItemStack(ItemInit.impossibleAlloyGear), "PSP", "SAS", "PSP", 'P', ItemInit.impossibleAlloy, 'S', Items.STICK, 'A', ItemInit.woodGear);
		GameRegistry.addShapedRecipe(new ResourceLocation(Vars.MOD_ID + ":" + "irongear"), new ResourceLocation(Vars.MOD_ID + ":" + "gearupgrade"), new ItemStack(ItemInit.ironGear), "PSP", "SAS", "PSP", 'P', Items.IRON_INGOT, 'S', Items.STICK, 'A', ItemInit.woodGear);
		GameRegistry.addShapedRecipe(new ResourceLocation(Vars.MOD_ID + ":" + "leadgear"), new ResourceLocation(Vars.MOD_ID + ":" + "gearupgrade"), new ItemStack(ItemInit.leadGear), "PSP", "SAS", "PSP", 'P', ItemInit.lead, 'S', Items.STICK, 'A', ItemInit.woodGear);
		GameRegistry.addShapedRecipe(new ResourceLocation(Vars.MOD_ID + ":" + "goldgear"), new ResourceLocation(Vars.MOD_ID + ":" + "gearupgrade"), new ItemStack(ItemInit.goldGear), "PSP", "SAS", "PSP", 'P', Items.GOLD_INGOT, 'S', Items.STICK, 'A', ItemInit.woodGear);
		GameRegistry.addShapedRecipe(new ResourceLocation(Vars.MOD_ID + ":" + "platinumgear"), new ResourceLocation(Vars.MOD_ID + ":" + "gearupgrade"), new ItemStack(ItemInit.platinumGear), "PSP", "SAS", "PSP", 'P', ItemInit.platinum, 'S', Items.STICK, 'A', ItemInit.woodGear);
		GameRegistry.addShapedRecipe(new ResourceLocation(Vars.MOD_ID + ":" + "tingear"), new ResourceLocation(Vars.MOD_ID + ":" + "gearupgrade"), new ItemStack(ItemInit.tinGear), "PSP", "SAS", "PSP", 'P', ItemInit.tin, 'S', Items.STICK, 'A', ItemInit.woodGear);
		GameRegistry.addShapedRecipe(new ResourceLocation(Vars.MOD_ID + ":" + "stonegear"), new ResourceLocation(Vars.MOD_ID + ":" + "gearupgrade"), new ItemStack(ItemInit.stoneGear), "PSP", "SAS", "PSP", 'P', Item.getItemFromBlock(Blocks.COBBLESTONE), 'S', Items.STICK, 'A', ItemInit.woodGear);
		GameRegistry.addShapedRecipe(new ResourceLocation(Vars.MOD_ID + ":" + "casing"), new ResourceLocation(Vars.MOD_ID + ":" + "casing"), new ItemStack(Item.getItemFromBlock(BlockInit.casingWooden)), "PSP", "SAS", "PSP", 'S', ItemInit.plating, 'P', Items.STICK, 'A', ItemInit.woodGear);
		GameRegistry.addShapelessRecipe(new ResourceLocation(Vars.MOD_ID + ":" + "platingwooden"), new ResourceLocation(Vars.MOD_ID + ":" + "plating"), new ItemStack(ItemInit.plating, 4), Ingredient.fromItem(Item.getItemFromBlock(Blocks.PLANKS)), Ingredient.fromItem(Items.STICK));
		GameRegistry.addShapedRecipe(new ResourceLocation(Vars.MOD_ID + ":" + "teleshaft"), new ResourceLocation(Vars.MOD_ID + ":" + "machines"), new ItemStack(Item.getItemFromBlock(BlockInit.teleporter)), "PSP", "SAS", "PSP", 'S', Items.ENDER_PEARL, 'P', Items.STICK, 'A', Item.getItemFromBlock(BlockInit.casingWooden));
		GameRegistry.addShapedRecipe(new ResourceLocation(Vars.MOD_ID + ":" + "harvester"), new ResourceLocation(Vars.MOD_ID + ":" + "machines"), new ItemStack(Item.getItemFromBlock(BlockInit.harvester)), "PGP", "SAS", "PHP", 'S', ItemInit.machine_plating, 'P', Items.STICK, 'A', Item.getItemFromBlock(BlockInit.casingWooden), 'G', ItemInit.ironGear, 'H', Items.DIAMOND_HOE);
		GameRegistry.addShapedRecipe(new ResourceLocation(Vars.MOD_ID + ":" + "block_breaker"), new ResourceLocation(Vars.MOD_ID + ":" + "machines"), new ItemStack(Item.getItemFromBlock(BlockInit.blockbreaker)), "PGP", "SAS", "PHP", 'S', ItemInit.machine_plating, 'P', Items.STICK, 'A', Item.getItemFromBlock(BlockInit.casingWooden), 'G', ItemInit.ironGear, 'H', Items.DIAMOND_PICKAXE);
		GameRegistry.addShapedRecipe(new ResourceLocation(Vars.MOD_ID + ":" + "machineplating"), new ResourceLocation(Vars.MOD_ID + ":plating"), new ItemStack(ItemInit.machine_plating, 2), "IPI", "PRP", "SPS", 'I', Items.IRON_INGOT, 'P', ItemInit.plating, 'R', Items.REDSTONE, 'S', Item.getItemFromBlock(Blocks.COBBLESTONE));
		GameRegistry.addShapedRecipe(new ResourceLocation(Vars.MOD_ID + ":" + "fluidcollector"), new ResourceLocation(Vars.MOD_ID + ":" + "machines"), new ItemStack(BlockInit.fluidcollector), "PBP", "QCQ", "PBP", 'Q', Item.getItemFromBlock(Blocks.PISTON), 'B', Items.BUCKET, 'C', Item.getItemFromBlock(BlockInit.casingWooden), 'P', ItemInit.machine_plating);
		GameRegistry.addShapedRecipe(new ResourceLocation(Vars.MOD_ID + ":" + "agriciltural_shower"), new ResourceLocation(Vars.MOD_ID + ":" + "machines"), new ItemStack(BlockInit.shower), "PBP", "QCQ", "PBP", 'Q', Items.IRON_HOE, 'B', Items.BONE, 'C', Item.getItemFromBlock(BlockInit.casingWooden), 'P', ItemInit.machine_plating);
	}
}
