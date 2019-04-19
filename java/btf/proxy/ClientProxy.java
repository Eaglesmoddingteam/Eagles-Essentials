package btf.proxy;

import btf.client.RenderHeatBall;
import btf.objects.blocks.tiles.heaters.HeaterBlazeing;
import btf.objects.blocks.tiles.heaters.HeaterEnder;
import btf.objects.entity.EntityHeatBall;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.Item;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.client.registry.RenderingRegistry;

public class ClientProxy extends ServerProxy {

	@Override
	public void registerItemRenderer(Item item, int meta, String ID) {
		ModelLoader.setCustomModelResourceLocation(item, meta, new ModelResourceLocation(item.getRegistryName(), ID));
	}

	@Override
	public String localize(String unlocalized, Object... args) {
		return I18n.format(unlocalized, args);
	}

	@Override
	public void openGUI(GuiScreen guiScreenIn) {
		Minecraft.getMinecraft().displayGuiScreen(guiScreenIn);
	}

	@Override
	public void registerRenders() {
		ClientRegistry.bindTileEntitySpecialRenderer(HeaterBlazeing.class, HeaterBlazeing.TESR);
		ClientRegistry.bindTileEntitySpecialRenderer(HeaterEnder.class, HeaterEnder.TESR);
		RenderingRegistry.registerEntityRenderingHandler(EntityHeatBall.class, new RenderHeatBall.Factory());
	}

}