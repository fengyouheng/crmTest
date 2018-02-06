package cn.wolfcode.crm.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Setter@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class SystemDictionary {
    private Long id;

    private String sn;

    private String name;

    private String intro;

}