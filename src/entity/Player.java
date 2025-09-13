package entity;

import main.GamePanel;
import main.KeyHandler;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.awt.Rectangle;
import java.awt.AlphaComposite;
import object.OBJ_Bullet;


//Object for player 
public class Player extends Entity{

  KeyHandler keyH;
 
  public final int screenX;
  public final int screenY;
  public int hasKey = 0;
  public int gunLvl = 0;
  public int score = 0;
  
  public Player(GamePanel gp, KeyHandler keyH){
    super(gp);
    this.keyH = keyH;

    screenX = gp.screenWidth/2 - (gp.tileSize/2);
    screenY = gp.screenHeight/2 - (gp.tileSize/2);
    
    //Collision area
    solidArea = new Rectangle(12, 10, 18, 38);
    solidAreaDefaultX = solidArea.x;
    solidAreaDefaultY = solidArea.y;
    
    setDefaultValues();
    getPlayerImage();
    getPlayerAttackImage();
  }

  public void setDefaultValues(){
    worldX = gp.tileSize * 23;
    worldY = gp.tileSize * 21;
    speed = 4;
    directionUpDown = "none"; //Direction changes with update method
    direction = "down";

    //Stats
    maxLife = 10;
    life = 10;
    hasKey = 0;
    gunLvl = 0;
    score = 0;

    projectile = new OBJ_Bullet(gp);
    
  }
  public void setDefaultPositions() {
    worldX = gp.tileSize * 23;
    worldY = gp.tileSize * 21;
    direction = "down";
    
  }
  public void restoreLife(){
    maxLife = 10;
    life = 10;
    invincible = false;
  }
  

  public void getPlayerImage(){
    up1 = setup("/player/playerBackwardL.png", gp.tileSize, gp.tileSize);
    up2 = setup("/player/playerBackwardR.png", gp.tileSize, gp.tileSize);
    down1 = setup("/player/playerForwardL.png", gp.tileSize, gp.tileSize);
    down2 = setup("/player/playerForward.png", gp.tileSize, gp.tileSize);
    left1 = setup("/player/playerFaceL.png", gp.tileSize, gp.tileSize);
    right1 = setup("/player/playerFaceR.png", gp.tileSize, gp.tileSize);
    left2 = setup("/player/playerDiagL.png", gp.tileSize, gp.tileSize);
    right2 = setup("/player/playerDiagR.png", gp.tileSize, gp.tileSize);
  }
  public void getPlayerAttackImage(){
    
    attackUp1 = setup("/player/playerAttackUp.png", gp.tileSize, gp.tileSize*2);
    attackUp2 = setup("/player/playerAttackUpF.png", gp.tileSize, gp.tileSize*2);
    attackDown1 = setup("/player/playerAttackDown.png", gp.tileSize, gp.tileSize*2);
    attackDown2 = setup("/player/playerAttackDownF.png", gp.tileSize, gp.tileSize*2);
    attackLeft1 = setup("/player/playerAttackL.png", gp.tileSize*2, gp.tileSize);
    attackLeft2 = setup("/player/playerAttackLF.png", gp.tileSize*2, gp.tileSize);
    attackRight1 = setup("/player/playerAttackR.png", gp.tileSize*2, gp.tileSize);
    attackRight2 = setup("/player/playerAttackRF.png", gp.tileSize*2, gp.tileSize);
    
  }
  
  public void update(){ 

    if(attacking){
      playerAttack();
    }
    //Player Movement (keypressed)
    else if(keyH.upPressed || keyH.downPressed || keyH.leftPressed || keyH.rightPressed || keyH.jPressed) {
      if(keyH.upPressed == true){
        direction = "up";
        directionUpDown = "up";
      }
      else if(keyH.downPressed == true){
        direction = "down";
        directionUpDown = "down";
      }
      if(keyH.downPressed == false && keyH.upPressed == false){
        directionUpDown = "none";
      }
      if(keyH.leftPressed == true){
        direction = "left";
      }
      else if(keyH.rightPressed == true){
        direction = "right";
      }
      
      //Check tile collision: false means player can move  
      collisionOn = false;
      gp.cChecker.checkTile(this);    
      //Object collision: 
      int objIndex = gp.cChecker.checkObject(this, true);
      pickUpObject(objIndex);
      //Enemy collision
      int enemyIndex = gp.cChecker.checkEntityB(this, gp.testEnemy);
      interactEnemy(enemyIndex);
      //Zombie collision
      int zombieIndex = gp.cChecker.checkEntityA(this, gp.zombieList1);
      contactZombie(zombieIndex);
      
      //Check if attack button is pressed
      if(keyH.jPressed == true){
        //for animation holding out gun
        attacking = true;
        //Create with those parameters
        projectile.set(worldX, worldY, direction, true, this);

        gp.bulletList.add(projectile);
        //Recoil
        if(collisionOn == false){
          switch(direction){
            case "left": 
              worldX += speed;
              break;
            case "right":
              worldX -= speed;
              break;
            case "up":
              worldY += speed;
              break;
            case "down":
              worldY -= speed;
              break;
          }
        }
        
      }
      
      //If collision is true, player can't move
      if(collisionOn == false){
        switch(direction){
          case "left":
            worldX -= speed;
            break;
          case "right":
            worldX += speed;
            break;
          case "up":
            worldY -= speed;
            break;
          case "down":
            worldY += speed;
            break;
        }
      }
      //If collision is true, player can't move
      if(collisionOn == false && !direction.equals("up") && !direction.equals("down")){
        switch(directionUpDown){
          case "up":
            worldY -= speed;
            break;
          case "down":
            worldY += speed;
            break;
          case "none":
            break;
        }
      }

      //Every 12 "frames", it changes the spriteNum
      spriteCounter++;
      if(spriteCounter > 15){
        if(spriteNum == 1){
          spriteNum = 2;
        }
        else if(spriteNum == 2){
          spriteNum = 1;
        }
        spriteCounter = 0;
      }
      
    } else {
      spriteNum = 1;
    }
    
    
    //Outside of keypress loop, iFrames
    if(invincible){
      invincibleCounter++;
      if(invincibleCounter > 120){
        invincible = false;
        invincibleCounter = 0;
      }
    }
    //Make sure HP does not go over max HP
    if(life > maxLife) {
      life = maxLife;
    }
    if(life <= 0) {
      gp.gameState = gp.gameOverState;
      gp.saveScore();
    }
    
  }
  public void pickUpObject(int i) {
    if(i != 999) {
      String objectName = gp.obj[i].name;

      switch (objectName) {
        case"Key":
          hasKey++;
          gp.obj[i] = null;
          gp.ui.showMessage("You found a key!");
          //Plays sound
//          gp.playSE(1);
//          gp.stopMusic();
          break;
        case "Door":
          if(hasKey > 0) {
            gp.obj[i] = null;
            hasKey--;
            gp.ui.showMessage("You unlocked a door!");
          }
          else {
            gp.ui.showMessage("You need a key!");
          }
          break;
        case "Gun":
          gunLvl++;          
          gp.obj[i] = null;
          gp.ui.showMessage("Upgraded Gun!");
          break;
        case "Heart": 
          gp.obj[i].use(this); 
          gp.obj[i] = null;
          gp.ui.showMessage("You gained a life!");
          break;
        case "Armor": 
          gp.obj[i].use(this); 
          gp.obj[i] = null;
          gp.ui.showMessage("You gained armor!");
          break;
        case "RunningShoes": 
          gp.obj[i].use(this);
          gp.obj[i] = null;
          gp.ui.showMessage("You gained running shoes!");
          break;
      }
      
    }
  }
  //ATtack
  public void playerAttack(){
    spriteCounter++;
    if(spriteCounter <= 15){
      spriteNum = 1;
    }
    if(spriteCounter > 15 && spriteCounter <= 30){
      spriteNum = 2;
    }
    if(spriteCounter > 25){
      spriteCounter = 0; 
      attacking = false;
    }
  }
  public void interactEnemy(int i){
    if(i != 999){
      System.out.println("hitting into an enemy");
    } 
  }
  //Method when you run into a zombie
  public void contactZombie(int i){
    if(i != 999){
      if(invincible == false){
        life-=1;
        invincible = true;
      }
    }
  }
  public void damageZombie(int i, int damage){
    if(i != 999){
      if(gp.zombieList1[i].invincible == false){
        
        int damageTaken = damage + gunLvl - gp.zombieList1[i].defense;
        if(damageTaken < 0){
          damageTaken = 0;
        }
        gp.zombieList1[i].life -= damageTaken;
        gp.zombieList1[i].invincible = true; //zombie gets iframe
        
        System.out.println("Hit a level "+gp.zombieList1[i].level+" zombie "+i+" for " + damageTaken + " damage! Zombie has "+gp.zombieList1[i].defense+" defense! Zombie has " + gp.zombieList1[i].life + " life left! Zombie is " + gp.zombieList1[i].speed+ " speed! Zombie has " + gp.zombieList1[i].damage + " damage!");
        
        //If its dead
        if(gp.zombieList1[i].life <= 0){
          //Drops
          gp.zombieList1[i].checkDrop();
          //respawn the zombie somewhere else on the map (set to null for permadeath)          
          gp.zombieList1[i].worldX = (int)(Math.random() * (48 - 2 + 1) + 2) * gp.tileSize;
          gp.zombieList1[i].worldY = (int)(Math.random() * (48 - 2 + 1) + 2) * gp.tileSize;
          //Mutate the zombie
          gp.zombieList1[i].level++;
          rollStats(gp.zombieList1[i]);
          //Restore HP
          gp.zombieList1[i].life = gp.zombieList1[i].maxLife;
          
          score++;          

        }
      }
    }
  }
  public void damageEnemy(int i, int damage){
    if(i != 999){
      if(gp.testEnemy[i].invincible == false){
        int damageTaken = damage - gp.testEnemy[i].defense;
        if(damageTaken < 0){
          damageTaken = 0;
        }
        gp.testEnemy[i].life -= damageTaken;
        gp.testEnemy[i].invincible = true; //zombie gets iframe

        if(gp.testEnemy[i].life <= 0){
          gp.testEnemy[i].checkDrop();
          gp.testEnemy[i] = null;
        }
      }
  }
  }
  public void draw(Graphics2D g2){

    BufferedImage image = null;
    int tempScreenX = screenX;
    int tempScreenY = screenY;
    
    //Changing sprites to make it look like its moving
    switch(direction){
      case "up":
        if(attacking==false){
          if(spriteNum == 1){image = up1;}
          if(spriteNum == 2){image = up2;}
        }
        if(attacking==true){
          tempScreenY = screenY - gp.tileSize;
          if(spriteNum == 1){image = attackUp1;}
          if(spriteNum == 2){image = attackUp2;}
        }
        break;
      case "down":
        if(attacking==false){
          if(spriteNum == 1){image = down1;}
          if(spriteNum == 2){image = down2;}
        }if(attacking==true){
          if(spriteNum == 1){image = attackDown1;}
          if(spriteNum == 2){image = attackDown2;}
        }
        break;
      case "left":
        if(attacking==false){
          if(spriteNum == 1){image = left1;}
          if(spriteNum == 2){image = left2;}
        }if(attacking==true){
          tempScreenX = screenX - gp.tileSize;
          if(spriteNum == 1){image = attackLeft1;}
          if(spriteNum == 2){image = attackLeft2;}
        }
        break;
      case "right":
        if(attacking==false){
          if(spriteNum == 1){image = right1;}
          if(spriteNum == 2){image = right2;}
        }if(attacking==true){
          if(spriteNum == 1){image = attackRight1;}
          if(spriteNum == 2){image = attackRight2;}
        }
        break;
    }
    if(invincible == true){ //Change opacity
      g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.3f));
    }
    
    //Draw image
    g2.drawImage(image, tempScreenX, tempScreenY, null);
    //RESET
    g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
  }
  
}