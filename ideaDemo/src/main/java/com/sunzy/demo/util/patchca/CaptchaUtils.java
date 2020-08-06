package com.sunzy.demo.util.patchca;

import com.sunzy.demo.util.patchca.service.Captcha;
import sun.misc.BASE64Encoder;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;


public class CaptchaUtils {
	private static  ConfigurableCaptchaService cs = new ConfigurableCaptchaService();
   
	private CaptchaUtils(){
		throw new IllegalAccessError("工具类不能实例化");
	}

	/**
	 * 生成随机图片
	 */
	public static BufferedImage getRandomCodeImage(StringBuilder randomCode) {
        Captcha captcha = cs.getCaptcha();
        randomCode.append(captcha.getChallenge());
        return captcha.getImage();

	}

	
//	public static void main(String[] args) {
//
//		BASE64Encoder  base64 = new BASE64Encoder();
//
//
//
//		StringBuilder code = new StringBuilder();
//		BufferedImage image = CaptchaUtils.genRandomCodeImage(code);
//		try {
//			// 将内存中的图片通过流动形式输出到客户端
//			System.out.println(image.toString());
//			System.out.println(code.toString());
//			ImageIO.write(image, "png", new FileOutputStream(new File(
//					"random-code.png")));
//		} catch (Exception e) {
//			System.out.println(e);
//		}

//	}

	
}

