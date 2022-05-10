package pl.edu.pw.onlinestore.app.domain;

import java.util.ArrayList;
import java.util.List;

public enum OpinionType {
    ALL("Wszystkie"), POSITIVE("Pozytywne"), NEGATIVE("Negatywne");

    private String title;

    OpinionType(String title) {
        this.title = title;
    }

    public static OpinionType findByName(String name) {
        OpinionType result = null;
        for (OpinionType opinionType : values()) {
            if (opinionType.name().equalsIgnoreCase(name)) {
                result = opinionType;
                break;
            }
        }
        return result;
    }

    public static List<String> getNames() {
        List<String> opinionNames = new ArrayList<>();
        for (OpinionType opinionType : values()) {
            opinionNames.add(opinionType.title);
        }
        return opinionNames;
    }

    public String getTitle() {
        return title;
    }
}
