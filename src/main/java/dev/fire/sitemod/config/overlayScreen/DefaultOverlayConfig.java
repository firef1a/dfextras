package dev.fire.sitemod.config.overlayScreen;

import dev.fire.sitemod.screen.utils.overlay.Alignment;
import dev.fire.sitemod.screen.utils.overlay.TextList;
import dev.fire.sitemod.screen.utils.overlay.containers.SitePlayerList;
import dev.fire.sitemod.screen.utils.scaler.Scaler;

public class DefaultOverlayConfig {
    public static final OverlayConfig.CharSetOption SaveCharSet = OverlayConfig.CharSetOption.UTF_8;
    public static final OverlayConfig.CharSetOption FileCharSet = OverlayConfig.CharSetOption.UTF_8;

    public static final SitePlayerList PlayerListTextListObject = new SitePlayerList(new Scaler(0.9822916666666667, 0.027777777777777776), Alignment.RIGHT, true);
}
