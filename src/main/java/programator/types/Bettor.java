package programator.types;

import java.io.Serializable;

public class Bettor implements Serializable {

    private long bettorId;
    private String login;


    public Bettor() {
    }

    public Bettor(long bettorId, String login) {
        this.bettorId = bettorId;
        this.login = login;
    }

    public long getBettorId() {
        return bettorId;
    }

    public void setBettorId(long bettorId) {
        this.bettorId = bettorId;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }
}
