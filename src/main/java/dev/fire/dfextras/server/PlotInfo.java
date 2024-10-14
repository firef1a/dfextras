package dev.fire.dfextras.server;

import net.minecraft.text.Text;

public class PlotInfo {
    public Text plotName;
    public String plotOwner;
    public int plotID;
    public String server;
    public LocationType locationType;

    public PlotInfo(String server, LocationType locationType) {
        this.server = server;
        this.locationType = locationType;
    }
    public PlotInfo(Text plotName, String plotOwner, int plotID, String server, LocationType locationType) {
        this.plotName = plotName;
        this.plotOwner = plotOwner;
        this.plotID = plotID;
        this.server = server;
        this.locationType = locationType;
    }

    public PlotInfo(LocationType locationType) {
        this.locationType = locationType;
    }
}
