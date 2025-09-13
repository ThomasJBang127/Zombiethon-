package object;

import entity.Entity;
import java.awt.Rectangle;
import main.GamePanel;

public class OBJ_Tree extends Entity {
  GamePanel gp;

  public OBJ_Tree (GamePanel gp){
    super(gp);

    name = "Tree";
    left1 = setup("/Tiles/tree01.png", gp.tileSize, gp.tileSize);

    solidArea = new Rectangle(15, 8, 18, 38);
    solidAreaDefaultX = solidArea.x;
    solidAreaDefaultY = solidArea.y;
    collision = true;
  } 
}