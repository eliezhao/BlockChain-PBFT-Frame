package bc_demo.control;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 公私密钥套装生成Controller
 *
 * @author ZhaoGao
 * @date 2020/8/19 - 20:04 - JavaProjects
 */
@RestController
@RequestMapping("/api/v1/key")
public class KeySuitController {
    protected static Logger logger =
            LoggerFactory.getLogger(KeySuitController.class);

    @Autowired
    private KeySuitService keySuitService;

    @GetMapping("/change")
    public KeySuitVO change() {
        return keySuitService.getRandowKeySuit();
    }

}
