package fr.flastar.magiqolsky.chatmanager.gui;

import fr.flastar.magiqolsky.chatmanager.ChatManagerConfig;
import fr.flastar.magiqolsky.chatmanager.TextReplacerEntry;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.Element;
import net.minecraft.client.gui.Selectable;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.ElementListWidget;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.text.Text;

import java.util.ArrayList;
import java.util.List;

public class ReplacementListWidget extends ElementListWidget<ReplacementListWidget.ReplacementEntry> {

    public ReplacementListWidget(MinecraftClient client, int width, int height, int y, int itemHeight) {
        super(client, width, height, y, itemHeight);
        refreshEntries();
    }

    public void refreshEntries() {
        this.clearEntries();
        for (TextReplacerEntry entry : ChatManagerConfig.getConfig().textReplacers()) {
            this.addEntry(new ReplacementEntry(entry));
        }
    }

    @Override
    public int getRowWidth() {
        return 320;
    }

    @Override
    protected int getScrollbarX() {
        return this.width / 2 + 165;
    }

    public class ReplacementEntry extends ElementListWidget.Entry<ReplacementEntry> {
        private final List<Element> children = new ArrayList<>();
        private final TextFieldWidget keyField;
        private final TextFieldWidget valField;
        private final ButtonWidget deleteBtn;

        public ReplacementEntry(TextReplacerEntry configEntry) {
            MinecraftClient client = MinecraftClient.getInstance();

            keyField = new TextFieldWidget(client.textRenderer, 0, 0, 140, 20, Text.empty());
            keyField.setText(configEntry.key);
            keyField.setChangedListener(s -> configEntry.key = s);

            valField = new TextFieldWidget(client.textRenderer, 0, 0, 140, 20, Text.empty());
            valField.setText(configEntry.value);
            valField.setChangedListener(s -> configEntry.value = s);

            deleteBtn = ButtonWidget.builder(Text.literal("§cX"), b -> {
                ChatManagerConfig.getConfig().textReplacers().remove(configEntry);
                refreshEntries();
            }).dimensions(0, 0, 20, 20).build();

            children.add(keyField);
            children.add(valField);
            children.add(deleteBtn);
        }

        @Override
        public void render(DrawContext context, int index, int y, int x, int width, int height, int mouseX, int mouseY, boolean hovered, float delta) {
            keyField.setX(x);
            keyField.setY(y);
            keyField.setPlaceholder(Text.translatable("gui.magiqol-sky.chatmanagerscreen.placeholder.wordtodetect"));
            valField.setX(x + 145);
            valField.setY(y);
            valField.setPlaceholder(Text.translatable("gui.magiqol-sky.chatmanagerscreen.placeholder.wordtoreplacewith"));
            deleteBtn.setX(x + 290);
            deleteBtn.setY(y);

            keyField.render(context, mouseX, mouseY, delta);
            valField.render(context, mouseX, mouseY, delta);
            deleteBtn.render(context, mouseX, mouseY, delta);
        }

        @Override
        public List<? extends Element> children() {
            return children;
        }

        @Override
        public List<? extends Selectable> selectableChildren() {
            return List.of(keyField, valField, deleteBtn);
        }
    }
}
