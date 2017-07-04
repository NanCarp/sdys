package stms.manual.singleloss;

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

import stms.manual.endproduct.EndProductService;
import stms.utils.ExcelKit;

/**
 * @ClassName: EndProductController
 * @Description: 手册管理_单损耗表
 * @author: xuhui
 * @date: 2017年6月27日上午10:00:00
 * @version: 1.0 版本初成
 */
public class SingleLossController extends Controller {
	
	/**
	 * @author xuhui
	 * @desc 展示单损耗表数据
	 */
	public void index(){
		String manualno = getPara("manualno");
		String endproname = getPara("endproname");
		List singleLossList = SingleLossService.getSingleLossList(manualno,endproname);
		setAttr("singleLossList", singleLossList);
		setAttr("manualno", manualno);
		setAttr("endproname", endproname);
		render("Single_loss.html");
	}
	
	/**
	 * @desc:导入excel
	 * @author:xuhui
	 */
	/*
	public void importExcel(){
		boolean flag = Db.tx(new IAtom() {		
			@Override
			public boolean run() throws SQLException {
				// TODO Auto-generated method stub
				try{
					UploadFile file = getFile("file");
					List<String[]> list=ExcelKit.getExcelData(file.getFile());
					boolean result = true;
					
					lable:
					for(String[] string:list){
						//验证excel表格是否有特殊字符！
						for(int i=0;i<=14;i++){
							if(match(string[i])){//匹配正则表达式，如果存在特殊字符则返回true；								
								result = false;
								break lable;
							}
						}
						//以excel表格每行第一列做判断是否为空，为空值则表示excel已经结束即循环到达最下面一行
						if(string[0]!=null&&!"".equals(string[0])){
							Record record = new Record();
							record.set("product_no", string[0]);
							record.set("product_name", string[1]);
							record.set("product_specification", string[2]);
							record.set("product_measurement_unit", string[3]);
							if(string[4]!=null&&!"".equals(string[4])){
								record.set("materials_no", string[4]);
							}							
							record.set("materials_name", string[5]);
							record.set("materials_specification", string[6]);
							record.set("materials_measurement_unit", string[7]);
							if(string[8]!=null&&!"".equals(string[8])){
								record.set("unit_loss", string[8]);
							}
							if(string[9]!=null&&"".equals(string[9])){
								record.set("loss_rate", string[9]);
							}
							record.set("loss_handle_flag", string[10]);
							if(string[11]!=null&&"".equals(string[11])){
								record.set("unbond_material_rate", string[11]);
							}
							record.set("version", string[12]);
							record.set("manual_no", string[13]);
							record.set("customs_department", string[14]);
							record.set("remark", string[15]);
							Db.save("t_manual_loss", record);
						}
					}
						return result;
					}catch(Exception e){
						e.printStackTrace();
						return false;	
					}
			}
		});
		renderJson(flag);
	}
	*/
	public void importExcel(){
		Map<String,Object> map = new HashMap<String,Object>();
		List<Integer> countWrongList = new ArrayList<Integer>();
		boolean flag = Db.tx(new IAtom() {	
			@Override
			public boolean run() throws SQLException {
				// TODO Auto-generated method stub
				UploadFile file = getFile("file");
				List<String[]> list=ExcelKit.getExcelData(file.getFile());	
				System.out.println(list);
				//导入excel返回结果，true导入正确，false导入错误
				boolean result = true;
				System.out.println(list.get(0).length);
				if(list.get(0).length!=16){
					getSession().setAttribute("ErrorFile",true);
					result = false;
				}else{
					getSession().setAttribute("ErrorFile",false);
					//检测所有被导入的excel数据是否含有特殊字符
				for(int i=0;i<=list.size()-1;i++){
					String[] string = list.get(i);	
					for(int k=0;k<=string.length-1;k++){
						if(match(string[k])){
							countWrongList.add(i+2);
						}
					}
					try{
						Record record = new Record();
						record.set("product_no", string[0]);
						record.set("product_name", string[1]);
						record.set("product_specification", string[2]);
						record.set("product_measurement_unit", string[3]);
						if(string[4]!=null&&!"".equals(string[4])){
							record.set("materials_no", string[4]);
						}							
						record.set("materials_name", string[5]);
						if(string[4]!=null&&!"".equals(string[4])){
							record.set("materials_no", string[4]);
						}	
						if(string[6]!=null&&!"".equals(string[6])){
							record.set("materials_specification", string[6]);
						}
						record.set("materials_measurement_unit", string[7]);
						if(string[8]!=null&&!"".equals(string[8])){
							record.set("unit_loss", string[8]);
						}
						if(string[9]!=null&&"".equals(string[9])){
							record.set("loss_rate", string[9]);
						}
						record.set("loss_handle_flag", string[10]);
						if(string[11]!=null&&"".equals(string[11])){
							record.set("unbond_material_rate", string[11]);
						}
						record.set("version", string[12]);
						record.set("manual_no", string[13]);
						record.set("customs_department", string[14]);
						record.set("remark", string[15]);
						Db.save("t_manual_loss", record);
					}catch(Exception e){
						//指定一个判定对象，如果countWrongList已有该行数则返回false，否则返回true；
						boolean countflag = true;
						for(Integer column:countWrongList){
							if(column==i){
								countflag = false;
							}
						}
						if(!countflag){
							countWrongList.add(i+2);
						}
						result = false;
					}
				}
				}
				return result;
			}
		});		
		System.out.println(flag);
		map.put("flag", flag);
		getSession().setAttribute("countWrongList", countWrongList);
		renderJson(map);
	}
	
	/**
	 * @desc 显示错误信息
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
	 * @desc 显示导入界面
	 * @author xuhui
	 */
	public void showimportdesk(){
		render("single_loss_import.html");
	}
	
	/**
	 * @desc:新增以及修改单损耗手册
	 * @author xuhui
	 */
	public void getSingleLoss(){
		//如果参数getParaToInt(0)不为空即前台传参入id值，根据id值查询单条数据
		if(getParaToInt(0)!=null){
			int id = getParaToInt(0);
			Record singleloss = SingleLossService.getSingle(id);
			setAttr("singleloss", singleloss);
		} 
		render("single_loss_detail.html");
	}
	
	/**
	 * @desc:删除以及批量删除操作
	 * @author xuhui
	 */
	public void delete(){
		String ids = getPara(0);
		boolean result = SingleLossService.delete(ids);
		renderJson(result);
	}
	
	/**
	 * @author xuhui
	 * @desc 保存以及修改单损耗表数据
	 */
	public void saveSingleLoss(){
		Map<String,Object> map =  new HashMap<String,Object>();
		map.put("id", getPara("id"));
		map.put("product_no", getPara("product_no"));
		map.put("product_name", getPara("product_name"));
		map.put("product_specification", getPara("product_specification"));
		map.put("product_measurement_unit", getPara("product_measurement_unit"));
		map.put("materials_no", getPara("materials_no"));
		map.put("materials_name", getPara("materials_name"));
		map.put("materials_specification", getPara("materials_specification"));
		map.put("materials_measurement_unit", getPara("materials_measurement_unit"));
		map.put("unit_loss", getPara("unit_loss"));
		map.put("loss_rate", getPara("loss_rate"));
		map.put("loss_handle_flag", getPara("loss_handle_flag"));
		map.put("unbond_material_rate", getPara("unbond_material_rate"));
		map.put("version", getPara("version"));
		map.put("manual_no", getPara("manual_no"));
		map.put("customs_department", getPara("customs_department"));
		map.put("remark", getPara("remark"));
		boolean flag = SingleLossService.saveSingleLoss(map);
		renderJson(flag);
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
