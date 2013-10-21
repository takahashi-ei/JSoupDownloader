import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.nio.charset.IllegalCharsetNameException;
import java.util.ArrayList;
import java.util.List;

import StaticField.StaticField;


public class ChangeMissUrlToFile {
	public static void main(String[] args) throws IOException, InterruptedException {
		//ミスしたurlを入れるために使う
		List<String> urls = new ArrayList<String>();
		//ランキングを入れておくために使う
		List<Integer> ranking = new ArrayList<Integer>();
//		List<String> letterCode = new ArrayList<String>();
		
		List<String> misUrls = new ArrayList<String>();
		List<Integer> misRankings = new ArrayList<Integer>();
		
		boolean g_flag = StaticField.G_FLAG;
		String query = StaticField.QUERY;
		BufferedReader br = null;
		
		try {
			if(g_flag){
				br = new BufferedReader(new InputStreamReader(new FileInputStream(StaticField.LOAD_FOLDER_NAME + "\\" + query+"_g_miss.txt"), "utf-8"));
			}else{
				br = new BufferedReader(new InputStreamReader(new FileInputStream(StaticField.LOAD_FOLDER_NAME + "\\" + query+"_y_miss.txt"), "utf-8"));
			}
		} catch (UnsupportedEncodingException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}
		String line = "";
		while( ( line = br.readLine() ) != null){
			ranking.add(Integer.valueOf(line.substring(0,line.indexOf(" "))));
			urls.add(line.substring(line.indexOf(" ")+1,line.length()));

//			System.out.println(line.substring(0,line.indexOf(" ")) + " " + line.substring(line.indexOf(" ")+1,line.length()));
		}
		
		for(int i = 0; i < ranking.size(); i++){
//			System.out.println(misUrls.get(i) + " " + misUrls.get(i).indexOf(".pdf"));
			if(urls.get(i).indexOf(".pdf") != -1){
				System.out.println(ranking.get(i) + " " + urls.get(i));
		//		i++;
				continue;
			}
			try{
				SEOcheckTF.setDoc(urls.get(i));
			}catch(IllegalCharsetNameException e){
				misUrls.add(urls.get(i));
				misRankings.add(ranking.get(i));
			//	i++;
				continue;
			}

			
			File webPage;
			if(g_flag){
				webPage = new File(StaticField.SAVE_FOLDER_NAME + "\\"+query+"\\google\\"+ ranking.get(i)+".html");
			}else{
				webPage = new File(StaticField.SAVE_FOLDER_NAME + "\\"+query+"\\yahoo\\"+ ranking.get(i)+".html");
			}
			webPage.getParentFile().mkdirs();
			PrintWriter pageBak;
			try{
				pageBak = new PrintWriter( new BufferedWriter(new OutputStreamWriter(new FileOutputStream(webPage), "utf-8")));
			}catch(UnsupportedEncodingException e){
				misUrls.add(urls.get(i));
				misRankings.add(ranking.get(i));
				continue;
			}
			pageBak.print(SEOcheckTF.getHTML());
			pageBak.close();

			//webのボディの中を全部取得する
			File webBodyPage;
			if(g_flag){
				webBodyPage = new File(StaticField.SAVE_FOLDER_NAME + "\\"+query+"_body\\google\\"+ranking.get(i)+".txt");
			}else{
				webBodyPage = new File(StaticField.SAVE_FOLDER_NAME + "\\"+query+"_body\\yahoo\\"+ranking.get(i)+".txt");
			}
			webBodyPage.getParentFile().mkdirs();
			PrintWriter pageBodyBak = new PrintWriter( new BufferedWriter(new OutputStreamWriter(new FileOutputStream(webBodyPage), "utf-8")));
			if(SEOcheckTF.getBody() != null){
			pageBodyBak.print(SEOcheckTF.getBody());
			}
			pageBodyBak.close();
			
			//Webのheadのなかを全部取得する
			File webHeadPage;
			if(g_flag){
				webHeadPage = new File(StaticField.SAVE_FOLDER_NAME + "\\"+query+"_head\\google\\"+ranking.get(i)+".html");
			}else{
				webHeadPage = new File(StaticField.SAVE_FOLDER_NAME + "\\"+query+"_head\\yahoo\\"+ranking.get(i)+".html");
			}
			webHeadPage.getParentFile().mkdirs();
			PrintWriter pageHeadBak = new PrintWriter( new BufferedWriter(new OutputStreamWriter(new FileOutputStream(webHeadPage), "utf-8")));
			pageHeadBak.print(SEOcheckTF.getHead());
			pageHeadBak.close();
			
			System.out.println("finish " + ranking.get(i));
		}

		File misFile;
		if(g_flag){
			misFile = new File("SEOチェックdata\\"+query+"_g_miss.txt");
		}else{
			misFile = new File("SEOチェックdata\\"+query+"_y_miss.txt");
		}
		misFile.getParentFile().mkdirs();
		PrintWriter missBak = new PrintWriter( new BufferedWriter(new OutputStreamWriter(new FileOutputStream(misFile), "utf-8")));
		for(int j = 0;j < misRankings.size(); j++){
			missBak.print(misRankings.get(j) + " " + misUrls.get(j));
			missBak.print("\n");
		}
		missBak.close();
	}

	
	
	
}
