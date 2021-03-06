/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package anthropologyapplication.MapTiles;

import anthropologyapplication.Map;
import anthropologyapplication.AutoMapper.MapTile;
import java.util.Random;
import javafx.scene.image.Image;

/**
 *
 * @author Duke
 */
public class MapTile_Land extends MapTile{
    public MapTile_Land() {
        super("Grassland.jpeg");
        super.setLand(true);
        
    }

 

    @Override
    public void generateSubType(MapTile[][] surroundingTilesAndThis, Random myRandom, double tilesAvailable) {
        super.setSubtype("Grassland");
    }

    @Override
    public String toString() {
        String Territory = "";
        if(super.getTerritory() != null)
        {
            Territory = "T: Yes";
        }
        if(super.Destination)
        {
            return "□";
        }
        if(super.isMover)
        {
            return "M";
        }
        if(debugTownRemovePlacement)
        {
            return "R";
        }
        
        if(!super.debugTownPlacement)
        {
            if(!super.debug)
            {        
                if(this.myBuilding != null)
                {
                    if(this.myBuilding.isFinishedBuilding())
                    {
                        return "Land, "+ this.myBuilding.getBuildingName() + "DEBUG: " + this.getCoordinates().toString();
                    } else {
                        return "Land, "+ this.myBuilding.getBuildingName() + ", " + this.myBuilding.getCompletionPercentage()*100 + "%" + "DEBUG: " + this.getCoordinates().toString() + " " + Territory;
                    }
                } else {
                    return "Land" + "DEBUG: " + this.getCoordinates().toString() + "ForegroundImageFileName: " + super.getTerritoryImage() + " " + Territory;
                }
            } else {
                return "X";
            }
        } else {
            if(super.ActiveLooker)
            {
                return "A";
            } else {
                return "∎";
            }
        }
    }

    @Override
    public boolean canBlockMovement() {
        return false; //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int getCost() {
        return 1;
    }

  

    
}
