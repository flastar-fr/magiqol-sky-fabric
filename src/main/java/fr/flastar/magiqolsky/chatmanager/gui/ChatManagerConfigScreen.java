package fr.flastar.magiqolsky.chatmanager.gui;

import fr.flastar.magiqolsky.chatmanager.ChatManagerConfig;
import fr.flastar.magiqolsky.chatmanager.TextReplacerEntry;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.tooltip.Tooltip;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.CheckboxWidget;
import net.minecraft.client.gui.widget.TextWidget;
import net.minecraft.text.Text;

public class ChatManagerConfigScreen extends Screen {
    private final Screen parent;
    private ReplacementListWidget list;

    public ChatManagerConfigScreen(Screen parent) {
        super(Text.literal("Configuration Chat"));
        this.parent = parent;
    }

    @Override
    protected void init() {
        int center = this.width / 2;

        int currentY = 10;
        addDrawableChild(new TextWidget(center - 100, currentY, 200, 20, Text.literal("Paramètres Généraux"), textRenderer));

        currentY += 25;

        drawCheckboxesConfig(center, currentY);

        currentY += 30;

        addDrawableChild(new TextWidget(center - 200, currentY, 200, 20, Text.literal("Paramètres Généraux"), textRenderer));

        addDrawableChild(ButtonWidget.builder(Text.literal("+"), button -> {
            ChatManagerConfig.getConfig().textReplacers().add(new TextReplacerEntry("", ""));
            list.refreshEntries();
        }).dimensions(center - 13, currentY, 140, 20).build());

        list = new ReplacementListWidget(client, this.width, this.height - 135, 95, 25);
        addSelectableChild(list);

        addDrawableChild(ButtonWidget.builder(Text.literal("Retour et Sauvegarder"), button -> close())
                .dimensions(center - 100, this.height - 30, 200, 20).build());
    }

    private void drawCheckboxesConfig(int center, int currentY) {
        Text txt1 = Text.literal("Auto-Fly");
        Text txt2 = Text.literal("Better Bienvenue");
        Text txt3 = Text.literal("Remplacement");

        int w1 = 24 + textRenderer.getWidth(txt1);
        int w2 = 24 + textRenderer.getWidth(txt2);
        int w3 = 24 + textRenderer.getWidth(txt3);

        int spacing = 20;
        int totalWidth = w1 + w2 + w3 + (spacing * 2);
        int startX = center - (totalWidth / 2);

        addDrawableChild(CheckboxWidget.builder(txt1, textRenderer)
                .pos(startX, currentY)
                .checked(ChatManagerConfig.getConfig().isAutoFlyingEnabled())
                .callback((cb, checked) -> ChatManagerConfig.getConfig().changeIsAutoFlyingEnabled(checked))
                .tooltip(Tooltip.of(Text.literal("Activer l'Auto-Fly (sur l'île uniquement)")))
                .build());
        addDrawableChild(CheckboxWidget.builder(txt2, textRenderer)
                .pos(startX + w1 + spacing, currentY)
                .checked(ChatManagerConfig.getConfig().isBetterBienvenueEnabled())
                .callback((cb, checked) -> ChatManagerConfig.getConfig().changeIsBetterBienvenueEnabled(checked))
                .tooltip(Tooltip.of(Text.literal("Activer le better Bienvenue (envoi d'un message de Bienvenue en plus du /b)")))
                .build());
        addDrawableChild(CheckboxWidget.builder(txt3, textRenderer)
                .pos(startX + w1 + w2 + (spacing * 2), currentY)
                .checked(ChatManagerConfig.getConfig().isTextReplacementEnabled())
                .callback((cb, checked) -> ChatManagerConfig.getConfig().changeIsTextReplacementEnabled(checked))
                .tooltip(Tooltip.of(Text.literal("Activer les textes de remplacement")))
                .build());
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        super.render(context, mouseX, mouseY, delta);
        this.list.render(context, mouseX, mouseY, delta);
    }

    @Override
    public void close() {
        ChatManagerConfig.save();
        if (this.client != null) {
            this.client.setScreen(parent);
        }
    }
}
