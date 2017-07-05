package stms.supplier.quality;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.jfinal.aop.Before;
import com.jfinal.core.Controller;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;
import com.jfinal.upload.UploadFile;

import stms.interceptor.ManageInterceptor;
import stms.supplier.month.MonthService;

/**
 * @ClassName: QualityController.java
 * @Description: 物流公司资质管理控制器
 * @author: LiYu
 * @date: 2017年6月26日上午9:23:06
 * @version: 1.0 版本初成
 */
@Before(ManageInterceptor.class)
public class QualityController extends Controller {
	/** 
	* @Title: index 
	* @Description: 物流公司资质列表
	* @param 
	* @return void
	* @throws 
	*/
	public void index() {
	    // 公司名称
		String forwarder = getPara("forwarder");
		// 简称
		String abbreviation = getPara("abbreviation");
		setAttr("abbreviation", abbreviation);
		// 状态
		String state = getPara("state");
		setAttr("state", state);
		try {
			forwarder = URLDecoder.decode(forwarder==null?"":forwarder, "utf-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		
		List<Record> list = QualityService.getQualityList(forwarder, abbreviation, state);
		setAttr("qualityList", list);
		setAttr("forwarder", forwarder);
		render("quality.html");
	}
	
	/** 
	* @Title: getQuality 
	* @Description: 获取物流公司资质
	* @param 
	* @return void
	* @throws 
	*/
	public void getQuality() {
		//　获取物流公司资质　id
		Integer id = getParaToInt(0, null);
		// id 不为空，即为编辑
		if (id != null){
			Record quality = QualityService.getQualityById(id);
			setAttr("quality", quality);
            // 文件名
            setAttr("fileName", quality.getStr("review_file"));
            // 获取公司列表
            List<Record> companyList = QualityService.getCompanyList();
            setAttr("companyList", companyList);
		} else {
            // 获取公司列表
            List<Record> companyList = QualityService.getCompanyNotInQuality();
            setAttr("companyList", companyList);
        }

		render("quality_detail.html");
	}
	/**
	 * @desc:上传资质文件
	 */
	public void uploadfile(){
		UploadFile uf = getFile("file");
		System.out.println(uf);
		
	}
	
	/** 
	* @Title: saveQuality 
	* @Description: 保存物流公司资质，文件同步传输 TODO
	* @param 
	* @return void
	* @throws 
	*/
	/*public void saveQuality() throws Exception{
	    // 资质文件
		UploadFile file = getFile();
		if (file != null){

        }
        String originalName = file.getFileName();
        String newName = PropKit.get("uploadPath")+"temp/" + originalName;
        file.getFile().renameTo(new File(newName));
		// 资质 id
		Integer id = getParaToInt("id", null);
		// 货代名称
		//String forwarder = getPara("forwarder").trim();
        // 物流公司 id
        String supplierId = getPara("companyId");
		// 货代简称
		String abbreviation = getPara("abbreviation").trim();
		// 状态：1：合格，2：备选，0：不合格
		Integer state = getParaToInt("state");
		// 文件

		//String fileUrl = getPara("file");

		// 备注
		String remark = getPara("remark");

		Record record = new Record();
		//record.set("supplier_name", forwarder);
		record.set("supplier_id", supplierId);
		record.set("short_name", abbreviation);
		record.set("state", state);

		record.set("review_file", originalName);

		record.set("remark", remark);
		// 修改时间
		Date now = new Date();
		record.set("review_time", now);

		boolean result = false;
		if (id != null) {
			record.set("id", id);
			result = Db.update("t_supplier_qualification", record);
		} else {
			// 创建时间
			record.set("create_time", now);
			// 注册代码
			String registrationCode = "123abc";
			record.set("registration_code", registrationCode);

			result = Db.save("t_supplier_qualification", record);

		}

		renderJson(result);
	}*/
    public void saveQuality() {
        // 资质 id
        Integer id = getParaToInt("id", null);
        // 货代名称
        //String forwarder = getPara("forwarder").trim();
        // 物流公司 id
        String supplierId = getPara("companyId");
        // 货代简称
        String abbreviation = getPara("abbreviation").trim();
        // 状态：1：合格，2：备选，0：不合格
        Integer state = getParaToInt("state");
        // 文件
        String fileUrl = getPara("file","");
        // 备注
        String remark = getPara("remark");

        Record record = new Record();
        //record.set("supplier_name", forwarder);
        record.set("supplier_id", supplierId);
        record.set("short_name", abbreviation);
        record.set("state", state);
        record.set("review_file", fileUrl);
        record.set("remark", remark);
        // 修改时间
        Date now = new Date();
        record.set("review_time", now);

        boolean result = false;
        if (id != null) {
            //Record originalRecord = Db.findById("t_supplier_qulification", id);
            record.set("id", id);
            result = Db.update("t_supplier_qualification", record);
        } else {
            // 创建时间
            record.set("create_time", now);
            // 注册代码
            String registrationCode = "123abc";
            record.set("registration_code", registrationCode);

            result = Db.save("t_supplier_qualification", record);
        }

        renderJson(result);
    }
	
	/** 
	* @Title: deleteQuality 
	* @Description: 删除物流公司资质 
	* @param 
	* @return void
	* @throws 
	*/
	public void deleteQuality() {
		// 物流公司资质 id
	    String idStr = getPara();
        String[] ids = idStr.split(",");
		// 删除结果
		boolean result = QualityService.deleteQuality(ids);
        
        renderJson(result);
	}
	
	/** 
	* @Title: verifyQuality 
	* @Description: 审核物流公司资质
	* @param 
	* @return void
	* @throws 
	*/
	public void verifyQuality() {
		// 物流公司资质 id
		Integer id = getParaToInt();
		// 审核结果
		boolean result = QualityService.verifyQuality(id);
		
		renderJson(result);
	}
	
	/** 
	* @Title: cancelQuality
	* @Description: 撤销物流公司资质
	* @param 
	* @return void
	* @throws 
	*/
	public void cancelQuality() {
		// 物流公司资质 id
		Integer id = getParaToInt();
		// 撤销结果
		boolean result = QualityService.cancelQuality(id);
		
		renderJson(result);
	}

	// 上传资质文件
    public void uploadQualityFile() {

	    UploadFile file = getFile();

        Map<String, Object> responseMsg = QualityService.saveFile(file);

        renderJson(responseMsg);
    }

    // 删除资质文件
    public void deleteQualityFile() {
	    // 文件名
        String fileName = getPara("fileName");
        // 删除资质文件
        Map<String, Object> responseMsg = QualityService.deleteFile(fileName);

        renderJson(responseMsg.get("result"));
    }

    // 下载资质文件
    public void downloadQualityFile() throws IOException {
	    // 物流公司 id
	    Integer id = getParaToInt();
	    // 资质记录
        Record record = QualityService.getQualityById(id);
        // 文件名
        String fileName = record.getStr("review_file");
        // 下载资质文件
        QualityService.downloadFile(getResponse(), fileName);

	    renderNull();
    }

    // 根据文件名下载文件
	public void downloadByFileName() throws IOException {
		// 文件名
		String fileName = getPara("fullName");
		// 下载资质文件
		QualityService.downloadFile(getResponse(), fileName);

		renderNull();
	}


    // 查看资质文件列表
    public void getFileList() {
        // 资质 id
        Integer id = getParaToInt();
        // 物流公司资质
        Record record = QualityService.getQualityById(id);
        // 文件列表 TODO service
        String[] fileStr = record.getStr("review_file").split(",");
        List<Record> fileList = new ArrayList<>();
        Record file = new Record();
        if (!"".equals(fileStr[0])) {
            for(int i = 0; i < fileStr.length; i++) {
                file.set("fullName", fileStr[i]); // 文件全名：文件名 + 日期
                String[] temp = fileStr[i].split("@");
                file.set("name", temp[0]); // 文件名
                file.set("uploadDate", temp[1]); // 日期
                fileList.add(file);
            }
        }
        
        System.out.println(fileList);
        setAttr("fileList", fileList);
        
        render("quality_file_list.html");
    }
    
    

}
