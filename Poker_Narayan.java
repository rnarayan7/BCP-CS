//Roshan Narayan
//FallProject
import java.util.ArrayList;
import java.util.Collections;
import java.io.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import javax.imageio.*;
import javax.swing.*;
/**
   REFLECTION: This is arguably the most difficult and comprehensive program
   that I have ever coded. It utilizes concepts as generic as subclasses, abstract
   classes, inner classes, interfaces, and comparators, to specific areas of JAVA 
   including JFrames, JPanels, JOptionPanes, LayoutManages, ActionListeners, 
   ProgramDelays, Graphics classes. It also includes some sense of an artifical
   intelligence, in that the CPU players must decide whether or not they must fold,
   bet low, bet high, and so on. It creates a Graphic User Interface, with the player
   using individual windows to input decisions and values. 
   
   Overall, I am glad I chose to do this because while I have learned a great deal 
   about Computer Scienceover the last two years, I have never had a chance to apply 
   my knowledge of so many concepts in one coherent program. 
   
   It should work in most cases as long as the user follows the basic instructions 
   given. I was not able to account for all exceptions thrown and human mistakes due 
   to time constrains, but it should be fairly easy to use.
   
   None of this code was copied. Certain websites were used to gain knowledge of
   certain concepts, but all code written here is original.
   
   Thanks. --- Roshan Narayan '16 --- 10/23/14
*/
/**
   This class has the runner for the poker program.
*/
public class Poker_Narayan extends JFrame {
   private JPanel board;
   private JPanel statBox;
   private JPanel notifBox;
   private int turn;
   private int roundNumber;
   private double currentBet;
   private double pot;
   private double smallBlind = 50.0;
   private double bigBlind = 100.0;
   private double medValue = 12;
   private ArrayList<Player> players = new ArrayList<Player>();
   private ArrayList<Card> communityCards = new ArrayList<Card>();
   //This class creates the frame and starts the game sequence
   public static void main (String [] args) {
      Poker_Narayan runner = new Poker_Narayan();
      runner.setVisible(true);
      runner.runGame();
   }
   //Calls initComponents() to add Components and sets size of frame
   public Poker_Narayan() {
      //Prompts user for name
      String fName = JOptionPane.showInputDialog(null,"Input your first name",1);
      String lName = JOptionPane.showInputDialog(null,"Input your last name",1);
      
      //Creates one user and 3 CPU
      players.add(new User(fName,lName));
      players.add(new CPU("James","Bond",9.3));
      players.add(new CPU("Kim","Kardashian",1.5));
      players.add(new CPU("Brad","Lindy",6.1));
      
      //Calls graphics method
      initComponents();
      this.setSize(1275,800);
   }
   //Runs the bet rounds and then the fold round
   public void runGame() {
      //Runs the round four times
      for(int i = 0; i<4; i++) {   
         //Sets up round 
         setUpRound();
         
         //Begins initial betting without user input
         initialBet();
         
         //Reveals three cards
         reveal(3);
         medValue = 12;
         betRound();
         
         //Reveals two more cards
         reveal(2);
         medValue += 280;
         betRound();
         
         //Displays winner
         winRound();
      }
      //After four rounds, displays winner
      winGame();
   }
   //Sets up the round the first time, by creating a new deck and distributing deckCards
   public void setUpRound() {
      //Sets deck and shuffles it
      Deck.setDeck();
      Deck.shuffle();
      Deck.shuffle();
      
      //DOubles blinds per round
      bigBlind = bigBlind * 4;
      smallBlind = bigBlind / 2;
      pot = 0;
      
      //Used to test for number of bankrupt people
      int numBankrupt = 0;
      for(int i = 0; i<4; i++) {
         if(players.get(i).getBankrupt())
            numBankrupt++;
         //Resets players hands and the community cards      
         players.get(i).setFold(false);
         communityCards = new ArrayList<Card>();
         players.get(i).setBet(0.0);
      }
      
      //Ends game quickly if three players go bankrupt
      if(numBankrupt == 3)
         winGame();
      
      initComponents();
   }
   //Initial round of betting that utilizes the blinds
   public void initialBet() {
      //First player pays big blind
      players.get(turn).setBet(bigBlind);
      pot += bigBlind;
      pause();
      turn++;
      //Resets turn value to zero if it goes over bounds
      if(turn > 3)
            turn = 0;
      //Other three players pay small blind
      for(int i = 0; i<3; i++) {
         players.get(turn).setBet(smallBlind);
         pot += smallBlind;
         initComponents();
         pause();
         turn++;
         //Resets turn value to zero if it goes over bounds
         if(turn > 3)
            turn = 0;
      }
      currentBet = smallBlind;
      
      //Draws two cards for each player
      for(int i = 0; i<4; i++) {
         players.get(i).addCards(Deck.drawCard(),Deck.drawCard());
      }
   }
   //Causes delay in the game
   public void pause() {
      try {
         Thread.sleep(2500);
      }
      catch(InterruptedException ex) {
          Thread.currentThread().interrupt();
      }
   }
   //Reveals proper number of cards
   public void reveal(int numCards) {
      //Draws cards to community pile and adds to each player's hand
      communityCards.addAll(Deck.drawCards(numCards));
      for(int i = 0; i<4; i++) {
         players.get(i).addCommCds(communityCards);
         players.get(i).setHand();
      }
      initComponents();  
   }
   //Commences the round of betting that requires each player to bet money
   public void betRound() {
      for(int i = 0; i<4; i++) {
         //If player hsnt folded or gone bankrupt, it requests bet
         if(!players.get(turn).getFold() && !players.get(turn).getBankrupt())
         {
            double temp = players.get(turn).requestBet(pot,currentBet,medValue);
            //Ensures that current bet isnt reduced
            if(temp > currentBet)
               currentBet = temp;
            //Adds to pot
            pot += temp;
         }
         //If player folds, increases CPU propensity to play
         else
            medValue -= 125;
         
         initComponents();        
         //Resets turn value if it exceeds number of players
         pause();
         turn++;
         if(turn > 3)
            turn = 0;
      }
      //Resets scores for further calculations
      for(int i = 0; i<4; i++) {
         players.get(i).getHand().reset();
      }
   }
   //Displays message for winner of round, then resets hands and turns
   public void winRound() {
      JOptionPane.showMessageDialog(this,new WinnerGraphics());
      turn++;
      for(int i = 0; i<4; i++) {
         players.get(i).resetHand();
      }
   }
   //Displays winner, then closes program
   public void winGame() {
      //Calculates winner
      double max = 0.0;
      int index = 0;
      for(int i = 0; i<4; i++) {
         if(players.get(i).getTotal() > max) {
            max = players.get(i).getTotal();
            index = i;
         }
      }
      
      //Displays winner
      JOptionPane.showMessageDialog(null,players.get(index).getFirstName() + " " +
         players.get(index).getLastName() + " is the winner with a total of " + 
         players.get(index).getTotal() + " dollars.","Winner",1);
      
      //Closes program
      this.setVisible(false);
      System.exit(0);
   }
   //Creates Components
   public void initComponents() {
      //Sets layout manager
      Container contentPane = this.getContentPane();
      contentPane.setLayout(new BorderLayout());
      
      //Sets up JPanels to occupy parts of the board      
      board = new JPanel();
      board.add(new TableGraphics());
      statBox = new JPanel();
      statBox.add(new StatBoxGraphics());
      notifBox = new JPanel();
      notifBox.add(new NotificationBoxGraphics());
            
      
      //Adds panels to different parts of the board                  
      contentPane.add(statBox,BorderLayout.LINE_START);
      contentPane.add(notifBox,BorderLayout.LINE_END);
      contentPane.add(board,BorderLayout.CENTER);
      
      //Sets close operation
      this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      this.pack();
   }
   //Draws cropped image of a card
   public void drawCard(Graphics g, int x, int y, int w, int l, int n, int t) {
      BufferedImage img = null;
      //Reads image from file
      try {
         img = ImageIO.read(new File("allCardsPic.gif"));
      }
      catch (IOException e) {
      }
      //Crops the image for a specific card
      img = scale(949,485,img);
      img = img.getSubimage(w*n,l*t,w,l);
      g.drawImage(img,x,y,this);
   }
   //Scales image to desired size
   public BufferedImage scale(int scaleWidth, int scaleHeight, BufferedImage img) {
      // creates output image
      BufferedImage outputImage = new BufferedImage(scaleWidth,
                scaleHeight, img.getType());
     
      // scales the input image to the output image
      Graphics g = outputImage.createGraphics();
      g.drawImage(img, 0, 0, scaleWidth, scaleHeight, null);
      g.dispose();
      return outputImage;
   }
   /**
      JPanel that contains the Table Graphics
   */
   class TableGraphics extends JComponent {
      TableGraphics() {
         setPreferredSize(new Dimension(1000,800));
      }
      //Draws graphics
      public void paintComponent(Graphics g) {
         super.paintComponent(g);
        
         //Draws round board
         g.drawOval(160,150,650,450);
         
         //Draws community Cards
         for(int i = 0; i<5; i++) {
            if(i<communityCards.size()) {
               drawCard(g,305+(73*i),325,73,97,communityCards.get(i).getNum(),
                        communityCards.get(i).getType());
            }
            else {
               drawCard(g,305+(73*i),325,73,97,0,4);
            }
         }
                
         //Draws four player bpxes
         drawPlayer(g,440,620,players.get(0));
         drawPlayer(g,440,30,players.get(2));
         drawPlayer(g,30,320,players.get(1));
         drawPlayer(g,830,320,players.get(3));
         
         //Draws box containing value in the pot
         g.drawRect(450,200,80,50);
         g.drawString("Pot:",475,220);
         g.drawString("" + Math.round(pot),475,240);
      }
      //Draws player box with name and total at a certain position
      public void drawPlayer(Graphics g, int x, int y, Player a) {
         g.drawRect(x,y,100,100);
         g.drawString(a.getFirstName(),x+10,y+30);
         g.drawString(a.getLastName(),x+10,y+50);
         g.drawString("" + a.getTotal(),x+10,y+70);
      }
   }
   /**
      Contains the graphics for the JPanel stat box on the left of the JFrame
   */
   class StatBoxGraphics extends JComponent {
      StatBoxGraphics() {
         setPreferredSize(new Dimension(140,800));
      }
      //Draws the graphics for the stat box on the left of the frame
      public void paintComponent(Graphics g) {
         int y = 10;
         for(int i = 0; i<4; i++) {
            drawStatBox(g,5,y,players.get(i));
            y+=180;
         }
      }
      //Draws individual stat box for player with name, total, and bet
      public void drawStatBox(Graphics g, int x, int y, Player a) {
         g.drawRect(x,y,120,180);
         //Draws player name and stats
         g.drawString(a.getFirstName(),x+10,y+20);
         g.drawString(a.getLastName(),x+10,y+40);
         g.drawString("---------",x+10,y+60);
         g.drawString("Total: " + a.getTotal(),x+10,y+80);
         g.drawString("Bet: " + a.getBet(),x+10,y+100);
      }
   }
   /**
      Contains the graphics for the JPanel Notification box on the right 
      of the JFrame.
   */
   class NotificationBoxGraphics extends JComponent {
      NotificationBoxGraphics() {
         setPreferredSize(new Dimension(120,800));
      }
      //Draws the graphics for the stat box on the left of the frame
      public void paintComponent(Graphics g) {
         //Checks and displays user's cards
         if(players.get(0).getHand() != null){
            g.drawRect(0,145,95,250);
            g.drawString("Your Cards:",15,165);
            drawCard(g,10,175,73,97,players.get(0).getHand().getCards().get(0).getNum(),
                        players.get(0).getHand().getCards().get(0).getType());
            drawCard(g,10,275,73,97,players.get(0).getHand().getCards().get(1).getNum(),
                        players.get(0).getHand().getCards().get(1).getType());
         }
         
         //Draws notification box that updates when each player bets
         g.drawRect(0,25,95,100);
         String name = players.get(turn).getLastName();
         
         //Checks if player folded or is bankrupt
         if(!players.get(turn).getFold() && !players.get(turn).getBankrupt()) {
            String bet = "bet";
            String value = "" + players.get(turn).getBet();
            String dollars = "dollars";
            g.drawString(name,10,50);
            g.drawString(bet,10,65);
            g.drawString(value,10,80);
            g.drawString(dollars,10,95);
         }
         //Prints messages if folded or bankrupt
         else if(players.get(turn).getBankrupt()) {
            g.drawString(name,10,50);
            g.drawString("is BANKRUPT",10,65);
         }
         else if(players.get(turn).getFold()) {
            g.drawString(name,10,50);
            g.drawString("FOLDED",10,65);
         }
      }
   }
   /**
      Contains the graphics for the JPanel that displays the winner of each round
   */
   class WinnerGraphics extends JComponent {
      WinnerGraphics() {
         setPreferredSize(new Dimension(1000,650));
      }
      //Draws the graphics for the stat box on the left of the frame
      public void paintComponent(Graphics g) {
         double maxVal = 0.0;
         int index = 0;
         Player winner = null;
         
         //Determines winner
         for(int i = 0; i<4; i++) {
            if(!players.get(i).getFold() && !players.get(i).getBankrupt()) {
               double temp = players.get(i).getHand().returnValue();
               if(temp > maxVal) {
                  maxVal = temp;
                  index = i;
                  winner = players.get(i);
               }
            }
         }
         
         //Displays winner
         if(winner != null) {
            g.drawString("The winner of this round is: " + players.get(index).getFirstName()
                         + " " + players.get(index).getLastName(),0,80);
         }
         else
            g.drawString("No one won the round.",50,80);
            
         //Adds pot money to winner
         players.get(index).add(pot);
         pot = 0;
         
         //Calculates overall winner and displays
         double maxTotal = 0;
         int indexTotal = 0;
         for(int i = 0; i<4; i++) {
            if(players.get(i).getTotal() > maxTotal) {
               maxTotal = players.get(i).getTotal();
               indexTotal = i;
            }
            if(players.get(i).getTotal() <= 0) {
               players.get(i).setBankrupt(true);
            }
         }
         g.drawString("The overall leader is: " + players.get(indexTotal).getFirstName()
             + " " + players.get(indexTotal).getLastName() + " with " +
             players.get(indexTotal).getTotal(),400,80);
         
         //Displays each player's overall hand
         for(int i = 0; i<4; i++) {
            int y = 125 + (125 * i);
            g.drawRect(180, y, 650, 125);
            g.drawString(players.get(i).getLastName() + "'s full hand:", 20, 200+(125 * i));
            ArrayList<Card> tempCds = players.get(i).getHand().getAllCards();
            for(int j = 0; j<tempCds.size(); j++) {
               drawCard(g,200+(80*j),y+20,73,97,tempCds.get(j).getNum(),tempCds.get(j).getType());
            }
         }
      }
   }
}
/**
   This class has a collection of deckCards that players draw from
   while the poker game is being played.
*/
class Deck
{
   private static ArrayList<Card> deckCards = new ArrayList<Card>();
   //Creates new set of 52 deckCards
   public static void setDeck() {
      for(int i = 0; i<13; i++) {
         for(int j = 0; j<4; j++) {
            deckCards.add(new Card(i,j));
         }
      }     
      shuffle();
   }
   //Returns size of deck
   public static int getNumCards(){
      return deckCards.size();
   }
   //Shuffles deckCards in the deck
   public static void shuffle() {
      ArrayList<Card> temp = new ArrayList<Card>();
      for(int i = 0; i<deckCards.size(); i+=0) {
         int tempNum = (int)(Math.random()*deckCards.size());
         temp.add(deckCards.get(tempNum));
         deckCards.remove(tempNum);
      }
      deckCards = temp;
   }
   //Removes deckCards from the deck and returns them
   public static ArrayList<Card> drawCards(int numCards) {
      ArrayList<Card> temp = new ArrayList<Card>();
      for(int i = 0; i<numCards; i++) {
         int tempNum = (int)(Math.random()*deckCards.size());
         temp.add(deckCards.get(tempNum));
         deckCards.remove(tempNum);
      }
      return temp;
   }
   //Removes one card from the deck and returns it
   public static Card drawCard () {
      int tempNum = (int)(Math.random()*deckCards.size());
      Card cd = deckCards.get(tempNum);
      deckCards.remove(tempNum);
      return cd;
   }
   //Returns ArrayList of deckCards
   public static ArrayList<Card> getCards() {
      return deckCards;
   }
}

/**
   This class has all the attributes of an individual player,
   such as his or her first and last name, money total, and hand.
*/
abstract class Player
{
   public double total;
   public double bet;
   private String firstN;
   private String lastN;
   private Hand hand;
   private Card c1;
   private Card c2;
   private ArrayList<Card> cds;
   private boolean isBankrupt = false;
   private boolean isFolded = false;
   //Sets player name and original bank balance
   public Player(String f, String l) {
      firstN = f;
      lastN = l;
      total = 10000;
   }
   //Adds player's two main cards
   public void addCards(Card a, Card b) {
      c1 = a;
      c2 = b;
   }
   //Adds community cards
   public void addCommCds(ArrayList<Card> inp) {
      cds = new ArrayList<Card>();
      for(int i = 0; i<inp.size(); i++) {
         cds.add(inp.get(i));
      }
   }
   //Creates new hand with two deckCards and communityCards
   public void setHand() {
      hand = new Hand(c1,c2,cds);
   }
   //Sets bet value for player
   public void setBet(double inp) {
      if(inp >= total) {
         bet = total;
         total = 0;
      }
      else {
         bet = inp;
         total -= inp;
      }
   }
   //Gets string representation of bet
   public String getStringBet() {
      return (this.getLastName() + " bets " + this.getBet());
   }
   //Gets bet value for player
   public double getBet() {
      return Math.round(bet);
   }
   //Returns player's hand
   public Hand getHand() {
      return hand;
   }
   //Resets player's hand
   public void resetHand() {
      hand = null;
   }
   //Adds money to bank balance
   public void add(double inp) {
      total = total + inp;
   }
   //Gets total of player bank
   public double getTotal() {
      return Math.round(total);
   }
   //Returns first name of player
   public String getFirstName() {
      return "" + firstN;
   }
   //Returns last name of player
   public String getLastName() {
      return "" + lastN;
   }
   //Changes state of player's hand if he or she folds
   public void setFold(boolean inp) {
      isFolded = inp;
   }
   //Returns state of player's hand
   public boolean getFold() {
      return isFolded;
   }
   //Changes state of player's hand if he or she is bankrupt 
   public void setBankrupt(boolean inp) {
      isBankrupt = inp;
   }
   //Returns stats of player
   public boolean getBankrupt() {
      return isBankrupt;
   }
   //Links to the subclass requestBet() method
   public double requestBet(double pot,double currentBet, double medVal) {
      return this.requestBet(pot,currentBet,medVal);
   }
}
/**
   A subclass of the player class. Is controlled by a user in terms
   of the bets placed.
*/
class User extends Player {
   //Sets name
   public User(String f, String l) {
      super(f,l);
   }
   //Prompts user for bet value
   public double requestBet(double pot, double currentBet, double medValue){      
      //Sets message box for user to set bet value
      String inp = JOptionPane.showInputDialog(null,"The current bet"
                + " is: " + Math.round(currentBet) + "\nThe pot contains: " +
                Math.round(pot) + "" + "\nEnter '-' if you wish to fold.",1);
      if(inp.equals("-")) {
         this.setFold(true);
         return 0;
      }
      double tempBet = (double)(Integer.parseInt(inp));      
      //Checks if bet is valid and plays
      if(tempBet < currentBet) {
         JOptionPane.showMessageDialog(null,"Please enter a valid bet.",
                                       "Invalid Bet", 1);
         tempBet = requestBet(pot,currentBet,medValue);
      }
      setBet(tempBet);  
      return this.getBet();  
   }
}
/**
   A subclass of the player class. Is controlled by the computer in terms
   of the bets placed.
*/
class CPU extends Player {
   private double skill;
   //Sets name by calling superclass and sets class-specific skill value
   public CPU(String f, String l, Double sk) {
      super(f,l);
      skill = sk;
   }
   //Returns skill of player
   public Double getSkill() {
      return skill;
   }
   //Sets bet based on algorithm
   public double requestBet(double pot, double currentBet, double medValue) {
      //Calculates estimated hand value
      double handValue = this.getHand().returnValue();
      //Recalculates using human skill error
      double estValue = handValue + ((Math.random()*(600) - 300) / getSkill());      
      
      //Compares to median value and then plays if higher
      if(estValue > medValue) {
         double tempBet = (1+((estValue - medValue)/1000)) * currentBet;
         if(tempBet < currentBet) {
            tempBet = requestBet(pot,currentBet,medValue);
         }
         setBet(tempBet);
         return this.getBet();
      }
      //Folds if lower
      else {
         this.setFold(true);
         return 0;
      }
   }
}
/**
   This class has two deckCards passed in from the player, and it combines
   these and the community deckCards to form an overall hand. Then it seeks
   out the optimal combination to render the player the highest number
   of points for his or her card. The value it returns can be compared
   against other hands.
*/
class Hand {
   private Card c1;
   private Card c2;
   private double score;
   private int [] nums;
   private ArrayList<Card> community = new ArrayList<Card>();
   private boolean scoreNotSet = true;
   //Appends two player deckCards to community deckCards
   public Hand(Card one, Card two, ArrayList<Card> inp) {
      for(int i = 0; i<inp.size(); i++) {
         community.add(inp.get(i));
      }
      c1 = one;
      c2 = two;
      community.add(c1);
      community.add(c2);
      community = sort();
   }
   //Returns the players two deckCards
   public ArrayList<Card> getCards() {
      ArrayList<Card> cards = new ArrayList<Card>();
      cards.add(c1);
      cards.add(c2);
      return cards;
   }
   public ArrayList<Card> getAllCards() {
      return community;
   }
   //Resets hand for further calculations
   public void reset() {
      score = 0;
      scoreNotSet = true;
   }
   //SOrts deckCards by their numbers
   public ArrayList<Card> sort() {
      Collections.sort(community);
      return community;
   }
   //Returns score of hand
   public double returnValue() {
      score = 0;
      //If score is set, algorithm doesnt check for other combinations
      if(scoreNotSet)
         straightFlush();
      if(scoreNotSet)
         fourKind();
      if(scoreNotSet)
         fullHouse();
      if(scoreNotSet)
         flush();
      if(scoreNotSet)
         straight();
      if(scoreNotSet)
         threeKind();
      if(scoreNotSet)
         twoPair();
      if(scoreNotSet)
         onePair();
      scoreNotSet = false;
      highCard();
      scoreNotSet = true;
      return score;
   }     
   //Checks if hand is a straight
   public void straightFlush() {
      for(int i = 0; i<community.size()-4; i++) {
         int num = community.get(i).getNum();
         //Checks for straight
         if(community.get(i+1).getNum() == num-1 && community.get(i+2).getNum() == num-2
            && community.get(i+3).getNum() == num-3 && community.get(i+4).getNum() == num-4) {
            //Checks for flush
            if(community.get(i+1).getType() == community.get(i+2).getType() 
               && community.get(i+3).getType() == community.get(i+4).getType()
               && community.get(i+1).getType() == community.get(i+4).getType())
               score = 900;
               nums = new int[] {num,num-1,num-2,num-3,num-4};
               scoreNotSet = false;
         }
      }
   }
   //Checks for four of a kind
   public void fourKind() {
      for(int i = 0; i<community.size()-3; i++) {
         int num = community.get(i).getNum();
         //Checks if four cards have similar number
         if(community.get(i+1).getNum() == num && community.get(i+2).getNum() == num
            && community.get(i+3).getNum() == num) {
            score = 800;
            nums = new int [] {num,num,num,num,num};
            scoreNotSet = false;
         }
      }
   }
   //Checks for full house
   public void fullHouse() {
      boolean three = false;
      int num = 0;
      //Checks for three of a kind
      for(int i = 0; i<community.size()-2; i++) {
         num = community.get(i).getNum();
         if(community.get(i+1).getNum() == num && community.get(i+2).getNum() == num) {
            three = true;
         }
      }
      //Checks for pair
      boolean two = false;
      int secNum = 0;
      for(int j = 0; j<community.size()-1; j++) {
         secNum = community.get(j).getNum();
         if((community.get(j+1).getNum() == secNum) && (secNum != num)) {
            two = true;
            break;
         }
      }
      //Returns true if there is a three of kind and a pair
      if(two && three) {
         score = 700;
         nums = new int[] {num,num,num,secNum,secNum};
         scoreNotSet = false;
      }
   }
   //Checks for flush
   public void flush() {
      int cardNum = 0;
      int [] tempNums = new int[5];
      //Checks through each type
      for(int i = 0; i<4; i++) {
         int numCds = 0;
         for(int j = 0; j<community.size(); j++) {
            if(community.get(j).getType() == i) {
               tempNums[numCds] = community.get(j).getNum();
               numCds++;
            }
            if(numCds >= 5) {
               score = 600;
               nums = tempNums;
               scoreNotSet = false;
               break;
            }
         }
      }
   }
   //Checks for straight
   public void straight() {
      for(int i = 0; i<community.size()-4; i++) {
         int num = community.get(i).getNum();
         if(community.get(i+1).getNum() == num-1 && community.get(i+2).getNum() == num-2
            && community.get(i+3).getNum() == num-3 && community.get(i+4).getNum() == num-4) {
            score = 500;
            nums = new int[] {num,num-1,num-2,num-3,num-4};
            scoreNotSet = false;
            break;
         }
      }
   }
   //Checks for three of a kind
   public void threeKind() {
      for(int i = 0; i<community.size()-2; i++) {
         int num = community.get(i).getNum();
         if(community.get(i+1).getNum() == num && community.get(i+2).getNum() == num) {
            score = 400;
            nums = new int [] {num,num,num};
            scoreNotSet = false;
         }
      }
   }
   //Checks for two pair
   public void twoPair() {
      //Checks for first pair
      for(int i = 0; i<community.size()-3; i++) {
         int num = community.get(i).getNum();
         if(community.get(i+1).getNum() == num) {
            //Checks for second pair
            for(int j = i+2; j<community.size()-1; j++) {
               int secNum = community.get(j).getNum();
               if(community.get(j+1).getNum() == secNum) {
                  score = 300;
                  nums = new int[] {num,num,secNum,secNum};
                  scoreNotSet = false;
               }
            }
         }
      }
   }
   //Checks for one pair
   public void onePair() {
      for(int i = 0; i<community.size()-1; i++) {
         int num = community.get(i).getNum();
         if(community.get(i+1).getNum() == num) {
            score = 200;
            nums = new int [] {num,num};
            scoreNotSet = false;
         }
      }
   }
   //Calculates high card value
   public void highCard() {
      if(nums == null) {
         nums = new int[5];
         for(int i = 0; i<5; i++) {
            nums[i] = community.get(i).getNum();
         }
      }
      //Reduces weight of each number as one progresses
      //through digits
      for(int i = 0; i<nums.length; i++) {
         if(nums[i] == 1)
            nums[i] = 14;
         score += (nums[i] * Math.pow(10,-i));
      }
   }
}

/**
   This class is essentially a card, and it has a number value
   and a type value assigned to it. It can be compared to other
   deckCards.
*/
class Card implements Comparable
{
   private int num;
   private int type;
   private BufferedImage img = null;
   //Sets calues for number and suit
   public Card(int n, int t) {
      num = n;
      type = t;
   }
   //Gets number of card
   public int getNum() {
      return num;
   }
   //Gets type of card, i.e. jack,heart,spade,diamond
   public int getType() {
      return type;
   }
   //Compares objects by their number values
   public int compareTo(Object inp) {
      Card c2 = (Card)(inp);
      if(this.getNum() > c2.getNum())
         return -1;
      else if(this.getNum() < c2.getNum())
         return 1;
      else
         return 0;
   }
}
