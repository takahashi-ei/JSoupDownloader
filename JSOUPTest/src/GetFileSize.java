import java.io.File;


public class GetFileSize {
	public static void main(String[] args){
		String query = "自転車";
		for(int i = 1; i < 200; i++){
			File allHtmlFile = new File("20131005SEOチェックdata\\"+query+"\\"+i+".html");
			File headHtmlFile = new File("20131005SEOチェックdata\\"+query+"_head\\"+i+".html");
			File bodyHtmlFile = new File("20131005SEOチェックdata\\"+query+"_body\\"+i+".html");
			System.out.println(allHtmlFile.length());
		}
	}
}
