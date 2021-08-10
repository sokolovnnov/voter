package ru.antisida.voter.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.transaction.annotation.Transactional;
import ru.antisida.voter.repo.JpaUtil;

import static ru.antisida.voter.util.ValidationUtil.getRootCause;

@SpringJUnitConfig(locations = {
        "classpath:spring/spring-app.xml",
        "classpath:spring/spring-db.xml"
})
@Transactional
//@Sql(scripts = "classpath:db/populateDB.sql", config = @SqlConfig(encoding = "UTF-8"))
public abstract class AbstractServiceTest {

    @Autowired
    protected JpaUtil jpaUtil;

    @Autowired
    private CacheManager cacheManager;

    @BeforeEach
    public void setup() {
        cacheManager.getCache("dateMenu").clear();
        cacheManager.getCache("restaurantMenu").clear();
        cacheManager.getCache("menu").clear();
        cacheManager.getCache("vote").clear();
        jpaUtil.clear2ndLevelHibernateCache();
    }

    //  Check root cause in JUnit: https://github.com/junit-team/junit4/pull/778
    protected <T extends Throwable> void validateRootCause(Class<T> rootExceptionClass, Runnable runnable) {
        Assertions.assertThrows(rootExceptionClass, () -> {
            try {
                runnable.run();
            } catch (Exception e) {
                throw getRootCause(e);
            }
        });
    }


}
