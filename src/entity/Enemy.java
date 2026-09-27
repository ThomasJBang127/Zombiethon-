package entity;
import main.GamePanel;

import java.awt.Rectangle;
import java.util.Random;
import object.OBJ_Key;

//Object for Enemys 
public class Enemy extends Entity{
  
  public Enemy(GamePanel gp){
    super(gp);

    type = 1;
    //Collision area is same as players
    solidArea = new Rectangle(0, 10, 48, 38);
    solidAreaDefaultX = solidArea.x;
    solidAreaDefaultY = solidArea.y;

    defense = 0;
    maxLife = 2;
    life = maxLife;
    
    setDefaultValues();
    getEnemyImage();
  }

  public void setDefaultValues(){
    speed = 2;
    direction = "right"; //Direction changes with update method
  }

  public void getEnemyImage(){
    right1 = setup("/enemy/zombieRv1.png", gp.tileSize, gp.tileSize);
    left1 = setup("/enemy/zombieLv1.png", gp.tileSize, gp.tileSize);
    up1 = setup("/enemy/zombieUpv1.png", gp.tileSize, gp.tileSize);
    down1 = setup("/enemy/zombieDownv1.png", gp.tileSize, gp.tileSize);
  }
  
  //Enemy "AI": Overrides the default one in the superclass entity
  public void setAction(){
    actionLockCounter++;

    if(actionLockCounter == 150){
      Random random = new Random();
      int i = random.nextInt(100) + 1;
      if(i <= 25){
        direction = "left";
      }
      if(i > 25 && i <= 50){
        direction = "right";
      }
      if(i > 50 && i <= 75){
        direction = "up";
      }
      if(i > 75 && i <= 100){
        direction = "down";
      }
      
      actionLockCounter = 0;
    }
  }

  public void checkDrop(){ //Drop items
    //RNGesus
    int i = new Random().nextInt(100) + 1;
    if(i > 5){
      dropItem(new OBJ_Key(gp));
    }
  }

}