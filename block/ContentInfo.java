package bc_demo.block;

/**
 * 区块body内的一条内容实体
 *
 * @author ZhaoGao
 * @date 2020/7/22 - 19:43 - JavaProjects
 */
public class ContentInfo {

    /**
     * 新的JSON内容
     */
    private String jsonContent;
    /**
     * 时间戳
     */
    private long timeStamp;
    /**
     * 公钥
     */
    private String publicKey;
    /**
     * 签名
     */
    private String sign;

    /**
     * 该操作的hash
     */
    private String hash;

    public String getJson() {
        return jsonContent;
    }

    public void setJsonContent(String jsonContent) {
        this.jsonContent = jsonContent;
    }

    public String getPublicKey() {
        return publicKey;
    }

    public void setPublicKey(String publicKey) {
        this.publicKey = publicKey;
    }

    public long getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(Long timeStamp) {
        this.timeStamp = timeStamp;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }
}
