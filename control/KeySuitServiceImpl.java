package bc_demo.control;

import cn.hutool.crypto.asymmetric.RSA;

/**
 * @author ZhaoGao
 * @date 2020/8/19 - 20:18 - JavaProjects
 */
public class KeySuitServiceImpl implements KeySuitService{
    @Override
    public KeySuitVO getRandowKeySuit() {
        RSA rsa = new RSA();
        //获取私钥
        String privateKey = rsa.getPrivateKeyBase64();
        //获得公钥
        String publicKey = rsa.getPublicKeyBase64();

        KeySuitVO vo = new KeySuitVO(privateKey, publicKey);
        return vo;
    }
}
