package faceRecognition;
/**
 * 判断两张照片的similarity是否使系统认为它们是一个人
 * @author Zhu Ce
 *
 */
public class JudgeModule 
{
	/**
	 * 进行相似度到是否为同一个人的转换
	 * @param similarity 相似度
	 * @param systemJudge 服务器的判断
	 * @return 是否为同一个人，是则为1，否则为0
	 */
	public static String judge(float similarity , boolean systemJudge)
	{
		if(systemJudge)
		{
			return ProjectInfomation.MATCH;
		}
		else
		{
			if(similarity >= ProjectInfomation.BOUND)
			{
				return ProjectInfomation.MATCH;
			}
			else
			{
				return ProjectInfomation.NOT_MATCH;
			}
		}
		
	}
	/**
	 * 进行相似度到是否为同一个人的转换
	 * @param similarity 相似度
	 * @return 是否为同一个人，是则为1，否则为0
	 */
	public static String judge(float similarity)
	{
		
			if(similarity >= ProjectInfomation.BOUND)
			{
				return ProjectInfomation.MATCH;
			}
			else
			{
				return ProjectInfomation.NOT_MATCH;
			}
		
		
	}
}
