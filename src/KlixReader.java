import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Scanner;
import java.util.Set;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class KlixReader {

	public static HashMap<String, Article> klixMap;
	public static int input;
	public static Set<String> naslovi;

	public static void klixReader() throws ParserConfigurationException,
			MalformedURLException, SAXException, IOException {

		DocumentBuilder docReader = DocumentBuilderFactory.newInstance()
				.newDocumentBuilder();
		Document xmldoc = docReader.parse(new URL(
				"http://www.klix.ba/rss/svevijesti").openStream());

		NodeList xmlItems = xmldoc.getElementsByTagName("item");

		klixMap = new HashMap<String, Article>();

		for (int i = 0; i < xmlItems.getLength(); i++) {
			Node current = xmlItems.item(i);

			if (current instanceof Element) {

				Element currentElement = (Element) current;

				NodeList children = currentElement.getChildNodes();

				String naslov = "";
				String uvod = "";
				String clanak = "";

				for (int j = 0; j < children.getLength(); j++) {

					if (children.item(j) instanceof Element) {
						Element currentChild = (Element) children.item(j);
						if (currentChild.getTagName().equalsIgnoreCase("title")) {
							naslov = currentChild.getTextContent();
						}
						if (currentChild.getTagName().equalsIgnoreCase("uvod")) {
							uvod = currentChild.getTextContent();
						}
						if (currentChild.getTagName()
								.equalsIgnoreCase("clanak")) {
							clanak = currentChild.getTextContent();
						}
					}

				}

				klixMap.put(naslov, new Article(naslov, uvod, clanak));
			}
		}
	}

	public static void titles() {
		naslovi = klixMap.keySet();

		Iterator<String> iter = naslovi.iterator();

		int numerator = 1;
		while (iter.hasNext()) {
			String str = numerator + ". " + iter.next();
			System.out.println(str);
			numerator++;
		}
	}

	public static void getArticle(int num) {
		int counter = 1;
		Iterator<String> iter = naslovi.iterator();
		while (counter < num) {
			iter.next();
			counter++;
		}
		String str = iter.next();
		System.out.println(klixMap.get(str));

	}

	public static void main(String[] args) {

		try {
			klixReader();
		} catch (MalformedURLException e) {
			System.out.println(e.getMessage());
		} catch (ParserConfigurationException e) {
			System.out.println(e.getMessage());
		} catch (SAXException e) {
			System.out.println(e.getMessage());
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}

		// Scanner for new selection
		Scanner scan = new Scanner(System.in);

		while (true) {
			System.out
					.println("********************************************************");
			System.out
					.println("Molimo unesite redni broj vijesti koju zelite procitati.");
			System.out.println("Unesite -1 za izlaz.");
			titles();
			System.out
					.println("********************************************************");

			input = scan.nextInt();
			if (input == -1) {
				break;
			}
			getArticle(input);

		}

		scan.close();

	}
}