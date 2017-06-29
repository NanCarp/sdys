package stms.manual.summary;

import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.jfinal.core.Controller;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;
import com.jfinal.upload.UploadFile;


/**
 * @ClassName: SummaryController.java
 * @Description: 手册情况汇总控制器
 * @author: LiYu
 * @date: 2017年6月27日上午8:22:54
 * @version: 1.0 版本初成
 */
public class SummaryController extends Controller{
	/** 
	* @Title: index 
	* @Description: 手册情况汇总列表
	*/
	public void index() {
	    // 手册号
	    String manualNo = getPara("manualNo");
        setAttr("manualNo", manualNo);
        // 手册办理日期
	    String handleDate = getPara("handleDate");
        setAttr("handleDate", handleDate);
        // 有效期
	    Integer expireDate = getParaToInt("expireDate");
        setAttr("expireDate", expireDate);
        // 获取手册情况列表
		List<Record> summaryList = SummaryService.getSummaryList(manualNo, handleDate, expireDate);
		setAttr("summaryList", summaryList);
		
		render("summary.html");
	}
	
	/** 
	* @Title: getSummary 
	* @Description: 获取手册情况
	*/
	public void getSummary() {
		// id
		Integer id = getParaToInt();
		
		if (id != null) {
			// 获取手册情况
			Record summary = SummaryService.getSummary(id);
			setAttr("summary", summary);
		}
		
		render("summary_detail.html");
	}
	
	/** 
	* @Title: saveSummary 
	* @Description: 保存手册情况
	*/
	public void saveSummary() {
		// id
	    Integer id = getParaToInt("id");
	    // 手册号
        String manualNo = getPara("manualNo");
        // 核销状态：1：正在使用，0：停用，2：核销
        Integer state = getParaToInt("state");
        // 组件系列
        String component = getPara("component");
        // 成品备案金额
        String finishedProductFilingAmount = getPara("finishedProductFilingAmount", null);
        // 进口料件备案金额
        String importedMaterialFilingAmount = getPara("importedMaterialFilingAmount", null);
        // 备案分配率
        String filingRate = getPara("filingRate", null);
        // 实际出口金额
        String actualExportAmount = getPara("actualExportAmount", null);
        // 实际进口金额
        String actualImportAmount = getPara("actualImportAmount", null);
        // 实际分配率
        String actualRate = getPara("actualRate", null);
        // 手册办理日期
        String handleDate = getPara("handleDate", null);
        // 有效期
        Integer expiryDate = getParaToInt("expiryDate", null);
        // 延期日期1
        String extensionDate1 = getPara("extensionDate1", null);
        // 延期日期2
        String extensionDate2 = getPara("extensionDate2", null);
        // 报核日期
        String verificationDate = getPara("verificationDate", null);
        // 结案日期
        String closeDate = getPara("closeDate", null);
        // 备注
        String remark = getPara("remark");
        // 当前时间
        Date now = new Date();
	    // 保存结果
	    boolean result = false;
	    
	    
	    Record record = new Record();
        record.set("manual_id", manualNo);
        record.set("off_state", state);
        record.set("module_set", component);
        record.set("pre_product_money", finishedProductFilingAmount);
        record.set("pre_import_money", importedMaterialFilingAmount);
        record.set("pre_money_dis", filingRate);
        record.set("act_export_money", actualExportAmount);
        record.set("act_import_money", actualImportAmount);
        record.set("act_money_dis", actualRate);
        record.set("exist_date", handleDate);
        record.set("valid_date", expiryDate);
        record.set("extension_date1", extensionDate1);
        record.set("extension_date2", extensionDate2);
        record.set("report_verificate_date", verificationDate);
        record.set("case_over_date", closeDate);
        record.set("remark", remark);
        record.set("review_time", now); // 修改时间
	    
	    if (id != null) { // 更新
	        record.set("id", id);
	        result = Db.update("t_manual_sum", record);
	    } else { // 新增
	        record.set("create_time", now);
	        result = Db.save("t_manual_sum", record);
	    }
	    
	    renderJson(result);
	    
	}
	
	/** 
	* @Title: deleteSummary 
	* @Description: 删除手册
	*/
	public void deleteSummary() {
	    // 手册 id
        String idStr = getPara();
        String[] ids = idStr.split(",");
        
        // 删除结果
        boolean result = SummaryService.deleteSummary(ids);
        
        renderJson(result);
	}
	
	/** 
	* @Title: importByExcel 
	* @Description: 导入 excel中的数据
	*/
	public void importByExcel() {
	    // excel
	    UploadFile uploadFile = getFile();
	    
	    boolean result = SummaryService.importByExcel(uploadFile);
	    
	    renderJson(result);
	}
}
