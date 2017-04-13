/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package anthropologyapplication;

import anthropologyapplication.AutoMapper.Vector3;
import java.util.ArrayList;

/**
 *
 * @author noone
 */
public class Path {
        ArrayList<Vector3> myVector = new ArrayList<Vector3>();
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
	public void appendStep(Vector3 newStep)
        {
            myVector.add(newStep);
        }
	public void prependStep(Vector3 newStep)
        {
            myVector.add(0, newStep);
        }
        
        public void removeLast()
        {
            myVector.remove(myVector.size());
        }
        
	public boolean contains(int x, int y)
        {
            for(Vector3 aVector : myVector)
            {
                if(aVector.X == x && aVector.Y == y)
                {
                    return true;
                }
            }
            return true;
        } //GT PreOrder/Post-Order level-order
        
        public void resetDebug()
        {
            
        }
}
