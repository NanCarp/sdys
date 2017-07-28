package stms.warehouse.locationcomparison;

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

/**
 * @ClassName: LocationComparisonService.java
 * @Description:
 * @author: LiYu
 * @date: 2017年7月26日上午9:23:51
 * @version: 1.0 版本初成
 */
public class LocationComparisonService {

    /** 
    * @Title: getDataPages 
    * @Description: TODO(这里用一句话描述这个方法的作用) 
    * @param pageindex
    * @param pagelimit
    * @return Page<Record>
    * @author liyu
    */
    public static Page<Record> getDataPages(Integer pageindex, Integer pagelimit) {
        return getDataPages(pageindex, pagelimit, null);
    }

    public static Page<Record> getDataPages(Integer pageindex, Integer pagelimit, String batch_no) {
        String sql = " FROM (SELECT a.storage_location,a.company_name,a.material_no,a.material,a.batch_no,a.tray_no,a.module_power, "
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
                + " LEFT JOIN t_location_comparison AS d "
                + " ON c.batch_no = d.batch_no2 WHERE 1=1 ";
        
        if (batch_no != null && !"".equals(batch_no)) {
            sql += " AND material_no like '%" + batch_no + "%'";
        }
        /*if (in_date != null && !"".equals(in_date)) {
            sql += " AND in_date = '" + in_date + "'";
        }
        if (company_name != null && !"".equals(company_name)) {
            sql += " AND company_name like '%" + company_name + "%'";
        }
        if (material_no != null && !"".equals(material_no)) {
            sql += " AND material_no like '%" + material_no + "%'";
        }*/
        
        return Db.paginate(pageindex, pagelimit, "SELECT c.*,d.* ", sql);
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
        String sql = "SELECT COUNT(*) FROM t_location_comparison "
                + " WHERE batch_no = ? OR tray_no = ? ";
        return Db.find(sql, batchNo, trayNo).size() > 0;
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
                    result = Db.deleteById("t_location_comparison", id);
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
                if(list.get(0).length!=6){
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
                            // 库位
                            record.set("location2", strings[0]);
                            // 数量
                            if (!"".equals(strings[1])) {
                                record.set("quantity2", strings[1]);
                            }
                            // 物料号
                            if (!"".equals(strings[2])) {
                                record.set("material_no2", strings[2]);
                            }
                            // 物料描述
                            if (!"".equals(strings[3])) {
                                record.set("material2", strings[3]);
                            }
                            // 批次号
                            record.set("batch_no2", strings[4]);
                            // 组件功率
                            if (!"".equals(strings[5])) {
                                record.set("module_power2", strings[5]);
                            }
                     
                            // 存在则更新，否则新增
                            Record recordDB = Db.findFirst("SELECT * FROM t_location_comparison  WHERE batch_no2 = ? ", 
                                    strings[4]);
                            if (recordDB != null) { // 更新
                                record.set("id", recordDB.getInt("id"));
                                Db.update("t_location_comparison", record);
                            } else { // 新增
                                Db.save("t_location_comparison", record);
                            }
                        } catch(Exception e) {
                            e.printStackTrace();
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

    /** 
    * @Title: getRecord 
    * @Description: TODO(这里用一句话描述这个方法的作用) 
    * @param batch_no
    * @return Record
    * @author liyu
    */
    public static Record getRecord(String batch_no) {
        List<Record> list = Db.find("SELECT * FROM t_location_comparison WHERE batch_no2 = ?", batch_no);
        if (list.size() > 0) {
            return list.get(0);
        } else {
            // 新建一条记录，只有批次号
            Record r = new Record();
            r.set("batch_no2", batch_no);
            return r;
        }
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



    
}
