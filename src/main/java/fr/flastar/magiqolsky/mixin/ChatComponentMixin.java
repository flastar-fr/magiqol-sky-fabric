package fr.flastar.magiqolsky.mixin;

import fr.flastar.magiqolsky.chatmanager.config.ChatManagerConfig;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.components.ChatComponent;
import net.minecraft.network.chat.Component;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

@Mixin(ChatComponent.class)
public class ChatComponentMixin {

    @ModifyVariable(
            method = "addMessage",
            at = @At("HEAD"),
            argsOnly = true,
            name = "contents")
    private Component addHourToMessage(Component message) {
        String hourFormat = ChatManagerConfig.getConfig().messageHourFormat();
        if (!ChatManagerConfig.getConfig().isMessageHourEnabled()) {
            return message;
        }

        String timestamp = LocalTime.now().format(DateTimeFormatter.ofPattern(hourFormat));

        Component timeText = Component.literal(timestamp + " ")
                .withStyle(ChatFormatting.GRAY);

        return Component.empty().append(timeText).append(message);
    }
}
