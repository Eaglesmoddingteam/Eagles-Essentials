package btf.util.seasons;


import net.minecraft.client.Minecraft;
import net.minecraft.world.World;
import net.minecraftforge.event.terraingen.DecorateBiomeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class SeasonsClientRenderer {
	private final Minecraft mcIn;
	private final World worldIn;

	public SeasonsClientRenderer(Minecraft mc, World world) {
		mcIn = mc;
		worldIn = world;
	}

	@SideOnly(Side.CLIENT)
	public void Initchunkload(DecorateBiomeEvent event) {

	}
}
