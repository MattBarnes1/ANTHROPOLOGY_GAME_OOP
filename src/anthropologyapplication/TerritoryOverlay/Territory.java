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
        this.gridsizeX = gridsize;
        this.gridsizeY = gridsize;
        this.myTerritoryLocation = new MapTile[gridsizeX][gridsizeY];
    }
    
    public void placeTerritoryAt(MapTile HomeTile, TribalCampObject whoseTerritory)
    {
        whoOwnsIt = whoseTerritory;
        Vector3 Coordinates = HomeTile.getCoordinates();
        Vector3 StartingCoordinatesLoop = new Vector3(Coordinates.X - gridsizeX, Coordinates.Y - gridsizeY, 0);
        MapTile MovingTile = HomeTile;
        int MidPoint = (int)Math.ceil(gridsizeX/2);
        for(int i = 1; i < MidPoint; i++)
        {
            if(MovingTile != null)
            {
                MovingTile.setTerritory(whoOwnsIt);
                MovingTile = MovingTile.getNorthwest();
                myTerritoryLocation[MidPoint-1-i][MidPoint-1-i] = MovingTile;
            } else {
                gridsizeX--;
                myTerritoryLocation[MidPoint-1-i][MidPoint-1-i] = null;
            }
        }
       
        fillRecursion(0, 0);
        
        for(int x = 0; x < gridsizeX; x++)
        {
            for(int y = 0; y < gridsizeY; y++)
            {
                if(myTerritoryLocation[x][y] != null)
                {
                    fillRecursion(x,y);//Finds first example to recurse from;
                    break;
                }
            }
        }    
        
    }
    
    
    private void fillRecursion(int x, int y)
    {
        if(x + 1 < gridsizeX)
        {
            if(myTerritoryLocation[x][y] != null)
            {
                if(myTerritoryLocation[x+1][y] == null && myTerritoryLocation[x][y].getEast() != null)
                {
                    myTerritoryLocation[x+1][y] = myTerritoryLocation[x][y].getEast();
                    myTerritoryLocation[x][y].getEast().setTerritory(whoOwnsIt);
                    fillRecursion(x+1, y);
                }
            }
        }
        if(x-1 > 0)
        {
            if(myTerritoryLocation[x][y] != null)
            {
                if(myTerritoryLocation[x-1][y] == null && myTerritoryLocation[x][y].getWest() != null)
                {
                    myTerritoryLocation[x-1][y] = myTerritoryLocation[x][y].getWest();
                    myTerritoryLocation[x][y].getWest().setTerritory(whoOwnsIt);
                    fillRecursion(x-1, y);
                }
            }
        }
        if(y+1 < gridsizeY)
        {
            if(myTerritoryLocation[x][y] != null)
            {
                if(myTerritoryLocation[x][y+1] == null && myTerritoryLocation[x][y].getNorth() != null)
                {
                    myTerritoryLocation[x][y+1] = myTerritoryLocation[x][y].getNorth();
                    myTerritoryLocation[x][y].getNorth().setTerritory(whoOwnsIt);
                    fillRecursion(x, y+1);
                }
            }
        }
        if(y-1 > 0)
        {
            if(myTerritoryLocation[x][y] != null)
            {
                if(myTerritoryLocation[x][y-1] == null && myTerritoryLocation[x][y].getSouth() != null)
                {
                    myTerritoryLocation[x-1][y-1] = myTerritoryLocation[x][y].getSouth();
                    myTerritoryLocation[x][y].getSouth().setTerritory(whoOwnsIt);
                    fillRecursion(x, y-1);
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
