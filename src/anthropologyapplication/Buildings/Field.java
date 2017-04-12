/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package anthropologyapplication.Buildings;

import anthropologyapplication.Building;
import anthropologyapplication.AutoMapper.MapTile;
import anthropologyapplication.Time;
import anthropologyapplication.Timer;

/**
 *
 * @author Duke
 */
public class Field extends Building {
    
    public Field(String Name, String Description, Timer BuildTime, int Index, int amountOfBuildersRequired, String FileNameForegroundImage, String FileNameForegroundDestroyedImage) {
        super(Name, Description, BuildTime, Index, amountOfBuildersRequired, FileNameForegroundImage, FileNameForegroundDestroyedImage);
    }

    private Field(Field aThis) {
        super(aThis.getBuildingName(), aThis.getDescription(), aThis.getBuildTime(), aThis.getIndex(), aThis.getBaseNumberOfBuilders(), aThis.getForeGroundImageName(),  aThis.getForeGroundDestroyedImageName());
    
    }

    @Override
    public boolean canBuildOnTile(MapTile aTile) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Building Copy() {
        return new Field(this);
    }

    @Override
    public void removeFromMapTile() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
