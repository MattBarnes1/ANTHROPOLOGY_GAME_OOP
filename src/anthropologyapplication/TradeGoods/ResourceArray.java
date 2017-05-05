/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package anthropologyapplication.TradeGoods;

/**
 *
 * @author Duke
 */
public class ResourceArray {
    private final int[] myResources;

    
    public ResourceArray(int[] ResourcesRequired)
    {
        this.myResources = ResourcesRequired;
    }
    
    public int getAmountAtIndex(int i) {
        return myResources[i];
    }

    public int getLength() {
        return myResources.length;
    }

    public ResourceArray multiply(float numberofHomesToBuildCurrently) {
        ResourceArray myArray = new ResourceArray(this, (int)numberofHomesToBuildCurrently);
        return myArray;
    }
    
    private ResourceArray(ResourceArray myOldArray, int numberofHomesToBuildCurrently)
    {
        myResources = new int[myOldArray.getLength()];
        for(int i = 0 ; i < myOldArray.getLength(); i++)
        {
            myResources[i] = myOldArray.getAmountAtIndex(i) * numberofHomesToBuildCurrently;
        }
    }
    
}
