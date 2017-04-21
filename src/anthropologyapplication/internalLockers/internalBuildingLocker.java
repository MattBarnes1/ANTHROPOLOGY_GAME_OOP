/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package anthropologyapplication.internalLockers;

import anthropologyapplication.Buildings.Building;

/**
 *
 * @author Duke
 */
public class internalBuildingLocker
{
    public boolean Available = true;
    public Building myBuilding;
    public internalBuildingLocker(Building aBuilding, boolean isAvailable)
    {
        this.Available = isAvailable;
        myBuilding = aBuilding;
    }

}