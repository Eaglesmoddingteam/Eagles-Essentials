package btf.client;

import btf.objects.entity.EntityHeatBall;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.entity.RenderZombie;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.client.registry.IRenderFactory;

public class RenderHeatBall extends net.minecraft.client.renderer.entity.Render<EntityHeatBall> {

	RenderZombie zombie;

	protected RenderHeatBall(RenderManager manager) {
		super(manager);
	}

	@Override
	protected ResourceLocation getEntityTexture(EntityHeatBall entity) {
		return TextureManager.RESOURCE_LOCATION_EMPTY;
	}

	@Override
	public void doRender(EntityHeatBall entity, double x, double y, double z, float entityYaw, float partialTicks) {
		//
	}

	public static class Factory implements IRenderFactory<EntityHeatBall> {

		@Override
		public Render<? super EntityHeatBall> createRenderFor(RenderManager manager) {
			return new RenderHeatBall(manager);
		}

	}
}
