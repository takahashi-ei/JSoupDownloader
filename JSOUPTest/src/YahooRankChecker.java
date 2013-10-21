import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import org.jsoup.Jsoup;
import org.jsoup.nodes.*;
import org.jsoup.select.Elements;


public class YahooRankChecker extends SearchRankChecker{
	final int sleepTime = 10000;

	public static void main(String[] args) {
		YahooRankChecker yrc = new YahooRankChecker();
		yrc.rankCheck("yahoo.csv");
	}


	@Override
	protected int getSearchRank(String keyword) {
		for( int start = 1 ; start<1000 ; start=start+100 ){
			String result = null;
			try {
				result = getHtml("http://search.yahoo.co.jp/search?p="+URLEncoder.encode(keyword, "utf-8")+"&pstart=1&b="+start+"&n=100");

				// 類似しているページを含める場合
//				result = getHtml("http://search.yahoo.co.jp/search?p="+URLEncoder.encode(keyword, "utf-8")+"&pstart=1&b="+start+"&n=100&dups=1");
			} catch (UnsupportedEncodingException e1) {
				e1.printStackTrace();
			}

			int rank = getUrlRank("(<div class=\\\"hd\\\"><h3><a href=\\\")([^\"]+)(\")", result);
			if( rank != -1 ){ return (start-1) + rank; }

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
			out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(keyword + "_yahoo.txt"), "utf-8"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		for( int start = 0 ; start<500 ; start=start+100 ){
			String result = null;
			try {
				result = getHtml("http://search.yahoo.co.jp/search?p="+URLEncoder.encode(keyword, "utf-8")+"&pstart=1&b="+start+"&n=100&pws=0");

				// 類似しているページを含める場合
//				result = getHtml("http://search.yahoo.co.jp/search?p="+URLEncoder.encode(keyword, "utf-8")+"&pstart=1&b="+start+"&n=100&dups=1");
			} catch (UnsupportedEncodingException e1) {
				e1.printStackTrace();
			}
			
			Document document = Jsoup.parse(result);
			
			Elements Tags = document.select("div#contents div.w div.hd h3 a");
			System.out.println(Tags.size());
			//System.out.println(result);
			for (Element Tag:Tags) {
				try {
					out.write(Tag.attributes().get("href").toString() + "\r\n");
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			
			//TODO:終了条件
			if(Tags.size()<90){
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
