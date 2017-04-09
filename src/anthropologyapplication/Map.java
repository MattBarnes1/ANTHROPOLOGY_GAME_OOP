/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package anthropologyapplication;

import anthropologyapplication.AutoMapper.AutoMapperGui;
import anthropologyapplication.AutoMapper.MapTile;
import anthropologyapplication.AutoMapper.Vector3;
import anthropologyapplication.MapTiles.MapTile_Land;
import anthropologyapplication.MapTiles.MapTile_Water;
import java.util.ArrayList;
import java.util.Random;
import javafx.application.Platform;
import javafx.beans.property.ReadOnlyBooleanProperty;
import javafx.beans.property.ReadOnlyDoubleProperty;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.property.ReadOnlyStringProperty;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.concurrent.Worker;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

/**
 *
 * @author Duke
 */
public class Map extends Service{
    MapTile[][] myMap;
    int mapXAndYLength = 0;
    private final MapTile[] BaseTileTypes = new MapTile[]{ new MapTile_Land(), new MapTile_Water()};
    int possibleSettlements = 3;
    Random myRandomNumberGen = new Random(47);//TODO better seeding

    float BaseProbability_Water = 0.1F;
    float BaseProbability_Land = 0.9F;
    int Value = myRandomNumberGen.nextInt(101);//TODO
    MainGameCode myCode;
    AutoMapperGui Automap;
    Map(int mapXAndYLength, MainGameCode aThis, AutoMapperGui automapper) {
        
        TerrainGenerator = new PerlinNoiseGeneratorForTerrain(0, mapXAndYLength, mapXAndYLength); 
        myCode = aThis;
        Automap = automapper;
        this.mapXAndYLength = mapXAndYLength;
        myMap = new MapTile[mapXAndYLength][mapXAndYLength];
    }
    public void generateWorldMap(int xCoord, int yCoord, int zCoord, double WaterTilesRemaining, double LandTilesRemaining)
    {
        if(Value <= (BaseProbability_Water*100)) //This Generates first tile!
        {
            myMap[xCoord][yCoord] = new MapTile_Water();
            ((MapTile_Water)myMap[xCoord][yCoord]).generateSubType(getSurroundingTiles(xCoord, yCoord),myRandomNumberGen, WaterTilesRemaining);
            ((MapTile_Water)myMap[xCoord][yCoord]).setCoordinates(new Vector3(xCoord, yCoord, zCoord));
        } else {
            myMap[xCoord][yCoord] = new MapTile_Land();
            ((MapTile_Land)myMap[xCoord][yCoord]).setCoordinates(new Vector3(xCoord, yCoord, zCoord));
            ((MapTile_Land)myMap[xCoord][yCoord]).generateSubType(getSurroundingTiles(xCoord, yCoord),myRandomNumberGen, LandTilesRemaining);

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
        PlayerCampTile = ValidSettlementPositions[possibleSettlements+1];
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
    Map aJumper = this;
    
  
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
    }

    public MapTile[][] getMapTiles() {
        return myMap;
    }

    PerlinNoiseGeneratorForTerrain TerrainGenerator;
        
    @Override
    protected Task createTask() {
        Task aTask = new Task<String>() {
            @Override
            protected String call() throws Exception {
                maxMapCoordinates = new Vector3(mapXAndYLength,mapXAndYLength,0);
                double AmountOfWater = Math.floor((mapXAndYLength*mapXAndYLength*.3));
                double AmountOfLand = Math.floor((mapXAndYLength*mapXAndYLength*.7));
                double counter = 0;
                while(TerrainGenerator.isRunning()){}; //Wait for Terrain Generator to be done;
                float[][] values = (float[][])TerrainGenerator.getValue();
                for(int x = 0; x < mapXAndYLength; x++)
                {
                    for(int y = 0; y < mapXAndYLength; y++)
                    {
                        //counter += ((double)((x+1)*(y+1)));
                        Value = myRandomNumberGen.nextInt(101);
                        generateWorldMap(x,y,0,AmountOfWater, AmountOfLand);
                        linkMapTiles(x,y);
                        if(getMapTile(x,y).getClass().getDeclaringClass() == MapTile_Land.class)
                        {
                            AmountOfLand--;
                        } else {
                            AmountOfWater--;
                        }
                    }
                    super.updateProgress((double)(x+1)*mapXAndYLength,(double)(mapXAndYLength*mapXAndYLength));
                }
                if(isValidMap())
                {
                  generateSettlements();
                }
                
                Automap.setMap(aJumper, myCode);
                
                return null;
            }         
        };         
        return aTask;  
    }        
        
    
}
