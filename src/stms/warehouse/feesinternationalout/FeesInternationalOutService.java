package stms.warehouse.feesinternationalout;

import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;

class FeesInternationalOutService {

    protected static Page<Record> getDataPages(Integer pageindex, Integer pagelimit) {
        String sql = " FROM t_inter_warehouse_fees_out WHERE 1=1 ";
        
        return Db.paginate(pageindex, pagelimit, "SELECT * ", sql);
    }
    
}
