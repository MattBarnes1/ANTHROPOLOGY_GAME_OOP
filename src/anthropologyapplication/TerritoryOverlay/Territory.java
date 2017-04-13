/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package anthropologyapplication.TerritoryOverlay;

import anthropologyapplication.AutoMapper.MapTile;
import anthropologyapplication.AutoMapper.Vector3;
import anthropologyapplication.Map;
import anthropologyapplication.TribalCampObject;

/**
 *
 * @author noone
 */
public class Territory {
    int gridsizeX;
    int gridsizeY;
    MapTile[][] myTerritoryLocation;
    TribalCampObject whoOwnsIt;
    public Territory(int gridsize)
    {
        assert(gridsize % 2 == 1);
        this.gridsizeX = gridsizeX;
        this.gridsizeY = gridsizeY;
        this.myTerritoryLocation = new MapTile[gridsizeX][gridsizeY];
    }
    
    public void placeTerritoryAt(MapTile HomeTile, TribalCampObject whoseTerritory, Map theMap)
    {
        whoOwnsIt = whoseTerritory;
        Vector3 Coordinates = HomeTile.getCoordinates();
        Vector3 StartingCoordinatesLoop = new Vector3(Coordinates.X - gridsizeX, Coordinates.Y - gridsizeY, 0);
        for(int x = 0; x < gridsizeX; x++)
        {
            for(int y = 0; y < gridsizeY; y++)
            {
                myTerritoryLocation[x][y] = theMap.getMapTile(StartingCoordinatesLoop.X + x,StartingCoordinatesLoop.Y + y);
                if(myTerritoryLocation[x][y] != null)
                {
                    myTerritoryLocation[x][y].setTerritory(whoseTerritory);
                }
            }
        }    
        
    }
    
    public void removeTerritoryFrom()
    {
      for(int x = 0; x < gridsizeX; x++)
        {
            for(int y = 0; y < gridsizeY; y++)
            {
                if(myTerritoryLocation[x][y] != null && whoOwnsIt != null)
                {
                    myTerritoryLocation[x][y].setTerritory(null);
                }
            }
        }
    }
    
    public void restoreTerritory()
    {
      for(int x = 0; x < gridsizeX; x++)
        {
            for(int y = 0; y < gridsizeY; y++)
            {
                if(myTerritoryLocation[x][y] != null && whoOwnsIt != null)
                {
                    myTerritoryLocation[x][y].setTerritory(whoOwnsIt);
                }
            }
        }
    }
    
    
}
