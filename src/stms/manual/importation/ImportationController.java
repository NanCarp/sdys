package stms.manual.importation;

import com.jfinal.core.Controller;

public class ImportationController extends Controller{

	/** 
	* @Title: index 
	* @Description: 进口明细列表
	*/
	public void index() {
		render("importation.html");
	}
}
