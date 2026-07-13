package fr.flastar.magiqolsky.containervalues.gui;

import fr.flastar.magiqolsky.containervalues.gui.config.ContainerValueConfig;
import fr.flastar.magiqolsky.containervalues.gui.model.ContainerValueData;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.components.Tooltip;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.Checkbox;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.components.StringWidget;
import net.minecraft.network.chat.Component;
import org.jetbrains.annotations.NotNull;

import static fr.flastar.magiqolsky.utils.ClientLocaleUtils.getClientLocale;

public class ContainerValueConfigScreen extends Screen {
    private final Screen parent;
    private String decimalSeparator;
    private String groupingSeparator;

    private static final int FIELDS_OFFSETS = 2;
    private static final int TEXT_FIELD_WIDTH = 120;
    public static final int TEXT_FIELD_HEIGHT = 20;

    public ContainerValueConfigScreen(Screen parent) {
        super(Component.translatable("gui.magiqol-sky.containervaluescreen.title.main"));
        this.parent = parent;
        ContainerValueData config = ContainerValueConfig.getConfig();
        this.decimalSeparator = String.valueOf(config.decimalSeparator());
        this.groupingSeparator = config.enableGrouping() ? String.valueOf(config.groupingSeparator()) : "";
    }

    @Override
    protected void init() {
        int center = this.width / 2;

        int currentY = 10;
        this.addRenderableWidget(new StringWidget(center - 100, currentY, 200, 20, Component.translatable("gui.magiqol-sky.containervaluescreen.title.settings"), this.font));

        currentY += 25;

        int cbWidth = 20 + this.font.width(Component.translatable("gui.magiqol-sky.containervaluescreen.text.containervalue")) + 4;
        Checkbox cbContainerValue = Checkbox.builder(Component.translatable("gui.magiqol-sky.containervaluescreen.text.containervalue"), this.font)
                .selected(ContainerValueConfig.getConfig().isContainerValueEnabled())
                .onValueChange((_, checked) -> {
                    ContainerValueConfig.getConfig().setEnableContainerValue(checked);
                    this.rebuildWidgets();
                })
                .tooltip(Tooltip.create(Component.translatable("gui.magiqol-sky.containervaluescreen.tooltip.containervalue")))
                .pos(center - (cbWidth / 2), currentY)
                .build();
        this.addRenderableWidget(cbContainerValue);

        currentY += 25;

        if (ContainerValueConfig.getConfig().isContainerValueEnabled()) {
            drawContainerValueConfig(center, currentY);
        }

        this.addRenderableWidget(
                Button.builder(
                        Component.translatable(
                                "gui.magiqol-sky.containervaluescreen.text.save"),
                                _ -> this.onClose()
                        )
                .bounds(center - 100, this.height - 30, 200, 20).build());
    }

    private void drawContainerValueConfig(int center, int currentY) {
        EditBox decimalSeparatorTextField = prepareSeparatorTextField(
                center - TEXT_FIELD_WIDTH - FIELDS_OFFSETS,
                currentY,
                Component.literal(ContainerValueConfig.getConfig().decimalSeparator() + ""),
                Component.translatable("gui.magiqol-sky.containervaluescreen.text.decimalseparator"),
                Component.translatable("gui.magiqol-sky.containervaluescreen.tooltip.decimalseparator")
        );
        decimalSeparatorTextField.setResponder(s -> decimalSeparator = s);
        this.addRenderableWidget(decimalSeparatorTextField);

        EditBox groupingSeparatorTextField = prepareSeparatorTextField(
                center + FIELDS_OFFSETS,
                currentY,
                Component.literal(groupingSeparator),
                Component.translatable("gui.magiqol-sky.containervaluescreen.text.groupingseparator"),
                Component.translatable("gui.magiqol-sky.containervaluescreen.tooltip.groupingseparator")
        );
        groupingSeparatorTextField.setResponder(s -> groupingSeparator = s);
        this.addRenderableWidget(groupingSeparatorTextField);
    }

    private EditBox prepareSeparatorTextField(int x, int y, Component value, Component placeholder, Component tooltip) {
        EditBox separatorTextField = new EditBox(this.font, x, y, TEXT_FIELD_WIDTH, TEXT_FIELD_HEIGHT, null, placeholder);
        separatorTextField.setValue(value.getString());
        separatorTextField.setMaxLength(1);
        separatorTextField.setTooltip(Tooltip.create(tooltip));
        return separatorTextField;
    }

    @Override
    public void extractRenderState(@NotNull GuiGraphicsExtractor graphics, int mouseX, int mouseY, float delta) {
        super.extractRenderState(graphics, mouseX, mouseY, delta);
    }

    @Override
    public void onClose() {
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
        this.minecraft.setScreen(parent);
    }
}
