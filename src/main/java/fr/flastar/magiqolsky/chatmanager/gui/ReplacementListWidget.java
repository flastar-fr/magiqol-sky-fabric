package fr.flastar.magiqolsky.chatmanager.gui;

import fr.flastar.magiqolsky.chatmanager.config.ChatManagerConfig;
import fr.flastar.magiqolsky.chatmanager.model.TextReplacerEntry;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.ContainerObjectSelectionList;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.client.gui.narration.NarratableEntry;
import net.minecraft.network.chat.Component;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class ReplacementListWidget extends ContainerObjectSelectionList<ReplacementListWidget.ReplacementEntry> {

    public ReplacementListWidget(Minecraft minecraft, int width, int height, int y, int itemHeight) {
        super(minecraft, width, height, y, itemHeight);
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
    protected int scrollBarX() {
        return this.width / 2 + 165;
    }

    public class ReplacementEntry extends ContainerObjectSelectionList.Entry<ReplacementEntry> {
        private final List<GuiEventListener> children = new ArrayList<>();
        private final EditBox keyField;
        private final EditBox valField;
        private final Button deleteBtn;

        public ReplacementEntry(TextReplacerEntry configEntry) {
            Minecraft minecraft = Minecraft.getInstance();

            keyField = new EditBox(minecraft.font, 0, 0, 140, 20, Component.empty());
            keyField.setValue(configEntry.key);
            keyField.setResponder(s -> configEntry.key = s);

            valField = new EditBox(minecraft.font, 0, 0, 140, 20, Component.empty());
            valField.setValue(configEntry.value);
            valField.setResponder(s -> configEntry.value = s);

            deleteBtn = Button.builder(Component.literal("§cX"), _ -> {
                ChatManagerConfig.getConfig().textReplacers().remove(configEntry);
                refreshEntries();
            }).bounds(0, 0, 20, 20).build();

            children.add(keyField);
            children.add(valField);
            children.add(deleteBtn);
        }

        @Override
        public void extractContent(@NotNull GuiGraphicsExtractor graphics, int mouseX, int mouseY, boolean hovered, float delta) {
            int x = getX();
            int y = getY();

            keyField.setX(x);
            keyField.setY(y);
            keyField.setHint(Component.translatable("gui.magiqol-sky.chatmanagerscreen.placeholder.wordtodetect"));

            valField.setX(x + 145);
            valField.setY(y);
            valField.setHint(Component.translatable("gui.magiqol-sky.chatmanagerscreen.placeholder.wordtoreplacewith"));

            deleteBtn.setX(x + 290);
            deleteBtn.setY(y);

            keyField.extractRenderState(graphics, mouseX, mouseY, delta);
            valField.extractRenderState(graphics, mouseX, mouseY, delta);
            deleteBtn.extractRenderState(graphics, mouseX, mouseY, delta);
        }

        @Override
        @NotNull
        public List<? extends GuiEventListener> children() {
            return children;
        }

        @Override
        @NotNull
        public List<? extends NarratableEntry> narratables() {
            return List.of(keyField, valField, deleteBtn);
        }
    }
}
