package bc_demo.encrypt;

import java.util.ArrayList;
import java.util.List;


public class SimpleMerkleTree {
    //5.20
    /**
     * Merkle tree
     *计算根hash---验证交易有效性
     *
     * merkle tree是从子节点构建起 先子最后母
     * @param hashsList 输入一个？？？？x
     */
     public static String getTreeNodeHash(List<String> hashsList){
         if (hashsList==null||hashsList.size()==0){
             //判断传入list是否为空集或者长度为0
             return null;
         }

         while (hashsList.size()!=1){
             hashsList=getMerkleNodeList(hashsList);
         }

         return hashsList.get(0);
     }



     public static List<String> getMerkleNodeList(List<String> contentList){
        List<String> merKleNodeList=new ArrayList<String>();

        if (contentList==null||contentList.size()==0){
            return merKleNodeList;
        }

        int index=0,length=contentList.size();
        while (index<length){
            //获取左子节点数据
            String left=contentList.get(index++);

            //获取右节点数据
            String right="";
            if (index<length){
                right=contentList.get(index++);
            }

            //计算左右节点的父节点哈希值
            String sha2HexValue=hutoolEncrypt.sha256BasedHutool(left+right);
            merKleNodeList.add(sha2HexValue);
        }

        return merKleNodeList;
     }
    /**
     * root node hash
     *首先定义循环，判断条件为1
     * 然后依次计算底层左右节点hash值
     *依次返回给hashslist 继续循环运算
     *
     * 关于奇数节点问题-由于是成树
     * 所以不会有奇数节点
     * 应该是成熟的二叉树
     */

//class
}
