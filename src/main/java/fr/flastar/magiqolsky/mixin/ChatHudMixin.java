package fr.flastar.magiqolsky.mixin;

import fr.flastar.magiqolsky.chatmanager.config.ChatManagerConfig;
import net.minecraft.client.gui.hud.ChatHud;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

@Mixin(ChatHud.class)
public class ChatHudMixin {

    @ModifyVariable(
        method = "addMessage(Lnet/minecraft/text/Text;Lnet/minecraft/network/message/MessageSignatureData;Lnet/minecraft/client/gui/hud/MessageIndicator;)V",
        at = @At("HEAD"),
        argsOnly = true
    )
    private Text addHourToMessage(Text message) {
        String hourFormat = ChatManagerConfig.getConfig().messageHourFormat();
        if (!ChatManagerConfig.getConfig().isMessageHourEnabled()) {
            return message;
        }

        String timestamp = LocalTime.now().format(DateTimeFormatter.ofPattern(hourFormat));

        Text timeText = Text.literal(timestamp + " ")
                            .formatted(net.minecraft.util.Formatting.GRAY);

        return Text.empty().append(timeText).append(message);
    }
}
