package cn.wolfcode.crm.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;

@Setter
@Getter
@ToString
public class Employee {
    public static final String DEFAULT_PASSWORD = "666";
    public static final int IN_JOB = 0;
    public static final int LEAVE = 1;

    private Long id;
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GTM+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date inputTime;
    private String username;
    private String realname;
    private String password;
    private String tel;
    private String email;
    private Boolean admin;
    private Integer state;
    private Department dept;
    private List<Role> roles = new ArrayList<>();
}
