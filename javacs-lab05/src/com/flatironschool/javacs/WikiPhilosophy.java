package com.flatironschool.javacs;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.nodes.TextNode;

import org.jsoup.select.Elements;

public class WikiPhilosophy {
	
	final static WikiFetcher wf = new WikiFetcher();
	
	/**
	 * Tests a conjecture about Wikipedia and Philosophy.
	 * 
	 * https://en.wikipedia.org/wiki/Wikipedia:Getting_to_Philosophy
	 * 
	 * 1. Clicking on the first non-parenthesized, non-italicized link
     * 2. Ignoring external links, links to the current page, or red links
     * 3. Stopping when reaching "Philosophy", a page with no links or a page
     *    that does not exist, or when a loop occurs
	 * 
	 * @param args
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException {
		
        // some example code to get you started

		String url = "https://en.wikipedia.org/wiki/Java_(programming_language)";

		int clickCount = 0;

		while (!url.equals("https://en.wikipedia.org/wiki/Philosophy")) {
			Elements paragraphs = wf.fetchWikipedia(url);

			Element firstPara = paragraphs.get(0);

			Iterable<Node> iter = new WikiNodeIterable(firstPara);
			for (Node node : iter) {
				if (node instanceof Element) {

					String nodeString = node.toString();
					int parenthesisIndex = nodeString.indexOf("(");
					int parenthesisIndex2 = nodeString.indexOf(")");
					int index = nodeString.indexOf("/wiki/");

					if(parenthesisIndex < index) {
						nodeString = nodeString.substring(parenthesisIndex2);
						index = nodeString.indexOf("/wiki/");
					}

					nodeString = nodeString.substring(index);
					int index2 = nodeString.indexOf("\"");
					String newURL = nodeString.substring(0, index2);
					System.out.println("New URL: " + newURL);

					clickCount++;

					url = "https://en.wikipedia.org" + newURL;

					break;
				}
			}
		}

		System.out.println("Number of clicks: " + clickCount);


	}
}
