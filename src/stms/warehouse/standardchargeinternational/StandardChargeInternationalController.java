package stms.warehouse.standardchargeinternational;

import java.util.HashMap;
import java.util.Map;

import com.jfinal.core.Controller;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;


/**
 * @ClassName: StandardChargeInternationalController.java
 * @Description:
 * @author: LiYu
 * @date: 2017年7月17日上午9:11:57
 * @version: 1.0 版本初成
 */
public class StandardChargeInternationalController extends Controller {
    /** 
    * @Title: index 
    * @Description: 仓储收费标准页面
    * @author liyu
    */
    public void index() {
        render("standard_charge_international.html");
    }
    
    /** 
    * @Title: getJson 
    * @Description: 仓储收费标准数据
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
        
        Page<Record> page = StandardChargeInternationalService.getDataPages(pageindex, pagelimit);
        
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
            Record record = Db.findById("t_standard_charge_international", id);
            setAttr("record", record);
        }

        render("standard_charge_international_detail.html");
    }
}
