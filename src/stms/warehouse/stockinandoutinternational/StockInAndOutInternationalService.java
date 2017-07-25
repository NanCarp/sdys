package stms.warehouse.stockinandoutinternational;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.IAtom;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;

/**
 * @ClassName: StockInAndOutInternationalService.java
 * @Description:
 * @author: LiYu
 * @date: 2017年7月19日上午8:27:24
 * @version: 1.0 版本初成
 */
public class StockInAndOutInternationalService {

    public static Page<Record> getDataPages(Integer pageindex, Integer pagelimit) {
        return getDataPages(pageindex, pagelimit, null, null, null);
    }

    public static Page<Record> getDataPages(Integer pageindex, Integer pagelimit, String in_date, String company_name,
            String material_no) {
        String select = "SELECT *, "
                + " a.in_quantity - b.out_quantity AS real_time_quantity, "
                + " a.in_tray_quantity - b.out_tray_quantity AS real_time_tray_quantity, "
                + " IF(ISNULL(delivery_no), DATEDIFF(NOW(), a.in_date ) + 1, DATEDIFF(b.out_date , a.in_date )) AS retention_days, "
                + " IF(ISNULL(b.out_date), NOW(), b.out_date) AS out_date2, "
                + " c.currency,c.amount ";
        String sql = " FROM `t_inter_in_warehouse` AS a "
                + " LEFT JOIN t_inter_out_warehouse AS b "
                + " ON a.in_batch_no = b.batch_no "
                + " LEFT JOIN t_standard_charge_domestic AS c "
                + " ON DATE_FORMAT(a.in_date,'%Y%m') = c.period AND a.in_storage_location = c.location "
                + " WHERE 1 = 1";
        
        if (company_name != null && !"".equals(company_name)) {
            sql += " AND a.in_company_name like '%" + company_name + "%'";
        }
        /*if (in_date != null && !"".equals(in_date)) {
            sql += " AND in_date = '" + in_date + "'";
        }
        if (material_no != null && !"".equals(material_no)) {
            sql += " AND material_no like '%" + material_no + "%'";
        }*/
        // System.out.println(select + sql);
        return Db.paginate(pageindex, pagelimit, select, sql);
    }

    /** 
    * @Title: getRecord 
    * @Description: 单条出入库明细数据
    * @param batch_no
    * @return Record
    * @author liyu
    */
    public static Record getRecord(String batch_no) {
        Date in_date = Db.findFirst("SELECT * FROM t_inter_in_warehouse WHERE in_batch_no = ?", batch_no).getDate("in_date");
        Date out_date = null;
        List<Record> list2 = Db.find("SELECT * FROM t_inter_out_warehouse WHERE batch_no = ?", batch_no);
        if (list2.size() > 0) {
            out_date = list2.get(0).getDate("out_date");
        }
        
        Record record = new Record();
        record.set("batch_no", batch_no);
        record.set("in_date", in_date);
        record.set("out_date", out_date);
        
        return record;
    }

    /** 
    * @Title: save 
    * @Description: 保存出入库日期
    * @param batch_no
    * @param in_date
    * @param out_date
    * @return boolean
    * @author liyu
    */
    public static boolean save(String batch_no, String in_date, String out_date) {
        return Db.tx(new IAtom() {
            boolean result = false;
            @Override
            public boolean run() throws SQLException {
                String sql1 = "SELECT * FROM t_inter_in_warehouse WHERE in_batch_no = '" + batch_no + "'";
                System.out.println(sql1);
                Record record1 = Db.find("SELECT * FROM t_inter_in_warehouse WHERE in_batch_no = '" + batch_no + "'").get(0);
                record1.set("in_date", in_date);
                result = Db.update("t_inter_in_warehouse", record1);
                if (result == false) {
                    return false;
                }
                if(out_date != null && !"".equals(out_date)) {
                    Record record2 = Db.find("SELECT * FROM t_inter_out_warehouse WHERE batch_no = '" + batch_no + "'").get(0);
                    record2.set("out_date", out_date);
                    result = Db.update("t_inter_out_warehouse", record2);
                    if (result == false) {
                        return false;
                    }
                }
                
                return result;
            }
            
        });
    }
     
}
