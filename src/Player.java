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
    }
    
    /*
     * returns current hand
     */
    
    public List<Card> getHand() {
        return this.hand;
    }
    
    
    /*
     * uses bucket sort to sort cards in hand
     * 
     * returns sorted hand by card value
     */   
    public ArrayList<Card> sortHand() {
        // Create buckets
        ArrayList<ArrayList<Card>> buckets = new ArrayList<ArrayList<Card>>();
        for (int i = 0; i < hand.size(); i++)
            buckets.add(new ArrayList<Card>());
        
        // Distributes cards into buckets (based on card value)
        for (Card c : hand) 
            buckets.get((int)Math.ceil(c.getIntValue()/3.0)-1).add(c);
        
        // sort each bucket using insertionSort
        for(int i = 0; i < buckets.size(); i++ ) {
            buckets.set(i, insertionSortHelper(buckets.get(i)));
        }
        // Merge buckets into a single list
        ArrayList<Card> finalList = new ArrayList<Card>();
        for (ArrayList<Card> bucket : buckets)
            finalList.addAll(bucket);
  
        
        return finalList; 
    }
    
    /*
     * uses insertion sort for each bucket 
     * 
     * @param bucket: one bucket from bucketsort
     * returns sorted bucket
     */
    private ArrayList<Card> insertionSortHelper(ArrayList<Card> bucket) {
        for(int i = 1; i < bucket.size(); i++) {
            Card temp = bucket.get(i);
            int j = i - 1;
            while(j >= 0 && bucket.get(j).getIntValue() > temp.getIntValue()) {
                bucket.set(j+1, bucket.get(j));
                j--;
            }
            
            bucket.set(j+1, temp);
        }
        return bucket;
    }
    
    /*
     * Evaluates hand based on Straight, flush, two pair, three pair, etc.
     * 
     * @param communityCards
     * 
     * returns score based on two hole cards + community cards
     */
    public int scoreHand(List<Card> communityCards) {
        
        // gets a list of all 7 cards = 2 hole cards + 5 community cards
        List<Card> all = new ArrayList<>();
        for(Card c : communityCards) {
            all.add(c);
        }
        for(Card c : this.hand) {
            all.add(c);
        }

        // sets hand back to empty
        this.hand = new ArrayList<>();
        
        // Check all 5 card combinations from the 7 total cards - 7 choose 5 = 21 combinations every time
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
                            
                            // default score starts with high card
                            int tempScore = findMax();
                            
                            // set hand to sorted hand
                            this.hand = this.sortHand();
                           
                            
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
                            
                            // checks if temp score is higher than current score
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
    
    /*
     * Checks if hand contains 2 cards with same value
     * 
     * returns true if a pair exists, false otherwise
     */
    private boolean checkPair() {
        
        // maps each value to its frequency
        Map<String, Integer> hm = new HashMap<>();
        for(Card c : this.hand) {
            if(!hm.containsKey(c.getValue())) {
                hm.put(c.getValue(), 1);
            }
            else {
                hm.put(c.getValue(), hm.get(c.getValue()) + 1);
            }
        }       
        
        // checks if any frequency is 2
        for(int value : hm.values()) {
            if(value == 2) {
                return true;
            }
        }
        return false;
    }

    /*
     * Checks if hand contains 2 pair
     * 
     * returns true if 2 pair exists, false otherwise
     */
    private boolean checkTwoPair() {
        // maps each card value to its frequency
        Map<String, Integer> hm = new HashMap<>();
        for(Card c : this.hand) {
            if(!hm.containsKey(c.getValue())) {
                hm.put(c.getValue(), 1);
            }
            else {
                hm.put(c.getValue(), hm.get(c.getValue()) + 1);
            }
        }  
        
        // checks if 2 frequency values are present - 2 pair
        int twoCount = 0;
        for(int value : hm.values()) {
            if(value == 2) {
                twoCount++;
            }
        }
        
        return twoCount == 2 ? true : false;
    }

    /*
     * Checks if hand contains 3 of a kind
     * 
     * returns true if 3 of a kind exists, false otherwise
     */
    private boolean checkThreeKind() {
        // maps each card value to its frequency
        Map<String, Integer> hm = new HashMap<>();
        for(Card c : this.hand) {
            if(!hm.containsKey(c.getValue())) {
                hm.put(c.getValue(), 1);
            }
            else {
                hm.put(c.getValue(), hm.get(c.getValue()) + 1);
            }
        }       
        
        // checks if any of the frequency values are equal to 3
        for(int value : hm.values()) {
            if(value == 3) {
                return true;
            }
        }
        return false;
    }

    /*
     * Checks if hand contains full house - 3 of a kind + a pair
     * 
     * returns true if full house exists, false otherwise
     */
    private boolean fullHouse() {
        // maps each card value to its frequency
        Map<String, Integer> hm = new HashMap<>();
        for(Card c : this.hand) {
            if(!hm.containsKey(c.getValue())) {
                hm.put(c.getValue(), 1);
            }
            else {
                hm.put(c.getValue(), hm.get(c.getValue()) + 1);
            }
        }  
        
        // checks hm contains 3 of a kind + pair
        boolean twoPair = false;
        boolean threeKind = false;
        for(int value : hm.values()) {
            if(value == 2) {
                twoPair = true;
            }
            else if(value == 3) {
                threeKind = true;
            }
        }
        
        return twoPair && threeKind;
    }

    /*
     * Checks if hand contains 4 of a kind
     * 
     * returns true if 4 of a kind exists, false otherwise
     */
    private boolean fourKind() {
        // maps each card value to its frequency
        Map<String, Integer> hm = new HashMap<>();
        for(Card c : this.hand) {
            if(!hm.containsKey(c.getValue())) {
                hm.put(c.getValue(), 1);
            }
            else {
                hm.put(c.getValue(), hm.get(c.getValue()) + 1);
            }
        }       
        
        // check if any frequency is 4
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
        // maps each card value to its frequency
        Map<String, Integer> hm = new HashMap<>();
        for(Card c : this.hand) {
            if(!hm.containsKey(c.getSuit())) {
                hm.put(c.getSuit(), 1);
            }
            else {
                hm.put(c.getSuit(), hm.get(c.getSuit()) + 1);
            }
        } 
        // checks if only 1 suit exists in hm
        return hm.size() == 1 ? true : false;       
    }
    
    /*
     * Check if hand contains straight
     * 
     * return true if straight, false otherwise
     */
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
     * prints out all of the cards in the players hand 
     */
    public void printHand() {
        for(Card c : hand) {
            System.out.print(c.getSuit() + "" + c.getValue() + " ");
        }
        System.out.println();
    }
    
   
    
    
}
