import java.util.*;

public class Table {
    
    private int numPlayers;
    private Deck deck;
    private List<Card> communityCards;
    private List<Player> players;
    
    
    
    public Table() {
        this.deck = new Deck();
        deck.shuffle();
        players = new ArrayList<>();
        communityCards = new ArrayList<>();
        
    }
    
    
    
    public void startGame() {
        Scanner sc = new Scanner(System.in);
        System.out.println("How many Players?");
        this.numPlayers = sc.nextInt();
        for(int i = 0; i < numPlayers; i++) {
            this.players.add(new Player());
        }
        
        System.out.println("Game for " + this.numPlayers + " players has begun!");
        
        for(Player p : players) {
            p.getHand().add(new Card("H", "3"));
            p.getHand().add(new Card("S", "3"));
        }
        
        communityCards.add(new Card("S", "J"));
        communityCards.add(new Card("H", "5"));
        communityCards.add(new Card("H", "7"));
        communityCards.add(new Card("H", "7"));
        communityCards.add(new Card("H", "K"));
        
        for(Player p : players) {
            p.printHand();
            System.out.println(p.scoreHand(communityCards));
        }
        
        
        //deal(players);
        
        /*
        for(Player p : players) {
            p.getHand().add(new Card("H", "9"));
            p.getHand().add(new Card("H", "5"));
            p.getHand().add(new Card("H", "10"));
            p.printHand();
        }
        
        System.out.println();
        
        for(Player p : players) {
            ArrayList<Card> sorted = p.sortHand();
            for(Card c : sorted) {
                System.out.println(c.getSuit() + " " + c.getValue());
            }
        }
        */

        
        
        
        
    }
     
    private void deal(List<Player> players) {
        for(int i = 0; i < players.size(); i++) {
            Player temp = players.get(i);
            temp.getHand().add(this.deck.getTopCard());
            temp.getHand().add(this.deck.getTopCard());
        }        
    }



    public void addToPot(int money) {
        // TODO Auto-generated method stub
        
    }
    
}
