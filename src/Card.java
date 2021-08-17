import java.util.HashMap;
import java.util.Map;

public class Card {

    // fieldsssssssss
    private String suit;
    private String value;
    private Map<String, Integer> hm;
    
    /*
     * Constructor for a single playing card
     */
    public Card(String suit, String value) {
        this.suit = suit;
        this.value = value;
        hm = new HashMap<>();
        hm.put("H", 1);
        hm.put("S", 2);
        hm.put("D", 3);
        hm.put("C", 4);
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
    public String getStringValue() {
        return this.value;
    }
    
    /*
     * returns suit in int form
     */
    public int getIntSuit() {
        
        return this.hm.get(this.suit);
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
