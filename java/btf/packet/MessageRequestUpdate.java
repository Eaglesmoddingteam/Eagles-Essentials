package btf.packet;

import io.netty.buffer.ByteBuf;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class MessageRequestUpdate implements IMessage {

	private BlockPos pos;

	public MessageRequestUpdate(BlockPos pos) {
		this.pos = pos;
	}

	public MessageRequestUpdate() {

	}

	@Override
	public void fromBytes(ByteBuf buf) {
		pos = BlockPos.fromLong(buf.readLong());
	}

	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeLong(pos.toLong());
	}

	public static class Handle implements IMessageHandler<MessageRequestUpdate, MessageUpdateTE> {

		@Override
		public MessageUpdateTE onMessage(MessageRequestUpdate message, MessageContext ctx) {
			TileEntity toSend = ctx.getServerHandler().player.world.getTileEntity(message.pos);
			if (toSend != null) {
				return new MessageUpdateTE(toSend.writeToNBT(new NBTTagCompound()));
			}
			return null;
		}

	}

}
