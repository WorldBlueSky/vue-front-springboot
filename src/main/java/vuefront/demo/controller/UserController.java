package vuefront.demo.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import vuefront.demo.mapper.UserMapper;
import vuefront.demo.pojo.User;
import vuefront.demo.service.UserService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/user")
@CrossOrigin(origins = "*",allowCredentials="true",allowedHeaders = "*")
public class UserController {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private UserService userService;

    @GetMapping
    public List<User> index(){
        return  userService.list();
    }

    /**
     * 前端传递 JSON 对象类型的 数据，进行增加用户信息
     *  新增或修改用户数据
     * @param user
     * @return
     */

    @PostMapping
    public Map<String, Object> save(@RequestBody User user){
        // 新增或更新

        Map<String ,Object> map = new HashMap<>();

         int result =  userService.saveNewUser(user);
         if(result==1){
             map.put("message", "更新成功!");
         }else{
             map.put("message", "更新失败!");
         }

         return map;
    }

    // 使用 restFul风格删除
    @DeleteMapping("/{id}")
    public boolean delete(@PathVariable(value = "id") Integer id){
        //log.info("传递的参数为 {}", id);
        return userService.removeById(id);
    }

    @GetMapping("/page") // 传递页数的参数， ？pageNum=...&pageSize=...
    public IPage<User> findPage(@RequestParam("pageNum") Integer pageNum ,
                                @RequestParam("pageSize") Integer pageSize,
                                @RequestParam String username,
                                @RequestParam String email,
                                @RequestParam String phone
     ){
        // 这里如果数据库中字段为null，即使 %% 也匹配不上，所以使用StringUtils工具判断是否为null，符合的话再like匹配，否则不进行like查询

        IPage<User> page  = new Page<>(pageNum,pageSize);
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();

        if(!StringUtils.isEmpty(username))
        queryWrapper.like("username", username);

        if(!StringUtils.isEmpty(email))
        queryWrapper.like("email", email);

        if(!StringUtils.isEmpty(phone))
        queryWrapper.like("phone", phone);

        return userService.page(page,queryWrapper);

        // 返回的IPage中包含关于分页的很多相关信息，我们只需要 total、records


//        // 表示从第几条开始展示，表示这一页数据的起始标号
//        pageNum = (pageNum-1)*pageSize;
//
//        // 查询数据库一共有多少条数据
//        Integer total = userMapper.selectCount(username);
//        // 查询指定页数的数据
//        List<User> list = userMapper.getPage(pageNum, pageSize,username);
//
//        Map<String,Object> map = new HashMap<>();
//        map.put("total", total);
//        map.put("users", list);
//
//        return map;

    }
}
