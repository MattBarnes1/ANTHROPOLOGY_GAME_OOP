/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package anthropologyapplication.Buildings;

import anthropologyapplication.Buildings.Building;
import anthropologyapplication.AutoMapper.MapTile;
import anthropologyapplication.Time;
import anthropologyapplication.Timer;
import anthropologyapplication.TradeGoods.ResourceArray;
import anthropologyapplication.TribalCampObject;

/**
 *
 * @author Duke
 */
public class Workshop extends Building {
    public Workshop(String Name, String Description, Timer BuildTime, int Index, int amountOfBuildersRequired, int TerritorySize, String FileNameForegroundImage, String FileNameForegroundDestroyedImage, ResourceArray myResourcesNeed) {
        super(Name, Description, BuildTime, Index, amountOfBuildersRequired, TerritorySize, FileNameForegroundImage, FileNameForegroundDestroyedImage,myResourcesNeed);
    }

    private Workshop(Workshop aThis) {
        super(aThis.getBuildingName(), aThis.getDescription(), aThis.getBuildTime(), aThis.getIndex(), aThis.getRequiredBuildersAmount(), aThis.getTerritorySize(), aThis.getForeGroundImageName(),  aThis.getForeGroundDestroyedImageName(), aThis.getResourceArray());
    
    }

    @Override
    public boolean canBuildOnTile(TribalCampObject myObject, MapTile aTile) {
        return (aTile.isLand() && aTile.isTerritoryOf(myObject) && !aTile.hasBuilding() && hasEnoughResources(myObject));
    }

  


    @Override
    public Building Copy() {
       return new Workshop(this);
    }

    @Override
    public void removeFromMapTile() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
