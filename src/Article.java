public class Article {
	
	public String naslov;
	public String uvod;
	public String clanak;
	
	
	public Article(String naslov, String uvod, String clanak) {
		
		this.naslov = naslov;
		this.uvod = uvod;
		this.clanak = clanak;
	}


	@Override
	public String toString() {
		return "Naslov:\n" + naslov + "\nUvod:\n" + uvod + "\n\nclanak:\n" + clanak + "\n";
	}
	
	

}
