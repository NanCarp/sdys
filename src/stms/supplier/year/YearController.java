package stms.supplier.year;

import java.time.LocalDate;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.jfinal.core.Controller;
import com.jfinal.plugin.activerecord.Record;

/**
 * @ClassName: YearController.java
 * @Description: 物流公司年度考核控制器
 * @author: LiYu
 * @date: 2017年6月26日下午6:14:21
 * @version: 1.0 版本初成
 */
public class YearController extends Controller {
	/** 
	* @Title: index 
	* @Description: 物流公司年度考核列表
	* @param 
	* @return void
	* @throws 
	*/
	public void index() {
		// 物流公司名称
		String forwarder = getPara("forwarder","");
		// 年份
		Integer year = getParaToInt("year",null);
		
		Map<String,Object> params = new HashMap<>();
		params.put("forwarder", forwarder);
		params.put("year", year);
		
		// 年度考核列表
		List<Record> yearList = YearService.getYearList(params);
		setAttr("yearList", yearList);
		setAttr("forwarder", forwarder);
		setAttr("year", year);
		
		render("year.html");
	}
	
	/** 
	* @Title: getYear 
	* @Description: 获取物流公司年度考核
	* @param 
	* @return void
	* @throws 
	*/
	public void getYear() {
		// 年度考核 id
		Integer id = getParaToInt();
		
		// 编辑
		if (id != null) {
			// 根据 id 查询年度考核
			Record record = YearService.getYearById(id);
			setAttr("year", record);
		} else {// 新增
			// 物流公司名称，物流公司 id
			String params = " a.state != 0 ";
			List<Record> forwarderList = YearService.getQualityByParams(params);
			setAttr("forwarderList", forwarderList);
		}
		
		// 当前年份
		int currentYear = LocalDate.now().getYear();
		setAttr("currentYear", currentYear);

        // 评分标准
        List<Record> criterionList =  YearService.getCriterionList();
        setAttr("criterionList", criterionList);

		render("year_detail.html");
	}
	
	/** 
	* @Title: saveYear 
	* @Description: 保存物流公司年度考核
	* @param 
	* @return void
	* @throws 
	*/
	public void saveYear() {
		// 年度考核 id
		Integer id = getParaToInt("id");
		// 当前时间
		Date now = new Date();
		// 年份
		String year = getPara("year");
		// 物流公司 id
		String supplierId = getPara("supplierId");
		// 年度得分
		int yearScore = getParaToInt("yearScore");
		// 评定等级
		String level = getPara("level");
		// 备注
		String remark = getPara("remark");
		
		Record record = new Record();
		record.set("year", year);
		record.set("supplier_id", supplierId);
		record.set("year_score", yearScore);
		record.set("supplier_level", level);
		record.set("remark", remark);
		record.set("review_time", now);// 修改时间
		// 编辑
		if (id != null) {
			// 年度考核 id
			record.set("id", id);
		} else {// 新增
			// 创建时间
			record.set("create_time", now);
		}
		// 保存结果
		boolean result = YearService.saveYear(record);
		
		renderJson(result);
	}
	
	/** 
	* @Title: deleteYear
	* @Description: 根据 id 删除年度考核
	* @param 
	* @return void
	* @throws 
	*/
	public void deleteYear() {
		// 年度考核 id
		String idStr = getPara();
		String[] ids = idStr.split(",");
		// 删除结果
		// boolean result = Db.deleteById("t_supplier_year_assess", ids);
		boolean result = YearService.deleteYearByIds(ids);
		
		renderJson(result);
		
	}
	
	/** 
	* @Title: calculateYearAlert 
	* @Description: 没有月度考核的列表
	* @param 
	* @return void
	* @throws 
	*/
	public void calculateYearAlert() {
		// 所有公司未审核月份列表
		//List<Record> forwarderList = YearService.calculateYearAlert();
		List<Record> forwarderList = YearService.getYearAlert();
		setAttr("forwarderList", forwarderList);
		render("year_alert.html");
	}
	
	/** 
	* @Title: calculateYear 
	* @Description: 计算年度得分
	* @param 
	* @return void
	* @throws 
	*/
	public void calculateYear() {
		// 评分结果
		boolean result = YearService.calculateYear();
		
		renderJson(result);
	}
	
	/** 
	* @Title: isAllSupplierCalculated 
	* @Description: 判断是否当年所有物流公司已评分
	*/
	public void isAllSupplierCalculated() {
		// 判断是否当年所有物流公司已评分
		boolean result = YearService.isAllSupplierCalculated();
		
		Map<String, Object> msg = new HashMap<>();
		msg.put("result", result);
		
		renderJson(msg);
	}
}
