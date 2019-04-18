package btf.init;

import btf.main.Main;
import btf.main.Vars;
import btf.objects.blocks.BlockBase;
import btf.objects.blocks.fluid.BlockFluid;
import btf.util.reflect.AddFluid;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.BlockFluidClassic;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.registries.IForgeRegistry;

import javax.annotation.Nonnull;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;

/**
 * old and rusty, inefficient code, should have a rewrite
 *
 * @author Tebreca
 */
public class FluidInit {

    protected static final List<Fluid> fluids = new ArrayList();

    protected static final List<Block> fluidBlocks = new ArrayList<>();
    protected static final Map<Block, Fluid> moltenVariants = new HashMap<>();
    private static final List<Callable<BlockFluidClassic>> blocktasks = new ArrayList<>();

    public static void preInit() {
        ReflectCollector.collectFrom(BlockInit.class);
        registerFluids();
    }

    public static List<Fluid> getFluids() {
        return fluids;
    }

    public static List<Block> getFluidblocks() {
        return fluidBlocks;
    }

    public static Map<Block, Fluid> getMoltenvariants() {
        return moltenVariants;
    }

    public static void registerBlocks(IForgeRegistry<Block> registry) {
        blocktasks.forEach(task -> {
            try {
                BlockFluidClassic b = task.call();
                b.setRegistryName("fluid_block_" + b.getFluid().getUnlocalizedName());
            } catch (Exception e) {
                Main.LOGGER.catching(e);
            }
        });
        registry.registerAll(fluidBlocks.toArray(new Block[0]));
    }

    public static void registerFluids() {
        for (Fluid fluid : fluids) {
            FluidRegistry.registerFluid(fluid);
            FluidRegistry.addBucketForFluid(fluid);
        }
    }

    public static Fluid forMetal(@Nonnull Block block) {
        ResourceLocation location = block.getRegistryName();
        Fluid fluid = new Fluid("metal_" + location.getResourcePath(), genPathStill(block), genPathFlowing(block));
        fluid.setUnlocalizedName("fluid_molten_" + location.getResourcePath());
        fluids.add(fluid);
        blocktasks.add(() -> {
            BlockFluidClassic block_fluid = new BlockFluid(fluid, Material.LAVA);
            fluidBlocks.add(block_fluid);
            moltenVariants.put(block, fluid);
            return block_fluid;
        });
        return fluid;
    }

    private static ResourceLocation genPathFlowing(Block block) {
        return new ResourceLocation(Vars.MOD_ID, genPath(block) + "flowing");
    }

    private static String genPath(Block block) {
        ResourceLocation location = block.getRegistryName();
        return "fluid/" + location.getResourcePath() + "/";
    }

    private static ResourceLocation genPathStill(Block block) {
        return new ResourceLocation(Vars.MOD_ID, genPath(block) + "still");
    }

    private static class ReflectCollector {

        public static void collectFrom(Class clazz) {
            for (Field field : clazz.getFields()) {
                if (field.isAnnotationPresent(AddFluid.class) && field.getType() == BlockBase.class) {
                    try {
                        BlockBase base = (BlockBase) field.get(null);
                        FluidInit.forMetal(base);
                    } catch (Exception e) {
                        Main.LOGGER.catching(e);
                    }
                }
            }
        }

    }
}
