/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package anthropologyapplication.PathfindingAI;

import anthropologyapplication.AutoMapper.MapTile;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.function.Consumer;

/**
 *
 * @author Duke
 */
public class ReversePathIterator implements Iterator<MapTile> {

    private final ArrayList<PathObject> Path;
    private int currentIndex = 0;
    ReversePathIterator(ArrayList<PathObject> aPathToFollow) {
      this.Path = aPathToFollow;
      currentIndex = Path.size()-1; //Last Object in Path
    }
    
    @Override
    public boolean hasNext() {
        return (currentIndex - 1 > 0);
    }

    @Override
    public MapTile next() {
        return Path.get(--currentIndex).aTile;
    }

    @Override
    public void remove() {
    }

    @Override
    public void forEachRemaining(Consumer<? super MapTile> action) {
    }
    
}
