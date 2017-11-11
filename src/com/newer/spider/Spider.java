package com.newer.spider;

import java.io.IOException;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class Spider implements Runnable {

	private String url;
	private List<Film> films;

	public Spider(String url, List<Film> films) {
		this.url = url;
		this.films = films;
	}

	@Override
	public void run() {
		try {
			Document doc = Jsoup.connect(url).timeout(10000).get();

			Elements elms = doc.select(".grid_view .item");

			for (Element e : elms) {
				Film film = new Film();
				String id = e.select("em").text();
				String path = e.select(".pic img").attr("src");
				String title = e.select(".title").text();
				String quote = e.select(".quote .inq").text();
				String rating = e.select(".star .rating_num").text();
				String info = e.select(".bd p").first().text();

				film.setId(Integer.parseInt(id));
				film.setTitle(title);
				film.setPath(path);
				film.setQuote(quote);
				film.setRating(Double.parseDouble(rating));
				film.setInfo(info);

				films.add(film);
				System.out.println(Thread.currentThread().getName() + " download " + id);
			}
			System.out.println(Thread.currentThread() + " over");

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
