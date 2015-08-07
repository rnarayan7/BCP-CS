//Roshan Narayan
//Final Project - Basketball ScoreKeeper

import java.util.ArrayList;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;

/**
   This class contains the main runner for the ScoreKeeping Program.
   It extends JFrame and holds all the necessary components in it
   for the generic interface.
*/
public class ScoreKeeper_Narayan extends JFrame implements ActionListener, ListSelectionListener {
   @SuppressWarnings("unchecked")
   //Instance variables
   JPanel inputHome;
   JPanel inputGuest;
   JPanel main;
   JPanel scoreDisp;
   JLabel namHome;
   JLabel namGuest;
   JTextField scoreHome;
   JTextField scoreGuest;
   JList homeScoreDisp;
   JList guestScoreDisp;
   JButton homeReset;
   JButton guestReset;
   JButton score1H;
   JButton score2H;
   JButton score3H;
   JButton score1G;
   JButton score2G;
   JButton score3G;
   JLabel homeGenText;
   JLabel guestGenText;
   JList homeMainText;
   JList guestMainText;
   Team home;
   Team guest;
   
   //Creates instance of class from within main and calls runner
   public static void main(String[]args) {
      ScoreKeeper_Narayan game = new ScoreKeeper_Narayan();
      game.runner();
   }
   
   //Creates inputFrame which will eventually call method that
   //links back to this code
   public void runner () {
      InputFrame choose = new InputFrame();
      choose.runner(this);
   }
   
   //Initializes all components of the GUI
   public void initComponents(String hInp, String gInp) {
      //Creates teams from input from inputFrame
      home = new Team(hInp);
      guest = new Team(gInp);
      
      //Creates generic layout for entire JFrame
      Container contentPane = this.getContentPane();
      contentPane.setLayout(new BorderLayout());
      
      //Calls specific methods for three main parts of GUI
      setHomeInfo();
      setGuestInfo();
      setChart();  
      setBottomScore();   

      //Basic set up for main GUI
      this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      this.pack();
      this.setResizable(false);
      this.setVisible(true);
   } 
      
   //This method creates the main info for the home team in the GUI  
   public void setHomeInfo() {
      //Creates inputHome JPanel on the left
      inputHome = new JPanel();
      inputHome.setLayout(new BoxLayout(inputHome,BoxLayout.Y_AXIS));
      
      //Text for the name of the team
      homeGenText = new JLabel(home.getName());
      //Sets selected player name
      namHome = new JLabel("Player");
      //Adds to JPanel
      inputHome.add(homeGenText);
      inputHome.add(namHome);
      
      //Creates buttons that increment scores for the players
      //Button to add one point
      score1H = new JButton("+1");
      score1H.setActionCommand("score1H");
      score1H.addActionListener(this);
      //Button to add two points
      score2H = new JButton("+2");
      score2H.setActionCommand("score2H");
      score2H.addActionListener(this);
      //Button to add three points
      score3H = new JButton("+3");
      score3H.setActionCommand("score3H");
      score3H.addActionListener(this);
      //Button that allows one to reset score of player to any value
      homeReset = new JButton("Reset Score");
      homeReset.setActionCommand("reset home");
      homeReset.addActionListener(this);
      //Creates text field to input score to reset
      scoreHome = new JTextField("");
      
      //Adds all buttons and text fields to the JPanel
      inputHome.add(score1H);
      inputHome.add(score2H);
      inputHome.add(score3H);
      inputHome.add(scoreHome);
      inputHome.add(homeReset);
      
      //Sets preferred size
      inputHome.setPreferredSize(new Dimension(150,100));
      //Adds to JFrame
      this.add(inputHome,BorderLayout.WEST);
   }
   
   //Does the same to the guest team for the right side of the GUI
   public void setGuestInfo() {
      //Creates inputHome JPanel on the left
      inputGuest = new JPanel();
      inputGuest.setLayout(new BoxLayout(inputGuest,BoxLayout.Y_AXIS));
     
      //Text for the name of the team
      guestGenText = new JLabel(guest.getName());
      //Sets selected player name
      namGuest = new JLabel("Player");
      //Adds to JPanel
      inputGuest.add(guestGenText);
      inputGuest.add(namGuest);
      
      //Creates buttons that increment scores for the players
      //Button to add one point
      score1G = new JButton("+1");
      score1G.setActionCommand("score1G");
      score1G.addActionListener(this);
      //Button to add two points
      score2G = new JButton("+2");
      score2G.setActionCommand("score2G");
      score2G.addActionListener(this);
      //Button to add three points
      score3G = new JButton("+3");
      score3G.setActionCommand("score3G");
      score3G.addActionListener(this);
      //Button that allows one to reset score of player to any value
      guestReset = new JButton("Reset Score");
      guestReset.setActionCommand("reset guest");
      guestReset.addActionListener(this);
      //Creates text field to input score to reset
      scoreGuest = new JTextField("");
      
      //Adds all buttons and text fields to the JPanel
      inputGuest.add(score1G);
      inputGuest.add(score2G);
      inputGuest.add(score3G);
      inputGuest.add(scoreGuest);
      inputGuest.add(guestReset);
      
      //Sets preferred size
      inputGuest.setPreferredSize(new Dimension(150,100));
      //Adds to JFrame
      this.add(inputGuest,BorderLayout.EAST);
   }
   
   //Creates graphics and info for main chart that displays players
   public void setChart() {
      //Creates basic JPanel
      main = new JPanel(new FlowLayout());
      
      //Creates JList with home player names
      homeMainText = new JList(home.getRoster().toArray());
      homeMainText.addListSelectionListener(this);
      //Allows JList to be scrollable
      JScrollPane scrollHome = new JScrollPane(homeMainText,JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
      scrollHome.setPreferredSize(new Dimension(400,200));
      //Adds to main JPanel
      main.add(scrollHome);

      //Creates JList with guest player names
      guestMainText = new JList(guest.getRoster().toArray());
      guestMainText.addListSelectionListener(this);
      //Allows JList to be scrollable
      JScrollPane scrollGuest = new JScrollPane(guestMainText,JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
      scrollGuest.setPreferredSize(new Dimension(400,200));
      //Adds to main JPanel
      main.add(scrollGuest); 
      
      //Adds main JPanel to JFrame
      this.add(main,BorderLayout.CENTER);
   }
   
   //Listener method called when different player is selected in list
   public void valueChanged(ListSelectionEvent e) {
      //Resets home player name
      if(homeMainText.getSelectedValue() != null)
         namHome.setText(((Player)homeMainText.getSelectedValue()).getName());
      //Resets away player name
      if(guestMainText.getSelectedValue() != null)
         namGuest.setText(((Player)guestMainText.getSelectedValue()).getName());
   }
   
   //Sets score displays at bottom of GUI
   public void setBottomScore() {
      //Creates container panel
      scoreDisp = new JPanel(new FlowLayout());
      
      //Creates home score display
      Team [] hm = {home};
      homeScoreDisp = new JList(hm);
      
      //Cretaes guest score display
      Team [] gs = {guest};
      guestScoreDisp = new JList(gs);
      
      //Sets sizes for score lists
      homeScoreDisp.setPreferredSize(new Dimension(400,20));
      guestScoreDisp.setPreferredSize(new Dimension(400,20));
      
      //Adds to JPanel
      scoreDisp.add(homeScoreDisp);
      scoreDisp.add(guestScoreDisp);
      
      //Adds JPanel to JFrame
      this.add(scoreDisp,BorderLayout.SOUTH);
   }
   
   //Refreshes graphics after each score is changed
   public void refreshChart() {
      //Obtains graphics from both lists
      Graphics chartH = homeMainText.getGraphics();
      Graphics chartG = guestMainText.getGraphics();
      //Obtains graphics from score displays
      Graphics scoreH = homeScoreDisp.getGraphics();
      Graphics scoreG = guestScoreDisp.getGraphics();
      
      //Repaints them
      homeMainText.paint(chartH);
      guestMainText.paint(chartG);
      homeScoreDisp.paint(scoreH);
      guestScoreDisp.paint(scoreG);
      
   }
   
   //Defines actions when each button is selected
   public void actionPerformed(ActionEvent e) {
      //Adds one point to selected player on home team
      if(e.getActionCommand().equals("score1H")) {
         if(home.hasPlayer(namHome.getText()))
               home.teamScored(namHome.getText(), 1);  
      }
      //Adds two points to selected player on home team
      else if(e.getActionCommand().equals("score2H")) {
         if(home.hasPlayer(namHome.getText()))
               home.teamScored(namHome.getText(), 2);  
      }
      //Adds three points to selected player on home team
      else if(e.getActionCommand().equals("score3H")) {
         if(home.hasPlayer(namHome.getText()))
               home.teamScored(namHome.getText(), 3);  
      }
      //Adds one point to selected player on guest team
      else if(e.getActionCommand().equals("score1G")) {
         if(guest.hasPlayer(namGuest.getText()))
               guest.teamScored(namGuest.getText(), 1);  
      }
      //Adds two points to selected player on guest team
      else if(e.getActionCommand().equals("score2G")) {
         if(guest.hasPlayer(namGuest.getText()))
               guest.teamScored(namGuest.getText(), 2);  
      }
      //Adds three points to selected player on guest team
      else if(e.getActionCommand().equals("score3G")) {
         if(guest.hasPlayer(namGuest.getText()))
               guest.teamScored(namGuest.getText(), 3);  
      }
      //Allows one to reset score of home team player based
      //on text in the reset score text box
      else if(e.getActionCommand().equals("reset home")) {
         if(home.hasPlayer(namHome.getText()))
            home.resetScore(namHome.getText(), Integer.parseInt(scoreHome.getText()));  
      }
      //Allows one to reset score of home team player based
      //on text in the reset score text box
      else if(e.getActionCommand().equals("reset guest")) {
         if(guest.hasPlayer(namGuest.getText()))
            guest.resetScore(namGuest.getText(), Integer.parseInt(scoreGuest.getText()));
      }
      
      //Refreshes graphics of main chart with player names
      refreshChart();
   }
}

/**
   This class creates the inputFrame box at the beginning of the program
   that allows the user to choose his or her home team and guest team.
*/
class InputFrame extends JFrame implements ActionListener {
   //Instance variables
   ScoreKeeper_Narayan main;
   JList homeList;
   JList guestList;
   String hNam;
   String gNam;
   //Main runner for the inputFrame that creates most of the main components
   public void runner(ScoreKeeper_Narayan input) {
      //Sets instance variable for the main JFrame to the input
      main = input;
      
      //Sets border layout for the inputFrame
      Container contentPane = this.getContentPane();
      contentPane.setLayout(new BorderLayout());
      
      //Creates left and right JPanels
      JPanel leftPanel = new JPanel();
      JPanel rightPanel = new JPanel();
      
      //List of teams to choose from
      String [] teams = {"Warriors","Cavaliers","Rockets","Hawks"};
      
      //Creates text telling to choose team
      JTextArea homeChoose = new JTextArea();
      homeChoose.append("Home Team: ");
      //Creates JList of teams to choose from
      homeList = new JList(teams);
      //Adds to left panel
      leftPanel.add(homeChoose);
      leftPanel.add(homeList);
      
      //Creates text telling to choose team
      JTextArea guestChoose = new JTextArea();
      guestChoose.append("Guest Team: ");
      //Creates JList of teams to choose from
      guestList = new JList(teams);
      //Adds to right panel
      rightPanel.add(guestChoose);
      rightPanel.add(guestList);
      
      //Creates finished button to submit both choices once selected
      JButton finished = new JButton("Choose");
      finished.setActionCommand("choose");
      finished.addActionListener(this);
      
      //Adds right and left panels and button
      this.add(rightPanel,BorderLayout.EAST);
      this.add(leftPanel,BorderLayout.WEST);
      this.add(finished,BorderLayout.SOUTH);
      this.pack();
      //Sets to visible
      this.setVisible(true);
   }
   
   //Returns choices for teams once finished button is selected
   public void actionPerformed(ActionEvent e) {
      if(e.getActionCommand().equals("choose")) {
         hNam = (String)homeList.getSelectedValue();
         gNam = (String)guestList.getSelectedValue();
         //Checks that both two choices are not identical
         if(hNam.equals(gNam) != true) {
            this.setVisible(false);
            //Sends values back to main GUI and calls initComponents
            //to continue the code process
            main.initComponents(hNam,gNam);
         }
      }
   }
}

/**
   This class contains the main information for each team.
   It contains an ArrayList of players as well as information
   of the overall team's score.
*/
class Team {
   //Instance variables
   private String name;
   private ArrayList<Player> roster;
   private int totalScore;
   
   //Constructor that creates team based off of name
   public Team(String teamName) {
      name = teamName;
      //Calls database method based on team name
      if(teamName.equals("Warriors"))
         roster = Database.warriors();
      else if(teamName.equals("Cavaliers"))
         roster = Database.cavaliers();
      else if(teamName.equals("Hawks"))
         roster = Database.hawks();
      else if(teamName.equals("Rockets"))
         roster = Database.rockets();
   }
   
   //Returns name of team
   public String getName() {
      return name;
   }
   
   //Returns ArrayList roster of players
   public ArrayList<Player> getRoster() {
      return roster;
   }
   
   //Returns size of team
   public int getNumPlayers() {
      return roster.size();
   }
   
   //Adds player to the team
   public void addPlayer(String inp) {
      roster.add(new Player(inp,name));
   }
   
   //Adds points to player
   public void teamScored(String player, int points) {
      for(int i = 0; i < roster.size(); i++) {
         if(player.equals(roster.get(i).getName()))
            roster.get(i).playerScored(points);
      }
   }
   
   //Resets score of player
   public void resetScore(String player, int points) {
      for(int i = 0; i < roster.size(); i++) {
         if(player.equals(roster.get(i).getName()))
            roster.get(i).resetScore(points);
      }
   }
   
   //Obtains overall score by adding up players scores
   public int getScore() {
      int score = 0;
      for(int i = 0; i < roster.size(); i++)
         score += roster.get(i).getPoints();
      return score;
   }
   
   //Checks if roster has player
   public boolean hasPlayer(String name) {
      boolean have = false;
      for(int i = 0; i < roster.size(); i++) {
         if(name.equals(roster.get(i).getName()))
            have = true;
      }
      return have;
   }
   
   //Returns totalScore in form of a string
   public String toString() {
      return name + ": " + getScore();
   }
}

/**
   This class contains the information for a player, including
   his name, team, and score.
*/
class Player {
   //Instance variables
   private int totalPoints;
   private String name;
   private String team;
   
   //Constructor that sets team name and player name
   public Player(String playerName, String tm) {
      team = tm;
      name = playerName;
   }
   
   //Adds points to player score total when he scores
   public void playerScored(int points) {
      totalPoints += points;
   }
   
   //Resets player score
   public void resetScore(int points) {
      totalPoints = points;
   }
   
   //Returns player score
   public int getPoints() {
      return totalPoints;
   }
   
   //Returns player name
   public String getName() {
      return name;
   }
   
   //toString representation of player for debugging use
   public String toString() {
      return "" + totalPoints + " - " + name;
   }
}

/**
   This class contains a collection of player names for each team
   of the four in the Conference Finals of the NBA Playoffs
*/
class Database {
   //Creates players for the Cleveland Cavaliers team 
   public static ArrayList<Player> cavaliers () {
      ArrayList<Player> roster = new ArrayList<Player>();
      //Players on Cavaliers
      String [] names = {"Lebron James","Kyrie Irving", "Kevin Love","J.R. Smith",
                         "Timofey Mozgov","Iman Shumpert","James Jones","Mike Miller",
                         "Kendrick Perkins","Matthew Dellavedova","Tristan Thompson",
                         "Shawn Marion", "Jow Harris"};
      //Creates player objects and adds to roster
      for(int i = 0; i<names.length; i++)
         roster.add(new Player(names[i],"cavaliers"));
      return roster;
   }
   
   //Creates players for the Golden State Warriors team 
   public static ArrayList<Player> warriors () {
      ArrayList<Player> roster = new ArrayList<Player>();
      //Players on Warriors
      String [] names = {"Stephen Curry","Klay Thompson","Harrison Barnes","Draymond Green",
                         "Andrew Bogut","Shaun Livingston","Leandro Barbosa","Justin Holliday",
                         "Brandon Rush","Andre Iguodala","Mareese Speights","James Michael McAddoo",
                         "Festus Ezeli","David Lee"};
      //Creates player objects and adds to roster
      for(int i = 0; i<names.length; i++)
         roster.add(new Player(names[i],"warriors"));
      return roster;
   }
   
   //Creates players for the Houston Rockets team 
   public static ArrayList<Player> rockets () {
      ArrayList<Player> roster = new ArrayList<Player>();
      //Players on Rockets
      String [] names = {"James Harden","Dwight Howard","Josh Smith","Pablo Prigioni",
                         "Jason Terry","Corey Brewer","Terrence Jones", "Joey Dorsey",
                         "Trevor Ariza","Nick Johnson","Kostas Papanikolaou","Clint Capela"};
      //Creates player objects and adds to roster
      for(int i = 0; i<names.length; i++)
         roster.add(new Player(names[i],"rockets"));
      return roster;
   }
   
   //Creates players for the Atlanta Hawks team 
   public static ArrayList<Player> hawks () {
      ArrayList<Player> roster = new ArrayList<Player>();
      //Players on Hawks
      String [] names = {"Jeff Teague","Paul Millsap","Al Horford","Kent Bazemore","Kyle Korver",
                         "DeMarre Carroll","Pero Antic","Thabo Sefolosha","Dennis Schroeder",
                         "Mike Scott","Shelvin Mack","Elton Brand","Mike Muscala"};
      //Creates player objects and adds to roster
      for(int i = 0; i<names.length; i++)
         roster.add(new Player(names[i],"hawks"));
      return roster;
   }
}
