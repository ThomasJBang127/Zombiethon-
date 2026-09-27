package main;

import entity.Enemy;
import entity.ZOM_type1;

import java.util.Random;
import object.OBJ_Armor;
import object.OBJ_Door;
import object.OBJ_RunningShoes;


public class AssetSetter {
  GamePanel gp;
  public AssetSetter(GamePanel gp) {
    this.gp = gp;
  }
  
  public void setObject() {
    int i = 0;

    gp.obj[i] = new OBJ_Door(gp);
    gp.obj[i].worldX = 40 * gp.tileSize;
    gp.obj[i].worldY = 6 * gp.tileSize;
    i++;

    gp.obj[i] = new OBJ_Armor(this.gp);
    gp.obj[i].worldX = 38 * gp.tileSize;
    gp.obj[i].worldY = 4 * gp.tileSize;
    i++;
    
    gp.obj[i] = new OBJ_RunningShoes(gp);
    gp.obj[i].worldX = 42 * gp.tileSize;
    gp.obj[i].worldY = 4 * gp.tileSize;
    i++;
    
  }

  public void setEnemy(){
    gp.testEnemy[0] = new Enemy(gp);
    gp.testEnemy[0].worldX = getRandomNum() * gp.tileSize;
    gp.testEnemy[0].worldY = getRandomNum() * gp.tileSize;
  }
  public void setZombie(){
    int i = 0;
    gp.zombieList1[i] = new ZOM_type1(gp);
    gp.zombieList1[i].worldX = getRandomNum() * gp.tileSize;
    gp.zombieList1[i].worldY = getRandomNum() * gp.tileSize;
    i++;
    gp.zombieList1[i] = new ZOM_type1(gp);
    gp.zombieList1[i].worldX = getRandomNum() * gp.tileSize;
    gp.zombieList1[i].worldY = getRandomNum() * gp.tileSize;
    i++;
    gp.zombieList1[i] = new ZOM_type1(gp);
    gp.zombieList1[i].worldX = getRandomNum() * gp.tileSize;
    gp.zombieList1[i].worldY = getRandomNum() * gp.tileSize;
    i++;
    gp.zombieList1[i] = new ZOM_type1(gp);
    gp.zombieList1[i].worldX = getRandomNum() * gp.tileSize;
    gp.zombieList1[i].worldY = getRandomNum() * gp.tileSize;
    i++;
    gp.zombieList1[i] = new ZOM_type1(gp);
    gp.zombieList1[i].worldX = getRandomNum() * gp.tileSize;
    gp.zombieList1[i].worldY = getRandomNum() * gp.tileSize;
    
    
  }
  public int getRandomNum(){
    //Random position 
    Random random = new Random();
    int randomNum = random.nextInt(48)+2; //Choose a number between 2 and 48
    // int randomY;
    // if(randomX < 5 || randomX > 45){ //Is the number between the edges of map
    //   randomY = random.nextInt(48)+2; //If so, Y does not matter
    // } else { //Otherwise, we need to make sure the Y is at VERY TOP or VERY BOTTOM
    //   randomY = random.nextInt(48)+2;  //Get random
    //   if(randomY > 5 || randomY < 26){ //Split in half
    //     randomY = random.nextInt(5)+2; //Random 2-7
    //   } else if(randomY < 45 || randomY >= 25){ //Other half
    //     randomY = random.nextInt(5)+43;
    //   }
    // }
    return randomNum;
  }
  
}