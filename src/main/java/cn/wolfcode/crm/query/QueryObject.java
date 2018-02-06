package cn.wolfcode.crm.query;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class QueryObject {
    private int page;
    private int rows;

    public int getStart(){
        return (this.page - 1) * rows;
    }
}
