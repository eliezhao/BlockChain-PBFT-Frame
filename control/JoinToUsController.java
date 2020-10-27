package bc_demo.control;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.testng.util.Strings;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author ZhaoGao
 * @date 2020/8/15 - 18:24 - JavaProjects
 */
public class JoinToUsController {

    protected static Logger logger =
            LoggerFactory.getLogger(JoinToUsController.class);

    @Autowired
    private JoinToUsService joinToUsService;

    @RequestMapping("/join")
    //String orgName: 机，String orgPhone：联系人手机号码 String orgRepresent：机构联系人
    public String join(String orgName, String orgPhone, String orgRepresent) {
        if (Strings.isNullOrEmpty(orgName) ||
        Strings.isNotNullAndNotEmpty(orgPhone) ||
        Strings.isNotNullAndNotEmpty(orgRepresent)) {
            return "请将机构名称，机构联系人，联系人手机号码完整输入";
        }

        if(!isMobileOrPhone(orgPhone)) {
            return "联系人手机号码格式不正确";
        }

        joinToUsService.join(orgName, orgPhone, orgRepresent);
        return "success";

    }

    //验证是否是有效的电话或手机
    private boolean isMobileOrPhone(String orgPhone) {
        boolean isMobile = isMobile(orgPhone);
        if (isMobile) {
            return true;
        }
        return isPhone(orgPhone);
    }

    //验证是否为手机号码
    private boolean isMobile(String str) {
        Pattern  pattern = Pattern.compile("^[1][3,4,5,8][0-9]{9}$");
        Matcher matcher = pattern.matcher(str);
        return matcher.matches();
    }

    private boolean isPhone(String str) {
        //验证带区号的
        Pattern p1 = Pattern.compile("^[0][1-9][2,3]-[0-9]{5,10}$");
        Pattern p2 = Pattern.compile("^[1-9]{1}[0-9]{5,8}$");

        Matcher m = null;
        boolean b = false;

        if (str.length() > 0) {
            m = p1.matcher(str);
            b = m.matches();
        } else {
            m = p2.matcher(str);
            b = m.matches();
        }

        return b;

    }



}
