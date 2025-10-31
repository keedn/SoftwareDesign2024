import java.io.Serializable;

/**
 * Player Action
 *
 */
public class PlayerAction implements Serializable {
    // All Possible Actions
    public enum Action{
        HIT,STAND,SPLIT,LEAVE,START_GAME,DEALER_CARD,DEALER_HIT,END_TURN,BUST,END_HAND,PLAYING,FLIP,DEALER_BJ
    }
    private Action action;
    private int clientID;

    /**
     * Constructor
     * @param action Action to Be Done
     * @param clientName Name of Client ( Player )
     */
    public PlayerAction(Action action, int clientName) {
        this.action = action;
        this.clientID = clientName;
    }

    public Action getActionType() {
        return action;
    }
    public void setAction(Action action) {
        this.action = action;
    }
    public int getClientID() {
        return clientID;
    }
    public void setClientID(int clientID) {
        this.clientID = clientID;
    }
}
