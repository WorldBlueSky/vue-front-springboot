package vuefront.demo.controller;

import cn.hutool.poi.excel.ExcelReader;
import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import vuefront.demo.mapper.UserMapper;
import vuefront.demo.pojo.User;
import vuefront.demo.service.UserService;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
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

    @PostMapping("/delete/batch")
    public boolean deleteByBatch(@RequestBody List<Integer> ids){  // [0,1,2,3] 批量删除
        // 执行批量删除的方法
        return userService.removeByIds(ids);
    }

    // 导出的接口，将数据库中的数据生成放到excel中，下载到浏览器本地
    @GetMapping("/export")
    public void export(HttpServletResponse response) throws IOException {
        // 查询数据库中所有的数据
        List<User> list = userService.list();
        // 在内存操作，写入到浏览器中
        ExcelWriter excelWriter = ExcelUtil.getWriter(true);// 设置成xlsx格式
        // 自定义标题的名字
//        excelWriter.addHeaderAlias("id", "编号");
//        excelWriter.addHeaderAlias("username", "用户名");
//        excelWriter.addHeaderAlias("password", "密码");
//        excelWriter.addHeaderAlias("nickname", "昵称");
//        excelWriter.addHeaderAlias("email", "邮箱");
//        excelWriter.addHeaderAlias("phone", "电话");
//        excelWriter.addHeaderAlias("address", "地址");
//        excelWriter.addHeaderAlias("createTime", "创建时间");
        // 将list内的对象一次性全部写入到true，使用默认样式，强制输出标题
        excelWriter.write(list,true);
        // 设置浏览器响应的格式（固定）
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=utf-8");
        // 用户下载的文件名字，解码格式
        String fileName = URLEncoder.encode("用户信息", "UTF-8");
        response.setHeader("Content-Disposition", "attachment;filename="+fileName+".xlsx");
        // 获取输出流
        ServletOutputStream outputStream = response.getOutputStream();
        // 强制刷新wirte的缓存区，用完关闭资源
        excelWriter.flush(outputStream,true);
        outputStream.close();
        excelWriter.close();
    }

    @PostMapping("/import")
    public Boolean imp(@RequestPart MultipartFile file) throws IOException {
        InputStream inputStream = file.getInputStream();// 获取文件输入流
        // excel读取输入流的内容
        ExcelReader excelReader = ExcelUtil.getReader(inputStream);
        // 将excel转化为实体类的信息
        List<User> list = excelReader.readAll(User.class);
        for (User user:list) {
            // 防止用户输入id导致id占用产生异常，导入的时候应该是自增的
            userService.saveNewUser(user);
        }
        return true;
    }

    @PostMapping("/login")
    public Map<String, Object> login(@RequestBody UserDTO userDTO){
        // 登陆的接口
        return userService.userSimpleLogin(userDTO);
    }

    @PostMapping("/register")
    public Map<String, Object> register(@RequestBody UserDTO userDTO){
        // 登陆的接口
        return userService.registerUser(userDTO);
    }
}
