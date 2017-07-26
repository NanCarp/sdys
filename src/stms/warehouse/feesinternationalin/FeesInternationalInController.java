package stms.warehouse.feesinternationalin;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.jfinal.core.Controller;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;
import com.jfinal.upload.UploadFile;

import stms.model.InterWarehouseFeesIn;



/**
 * @ClassName: FeesInternationalInController.java
 * @Description: 费用明细结算表（国际）
 * @author: LiYu
 * @date: 2017年7月19日下午1:16:48
 * @version: 1.0 版本初成
 */
public class FeesInternationalInController extends Controller {
    /** 
    * @Title: index 
    * @Description: 费用明细结算表（国际）页面
    * @author liyu
    */
    public void index() {
        render("fees_international_in.html");
    }
    
    /** 
    * @Title: getJson 
    * @Description: 费用明细结算表（国际）数据
    * @author liyu
    */
    public void getJson() {
        // 物流公司名称
        String company_name = getPara("company_name");
        setAttr("company_name", company_name);
        
        Integer pageindex = 0; // 页码
        Integer pagelimit = getParaToInt("limit")==null? 12 :getParaToInt("limit"); // 每页数据条数
        Integer offset = getParaToInt("offset")==null?0:getParaToInt("offset");
        if(offset!=0){
            pageindex = offset/pagelimit;
        }
        pageindex += 1;
        
        //Page<Record> page = FeesInternationalInService.getDataPages(pageindex, pagelimit);
        Page<Record> page = FeesInternationalInService.getDataPages(pageindex, pagelimit, company_name);
        
        Map<String, Object> map = new HashMap<String,Object>();
        map.put("rows", page.getList());
        map.put("total",page.getTotalRow());
        //System.out.println(page.getList());
        
        renderJson(map);
    }
    
    public void getRecord() {
        // id
        Integer id = getParaToInt();

        if (id != null) {//编辑
            Record record = Db.findById("t_inter_warehouse_fees_in", id);
            setAttr("record", record);
        }

        // 物流公司列表
        List<Record> companyList = FeesInternationalInService.getCompanyList();
        setAttr("companyList", companyList);
        // 币制列表
        List<Record> currencyList = FeesInternationalInService.getCurrencyList();
        setAttr("currencyList", currencyList);
        
        render("fees_international_in_detail.html");
    }
    
    // 保存
    public void save() {
        InterWarehouseFeesIn record = getModel(InterWarehouseFeesIn.class, "");
        
        // 保存结果
        boolean result = false;
        // 返回信息
        Map<String, Object> response = new HashMap<>();
        /*// 重复检测
        Integer id = record.getId();
        String batchNo = record.getBatchNo();
        String trayNo = record.getTrayNo();
        if (id == null && StockInDomesticService.isDuplicate(batchNo, trayNo)) {
            response.put("tips", "数据重复！");
            response.put("isSuccess", false);
            renderJson(response);
            return;
        }*/
        
        if (record.getId() != null) {// 编辑
            result = record.update();
        } else {// 新增
            
        }
        response.put("isSuccess", result);
        response.put("tips", result ? "保存成功": "保存失败");
        
        renderJson(response);
    }
    
    /**
     * @desc:批量删除
     * @author liyu
     */
    public void delete(){
        // id
        String idStr = getPara();
        String[] ids = idStr.split(","); 
        // 返回信息
        Map<String, Object> response = new HashMap<>();
        
        boolean result = FeesInternationalInService.delete(ids);
        response.put("isSuccess", result);
        response.put("tips", result ? "删除成功": "删除失败");
        renderJson(response);
    }
    
    public void importUI() {
        // 导入页面
        render("fees_international_in_import.html");
    }
    
    /** 
    * @Title: importByExcel 
    * @Description: 导入 excel 中的数据
    */
    public void importByExcel() {
        // excel
        UploadFile uploadFile = getFile();
        
        Map<String, Object> msgMap = FeesInternationalInService.importByExcel(uploadFile, getSession());
        
        renderJson(msgMap);
    }
    
    /** 
    * @Title: showErrorExcelMessage 
    * @Description: 显示错误信息
    */
    public void showErrorExcelMessage(){
        List<Integer> countlist = getSessionAttr("countWrongList");
        boolean ErrorFile = getSessionAttr("ErrorFile");
        setAttr("countlist", countlist);
        setAttr("ErrorFile", ErrorFile);
        render("wrong_message.html");
    }
    
}
