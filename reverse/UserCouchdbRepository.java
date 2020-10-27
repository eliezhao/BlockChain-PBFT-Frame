package bc_demo.reverse;

import org.springframework.data.couchbase.core.query.Dimensional;
import org.springframework.data.couchbase.core.query.View;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.geo.Box;

import java.util.List;

/**
 * @author ZhaoGao
 * @date 2020/7/10 - 21:53 - JavaProjects
 *
 * 构建对应Couchdb的JPA类 UserCouchdbRepository
 * */




public interface UserCouchdbRepository extends
        CrudRepository<UserCouchdb, String> {
    List<UserCouchdb> findByLastnameAndAgeBetween(String lastName, int minAge, int maxAge);

    @View(designDocument = "user", viewName = "byName")
    List<UserCouchdb> findByLastname(String lastName);

    @Dimensional(designDocument = "userGeo", spatialViewName = "byLocation")
    List<UserCouchdb> findByLocationWithin(Box cityBoundingBox);







}


