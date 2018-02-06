package cn.wolfcode.crm.domain;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter@Getter
@NoArgsConstructor
@AllArgsConstructor
public class SystemLog {
    private Long id;

    private Long employeeId;

    private Date operateTime;

    private String ip;

    private String function;

    private String params;

}