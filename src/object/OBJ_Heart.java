package object;

import entity.Entity;
import main.GamePanel;


public class OBJ_Heart extends Entity{
  
  GamePanel gp;
  private int value = 2;

  public OBJ_Heart(GamePanel gp){
    super(gp);

    type = 3;
    name = "Heart";
    left1 = setup("/objects/HeartFull.png", gp.tileSize, gp.tileSize);
    image = setup("/objects/HeartFull.png", gp.tileSize, gp.tileSize);
    image2 = setup("/objects/HeartHalf.png", gp.tileSize, gp.tileSize);
    image3 = setup("/objects/HeartDead.png", gp.tileSize, gp.tileSize);
    
  }
  public void use(Entity entity){
    //Heals the entity who picks it up (player)
    entity.life += getValue();
    if(entity.life > entity.maxLife){
      entity.life = entity.maxLife;
    }
  }
  //Getter and setter
  public int getValue(){
    return value;
  }
  public void setValue(int value){
    this.value = value;
  }
}
