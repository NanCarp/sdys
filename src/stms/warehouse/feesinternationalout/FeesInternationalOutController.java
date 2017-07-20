package stms.warehouse.feesinternationalout;

import java.util.HashMap;
import java.util.Map;

import com.jfinal.core.Controller;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;

/**
 * @ClassName: FeesInternationalInController.java
 * @Description: 费用明细结算表（国际）出库费用
 * @author: LiYu
 * @date: 2017年7月19日下午1:16:48
 * @version: 1.0 版本初成
 */
public class FeesInternationalOutController extends Controller {
    /** 
    * @Title: index 
    * @Description: 费用明细结算表（国际）页面
    * @author liyu
    */
    public void index() {
        render("fees_international_out.html");
    }
    
    /** 
    * @Title: getJson 
    * @Description: 费用明细结算表（国际）数据
    * @author liyu
    */
    public void getJson() {
        Integer pageindex = 0; // 页码
        Integer pagelimit = getParaToInt("limit")==null? 12 :getParaToInt("limit"); // 每页数据条数
        Integer offset = getParaToInt("offset")==null?0:getParaToInt("offset");
        if(offset!=0){
            pageindex = offset/pagelimit;
        }
        pageindex += 1;
        
        Page<Record> page = FeesInternationalOutService.getDataPages(pageindex, pagelimit);
        
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
            Record record = Db.findById("t_inter_warehouse_fees_out", id);
            setAttr("record", record);
        }

        render("fees_international_out_detail.html");
    }
}
