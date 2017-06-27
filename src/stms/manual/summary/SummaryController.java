package stms.manual.summary;

import java.util.Date;
import java.util.List;

import com.jfinal.core.Controller;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;

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
		
		// 获取手册情况列表
		List<Record> summaryList = SummaryService.getSummaryList();
		setAttr("summaryList", summaryList);
		
		render("summary.html");
	}
	
	/** 
	* @Title: getSummary 
	* @Description: 获取手册情况
	*/
	public void getSummary() {
		// id
		Integer id = getParaToInt("id");
		
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
	    // 当前时间
        Date now = new Date();
	    // 保存结果
	    boolean result = false;
	    
	    
	    Record record = new Record();
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
}
