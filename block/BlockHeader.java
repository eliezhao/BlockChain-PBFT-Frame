package bc_demo.block;

import java.util.List;

/**
 * 区块头
 *
 * @author ZhaoGao
 * @date 2020/7/22 - 19:51 - JavaProjects
 */
public class BlockHeader {
    /**
     * 版本号
     *
     */
    private int version;

    /**
     * 上个区块的hash
     */
    private String hashPreviousBlock;

    /**
     * Merkle树根节点hash值
     */
    private String hashMerkleRoot;

    /**
     * 生成该区块的公钥
     */
    private String publicKey;

    /**
     * 区块的序号
     */
    private int number;

    /**
     * 时间戳
     */
    private long timeStamp;

    /**
     * 32位随机数
     */
    private long nonce;

    /**
     * 该区块里每条交易信息的hash集合，按顺序，通过hash集合算出根节点hash值
     */
    private List<String> hashList;

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public String getHashPreviousBlock() {
        return hashPreviousBlock;
    }

    public void setHashPreviousBlock(String hashPreviousBlock) {
        this.hashPreviousBlock = hashPreviousBlock;
    }

    public String getHashMerkleRoot() {
        return hashMerkleRoot;
    }

    public void setHashMerkleRoot(String hashMerkleRoot) {
        this.hashMerkleRoot = hashMerkleRoot;
    }

    public String getPublicKey() {
        return publicKey;
    }

    public void setPublicKey(String publicKey) {
        this.publicKey = publicKey;
    }

    public int getNumber() {
        return number;
    }


    public void setNumber(int number) {
        this.number = number;
    }

    public long getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(long timeStamp) {
        this.timeStamp = timeStamp;
    }

    public long getNonce() {
        return nonce;
    }

    public void setNonce(long nonce) {
        this.nonce = nonce;
    }

    public List<String> getHashList() {
        return hashList;
    }

    public void setHashList(List<String> hashList) {
        this.hashList = hashList;
    }


}
