package fr.flastar.magiqolsky.screens;

import fr.flastar.magiqolsky.screens.data.TimerData;
import fr.flastar.magiqolsky.screens.data.MobCounterConfig;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.tooltip.Tooltip;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.text.Text;

public class MobCountingScreen extends Screen {
    private static final String START_TIMER = "Démarrer le chrono";
    private static final String STOP_TIMER = "Arrêter le chrono";
    private static final String PAUSE_TIMER = "Mettre en pause";
    private static final String RESUME_TIMER = "Reprendre le chrono";

    private ButtonWidget buttonTimerStart;
    private ButtonWidget buttonTimerStop;
    private ButtonWidget buttonTimerPause;

    private final Screen parent;

    public MobCountingScreen(Screen parent) {
        super(Text.of("Compteur de mobs"));
        this.parent = parent;
    }

    @Override
    protected void init() {
        buttonTimerStart = ButtonWidget.builder(Text.literal(START_TIMER), button -> MobCounterConfig.getConfig().startTimer())
                .dimensions(width / 2 - 100, 20, 200, 20)
                .tooltip(Tooltip.of(Text.literal(START_TIMER)))
                .build();

        buttonTimerStop = ButtonWidget.builder(Text.literal(STOP_TIMER), button -> MobCounterConfig.getConfig().stopTimer())
                .dimensions(width / 2 - 205, 20, 200, 20)
                .tooltip(Tooltip.of(Text.literal(STOP_TIMER)))
                .build();

        buttonTimerPause = ButtonWidget.builder(Text.literal(PAUSE_TIMER), button -> MobCounterConfig.getConfig().togglePauseTimer())
                .dimensions(width / 2 + 5, 20, 200, 20)
                .tooltip(Tooltip.of(Text.literal(PAUSE_TIMER)))
                .build();

        this.addDrawableChild(buttonTimerStart);
        this.addDrawableChild(buttonTimerStop);
        this.addDrawableChild(buttonTimerPause);

        updateButtonStates();
    }

    private void updateButtonStates() {
        TimerData data = MobCounterConfig.getConfig();

        boolean isRunning = data.isStarted();
        boolean isPaused = data.isPaused();
        System.out.println(isRunning + " " + isPaused);

        buttonTimerStart.visible = !isRunning;

        buttonTimerStop.visible = isRunning;
        buttonTimerPause.visible = isRunning;

        buttonTimerPause.setMessage(isPaused ? Text.literal(RESUME_TIMER) : Text.literal(PAUSE_TIMER));

        if (isRunning) {
            long elapsedTime = data.getTotalElapsedTime() / 1000;
            buttonTimerStop.setMessage(Text.of(STOP_TIMER + " : " + elapsedTime + "s"));
        }
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        super.render(context, mouseX, mouseY, delta);

        updateButtonStates();
    }

    @Override
    public void close() {
        if (client != null) {
            client.setScreen(parent);
        }
    }
}
