package bc_demo.encrypt;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


/**
 * Merkle tree构建，生成根节点哈希值的工具类
 *
 */
public class MerkleTree {
    //TreeNode List
    private List<TreeNode> list;
    //根节点
    private TreeNode root;
    //构造函数
    public MerkleTree(List<String> contents){
        createMerkleTree(contents);
    }

    /**
     * 总纲：
     *创建Merkle树需要：节点类，树叶节点创建，母节点创建，循环创建总节点
     *判断输入内容是否为空  初始化list=arraylist
     *创建树叶节点
     *循环创建母节点
     *最后获取根节点-get parents 0
     *
     * @param contents
     */


    //构建Merkle树
    private void createMerkleTree(List<String> contents){
        if (contents==null||contents.size()==0){
            return;
            //原来return还能return空的。。
        }

        //初始化
        list = new ArrayList<>();

        //根据数据创建叶子节点
        List<TreeNode> leafList = createLeafList(contents);
        list.addAll(leafList);

        //创建父节点
        List<TreeNode> parents=createParentList(leafList);
        //创建母节点群列表

        //循环创建各级父节点直至根节点
        while(parents.size()>1){
            List<TreeNode> temp=createParentList(parents);
            list.addAll(temp);
            parents=temp;
        }

        //通过循环 parents只有一个节点了 那就是root
        root=parents.get(0);

        //创建父节点列表-5/24

    }

    //创建父节点列表
    private List<TreeNode> createParentList(List<TreeNode> leafList){
        List<TreeNode> parents =new ArrayList<>();

        //空检验
        if (leafList ==null||leafList.size()==0){
            return parents;
        }

        int length=leafList.size();
        for (int i=0;i<length-1;i+=2){
            TreeNode parent=createParentNode(leafList.get(i), leafList.get(i+1));
            parents.add(parent);
        }


        //奇数个节点 手动创建最后一个节点
        if (length%2!=0){
            TreeNode parent= createParentNode(leafList.get(length-1),null);
            parents.add(parent);
        }
        return parents;
    }

    /**
     *
     * @param left 左节点
     * @param right 右节点
     * @return
     */
    private TreeNode createParentNode(TreeNode left,TreeNode right){
        TreeNode parent = new TreeNode();

        parent.setLeft(left);
        parent.setRight(right);

        //right==null 则父节点的哈希值是left的哈希值
        String hash=left.getHash();
        if (right !=null){
            hash = hutoolEncrypt.sha256BasedHutool(left.getHash()+right.getHash());
        }

        //hash 字段和data字段同值
        parent.setData(hash);
        parent.setHash(hash);

        if (right != null) {
            parent.setName("("+left.getName()+"和"+right.getName()+"的父节点");
        }else {
            parent.setName("(继承节点{"+left.getName()+"}成为父节点)");
        }

        return parent;
    }

    /**
     * 设置母节点步骤：
     * 初始化母节点
     * 在函数的参数中 需要设置两个参数---左节点和右节点
     * 母节点set左右节点
     * 母节点hash值计算---左节点计算hash；有不为空，则计算两个hash值和的hash
     * 母节点hash与data同值---可以把data设置成节点编号
     * 最后，设置母节点名称--已经设置了，那么节点的data可以设置别的 依情况而定
     *
     */

    //构建叶子节点列表
    private List<TreeNode> createLeafList(List<String> contents){
        List<TreeNode> leafList=new ArrayList<TreeNode>();

        //空检验
        if (contents==null||contents.size()==0){
            return leafList;
        }

        for (String content : contents){
            TreeNode node= new TreeNode(content);
            leafList.add(node);
        }

        return leafList;
    }

    /**
     * 创建树的叶子列表
     * 创建TreeNode列表
     * 遍历列表 --输入明文（内容），并且设置节点的data等于明文
     *
     */


    //遍历树
    public void traverseTreeNodes() {
        Collections.reverse(list);
        //collections方法-列表倒置
        TreeNode root = list.get(0);
        traverseTreeNodes(root);
    }

    /**
     * 倒置树列表-获取根节点
     *
     */

    public void traverseTreeNodes(TreeNode node){
        System.out.println(node.getName());
        if (node.getLeft()!=null){
            traverseTreeNodes(node.getLeft());
        }

        if (node.getRight() != null){
            traverseTreeNodes(node.getRight());
        }
    }

    /**
     * 递归法---遍历出二叉树的各个节点的左右节点---效率低，应该修改!
     */


    public List<TreeNode> getList(){
        if (list==null){
            return list;
        }
        Collections.reverse(list);
        return list;
    }

    public void setList(List<TreeNode> list) {
        this.list = list;
    }

    public TreeNode getRoot(){
        return root;
    }

    public void setRoot(TreeNode root){
        this.root=root;
    }

}
//Merkle 节点完全完成 不理解 没有复盘 需要测试---5.25