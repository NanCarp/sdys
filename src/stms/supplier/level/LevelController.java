package stms.supplier.level;

import java.util.Date;
import java.util.List;

import com.jfinal.core.Controller;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;

public class LevelController extends Controller {
	/** 
	* @Title: index 
	* @Description: 物流公司考核标准
	* @param 
	* @return void
	* @throws 
	*/
	public void index() {
		// 考核标准列表
		List<Record> levelList = LevelService.getLevelList();
		setAttr("levelList", levelList);
		
		render("level.html");
	}
	
	/** 
	* @Title: getLevel 
	* @Description: 获取物流公司等级 
	* @param
	* @return void
	* @throws
	*/
	public void getLevel() {
		// 考核标准 id
		Integer id = getParaToInt();
		
		if (id != null) {//编辑
			Record level = Db.findById("t_supplier_level", id);
			setAttr("level", level);
		}
		
		render("level_detail.html");
	}
	
	/** 
	* @Title: saveLevel 
	* @Description: 保存考核标准 
	* @param 
	* @return void
	* @throws 
	*/
	public void saveLevel() {
		// 考核标准 id
		Integer id = getParaToInt("id");
		// 等级
		String level = getPara("level");
		// 得分
		String score = getPara("score").trim();
		// 当前时间 
		Date now = new Date();
		// 保存结果
		boolean result = false;
		Record record = new Record();
		record.set("supplier_level", level);
		record.set("supplier_score", score);
		record.set("review_time", now);// 修改时间
		if (id != null) {// 编辑
			record.set("id", id);
			result = Db.update("t_supplier_level", record);
		} else {// 新增
			record.set("create_time", now);
			result = Db.save("t_supplier_level", record);
		}
		
		renderJson(result);
	}
	
	/** 
	* @Title: deleteLevel 
	* @Description: 删除考核标准 
	* @param 
	* @return void
	* @throws 
	*/
	public void deleteLevel() {
		// 考核标准 id
		String idStr = getPara();
		String[] ids = idStr.split(",");
		// 删除结果
		boolean result = LevelService.deleteLevel(ids);
		
		//result = Db.deleteById("t_supplier_level", id);
		
		renderJson(result);
		
	}
	

}
