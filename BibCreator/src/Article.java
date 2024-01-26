public class Article {
    private int id;
    private String author;
    private String journal;
    private String title;
    private String year;
    private String volume;
    private String number;
    private String pages;
    private String keywords;
    private String doi;
    private String issn;
    private String month;

    public void setId(int id){
        this.id = id;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setJournal(String journal) {
        this.journal = journal;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public void setVolume(String volume) {
        this.volume = volume;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public void setPages(String pages) {
        this.pages = pages;
    }

    public void setKeywords(String keywords) {
        this.keywords = keywords;
    }

    public void setDoi(String doi) {
        this.doi = doi;
    }

    public void setIssn(String issn) {
        this.issn = issn;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public int getId(){
        return id;
    }

    public String getAuthor() {
        return author;
    }

    public String getJournal() {
        return journal;
    }

    public String getTitle() {
        return title;
    }

    public String getYear() {
        return year;
    }

    public String getVolume() {
        return volume;
    }

    public String getNumber() {
        return number;
    }

    public String getPages() {
        return pages;
    }

    public String getKeywords() {
        return keywords;
    }

    public String getDoi() {
        return doi;
    }

    public String getIssn() {
        return issn;
    }

    public String getMonth() {
        return month;
    }

    public static String createIeeeFormat(Article article) {
        StringBuilder authors = new StringBuilder();
        for (String author : article.getAuthor().split(" and ")) {
            if (authors.length() > 0) {
                authors.append(", ");
            }
            authors.append(author);
        }

        return authors.toString() + "." + "\"" +article.getTitle() + "\""+ "," + article.getJournal() + "," + "vol." + article.getVolume() + "," + "no." + article.getNumber() + "," + "p." + article.getPages() + "," + article.getMonth() + " " + article.getYear();
    }

    public static String  createAcmFormat(Article article) {
        String[] authors = article.getAuthor().split(" and ");
        String authorList;
        if (authors.length > 1) {
            authorList = authors[0] + " et al.";
        } else {
            authorList = authors[0];
        }

        return authorList + article.getYear() + "." + article.getTitle() + "." + article.getJournal() + "."+
                article.getVolume() + "," + article.getNumber() + "(" + article.getYear() + ")," + article.getPages()+"." + "DOI:https://doi.org/" +
                article.getDoi();
    }

    public static String  createNjFormat(Article article) {
        String[] authors = article.getAuthor().split(" and ");
        String authorList = String.join(" & ", authors);

        return authorList + "." + article.getTitle() + "." + article.getJournal() + "."+
                article.getVolume() + "," + article.getPages() + "(" + article.getYear() + ")." ;
    }

    public String toString(){
        return this.id + " " + this.author + " " + this.journal  + " " + this.title + " " + this.year + " " + this.volume + " " + this.number + " " + this.pages + " " + this.doi + " " + this.keywords + " " + this.issn + " " + this.month;
    }
}



