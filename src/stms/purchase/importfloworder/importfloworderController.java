package stms.purchase.importfloworder;

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

import stms.model.ImportFloworder;
import stms.model.ImportFreight;
import stms.purchase.importedmaterials.ImportedmaterialsService;
import stms.purchase.importfreight.ImportFreightService;
import stms.utils.ExcelKit;

/**
 * @desc 流转单汇总
 * @ClassName importfloworderController
 * @author xuhui
 * @date 2017年7月21日下午17:13:20
 * @version: 1.0 版本初成
 */
public class importfloworderController extends Controller {

	/**
	 * @desc 展示流转单页
	 * @author xuhui
	 */
	public void index(){
		render("importfloworder.html");
	}
	
	/**
	 * @desc 展示流转单清单页数据
	 * @author xuhui
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
    	
    	List<Record> dictionaryList = importfloworderService.getimportfloworder(pageindex,pagelimit
    			,flow_number,custom_batch_num,manual_id).getList();
    	map.put("rows", dictionaryList);
    	map.put("total",importfloworderService.getimportfloworder(pageindex, pagelimit
    			,flow_number,custom_batch_num,manual_id).getTotalRow());
    	renderJson(map);	
	}
	
	/**
	 * @desc 删除操作
	 * @author xuhui
	 */
	public void delete(){
		String ids = getPara(0);
   		boolean result = importfloworderService.delete(ids);
   		System.out.println(result);
   		renderJson(result);
	}
	
	/**
	 * @desc 打开新增以及修改数据页面
	 * @author xuhui
	 */
	public void getImportfloworder(){
		Integer id = getParaToInt(0);
		List<Record> normalMasnual = importfloworderService.getNormalManual();
		setAttr("normalMasnual", normalMasnual);
		if(id!=null){
			Record record = importfloworderService.getSinleImportfloworder(id);
			setAttr("im", record);
		}
		render("importfloworder_detail.html");
	}
	
	/**
	 * @desc 新增以及修改数据
	 * @author xuhui
	 */
	public void saveOrUpdate(){	
		ImportFloworder record = getModel(ImportFloworder.class,"");
		boolean result = false;
		result = importfloworderService.saveOrUpdate(record);
		renderJson(result);
	}
	
	/**
	 * @desc 导入Excel
	 * @author xuhui
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
				if(list.get(0).length!=14){
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
							System.out.println(strings[1]);
							if(strings[1]!=null&&!"".equals(strings[1])){
								record.set("issue_date",strings[1]);//发出日期
							}							
							record.set("manual_id", strings[2]);//手册号
							record.set("material_properties", strings[3]);//材料性质（补税类型）
							record.set("import_specification", strings[4]);//规格
							record.set("import_code",strings[5]);//物料编号
							record.set("batch_num",strings[6]);//产品批次号
							if(strings[7]!=null&&!"".equals(strings[7])){
								record.set("domes_sale_num", strings[7]);//内销数量
							}						
							record.set("unit", strings[8]);//单位
							record.set("overdue_tax_progress", strings[9]);//补税项目
							record.set("applicant", strings[10]);//申请人
							record.set("storage_location", strings[11]);//库位
							record.set("costum_batch_num", strings[12]);//海关批次号
							record.set("remark", strings[13]);//备注
							Db.save("t_import_floworder", record);
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
		render("importfloworder_import.html");
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
