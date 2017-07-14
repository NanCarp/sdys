package stms.warehouse;

import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;

public class StockInDomesticService {

    public static Page<Record> getDataPages(Integer pageindex, Integer pagelimit) {
        String sql = " FROM t_domes_in_warhouse WHERE 1=1";
        return Db.paginate(pageindex, pagelimit, "SELECT * ", sql);
    }

}
