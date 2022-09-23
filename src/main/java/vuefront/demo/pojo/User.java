package vuefront.demo.pojo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.ibatis.type.Alias;

import java.util.Date;

@TableName("sys_user")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @TableId
    private Integer id;
    private String username;
    @JsonIgnore // 忽略展示
    private String password;
    private String nickname;
    private String email;
    private String address;
    private String phone;
    @TableField("create_time")
    private Date createTime;
}
