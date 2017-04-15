/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package anthropologyapplication.MapTiles;

import anthropologyapplication.Map;
import anthropologyapplication.AutoMapper.MapTile;
import java.util.Hashtable;
import java.util.Random;

/**
 *
 * @author Duke
 */
public class MapTile_Water extends MapTile {
    



   
    @Override
    public void generateSubType(MapTile[][] surroundingTilesAndThis, Random myRandom, double tilesAvailable) {
        super.setSubtype("Ocean");
    }

    @Override
    public boolean canBlockMovement() {
        return true; //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int getCost() {
        return 0; //To change body of generated methods, choose Tools | Templates.
    }
    
    enum SubType
    {
        RIVER,
        
    }
    
    
    @Override
    public String toString() {
        if(this.BuildingName == null)
        {
            return "Water";
        } else {
            return "Water, " + this.BuildingName;
        }
    }
    
    public MapTile_Water() {
        super("Water.jpeg");
        super.setLand(false);
    }
    
}
