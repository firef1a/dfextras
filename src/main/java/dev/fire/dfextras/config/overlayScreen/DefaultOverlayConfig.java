package dev.fire.dfextras.config.overlayScreen;

import dev.fire.dfextras.screen.utils.overlay.Alignment;
import dev.fire.dfextras.screen.utils.overlay.containers.PlotInfoOverlay;
import dev.fire.dfextras.screen.utils.scaler.Scaler;

public class DefaultOverlayConfig {
    public static final OverlayConfig.CharSetOption SaveCharSet = OverlayConfig.CharSetOption.UTF_8;
    public static final OverlayConfig.CharSetOption FileCharSet = OverlayConfig.CharSetOption.UTF_8;

    public static final PlotInfoOverlay plotInfoOverlay = new PlotInfoOverlay(new Scaler(0.9927083333333333, 0.012962962962962963), Alignment.RIGHT, true);
}
