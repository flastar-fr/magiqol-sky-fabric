package fr.flastar.magiqolsky.containervalues.gui;

import fr.flastar.magiqolsky.chatmanager.config.ChatManagerConfig;
import fr.flastar.magiqolsky.containervalues.gui.config.ContainerValueConfig;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.tooltip.Tooltip;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.CheckboxWidget;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.client.gui.widget.TextWidget;
import net.minecraft.text.Text;

import static fr.flastar.magiqolsky.utils.ClientLocaleUtils.getClientLocale;

public class ContainerValueConfigScreen extends Screen {
    private final Screen parent;
    private String decimalSeparator;
    private String groupingSeparator;

    private static final int FIELDS_OFFSETS = 2;
    private static final int TEXT_FIELD_WIDTH = 120;

    public ContainerValueConfigScreen(Screen parent) {
        super(Text.translatable("gui.magiqol-sky.containervaluescreen.title.main"));
        this.parent = parent;
        this.decimalSeparator = "";
        this.groupingSeparator = "";
    }

    @Override
    protected void init() {
        decimalSeparator = ChatManagerConfig.getConfig().messageHourFormat();
        int center = this.width / 2;

        int currentY = 10;
        addDrawableChild(new TextWidget(center - 100, currentY, 200, 20, Text.translatable("gui.magiqol-sky.containervaluescreen.title.settings"), textRenderer));

        currentY += 25;

        int cbWidth = 20 + textRenderer.getWidth(Text.translatable("gui.magiqol-sky.containervaluescreen.text.containervalue")) + 4;
        CheckboxWidget cbContainerValue = CheckboxWidget.builder(Text.translatable("gui.magiqol-sky.containervaluescreen.text.containervalue"), textRenderer)
                .checked(ContainerValueConfig.getConfig().isContainerValueEnabled())
                .callback((cb, checked) -> {
                    ContainerValueConfig.getConfig().setEnableContainerValue(checked);
                    clearAndInit();
                })
                .tooltip(Tooltip.of(Text.translatable("gui.magiqol-sky.containervaluescreen.tooltip.containervalue")))
                .pos(center - (cbWidth / 2), currentY)
                .build();
        addDrawableChild(cbContainerValue);

        currentY += 25;

        if (ContainerValueConfig.getConfig().isContainerValueEnabled()) {
            drawContainerValueConfig(center, currentY);
        }

        addDrawableChild(ButtonWidget.builder(Text.translatable("gui.magiqol-sky.containervaluescreen.text.save"), button -> close())
                .dimensions(center - 100, this.height - 30, 200, 20).build());
    }

    private void drawContainerValueConfig(int center, int currentY) {
        TextFieldWidget decimalSeparatorTextField = prepareSeparatorTextField(
                center - TEXT_FIELD_WIDTH - FIELDS_OFFSETS,
                currentY,
                Text.literal(ContainerValueConfig.getConfig().decimalSeparator() + ""),
                Text.translatable("gui.magiqol-sky.containervaluescreen.text.decimalseparator"),
                Text.translatable("gui.magiqol-sky.containervaluescreen.tooltip.decimalseparator")
        );
        decimalSeparatorTextField.setChangedListener(s -> decimalSeparator = s);
        addDrawableChild(decimalSeparatorTextField);

        String groupingSeparatorValue = ContainerValueConfig.getConfig().enableGrouping() ?
                ContainerValueConfig.getConfig().groupingSeparator() + "" : "";

        TextFieldWidget groupingSeparatorTextField = prepareSeparatorTextField(
                center + FIELDS_OFFSETS,
                currentY,
                Text.literal(groupingSeparatorValue),
                Text.translatable("gui.magiqol-sky.containervaluescreen.text.groupingseparator"),
                Text.translatable("gui.magiqol-sky.containervaluescreen.tooltip.groupingseparator")
        );
        groupingSeparatorTextField.setChangedListener(s -> groupingSeparator = s);
        addDrawableChild(groupingSeparatorTextField);
    }

    private TextFieldWidget prepareSeparatorTextField(int x, int y, Text value, Text placeholder, Text tooltip) {
        TextFieldWidget separatorTextField = new TextFieldWidget(textRenderer, x, y, TEXT_FIELD_WIDTH, 20, value);
        separatorTextField.setText(value.getString());
        separatorTextField.setMaxLength(1);
        separatorTextField.setPlaceholder(placeholder);
        separatorTextField.setTooltip(Tooltip.of(tooltip));
        return separatorTextField;
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        super.render(context, mouseX, mouseY, delta);
    }

    @Override
    public void close() {
        if (decimalSeparator == null || decimalSeparator.isEmpty()) {
            ContainerValueConfig.getConfig().setDefaultDecimalSeparatorWithLocale(getClientLocale());
        }

        if (decimalSeparator != null && !decimalSeparator.isEmpty()) {
            ContainerValueConfig.getConfig().setDecimalSeparator(decimalSeparator.charAt(0));
        }
        if (groupingSeparator != null && !groupingSeparator.isEmpty()) {
            ContainerValueConfig.getConfig().setGroupingSeparator(groupingSeparator.charAt(0));
        }
        ContainerValueConfig.getConfig().setEnableGrouping(groupingSeparator != null && !groupingSeparator.isEmpty());

        ContainerValueConfig.save();
        if (this.client != null) {
            this.client.setScreen(parent);
        }
    }
}
