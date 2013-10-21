package SEOSearcher;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import SEOSearcher.SEOSearcherImp;

public class CheckH1Count extends SEOSearcherImp{

	@Override
	public
	String getTitleInExcel() {
		return "h1タグの数が1つのみかどうか";
	}

	@Override
	public
	String getResult() {
		Element body = doc.body();
		Elements h1s = body.select("h1");
		if(h1s.size() == 1){
			return Integer.toString(1);
		}
		return Integer.toString(0);
	}

}
