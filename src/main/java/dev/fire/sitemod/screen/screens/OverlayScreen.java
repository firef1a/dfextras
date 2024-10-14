package dev.fire.sitemod.screen.screens;

import com.google.common.collect.Lists;
import com.mojang.blaze3d.systems.RenderSystem;
import dev.fire.sitemod.SiteMod;
import dev.fire.sitemod.config.overlayScreen.OverlayConfig;
import dev.fire.sitemod.devutils.InputUtils;
import dev.fire.sitemod.screen.utils.Point2d;
import dev.fire.sitemod.screen.utils.Point2i;
import dev.fire.sitemod.screen.utils.RenderObject;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.Drawable;
import net.minecraft.client.gui.Element;
import net.minecraft.client.gui.Selectable;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.Text;

import java.util.Iterator;
import java.util.List;

public class OverlayScreen extends Screen {
    private boolean usingMouseToSelect = false;
    private Integer lastMouseX;
    private Integer lastMouseY;
    private final Screen parent;


    public int renderCycle = 1;

    private final List<Drawable> drawables = Lists.newArrayList();

    private RenderObject selectedObject;
    private Point2i selectedObjectOffset;

    public OverlayScreen(Text title, Screen parent) {
        super(title);
        this.parent = parent;
    }

    @Override
    protected void init() {
        super.init();
        this.renderCycle = 1;

    }
    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        this.renderBackground(context, mouseX, mouseY, delta);
        Iterator var5 = drawables.iterator();

        while(var5.hasNext()) {
            Drawable drawable = (Drawable)var5.next();
            drawable.render(context, mouseX, mouseY, delta);
        }

    }



    @Override
    public void renderBackground(DrawContext context, int mouseX, int mouseY, float delta) {
        //super.render(context, mouseX, mouseY, delta);

        this.renderInGameBackground(context);
        RenderSystem.enableBlend();
        int centerX = this.width / 2 - 62;
        int centerY = this.height / 2 - 31 - 27;

        // set last mouse position
        if (lastMouseX == null) lastMouseX = mouseX;
        if (lastMouseY == null) lastMouseY = mouseY;

        int width = this.width;
        int height = this.height;

        for (RenderObject object : SiteMod.OVERLAY_MANAGER.getRenderList()) {
            object.renderOutline(context, textRenderer, object == selectedObject);
        }
    }


    @Override
    public boolean mouseScrolled(double mouseX, double mouseY, double horizontalAmount, double verticalAmount) {

        return false;
    }


    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        Point2d mouse = new Point2d(mouseX, mouseY);
        Point2i mousei = mouse.getPoint2i();

        for (RenderObject object : SiteMod.OVERLAY_MANAGER.getRenderList()) {
            if (object.containsPointInclusive(mousei, true)) {
                SiteMod.LOGGER.info("mouse: " + button);
                if (button == InputUtils.LEFT_CLICK) {
                    this.selectedObject = object;
                    this.selectedObjectOffset = object.getOffset(mousei);
                } else if (button == InputUtils.RIGHT_CLICK) {
                    object.toggleVisibility();
                }
                break;
            }
        }
        return super.mouseClicked(mouseX, mouseY, button);
    }

    @Override
    public boolean mouseReleased(double mouseX, double mouseY, int button) {
        if (selectedObject == SiteMod.OVERLAY_MANAGER.playerListDisplay) {
            OverlayConfig.getConfig().PlayerListTextListObject.scaler.setScaler(selectedObject.scaler);
        }
        this.selectedObject = null;
        this.selectedObjectOffset = null;
        return super.mouseReleased(mouseX, mouseY, button);
    }

    @Override
    public boolean mouseDragged(double mouseX, double mouseY, int button, double deltaX, double deltaY) {
        Point2d mouse = new Point2d(mouseX, mouseY);
        Point2i mousei = mouse.getPoint2i();

        if (this.selectedObject != null) {
            this.selectedObject.shiftToMouse(mousei, this.selectedObjectOffset);
        }
        return super.mouseDragged(mouseX, mouseY, button, deltaX, deltaY);
    }

    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {

        return super.keyPressed(keyCode, scanCode, modifiers);
    }

    @Override
    public boolean shouldPause() {
        return false;
    }
    @Override
    public boolean shouldCloseOnEsc() {
        return true;
    }
    @Override
    public void close() {
        SiteMod.OVERLAY_CONFIG.save();

        assert client != null;
        client.setScreen(parent);
    }

    protected <T extends Element & Drawable & Selectable> T addDrawableChild(T drawableElement) {
        this.drawables.add((Drawable)drawableElement);
        return this.addSelectableChild(drawableElement);
    }

    protected <T extends Drawable> T addDrawable(T drawable) {
        this.drawables.add(drawable);
        return drawable;
    }

    public static boolean isOverlayScreenOpen() {
        return SiteMod.MC.currentScreen instanceof OverlayScreen;
    }
}