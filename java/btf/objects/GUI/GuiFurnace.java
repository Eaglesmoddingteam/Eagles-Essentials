package btf.objects.GUI;


import btf.main.Vars;
import btf.objects.blocks.tiles.TileFurnaceMultiBlock;
import btf.objects.blocks.tiles.container.ContainerFurnaceMultiBlock;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.util.ResourceLocation;

public class GuiFurnace extends GuiContainer {
	public static final int WIDTH = 176;
	public static final int HEIGHT = 166;

	private static final ResourceLocation background = new ResourceLocation(Vars.MOD_ID,
			"textures/gui/furnace.png");

	public GuiFurnace(TileFurnaceMultiBlock tileEntity, ContainerFurnaceMultiBlock container) {
		super(container);
		xSize = WIDTH;
		ySize = HEIGHT;
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
		this.drawDefaultBackground();
		mc.getTextureManager().bindTexture(background);
		drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);
	}
}
