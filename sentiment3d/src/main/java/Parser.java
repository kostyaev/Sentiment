import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Parser {

    private Connection conn;
    private Statement statement;

    private static final String BAD_OPINIONS_URL = "http://www.kinopoisk.ru/reviews/type/comment/status/bad/perpage/100/page/";

    private static final String GOOD_OPINIONS_URL = "http://www.kinopoisk.ru/reviews/type/comment/status/good/perpage/100/page/";

    private static final String NEUTRAL_OPINIONS_URL = "http://www.kinopoisk.ru/reviews/type/comment/status/neutral/perpage/100/page/";

    private final static int NEUTRAL_LAST_PAGE = 10;

    private final static int GOOD_LAST_PAGE = 30;

    private final static int BAD_LAST_PAGE = 9;

    private static final String BAD_SELECTOR = "div.response.bad";

    private static final String GOOD_SELECTOR = "div.response.good";

    private static final String NEUTRAL_SELECTOR = "div.response";




    private Document connect(String addr) throws IOException {
        Document doc = Jsoup.connect(addr)
                .userAgent("Mozilla/5.0 (Windows; U; WindowsNT 5.1; en-US; rv1.8.1.6) Gecko/20070725 Firefox/2.0.0.6")
                .referrer("http://www.google.com")
                .get();
        return doc;
    }

    private String extractNumber(String str) {
        Pattern pattern = Pattern.compile("^.*/(\\d+)/");
        Matcher matcher = pattern.matcher(str);
        if (matcher.find())
        {
            return matcher.group(1);
        }
        return null;
    }

    public void parse(String startURL, int lastpage, int grade, String selector) throws IOException, SQLException {
        Document page;
        int cnt = 0;
        for (int i = 1; i <= lastpage; i++){
            String url = startURL + i;
            page = connect(url);
            Elements pageElems = page.select(selector);
            for(Element elem : pageElems) {
                String filmURL = elem.select("p.film").select("a").attr("href");
                int filmId = Integer.parseInt(extractNumber(filmURL));
                //System.out.println("filmId: " + filmId);
                String message = elem.select("span._reachbanner_").get(0).text();
                putData(filmId, message, grade);
                //System.out.println("message: " + message);
                cnt++;
                System.out.println(cnt);

            }
        }
    }

    private void putData(int filmId, String message, int grade) throws SQLException {
        String query = "INSERT INTO checked_3d_opinions"
                + "(film_id, message, sent_grade) VALUES"
                + "("+filmId+", \'"+message + "\'," + grade + ");";
        statement.execute(query);
    }

    private void createTable() throws SQLException {
        String query = "CREATE TABLE checked_3d_opinions (id serial PRIMARY KEY, film_id int, message text, sent_grade smallint);";
        statement.executeUpdate(query);
    }

    public void initConn() throws ClassNotFoundException, IllegalAccessException, InstantiationException, SQLException {
        String connectionURL =
                "jdbc:postgresql://localhost:5432/sentiment3d";
        Class.forName("org.postgresql.Driver").newInstance();
        conn = DriverManager.getConnection
                (connectionURL, "postgres", "postgres");
        statement = conn.createStatement();
    }

    public void closeConn() throws SQLException {
        statement.close();
        conn.close();
    }

    public static void main(String [] args) throws IOException, ClassNotFoundException, SQLException, InstantiationException, IllegalAccessException {
        Parser parser =  new Parser();
        parser.initConn();
//        parser.parse(BAD_OPINIONS_URL, BAD_LAST_PAGE, 2);
//        parser.parse(NEUTRAL_OPINIONS_URL, NEUTRAL_LAST_PAGE, 3, NEUTRAL_SELECTOR);
        parser.parse(GOOD_OPINIONS_URL, GOOD_LAST_PAGE, 4, GOOD_SELECTOR);
    }
}
