package fr.flastar.magiqolsky.cooldowndisplay.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.world.item.ItemStack;

import static fr.flastar.magiqolsky.cooldowndisplay.gui.CooldownDisplayHud.TICK_PER_SECOND;
import static fr.flastar.magiqolsky.utils.IDFromStack.retrieveIDFromStack;

public class CooldownDisplayWidget {
    private ItemStack lastStackUsed;
    private int currentTickCooldown;

    private boolean isAlive;
    private String nbt;

    public void render(GuiGraphicsExtractor context, int x, int y, boolean alignRight) {
        if (currentTickCooldown <= 0 || lastStackUsed == null) return;

        Minecraft client = Minecraft.getInstance();
        Font font = client.font;
        float secondsLeft = currentTickCooldown / TICK_PER_SECOND;

        String text = String.format("%.1fs", secondsLeft);
        int textWidth = font.width(text);

        if (alignRight) {
            context.item(lastStackUsed, x - 18, y);
            context.text(font, text, x - 20 - textWidth, y + 4, 0xFFFFFF, true);
        } else {
            context.item(lastStackUsed, x, y);
            context.text(font, text, x + 18, y + 4, 0xFFFFFF, true);
        }
    }

    public void tick() {
        if (currentTickCooldown > 0) {
            currentTickCooldown--;
        } else {
            isAlive = false;
        }
    }

    public void setCooldown(int cooldown, ItemStack stack) {
        this.nbt = retrieveIDFromStack(stack);
        currentTickCooldown = (int) (cooldown * TICK_PER_SECOND);
        lastStackUsed = stack;
        isAlive = true;
    }

    public boolean isAlive() {
        return isAlive;
    }

    public String getNbt() {
        return nbt;
    }
}
