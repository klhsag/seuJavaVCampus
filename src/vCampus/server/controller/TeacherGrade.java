package vCampus.server.controller;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

import vCampus.utility.Token;
import vCampus.utility.loop.LoopAlwaysAdapter;
import vCampus.utility.loop.Message;
import vCampus.utility.socket.ResponseSender;
import vCampus.server.dao.*;
import vCampus.server.dao.model.*;
import vCampus.server.*;

public class TeacherGrade {	
	static {
		ServerMain.addRequestListener("TeacherGrade/get_course", new LoopAlwaysAdapter() {
			@Override
			public boolean resolveMessage(Message msg, Map<String, Object> transferData) {
								
                Token token=(Token) msg.getData().get("token");
                int userId=token.getUserId();                 
                Map<String, Object> data = new HashMap<String, Object>();
                                               
                Teacher t;
				try {
					t = TeacherDao.getTeach(userId);
		            ArrayList<Integer> classId = StrtoArr.strtoArr(t.getClassTable());
		            Object[][] res = new Object[classId.size()][4];
		            Lesson tmp = new Lesson();
		            for(int i = 0;i < classId.size(); i++){
		            	tmp = LessonsDao.getRec(classId.get(i));
		            	res[i][0] = tmp.getLessonID();
		            	res[i][1] = tmp.getLessonName();
		            	res[i][2] = tmp.getLocation();
		            	res[i][3] = null;
		            }
		            
		            data.put("object", res);
		            data.put("success", true);
				} catch (SQLException e) {
					e.printStackTrace();
					data.put("success", false);
				}
                
				((ResponseSender) transferData.get("sender")).send(data);
				return true;
			}			
		});
	}
}