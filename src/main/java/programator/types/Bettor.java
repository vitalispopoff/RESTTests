package programator.types;

import java.io.Serializable;

public class Bettor implements Serializable {

//    private long bettorId;
    private String bettorLogin;


    public Bettor() {
    }

    public Bettor(long bettorId, String bettorLogin) {

//        this.bettorId = bettorId;
        this.bettorLogin = bettorLogin;
    }

    /*public long getBettorId() {
        return bettorId;
    }*/

    /*public void setBettorId(long bettorId) {
        this.bettorId = bettorId;
    }*/

    public String getBettorLogin() {
        return bettorLogin;
    }

    public void setBettorLogin(String bettorLogin) {
        this.bettorLogin = bettorLogin;
    }
}
