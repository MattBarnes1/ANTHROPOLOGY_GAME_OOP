/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package anthropologyapplication.Buildings;

import anthropologyapplication.Building;
import anthropologyapplication.AutoMapper.MapTile;

/**
 *
 * @author Duke
 */
public class Field extends Building {
    
    public Field(String Name, String Description, float BuildTime, int Index, int amountOfBuildersRequired, String FileNameForegroundImage, String FileNameForegroundDestroyedImage) {
        super(Name, Description, BuildTime, Index, amountOfBuildersRequired, FileNameForegroundImage, FileNameForegroundDestroyedImage);
    }

    @Override
    public boolean canBuildOnTile(MapTile aTile) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }


    @Override
    public void startBuildingAtLocation(MapTile aTile) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Building Copy() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void removeFromMapTile() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
