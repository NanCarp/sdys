package stms.login;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import com.jfinal.aop.Before;
import com.jfinal.aop.Clear;
import com.jfinal.core.Controller;
import com.jfinal.plugin.activerecord.Record;

import com.jfinal.plugin.activerecord.Db;
import stms.interceptor.ManageInterceptor;

import stms.utils.CountTime;
import stms.utils.MD5Util;


/**
 * @ClassName: LoginController.java
 * @Description: 登陆控制器
 * @author: LiYu
 * @date: 2017年5月12日下午4:02:44
 * @version: 1.0 版本初成
 */
@Before(ManageInterceptor.class)
public class LoginController extends Controller{
	/** 
	* @Title: index 
	* @Description: 首页
	* @param 
	* @return void
	* @throws 
	*/
	public void index(){
		//  session 获取用户
		Record user = getSessionAttr("user");

		// 测试账号
		user = Db.find("SELECT * FROM `t_user` WHERE id = 2").get(0);

		// 角色 id
        Integer roleId = user.getInt("role_id");
        // 角色对应菜单列表
        List<Record> menuList = LoginService.getMenusByRoleId(roleId);
        setAttr("menuList", menuList);

        render("index.html");
	}
	
	/** 
	* @Title: login 
	* @Description: 登录页面
	* @param 
	* @return void
	* @throws 
	*/
	@Clear
	public void login() {
		
		render("login.html");
	}
	
	/**
	 * @Title:amdinLogin
	 * @desc 判定用户名以及密码，同一个账户密码连续输错三次限制登录时间；
	 * @return void 
	 * @throws UnsupportedEncodingException 
	 * @throws NoSuchAlgorithmException 
	 */
	@Clear
	public void adminLogin() throws NoSuchAlgorithmException, UnsupportedEncodingException{
		String username = getPara("username");
		String password = getPara("password");
		boolean isInsider = getParaToBoolean("isInsider"); // 内部人员标识
		
		boolean result = false;//判定返回结果，true即验证正确
		boolean flag= true;//判定map中是否存在该key，false即存在
		boolean v = false;//MD5比对密码结果，true为正确
		String msg = new String();

		Record admin = new Record(); // 用来存放登陆用户
		if (isInsider) { // 判断是否是内部人员
			admin = LoginService.getUserFromSAP(username); // 内部人员，从 SAP 获取数据
		} else {
			admin = LoginService.getLoginInfo(username); // 非内部人员，从数据库获取数据
		}

		Map<String, Object> responseMap = new HashMap<>();
		if(admin!=null){
			 v = MD5Util.validPassword(password, admin.getStr("password"));
		}
		if(getSession().getAttribute("countMap")!=null){
			Map<String, Object> map = (Map<String, Object>) getSession().getAttribute("countMap");
			if(map.get(username)!=null){
				int count = (int) map.get(username);
				if(v&&count<3){
					result = true;
					msg = "登录成功";
					getSession().setAttribute("admin", admin);
					Cookie cookie = new Cookie("stms", ""+admin.getInt("id"));
					cookie.setMaxAge(60*60*24*7);
					cookie.setPath("/login/");
					getResponse().addCookie(cookie);
				}else{
					if(getSession().getAttribute("countMap")!=null){	
						System.out.println(getSession().getAttribute("countMap"));						
							Set<String> keys = map.keySet();
							for(String key:keys){
								if(key.equals(username)){
									int k = (int)map.get(key);
									if(k<3){
										k++;
										map.put(username, k);
										getSession().setAttribute("countMap", map);
										msg="用户名或密码错误";
									}					
									else if(k==3){
										CountTime countime = new CountTime(username, getSession());
										Thread thread = new Thread(countime);
										thread.start();
										k++;
										map.put(username, k);
										getSession().setAttribute("countMap", map);
										msg="信息错误三次或以上,稍后再试";
									}else{
										k++;
										map.put(username, k);
										getSession().setAttribute("countMap", map);
										msg="信息错误三次或以上,稍后再试";
									}
									result = false;
									flag = false;
								}
							}
							if(flag){
								Integer countt = 1;
								map.put(username, countt);
								getSession().setAttribute("countMap", map);
								msg="用户名和密码错了啦";
								result = false;
							}
					}
				}
			}else{
				int count = 1;
				map.put(username, count);
				getSession().setAttribute("countMap", map);
				msg="用户名或密码错误";
			}
		}else{
			if(v){
				result = true;
				msg = "登录成功";
				getSession().setAttribute("admin", admin);
				Cookie cookie = new Cookie("morality", ""+admin.getInt("id"));
				cookie.setMaxAge(60*60*24*7);
				cookie.setPath("/login/");
				getResponse().addCookie(cookie);
				//记录用户登录信息
				Map<String,Object> loginRecordMap = new HashMap<String, Object>();
				String userip = getRealIp(getRequest());//用户ip
				String MoblieOrPc = "PC";//用户登录设备
				if(JudgeIsMoblie(getRequest())){
					MoblieOrPc = "phone";
				}
				Integer userid = admin.getInt("id");//用户登录账号
				Date loginTime = new Date();//用户登录时间
				loginRecordMap.put("userip", userip);
				loginRecordMap.put("MoblieOrPc", MoblieOrPc);
				loginRecordMap.put("userid", userid);
				loginRecordMap.put("loginTime", loginTime);
				getSession().setAttribute("loginRecordMap", loginRecordMap);
			}else{
				Map<String,Integer> countMap = new HashMap<String,Integer>();
				Integer countt = 1;
				countMap.put(username, countt);
				getSession().setAttribute("countMap",countMap);
				msg="用户名或密码错误";
				result = false;	
			}
		}
		responseMap.put("result", result);	
		responseMap.put("msg", msg);
		renderJson(responseMap);
	}
	
	
	/**
	 * @desc 获取客户端的ip地址
	 * @param request
	 * @return String 
	 * @author xuhui
	 */
	public static String getRealIp(HttpServletRequest request) {
		
		String ip = request.getHeader("x-forwarded-for");
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}
		return ip;
	}
	
	/**
	 * @desc 判定客户端是否为手机登录
	 * @param request
	 * @return boolean 
	 * @author xuhui
	 */
	public static  boolean JudgeIsMoblie(HttpServletRequest request) {  
        boolean isMoblie = false;  
        String[] mobileAgents = { "iphone", "android", "phone", "mobile",  
                "wap", "netfront", "java", "opera mobi", "opera mini", "ucweb",  
                "windows ce", "symbian", "series", "webos", "sony",  
                "blackberry", "dopod", "nokia", "samsung", "palmsource", "xda",  
                "pieplus", "meizu", "midp", "cldc", "motorola", "foma",  
                "docomo", "up.browser", "up.link", "blazer", "helio", "hosin",  
                "huawei", "novarra", "coolpad", "webos", "techfaith",  
                "palmsource", "alcatel", "amoi", "ktouch", "nexian",  
                "ericsson", "philips", "sagem", "wellcom", "bunjalloo", "maui",  
                "smartphone", "iemobile", "spice", "bird", "zte-", "longcos",  
                "pantech", "gionee", "portalmmm", "jig browser", "hiptop",  
                "benq", "haier", "^lct", "320x320", "240x320", "176x220",  
                "w3c ", "acs-", "alav", "alca", "amoi", "audi", "avan", "benq",  
                "bird", "blac", "blaz", "brew", "cell", "cldc", "cmd-", "dang",  
                "doco", "eric", "hipt", "inno", "ipaq", "java", "jigs", "kddi",  
                "keji", "leno", "lg-c", "lg-d", "lg-g", "lge-", "maui", "maxo",  
                "midp", "mits", "mmef", "mobi", "mot-", "moto", "mwbp", "nec-",  
                "newt", "noki", "oper", "palm", "pana", "pant", "phil", "play",  
                "port", "prox", "qwap", "sage", "sams", "sany", "sch-", "sec-",  
                "send", "seri", "sgh-", "shar", "sie-", "siem", "smal", "smar",  
                "sony", "sph-", "symb", "t-mo", "teli", "tim-", /*"tosh",*/ "tsm-",  
                "upg1", "upsi", "vk-v", "voda", "wap-", "wapa", "wapi", "wapp",  
                "wapr", "webc", "winw", "winw", "xda", "xda-",  
                "Googlebot-Mobile" };  
        if (request.getHeader("User-Agent") != null) {  
            for (String mobileAgent : mobileAgents) {   
                if (request.getHeader("User-Agent").toLowerCase().indexOf(mobileAgent) >= 0) {  
                    isMoblie = true;  
                    break;  
                }  
            }  
        }  
        return isMoblie;  
    }
	
	/**
	 * @desc:退出登录
	 */
	@Clear
	public void loginOut(){
		
		Map<String, Object> loginRecordMap = (Map<String, Object>) getSession().getAttribute("loginRecordMap");
		if(loginRecordMap!=null){
			System.out.println(loginRecordMap);
			boolean b = LoginService.saveLoginMessage(loginRecordMap);
		}
		getSession().invalidate();
		render("login.html");
	}
}
