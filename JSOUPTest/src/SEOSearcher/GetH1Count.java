package SEOSearcher;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import SEOSearcher.SEOSearcherImp;

public class GetH1Count extends SEOSearcherImp{

	@Override
	public
	String getTitleInExcel() {
		return "h1タグの数を数える";
	}

	@Override
	public
	String getResult() {
		Element body = doc.body();
		Elements h1s = body.select("h1");
		return Integer.toString(h1s.size());
	}

}
