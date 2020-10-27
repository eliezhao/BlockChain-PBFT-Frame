package bc_demo.encrypt;

import cn.hutool.crypto.SecureUtil;
import cn.hutool.crypto.asymmetric.KeyType;
import cn.hutool.crypto.asymmetric.RSA;
import cn.hutool.crypto.digest.DigestUtil;
import cn.hutool.crypto.symmetric.DES;
import com.google.crypto.tink.subtle.Hex;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;

public class hutoolEncrypt {
    //RSA 对象
    private static RSA rsa=new RSA();
    //对象私钥
    private static PrivateKey privateKey=rsa.getPrivateKey();
    //对象公钥
    private static PublicKey publicKey=rsa.getPublicKey();

    public static String rsaEncrypt(String originalContent){
        if (originalContent.isEmpty()|originalContent.isBlank()){
            return null;
        }

        //公钥对原文进行加密
        return rsa.encryptBase64(originalContent, KeyType.PublicKey);
    }

    public static String rsaDecrypt(String ciphertext) {
        if (ciphertext.isBlank()|ciphertext.isEmpty()){
            return null;
        }
        return rsa.decryptStr(ciphertext,KeyType.PrivateKey);
    }

    public static String desEncrypt(String originalContent,String key){
        if(originalContent.isEmpty()|originalContent.isBlank()|key.isEmpty()|key.isBlank()){
            return null;
        }


//        随机生成密钥---与DES密码学算法有关系
//        byte[] desKey= SecureUtil.generateKey(SymmetricAlgorithm.DES.getValue()).getEncoded();
// DES对象
        DES des=SecureUtil.des(key.getBytes());
        return des.encryptHex(originalContent);

    }

    public static String desDecrpty(String ciphertext,String key){
        if (ciphertext.isBlank()|ciphertext.isEmpty()|key.isBlank()|key.isEmpty()){
            return null;
        }

        //构建DES对象
        DES des=SecureUtil.des(key.getBytes());

        return des.decryptStr(ciphertext);
    }

    /**
     *apache commons-codec工具类工具类实现sha-256加密
     *
     * @param originalStr 明文
     * @param encodeStr 密文
     */

    //用message digest 加密字符串

    /**
     * 先获取message digest实例-sha256，然后用message处理的方法：digest参数是原文的bytes数组，参数为UTF-8
     * @param originalStr
     * @return
     */
    public static String getSHA256BasedMD(String originalStr){
        MessageDigest messageDigest;
        String encodeStr="";
        try {
            messageDigest=MessageDigest.getInstance("SHA-256");
            byte[] hash=messageDigest.digest(originalStr.getBytes("UTF-8"));
            encodeStr= Hex.encode(hash);
        }catch (NoSuchAlgorithmException a){
            a.printStackTrace();
        }catch (UnsupportedEncodingException b){
            b.printStackTrace();
        }

        return encodeStr;

    }
/**
 * hutool进行Sha加密步骤
 *
 *1.创建messagedigest对象---明文处理库，记得在getinstance里面加上要使用的加密的算法名字字符串
 * 2.用messagedigest的digest方法对明文进行处理，注意获取明文byte序列的时候，在getbytes里面加上UTF-8
 * 3.获取密文：使用Hex.encode方法（十六进制编码）加密密文
 */

    /**
     * hutool工具类
     *
     * @param originalStr 原文
     * @return  密文
     */
    public static String sha256BasedHutool(String originalStr){
        return DigestUtil.sha256Hex(originalStr);
    }
    /**
     * 没啥好说的，步骤就是
     * 获取DisgetUtil的sha256Hex方法，param输入originalStr
     */

    public static void main(String[] args) {
        /**
         * test
         */
        String originalStr="区块链是分布式数据存储，点对点传输，共识机制，加密算法等计算机技术的新型应用模式";
        System.out.println("hutool"+System.lineSeparator()+sha256BasedHutool(originalStr));
        System.out.println("Apache"+System.lineSeparator()+getSHA256BasedMD(originalStr));

    }

}
