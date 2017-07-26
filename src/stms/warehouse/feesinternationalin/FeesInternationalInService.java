package stms.warehouse.feesinternationalin;

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
 * @ClassName: FeesInternationalInService.java
 * @Description:
 * @author: LiYu
 * @date: 2017年7月25日上午8:32:06
 * @version: 1.0 版本初成
 */
class FeesInternationalInService {

    /** 
    * @Title: getDataPages 
    * @Description: TODO(这里用一句话描述这个方法的作用) 
    * @param pageindex
    * @param pagelimit
    * @return Page<Record>
    * @author liyu
    */
    protected static Page<Record> getDataPages(Integer pageindex, Integer pagelimit) {
        String sql = " FROM t_inter_warehouse_fees_in WHERE 1=1 ";
        
        return Db.paginate(pageindex, pagelimit, "SELECT * ", sql);
    }
    
    public static Page<Record> getDataPages(Integer pageindex, Integer pagelimit, String company_name) {
        String sql = " FROM t_inter_warehouse_fees_in WHERE 1=1 ";
        
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
                    result = Db.deleteById("t_inter_warehouse_fees_in", id);
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
                            // 入库集装箱号
                            record.set("in_container_no", strings[4]);
                            // 入库托数，默认 1 托
                            if (!"".equals(strings[5])) {
                                record.set("in_tray_quantity", strings[5]);
                            } else {
                                record.set("in_tray_quantity", 1);
                            }
                            // 入库操作费
                            if (!"".equals(strings[6])) {
                                record.set("in_handling_fees", strings[6]);
                            }
                            // 船公司费用（代收代付）
                            if (!"".equals(strings[7])) {
                                record.set("ship_fees", strings[7]);
                            }
                            // 仓储费
                            if (!"".equals(strings[8])) {
                                record.set("warehouse_fee", strings[8]);
                            }
                            // 备注
                            record.set("remark", strings[9]);
                            
                            // 存在则更新，否则新增 TODO 唯一标识？？
                            Record recordDB = Db.findFirst("SELECT * FROM t_inter_warehouse_fees_in  WHERE in_container_no = ? ", 
                                    strings[4]);
                            if (recordDB != null) { // 更新
                                record.set("id", recordDB.getInt("id"));
                                Db.update("t_inter_warehouse_fees_in", record);
                            } else { // 新增
                                Db.save("t_inter_warehouse_fees_in", record);
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
