package btf.packet;

import java.io.IOException;

import org.apache.logging.log4j.Logger;

import btf.main.Main;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.PacketBuffer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class MessageUpdateTE implements IMessage {

	public static final Logger l = Main.LOGGER;

	NBTTagCompound compound;

	public MessageUpdateTE(NBTTagCompound compound) {
		this.compound = compound;
	}

	public MessageUpdateTE() {
		compound = new NBTTagCompound();
	}

	@Override
	public void fromBytes(ByteBuf buf) {
		PacketBuffer buffer = new PacketBuffer(buf);
		try {
			compound = buffer.readCompoundTag();
		} catch (IOException e) {
		}
	}

	@Override
	public void toBytes(ByteBuf buf) {
		PacketBuffer buffer = new PacketBuffer(buf);
		buffer.writeCompoundTag(compound);
	}

	public static class Handle implements IMessageHandler<MessageUpdateTE, IMessage> {

		@Override
		public IMessage onMessage(MessageUpdateTE message, MessageContext ctx) {
			Minecraft.getMinecraft().addScheduledTask(() -> {
				BlockPos pos = new BlockPos(message.compound.getInteger("x"), message.compound.getInteger("y"),
						message.compound.getInteger("z"));
				TileEntity te = Minecraft.getMinecraft().world.getTileEntity(pos);
				if(te != null) {
					te.readFromNBT(message.compound);
				}
			});
			return null;
		}

	}

}
