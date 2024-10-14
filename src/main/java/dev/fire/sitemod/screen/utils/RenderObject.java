package dev.fire.sitemod.screen.utils;

import dev.fire.sitemod.devutils.ColorUtils;
import dev.fire.sitemod.screen.utils.overlay.Alignment;
import dev.fire.sitemod.screen.utils.scaler.Scaler;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;

import java.util.ArrayList;

public abstract class RenderObject extends Rect {
    public ArrayList<RenderObject> beforeSiblingObjects;
    public ArrayList<RenderObject> afterSiblingObjects;
    protected boolean isVisible = true;
    protected boolean isEnabled = true;

    protected RenderObject parent = null;

    protected int backgroundColor = ColorUtils.setAlpha(0x7fbdf0, 0.3f);
    protected int selectedBackgroundColor = ColorUtils.setAlpha(0x7fbdf0, 0.42f);

    protected int disabledBackgroundColor = ColorUtils.setAlpha(0xed5a58, 0.32f);
    protected int disabledSelectedBackgroundColor = ColorUtils.setAlpha(0xed5a58, 0.42f);
    protected int margin = 4;

    protected final Alignment alignment;

    public RenderObject(Scaler scaler, Scaler size, Alignment alignment) {
        super(scaler, size);
        this.beforeSiblingObjects = new ArrayList<>();
        this.afterSiblingObjects = new ArrayList<>();
        this.alignment = alignment;

    }

    public RenderObject(Scaler scaler) {
        super(scaler);
        this.beforeSiblingObjects = new ArrayList<>();
        this.afterSiblingObjects = new ArrayList<>();
        this.alignment = Alignment.LEFT;
    }

    public RenderObject(Scaler scaler, Alignment alignment) {
        super(scaler);
        this.beforeSiblingObjects = new ArrayList<>();
        this.afterSiblingObjects = new ArrayList<>();
        this.alignment = alignment;
    }

    protected void internalRenderMethod(DrawContext context, TextRenderer textRenderer, Scaler base) { }

    public void renderOutline(DrawContext context, TextRenderer textRenderer, boolean isSelected) {
        this.renderOutline(context, textRenderer, new Scaler(0.0, 0.0), isSelected);
    }

    public void renderOutline(DrawContext context, TextRenderer textRenderer, Scaler base, boolean isSelected) {
        int drawColor = backgroundColor;
        if (isSelected) drawColor = selectedBackgroundColor;

        int xpos = x1() + base.getScreenPosition().x;
        int ypos = y1() + base.getScreenPosition().y;

        if (!isEnabled) {
            drawColor = disabledBackgroundColor;
            if (isSelected) drawColor = disabledSelectedBackgroundColor;
        }
        if (!isVisible) drawColor = ColorUtils.grayScale(drawColor);


        context.fill(xpos-margin, ypos-margin, xpos+ getScreenWidth()+margin, ypos+ getScreenHeight()+margin, drawColor);
        context.drawBorder(xpos-margin,ypos-margin, getScreenWidth()+(margin*2), getScreenHeight()+(margin*2), drawColor);

        internalRenderMethod(context, textRenderer, base);

    }

    public void render(DrawContext context, TextRenderer textRenderer) {
        this.render(context, textRenderer, new Scaler(0.0, 0.0));
    }

    public void render(DrawContext context, TextRenderer textRenderer, Scaler base) {
        for (RenderObject rect : this.beforeSiblingObjects) {
            rect.render(context, textRenderer, base.add(this.scaler));
        }
        if (isVisible && isEnabled) this.internalRenderMethod(context, textRenderer, base);
        for (RenderObject rect : this.afterSiblingObjects) {
            rect.render(context, textRenderer, base.add(this.scaler));
        }
    }

    @Override
    public int x1() {
        if (alignment == Alignment.RIGHT) return scaler.getScreenPosition().x - getScreenWidth();
        return scaler.getScreenPosition().x;
    }

    public Point2i getOffset(Point2i mousei) {
        if (this.alignment == Alignment.RIGHT) {
            return  mousei.subtract(topRight());
        }
        return mousei.subtract(topLeft());
    }

    public void shiftToMouse(Point2i mousei, Point2i offset) {
        if (this.alignment == Alignment.LEFT) {
            Point2i newPosition = mousei.subtract(offset);
            this.setPosition(newPosition.x, newPosition.y);
        } else if (this.alignment == Alignment.RIGHT) {
            Point2i newPosition = new Point2i(mousei.x-(offset.x), mousei.y-offset.y);
            this.setPosition(newPosition.x, newPosition.y);
        }
    }

    public void addBeforeSibling(RenderObject obj) { beforeSiblingObjects.add(obj); obj.parent = this; }
    public void addAfterSibling(RenderObject obj) { afterSiblingObjects.add(obj); obj.parent = this; }

    public void setVisibility(boolean re) { isVisible = re; }
    public void toggleVisibility() { isVisible = !isVisible; }
    public boolean getVisible() { return isVisible; }

    public void setEnabled(boolean re) { isEnabled = re; }
    public void toggleEnabled() { isEnabled = !isEnabled; }
    public boolean getEnabled() { return isEnabled; }

    public boolean isElementRendered() { return isVisible && isEnabled; }

    public boolean containsPointInclusive(Point2i point, boolean includesMargin) {
        int m = 0;
        if (includesMargin) m = margin;
        return point.x >= x1()-m && point.x <= x2()+m && point.y >= y1()-m && point.y <= y2()+m;
    }

    public void onClick(Point2i mouse, int button) { }

    public void update() {};
}
