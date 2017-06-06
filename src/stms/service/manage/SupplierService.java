package stms.service.manage;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.IAtom;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;

public class SupplierService {

	public static Page<Record> getQualityByPage(Integer pageNumber,String forwarder) {
		String select = "SELECT * FROM t_supplier_qualification WHERE 1=1 ";
		String sqlExceptSelect = "";
		if (forwarder != ""){
			sqlExceptSelect = "AND supplier_name LIKE '%" + forwarder + "%'";
		}
		
		return Db.paginate(pageNumber, 10, select, sqlExceptSelect);
	}


	/** 
	* @Title: getQualityList 
	* @Description: 获取货代资质列表
	* @param
	* @return List<Record>
	* @throws 
	*/
	public static List<Record> getQualityList() {
		String forwarder = "";
		return getQualityList(forwarder);
	}
	
	/** 
	* @Title: getQualityList 
	* @Description: 获取货代资质列表
	* @param
	* @return List<Record>
	* @throws 
	*/
	public static List<Record> getQualityList(String forwarder) {
		String sql = "SELECT * FROM t_supplier_qualification WHERE 1=1 ";
		if (forwarder != ""){
			sql += "AND supplier_name LIKE '%" + forwarder + "%'";
		}
		return Db.find(sql);
	}

	/** 
	* @Title: getQualityById 
	* @Description: 根据 id 获取供应商资质 
	* @param id
	* @param 
	* @return Record
	* @throws 
	*/
	public static Record getQualityById(Integer id) {
		return Db.findById("t_supplier_qualification", id);
	}
	
	/** 
	* @Title: getQualityByParams 
	* @Description: 根据参数获取货代资质
	* @param params
	* @return List<Record>
	* @throws 
	*/
	public static List<Record> getQualityByParams(String params) {
		String sql = "SELECT * FROM t_supplier_qualification WHERE " + params;
		return Db.find(sql);
	}
	
	/** 
	* @Title: deleteQuality 
	* @Description: 根据 id 删除供应商资质
	* @param id
	* @return boolean
	* @throws 
	*/
	public static boolean deleteQuality(Integer id) {
		return Db.deleteById("t_supplier_qualification", id);
	}
	
	/** 
	* @Title: verifyQuality 
	* @Description: 审核供应商资质
	* @param id
	* @return boolean
	* @throws 
	*/
	public static boolean verifyQuality(Integer id) {
		
		int count = -1;
		count = Db.update("UPDATE t_supplier_qualification SET state = 1 WHERE id = " + id);
		return count == 1;
	}
	
	/** 
	* @Title: cancelQuality
	* @Description: 撤销供应商资质
	* @param id
	* @return boolean
	* @throws 
	*/
	public static boolean cancelQuality(Integer id) {
		int count = -1;
		count = Db.update("UPDATE t_supplier_qualification SET state = 2 WHERE id = " + id);
		return count == 1;
	}
	/*********************供应商信息管理*************************/
	/** 
	* @Title: getInfoList 
	* @Description: 获取供应商信息列表
	* @param 
	* @return List<Record>
	* @throws 
	*/
	public static List<Record> getInfoList() {
		String forwarder = "";
		return getInfoList(forwarder);
	}

	/** 
	* @Title: getInfoList 
	* @Description: 获取供应商信息列表 
	* @param forwarder
	* @return List<Record>
	* @throws 
	*/
	public static List<Record> getInfoList(String forwarder) {
		String sql = " SELECT a.*,b.registration_code,b.supplier_name,b.state "
				+ " FROM `t_supplier` AS a LEFT JOIN t_supplier_qualification AS b "
				+ " ON a.supplier_id = b.supplier_id WHERE 1=1 ";
		if (forwarder != ""){
			sql += " AND b.supplier_name LIKE '%" + forwarder + "%'";
		}
		return Db.find(sql);
	}
	
	/** 
	* @Title: getInfoById 
	* @Description: 根据 id 获取供应商信息
	* @param id
	* @return Record
	* @throws 
	*/
	public static Record getInfoById(Integer id) {
		return Db.findById("t_supplier", id);
	}

	
	/** 
	* @Title: deleteInfoById 
	* @Description: 根据 id 删除货代信息记录
	* @param id
	* @return boolean
	* @throws 
	*/
	public static boolean deleteInfoById(Integer id) {
		return Db.deleteById("t_supplier", id);
	}

	/*********************供应商考核标准*************************/
	/** 
	* @Title: getLevelList 
	* @Description: 获取供应商考核标准列表 
	* @param 
	* @return List<Record>
	* @throws 
	*/
	public static List<Record> getLevelList() {
		
		return Db.find("SELECT * FROM t_supplier_level");
	}

	/** 
	* @Title: deleteLevel 
	* @Description: 根据 id 数组删除考核标准
	* @param ids
	* @return boolean
	* @throws 
	*/
	public static boolean deleteLevel(String[] ids) {
		boolean succeed = Db.tx(new IAtom(){
			boolean result = false;
			@Override
			public boolean run() throws SQLException {
				for (String id: ids){
					result = Db.deleteById("t_supplier_level", id);
					if (!result) {
						break;
					}
				}
				return result;
			}
			
		});
		
		return succeed;
	}
	
	/*********************供应商月度考核*************************/

	public static List<Record> getMonthList(Map<String, Object> params) {
		String forwarder = (String) params.getOrDefault("forwarder", "");
		String year = (String) params.getOrDefault("year","");
		String month = (String) params.getOrDefault("month","");
		String sql = "SELECT a.*,b.supplier_name "
				+ " FROM t_supplier_month_assess AS a "
				+ " LEFT JOIN t_supplier_qualification AS b "
				+ " ON a.supplier_id = b.supplier_id WHERE 1=1 ";
		if(forwarder!=""){
			sql += " AND b.supplier_name LIKE '%" + forwarder + "%'";
		}
		if(year != ""){
			sql += " AND a.year = " + year;
		}
		if(month != ""){
			sql += " AND a.month = " + month;
		}
		sql += " ORDER BY a.year DESC, a.`month` DESC ";
		
		return Db.find(sql);
	}
	/** 
	* @Title: saveMonth 
	* @Description: 保存月度考核 
	* @param map
	* @return boolean
	* @throws 
	*/
	public static boolean saveMonth(Map<String, Object> map) {
		Integer id = (Integer) map.get("id");
		Record record = new Record();
		record.set("supplier_id", map.get("supplierId"));
		record.set("month_score", map.get("score"));
		record.set("year", map.get("year"));
		record.set("month", map.get("month"));
		record.set("month_score", map.get("score"));
		record.set("supplier_level", map.get("level"));
		//record.set("file", map.get("file"));
		record.set("remark", map.get("remark"));
		record.set("review_time", map.get("now"));
		if (id==null) {// 新增，创建时间
			record.set("create_time", map.get("now"));
		}
		
		boolean succeed = Db.tx(new IAtom() {

			@Override
			public boolean run() throws SQLException {
				boolean result = false;
				if(id == null) {
					result = Db.save("t_supplier_month_assess", record);
				} else {
					record.set("id", map.get("id"));
					result = Db.update("t_supplier_month_assess", record);
				}
				
				/*int count = Db.update("update t_supplier "
						+ " set supplier_level = ?,review_time = ? "
						+ " WHERE supplier_id = ?",
						map.get("level"), map.get("now"),map.get("supplierId"));*/
				int count = 1;
				return result && count == 1;
			}
			
		});
		return succeed;
	}


	/** 
	* @Title: deleteMonthById 
	* @Description: 根据 id 删除月度考核
	* @param id
	* @return boolean
	* @throws 
	*/
	public static boolean deleteMonthById(Integer id) {
		return Db.deleteById("t_supplier_month_assess", id);
	}


	/** 
	* @Title: getMonthById 
	* @Description: 根据 id 查询月度考核
	* @param id
	* @return Record
	* @throws 
	*/
	public static Record getMonthById(Integer id) {
		return Db.findById("t_supplier_month_assess", id);
	}

	/** 
	* @Title: deleteMonth 
	* @Description: 根据 id 数组删除月度考核
	* @param ids
	* @return boolean
	* @throws 
	*/
	public static boolean deleteMonth(String[] ids) {
		boolean succeed = Db.tx(new IAtom(){
			boolean result = false;
			@Override
			public boolean run() throws SQLException {
				for (String id: ids){
					result = Db.deleteById("t_supplier_month_assess", id);
					if (!result) {
						break;
					}
				}
				return result;
			}
			
		});
		
		return succeed;
	}
	
	/*********************供应商年度考核*************************/
	/** 
	* @Title: getYearList 
	* @Description: 查询供应商年度考核列表
	* @return List<Record>
	* @throws 
	*/
	public static List<Record> getYearList() {
		Map<String, Object> params = new HashMap<>();
		// 当前年份
		params.put("year", LocalDate.now().getYear());
		
		return getYearList(params);
	}
	/** 
	* @Title: getYearList 
	* @Description: 查询供应商年度考核列表
	* @param params
	* @return List<Record>
	* @throws 
	*/
	public static List<Record> getYearList(Map<String, Object> params) {
		String forwarder = (String) params.getOrDefault("forwarder", "");
		String year = (String) params.getOrDefault("year","");
		String sql = "SELECT a.*,c.supplier_name, "
				+ " SUM(CASE WHEN `month` = 1 THEN month_score END) m1, "
				+ " SUM(CASE WHEN `month` = 2 THEN month_score END) m2, "
				+ " SUM(CASE WHEN `month` = 3 THEN month_score END) m3, "
				+ " SUM(CASE WHEN `month` = 4 THEN month_score END) m4, "
				+ " SUM(CASE WHEN `month` = 5 THEN month_score END) m5, "
				+ " SUM(CASE WHEN `month` = 6 THEN month_score END) m6, "
				+ " SUM(CASE WHEN `month` = 7 THEN month_score END) m7, "
				+ " SUM(CASE WHEN `month` = 8 THEN month_score END) m8, "
				+ " SUM(CASE WHEN `month` = 9 THEN month_score END) m9, "
				+ " SUM(CASE WHEN `month` = 10 THEN month_score END) m10, "
				+ " SUM(CASE WHEN `month` = 11 THEN month_score END) m11, "
				+ " SUM(CASE WHEN `month` = 12 THEN month_score END) m12 "
				+ " FROM t_supplier_year_assess AS a "
				+ " INNER JOIN t_supplier_month_assess  AS b "
				+ " ON a.supplier_id = b.supplier_id AND a.`year` = b.`year` "
				+ " LEFT JOIN t_supplier_qualification AS c "
				+ " ON a.supplier_id = c.supplier_id "
				+ " WHERE 1=1 ";
		if(forwarder!=""){
			sql += " AND c.supplier_name LIKE '%" + forwarder + "%' ";
		}
		if(year != ""){
			sql += " AND a.year = " + year;
		}
		sql += " GROUP BY a.`year`,a.supplier_id "
				+ "ORDER BY a.`year` DESC ";
		
		return Db.find(sql);
	}
	/** 
	* @Title: saveYear 
	* @Description: 保存供应商年度考核
	* @param record
	* @return boolean
	* @throws 
	*/
	public static boolean saveYear(Record record) {
		// 年度考核 id
		Integer id = record.getInt("id");
		// 保存结果
		boolean result = false;
		// 编辑
		if (id != null) {
			result = Db.update("t_supplier_year_assess", record);
		} else {// 新增
			result = Db.save("t_supplier_year_assess", record);
		}
		
		return result;
	}


	/** 
	* @Title: getYearById 
	* @Description: 根据 id 查询年度考核
	* @param id
	* @return Record
	* @throws 
	*/
	public static List<Record> getYearById(Integer id) {
		return Db.find("SELECT a.*, b.supplier_name "
				+ " FROM t_supplier_year_assess AS a "
				+ " LEFT JOIN t_supplier_qualification AS b "
				+ " ON a.supplier_id = b.supplier_id "
				+ " WHERE a.id = ? ", id);
	}


	/** 
	* @Title: calculateYear 
	* @Description: 计算年度得分
	* @return boolean
	* @throws 
	*/
	public static boolean calculateYear() {
		Date now = new Date();
		// 当前年份
		int year = now.getYear() + 1900;
		String sql = "SELECT supplier_id, ROUND(AVG(month_score)) AS year_score "
				+ " FROM t_supplier_month_assess "
				+ " WHERE `year` = " + year
				+ " AND supplier_id NOT IN (SELECT supplier_id FROM t_supplier_year_assess WHERE `year` = "+year+") "
				+ " GROUP BY supplier_id ";
		List<Record> list = Db.find(sql);
		
		boolean succeed = Db.tx(new IAtom() {

			@Override
			public boolean run() throws SQLException {
				if(list.isEmpty()) {
					return true;
				}
				
				boolean result = false;
				
				for(Record record : list) {
					int yearScore = record.getBigDecimal("year_score").intValue();
					String level = "";
					if (yearScore >= 96) {
						level = "AA";
					} else if (yearScore >= 90) {
						level = "A";
					} else if (yearScore >= 80) {
						level = "B";
					} else if (yearScore >= 70) {
						level = "C";
					} else {
						level = "D";
					}
					record.set("supplier_level", level);
					record.set("create_time", now);
					record.set("review_time",now);
					record.set("year", year);
					result = Db.save("t_supplier_year_assess", record);
					
					if (!result) {
						break;
					}
				}
				
				return result;
			}
			
		});
		
		return succeed;
	}


	/** 
	* @Title: deleteYearByIds 
	* @Description: 通过 id 数组删除年度考核
	* @param ids
	* @return boolean
	* @throws 
	*/
	public static boolean deleteYearByIds(String[] ids) {
		boolean succeed = Db.tx(new IAtom(){
			boolean result = false;
			@Override
			public boolean run() throws SQLException {
				for (String id: ids){
					result = Db.deleteById("t_supplier_year_assess", id);
					if (!result) {
						return false;
					}
				}
				return true;
			}
			
		});
		
		return succeed;
	}


	/** 
	* @Title: calculateYearAlert 
	* @Description: 所有公司未审核月份列表
	* @return List<Record>
	* @throws 
	*/
	public static List<Record> calculateYearAlert() {
		List<Record> list = getYearList();
		for (Record record: list) {
			
		}
		
		return list;
	}









}
