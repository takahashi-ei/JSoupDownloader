

import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import org.jsoup.Jsoup;
import org.jsoup.nodes.*;
import org.jsoup.select.Elements;

import StaticField.StaticField;




public class GoogleRankChecker extends SearchRankChecker{
	final int sleepTime = 10000;

	public static void main(String[] args) {
		GoogleRankChecker grc = new GoogleRankChecker();
		grc.rankCheck("google.csv");
	}


	@Override
	protected int getSearchRank(String keyword){
		for( int start = 0 ; start<1000 ; start=start+100 ){
			String result = null;
			try {
				result = getHtml("https://www.google.co.jp/search?q="+URLEncoder.encode(keyword, "utf-8")+"&start="+start+"&num=100&pws=0");

				// 類似しているページを含める場合
//				result = getHtml("https://www.google.co.jp/search?q="+URLEncoder.encode(keyword, "utf-8")+"&start="+start+"&filter=0&num=100");
			} catch (UnsupportedEncodingException e1) {
				e1.printStackTrace();
			}

			int rank = getUrlRank("(<h3 class=\\\"r\\\"><a href=\\\")([^\"]+)(\")", result);
			if( rank != -1 ){ return start + rank; }

			try {
				Thread.sleep(sleepTime);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		return -1;
	}
	
	@Override
	protected void getUrl(String keyword){
		System.out.println("「" + keyword + "」の検索結果のURLを取得します");
		
		BufferedWriter out = null;
		try {
			out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(StaticField.SAVE_FOLDER_NAME + "//" + keyword + "_google.txt"), "utf-8"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		for( int start = 0 ; start<1000 ; start=start+100 ){
			String result = null;
			try {
				result = getHtml("https://www.google.co.jp/search?q="+URLEncoder.encode(keyword, "utf-8")+"&start="+start+"&num=100&pws=0");

				// 類似しているページを含める場合
//				result = getHtml("https://www.google.co.jp/search?q="+URLEncoder.encode(keyword, "utf-8")+"&start="+start+"&filter=0&num=100");
			} catch (UnsupportedEncodingException e1) {
				e1.printStackTrace();
			}
			
			Document document = Jsoup.parse(result);
			
			Elements Tags = document.select("div#search ol li h3.r a");
			System.out.println(Tags.size());
			for (Element Tag:Tags) {
				try {
					out.write(Tag.attributes().get("href").toString() + "\r\n");
				} catch (IOException e) {
					e.printStackTrace();
				}
			}

			//TODO:終了条件
			if(Tags.size()<100){
				System.out.println((start+Tags.size()) + "件取得しました");
				break;
			}
			System.out.println(start + "~" + (start+99));
			
			try {
				Thread.sleep(sleepTime);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		try {
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return;
	}
}
