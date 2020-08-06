package com.sunzy.demo.util.patchca;

import org.springframework.stereotype.Controller;

import javax.imageio.ImageIO;
import javax.servlet.GenericServlet;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;
import java.io.IOException;

@Controller  //NOSONAR
public class CommonController extends GenericServlet{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 生成验证码
	 * 
	 * @return
	 * @throws IOException
	 */
	public void getCaptcha(HttpSession session, HttpServletResponse response) throws IOException {

		// HttpHeaders headers = new HttpHeaders();
		// headers.setContentType(MediaType.IMAGE_JPEG);

		StringBuilder code = new StringBuilder();
		BufferedImage image = CaptchaUtils.getRandomCodeImage(code);
		session.removeAttribute("checkPicCode");
		session.setAttribute("checkPicCode", code.toString());

		response.setHeader("Content-Type", "image/jpeg");

		// 将内存中的图片通过流动形式输出到客户端
		ImageIO.write(image, "JPEG", response.getOutputStream());
		return;
	}

	@Override //NOSONAR
	public void service(ServletRequest req, ServletResponse res) throws ServletException, IOException {
		HttpServletRequest request = (HttpServletRequest)req;
		HttpSession session = request.getSession();
		HttpServletResponse response = (HttpServletResponse)res;
		getCaptcha(session,response);
		
	}

}
