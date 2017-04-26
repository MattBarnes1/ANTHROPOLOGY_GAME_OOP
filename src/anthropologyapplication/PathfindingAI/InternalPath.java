/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package anthropologyapplication.PathfindingAI;

import anthropologyapplication.AutoMapper.MapTile;

/**
 *
 * @author Duke
 */
class PathObject 
{
            public MapTile aTile;
            public int CostFromStartToHere = 0;//g
            public int CostFromHereToGoal = 0;//f
            public PathObject CameFrom = null;
            PathObject(MapTile aTile, int CostFromStartToHere, int CostFromHereToGoal) {
                this.aTile = aTile;
                this.CostFromStartToHere = CostFromStartToHere;
                this.CostFromHereToGoal = CostFromHereToGoal;
            }
}
