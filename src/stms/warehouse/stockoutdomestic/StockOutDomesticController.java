package stms.warehouse.stockoutdomestic;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.jfinal.aop.Before;
import com.jfinal.core.Controller;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;
import com.jfinal.upload.UploadFile;

import stms.interceptor.ManageInterceptor;
import stms.model.DomesInWarehouse;

/**
 * @ClassName: StockOutDomesticController.java
 * @Description: 入库明细（国内）控制器
 * @author: LiYu
 * @date: 2017年7月13日下午3:23:23
 * @version: 1.0 版本初成
 */
@Before(ManageInterceptor.class)
public class StockOutDomesticController extends Controller {
    // 基础数据列表
    public void index() {
        render("stock_out_domestic.html");
    }
    
    // 
    public void getJson(){
        String out_date = getPara("out_date");
        setAttr("out_date", out_date);
        String company_name = getPara("company_name");
        setAttr("company_name", company_name);
        String material_no = getPara("material_no");
        setAttr("company_name", company_name);
        
        Integer pageindex = 0; // 页码
        Integer pagelimit = getParaToInt("limit")==null? 12 :getParaToInt("limit"); // 每页数据条数
        Integer offset = getParaToInt("offset")==null?0:getParaToInt("offset");
        if(offset!=0){
            pageindex = offset/pagelimit;
        }
        pageindex += 1;
        
        Page<Record> page = StockOutDomesticService.getDataPages(pageindex, pagelimit, out_date, company_name, material_no);
        
        Map<String, Object> map = new HashMap<String,Object>();
        map.put("rows", page.getList());
        map.put("total",page.getTotalRow());
        //System.out.println(page.getList());
        
        renderJson(map);
    }
    
    // 获得
    public void getStockOutDomestic() {
        // id
        Integer id = getParaToInt();

        if (id != null) {//编辑
            Record StockOutDomestic = Db.findById("t_domes_out_warehouse", id);
            setAttr("stockOutDomestic", StockOutDomestic);
        }
        
        // 物流公司列表
        List<Record> companyList = StockOutDomesticService.getCompanyList();
        setAttr("companyList", companyList);

        render("stock_out_domestic_detail.html");
    }

    // 保存
    public void save() {
        DomesInWarehouse record = getModel(DomesInWarehouse.class, "");
        
        // 保存结果
        boolean result = false;
        // 返回信息
        Map<String, Object> response = new HashMap<>();
        // 重复检测
        Integer id = record.getId();
        /*String batchNo = record.getBatchNo();
        String trayNo = record.getTrayNo();
        if (id == null && StockOutDomesticService.isDuplicate(null, trayNo)) {
            response.put("tips", "数据重复！");
            response.put("isSuccess", false);
            renderJson(response);
            return;
        }*/
        
        if (id != null) {// 编辑
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
        String ids = getPara();
        boolean result = StockOutDomesticService.delete(ids);
        renderJson(result);
    }
    
    public void importUI() {
        // 导入页面
        render("stock_out_domestic_import.html");
    }
    
    /** 
    * @Title: importByExcel 
    * @Description: 导入 excel 中的数据
    */
    public void importByExcel() {
        // excel
        UploadFile uploadFile = getFile();
        
        Map<String, Object> msgMap = StockOutDomesticService.importByExcel(uploadFile, getSession());
        
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