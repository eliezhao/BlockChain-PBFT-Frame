package bc_demo.control;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author ZhaoGao
 * @date 2020/8/15 - 17:01 - JavaProjects
 */
@Repository
public interface IJoinToUsDAO extends JpaRepository<JoinToUsEntity, Long> {

    public JoinToUsEntity getByOrgNameAndOrgRepresent(String orgName, String orgPhone, String orgRepresent);

    public List<JoinToUsEntity> getByResult(int result);

}
