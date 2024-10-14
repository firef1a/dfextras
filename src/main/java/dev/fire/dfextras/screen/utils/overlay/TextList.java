package dev.fire.dfextras.screen.utils.overlay;

import com.google.gson.JsonObject;
import dev.fire.dfextras.Mod;
import dev.fire.dfextras.devutils.JsonUtils;
import dev.fire.dfextras.screen.utils.RenderObject;
import dev.fire.dfextras.screen.utils.scaler.Scaler;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.text.Text;

import java.awt.*;
import java.util.ArrayList;

public class TextList extends RenderObject {
    private ArrayList<Text> textList = new ArrayList<>();
    private final int lineYMargin = 1;


    public TextList(Scaler scaler, Alignment alignment) {
        super(scaler, alignment);
        this.textList = new ArrayList<>();
    }


    public TextList(Scaler scaler, ArrayList<Text> textList, Alignment alignment) {
        super(scaler, alignment);
        this.textList = textList;
    }

    public TextList(Scaler scaler, Alignment alignment, boolean isVisible) {
        super(scaler, alignment);
        this.isVisible = isVisible;
    }


    public void setTextList(ArrayList<Text> textList) { this.textList = textList; }

    @Override
    protected void internalRenderMethod(DrawContext context, TextRenderer textRenderer, Scaler base) {
        int render_x_position, lineLength, longestLine = 0;

        int xpos = x1()+base.getScreenPosition().x;
        int ypos = y1()+base.getScreenPosition().y;

        for (Text drawText : textList) {
            lineLength = textRenderer.getWidth(drawText);
            if (lineLength > longestLine) {
                longestLine = lineLength;
            }
        }

        for (Text drawText : textList) {
            // Alignment.LEFT
            render_x_position = xpos;

            if (alignment == Alignment.RIGHT) {
                render_x_position = ((xpos) - textRenderer.getWidth(drawText)) + getScreenWidth();
            }

            context.drawText(textRenderer, drawText, render_x_position, ypos, Color.white.getRGB(), true);
            ypos += textRenderer.fontHeight+lineYMargin;
        }
    }

    @Override
    public int getScreenWidth() {
        TextRenderer textRenderer = Mod.getTextRenderer();
        int lineLength, longestLine = 0;

        for (Text drawText : textList) {
            lineLength = textRenderer.getWidth(drawText);
            if (lineLength > longestLine) {
                longestLine = lineLength;
            }
        }

        return longestLine;
    }
    @Override
    public int getScreenHeight() {
        TextRenderer textRenderer = Mod.getTextRenderer();
        return textList.size()*(textRenderer.fontHeight+lineYMargin);
    }

    public void toJson(JsonObject jsonObject, String namespace, String fieldName) { this.toJson(jsonObject,namespace + "." + fieldName); }
    public static TextList getFromJson(JsonObject jsonObject, String namespace, String fieldName) { return getFromJson(jsonObject,namespace + "." + fieldName); }

    public void toJson(JsonObject jsonObject, String namespace) {
        this.scaler.toJson(jsonObject, namespace, "scaler");
        this.alignment.toJson(jsonObject, namespace, "alignment");
        JsonUtils.addProperty(jsonObject, namespace, "isVisible", this.isVisible);
    }

    public static TextList getFromJson(JsonObject jsonObject, String namespace) {
        Scaler scaler = Scaler.getFromJson(jsonObject, namespace, "scaler");
        Alignment alignment = Alignment.getFromJson(jsonObject, namespace, "alignment");
        boolean isVisible = jsonObject.get(namespace + "." + "isVisible").getAsBoolean();

        return new TextList(scaler, alignment, isVisible);
    }
}
