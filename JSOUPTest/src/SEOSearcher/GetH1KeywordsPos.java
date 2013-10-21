package SEOSearcher;

import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class GetH1KeywordsPos extends SEOSearcherImp{

	@Override
	public
	String getTitleInExcel() {
		// TODO 自動生成されたメソッド・スタブ
		return "先頭にあるh1のどの位置にキーワードが含まれているか";
	}

	@Override
	public
	String getResult() {
		int minPlace = Integer.MAX_VALUE;
		// TODO 自動生成されたメソッド・スタブ
		Element body = doc.body();
		Elements h1s = body.select("h1");
		Element head = doc.head();
		Elements metaKeywords = head.select("meta[name=keywords]");
		String[] keywords = metaKeywords.attr("content").split(",");
		if(h1s.size() == 0){
			return Integer.toString(-1);
		}
		Element h1 = h1s.get(0);

//		for( Element h1 : h1s ){
			boolean flag = false;
			for( String keyword : keywords ){
				int index = h1.text().indexOf(keyword);
//				System.out.println(h1.text());
				if(index != -1){
					if(minPlace < index){
						minPlace = index;
					}
				}
			}
//		}
			if(minPlace != Integer.MAX_VALUE){
				return Integer.toString(minPlace);
			}
			return Integer.toString(-1);
	}

}
