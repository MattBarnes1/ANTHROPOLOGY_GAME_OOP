/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package anthropologyapplication;

import AutoMapper.Vector3;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Random;
import javafx.scene.image.Image;
import javafx.scene.image.WritableImage;

/**
 *
 * @author Duke
 */
public abstract class MapTile {
    private Vector3 MapTileLocation;
    
    private static double MaxImageSizeX;
    private static boolean MaxImageSize = false;
    private static double MaxImageSizeY;
    private static transient Image DefaultImage;
    private static transient Image DefaultImageUnexplored;
    private static transient Image derivedLoadedImage;
    private String ImageName;
    private Image tileBuilding;
    private char Printout;
    
    private Vector3 MapCoordinates;
    public MapTile(String filenameInAutomapperData)
    {
        ImageName = filenameInAutomapperData;
        Image retVal = new Image("AutomapperData/" + filenameInAutomapperData);
        while(retVal.isBackgroundLoading());
        setupUnexploredImage();
    }
    
    public void setPrintoutString(char aChar)
    {
        Printout = aChar;
    }
    
    public void setCoordinates(Vector3 myCoordinates)
    {
        MapCoordinates = myCoordinates;
    }
    
    public Vector3 getCoordinates()
    {
        return MapCoordinates;
    }
    
    static void DrawDefaultTile(int x, int y, WritableImage myWorldImage) {
                myWorldImage.getPixelWriter().setPixels((int)(x*getTileWidth()), (int)(y*getTileHeight()), (int)getTileWidth(), (int)getTileHeight(), DefaultImage.getPixelReader(), 0, 0);
    }
    private String myImageName;
    
    public static void DrawUndefinedTile(int x, int y, WritableImage aGameCanvas) {
          
        aGameCanvas.getPixelWriter().setPixels((int)(x*getTileWidth()), (int)(y*getTileHeight()), (int)getTileWidth(), (int)getTileHeight(), DefaultImageUnexplored.getPixelReader(), 0, 0);
    }
    
    private  transient Image myImage;
    private boolean Explored = false;
    private boolean CanSeeInto = false;
    
    private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
         s.defaultReadObject();
         setupUnexploredImage();
    }

    private void writeObject(ObjectOutputStream s) throws IOException, ClassNotFoundException {
        s.defaultWriteObject();
        
    }
@Override
    public String toString()
    {
        return Printout + "";   
    }
    
    
    
    public abstract void generateSubType(MapTile[][] surroundingTilesAndThis, Random myRandom, double tilesAvailable);
    
    public static double getTileWidth() {
        return MaxImageSizeX;
    }

    public static double getTileHeight() {
        return MaxImageSizeY;
    }
    
    
    private void setupUnexploredImage()
    {
        if(DefaultImageUnexplored == null)
        {
            DefaultImageUnexplored = new Image("AutomapperData/UnexploredTile.jpeg");
            MaxImageSizeX = DefaultImageUnexplored.getWidth();
            MaxImageSizeY = DefaultImageUnexplored.getHeight();
        }
    }

    public void setMapImage(Image myImage)
    {
        this.myImage = myImage;
    }
    
    public void tileWasExplored(boolean aBool)
    {
        Explored = aBool;
    }  
    
    public void Draw(int x, int y, WritableImage aGameCanvas) 
    {//TODO: int or double here?
        if(!Explored)
        {
            aGameCanvas.getPixelWriter().setPixels((int)(x*getTileWidth()), (int)(y*getTileHeight()), (int)getTileWidth(), (int)getTileHeight(), DefaultImageUnexplored.getPixelReader(), 0, 0);
        } 
        else {
            aGameCanvas.getPixelWriter().setPixels((int)(x*getTileWidth()), (int)(y*getTileHeight()), (int)getTileWidth(), (int)getTileHeight(), myImage.getPixelReader(), 0, 0);            
            if(tileBuilding != null)
            {
                aGameCanvas.getPixelWriter().setPixels((int)(x*getTileWidth()), (int)(y*getTileHeight()), (int)getTileWidth(), (int)getTileHeight(), tileBuilding.getPixelReader(), 0, 0);
            }
        }
    }

    boolean isLand() {
        return b_isLand;
    }

    boolean b_isLand = false;
    
    public void setLand(boolean b) {
        b_isLand = b;
    }

    private String TileSubType;
    public void setSubtype(String subType) {
        TileSubType = subType;
    }
    private MapTile North = null;
    private MapTile Northeast = null;
    private MapTile Northwest = null;
    private MapTile East = null;
    private MapTile Southeast = null;
    private MapTile Southwest = null;
    private MapTile South = null;
    private MapTile West = null;
    
    
    MapTile getNorthwest() {
        return Northwest;
    }

    void setNorthwest(MapTile mapTile) {
        Northwest = mapTile;
        mapTile.setSoutheast(this);
    }

    MapTile getNorth() {
        return North;
    }

    void setNorth(MapTile mapTile) {
        North = mapTile;
        mapTile.setSouth(this);
    }

    MapTile getNortheast() {
        return Northeast;
    }

    void setNortheast(MapTile mapTile) {
        Northeast = mapTile;
        mapTile.setSouthwest(this);
    }

    MapTile getEast() {
        return East;
    }

    void setEast(MapTile mapTile) {
        East = mapTile;
        mapTile.setWest(this);
    }

    MapTile getWest() {
       return West;
    }

    MapTile getSouthwest() {
        return Southwest;
    }

    MapTile getSouth() {
        return South;
    }

    MapTile getSoutheast() {
        return Southeast;
    }

    void setSoutheast(MapTile mapTile) {
        Southeast = mapTile;
        mapTile.setNorthwest(this);
    }

    void setSouth(MapTile mapTile) {
        South = mapTile;
        mapTile.setNorth(this);
    }

    void setSouthwest(MapTile mapTile) {
        Southwest = mapTile;
        mapTile.setNortheast(this);
    }

    void setWest(MapTile mapTile) {
        West = mapTile;
        mapTile.setEast(this);
    }

    void clearForeground() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    void setForegroundImage(String ForegroundImageFileName) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

  

    

    

}
