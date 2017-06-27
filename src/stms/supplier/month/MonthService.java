package stms.supplier.month;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.servlet.http.HttpServletResponse;

import com.jfinal.kit.PropKit;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.IAtom;
import com.jfinal.plugin.activerecord.Record;
import com.jfinal.upload.UploadFile;

import stms.utils.EncodeUtil;

/**
 * @ClassName: MonthService.java
 * @Description: 物流公司月度考核 service
 * @author: LiYu
 * @date: 2017年6月26日下午6:11:11
 * @version: 1.0 版本初成
 */
public class MonthService {

	/** 
	* @Title: getMonthList 
	* @Description: 获得月度考核列表
	* @param params
	* @return List<Record>
	*/
	public static List<Record> getMonthList(Map<String, Object> params) {
		String forwarder = (String) params.getOrDefault("forwarder", "");
		String year = (String) params.getOrDefault("year","");
		String month = (String) params.getOrDefault("month","");
		String sql = "SELECT a.*,c.company_name AS supplier_name  " +
                "FROM t_supplier_month_assess AS a  " +
                "LEFT JOIN t_supplier_qualification AS b " +
                "ON a.supplier_id = b.supplier_id " +
                "LEFT JOIN t_company AS c " +
                "ON a.supplier_id = c.id " +
                "WHERE 1=1 ";
		if(forwarder!=""){
			sql += " AND c.company_name LIKE '%" + forwarder + "%'";
		}
		if(year != ""){
			sql += " AND a.year = " + year;
		}
		if(month != ""){
			sql += " AND a.month = " + month;
		}
		sql += " ORDER BY a.`year` DESC, a.`month` DESC ";
		
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
		record.set("file", map.get("file"));
		record.set("remark", map.get("remark"));
		record.set("review_time", map.get("now"));
		if (id==null) {// 新增，创建时间
			record.set("create_time", map.get("now"));
		}
		
		boolean succeed = Db.tx(new IAtom() {
			boolean result = false;
			@Override
			public boolean run() throws SQLException {

				if(id == null) {
					result = Db.save("t_supplier_month_assess", record);
				} else {
					record.set("id", map.get("id"));
					result = Db.update("t_supplier_month_assess", record);
				}
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
				    // 待删除文件名
                    String fileName = getMonthById(Integer.parseInt(id)).getStr("file");
                    // 删除月度考核
					result = Db.deleteById("t_supplier_month_assess", id);
					if (result == false) {
						break;
					}
					// 删除对应附件
                    deleteFile(fileName);
                }
				return result;
			}
			
		});
		
		return succeed;
	}

	/** 
	* @Title: getMonthListChecked 
	* @Description: 根据物流公司 id、年份获得审核月份列表
	* @param supplierId
	* @param year
	* @return List<Record>
	*/
	public static List<Record> getMonthListChecked(Integer supplierId, Integer year) {
        String sql = "SELECT `month` FROM t_supplier_month_assess " +
				"WHERE supplier_id = ? " +
				"AND `year` = ? ";
        return Db.find(sql, supplierId, year);
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
 	* @Title: saveFile 
 	* @Description: 保存文件
 	* @param file
 	* @return Map<String,Object>
 	*/
 	public static Map<String, Object> saveFile(UploadFile file) {
         String originalName = file.getFileName();
         String newName = PropKit.get("uploadPath")+"temp/" + originalName;
         file.getFile().renameTo(new File(newName));

         Map<String, Object> responseMsg = new HashMap<>();
         responseMsg.put("fileName", originalName);

 		return responseMsg;
 	}
 	
     /** 
    * @Title: deleteFile 
    * @Description: 删除文件
    * @param fileName
    * @return Map<String,Object>
    */
    public static Map<String, Object> deleteFile(String fileName) {
         boolean result = false;
         String path = PropKit.get("uploadPath")+"temp/" + fileName;
         File file = new File(path);
         if (file.exists() && file.isFile()) {
             file.delete();
             result = true;
         }

         Map<String, Object> responseMsg = new HashMap<>();
         responseMsg.put("result", result);

         return responseMsg;
     }

     /** 
    * @Title: downloadFile 
    * @Description: 下载文件
    * @param response
    * @param file
    * @throws IOException void
    */
    public static void downloadFile(HttpServletResponse response, String file) throws IOException{
         byte[] buffer = new byte[4096];
         String ZipName = file + ".rar";
         response.setContentType("application/octet-stream");
         response.setHeader("Content-Disposition", "attachment; filename=" + EncodeUtil.toUtf8String(ZipName));
         ZipOutputStream out = new ZipOutputStream(response.getOutputStream());
         String file_url = file;
         String[] farr = file_url.split(",");
         File[] fs = new File[farr.length];
         for (int i = 0; i < farr.length; i++) {
             fs[i] = new File(PropKit.get("uploadPath")+ "temp/" + farr[i]);
         }
         for (int j = 0; j < fs.length; j++) {
             FileInputStream fis = new FileInputStream(fs[j]);
             out.putNextEntry(new ZipEntry(fs[j].getName()));
             int len;
             // 读入需要下载的文件的内容，打包到zip文件
             while ((len = fis.read(buffer)) > 0) {
                 out.write(buffer, 0, len);
             }
             out.closeEntry();
             fis.close();
         }
         out.close();
     }
    
}
