package com.sunzy.demo.controller;

import com.sunzy.demo.util.ExcelUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

/**
 * @author sunzy
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

}
