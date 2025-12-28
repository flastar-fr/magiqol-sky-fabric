package fr.flastar.magiqolsky.chatmanager;

import fr.flastar.magiqolsky.chatmanager.registerables.*;

import java.util.ArrayList;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

import static fr.flastar.magiqolsky.chatmanager.gui.ChatManagerConfigButton.registerAutoCommandConfigurationButton;

public class ChatManager {
    public static void registerChatFeatures() {
        ChatManagerConfig.load();

        ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();

        ArrayList<Registerable> registerables = new ArrayList<>();
        registerables.add(new AutoFly(scheduler));
        registerables.add(new BetterBienvenue());
        registerables.add(new TextReplacer());
        registerables.add(new AutoNightVision());

        for (Registerable command : registerables) {
            command.register();
        }

        registerAutoCommandConfigurationButton();
    }
}
