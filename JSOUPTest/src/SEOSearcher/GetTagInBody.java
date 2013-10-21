package SEOSearcher;

public class GetTagInBody extends SEOSearcherImp{

	@Override
	public
	String getTitleInExcel() {
		// TODO 自動生成されたメソッド・スタブ
		return "bodyタグ内にあるタグのサイズを返す";
	}

	@Override
	public
	String getResult() {
		GetBodyParcent bodyParcent = new GetBodyParcent();
		bodyParcent.init(doc, ranking, query, engine);
		double bodyP = Double.valueOf(bodyParcent.getResult());
		GetHeadParcent headParcent = new GetHeadParcent();
		headParcent.init(doc, ranking, query, engine);
		double headP = Double.valueOf(headParcent.getResult());
		return Double.toString(100 - (bodyP + headP));
	}

}
