package bc_demo.control;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * @author ZhaoGao
 * @date 2020/8/15 - 16:49 - JavaProjects
 */

@Entity
@Table(name = "tb_user_tojoin")
public class JoinToUsEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //机构名称
    private String orgName;

    //机构联系方式
    private String orgPhone;

    //机构联系人
    private String orgRepresent;

    //申请时间
    private Date createTime = new Date();

    //申请结果 已处理是1 未处理是0
    private int result = 0;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    public String getOrgPhone() {
        return orgPhone;
    }

    public void setOrgPhone(String orgPhone) {
        this.orgPhone = orgPhone;
    }

    public String getOrgRepresent() {
        return orgRepresent;
    }

    public void setOrgRepresent(String orgRepresent) {
        this.orgRepresent = orgRepresent;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public int getResult() {
        return result;
    }

    public void setResult(int result) {
        this.result = result;
    }
}
