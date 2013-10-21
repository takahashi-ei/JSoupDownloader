package SEOSearcher;

import org.jsoup.nodes.Document;

public abstract class SEOSearcherImp {
	Document doc;
	int ranking;
	String query;
	String engine;
	
	/**
	 * Documentの構造を保存する
	 * @param doc
	 */
	public void init(Document doc,int ranking,String query,String engine){
		this.doc = doc;
		this.ranking = ranking;
		this.query = query;
		this.engine =engine;
	}
	
	/**
	 * Excelで書く調査内容のtitleを書く
	 * @return
	 */
	public abstract String getTitleInExcel();
	
	/**
	 * 調査結果を出力する
	 * @return
	 */
	public abstract String getResult();
}
