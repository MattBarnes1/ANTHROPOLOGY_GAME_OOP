/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package anthropologyapplication.TradeGoods;


import anthropologyapplication.Buildings.Blacksmith;
import anthropologyapplication.Buildings.Homes;
import anthropologyapplication.Buildings.Smelterer;
import anthropologyapplication.TradeGoods.Wood;
import anthropologyapplication.DisplayData.ProductProductionDisplayData;
import anthropologyapplication.GameTime;
import anthropologyapplication.Timer;
import anthropologyapplication.TribalCampObject;
import anthropologyapplication.internalLockers.internalProductLocker;
import java.util.ArrayList;
import java.util.Iterator;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

/**
 *
 * @author Duke
 */
public class ProductionHandler
	{
		
		int ProducersAmount = 0;
                private internalProductLocker[] internalProductList = new internalProductLocker[] {
                    new internalProductLocker(new Clay("Clay", 10, new Timer(0,2,0,1), 0), true),
                    new internalProductLocker(new Wood("Wood", 10, new Timer(0,2,0,1), 0), true),
                    new internalProductLocker(new Stone("Stone", 10, new Timer(0,2,0,1), 0), true),
                    new internalProductLocker(new Smelted_Copper("Smelted Copper", 0, new Timer(0,0,0,1), 0), false),
                    new internalProductLocker(new Unsmelted_Copper("Unsmelted Copper", 0, new Timer(0,0,0,1), 0), false)
                };
                
                private TribalCampObject myTribe;
       
                
                private ArrayList<ProductProductionDisplayData> ProductsThatCanBeBuilt = new ArrayList<>();
                private ArrayList<TradeGood> ProductsBeingMade = new ArrayList<>();
                
                
                
                
		public ProductionHandler(TribalCampObject myObject)
		{
                        this.myTribe = myObject;
			updatePossibleItems();
		}

		public void update(GameTime MS)
		{
                   Iterator<TradeGood> myTradeGood = ProductsBeingMade.iterator();
                    if(myTribe.getProductionHandler().getAmountByString("Clay") > 20 && myTribe.getProductionHandler().getAmountByString("Wood") > 20)
                    {
                        if(!myTribe.getBuildingHandler().isUnlocked(Homes.class))
                        {
                            this.hasChanged = true;
                            myTribe.getBuildingHandler().unlockBuilding(Homes.class);
                        }
                    }
                    else if (myTribe.getBuildingHandler().isUnlocked(Homes.class))
                    {
                       myTribe.getBuildingHandler().lockBuilding(Homes.class);
                        this.hasChanged = true;
                    }
                    if(myTribe.getProductionHandler().getAmountByString("Clay") > 50 && myTribe.getProductionHandler().getAmountByString("Wood") > 50
                            && myTribe.getProductionHandler().getAmountByString("Stone") > 50)
                    {
                        if(!myTribe.getBuildingHandler().isUnlocked(Smelterer.class))
                        {
                            this.hasChanged = true;
                            myTribe.getBuildingHandler().unlockBuilding(Smelterer.class);
                        }
                    } else if (myTribe.getBuildingHandler().isUnlocked(Smelterer.class))
                    {
                        if(!myTribe.getBuildingHandler().isUnlocked(Homes.class))
                        {
                            this.hasChanged = true;
                            myTribe.getBuildingHandler().lockBuilding(Smelterer.class);
                        }
                    }
                    if(myTribe.getProductionHandler().getAmountByString("Clay") > 100 && myTribe.getProductionHandler().getAmountByString("Wood") > 100
                            && myTribe.getProductionHandler().getAmountByString("Stone") > 100)
                    {
                        if(!myTribe.getBuildingHandler().isUnlocked(Blacksmith.class))
                        {
                            this.hasChanged = true;
                           myTribe.getBuildingHandler().unlockBuilding(Blacksmith.class);
                        }
                    } 
                    else if (myTribe.getBuildingHandler().isUnlocked(Blacksmith.class))
                    {
                            this.hasChanged = true;
                            myTribe.getBuildingHandler().lockBuilding(Blacksmith.class);
                    }
                   while(myTradeGood.hasNext())
                   {
                       TradeGood stuff = myTradeGood.next();
                       stuff.update(MS, myTribe);
                       if(stuff.needsAnUpdate())
                       {
                           hasChanged = true;
                       }
                   }
		}
               
                
                public void lockTradeGood(Class<? extends TradeGood> aTradeGood)
                {
                     hasChanged = true;
                     for(internalProductLocker A : internalProductList)
                    {
                        if(A.myTradeGood.getClass() == aTradeGood)
                        {
                            A.Available = false;
                            updatePossibleItems();
                        }
                    }
                    
                }
                
                public void unlockTradeGood(Class<? extends TradeGood> aTradeGood)
                {
                     hasChanged = true;
                     for(internalProductLocker A : internalProductList)
                    {
                        if(A.myTradeGood.getClass() == aTradeGood)
                        {
                            A.Available = true;
                            updatePossibleItems();
                        }
                    }
                }
                
		public TradeGood[] getTradeGoodInventory()
		{
			throw new NotImplementedException();
		}

		public void addProducers(int amount)
		{
                    ProducersAmount++;
                    calculateMaxNumberOfGoodsCanWorkOn();
		}

		public void removeProducers()
		{
                    hasChanged = true;
                    if((ProducersAmount - 1) > 0)
                    {
			ProducersAmount = ProducersAmount - 1;
                    } else {
                        ProducersAmount = 0;
                    }
                    calculateMaxNumberOfGoodsCanWorkOn();
                    while(maxActiveTradeGoods < currentlyActiveTradeGoods)
                    {
                       this.removeProductFromProduction(this.ProductsBeingMade.get(ProductsBeingMade.size()-1));
                    }
		}

            private void updatePossibleItems() { 
                    ProductsThatCanBeBuilt.clear();
                    for(internalProductLocker aVal : internalProductList)
                    {
                        if(aVal.Available)
                        {
                            ProductsThatCanBeBuilt.add(new ProductProductionDisplayData(aVal.myTradeGood));
                        }
                    }
            }

    public int getProducersAmount() {
        return ProducersAmount;
    }

    
    public Iterator<ProductProductionDisplayData> getTradeGoodsAvailable() {
        return ProductsThatCanBeBuilt.iterator();
    }

    public int getAmountByString(String string) {
        for(int i = 0; i < internalProductList.length; i++)
        {
            if(internalProductList[i].getName().compareTo(string) == 0)
            {
                return internalProductList[i].getAmount();
            }
        }
        assert(false);
        return -1;
    }
    
    int maxActiveTradeGoods = 0;
    int currentlyActiveTradeGoods = 0;
    public void calculateMaxNumberOfGoodsCanWorkOn()
    {
        if(ProducersAmount != 0)
        {
            maxActiveTradeGoods = (ProducersAmount - (ProducersAmount % 2))/2;
        } else {
            maxActiveTradeGoods = 0;
        }
    }
    
    public boolean canRemoveMore()
    {
        return ((ProducersAmount - 1) >= 0);
    }
    
    public boolean canAddTradeGood() {
        return (currentlyActiveTradeGoods < maxActiveTradeGoods);
    }

    public ProductProductionDisplayData addProductionItem(String text) {
       for(internalProductLocker aLocker : this.internalProductList)
       {
           if(aLocker.getName().compareTo(text) == 0)
           {
                hasChanged = true;
               this.ProductsBeingMade.add(aLocker.myTradeGood);
               currentlyActiveTradeGoods++;
               return new ProductProductionDisplayData(aLocker.myTradeGood);
           }
       }
       return null;
    }

    public void removeProductFromProduction(TradeGood ProgressData) {
        if(this.ProductsBeingMade.remove(ProgressData))
        {
            
            hasChanged = true;
            currentlyActiveTradeGoods--;
        }
    }
    
    public void clearProductsInProduction()
    {
        this.ProductsBeingMade.clear();
    }
    
    public boolean isProducing(TradeGood myTradeGood)
    {
        return this.ProductsBeingMade.contains(myTradeGood);
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

    public int getAmountAtIndex(int i) {
        return internalProductList[i].getAmount();
    }

    public void subtractFromResourceAtIndex(int i, int amountAtIndex) {
        internalProductList[i].reduceAmount(i);
    }

}
