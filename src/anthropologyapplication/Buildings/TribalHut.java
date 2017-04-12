/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package anthropologyapplication.Buildings;

import anthropologyapplication.AutoMapper.MapTile;
import anthropologyapplication.Building;
import anthropologyapplication.Time;
import anthropologyapplication.Timer;

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
    public boolean canBuildOnTile(MapTile aTile) {
        return true;
    }

    @Override
    public Building Copy() {
        return new TribalHut(this);
    }
    
}
