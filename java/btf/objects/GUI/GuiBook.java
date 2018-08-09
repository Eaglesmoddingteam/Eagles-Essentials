package btf.objects.GUI;

import btf.main.Vars;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.util.ResourceLocation;

public class GuiBook extends GuiScreen {
	private static final ResourceLocation BG_TEXTURE = new ResourceLocation(Vars.MOD_ID, "textures/gui/book.png");

	public GuiBook() {
		width = Minecraft.getMinecraft().displayWidth;
		height = Minecraft.getMinecraft().displayHeight;
		drawHoveringText("hey",  2, 2);
	}
	
	@Override
	public void onResize(Minecraft mcIn, int w, int h) {
		this.width = w;
		this.height = h;
		super.onResize(mcIn, w, h);
	}

}
