package stms.warehouse.standardchargedomestic;

import java.sql.SQLException;
import java.util.List;

import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.IAtom;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;

/**
 * @ClassName: StandardChargeDomesticService.java
 * @Description:
 * @author: LiYu
 * @date: 2017年7月21日下午1:25:48
 * @version: 1.0 版本初成
 */
class StandardChargeDomesticService {

    /** 
    * @Title: getDataPages 
    * @Description: 仓储收费标准
    * @param pageindex
    * @param pagelimit
    * @return Page<Record>
    * @author liyu
    */
    protected static Page<Record> getDataPages(Integer pageindex, Integer pagelimit) {
        return getDataPages(pageindex,pagelimit, null);
    }

    /** 
    * @Title: getDataPages 
    * @Description: 仓储收费标准
    * @param pageindex
    * @param pagelimit
    * @param company_name
    * @return Page<Record>
    * @author liyu
    */
    public static Page<Record> getDataPages(Integer pageindex, Integer pagelimit, String company_name) {
        String sql = " FROM t_standard_charge_domestic WHERE 1=1 ";
        if (company_name != null && !"".equals(company_name)) {
            sql += " AND company_name like '%" + company_name + "%'";
        }
        
        return Db.paginate(pageindex, pagelimit, "SELECT * ", sql);
    }
    
    /** 
    * @Title: getCompanyList 
    * @Description: 物流公司列表
    * @return List<Record>
    * @author liyu
    */
    public static List<Record> getCompanyList() {
        String sql = "SELECT *  " +
                "FROM t_company " +
                "WHERE state = 1 " ;
        return Db.find(sql);
    }

    /** 
    * @Title: getCurrencyList 
    * @Description: 币制列表
    * @return List<Record>
    * @author liyu
    */
    public static List<Record> getCurrencyList() {
        String sql = "SELECT * FROM `t_dictionary` WHERE `key` = 'currency'";
        return Db.find(sql);
    }

    /** 
    * @Title: isDuplicate 
    * @Description: 重复检测
    * @param period
    * @param company_name
    * @return boolean
    * @author liyu
    */
    public static boolean isDuplicate(String period, String company_name) {
        String sql = "SELECT * FROM `t_standard_charge_domestic` WHERE period = ? AND company_name = ?";
        return Db.find(sql, period, company_name).size() > 0;
    }

    /** 
    * @Title: delete 
    * @Description: 批量删除
    * @param ids
    * @return boolean
    * @author liyu
    */
    public static boolean delete(String[] ids) {
        boolean flag = Db.tx(new IAtom() {
            boolean result = true;
            @Override
            public boolean run() throws SQLException {
                for(String id:ids){
                    result = Db.deleteById("t_standard_charge_domestic", id);
                    if (result == false) {
                        break;
                    }
                }
                return result;
            }
        });
        return flag;
    }

    
    
}
