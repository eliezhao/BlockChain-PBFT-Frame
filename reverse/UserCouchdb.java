package bc_demo.reverse;

import org.springframework.data.annotation.Id;
import org.springframework.data.couchbase.core.mapping.Field;

/**
 * @author ZhaoGao
 * @date 2020/7/10 - 21:40 - JavaProjects
 */
public class UserCouchdb {
    @Id
    private String id;

    /**
     * 姓
     */
    @Field
    private String lastName;

    /**
     * 性别 1:girl 0:boy
     */
    @Field
    private int sex;

    /**
     * 年龄
     */
    @Field
    private int age;

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }


    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }


}
