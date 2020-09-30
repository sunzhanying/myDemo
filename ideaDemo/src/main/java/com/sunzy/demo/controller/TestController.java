package com.sunzy.demo.controller;

import com.alibaba.fastjson.JSONObject;
import com.sunzy.demo.util.fileUtils.FileUtils;
import com.sunzy.demo.util.excel.ExcelUtils;
import com.sunzy.demo.util.patchca.CaptchaUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.InputStream;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

/**
 * @author sunzy 测试访问地址：http://127.0.0.1:8080/hello
 * @date 2019/10/18
 */
@Controller
public class TestController {

    @RequestMapping(value = "/hello",method = RequestMethod.GET)
    @ResponseBody
    public String say(){
        System.out.println("12222222222233333333333333333333344");
        return "hello";
    }


    @RequestMapping(value = "/getCaptcha",method = RequestMethod.GET)
    @ResponseBody
    public void getCaptcha(String userCode,HttpServletResponse response){
        System.out.println("----- in getCaptcha -----");
        StringBuilder code = new StringBuilder();
        BufferedImage image = CaptchaUtils.getRandomCodeImage(code);
        JSONObject json = new JSONObject();
        json.put("key",userCode);//前端传过来的随机数，用于关联前端输入验证码之后找到与后台对应存储的验证码，可用作redis的key
        json.put("value",code);
        json.put("flag","1");//1，标识图片验证码
        json.put("time","300");//3分钟自动过期
        //todo 保存进redis，或者放入到session中，用于登录时拿出做校验
        System.out.println("json：" + json.toJSONString());
        try{
            response.setHeader("Content-Type","image/jpeg");
            ImageIO.write(image,"JPEG",response.getOutputStream());
        }catch (Exception e){

        }


    }

    @RequestMapping(value = "/download",method = RequestMethod.GET)
    @ResponseBody
    public void download(HttpServletResponse response){
        System.out.println("download");
        try{
            FileUtils.downloadLocal(response);
        }catch (Exception e){

        }

    }

    //上传excel文件并解析
    @RequestMapping(value = "/upload",method = RequestMethod.POST)
    @ResponseBody
    public String uploadFile(@RequestParam(name = "file",required = true) MultipartFile file){
       try{
           System.out.println("upload");
           InputStream inputStream = file.getInputStream();
           String name = file.getOriginalFilename();
           System.out.println("name" + name);
           List<String[]> list =  ExcelUtils.readStringExcel(inputStream,name);
           StringBuffer sb = new StringBuffer();
           for(String[] s:list){
               sb.append(s[0]);
               sb.append(s[1]);
               sb.append("--------------");
           }
           return sb.toString();
       }catch (Exception e){

       }
        return "hello";
    }

    //下载excel
    @GetMapping("/downloadExcel")
    @ResponseBody
    public String downloadExcel( HttpServletResponse response){
        try{
            System.out.println("in downloadExcel");
            response.reset();//重置response
            String name = "模板数据.xlsx";
            String[] title = {"名称","内容"};
            String[] text = {"内黄县","领导"};
            List<Object[]> objects = new ArrayList<>();
            objects.add(title);
            objects.add(text);
            response.setContentType("application/x-excel;charset=UTF-8");
            response.setHeader("Content-Disposition","attachment;filename=" + URLEncoder.encode(name,"UTF-8"));
            response.setCharacterEncoding("UTF-8");
            // response.getOutputStream() 也可以指向固定目录的文件流
            ExcelUtils.exportExcelToResponse(name,objects,"sheet007",response.getOutputStream());

        }catch (Exception e){

        }

        return "hello";
    }

    //下载excel todo,爬虫信息 下载成excel
    @GetMapping("/pc")
    @ResponseBody
    public String pc( HttpServletResponse response){
        try{
            System.out.println("in downloadExcel");
            response.reset();//重置response
            String name = "模板数据.xlsx";
            String[] title = {"名称","内容"};
            String[] text = {"内黄县","领导"};
            List<Object[]> objects = new ArrayList<>();
            objects.add(title);
            objects.add(text);
            response.setContentType("application/x-excel;charset=UTF-8");
            response.setHeader("Content-Disposition","attachment;filename=" + URLEncoder.encode(name,"UTF-8"));
            response.setCharacterEncoding("UTF-8");
            // response.getOutputStream() 也可以指向固定目录的文件流
            ExcelUtils.exportExcelToResponse(name,objects,"sheet007",response.getOutputStream());

        }catch (Exception e){

        }

        return "hello";
    }

}
