/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package anthropologyapplication.TradeGoods;


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
                    new internalProductLocker(new Clay("Clay", 100, new Timer(0,0,0,1), 0), true),
                    new internalProductLocker(new Wood("Wood", 100, new Timer(0,0,0,1), 0), true),
                    new internalProductLocker(new Stone("Stone", 100, new Timer(0,0,0,1), 0), true),
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
                   while(myTradeGood.hasNext())
                   {
                       TradeGood stuff = myTradeGood.next();
                       stuff.update(MS, myTribe);
                   }
		}
               
                
                public void lockTradeGood(Class<? extends TradeGood> aTradeGood)
                {
                     for(internalProductLocker A : internalProductList)
                    {
                        if(A.myTradeGood.getClass() == aTradeGood)
                        {
                            A.Available = false;
                        }
                    }
                    
                }
                
                public void unlockTradeGood(Class<? extends TradeGood> aTradeGood)
                {
                     for(internalProductLocker A : internalProductList)
                    {
                        if(A.myTradeGood.getClass() == aTradeGood)
                        {
                            A.Available = true;
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

		public void removeProducers(int Amount)
		{
                    if((ProducersAmount - Amount) > 0)
                    {
			ProducersAmount--;
                    } else {
                        ProducersAmount = 0;
                    }
                    calculateMaxNumberOfGoodsCanWorkOn();
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
    
    public void calculateMaxNumberOfGoodsCanWorkOn()
    {
        
    }
    
    public boolean canRemoveMore()
    {
        return ((ProducersAmount - 1) > 0);
    }
    
    public boolean canAddTradeGood() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void addProductionItem(String text) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void removeProductFromProduction(TradeGood ProgressData) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
