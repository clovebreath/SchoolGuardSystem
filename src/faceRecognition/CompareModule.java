package faceRecognition;

import com.alibaba.fastjson.JSONObject;
import com.eyekey.exception.EyeKeyException;
import com.eyekey.http.EyeKeyHttp;

/**
 * 相机照片与身份证照片进行人证合一的验证比较模块
 * @author Zhu 	Ce
 *
 */
public class CompareModule {
	
	private static EyeKeyHttp e;
	
	/**
	 * 模块初始化方法，必须在比较前先行执行
	 * 当前一次初始化失败后必须重新初始化
	 * @return 初始化是否成功
	 */
	public static String init()
	{
		try
		{
			e = new EyeKeyHttp(ProjectInfomation.APP_ID , ProjectInfomation.APP_KEY);
			e.deletePeopleById("all");
			return ProjectInfomation.SUCCESS;
		}
		catch(EyeKeyException e)
		{
			System.out.println("网络连接失败！");
			return ProjectInfomation.NETWORK_ERROR;
		}
		
		
	}
	
		/**
		 * 将摄像头照片与身份证照片base64进行人证合一人证的方法
		 * @param photoB64 摄像头照片的base64
		 * @param cardB64身份证照片的base64
		 * @return 人证是否合一的识别码
		 */
	public static String Compare(String photoB64 , String cardB64)
	{
		try
		{
			String face_id1=getFaceidBycheckImg(photoB64);
			String face_id2=getFaceidBycheckImg(cardB64);
			
			if(!face_id1.equals("error") && !face_id2.equals("error"))
			{
				
				JSONObject json  = e.createPeopleById(null, null , null, face_id2);
				String people_id = json.getString("people_id");
				
				JSONObject json2 = e.matchVerifyById(face_id1, people_id);
				float similarity =  json2.getFloatValue("similarity");
				boolean systemJudge = json2.getBoolean("result");
				String result = JudgeModule.judge(similarity , systemJudge);
				System.out.println(similarity);
				System.out.println(result);
				e.deletePeopleById(people_id);
				return result;
			}
			else
			{
				if(face_id1.equals("error"))
				{
					System.out.println("pic1 error 请重新拍照!");
					return ProjectInfomation.PHOTO_ERROR;
				}
				else
				{
					System.out.println("pic2 error 请重新刷卡!");
					return ProjectInfomation.ID_CARD_ERROR;
				}
				
			}
		}
		catch(EyeKeyException e)
		{
			System.out.println("网络连接失败！");
			return ProjectInfomation.NETWORK_ERROR;
		}
		
		
		
	}
	
	
	/**
	 * 将base64编码的图片转换为唯一确定的face_id
	 * @param base64 base64编码的图片
	 * @return 唯一确定的face_id
	 */
	private static String getFaceidBycheckImg(String base64)
	{
		JSONObject json=null;
		json = e.checkingImgB64(base64, null);
		if("0000".equals(json.get("res_code")))
		{
			return json.getJSONArray("face").getJSONObject(0).get("face_id").toString();
		}
		else
		{
			System.out.println("照片中无人脸");
			return "error";
		}
	}
	
	
	
	
	
	
	
	
	
}
