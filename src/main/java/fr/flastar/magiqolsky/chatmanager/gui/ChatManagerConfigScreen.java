package fr.flastar.magiqolsky.chatmanager.gui;

import fr.flastar.magiqolsky.chatmanager.config.ChatManagerConfig;
import fr.flastar.magiqolsky.chatmanager.model.TextReplacerEntry;
import fr.flastar.magiqolsky.utils.StackItems;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.components.*;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.List;

public class ChatManagerConfigScreen extends Screen {
    public static final int SIZE_CHECKBOX_CASE = 24;
    private final Screen parent;
    private ReplacementListWidget list;
    private String messageHourFormat;

    public ChatManagerConfigScreen(Screen parent) {
        super(Component.translatable("gui.magiqol-sky.chatmanagerscreen.title.main"));
        this.parent = parent;
        this.messageHourFormat = "";
    }

    @Override
    protected void init() {
        messageHourFormat = ChatManagerConfig.getConfig().messageHourFormat();
        int center = this.width / 2;

        int currentY = 10;
        addRenderableWidget(new StringWidget(center - 100, currentY, 200, 20, Component.translatable("gui.magiqol-sky.chatmanagerscreen.title.generalsetting"), font));

        currentY += 25;

        drawCheckboxesConfig(center, currentY);

        if (ChatManagerConfig.getConfig().isMessageHourEnabled()) {
            currentY += 30;
        }

        currentY += 30;

        addRenderableWidget(new StringWidget(center - 200, currentY, 200, 20, Component.translatable("gui.magiqol-sky.chatmanagerscreen.title.textreplacement"), font));

        addRenderableWidget(Button.builder(Component.literal("+"), _ -> {
                    ChatManagerConfig.getConfig().textReplacers().add(new TextReplacerEntry("", ""));
                    list.refreshEntries();
                })
                .bounds(center - 13, currentY, 140, 20)
                .tooltip(Tooltip.create(Component.translatable("gui.magiqol-sky.chatmanagerscreen.tooltip.textreplacementbutton")))
                .build());

        int yReplacementList = ChatManagerConfig.getConfig().isMessageHourEnabled() ? this.height - 165 : this.height - 135;

        list = new ReplacementListWidget(minecraft, this.width, yReplacementList, currentY + 30, 25);
        addRenderableWidget(list);

        addRenderableWidget(Button.builder(Component.translatable("gui.magiqol-sky.chatmanagerscreen.text.save"), _ -> onClose())
                .bounds(center - 100, this.height - 30, 200, 20).build());
    }

    private void drawCheckboxesConfig(int center, int currentY) {
        int spacing = 20;

        Checkbox cbAutoFly = Checkbox.builder(Component.translatable("gui.magiqol-sky.chatmanagerscreen.text.autofly"), font)
                .selected(ChatManagerConfig.getConfig().isAutoFlyingEnabled())
                .onValueChange((_, checked) -> ChatManagerConfig.getConfig().changeIsAutoFlyingEnabled(checked))
                .tooltip(Tooltip.create(Component.translatable("gui.magiqol-sky.chatmanagerscreen.tooltip.autofly")))
                .build();

        Checkbox cbBetterBienvenue = Checkbox.builder(Component.translatable("gui.magiqol-sky.chatmanagerscreen.text.betterbienvenue"), font)
                .selected(ChatManagerConfig.getConfig().isBetterBienvenueEnabled())
                .onValueChange((_, checked) -> ChatManagerConfig.getConfig().changeIsBetterBienvenueEnabled(checked))
                .tooltip(Tooltip.create(Component.translatable("gui.magiqol-sky.chatmanagerscreen.tooltip.betterbienvenue")))
                .build();

        Checkbox cbTextReplacement = Checkbox.builder(Component.translatable("gui.magiqol-sky.chatmanagerscreen.text.textreplacement"), font)
                .selected(ChatManagerConfig.getConfig().isTextReplacementEnabled())
                .onValueChange((_, checked) -> ChatManagerConfig.getConfig().changeIsTextReplacementEnabled(checked))
                .tooltip(Tooltip.create(Component.translatable("gui.magiqol-sky.chatmanagerscreen.tooltip.textreplacement")))
                .build();

        Checkbox cbAutoNightVision = Checkbox.builder(Component.translatable("gui.magiqol-sky.chatmanagerscreen.text.autonightvision"), font)
                .selected(ChatManagerConfig.getConfig().isAutoNightVisionEnabled())
                .onValueChange((_, checked) -> ChatManagerConfig.getConfig().changeIsAutoNightVisionEnabled(checked))
                .tooltip(Tooltip.create(Component.translatable("gui.magiqol-sky.chatmanagerscreen.tooltip.autonightvision")))
                .build();

        Checkbox cbHourMessages = Checkbox.builder(Component.translatable("gui.magiqol-sky.chatmanagerscreen.text.hourmessages"), font)
                .selected(ChatManagerConfig.getConfig().isMessageHourEnabled())
                .onValueChange((_, checked) -> {
                    ChatManagerConfig.getConfig().changeIsMessageHourEnabled(checked);
                    rebuildWidgets();
                })
                .tooltip(Tooltip.create(Component.translatable("gui.magiqol-sky.chatmanagerscreen.tooltip.hourmessages")))
                .build();

        List<Checkbox> checkboxes = Arrays.asList(cbAutoFly, cbBetterBienvenue, cbTextReplacement, cbAutoNightVision, cbHourMessages);

        StackItems<Checkbox> stackItems = new StackItems<>(this, center, currentY, spacing, SIZE_CHECKBOX_CASE, checkboxes);
        stackItems.layout();

        for (Checkbox item : stackItems.getItems()) {
            addRenderableWidget(item);
        }

        if (ChatManagerConfig.getConfig().isMessageHourEnabled()) {
            currentY += 30;
            EditBox hourFormatTextField = new EditBox(font, stackItems.getCorrespondingX(4), currentY, 140, 20, Component.empty());
            hourFormatTextField.setValue(ChatManagerConfig.getConfig().messageHourFormat());
            hourFormatTextField.setTooltip(Tooltip.create(Component.translatable("gui.magiqol-sky.chatmanagerscreen.tooltip.hourformat")));
            hourFormatTextField.setResponder(s -> messageHourFormat = s);
            addRenderableWidget(hourFormatTextField);
        }
    }

    @Override
    public void extractRenderState(@NotNull GuiGraphicsExtractor context, int mouseX, int mouseY, float delta) {
        super.extractRenderState(context, mouseX, mouseY, delta);
        this.list.extractRenderState(context, mouseX, mouseY, delta);
    }

    @Override
    public void onClose() {
        if (messageHourFormat != null)
            ChatManagerConfig.getConfig().changeMessageHourFormat(messageHourFormat);
        ChatManagerConfig.save();
        this.minecraft.setScreen(parent);
    }
}
