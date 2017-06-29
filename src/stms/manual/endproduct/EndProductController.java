package stms.manual.endproduct;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.jfinal.core.Controller;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.IAtom;
import com.jfinal.plugin.activerecord.Record;
import com.jfinal.upload.UploadFile;

import stms.utils.ExcelKit;
/**
 * @ClassName: EndProductController
 * @Description: 手册管理_成品表体
 * @author: xuhui
 * @date: 2017年6月27日上午10:00:00
 * @version: 1.0 版本初成
 */
public class EndProductController extends Controller {

	public void index(){
		String manualno = getPara("manualno");
		String recordno = getPara("recordno");
		String productname = getPara("productname");
		
		System.out.println(manualno+recordno+productname);

		List endProductList = EndProductService.getEndProductList(manualno,recordno,productname);
		setAttr("endProductList", endProductList);
		setAttr("manualno", manualno);
		setAttr("recordno", recordno);
		setAttr("productname", productname);
		render("end_product.html");
	}
	
	/**
	 * @desc:导入excel
	 * @author:xuhui
	 */
	public void importExcel(){
		boolean flag = Db.tx(new IAtom() {		
			@Override
			public boolean run() throws SQLException {
				// TODO Auto-generated method stub
				try{
					UploadFile up = getFile("file");
					List<String[]> list = ExcelKit.getExcelData(up.getFile());
					System.out.println(list);
					for(String[] strings :list){
						if(strings[0]!=null&&!"".equals(strings[0])){
							Record record = new Record();
							record.set("product_record_num", strings[0]);
							record.set("product_code_and_extra",strings[1] );
							record.set("product_name", strings[2]);
							record.set("product_specification", strings[3]);
							record.set("product_unit", strings[4]);
							record.set("product_fixed_unit", strings[5]);
							record.set("product_report_num",strings[6] );
							record.set("product_report_unit_price", strings[7]);
							record.set("product_report_total_price", strings[8]);
							record.set("currency_system", strings[9]);
							record.set("product_pro_marker", strings[10]);
							record.set("fix_unit_ratio", strings[11]);
							record.set("product_levy_mode", strings[12]);
							record.set("product_handle_flag",strings[13] );
							record.set("product_report_state", strings[14]);
							record.set("remark", strings[15]);
							record.set("version", strings[16]);
							record.set("manual_no", strings[17]);
							record.set("customs_department", strings[18]);
							Db.save("t_manual_product", record);
						}
					}
					
				}catch(Exception e){
					e.printStackTrace();
					return false;	
				}
				return false;
			}
		});
		renderJson(flag);
	}
	
	/**
	 * @desc:打开成品表体修改以及新增表
	 * @author:xuhui
	 */
	public void getEndProduct(){
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
		boolean result = EndProductService.saveEndProduct(map);
		renderJson(result);
	}
	
	
	/**
	 * @desc:批量删除
	 * @author xuhui
	 */
	
	public void delete(){
		String ids = getPara(0);
		boolean result = EndProductService.delete(ids);
		System.out.println(ids);
		renderJson(result);
	}
}
