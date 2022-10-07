package vuefront.demo.pojo;

import cn.hutool.core.annotation.Alias;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@TableName("sys_user")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @TableId
    @Alias("编号") // 使得excel的标题识别实体类的属性
    private Integer id;
    @Alias("用户名")
    private String username;
    @Alias("密码")
    private String password;
    @Alias("昵称")
    private String nickname;
    @Alias("邮箱")
    private String email;
    @Alias("地址")
    private String address;
    @Alias("手机")
    private String phone;
    @TableField("create_time")
    @Alias("创建时间")
    private Date createTime;
}
