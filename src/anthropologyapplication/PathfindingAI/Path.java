/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package anthropologyapplication.PathfindingAI;

import anthropologyapplication.Logger.FileLogger;
import anthropologyapplication.AutoMapper.MapTile;
import anthropologyapplication.AutoMapper.Vector3;
import anthropologyapplication.Map;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.function.Consumer;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author noone
 */
public class Path {

    private PathObject getLowestHeuristicValue(ArrayList<PathObject> aOpenList) {
        PathObject Lowest = null;
        for(PathObject X : aOpenList)
        {
            if(Lowest == null)
            {
                Lowest = X;
            } else {
                if(Lowest.CostFromHereToGoal > X.CostFromHereToGoal)
                {
                    Lowest = X;
                }
            }
        }
        return Lowest;
    }

    private boolean checkListHasTile(ArrayList<PathObject> ClosedList, MapTile X) {
        for(PathObject x : ClosedList)
        {
            if(x.aTile == X)
            {
                return true;
            }
        }
        return false;
    }

    private void doPathfindingDebugSuccess() {
        if(isValidPath())
        {
           Iterator<MapTile> g = getPath();
            while(g.hasNext())
            {
                g.next().doPathfinderDebug();
            }
            FileLogger.writeToLog(FileLogger.LOGTO.PATHFINDER, getClass().toString(), "Pathfinding Success!"  + System.getProperty("line.separator") + Map.getMapString() + System.getProperty("line.separator"));
           
            g = getPath();
            while(g.hasNext())
            {
                g.next().clearPathfinderDebug();
            }
        }
    }

private void doPathfindingDebugFailure() {
        if(isValidPath())
        {
           Iterator<MapTile> g = getPath();
            while(g.hasNext())
            {
                g.next().doPathfinderDebug();
            }
                FileLogger.writeToLog(FileLogger.LOGTO.PATHFINDER, getClass().toString(), "Pathfinding Failure!"  + System.getProperty("line.separator") + Map.getMapString() + System.getProperty("line.separator"));

            g = getPath();
            while(g.hasNext())
            {
                g.next().clearPathfinderDebug();
            }
        }
    }
    

        
    
        
        public Iterator<MapTile> getPath()
        {
            return new PathIterator(this.cameFromPath);
        }
        
        public Iterator<MapTile> getReversePath()
        {
            return new ReversePathIterator(this.cameFromPath);
        }
        
        
       
        
        
        private ArrayList<PathObject> OpenList = new ArrayList<>();
        private ArrayList<PathObject> ClosedList = new ArrayList<>();
        private ArrayList<PathObject> cameFromPath = new ArrayList<>();
        protected ArrayList<Vector3> myVector = new ArrayList<Vector3>();
        protected ArrayList<Integer> totalCostOfTravel = new ArrayList<Integer>();
        
        
        public Path(MapTile startTile, MapTile endTile)
        {
            OpenList.add(new PathObject(startTile, 0, getDistance(startTile, endTile)));
            PathObject Current;
           
            while(!OpenList.isEmpty())
            {
                Current = getLowestHeuristicValue(OpenList);
                if(Current.aTile == endTile )
                {
                    convertToPath(Current);
                    OpenList = null;
                    ClosedList = null; //cleared for memory reasons
                    doPathfindingDebugSuccess();
                    return;
                }
                OpenList.remove(Current);
                ClosedList.add(Current);
                ArrayList<MapTile> mySurroundingTiles = getNeighbors(Current.aTile);
                startTile.doPathfinderDebug();
                for(MapTile X : mySurroundingTiles)
                {
                    if(X.isLand())
                    {
                            if(checkListHasTile(ClosedList, X))
                            {
                                continue;
                            }

                            int Score = Current.CostFromStartToHere + 1; //Moving 1 tile
                            PathObject newPath = null;
                            if(!checkListHasTile(OpenList, X))
                            {
                                newPath = new PathObject(X, Score,  Score + getDistance(X, endTile));
                                newPath.CameFrom = Current;
                                newPath.CostFromStartToHere = Score;
                                newPath.CostFromHereToGoal =  Score + getDistance(X, endTile);
                                OpenList.add(newPath);
                            }
                            else if (Score + getDistance(X, endTile) >= Current.CostFromHereToGoal)
                            {
                                continue;
                            }
                            //Map.printMap();
                    }
                }
                if(OpenList.isEmpty())
                {
                    convertToPath(Current); //It failed to find the right location
                    ValidPath = false;
                }
            }

            doPathfindingDebugFailure();
        }
        
        
        
        private void convertToPath(PathObject comeFrom)
        {
            if(comeFrom.CameFrom != null)
            {
                convertToPath(comeFrom.CameFrom);
                cameFromPath.add(comeFrom);
            } else {
                cameFromPath.add(comeFrom);
            }
        }
        
        boolean ValidPath = true;
        public boolean isValidPath()
        {
            return cameFromPath != null;
        }
        
        private ArrayList<MapTile> getNeighbors(MapTile aTile)
        {
            ArrayList<MapTile> myList = new ArrayList<MapTile>();
            if(aTile.getNorth() != null)
            {
                myList.add(aTile.getNorth());
            }
            if(aTile.getEast() != null)
            {
                myList.add(aTile.getEast());
            }
            if(aTile.getSouth() != null)
            {
                myList.add(aTile.getSouth());
            }
            if(aTile.getWest() != null)
            {
                myList.add(aTile.getWest());
            }
            if(aTile.getNortheast() != null)
            {
                myList.add(aTile.getNortheast());
            }
            if(aTile.getNorthwest() != null)
            {
                myList.add(aTile.getNorthwest());
            }
            if(aTile.getSoutheast() != null)
            {
                myList.add(aTile.getSoutheast());
            }
            if(aTile.getSouthwest() != null)
            {
                myList.add(aTile.getSouthwest());
            }
            
            return myList;
        }
         
        
        private int getDistance(MapTile Current, MapTile Next)
        {
            Vector3 nextCoords = Next.getCoordinates();
            Vector3 currentCoords = Current.getCoordinates(); 
            int dx = Math.abs(nextCoords.X - currentCoords.X);
            int dy = Math.abs(nextCoords.Y - currentCoords.Y);
            return dx+dy;
        }    
        
	public int getLength()
        {
            return myVector.size();
        }
	public Vector3 getStep(int index)
        {
            return myVector.get(index);
        }
	public int getX(int index)
        {
            return myVector.get(index).X;
        }
	public int getY(int index)
        {
            return myVector.get(index).Y;
        }
	public void appendStep(MapTile newStep)
        {
            totalCostOfTravel.add(newStep.getCost());
            myVector.add(newStep.getCoordinates());
        }
	public void prependStep(MapTile newStep)
        {
            totalCostOfTravel.add(newStep.getCost());
            myVector.add(0, newStep.getCoordinates());
        }
        
        public void removeLast()
        {
            totalCostOfTravel.remove(totalCostOfTravel.size()-1);
            myVector.remove(myVector.size()-1);
        }
        
	public boolean contains(int x, int y)
        {
           for(Vector3 aVector : myVector)
           {
               if(aVector.X == x && aVector.Y == y)
               {
                   return true;
               }
           }
           return false;
        } //GT PreOrder/Post-Order level-order
        
      

    int getTotalCost() {
       int Cost = 0;
       for(Integer X : totalCostOfTravel)
       {
           Cost += X.intValue();
       }
       return Cost;
    }

    void appendToPath(Path FirstPath) {
      myVector.addAll(FirstPath.myVector);
      totalCostOfTravel.addAll(FirstPath.totalCostOfTravel);
    }

    public Vector3 getLast() {
       return myVector.get(myVector.size()-1);//TODO: is this right?
    }

    @Override
    public String toString()
    {
        String ret = "";
       for(Vector3 aVector : myVector)
       {
           ret += aVector.toString() + " ";
       }
       return ret;
    }
    
    boolean isEmpty() {
       return myVector.isEmpty();
    }
}
