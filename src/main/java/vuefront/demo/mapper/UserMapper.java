package vuefront.demo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import vuefront.demo.pojo.User;

import java.util.List;


@Mapper
public interface UserMapper extends BaseMapper<User> {

    /**
     *
     * @param pageNum  表示从第几页开始
     * @param pageSize  表示这一页显示多少内容
     * @return
     */
//    List<User> getPage(@Param("pageNum") Integer pageNum,@Param("pageSize") Integer pageSize,@Param("username") String username);
//
//    // (pageNum-1)*pageSize , pageSize   页数-1*展示数量=从第几条开始，后面跟展示多少条数据
//
//
//    Integer selectCount(String username);


}
