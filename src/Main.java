

import java.util.ArrayList;
import java.util.Queue;
import java.util.Scanner;

public class Main {
	public static void main(String[] args) {
		Player player1 = new Player();
		Player player2 = new Player();
		
		while(player1.getScore()<100 && player2.getScore()<100) {
			boolean knockValid = false;
			Card discard = null;
			int whoesTurn = 2;
			Player currPlayer = null;
			Player knockPlayer = null;
			int knockScore = 0;
			Deck deck = new Deck();
			Queue<Card> shuffled = deck.shuffle();
			for(int i =0;i<10;i++) {
				player1.addCard(shuffled.remove());
				player2.addCard(shuffled.remove());
			}
			player1.addCard(shuffled.remove());
			System.out.println("Player 1 hand: " + player1.hand);
			Scanner scan = new Scanner(System.in);
			System.out.println("Player 1 choose a card to discard");
			Command com = new Command(scan.nextLine());
			discard = player1.remove(com.getValue().toString(), com.getSuit().toString());
			while(discard == null) {
				System.out.println("card not found. Try again");
				com = new Command(scan.nextLine());
				discard = player1.remove(com.getValue().toString(), com.getSuit().toString());
			}
			System.out.println(player1.hand);
			while(whoesTurn > 0) {
				String playerTxt = "";
				if(whoesTurn%2 == 1) {
					currPlayer = player1;
					playerTxt = "Player 1";
				}
				if(whoesTurn%2 == 0) {
					currPlayer = player2;
					playerTxt = "Player 2";
				}
				System.out.println(playerTxt + "'s Turn");
				System.out.println(playerTxt +": " + currPlayer.hand + " Sets: " + currPlayer.curSets + " Runs: " + currPlayer.curRuns);
				System.out.println("Would you like to pick up the discard card, " + discard + "? Type 'no' to draw from deck.");
				ArrayList<String> yesNoArray = new ArrayList<>();
				yesNoArray.add("yes"); yesNoArray.add("no");
				String yesno = userInput(scan.nextLine(), yesNoArray);
				if (yesno.equals("yes")) {
					currPlayer.addCard(discard);
				} else {
					currPlayer.draw(shuffled);
				}
				
				System.out.println(playerTxt +": " + currPlayer.hand + " Sets: " + currPlayer.curSets + " Runs: " + currPlayer.curRuns);
				Command command;
				do {
					System.out.print("What move do you wish to make? ");
					command = new Command(scan.nextLine(), 0);
					if (command.getVerb().equals(Verb.UNKNOWN)) {
						System.out.println("Invalid Command. For list of commands, type 'help'.");
					} else {
						if (command.getVerb().equals(Verb.CREATE)) {
							if (command.getNoun().equals("set")) {
								currPlayer.buildSet();
							}
							else if (command.getNoun().equals("run")) {
								currPlayer.buildRun();
							} else {
								System.out.println("Please specify whether you want to create a set or a run.");
							}
						}
						else if (command.getVerb().equals(Verb.POSSIBLE)) {
							if (command.getNoun().equals("runs")) {
								System.out.println("Possible runs:");
								currPlayer.printRuns();
							}
							else if (command.getNoun().equals("sets")) {
								System.out.println("Possible sets:");
								currPlayer.printSets();
							} else {
								System.out.println("Please specify whether you want to see possible sets or runs.");
							}
						}
						else if(command.getVerb().equals(Verb.ADD)) {
							if (command.getNoun().equals("set")) {
								currPlayer.addSet();
							}
							else if (command.getNoun().equals("run")) {
								currPlayer.addRun();
							} else {
								System.out.println("Please specify whether you want to add to a set or a run.");
							}
						}
						else if(command.getVerb().equals(Verb.POINTS)) {
							System.out.println("Player 1 has " + player1.getScore() + " points.");
							System.out.println("Player 2 has " + player2.getScore() + " points.");
						}
						else if(command.getVerb().equals(Verb.HELP)) {
							System.out.println("Available Commands:\n"
									+ "   create [set/run]: Form a new set or run.\n"
									+ "   add [set/run]: Add cards to your sets or runs. (if on lower end of run have to dissolve and restart) \n"
									+ "   hand: Look at your current hand.\n"
									+ "   possible [sets/runs]: See your possible sets or runs.\n"
									+ "   points: See the points of both players.\n"
									+ "   knock: knocks, ends the game, must have less than 10 points of deadwood to be be valid"
									+ "   discard: End your turn by dicarding one of your cards.\n"
									+ "   quit: End your turn and discard one of your cards.\n"
									+ "for rules: https://en.wikipedia.org/wiki/Gin_rummy \n excluded rule allowing players to place cards on other peoples sets after knocking");
						}
						else if(command.getVerb().equals(Verb.HAND)) {
							System.out.println(playerTxt +": " + currPlayer.hand + " Sets: " + currPlayer.curSets + " Runs: " + currPlayer.curRuns);
						}
						else if(command.getVerb().equals(Verb.DISSOLVE)) {
							currPlayer.dissolve();
						}
						else if(command.getVerb().equals(Verb.KNOCK)) {
							Card knockCard = null;
							System.out.println("Choose a card to knock with");
							com = new Command(scan.nextLine());
							knockCard = currPlayer.remove(com.getValue().toString(), com.getSuit().toString());
							while(knockCard == null) {
								System.out.println("card not found. Try again");
								com = new Command(scan.nextLine());
								knockCard = currPlayer.remove(com.getValue().toString(), com.getSuit().toString());
							}
							int pts = currPlayer.knock(knockCard);
							if(pts== -1) {
							}
							else {
								knockScore = pts;
								knockPlayer = currPlayer;
								whoesTurn = -1;
								knockValid = true;
								command.setVerb(Verb.QUIT);
							}
						}
					}
				} while (!command.getVerb().equals(Verb.QUIT) && !command.getVerb().equals(Verb.DISCARD)); 
				if (!knockValid) {
					System.out.println(playerTxt +": " + currPlayer.hand + " Sets: " + currPlayer.curSets + " Runs: " + currPlayer.curRuns);
					System.out.println(playerTxt + " choose a card to discard");
					com = new Command(scan.nextLine());
					discard = currPlayer.remove(com.getValue().toString(), com.getSuit().toString());
					while(discard == null) {
						System.out.println("card not found. Try again");
						com = new Command(scan.nextLine());
						discard = currPlayer.remove(com.getValue().toString(), com.getSuit().toString());
					}
					whoesTurn++;
				}
			}
				int pts= 0;
				if(knockPlayer.equals(player1)) {
					int otherDeadwood = player2.getDeadwood();
					System.out.println("Player 1 knocks with " + knockScore + " points.");
					System.out.println("Player 2 has " + otherDeadwood + " points.");
					int diff = otherDeadwood-knockScore;
					if(diff <= 0) {
						System.out.println("Undercut! Player 2 wins and gets 15 bonus points");
						pts = 15-diff;
						player2.setScore(player2.getScore()+pts);
					}
					else {
						if(knockScore == 0) {
							System.out.println("Gin! 25 bonus points");
							pts +=25;
						}
						System.out.println("Player 1 wins");
						pts+=diff;
						player1.setScore(player1.getScore()+pts);
					}
				} else {
					int otherDeadwood = player1.getDeadwood();
					System.out.println("Player 2 knocks with " + knockScore + " points.");
					System.out.println("Player 1 has " + otherDeadwood + " points.");
					int diff = otherDeadwood-knockScore;
					if(diff <= 0) {
						System.out.println("Undercut! Player 1 wins and gets 15 bonus points");
						pts = 15-diff;
						player1.setScore(player1.getScore()+pts);
					}
					else {
						if(knockScore == 0) {
							System.out.println("Gin! 25 bonus points");
							pts +=25;
						}
						System.out.println("Player 2 wins");
						pts+=diff;
						player2.setScore(player2.getScore()+pts);
					}
				}
				player1.hand.clear();
				player2.hand.clear();
				System.out.println("Player 1: "+ player1.getScore());
				System.out.println("Player 2: "+ player2.getScore());
			
		}
		if(player1.getScore()>100)
			System.out.println("Player 1 wins!");
		else
			System.out.println("Player 2 wins!");
	}
	
	public static String userInput(String user, ArrayList<String> expected) {
		if (!expected.contains(user)) {
			String possible = "";
			for (String string : expected) {
				possible += string + ", ";
			}
			System.out.println("Valid inputs are: " + possible);
			Scanner sc = new Scanner(System.in);
			String nextUser = sc.next();
			return userInput(nextUser, expected);
		} return user;
 	}
}
