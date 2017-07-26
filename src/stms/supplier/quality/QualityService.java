package stms.supplier.quality;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.servlet.http.HttpServletResponse;

import com.jfinal.kit.PropKit;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.IAtom;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;
import com.jfinal.upload.UploadFile;

import stms.utils.EncodeUtil;

/**
 * @ClassName: QualityService.java
 * @Description: 物流公司资质管理 service
 * @author: LiYu
 * @date: 2017年6月26日上午9:28:14
 * @version: 1.0 版本初成
 */
public class QualityService {
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
	* @return List<Record>
	* @throws 
	*/
	public static List<Record> getQualityList() {
		String forwarder = "";
		String abbreviation = "";
		String state = "";
		return getQualityList(forwarder, abbreviation, state);
	}
	
	/** 
	* @Title: getQualityList 
	* @Description: 获取货代资质列表
	* @return List<Record>
	* @throws 
	*/
	public static List<Record> getQualityList(String forwarder, String abbreviation, String state) {
		String sql = " SELECT a.*, b.company_name AS supplier_name " +
				" FROM t_supplier_qualification AS a " +
				" LEFT JOIN t_company AS b " +
				" ON a.supplier_id = b.id " +
				" WHERE 1=1 ";
		if (forwarder != ""){
			sql += "AND b.company_name LIKE '%" + forwarder + "%'";
		}
		if (abbreviation != null && !"".equals(abbreviation)) {
		    sql += "AND a.short_name LIKE '%" + abbreviation + "%'";
		}
		if (state != null && !"".equals(state)) {
            sql += "AND a.state = " + state;
        }
		
		// System.out.println(sql);
		return Db.find(sql);
	}

	/** 
	* @Title: getQualityById 
	* @Description: 根据 id 获取供应商资质 
	* @param id
	* @return Record
	* @throws 
	*/
	public static Record getQualityById(Integer id) {
	    String sql = " SELECT a.*, b.company_name AS supplier_name " +
                " FROM t_supplier_qualification AS a " +
                " LEFT JOIN t_company AS b " +
                " ON a.supplier_id = b.id " +
                " WHERE a.id = ? ";
		return Db.find(sql, id).get(0);
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
	* @Title: deleteQuality 
	* @Description: 根据 id 删除供应商资质 
	* @param id
	* @return boolean
	* @throws 
	*/
	public static boolean deleteQuality(Integer id) {
	    /*boolean succeed = Db.tx(new IAtom() {
	        boolean result = false;
            @Override
            public boolean run() throws SQLException {
                return false;
            }
        });*/
	    // 待删除记录
        Record record = Db.findById("t_supplier_qualification", id);
        // 待删除文件名
		String[] fileList = record.getStr("review_file").replace("quality/", "").split(",");
        // 删除结果
        boolean result = Db.delete("t_supplier_qualification", record);
        // 数据库删除成功，删除对应文件
        if (result == true) {
			for (String fileName : fileList) {
				deleteFile(fileName);
			}
        }

		return result;
	}
	
	/** 
    * @Title: deleteMonth 
    * @Description: 根据 id 数组删除供应商资质 
    * @param ids
    * @return boolean
    * @throws 
    */
    public static boolean deleteQuality(String[] ids) {
        boolean succeed = Db.tx(new IAtom(){
            boolean result = false;
            @Override
            public boolean run() throws SQLException {
                for (String id: ids){
                    /*// 待删除文件名
                    String fileName = getQualityById(Integer.parseInt(id)).getStr("file");
                    // 删除资质 
                    result = Db.deleteById("t_supplier_qualification", id);
                    if (result == false) {
                        break;
                    }
                    // 删除对应附件
                    deleteFile(fileName);*/
                    result = deleteQuality(Integer.parseInt(id));
                    
                }
                return result;
            }
            
        });
        
        return succeed;
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

    /**
     * @Title: getCompanyList
     * @Description: 获取公司列表
     * @return List<Record>
     * @throws
     */
    public static List<Record> getCompanyList() {
		String sql = "SELECT *  " +
				"FROM t_company " +
				"WHERE state = 1 " ;
		return Db.find(sql);
    }

    /** 
    * @Title: getCompanyNotInQuality 
    * @Description: 获取没有新增资质的公司列表
    * @return List<Record>
    * @throws 
    */
    public static List<Record> getCompanyNotInQuality() {
        String sql = "SELECT *  " +
            "FROM t_company " +
            "WHERE state = 1 " +
            "AND id NOT IN (SELECT supplier_id FROM t_supplier_qualification) " ;
        return Db.find(sql);

    }

	/** 
	* @Title: saveFile 
	* @Description: 保存文件
	* @param file
	* @return Map<String,Object>
	* @throws 
	*/
	public static Map<String, Object> saveFile(UploadFile file) {
        String originalName = file.getFileName();
        // 当前日期
        //LocalDate now = LocalDate.now();
        // 当前时间
        LocalDateTime now = LocalDateTime.now();
        // 文件路径
        String newName = originalName + "@" + now.toString().replace(":", " ");
        String path = PropKit.get("uploadPath")+"quality/" + newName;
        file.getFile().renameTo(new File(path));

        Map<String, Object> responseMsg = new HashMap<>();
        responseMsg.put("fileName", newName);

		return responseMsg;
	}
	
    /** 
    * @Title: saveFile 
    * @Description: 保存文件
    * @param file
    * @return Map<String,Object>
    * @throws 
    */
    public static Map<String, Object> saveFileSync(UploadFile file) {
        String originalName = file.getFileName();
        // 当前日期
        //LocalDate now = LocalDate.now();
        // 当前时间
        LocalDateTime now = LocalDateTime.now();
        // 文件路径
        String newName = "quality/" + originalName + "@" + now.toString().replace(":", " ");
        String path = PropKit.get("uploadPath")+ newName;
        file.getFile().renameTo(new File(path));

        Map<String, Object> responseMsg = new HashMap<>();
        responseMsg.put("fileName", newName);

        return responseMsg;
    }
	
    /** 
    * @Title: deleteFile 
    * @Description: 删除文件
    * @param fileName
    * @return Map<String,Object>
    * @throws 
    */
    public static Map<String,Object> deleteFile(String fileName) {
        boolean result = false;
        String path = PropKit.get("uploadPath")+"quality/" + fileName;
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
    * @throws IOException
    * @return void
    */
    public static void downloadFile(HttpServletResponse response, String file) throws IOException{
        byte[] buffer = new byte[4096];
        String ZipName = file.split("@")[0] + ".rar";
        response.setContentType("application/octet-stream");
        response.setHeader("Content-Disposition", "attachment; filename=" + EncodeUtil.toUtf8String(ZipName));
        ZipOutputStream out = new ZipOutputStream(response.getOutputStream());
        String file_url = file;
        String[] farr = file_url.split(",");
        File[] fs = new File[farr.length];
        for (int i = 0; i < farr.length; i++) {
            fs[i] = new File(PropKit.get("uploadPath")+ "quality/" + farr[i]);
        }
        for (int j = 0; j < fs.length; j++) {
            FileInputStream fis = new FileInputStream(fs[j]);
            out.putNextEntry(new ZipEntry(fs[j].getName().split("@")[0]));
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


    /** 
    * @Title: getFileList 
    * @Description: 资质文件列表
    * @param id
    * @return List<Record>
    */
    public static List<Record> getFileList(Integer id) {
        // 物流公司资质
        Record record = getQualityById(id);
        String[] fileStr = record.getStr("review_file").replace("quality/", "").split(",");
        List<Record> fileList = new ArrayList<>();
        if (!"".equals(fileStr[0])) {
            for(int i = 0; i < fileStr.length; i++) {
                Record file = new Record();
                file.set("fullName", fileStr[i]); // 文件全名：文件名 + 日期
                String[] temp = fileStr[i].split("@");
                file.set("name", temp[0]); // 文件名
                String date = temp[1].substring(0, 19).replace(" ", ":").replace("T", " ");
                file.set("uploadDate", date); // 日期
                fileList.add(file);
            }
        }
        return fileList;
    }


    /**
    * @Title: getFileList
    * @Description: 获取物流公司附件列表
    * @param id void
     * @return
    */
    /*public static String getFileList(Integer id) {
        Record record = Db.findById("t_supplier_qualification", id);
        String fileList = record.get("review_file");
        return fileList;
    }*/

}
