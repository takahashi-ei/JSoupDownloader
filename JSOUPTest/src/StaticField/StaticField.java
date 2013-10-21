package StaticField;

import SEOSearcher.*;

public final class StaticField {
	/* アジサイ，自動車，自転車、卓球，饅頭，SEO，SEO対策*/
	public static final String QUERY = "アジサイ";
	
	public static final boolean G_FLAG = true;
	
	/*HTMLファイルの保存先のディレクトリ */
	public static final String SAVE_FOLDER_NAME = "SEOチェックdata";
	/*HTMLファイルのロード先ディレクトリ */
	public static final String LOAD_FOLDER_NAME = "SEOチェックdata";
	
	public static final SEOSearcherImp[] SEO_SEARCHERS =
	{
			new GetH1Count(),
			new CheckH1KeywordsCount(),
			new CheckH1KeywordsPos(),
			new GetH1KeywordsPos(),
			new CheckH1WordCount(),
			new GetH1QueryPos(),
//			new GetH1WordCount(),
			new CheckHeadCharset(),
			new CheckHeadCss(),
			new GetHeadDescriptionCount(),
			new CheckHeadDescriptionKeywordPos(),
			new GetHeadDescriptionKeywordPos(),
			new GetHeadDescriptionKeywordPosParcent(),
			new GetHeadDescriptionQueryPos(),
			new GetHeadDescriptionQueryPosParcent(),
			new GetHeadDescriptionKeywordMinPos(),
	/** 作成終了 */
			new GetHeadDescriptionKeywordCount(),
			new CheckHeadDescriptionKeywordCount(),
//			new GetHeadDescriptionKeywordCount(),
			new CheckHeadDescriptionWordCount(),
			new CheckHeadJavaScript(),
			new CheckHeadKeywordsCount(),
			new CheckHeadKeywordsSeparater(),
			new CheckHeadLanguage(),
			new CheckHeadTitleCount(),
			new CheckHeadTitleKeywordsCount(),
			new CheckHeadTitleKeywordsPos(),
			new CheckHeadTitleSymble(),
			new CheckHeadUnusedTag(),
			new CheckHxCount(),
			new CheckHxKeywordsPos(),
			new CheckHxStrong(),
			new CheckImgAlt(),
			new CheckStrongCount(),
			new CheckStrongWordCount(),
			new CountHeadUnusedTag(),
			new GetBodyParcent(),
			new GetBodyWeight(),
			new GetCountForKeywords(),
			new GetHeadParcent(),
			new GetHeadWeight(),
			new GetHtmlWeight(),
			new GetTagInBody()
	};
	
	//SEOTestNewでチェックがとまる順位を，この配列の要素に加えるとその順位を飛ばしてチェックする
	public static final int[] MIS_RANKING = {

	};
}
