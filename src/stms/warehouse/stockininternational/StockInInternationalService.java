package stms.warehouse.stockininternational;

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

public class StockInInternationalService {

    public static Page<Record> getDataPages(Integer pageindex, Integer pagelimit) {
        return getDataPages(pageindex, pagelimit, null, null, null);
    }

    public static Page<Record> getDataPages(Integer pageindex, Integer pagelimit, String in_date, String company_name,
            String material_no) {
        String sql = " FROM t_inter_in_warehouse WHERE 1=1 ";
        if (in_date != null && !"".equals(in_date)) {
            sql += " AND in_date = '" + in_date + "'";
        }
        if (company_name != null && !"".equals(company_name)) {
            sql += " AND in_company_name like '%" + company_name + "%'";
        }
        if (material_no != null && !"".equals(material_no)) {
            sql += " AND in_material_no like '%" + material_no + "%'";
        }
        
        return Db.paginate(pageindex, pagelimit, "SELECT * ", sql);
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
        String sql = "SELECT COUNT(*) FROM t_inter_in_warehouse "
                + " WHERE in_batch_no = ? OR in_tray_no = ? ";
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
        String sql = "SELECT COUNT(*) FROM t_inter_out_warehouse "
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
        Record record = Db.findById("t_inter_out_warehouse", id);
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
                    result = Db.deleteById("t_inter_in_warehouse", id);
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
                            record.set("in_storage_location", strings[1]);
                            // 物流公司名称
                            record.set("in_company_name", strings[2]);
                            // 物料号
                            if (!"".equals(strings[3])) {
                                record.set("in_material_no", strings[3]);
                            }
                            // 物料描述
                            if (!"".equals(strings[4])) {
                                record.set("in_material", strings[4]);
                            }
                            // 批次号
                            record.set("in_batch_no", strings[5]);
                            // 托盘号
                            record.set("in_tray_no", strings[6]);
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
                                record.set("in_module_power", strings[9]);
                            }
                     
                            // 存在则更新，否则新增
                            Record recordDB = Db.findFirst("SELECT * FROM t_inter_in_warehouse  WHERE in_batch_no = ? OR in_tray_no = ? ", 
                                    strings[5], strings[6]);
                            if (recordDB != null) { // 更新
                                record.set("id", recordDB.getInt("id"));
                                Db.update("t_inter_in_warehouse", record);
                            } else { // 新增
                                Db.save("t_inter_in_warehouse", record);
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
