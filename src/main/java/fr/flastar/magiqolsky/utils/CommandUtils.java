package fr.flastar.magiqolsky.utils;

import com.mojang.brigadier.CommandDispatcher;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientPacketListener;
import net.minecraft.client.multiplayer.ClientSuggestionProvider;

public class CommandUtils {
    public static boolean isCommandAvailable(String command) {
        ClientPacketListener handler = Minecraft.getInstance().getConnection();

        if (handler != null) {
            CommandDispatcher<ClientSuggestionProvider> dispatcher = handler.getCommands();

            return dispatcher.getRoot().getChild(command) != null;
        }

        return false;
    }
}
