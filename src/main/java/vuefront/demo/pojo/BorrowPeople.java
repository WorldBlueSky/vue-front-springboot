package vuefront.demo.pojo;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BorrowPeople {
     private int id;//申请编号
     private String name;//姓名
     private String type;//类别
     private String sex;//性别
     private int age;//年龄
     private String phone;//手机号
     @TableField(value = "create_time")
     private Date createTime;//操作时间
}
