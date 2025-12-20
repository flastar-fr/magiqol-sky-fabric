package fr.flastar.magiqolsky.chatmanager;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.CheckboxWidget;
import net.minecraft.client.gui.widget.TextWidget;
import net.minecraft.text.Text;

public class ChatManagerConfigScreen extends Screen {
    private static final String AUTO_FLY_TEXT = "Activer l'Auto-Fly (sur l'île uniquement)";
    private static final String BETTER_BIENVENUE_TEXT = "Activer le better Bienvenue (envoi d'un message de Bienvenue en plus du /b)";

    private final Screen parent;

    public ChatManagerConfigScreen(Screen parent) {
        super(Text.of("Configuration des paramètres de chat"));
        this.parent = parent;
    }

    @Override
    protected void init() {
        TextWidget autoCommandsConfig = new TextWidget(this.width / 2 - 300, 15, 300, 15,
                Text.literal("Configuration des auto commandes"),
                this.textRenderer
        );

        CheckboxWidget autoFlyCheckbox = CheckboxWidget.builder(Text.literal(AUTO_FLY_TEXT), this.textRenderer)
                .pos(this.width / 2 - 200, 30)
                .checked(ChatManagerConfig.getConfig().isAutoFlyingEnabled())
                .callback((checkbox, checked) -> {
                    ChatManagerConfig.getConfig().changeIsAutoFlyingEnabled(checked);
                    ChatManagerConfig.save();
                })
                .build();

        CheckboxWidget betterBienvenueCheckbox = CheckboxWidget.builder(Text.literal(BETTER_BIENVENUE_TEXT), this.textRenderer)
                .pos(this.width / 2 - 200, 45)
                .checked(ChatManagerConfig.getConfig().isBetterBienvenueEnabled())
                .callback((checkbox, checked) -> {
                    ChatManagerConfig.getConfig().changeIsBetterBienvenueEnabled(checked);
                    ChatManagerConfig.save();
                })
                .build();

        TextWidget textReplacerConfig = new TextWidget(this.width / 2 - 300, 15, 300, 15,
                Text.literal("Configuration des textes de remplacement"),
                this.textRenderer
        );

        addDrawableChild(autoCommandsConfig);

        addDrawableChild(autoFlyCheckbox);
        addDrawableChild(betterBienvenueCheckbox);

        addDrawableChild(textReplacerConfig);
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
