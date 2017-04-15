/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package anthropologyapplication;

import anthropologyapplication.AutoMapper.MapTile;
import anthropologyapplication.AutoMapper.Vector3;
import java.util.ArrayList;

/**
 *
 * @author noone
 */
public class Path {
        protected ArrayList<Vector3> myVector = new ArrayList<Vector3>();
        protected ArrayList<Integer> totalCostOfTravel = new ArrayList<Integer>();
        
	public int getLength()
        {
            return myVector.size();
        }
	public Vector3 getStep(int index)
        {
            return myVector.get(index);
        }
	public int getX(int index)
        {
            return myVector.get(index).X;
        }
	public int getY(int index)
        {
            return myVector.get(index).Y;
        }
	public void appendStep(MapTile newStep)
        {
            totalCostOfTravel.add(newStep.getCost());
            myVector.add(newStep.getCoordinates());
        }
	public void prependStep(MapTile newStep)
        {
            totalCostOfTravel.add(newStep.getCost());
            myVector.add(0, newStep.getCoordinates());
        }
        
        public void removeLast()
        {
            totalCostOfTravel = (ArrayList)totalCostOfTravel.subList(0, totalCostOfTravel.size()-2);
            myVector = (ArrayList)myVector.subList(0, myVector.size()-2);
        }
        
	public boolean contains(int x, int y)
        {
           
            return myVector.contains(new Vector3(x,y,0));
        } //GT PreOrder/Post-Order level-order
        
        public void resetDebug()
        {
            
        }

    int getTotalCost() {
       int Cost = 0;
       for(Integer X : totalCostOfTravel)
       {
           Cost += X.intValue();
       }
       return Cost;
    }

    void appendToPath(Path FirstPath) {
      myVector.addAll(FirstPath.myVector);
      totalCostOfTravel.addAll(FirstPath.totalCostOfTravel);
    }

    public Vector3 getLast() {
       return myVector.get(myVector.size()-1);//TODO: is this right?
    }
}
