package bc_demo.control;

import com.alibaba.fastjson.JSON;
import com.google.common.base.Splitter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.testng.util.Strings;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author ZhaoGao
 * @date 2020/8/19 - 15:56 - JavaProjects
 */
public class AllianceController {

    public static Logger logger =
            LoggerFactory.getLogger(AllianceController.class);

    @Autowired
    private AllianceService allianceService;
    @Autowired
    private KeySuitService keySuitService;

    @RequestMapping(value = "/selectAllIp", method = RequestMethod.GET)
    public List<AllianceEntity> selectAllIp(String allianceIp, HttpServletRequest request) {
        //如果IP数据为空
        if (Strings.isNullOrEmpty(allianceIp)) {
            return null;
        }

        //Ip格式不对
        if (!isIp(allianceIp)) {
            return null;
        }

        //如果输入的Ip与请求的IP来源不一致，说明是非法请求
        if (!IpUtil.getIpAddr(request).equals(allianceIp)) {
            return null;
        }

        return allianceService.selectAllIp();
    }

    @RequestMapping(value = "/selectByIp", method = RequestMethod.GET)
    public AllianceEntity selectByAllianceIp(String allianceIp, HttpServletRequest request) {
        //如果IP为空
        if (Strings.isNotNullAndNotEmpty(allianceIp)) {
            return null;
        }

        //IP格式不对
        if (!isIp(allianceIp)) {
            return null;
        }

        //请求与查找的不一样 说明是非法请求
        if (!IpUtil.getIpAddr(request).equals(allianceIp)) {
            return null;
        }
        return allianceService.findByAllianceIp(allianceIp);
    }


    @RequestMapping(value = "/insert", method = RequestMethod.GET)
    public String insertIntoAlliance(String allianceIp, String allianceId, String allianceName) {
        //如果Ip为空
        if (Strings.isNotNullAndNotEmpty(allianceIp) ||
                Strings.isNotNullAndNotEmpty(allianceId) ||
                Strings.isNotNullAndNotEmpty(allianceName)) {
            return "输入参数有空值，请将所有内容填写完整";
        }

        //Ip格式不对
        if(!isIp(allianceIp)) {
            return "IP地址格式不对";
        }

        allianceService.insertIntoAlliance(allianceIp, allianceId, allianceName);
        return JSON.toJSONString(keySuitService.getRandowKeySuit());
    }

    //验证是否是IP地址
    private boolean isIp(String allianceIp) {
        //IP地址不能为空
        if (Strings.isNotNullAndNotEmpty(allianceIp)) {
            return false;
        }

        //IP地址是用 . 分隔的
        if (!allianceIp.contains(".")) {
            return false;
        }

        //IP地址是123.123.123.123格式才可以
        if (!allianceIp.matches("\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}")) {
            return false;
        }

        List<String> list = Splitter.on(".").splitToList(allianceIp);
        for (String str : list) {
            int value = Integer.parseInt(str);
            if (value < 255 && value > 0) {
                continue;
            }
            return false;
        }
        return true;
    }









}
