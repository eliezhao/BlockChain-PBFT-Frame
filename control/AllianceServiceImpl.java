package bc_demo.control;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.List;

/**
 * @author ZhaoGao
 * @date 2020/8/19 - 15:35 - JavaProjects
 */
public class AllianceServiceImpl implements AllianceService{

    protected static Logger logger =
            LoggerFactory.getLogger(AllianceServiceImpl.class);

    @Autowired
    private IAllianceDAO allianceDAO;

    @Override
    public AllianceEntity findByAllianceIp(String allianceIp) {
        return allianceDAO.findAllianceIp(allianceIp);
    }

    @Override
    public void insertIntoAlliance(String allianceIp, String allianceId, String allianceName) {
        AllianceEntity allianceEntity = new AllianceEntity();
        allianceEntity.setAllianceId(allianceId);
        allianceEntity.setAllianceIp(allianceIp);
        allianceEntity.setAllianceName(allianceName);
        allianceEntity.setCreateTime(new Date());
        allianceEntity.setUpdateTime(new Date());

        allianceDAO.save(allianceEntity);
    }

    @Override
    public List<AllianceEntity> selectAllIp() {
        return null;
    }
}
