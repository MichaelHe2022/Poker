import java.util.HashMap;
import java.util.Map;

public class Card {

    // fields
    private String suit;
    private String value;
    
    /*
     * Constructor for a single playing card
     */
    public Card(String suit, String value) {
        this.suit = suit;
        this.value = value;
    }
    
    /*
     * Returns suit in string form
     */
    public String getSuit() {
        return this.suit;
    }
    
    /*
     * Returns value in string form
     */
    public String getValue() {
        return this.value;
    }
    
    /*
     * returns value in int form
     */
    public int getIntValue() {
        if(this.value.equals("J")) {
            return 11;
        }
        else if(this.value.equals("Q")) {
            return 12;
        }
        else if(this.value.equals("K")) {
            return 13;
        }
        else if(this.value.equals("A")) {
            return 14;
        }
        else {
            return Integer.parseInt(this.value);
        }
    }
    
    

}
