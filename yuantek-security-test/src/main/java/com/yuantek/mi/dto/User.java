package com.yuantek.mi.dto;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonView;
import com.yuantek.mi.validator.MyConstraint;
import lombok.Setter;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.Past;
import java.util.Date;

/**
 * 封装RestfulApi的输入输出数据对象
 *
 * @JsonView使用步骤 1. 使用接口来声明多个视图
 * 2. 在值对象的get方法上指定视图(哪个字段在哪个视图上显示)
 * 3. 在controller方法上指定视图
 * <p>
 * 对于时间类型的在前后端分离的架构中要传时间戳,后端只负责回传数据,前端做格式转换,这也是前后端分离架构的思想,前端决定如何展示
 */
@Setter
// 如果字段为null,就不返回给前端
//@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
@JsonInclude(value = JsonInclude.Include.NON_NULL)
public class User {
    private String id;
    @MyConstraint(message = "这是一个测试的校验注解")
    private String username;
    @NotBlank(message = "密码不能为空")
    private String password;
    @Past(message = "生日必须为过去的时间")
    private Date birthday;

    public interface UserSimpleView {
    }

    public interface UserDetailView extends UserSimpleView {
    }

    @JsonView(UserSimpleView.class)
    public String getId() {
        return id;
    }

    @JsonView(UserSimpleView.class)
    public String getUsername() {
        return username;
    }

    @JsonView(UserDetailView.class)
    public String getPassword() {
        return password;
    }

    @JsonView(UserSimpleView.class)
    public Date getBirthday() {
        return birthday;
    }
}
