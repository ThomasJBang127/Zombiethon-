package object;

import entity.Entity;
import main.GamePanel;

public class OBJ_Gun extends Entity {
  GamePanel gp;
  int value = 1;

  public OBJ_Gun(GamePanel gp){
    super(gp);

    type = 3;
    name = "Gun";
    left1 = setup("/objects/gun01.png", gp.tileSize, gp.tileSize);

    solidArea.x = 5;
  }

  public int getValue(){
    return value;
  }
  public void setValue(int value){
    this.value = value;
  }
  
}
