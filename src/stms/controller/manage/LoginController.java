package stms.controller.manage;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.jfinal.aop.Before;
import com.jfinal.core.Controller;


import stms.interceptor.ManageInterceptor;
import stms.utils.CountTime;

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
		render("index.html");
	}
	
	/** 
	* @Title: login 
	* @Description: 登录页面
	* @param 
	* @return void
	* @throws 
	*/
	public void login() {
		HttpSession session =  getSession();
		render("login.html");
	}
	/**
	 * @desc 判定用户名以及密码，密码连续输错三次限制登录时间为10分钟
	 */
	public void adminLogin(){
		boolean result = false;
		String msg = new String();
		Map<String, Object> responseMap = new HashMap<>();
		String username = getPara("username");
		String password = getPara("password");	
		System.out.println(username+":"+password);
		if(username!=null&&username.equals("tom")){
			if(password!=null&&password.equals("123")){
				String ipaddress = LoginController.getRealIp(getRequest());
				boolean isMobile = LoginController.JudgeIsMoblie(getRequest());
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd kk:mm:ss");
				Date dateTime = new Date();
				String date = sdf.format(dateTime);
				System.out.println(ipaddress);
				System.out.println(isMobile);
				System.out.println(date);
				result = true;
			}
		}
		if(getSession().getAttribute("countnum")==null||(int)getSession().getAttribute("countnum")<3){
			if(username==null){
				msg="用户名密码错误";
				if(getSession().getAttribute("countnum")==null){
					int countnum = 1;
					getSession().setAttribute("countnum", countnum);
					msg = "用户名或密码错误";
				}else{
					int k =  (int) getSession().getAttribute("countnum");
					k++;
					getSession().setAttribute("countnum", k);
					System.out.println("3333de"+k);
					msg="用户名或密码错误";
				}
			}else{
				boolean v = result;
				if(v){
					result = true;
					msg = "登录成功";
					}else{
						if(getSession().getAttribute("countnum")==null){
							int countnum = 1;
							getSession().setAttribute("countnum", countnum);
							msg = "用户名或密码错误";
						}else{
							int k =  (int) getSession().getAttribute("countnum");
							k++;
							getSession().setAttribute("countnum", k);
							System.out.println("1111de"+k);
							msg="用户名或密码错误";
						}
					}
				}
				responseMap.put("result", result);	
				responseMap.put("msg", msg);
				renderJson(responseMap);
			
		}else{
			if((int)getSession().getAttribute("countnum")==3){
				CountTime countTime = new CountTime(getSession()); 
				Thread thread = new Thread(countTime);
				thread.start();	
			}
			int k =(int)getSession().getAttribute("countnum");
			k++;
			System.out.println("222de："+k);
			getSession().setAttribute("countnum", k);
			responseMap.put("result", false);	
			responseMap.put("msg", "超过三次,稍后再试");
			renderJson(responseMap);
		}	
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
}
