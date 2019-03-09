package btf.init;


import btf.main.Main;
import btf.objects.items.ItemBase;
import btf.objects.items.ItemTeleportingWand;
import btf.objects.items.book.ItemBook;
import btf.objects.tools.ImPossibiliumAxe;
import btf.objects.tools.ImPossibiliumPickaxe;
import btf.objects.tools.ImPossibiliumSword;
import btf.objects.tools.ItemMeltingTool;
import net.minecraft.item.Item;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.common.util.EnumHelper;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.registries.IForgeRegistry;

public class ItemInit {
	
	public static Item.ToolMaterial impossiblealloy = EnumHelper.addToolMaterial("Impossible", 15, 8000, 10, 20, 10);

	/** ingots **/
	public static ItemBase bronze = new ItemBase("bronze_ingot", Main.ingotsTab);
	public static ItemBase brass = new ItemBase("brass_ingot", Main.ingotsTab);
	public static ItemBase cobalt = new ItemBase("cobalt_ingot", Main.ingotsTab);
	public static ItemBase copper = new ItemBase("copper_ingot", Main.ingotsTab);
	public static ItemBase lead = new ItemBase("lead_ingot", Main.ingotsTab);
	public static ItemBase platinum = new ItemBase("platinum_ingot", Main.ingotsTab);
	public static ItemBase tin = new ItemBase("tin_ingot", Main.ingotsTab);
	public static ItemBase impossibleAlloy = new ItemBase("impossible_alloy", Main.ingotsTab);
	public static ItemBase zincShard = new ItemBase("zinc_shard", Main.ingotsTab);
	/** gears **/
	public static ItemBase brassGear = new ItemBase("brass_gear", Main.itemstab);
	public static ItemBase bronzeGear = new ItemBase("bronze_gear", Main.itemstab);
	public static ItemBase cobaltGear = new ItemBase("cobalt_gear", Main.itemstab);
	public static ItemBase copperGear = new ItemBase("copper_gear", Main.itemstab);
	public static ItemBase goldGear = new ItemBase("gold_gear", Main.itemstab);
	public static ItemBase impossibleAlloyGear = new ItemBase("impossible_alloy_gear", Main.itemstab);
	public static ItemBase ironGear = new ItemBase("iron_gear", Main.itemstab);
	public static ItemBase leadGear = new ItemBase("lead_gear", Main.itemstab);
	public static ItemBase platinumGear = new ItemBase("platinum_gear", Main.itemstab);
	public static ItemBase tinGear = new ItemBase("tin_gear", Main.itemstab);
	public static ItemBase woodGear = new ItemBase("wooden_gear", Main.itemstab);
	public static ItemBase stoneGear = new ItemBase("stone_gear", Main.itemstab);
	//book
	//public static Item book = new ItemBook("manual_book", 1, Main.itemstab);
	//Plating and other crafting materials
	public static ItemBase plating = new ItemBase("wooden_plating", Main.itemstab);
	public static ItemBase machine_plating = new ItemBase("machine_plating", 64, Main.itemstab);
	//tools
	public static ItemBase block_uniter = new ItemBase("block_uniter", Main.itemstab);
	public static ImPossibiliumAxe imPossibiliumAxe = new ImPossibiliumAxe("axe_impossible", impossiblealloy);
	public static ImPossibiliumSword imPossibiliumSword = new ImPossibiliumSword(impossiblealloy, "sword_impossible");
	public static ImPossibiliumPickaxe imPossibiliumPick = new ImPossibiliumPickaxe(impossiblealloy, "pick_impossible");
	public static ItemTeleportingWand telewand = new ItemTeleportingWand();
	public static ItemBase melting_tool	= new ItemMeltingTool();
	
	public static Item[] items = {
			//book
			//book,
			//metals
			bronze,
			brass,
			cobalt,
			copper,
			lead,
			platinum,
			tin,
			impossibleAlloy,
			zincShard,
			//gears
			brassGear,
			bronzeGear,
			cobaltGear,
			copperGear,
			goldGear,
			impossibleAlloyGear,
			ironGear,
			leadGear,
			platinumGear,
			tinGear,
			woodGear,
			stoneGear,
			//tools
			block_uniter,
			imPossibiliumAxe,
			imPossibiliumSword,
			imPossibiliumPick,
			//Plating and other crafting materials
			plating,
			machine_plating,
			telewand,
			melting_tool
	};

	private static void oreDictionaryRegistration() {

		//ingots
		OreDictionary.registerOre("ingotBronze",bronze);
		OreDictionary.registerOre("ingotBrass", brass);
		OreDictionary.registerOre("ingotCobalt", cobalt);
		OreDictionary.registerOre("ingotCopper", copper);
		OreDictionary.registerOre("ingotLead", lead);
		OreDictionary.registerOre("ingotPlatinum", platinum);
		OreDictionary.registerOre("ingotTin", tin);
		OreDictionary.registerOre("alloyImpossible", impossibleAlloy);
		OreDictionary.registerOre("shardZinc", zincShard);
		//gears
		OreDictionary.registerOre("gearBrass", brassGear);
		OreDictionary.registerOre("gearBronze", bronzeGear);
		OreDictionary.registerOre("gearCobalt", cobaltGear);
		OreDictionary.registerOre("gearCopper", copperGear);
		OreDictionary.registerOre("gearGold", goldGear);
		OreDictionary.registerOre("gearImpossibleAlloy", impossibleAlloyGear);
		OreDictionary.registerOre("gearIron", ironGear);
		OreDictionary.registerOre("gearLead", leadGear);
		OreDictionary.registerOre("gearPlatinum", platinumGear);
		OreDictionary.registerOre("gearTin", tinGear);
		OreDictionary.registerOre("gearWooden", woodGear);
		OreDictionary.registerOre("gearStone", stoneGear);
	}

	public static void register(IForgeRegistry<Item> registry) {
		registry.registerAll(items);
		ItemInit.oreDictionaryRegistration();
	}

	public static void registerModels(ModelRegistryEvent registryEvent) {
		for (Item item: items) {
			Main.proxy.registerItemRenderer(item, 0, "inventory");
		}
	}
}
