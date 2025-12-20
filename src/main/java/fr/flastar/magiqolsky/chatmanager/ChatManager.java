package fr.flastar.magiqolsky.chatmanager;

import java.util.ArrayList;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

import static fr.flastar.magiqolsky.chatmanager.ChatManagerConfigButton.registerAutoCommandConfigurationButton;

public class ChatManager {
    public static void registerChatFeatures() {
        ChatManagerConfig.load();

        ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();

        ArrayList<RegisterableCommand> commands = new ArrayList<>();
        commands.add(new AutoFlyCommand(scheduler));
        commands.add(new BetterBienvenue());

        for (RegisterableCommand command : commands) {
            command.register();
        }

        registerAutoCommandConfigurationButton();
    }
}
