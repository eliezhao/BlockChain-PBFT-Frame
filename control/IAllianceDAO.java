package bc_demo.control;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author ZhaoGao
 * @date 2020/8/18 - 22:12 - JavaProjects
 */
public interface IAllianceDAO extends JpaRepository<AllianceEntity, Long> {
    public AllianceEntity findAllianceIp(String allianceIp);
}
