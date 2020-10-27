package bc_demo.consistencyWS;

import java.util.List;

/**
 * 投票信息类
 *
 * @author ZhaoGao
 * @date 2020/8/11 - 20:32 - JavaProjects
 */
public class VoteInfo {

    //投票状态码
    private int code;

    //特写入区块链的内容
    private List<String> list;

    //待写入区块的内容的Merkle树根节点hash值
    private String hash;

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    public List<String> getList() {
        return list;
    }

    public void setList(List<String> list) {
        this.list = list;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

}
