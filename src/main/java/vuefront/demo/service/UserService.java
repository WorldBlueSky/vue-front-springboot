package vuefront.demo.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vuefront.demo.mapper.UserMapper;
import vuefront.demo.pojo.User;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

@Service
public class UserService extends ServiceImpl<UserMapper,User> {

    @Autowired
    private UserMapper userMapper;

    public Integer saveNewUser(User user){

        // 设置时间
        Date dNow = new Date();
        user.setCreateTime(dNow);

        // 作用与下面一样,自动判断id是否存在
//        return saveOrUpdate(user);

       // id 是 Integer类型的，如果为null，说明没有是一个新用户
        if(user.getId()==null){
            user.setId(0);// 如果没有id，或者id为null，主键不能设置为null，需要我们设置一个0，到时候自动递增
            return userMapper.insert(user);
        }else {
            // 否则表示这个用户已经存在id，不能新增,只能修改更新
            return userMapper.updateById(user); // 根据id进行更新
        }
    }

}
