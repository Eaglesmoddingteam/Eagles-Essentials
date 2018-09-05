package btf.init;

import btf.main.Main;
import btf.objects.blocks.BlockAccumulator;
import btf.objects.blocks.BlockBase;
import btf.objects.blocks.BlockFluidCollector;
import btf.objects.blocks.BlockFurnaceBrick;
import btf.objects.blocks.BlockHeatCell;
import btf.objects.blocks.BlockHeaterBase;
import btf.objects.blocks.BlockSqueezer;
import btf.objects.blocks.Machine;
import btf.objects.blocks.MachineShower;
import btf.util.blockstate.registry.EntryManager;
import btf.util.blockstate.types.heater.HeaterBlazeing;
import btf.util.handlers.HeaterHandler;
import btf.util.handlers.MachineHandler;
import btf.util.handlers.MachineHandler.MachineTypes;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockRenderLayer;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.registries.IForgeRegistry;

public class BlockInit {

	public static BlockBase bronzeBlock = new BlockBase("bronze_block", Material.IRON, Main.ingotsTab);
	public static BlockBase brassBlock = new BlockBase("brass_block", Material.IRON, Main.ingotsTab);
	public static BlockBase cobaltBlock = new BlockBase("cobalt_block", Material.IRON, Main.ingotsTab);
	public static BlockBase copperBlock = new BlockBase("copper_block", Material.IRON, Main.ingotsTab);
	public static BlockBase leadBlock = new BlockBase("lead_block", Material.IRON, Main.ingotsTab);
	public static BlockBase platinumBlock = new BlockBase("platinum_block", Material.IRON, Main.ingotsTab);
	public static BlockBase tinBlock = new BlockBase("tin_block", Material.IRON, Main.ingotsTab);
	public static BlockBase cobaltOre = new BlockBase("cobalt_ore", Material.ROCK, Main.ingotsTab, 2);
	public static BlockBase copperOre = new BlockBase("copper_ore", Material.ROCK, Main.ingotsTab, 2);
	public static BlockBase leadOre = new BlockBase("lead_ore", Material.ROCK, Main.ingotsTab, 2);
	public static BlockBase platinumOre = new BlockBase("platinum_ore", Material.ROCK, Main.ingotsTab, 2);
	public static BlockBase tinOre = new BlockBase("tin_ore", Material.ROCK, Main.ingotsTab, 2);
	public static BlockBase zincOre = new BlockBase("zinc_ore", Material.ROCK, Main.ingotsTab, 2);
	public static BlockBase casingBedrockium = new BlockBase("casing_bedrock", Material.ROCK, Main.blocksTab, 3);
	public static BlockBase casingEnder = new BlockBase("casing_ender", Material.ROCK, Main.blocksTab, 3);
	public static BlockBase casingMetallic = new BlockBase("casing_metallic", Material.ROCK, Main.blocksTab, 3);
	public static BlockFurnaceBrick furnaceBrick = new BlockFurnaceBrick("furnace_brick", Material.ROCK,
			Main.blocksTab);
	public static Machine casingWooden = new Machine("wooden_casing", Material.WOOD, Main.blocksTab,
			MachineTypes.WOODENCASING);
	public static Machine factorytable = new Machine("factory_table", Material.WOOD, Main.blocksTab,
			MachineTypes.FACTORYTABLE);
	public static Machine harvester = new Machine("harvester", Material.WOOD, Main.blocksTab, MachineTypes.HARVESTER);
	public static Machine teleporter = new Machine("teleporter", Material.WOOD, Main.blocksTab,
			MachineTypes.TELEPORTER);
	public static Machine blockbreaker = new Machine("block_breaker", Material.WOOD, Main.blocksTab,
			MachineTypes.BLOCKBREAKER);
	public static BlockFluidCollector fluidcollector = new BlockFluidCollector("fluid_collector", Material.WOOD,
			Main.blocksTab, 1);
	public static MachineShower shower = new MachineShower("shower", Material.WOOD, Main.blocksTab, 1);
	public static BlockHeatCell heatCell = new BlockHeatCell();
	public static BlockAccumulator impossibilium_Accumulator = new BlockAccumulator();
	public static BlockBase telepad = new BlockBase("telepad", Material.ROCK, Main.blocksTab, 2);
	public static BlockSqueezer squeezer = new BlockSqueezer();

	public static Block[] blocks = {
			// Metal Blocks
			bronzeBlock, brassBlock, cobaltBlock, copperBlock, leadBlock, platinumBlock, tinBlock,

			// Ores
			cobaltOre, copperOre, leadOre, platinumOre, tinOre, zincOre,

			// machines
			casingWooden, casingBedrockium, casingEnder, casingMetallic, fluidcollector, harvester, teleporter, blockbreaker, shower,
			heatCell, telepad, squeezer, impossibilium_Accumulator //
			};

	public static void changeblockdata() {
		fluidcollector.setLightOpacity(2);
	}

	public static void register(IForgeRegistry<Block> registry) {
		registry.registerAll(blocks);
	}

	public static void registerItemBlocks(IForgeRegistry<Item> registry) {
		for (Block block : blocks) {
			BlockBase blockBase = (BlockBase) block;
			registry.register(blockBase.createItemBlock());
		}
		oreDictionaryRegistration();
	}

	public static void registerModels(ModelRegistryEvent registryEvent) {
		for (Block block : blocks)
			Main.proxy.registerItemRenderer(Item.getItemFromBlock(block), 0, "inventory");
	}

	private static void oreDictionaryRegistration() {
		// Metals Blocks
		OreDictionary.registerOre("blockBronze", new ItemStack(bronzeBlock, 1, 0));
		OreDictionary.registerOre("blockBrass", new ItemStack(brassBlock, 1, 0));
		OreDictionary.registerOre("blockCobalt", new ItemStack(cobaltBlock, 1, 0));
		OreDictionary.registerOre("blockCopper", new ItemStack(copperBlock, 1, 0));
		OreDictionary.registerOre("blockLead", new ItemStack(leadBlock, 1, 0));
		OreDictionary.registerOre("blockPlatinum", new ItemStack(platinumBlock, 1, 0));
		OreDictionary.registerOre("blockTin", new ItemStack(tinBlock, 1, 0));
		// Ores
		OreDictionary.registerOre("oreCobalt", new ItemStack(cobaltOre, 1, 0));
		OreDictionary.registerOre("oreCopper", new ItemStack(copperOre, 1, 0));
		OreDictionary.registerOre("oreLead", new ItemStack(leadOre, 1, 0));
		OreDictionary.registerOre("orePlatinum", new ItemStack(platinumOre, 1, 0));
		OreDictionary.registerOre("oreTin", new ItemStack(tinOre, 1, 0));
		OreDictionary.registerOre("oreZinc", new ItemStack(zincOre, 1, 0));
	}
}
