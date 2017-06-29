package stms.manual.singleloss;

import java.sql.SQLException;
import java.util.List;

import com.jfinal.core.Controller;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.IAtom;
import com.jfinal.upload.UploadFile;

/**
 * @ClassName: EndProductController
 * @Description: 手册管理_单损耗表
 * @author: xuhui
 * @date: 2017年6月27日上午10:00:00
 * @version: 1.0 版本初成
 */
public class SingleLossController extends Controller {

	public void index(){
		String manualno = getPara("manualno");
		String endproname = getPara("endproname");
		
		System.out.println(manualno+endproname);
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
	public void importExcel(){
		boolean flag = Db.tx(new IAtom() {		
			@Override
			public boolean run() throws SQLException {
				// TODO Auto-generated method stub
				try{
					
				}catch(Exception e){
					e.printStackTrace();
					return false;	
				}
				return false;
			}
		});
		renderJson(true);
	}
	
	/**
	 * @desc:新增以及修改单损耗手册
	 * @author xuhui
	 */
	public void getSingleLoss(){
		
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
}
