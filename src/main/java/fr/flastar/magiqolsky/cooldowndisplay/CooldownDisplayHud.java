package fr.flastar.magiqolsky.cooldowndisplay;

import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.rendering.v1.HudLayerRegistrationCallback;
import net.fabricmc.fabric.api.client.rendering.v1.IdentifiedLayer;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.render.RenderTickCounter;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;

public class CooldownDisplayHud {
    private static final Identifier LAYER_ID = Identifier.of("magiqol-sky", "cooldowns_layer");
    private static final int WIDTH_HOTBAR = 182;
    private static final float TICK_PER_SECOND = 20.f;

    public static int foodCooldownTick = 400;
    public static ItemStack lastStackUsed = ItemStack.EMPTY;

    public static void register() {
        HudLayerRegistrationCallback.EVENT.register(
                layeredDrawer -> layeredDrawer.attachLayerBefore(IdentifiedLayer.CHAT, LAYER_ID, CooldownDisplayHud::render)
        );
        ClientTickEvents.START_CLIENT_TICK.register(client -> CooldownDisplayHud.tick());
        CustomFoodDetectionHandler.register();
    }

    private static void render(DrawContext context, RenderTickCounter tickCounter) {
        MinecraftClient client = MinecraftClient.getInstance();
        if (client == null || client.options.hudHidden) return;

        int width = client.getWindow().getScaledWidth();
        int height = client.getWindow().getScaledHeight();

        int x = width/2 + WIDTH_HOTBAR/2 + 2;
        int y = height - 20;

        if (foodCooldownTick <= 0) return;

        float secondsLeft = foodCooldownTick / TICK_PER_SECOND;
        String text = String.format("Cooldown: %.1fs", secondsLeft);
        context.drawText(client.textRenderer, text, x, y, 0xFFFFFF, true);
        int textWidth = client.textRenderer.getWidth(text);
        context.drawItem(lastStackUsed, x + 2 + textWidth, y - 4);
    }

    public static void tick() {
        if (foodCooldownTick > 0) {
            foodCooldownTick--;
        }
    }
}
