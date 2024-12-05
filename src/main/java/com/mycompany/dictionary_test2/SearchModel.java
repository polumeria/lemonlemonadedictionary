package com.mycompany.dictionary_test2;

/**
 *
 * @author holodok
 */
public class SearchModel {
    private String termin;
    private String description;

    public SearchModel(String termin, String description) {
        this.termin = termin;
        this.description = description;
    }    

    public String getTermin() {
        return termin;
    }

    public void setTermin(String termin) {
        this.termin = termin;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}