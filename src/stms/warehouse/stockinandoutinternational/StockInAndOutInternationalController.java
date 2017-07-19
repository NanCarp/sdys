package stms.warehouse.stockinandoutinternational;

import java.util.HashMap;
import java.util.Map;

import com.jfinal.core.Controller;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;

/**
 * @ClassName: StockInAndOutInternationalController.java
 * @Description:
 * @author: LiYu
 * @date: 2017年7月17日下午3:02:51
 * @version: 1.0 版本初成
 */
public class StockInAndOutInternationalController extends Controller {
    /** 
    * @Title: index 
    * @Description: 出入库明细页面
    * @author liyu
    */
    public void index() {
        render("stock_in_and_out_international.html");
    }
    
    /** 
    * @Title: getJson 
    * @Description: 出入库明细数据
    * @author liyu
    */
    public void getJson(){
        String in_date = getPara("in_date");
        setAttr("in_date", in_date);
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
        
        Page<Record> page = StockInAndOutInternationalService.getDataPages(pageindex, pagelimit, in_date, company_name, material_no);
        
        Map<String, Object> map = new HashMap<String,Object>();
        map.put("rows", page.getList());
        map.put("total",page.getTotalRow());
        System.out.println(page.getList());
        
        renderJson(map);
    }
    
    /** 
    * @Title: getRecord
    * @Description: 单条出入库明细数据
    * @author liyu
    */
    public void getRecord() {
        // 批次号
        String batch_no = getPara();
        // 
        Record record = StockInAndOutInternationalService.getRecord(batch_no);
        setAttr("record", record);
        
        render("stock_in_and_out_international_detail.html");
    }
    
    /** 
    * @Title: save 
    * @Description: 修改出入库日期
    * @author liyu
    */
    public void save() {
        // 批次号
        String batch_no = getPara("batch_no");
        // 入库日期
        String in_date = getPara("in_date");
        // 出库日期
        String out_date = getPara("out_date");
        // 返回信息
        Map<String, Object> response = new HashMap<>();
        // 
        boolean result = StockInAndOutInternationalService.save(batch_no, in_date, out_date);
        
        response.put("isSuccess", result);
        response.put("tips", result ? "保存成功": "保存失败");
        
        renderJson(response);
        
    }
    
}
