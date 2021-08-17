import java.util.ArrayList;
import java.util.List;

public class Player {
    // Instance Fields
    private int money;
    private boolean folded;
    private int currentBet;
    private List<Card> hand;
    
    
    /*
     * Constructor
     */
    
    public Player(int startingMoney) {
        this.money = startingMoney;
        this.hand = new ArrayList<Card>();
        this.folded = false;
        this.currentBet = 0;
    }
    
    
}
