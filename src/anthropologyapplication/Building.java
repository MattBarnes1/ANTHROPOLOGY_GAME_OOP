/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package anthropologyapplication;

import anthropologyapplication.AutoMapper.MapTile;
import anthropologyapplication.AutoMapper.Vector3;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

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
        
        public abstract boolean canBuildOnTile(MapTile aTile);
        public abstract Building Copy();
        
        public void startBuildingAtLocation(MapTile aTile, MapTile[][] surroundingTiles)
        {
            BuiltOn = aTile;
            aTile.setForegroundImage(ForegroundImageFileName);
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
        
       

        protected Building(String Name, String Description, float BuildTimeMS, int Index, int BuildersRequired, String ForegroundImageFileName, String ForegroundImageDestroyedFileName)
        {
            this.ForegroundImageFileName = ForegroundImageFileName;
            this.ForegroundImageDestroyedFileName = ForegroundImageDestroyedFileName;
            this.BuildersRequired = BuildersRequired;
            BuildingIndex = Index;
            this.BuilidingName = Name;
            this.Description = Description;
            timeTillBuilt = new Time(0,0,0,BuildTimeMS);
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

        Time timeTillBuilt;
        boolean isFinishedBuilding = false;
        void update(GameTime T) {
            timeTillBuilt = timeTillBuilt.subtract(T.getElapsedTime());
            if(!isFinishedBuilding)
            {
                isFinishedBuilding = timeTillBuilt.EqualTo(new Time(0,0,0));
            }
        }

        boolean isFinishedBuilding() {
            return isFinishedBuilding;
        }


        void onLock(TribalCampObject anObject)
        {

        }

        void onUnlock(TribalCampObject anObject) {

        }


    public Time getBuildTime() {
        return timeTillBuilt;
    }


  

  
}

