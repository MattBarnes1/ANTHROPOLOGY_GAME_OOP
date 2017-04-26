/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package anthropologyapplication.Buildings;

import anthropologyapplication.AutoMapper.AutoMapperGui;
import anthropologyapplication.AutoMapper.MapTile;
import anthropologyapplication.AutoMapper.Vector3;
import anthropologyapplication.internalLockers.internalBuildingLocker;
import anthropologyapplication.DisplayData.BuildingConstructionDisplayData;
import anthropologyapplication.GameTime;
import anthropologyapplication.Timer;
import anthropologyapplication.TribalCampObject;
import java.util.ArrayList;
import java.util.Iterator;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

/**
 *
 * @author Duke
 */
public class BuildingHandler {
                private final String NoFile = "NoFile";
                private internalBuildingLocker[] internalBuildingList = new internalBuildingLocker[] { 
                    new internalBuildingLocker(new TribalHut("Tribal Hut", "Goverment", new Timer(0,1,1,0), 0, 2, 5,"TribalCamp.jpg", "TribalCampDestroyed.jpg"), false),
                    new internalBuildingLocker(new Granary("Granary", "Food Storage",   new Timer(0,0,0,5), 1, 2, 3,"Granary.jpg", "GranaryDestroyed.jpg"), true),
                    new internalBuildingLocker(new Field ("Field", "A field",           new Timer(0,5,5,5), 2, 4, 3,"Field.jpg", "FieldDestroyed.jpg"), true),
                    new internalBuildingLocker(new Workshop("Workshop", "A workshop",   new Timer(0,0,0,5), 3, 2, 3,"Workshop.jpg", "WorkshopDestroyed.jpg"), true),
                    new internalBuildingLocker(new Blacksmith("Blacksmith", "A workshop",  new Timer(0,0,0,5), 4, 2, 3, "Blacksmith.jpg", "BlacksmithDestroyed.jpg"), false),
                    new internalBuildingLocker(new Homes("Homes", "A workshop",  new Timer(0,0,0,5), 5, 2, 3, "Homes.jpg","HomesDestroyed.jpg"), true),
                    new internalBuildingLocker(new Smelterer("Smelterer", "A workshop",  new Timer(0,0,0,5), 6, 2, 3,"Smelterer.jpg", "SmeltererDestroyed.jpg"), true)  
                };//this is a template list of all buildings
		private int BuildersBuilding = 0; //number of people building
		private ArrayList<BuildingConstructionDisplayData> BuildingsThatCanBeBuilt = new ArrayList<>(); //Used for building in menu
                 //This is updated for the player when they call so we don't have to iterate through a list.
                private int currentlyAllowedTier = 0; //Unlocked level
		private ArrayList<Building> BuildingsBeingConstructed = new ArrayList<>();
                private ArrayList<Building> BuildingsBuilt = new ArrayList<>();
                private final TribalCampObject myTribe;
                
                
		public BuildingHandler(TribalCampObject myObject)
		{
                    this.myTribe = myObject;
                    updatePossibleBuildings();
		}

                private void updatePossibleBuildings()
                {
                    BuildingsThatCanBeBuilt.clear();
                    for(internalBuildingLocker aVal : internalBuildingList)
                    {
                        if(aVal.Available)
                        {
                            BuildingsThatCanBeBuilt.add(new BuildingConstructionDisplayData(aVal.myBuilding));
                        }
                    }
                    
                }
                
               
                
                
		public void lockBuilding(Class<? extends Building> aBuilding)
                {
                    for(internalBuildingLocker A : internalBuildingList)
                    {
                        if(A.myBuilding.getClass() == aBuilding)
                        {
                            A.Available = false;
                            A.myBuilding.onLock(myTribe);
                            updatePossibleBuildings();
                            hasChanged = true;
                        }
                    }
                }
                
                public void unlockBuilding(Class<? extends Building> aBuilding)
                {
                    for(internalBuildingLocker A : internalBuildingList)
                    {
                        if(A.myBuilding.getClass() == aBuilding)
                        {
                            A.Available = true;
                            A.myBuilding.onUnlock(myTribe);
                            updatePossibleBuildings();
                            hasChanged = true;
                        }
                    }
                }
                
                public void update(GameTime T)
                {
                    if(this.BuildersBuilding != 0)
                    {
                        Iterator<Building> buildingIterator = BuildingsBeingConstructed.iterator();
                        float J = getNumberofBuildersRequired();
                        if(J == 0) return;
                        float Ratio = (float)(BuildersBuilding/J);
                        while(buildingIterator.hasNext())
                        {
                            Building myNext = buildingIterator.next();
                            if(internalBuildingList[myNext.getIndex()].Available)
                            {
                                myNext.update(T, Ratio);
                                if(myNext.isFinishedBuilding())
                                {
                                    myNext.forceBuildAtLocation(myNext.getBuildingTile(), myTribe);
                                    BuildingsBuilt.add(myNext);
                                    buildingIterator.remove();
                                }
                            }   
                        }
                    }
                }
                
                private int getNumberofBuildersRequired()
                {
                    int ret = 0;
                    Iterator<Building> buildingIterator = BuildingsBeingConstructed.iterator();
                    while(buildingIterator.hasNext())
                    {
                        Building aBuilding = buildingIterator.next();
                        ret += aBuilding.getRequiredBuildersAmount();
                    }
                    return ret;
                }
                
                
		public void forceBuild(String aBuildingName, MapTile aLocation)
                {
			for(internalBuildingLocker A : internalBuildingList)
                        {
                            if(A.myBuilding.getBuildingName().compareTo(aBuildingName) == 0)
                            {
                                if(BuildingsBuilt.contains(A))
                                {
                                  if(A.myBuilding.canBuildMultiples())
                                  {
                                      Building newBuilding = A.myBuilding.Copy();
                                      newBuilding.forceBuildAtLocation(aLocation, myTribe);
                                      BuildingsBuilt.add(newBuilding);
                                      AutoMapperGui.redrawMap();
                                      return;
                                  }
                                } else {
                                      Building newBuilding = A.myBuilding.Copy();
                                      newBuilding.forceBuildAtLocation(aLocation, myTribe);
                                      BuildingsBuilt.add(newBuilding);
                                      AutoMapperGui.redrawMap();
                                      return;
                                }
                            }
                        }
                        throw new UnsupportedOperationException("Attempted to force build a building, but couldn't find it!");
                }

                
                
                
		public BuildingConstructionDisplayData startBuilding(String aBuildingName, MapTile aTile)
		{//TODO: Valid maptile check
                    BuildingConstructionDisplayData retVal = null;
			for(internalBuildingLocker A : internalBuildingList)
                        {
                            if(A.myBuilding.getBuildingName().compareTo(aBuildingName) == 0)
                            {
                                if(A.myBuilding.canBuildOnTile(myTribe, aTile))
                                {
                                    if((BuildingsBuilt.contains(A) && A.myBuilding.canBuildMultiples()) || !BuildingsBuilt.contains(A))
                                    {
                                          Building newBuilding = A.myBuilding.Copy();
                                          newBuilding.startBuildingAtLocation(aTile);
                                          retVal = new BuildingConstructionDisplayData(newBuilding);
                                          BuildingsBeingConstructed.add(newBuilding);
                                    }
                                }
                            }
                        }
                    return retVal;
		}

		public ArrayList<BuildingConstructionDisplayData> getBuiltBuildings()
		{
			ArrayList<BuildingConstructionDisplayData> myRetString = new ArrayList<BuildingConstructionDisplayData>();
                        for(Building B : BuildingsBuilt)
                        {
                            myRetString.add(new BuildingConstructionDisplayData(B));
                        }
                        return myRetString;
		}
                
                public int countBuildingsInList(Class<? extends Building> aClass)
                {
                    return countBuildingsInList(BuildingsBuilt, aClass);
                }
                
                
                private int countBuildingsInList(ArrayList<Building> aBuildingList, Class<? extends Building> aClass)
                {
                    int Count = 0;
                    for(Building B : aBuildingList)
                    {
                        if(B.getClass() == aClass)
                        {
                            Count++;
                        }
                    }
                    return Count;
                }
                    
                
                
                public BuildingConstructionDisplayData[] updateBuildingsBeingConstructedDisplay()
                {
                    BuildingConstructionDisplayData[] Data = new BuildingConstructionDisplayData[BuildingsBeingConstructed.size()];
                    for(int i = 0 ; i < BuildingsBeingConstructed.size(); i++)
                    {
                        Data[i] = new BuildingConstructionDisplayData(BuildingsBeingConstructed.get(i));
                    }
                    return Data;
                }
                
                
		public ArrayList<BuildingConstructionDisplayData> getBuildable()
		{
			return BuildingsThatCanBeBuilt;
		}

		public void addBuilders(int amount)
		{
			BuildersBuilding += amount;
		}
                
                public boolean canRemoveMore()
                {
                    return ((BuildersBuilding - 1) > 0);
                }

		public void removeBuilders(int amount)
		{
			if(BuildersBuilding - amount < 0)
                        {
                            BuildersBuilding = 0;
                        } else {
                            BuildersBuilding -= amount;
                        }
		}

		public int getBuildersAmount()
		{
                    return BuildersBuilding;
		}

		public ArrayList<Building> getAllBuiltBuildingsByType(Class<? extends Building> theClass)
		{
                    ArrayList<Building> retBuilding = new ArrayList<Building>();
                    for(Building A : BuildingsBuilt)
                    {
                       //System.out.println(A.getClass());
                        if(A.getClass() == theClass)
                        {
                           retBuilding.add(A);
                        }
                    }
                    return retBuilding;
		}

                
                public ArrayList<Building> getBuildingsCurrentlyBeingBuiltByType(Class<? extends Building> myClass)
                { 
                    ArrayList<Building> retBuilding = new ArrayList<Building>();
                    for(Building A : BuildingsBeingConstructed)
                    {
                       //System.out.println(A.getClass());
                        if(A.getClass() == myClass)
                        {
                           retBuilding.add(A);
                        }
                    }
                    return retBuilding;
                }
    
               /* public boolean hasWorking(Class<? extends Building> aClass) {
                    ArrayList<Building> retBuilding = getAllBuiltBuildingsByType(aClass);
                    retBuilding.iterator();
                }*/

    
    
    public boolean hasWorking(Class<? extends Building> aClass) {
        for(int i = 0; i < BuildingsBuilt.size(); i++)
        {
            Building aBuilding = BuildingsBuilt.get(i);
            if(aBuilding.getClass() == aClass)
            {
                if(!aBuilding.isDestroyed())
                {
                    return true;
                }
            }
        }
        return false;
    }

    public void stopBuilding(Building ProgressData) {
        ProgressData.stopConstruction();
        this.BuildingsBeingConstructed.remove(ProgressData);
    }

    boolean hasChanged = false;
    public boolean hasChanged() {
        if(hasChanged == true)
        {
            hasChanged = false;
            return true;
        } else {
            return false;
        }
    }


}
