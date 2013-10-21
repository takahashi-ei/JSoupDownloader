package actionWithDifferentRanking;

import java.io.File;
import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import StaticField.StaticFieldForDifferentRanking;


public abstract class ActionWithDifferentRankingImp {
	File beforeAllHtml;
	File afterAllHtml;
	File beforeBody;
	File afterBody;
	File beforeHead;
	File afterHead;
	int beforeRank;
	int afterRank;
	Document beforeDoc;
	Document afterDoc;
	
	public void init(int beforeRank,int afterRank,String query){
		this.beforeRank = beforeRank;
		this.afterRank = afterRank;
		String engine = StaticFieldForDifferentRanking.G_FLAG ? "google" : "yahoo";
		this.beforeAllHtml = new File(StaticFieldForDifferentRanking.ONE_FOLDER + File.separatorChar +
				              query + File.separatorChar +
				              engine + File.separatorChar +
				              beforeRank + ".html"
				              );
		System.out.println(StaticFieldForDifferentRanking.ONE_FOLDER + File.separatorChar +
	              query + File.separatorChar +
	              engine + File.separatorChar +
	              beforeRank + ".html");
		this.afterAllHtml =  new File(StaticFieldForDifferentRanking.OTHER_FOLDER + File.separatorChar +
	              query + File.separatorChar +
	              engine + File.separatorChar +
	              afterRank + ".html"
	              );
		
		System.out.println(StaticFieldForDifferentRanking.OTHER_FOLDER + File.separatorChar +
	              query + File.separatorChar +
	              engine + File.separatorChar +
	              afterRank + ".html");
		this.beforeBody = new File(StaticFieldForDifferentRanking.ONE_FOLDER + File.separatorChar +
	              query + "_body" + File.separatorChar +
	              engine + File.separatorChar +
	              beforeRank + ".txt"
	              );
		this.afterBody = new File(StaticFieldForDifferentRanking.OTHER_FOLDER + File.separatorChar +
	             query + "_body" + File.separatorChar +
	              engine + File.separatorChar +
	              beforeRank + ".txt"
	              );
		this.beforeHead = new File(StaticFieldForDifferentRanking.ONE_FOLDER + File.separatorChar +
	              query + "_head" + File.separatorChar +
	              engine + File.separatorChar +
	              beforeRank + ".html"
	              );
		this.afterHead = new File(StaticFieldForDifferentRanking.OTHER_FOLDER + File.separatorChar +
	              query + "_head" + File.separatorChar +
	              engine + File.separatorChar +
	              beforeRank + ".html"
	              );
		try {
			if(beforeRank != -1)this.beforeDoc = Jsoup.parse(this.beforeAllHtml, "utf-8");
			if(afterRank != -1)this.afterDoc = Jsoup.parse(this.afterAllHtml,"utf-8");
		} catch (IOException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}
	}
	
	public abstract void output() throws IOException;
	
	public static String getLetterCodeFromDoc(Document doc){
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
	
	private static boolean isContainLetterCode(Element metaE){
		return metaE.toString().contains("Content-Type") ||
			   metaE.toString().contains("Content-Type") ||
			   metaE.toString().contains("content-type") ||
			   metaE.toString().contains("Content-type") ||
			   metaE.toString().contains("meta charset=");
	}
	
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
}
