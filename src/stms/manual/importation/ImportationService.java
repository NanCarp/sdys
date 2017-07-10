package stms.manual.importation;

import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.IAtom;
import com.jfinal.plugin.activerecord.Record;
import com.jfinal.upload.UploadFile;

import stms.model.ManualImport;
import stms.model.ManualSum;
import stms.utils.ExcelKit;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpSession;

public class ImportationService {
    
    private static final ManualImport dao = new ManualImport().dao();

    public static List<Record> getImportationList() {
        return Db.find("SELECT * FROM t_manual_import");
    }

    /** 
    * @Title: getImportationList 
    * @Description: 进口明细列表
    * @param manualNo
    * @param recordNo
    * @param productNo
    * @param productName
    * @param mainMaterial
    * @return List<ManualImport>
    */
    public static List<ManualImport> getImportationList(String manualNo, String recordNo, String productNo, String productName, String mainMaterial) {
        String sql = "SELECT * FROM t_manual_import WHERE 1=1 ";
        if(manualNo != null && !"".equals(manualNo)) {
            sql += " AND manual_id like '%" + manualNo + "%' ";
        }
        if(recordNo != null && !"".equals(recordNo)) {
            sql += " AND import_num like '%" + recordNo + "%' ";
        }
        if(productNo != null && !"".equals(productNo)) {
            sql += " AND import_code like '%" + productNo + "%' ";
        }
        if(productName != null && !"".equals(productName)) {
            sql += " AND import_name like '%" + productName + "%' ";
        }
        if(mainMaterial != null && !"".equals(mainMaterial)) {
            sql += " AND main_material like '%" + mainMaterial + "%' ";
        }
        return dao.find(sql);
    }

    /** 
    * @Title: deleteImportation 
    * @Description: 删除进口明细
    * @param ids
    * @return boolean
    */
    public static boolean deleteImportation(String[] ids) {
        boolean succeed = Db.tx(new IAtom(){
            boolean result = false;
            @Override
            public boolean run() throws SQLException {
                for (String id: ids){
                    // 删除
                    result = dao.deleteById(id);
                    if (result == false) {
                        break;
                    }
                }
                return result;
            }
            
        });
        
        return succeed;
    }


    /** 
    * @Title: importByExcel 
    * @Description: 从 excel 导入数据
    * @param uploadFile
    * @return boolean
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
                if(list.get(0).length!=20){
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
                            ManualImport record = new ManualImport();
                            record.set("import_num", strings[0]);
                            record.set("import_record_num", strings[1]);
                            record.set("import_code", strings[2]);
                            record.set("import_name", strings[3]);
                            record.set("import_specification", strings[4]);
                            record.set("main_material", strings[5]);
                            record.set("import_unit", strings[6]);
                            record.set("import_fixed_unit", strings[7]);
                            record.set("import_report_num", strings[8]);
                            record.set("import_report_unit_price", strings[9]);
                            record.set("import_report_total_price", strings[10]);
                            record.set("currency_system", strings[11]);
                            record.set("import_pro_market", strings[12]);
                            record.set("fixed_unit_ratio", strings[13]);
                            record.set("import_levy_mode", strings[14]);
                            record.set("import_handle_flag", strings[15]);
                            record.set("tax_rate", strings[16]);
                            record.set("version", strings[17]);
                            record.set("manual_id", strings[18]);
                            record.set("remark", strings[19]);
                     
                            // 存在则更新，否则新增
                            String sql = "SELECT * FROM t_manual_import " +
                                    " WHERE manual_id = ? AND import_num = ? ";
                            ManualImport recordDB = dao.findFirst(sql, strings[18], strings[0]);
                            if (recordDB != null) { // 更新
                                record.setId(recordDB.getId());
                                record.update();
                            } else { // 新增
                                record.save();
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
    * @Title: saveOrUpdate 
    * @Description: 新增或更新记录
    * @param record
    * @return boolean
    */
    public boolean saveOrUpdate(ManualImport record) {
        boolean result = false;
        // id
        Integer id = record.getId();
        // id 存在则更新，否则新增
        if (id != null) {
            result = record.update();
        } else {
            result = record.save();
        }
        return result;
    }
    
    /**
     * @desc 验证导入excel输入的正则验证
     * @author xuhui
     */
    private static boolean match(String str){
        Pattern pattern = Pattern.compile("[`~!@#$%^&*()+=|{}':;',\\[\\]<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]");
        Matcher matcher = pattern.matcher(str);
        return matcher.find();
    }
}
