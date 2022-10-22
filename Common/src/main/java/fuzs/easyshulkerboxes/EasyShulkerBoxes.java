package fuzs.easyshulkerboxes;

import fuzs.easyshulkerboxes.api.world.item.container.ContainerItemProvider;
import fuzs.easyshulkerboxes.api.world.item.container.ContainerItemHelper;
import fuzs.easyshulkerboxes.config.ClientConfig;
import fuzs.easyshulkerboxes.config.ServerConfig;
import fuzs.easyshulkerboxes.init.ModRegistry;
import fuzs.easyshulkerboxes.network.S2CEnderChestSetContentMessage;
import fuzs.easyshulkerboxes.network.S2CEnderChestSetSlotMessage;
import fuzs.easyshulkerboxes.network.client.C2SEnderChestMenuMessage;
import fuzs.easyshulkerboxes.network.client.C2SEnderChestSetSlotMessage;
import fuzs.puzzleslib.config.ConfigHolder;
import fuzs.puzzleslib.core.CommonFactories;
import fuzs.puzzleslib.core.ModConstructor;
import fuzs.puzzleslib.network.MessageDirection;
import fuzs.puzzleslib.network.NetworkHandler;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.EnderChestBlock;
import net.minecraft.world.level.block.ShulkerBoxBlock;
import net.minecraft.world.level.block.entity.BlockEntityType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EasyShulkerBoxes implements ModConstructor {
    public static final String MOD_ID = "easyshulkerboxes";
    public static final String MOD_NAME = "Easy Shulker Boxes";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_NAME);

    public static final NetworkHandler NETWORK = CommonFactories.INSTANCE.network(MOD_ID);
    @SuppressWarnings("Convert2MethodRef")
    public static final ConfigHolder CONFIG = CommonFactories.INSTANCE
            .clientConfig(ClientConfig.class, () -> new ClientConfig())
            .serverConfig(ServerConfig.class, () -> new ServerConfig());

    @Override
    public void onConstructMod() {
        CONFIG.bakeConfigs(MOD_ID);
        ModRegistry.touch();
        registerMessages();
    }

    private static void registerMessages() {
        NETWORK.register(S2CEnderChestSetContentMessage.class, S2CEnderChestSetContentMessage::new, MessageDirection.TO_CLIENT);
        NETWORK.register(S2CEnderChestSetSlotMessage.class, S2CEnderChestSetSlotMessage::new, MessageDirection.TO_CLIENT);
        NETWORK.register(C2SEnderChestSetSlotMessage.class, C2SEnderChestSetSlotMessage::new, MessageDirection.TO_SERVER);
        NETWORK.register(C2SEnderChestMenuMessage.class, C2SEnderChestMenuMessage::new, MessageDirection.TO_SERVER);
    }

    @Override
    public void onCommonSetup() {
        ContainerItemProvider.registerItemLike(ShulkerBoxBlock.class, (Player player, ItemStack stack) -> {
            return () -> ContainerItemHelper.loadItemContainer(stack, BlockEntityType.SHULKER_BOX, 3, false);
        });
        ContainerItemProvider.registerItemLike(EnderChestBlock.class, (Player player, ItemStack stack) -> {
            return player::getEnderChestInventory;
        });
    }
}
