package object;

import entity.Projectile;
import main.GamePanel;


public class OBJ_Bullet extends Projectile{ 
  //Projectile is an entity, and Bullet is a subclass of Projectile.
  GamePanel gp;
  
  public OBJ_Bullet(GamePanel gp){
    super(gp);
    this.gp = gp;

    name = "Bullet";
    speed = 6;
    maxLife = 80;
    damage = 2;
    boolean alive = false;
    
    getImage();
    
  } 

  public void getImage(){
    up1 = setup("/projectiles/bulletUp.png", gp.tileSize, gp.tileSize);
    //up2 = setup("projectiles/bulletUp.png", gp.tileSize, gp.tileSize);
    down1 = setup("/projectiles/bulletDown.png", gp.tileSize, gp.tileSize);
    //down2 = setup("projectiles/bulletDown.png", gp.tileSize, gp.tileSize);
    left1 = setup("/projectiles/bullet01.png", gp.tileSize, gp.tileSize);
    //left2 = setup("projectiles/bullet01.png", gp.tileSize, gp.tileSize);
    right1 = setup("/projectiles/bulletRight.png", gp.tileSize, gp.tileSize);
    //right2 = setup("projectiles/bulletRight.png", gp.tileSize, gp.tileSize);
  }
}