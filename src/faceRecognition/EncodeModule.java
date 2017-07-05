package faceRecognition;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.MalformedURLException;

import javax.imageio.ImageIO;

import Decoder.BASE64Decoder;
import Decoder.BASE64Encoder;

/**
 * 图片编码模块，用于图片与base64编码的转换
 * @author Zhu Ce
 *
 */
public class EncodeModule 
{

	/**
	 * 
	 * @param imageFile 图片文件
	 * @return Base64编码过的字节数组字符串
	 */
	public static String encodeImgageToBase64(File imageFile) {
		 
		 ByteArrayOutputStream outputStream = null;
		    try {
		      BufferedImage bufferedImage = ImageIO.read(imageFile);
		      outputStream = new ByteArrayOutputStream();
		      ImageIO.write(bufferedImage, "jpg", outputStream);
		    } catch (MalformedURLException e1) {
		      e1.printStackTrace();
		    } catch (IOException e) {
		      e.printStackTrace();
		    }
		    BASE64Encoder encoder = new BASE64Encoder();
		    
		    return encoder.encode(outputStream.toByteArray());
	}
	
	
	  /**
	   * 
	   * @param base64 base64码
	   * @return 转换是否成功
	   */
    public static String decodeBase64ToImage(String base64)  
    {     
        if (base64 == null)  
            return ProjectInfomation.FILE_NOT_EXIST_ERROR;  
        BASE64Decoder decoder = new BASE64Decoder();  
        try   
        {  
              
            byte[] b = decoder.decodeBuffer(base64);  
            for(int i=0;i<b.length;++i)  
            {  
                if(b[i]<0)  
                { 
                    b[i]+=256;  
                }  
            }  
              
            String name = "123.jpeg";  
            OutputStream out = new FileOutputStream(ProjectInfomation.OUTPUT_PATH + name);      
            out.write(b);  
            out.flush();  
            out.close();  
            return ProjectInfomation.SUCCESS;  
        }   
        catch (Exception e)   
        {  
            return ProjectInfomation.UNKNOWN_ERROR;  
        }  
    }  
	
	
}
