package vuefront.demo;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import vuefront.demo.mapper.UserMapper;
import vuefront.demo.pojo.User;

import java.util.Date;
import java.util.List;

@SpringBootTest
class DemoApplicationTests {

    @Autowired
    private UserMapper userMapper;

    @Test
    void contextLoads() {

    }

}
