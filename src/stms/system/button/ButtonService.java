package stms.system.button;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.IAtom;
import com.jfinal.plugin.activerecord.Record;
/**
 * @ClassName: ButtonService
 * @Description: 系统管理_按钮管理
 * @author: xuhui
 * @date: 2017年5月15日下午2:00:00
 * @version: 1.0 版本初成
 */
public class ButtonService {
	/**
     * @Title: getButtonList
     * @Description: 获取按钮列表
     * @return List<Record>
     * @throws
     */
    public static List<Record> getButtonList() {
        return Db.find("SELECT a.*,b.module_name  " +
                "FROM t_button AS a " +
                "LEFT JOIN t_menu AS b " +
                "ON a.menu_id = b.id ");
    }

    // 删除按钮
    public static boolean deleteButton(Integer id) {
        return Db.deleteById("t_button", id);
    }

    /** 
	* @Title: getMenuListByParams 
	* @Description: 根据条件获取菜单列表
	* @param params
	* @return List<Record>
	* @throws 
	*/
    public static List<Record> getMenuListByParams(String params) {
		String sql = " SELECT * FROM `t_menu` WHERE 1=1 ";
		sql += params;
		return Db.find(sql);
	}


}
