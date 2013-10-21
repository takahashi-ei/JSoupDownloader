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
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import StaticField.StaticField;


public class JsoupTestNew {

	static Document doc;
	public static void main(String[] args) throws IOException{
		String query = StaticField.QUERY;

		boolean g_flag = StaticField.G_FLAG;
		File outFile;
		//csvでは文字化けをするのでtxtに変える。
		if(g_flag){
			outFile = new File("SEOチェックdata\\"+query+"_g.txt");
		}else{
			outFile = new File("SEOチェックdata\\"+query+"_y.txt");
		}
		
		outFile.getParentFile().mkdirs();

		PrintWriter out = new PrintWriter( new BufferedWriter(new OutputStreamWriter(new FileOutputStream(outFile), "utf-8")));
		doc = null;

		BufferedReader br;
		if(g_flag){
			GoogleRankChecker grc = new GoogleRankChecker();
//			grc.getUrl(query);
			br = new BufferedReader(new InputStreamReader(new FileInputStream(query+"_google.txt"), "utf-8"));
		}else{
			YahooRankChecker yrc = new YahooRankChecker();
			yrc.getUrl(query);
			br = new BufferedReader(new InputStreamReader(new FileInputStream(query+"_yahoo.txt"),"utf-8"));
		}
		int i = 1;
		String line = "";
		
		//excelの上部にどのようなデータを取得するか出力する
		out.print("ranking,");
		for(int j = 0; j < StaticField.SEO_SEARCHERS.length; j++){
			if(j != StaticField.SEO_SEARCHERS.length -1){
			out.print(StaticField.SEO_SEARCHERS[j].getTitleInExcel() + ",");
			}else{
				out.print(StaticField.SEO_SEARCHERS[j].getTitleInExcel());	
			}
		}
		out.print("\n");
		
		while( ( line = br.readLine() ) != null ){
			try {

				for(int j = 0; j < StaticField.MIS_RANKING.length; j++){
					if(StaticField.MIS_RANKING[j] == i){
						i++;
						out.print("\n");
						continue;
					}
				}

				/**文字コード周りを修正する必要あり*/				
				File f = new File(StaticField.LOAD_FOLDER_NAME + "\\"+query+"\\google\\"+i+".html");
				doc = Jsoup.parse(f, "utf-8");
				for(int j = 0; j < StaticField.SEO_SEARCHERS.length; j++){
					StaticField.SEO_SEARCHERS[j].init(doc, i, query, (g_flag ? "google" : "yahoo"));
				}
			} catch (IOException e) {
				// TODO 自動生成された catch ブロック
				out.print("\n");
				i++;
				continue;
			}
			int frame = checkFrame();

			if( frame==1 ){
				out.print(i + ",");
				for(int j = 0; j < StaticField.SEO_SEARCHERS.length; j++){
					if(j != StaticField.SEO_SEARCHERS.length - 1){
					out.print(StaticField.SEO_SEARCHERS[j].getResult() + ",");
					}else{
						out.print(StaticField.SEO_SEARCHERS[j].getResult());
					}
				}
			}else{
				out.print(frame);
			}
			out.print("\n");
			i++;
			System.out.println(i + " is finish");
		}
		
		out.close();
	}

	public static int checkFrame(){
		Elements frames = doc.select("frameset");
		for (Element frame : frames) {
			return 0;
		}
		return 1;
	}
}