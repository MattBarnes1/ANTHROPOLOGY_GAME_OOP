/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package anthropologyapplication;


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
		TradeGood[] TradeGoodList;
		int ProducersAmount = 0;
                private internalProductLocker[] internalProductList = new internalProductLocker[] {
                                     
                };
       
                private ArrayList<String> ProductsThatCanBeBuilt = new ArrayList<>();
                private ArrayList<TradeGood> ProductsBeingMade = new ArrayList<>();
                
                
		public ProductionHandler()
		{
			updatePossibleItems();
		}

		public void update(GameTime MS)
		{
			throw new NotImplementedException();
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
		}

		public void removeProducers(int Amount)
		{
                    if((ProducersAmount - Amount) > 0)
                    {
			ProducersAmount--;
                    } else {
                        ProducersAmount = 0;
                    }
		}

            private void updatePossibleItems() { 
                    ProductsThatCanBeBuilt.clear();
                    for(internalProductLocker aVal : internalProductList)
                    {
                        if(aVal.Available)
                        {
                            ProductsThatCanBeBuilt.add(aVal.myTradeGood.getName());
                        }
                    }
            }
	}
