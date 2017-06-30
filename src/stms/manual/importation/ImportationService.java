package stms.manual.importation;

import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.IAtom;
import com.jfinal.plugin.activerecord.Record;
import com.jfinal.upload.UploadFile;

import stms.model.ManualImport;
import stms.utils.ExcelKit;

import java.sql.SQLException;
import java.util.List;

public class ImportationService {

    public static List<Record> getImportationList() {
        return Db.find("SELECT * FROM t_manual_import");
    }

    public static List<Record> getImportationList(String manualNo, String recordNo, String productNo, String productName, String mainMaterial) {
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
        return Db.find(sql);
    }

    public static boolean deleteImportation(String[] ids) {
        boolean succeed = Db.tx(new IAtom(){
            boolean result = false;
            @Override
            public boolean run() throws SQLException {
                for (String id: ids){
                    // 删除
                    result = Db.deleteById("t_manual_import", id);
                    if (result == false) {
                        break;
                    }
                }
                return result;
            }
            
        });
        
        return succeed;
    }


    public static boolean importByExcel(UploadFile uploadFile) {
        boolean succeed = Db.tx(new IAtom() {
            @Override
            public boolean run() throws SQLException {
                try{
                    List<String[]> list = ExcelKit.getExcelData(uploadFile.getFile());
                    for(String[] strings : list){
                        if(strings[0] != null && !"".equals(strings[0])){
                            // TODO 空值
                            Record record = new Record();
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
                            List<Record> list1 = Db.find(sql, strings[18], strings[0]);
                            if(list1.size() > 0) {
                                Integer id = list1.get(0).getInt("id");
                                record.set("id", id);
                                Db.update("t_manual_import", record);
                            } else {
                                Db.save("t_manual_import", record);
                            }
                        }
                    }
                    return true;
                }catch (Exception e){
                    e.printStackTrace();
                    return false;
                }
            }
        });
        return succeed;
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
}
