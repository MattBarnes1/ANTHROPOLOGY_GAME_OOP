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
class PathIterator implements Iterator<MapTile>
{

    Iterator<PathObject> aPathIterator;

    PathIterator(ArrayList<PathObject> aPathToFollow) {
        aPathIterator = aPathToFollow.iterator();
    }

    @Override
    public boolean hasNext() {
        return aPathIterator.hasNext();
    }

    @Override
    public MapTile next() {
        return aPathIterator.next().aTile;
    }

    @Override
    public void remove() {
    }

    @Override
    public void forEachRemaining(Consumer<? super MapTile> action) {

    }
}
