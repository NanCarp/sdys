package stms.manual.summary;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.IAtom;
import com.jfinal.plugin.activerecord.Record;
import com.jfinal.upload.UploadFile;

import stms.model.ManualSum;
import stms.utils.ExcelKit;

/**
 * @ClassName: SummaryService.java
 * @Description: 手册情况汇总 service
 * @author: LiYu
 * @date: 2017年6月27日上午9:37:41
 * @version: 1.0 版本初成
 */
public class SummaryService {
    
    private static final ManualSum dao = new ManualSum().dao();

	/** 
	* @Title: getSummaryList 
	* @Description: 获取手册情况列表
	* @return List<Record>
	*/
	public List<Record> getSummaryList() {
		return dao.getSummaryList();
	}
	
	/** 
    * @Title: getSummaryList 
    * @Description: 根据搜索条件获取手册列表
    * @param manualNo
    * @param handleDate
    * @param expireDate
    * @return List<Record>
    */
    public List<ManualSum> getSummaryList(String manualNo, String handleDate, Integer expireDate) {
        String sql = "SELECT * FROM t_manual_sum WHERE 1=1 ";
        // 手册号不为空，增加查询条件
        if(manualNo != null && !"".equals(manualNo)) {
            sql += " AND manual_id like '%" + manualNo + "%' ";
        }
        // 手册办理日期不为空，增加查询条件
        if(handleDate != null && !"".equals(handleDate)) {
            sql += " AND exist_date = '" + handleDate +"'";
        }
        // 手册有效期不为空，增加查询条件
        if(expireDate != null && !"".equals(expireDate)) {
            sql += " AND valid_date = '" + expireDate +"'";
        }
        
        return dao.find(sql);
    }

	/** 
	* @Title: getSummary 
	* @Description: 根据 id 获取手册情况
	* @param id
	* @return Record
	*/
	public Record getSummary(Integer id) {
		return Db.findById("t_manual_sum", id);
	}

    /** 
    * @Title: deleteSummary 
    * @Description: 删除手册
    * @param ids
    * @return boolean
    */
    public boolean deleteSummary(String[] ids) {
        boolean succeed = Db.tx(new IAtom(){
            boolean result = false;
            @Override
            public boolean run() throws SQLException {
                for (String id: ids){
                    // 删除手册
                    result = Db.deleteById("t_manual_sum", id);
                    if (result == false) {
                        break;
                    }
                }
                return result;
            }
            
        });
        
        return succeed;
    }

    public boolean importByExcel(UploadFile uploadFile) {
        boolean succeed = Db.tx(new IAtom() {            
            @Override
            public boolean run() throws SQLException {
                try{
                    System.out.println(uploadFile);System.out.println(uploadFile.getFile());
                    List<String[]> list = ExcelKit.getExcelData(uploadFile.getFile());
                    for(String[] strings : list){
                        if(strings[0] != null && !"".equals(strings[0])){
                            // TODO 空值
                            Record record = new Record();
                            record.set("manual_id", strings[0]);
                            record.set("off_state", strings[1] == "" ? null : strings[1]);
                            record.set("module_set", strings[2] == "" ? null : strings[2]);
                            record.set("pre_product_money", strings[3] == "" ? null : strings[3]);
                            record.set("pre_import_money", strings[4] == "" ? null : strings[4]);
                            record.set("pre_money_dis", strings[5] == "" ? null : strings[5]);
                            record.set("act_export_money", strings[6] == "" ? null : strings[6]);
                            record.set("act_import_money", strings[7] == "" ? null : strings[7]);
                            record.set("act_money_dis", strings[8] == "" ? null : strings[8]);
                            record.set("exist_date", strings[9] == "" ? null : strings[9]);
                            record.set("valid_date", strings[10] == "" ? null : strings[10]);
                            record.set("extension_date1", strings[11] == "" ? null : strings[11]);
                            record.set("extension_date2", strings[12] == "" ? null : strings[12]);
                            record.set("report_verificate_date", strings[13] == "" ? null : strings[13]);
                            record.set("case_over_date", strings[14] == "" ? null : strings[14]);
                            record.set("remark", strings[15] == "" ? null : strings[15]);
                     
                            // 存在则更新，否则新增
                            List<Record> list1 = Db.find("SELECT * FROM t_manual_sum WHERE manual_id = ?", strings[0]);
                            if(list1.size() > 0) {
                                Integer id = list1.get(0).getInt("id");
                                record.set("id", id);
                                Db.update("t_manual_sum", record);
                            } else {
                                Db.save("t_manual_sum", record);
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
    * @Description: 新增或更新手册
    * @param record
    * @return boolean
    */
    public boolean saveOrUpdate(ManualSum record) {
        boolean result = false;
        // 手册 id
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
