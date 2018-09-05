package btf.util.handlers;


import btf.init.BlockInit;
import btf.init.ItemInit;
import btf.main.Vars;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod.EventBusSubscriber
public class RegistryHandler {

	@SubscribeEvent
	public static void onItemRegister(RegistryEvent.Register<Item> event) {
		ItemInit.register(event.getRegistry());
		BlockInit.registerItemBlocks(event.getRegistry());
	}

	@SubscribeEvent
	public static void onBlockRegister(RegistryEvent.Register<Block> event) {
		BlockInit.register(event.getRegistry());
	}

	@SubscribeEvent
	public static void onModelRegister(ModelRegistryEvent event) {
		ItemInit.registerModels(event);
		BlockInit.registerModels(event);
	}
}
