/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Buildings;

import anthropologyapplication.AutoMapper.MapTile;
import anthropologyapplication.AutoMapper.Vector3;
import anthropologyapplication.GameTime;
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
            aTile.setForeground1Image(ForegroundImageFileName);
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
        
       int BuilderRequired;
        final Timer BuildTimeToBuild;
        protected Building(String Name, String Description, Timer BuildTime, int Index, int BuildersRequired, String ForegroundImageFileName, String ForegroundImageDestroyedFileName)
        {
            this.BuildersRequired = BuilderRequired;
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
        void update(GameTime T) {
            timeTillBuilt = timeTillBuilt.subtract(T.getElapsedTime());
            if(!isFinishedBuilding)
            {
                isFinishedBuilding = timeTillBuilt.EqualTo(new Timer(0,0,0,0));
            }
        }
        
        public int getBaseNumberOfBuilders()
        {
            return BuilderRequired;
        }
        

        boolean isFinishedBuilding() {
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

    void forceBuildAtLocation(MapTile aLocation) {
            BuiltOn = aLocation;
            aLocation.setForeground1Image(ForegroundImageFileName);
    }

    public String getTotalBuildTime()
    {
        return BuildTimeToBuild.toString();
    }



  
}

