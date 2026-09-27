package entity;

import main.GamePanel;


public class Projectile extends Entity {

  Entity user;
  
  public Projectile(GamePanel gp){
    super(gp);
    
  }

  public void set(int worldX, int worldY, String direction, boolean alive, Entity user){
    this.worldX = worldX;
    this.worldY = worldY;
    this.direction = direction;
    this.alive = alive;
    this.user = user;
    this.life = this.maxLife; //Each bullet is full HP
  }

  public void update(){

    if(user == gp.player){ //Player bullets
      int zombieIndex = gp.cChecker.checkEntityB(this, gp.zombieList1);
      if(zombieIndex != 999){ //If the bullet hits a zombie
        gp.player.damageZombie(zombieIndex, damage);
        alive = false; //Bullet is dead
      }
      //Check if hit Enemy (roaming zombie)
      int enemyIndex = gp.cChecker.checkEntityB(this, gp.testEnemy);
      if(enemyIndex != 999){
        gp.player.damageEnemy(enemyIndex, damage);
        alive = false;
      }
    } else if(user != gp.player){ //Zombie pojectiles
      
    }
    
    switch(direction){
        case "up": worldY -= speed; break;
        case "down": worldY += speed; break;
        case "left": worldX -= speed; break;
        case "right": worldX += speed; break;
    }

    life--; //Basically flight time
    if(life <= 0){
      alive = false;
    }
  }
  
}
