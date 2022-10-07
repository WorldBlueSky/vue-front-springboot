package vuefront.demo.pojo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("equipment")
public class Equipment {
    private Integer id; //设备编号,自增默认设置成0
    private String name;//设备名称
    private String typename;//设备类别
    private Integer number;//当前数量
    private String unit;//数量单位
    private String location;//存放地点
    @TableField("purchase_Date")
    private Date purchaseDate;//购入日期
    private Double price;//购入单价
    @TableField("create_time")
    private Date createTime; //保存时间
}
