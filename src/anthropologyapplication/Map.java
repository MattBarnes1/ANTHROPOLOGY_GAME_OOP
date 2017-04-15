/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package anthropologyapplication;

import AIStuff.AICampObject;
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
    Random myRandomNumberGen = new Random();//TODO better seeding

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
        
        TerrainGenerator.start();
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
    Random myRandom = new Random();
    Boolean[][] myLocationsValidLines = new Boolean[possibleSettlements + 1][possibleSettlements + 1]; 
    int Count = 0;
    while(Count <= possibleSettlements + 1)
    {
        while(Count <= possibleSettlements + 1)
        {
            int xLocation = 0;
            while (xLocation < 5 || xLocation > myMap.length-5)
            {
                xLocation = myRandom.nextInt(myMap.length);
            }
            int yLocation = 0;
            while (yLocation < 5 || yLocation > myMap.length-5)
            {
                yLocation = myRandom.nextInt(myMap.length);
            }
            if (!myMap[xLocation][yLocation].isLand())
            {
                continue;
            }
            int waterTiles = 0;
            int landTiles = 0;
            for(int x = xLocation - 1; x < xLocation + 2; x++)
            {
                for(int y = yLocation - 1; y < yLocation + 2; y++)
                {
                    if(myMap[x][y].isLand())
                    {
                        landTiles++;
                    } else {
                        waterTiles++;
                    }
                }
            }
            if ((landTiles - 1) < waterTiles)
            {
                continue;
            }
            
            if(checkDistance(myMap[xLocation][yLocation], habitable))
            {
                habitable.add(myMap[xLocation][yLocation]);
                myMap[xLocation][yLocation].setTown();
                Count++;
            }

          
        }
          for(int i = 0; i < habitable.size(); i++)
            {
                for(int g = 0; g < habitable.size(); g++)
                {
                    if(pathFindFromTo(habitable.get(i), habitable.get(g), false)!= null)
                    {
                        resetDebug();
                        if(CheckPathNumber(habitable.get(i), habitable) < CheckPathNumber(habitable.get(g),habitable))
                        {
                            System.out.println("Removing a town!");
                            habitable.get(i).resetTown();
                            habitable.get(i).setRemove();
                            System.out.println(this.toMapString());
                             habitable.get(i).resetRemove();
                            habitable.remove(i);
                            
                            Count--;
                        } 
                        else if (CheckPathNumber(habitable.get(i), habitable) == CheckPathNumber(habitable.get(g), habitable))
                        {
                            System.out.println("All Towns can find eachother!");
                        }
                        else 
                        {
                            System.out.println("Removing a town!");
                            habitable.get(g).resetTown();
                            habitable.get(g).setRemove();
                            System.out.println(this.toMapString());
                             habitable.get(i).resetRemove();
                            habitable.remove(g);
                            Count--;
                        }
                    }
                }
    }
        
    }    
        
    

    MapTile[] myLocations = new MapTile[habitable.size()];
    habitable.toArray(myLocations);
    for(MapTile aLocation : myLocations)
    {
        aLocation.resetTown(); //Mostly for the debug;
    }
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

    private String toMapString()
    {
        StringBuilder Map = new StringBuilder();
        for(int x = 0; x < mapXAndYLength; x++)
        {
            for(int y = 0; y < mapXAndYLength; y++)
            {
                if(myMap[x][y] != null) 
                {
                    if(myMap[x][y].toString().length() == 1)
                    {
                        Map.append(myMap[x][y].toString());
                    }
                    else if(myMap[x][y].getClass() == MapTile_Land.class)
                    {
                        Map.append("L");
                    } else {
                        Map.append("W");
                    }
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
            @SuppressWarnings("empty-statement")
            protected String call() throws Exception {
                maxMapCoordinates = new Vector3(mapXAndYLength,mapXAndYLength,0);
                double AmountOfWater = Math.floor((mapXAndYLength*mapXAndYLength*.3));
                double AmountOfLand = Math.floor((mapXAndYLength*mapXAndYLength*.7));
                double counter = 0;
                 //Wait for Terrain Generator to be done;
                 float[][] values = null;
                while((values = TerrainGenerator.getPerlin()) == null){
                    this.updateMessage(TerrainGenerator.getInternalMessage());
                };
                this.updateMessage("Interpreting noise from generator");
                InterpretPerlinNoise(AmountOfWater, AmountOfLand, values);
                System.out.println(toMapString());
                if(isValidMap())
                {
                  generateSettlements();
                }
                
                Automap.setMap(aJumper, myCode);
                while(!Automap.isDone())
                {
                    updateMessage(Automap.getInternalMessage());
                    updateProgress(Automap.getInternalWorkCompleted(), Automap.getInternalWorkTotal());
                }

                
                
                
                return null;
            }         

            private void InterpretPerlinNoise(double AmountOfWater, double AmountOfLand, float[][] PerlinNoiseGeneration) throws SecurityException {
                for(int x = 0; x < mapXAndYLength; x++)
                {
                    for(int y = 0; y < mapXAndYLength; y++)
                    {
                           if(PerlinNoiseGeneration[x][y] < 0.7)
                           {
                               myMap[x][y] = new MapTile_Land();
                               myMap[x][y].setCoordinates(new Vector3(x,y,0));
                           } else {
                               myMap[x][y] = new MapTile_Water();
                               myMap[x][y].setCoordinates(new Vector3(x,y,0));
                           }
                    }
                    super.updateProgress((double)(x+1)*mapXAndYLength,(double)(mapXAndYLength*mapXAndYLength));
                }
            }         
        };         
        return aTask;  
    }        

   
    private int CheckPathNumber(MapTile toCheck, ArrayList<MapTile> aMap)
    {
        int Counter = 0;
        for(int i = 0; i < aMap.size(); i++)
        {
            toCheck.setDebugActiveLooker();
            if(pathFindFromTo(toCheck, aMap.get(i), false) != null)
            {
              resetDebug();
              Counter++;  
            }
            toCheck.resetDebugActiveLooker();
        }
        return Counter;
    }

    //ArrayList<MapTile> TilesToBeConsidered = new ArrayList<MapTile>();
    private Path pathFindFromTo(MapTile startTile, MapTile endTile, boolean ignoreSplit) {
        
        float maxSearchDistance = mapXAndYLength*mapXAndYLength;
        ArrayList<MapTile> TilesConsideredFail = new ArrayList<MapTile>();
        ArrayList<MapTile> myTiles = new ArrayList<MapTile>();
        Path myPath = new Path(); 
        MapTile currentlyLookingAt = startTile;
        myPath.appendStep(currentlyLookingAt);
        //if(startTile == endTile)
        //{
         //   return myPath;
        //}
        
        //MapTile[][] myTile = this.getSurroundingTiles(startTile.getCoordinates().X, startTile.getCoordinates().Y);
        int maxDepth = 0;
        while (true) 
        {
            if(currentlyLookingAt == endTile)
            {
                System.out.println("Success! We found the town!");
                myPath.appendStep(currentlyLookingAt);
                break;
            }
            
            
            
            MapTile[][] myTile = getSurroundingTiles(currentlyLookingAt.getCoordinates().X, currentlyLookingAt.getCoordinates().Y);
            MapTile leastNextTile = null;
            MapTile leastNextTile2 = null;
            
            for(int x = 0; x < myTile.length; x++)
            {
                for(int y = 0; y < myTile[x].length; y++)
                {
                    if(myTile[x][y] != null)
                    { 
                        if (myTile[x][y].isLand() && !TilesConsideredFail.contains(myTile[x][y]) && !myPath.contains(myTile[x][y].getCoordinates().X, myTile[x][y].getCoordinates().Y) && !myTile[x][y].canBlockMovement())
                        {
                            if(leastNextTile == null)
                            {
                                leastNextTile = myTile[x][y];
                            } else {
                                int lnTile = distanceHeuristic(leastNextTile, endTile) + leastNextTile.getCost();
                                int nTile = distanceHeuristic(myTile[x][y], endTile) + myTile[x][y].getCost();
                                if(lnTile > nTile)
                                {
                                    leastNextTile = myTile[x][y];
                                    leastNextTile2 = null; //Used for determining a second path if needed.
                                } 
                                else if (lnTile == nTile && !ignoreSplit)
                                {
                                    leastNextTile2 = myTile[x][y];
                                }
                            }
                        } 
                       
                    }
                }
            }
            
            if(leastNextTile2 != null)
            {
              System.out.println("Pathfinder is splitting here!");
              System.out.println(this.toMapString());
              Path FirstPath = pathFindFromTo(leastNextTile, endTile, true);
              Path SecondPath = pathFindFromTo(leastNextTile2, endTile, true);
              if(FirstPath == null && SecondPath != null)
              {
                  int happens = 0;
              }
              if(FirstPath != null && SecondPath == null)
              {
                  int happens = 0;
              }
              System.out.println("Pathfinder is splitting here!");
              System.out.println(this.toMapString());
              if(FirstPath.getTotalCost() >= SecondPath.getTotalCost())
              {
                  FirstPath = null;
                  myPath.appendToPath(SecondPath);
                    System.out.println("Map finished: Success");
                    assert(maxDepth < maxSearchDistance);

                        return myPath;
              } else {
                  SecondPath = null;
                  myPath.appendToPath(FirstPath);
                    System.out.println("Map finished: Success");

                        return myPath;
              }
            } else {
                if(leastNextTile == null)
                {
                    int i = 0;
                }
                Vector3 myVector3 = leastNextTile.getCoordinates();
                if(myPath.contains(myVector3.X, myVector3.Y))
                {
                    if(myPath.isEmpty())
                    {
                        if(myPath.getLast().isEqualTo(myVector3))
                        {
                            //currentlyLookingAt.doPathfinderDebug();
                            TilesConsideredFail.add(currentlyLookingAt); //it's pointing us backwards
                            currentlyLookingAt.resetDebugActiveLooker();
                            currentlyLookingAt = leastNextTile;
                        maxDepth--; //This will move us backwards to the tile
                        } else {
                            currentlyLookingAt.doPathfinderDebug();
                            System.out.println(this.toMapString());
                            System.out.println("Map finished: Failed");
                            return null; //the thing did a loop which doesn't make any sense;
                        }
                    } else {
                            currentlyLookingAt.resetDebugActiveLooker();
                        System.out.println(this.toMapString());
                        System.out.println("Map finished: Failed");
                        return null;
                    }
                } else {
                    currentlyLookingAt.doPathfinderDebug();
                    currentlyLookingAt = leastNextTile;
                    currentlyLookingAt.doPathfinderDebug();
                    myPath.appendStep(leastNextTile);
                    maxDepth++;
                }
            }
            
        }
        assert(maxDepth < maxSearchDistance);
        System.out.println(this.toMapString());
         System.out.println("Map finished: Success");
        return myPath;
    }

    private void resetDebug()
    {
        for(int x = 0; x < this.mapXAndYLength; x++)
        {
            for(int y = 0; y < this.mapXAndYLength; y++)
            {
                  this.myMap[x][y].clearPathfinderDebug();
            }
        }
    }
    
    private int distanceHeuristic(MapTile Current, MapTile Next)
    {
        Vector3 nextCoords = Next.getCoordinates();
        Vector3 currentCoords = Current.getCoordinates(); 
        int dx = Math.abs(nextCoords.X - currentCoords.X);
        int dy = Math.abs(nextCoords.Y - currentCoords.Y);
        return dx+dy;
    }       

    PerlinNoiseGeneratorForTerrain getTerrainGenerator() {
        return TerrainGenerator;
    }

    private boolean checkDistance(MapTile mapTile, ArrayList<MapTile> habitable) {
       for(MapTile A : habitable)
       {
           if( distanceHeuristic(mapTile, A) < mapXAndYLength*0.25)
           {
               return false;
           }
       }
       return true;
    }
    
}


