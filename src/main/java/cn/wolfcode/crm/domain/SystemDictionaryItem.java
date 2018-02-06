package cn.wolfcode.crm.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter@Getter
@NoArgsConstructor
@AllArgsConstructor
public class SystemDictionaryItem {
    private Long id;

    private String name;

    private String intro;
    
    private String sn;

    private SystemDictionary parent;

}