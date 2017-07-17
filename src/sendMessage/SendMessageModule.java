package sendMessage;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLConnection;
import java.util.Date;

import java.text.SimpleDateFormat;
import org.apache.commons.codec.digest.DigestUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import faceRecognition.ProjectInfomation;


public class SendMessageModule {
    
	private static String param = "";
	private static SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
	
	public final static String ACCOUNT_SID = "34575ba9cd944cc6927ca6706770952b";
	public final static String AUTH_TOKEN = "7c5bcf0e81c747abba2343a5a843d9d0";
	
	private static String url = "https://api.miaodiyun.com/20150822/industrySMS/sendSMS";
	
	private static void setParam(String key , String content)
	{
		if(!param.equals(""))
		{
			param += "&" + key + "=" + content;
		}
		else
		{
			param += key + "=" + content;
		}
	}
	
	public static String sendMessageSMS(String telephone_number, String teacher_name , String student_class , String student_name , String parent_name)
	{
		
		setParam("accountSid", ACCOUNT_SID);
    	setParam("smsContent", "{1}老师： 经过预约的{2}{3}学生的家长{4}已经到达学校，请老师下课后在办公室进行接待。");
    	
    	setParam("templateid" , "49576214");
    	setParam("param", teacher_name + "," + student_class + "," + student_name + "," + parent_name);
    	setParam("to" , telephone_number);
    	
    	String time = df.format(new Date());
    	setParam("timestamp", time);
    	
    	String sig = DigestUtils.md5Hex(ACCOUNT_SID + AUTH_TOKEN + time);
    	setParam("sig", sig);
    	
        String result = sendPost(url);
        System.out.println(result);
        
        JSONObject json = JSONObject.parseObject(result);
        if(json.getString("respCode").equals("00000"))
        {
        	return "sucess";
        }
        else
        {
        	return "error";
        }
        
	}
	
	/**
     * 向指定 URL 发送POST方法的请求
     * 
     * @param url
     *            发送请求的 URL
     * @param param
     *            请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
     * @return 所代表远程资源的响应结果
     */
    private static String sendPost(String url) {
       // PrintWriter out = null;
    	 OutputStreamWriter out = null;
        BufferedReader in = null;
        String result = "";
        
        try {
            URL realUrl = new URL(url);
            // 打开和URL之间的连接
            URLConnection conn = realUrl.openConnection();
            // 设置通用的请求属性
            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.setRequestProperty("user-agent",
                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            conn.setRequestProperty("Accept-Charset", "utf-8");
            conn.setRequestProperty("contentType", "utf-8");
            // 发送POST请求必须设置如下两行
            conn.setDoOutput(true);
            conn.setDoInput(true);
            out = new OutputStreamWriter(conn .getOutputStream(), "UTF-8"); 
            out.write(param);
            // 获取URLConnection对象对应的输出流
            //out = new PrintWriter(conn.getOutputStream());
            // 发送请求参数
            //out.print(param);
            // flush输出流的缓冲
            out.flush();
            // 定义BufferedReader输入流来读取URL的响应
            in = new BufferedReader(
                    new InputStreamReader(conn.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) 
            {
                result += line;
            }
            System.out.println(result);
        } catch (Exception e) {
            System.out.println("发送 POST 请求出现异常！"+e);
            e.printStackTrace();
        }
        //使用finally块来关闭输出流、输入流
        finally{
            try{
                if(out!=null){
                    out.close();
                }
                if(in!=null){
                    in.close();
                }
            }
            catch(IOException ex){
                ex.printStackTrace();
            }
        }
        return result;
    }
    /*
    public static void main(String[] args) {
        
    	sendMessageSMS("17865197365", "小二", "7班", "阿旺", "Elisa");
    }
    */
}
