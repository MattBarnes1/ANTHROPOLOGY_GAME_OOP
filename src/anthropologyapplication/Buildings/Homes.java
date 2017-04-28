/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package anthropologyapplication.Buildings;

import anthropologyapplication.AutoMapper.MapTile;
import anthropologyapplication.Timer;
import anthropologyapplication.TradeGoods.ResourceArray;
import anthropologyapplication.TribalCampObject;

/**
 *
 * @author noone
 */
public class Homes extends Building {

    public Homes(String Name, String Description, Timer BuildTime, int Index, int BuildersRequired,  int TerritorySize, String ForegroundImageFileName, String ForegroundImageDestroyedFileName, ResourceArray myResourcesNeed)
    {
        super(Name,Description, BuildTime, Index, BuildersRequired, TerritorySize, ForegroundImageFileName, ForegroundImageDestroyedFileName,myResourcesNeed);
        
    }

    private Homes(Homes aThis) {      
        super(aThis.getBuildingName(), aThis.getDescription(), aThis.getBuildTime(), aThis.getIndex(), aThis.getRequiredBuildersAmount(), aThis.getTerritorySize(), aThis.getForeGroundImageName(),  aThis.getForeGroundDestroyedImageName(), aThis.getResourceArray());
    }

    @Override
    public boolean canBuildOnTile(TribalCampObject myObject, MapTile aTile) {
        return (aTile.isLand() && aTile.isTerritoryOf(myObject) && !aTile.hasBuilding() && hasEnoughResources(myObject));
    }

    @Override
    public Building Copy() {
        return new Homes(this);
    }

    public int getOccupancy() {
        return 2;//TODO: For now
    }
    
}
