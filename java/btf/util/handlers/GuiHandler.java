package btf.util.handlers;


import btf.objects.GUI.GuiFurnace;
import btf.objects.blocks.tiles.TileFurnaceMultiBlock;
import btf.objects.blocks.tiles.container.ContainerFurnaceMultiBlock;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;

import javax.annotation.Nullable;

public class GuiHandler implements IGuiHandler {

	public static final int furnace = 0;

	@Nullable
	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		BlockPos pos = new BlockPos(x, y, z);
		TileEntity te = world.getTileEntity(pos);
		switch (ID) {
			case furnace:
				TileFurnaceMultiBlock furnaceMultiBlock = (TileFurnaceMultiBlock) te;
				return new GuiFurnace(furnaceMultiBlock,
						new ContainerFurnaceMultiBlock(player.inventory, furnaceMultiBlock));
		}
		return null;
	}

	@Nullable
	@Override
	public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		BlockPos pos = new BlockPos(x, y, z);
		TileEntity te = world.getTileEntity(pos);
		switch (ID) {
			case furnace:
				TileFurnaceMultiBlock furnaceMultiBlock = (TileFurnaceMultiBlock) te;
				return new ContainerFurnaceMultiBlock(player.inventory, furnaceMultiBlock);
		}
		return null;
	}
}
