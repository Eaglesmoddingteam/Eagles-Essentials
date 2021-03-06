package btf.proxy;

import btf.main.Main;
import btf.main.Vars;
import btf.objects.blocks.tiles.*;
import btf.objects.blocks.tiles.heaters.HeaterBlazeing;
import btf.objects.blocks.tiles.heaters.HeaterEnder;
import btf.objects.blocks.tiles.heaters.HeaterFluid;
import btf.util.handlers.GuiHandler;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
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
		GameRegistry.registerTileEntity(TileFurnaceMultiBlock.class,
				new ResourceLocation(Vars.MOD_ID, "tile_furnace_multiblock"));
		GameRegistry.registerTileEntity(TileHarvesterTicker.class, new ResourceLocation(Vars.MOD_ID, "tile_harvester"));
		GameRegistry.registerTileEntity(TileBlockBreaker.class, new ResourceLocation(Vars.MOD_ID, "tile_blockbreaker"));
		GameRegistry.registerTileEntity(TileShower.class, new ResourceLocation(Vars.MOD_ID, "tile_shower"));
		GameRegistry.registerTileEntity(TileFluidCollector.class,
				new ResourceLocation(Vars.MOD_ID, "tile_fluid_collector"));
		GameRegistry.registerTileEntity(TileHeatCell.class, new ResourceLocation(Vars.MOD_ID, "tile_heatcell"));
		GameRegistry.registerTileEntity(TileSqueezer.class, new ResourceLocation(Vars.MOD_ID, "tile_squeezer"));
		GameRegistry.registerTileEntity(TileAccumulator.class, new ResourceLocation(Vars.MOD_ID, "tile_accumulator"));
		GameRegistry.registerTileEntity(TileFluidHopper.class, new ResourceLocation(Vars.MOD_ID, "tile_fluid_hopper"));
		GameRegistry.registerTileEntity(HeaterBlazeing.class,
				new ResourceLocation(Vars.MOD_ID, "tile_heater_blazeing"));
		GameRegistry.registerTileEntity(HeaterFluid.class, new ResourceLocation(Vars.MOD_ID, "tile_heater_fluid"));
		GameRegistry.registerTileEntity(HeaterEnder.class, new ResourceLocation(Vars.MOD_ID, "tile_heater_ender"));
		GameRegistry.registerTileEntity(TileMelter.class, new ResourceLocation(Vars.MOD_ID, "tile_melter"));
	}

	public void init(FMLInitializationEvent e) {
		NetworkRegistry.INSTANCE.registerGuiHandler(Main.instance, new GuiHandler());
	}

	public void openGUI(GuiScreen guiScreenIn) {
		/**
		 * empty because no implementation on server
		 */
	}

	public void registerRenders() {
		/**
		 * empty because no implementation on server
		 */
	}
}
