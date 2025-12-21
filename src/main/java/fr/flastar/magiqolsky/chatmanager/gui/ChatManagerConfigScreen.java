package fr.flastar.magiqolsky.chatmanager.gui;

import fr.flastar.magiqolsky.chatmanager.ChatManagerConfig;
import fr.flastar.magiqolsky.chatmanager.TextReplacerEntry;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.tooltip.Tooltip;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.CheckboxWidget;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.client.gui.widget.TextWidget;
import net.minecraft.text.Text;

import java.util.List;

public class ChatManagerConfigScreen extends Screen {
    private final Screen parent;

    public ChatManagerConfigScreen(Screen parent) {
        super(Text.of("Configuration des paramètres de chat"));
        this.parent = parent;
    }

    @Override
    protected void init() {
        int currentY = 20;
        int center = this.width / 2;

        addDrawableChild(new TextWidget(center - 100, currentY, 400, 20, Text.literal("Auto Commandes"), textRenderer)).alignLeft();
        currentY += 25;

        addDrawableChild(CheckboxWidget.builder(Text.literal("Auto-Fly"), textRenderer)
                .pos(center - 100, currentY)
                .checked(ChatManagerConfig.getConfig().isAutoFlyingEnabled())
                .callback((cb, checked) -> ChatManagerConfig.getConfig().changeIsAutoFlyingEnabled(checked))
                .tooltip(Tooltip.of(Text.literal("Activer l'Auto-Fly (sur l'île uniquement)")))
                .build());
        currentY += 30;
        addDrawableChild(CheckboxWidget.builder(Text.literal("Better Bienvenue"), textRenderer)
                .pos(center - 100, currentY)
                .checked(ChatManagerConfig.getConfig().isAutoFlyingEnabled())
                .callback((cb, checked) -> ChatManagerConfig.getConfig().changeIsBetterBienvenueEnabled(checked))
                .tooltip(Tooltip.of(Text.literal("Activer le better Bienvenue (envoi d'un message de Bienvenue en plus du /b)")))
                .build());
        currentY += 30;
        addDrawableChild(CheckboxWidget.builder(Text.literal("Textes de remplacement"), textRenderer)
                .pos(center - 100, currentY)
                .checked(ChatManagerConfig.getConfig().isAutoFlyingEnabled())
                .callback((cb, checked) -> ChatManagerConfig.getConfig().changeIsBetterBienvenueEnabled(checked))
                .tooltip(Tooltip.of(Text.literal("Activer les textes de remplacement")))
                .build());
        currentY += 40;

        addDrawableChild(new TextWidget(center - 100, currentY, 400, 20, Text.literal("Remplacements de texte"), textRenderer)).alignLeft();

        addDrawableChild(ButtonWidget.builder(Text.literal("+"), button -> {
            ChatManagerConfig.getConfig().textReplacers().add(new TextReplacerEntry("", ""));
            this.clearAndInit();
        }).dimensions(center + 50, currentY, 60, 20).build());

        currentY += 30;

        List<TextReplacerEntry> entries = ChatManagerConfig.getConfig().textReplacers();

        for (int i = 0; i < entries.size(); i++) {
            TextReplacerEntry entry = entries.get(i);
            final int index = i;

            TextFieldWidget keyField = new TextFieldWidget(textRenderer, center - 160, currentY, 140, 20, Text.literal(""));
            keyField.setText(entry.key);
            keyField.setChangedListener(newText -> entry.key = newText);
            keyField.setPlaceholder(Text.literal("Mot à détecter"));
            addDrawableChild(keyField);

            TextFieldWidget valField = new TextFieldWidget(textRenderer, center + 5, currentY, 140, 20, Text.literal(""));
            valField.setText(entry.value);
            valField.setChangedListener(newText -> entry.value = newText);
            valField.setPlaceholder(Text.literal("Mot de remplacement"));
            addDrawableChild(valField);

            addDrawableChild(ButtonWidget.builder(Text.literal("§cX"), button -> {
                entries.remove(index);
                this.clearAndInit();
            }).dimensions(center + 150, currentY, 20, 20).build());

            currentY += 25;

            if (currentY > this.height - 40) break;
        }
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        this.renderBackground(context, mouseX, mouseY, delta);
        super.render(context, mouseX, mouseY, delta);
    }

    @Override
    public void close() {
        ChatManagerConfig.save();
        if (client != null) {
            client.setScreen(parent);
        }
    }
}
