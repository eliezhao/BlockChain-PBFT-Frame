package bc_demo.reverse;

/**
 * @author ZhaoGao
 * @date 2020/7/10 - 20:31 - JavaProjects
 */

import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.data.couchbase.config.AbstractCouchbaseConfiguration;
import org.springframework.data.couchbase.repository.config.EnableCouchbaseRepositories;

import java.util.Arrays;
import java.util.List;

@Configurable
@EnableCouchbaseRepositories
public class CouchdbConfig extends AbstractCouchbaseConfiguration {

    //启动时建立配置的集群的连接，客户端将发现所有节点病保持群集映射最新
    protected List<String> getBootstrapHosts() {
        return Arrays.asList("host1", "host2");
    }

    @Override
    public String getConnectionString() {
        return null;
    }

    @Override
    public String getUserName() {
        return null;
    }

    @Override
    public String getPassword() {
        return null;
    }

    //配置主数据桶的名称
    @Override
    public String getBucketName() {
        return "default";
    }

    //配置主数据桶的密码
    public String getBucketPassword() {
        return "";
    }


}