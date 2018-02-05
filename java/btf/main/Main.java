package btf.main;


import btf.init.BlockInit;
import btf.init.ItemInit;
import btf.proxy.ServerProxy;
import btf.util.energy.CapabilityGrowthPotential;
import btf.util.furnace.MainHandler;
import btf.util.handlers.CraftingHandler;
import btf.util.handlers.OresHandler;
import btf.util.registry.FurnaceRegisty;
import btf.util.seasons.SeasonColor;
import net.minecraft.client.renderer.color.BlockColors;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(modid = Vars.MOD_ID, name = Vars.MOD_NAME, version = Vars.MOD_VERS)
public class Main {
public static MainHandler furnaceHelper = new MainHandler();
	public static CreativeTabs ingotsTab = new CreativeTabs("EaglesEssentialsIngots") {
		@Override
		public ItemStack getTabIconItem() {
			return new ItemStack(ItemInit.bronze);
		}
	};
	public static CreativeTabs itemstab = new CreativeTabs("EaglesEssentialsoitandtool") {
		@Override
		public ItemStack getTabIconItem() {
			return new ItemStack(ItemInit.stoneGear);
		}
	};
	public static CreativeTabs blocksTab = new CreativeTabs("EaglesEssentialsBlocks") {
		@Override
		public ItemStack getTabIconItem() {
			return new ItemStack(BlockInit.harvester);
		}
	};

	public static final Logger LOGGER = LogManager.getLogger(Vars.MOD_ID);

	@Mod.Instance
	public static Main instance;
	
	@SidedProxy(clientSide = Vars.clientp, serverSide = Vars.serverp)
	public static ServerProxy proxy;
	
	@Mod.EventHandler
	public static void preInit(FMLPreInitializationEvent event) {
		CapabilityGrowthPotential.register();
	}
	
	@Mod.EventHandler
	public static void init(FMLInitializationEvent event) {
		BlockColors.init().registerBlockColorHandler(new SeasonColor(), Blocks.GRASS, Blocks.LEAVES, Blocks.LEAVES2);
		CraftingHandler.removeRecipes();
		CraftingHandler.RegisterRecipes();
		GameRegistry.registerWorldGenerator(new OresHandler(), 0);
		proxy.registerTileEntities();
		proxy.init(event);
	}
	
	@Mod.EventHandler
	public static void postInit(FMLPostInitializationEvent event) {
		FurnaceRegisty.postInit();
	}
}
