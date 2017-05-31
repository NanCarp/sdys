package stms.utils;

import javax.servlet.http.HttpSession;

import org.apache.catalina.Session;

public class CountTime implements Runnable {
	HttpSession session;
	public CountTime(HttpSession session){		
		this.session = session;
	}
	@Override
	public void run() {
		// TODO Auto-generated method stub
		int k = (int) session.getAttribute("countnum");
		while(k>2){
			try {
				Thread.currentThread().sleep(1000*20);
				session.setAttribute("countnum", 0);
				k--;
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}		
		System.out.println("time out");
	}
}
