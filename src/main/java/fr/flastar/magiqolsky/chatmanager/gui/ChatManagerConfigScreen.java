package fr.flastar.magiqolsky.chatmanager.gui;

import fr.flastar.magiqolsky.chatmanager.config.ChatManagerConfig;
import fr.flastar.magiqolsky.chatmanager.model.TextReplacerEntry;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.tooltip.Tooltip;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.CheckboxWidget;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.client.gui.widget.TextWidget;
import net.minecraft.text.Text;

import java.util.Arrays;
import java.util.List;

public class ChatManagerConfigScreen extends Screen {
    public static final int SIZE_CHECKBOX_CASE = 24;
    private final Screen parent;
    private ReplacementListWidget list;
    private String messageHourFormat;

    public ChatManagerConfigScreen(Screen parent) {
        super(Text.translatable("gui.magiqol-sky.chatmanagerscreen.title.main"));
        this.parent = parent;
        this.messageHourFormat = "";
    }

    @Override
    protected void init() {
        messageHourFormat = ChatManagerConfig.getConfig().messageHourFormat();
        int center = this.width / 2;

        int currentY = 10;
        addDrawableChild(new TextWidget(center - 100, currentY, 200, 20, Text.translatable("gui.magiqol-sky.chatmanagerscreen.title.generalsetting"), textRenderer));

        currentY += 25;

        drawCheckboxesConfig(center, currentY);

        if (ChatManagerConfig.getConfig().isMessageHourEnabled()) {
            currentY += 30;
        }

        currentY += 30;

        addDrawableChild(new TextWidget(center - 200, currentY, 200, 20, Text.translatable("gui.magiqol-sky.chatmanagerscreen.title.textreplacement"), textRenderer));

        addDrawableChild(ButtonWidget.builder(Text.literal("+"), button -> {
            ChatManagerConfig.getConfig().textReplacers().add(new TextReplacerEntry("", ""));
            list.refreshEntries();
        })
                .dimensions(center - 13, currentY, 140, 20)
                .tooltip(Tooltip.of(Text.translatable("gui.magiqol-sky.chatmanagerscreen.tooltip.textreplacementbutton")))
                .build());

        int yReplacementList = ChatManagerConfig.getConfig().isMessageHourEnabled() ? this.height - 165 : this.height - 135;
        list = new ReplacementListWidget(client, this.width, yReplacementList, currentY + 30, 25);
        addSelectableChild(list);

        addDrawableChild(ButtonWidget.builder(Text.translatable("gui.magiqol-sky.chatmanagerscreen.text.save"), button -> close())
                .dimensions(center - 100, this.height - 30, 200, 20).build());
    }

    private void drawCheckboxesConfig(int center, int currentY) {
        int spacing = 20;

        CheckboxWidget cbAutoFly = CheckboxWidget.builder(Text.translatable("gui.magiqol-sky.chatmanagerscreen.text.autofly"), textRenderer)
                .checked(ChatManagerConfig.getConfig().isAutoFlyingEnabled())
                .callback((cb, checked) -> ChatManagerConfig.getConfig().changeIsAutoFlyingEnabled(checked))
                .tooltip(Tooltip.of(Text.translatable("gui.magiqol-sky.chatmanagerscreen.tooltip.autofly")))
                .build();
        CheckboxWidget cbBetterBienvenue = CheckboxWidget.builder(Text.translatable("gui.magiqol-sky.chatmanagerscreen.text.betterbienvenue"), textRenderer)
                .checked(ChatManagerConfig.getConfig().isBetterBienvenueEnabled())
                .callback((cb, checked) -> ChatManagerConfig.getConfig().changeIsBetterBienvenueEnabled(checked))
                .tooltip(Tooltip.of(Text.translatable("gui.magiqol-sky.chatmanagerscreen.tooltip.betterbienvenue")))
                .build();
        CheckboxWidget cbTextReplacement = CheckboxWidget.builder(Text.translatable("gui.magiqol-sky.chatmanagerscreen.text.textreplacement"), textRenderer)
                .checked(ChatManagerConfig.getConfig().isTextReplacementEnabled())
                .callback((cb, checked) -> ChatManagerConfig.getConfig().changeIsTextReplacementEnabled(checked))
                .tooltip(Tooltip.of(Text.translatable("gui.magiqol-sky.chatmanagerscreen.tooltip.textreplacement")))
                .build();
        CheckboxWidget cbAutoNightVision = CheckboxWidget.builder(Text.translatable("gui.magiqol-sky.chatmanagerscreen.text.autonightvision"), textRenderer)
                .checked(ChatManagerConfig.getConfig().isAutoNightVisionEnabled())
                .callback((cb, checked) -> ChatManagerConfig.getConfig().changeIsAutoNightVisionEnabled(checked))
                .tooltip(Tooltip.of(Text.translatable("gui.magiqol-sky.chatmanagerscreen.tooltip.autonightvision")))
                .build();
        CheckboxWidget cbHourMessages = CheckboxWidget.builder(Text.translatable("gui.magiqol-sky.chatmanagerscreen.text.hourmessages"), textRenderer)
                .checked(ChatManagerConfig.getConfig().isMessageHourEnabled())
                .callback((cb, checked) -> {
                    ChatManagerConfig.getConfig().changeIsMessageHourEnabled(checked);
                    clearAndInit();
                })
                .tooltip(Tooltip.of(Text.translatable("gui.magiqol-sky.chatmanagerscreen.tooltip.hourmessages")))
                .build();

        List<CheckboxWidget> checkboxes = Arrays.asList(cbAutoFly, cbBetterBienvenue, cbTextReplacement, cbAutoNightVision, cbHourMessages);
        StackItems<CheckboxWidget> stackItems = new StackItems<>(this, center, currentY, spacing, SIZE_CHECKBOX_CASE, checkboxes);
        stackItems.layout();

        for (CheckboxWidget item : stackItems.getItems()) {
            addDrawableChild(item);
        }

        if (ChatManagerConfig.getConfig().isMessageHourEnabled()) {
            currentY += 30;
            TextFieldWidget hourFormatTextField = new TextFieldWidget(textRenderer, stackItems.getCorrespondingX(4), currentY, 140, 20, Text.empty());
            hourFormatTextField.setText(ChatManagerConfig.getConfig().messageHourFormat());
            hourFormatTextField.setTooltip(Tooltip.of(Text.translatable("gui.magiqol-sky.chatmanagerscreen.tooltip.hourformat")));
            hourFormatTextField.setChangedListener(s -> messageHourFormat = s);
            addDrawableChild(hourFormatTextField);
        }
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        super.render(context, mouseX, mouseY, delta);
        this.list.render(context, mouseX, mouseY, delta);
    }

    @Override
    public void close() {
        if (messageHourFormat != null)
            ChatManagerConfig.getConfig().changeMessageHourFormat(messageHourFormat);
        ChatManagerConfig.save();
        if (this.client != null) {
            this.client.setScreen(parent);
        }
    }
}
