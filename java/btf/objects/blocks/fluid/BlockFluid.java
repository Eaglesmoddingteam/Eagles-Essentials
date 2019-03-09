package btf.objects.blocks.fluid;

import net.minecraft.block.material.Material;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fluids.BlockFluidClassic;
import net.minecraftforge.fluids.Fluid;

public class BlockFluid extends BlockFluidClassic{

	public BlockFluid(Fluid fluid, Material material) {
		super(fluid, material);
	}
	
	@Override
	protected void flowIntoBlock(World world, BlockPos pos, int meta) {
		if(!canFlowInto(world, pos)) {
			return;
		}
		super.flowIntoBlock(world, pos, meta);
	}
}
