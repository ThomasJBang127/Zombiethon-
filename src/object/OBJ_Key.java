package object;

import entity.Entity;
import main.GamePanel;

public class OBJ_Key extends Entity {
  GamePanel gp;
  
  public OBJ_Key(GamePanel gp){
    super(gp);
    
    name = "Key";
    left1 = setup("/objects/key01.png", gp.tileSize, gp.tileSize);

    solidArea.x = 5;
  }
}
