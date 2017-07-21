package stms.purchase.importfreight;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.jfinal.aop.Before;
import com.jfinal.core.Controller;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.IAtom;
import com.jfinal.plugin.activerecord.Record;
import com.jfinal.upload.UploadFile;

import stms.interceptor.ManageInterceptor;
import stms.model.ImportFreight;
import stms.purchase.importedmaterials.ImportedmaterialsService;
import stms.utils.ExcelKit;
/**
 * @desc 进口运费
 * @date 2017年7月18日
 * @author xuhui
 */
@Before(ManageInterceptor.class)
public class ImportFreightControllr extends Controller {
	
	/**
	 * @desc 展示进口运费界面
	 */
	public void index(){
		render("importfreight.html");
	}
	
	/**
	 * @desc 展示进口运费数据
	 */
	public void getJson(){
		Record admin = getSessionAttr("admin");
		Integer company_id = admin.getInt("company_id");
		System.out.println(company_id);
		
		String import_inner_num = getPara("import_inner_num");
    	String delivery_num = getPara("delivery_num");
    	String import_invoice_num = getPara("import_invoice_num");
    	String logistics = getPara("logistics");

    	Integer	pageindex = 0;
    	Integer pagelimit = getParaToInt("limit")==null? 12 :getParaToInt("limit");
    	Integer offset = getParaToInt("offset")==null?0:getParaToInt("offset");
    	if(offset!=0){
    		pageindex = offset/pagelimit;
    	}
    	pageindex += 1;
    	Map<String, Object> map = new HashMap<String,Object>();
    	List<Record> dictionaryList = ImportFreightService.getImportFreight(pageindex, pagelimit,import_inner_num,delivery_num,import_invoice_num,logistics,company_id).getList();
    	map.put("rows", dictionaryList);
    	map.put("total",ImportFreightService.getImportFreight(pageindex, pagelimit,import_inner_num,delivery_num,import_invoice_num,logistics,company_id).getTotalRow());
    	System.out.println(dictionaryList);
    	renderJson(map);
	}
	
	/**
	 * @author xuhui
	 * @desc 删除操作
	 */
	public void delete(){
		String ids = getPara(0);
   		boolean result = ImportFreightService.delete(ids);
   		System.out.println(result);
   		renderJson(result);
	}
	
	/**
	 * @author xuhui
	 * @desc 打开新增页
	 */
	public void addImportFreight(){
		Integer id = getParaToInt(0);
		if(id!=null){
			Record importFreight = ImportFreightService.edit(id);		
			setAttr("im", importFreight);
		}
		Record admin = getSessionAttr("admin");
		System.out.println(admin);
		Integer logistics_id = admin.getInt("company_id");
		if(logistics_id!=1){//logistics_id为尚德公司
			String company = ImportFreightService.getWuliuCompany(logistics_id).getStr("company_name");	
			setAttr("logistics_name", company);
			setAttr("host", false);
		}else{
			List<Record> list = ImportFreightService.getWuliuCompany();
			setAttr("WuLiuCompanies", list);
			setAttr("host", true);
		}
		render("importfreight_detail.html");
	}
	
	/**
	 * @author xuhui
	 * @desc 新增以及修改数据
	 */
	public void saveOrUpdate(){	
		ImportFreight record = getModel(ImportFreight.class,"");
		Record admin = getSessionAttr("admin");
		Integer logistics_id  = admin.getInt("company_id");
		if(logistics_id!=1){//logistics_id=1为尚德公司，不进行保存；
			record.setLogisticsId(logistics_id);
		}	
		boolean result = false;
		result = ImportFreightService.saveOrUpdate(record);
		renderJson(result);
	}
	
	/**
	 * @author xuhui
	 * @desc 展示导入界面
	 */
	public void showimportdesk(){
		render("importfreight_import.html");
	}
	
	/**
	 * @author xuhui
	 * @desc 导入Excel
	 */
	public void importExcel(){
		Map<String,Object> map = new HashMap<String,Object>();
		List countWrongList = new ArrayList();
		Record admin = getSessionAttr("admin");
		Integer company_id = admin.getInt("company_id");
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
				if(list.get(0).length!=30){
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
							record.set("import_invoice_num", strings[0]);//进口发票号
							record.set("import_inner_num",strings[1] );//内部编号
							record.set("import_name", strings[2]);//商品名称
							record.set("trade_terms", strings[3]);//贸易条款
							record.set("delivery_num", strings[4]);//提运单号
							if(strings[5] !=null && !"".equals(strings[5])){
								record.set("import_num",strings[5]);//件数
							}	
							if(strings[6] != null && !"".equals(strings[6])){
								record.set("gross_weight",strings[6]);//毛重
							}	
							if(strings[7]!=null&&!"".equals(strings[6])){
								record.set("customs_fees", strings[7]);//报关费
							}
							if(strings[8]!=null && !"".equals(strings[8])){
								record.set("CIQ_Brokerage_Fee", strings[8]);//报检费
							}
							if(strings[9]!=null&&!"".equals(strings[9])){
								record.set("customs_agent_fee", strings[9]);//报关代理费
							}
							if(strings[10]!=null&&!"".equals(strings[10])){
								record.set("replacement_fee", strings[10]);//换单费
							}
							if(strings[11]!=null&&!"".equals(strings[11])){
								record.set("inspection_fee", strings[11]);//待检费
							}
							if(strings[12]!=null&&!"".equals(strings[12])){
								record.set("custom_inspect_fee", strings[12]);//海关检查费
							}		
							if(strings[13]!=null&&!"".equals(strings[13])){
								record.set("domestic_land_freight", strings[13]);//国内陆运费
							}
							if(strings[14]!=null&&!"".equals(strings[14])){
								record.set("incidental_fee", strings[14]);//检疫费/港砸/港建/坏污箱
							}
							if(strings[15]!=null&&!"".equals(strings[15])){
								record.set("packing_inspection_fee", strings[15]);//包装查验费
							}
							if(strings[16]!=null&&!"".equals(strings[16])){
								record.set("inspection_service_fee", strings[16]);//商检服务费
							}
							if(strings[17]!=null&&!"".equals(strings[17])){
								record.set("customs_service_charge", strings[17]);//海关查验服务费
							}
							if(strings[18]!=null&&!"".equals(strings[18])){
								record.set("pick_up_charge", strings[18]);//提货费
							}
							if(strings[19]!=null&&!"".equals(strings[19])){
								record.set("accreditation_fee", strings[19]);//办证费
							}
							if(strings[20]!=null&&!"".equals(strings[20])){
								record.set("international_shipping", strings[20]);//国际运费
							}
							if(strings[21]!=null&&!"".equals(strings[21])){
								record.set("international_fees", strings[21]);//国际杂费
							}
							if(strings[22]!=null&&!"".equals(strings[22])){
								record.set("international_agency_fee", strings[22]);//国际代理费
							}
							if(strings[23]!=null&&!"".equals(strings[23])){
								record.set("total_clearcustoms_rmb", strings[23]);//进口清关费
							}
							if(strings[24]!=null&&!"".equals(strings[24])){
								record.set("total_clearcustoms_usd", strings[24]);//进口清关合计
							}
							record.set("statement_date", strings[25]);//账单日期
							record.set("cost_type", strings[26]);//费用类型
							record.set("freight_pay_terms", strings[27]);//运费支付方式							
							record.set("IBMS_num", strings[28]);//提货费
							record.set("remark", strings[29]);//提货费
							if(company_id!=1){
								record.set("logistics_id", company_id);//货代
							}
							Db.save("t_import_freight", record);
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
	 * @desc 验证导入excel输入的正则验证
	 * @author xuhui
	 */
	public boolean match(String str){
		Pattern pattern = Pattern.compile("[`~!@#$%^&*+=|{}':;',\\[\\]<>/?~！@#￥%……&*——+|{}【】‘；：”“’。，、？]");
		Matcher matcher = pattern.matcher(str);
		return matcher.find(); 
	}
	
	/**
	 * @desc 根据内编号查询信息
	 * @author xuhui
	 */
	public void getMessageFrominnerNum(){
		String innerNum = getPara("innerNum");
		List<Record> list = ImportFreightService.getMessageFrominnerNum(innerNum);
		Map<String, Object> map =new HashMap<String, Object>();
		boolean flag = false;
		if(list.size()!=0){
			Record record = list.get(0);
			flag = true;
			map.put("flag", flag);
			map.put("import_name", record.get("import_name"));
			map.put("trade_terms", record.get("trade_terms"));
			map.put("main_note_num", record.get("main_note_num"));
			map.put("import_num", record.get("import_num"));
			map.put("gross_weight", record.get("gross_weight"));
		}else{
			map.put("flag", flag);
		}
		renderJson(map);
	}
}
