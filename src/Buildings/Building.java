/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Buildings;

import anthropologyapplication.AutoMapper.MapTile;
import anthropologyapplication.AutoMapper.Vector3;
import anthropologyapplication.GameTime;
import anthropologyapplication.TerritoryOverlay.Territory;
import anthropologyapplication.Timer;
import anthropologyapplication.TribalCampObject;
//import sun.reflect.generics.reflectiveObjects.NotImplementedException;

/**
 *
 * @author Duke
 */

public abstract class Building
{
        private Vector3 BuildingCoordinates;
        private String BuilidingName;
        private String Description;
        private boolean canBuildMultiples = false;
        private boolean Destroyed = false;
        private boolean Destroyable = true;
        private int BuildingIndex;
        private int BuildersRequired;
        private Territory myTerritory;
        private MapTile BuiltOn;
        private final String ForegroundImageFileName;
        private final String ForegroundImageDestroyedFileName;
        public void removeFromMapTile()
        {
            BuiltOn.clearForeground();
        }
        
        public abstract boolean canBuildOnTile(TribalCampObject myObject, MapTile aTile);
        public abstract Building Copy();
        
        public void startBuildingAtLocation(MapTile aTile)
        {
            BuiltOn = aTile;
           // aTile.setForeground1Image(ForegroundImageFileName); //This will be the construction image of our file
        }
       
        protected int getTerritorySize()
        {
            return TerritorySize;
        }
        public String getBuildingName()
        {
            return BuilidingName;
        }       
        
        public boolean isDestroyed()
        {
            return Destroyed;
        }
        
        public boolean isDestroyable()
        {
            return Destroyable;
        }
        
        final Timer BuildTimeToBuild;
       final int TerritorySize;
        protected Building(String Name, String Description, Timer BuildTime, int Index, int BuildersRequired, int territorySize, String ForegroundImageFileName, String ForegroundImageDestroyedFileName)
        {
            assert(territorySize % 2 != 0);
            TerritorySize = territorySize;
            myTerritory = new Territory(territorySize);
            this.ForegroundImageFileName = ForegroundImageFileName;
            this.ForegroundImageDestroyedFileName = ForegroundImageDestroyedFileName;
            this.BuildersRequired = BuildersRequired;
            BuildingIndex = Index;
            this.BuilidingName = Name;
            this.Description = Description;
            timeTillBuilt = BuildTime;
            BuildTimeToBuild = BuildTime;
        }
        
        public String getForeGroundImageName()
        {
            return this.ForegroundImageFileName;
        }
        
        public String getForeGroundDestroyedImageName()
        {
            return this.ForegroundImageDestroyedFileName;
        }
        public int getIndex()
        {
            return BuildingIndex;
        }

        public boolean canBuildMultiples()
        {
            return canBuildMultiples;
        }

        public String getDescription()
        {
            return Description;
        }

        Timer timeTillBuilt;
        boolean isFinishedBuilding = false;
        void update(GameTime T, double Ratio) {
            //System.out.println("Build Time: " + timeTillBuilt);
            if(!isFinishedBuilding)
            {
               // System.out.println("T.Elapsed: " + T.getElapsedTime());
                timeTillBuilt = ((timeTillBuilt.subtract(T.getElapsedTime().multiply(Ratio))));
               // System.out.println("timeTillBuilt.subtract(T.getElapsedTime()): " + timeTillBuilt.subtract(T.getElapsedTime()));
                isFinishedBuilding = (timeTillBuilt.EqualTo(new Timer(0,0,0,0)));;
                
            }
        }
        
        public int getRequiredBuildersAmount()
        {
            return BuildersRequired;
        }
        

        public boolean isFinishedBuilding() {
            return isFinishedBuilding;
        }


        void onLock(TribalCampObject anObject)
        {

        }

        void onUnlock(TribalCampObject anObject) {

        }


    public Timer getBuildTime() {
        return timeTillBuilt;
    }

    void forceBuildAtLocation(MapTile aLocation, TribalCampObject myOwner) {
            BuiltOn = aLocation;
            timeTillBuilt = new Timer(0,0,0,0);
            this.myTerritory.placeTerritoryAt(aLocation, myOwner);
            aLocation.setBuildingOnThis(this);
    }

    public String getTotalBuildTime()
    {
        return BuildTimeToBuild.toString();
    }

    public double getCompletionPercentage() {
        return timeTillBuilt.dividedBy(BuildTimeToBuild);
    }

    public MapTile getBuildingTile() {
        return BuiltOn;
    }

   

   




  
}

