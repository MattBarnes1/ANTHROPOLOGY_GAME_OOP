
package anthropologyapplication.AutoMapper;

import anthropologyapplication.MainGameCode;
import anthropologyapplication.Map;
import javafx.application.Platform;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.WritableImage;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Duke
 */
public class AutoMapperGui extends Service { 
    
    final int DEFAULT_ROOMTODRAWSIZE_X = 5;
    final int DEFAULT_ROOMTODRAWSIZE_Y = 5;

    Vector3 VectorZeroTransformData;
    Vector3 WorldMaxXY;
    static transient GraphicsContext aGameCanvas;//TODO Don't save this value
    double canvasWidth;
    double canvasHeight;
    boolean JustActivated = true;
    double ScreenTileWidth = DEFAULT_ROOMTODRAWSIZE_X;
    double ScreenTileHeight = DEFAULT_ROOMTODRAWSIZE_Y;
    MapTile[][] RoomsToDraw = new MapTile[(int) ScreenTileWidth][(int) ScreenTileHeight];
    boolean[][] RoomsISeeInto = new boolean[(int) ScreenTileWidth][(int) ScreenTileHeight];
    MapTile[] RoomsDrawnTo;
    transient WritableImage myWorldImage;
    MapTile currentFocusedRoom;
    int MidPointX;
    int MidPointY;
    Vector3 RoomsToDrawZero;
    
   
    
    private void PlaceRoomsInWorld(MapTile[] myRooms) {//TODO:Remove debugging hsit
        for (int i = 0; i < myRooms.length; i++) {
            if (myRooms[i].getCoordinates() != null) {
                
                System.out.println(myRooms[i].getClass() + "\nCoordsBeforeTransform: " + myRooms[i].getCoordinates());
                Vector3 Coordinate = myRooms[i].getCoordinates().Transform(VectorZeroTransformData);
                System.out.println("CoordsAfterTransform: " + Coordinate);
                WorldRoomLocations[Coordinate.X][Coordinate.Y][Coordinate.Z] = myRooms[i];
            }
        }
    }
    
    public void CreateBackground() {
        double width = ScreenTileWidth * MapTile.getTileWidth();
        double height = ScreenTileHeight * MapTile.getTileHeight();
        if (RoomFocusHasShifted) {
            updateRoomsToDraw();
            RoomFocusHasShifted = false;
            myWorldImage = new WritableImage((int) width, (int) height);
            for (int x = 0; x < RoomsToDraw.length; x++) {
                for (int y = 0; y < RoomsToDraw[x].length; y++) {
                    if (RoomsToDraw[x][y] != null) {
                        RoomsToDraw[x][y].Draw(x, y, myWorldImage);
                    } else {
                       MapTile.DrawUndefinedTile(x, y, myWorldImage);
                    }
                }
            }
        }
    }
    
    public void Draw() {
        CreateBackground();
        //aGameCanvas = GC;
        aGameCanvas.drawImage(myWorldImage, 0, 0);  
    }
    
    public void setCanvas(GraphicsContext gc, double Width, double Height) {
        aGameCanvas = gc;
        System.out.println("AUTOMAP WIDTH" + " " + Width + "\tHeight" + Height);
        canvasResized(Width, Height);
    }

    public void canvasResized(double Width, double Height) {
        canvasWidth = Width;
        canvasHeight = Height;
        setSize(canvasWidth, canvasHeight);
    }

    
    private boolean checkWidth(double Width, double numOfTiles) {
        return ((MapTile.getTileWidth() * numOfTiles) <= Width);
    }

    private boolean checkHeight(double Height, double numOfTiles) {
        return ((MapTile.getTileHeight() * numOfTiles) <= Height);
    }

    private void resizeRoomsDisplayedWidth(double canvasWidthSize) {
        System.out.println("Map Image Width: " + MapTile.getTileWidth());
        double TilesTest = Math.ceil(canvasWidthSize / MapTile.getTileWidth());
        System.out.println("Resize Canvas Width: " + TilesTest);
        if(TilesTest % 2 == 0){ //IsItOdd?
            TilesTest++;
        }
        ScreenTileWidth = TilesTest;
    }

    private void resizeRoomsDisplayedHeight(double canvasHeightSize) {
        System.out.println("Map Image Height: " + MapTile.getTileHeight());
        double TilesTest = Math.ceil(canvasHeightSize / MapTile.getTileHeight());
        System.out.println("Resize Canvas Height: " + TilesTest);
        if(TilesTest % 2 == 0){ //IsItOdd?
            TilesTest++;
        }
        ScreenTileHeight = TilesTest;
    }

    private void setSize(double Width, double Height) {
        canvasWidth = Width;
        canvasHeight = Height;
        boolean Resized = false;
        if (checkWidth(Width, ScreenTileWidth)) {
            resizeRoomsDisplayedWidth(Width);
            Resized = true;
        }
        if (checkHeight(Height, ScreenTileHeight)) {
            resizeRoomsDisplayedHeight(Height);
            Resized = true;
        }
        if(Resized)
        {
            updateRoomsToDraw();
            System.out.println("WIDTH: " + ScreenTileWidth);
            System.out.println("Height: " + ScreenTileHeight);
            RoomsISeeInto = new boolean[(int) ScreenTileWidth][(int) ScreenTileHeight];
        }
    }

    MapTile[][][] WorldRoomLocations;
    Vector3 RoomFocus;

     private void updateRoomsToDraw() {
        ClearAllRoomsToDraw();
        if(currentFocusedRoom != null)
        {
            PlaceFocusedRoomInCenter();
            FillArrayWithRooms();
        }
    }
    private void FillArrayWithRooms() {
        Vector3 aZeroZeroRoom = RoomFocus.Transform(RoomsToDrawZero);
        Vector3 output = new Vector3(aZeroZeroRoom);
        for (int x = 0; x < RoomsToDraw.length; x++) {
            for (int y = 0; y < RoomsToDraw[x].length; y++) {
                if(x == 3 && y == 3)
                {
                    int g = 0;
                }
                Vector3 myVector = new Vector3(x, y, 0);
                output = aZeroZeroRoom.Transform(myVector);
                MapTile aRoom = getRoomAtCoordinate(output);
                System.out.println("X: " + x + "Y: " + y + " :\tmyVectorTransform: " + myVector + " \toutput" + output);
                RoomsToDraw[x][y] = aRoom;
            }
        }
        if(RoomsToDraw[MidPointX][MidPointY] != currentFocusedRoom)
        {
           throw new java.lang.UnsupportedOperationException("Midpoint Room Overwritten");
        }
    }
    private void PlaceFocusedRoomInCenter() {//TODO: Look for erros
        MidPointX = (int) Math.ceil((RoomsToDraw.length-1) * .5D);
        MidPointY = (int) Math.ceil((RoomsToDraw[0].length - 1) * .5D);
        RoomsToDraw[MidPointX][MidPointY] = currentFocusedRoom;
        RoomsToDrawZero = new Vector3(-MidPointX, -MidPointY, 0);
    }
     private void ClearAllRoomsToDraw() {
        for (int x = 0; x < RoomsToDraw.length; x++) {
            for (int y = 0; y < RoomsToDraw[x].length; y++) {
                RoomsToDraw[x][y] = null;
            }
        }
    }
    
    public MapTile getRoomAtCoordinate(Vector3 myCoordinates) {
        MapTile retVal = null;
        Vector3 Result = myCoordinates.Transform(VectorZeroTransformData);
        if (CheckIfCoordinateExists(Result)) {
            retVal = WorldRoomLocations[Result.X][Result.Y][Result.Z];
        }
        return retVal;
    }
    
       private boolean CheckIfCoordinateExists(Vector3 aVectorToCheck) {
        return (aVectorToCheck.isConfinedToRegion(WorldMaxXY, Vector3.Zero));
    }
    boolean RoomFocusHasShifted = false;

    public void setRoomFocus(Vector3 myFocus) {
        RoomFocusHasShifted = true;////Tells us to redraw
        RoomFocus = myFocus;
        currentFocusedRoom = getRoomAtCoordinate(RoomFocus);
    }

    private void CalculateWorldZeroTransforms(Vector3 MinValues) {
        VectorZeroTransformData = Vector3.calculateZeroTransform(MinValues);
        System.out.println("Repported VectorZeroTransformData; " + VectorZeroTransformData.toString());
    }

    private void CreateWorldRoomLocationsArray(Vector3 MaxValues) {
        WorldMaxXY = MaxValues.Transform(VectorZeroTransformData);
        WorldRoomLocations = new MapTile[WorldMaxXY.X + 1][WorldMaxXY.Y + 1][WorldMaxXY.Z + 1];
        System.out.println("Repported World Max; " + WorldMaxXY.toString());
    }


    public void setScreenXYSize(int ScreenWidth, int ScreenHeight)
    {
        ScreenTileWidth = Math.floor(ScreenWidth/MapTile.getTileWidth());
        ScreenTileHeight = Math.floor(ScreenHeight/MapTile.getTileHeight());
        
    }
    
    
    MainGameCode myCode;
    Map mapData = null;
    public void setMap(Map mapData, MainGameCode myCode) {
        this.myCode = myCode;
        this.mapData = mapData;
        RoomsToDraw = new MapTile[(int)ScreenTileWidth][(int)ScreenTileHeight];
        
        this.start();
    }
    
    public int getTileWidth() {
       return (int)MapTile.getTileWidth();
    }

    public int getTileHeight() {
       return (int)MapTile.getTileHeight();
    }

    public Vector3 getCurrentRoomCoordinates() {
        return this.RoomFocus;
    }

    public MapTile getTileAtMouseCoordinates(double sceneX, double sceneY) {

        int XCoord = (int)((sceneX - sceneX % MapTile.getTileWidth())/MapTile.getTileWidth());
        int YCoord = (int)((sceneY - sceneY % MapTile.getTileHeight())/MapTile.getTileHeight());
        
        MapTile debug = this.getRoomAtCoordinate(new Vector3(XCoord, YCoord, 0));
        System.out.println(debug.getCoordinates());
        return debug;
        
    }

    @Override
    protected Task createTask() {
        Task NewTask = new Task<Integer>(){
            @Override
            protected Integer call() throws Exception {
                
                
                CalculateWorldZeroTransforms(mapData.getMinMapCoordinates());
                CreateWorldRoomLocationsArray(mapData.getMaxMapCoordinates());
                MapTile[][] tempArray = mapData.getMapTiles();

                for(int i = 0; i < tempArray.length; i++)
                {
                    super.updateMessage("DAMMMMNNNN IIITTTT");
                    super.updateProgress((i+1)*tempArray.length, tempArray.length*tempArray.length);
                    System.out.println((i+1)*tempArray.length);
                    PlaceRoomsInWorld(tempArray[i]);
                }
                setRoomFocus(new Vector3(0,0,0));
               
                return null;
            }
            
        };
        return NewTask;
    }

    public void setCanvas(GraphicsContext graphicsContext2D) {
       aGameCanvas = graphicsContext2D;
    }

}
