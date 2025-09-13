package main;

import javax.imageio.ImageIO;
import java.awt.Graphics2D;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.IOException;


public class TileManager {
  GamePanel gp;
  public Tile[] tile;
  public int mapTileNum[][];
  
  public TileManager(GamePanel gp) {
    this.gp=gp;
    tile = new Tile[20];
    mapTileNum = new int[gp.maxWorldCol][gp.maxWorldRow];
    
    getTileImage();
    loadMap("/maps/worldMap01.txt");
  }
  public void getTileImage() {
    setup(0,"Grassv1",false);
    setup(1,"Tilev1",false);
    setup(2,"Mudv1",false);
    setup(3,"TileMossv1",false);
    setup(4,"tree01",true); 
    setup(5,"Grassv2",false); 
    setup(6,"MudGrassv1",false);
    setup(13,"gore01", true); 
    setup(8,"woodFloor01",false);
    setup(9,"brick01",true);
    
    setup(11,"edgeUp",true); //EDGE UP
    setup(12,"edgeDown",true); //EDGE DOWn
    setup(7,"edgeRight",true); //EDGE RIGHT
    setup(14,"edgeLeft",true); //EDGE LEFT

    setup(15,"edgeUpLeft",true); //EDGE TOP LEFT
    setup(16,"edgeUpRight",true); //EDGE TOP RIGHT
    setup(17,"edgeDownLeft",true); //EDGE BOTTOM LEFT
    setup(18,"edgeDownRight",true); //EDGE BOTTOM RIGHT
  }
  //Method for scaling images and grabbing them
  public void setup(int index, String imagePath, boolean collision){
    UtilityTool uTool = new UtilityTool();
    try {
      tile[index] = new Tile();
      //ImageIO.read(getClass().getResourceAsStream("/player/playerBackwardL.png"));
      String path = "/Tiles/"+imagePath+".png";
      //System.out.println(path);
      //System.out.println(getClass().getResource(path) == null);
      tile[index].image = ImageIO.read(getClass().getResourceAsStream(path));
      tile[index].image = uTool.scaleImage(tile[index].image, gp.tileSize, gp.tileSize);
      tile[index].collision = collision;
    }catch(IOException e) {
    }
  }
  
  public void loadMap(String filePath) {
    try {
      InputStream is = getClass().getResourceAsStream(filePath);
      BufferedReader br = new BufferedReader(new InputStreamReader(is));
      int col = 0;
      int row = 0;
      
      while(col < gp.maxWorldCol && row < gp.maxWorldRow) {
        String line = br.readLine();

        while(col < gp.maxWorldCol) {
          String numbers [] = line.split(" ");

          int num = Integer.parseInt(numbers[col]);

          mapTileNum[col][row] = num;
          col++;
          
        }
        if(col == gp.maxWorldCol) {
          col = 0;
          row++;
        }
      }
      System.out.println("Map Loaded");
      br.close();
      
    } catch(Exception e) {
      
    } 
    
  }
  
  public void draw(Graphics2D g2) {
    int worldCol = 0;
    int worldRow = 0;


    while(worldCol < gp.maxWorldCol && worldRow < gp.maxWorldRow){

      int tileNum = mapTileNum[worldCol][worldRow];
      
      int worldX = worldCol * gp.tileSize;
      int worldY = worldRow * gp.tileSize;
      //Offset
      int screenX = worldX - gp.player.worldX + gp.player.screenX;
      int screenY = worldY - gp.player.worldY + gp.player.screenY;

      //ONLY load what can be seen on the camera. The plus 1 or minus 1 is to make sure the tile is loaded on the camera (expands it out one tile).
      if(worldX + gp.tileSize > gp.player.worldX - gp.player.screenX &&
         worldX - gp.tileSize < gp.player.worldX + gp.player.screenX &&
         worldY + gp.tileSize > gp.player.worldY - gp.player.screenY &&
         worldY - gp.tileSize < gp.player.worldY + gp.player.screenY) {
        
        g2.drawImage(tile[tileNum].image, screenX, (screenY), null);
      }
      
      worldCol++;
      
      if(worldCol == gp.maxWorldCol) {
        worldCol = 0;
        worldRow++;
      }
    }

  }
}
