package entity;
import main.GamePanel;
import main.UtilityTool;


// Parent class for all entities
import java.awt.image.BufferedImage;
import java.awt.Rectangle;
import java.awt.Graphics2D;
import javax.imageio.ImageIO;
import java.util.Random;
import java.awt.AlphaComposite;

public class Entity {

  GamePanel gp;
  
  //For sprites
  public BufferedImage up1, up2, down1, down2, left1, left2, right1, right2;
  public BufferedImage attackUp1, attackUp2, attackDown1, attackDown2, attackLeft1, attackLeft2, attackRight1, attackRight2;
  public BufferedImage image, image2, image3;
  //For animation (sprites)
  public int spriteCounter = 0;
  public int spriteNum = 1;
  //For collision (hitbox)
  public Rectangle solidArea = new Rectangle(0,0,48,48); // x y width height 
  public int solidAreaDefaultX, solidAreaDefaultY;  
  public boolean collision = false; //For tiles/objects
  //World
  public int worldX, worldY;
  public String direction = "left", directionUpDown = "none";
  //Enitity action (booleans and counters)
  public int actionLockCounter = 0;
  public boolean invincible = false;
  public int invincibleCounter = 0;
  public boolean collisionOn = false; //For entities
  public boolean attacking = false;
  public boolean alive = true;  
  public Projectile projectile;
  //Character Stats
  public String name;
  public int type; //0 Player, 1 non-hostile, 2 hostile, 3 pickup objects
  public int speed;
  public int maxLife;
  public int life;
  public int damage;
  public int defense;
  public int level = 1;
  
  public Entity(GamePanel gp){
    this.gp = gp;
  }
  
  //What the entity does
  public void setAction(){
    //Subclass takes priority
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
  //Updates the entity (animation)
  public void update(){
    
    setAction(); //Gets direction

    collisionOn = false;
    gp.cChecker.checkTile(this);
    gp.cChecker.checkObject(this, false);
    int enemyIndex = gp.cChecker.checkEntityB(this, gp.testEnemy);
    int zombieIndex = gp.cChecker.checkEntityB(this, gp.zombieList1);
    boolean contactPlayer = gp.cChecker.checkPlayerB(this);

    //Zombie hits player
    if(this.type == 2 && contactPlayer == true){
      if(gp.player.invincible == false){
        int damageTaken = this.damage - gp.player.defense; //Damage calc  
        System.out.println("Defense: " + gp.player.defense + " Damage: " + this.damage);
        if(damageTaken < 0){
          damageTaken = 0;
        }
        gp.player.life -= damageTaken;
        gp.player.invincible = true;
        if (damageTaken == 0) {
          gp.player.invincible = false;
        }
      }
    }

    //If collision is true, enitity can't move
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
    if(collisionOn == false){
      if(directionUpDown != null){
        switch(directionUpDown){
          case "up":
            worldY -= speed;
            break;
          case "down":
            worldY += speed;
            break;
        }
      }
    }

    /*spriteCounter++;
    if(spriteCounter > 25){
      if(spriteNum == 1){
        spriteNum = 2;
      }
      else if(spriteNum == 2){
        spriteNum = 1;
      }
      spriteCounter = 0;
    }*/
    if(invincible){
      invincibleCounter++;
      if(invincibleCounter > 10){
        invincible = false;
        invincibleCounter = 0;
      }
    }
  }
  //Draws the entity 
  public void draw(Graphics2D g2){

    BufferedImage image = null;
    
    int screenX = worldX - gp.player.worldX + gp.player.screenX;
    int screenY = worldY - gp.player.worldY + gp.player.screenY;

    //ONLY load what can be seen on the camera. The plus 1 or minus 1 is to make sure the tile is loaded on the camera (expands it out one tile).
    if(worldX + gp.tileSize > gp.player.worldX - gp.player.screenX &&
       worldX - gp.tileSize < gp.player.worldX + gp.player.screenX &&
       worldY + gp.tileSize > gp.player.worldY - gp.player.screenY &&
       worldY - gp.tileSize < gp.player.worldY + gp.player.screenY) {

      //Changing sprites to make it look like its moving
      switch(direction){
        case "up":
          if(spriteNum == 1){image = up1;}
          if(spriteNum == 2){image = up2;}
          break;
        case "down":
          if(spriteNum == 1){image = down1;}
          if(spriteNum == 2){image = down2;}
          break;
        case "left":
          if(spriteNum == 1){image = left1;}
          if(spriteNum == 2){image = left2;}
          break;
        case "right":
          if(spriteNum == 1){image = right1;}
          if(spriteNum == 2){image = right2;}
          break;
      }
      if(invincible == true){ //Change opacity
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.5f));
      }
      
      g2.drawImage(image, screenX, screenY, null);
      //RESET
      g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
    }
    
  }
  //Roll stats (mainly for zombies to make them increasingly harder)
  public void rollStats(Entity entity){
    //Rolls as many times as his level
    for(int i = 0; i < entity.level; i++){
      Random random = new Random();
      int rand = random.nextInt(10) + 1;
      switch(rand){
        case 1: entity.damage += 1; break;
        case 2: entity.defense += 1; break;
        case 3: entity.speed += 1; 
          if(entity.speed > 4){
            entity.speed = 4;
            entity.damage +=1;
          }
          break;
        case 4: entity.maxLife += 1; break;
        case 6: entity.maxLife += 1; break;

        default: break;
      }
      
    }
  }
  public void use(Entity entity){
    
  }
  public void checkDrop(){ //Drop items

  }
  public void dropItem(Entity droppedItem){ //Which item to drop

    for(int i = 0; i < gp.obj.length; i++){
      if(gp.obj[i] == null){
        gp.obj[i] = droppedItem;
        gp.obj[i].worldX = worldX;
        gp.obj[i].worldY = worldY;
        break;
      }
    }
    
  }
  public BufferedImage setup(String imageName, int width, int height){
    
    UtilityTool uTool = new UtilityTool();
    BufferedImage image = null;
    try{
      image = ImageIO.read(getClass().getResourceAsStream(imageName));
      image = uTool.scaleImage(image, width, height);
    }catch(Exception e){
      e.printStackTrace();
    }
    return image;
  }
  
}