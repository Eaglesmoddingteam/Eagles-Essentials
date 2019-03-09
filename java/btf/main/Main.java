package btf.main;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import btf.init.AccumulatorRecipeinit;
import btf.init.BlockInit;
import btf.init.FluidInit;
import btf.init.ItemInit;
import btf.objects.entity.EntityHeatBall;
import btf.packet.MessageRequestUpdate;
import btf.packet.MessageUpdateTE;
import btf.proxy.ServerProxy;
import btf.util.energy.CapabilityGrowthPotential;
import btf.util.energy.heat.CapabilityHeat;
import btf.util.furnace.MainHandler;
import btf.util.handlers.BurnTimeHandler;
import btf.util.handlers.CraftingHandler;
import btf.util.handlers.OresHandler;
import btf.util.registry.FurnaceRegisty;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.common.registry.EntityRegistry;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;

@Mod(modid = Vars.MOD_ID, name = Vars.MOD_NAME, version = Vars.MOD_VERS)
public class Main {

	public static final SimpleNetworkWrapper NETWORK = NetworkRegistry.INSTANCE.newSimpleChannel(Vars.MOD_ID);

	public Main() {
		FluidRegistry.enableUniversalBucket();
		MinecraftForge.EVENT_BUS.register(new BurnTimeHandler());
	}

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
		FluidInit.preInit();
		NETWORK.registerMessage(MessageUpdateTE.Handle.class, MessageUpdateTE.class, 0, Side.CLIENT);
		NETWORK.registerMessage(MessageRequestUpdate.Handle.class, MessageRequestUpdate.class, 1, Side.SERVER);
		CapabilityGrowthPotential.register();
		CapabilityHeat.register();
		AccumulatorRecipeinit.register();
		EntityRegistry.registerModEntity(new ResourceLocation(Vars.MOD_ID, "heatball"), EntityHeatBall.class, "Heat Ball", 0, instance, 0, 20, false);
		proxy.registerRenders();
		
	}

	@Mod.EventHandler
	public static void init(FMLInitializationEvent event) {
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
