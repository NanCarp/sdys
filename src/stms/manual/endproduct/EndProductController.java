package stms.manual.endproduct;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.jfinal.core.Controller;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.IAtom;
import com.jfinal.plugin.activerecord.Record;
import com.jfinal.upload.UploadFile;

import stms.manual.summary.SummaryService;
import stms.utils.ExcelKit;
/**
 * @ClassName: EndProductController
 * @Description: 手册管理_成品表体
 * @author: xuhui
 * @date: 2017年6月27日上午10:00:00
 * @version: 1.0 版本初成
 */
public class EndProductController extends Controller {
	
	/**
	 * @author xuhui
	 * @desc 展示成品表体页面
	 */
	public void index(){
		String manualno = getPara("manualno");
		String recordno = getPara("recordno");
		String productname = getPara("productname");
		List endProductList = EndProductService.getEndProductList(manualno,recordno,productname);
		
		setAttr("endProductList", endProductList);
		setAttr("manualno", manualno);
		setAttr("recordno", recordno);
		setAttr("productname", productname);
		render("end_product.html");
	}
	
	/**
	 * @desc 显示导入界面
	 * @author xuhui
	 */
	public void showimportdesk(){
		render("end_product_import.html");
	}
	
	/**
	 * @author xuhui
	 * @desc 导入Excel
	 */
	public void importExcel(){
		Map<String,Object> map = new HashMap<String,Object>();
		List countWrongList = new ArrayList();
		boolean flag = Db.tx(new IAtom() {	
			@Override
			public boolean run() throws SQLException {
				// TODO Auto-generated method stub
				UploadFile file = getFile("file");
				List<String[]> list=ExcelKit.getExcelData(file.getFile());	
				System.out.println(list);
				//导入excel返回结果，true导入正确，false导入错误
				boolean result = true;
				//该Excel导入列数应为16列，不符合16列即为错误导入excel文件
				if(list.get(0).length!=19){
					getSession().setAttribute("ErrorFile",true);
					result = false;
				}else{
					getSession().setAttribute("ErrorFile",false);
					//检测所有被导入的excel数据是否含有特殊字符
					for(int i=0;i<=list.size()-1;i++){
						String[] strings = list.get(i);	
						for(int k=0;k<=strings.length-1;k++){
							if(match(strings[k])){
								countWrongList.add(i+2+"排"+(k+1)+"列");
								result = false;
							}
						}
						try{
							Record record = new Record();						
							record.set("product_record_num", strings[0]);
							record.set("product_code_and_extra",strings[1] );
							record.set("product_name", strings[2]);
							int kk =3;
							record.set("product_specification", strings[3]);
							record.set("product_unit", strings[4]);
							record.set("product_fixed_unit", strings[5]);	
							if(strings[6] != null && !"".equals(strings[6])){
								record.set("product_report_num",strings[6]);
							}	
							if(strings[7]!=null&&!"".equals(strings[6])){
								record.set("product_report_unit_price", strings[7]);
							}
							if(strings[8]!=null && !"".equals(strings[8])){
								record.set("product_report_total_price", strings[8]);
							}							
							record.set("currency_system", strings[9]);
							record.set("product_pro_marker", strings[10]);
							if(strings[11]!=null&&!"".equals(strings[11])){
								record.set("fix_unit_ratio", strings[11]);
							}
							record.set("product_levy_mode", strings[12]);
							record.set("product_handle_flag",strings[13] );
							record.set("product_report_state", strings[14]);
							record.set("remark", strings[15]);
							record.set("version", strings[16]);
							record.set("manual_no", strings[17]);
							record.set("customs_department", strings[18]);
							Db.save("t_manual_product", record);
						}catch(Exception e){
							//countWrongList记录错误行数，不重复显示错误行数；
							//指定一个判定对象，如果countWrongList已有该行数则返回false，否则返回true；
							countWrongList.add(i+2+"行"+"存在数据异常，请校验");
							result = false;
						}
					}
				}
				return result;
			}
		});		
		map.put("flag", flag);
		getSession().setAttribute("countWrongList", countWrongList);
		renderJson(map);
	}
	
	/**
	 * @desc 将错误行数或者文件错误显示在页面上
	 * @author xuhui
	 */
	public void showErrorExcelMessage(){
	List<Integer> countlist = getSessionAttr("countWrongList");
	boolean ErrorFile = getSessionAttr("ErrorFile");
	setAttr("countlist", countlist);
	setAttr("ErrorFile", ErrorFile);
	render("wrong_message.html");
	}

	/**
	 * @desc:打开成品表体修改以及新增表
	 * @author:xuhui
	 */
	public void getEndProduct(){
		//如果参数getParaToInt(0)不为空即前台传参id不为空，即表示修改成品表体单挑数据
		if(getParaToInt(0)!=null){
			int id = getParaToInt(0);
			Record endpro = EndProductService.getProduct(id);
			setAttr("endpro", endpro);
		} 
		render("end_product_detail.html");
	}
	
	/**
	 * @desc:成品表体保存
	 * @author:xuhui
	 */
	public void saveEndProduct(){
		Map<String,Object> map = new HashMap<String,Object>();		
		map.put("id", getPara("id"));
		map.put("product_record_num",getPara("product_record_num"));
		map.put("product_code_and_extra", getPara("product_code_and_extra"));
		map.put("product_name", getPara("product_name"));
		map.put("product_specification", getPara("product_specification"));
		map.put("product_unit", getPara("product_unit"));
		map.put("product_fixed_unit", getPara("product_fixed_unit"));
		map.put("product_report_num", getPara("product_report_num"));
		map.put("product_report_unit_price", getPara("product_report_unit_price"));
		map.put("product_report_total_price", getPara("product_report_total_price"));
		map.put("currency_system", getPara("currency_system"));
		map.put("product_pro_marker", getPara("product_pro_marker"));
		map.put("fix_unit_ratio", getPara("fix_unit_ratio"));
		map.put("product_levy_mode", getPara("product_levy_mode"));
		map.put("product_handle_flag", getPara("product_handle_flag"));
		map.put("product_report_state", getPara("product_report_state"));
		map.put("remark", getPara("remark"));
		map.put("version", getPara("version"));
		map.put("manual_no", getPara("manual_no"));
		map.put("customs_department", getPara("customs_department"));
		
		// 返回信息
        Map<String, Object> response = new HashMap<>();
        // 重复检测
        String product_record_num = getPara("product_record_num");
        String manualId = getPara("manual_no");
        if (getPara("id") == null && EndProductService.isDuplicate(manualId, product_record_num)) {
            response.put("tips", "数据重复！");
            response.put("isSuccess", false);
            renderJson(response);
            return;
        }
        
        boolean result = EndProductService.saveEndProduct(map);
        response.put("isSuccess", result);
        response.put("tips", result ? "保存成功": "保存失败");
        
		renderJson(response);
	}
	
	/**
	 * @desc:批量删除
	 * @author xuhui
	 */
	public void delete(){
		String ids = getPara(0);
		boolean result = EndProductService.delete(ids);
		renderJson(result);
	}
	
	/**
	 * @desc 验证导入excel输入的正则验证
	 * @author xuhui
	 */
	public boolean match(String str){
		Pattern pattern = Pattern.compile("[`~!@#$%^&*()+=|{}':;',\\[\\]<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]");
		Matcher matcher = pattern.matcher(str);
		return matcher.find(); 
	}
}
