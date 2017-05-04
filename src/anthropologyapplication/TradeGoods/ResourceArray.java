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
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
