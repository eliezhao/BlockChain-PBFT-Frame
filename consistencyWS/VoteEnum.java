package bc_demo.consistencyWS;

/**
 * @author ZhaoGao
 * @date 2020/8/11 - 19:53 - JavaProjects
 */
public enum VoteEnum {
    PRE_PREPARE("节点将自己生成Block",100),
    PREPARE("节点收到请求生成Block的消息，进入准备状态，并对外广播该状态",200),
    COMMIT("每个节点收到超过2f+1个不同节点的commit消息后，则认为该区块已经达成一致，即进入Commit状态，并将其持久化到区块链数据库中",400);

    VoteEnum(String message, int code) {
        this.message = message;
        this.code = code;
    }

    //投票情况描述
    private String message;

    //投票情况状态码
    private int code;

    //根据状态码返回对应的Enum
    public static VoteEnum find(int code) {
        for (VoteEnum ve : VoteEnum.values()) {
            if (ve.code == code) {
                return ve;
            }
        }
        return null;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

}
