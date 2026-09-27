package entity;

import java.awt.Rectangle;
import java.util.Random;
import main.GamePanel;
import object.OBJ_Armor;
import object.OBJ_Gun;
import object.OBJ_Heart;
import object.OBJ_Key;
import object.OBJ_RunningShoes;


public class ZOM_type1 extends Entity{
  GamePanel gp;
  
  public ZOM_type1(GamePanel gp){
    super(gp);

    this.gp = gp;

    //Zombie stats
    name = "Basic Zombie";
    type = 2;

    damage = 1;
    speed = 1;
    defense = 0;
    maxLife = 2;
    life = maxLife;

    solidArea = new Rectangle(0, 10, 48, 38);
    solidAreaDefaultX = solidArea.x;
    solidAreaDefaultY = solidArea.y;

    getImage();
  }
  public void getImage(){
    right1 = setup("/enemy/zombieRv1.png", gp.tileSize, gp.tileSize);
    left1 = setup("/enemy/zombieLv1.png", gp.tileSize, gp.tileSize);
    up1 = setup("/enemy/zombieUpv1.png", gp.tileSize, gp.tileSize);
    down1 = setup("/enemy/zombieDownv1.png", gp.tileSize, gp.tileSize);
  }
  public void setAction(){
    //Locate player
    int playerX = gp.player.worldX;
    int playerY = gp.player.worldY;

    int distanceX = playerX - worldX;
    int distanceY = playerY - worldY;

    if(distanceX > 0){
      direction = "right";
      worldX += speed;
    } else if(distanceX < 0){
      direction = "left";
      worldX -= speed;
    } 
    if(distanceY > 0){
      direction = "down";
      worldY += speed;
    } else if(distanceY < 0){
      direction = "up";
      worldY -= speed;
    }
    
  }
  
  public void checkDrop(){ //Drop items
    //RNGesus
    int i = new Random().nextInt(100) + 1;
    if(i < 30){
      dropItem(new OBJ_Heart(gp));
    }
    if(i > 70){
      dropItem(new OBJ_Gun(gp));
    }
    if(i == 70){
      dropItem(new OBJ_Key(gp));
    }
    if(i < 60 && i >=47){
      dropItem(new OBJ_Armor(gp));
    }
    if(i <47 && i >= 46){
      dropItem(new OBJ_RunningShoes(gp));
    }
    
  }
  
}