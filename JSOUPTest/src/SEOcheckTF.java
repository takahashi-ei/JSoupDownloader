import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.net.ssl.HttpsURLConnection;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import StaticField.StaticField;


public class SEOcheckTF{
	private static Document doc;
	private static String html;
	private static String mojiCode;


	// URLからHTML解析
	public static void setDoc(String openUrl){
		String userAgent = "Mozilla/5.0 (Windows NT 6.1; rv:23.0) Gecko/20100101 Firefox/23.0";
/*
		BufferedInputStream bis = null;
		BufferedOutputStream bos = null;
		try {
			bos = new BufferedOutputStream(new FileOutputStream("temp.html"));
			URL url = new URL(openUrl);
			if( openUrl.matches("https") ){
				HttpsURLConnection  http = (HttpsURLConnection)url.openConnection();
				http.setRequestMethod("GET");
				http.setRequestProperty("User-Agent", userAgent);
				http.connect();
				bis = new BufferedInputStream(http.getInputStream());
			}else{
				HttpURLConnection  http = (HttpURLConnection) url.openConnection();
				http.setRequestMethod("GET");
				http.setRequestProperty("User-Agent", userAgent);
				http.connect();
				bis = new BufferedInputStream(http.getInputStream());
			}

			while ( true ){
				byte[] buf = new byte[1024];
				int len = bis.read(buf);
				if( len == -1 ){ break; }
				bos.write(buf, 0, len);
				bos.flush();
			}
			bis.close();
			bos.close();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
*/
		try {
			doc = Jsoup.connect(openUrl).userAgent(userAgent).timeout(0).get();
			} catch (IOException e) {
			e.printStackTrace();
		}

/*
		doc = setDoc();
*/
	}

/**
 * ファイル名からdocを設定する
 * @param file
 */
	public static void setDocByFile(String file) throws IOException{
		File f = new File(file);
			doc = Jsoup.parse(f, "utf-8");
	}
	
	// ファイルからHTML解析
	public static void setDoc(File file){
		mojiCode = getMojiCode(file);

		try {
			doc = Jsoup.parse(file, mojiCode);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}


	// 文字コード判定
	public static String getMojiCode(File file){
		byte[] buf = new byte[4096];
		byte[] rebuf = new byte[4096];
		boolean flag = true;

		try {
			FileInputStream fis = new FileInputStream(file);
			fis.read(buf);
			fis.close();

			// JIS判定
			flag = true;
			rebuf = (new String(buf,"JIS")).getBytes("JIS");
			for( int i = 0 ; i < buf.length ; i++ ){
				if( buf[i] != rebuf[i] ){ flag=false; break; }
			}
			if( flag==true ){ return "JIS"; }

			// SJIS判定
			flag = true;
			rebuf = (new String(buf,"Shift_JIS")).getBytes("Shift_JIS");
			for( int i = 0 ; i < buf.length ; i++ ){
				if( buf[i] != rebuf[i] ){ flag=false; break; }
			}
			if( flag==true ){ return "Shift_JIS"; }

			// UTF-8判定
			flag = true;
			rebuf = (new String(buf,"UTF-8")).getBytes("UTF-8");
			for( int i = 0 ; i < buf.length ; i++ ){
				if( buf[i] != rebuf[i] ){ flag=false; break; }
			}
			if( flag==true ){ return "UTF-8"; }

			// EUC_JP判定
			flag = true;
			rebuf = (new String(buf,"EUC-JP")).getBytes("EUC-JP");
			for( int i = 0 ; i < buf.length ; i++ ){
				if( buf[i] != rebuf[i] ){ flag=false; break; }
			}
			if( flag==true ){ return "EUC-JP"; }

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "unknown";
	}


	// 結果のHTMLソースを取得
	public static String getHTML(){
		//return html;
		return doc.html();
	}


	// クリア
	public static void clear(){
		doc = null;
	}


	// nullチェック
	public static boolean isDocNull(){
		if( doc == null ){ return true; } else{ return false; }
	}



	// head内に文字コード指定のタグ
	public static int checkHeadCharset(){
		Element head = doc.head();
		Elements metaCharset = head.select("meta[charset], meta[http-equiv=Content-Type]");
		if( metaCharset.isEmpty() ){
			return 0;
		}
		return 1;
	}

	// head内に文字コード指定のタグ
	public static String getHeadCharset(){
		Element head = doc.head();
		Elements metaCharset = head.select("meta[charset], meta[http-equiv=Content-Type]");
//		System.out.println(metaCharset.toString());
		return metaCharset.toString();
	}

	// head内に言語指定のタグ
	public static int checkHeadLanguage(){
		Element head = doc.head();
		Elements metaLanguage = head.select("meta[http-equiv=Content-Language]");
		if( metaLanguage.isEmpty() ){
			return 0;
		}
		return 1;
	}


	// head内にスタイルシート指定のタグ
	public static int checkHeadCss(){
		Element body = doc.body();
		Elements style = body.select("[style]");

		Element head = doc.head();
		Elements metaCss = head.select("meta[http-equiv=Content-Style-Type]");

		if( !style.isEmpty() && metaCss.isEmpty() ){
			return 0;
		}else if( style.isEmpty() && !metaCss.isEmpty() ){
			return 0;
		}
		return 1;
	}


	// head内にJavaScript指定のタグ
	public static int checkHeadJavaScript(){
		Element body = doc.body();
		Elements event = body.select("[^on]");

		Element head = doc.head();
		Elements metaJs = head.select("meta[http-equiv=Content-Script-Type]");

		if( !event.isEmpty() && metaJs.isEmpty() ){
			return 0;
		}else if( event.isEmpty() && !metaJs.isEmpty() ){
			return 0;
		}
		return 1;
	}


	// head内の不要タグを削除
	public static int removeHeadUnusedTag(){
		Element head = doc.head();

		Elements metaTag = head.select("meta[name=GENERATOR]");
		if( !metaTag.isEmpty() ){
			return 0;
		}

		metaTag = head.select("meta[name=author]");
		if( !metaTag.isEmpty() ){
			return 0;
		}

		metaTag = head.select("meta[name=copyright]");
		if( !metaTag.isEmpty() ){
			return 0;
		}

		metaTag = head.select("meta[name=robots]");
		if( !metaTag.isEmpty() ){
			return 0;
		}

		metaTag = head.select("meta[name=made]");
		if( !metaTag.isEmpty() ){
			return 0;
		}

		metaTag = head.select("meta[name=rating]");
		if( !metaTag.isEmpty() ){
			return 0;
		}

		metaTag = head.select("meta[name=revisit_after]");
		if( !metaTag.isEmpty() ){
			return 0;
		}

		return 1;
	}

	public static int countHeadUnusedTag(){
		Element head = doc.head();
		int count = 0;
		Elements metaTag = head.select("meta[name=GENERATOR]");
		if( !metaTag.isEmpty() ){
			count+=1;
		}

		metaTag = head.select("meta[name=author]");
		if( !metaTag.isEmpty() ){
			count+=1;
		}

		metaTag = head.select("meta[name=copyright]");
		if( !metaTag.isEmpty() ){
			count+=1;
		}

		metaTag = head.select("meta[name=robots]");
		if( !metaTag.isEmpty() ){
			count+=1;
		}

		metaTag = head.select("meta[name=made]");
		if( !metaTag.isEmpty() ){
			count+=1;
		}

		metaTag = head.select("meta[name=rating]");
		if( !metaTag.isEmpty() ){
			count+=1;
		}

		metaTag = head.select("meta[name=revisit_after]");
		if( !metaTag.isEmpty() ){
			count+=1;
		}	
		return count;
	}
	
	// descriptionの文字数をチェック
	public static int checkHeadDescriptionWordCount(){
		Element head = doc.head();
		Elements metaDescription = head.select("meta[name=description]");
		String description = metaDescription.attr("content");
		if( description.length() >= 110 ){
			return 0;
		}else if( description.length() <= 60 ){
			return 0;
		}
		return 1;
	}


	// descriptionの前方にキーワードがあるか
	public static int checkHeadDescriptionKeyword() {
		Element head = doc.head();
		Elements metaDescription = head.select("meta[name=description]");
		String description = metaDescription.attr("content");

		Elements metaKeywords = head.select("meta[name=keywords]");
		String[] keywords = metaKeywords.attr("content").split(",");

		boolean flag = false;
		for( String keyword : keywords ){
			int index = description.indexOf(keyword);
			if( index != -1 && index < description.length()/2 ){ flag = true; break; }
		}

		// descriptionの前半にkeywordsが１つも入っていなかったら警告
		if( !flag ){
			return 0;
		}
		return 1;
	}


	// descriptionでキーワードが３回以上使われていないか
	public static int checkHeadDescriptionKeywordCount() {
		Element head = doc.head();
		Elements metaDescription = head.select("meta[name=description]");
		String description = metaDescription.attr("content");

		Elements metaKeywords = head.select("meta[name=keywords]");
		String[] keywords = metaKeywords.attr("content").split(",");

		for( String keyword : keywords ){
			int sum = 0;

			keyword = keyword.replace("¥", "\\");
			keyword = keyword.replace("*", "\\*");
			keyword = keyword.replace("+", "\\+");
			keyword = keyword.replace(".", "\\.");
			keyword = keyword.replace("?", "\\?");
			keyword = keyword.replace("|", "\\|");
			keyword = keyword.replace("-", "\\-");
			keyword = keyword.replace("$", "\\$");
			keyword = keyword.replace("^", "\\^");
			keyword = keyword.replace("]", "\\]");
			keyword = keyword.replace("[", "\\[");
			keyword = keyword.replace(")", "\\)");
			keyword = keyword.replace("(", "\\(");
			keyword = keyword.replace("}", "\\}");
			keyword = keyword.replace("{", "\\{");
			keyword = keyword.replace("?", "\\?");

			Pattern p = Pattern.compile(keyword);
			Matcher m = p.matcher(description);
			while(m.find()){
				sum++;
			}

			if( sum>2 ){
				return 0;
			}
		}
		return 1;
	}


	// keywordsの個数をチェック
	public static int checkHeadKeywordsCount(){
		Element head = doc.head();
		Elements metaKeywords = head.select("meta[name=keywords]");
		String[] keywords = metaKeywords.attr("content").split(",");
		if( keywords.length > 5 ){
			return 0;
		}
		return 1;
	}


	// keywordsをカンマ区切りにする
	public static int checkHeadKeywordsSeparater(){
		Element head = doc.head();
		Elements metaKeywords = head.select("meta[name=keywords]");

		
		String[] keywords = metaKeywords.attr("content").split("[、．.。・]");
		if( keywords.length > 1 ){
			return 0;
		}

		return 1;
	}


	// titleの文字数チェック
	public static int checkHeadTitleCount(){
		Element head = doc.head();
		Elements title = head.select("title");
		String titleText = title.text();
		if( titleText.length() > 31 ){
			return 0;
		}
		return 1;
	}


	// titleに記号が含まれているか
	public static int checkHeadTitleSymble(){
		Element head = doc.head();
		Elements title = head.select("title");
		String titleText = title.text();

		if( titleText.matches(".*[!\"#\\$%&'\\(\\)=~`\\{\\+\\*\\}<>\\?_\\-\\^\\\\@\\[;:\\],\\./" + "！”＃＄％＆’（）＝～‘｛＋＊｝＜＞？＿－＾￥＠「；：」、。・].*") ){
			return 0;
		}
		return 1;
	}


	// titleの前部にキーワードがあるか
	public static int checkHeadTitleKeywordsPos() {
		Element head = doc.head();
		Elements title = head.select("title");
		String titleText = title.text();

		Elements metaKeywords = head.select("meta[name=keywords]");
		String[] keywords = metaKeywords.attr("content").split(",");

		boolean flag = false;
		for( String keyword : keywords ){
			int index = titleText.indexOf(keyword);
			if( index != -1 && index < titleText.length()/2 ){ flag = true; break; }
		}

		// titleの前半にkeywordsが１つも入っていなかったら警告
		if( !flag ){
			return 0;
		}
		
		return 1;
	}


	// titleに含まれるキーワード値のチェック
	public static int checkHeadTitleKeywordsCount(){
		Element head = doc.head();
		Elements title = head.select("title");
		String titleText = title.text();

		Elements metaKeywords = head.select("meta[name=keywords]");
		String[] keywords = metaKeywords.attr("content").split(",");

		// titleに含まれるキーワードの個数をカウント
		int sum = 0;
		for( String keyword : keywords ){
			keyword = keyword.replace("¥", "\\");
			keyword = keyword.replace("*", "\\*");
			keyword = keyword.replace("+", "\\+");
			keyword = keyword.replace(".", "\\.");
			keyword = keyword.replace("?", "\\?");
			keyword = keyword.replace("|", "\\|");
			keyword = keyword.replace("-", "\\-");
			keyword = keyword.replace("$", "\\$");
			keyword = keyword.replace("^", "\\^");
			keyword = keyword.replace("]", "\\]");
			keyword = keyword.replace("[", "\\[");
			keyword = keyword.replace(")", "\\)");
			keyword = keyword.replace("(", "\\(");
			keyword = keyword.replace("}", "\\}");
			keyword = keyword.replace("{", "\\{");
			keyword = keyword.replace("?", "\\?");

			Pattern p = Pattern.compile(keyword);
			Matcher m = p.matcher(titleText);
			while(m.find()){
				sum++;
			}
		}

		// （titleタグ内で使ているキーワードの個数） /（titleタグ内のキーワードの種類） ×2
		double cal = sum * 2.0 / keywords.length;
		if( cal < 0.5 ){
			return 0;
		}else if( cal > 0.75 ){
			return 0;
		}
		return 1;
	}


	// imgタグにaltが設定されてないものを検出してみる
	public static int checkImgAlt(){
		Elements imgs = doc.select("img");
		for (Element img : imgs) {
			// alt=""の場合は検出できない方法
			if( !img.hasAttr("alt") ){
				return 0;
			}

			// alt=""の場合も検出できる方法
			String alt_text = img.attr("alt");
			if( alt_text.isEmpty() ){
				return 0;
			}
		}
		
		return 1;
	}


	// h1の前部にキーワードがあるか
	public static int checkH1KeywordsPos() {
		Element body = doc.body();
		Elements h1s = body.select("h1");

		Element head = doc.head();
		Elements metaKeywords = head.select("meta[name=keywords]");
		String[] keywords = metaKeywords.attr("content").split(",");

		for( Element h1 : h1s ){
			boolean flag = false;
			for( String keyword : keywords ){
				int index = h1.text().indexOf(keyword);
				if( index != -1 && index < h1.text().length()/2 ){ flag = true; break; }
			}
			
			// h1の前半にkeywordsが１つも入っていなかったら警告
			if( !flag ){
				return 0;
			}
		}
		return 1;
	}

	public static String getHead(){
		Element e = doc.head();
		return /*Jsoup.parse(e.toString()).html()*/ e.toString();
	}

	public static String getBody(){
//		System.out.println(doc.body().text().toString());
		Element body = doc.body();
		if(body == null){
			return null;
		}
		return body.text();
	}

	// １つのh1タグで同じキーワードは１回だけ
	public static int checkH1KeywordsCount() {
		Element body = doc.body();
		Elements h1s = body.select("h1");

		Element head = doc.head();
		Elements metaKeywords = head.select("meta[name=keywords]");
		String[] keywords = metaKeywords.attr("content").split(",");

		for( Element h1 : h1s ){
			for( String keyword : keywords ){
				int sum = 0;
				keyword = keyword.replace("¥", "\\");
				keyword = keyword.replace("*", "\\*");
				keyword = keyword.replace("+", "\\+");
				keyword = keyword.replace(".", "\\.");
				keyword = keyword.replace("?", "\\?");
				keyword = keyword.replace("|", "\\|");
				keyword = keyword.replace("-", "\\-");
				keyword = keyword.replace("$", "\\$");
				keyword = keyword.replace("^", "\\^");
				keyword = keyword.replace("]", "\\]");
				keyword = keyword.replace("[", "\\[");
				keyword = keyword.replace(")", "\\)");
				keyword = keyword.replace("(", "\\(");
				keyword = keyword.replace("}", "\\}");
				keyword = keyword.replace("{", "\\{");
				keyword = keyword.replace("?", "\\?");

				Pattern p = Pattern.compile(keyword);
				Matcher m = p.matcher(h1.text());
				while(m.find()){
					sum++;
				}
				if( sum > 1 ){
					return 0;
				}
			}
		}
		return 1;
	}


	// h1タグ内の文字数を多くしすぎない
	public static int checkH1WordCount() {
		Element body = doc.body();
		Elements h1s = body.select("h1");

		for( Element h1 : h1s ){
			if( h1.text().length() > 30 ){
				return 0;
			}
		}
		return 1;
	}


	// h1タグは１つのページで１つまで
	public static int checkH1Count() {
		Element body = doc.body();
		Elements h1s = body.select("h1");

		if( h1s.size() > 1 ){
			return 0;
		}
		return 1;
	}


	// hxタグを多用しない
	public static int checkHxCount() {
		Element body = doc.body();
		Elements h2s = body.select("h2");

		if( h2s.size() > 2 ){
			return 0;
		}

		for( int i = 3 ; i<=6 ; i++ ){
			Elements hxs = body.select("h"+i);
			if( hxs.size() > 5 ){
				return 0;
			}
		}
		return 1;
	}


	// hxとstrongの併用はやめる
	public static int checkHxStrong() {
		Elements hxStrong = doc.select("h2 strong,h3 strong,h4 strong,h5 strong,h6 strong");
		for (Element strong : hxStrong) {
			return 0;
		}
		return 1;
	}


	// hxの前部にキーワードがあるか
	public static int checkHxKeywordsPos() {
		Element body = doc.body();
		Elements hxs = body.select("h2,h3,h4,h5,h6");

		Element head = doc.head();
		Elements metaKeywords = head.select("meta[name=keywords]");
		String[] keywords = metaKeywords.attr("content").split(",");

		for( Element hx : hxs ){
			boolean flag = false;
			for( String keyword : keywords ){
				int index = hx.text().indexOf(keyword);
				if( index != -1 && index < hx.text().length()/2 ){ flag = true; break; }
			}
			
			// hxの前半にkeywordsが１つも入っていなかったら警告
			if( !flag ){
				return 0;
			}
		}
		return 1;
	}


	// strongタグの回数
	public static int checkStrongCount(){
		Elements strongs = doc.select("strong");

		if( strongs.size() > 2 ){
			return 0;
		}
		return 1;
	}


	// 同じ単語を複数回strongしない
	public static int checkStrongWordCount(){
		Set<String> strongWords = new HashSet<String>();
		Elements strongs = doc.select("strong");
		for (Element strong : strongs) {
			if( strongWords.contains(strong.text()) ){
				return 0;
			}else{
				strongWords.add(strong.text());
			}
		}
		return 1;
	}


/*
	// strongは単語のみをくくる
	public void checkStrongBasicWord(){
		Elements strongs = doc.select("strong");
		for (Element strong : strongs) {
			List<Token> tokens = tokenizer.tokenize(strong.text());

			if( tokens.size() > 1 ){
				messageList.add(new Message("【警告】strongタグは単語のみをくくるようにしましょう", strong.outerHtml()));
			}
		}
	}
*/


	// h1タグの中のstrongタグを削除
	public static int removeStrongInH1(){
		Elements h1Strong = doc.select("h1 strong");
		for (Element strong : h1Strong) {
			return 0;
		}
		return 1;
	}


	// フレームが使用されているかチェック
	public static int checkFrame(){
		Elements frames = doc.select("frameset");
		for (Element frame : frames) {
			return 0;
		}
		return 1;
	}
	
	/**
	 * HTMLファイル全体を比較してheadタグの大きさを返す
	 * @return
	 */
	public static double getHeadParcent(int i, String query, String engine){
		return ((double)getHeadWeight(i, query,engine) / getHtmlWeight(i, query, engine)) * 100;
	}
	
	/**
	 * htmlファイルのheadタグの大きさを返す
	 * headタグのみのファイルサイズを取得する
	 * @param i 検索順位
	 * @param query 検索クエリ
	 * @return 引数となる検索クエリを投げたときに検索順位i番目のheadタグのみのファイルサイズを返す
	 */
	public static long getHeadWeight(int i,String query,String engine){
		File headFile = new File(StaticField.LOAD_FOLDER_NAME + "\\"+query+"_head\\" + engine + "\\"+i+".html");
		return headFile.length();
	}
	
	/**
	 * htmlファイル内のbodyのタグなしの大きさを返す
	 * @param i　検索順位
	 * @param query 検索クエリ
	 * @return 引数となる検索クエリを投げたときに検索順位i番目のbodyタグのみのファイルサイズを返す
	 */
	public static long getBodyWeight(int i,String query,String engine){
		File bodyFile = new File(StaticField.LOAD_FOLDER_NAME + "\\"+query+"_body\\" + engine + "\\"+i+".txt");
		return bodyFile.length();
	}
	
	/**
	 * htmlファイルの大きさを返す
	 * @param i 検索順位
	 * @param query 検索クエリ
	 * @param engine 検索に使ったengine(Google,Yahoo)
	 * @return 引数となる検索クエリをなげたときに検索順位i番目のhtmlファイルの大きさを返す
	 */
	public static long getHtmlWeight(int i,String query,String engine){
		File allFile = new File(StaticField.LOAD_FOLDER_NAME + "\\"+query+"\\"+engine+"\\"+i+".html");
		return allFile.length();
	}

	/**
	 * HTMLファイル全体を比較して本文テキストの大きさを返す
	 * @return
	 */
	public static double getBodyParcent(int i, String query, String engine){

		return ((double)getBodyWeight(i, query,engine) / getHtmlWeight(i, query, engine)) * 100;
	}
	
	/**
	 * headのmeta keywordsに書かれたキーワードの個数を返す
	 * @return
	 */
	public static int getCountForKeywords(){
		Element head = doc.head();
		Elements metaKeywords = head.select("meta[name=keywords]");
		String[] keywords = metaKeywords.attr("content").split(",");
		return keywords.length;
	}
	
	/**
	 * headのmeta keywordsに書かれたキーワードの文字を返す
	 * @return
	 */
	public static String getkeywords(){
		String returnValue = "";
		Element head = doc.head();
		Elements metaKeywords = head.select("meta[name=keywords]");
		String[] keywords = metaKeywords.attr("content").split(",");
		for(int i = 0; i < keywords.length; i++){
			returnValue += keywords[i] + "|";
		}
		return returnValue;		
	}
	
	/**
	 * JSOUPの構造が入ったDocを使って文字コードを判定する
	 * 判定方法は,metaタグを全てとってきてその中の文字コードが書かれている部分から判定する
	 * @return JIS,Shift_JIS,UTF-8,EUC-JP
	 */
	public static String getLetterCodeFromDoc(){
	    Elements tagElements = doc.getElementsByTag("meta");
	    int i = 0;
	    for(Element e : tagElements){
	    	//文字コードが書かれているか判定する
	    	if(isContainLetterCode(e)){
	    		//文字コードを返す
	    		return getContainLetterCode(e);
	    	}
	    }
	    return null;
	}
	/**
	 * 引数のmetaタグ内に,文字コードがかかれているか調べる
	 * 判定方法はhtmlでは"Content-Type"が含まれたmetaタグ内で文字コードを指定するので、
	 * "Content-Type"(小文字にしたものも含める)が含まれているかどうか
	 * "meta charset="が含まれているかどうか
	 * @param e
	 * @return 文字コードが書かれている True,かかれていない false;
	 * 
	 */
	private static boolean isContainLetterCode(Element metaE){
		return metaE.toString().contains("Content-Type") ||
			   metaE.toString().contains("Content-Type") ||
			   metaE.toString().contains("content-type") ||
			   metaE.toString().contains("Content-type") ||
			   metaE.toString().contains("meta charset=");
	}
	
	/**
	 * 引数のmetaタグから,文字コードを取得する
	 * 方法は,metaタグに文字コードとなる文字列が含まれているかで判定する
	 * @param e
	 * @return
	 */
	private static String getContainLetterCode(Element e){
		if(e.toString().contains(StaticFieldForLetterCode.UTF_8)||
		   e.toString().contains(StaticFieldForLetterCode.SMALL_UTF_8)){
			return StaticFieldForLetterCode.UTF_8;
		}else if(e.toString().contains(StaticFieldForLetterCode.SHIFT_JIS) ||
				 e.toString().contains(StaticFieldForLetterCode.SMALL_SHIFT_JIS) ||
				 e.toString().contains(StaticFieldForLetterCode.X_SJIS) ||
				 e.toString().contains(StaticFieldForLetterCode.LARGE_SHIFT_JIS) ||
				 e.toString().contains(StaticFieldForLetterCode.RIGHT_LARGE_SHIFT_JIS ) ||
				 e.toString().contains(StaticFieldForLetterCode.RIGHT_LARGE_SHIFT_JIS2 ) ||
				 e.toString().contains(StaticFieldForLetterCode.ALL_LARGE_SHIFT_JIS ) ||
				 e.toString().contains(StaticFieldForLetterCode.ALL_LARGE_SHIFT_JIS2 ) ||
				 e.toString().contains(StaticFieldForLetterCode.SMALL_AND_LINE_SHIFT_JIS) ||
				 e.toString().contains(StaticFieldForLetterCode.SMALL_X_SJIS)){
			return StaticFieldForLetterCode.SHIFT_JIS;
		}else if(e.toString().contains(StaticFieldForLetterCode.EUC_JP) ||
				 e.toString().contains(StaticFieldForLetterCode.SMALL_EUC_JP)){
			return StaticFieldForLetterCode.EUC_JP;
		}
		return null;
	}
	
	public static double getTagInBody(int i, String query, String engine){

		return 100 - (getBodyParcent(i, query, engine) + getHeadParcent(i, query, engine));
	}
}
