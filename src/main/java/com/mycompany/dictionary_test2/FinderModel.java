package com.mycompany.dictionary_test2;

import java.util.UUID;

/**
 *
 * @author holodok
 */
public class FinderModel {
    private String termin;
    private UUID id;


    public FinderModel(String termin, UUID id) {
        this.termin = termin;
        this.id = id;
    }

    public String getTermin() {
        return termin;
    }

    public void setTermin(String termin) {
        this.termin = termin;
    }
    
    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }
}