import java.util.ArrayList;
import java.util.List;
import java.util.Random;
public class Deck {
    // testing changes 1234daf
    // final static variables
    private static final int NUM_DECK_CARDS = 52;
    private static final String[] values = {"2", "3", "4", "5", "6", "7", "8", "9", "10", "J","Q","K","A" };
    private static final String[] suits = {"H", "S", "C", "D"};  
    
    // class fields
    private int current_num_cards;
    private List<Card> deck;
      
    /*
     * Constructor : initialize a deck of 52 cards
     */
    public Deck() {
        this.current_num_cards = NUM_DECK_CARDS;
        this.deck = new ArrayList<Card>();
        
        for(String suit : suits) {
            for(String value : values) {
                this.deck.add(new Card(suit, value));
            }
        }
    }
    
    /*
     * Shuffles the deck using a random generator and swapping card places in array
     */
    public void shuffle() {
        Random r = new Random();
        for(int i = this.deck.size() - 1; i > 0; i--) {
            int rand = r.nextInt(i + 1);
            Card temp = this.deck.get(i);
            this.deck.set(i, this.deck.get(rand));
            this.deck.set(rand, temp);
        }
    }
    
    /*
     * Retrieves top card of deck
     */
    public Card getTopCard() {
        Card temp = this.deck.remove(0);
        current_num_cards--;
        return temp;
    }
    
    /*
     * Retrieves the current remaining number of cards in deck
     */
    public int getNumCards() {
        return current_num_cards;
    }
    
    public void printDeck() {
        for(Card c : this.deck) {
            System.out.println(c.getSuit() + " " + c.getValue());
        }
    }
    
    
    
    
}
