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

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import StaticField.StaticField;


public class JsoupTest {

	public static void main(String[] args) throws IOException{
		String query = StaticField.QUERY;

		boolean g_flag = StaticField.G_FLAG;
		File outFile;
		if(g_flag){
			outFile = new File("SEOチェックdata\\"+query+"_g.csv");
		}else{
			outFile = new File("SEOチェックdata\\"+query+"_y.csv");
		}
		
		outFile.getParentFile().mkdirs();

		PrintWriter out = new PrintWriter( new BufferedWriter(new OutputStreamWriter(new FileOutputStream(outFile), "utf-8")));
		Document doc = null;

		BufferedReader br;
		if(g_flag){
			GoogleRankChecker grc = new GoogleRankChecker();
			grc.getUrl(query);
			br = new BufferedReader(new InputStreamReader(new FileInputStream(query+"_google.txt"), "utf-8"));
		}else{
			YahooRankChecker yrc = new YahooRankChecker();
			yrc.getUrl(query);
			br = new BufferedReader(new InputStreamReader(new FileInputStream(query+"_yahoo.txt"),"utf-8"));
		}
		int i = 1;
		String line = "";
		
		while( ( line = br.readLine() ) != null ){
			try {
/*
				if(i == 344){
					i++;
					out.print("\n");
					continue;
				}
*/
				/**文字コード周りを修正する必要あり*/
				SEOcheckTF.setDocByFile(StaticField.LOAD_FOLDER_NAME + "\\"+query+"\\google\\"+i+".html");
			} catch (IOException e) {
				// TODO 自動生成された catch ブロック
				out.print("\n");
				i++;
				continue;
			}
			int frame = SEOcheckTF.checkFrame();

			if( frame==1 ){
				out.print(frame+",");
				out.print(SEOcheckTF.checkHeadCharset()+",");
				out.print(SEOcheckTF.checkHeadLanguage()+",");
				out.print(SEOcheckTF.checkHeadCss()+",");
				out.print(SEOcheckTF.checkHeadJavaScript()+",");
				out.print(SEOcheckTF.removeHeadUnusedTag()+",");
				out.print(SEOcheckTF.countHeadUnusedTag() + ",");
				out.print(SEOcheckTF.checkHeadDescriptionWordCount()+",");
				out.print(SEOcheckTF.checkHeadDescriptionKeyword()+",");
				out.print(SEOcheckTF.checkHeadDescriptionKeywordCount()+",");
				out.print(SEOcheckTF.checkHeadKeywordsCount()+",");
				out.print(SEOcheckTF.checkHeadKeywordsSeparater()+",");
				out.print(SEOcheckTF.checkHeadTitleCount()+",");
				out.print(SEOcheckTF.checkHeadTitleSymble()+",");
				out.print(SEOcheckTF.checkHeadTitleKeywordsPos()+",");
				out.print(SEOcheckTF.checkHeadTitleKeywordsCount()+",");
				out.print(SEOcheckTF.checkImgAlt()+",");
				out.print(SEOcheckTF.checkH1KeywordsPos()+",");
				out.print(SEOcheckTF.checkH1KeywordsCount()+",");
				out.print(SEOcheckTF.checkH1WordCount()+",");
				out.print(SEOcheckTF.checkH1Count()+",");
				out.print(SEOcheckTF.checkHxCount()+",");
				out.print(SEOcheckTF.checkHxStrong()+",");
				out.print(SEOcheckTF.checkHxKeywordsPos()+",");
				out.print(SEOcheckTF.checkStrongCount()+",");
				out.print(SEOcheckTF.checkStrongWordCount()+",");
//				out.print(SEOcheckTF.checkStrongBasicWord()+",");
				out.print(SEOcheckTF.removeStrongInH1() + ",");
				out.print(SEOcheckTF.getHtmlWeight(i, query, (g_flag ? "google" : "yahoo")) + ",");
				out.print(SEOcheckTF.getHeadWeight(i, query,(g_flag ? "google" : "yahoo")) + ",");
				out.print(SEOcheckTF.getBodyWeight(i, query,(g_flag ? "google" : "yahoo")) + ",");
				out.print(SEOcheckTF.getHeadParcent(i,query,(g_flag ? "google" : "yahoo")) + ",");
				out.print(SEOcheckTF.getBodyParcent(i,query,(g_flag ? "google" : "yahoo")) + ",");
				out.print(SEOcheckTF.getTagInBody(i,query,(g_flag ? "google" : "yahoo")) + ",");
				out.print(SEOcheckTF.getCountForKeywords());
	//			System.out.println(SEOcheckTF.getHeadCharset());
			}else{
				out.print(frame);
			}
			out.print("\n");
			i++;
			System.out.println(i + " is finish");
			SEOcheckTF.clear();
		}
		
		out.close();
	}

}