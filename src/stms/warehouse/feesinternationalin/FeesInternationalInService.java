package stms.warehouse.feesinternationalin;

import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;

class FeesInternationalInService {

    protected static Page<Record> getDataPages(Integer pageindex, Integer pagelimit) {
        String sql = " FROM t_inter_warehouse_fees_in WHERE 1=1 ";
        
        return Db.paginate(pageindex, pagelimit, "SELECT * ", sql);
    }
    
}
