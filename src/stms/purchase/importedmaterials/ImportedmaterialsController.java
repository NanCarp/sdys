package stms.purchase.importedmaterials;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;

import com.jfinal.core.Controller;
import com.jfinal.core.JFinal;
import com.jfinal.plugin.activerecord.Record;
import com.sun.glass.ui.Application;
import com.sun.org.apache.regexp.internal.recompile;

import stms.model.ImportMaterials;
import stms.system.dictionary.DictionService;

/**
 * @desc 进口料件清单
 * @ClassName ImportedmaterialsController
 * @author xuhui
 * @date 2017年7月13日下午13:43:20
 * @version: 1.0 版本初成
 */
public class ImportedmaterialsController extends Controller{

	/**
	 * @desc 展示清单页面
	 */
	public void index(){
		render("importedmaterials.html");
	}
	
	/**
	 * @author xuhui
	 * @desc 加载清单页面数据
	 * @date 2017年7月14日上午9:44:05
	 */
	public void getJson(){
    	String import_inner_num = getPara("import_inner_num");
    	String purchasing_agent = getPara("purchasing_agent");
    	String order_num = getPara("order_num");
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
    	List<Record> dictionaryList = ImportedmaterialsService.getImportedmaterials(pageindex, pagelimit,import_inner_num,purchasing_agent,order_num,import_invoice_num,logistics).getList();
    	map.put("rows", dictionaryList);
    	map.put("total",ImportedmaterialsService.getImportedmaterials(pageindex, pagelimit,import_inner_num,purchasing_agent,order_num,import_invoice_num,logistics).getTotalRow());
    	System.out.println(dictionaryList);
    	renderJson(map);
    }
	
	/**
	 * @author xuhui
	 * @desc 删除操作
	 */
	public void delete(){
		String ids = getPara(0);
   		boolean result = ImportedmaterialsService.delete(ids);
   		System.out.println(result);
   		renderJson(result);
	}
	
	/**
	 * @desc 添加进口料件清单
	 */
	public void getImportedmaterials(){
		Integer id = getParaToInt(0);
		System.out.println(id);
		if(id!=null){
			Record record = ImportedmaterialsService.getImportedmaterialsById(id);
			setAttr("im", record);
		}
		List<Record> WuLiuCompanies = ImportedmaterialsService.getWuliuCompany();
		List<Record> importmanuals = ImportedmaterialsService.getImportmanual();
		setAttr("importmanuals", importmanuals);
		setAttr("WuLiuCompanies", WuLiuCompanies);
		render("importedmaterials_detail.html");
	}
	
	/**
	 * @desc 保存添加和修改
	 * @author xuhui
	 */
	public void saveimportedmaterials(){		
		ImportMaterials record = getModel(ImportMaterials.class,"");
		boolean result = false;
		Map<String,Object> map = new HashMap<String,Object>();
		//生成内部编号
		if(record.get("id")==null){
			List<Record> list = ImportedmaterialsService.getMaxImportInnerNum();
			if(list.size()!=0){
				String importInner = list.get(0).getStr("import_inner_num");
				int importInnerCount =  Integer.parseInt(importInner.substring(5, 9));
				importInnerCount += 10001;
				String suf = String.valueOf(importInnerCount);
				String suffix = suf.substring(1, 5);
				String prefix = "J2017";
				String import_inner_num = prefix+suffix;
				record.setImportInnerNum(import_inner_num);
				result = ImportedmaterialsService.saveOrUpdate(record);
				map.put("result", result);
				map.put("import_inner_num",import_inner_num);
			}
		}else{
			result = ImportedmaterialsService.saveOrUpdate(record);
			map.put("result", result);
		}		
		renderJson(map);
	}
	
	/**
	 * @desc 根据商品名称获取商品编码
	 * @author xuhui
	 */
	public void getHScode(){
		String import_name = getPara("import_name");
		List<Record> HsCodes = ImportedmaterialsService.getHsCode(import_name);
		Map<String, Object> map = new HashMap<String,Object>();
		boolean flag = true;
		if(HsCodes.size()!=0){
			String import_code = HsCodes.get(0).get("import_code");			
			map.put("import_code", import_code);
			map.put("flag", flag);
		}else{
			flag = false;
			map.put("flag", flag);
		}
		renderJson(map);
	}
	
	/**
	 * @desc 根据商品名称以及手册号获取项号
	 * @author xuhui
	 */
	public void getItemNumber(){
		 String import_name = getPara("import_name");
		 String manual_id = getPara("manual_id");
		 Map<String, Object> map = new HashMap<String,Object>();
		 List<Record> Items = ImportedmaterialsService.getItemNumber(import_name, manual_id);
		 boolean flag = true;
		 if(Items.size()!=0){
			 Integer itemnumber = Items.get(0).getInt("id");
			 map.put("flag", flag);
			 map.put("itemnumber", itemnumber);
		 }else{
			 flag = false;
			 map.put("flag", flag);
		 }
		 renderJson(map);
	}
}
