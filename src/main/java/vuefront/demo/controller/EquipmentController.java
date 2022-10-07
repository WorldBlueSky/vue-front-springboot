package vuefront.demo.controller;

import cn.hutool.core.date.DateTime;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import vuefront.demo.pojo.Equipment;
import vuefront.demo.service.EquipmentService;
import vuefront.demo.util.TimeFormat;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/equipment")
@CrossOrigin(origins = "*",allowCredentials="true",allowedHeaders = "*")
@Slf4j
public class EquipmentController {

    @Autowired
    private EquipmentService equipmentService;

    /**
     * 插入一条 设备记录
     * @param equipment
     * @return
     */
    @PostMapping("/add")
    public boolean add(@RequestBody Equipment equipment){
        equipment.setId(0);// 设置主键默认值
        log.info("增加的设备信息为 {}", equipment.toString());
        return equipmentService.save(equipment);
    }

    /**
     * 根据 id 删除具体的 记录
     * @param id
     * @return
     */
    @DeleteMapping("/delete/{id}")
    public boolean del(@PathVariable("id") Integer id){
        log.info("打印的id是{}",id);
        return equipmentService.removeById(id);
    }

    /**
     *  批量删除 设备记录
     * @param ids
     * @return
     */
    @PostMapping("/delete/batch")
    public boolean deleteByBatch(@RequestBody List<Integer> ids){  // [0,1,2,3] 批量删除
        // 执行批量删除的方法
        return equipmentService.removeByIds(ids);
    }

    /**
     *  根据id进行 修改记录
     */
    @PutMapping("/update")
    public boolean update(@RequestBody Equipment equipment){
        equipment.setCreateTime(new Date());
        return equipmentService.updateById(equipment);
    }

    /**
     * 分页查询，条件查询
     * @param pageNum 前端提供的 第几页
     * @param pageSize 前端提供的 每页几条记录
     * @param name 是否按照设备名称进行模糊查询
     * @return
     */
    @GetMapping("/page")
    public IPage<Equipment> page(@RequestParam("pageNum") Integer pageNum,
                                 @RequestParam("pageSize") Integer pageSize,
                                 @RequestParam(required = false) String name){
        // 创建分页的实例对象
        IPage<Equipment> page = new Page<>(pageNum,pageSize);

        // 如果此时name不为空，为带有条件的分页查询，需要创建条件对象
        QueryWrapper<Equipment> wrapper = new QueryWrapper<>();
        if(!StringUtils.isEmpty(name)){
            wrapper.like("name", name);
        }
        // 返回分页的结果
        return equipmentService.page(page, wrapper);
    }


}
