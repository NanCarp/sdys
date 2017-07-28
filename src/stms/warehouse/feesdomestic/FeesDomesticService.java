package stms.warehouse.feesdomestic;

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

class FeesDomesticService {

    /** 
    * @Title: getDataPages 
    * @Description: TODO(这里用一句话描述这个方法的作用) 
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
    * @Description: TODO(这里用一句话描述这个方法的作用) 
    * @param pageindex
    * @param pagelimit
    * @param company_name
    * @return Page<Record>
    * @author liyu
    */
    public static Page<Record> getDataPages(Integer pageindex, Integer pagelimit, String company_name) {
        String sql = " FROM t_domes_warehouse_fees WHERE 1=1 ";
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
        String sql = "SELECT * FROM `t_domes_warehouse_fees` WHERE period = ? AND company_name = ?";
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
                    result = Db.deleteById("t_domes_warehouse_fees", id);
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
                if(list.get(0).length!=18){
                    session.setAttribute("ErrorFile",true);
                    result = false;
                } else {
                    session.setAttribute("ErrorFile",false);
                    for(int i = 0; i < list.size(); i++){
                        String[] strings = list.get(i);
                        /*for(int k=0;k<=strings.length-1;k++){
                            if(match(strings[k])){
                                countWrongList.add(i+2+"排"+(k+1)+"列");
                                result = false;
                            }
                        }*/
                        try {
                            Record record = new Record();
                            // 物流公司名称
                            record.set("company_name", strings[0]);
                            // 账单周期
                            record.set("period", strings[1].substring(0, 6));
                            // 币制
                            record.set("currency", strings[2]);
                            // 时间
                            if (!"".equals(strings[3])) {
                                record.set("in_date", strings[3]);
                            }
                            // 入库托数，默认 1 托
                            if (!"".equals(strings[4])) {
                                record.set("in_tray_quantity", strings[4]);
                            }
                            record.set("in_tray_quantity", 1);
                            // 卸货费（入库）
                            if (!"".equals(strings[5])) {
                                record.set("unloading_fees", strings[5]);
                            }
                            // 短驳车型及数量
                            record.set("in_drayage_type_quantity", strings[6]);
                            // 短驳费
                            if (!"".equals(strings[7])) {
                                record.set("in_drayage", strings[7]);
                            }
                            // 加班费
                            if (!"".equals(strings[8])) {
                                record.set("in_overtime_pay", strings[8]);
                            }
                            // 备注
                            record.set("in_remark", strings[9]);
                            // 时间
                            if (!"".equals(strings[10])) {
                                record.set("out_date", strings[10]);
                            }
                            // 出库托数
                            if (!"".equals(strings[11])) {
                                record.set("out_tray_quantity", strings[11]);
                            }
                            // 装货费（出库）
                            if (!"".equals(strings[12])) {
                                record.set("loading_fees", strings[12]);
                            }
                            // 短驳车型及数量
                            record.set("out_drayate_type_quantity", strings[13]);
                            // 短驳费
                            if (!"".equals(strings[14])) {
                                record.set("out_drayage", strings[14]);
                            }
                            // 加班费
                            if (!"".equals(strings[15])) {
                                record.set("out_overtime_pay", strings[15]);
                            }
                            // 备注
                            record.set("out_remark", strings[16]);
                            // 仓储费
                            if (!"".equals(strings[17])) {
                                record.set("warehouse_fee", strings[17]);
                            }
                            
                            // 存在则更新，否则新增 TODO 唯一标识？？
                            Record recordDB = Db.findFirst("SELECT * FROM t_domes_warehouse_fees WHERE company_name = ? AND in_date = ? ", 
                                    strings[0], strings[3]);
                            if (recordDB != null) { // 更新
                                record.set("id", recordDB.getInt("id"));
                                Db.update("t_domes_warehouse_fees", record);
                            } else { // 新增
                                Db.save("t_domes_warehouse_fees", record);
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

    

}
