package btf.objects.blocks.tiles;

import btf.init.BlockInit;
import net.minecraft.block.Block;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.init.Items;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;

import java.util.List;

public class TileAccumulator extends TileEntity implements ITickable {

    int tick = 0;
    private EnumFacing[] HORIZONTAL_FACINGS = {EnumFacing.NORTH, EnumFacing.EAST, EnumFacing.SOUTH, EnumFacing.WEST};

    @Override
    public void update() {
        if (++tick == 10 && !world.isRemote) {
            if (checkState()) {
                List<EntityItem> entities = world.getEntitiesWithinAABB(EntityItem.class, //
                        new AxisAlignedBB(pos.getX(), pos.getY() + 1, pos.getZ(), pos.getX() + 1, pos.getY() + 2,
                                pos.getZ() + 1));
                for (EntityItem item : entities)//
                {
                    if (canTransform(item.getItem()))
                        if (item.isGlowing()) {
                            if (item.ticksExisted >= 1000) {
                                InventoryHelper.spawnItemStack(//
                                        world, pos.getX(), pos.getY() + 2, pos.getZ(), getTransformed(item.getItem()));
                                item.setDead();
                            }
                        } else {
                            item.setGlowing(true);
                            item.ticksExisted = 0;
                            item.setInfinitePickupDelay();
                        }
                }
            } else {
                for (EntityItem item : world.getEntitiesWithinAABB(EntityItem.class, new AxisAlignedBB(pos, pos.up())))
                    item.setGlowing(false);
            }
            tick = 0;
        }
    }

    private ItemStack getTransformed(ItemStack stack) {
        if (stack.getItem() == Items.APPLE) {
            return new ItemStack(Items.GOLDEN_APPLE);
        }
        return ItemStack.EMPTY;
    }

    private boolean canTransform(ItemStack item) {
        return item.getItem() == Items.APPLE;
    }

    private boolean checkState() {
        boolean flag = true;
        BlockPos base = pos.up();
        for (EnumFacing facing : HORIZONTAL_FACINGS) {
            Block b = world.getBlockState(base.offset(facing)).getBlock();
            flag &= b == BlockInit.impossibilium_Accumulator;
        }
        return flag;
    }

}
