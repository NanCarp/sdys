package stms.manual.summary;

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
     public List<ManualSum> getSummaryList() {
         String sql = "SELECT * FROM t_manual_sum";
         
         return dao.find(sql);
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
	* @return ManualSum
	*/
	public ManualSum getSummary(Integer id) {
		return dao.findById(id);
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

    public Map<String, Object> importByExcel(UploadFile uploadFile, HttpSession session) {
        Map<String,Object> map = new HashMap<String,Object>();
        List<Object> countWrongList = new ArrayList<>();
        boolean flag = Db.tx(new IAtom() {            
            @Override
            public boolean run() throws SQLException {
                List<String[]> list = ExcelKit.getExcelData(uploadFile.getFile());
                //导入excel返回结果，true导入正确，false导入错误
                boolean result = true;
                System.out.println(list.get(0).length);
                if(list.get(0).length!=16){
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
                            ManualSum record = new ManualSum();
                            record.set("manual_id", strings[0]);
                            record.set("off_state", strings[1]);
                            record.set("module_set", strings[2]);
                            if (!"".equals(strings[3])) {
                                record.set("pre_product_money", strings[3]);
                            }
                            if (!"".equals(strings[4])) {
                                record.set("pre_import_money", strings[4]);
                            }
                            if (!"".equals(strings[5])) {
                                record.set("pre_money_dis", strings[5]);
                            }
                            if (!"".equals(strings[6])) {
                                record.set("act_export_money", strings[6]);
                            }
                            if (!"".equals(strings[7])) {
                                record.set("act_import_money", strings[7]);
                            }
                            if (!"".equals(strings[8])) {
                                record.set("act_money_dis", strings[8]);
                            }
                            if (!"".equals(strings[9])) {
                                record.set("exist_date", strings[9]);
                            }
                            if (!"".equals(strings[10])) {
                                record.set("valid_date", strings[10]);
                            }
                            if (!"".equals(strings[11])) {
                                record.set("extension_date1", strings[11]);
                            }
                            if (!"".equals(strings[12])) {
                                record.set("extension_date2", strings[12]);
                            }
                            if (!"".equals(strings[13])) {
                                record.set("report_verificate_date", strings[13]);
                            }
                            if (!"".equals(strings[14])) {
                                record.set("case_over_date", strings[14]);
                            }
                            record.set("remark", strings[15]);
                     
                            // 存在则更新，否则新增
                            ManualSum recordDB = dao.findFirst("SELECT * FROM t_manual_sum WHERE manual_id = ?", strings[0]);
                            if (recordDB != null) { // 更新
                                record.setId(recordDB.getId());
                                record.update();
                            } else { // 新增
                                record.save();
                            }
                        } catch(Exception e) {
                            //指定一个判定对象，如果countWrongList已有该行数则返回false，否则返回true；
                            /*boolean countflag = true;
                            for(Integer column:countWrongList){
                                if(column==i){
                                    countflag = false;
                                }
                            }
                            if(!countflag){
                                countWrongList.add(i+2);
                            }*/
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

    /**
     * @desc 验证导入excel输入的正则验证
     * @author xuhui
     */
    private boolean match(String str){
        Pattern pattern = Pattern.compile("[`~!@#$%^&*()+=|{}':;',\\[\\]<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]");
        Matcher matcher = pattern.matcher(str);
        return matcher.find();
    }

}
