package fr.flastar.magiqolsky.widgets;

import net.fabricmc.fabric.api.client.screen.v1.ScreenEvents;
import net.fabricmc.fabric.api.client.screen.v1.Screens;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.ingame.InventoryScreen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

import static fr.flastar.magiqolsky.MagiQoLSky.MOD_ID;

public class MobsCounterButton extends ButtonWidget {
    private static final Identifier ZOMBIE_TEXTURE = Identifier.of(MOD_ID, "textures/gui/zombie_head.png");

    public MobsCounterButton(int x, int y, PressAction onPress) {
        super(x, y, 20, 20, Text.empty(), onPress, DEFAULT_NARRATION_SUPPLIER);
    }

    @Override
    protected void renderWidget(DrawContext context, int mouseX, int mouseY, float delta) {
        super.renderWidget(context, mouseX, mouseY, delta);

        context.drawTexture(
                RenderLayer::getGuiTextured,
                ZOMBIE_TEXTURE,
                getX() + 2, getY() + 2,
                0.0f, 0.0f,
                16, 16,
                16, 16
        );
    }

    public static void registerMobsCounterButton() {
        ScreenEvents.AFTER_INIT.register((client, screen, scaledWidth, scaledHeight) -> {

            if (screen instanceof InventoryScreen) {
                int x = 10;
                int y = scaledHeight - 30;

                MobsCounterButton myButton = new MobsCounterButton(x, y, button -> {
                    if (client.player != null) {
                        client.player.sendMessage(Text.literal("Grrr !"), false);
                    }
                });

                Screens.getButtons(screen).add(myButton);
            }
        });
    }
}
