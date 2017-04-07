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
        return "L";
    }

  

    
}
