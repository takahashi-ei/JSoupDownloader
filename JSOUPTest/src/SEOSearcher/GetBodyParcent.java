package SEOSearcher;

public class GetBodyParcent extends SEOSearcherImp{

	@Override
	public
	String getTitleInExcel() {
		// TODO 自動生成されたメソッド・スタブ
		return "HTMLファイル全体を比較して本文テキストの大きさを返す";
	}

	@Override
	public
	String getResult() {
		// TODO 自動生成されたメソッド・スタブ
		GetBodyWeight bodyWeight = new GetBodyWeight();
		bodyWeight.init(doc, ranking, query, engine);
		double bodyW = Double.valueOf(bodyWeight.getResult());
		GetHtmlWeight htmlWeight = new GetHtmlWeight();
		htmlWeight.init(doc, ranking, query, engine);
		double htmlW = Double.valueOf(htmlWeight.getResult());
		
		return Double.toString((bodyW /htmlW) * 100);
	}

}
