package stms.supplier.month;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.jfinal.core.Controller;
import com.jfinal.kit.JsonKit;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.IAtom;
import com.jfinal.plugin.activerecord.Record;
import com.jfinal.upload.UploadFile;

import stms.utils.ExcelKit;

/**
 * @ClassName: MonthController.java
 * @Description: 物流公司月度考核控制器
 * @author: LiYu
 * @date: 2017年6月26日下午6:10:48
 * @version: 1.0 版本初成
 */
public class MonthController extends Controller {
	
	/** 
	* @Title: index 
	* @Description: 物流公司月度考核列表 
	* @param 
	* @return void
	* @throws 
	*/
	public void index() {
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
		List<Record> monthList = MonthService.getMonthList(params);
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
			Record month = MonthService.getMonthById(id);
			setAttr("month", month);
			// 附件名
            setAttr("fileName", month.getStr("file"));
        }
		
		// 物流公司名称，id
		String params = " a.state != 0 ";
		List<Record> forwarderList = MonthService.getQualityByParams(params);
		setAttr("forwarderList", forwarderList);

        // 评分标准
        List<Record> criterionList =  MonthService.getCriterionList();
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
		boolean result = MonthService.saveMonth(map);
		
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
		boolean result = MonthService.deleteMonth(ids);
		
		renderJson(result);
	}

	// 上传备注文件，异步上传
	public void uploadMonthFile() {
        UploadFile file = getFile();
        Map<String, Object> responseMsg = MonthService.saveFile(file);

		renderJson(responseMsg);
	}

	//  删除备注文件
	public void deleteMonthFile() {

		// 文件名
		String fileName = getPara("fileName");
        Map<String, Object> responseMsg = MonthService.deleteFile(fileName);

		renderJson(responseMsg.get("result"));
	}

	// 下载备注文件
	public void downloadMonthFile() throws IOException {
		// 月度考核 id
		Integer id = getParaToInt();
		// 根据 id 获取物流公司月度考核记录
        Record record = MonthService.getMonthById(id);
        // 附件
        String file = record.getStr("file");

        // 下载备注文件
        MonthService.downloadFile(getResponse(), file);

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
        List<Record> monthList = MonthService.getMonthListChecked(supplierId, year);

        renderJson(monthList);
    }

}
