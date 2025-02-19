package fuzs.easyshulkerboxes.world.inventory;

import fuzs.easyshulkerboxes.EasyShulkerBoxes;
import fuzs.easyshulkerboxes.api.world.item.container.ContainerItemHelper;
import fuzs.easyshulkerboxes.api.world.inventory.ContainerItemProvider;
import fuzs.easyshulkerboxes.config.ServerConfig;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.tooltip.TooltipComponent;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.ShulkerBoxBlock;
import net.minecraft.world.level.block.entity.BlockEntityType;

import java.util.Optional;

public class ShulkerBoxProvider implements ContainerItemProvider {
    public static final ContainerItemProvider INSTANCE = new ShulkerBoxProvider();

    private ShulkerBoxProvider() {

    }

    @Override
    public SimpleContainer getItemContainer(Player player, ItemStack stack, boolean allowSaving) {
        return ContainerItemHelper.loadItemContainer(stack, BlockEntityType.SHULKER_BOX, 3, allowSaving);
    }

    @Override
    public boolean canAcceptItem(ItemStack stack) {
        return stack.getItem().canFitInsideContainerItems();
    }

    @Override
    public boolean isAllowed() {
        return EasyShulkerBoxes.CONFIG.get(ServerConfig.class).allowShulkerBox;
    }

    @Override
    public Optional<TooltipComponent> getTooltipImage(ItemStack stack) {
        DyeColor color = ShulkerBoxBlock.getColorFromItem(stack.getItem());
        return ContainerItemHelper.getTooltipImage(stack, BlockEntityType.SHULKER_BOX, 3, color);
    }
}
