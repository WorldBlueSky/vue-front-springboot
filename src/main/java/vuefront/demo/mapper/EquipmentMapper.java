package vuefront.demo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import vuefront.demo.pojo.Equipment;

@Mapper
public interface EquipmentMapper extends BaseMapper<Equipment> {

// 继承了所有的增删改查方法

}
