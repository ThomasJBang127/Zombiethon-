package object;

import entity.Entity;
import main.GamePanel;

public class OBJ_Door extends Entity {
  GamePanel gp;
  
  public OBJ_Door(GamePanel gp){
    super(gp);
    
    name = "Door";
    left1 = setup("/objects/door01.png", gp.tileSize, gp.tileSize);

    collision = true;
  }
}
