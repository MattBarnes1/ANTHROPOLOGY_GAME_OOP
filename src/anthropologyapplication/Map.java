/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package anthropologyapplication;

import anthropologyapplication.AutoMapper.MapTile;
import anthropologyapplication.AutoMapper.Vector3;
import anthropologyapplication.MapTiles.MapTile_Land;
import anthropologyapplication.MapTiles.MapTile_Water;
import java.util.ArrayList;
import java.util.Random;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

/**
 *
 * @author Duke
 */
public class Map implements Runnable {
    MapTile[][] myMap = new MapTile[40][40];
    int mapXAndYLength = 0;
    private final MapTile[] BaseTileTypes = new MapTile[]{ new MapTile_Land(), new MapTile_Water()};
    int possibleSettlements = 3;
    Random myRandomNumberGen = new Random(47);//TODO better seeding
    public Map(int mapXAndYLength) {
        this.mapXAndYLength = mapXAndYLength;
        myMap = new MapTile[mapXAndYLength][mapXAndYLength];
    }

    float BaseProbability_Water = 0.3F;
    float BaseProbability_Land = 0.7F;
    public void generateWorldMap(int xCoord, int yCoord, int zCoord, double WaterTilesRemaining, double LandTilesRemaining)
    {
        int Value = myRandomNumberGen.nextInt(101);//TODO
        if(Value <= (BaseProbability_Water*100)) //This Generates first tile!
        {
            MapTile_Water myWaterTile = new MapTile_Water();
            myWaterTile.generateSubType(getSurroundingTiles(xCoord, yCoord),myRandomNumberGen, WaterTilesRemaining);
            myWaterTile.setCoordinates(new Vector3(xCoord, yCoord, zCoord));
            WaterTilesRemaining--;
            myMap[xCoord++][yCoord] = myWaterTile;
            if(xCoord > mapXAndYLength - 1)
            {
                xCoord = 0;
                yCoord += 1;
                if(yCoord > mapXAndYLength - 1)
                {
                    return;
                }
            }
            generateWorldMap(xCoord,yCoord, zCoord, WaterTilesRemaining, LandTilesRemaining);
        } else {
            MapTile_Land myLandTile = new MapTile_Land();
            myLandTile.setCoordinates(new Vector3(xCoord, yCoord, zCoord));
            myLandTile.generateSubType(getSurroundingTiles(xCoord, yCoord),myRandomNumberGen, LandTilesRemaining);
            LandTilesRemaining--;
            myMap[xCoord++][yCoord] = myLandTile;
            if(xCoord > mapXAndYLength - 1)
            {
                xCoord = 0;
                yCoord += 1;
                if(yCoord > mapXAndYLength - 1)
                {
                    return;
                }
            }
            generateWorldMap(xCoord,yCoord, zCoord, WaterTilesRemaining, LandTilesRemaining);

        }
    }
    
    
    
private MapTile[] getPossibleSettlementPosition()
{
    ArrayList<MapTile> habitable = new ArrayList<MapTile>();
    for(int x = 0; x < myMap.length; x++) //using length in case we eventually implement a scaling map
    {
        for(int y = 0; y < myMap[0].length; y++)
        {
           if(myMap[x][y].isLand());
           {
               habitable.add(myMap[x][y]);
           }
        }
    }
    MapTile[] myLocations = new MapTile[habitable.size()];
    habitable.toArray(myLocations);
    return myLocations;
}
    
    private void clearMap()
    {
        for(int x = 0; x < myMap.length; x++) //using length in case we implement a scaling map
        {
            for(int y = 0; y < myMap[0].length; y++)
            {
                myMap[x][y] = null;
            }
        }
    }
   
    
    
    MapTile[] ValidSettlementPositions;
    
    private boolean isValidMap()
    {
        ValidSettlementPositions = getPossibleSettlementPosition();
        if(ValidSettlementPositions.length < possibleSettlements + 1)
        {
            return false;
        } else {
            return true;
        }
    }
    
   
    
    
    
    
    private AICampObject[] generateSettlements()
    {
        Random myRandomNumber = new Random();
        ArrayList<SocialValues> AISocialValues = new ArrayList<>();
        myCamps = new AICampObject[possibleSettlements];
        SocialValues[] mySocialValues = SocialValues.values();
        for(int i = 0; i < possibleSettlements; i++)
        {
            AISocialValues.clear();
            for(int g = 0; g < SocialValues.values().length; g+=2)
            {
               int Rando = myRandomNumberGen.nextInt(2);
                if(Rando == 1)
                {
                    AISocialValues.add(mySocialValues[g]);
                } else {
                    AISocialValues.add(mySocialValues[g+1]);
                }
            }
            myCamps[i] = new AICampObject(new SocietyChoices(AISocialValues), ValidSettlementPositions[i]);
        }
        PlayerCampTile = ValidSettlementPositions[possibleSettlements+1];
        return myCamps;
    }

    AICampObject[] myCamps;
    MapTile PlayerCampTile;
    public AICampObject[] getCamps()
    {
        return myCamps;
    }
    
    public MapTile getPlayerMapTile()
    {
        return PlayerCampTile;
    }
    
    Vector3 maxMapCoordinates = new Vector3(0,0,0);
    Vector3 minMapCoordinates = new Vector3(0,0,0);
    
    
    public Vector3 getMaxMapCoordinates()
    {
        return maxMapCoordinates;
    }
    
    public Vector3 getMinMapCoordinates()
    {
        return minMapCoordinates;
    }
    
    @Override
    public void run() {
        maxMapCoordinates = new Vector3(mapXAndYLength,mapXAndYLength,0);
        generateWorldMap(0,0,0,Math.floor((mapXAndYLength*mapXAndYLength*.3)), Math.floor(mapXAndYLength*mapXAndYLength*.7));
        System.out.print(this);
        linkMapTiles(0,0);
        if(isValidMap())
        {
        generateSettlements();
        }
        
    }

  
    private MapTile[][] getSurroundingTiles(int X, int Y)
    {
        MapTile[][] retTiles = new MapTile[3][3];
        retTiles[1][1] = getMapTile(X,Y);
        retTiles[0][0] = getMapTile(X-1,Y-1);
        retTiles[0][1] = getMapTile(X-1,Y);
        retTiles[0][2] = getMapTile(X-1,Y+1);
        retTiles[1][0] = getMapTile(X,Y-1);
        retTiles[1][2] = getMapTile(X,Y+1);
        retTiles[2][0] = getMapTile(X+1,Y-1);
        retTiles[2][1] = getMapTile(X+1,Y);
        retTiles[2][2] = getMapTile(X+1,Y+1);
        return retTiles;
    }
    
   public MapTile getMapTile(int X, int Y)
   {
       if((X > mapXAndYLength-1 || Y > mapXAndYLength-1)||(X < 0 || Y < 0))
       {
           return null;
       } else {
           return myMap[X][Y];
       }
   }

    
    @Override
    public String toString()
    {
        StringBuilder Map = new StringBuilder();
        for(int x = 0; x < mapXAndYLength; x++)
        {
            for(int y = 0; y < mapXAndYLength; y++)
            {
                if(myMap[x][y] != null) 
                {
                    Map.append(myMap[x][y]);
                }
            }
            Map.append("\n");
        }
        return Map.toString();
    }

    private void linkMapTiles(int xCoordinate, int yCoordinate) {
       MapTile ourTile = getMapTile(xCoordinate, yCoordinate);
       MapTile[][] surroundingTiles = getSurroundingTiles(xCoordinate, yCoordinate);
       if(ourTile.getNorthwest() == null && surroundingTiles[0][0] != null) ourTile.setNorthwest(surroundingTiles[0][0]);
       if(ourTile.getNorth() == null && surroundingTiles[1][0] != null) ourTile.setNorth(surroundingTiles[1][0]);
       if(ourTile.getNortheast() == null && surroundingTiles[2][0] != null) ourTile.setNortheast(surroundingTiles[2][0]);
       if(ourTile.getEast() == null && surroundingTiles[2][1] != null) ourTile.setEast(surroundingTiles[2][1]);
       if(ourTile.getSoutheast() == null && surroundingTiles[2][2] != null) ourTile.setSoutheast(surroundingTiles[2][2]);
       if(ourTile.getSouth() == null && surroundingTiles[1][2] != null) ourTile.setSouth(surroundingTiles[1][2]);
       if(ourTile.getSouthwest() == null && surroundingTiles[0][2] != null) ourTile.setSouthwest(surroundingTiles[0][2]);
       if(ourTile.getWest() == null && surroundingTiles[0][1] != null) ourTile.setWest(surroundingTiles[0][1]);
        xCoordinate++;
        if(xCoordinate > mapXAndYLength - 1)
        {
            xCoordinate = 0;
            yCoordinate += 1;
            if(yCoordinate > mapXAndYLength - 1)
            {
                return;
            }
        }
        linkMapTiles(xCoordinate, yCoordinate);
    }

    public MapTile[][] getMapTiles() {
        return myMap;
    }

}
