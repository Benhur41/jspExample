package com.yedam.common;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.yedam.member.control.LoginControl;
import com.yedam.member.control.LoginFormControl;
import com.yedam.member.control.LogoutControl;
import com.yedam.member.control.MemberInfoControl;
import com.yedam.member.control.ModifyMemberControl;
import com.yedam.notice.control.DeleteNoticeControl;
import com.yedam.notice.control.InsertNoticeControl;
import com.yedam.notice.control.MainControl;
import com.yedam.notice.control.NoticeListControl;
import com.yedam.notice.control.SearchNoticeControl;
import com.yedam.notice.control.UpdateNoticeControl;

public class FrontController extends HttpServlet{
	
	private Map<String , Control> map;
	String encoding;
	public FrontController() {
		map = new HashMap<>();
	}
	
	@Override
	public void init(ServletConfig config) throws ServletException {
		encoding = config.getInitParameter("enc");
		//첫페이지
		map.put("/main.do", new MainControl());
		//공지사항
		map.put("/noticeList.do", new NoticeListControl());
		//글 작성
		map.put("/insertNotice.do", new InsertNoticeControl());
		//글 조회
		map.put("/searchNotice.do", new SearchNoticeControl());
		//글 수정
		map.put("/updateNotice.do", new UpdateNoticeControl());
		//글 삭제
		map.put("/deleteNotice.do", new DeleteNoticeControl());
		//로그인
		map.put("/loginForm.do", new LoginFormControl());
		map.put("/login.do", new LoginControl());
		map.put("/logout.do", new LogoutControl());
		map.put("/memberInfo.do", new MemberInfoControl());
		map.put("/modifyMember.do", new ModifyMemberControl());
	}
	
	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");//
		
		String uri = req.getRequestURI();
		String context = req.getContextPath();
		String path = uri.substring(context.length());
		System.out.println(path);
		
		Control control = map.get(path);
		String viewPage = control.execute(req, resp);
		System.out.println(viewPage);
		
//		if(viewPage.endsWith(".tiles")) {
//			
//		}
		
		//페이지 재지정
		RequestDispatcher rd = req.getRequestDispatcher(viewPage);
		rd.forward(req, resp);
		
	}
}
