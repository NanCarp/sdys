package stms.warehouse.standardchargeinternational;

import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;

class StandardChargeInternationalService {

    protected static Page<Record> getDataPages(Integer pageindex, Integer pagelimit) {
        String sql = " FROM t_standard_charge_international WHERE 1=1 ";
        
        return Db.paginate(pageindex, pagelimit, "SELECT * ", sql);
    }
    
}
