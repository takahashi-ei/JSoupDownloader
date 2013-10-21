import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.nio.charset.IllegalCharsetNameException;
import java.util.ArrayList;
import java.util.List;

import StaticField.StaticField;


/**
 * ファイルの書き込みのみにする
 * @author kakelab
 *
 */
public class SEOcheckTest {

	public static void main(String[] args) throws IOException, InterruptedException {
		// Waseda-proxy
//		System.setProperty("proxySet", "true");
//		System.setProperty("proxyHost", "www-proxy.waseda.jp");
//		System.setProperty("proxyPort", "8080");

		//担当する文字列は，アジサイ，自転車，自動車，卓球，饅頭
		String query = StaticField.QUERY;	// SEO手法チェックの対象とする検索クエリ
		Boolean g_flag = StaticField.G_FLAG;  // 対象となる検索エンジン指定　true=>google, false=>yahoo
		
		File outFile;
		if(g_flag){
			outFile = new File("SEOチェックdata\\"+query+"_g.csv");
		}else{
			outFile = new File("SEOチェックdata\\"+query+"_y.csv");
		}
		
		outFile.getParentFile().mkdirs();
		PrintWriter out = new PrintWriter( new BufferedWriter(new OutputStreamWriter(new FileOutputStream(outFile), "utf-8")));
		
		BufferedReader br;
		if(g_flag){
			GoogleRankChecker grc = new GoogleRankChecker();
			grc.getUrl(query);
			br = new BufferedReader(new InputStreamReader(new FileInputStream(StaticField.SAVE_FOLDER_NAME + "//"+ query+"_google.txt"), "utf-8"));
		}else{
			YahooRankChecker yrc = new YahooRankChecker();
			yrc.getUrl(query);
			br = new BufferedReader(new InputStreamReader(new FileInputStream(StaticField.SAVE_FOLDER_NAME + "//" + query+"_yahoo.txt"),"utf-8"));
		}

		String line = "";
		int i=1;
		
		//自動的に取得できなかったものを保存する
		//misUrlsはurlを保存，misRankingsは順位を保存
		List<String> misUrls = new ArrayList<String>();
		List<Integer> misRankings = new ArrayList<Integer>();
		while ( ( line = br.readLine() ) != null ){
			File webPage;
			if(g_flag){
				webPage = new File(StaticField.SAVE_FOLDER_NAME +  "\\"+query+"\\google\\"+i+".html");
			}else{
				webPage = new File(StaticField.SAVE_FOLDER_NAME + "\\"+query+"\\yahoo\\"+i+".html");
			}
			webPage.getParentFile().mkdirs();

			System.out.println(i+"\t"+line);
			if( !line.matches("(http|https).*") ){ continue; }

			out.print(i+",");
			try{
				SEOcheckTF.setDoc(line);
			}catch(IllegalCharsetNameException e){
				misUrls.add(line);
				misRankings.add(i);
				out.print("\n");
				i++;
				continue;
			}
			if( SEOcheckTF.isDocNull() ){ misUrls.add(line); misRankings.add(i); out.print("\n"); i++; continue; }
			
			//文字化けに対応するためSEOcheckTFから文字コードを取得する。
			//但し，文字コードが取得できなかったら，自動的に出力できないとする
			if(SEOcheckTF.getLetterCodeFromDoc() == null){
				misUrls.add(line);
				misRankings.add(i);
				out.print("\n");
				i++;
				continue;
			}
		
			PrintWriter pageBak = new PrintWriter( new BufferedWriter(new OutputStreamWriter(new FileOutputStream(webPage), SEOcheckTF.getLetterCodeFromDoc())));
			pageBak.print(SEOcheckTF.getHTML());
			pageBak.close();

			//webのボディの中を全部取得する
			File webBodyPage;
			if(g_flag){
				webBodyPage = new File(StaticField.SAVE_FOLDER_NAME + "\\"+query+"_body\\google\\"+i+".txt");
			}else{
				webBodyPage = new File(StaticField.SAVE_FOLDER_NAME + "\\"+query+"_body\\yahoo\\"+i+".txt");
			}
			webBodyPage.getParentFile().mkdirs();
			PrintWriter pageBodyBak = new PrintWriter( new BufferedWriter(new OutputStreamWriter(new FileOutputStream(webBodyPage), SEOcheckTF.getLetterCodeFromDoc())));
			if(SEOcheckTF.getBody() != null){
			pageBodyBak.print(SEOcheckTF.getBody());
			}
			pageBodyBak.close();
			
			//Webのheadのなかを全部取得する
			File webHeadPage;
			if(g_flag){
				webHeadPage = new File(StaticField.SAVE_FOLDER_NAME + "\\"+query+"_head\\google\\"+i+".html");
			}else{
				webHeadPage = new File(StaticField.SAVE_FOLDER_NAME + "\\"+query+"_head\\yahoo\\"+i+".html");
			}
			webHeadPage.getParentFile().mkdirs();
			PrintWriter pageHeadBak = new PrintWriter( new BufferedWriter(new OutputStreamWriter(new FileOutputStream(webHeadPage), SEOcheckTF.getLetterCodeFromDoc())));
			pageHeadBak.print(SEOcheckTF.getHead());
			pageHeadBak.close();
			i++;
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
		
		br.close();
		out.close();
	}

}
