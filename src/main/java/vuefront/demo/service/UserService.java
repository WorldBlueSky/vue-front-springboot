package vuefront.demo.service;

import cn.hutool.core.lang.hash.Hash;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import vuefront.demo.controller.UserDTO;
import vuefront.demo.mapper.UserMapper;
import vuefront.demo.pojo.User;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
@Slf4j
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


    // 实现简单用户登陆
    public Map<String,Object> userSimpleLogin(UserDTO userDTO){
        Map<String,Object> map = new HashMap<>();

        if(StringUtils.isEmpty(userDTO.getPassword()) || StringUtils.isEmpty(userDTO.getUsername())){
            map.put("state", 0);
            map.put("message","用户名或密码不能为空，请重新输入!" );
            return map;
        }

        QueryWrapper<User> nameWrapper = new QueryWrapper<>();
        nameWrapper.eq("username", userDTO.getUsername());

        // 先查用户名是否存在
        User user = userMapper.selectOne(nameWrapper);
        System.out.println(user);
        if(user==null){
            map.put("state",1);
            map.put("message", "该用户名不存在!");
            return map;
        }

        // 存在的话，查找用户名和密码是否匹配
        if(!userDTO.getPassword().equals(user.getPassword())){
            map.put("state",2);
            map.put("message", "密码输入错误，请重新输入!");
            return map;
        }

        map.put("state",3);
        map.put("message", "登陆成功!");
        return map;
    }

    // 业务层实现注册用户
    public Map<String,Object> registerUser(UserDTO userDTO){

        Map<String,Object> map = new HashMap<>();

        if(StringUtils.isEmpty(userDTO.getUsername())||StringUtils.isEmpty(userDTO.getPassword())){
            map.put("state", 0);
            map.put("message","用户名或密码不能为空，请重新输入!" );
            return map;
        }

        User user = new User();
        user.setId(0);// id默认设置为0，设置进入数据库自动生成自增id
        user.setUsername(userDTO.getUsername());
        user.setPassword(userDTO.getPassword());

        // 先查找是否有相同的用户名，如果有的话，那么不能进行占用
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username", user.getUsername());

        if(userMapper.selectOne(queryWrapper)!=null){
             // 说明被占用
            map.put("state", 1);
            map.put("message","用户名已经被他人注册，请重新输入!" );
            return map;
        }

        // 如果用户名不相同的话，可以直接进行插入操作
        int ret = userMapper.insert(user);
        if(ret==0){
            map.put("state", 2);
            map.put("message","注册异常，插入数据库失败!" );
            return map;
        }

        map.put("state", 3);
        map.put("message","注册成功!" );
        return map;
    }
}
