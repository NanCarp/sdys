package stms.controller.manage;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.jfinal.aop.Before;
import com.jfinal.core.Controller;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;

import stms.interceptor.ManageInterceptor;
import stms.service.manage.SupplierService;

/**
 * @ClassName: SupplierController
 * @Description: 供应商管理
 * @author: LiYu
 * @date: 2017年5月12日下午3:40:50
 * @version: 1.0 版本初成
 */
@Before(ManageInterceptor.class)
public class SupplierController extends Controller{
	/*********************供应商资质管理*************************/
	/** 
	* @Title: quality 
	* @Description: 供应商资质列表
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
	* @Description: 获取供应商资质
	* @param 
	* @return void
	* @throws 
	*/
	public void getQuality() {
		//　获取供应商资质　id
		Integer id = getParaToInt(0, null);
		// id 不为空，即为编辑
		if (id != null){
			Record quality = SupplierService.getQualityById(id);
			setAttr("quality", quality);
		}
		
		render("quality_detail.html");
	}

	/** 
	* @Title: saveQuality 
	* @Description: 保存供应商资质
	* @param 
	* @return void
	* @throws 
	*/
	public void saveQuality() {
		// 资质 id
		Integer id = getParaToInt("id", null);
		// 货代名称
		String forwarder = getPara("forwarder").trim();
		// 货代简称
		String abbreviation = getPara("abbreviation").trim();
		// 状态：1：合格，2：备选，0：不合格
		Integer state = getParaToInt("state");
		// 文件
		String file = getPara("file");
		// 备注
		String remark = getPara("remark");
		
		Record record = new Record();
		record.set("supplier_name", forwarder);
		record.set("short_name", abbreviation);
		record.set("state", state);
		record.set("review_file", file);
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
			// 供应商 id
			Integer supplierId = (int) new Date().getTime();
			record.set("supplier_id", supplierId);
			result = Db.save("t_supplier_qualification", record);
		}
		
		renderJson(result);
	}
	
	/** 
	* @Title: deleteQuality 
	* @Description: 删除供应商资质 
	* @param 
	* @return void
	* @throws 
	*/
	public void deleteQuality() {
		Integer id = getParaToInt();
		boolean result = SupplierService.deleteQuality(id);
		renderJson(result);
	}
	
	/*********************供应商信息管理*************************/
	/** 
	* @Title: info 
	* @Description: 供应商信息列表 
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
		
		// 供应商信息列表
		List<Record> infoList = SupplierService.getInfoList(forwarder);
		setAttr("infoList", infoList);
		setAttr("forwarder", forwarder);
		render("info.html");
	}
	
	/** 
	* @Title: getInfo 
	* @Description: 获取供应商信息 
	* @param 
	* @return void
	* @throws 
	*/
	public void getInfo() {
		//　获取供应商信息　id
		Integer id = getParaToInt(0, null);
		// id 不为空，即为编辑
		if (id != null){
			//　获取供应商信息
			Record info = SupplierService.getInfoById(id);
			setAttr("info", info);
			String params = " supplier_id=" + info.getInt("supplier_id");
			List<Record> forwarderList = SupplierService.getQualityByParams(params);
			setAttr("forwarder", forwarderList.get(0));
		} else{
			// 供应商名称，id
			String params = " state != 0 AND supplier_id NOT IN (SELECT supplier_id FROM t_supplier) ";
			List<Record> forwarderList = SupplierService.getQualityByParams(params);
			setAttr("forwarderList", forwarderList);
		}
		
		render("info_detail.html");
	}

	/**
	 * @Title: saveInfo
	 * @Description: 保存供应商信息
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
		Integer level = getParaToInt("level");
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
			result = Db.update("t_supplier", record);
		}
		
		renderJson(result);
		
	}
	
	/** 
	* @Title: deleteInfo 
	* @Description: 删除供应商信息
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
	/*********************供应商考核标准*************************/
	/** 
	* @Title: level 
	* @Description: 供应商考核标准
	* @param 
	* @return void
	* @throws 
	*/
	public void level() {
		render("level.html");
	}
	
	/** 
	* @Title: getLevel 
	* @Description: 获取供应商等级 
	* @param 
	* @return void
	* @throws 
	*/
	public void getLevel() {
		render("level_detail.html");
	}
	
	/*********************供应商月度考核*************************/
	/** 
	* @Title: month 
	* @Description: 供应商月度考核列表 
	* @param 
	* @return void
	* @throws 
	*/
	public void month() {
		render("month.html");
	}
	
	/** 
	* @Title: getMonth 
	* @Description: 获取供应商月度考核 
	* @param 
	* @return void
	* @throws 
	*/
	public void getMonth() {
		render("month_detail.html");
	}
	
	/*********************供应商年度考核*************************/
	/** 
	* @Title: year 
	* @Description: 供应商年度考核列表
	* @param 
	* @return void
	* @throws 
	*/
	public void year() {
		render("year.html");
	}
	
	/** 
	* @Title: getYear 
	* @Description: 获取供应商年度考核
	* @param 
	* @return void
	* @throws 
	*/
	public void getYear() {
		render("year_detail.html");
	}
	
	/** 
	* @Title: yearDetail 
	* @Description: 供应商年度考核详细信息 
	* @param 
	* @return void
	* @throws 
	*/
	public void yearDetail() {
		render("year_all.html");
	}
}
