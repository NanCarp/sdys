package stms.warehouse.stockinandoutdomestic;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.IAtom;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;

public class StockInAndOutDomesticService {

    public static Page<Record> getDataPages(Integer pageindex, Integer pagelimit) {
        return getDataPages(pageindex, pagelimit, null, null, null);
    }

    public static Page<Record> getDataPages(Integer pageindex, Integer pagelimit, String in_date, String company_name,
            String material_no) {
        String select = "SELECT a.in_date,a.storage_location AS in_storage_location,a.company_name AS in_company_name,a.material_no AS in_material_no, "
                + " a.material AS in_material,a.batch_no AS in_batch_no,a.tray_no AS in_tray_no,"
                + " a.in_quantity,a.in_tray_quantity,a.module_power AS in_module_power, b.*, "
                + " a.in_quantity - b.out_quantity AS real_time_quantity, "
                + " a.in_tray_quantity - b.out_tray_quantity AS real_time_tray_quantity, "
                + " IF(ISNULL(delivery_no), DATEDIFF(NOW(), a.in_date ) + 1, DATEDIFF(b.out_date, a.in_date )) AS retention_days, "
                + " IF(ISNULL(b.out_date), NOW(), b.out_date) AS out_date2, "
                + " c.currency,c.amount ";
        String sql = " FROM `t_domes_in_warehouse` AS a "
                + " LEFT JOIN t_domes_out_warehouse AS b "
                + " ON a.batch_no = b.batch_no "
                + " LEFT JOIN t_standard_charge_domestic AS c "
                + " ON DATE_FORMAT(a.in_date,'%Y%m') = c.period AND a.storage_location = c.location "
                + " WHERE 1 = 1";
        
        if (company_name != null && !"".equals(company_name)) {
            sql += " AND a.company_name like '%" + company_name + "%'";
        }
        /*if (in_date != null && !"".equals(in_date)) {
            sql += " AND in_date = '" + in_date + "'";
        }*/
        if (material_no != null && !"".equals(material_no)) {
            sql += " AND a.material_no like '%" + material_no + "%'";
        }
        
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
        Date in_date = Db.findFirst("SELECT * FROM t_domes_in_warehouse WHERE batch_no = ?", batch_no).getDate("in_date");
        Date out_date = null;
        List<Record> list2 = Db.find("SELECT * FROM t_domes_out_warehouse WHERE batch_no = ?", batch_no);
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
                String sql1 = "SELECT * FROM t_domes_in_warehouse WHERE batch_no = '" + batch_no + "'";
                System.out.println(sql1);
                Record record1 = Db.find("SELECT * FROM t_domes_in_warehouse WHERE batch_no = '" + batch_no + "'").get(0);
                record1.set("in_date", in_date);
                result = Db.update("t_domes_in_warehouse", record1);
                if (result == false) {
                    return false;
                }
                if(out_date != null && !"".equals(out_date)) {
                    Record record2 = Db.find("SELECT * FROM t_domes_out_warehouse WHERE batch_no = '" + batch_no + "'").get(0);
                    record2.set("out_date", out_date);
                    result = Db.update("t_domes_out_warehouse", record2);
                    if (result == false) {
                        return false;
                    }
                }
                
                return result;
            }
            
        });
    }
     
}
