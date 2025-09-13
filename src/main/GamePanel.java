package main;

import entity.Entity;
import entity.Player;
import entity.ZOM_type1;

import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.FileReader;
import java.util.Scanner;


/*
Basically, a JFrame represents a framed window and a JPanel represents some area in which controls (e.g., buttons, checkboxes, and textfields) and visuals (e.g., figures, pictures, and even text) can appear.
*/

public class GamePanel extends JPanel implements Runnable{

  // SCREEN SETTINGS
  final int originalTileSize = 16; //16x16 pixels
  final int scale = 3; //Scale up
  public final int tileSize = originalTileSize * scale; //new size (pixels)
  public final int maxScreenCol = 16;
  public final int maxScreenRow = 12;
  public final int screenWidth = tileSize * maxScreenCol; //new width (pixels)
  public final int screenHeight = tileSize * maxScreenRow; //new height (pixels)

  //WORLD SETTINGS
  public final int maxWorldCol = 50;
  public final int maxWorldRow = 50;
  public final int worldWidth = tileSize * maxWorldCol;
  public final int worldHeight = tileSize * maxWorldRow;
  
  //FPS 
  int FPS = 30;
  int spawnCounter = 0;

  //Initiate other classes
  TileManager tileM = new TileManager(this); 
  KeyHandler keyH = new KeyHandler(this);
  Sound sound = new Sound();
  Thread gameThread;
  public UI ui = new UI(this);
//  public EventHandler eHandler = new EventHandler(this);
  public CollisionChecker cChecker = new CollisionChecker(this);
  public AssetSetter aSetter = new AssetSetter(this);
  
  //PLAYER CREATED 
  public Player player = new Player(this, keyH); //this is this gamePanel
  public Entity obj[] = new Entity[10];
  public Entity testEnemy[] = new Entity[3]; //Max number of raoming zombies/
  public Entity zombieList1 [] = new Entity[30]; //30 zombies MAX for now
  public ArrayList<Entity> bulletList = new ArrayList<Entity>();
  ArrayList<Entity> entityList = new ArrayList<Entity>();

  //Game State
  public int gameState;
  public final int titleState = 0;
  public final int playState = 1;
  public final int pauseState = 2;
  public final int gameOverState = 3; 
  //File 
  int highScore;
  double highTime;
  
  public GamePanel(){

    //Creates panel?
    this.setPreferredSize(new Dimension(screenWidth, screenHeight));
    this.setBackground(Color.black);
    this.setDoubleBuffered(true); //Improves performance
    this.addKeyListener(keyH);
    this.setFocusable(true);
    
  }

  public void setUpGame(){

    readScore();
    
    aSetter.setObject();
    aSetter.setZombie();
    aSetter.setEnemy();
    gameState = titleState;
  }

  public void retry() {
    player.setDefaultPositions();
    player.restoreLife();
    aSetter.setObject();
    aSetter.setZombie();
    aSetter.setEnemy();
  }

  public void restart() {
    player.setDefaultPositions();
    player.setDefaultValues();
    aSetter.setObject();
    aSetter.setZombie();
    aSetter.setEnemy();
  }
  

  //Starts gameloop?
  public void startGameThread(){
    gameThread = new Thread(this);
    gameThread.start();
  }

  //Game gets run 
  @Override
  public void run(){ //Game loop (Delta method. Video also covers a "sleep" method)

    double drawInterval = 1000000000/FPS;
    double delta = 0;
    long lastTime = System.nanoTime();
    long currentTime;
    
    // used for FPS
    long timer = 0;
    int drawCount = 0;
    
    while(gameThread != null){ //Gets called FPS times a second

      currentTime = System.nanoTime();
      //Time difference
      delta += (currentTime - lastTime) / drawInterval;
      timer += (currentTime - lastTime); //For FPS
      lastTime = currentTime;
      
      if(delta >= 1){
        //1 UPDATE: Update information (e.g., character positions)
        update();
        //2 DRAW: Draw the screen with the updated information
        repaint();
        
        delta--;
        drawCount++; //For FPS
      }

      if(timer >= 1000000000){
        //FPS printing
        //System.out.println("FPS: " + drawCount);
        drawCount = 0;
        timer = 0;
        
      }
      
    }
    
  }

  //Run in gameloop (run)
  public void update(){ 
    if (gameState == playState) {
      spawnCounter++;
      //player
      player.update();
      //Enemy
      for(int i = 0; i < testEnemy.length; i++){
        if(testEnemy[i] != null){
          testEnemy[i].update();
        }
      }
      //Zombie
      for(int i = 0; i < zombieList1.length; i++){
        if(zombieList1[i] != null){
          zombieList1[i].update();
        }
      }
      //Bullet
      for(int i = 0; i < bulletList.size(); i++){
        if(bulletList.get(i) != null){
          if(bulletList.get(i).alive){
            bulletList.get(i).update();
          }
          if(bulletList.get(i).alive == false){
            bulletList.remove(i);
          }
        }
      }
      if(spawnCounter >= 6000){
        for(int i = 0; i < zombieList1.length; i++){
          if(zombieList1[i] == null){
            zombieList1[i] = new ZOM_type1(this);
            zombieList1[i].worldX = aSetter.getRandomNum() * tileSize;
            zombieList1[i].worldY = aSetter.getRandomNum() * tileSize;
            break;
          }
        }
        spawnCounter = 0;
      }
      
    }
    if(gameState == pauseState) {
    }
   
  }
  
  public void paintComponent(Graphics g){
    
    super.paintComponent(g);
    Graphics2D g2 = (Graphics2D)g; //Type casting the g into a Graphics2D object
    
    //Title Screen
    if (gameState == titleState)  {
      ui.draw(g2);
      g2.dispose();
    }
    //Other States
    else{
      //Draw tile
      tileM.draw(g2);
      //Add entities to entityList
      entityList.add(player); //Player
      for(int i = 0; i < testEnemy.length; i++){
        if(testEnemy[i] != null){
          entityList.add(testEnemy[i]); //Enemy
        }
      }
      for(int i = 0; i < zombieList1.length; i++){
        if(zombieList1[i] != null){
          entityList.add(zombieList1[i]); //Zombie
        }
      }
      for(int i = 0; i < obj.length; i++){
        if(obj[i] != null){
          entityList.add(obj[i]); //Obejcts
        }
      }
      for(int i = 0; i < bulletList.size(); i++){
        if(bulletList.get(i) != null){
          entityList.add(bulletList.get(i)); //Bullet
        }
      }
      //Put arraylist into array
      Entity[] entityArray = new Entity[entityList.size()];
      entityList.toArray(entityArray);
      entityList.clear(); //Clears arraylist
      //MERGE SORT
      mergeSort(entityArray, 0, entityArray.length - 1);
      //Draw entities based of entityArray
      for(int i = 0; i < entityArray.length; i++){
        entityArray[i].draw(g2);
      }

      //Draw UI
      ui.draw(g2); //Calls on draw method in UI class

      g2.dispose(); //Save storage space 
    }

   
    
  }
  
  public static void mergeSort(Entity [] list, int start, int end){
      //Split the array in halves until we reach our base case. Then we sort each array until we work our way back up.

      if (start < end) {
          int mid = (start+end)/2;
          //left half of original array
          mergeSort(list,start,mid);
          //Right half of original array
          mergeSort(list,(mid+1),end);
          //sort method - this will compare smaller
          sortForMergeSort(list,start,mid,end);
      }

  }
  public static void sortForMergeSort(Entity []list, int start, int mid, int end){
      //length of the left and right arrays at this particular point
      int leftSide = mid - start +1; 
      int rightSide = end - mid;

      //make some temporary arrays
      Entity [] L = new Entity[leftSide];
      Entity [] R = new Entity[rightSide];

      //Take the info from the ORIGINAL list and add it to the TEMP arrays
      //leftside
      for (int i = 0; i < L.length; i++) {
          L[i] = list[start+i];
      }
      //rightside
      for (int i = 0; i < R.length; i++) {
          R[i] = list[mid+1+i];
      }

      //start values of the temp arrays
      int i = 0;
      int j = 0;
      int k = start; //used to determine the value in the ORIGINAL arraw

      while (i < leftSide && j < rightSide) {
          if (L[i].worldY < R[j].worldY) {
              list[k] = L[i]; //Replace value in original state
              i++;
          } else {
              list[k] = R[j]; //otherwise the rightside
              j++;
          }
          k++;
      }

      //copy any remaining left side values, if there are any
      while (i<leftSide) {
          list[k] = L[i];
          i++;
          k++;
      }
      //copy any remaining right side values, if there are any
      while (i<rightSide) {
          list[k] = R[j];
          j++;
          k++;
      }
  }
  //Sound for Game

  //Music
  public void playMusic(int i) {
    sound.setFile(i);
    sound.play();
    sound.loop();    
  }
  //Stop sound
  public void stopMusic() {
    sound.stop();
  }
  //Sound Effects
  public void playSE(int i) {
    sound.setFile(i);
    sound.play();
  }
  public void saveScore(){
    try{
      //Print into file
      FileWriter fw = new FileWriter("scores.txt");
      PrintWriter pw = new PrintWriter(fw);
      System.out.println("Scores are being put on: " + player.score);

      //Finds highest score and prints it into txt 
      if(highScore < player.score){
        System.out.println("New High Score!");
        pw.println(player.score);
      } else {
        System.out.println("No High Score!");
        pw.println(highScore);
      }
      pw.close();
    }catch (Exception e) {System.out.println("Error in Write file");}
  }
  public void readScore(){
    try{
      //File reader
      System.out.println("File reader");
      FileReader fr = new FileReader("scores.txt");
      Scanner s = new Scanner(fr);
      
      String line1, line2;
      line1 = s.nextLine();

      highScore = Integer.parseInt(line1);
      //Check by printing
      System.out.println(highScore);
      
      s.close();
    }catch (Exception e) {System.out.println("Error in file reader");}
  }
  
}