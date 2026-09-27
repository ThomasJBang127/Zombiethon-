package main;


import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;

public class KeyHandler implements KeyListener {

  GamePanel gp;
  public boolean upPressed, downPressed, leftPressed, rightPressed;
  public boolean jPressed;


  public KeyHandler(GamePanel gp) {
    this.gp=gp;
  }
  // Implement KeyListener methods
  @Override
  public void keyPressed(KeyEvent e) {
    // Handle key pressed events
    int code = e.getKeyCode();

    //Title State
    if(gp.gameState == gp.titleState) {
      if (code == KeyEvent.VK_W) {
        gp.ui.commandNum--;
        if(gp.ui.commandNum < 0) {
          gp.ui.commandNum = 1;
        }
      }
      if (code == KeyEvent.VK_S) {
        gp.ui.commandNum++;
        if(gp.ui.commandNum > 1) {
          gp.ui.commandNum = 0;
        }
      }
      if(code == KeyEvent.VK_ENTER) {
        if(gp.ui.commandNum == 0) {
          gp.gameState = gp.playState;
          //Play Music
          gp.playMusic(0);
        }
        if(gp.ui.commandNum == 1){
          System.exit(0);
        }
      }
      
    }
    

    //Play State
    //Checks the key pressed and sets the boolean to true
    if (gp.gameState == gp.playState){
      if (code == KeyEvent.VK_W) {
        upPressed = true;
      }
      if (code == KeyEvent.VK_S) {
        downPressed = true;
      }
      if (code == KeyEvent.VK_A) {
        leftPressed = true;
      }
      if (code == KeyEvent.VK_D) {
        rightPressed = true;
      }
      if (code == KeyEvent.VK_J) {
        jPressed = true;
      }
    }
    //GAME OVER STATE
    else if (gp.gameState == gp.gameOverState) {
      if(code == KeyEvent.VK_W) {
        gp.ui.commandNum--;
        if(gp.ui.commandNum < 0) {
          gp.ui.commandNum = 1;
        }
      }
      if(code == KeyEvent.VK_S) {
        gp.ui.commandNum++;
        if(gp.ui.commandNum > 1) {
          gp.ui.commandNum = 0;
        }
      }
      if (code == KeyEvent.VK_ENTER) {
        if(gp.ui.commandNum == 0) {
          gp.gameState = gp.playState;
          gp.retry();
        }
        else if(gp.ui.commandNum == 1) {
          gp.gameState = gp.titleState;
          gp.restart();
        }
      }
    }
    // Pause State
    if (code == KeyEvent.VK_P) {
      if(gp.gameState == gp.playState) {
        System.out.println("Pause");
        gp.gameState = gp.pauseState;
      }  
      else if(gp.gameState == gp.pauseState){
        System.out.println("Unpause");
        gp.gameState = gp.playState;
      }
    }
    
  }
  @Override
  public void keyReleased(KeyEvent e) {
    // Handle key released events
    int code = e.getKeyCode();
    //Checks the key that was released and sets the boolean to false
    if (code == KeyEvent.VK_W) {
      upPressed = false;
    }
    if (code == KeyEvent.VK_S) {
      downPressed = false;
    }
    if (code == KeyEvent.VK_A) {
      leftPressed = false;
    }
    if (code == KeyEvent.VK_D) {
      rightPressed = false;
    }
    if (code == KeyEvent.VK_J) {
      jPressed = false;
    }
    
    
  }
  @Override
  public void keyTyped(KeyEvent e) {
    // Handle key typed events
  }
  
}
