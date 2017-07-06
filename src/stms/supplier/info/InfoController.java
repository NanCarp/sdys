package stms.supplier.info;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.sql.SQLException;
import java.util.*;

import com.alibaba.fastjson.JSONObject;
import com.jfinal.core.Controller;
import com.jfinal.json.Json;
import com.jfinal.kit.JsonKit;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.IAtom;
import com.jfinal.plugin.activerecord.Record;


/**
 * @ClassName: InfoController.java
 * @Description: 物流公司信息控制器
 * @author: LiYu
 * @date: 2017年6月26日下午6:08:36
 * @version: 1.0 版本初成
 */
public class InfoController extends Controller {
	/** 
	* @Title: index 
	* @Description: 物流公司信息列表 
	* @param 
	* @return void
	* @throws 
	*/
	public void index() {
		String forwarder = getPara(1);
		try {
			forwarder = URLDecoder.decode(forwarder==null?"":forwarder, "utf-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		// 物流公司信息列表
		List<Record> infoList = InfoService.getInfoList(forwarder);
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
			Record info = InfoService.getInfoById(id);
			setAttr("info", info);
			// 联系人信息
            List<Record> contactsList = InfoService.getContactList(info.getInt("supplier_id"));
            setAttr("contactsList", contactsList);
		} else{
			// 物流公司名称，id
			List<Record> forwarderList = InfoService.getCompanyListQualified();
			setAttr("forwarderList", forwarderList);
		}

		// 等级列表
        List<Record> levelList = InfoService.getLevelList();
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
		Long id = getParaToLong("id");
		// 年份
		String year = getPara("year");
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
		String deposit = getPara("deposit", null);
		// 缴纳时间
		String payDate = getPara("payDate", null);
		// 退保时间
		String withdrawDate = getPara("withdrawDate", null);
//		// 联系人
//		String contact = getPara("contact");
//		// 联系电话
//		String phone = getPara("phone");
//		// 邮箱
//		String email = getPara("email");
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
		record.set("pay_date", payDate);
		record.set("withdraw_date", withdrawDate);
//		record.set("contact", contact);
//		record.set("phone", phone);
//		record.set("email", email);
		record.set("remark", remark);
		record.set("review_time", now);
		// 新增货代信息
		// 操作结果
		boolean result = false;
		if (id == null) {
			// 创建时间
			record.set("create_time", now);
			result = Db.save("t_supplier", record);
			id = record.getLong("id");// 新增记录 id
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
            // result = Db.update("t_supplier", record);
        }

        // 返回消息
        Map<String, Object> message = new HashMap<>();
        message.put("isSuccess", result);
        message.put("supplierId", id);

		renderJson(message);
		
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
		boolean result = InfoService.deleteInfoById(id);
		renderJson(result);
	}

	
	/** 
	* @Title: saveContacts 
	* @Description: 保存联系人
	*/
	public void saveContacts() {
	    // 供应商 id
        Integer supplierId = getParaToInt("supplierId");
        // 联系人列表
        String contactsList = getPara("contactsList");
        List<JSONObject> list = new ArrayList<>();
        list = JSONObject.parseObject(contactsList, list.getClass());
        System.out.println(list);

        // 删除原联系人
		List<Record> originalContacts = Db.find("SELECT * FROM t_supplier_contacts WHERE supplier_id = ?", supplierId);
		if (originalContacts.size() > 0) {
			for (Record r : originalContacts) {
				Db.delete("t_supplier_contacts", r);
			}
		}
		// 保存联系人
        for (JSONObject contacts : list) {
            String name = contacts.getString("name");
            String phone = contacts.getString("phone");
            String email = contacts.getString("email");
            System.out.println("name: "+name+" phone: "+phone+" email: "+ email);
            Record record = new Record();
            record.set("supplier_id", supplierId);
            record.set("name", name);
            record.set("phone", phone);
            record.set("email", email);
            Db.save("t_supplier_contacts", record);
        }

        // 返回消息
        Map<String, Object> message = new HashMap<>();
        message.put("isSuccess", true);

        renderJson(message);
    }
}
