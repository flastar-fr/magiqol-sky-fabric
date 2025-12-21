package fr.flastar.magiqolsky.chatmanager;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.tooltip.Tooltip;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.CheckboxWidget;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.client.gui.widget.TextWidget;
import net.minecraft.text.Text;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
                .callback((checkbox, checked) -> ChatManagerConfig.getConfig().changeIsAutoFlyingEnabled(checked))
                .build();

        CheckboxWidget betterBienvenueCheckbox = CheckboxWidget.builder(Text.literal(BETTER_BIENVENUE_TEXT), this.textRenderer)
                .pos(this.width / 2 - 200, 45)
                .checked(ChatManagerConfig.getConfig().isBetterBienvenueEnabled())
                .callback((checkbox, checked) -> ChatManagerConfig.getConfig().changeIsBetterBienvenueEnabled(checked))
                .build();

        TextWidget textReplacerConfig = new TextWidget(this.width / 2 - 300, 80, 300, 15,
                Text.literal("Configuration des textes de remplacement"),
                this.textRenderer
        );

        ButtonWidget textReplacerAdder = ButtonWidget.builder(Text.literal("+"), button -> ChatManagerConfig.getConfig().addNewTextReplacer("", ""))
                .dimensions(width / 2, 80, 200, 15)
                .tooltip(Tooltip.of(Text.literal("Ajouter de nouvelles cases pour les remplacements de textes")))
                .build();

        addDrawableChild(autoCommandsConfig);

        addDrawableChild(autoFlyCheckbox);
        addDrawableChild(betterBienvenueCheckbox);

        addDrawableChild(textReplacerConfig);
        addDrawableChild(textReplacerAdder);

        drawTextReplacers();
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        super.render(context, mouseX, mouseY, delta);
    }

    @Override
    public void close() {
        ChatManagerConfig.save();
        if (client != null) {
            client.setScreen(parent);
        }
    }

    private void drawTextReplacers() {
        Map<String, List<String>> drawableTextReplacers = retrieveDrawableTextReplacers();

        int currentY = 95;
        for (Map.Entry<String, List<String>> entry : drawableTextReplacers.entrySet()) {
            TextFieldWidget textToReplaceWith = new TextFieldWidget(this.textRenderer, this.width/2 - 200, currentY, 100, 20, Text.literal(entry.getKey()));
            currentY += 15;
            addDrawableChild(textToReplaceWith);
            for (String textToSpot : entry.getValue()) {
                TextFieldWidget textToSpotText = new TextFieldWidget(this.textRenderer, this.width/2, currentY, 100, 20, Text.literal(textToSpot));
                addDrawableChild(textToSpotText);
                currentY += 20;
            }
            ButtonWidget addTextToSpot = ButtonWidget.builder(Text.literal("+"), button -> ChatManagerConfig.getConfig().addNewTextReplacer("", ""))
                    .dimensions(width/2, currentY, 100, 20)
                    .tooltip(Tooltip.of(Text.literal("Ajouter de nouveaux textes à rajouter")))
                    .build();
            addDrawableChild(addTextToSpot);
            currentY += 20;
        }
    }

    private Map<String, List<String>> retrieveDrawableTextReplacers() {
        Map<String, String> textReplacers = ChatManagerConfig.getConfig().textReplacers();

        return transformTextReplacersToDrawable(textReplacers);
    }

    private Map<String, List<String>> transformTextReplacersToDrawable(Map<String, String> textReplacers) {
        return textReplacers.entrySet().stream()
                .collect(Collectors.groupingBy(
                        Map.Entry::getValue,
                        Collectors.mapping(Map.Entry::getKey, Collectors.toList())
                ));
    }
}
