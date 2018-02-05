package btf.objects.GUI;


import btf.main.Vars;
import net.minecraft.client.gui.Gui;
import net.minecraft.util.ResourceLocation;

public class GuiBook extends Gui {
	private static final ResourceLocation BG_TEXTURE = new ResourceLocation(Vars.MOD_ID, "textures/gui/book.png");
	public GuiBook() {
		drawRect(100, 20, 200, 220, 0);
	}

}
