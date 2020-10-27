package bc_demo.control;

import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author ZhaoGao
 * @date 2020/8/17 - 19:55 - JavaProjects
 */
public class JoinToUsServiceImpl implements JoinToUsService{

    @Autowired
    private IJoinToUsDAO dao;

    //添加机构
    @Override
    public void join(String orgName, String orgPhone, String orgRepresent) {
        JoinToUsEntity entity =
                dao.getByOrgNameAndOrgRepresent(orgName, orgPhone, orgRepresent);

        //若已经录入 不在录入
        if (entity != null) {
            return;
        }

        //尚未录入
        JoinToUsEntity joinToUsEntity = new JoinToUsEntity();
        joinToUsEntity.setOrgName(orgName);
        joinToUsEntity.setOrgPhone(orgPhone);
        joinToUsEntity.setOrgRepresent(orgRepresent);
        joinToUsEntity.setResult(0);

        dao.save(joinToUsEntity);
    }



}
