package cloud.model;

import lombok.Getter;

@Getter
public enum AgeRatingEnum {
    G("G"),
    PG("PG"),
    PG_13("PG-13"),
    R("R"),
    NC_17("NC-17"),
    TV_MA("TV-MA"),
    TV_14("TV-14"),
    NOT_RATED("Not Rated"),
    TV_G("TV-G"),
    TV_PG("TV-PG");

    private final String rating;

    AgeRatingEnum(String rating) {
        this.rating = rating;
    }

}
