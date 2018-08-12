package btf.proxy;


import btf.main.Main;
import btf.main.Vars;
import btf.objects.blocks.tiles.TileAccumulator;
import btf.objects.blocks.tiles.TileAccumulatorMaster;
import btf.objects.blocks.tiles.TileBlockBreaker;
import btf.objects.blocks.tiles.TileFluidCollector;
import btf.objects.blocks.tiles.TileFluidHopper;
import btf.objects.blocks.tiles.TileFurnaceMultiBlock;
import btf.objects.blocks.tiles.TileHarvesterTicker;
import btf.objects.blocks.tiles.TileHeatCell;
import btf.objects.blocks.tiles.TileShower;
import btf.objects.blocks.tiles.TileSqueezer;
import btf.util.handlers.GuiHandler;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.item.Item;
import net.minecraft.util.text.translation.I18n;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class ServerProxy {

	public void registerItemRenderer(Item item, int meta, String ID) {

		
	}
	

	public String localize(String unlocalized, Object... args) {
		return I18n.translateToLocalFormatted(unlocalized, args);
	}

	public void registerTileEntities() {
		GameRegistry.registerTileEntity(TileFurnaceMultiBlock.class, Vars.MOD_ID + ":tile_furnace_multiblock");
		GameRegistry.registerTileEntity(TileHarvesterTicker.class, Vars.MOD_ID + ":tile_harvester");
		GameRegistry.registerTileEntity(TileBlockBreaker.class, Vars.MOD_ID + ":tile_blockbreaker");
		GameRegistry.registerTileEntity(TileShower.class, Vars.MOD_ID + ":tile_shower");
		GameRegistry.registerTileEntity(TileFluidCollector.class, Vars.MOD_ID + ":tile_fluid_collector");
		GameRegistry.registerTileEntity(TileHeatCell.class, Vars.MOD_ID + ":tile_heatcell");
		GameRegistry.registerTileEntity(TileSqueezer.class, Vars.MOD_ID + ":tile_squeezer");
		GameRegistry.registerTileEntity(TileAccumulatorMaster.class, Vars.MOD_ID + ":tile_accumulator_master");
		GameRegistry.registerTileEntity(TileAccumulator.class, Vars.MOD_ID + ":tile_accumulator_slave");
		GameRegistry.registerTileEntity(TileFluidHopper.class, Vars.MOD_ID + ":tile_fluid_hopper");
	}

	public void init(FMLInitializationEvent e) {
		NetworkRegistry.INSTANCE.registerGuiHandler(Main.instance, new GuiHandler());
	}

	public void openGUI(GuiScreen guiScreenIn) {
		
	}
}
