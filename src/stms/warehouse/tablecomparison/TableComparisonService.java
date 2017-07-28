package stms.warehouse.tablecomparison;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpSession;

import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.IAtom;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;
import com.jfinal.upload.UploadFile;

import stms.utils.ExcelKit;

public class TableComparisonService {

    public static Page<Record> getDataPages(Integer pageindex, Integer pagelimit) {
        return getDataPages(pageindex, pagelimit, null, null);
    }

    public static Page<Record> getDataPages(Integer pageindex, Integer pagelimit, String location, String company_name) {
        String sql = " FROM "
                + " (SELECT c.storage_location,c.company_name,SUM(c.real_time_quantity) AS quality,SUM(c.real_time_tray_quantity) AS tray_quality "
                + " FROM "
                + " (SELECT a.storage_location,a.company_name,a.material_no,a.material,a.batch_no,a.tray_no,a.module_power, "
                + " IF(ISNULL(b.out_date), a.in_quantity, a.in_quantity - b.out_quantity) AS real_time_quantity, "
                + " IF(ISNULL(b.out_date), a.in_tray_quantity, a.in_tray_quantity - b.out_tray_quantity) AS real_time_tray_quantity "
                + " FROM `t_domes_in_warehouse` AS a "
                + " LEFT JOIN t_domes_out_warehouse AS b "
                + " ON a.batch_no = b.batch_no "
                + " UNION "
                + " SELECT a.in_storage_location,a.in_company_name,a.in_material_no,a.in_material,a.in_batch_no,a.in_tray_no,a.in_module_power, "
                + " IF(ISNULL(b.out_date), a.in_quantity, a.in_quantity - b.out_quantity) AS real_time_quantity, "
                + " IF(ISNULL(b.out_date), a.in_tray_quantity, a.in_tray_quantity - b.out_tray_quantity) AS real_time_tray_quantity "
                + " FROM `t_inter_in_warehouse` AS a "
                + " LEFT JOIN t_inter_out_warehouse AS b "
                + " ON a.in_batch_no = b.batch_no) AS c "
                + " GROUP BY c.storage_location, c.company_name) AS d "
                + " LEFT JOIN t_table_comparison  AS e "
                + " ON d.company_name = e.company_name2 AND d.storage_location = e.location2";
        
        if (company_name != null && !"".equals(company_name)) {
            sql += " AND company_name2 like '%" + company_name + "%'";
        }
        if (location != null && !"".equals(location)) {
            sql += " AND location2 like '%" + location + "%'";
        }
        
        return Db.paginate(pageindex, pagelimit, "SELECT d.*,e.* ", sql);
    }
    
    /** 
    * @Title: isDuplicate 
    * @Description: 检测重复
    * @param batchNo
    * @param trayNo
    * @return boolean
    * @author liyu
    */
    public static boolean isDuplicate(String batchNo, String trayNo) {
        String sql = "SELECT COUNT(*) FROM t_domes_in_warehouse "
                + " WHERE batch_no = ? OR tray_no = ? ";
        return Db.find(sql, batchNo, trayNo).size() > 0;
    }
    
    /** 
    * @Title: hasOtherBusiness 
    * @Description: 检测是否有后续业务单据
    * @param batchNo
    * @param trayNo
    * @return boolean
    * @author liyu
    */
    public static boolean hasOtherBusiness(String batchNo, String trayNo) {
        boolean flag = false;
        String sql = "SELECT COUNT(*) FROM t_domes_out_warehouse "
                + " WHERE batch_no = ? OR tray_no = ? ";
        flag = Db.find(sql, batchNo, trayNo).size() > 0;
        return flag;
    }
    
    /** 
    * @Title: hasOtherBusiness 
    * @Description: 检测是否有后续业务单据
    * @param id
    * @return boolean
    * @author liyu
    */
    public static boolean hasOtherBusiness(String id) {
        Record record = Db.findById("t_domes_out_warehouse", id);
        String batchNo = record.getStr("batch_no");
        String trayNo = record.getStr("tray_no");
        return hasOtherBusiness(batchNo, trayNo);
    }
    
    /** 
    * @Title: delete 
    * @Description: 删除记录
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
                    result = Db.deleteById("t_domes_in_warehouse", id);
                    if (result == false) {
                        break;
                    }
                }
                return result;
            }
        });
        return flag;
    }

    /** 
    * @Title: importByExcel 
    * @Description: 导入数据
    * @param uploadFile
    * @param session
    * @return Map<String,Object>
    * @author liyu
    */
    public static Map<String, Object> importByExcel(UploadFile uploadFile, HttpSession session) {
        Map<String,Object> map = new HashMap<String,Object>();
        List<Object> countWrongList = new ArrayList<>();
        boolean flag = Db.tx(new IAtom() {            
            @Override
            public boolean run() throws SQLException {
                List<String[]> list = ExcelKit.getExcelData(uploadFile.getFile());
                //导入excel返回结果，true导入正确，false导入错误
                boolean result = true;
                System.out.println(list.get(0).length);
                if(list.get(0).length!=10){
                    session.setAttribute("ErrorFile",true);
                    result = false;
                } else {
                    session.setAttribute("ErrorFile",false);
                    for(int i = 0; i < list.size(); i++){
                        String[] strings = list.get(i);
                        for(int k=0;k<=strings.length-1;k++){
                            if(match(strings[k])){
                                countWrongList.add(i+2+"排"+(k+1)+"列");
                                result = false;
                            }
                        }
                        try {
                            Record record = new Record();
                            // 日期
                            record.set("in_date", strings[0]);
                            // 库位
                            record.set("storage_location", strings[1]);
                            // 物流公司名称
                            record.set("company_name", strings[2]);
                            // 物料号
                            if (!"".equals(strings[3])) {
                                record.set("material_no", strings[3]);
                            }
                            // 物料描述
                            if (!"".equals(strings[4])) {
                                record.set("material", strings[4]);
                            }
                            // 批次号
                            record.set("batch_no", strings[5]);
                            // 托盘号
                            record.set("tray_no", strings[6]);
                            // 数量
                            if (!"".equals(strings[7])) {
                                record.set("in_quantity", strings[7]);
                            }
                            // 托盘数量，默认 1 托
                            if (!"".equals(strings[8])) {
                                record.set("in_tray_quantity", strings[8]);
                            } else {
                                record.set("in_tray_quantity", 1);
                            }
                            // 组件功率
                            if (!"".equals(strings[9])) {
                                record.set("module_power", strings[9]);
                            }
                     
                            // 存在则更新，否则新增
                            Record recordDB = Db.findFirst("SELECT * FROM t_domes_in_warehouse  WHERE batch_no = ? OR tray_no = ? ", 
                                    strings[5], strings[6]);
                            if (recordDB != null) { // 更新
                                record.set("id", recordDB.getInt("id"));
                                Db.update("t_domes_in_warehouse", record);
                            } else { // 新增
                                Db.save("t_domes_in_warehouse", record);
                            }
                        } catch(Exception e) {
                            //指定一个判定对象，如果countWrongList已有该行数则返回false，否则返回true；
                            countWrongList.add(i+2+"行"+"存在数据异常，请校验");
                            result = false;
                        }
                            
                            
                    }
                }
                return result;  
            }
        });
        map.put("flag", flag);
        session.setAttribute("countWrongList", countWrongList);
        return map;
    }

    /** 
    * @Title: match 
    * @Description: 验证导入excel输入的正则验证
    * @param str
    * @return boolean
    * @author liyu
    */
    private static boolean match(String str){
        Pattern pattern = Pattern.compile("[`~!@#$%^&*()+=|{}':;',\\[\\]<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]");
        Matcher matcher = pattern.matcher(str);
        return matcher.find();
    }





    
}
