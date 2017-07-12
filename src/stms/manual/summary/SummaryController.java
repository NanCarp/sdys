package stms.manual.summary;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.jfinal.core.Controller;
import com.jfinal.upload.UploadFile;

import stms.model.ManualSum;
import stms.supplier.year.YearService;


/**
 * @ClassName: SummaryController.java
 * @Description: 手册情况汇总控制器
 * @author: LiYu
 * @date: 2017年6月27日上午8:22:54
 * @version: 1.0 版本初成
 */
public class SummaryController extends Controller{
    static SummaryService service = new SummaryService();
    
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
		List<ManualSum> summaryList = service.getSummaryList(manualNo, handleDate, expireDate);
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
		
		if (id != null) {// id 不为空，编辑手册
			// 获取手册情况
			ManualSum summary = service.getSummary(id);
			setAttr("summary", summary);
		}
		
		render("summary_detail.html");
	}
	
	/** 
	* @Title: saveSummary 
	* @Description: 保存手册情况
	*/
	public void saveSummary() {
	    // 返回信息
        Map<String, Object> response = new HashMap<>();
	    // 手册
	    ManualSum record = getModel(ManualSum.class, "");
	    
	    // 重复检测
	    Integer id = record.getId();
	    String manualId = record.getManualId();
	    if (id == null && SummaryService.isDuplicate(manualId)) {
            response.put("tips", "手册号重复！");
            response.put("isSuccess", false);
            renderJson(response);
            return;
        }
	    
	    // 保存
	    boolean result = service.saveOrUpdate(record);
	    response.put("isSuccess", result);
        response.put("tips", result ? "保存成功": "保存失败");
	    
        renderJson(response);
	    
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
        boolean result = service.deleteSummary(ids);
        
        renderJson(result);
	}
	
	public void importUI() {
	    // 导入页面
	    render("summary_import.html");
	}
	
	/** 
	* @Title: importByExcel 
	* @Description: 导入 excel 中的数据
	*/
	public void importByExcel() {
	    // excel
	    UploadFile uploadFile = getFile();
	    
	    Map<String, Object> msgMap = service.importByExcel(uploadFile, getSession());
	    
	    renderJson(msgMap);
	}
	
	
    /** 
    * @Title: showErrorExcelMessage 
    * @Description: 显示错误信息
    */
    public void showErrorExcelMessage(){
        List<Integer> countlist = getSessionAttr("countWrongList");
        boolean ErrorFile = getSessionAttr("ErrorFile");
        setAttr("countlist", countlist);
        setAttr("ErrorFile", ErrorFile);
        render("wrong_message.html");
    }
}
