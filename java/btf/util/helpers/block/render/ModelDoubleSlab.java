package btf.util.helpers.block.render;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

import btf.main.Vars;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.block.model.ItemOverrideList;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.vertex.VertexFormat;
import net.minecraft.client.resources.IResourceManager;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.ICustomModelLoader;
import net.minecraftforge.client.model.IModel;
import net.minecraftforge.common.model.IModelState;

public class ModelDoubleSlab<T, E> implements IModel {

	@Override
	public IBakedModel bake(IModelState state, VertexFormat format,
			Function<ResourceLocation, TextureAtlasSprite> bakedTextureGetter) {

		return null;
	}

	
	public class ModelDoubleSlabLoader implements ICustomModelLoader {

		@Override
		public void onResourceManagerReload(IResourceManager resourceManager) {
			
		}

		@Override
		public boolean accepts(ResourceLocation modelLocation) {
			return false;
		}

		@Override
		public IModel loadModel(ResourceLocation modelLocation) throws Exception {
			return null;
		}
		
	}
	
	private class BakedDoubleSlab implements IBakedModel {

		@Override
		public List<BakedQuad> getQuads(IBlockState state, EnumFacing side, long rand) {
			List<BakedQuad> quads = new ArrayList();

			return null;
		}

		@Override
		public boolean isAmbientOcclusion() {
			return true;
		}

		@Override
		public boolean isGui3d() {
			return false;
		}

		@Override
		public boolean isBuiltInRenderer() {
			return false;
		}

		@Override
		public TextureAtlasSprite getParticleTexture() {
			return (TextureAtlasSprite) Minecraft.getMinecraft().renderEngine.getTexture(new ResourceLocation(Vars.MOD_ID, "machines/wooden_casing"));
		}

		@Override
		public ItemOverrideList getOverrides() {
			return null;
		}
		
	}
}
