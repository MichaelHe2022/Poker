import java.util.*;

public class Table {
    
    // instance fields
    private int numPlayers;
    private Deck deck;
    private List<Card> communityCards;
    private List<Player> players;
    private int[] scores;
    
    /*
     * Constructor
     * 
     * Initializes shuffled deck, players, community cards
     */
    public Table() {
        this.deck = new Deck();
        deck.shuffle();
        players = new ArrayList<>();
        communityCards = new ArrayList<>();
        
    }
        
    /*
     * Simulates a game of Poker, prints 5 community cards, prints each player's hands, determines winner
     */
    public void startGame() {
        // Asks for number of players
        Scanner sc = new Scanner(System.in);
        System.out.println("How many Players?");
        this.numPlayers = sc.nextInt();
        
        // initializes players and score array
        for(int i = 0; i < numPlayers; i++) {
            this.players.add(new Player());
        }
        scores = new int[numPlayers];
        
        // begin 
        System.out.println("Game for " + this.numPlayers + " players has begun!");
        
        // deal each player 2 cards
        deal(players);
        
        // deal the 5 community cards
        for(int i = 0; i < 5; i++) {
            communityCards.add(deck.getTopCard());
        }
        
        // print the 5 community cards
        System.out.print("Community Cards: ");
        for(Card c : communityCards) {
            System.out.print(c.getSuit() + "" + c.getValue() + " ");            
        }
        System.out.println();
        
        // print each player number + cards + score
        for(int i = 0; i < players.size(); i++) {
            System.out.print("Player " + (i+1) + " Cards: ");
            players.get(i).printHand();
            System.out.println("Player " + (i+1) + " Score: " + players.get(i).scoreHand(communityCards));
            System.out.println();          
            
            scores[i] = players.get(i).scoreHand(communityCards);
        }
        
        // determine player(s) with the highest score
        System.out.println();
        List<Integer> winners = new ArrayList<>();
        int max = Integer.MIN_VALUE;
        for(int i = 0; i < scores.length; i++) {
            max = Math.max(max,  scores[i]);
        }
        
        for(int i = 0; i < scores.length; i++) {
            if(scores[i] == max) {
                winners.add(i + 1);
            }
        }
        
        // print out winning players
        System.out.print("Players: ");
        for(int playerNum : winners) {
            System.out.print(playerNum + " ");
        }
        System.out.print("wins!");
        
    }
     
    /*
     * deals each player 2 cards from the deck
     * 
     * @param List<Player> players
     */
    private void deal(List<Player> players) {
        for(int i = 0; i < players.size(); i++) {
            Player temp = players.get(i);
            temp.getHand().add(this.deck.getTopCard());
            temp.getHand().add(this.deck.getTopCard());
        }        
    }
    
}
