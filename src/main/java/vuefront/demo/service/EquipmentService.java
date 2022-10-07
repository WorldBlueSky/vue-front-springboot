package vuefront.demo.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vuefront.demo.mapper.EquipmentMapper;
import vuefront.demo.pojo.Equipment;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class EquipmentService extends ServiceImpl<EquipmentMapper, Equipment> {

    @Autowired
    private EquipmentMapper equipmentMapper;



}
