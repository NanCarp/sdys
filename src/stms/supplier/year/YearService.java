package stms.supplier.year;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.IAtom;
import com.jfinal.plugin.activerecord.Record;

/**
 * @ClassName: YearService.java
 * @Description: 流公司年度考核 service
 * @author: LiYu
 * @date: 2017年6月26日下午6:14:50
 * @version: 1.0 版本初成
 */
public class YearService {
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
		Integer year = (Integer) params.getOrDefault("year",null);
		String level = (String) params.getOrDefault("level", "");
		String sql = "SELECT a.*,c.company_name AS supplier_name, "
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
				+ " LEFT JOIN t_supplier_month_assess  AS b "
				+ " ON a.supplier_id = b.supplier_id AND a.`year` = b.`year` "
				+ " LEFT JOIN t_company AS c "
				+ " ON a.supplier_id = c.id "
				+ " WHERE 1=1 ";
		if(forwarder!=""){
			sql += " AND c.supplier_name LIKE '%" + forwarder + "%' ";
		}
		if(year != null){
			sql += " AND a.year = " + year;
		}
		if(level != "") {
		    sql += " AND a.supplier_level = '" + level + "'";
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
	public static Record getYearById(Integer id) {
		return Db.find("SELECT a.*, b.company_name AS supplier_name "
				+ " FROM t_supplier_year_assess AS a "
				+ " LEFT JOIN t_company AS b "
				+ " ON a.supplier_id = b.id "
				+ " WHERE a.id = ? ", id).get(0);
	}

    /** 
    * @Title: getYearAlert 
    * @Description: 本年度考核公司列表
    * @return List<Record>
    */
    public static List<Record> getYearAlert() {
	    String sql = "SELECT a.supplier_id,b.company_name AS supplier_name, " +
                "SUM(CASE WHEN `month` = 1 THEN month_score END) m1, " +
                "SUM(CASE WHEN `month` = 2 THEN month_score END) m2, " +
                "SUM(CASE WHEN `month` = 3 THEN month_score END) m3, " +
                "SUM(CASE WHEN `month` = 4 THEN month_score END) m4, " +
                "SUM(CASE WHEN `month` = 5 THEN month_score END) m5, " +
                "SUM(CASE WHEN `month` = 6 THEN month_score END) m6, " +
                "SUM(CASE WHEN `month` = 7 THEN month_score END) m7, " +
                "SUM(CASE WHEN `month` = 8 THEN month_score END) m8, " +
                "SUM(CASE WHEN `month` = 9 THEN month_score END) m9, " +
                "SUM(CASE WHEN `month` = 10 THEN month_score END) m10, " +
                "SUM(CASE WHEN `month` = 11 THEN month_score END) m11, " +
                "SUM(CASE WHEN `month` = 12 THEN month_score END) m12 " +
                "FROM t_supplier_month_assess  AS a " +
                "LEFT JOIN t_company AS b " +
                "ON a.supplier_id = b.id " +
                "WHERE 1=1 " +
                "AND a.`year` = ? " +
                //"AND a.supplier_id NOT IN (SELECT supplier_id FROM t_supplier_year_assess WHERE `year` = ?) " +
                "GROUP BY a.`year`,a.supplier_id " +
                "ORDER BY a.`year` DESC ";
        Integer thisYear = LocalDate.now().getYear();
        //return Db.find(sql, thisYear, thisYear);
        return Db.find(sql, thisYear);
    }

	/** 
	* @Title: calculateYear 
	* @Description: 计算年度得分
	* @return boolean
	* @throws 
	*/
	public static boolean calculateYear() {
		/*Date now = new Date();
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
				
				boolean result1 = false;
				boolean result2 = false;

				for(Record record : list) {
					// 删除已评分的
                    Integer supplierId = record.getInt("supplier_id");
//                    Integer count = Db.queryInt("SELECT COUNT(*)  " +
//                            "FROM t_supplier_year_assess  " +
//                            "WHERE supplier_id = ?  " +
//                            "GROUP BY supplier_id ", supplierId);
                    List<Record> list = Db.find("SELECT * FROM t_supplier_year_assess WHERE supplier_id = ? ", supplierId);

                    if (list.size() > 0) {
                        Record originalRecord = list.get(0);
                        result1 = Db.delete("t_supplier_year_assess", originalRecord);
                        if (result1 == false) {
                            break;
                        }
                    } else {
                        result1 = true;
                    }

					int yearScore = record.getBigDecimal("year_score").intValue();
					String level = "";
					// TODO 修改判断方式
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
					result2 = Db.save("t_supplier_year_assess", record);
					
					if (!result2) {
						break;
					}
				}
				
				return result1 && result2;
			}
			
		});
		
		return succeed;*/
	    Map<String, Object> params = new HashMap<>();
	    Date now = new Date();
        int thisYear = now.getYear() + 1900;
        params.put("year", thisYear);
	    List<Record> list = getYearList(params);
	    for (Record record : list) {
	        // 公司 id
	        Integer supplierId = record.getInt("supplier_id");
	        // 年度得分
            Integer average = 0;
	        for (int i = 1; i <= 12; i++) {
	            BigDecimal score1 = record.getBigDecimal("m" + i);
	            if (score1 == null) {
	                continue;
	            }
	            Integer total = 0;
	            Integer count = 12 - i + 1;
	            for(int j = i; j <= 12; j++) {
	                Integer score2 = record.getBigDecimal("m" + j) == null ? 0 : record.getBigDecimal("m" + j).intValue() ;
	                total += score2;
	            }
	            average = total / count;
	            break;
	        }
	        // 等级
	        String level = convertScore2Level(average);
	        
	        record.clear();
	        record.set("supplier_id", supplierId);
	        record.set("supplier_level", level);
            record.set("create_time", now);
            record.set("review_time",now);
            record.set("year", thisYear);
	        record.set("year_score", average);
	    }
	    
	    boolean succeed = Db.tx(new IAtom() {

            @Override
            public boolean run() throws SQLException {
                if(list.isEmpty()) {
                    return true;
                }
                
                boolean result1 = false;
                boolean result2 = false;

                for(Record record : list) {
                    // 删除已评分的
                    Integer supplierId = record.getInt("supplier_id");
//                    Integer count = Db.queryInt("SELECT COUNT(*)  " +
//                            "FROM t_supplier_year_assess  " +
//                            "WHERE supplier_id = ?  " +
//                            "GROUP BY supplier_id ", supplierId);
                    List<Record> list = Db.find("SELECT * FROM t_supplier_year_assess WHERE supplier_id = ? ", supplierId);

                    if (list.size() > 0) {
                        Record originalRecord = list.get(0);
                        result1 = Db.delete("t_supplier_year_assess", originalRecord);
                        if (result1 == false) {
                            break;
                        }
                    } else {
                        result1 = true;
                    }

                    int yearScore = record.getInt("year_score");
                    String level = "";
                    // TODO 修改判断方式
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
                    Date now = new Date();
                    int year = now.getYear() + 1900;
                    record.set("supplier_level", level);
                    record.set("create_time", now);
                    record.set("review_time",now);
                    record.set("year", year);
                    result2 = Db.save("t_supplier_year_assess", record);
                    
                    if (!result2) {
                        break;
                    }
                }
                
                return result1 && result2;
            }
	        
	    });
	    
	    
	    return succeed;
	}


	private static String convertScore2Level(Integer average) {
	    String level = "";
        // TODO 修改判断方式
        if (average >= 96) {
            level = "AA";
        } else if (average >= 90) {
            level = "A";
        } else if (average >= 80) {
            level = "B";
        } else if (average >= 70) {
            level = "C";
        } else {
            level = "D";
        }
        return level;
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
    * @Title: getCriterionList 
    * @Description: 获取考核标准列表 supplier_score 拆分成 high 和 low
    * @return List<Record>
    */
    public static List<Record> getCriterionList() {
        // 等级列表
        List<Record> levelList =
                Db.find("SELECT id,supplier_level,supplier_score FROM `t_supplier_level`");
        // supplier_score 拆分成 high 和 low
        for (Record record : levelList) {
            Integer id = record.getInt("id");
            String level = record.getStr("supplier_level");
            String[] score = record.getStr("supplier_score").split("~");
            Integer low = Integer.parseInt(score[0]);
            Integer high = Integer.parseInt(score[1]);
            if (low > high) {
                high = low;
                low = Integer.parseInt(score[1]);
            }
            record.set("low", low);
            record.set("high", high);
        }

        return levelList;
    }
    
    /** 
	* @Title: getQualityByParams 
	* @Description: 根据参数获取货代资质
	* @param params
	* @return List<Record>
	* @throws 
	*/
	public static List<Record> getQualityByParams(String params) {
		String sql = "SELECT a.*, b.company_name AS supplier_name " +
                "FROM t_supplier_qualification AS a " +
                "LEFT JOIN t_company AS b " +
                "ON a.supplier_id = b.id " +
                "WHERE " + params;
		return Db.find(sql);
	}
	
	/** 
	* @Title: isAllSupplierCalculated 
	* @Description: 判断是否当年所有物流公司已评分
	* @return boolean
	*/
	public static boolean isAllSupplierCalculated() {
		// 当年年份
		Integer thisYear = LocalDate.now().getYear();
		// 当年未评分物流公司数量
		String sql = "SELECT COUNT(*) "
				+ "FROM t_supplier_month_assess "
				+ "WHERE `year` = ? "
				+ "AND supplier_id NOT IN "
				+ "(SELECT supplier_id FROM t_supplier_year_assess WHERE `year` = ?) ";
		Long count = Db.queryLong(sql, thisYear, thisYear);
		
		return count < 1; // 小于 1，已经全部评分，返回 true，否则，返回 false
	}
    /** 
    * @Title: isDuplicate 
    * @Description: 判断是否重复
    * @param year
    * @param supplierId
    * @return boolean
    */
    public static boolean isDuplicate(String year, String supplierId) {
        return Db.find("SELECT * FROM t_supplier_year_assess WHERE year = ? AND supplier_id = ?", year, supplierId).size() > 0;
    }
}
