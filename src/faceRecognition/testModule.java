package faceRecognition;

import java.io.File;

public class testModule {
			//摄像头照片
			private static String imgPath1="F:\\test\\sxt\\pic12.jpg";
			//身份证照片
			private static String imgPath2="F:\\test\\sfz\\pic3.jpg";
			
	public static void main(String[] args) 
	{
		
		File file1=new File(imgPath1);
		File file2=new File(imgPath2);
		
		String s1 = EncodeModule.encodeImgageToBase64(file1);
		String s2 = EncodeModule.encodeImgageToBase64(file2);
		
		CompareModule.init();
		CompareModule.Compare(s1, s2);
		
		File file3 = new File("F:\\test\\sjk");
		File[] files = file3.listFiles();
		String[] strs = new String[files.length];
		String[] names = new String[files.length];

		for(int i = 0 ; i < files.length ; i++)
		{
			String b64 = EncodeModule.encodeImgageToBase64(files[i]);
			names[i] = files[i].getName();
			strs[i] = b64;
		}
/*
		IdentifyModule.init(strs, names);
		IdentifyModule.identify(s1);
		*/

		
	}

}
