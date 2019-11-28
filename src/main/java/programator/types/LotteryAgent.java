package programator.types;

import java.io.Serializable;

public class LotteryAgent implements Serializable {

    private static Long agentNumbers = 1l;

    private Long agentId;
    private String name;

    public LotteryAgent() {
//        super();
        this.agentId = agentNumbers++;
    }

    public Long getAgentId() {
        return agentId;
    }

    public void setAgentId(Long agentId) {
        this.agentId = agentId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
