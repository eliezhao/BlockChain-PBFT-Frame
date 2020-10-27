package bc_demo.reverse;

import org.rocksdb.RocksDB;
import org.rocksdb.RocksDBException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import com.google.common.base.Strings;

import javax.annotation.Resource;
import java.io.UnsupportedEncodingException;

/**
 * @author ZhaoGao
 * @date 2020/7/9 - 19:22 - JavaProjects
 */
@Component
public class RocksdbDAO {
    //日志记录
    private Logger logger = LoggerFactory.getLogger(RocksdbDAO.class);

    @Resource
    private RocksDB rocksDB;

    private static final String CHARSET = "utf-8";

    //存入数据或更改
    public void put(String key, String value) {
        if (Strings.isNullOrEmpty(key) || Strings.isNullOrEmpty(value)) {
            return;
        }

        try {
            rocksDB.put(key.getBytes(CHARSET), value.getBytes(CHARSET));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (RocksDBException e) {
            e.printStackTrace();
        }
    }

    //获取数据
    public String get(String key) {
        if (Strings.isNullOrEmpty(key)) {
            return null;
        }

        try {
            byte[] bytes = rocksDB.get(key.getBytes(CHARSET));
            if (bytes != null) {
                return new String(bytes);
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (RocksDBException e) {
            e.printStackTrace();
        }
        return null;
    }


    //删除数据
    public void delete(String key) {
        if (Strings.isNullOrEmpty(key)) {
            return;
        }

        try {
            rocksDB.delete(rocksDB.get(key.getBytes(CHARSET)));
        } catch (RocksDBException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }


    }



}
