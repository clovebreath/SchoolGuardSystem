package faceRecognition;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.eyekey.exception.EyeKeyException;
import com.eyekey.http.EyeKeyHttp;
/**
 * 进行一对多比对的模块，用于验证照片中的人是否在数据库中登记过
 * @author Zhu Ce
 *
 */
public class IdentifyModule 
{
	private static EyeKeyHttp e;
	private static JSONObject exist;
	/**
	 * 模块初始化方法，必须在比较前先行执行
	 * 当前一次初始化失败后必须重新初始化
	 * @param datas 照片base64数组
	 * @param ids 个人信息唯一指定id数组
	 * @return 初始化是否成功
	 */
	public static String init(JSONArray infos)
	{
		try
		{
		e = new EyeKeyHttp(ProjectInfomation.APP_ID , ProjectInfomation.APP_KEY);
		e.deleteCrowdById("all");
		e.deletePeopleById("all");
		e.createCrowdByName(ProjectInfomation.CROWD_NAME, null);
		for(int i = 0 ; i < infos.size() ; i++)
		{
			String temp_face_id = getFaceidBycheckImg(infos.getJSONObject(i).getString("pic"));
				if(!temp_face_id.equals("error"))
			{
					JSONObject j = e.createPeopleById(infos.getJSONObject(i).getString("id"), null , null, temp_face_id);
					String people_id =j.getString("people_id");
				e.addPeopleToCrowdById(null , ProjectInfomation.CROWD_NAME, people_id); 
			}
			
		}
		return ProjectInfomation.SUCCESS;
		}
		catch(EyeKeyException e)
		{
			System.out.println("网络连接失败！");
			return ProjectInfomation.NETWORK_ERROR;
		}
	}

	/**
	 * 1对n验证方法
	 * @param photoB64 摄像头拍摄的照片
	 * @return 最匹配的person_name，如果失败，返回error信息
	 */
	public static String identify(String photoB64)
	{
		try
		{
			exist = e.getCrowdByName(ProjectInfomation.CROWD_NAME);
			String face_id1 = getFaceidBycheckImg(photoB64);
			if(!face_id1.equals("error")&&exist.get("res_code").equals("0000"))
			{
				JSONObject j1=e.matchIdentifyByName(face_id1, ProjectInfomation.CROWD_NAME);
				System.out.println(j1);
				JSONArray ja=j1.getJSONArray("face");
				JSONObject j2=ja.getJSONObject(0);
				JSONObject json;
				int index = 0;
				while( j2.getJSONArray("result").isEmpty())
				{
					 j1=e.matchIdentifyByName(face_id1, ProjectInfomation.CROWD_NAME);
					 ja=j1.getJSONArray("face");
					 j2=ja.getJSONObject(0);
					 System.out.println(index++);
				}
				
				json = j2.getJSONArray("result").getJSONObject(0);

				//JSONObject json = e.matchIdentifyByName(face_id1, ProjectInfomation.CROWD_NAME).getJSONArray("face").getJSONObject(0).getJSONArray("result").getJSONObject(0);
				float similarity = json.getFloat("similarity");
				String result = json.getString("person_name");
				if(JudgeModule.judge(similarity).equals(ProjectInfomation.MATCH))
				{
					System.out.println(result);
					System.out.println(similarity);
					return result;
				}
				else
				{
					System.out.println(result);
					System.out.println(ProjectInfomation.NOT_MATCH);
					System.out.println(similarity);
					return ProjectInfomation.NOT_MATCH;
				}
				
			}else if(face_id1.equals("error")){
				System.out.println("接受的照片中无人脸");
				return ProjectInfomation.PHOTO_ERROR;
			}else
			{
				System.out.println("1对n模块初始化错误");
				return ProjectInfomation.MODULE_INIT_ERROR;
			}
		}
		catch(EyeKeyException e)
		{
			System.out.println("网络连接失败！");
			return ProjectInfomation.NETWORK_ERROR;
		}
	}
	
	/**
	 * 注册一个people的方法
	 * @param photoB64 Base64格式编码的照片
	 * @param id 唯一指定id
	 */
	public static String register(String photoB64 , String id)
	{
		try
		{
			String face_id1=getFaceidBycheckImg(photoB64);
			JSONObject json = e.createPeopleByName(id,null, ProjectInfomation.CROWD_NAME, face_id1);
			if(!json.get("res_code").equals("0000"))
			{
				System.out.println("人已存在");
				return ProjectInfomation.PERSON_EXIST_ERROR;
			}
			else
			{
				return ProjectInfomation.SUCCESS;
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
		json=e.checkingImgB64(base64, null);
		if(json.get("res_code").equals("0000"))
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
