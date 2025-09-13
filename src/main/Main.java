

package main;

class Main {
  
  public static void main(String[] args) {
    System.out.println("Hello world!");

    GameFrame frame1 = new GameFrame();
    
    frame1.gamePanel.setUpGame();
    frame1.gamePanel.startGameThread();
    
  }

  
}
