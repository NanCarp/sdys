package stms.purchase.importtaxbreak;

import java.math.BigDecimal;
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

import stms.model.ImportTaxBreak;
import stms.utils.ExcelKit;
/**
 * @desc 转税折料表
 * @ClassName ImporttaxbreakController
 * @author xuhui
 * @date 2017年7月21日上午09:55:20
 * @version: 1.0 版本初成
 */
public class ImporttaxbreakController extends Controller{

	public void index(){
		render("importtaxbreak.html");
	}
	
	/**
	 * @author xuhui
	 * @desc 加载清单页面数据
	 * @date 2017年7月14日上午9:44:05
	 */
	public void getJson(){
		String flow_number = getPara("flow_number");
		String custom_batch_num = getPara("custom_batch_num");
		String manual_id = getPara("manual_id");
			
    	Integer	pageindex = 0;
    	Integer pagelimit = getParaToInt("limit")==null? 12 :getParaToInt("limit");
    	Integer offset = getParaToInt("offset")==null?0:getParaToInt("offset");
    	if(offset!=0){
    		pageindex = offset/pagelimit;
    	}
    	pageindex += 1;
    	Map<String, Object> map = new HashMap<String,Object>();
    	List<Record> dictionaryList = ImporttaxbreakService.getImportedmaterials(pageindex, pagelimit,flow_number,custom_batch_num,manual_id).getList();
    	map.put("rows", dictionaryList);
    	map.put("total",ImporttaxbreakService.getImportedmaterials(pageindex, pagelimit,flow_number,custom_batch_num,manual_id).getTotalRow());
    	System.out.println(dictionaryList);
    	renderJson(map);
    }
	
	/**
	 * @author xuhui
	 * @desc 删除操作
	 */
	public void delete(){
		String ids = getPara(0);
   		boolean result = ImporttaxbreakService.delete(ids);
   		System.out.println(result);
   		renderJson(result);
	}
	
	/**
	 * @author xuhui
	 * @desc 打开新增页面
	 */
	public void getimporttaxbreak(){
		Integer id = getParaToInt(0);
		List<Record> normanlManuals = ImporttaxbreakService.getNormalManual();
		setAttr("normanlManuals", normanlManuals);
		if(id!=null){
			Record record = ImporttaxbreakService.getSingleImportedmaterials(id);
			setAttr("im", record);
		}	
		render("importtaxbreak_detail.html");
	}
	
	/**
	 * @author xuhui
	 * @desc 
	 */
	public void saveOrUpdate(){
		ImportTaxBreak record = getModel(ImportTaxBreak.class,"");
		//抓取手册号、材料性质、规格、进口料件名称、数量、内部流转单号、海关批次号到内销补税明细总表
		//抓取条件：保税耗用>0
		String manual_id = record.getManualId();
		String material_properties = record.getMaterialProperties();
		String import_specification = record.getImportSpecification();
		String import_name = record.getImportName();
		Integer domes_sale_num = record.getDomesSaleNum();
		String custom_batch_num = record.getCustomBatchNum();
		String flow_number = record.getFlowNumber();
		BigDecimal bond_consume = record.getBondConsume();
		
		//保存转税折料表信息
		boolean result = ImporttaxbreakService.saveOrUpdate(record,manual_id,material_properties
				,import_specification,import_name,domes_sale_num,custom_batch_num,flow_number,bond_consume);
		renderJson(result);
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
				//该Excel导入列数应为31列，不符合31列即为错误导入excel文件
				System.out.println(list.get(0).length);
				if(list.get(0).length!=11){
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
							record.set("flow_number", strings[0]);//流转单号
							record.set("import_name", strings[1]);
							record.set("material_properties",strings[2]);//材料性质
							record.set("import_specification", strings[3]);//规格
							if(strings[4]!=null&&strings[4]!=""){
								record.set("domes_sale_num", strings[4]);//内销数量
							}							
							record.set("fold_pieces", strings[5]);//折成料件品名和单位
							if(strings[6] !=null && !"".equals(strings[6])){
								record.set("unit_consume",strings[6]);//单耗
							}	
							if(strings[7] != null && !"".equals(strings[7])){
								record.set("bond_consume",strings[7]);//保税耗用
							}	
							if(strings[8]!=null&&!"".equals(strings[8])){
								record.set("duty_paid_consume", strings[8]);//完税耗用
							}
							if(strings[9]!=null && !"".equals(strings[9])){
								record.set("custom_batch_num", strings[9]);//海关批次号
							}
							if(strings[10]!=null&&!"".equals(strings[10])){
								record.set("manual_id", strings[10]);//手册号
							}
							Db.save("t_import_tax_break", record);
						}catch(Exception e){
							//countWrongList记录错误行数，不重复显示错误行数；
							//指定一个判定对象，如果countWrongList已有该行数则返回false，否则返回true；
							e.printStackTrace();
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
	 * @desc 验证导入excel输入的正则验证
	 * @author xuhui
	 */
	public boolean match(String str){
		Pattern pattern = Pattern.compile("[`~!@#$%^&*+=|{}':;',\\[\\]<>?~！@#￥%……&*——+|{}【】‘；：”“’。，、？]");
		Matcher matcher = pattern.matcher(str);
		return matcher.find(); 
	}
	
	/**
	 * @author xuhui
	 * @desc 展示导入界面
	 */
	public void showimportdesk(){
		render("importtaxbreak_import.html");
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
}
