package cn.wolfcode.crm.domain;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Department {
    private Long id;
    private String sn;
    private String name;
    private Integer state;
    
    private Employee manager ;
    private Department parent ;
}
