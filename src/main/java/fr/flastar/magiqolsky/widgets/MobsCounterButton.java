package fr.flastar.magiqolsky.widgets;

import fr.flastar.magiqolsky.screens.MobCountingScreen;
import net.fabricmc.fabric.api.client.screen.v1.ScreenEvents;
import net.fabricmc.fabric.api.client.screen.v1.Screens;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.ingame.CreativeInventoryScreen;
import net.minecraft.client.gui.screen.ingame.InventoryScreen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

import static fr.flastar.magiqolsky.MagiQoLSky.MOD_ID;

public class MobsCounterButton extends ButtonWidget {
    private static final Identifier ZOMBIE_TEXTURE = Identifier.of(MOD_ID, "textures/gui/zombie_head.png");

    private static final int BUTTON_SIZE = 26;
    private static final int TEXTURE_SIZE = 16;
    private static final int TEXTURE_OFFSET = (BUTTON_SIZE - TEXTURE_SIZE) / 2;

    private static final int BUTTON_X_COORDINATE = 10;
    private static final int BUTTON_OFFSET = 30;

    public MobsCounterButton(int x, int y, PressAction onPress) {
        super(x, y, BUTTON_SIZE, BUTTON_SIZE, Text.empty(), onPress, DEFAULT_NARRATION_SUPPLIER);
    }

    @Override
    protected void renderWidget(DrawContext context, int mouseX, int mouseY, float delta) {
        super.renderWidget(context, mouseX, mouseY, delta);

        context.drawTexture(
                RenderLayer::getGuiTextured,
                ZOMBIE_TEXTURE,
                getX() + TEXTURE_OFFSET, getY() + TEXTURE_OFFSET,
                0.0f, 0.0f,
                TEXTURE_SIZE, TEXTURE_SIZE,
                TEXTURE_SIZE, TEXTURE_SIZE
        );
    }

    public static void registerMobsCounterButton() {
        ScreenEvents.AFTER_INIT.register((client, screen, scaledWidth, scaledHeight) -> {

            if (screen instanceof InventoryScreen || screen instanceof CreativeInventoryScreen) {
                int y = scaledHeight - BUTTON_OFFSET;

                MobsCounterButton myButton = new MobsCounterButton(BUTTON_X_COORDINATE, y, button -> {
                    MinecraftClient.getInstance().setScreen(
                            new MobCountingScreen(screen)
                    );
                });

                Screens.getButtons(screen).add(myButton);
            }
        });
    }
}
