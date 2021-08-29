import java.util.*;

public class Player {
    // Instance Fields
    private List<Card> hand;
    private int score;
    
    // fields for hand evaluation
    private final int STRAIGHT_FLUSH = 800;
    private final int FOUR_KIND = 700;
    private final int FULL_HOUSE = 600;
    private final int FLUSH = 500;
    private final int STRAIGHT = 400;
    private final int THREE_KIND = 300;
    private final int TWO_PAIR = 200;
    private final int PAIR = 100;
    
    
    /*
     * Constructor
     * @param startingMoney
     */    
    public Player() {
        this.hand = new ArrayList<Card>();
        this.score = 0;
    }
    
    /*
     * returns current hand
     */  
    public List<Card> getHand() {
        return this.hand;
    }
    
    /*
     * returns score
     */  
    public int getScore() {
        return this.score;
    }
    
    
    /*
     * Implements bucket sort to sort hand
     * 
     * returns sorted hand by card value
     */   
    public ArrayList<Card> sortHand() {
        // Create buckets
        ArrayList<ArrayList<Card>> buckets = new ArrayList<ArrayList<Card>>();
        for (int i = 0; i < hand.size(); i++)
            buckets.add(new ArrayList<Card>());
        
        // cards range from 2-14 with 5 buckets, implement hashing function
        for (Card c : hand) {
            int index = (int) Math.floor(c.getIntValue() / 3.0);            
            buckets.get(index).add(c);
        }
        
        // Sort each individual bucket
        for(ArrayList<Card> bucket : buckets) {
            bucket = insertionSortHelper(bucket);
        }   
  
        // Merge all sorted buckets into final list
        ArrayList<Card> merged = new ArrayList<Card>();
        for (ArrayList<Card> bucket : buckets) {
            merged.addAll(bucket);
        }
        
        return merged;
   
      
    }
    
    /*
     * uses Insertion sort to sort each individual bucket from buckets
     * @param bucket
     * 
     * return sorted bucket
     */
    private ArrayList<Card> insertionSortHelper(ArrayList<Card> bucket) {
        for(int i = 1; i < bucket.size(); i++) {
            int j = i - 1;
            while(j >= 0 && bucket.get(j).getIntValue() > bucket.get(i).getIntValue()) {
                bucket.set(j+1, bucket.get(j));
                j--;
            }
            
            bucket.set(j+1, bucket.get(i));
        }
        return bucket;
    }
    
    /*
     * Evaluates hand based on Straight, flush, two pair, three pair, etc.
     * 
     * @param sortedHand
     * 
     * returns score based on two hole cards + community cards
     */
    public int scoreHand(List<Card> communityCards) {
        
        List<Card> all = new ArrayList<>();
        for(Card c : communityCards) {
            all.add(c);
        }
        for(Card c : this.hand) {
            all.add(c);
        }
        
        System.out.print("ALL CARDS: ");
        for(Card c : all) {
            System.out.print(c.getSuit() + "" + c.getIntValue() + " ");
        }
        
        System.out.println();
        this.hand = new ArrayList<>();
        
        System.out.println("Reached");
        
        for(int first = 0; first < all.size() - 4; first++) {
            this.hand.add(all.get(first));
            for(int second = first + 1; second < all.size(); second++) {
                this.hand.add(all.get(second));
                for(int third = second + 1; third < all.size(); third++) {
                    this.hand.add(all.get(third));
                    for(int fourth = third + 1; fourth < all.size(); fourth++) {
                        this.hand.add(all.get(fourth));
                        for(int fifth = fourth + 1; fifth < all.size(); fifth++) {
                            this.hand.add(all.get(fifth));
                            
                            for(Card c : this.hand) {
                                System.out.print(c.getSuit() + "" + c.getIntValue() + " ");
                               
                            }
                            System.out.println();
                            // score starts with high card
                            int tempScore = findMax();
                            
                            this.sortHand();
                           
                            
                            // Check tier list from top to bottom
                            if(checkFlush() && checkStraight()) {
                                tempScore += STRAIGHT_FLUSH;
                            }
                            else if(fourKind()) {
                                tempScore += FOUR_KIND;
                            }
                            else if(fullHouse()) {
                                tempScore += FULL_HOUSE;
                            }
                            else if(checkFlush()) {
                                tempScore += FLUSH;
                            }
                            else if(checkStraight()) {
                                tempScore += STRAIGHT;
                            }
                            else if(checkThreeKind()) {
                                tempScore += THREE_KIND;
                            }
                            else if(checkTwoPair()) {
                                tempScore += TWO_PAIR;
                            }
                            else if(checkPair()) {
                                tempScore += PAIR;
                            }
                            
                            this.score = Math.max(tempScore, this.score);
                            
                            this.hand.remove(all.get(fifth));                          
                        }
                        this.hand.remove(all.get(fourth)); 
                    }
                    this.hand.remove(all.get(third)); 
                }
                this.hand.remove(all.get(second)); 
            }
            this.hand.remove(all.get(first)); 
        }      
        
        return this.score;
    }
    
    private boolean checkPair() {
        Map<String, Integer> hm = new HashMap<>();
        for(Card c : this.hand) {
            if(!hm.containsKey(c.getValue())) {
                hm.put(c.getValue(), 1);
            }
            else {
                hm.put(c.getValue(), hm.get(c.getValue()) + 1);
            }
        }       
        
        for(int value : hm.values()) {
            if(value == 2) {
                return true;
            }
        }
        return false;
    }

    private boolean checkTwoPair() {
        Map<String, Integer> hm = new HashMap<>();
        for(Card c : this.hand) {
            if(!hm.containsKey(c.getValue())) {
                hm.put(c.getValue(), 1);
            }
            else {
                hm.put(c.getValue(), hm.get(c.getValue()) + 1);
            }
        }  
        
        int twoCount = 0;
        for(int value : hm.values()) {
            if(value == 2) {
                twoCount++;
            }
        }
        
        return twoCount == 2 ? true : false;
    }

    private boolean checkThreeKind() {
        Map<String, Integer> hm = new HashMap<>();
        for(Card c : this.hand) {
            if(!hm.containsKey(c.getValue())) {
                hm.put(c.getValue(), 1);
            }
            else {
                hm.put(c.getValue(), hm.get(c.getValue()) + 1);
            }
        }       
        
        for(int value : hm.values()) {
            if(value == 3) {
                return true;
            }
        }
        return false;
    }

    private boolean fullHouse() {
        // TODO Auto-generated method stub
        return false;
    }

    private boolean fourKind() {
        Map<String, Integer> hm = new HashMap<>();
        for(Card c : this.hand) {
            if(!hm.containsKey(c.getValue())) {
                hm.put(c.getValue(), 1);
            }
            else {
                hm.put(c.getValue(), hm.get(c.getValue()) + 1);
            }
        }       
        
        for(int value : hm.values()) {
            if(value == 4) {
                return true;
            }
        }
        return false;
    }

    /*
     * Finds max value of cards in hand - high card
     * 
     * return high card value
     */
    private int findMax() {
        int max = Integer.MIN_VALUE;
        for(Card c: this.hand) {
            max = Math.max(max, c.getIntValue());
        }
        
        return max;
    }
    
    /*
     * Check if all cards in hand share same suit
     * 
     * return true if flush, false otherwise
     */
    private boolean checkFlush() {        
        Map<String, Integer> hm = new HashMap<>();
        for(Card c : this.hand) {
            if(!hm.containsKey(c.getSuit())) {
                hm.put(c.getSuit(), 1);
            }
            else {
                hm.put(c.getSuit(), hm.get(c.getSuit()) + 1);
            }
        }       
        return hm.size() == 1 ? true : false;       
    }
    
    private boolean checkStraight() {
        boolean isStraight = true;
        for(int i = 1; i < this.hand.size(); i++) {
            if(this.hand.get(i).getIntValue() != this.hand.get(i-1).getIntValue() + 1) {
                isStraight = false;
            }
        }
        return isStraight;
    }


    /*
     * prints out all of the cards in the players hand (includes communal cards)
     */
    public void printHand() {
        System.out.println("Current Hand: ");
        
        for(Card c : hand) {
            System.out.println(c.getSuit() + " " + c.getValue());
        }
    }
}
