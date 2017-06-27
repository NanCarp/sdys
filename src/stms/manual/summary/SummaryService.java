package stms.manual.summary;

import java.util.List;

import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;

/**
 * @ClassName: SummaryService.java
 * @Description: 手册情况汇总 service
 * @author: LiYu
 * @date: 2017年6月27日上午9:37:41
 * @version: 1.0 版本初成
 */
public class SummaryService {

	/** 
	* @Title: getSummaryList 
	* @Description: 获取手册情况列表
	* @return List<Record>
	*/
	public static List<Record> getSummaryList() {
		String sql = "SELECT * FROM t_manual_sum";
		
		return Db.find(sql);
	}

	/** 
	* @Title: getSummary 
	* @Description: 根据 id 获取手册情况
	* @param id
	* @return Record
	*/
	public static Record getSummary(Integer id) {
		return Db.findById("t_manual_sum", id);
	}

}
