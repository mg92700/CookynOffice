package com.general.service;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;

import javax.imageio.ImageIO;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import com.general.dto.UserDto;

@Component
public class ServiceImageFtp {
	
	
	private static final long TICKS_AT_EPOCH = 621355968000000000L;
    private static final long TICKS_PER_MILLISECOND = 10000;

	
    @Async
	public String resultat(String userName,String libelleRecette,byte[] images)throws IOException
	{
		// long ticks = 634200192000000000L;
		
		Date date = new Date();
		String url=userName+libelleRecette+getUTCTicks(date)+".png";
		//BufferedImage img = ImageIO.read(new ByteArrayInputStream(bytes));
		String server = "ftp.cluster023.hosting.ovh.net";
		int port = 21;
		
		
		

		
		Configuration uneConfig = new Configuration();
		Map<String, String> mapConfig  = null;
    	try {
			mapConfig = uneConfig.GetConfiguration();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
    	
		String user = mapConfig.get("loginFtp");
		String pass = mapConfig.get("passwordFtp");

        FTPClient ftpClient = new FTPClient();
        try 
        {
 
            ftpClient.connect(server, port);
            ftpClient.login(user, pass);
            ftpClient.enterLocalPassiveMode();
            ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
 
            
            String urlServer=GetPathServer(userName,libelleRecette,images);
           
            // Approche 1: upload d'un fichier en utilisant InputStream
            File file = new File(urlServer);

            String chemin = "/Cookyn/";
            InputStream inputStream = new FileInputStream(file);

            urlServer=urlServer.replaceAll(" ", "");
            System.out.println("Début de l'upload");
      
            InputStream input = new FileInputStream(new File(urlServer)) ;
            boolean res=ftpClient.storeFile(chemin + url, input);
            
            //fermet le flut de lecture
            inputStream.close();
            
            if (res==true) 
            {
              System.out.println("Le fichier "+chemin+" a été transféré avec succès");
            }
            
            
            System.out.println("Le fichier "+chemin+" a été supprimé avec succès");
            
            file.delete();
        } 
        catch (IOException ex) 
        {
            System.out.println("Error: " + ex.getMessage());
            ex.printStackTrace();
        } 
        finally 
        {
            try {
                if (ftpClient.isConnected()) {
                    ftpClient.logout();
                    ftpClient.disconnect();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        System.out.println("http://khaktus.fr/Cookyn/"+url);
        return "http://khaktus.fr/Cookyn/"+url;
		
	 }
    
    private static String GetPathServer(String userName,String libelleRecette,byte[] images) throws IOException
    {
   //"/Users/mouhsin/Documents/"
    	Date date = new Date();
		String path=userName+libelleRecette+getUTCTicks(date)+".png";
		path=path.replaceAll(" ", "");
		BufferedImage img = ImageIO.read(new ByteArrayInputStream(images));
        File outputfile = new File(path);
        ImageIO.write(img, "jpg", outputfile);

    	return path;
    	
    }
    
    public static long getUTCTicks(Date date){

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);

        return (calendar.getTimeInMillis() * TICKS_PER_MILLISECOND) + TICKS_AT_EPOCH;

    }
	

}