package bc_demo.block;

import cn.hutool.crypto.digest.DigestUtil;

/**
 * 区块
 * @author ZhaoGao
 * @date 2020/7/22 - 19:04 - JavaProjects
 */
public class Block {
    /**
     * 区块头
     */
    private BlockHeader blockHeader;
    /**
     * 区块body
     */
    private BlockBody blockBody;
    /**
     * 区块hash
     */
    private String blockHash;



    public BlockHeader getBlockHeader() {
        return blockHeader;
    }

    public void setBlockHeader(BlockHeader blockHeader) {
        this.blockHeader = blockHeader;
    }

    public BlockBody getBlockBody() {
        return blockBody;
    }

    public void setBlockBody(BlockBody blockBody) {
        this.blockBody = blockBody;
    }

    public String getBlockHash() {
        return DigestUtil.sha256Hex(blockHeader.toString()) + blockBody.toString();
    }

    /**
     * 根据该情况所有属性计算SHA256
     * @return sha256HEX
     */
    public void setBlockHash(String blockHash) {
        this.blockHash = blockHash;
    }

}
