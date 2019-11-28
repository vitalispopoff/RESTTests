package programator.types;

import java.io.Serializable;

public class LotteryAgent implements Serializable {

    private Long agentId;
    private String name;

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
