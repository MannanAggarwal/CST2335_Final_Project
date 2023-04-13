package algonquin.cst2335.cst2335_final_project.ui;

import java.io.Serializable;

/**
 * Article class that represents an article with headline, publication date, and URL.
 * This class implements Serializable to allow Article objects between activities.
 */
public class Article implements Serializable {
    /**
     * Article attributes
     */
    private String headline;
    private String pub_date;
    private String web_url;

    /**
     * Constructs an Article object with the given headline, publication date, and URL.
     * @param headline The headline of the article.
     * @param pub_date The publication date of the article
     * @param web_url  The URL of the article
     * @param pubDate
     */
    public Article(String headline, String pub_date, String web_url, String pubDate) {
        this.headline = headline;
        this.pub_date = pub_date;
        this.web_url = web_url;
    }

    /**
     * Retrieves the headline of the article.
     * @return The headline of the article
     */
    public String getHeadline() {
        return headline;
    }

    /**
     * Sets the headline of the article.
     * @param headline The new headline of the article
     */
    public void setHeadline(String headline) {
        this.headline = headline;
    }

    /**
     * Retrieves the publication date of the article.
     * @return The publication date of the article
     */
    public String getPublicationDate() {
        return pub_date;
    }

    /**
     * Sets the publication date of the article.
     * @param pub_date The new publication date of the article
     */
    public void setPublicationDate(String pub_date) {
        this.pub_date = pub_date;
    }

    /**
     * Retrieves the URL of the article.
     * @return The URL of the article
     */
    public String getUrl() {
        return web_url;
    }

    /**
     * Sets the URL of the article.
     * @param web_url The new URL of the article
     */
    public void setUrl(String web_url) {
        this.web_url = web_url;
    }
}
