package vCampus.client.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

import vCampus.client.ClientMain;
import vCampus.client.view.ProfilePanel;
import vCampus.client.view.ShopPanel;
import vCampus.client.view.TeacherGradePanel;
import vCampus.utility.Config;
import vCampus.utility.Token;
import vCampus.utility.loop.*;

public class Auth {

	public static void login(String userName, String pwd) {
		Map<String, Object> data = new HashMap<String, Object>();
		data.put("userName", userName);
		data.put("encryptedPwd", pwd);
		Message msg = new Message("auth/login", data);
		
		ClientMain.getSocketLoop().sendMsgWithCallBack(msg, new LoopOnceAdapter() {
			@Override
			public void resolveMessageForSwing(Message msg, Map<String, Object> transferData) {
				int code = (int) msg.getData().get("code");
				if (code == 200) {
					Token token = (Token) msg.getData().get("token");
					ClientMain.getTempData().put("token", token);
					ClientMain.getTopFrame().getMainFrame().setTitle(userName + " - vCampus");
					
					String authority = (String)msg.getData().get("authority");
					ClientMain.getTopFrame().getMainFrame().setRole(authority);
					//管理员
					if(authority.equals("admin")) {
						
					}
					//非管理员
					else if(authority.equals("student")){
						ArrayList<String> s = new ArrayList<String>();					
						s = (ArrayList<String>)msg.getData().get("personInfo");
						((ProfilePanel) ClientMain.getTopFrame().getMainFrame().getPagePanel("个人信息")).setPersonInfo(s);   
						((ProfilePanel) ClientMain.getTopFrame().getMainFrame().getPagePanel("个人信息")).setPhoto((ImageIcon)msg.getData().get("photo")); 
						
					}
					else if(authority.equals("teacher")){
						String name = null;
						name = (String)msg.getData().get("name");
						((TeacherGradePanel) ClientMain.getTopFrame().getMainFrame().getPagePanel("教学事务")).initCoursetable((Object[][]) msg.getData().get("object")); 
						((TeacherGradePanel) ClientMain.getTopFrame().getMainFrame().getPagePanel("教学事务")).setLabel_name(name);
					}
					else if(authority.equals("publisher")){
						//pass
					}
					else if(authority.equals("doctor")) {
						
					}
					else {
						// TODO : NOT MATCH						
					}
					
					ClientMain.getTopFrame().showMainFrame();
				}else {
					//TODO
					JOptionPane.showMessageDialog(null, "登录失败", "vCampus", JOptionPane.ERROR_MESSAGE);
					Config.log("login fail " + code);
				}
			}
		});
	}
	
	public static void logout() {
		ClientMain.getSocketLoop().sendMsgWithCallBack(new Message("auth/logout"), new LoopOnceAdapter() {
			@Override
			public void resolveMessageForSwing(Message msg, Map<String, Object> transferData) {
				ClientMain.getTempData().remove("token");
				ClientMain.getTopFrame().showLoginFrame();
			}
		});
	}
	
}
  