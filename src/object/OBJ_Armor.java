package object;

import entity.Entity;
import main.GamePanel;

public class OBJ_Armor extends Entity {
  GamePanel gp;
  int value;
  
  public OBJ_Armor(GamePanel gp){
    super(gp);

    name = "Armor";
    value = 2; 
    left1 = setup("/objects/armor01.png", gp.tileSize, gp.tileSize);

    solidArea.x = 5;
  }
  public void use(Entity entity){
    //Buffs the entity who picks it up (player)
    entity.defense += value;
  }
}