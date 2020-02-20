package programator.types;

import java.io.Serializable;

public class Bettor implements Serializable {

    private String bettorLogin;

    public Bettor() {}

    public Bettor(long bettorId, String bettorLogin) {
        this.bettorLogin = bettorLogin;
    }

    public String getBettorLogin() {
        return bettorLogin;
    }

    public void setBettorLogin(String bettorLogin) {
        this.bettorLogin = bettorLogin;
    }
}
