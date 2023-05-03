package uk.tees.ac.uk.b1197000.veesyx.entities;

import java.util.ArrayList;
import java.util.Date;

public class ArrayListFood extends ArrayList<Food> {
    private Date loaded;

    public Date getLoaded() {
        return loaded;
    }

    public void setLoaded(Date loaded) {
        this.loaded = loaded;
    }
}
