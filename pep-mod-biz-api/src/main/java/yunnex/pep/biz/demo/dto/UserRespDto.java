package yunnex.pep.biz.demo.dto;

import java.io.Serializable;

import lombok.Data;

@Data
public class UserRespDto implements Serializable {

    private String id;
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

}
