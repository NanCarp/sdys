package stms.controller.manage;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.jfinal.aop.Before;
import com.jfinal.core.Controller;
import com.jfinal.kit.JsonKit;
import com.jfinal.kit.PropKit;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.IAtom;
import com.jfinal.plugin.activerecord.Record;
import com.jfinal.upload.UploadFile;

import com.jfinal.upload.UploadFile;
import stms.interceptor.ManageInterceptor;
import stms.service.manage.SupplierService;
import stms.utils.ExcelKit;

/**
 * @ClassName: SupplierController
 * @Description: 物流公司管理
 * @author: LiYu
 * @date: 2017年5月12日下午3:40:50
 * @version: 1.0 版本初成
 */
// @Before(ManageInterceptor.class)
public class SupplierController extends Controller{
	/*********************物流公司资质管理*************************/
	/** 
	* @Title: quality 
	* @Description: 物流公司资质列表
	* @param 
	* @return void
	* @throws 
	*/
	public void quality() {
		Integer pageNumber = getParaToInt(0) == null ? 1 : getParaToInt(0);
		String forwarder = getPara(1);
		try {
			forwarder = URLDecoder.decode(forwarder==null?"":forwarder, "utf-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		/*Page<Record> qualityPage = SupplierService.getQualityByPage(pageNumber,forwarder);
		setAttr("qualityList", qualityPage.getList());
		setAttr("pageNumber", qualityPage.getPageNumber());
		setAttr("totalPage", qualityPage.getTotalPage());
		setAttr("totalRow", qualityPage.getTotalRow());
		setAttr("forwarder", forwarder);*/
		
		List<Record> list = SupplierService.getQualityList(forwarder);
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
			Record quality = SupplierService.getQualityById(id);
			setAttr("quality", quality);
            // 文件名
            setAttr("fileName", quality.getStr("review_file"));
            // 获取公司列表
            List<Record> companyList = SupplierService.getCompanyList();
            setAttr("companyList", companyList);
		} else {
            // 获取公司列表
            List<Record> companyList = SupplierService.getCompanyNotInQuality();
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
		Integer id = getParaToInt();
		// 删除结果
		boolean result = SupplierService.deleteQuality(id);
		
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
		boolean result = SupplierService.verifyQuality(id);
		
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
		boolean result = SupplierService.cancelQuality(id);
		
		renderJson(result);
	}

	// 上传资质文件
    public void uploadQualityFile() {

	    UploadFile file = getFile();

        Map<String, Object> responseMsg = SupplierService.saveFile(file);

        renderJson(responseMsg);
    }

    // 删除资质文件
    public void deleteQualityFile() {
	    // 文件名
        String fileName = getPara("fileName");
        // 删除资质文件
        Map<String, Object> responseMsg = SupplierService.deleteFile(fileName);

        renderJson(responseMsg.get("result"));
    }

    // 下载资质文件
    public void downloadQualityFile() throws IOException {
	    // 物流公司 id
	    Integer id = getParaToInt();
	    // 资质记录
        Record record = SupplierService.getQualityById(id);
        // 文件名
        String fileName = record.getStr("review_file");
        // 下载资质文件
        SupplierService.downloadFile(getResponse(), fileName);

	    renderNull();
    }

	/*********************物流公司信息管理*************************/
	/** 
	* @Title: info 
	* @Description: 物流公司信息列表 
	* @param 
	* @return void
	* @throws 
	*/
	public void info() {
		String forwarder = getPara(1);
		try {
			forwarder = URLDecoder.decode(forwarder==null?"":forwarder, "utf-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		// 物流公司信息列表
		List<Record> infoList = SupplierService.getInfoList(forwarder);
		setAttr("infoList", infoList);
		setAttr("forwarder", forwarder);
		render("info.html");
	}
	
	/** 
	* @Title: getInfo 
	* @Description: 获取物流公司信息 
	* @param 
	* @return void
	* @throws 
	*/
	public void getInfo() {
		//　获取物流公司信息　id
		Integer id = getParaToInt(0, null);
		// id 不为空，即为编辑
		if (id != null){
			//　获取物流公司信息
			Record info = SupplierService.getInfoById(id);
			setAttr("info", info);
		} else{
			// 物流公司名称，id
			List<Record> forwarderList = SupplierService.getCompanyListQualified();
			setAttr("forwarderList", forwarderList);
		}

		// 等级列表
        List<Record> levelList = SupplierService.getLevelList();
        setAttr("levelList", levelList);

        render("info_detail.html");
	}

	/**
	 * @Title: saveInfo
	 * @Description: 保存物流公司信息
	 * @param
	 * @return void
	 * @throws
	 */
	public void saveInfo() {
		
		// 记录 id
		Integer id = getParaToInt("id");
		// 货代 id
		Integer supplierId = getParaToInt("supplierId");
		// 合同号
		String contractNo = getPara("contractNo") ;
		// 货代等级
		String level = getPara("level");
		// 货代类型
		Integer state = getParaToInt("state");
		// 业务范围
		String businessScope = getPara("businessScope");
		// 合作年限
		Integer cooperationDuration = getParaToInt("cooperationDuration");
		// 保证金
		String deposit = getPara("deposit");
		// 联系人
		String contact = getPara("contact");
		// 联系电话
		String phone = getPara("phone");
		// 邮箱
		String email = getPara("email");
		// 备注
		String remark = getPara("remark");
		// 修改时间
		Date now = new Date();
		
		Record record = new Record();
		record.set("supplier_id", supplierId);
		record.set("contract_no", contractNo);
		record.set("supplier_level", level);
		record.set("supplier_field", businessScope);
		record.set("supplier_years", cooperationDuration);
		record.set("supplier_bail", deposit);
		record.set("contact", contact);
		record.set("phone", phone);
		record.set("email", email);
		record.set("remark", remark);
		record.set("review_time", now);
		// 新增货代信息
		// 操作结果
		boolean result = false;
		if (id == null) {
			// 创建时间
			record.set("create_time", now);
			result = Db.save("t_supplier", record);
		} else {// 更新货代信息
			record.set("id", id);
			result = Db.tx(new IAtom() {

				@Override
				public boolean run() throws SQLException {
					boolean result = false;
					result = Db.update("t_supplier", record);
					int count = Db.update("update t_supplier_qualification "
							+ " SET state = ?,review_time = ? "
							+ " WHERE supplier_id = ?",
							state, now, supplierId);
					return result && count == 1;
				}
				
			});
		}
		
		renderJson(result);
		
	}
	
	/** 
	* @Title: deleteInfo 
	* @Description: 删除物流公司信息
	* @param 
	* @return void
	* @throws 
	*/
	public void deleteInfo() {
		// 货代信息 id
		Integer id = getParaToInt();
		// 通过 id 删除货代信息
		boolean result = SupplierService.deleteInfoById(id);
		renderJson(result);
	}
	/*********************物流公司考核标准*************************/
	/** 
	* @Title: level 
	* @Description: 物流公司考核标准
	* @param 
	* @return void
	* @throws 
	*/
	public void level() {
		// 考核标准列表
		List<Record> levelList = SupplierService.getLevelList();
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
		boolean result = SupplierService.deleteLevel(ids);
		
		//result = Db.deleteById("t_supplier_level", id);
		
		renderJson(result);
		
	}
	
	/*********************物流公司月度考核*************************/
	/** 
	* @Title: month 
	* @Description: 物流公司月度考核列表 
	* @param 
	* @return void
	* @throws 
	*/
	public void month() {
		// 物流公司名称
		String forwarder = getPara("forwarder","");
		// 年份
		String year = getPara("year","");
		// 月份
		String month = getPara("month","");
		
		Map<String,Object> params = new HashMap<>();
		params.put("forwarder", forwarder);
		params.put("year", year);
		params.put("month", month);
		// 月度考核列表 
		List<Record> monthList = SupplierService.getMonthList(params);
		setAttr("monthList", monthList);
		setAttr("forwarder", forwarder);
		setAttr("year", year);
		setAttr("month", month);

		render("month.html");
	}
	
	/** 
	* @Title: getMonth 
	* @Description: 获取物流公司月度考核 
	* @param 
	* @return void
	* @throws 
	*/
	public void getMonth() {
		//　获取月度考核　id
		Integer id = getParaToInt(0, null);
		// id 不为空，即为编辑
		if (id != null){
			// 根据 id 查询月度考核
			Record month = SupplierService.getMonthById(id);
			setAttr("month", month);
			// 附件名
            setAttr("fileName", month.getStr("file"));
        }
		
		// 物流公司名称，id
		String params = " a.state != 0 ";
		List<Record> forwarderList = SupplierService.getQualityByParams(params);
		setAttr("forwarderList", forwarderList);

        // 评分标准
        List<Record> criterionList =  SupplierService.getCriterionList();
        setAttr("criterionList", criterionList);
        String criterionJson = JsonKit.toJson(criterionList);
        setAttr("criterionJson", criterionJson);

        render("month_detail.html");
	}
	
	/** 
	* @Title: saveMonth 
	* @Description: 保存月度考核
	* @param 
	* @return void
	* @throws 
	*/
	public void saveMonth() {
		Map<String,Object> map = new HashMap<>();
		// 月度考核 id
		map.put("id", getParaToInt("id"));
		// 物流公司 supplier_id
		map.put("supplierId", getParaToInt("supplierId"));
		// 年份
		map.put("year", getPara("year"));
		// 月份
		map.put("month", getPara("month"));
		// 月度得分
		map.put("score", getParaToInt("score"));
		// 评定等级
		map.put("level", getPara("level"));
		// 附件
		map.put("file", getPara("file"));
		// 备注
		map.put("remark", getPara("remark"));
		// 当前时间
		map.put("now", new Date());
		
		// 保存结果
		boolean result = SupplierService.saveMonth(map);
		
		renderJson(result);
	}
	
	/** 
	* @Title: deleteMonth 
	* @Description: 删除月度考核
	* @param 
	* @return void
	* @throws 
	*/
	public void deleteMonth() {
		// 月度考核 id
		String idStr = getPara();
		String[] ids = idStr.split(",");
		
		// 删除结果
		boolean result = SupplierService.deleteMonth(ids);
		
		renderJson(result);
	}

	// 上传备注文件，异步上传
	public void uploadMonthFile() {
        UploadFile file = getFile();
        Map<String, Object> responseMsg = SupplierService.saveFile(file);

		renderJson(responseMsg);
	}

	//  删除备注文件
	public void deleteMonthFile() {

		// 文件名
		String fileName = getPara("fileName");
        Map<String, Object> responseMsg = SupplierService.deleteFile(fileName);

		renderJson(responseMsg.get("result"));
	}

	// 下载备注文件
	public void downloadMonthFile() throws IOException {
		// 月度考核 id
		Integer id = getParaToInt();
		// 根据 id 获取物流公司月度考核记录
        Record record = SupplierService.getMonthById(id);
        // 附件
        String file = record.getStr("file");

        // 下载备注文件
        SupplierService.downloadFile(getResponse(), file);

		renderNull();
	}


	/**
	 * @desc:导入月度考核Excel
	 */
	public void getExcel(){		
		boolean flag=Db.tx(new IAtom() {			
			@Override
			public boolean run() throws SQLException {
				// TODO Auto-generated method stub
				try{
					UploadFile up = getFile("file");
					System.out.println(up);System.out.println(up.getFile());
					List<String[]> list = ExcelKit.getExcelData(up.getFile());
					for(String[] strings : list){
						if(strings[0] != null && !"".equals(strings[0])){
							Record record = new Record();
							record.set("year", strings[0]);
							if(strings[1] != null && !"".equals(strings[1])){
								record.set("month", strings[1]);
							}						
							record.set("month_score", (int)Double.parseDouble(strings[3]));
							record.set("supplier_level", strings[4]);
							record.set("supplier_id", strings[2]);
							record.set("remark", strings[5]);
							Db.save("t_supplier_month_assess", record);
						}
					}
					return true;	
				}catch (Exception e){
					e.printStackTrace();
					return false;
				}
			}
		});
		renderJson(flag);
	}

	// 根据物流公司 id、年份获得未审核月份
    public void getMonthListChecked() {
	    //  物流公司 id
        Integer supplierId = getParaToInt("supplierId");
        // 年份
        Integer year = getParaToInt("year");

        // 未审核月份
        List<Record> monthList = SupplierService.getMonthListChecked(supplierId, year);

        renderJson(monthList);
    }


	/*********************物流公司年度考核*************************/

	/** 
	* @Title: year 
	* @Description: 物流公司年度考核列表
	* @param 
	* @return void
	* @throws 
	*/
	public void year() {
		// 物流公司名称
		String forwarder = getPara("forwarder","");
		// 年份
		Integer year = getParaToInt("year",null);
		
		Map<String,Object> params = new HashMap<>();
		params.put("forwarder", forwarder);
		params.put("year", year);
		
		// 年度考核列表
		List<Record> yearList = SupplierService.getYearList(params);
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
			Record record = SupplierService.getYearById(id);
			setAttr("year", record);
		} else {// 新增
			// 物流公司名称，物流公司 id
			String params = " a.state != 0 ";
			List<Record> forwarderList = SupplierService.getQualityByParams(params);
			setAttr("forwarderList", forwarderList);
		}
		
		// 当前年份
		int currentYear = LocalDate.now().getYear();
		setAttr("currentYear", currentYear);

        // 评分标准
        List<Record> criterionList =  SupplierService.getCriterionList();
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
		boolean result = SupplierService.saveYear(record);
		
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
		boolean result = SupplierService.deleteYearByIds(ids);
		
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
		//List<Record> forwarderList = SupplierService.calculateYearAlert();
		List<Record> forwarderList = SupplierService.getYearAlert();
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
		boolean result = SupplierService.calculateYear();
		
		renderJson(result);
	}

}
