package fr.flastar.magiqolsky.chatmanager;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.CheckboxWidget;
import net.minecraft.client.gui.widget.TextWidget;
import net.minecraft.text.Text;

public class AutoCommandConfigurationScreen extends Screen {
    private final Screen parent;

    public AutoCommandConfigurationScreen(Screen parent) {
        super(Text.of("Configuration de l'auto command"));
        this.parent = parent;
    }

    @Override
    protected void init() {
        TextWidget autoCommandsConfig = new TextWidget(this.width / 2 - 300, 15, 300, 15, Text.literal("Configuration des auto commandes"), this.textRenderer);

        CheckboxWidget autoFlyCheckbox = CheckboxWidget.builder(Text.literal("Activer l'Auto-Fly (sur l'île uniquement)"), this.textRenderer)
                .pos(this.width / 2, 30)
                .checked(AutoCommandConfig.getConfig().isAutoFlyingEnabled())
                .callback((checkbox, checked) -> {
                    AutoCommandConfig.getConfig().changeIsAutoFlyingEnabled(checked);
                    AutoCommandConfig.save();
                })
                .build();

        this.addDrawableChild(autoCommandsConfig);

        this.addDrawableChild(autoFlyCheckbox);
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        super.render(context, mouseX, mouseY, delta);
    }

    @Override
    public void close() {
        if (client != null) {
            client.setScreen(parent);
        }
    }
}
