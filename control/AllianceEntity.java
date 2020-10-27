package bc_demo.control;


import javax.persistence.*;
import java.util.Date;

/**
 * @author ZhaoGao
 * @date 2020/8/18 - 22:05 - JavaProjects
 */
@Entity
@Table(name = "tb_alliance")
public class AllianceEntity {
    //数据库ID
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    //联盟节点ID
    private String allianceId;

    //联盟节点名称
    private String allianceName;

    //联盟节点IP白名单
    private String allianceIp;

    //联盟节点信息创建时间
    private Date createTime;

    //联盟节点信息更新时间
    private Date updateTime;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getAllianceId() {
        return allianceId;
    }

    public void setAllianceId(String allianceId) {
        this.allianceId = allianceId;
    }

    public String getAllianceName() {
        return allianceName;
    }

    public void setAllianceName(String allianceName) {
        this.allianceName = allianceName;
    }

    public String getAllianceIp() {
        return allianceIp;
    }

    public void setAllianceIp(String allianceIp) {
        this.allianceIp = allianceIp;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}
