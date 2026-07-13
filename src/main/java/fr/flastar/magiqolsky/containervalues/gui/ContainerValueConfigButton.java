package fr.flastar.magiqolsky.containervalues.gui;

import net.fabricmc.fabric.api.client.screen.v1.ScreenEvents;
import net.fabricmc.fabric.api.client.screen.v1.Screens;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.inventory.CreativeModeInventoryScreen;
import net.minecraft.client.gui.screens.inventory.InventoryScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;

import static fr.flastar.magiqolsky.MagiQoLSky.MOD_ID;

public class ContainerValueConfigButton extends Button {
    private static final Identifier CONTAINER_TEXTURE = Identifier.fromNamespaceAndPath(MOD_ID, "textures/gui/container_icon.png");

    private static final int BUTTON_SIZE = 26;
    private static final int TEXTURE_SIZE = 16;
    private static final int TEXTURE_OFFSET = (BUTTON_SIZE - TEXTURE_SIZE) / 2;

    private static final int BUTTON_X_COORDINATE = 10;
    private static final int BUTTON_OFFSET = 30;

    public ContainerValueConfigButton(int x, int y, OnPress onPress) {
        super(x, y, BUTTON_SIZE, BUTTON_SIZE, Component.empty(), onPress, DEFAULT_NARRATION);
    }

    @Override
    protected void extractContents(GuiGraphicsExtractor graphics, int mouseX, int mouseY, float delta) {
        graphics.blit(
                CONTAINER_TEXTURE,
                getX() + TEXTURE_OFFSET,
                getY() + TEXTURE_OFFSET,
                getX() + TEXTURE_OFFSET + TEXTURE_SIZE,
                getY() + TEXTURE_OFFSET + TEXTURE_SIZE,
                0.0f, 1.0f,
                0.0f, 1.0f
        );
    }

    public static void registerContainerValueConfigurationButton() {
        ScreenEvents.AFTER_INIT.register((_, screen, _, scaledHeight) -> {

            if (screen instanceof InventoryScreen || screen instanceof CreativeModeInventoryScreen) {
                int y = scaledHeight - BUTTON_OFFSET - TEXTURE_SIZE * 2;

                ContainerValueConfigButton myButton = new ContainerValueConfigButton(
                        BUTTON_X_COORDINATE, y, _ -> Minecraft.getInstance().setScreen(
                        new ContainerValueConfigScreen(screen)
                ));

                Screens.getWidgets(screen).add(myButton);
            }
        });
    }
}