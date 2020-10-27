package bc_demo.control;

/**
 * 公私钥对
 *
 * @author ZhaoGao
 * @date 2020/8/19 - 20:22 - JavaProjects
 */
public class KeySuitVO {
    //私钥
    private String privateKey;
    //公钥
    private String publicKey;

    public KeySuitVO(){}

    public KeySuitVO(String privateKey, String publicKey) {
        this.privateKey = privateKey;
        this.publicKey = publicKey;
    }

    public String getPrivateKey() {
        return privateKey;
    }

    public void setPrivateKey(String privateKey) {
        this.privateKey = privateKey;
    }

    public String getPublicKey() {
        return publicKey;
    }

    public void setPublicKey(String publicKey) {
        this.publicKey = publicKey;
    }
}
