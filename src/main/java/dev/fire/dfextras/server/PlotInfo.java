package dev.fire.dfextras.server;

import net.minecraft.text.Text;

public class PlotInfo {
    public Text plotName;
    public String plotOwner;
    public int plotID;
    public String node;
    public LocationType locationType;

    public PlotInfo(String node, LocationType locationType) {
        this.node = node;
        this.locationType = locationType;
    }
    public PlotInfo(Text plotName, String plotOwner, int plotID, String node, LocationType locationType) {
        this.plotName = plotName;
        this.plotOwner = plotOwner;
        this.plotID = plotID;
        this.node = node;
        this.locationType = locationType;
    }

    public PlotInfo(LocationType locationType) {
        this.locationType = locationType;
    }

}
