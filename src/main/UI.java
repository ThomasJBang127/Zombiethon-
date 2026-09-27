package main;

import entity.Entity;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.awt.Color;
import java.awt.Font;
import java.text.DecimalFormat;
import object.OBJ_Gun;
import object.OBJ_Heart;
import object.OBJ_Key;


public class UI {

  GamePanel gp;
  Font arial_40, arial_80B;
  Graphics2D g2;
  
  
  BufferedImage HeartFull, HeartHalf, HeartDead;
  BufferedImage keyImage;
  BufferedImage gunImage;

  
  public boolean messageOn = false;
  public String message = "";
  int messageCounter = 0;
  public boolean gameFinished = false;
  public int commandNum = 0;

   public double playTime;
  DecimalFormat dFormat = new DecimalFormat("#0.00");
  
  public UI(GamePanel gp){
    this.gp = gp;

    arial_40 = new Font("Arial", Font.PLAIN, 40);
    arial_80B = new Font("Arial", Font.BOLD, 80);
    Entity key = new OBJ_Key(gp);
    keyImage = key.left1;

    Entity gun = new OBJ_Gun(gp);
    gunImage = gun.left1;

    Entity heart = new OBJ_Heart(gp);
    HeartFull = heart.image;
    HeartHalf = heart.image2;
    HeartDead = heart.image3;
  }

  public void showMessage(String text) {

    message = text;
    messageOn = true;
  }
  public void draw(Graphics2D g2){
    this.g2 = g2;

    //Title State
    if (gp.gameState == gp.titleState) {
      drawTitleScreen();
    }

    g2.setFont(arial_40); 
    g2.setColor(Color.white);
    
    //Play State
    if(gp.gameState == gp.playState) {
      //Draw number of Keys
      drawKeys();

      //Draw number of Guns
      drawGunLvl();

      g2.setFont(g2.getFont().deriveFont(25F));
      //Score
      drawScore();
      
      //Draw High Score
      drawHighScore();

      //Draw heart
      drawPlayerLife(); 

      //Draw Time
      drawTime();

      //Message
      if(messageOn == true){
        g2.setFont(g2.getFont().deriveFont(30F));
        g2.drawString(message, gp.tileSize*6, gp.tileSize/2);

        messageCounter++;

        if(messageCounter > 120) {
          messageOn = false;
          messageCounter = 0;
        }
      }     
    }
    //Pause State
    if(gp.gameState == gp.pauseState) {
      drawPauseScreen();
    }
    //GamveOver State
    if(gp.gameState == gp.gameOverState) {
      drawGameOverScreen();
    }
  }

  public void drawPlayerLife(){
    int x = gp.tileSize / 2;
    int y = gp.tileSize / 2;
    int remainingLife = gp.player.life;
    int drawn = 0;
    int maxLife = gp.player.maxLife;
    
    while (drawn < maxLife/2) {
      if (remainingLife >= 2) {
        g2.drawImage(HeartFull, x, y, null);
        remainingLife -= 2;
      } else if (remainingLife >= 1) {
        g2.drawImage(HeartHalf, x, y, null);
        remainingLife -= 1;
      } else {
        g2.drawImage(HeartDead, x, y, null);
      }
      x += gp.tileSize;
      drawn++;
    }
    
  }

  public void drawTitleScreen() {

  
    //Title Name
    g2.setFont(g2.getFont().deriveFont(Font.BOLD,96F));
    String text = "Zombiethon";
    int x = getXforCenteredText(text);
    int y = gp.tileSize*3;

    //Shadow
    g2.setColor(new Color(136, 8, 8));
    g2.drawString(text, x+4, y+4);
    
    //Main Color
    g2.setColor(Color.red);
    g2.drawString(text, x, y);

    //Player Image
    x = gp.screenWidth/2 - (gp.tileSize*2)/2;
    y += gp.tileSize*2;
    
    g2.drawImage(gp.zombieList1[0].down1, x, y, gp.tileSize*2, gp.tileSize*2, null);

    //Menu
    g2.setFont(g2.getFont().deriveFont(Font.BOLD,40F));
    
    text = "NEW GAME";
    x = getXforCenteredText(text);
    y += gp.tileSize*3.5;
    g2.drawString(text, x, y);
    if(commandNum == 0) {
      g2.drawString(">", x-gp.tileSize, y);
    }

    text = "QUIT GAME";
    x = getXforCenteredText(text);
    y += gp.tileSize;
    g2.drawString(text, x, y);
    if(commandNum == 1) {
      g2.drawString(">", x-gp.tileSize, y);
    }
  }


  //DRAWS PAUSE SCREEN
  public void drawPauseScreen() {

    String text = "PAUSED";
    int x = getXforCenteredText(text);
    int y = gp.screenHeight/2;

    g2.drawString(text, x, y);
  }

  //Calculates text centre
  public int getXforCenteredText(String text) {
    int length = (int)g2.getFontMetrics().getStringBounds(text, g2).getWidth();
    int x = gp.screenWidth/2 - length/2;
    return x;
  }
  
  //Draws high score
  public void drawHighScore() {
    g2.setColor(Color.white);
    if(gp.player.score > gp.highScore){
      gp.highScore = gp.player.score;
    }
    g2.drawString("Highest Score: " + gp.highScore, 20, 120);
    g2.setFont(g2.getFont().deriveFont(30F));
  }
  
  //Draws time played
  public void drawTime() {
    playTime+=(double)1/30;
    g2.drawString("Time: " + dFormat.format(playTime), gp.tileSize*11, 65);
  }

  //Draw Score
  public void drawScore() {
    g2.setColor(Color.white);
    g2.drawString("Score: " + gp.player.score, 20, 145);
  }

  //Draw gun level
  public void drawGunLvl() {
    g2.drawImage(gunImage, 6, 450, null);
    g2.drawString("x " + gp.player.gunLvl, 72, 485);
  }
  public void drawKeys(){
    g2.drawImage(keyImage, 6, 500, null);
    g2.drawString("x " + gp.player.hasKey, 72, 540);
    this.g2 = g2;
  }

  //DRAWS GAME OVER SCREEN
  public void drawGameOverScreen() {
    
    g2.setColor(new Color(0,0,0,150));
    g2.fillRect(0,0,gp.screenWidth, gp.screenHeight);

    int x;
    int y;
    String text;
    g2.setFont(g2.getFont().deriveFont(Font.BOLD,110F));
    
    //Shadow
    text = "YOU DIED";
    g2.setColor(Color.red);
    x = getXforCenteredText(text);
    y = gp.tileSize*4;
    g2.drawString(text, x, y);

    //Main
    g2.setColor(Color.black);
    g2.drawString(text, x+4, y+4);

    //Retry
    g2.setColor(Color.red);
    g2.setFont(g2.getFont().deriveFont(50F));
    text = "Retry";
    x = getXforCenteredText(text);
    y += gp.tileSize*4;
    g2.drawString(text, x, y);
    if(commandNum == 0) {
      g2.drawString(">", x-40, y);
    }
    
    //Return to menu
    text = "Return to Menu";
    x = getXforCenteredText(text);
    y += 55;
    g2.drawString(text, x, y);
    if (commandNum == 1) {
      g2.drawString(">", x-40, y);
    }

    
  }
  

}