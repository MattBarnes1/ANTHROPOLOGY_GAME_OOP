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
    int gridsizeXOffset = 0;
    int gridsizeYOffset = 0;
    MapTile[][] myTerritoryLocation;
    TribalCampObject whoOwnsIt;
    public Territory(int gridsize)
    {
        assert(gridsize % 2 != 1);
        this.gridsizeX = gridsize;
        this.gridsizeY = gridsize;
        this.myTerritoryLocation = new MapTile[gridsizeX][gridsizeY];
    }
    
    public void placeTerritoryAt(MapTile HomeTile, TribalCampObject whoseTerritory)
    {
        
        whoOwnsIt = whoseTerritory;
        Vector3 Coordinates = HomeTile.getCoordinates();
        int MidPoint = (int)Math.ceil(((float)gridsizeX)/2);
        myTerritoryLocation[MidPoint][MidPoint] = HomeTile;
        MapTile OriginTile = HomeTile;
        for(int i = MidPoint-1; i > 0; i--)
        {
            if(OriginTile.getNorthwest() != null)
            {
                OriginTile = OriginTile.getNorthwest();
            } else {
                if(OriginTile != null)
                {
                    if(OriginTile.getNorth() != null && OriginTile.getWest() == null)
                    {
                        gridsizeXOffset++;
                    }
                    else if(OriginTile.getNorth() == null && OriginTile.getWest() != null)
                    {
                        gridsizeYOffset++;
                    }
                    else {
                        gridsizeXOffset++;
                        gridsizeYOffset++;
                    }
                }
            }
        }
        myTerritoryLocation[gridsizeXOffset][gridsizeYOffset] = OriginTile;
                
        for(int x = gridsizeXOffset; x < gridsizeX; x++)
        {
            if(myTerritoryLocation[x][0] != null)
            {
                for(int y = gridsizeYOffset; y < gridsizeY; y++)
                {
                    if(myTerritoryLocation[x][y] != null)
                    {
                        if(myTerritoryLocation[x][y].getSouth() != null)
                        {
                            if(y+1 < gridsizeY) //to prevent index problem
                            {
                                myTerritoryLocation[x][y+1] = myTerritoryLocation[x][y].getSouth();
                                myTerritoryLocation[x][y+1].setTerritory(whoOwnsIt);
                            }
                            if(x+1 < gridsizeX)
                            {
                                myTerritoryLocation[x+1][y] = myTerritoryLocation[x][y].getEast();
                                myTerritoryLocation[x+1][y].setTerritory(whoOwnsIt);
                            }
                        } else {
                            gridsizeY--;
                        }
                    }
                }
            } else {
                gridsizeX--;
            }
        }
        
        for(int x = gridsizeXOffset; x < gridsizeX; x++)
        {
           for(int y = gridsizeYOffset; y < gridsizeY; y++)
           {
               decideTerritoryImage(x,y);
           }
        }
        
    }
    
    
    
    
    private void decideTerritoryImage(int x, int y)
    {
        
        if(x == gridsizeXOffset && y != gridsizeYOffset && y < gridsizeY-1)//Left Edge
        {
            if(myTerritoryLocation[x][y] != null)
            {
                if(myTerritoryLocation[x][y].getWest() != null)
                {
                    MapTile aTerritory = myTerritoryLocation[x][y].getWest();
                    if(aTerritory.isTerritoryOf(whoOwnsIt))
                    {
                        myTerritoryLocation[x][y].setTerritoryImage(TERRITORY_IMAGE.NONE);
                        changeAdjacentTerritory(myTerritoryLocation[x][y], myTerritoryLocation[x][y].getWest());
                    } else {
                        myTerritoryLocation[x][y].setTerritoryImage(TERRITORY_IMAGE.WEST);
                    }
                }
            }
        }
        else if(y == gridsizeYOffset && x != gridsizeXOffset && x < gridsizeX-1) //Top, not corner Edge
        {
            if(myTerritoryLocation[x][y] != null)
            {
                    if(myTerritoryLocation[x][y].getNorth() != null)
                    {
                       MapTile aTerritory = myTerritoryLocation[x][y].getNorth();
                       if(aTerritory.isTerritoryOf(whoOwnsIt))
                       {
                         myTerritoryLocation[x][y].setTerritoryImage(TERRITORY_IMAGE.NONE);
                         changeAdjacentTerritory(myTerritoryLocation[x][y], myTerritoryLocation[x][y].getNorth());                          
                       } else {
                            myTerritoryLocation[x][y].setTerritoryImage(TERRITORY_IMAGE.NORTH);
                       }
                    }
            }
        }
        else if(x == gridsizeXOffset && y == gridsizeYOffset) //Top Left Corner Edge
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
                    changeAdjacentTerritory(myTerritoryLocation[x][y], myTerritoryLocation[x][y].getNorth());
                } 
                else if (!ownsNorth && ownsWest)
                {
                    myTerritoryLocation[x][y].setTerritoryImage(TERRITORY_IMAGE.NORTH);
                    changeAdjacentTerritory(myTerritoryLocation[x][y], myTerritoryLocation[x][y].getWest());
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
        else if(x == gridsizeXOffset && y == gridsizeY-1) //Bottom Left
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
                    changeAdjacentTerritory(myTerritoryLocation[x][y], myTerritoryLocation[x][y].getSouth());
                } 
                else if (!ownsSouth && ownsWest)
                {
                    myTerritoryLocation[x][y].setTerritoryImage(TERRITORY_IMAGE.SOUTH);
                    changeAdjacentTerritory(myTerritoryLocation[x][y], myTerritoryLocation[x][y].getWest());
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
        else if(x == gridsizeX-1 && y == gridsizeYOffset) //Upper left
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
                    changeAdjacentTerritory(myTerritoryLocation[x][y], myTerritoryLocation[x][y].getEast());
                } 
                else if (!ownsNorth && ownsEast)
                {
                    myTerritoryLocation[x][y].setTerritoryImage(TERRITORY_IMAGE.NORTH);
                    changeAdjacentTerritory(myTerritoryLocation[x][y], myTerritoryLocation[x][y].getNorth());
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
        else if(x == gridsizeX-1 && y < gridsizeY-1 && y > gridsizeYOffset) //Right side side
        {
            if(myTerritoryLocation[x][y] != null)
            {
                if(myTerritoryLocation[x][y].getEast() != null)
                {
                    MapTile aTerritory = myTerritoryLocation[x][y].getEast();

                    if(aTerritory.isTerritoryOf(whoOwnsIt))
                    {
                         myTerritoryLocation[x][y].setTerritoryImage(TERRITORY_IMAGE.NONE);
                    changeAdjacentTerritory(myTerritoryLocation[x][y], myTerritoryLocation[x][y].getEast());
                    } else {
                         myTerritoryLocation[x][y].setTerritoryImage(TERRITORY_IMAGE.EAST);
                    }
                }
            }
        }
        else if(y == gridsizeY-1 && x > gridsizeXOffset && x < gridsizeX-1) //Bottom Edge
        {
            if(myTerritoryLocation[x][y] != null)
            { 
                if(myTerritoryLocation[x][y].getSouth() != null)
                {
                    MapTile aTerritory = myTerritoryLocation[x][y].getSouth();
                   if(aTerritory.isTerritoryOf(whoOwnsIt))
                   {     
                        myTerritoryLocation[x][y].setTerritoryImage(TERRITORY_IMAGE.NONE); 
                        changeAdjacentTerritory(myTerritoryLocation[x][y], myTerritoryLocation[x][y].getSouth());              
                   } else {
                        myTerritoryLocation[x][y].setTerritoryImage(TERRITORY_IMAGE.SOUTH);  
                   }
                        
                } 
            } 
        }
        else if(x == gridsizeX-1 && y == gridsizeY-1) //Bottom Right Corner
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
                    changeAdjacentTerritory(myTerritoryLocation[x][y], myTerritoryLocation[x][y].getSouth());
                } 
                else if (!ownsSouth && ownsEast)
                {
                    myTerritoryLocation[x][y].setTerritoryImage(TERRITORY_IMAGE.SOUTH);
                    changeAdjacentTerritory(myTerritoryLocation[x][y], myTerritoryLocation[x][y].getEast());
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

    private void changeAdjacentTerritory(MapTile changedTile, MapTile Changee) {
        if(Changee.getNorth() == changedTile)
        {
            if(Changee.getTerritoryImage() == TERRITORY_IMAGE.NORTH)
            {
                Changee.setTerritoryImage(TERRITORY_IMAGE.NONE);  
            }
            else if(Changee.getTerritoryImage() == TERRITORY_IMAGE.NORTHEAST)
            {
                Changee.setTerritoryImage(TERRITORY_IMAGE.EAST);
            }
            else if(Changee.getTerritoryImage() == TERRITORY_IMAGE.NORTHWEST)
            {
                Changee.setTerritoryImage(TERRITORY_IMAGE.WEST);
            }
        }
        if(Changee.getSouth() == changedTile)
        {
            if(Changee.getTerritoryImage() == TERRITORY_IMAGE.SOUTH)
            {
                Changee.setTerritoryImage(TERRITORY_IMAGE.NONE);  
            }
            else if(Changee.getTerritoryImage() == TERRITORY_IMAGE.SOUTHEAST)
            {
                Changee.setTerritoryImage(TERRITORY_IMAGE.EAST);
            }
            else if(Changee.getTerritoryImage() == TERRITORY_IMAGE.SOUTHWEST)
            {
                Changee.setTerritoryImage(TERRITORY_IMAGE.WEST);
            }
        }
        if(Changee.getEast() == changedTile)
        {
            if(Changee.getTerritoryImage() == TERRITORY_IMAGE.EAST)
            {
                Changee.setTerritoryImage(TERRITORY_IMAGE.NONE);  
            }
            else if(Changee.getTerritoryImage() == TERRITORY_IMAGE.SOUTHEAST)
            {
                Changee.setTerritoryImage(TERRITORY_IMAGE.SOUTH);
            }
            else if(Changee.getTerritoryImage() == TERRITORY_IMAGE.NORTHEAST)
            {
                Changee.setTerritoryImage(TERRITORY_IMAGE.NORTH);
            }
        }
        if(Changee.getWest() == changedTile)
        {
            if(Changee.getTerritoryImage() == TERRITORY_IMAGE.WEST)
            {
                Changee.setTerritoryImage(TERRITORY_IMAGE.NONE);  
            }
            else if(Changee.getTerritoryImage() == TERRITORY_IMAGE.SOUTHWEST)
            {
                Changee.setTerritoryImage(TERRITORY_IMAGE.SOUTH);
            }
            else if(Changee.getTerritoryImage() == TERRITORY_IMAGE.NORTHWEST)
            {
                Changee.setTerritoryImage(TERRITORY_IMAGE.NORTH);
            }
        }
    }
    
    
}
