/**
 * Filename: GameState.java
 * Name: Tiancheng Ma
 * Login: cs8bwi20dp
 * Date: February.1.2020
 * Sources of Help: Piazza
 *
 * Description: this file implements the infrastructure of the game 2048,
     including operations like slide down, setter, getter and add tile.
 */

import java.util.Random;

/**
 * Class header: This class contains methods of the game to progress including
     setter and getter, adding random tile, counting empty tiles, adding tiles
     and methods about movement.
 */
public class GameState{
  //keep track of the board
  private int[][] board;
  //keep track of the score
  private int score;
  //bound for randomTile()
  private static final int FULL_RANGE = 100;
  //total rotation we need to do in isGameOver()
  private static final int ROTATE_TIME = 3;

  /**
   * this method is provided which makes sure my game board will be displayed in the correct format
   * @return the string version of my board
   */
  public String toString () {
      StringBuilder outputString = new StringBuilder();
      outputString.append(String.format("Score: %d\n", getScore()));
      for (int row = 0; row < getBoard().length; row++) {
          for (int column = 0; column < getBoard()[0].length; column++) {
              outputString.append(getBoard()[row][column] == 0 ? "    -" :
                  String.format("%5d", getBoard()[row][column]));
          }
          outputString.append("\n");
      }
      return outputString.toString();
  }

  /**
   * this constructor gives us a new board and empty score for one game.
   * @param numRows the number of rows we want on board.
   * @param numCols the number of columns we want on board.
   */
  public GameState (int numRows, int numCols){
    this.board = new int[numRows][numCols];
    this.score = 0;
  }

  /**
   * this method gives us a deep copy of our current board.
   * @return the deep copy of current board.
   */
  public int[][] getBoard (){
    //initialize the size of deep copy variable
    int[][] deepcopyGET = new int[this.board.length][this.board[0].length];
    for(int i = 0; i < this.board.length; i++){
      for(int j = 0; j < this.board[0].length; j++){
        //copy every element
        deepcopyGET[i][j] = this.board[i][j];
      }
    }
    return deepcopyGET;
  }

  /**
   * this method set our current board to the deep copy of board passed in
   * @param newBoard the board we want to pass in to our current board
   */
  public void setBoard (int[][] newBoard){
    //check if given board is null
    if(newBoard == null){
      return;
    }
    //make a deep copy of given board.
    int[][] deepcopySET = new int[newBoard.length][newBoard[0].length];
    for(int i = 0; i < newBoard.length; i++){
      for(int j = 0; j < newBoard[0].length; j++){
        deepcopySET[i][j] = newBoard[i][j];
      }
    }
    //set current board to deep copy of new board
    this.board = deepcopySET;
  }

  /**
   * this method gives us current score
   * @return current score
   */
  public int getScore (){
    return this.score;
  }

  /**
   * this method will set current score to given score
   * @param newScore the new score we want to pass in to current score
   */
  public void setScore (int newScore){
    this.score = newScore;
  }

  /**
   * this method gives us an interger between 0 and the bound passed in
   * @param bound the exclusive upper bound of the range
   * @return the randomly chosen number in range
   */
  protected int rollRNG (int bound){
    //initialize a new Random object
    Random rad = new Random();
    //randomly select number from 0 inclusively to bound exclusively
    int rollrng = rad.nextInt(bound);
    return rollrng;
  }

  /**
   * this method will give us either TWO_TILE or FOUR_TILE
     based on TWO_PROB defined in Config.java
   * @return either TWO_TILE or FOUR_TILE
   */
  protected int randomTile (){
    //initialize a new Random object
    Random sampleArray = new Random();
    int value = sampleArray.nextInt(FULL_RANGE);
    //if value is less than TWO_PROB we get TWO_TILE or we get FOUR_TILE
    if(value < Config.TWO_PROB){
      return Config.TWO_TILE;
    }
    else{
      return Config.FOUR_TILE;
    }
  }

  /**
   * this method will count total number of tiles of value 0 on board.
   * @return the number of tiles of 0
   */
  protected int countEmptyTiles (){
    //initialize the variable
    int emptyTiles = 0;
    //count 0 all over the board
    for(int i = 0; i < this.board.length; i++){
      for(int j = 0; j< this.board[0].length; j++){
        if(this.board[i][j] == 0){
          //if there is one empty tile we add 1 on variable
          emptyTiles += 1;
        }
      }
    }
    return emptyTiles;
  }

  /**
   * this method will first randomly choose an empty tile on board
     as long as empty tile exists and add new tile at that position
     using addTile()
   * @return the new tile added including 0, TWO_TILE or FOUR_TILE
   */
  protected int addTile (){
    //count the number of empty tiles
    int ZeroNum = 0;
    for(int i = 0; i < this.board.length; i++){
      for(int j = 0; j< this.board[0].length; j++){
        if(this.board[i][j] == 0){
          ZeroNum += 1;
        }
      }
    }
    //if there is no empty tile, we should return 0
    if(ZeroNum == 0){
      return 0;
    }
    //create two int[] to store index of empty tiles
    int[] row = new int[ZeroNum];
    int[] col = new int[ZeroNum];
    //position of int[] to store index of empty tiles
    int index = 0;
    for(int i = 0; i < this.board.length; i++){
      for(int j = 0; j< this.board[0].length; j++){
        if(this.board[i][j] == 0){
          row[index] = i;
          col[index] = j;
          index += 1;
        }
      }
    }
    //randomly select a position for int[] and add random tile on it
    Random rad = new Random();
    int rad_index = rad.nextInt(ZeroNum);
    this.board[row[rad_index]][col[rad_index]] = randomTile();
    return this.board[row[rad_index]][col[rad_index]];
  }

  /**
   * this method will rotate current board for 90 degrees counter-clockwisely
   */
  protected void rotateCounterClockwise (){
    //make a deep copy of current board
    int[][] deepcopy = new int[this.board.length][this.board[0].length];
    for(int i = 0; i < this.board.length; i++) {
      for (int j = 0; j < this.board[0].length; j++) {
        deepcopy[i][j] = this.board[i][j];
      }
    }
    //create new board with assumed size
    int[][] rotate = new int[this.board[0].length][this.board.length];
    for(int i = 0; i < this.board.length; i++){
      for(int j = 0; j< this.board[0].length; j++){
        //rotate operation
        rotate[j][i] = deepcopy[i][deepcopy[0].length-j-1];
      }
    }
    this.board = rotate;
  }

  /**
   * this method will check whether current board can be slide down
   * @return true if the board can be slided down and false if it can't
   */
  protected boolean canSlideDown (){
    //check if two tiles can be added from bottom to top of board
    for(int col = 0; col < this.board[0].length; col++){
      for(int row = this.board.length - 1; row > 0; row--){
        //check if this tile is empty
        if(this.board[row][col] != 0){
          //check if there is empty tile between two tiles
          boolean empty_tile = false;
          int next_int = this.board[row-1][col];
          if(next_int == 0){
            empty_tile = true;
          }
          //return true if no empty tiles between them and two tiles are the same
          if(this.board[row][col] == next_int && empty_tile == false){
            return true;
          }
        }
      }
    }
    //if there is empty tiles below a non-empty tile, return true
    for(int col = 0; col < this.board[0].length; col++){
      for(int row = this.board.length-1; row > 0; row--){
        if(this.board[row][col] == 0 && this.board[row-1][col] != 0){
          return true;
        }
      }
    }
    return false;
  }

  /**
   * this method will check whether game is over
   * @return true if game is over or false if it's not
   */
  public boolean isGameOver (){
    //current rotate time
    int current_time = 0;
    while(current_time <= ROTATE_TIME){
      current_time += 1;
      //if we can slide down the board, return false
      if(canSlideDown() == true){
        return false;
      }
      //if we can't slide down for current location, do rotateCounterClockwise()
      else{
        rotateCounterClockwise();
      }
    }
    //if in all directions the board can't be slided down, return true
    return true;
  }

  /**
   * this method will do the slide down operation to current board
     and check if the board changed
   * @return true if the board is changed and false if it is not
   */
  protected boolean slideDown (){
    boolean if_change = false;
    //first time to move downward
    for(int j = 0; j < this.board[0].length; j++){
      for(int i = this.board.length-1; i >= 0; i--){
        int move = 0;
        if(this.board[i][j] != 0){
          //for non-empty tiles, we count empty tiles below them
          for(int space = i; space < this.board.length; space++){
            if(this.board[space][j] == 0){
              move += 1;
            }
          }
          if(move != 0){
            //we replace values as move is not zero
            this.board[i+move][j] = this.board[i][j];
            this.board[i][j] = 0;
          }
        }
      }
    }
    //combine tiles of the same from bottom to top
    for(int j = 0; j < this.board[0].length; j++){
      for(int i = this.board.length-1; i > 0; i--){
        if(this.board[i][j] == this.board[i-1][j]){
          //combine value
          this.board[i][j] += this.board[i-1][j];
          //top one tile becomes 0
          this.board[i-1][j] = 0;
          //at least two tiles are combined which means the board changes
          if_change = true;
          //add score
          this.score += this.board[i][j];
        }
      }
    }
    //the second time to move downward
    for(int j = 0; j < this.board[0].length; j++){
      for(int i = this.board.length-1; i >= 0; i--){
        int move = 0;
        if(this.board[i][j] != 0){
          for(int space = i; space < this.board.length; space++){
            if(this.board[space][j] == 0){
              move += 1;
            }
          }
          if(move != 0){
            this.board[i+move][j] = this.board[i][j];
            this.board[i][j] = 0;
          }
        }
      }
    }
    //if no tiles are combined, it will return false
    return if_change;
  }

  /**
   * this method will check if direction passed in is valid to be slided down
   * @param dir the direction user will pass in
   * @return whether sliding down at that direction is valid
   */
  public boolean move (Direction dir){
    //check null case
    if(dir == null){
      return false;
    }
    //get rotate time
    int rotate = 0;
    rotate = dir.getRotationCount();
    //do rotateCounterClockwise() based on supposed roatete time
    while(rotate > 0){
      this.rotateCounterClockwise();
      rotate -= 1;
    }
    //check if current board can be slided down
    boolean can_slide = this.slideDown();
    //find values to change the board back
    int full_rotate = Direction.RIGHT.getRotationCount()+1;
    rotate = dir.getRotationCount();
    //change back
    while(full_rotate-rotate > 0){
      this.rotateCounterClockwise();
      rotate += 1;
    }
    //add tile
      this.addTile();
    //return false if can not slide down and true if the move is valid
    return can_slide;
  }
}
