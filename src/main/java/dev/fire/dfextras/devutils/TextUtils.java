package dev.fire.dfextras.devutils;

import net.minecraft.text.MutableText;
import net.minecraft.text.Text;

public class TextUtils {
    public static MutableText monoText(String text, int color) {
        return Text.literal(text).withColor(color);
    }
}
