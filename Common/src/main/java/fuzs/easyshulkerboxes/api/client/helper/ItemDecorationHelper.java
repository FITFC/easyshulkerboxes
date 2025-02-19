package fuzs.easyshulkerboxes.api.client.helper;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.Tesselator;
import fuzs.puzzleslib.client.renderer.entity.DynamicItemDecorator;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.world.item.ItemStack;

import java.util.function.BooleanSupplier;

public class ItemDecorationHelper {

    public static DynamicItemDecorator getDynamicItemDecorator(ItemDecoratorPredicate filter, BooleanSupplier allow) {
        return (Font font, ItemStack stack, int itemPosX, int itemPosY, float blitOffset) -> registerContainerItemDecoration(font, stack, itemPosX, itemPosY, blitOffset, filter, allow);
    }

    @SuppressWarnings("ConstantConditions")
    private static boolean registerContainerItemDecoration(Font font, ItemStack stack, int itemPosX, int itemPosY, float blitOffset, ItemDecoratorPredicate filter, BooleanSupplier allow) {
        if (!allow.getAsBoolean()) return false;
        if (stack.getCount() == 1) {
            if (Minecraft.getInstance().screen instanceof AbstractContainerScreen<?> screen) {
                ItemStack carriedStack = screen.getMenu().getCarried();
                if (!carriedStack.isEmpty() && stack != carriedStack && carriedStack.getItem().canFitInsideContainerItems() && filter.test(screen, stack, carriedStack)) {
                    PoseStack posestack = new PoseStack();
                    String s = "+";
                    posestack.translate(0.0, 0.0, blitOffset + 200.0);
                    MultiBufferSource.BufferSource multibuffersource$buffersource = MultiBufferSource.immediate(Tesselator.getInstance().getBuilder());
                    font.drawInBatch(s, (float) (itemPosX + 19 - 2 - font.width(s)), (float) (itemPosY + 6 + 3), ChatFormatting.YELLOW.getColor(), true, posestack.last().pose(), multibuffersource$buffersource, false, 0, 15728880);
                    multibuffersource$buffersource.endBatch();
                    // font renderer modifies render states, so this tells the implementation to reset them
                    return true;
                }
            }
        }
        return false;
    }

    @FunctionalInterface
    public interface ItemDecoratorPredicate {

        boolean test(AbstractContainerScreen<?> screen, ItemStack stack, ItemStack carriedStack);
    }
}
