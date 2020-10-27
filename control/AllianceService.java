package bc_demo.control;

import java.util.List;

/**
 * @author ZhaoGao
 * @date 2020/8/19 - 15:37 - JavaProjects
 */
public interface AllianceService {
    public AllianceEntity findByAllianceIp(String allianceIp);

    public void insertIntoAlliance(String allianceIp, String allianceId, String allianceName);

    public List<AllianceEntity> selectAllIp();
}
