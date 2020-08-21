/*
package com.sunzy.demo;

*/
/**
 * @author sunzy
 * @date 2020/8/19
 *//*

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sunzy.demo.common.Constants;
import com.sunzy.demo.common.OutputObject;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.RequestEntity;
import org.apache.commons.httpclient.methods.StringRequestEntity;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.DisabledAccountException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
//import com.cmos.core.bean.OutputObject;
*/
/*import com.cmos.core.logger.Logger;
import com.cmos.core.logger.LoggerFactory;*//*

//import com.cmos.core.util.JsonUtil;
import com.cmos.ngopm.service.IOprLogService;
import com.cmos.ngopm.service.IStaffInfoService;
import com.cmos.ngopm.shiro.CmosUsernamePasswordToken;
import com.cmos.ngopm.shiro.exception.UnknowPermissionsException;
import com.cmos.ngopm.util.PropertiesUtil;
*/
/**
 * @author guoXiangRu
 * 政企客户中心 单点登录集成
 *
 *
 * 中国移动客服云平台 简称 客服云平台
 * 被集成的系统       简称 集成系统
 *
 *
 *//*

public class OauthFilter implements Filter {
    //private static final Logger logger = (Logger) LoggerFactory.getServiceLog(OauthFilter.class);
    //verifiedUri值可以变更，也可以保持原URI
    public final String verifiedUri = "SSO/fromCrmSystem";

    //可以将以下三个重要参数，配置到配置文件
    //public static String crmVerifiedUrl = "http://127.0.0.1:18086/crmapi/SSO/checkAuthCode";
    //public final static String clientId = "15967055034686068";
    //public final static String clientSecretKey = "82cebb5aa6c24a85b841e273af0efbd7";
    public final static String rtnCode = "returnCode";
    public final static String rtnMsg = "returnMessage";
    public final static String SUCCESS = "0";
    public final static String FAILURE = "-9999";
    */
/**
     * Default constructor.
     *//*

    public OauthFilter() {
    }
    */
/**
     * @see Filter#destroy()
     *//*

    public void destroy() {
    }
    */
/**
     * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
     *//*

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        //获取request对象
        HttpServletRequest reqObj = (HttpServletRequest) request;
        String reqUri = reqObj.getRequestURI();//拦截 单点登录路径

        //拦截单点登录地址
        if(reqUri.endsWith(verifiedUri)) {
            //将当前session会话直接失效，不管是登录或非登录状态，以下是Shiro组件控制，如果是其它方式请修改代码
            Subject currentUser = SecurityUtils.getSubject();
            currentUser.logout();
            //不需要请求端携带cookie相关参数
            Enumeration em = reqObj.getSession().getAttributeNames();  //得到session中所有的属性名
            while (em.hasMoreElements()) {
                reqObj.getSession().removeAttribute(em.nextElement().toString()); //遍历删除session中的值
            }
            //单点登录开始验证
            ssoHandle((HttpServletRequest)request, (HttpServletResponse)response);
            return;
        }
        chain.doFilter(request, response);
    }
    */
/*
     *单点登录，验证交互控制函数
     *//*

    private void ssoHandle(HttpServletRequest request, HttpServletResponse response) {
        //logger.error("单点登录开始");
        JSONObject resJsonObj = OauthUtils.sso(request, response);
        if(resJsonObj != null) {
            if (FAILURE.equals(resJsonObj.get(rtnCode))){
                sendJsonBySimple(resJsonObj.get(rtnCode).toString(), resJsonObj.get(rtnMsg).toString(), response);
                return;
            }
			*/
/*
			 * 验证通过后，客服云平台当前登录帐户基本信息结构体如下
			 * {
				    "bean": {
				        "deptId": "组织ID",
				        "level": "用户等级",
				        "mobilePhone": "用户手机号",
				        "roleId": "角色ID",
				        "staffId": "用户ID",
				        "staffName": "用户名称",
				        "staffNo": "用户工号",
				        "staffType":"0企业客户,1管理者"
				    },
				    "returnCode":"0",
				    "returnMessage":"成功"
				}
			 *//*


            */
/*
             * 1：先判断 集成系统是否有此帐户
             *   不存在：把响应结果做为集成系统的注册资料(登录密码集成系统可默认一个，可以和客服云平台不一致)，也可以调用此帐户更加详细的内存见技术文档。
             *     存在：接着向后流转
             *//*

            //集成系统注册代码省略，需要集成系统来写注册逻辑开发

            //2: 通过响应参数如 mobilePhone、staffId、staffNo 选一个来完成登录，不需要使用密码
            OauthUtils.shiroLogin(resJsonObj, response);
        }
    }
    */
/**
     * @see Filter#init(FilterConfig)
     *//*

    public void init(FilterConfig fConfig) throws ServletException {

    }
    public static void sendJsonBySimple(String rtnCode, String rtnMeg, HttpServletResponse response) {
        JSONObject rtnJson = JSONObject.parseObject("{}");
        rtnJson.put(OauthFilter.rtnCode, rtnCode);
        rtnJson.put(OauthFilter.rtnMsg, rtnMeg);
        sendJson(rtnJson.toString(), response);
    }

    */
/** Print OutputStream to the Browser **//*

    public static void sendJson(String json, HttpServletResponse response) {
        try {
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().print(json);
            response.setStatus(HttpServletResponse.SC_OK);
        } catch (IOException e) {
            //logger.error("sendJson", "Exception Occured When Send Json to Client !", e);
        }
    }
}
class OauthUtils{
    //private static final Logger logger = (Logger) LoggerFactory.getServiceLog(OauthUtils.class);
    private static String DEFAULT_CHARSET = "UTF-8";
    private final static String ISO_CHARSET = "ISO-8859-1";
    private static IStaffInfoService staffInfoService;
    private static IOprLogService opreLogService;

    //  注：shiroLogin函数是根据 能开平台运营者后台开发的登录模块，集成系统需要根据自己系统的登录模型改造下。
    //规范：可通过 mobilePhone、staffId、staffNo 选一个来完成登录，不需要验证密码。
    public static void shiroLogin(JSONObject resJsonObj, HttpServletResponse response) {
		*/
/*
		   resJsonObj 的数据结构
		   {
			    "bean": {
			        "deptId": "组织ID",
			        "level": "用户等级",
			        "mobilePhone": "用户手机号",
			        "roleId": "角色ID",
			        "staffId": "用户ID",
			        "staffName": "用户名称",
			        "staffNo": "用户工号",
			        "staffType":"0企业客户,1管理者"
			    },
			    "returnCode":"0",
			    "returnMessage":"成功"
			}
		 *//*

        if (staffInfoService == null) {
            WebApplicationContext wac = ContextLoader.getCurrentWebApplicationContext();
            staffInfoService = (IStaffInfoService) wac.getBean("staffInfoService");
            opreLogService = (IOprLogService) wac.getBean("opreLogService");
        }
        resJsonObj = resJsonObj.getJSONObject("bean");
        String userName = resJsonObj.getString("staffNo");
        String staffType = resJsonObj.getString("staffType");
        String mobilePhone = resJsonObj.getString("mobilePhone");
        String errMessage = null;
        //仅允许管理者 打开
        if("1".equals(staffType)) {
            OutputObject outputObject = new OutputObject(OauthFilter.SUCCESS, "单点登录成功");
            //shiro登录验证
            try {
                Subject currentUser = SecurityUtils.getSubject();

                Map<String,Object> staffInfo = staffInfoService.queryStaffInfo(mobilePhone,"127.0.0.1");
                currentUser.getSession().setAttribute(Constants.SESSION_SMS_USER_MOBILE, staffInfo.get("mobilePhone"));

                //Shiro改造，让shiro支持只验证用户名，不验证密码
                CmosUsernamePasswordToken token = new CmosUsernamePasswordToken(userName, "sso");
                token.setValidate(false);

                //登录
                currentUser.login(token);

                //保存当前登录帐户基本信息
                Session session = currentUser.getSession();
                staffInfo.put("operIp", session.getHost());
                session.setAttribute(Constants.SESSION_STAFF_INFO, staffInfo);
                //logger.info("[staffInfoService]  [ queryStaffInfo ]  : ", "[ staffId = "+userName+" ]");


                //保存登陆日志
                Map<String, String> oprLogInfo = new HashMap<>();
                oprLogInfo.put("staffCode", userName);
                oprLogInfo.put("sessionId", session.getId()+"");
                oprLogInfo.put("ip", "");
                oprLogInfo.put("content", "单点登录");
                opreLogService.createOprLog(oprLogInfo);
                //logger.info("[opreLogService] = "+opreLogService+" : ", "[" + oprLogInfo.toString() + "]");

                //将重要的cookie信息返回给客服云平台（客服云平台不能直接从response的cookie里面读，经测试response.cookie里有时候获取不到sessionID）。
                outputObject.getBean().put("ngopmsessionid", session.getId().toString());//这个sessionid是必须返回的。
            } catch (UnknownAccountException e) {
                //logger.info("[login error! = ]", e);
                errMessage = "账号或密码不正确";
            } catch (UnknowPermissionsException e){
                //logger.info("[login error! = ]", e);
                errMessage = "账号或密码不正确";//此账号没有访问权限，请联系管理员
            } catch (LockedAccountException e) {
                //logger.info("[login error! = ]", e);
                errMessage = "账号或密码不正确";//账号被锁定,请稍后重试
            } catch (DisabledAccountException e) {
                //logger.info("[login error! = ]", e);
                errMessage = "账号或密码不正确";//工号账号无效,请重新输入
            } catch (Exception e) {
                //logger.info("[login error! = ]", e);
                errMessage = "系统异常";
            }
            //登陆成功
            if(errMessage == null) {
                //OauthFilter.sendJson(JsonUtil.convertObject2Json(outputObject), response);
                OauthFilter.sendJson(outputObject.toString(), response);
            }else {
                OauthFilter.sendJsonBySimple(OauthFilter.FAILURE, "帐户登录失败，原因：" + errMessage, response);
            }
        }else {
            OauthFilter.sendJsonBySimple(OauthFilter.FAILURE, "您没有权限打开此页面", response);
        }
    }
    */
/*
     * 集成系统 携带客服云平台传过来的动态sign参数 和本地的 clientId、secryKey 回调 客服云平台 进行校验
     *//*

    public static JSONObject sso(HttpServletRequest request, HttpServletResponse response) {
        String reqData = getDataFromRequestContext(request);
        Map<String, Object> reqMap = JSON.parseObject(reqData, Map.class);
        String sign = ((Map)reqMap.get("params")).get("sign").toString();
        JSONObject jsonParams = new JSONObject();
        jsonParams.put("sign", sign);
        jsonParams.put("clientId", PropertiesUtil.getString("integrate.crm.clientId"));       //参数需要配置到属性文件里
        jsonParams.put("secryKey", PropertiesUtil.getString("integrate.crm.clientSecretKey"));//参数需要配置到属性文件里
        JSONObject reqJson = new JSONObject();
        reqJson.put("params", jsonParams);
        String requestUrl = PropertiesUtil.getString("integrate.crm.VerifiedUrl");
        String resString = sendPostHttp(reqJson.toString(), requestUrl);
        return resString != null ? JSONObject.parseObject(resString) : null;
    }

    */
/*
     *  获取请求报文
     *//*

    public static String getDataFromRequestContext (HttpServletRequest request){
        String bodyData = "";
        try {
            byte[] buffer = new byte[4 * 1024];
            InputStream sis = request.getInputStream();
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            int bLen;
            while ( (bLen = sis.read(buffer)) > 0) {
                baos.write(buffer, 0, bLen);
            }
            bodyData = new String(baos.toByteArray(), DEFAULT_CHARSET);
        }
        catch (IOException e) {
        }
        return bodyData;
    }

    public static String sendPostHttp(String postString, String requestUrl) {
        //logger.error("开始调用直连地址：" + requestUrl);
        //建立HttpPost对象
        HttpClient httpClient = new HttpClient();
        httpClient.getHttpConnectionManager().getParams().setConnectionTimeout(6000);
        PostMethod postMethod = new PostMethod(requestUrl);
        //返回应答字符串
        try {
            RequestEntity requestEntity = new StringRequestEntity(postString, "application/json", "UTF-8");
            postMethod.setRequestEntity(requestEntity);
            httpClient.executeMethod(postMethod);
            String responseString = postMethod.getResponseBodyAsString();
            String resCharSet = postMethod.getResponseCharSet();
            if(resCharSet.toUpperCase().equals(ISO_CHARSET)) {
                responseString = new String(responseString.getBytes(ISO_CHARSET), DEFAULT_CHARSET);
            }
            //.error("调用成功：" + responseString);
            return responseString;
        } catch (Exception e) {
            //logger.error("调用失败", e);
            return  "{\"returnCode\":\"-9999\",\"returnMessage\":\"调用失败\"}";
        } finally {
            if (postMethod != null){
                postMethod.releaseConnection();
            }
        }
    }
}
*/
