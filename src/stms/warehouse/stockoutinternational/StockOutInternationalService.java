package stms.warehouse.stockoutinternational;

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

public class StockOutInternationalService {
    
    public static Page<Record> getDataPages(Integer pageindex, Integer pagelimit) {
        return getDataPages(pageindex, pagelimit, null, null, null);
    }

    public static Page<Record> getDataPages(Integer pageindex, Integer pagelimit, String out_date, String company_name,
            String material_no) {
        String sql = " FROM t_inter_out_warehouse WHERE 1=1 ";
        if (out_date != null && !"".equals(out_date)) {
            sql += " AND out_date = '" + out_date + "'";
        }
        if (company_name != null && !"".equals(company_name)) {
            sql += " AND company_name like '%" + company_name + "%'";
        }
        if (material_no != null && !"".equals(material_no)) {
            sql += " AND material_no like '%" + material_no + "%'";
        }
        sql += " ORDER BY delivery_no DESC ";
        
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
        String sql = "SELECT COUNT(*) FROM t_inter_out_warehouse "
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
    public static boolean delete(String ids) {
        String[] allid = ids.split(",");    
        boolean flag = Db.tx(new IAtom() {
            boolean result = true;
            @Override
            public boolean run() throws SQLException {
                for(String id:allid){
                    result = Db.deleteById("t_inter_out_warehouse", id);
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
                if(list.get(0).length!=15){
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
                            // 出库单号
                            record.set("delivery_no", strings[0]);
                            // 日期
                            record.set("out_date", strings[1]);
                            // 库位
                            record.set("storage_location", strings[2]);
                            // 物流公司名称
                            record.set("company_name", strings[3]);
                            // 物料号
                            if (!"".equals(strings[4])) {
                                record.set("material_no", strings[4]);
                            }
                            // 物料描述
                            if (!"".equals(strings[5])) {
                                record.set("material", strings[5]);
                            }
                            // 批次号
                            record.set("batch_no", strings[6]);
                            // 托盘号
                            record.set("tray_no", strings[7]);
                            // 数量
                            if (!"".equals(strings[8])) {
                                record.set("out_quantity", strings[8]);
                            }
                            // 托盘数量，默认 1 托
                            if (!"".equals(strings[9])) {
                                record.set("out_tray_quantity", strings[9]);
                            } else {
                                record.set("out_tray_quantity", 1);
                            }
                            // 发票号
                            record.set("invoice_no", strings[10]);
                            // 组件功率
                            if (!"".equals(strings[10])) {
                                record.set("module_power", strings[11]);
                            }
                            // 实际客户
                            record.set("customer", strings[12]);
                            // 外库通知单号
                            record.set("notice_no", strings[13]);
                            // 客户发票号
                            record.set("customer_invoice_no", strings[14]);
                     
                            // 存在则更新，否则新增
                            Record recordDB = Db.findFirst("SELECT * FROM t_inter_out_warehouse  WHERE batch_no = ? OR tray_no = ? ", 
                                    strings[6], strings[7]);
                            if (recordDB != null) { // 更新
                                record.set("id", recordDB.getInt("id"));
                                Db.update("t_inter_out_warehouse", record);
                            } else { // 新增
                                Db.save("t_inter_out_warehouse", record);
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
