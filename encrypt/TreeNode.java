package bc_demo.encrypt;

/**
 * 树结点定义
 *
 */
public class TreeNode {
    /**
     * @param left 左节点定义
     * @param right 右节点定义
     * @param hash data对应hash值---SHA-256
     * @param data 节点数据
     * @param name 节点名称
     */
    private TreeNode left;
    private TreeNode right;
    private String data;
    private String hash;
    private String name;

    public TreeNode() {}

    public TreeNode(String data) {
        this.data=data;
        this.hash=hutoolEncrypt.sha256BasedHutool(data);
        this.name="[节点"+data+"]";
    }

    public String getName() {
        return name;
    }

    public void setName(String name){
        this.name=name;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data=data;
    }

    public TreeNode getRight() {
        return right;
    }

    public void setRight(TreeNode right){
        this.right=right;
    }

    public TreeNode getLeft() {
        return left;
    }

    public void setLeft(TreeNode left) {
        this.left=left;
    }

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash=hash;
    }

}
