/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Buildings;

import anthropologyapplication.AutoMapper.MapTile;
import Buildings.Building;
import anthropologyapplication.Time;
import anthropologyapplication.Timer;
import anthropologyapplication.TribalCampObject;

/**
 *
 * @author Duke
 */
public class TribalHut extends Building {

    public TribalHut(String Name, String Description, Timer BuildTime, int Index, int amountOfBuildersRequired, String FileNameForegroundImage, String FileNameForegroundDestroyedImage) {
        super(Name, Description, BuildTime, Index, amountOfBuildersRequired,FileNameForegroundImage, FileNameForegroundDestroyedImage);
        
    }

    private TribalHut(TribalHut aThis) {
        super(aThis.getBuildingName(), aThis.getDescription(), aThis.getBuildTime(), aThis.getIndex(), aThis.getBaseNumberOfBuilders(), aThis.getForeGroundImageName(),  aThis.getForeGroundDestroyedImageName());
    }

    @Override
    public boolean canBuildMultiples()
    {
        return false;
    }
    
    
    
    
    @Override
    public boolean canBuildOnTile(TribalCampObject myObject, MapTile aTile) {
        return (aTile.isLand() && aTile.isTerritoryOf(myObject));
    }

    @Override
    public Building Copy() {
        return new TribalHut(this);
    }
    
}
