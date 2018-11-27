package yunnex.pep.biz.demo.dto;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import lombok.Data;

@Data
public class UserReqDto extends Page<UserRespDto> {

    private static final long serialVersionUID = 1L;

    /**
     * 姓名
     */
    private String name;

    /**
     * 年龄
     */
    private Integer age;

    /**
     * 邮箱
     */
    private String email;

    // 和Page的long total不兼容，即不可出现和Page同名的属性
    //private Long total;


}
