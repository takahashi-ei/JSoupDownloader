package SEOSearcher;

public class GetHeadParcent extends SEOSearcherImp{

	@Override
	public
	String getTitleInExcel() {
		// TODO 自動生成されたメソッド・スタブ
		return "headタグの割合";
	}

	@Override
	public
	String getResult() {
		GetHeadWeight headWeight = new GetHeadWeight();
		headWeight.init(doc, ranking, query, engine);
		double headW = Double.valueOf(headWeight.getResult());
		GetHtmlWeight htmlWeight = new GetHtmlWeight();
		htmlWeight.init(doc, ranking, query, engine);
		double htmlW = Double.valueOf(htmlWeight.getResult());
		// TODO 自動生成されたメソッド・スタブ
		return Double.toString((headW / htmlW) * 100);
	}

}
