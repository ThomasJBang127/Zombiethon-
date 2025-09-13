package object;

import entity.Entity;
import main.GamePanel;

public class OBJ_RunningShoes extends Entity {
  GamePanel gp;
  int value;
  
  public OBJ_RunningShoes (GamePanel gp){
    super(gp);

    name = "RunningShoes";
    value = 1;
    left1 = setup("/objects/boots01.png", gp.tileSize, gp.tileSize);

    solidArea.x = 5;
  }
  public void use(Entity entity){
    //Buffs the entity who picks it up (player)
    entity.speed += value;
      System.out.println("Speed: " + entity.speed);
  }
}

