package fr.flastar.magiqolsky.cooldowndisplay.gui;

import fr.flastar.magiqolsky.cooldowndisplay.CustomFoodDetectionHandler;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.rendering.v1.hud.HudElementRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.hud.VanillaHudElements;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.world.item.ItemStack;
import net.minecraft.resources.Identifier;

import java.util.ArrayList;
import java.util.List;

import static fr.flastar.magiqolsky.utils.IDFromStack.retrieveIDFromStack;

public class CooldownDisplayHud {
    private static final Identifier LAYER_ID = Identifier.fromNamespaceAndPath("magiqol-sky", "cooldowns");
    private static final int WIDTH_HOTBAR = 182;
    private static final int Y_OFFSET = 25;

    public static final float TICK_PER_SECOND = 20.f;

    private static CooldownDisplayWidget customFoodDisplayWidget;
    private static List<CooldownDisplayWidget> cooldownDisplayWidgets;

    public static void register() {
        HudElementRegistry.attachElementBefore(
                VanillaHudElements.CHAT,
                LAYER_ID,
                (context, _) -> CooldownDisplayHud.render(context)
        );

        ClientTickEvents.START_CLIENT_TICK.register(_ -> CooldownDisplayHud.tick());
        CustomFoodDetectionHandler.register();

        cooldownDisplayWidgets = new ArrayList<>();
    }

    private static void render(GuiGraphicsExtractor context) {
        Minecraft client = Minecraft.getInstance();
        if (client.options.hideGui) return;

        int width = context.guiWidth();
        int height = context.guiHeight();

        if (customFoodDisplayWidget != null && customFoodDisplayWidget.isAlive()) {
            int x = width / 2 + WIDTH_HOTBAR / 2 + 5;
            int y = height - Y_OFFSET;
            customFoodDisplayWidget.render(context, x, y, false);
        }

        int currentY = height - Y_OFFSET;
        for (CooldownDisplayWidget widget : cooldownDisplayWidgets) {
            if (widget.isAlive()) {
                widget.render(context, width - 20, currentY, true);
                currentY -= 20;
            }
        }
    }

    public static void tick() {
        if (customFoodDisplayWidget != null) customFoodDisplayWidget.tick();

        cooldownDisplayWidgets.removeIf(widget -> {
            widget.tick();
            return !widget.isAlive();
        });
    }

    public static void setCustomFoodCooldown(int cooldown, ItemStack stack) {
        if (customFoodDisplayWidget == null) {
            customFoodDisplayWidget = new CooldownDisplayWidget();
        }
        customFoodDisplayWidget.setCooldown(cooldown, stack);
    }

    public static void addBoostCooldown(int cooldown, ItemStack stack) {
        if (isBoostPotionAlreadyInUsed(stack)) return;
        CooldownDisplayWidget widget = new CooldownDisplayWidget();
        widget.setCooldown(cooldown, stack);
        cooldownDisplayWidgets.add(widget);
    }

    private static boolean isBoostPotionAlreadyInUsed(ItemStack stack) {
        String stackNbt = retrieveIDFromStack(stack);
        for (CooldownDisplayWidget widget : cooldownDisplayWidgets) {
            if (widget.getNbt().equals(stackNbt)) return true;
        }
        return false;
    }
}
