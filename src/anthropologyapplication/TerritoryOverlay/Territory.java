/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package anthropologyapplication.TerritoryOverlay;

import anthropologyapplication.AutoMapper.MapTile;
import anthropologyapplication.AutoMapper.MapTile.TERRITORY_IMAGE;
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
        MapTile leftUpperCorner = HomeTile;
        int MidPoint = (int)Math.ceil(gridsizeX/2);
        for(int i = 1; i < MidPoint; i++)
        {
            if(leftUpperCorner != null)
            {
                leftUpperCorner.setTerritory(whoOwnsIt);
                leftUpperCorner = leftUpperCorner.getNorthwest();
                myTerritoryLocation[MidPoint-1-i][MidPoint-1-i] = leftUpperCorner;
            } else {
                gridsizeX--;
                myTerritoryLocation[MidPoint-1-i][MidPoint-1-i] = null;
            }
        }
       
        fillRecursion(0, 0);
        
  
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
    
    private void decideTerritoryImage(int x, int y)
    {
        
        if(x == 0 && y != 0 && y < gridsizeY-1)//Left Edge
        {
            if(myTerritoryLocation[x][y] != null)
            {
                if(myTerritoryLocation[x][y].getWest() != null)
                {
                    MapTile aTerritory = myTerritoryLocation[x][y].getWest();
                    if(aTerritory.isTerritoryOf(whoOwnsIt))
                    {
                        myTerritoryLocation[x][y].setTerritoryImage(TERRITORY_IMAGE.NONE);
                    } else {
                        myTerritoryLocation[x][y].setTerritoryImage(TERRITORY_IMAGE.WEST);
                    }
                }
            }
        }
        else if(y == 0 && x != 0 && x < gridsizeX-1) //Top, not corner Edge
        {
            if(myTerritoryLocation[x][y] != null)
            {
                    if(myTerritoryLocation[x][y].getNorth() != null)
                    {
                       MapTile aTerritory = myTerritoryLocation[x][y].getNorth();
                       if(aTerritory.isTerritoryOf(whoOwnsIt))
                       {
                         myTerritoryLocation[x][y].setTerritoryImage(TERRITORY_IMAGE.NONE);                          
                       } else {
                            myTerritoryLocation[x][y].setTerritoryImage(TERRITORY_IMAGE.NORTH);
                       }
                    }
            }
        }
        else if(x == 0 && y == 0) //Top Left Corner Edge
        {
            boolean ownsNorth = false;
            boolean ownsWest = false;
            boolean ownsNorthWest = false; //Used for determining if we're using a corner piece only
            if(myTerritoryLocation[x][y] != null)
            {
                MapTile aTile = myTerritoryLocation[x][y].getNorth();
                if(aTile != null)
                {       
                     ownsNorth = aTile.isTerritoryOf(whoOwnsIt);
                }
                aTile = myTerritoryLocation[x][y].getWest();
                if(aTile != null)
                {       
                     ownsWest = aTile.isTerritoryOf(whoOwnsIt);
                }
                aTile = myTerritoryLocation[x][y].getNorthwest();
                if(aTile != null)
                {       
                     ownsNorthWest = aTile.isTerritoryOf(whoOwnsIt);
                }
                if(ownsNorth && !ownsWest)
                {
                    myTerritoryLocation[x][y].setTerritoryImage(TERRITORY_IMAGE.WEST);
                } 
                else if (!ownsNorth && ownsWest)
                {
                    myTerritoryLocation[x][y].setTerritoryImage(TERRITORY_IMAGE.NORTH);
                }
                else if (!ownsNorth && !ownsWest)
                {
                    myTerritoryLocation[x][y].setTerritoryImage(TERRITORY_IMAGE.NORTHWEST);
                }
                else if (ownsNorth && ownsWest && !ownsNorthWest)
                {
                    //TODO: Corner Piece
                }
            }
        }
        else if(x == 0 && y == gridsizeY-1) //Bottom Left
        {
                boolean ownsSouth = false;
                boolean ownsWest = false;
                boolean ownsSouthwest = false; //Used for determining if we're using a corner piece only      
                
                MapTile aTile = myTerritoryLocation[x][y].getSouth();
                if(aTile != null)
                {       
                     ownsSouth = aTile.isTerritoryOf(whoOwnsIt);
                }
                aTile = myTerritoryLocation[x][y].getWest();
                if(aTile != null)
                {       
                     ownsWest = aTile.isTerritoryOf(whoOwnsIt);
                }
                aTile = myTerritoryLocation[x][y].getSouthwest();
                if(aTile != null)
                {       
                     ownsSouthwest = aTile.isTerritoryOf(whoOwnsIt);
                }
                if(ownsSouth && !ownsWest)
                {
                    myTerritoryLocation[x][y].setTerritoryImage(TERRITORY_IMAGE.WEST);
                } 
                else if (!ownsSouth && ownsWest)
                {
                    myTerritoryLocation[x][y].setTerritoryImage(TERRITORY_IMAGE.SOUTH);
                }
                else if (!ownsSouth && !ownsWest)
                {
                    myTerritoryLocation[x][y].setTerritoryImage(TERRITORY_IMAGE.SOUTHWEST);
                }
                else if (ownsSouth && ownsWest && !ownsSouthwest)
                {
                    //TODO: Corner Piece
                }
        }
        else if(x == gridsizeX && y == 0) //Upper left
        {
            boolean ownsNorth = false;
                boolean ownsEast = false;
                boolean ownsNorthEast = false; //Used for determining if we're using a corner piece only      
                
                MapTile aTile = myTerritoryLocation[x][y].getNorth();
                if(aTile != null)
                {       
                     ownsNorth = aTile.isTerritoryOf(whoOwnsIt);
                }
                aTile = myTerritoryLocation[x][y].getEast();
                if(aTile != null)
                {       
                     ownsEast = aTile.isTerritoryOf(whoOwnsIt);
                }
                aTile = myTerritoryLocation[x][y].getNortheast();
                if(aTile != null)
                {       
                     ownsNorthEast = aTile.isTerritoryOf(whoOwnsIt);
                }
                if(ownsNorth && !ownsEast)
                {
                    myTerritoryLocation[x][y].setTerritoryImage(TERRITORY_IMAGE.EAST);
                } 
                else if (!ownsNorth && ownsEast)
                {
                    myTerritoryLocation[x][y].setTerritoryImage(TERRITORY_IMAGE.NORTH);
                }
                else if (!ownsNorth && !ownsEast)
                {
                    myTerritoryLocation[x][y].setTerritoryImage(TERRITORY_IMAGE.NORTHEAST);
                }
                else if (ownsNorth && ownsEast && !ownsNorthEast)
                {
                    //TODO: Corner Piece
                }
        }
        else if(x == gridsizeX && y < gridsizeY-1 && y > 0) //Right side side
        {
            if(myTerritoryLocation[x][y] != null)
            {
                if(myTerritoryLocation[x][y].getEast() != null)
                {
                    MapTile aTerritory = myTerritoryLocation[x][y].getEast();
                    if(aTerritory == null)
                    {
                        if(aTerritory.isTerritoryOf(whoOwnsIt))
                        {
                             myTerritoryLocation[x][y].setTerritoryImage(TERRITORY_IMAGE.NONE);
                        } else {
                             myTerritoryLocation[x][y].setTerritoryImage(TERRITORY_IMAGE.EAST);
                        }
                    }
                        
                }
            }
        }
        else if(y == gridsizeY && x > 0 && x < gridsizeX-1) //Bottom Edge
        {
            if(myTerritoryLocation[x][y] != null)
            { 
                if(myTerritoryLocation[x][y].getWest() != null)
                {
                    MapTile aTerritory = myTerritoryLocation[x][y].getWest();
                   if(aTerritory.isTerritoryOf(whoOwnsIt))
                   {
                        myTerritoryLocation[x][y].setTerritoryImage(TERRITORY_IMAGE.WEST);
                   }
                        
                } 
            } 
        }
        else if(x == gridsizeX && y == gridsizeY) //Bottom Right Corner
        {
            if(myTerritoryLocation[x][y] != null)
            {
                boolean ownsSouth = false;
                boolean ownsEast = false;
                boolean ownsSouthEast = false; //Used for determining if we're using a corner piece only      
                
                MapTile aTile = myTerritoryLocation[x][y].getSouth();
                if(aTile != null)
                {       
                     ownsSouth = aTile.isTerritoryOf(whoOwnsIt);
                }
                aTile = myTerritoryLocation[x][y].getEast();
                if(aTile != null)
                {       
                     ownsEast = aTile.isTerritoryOf(whoOwnsIt);
                }
                aTile = myTerritoryLocation[x][y].getSoutheast();
                if(aTile != null)
                {       
                     ownsSouthEast = aTile.isTerritoryOf(whoOwnsIt);
                }
                if(ownsSouth && !ownsEast)
                {
                    myTerritoryLocation[x][y].setTerritoryImage(TERRITORY_IMAGE.EAST);
                } 
                else if (!ownsSouth && ownsEast)
                {
                    myTerritoryLocation[x][y].setTerritoryImage(TERRITORY_IMAGE.SOUTH);
                }
                else if (!ownsSouth && !ownsEast)
                {
                    myTerritoryLocation[x][y].setTerritoryImage(TERRITORY_IMAGE.SOUTHEAST);
                }
                else if (ownsSouth && ownsEast && !ownsSouthEast)
                {
                    //TODO: Corner Piece
                }   
            }
        }   
        else
        {
            if(myTerritoryLocation[x][y] != null)
            { 
                myTerritoryLocation[x][y].setTerritoryImage(TERRITORY_IMAGE.NONE);
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
