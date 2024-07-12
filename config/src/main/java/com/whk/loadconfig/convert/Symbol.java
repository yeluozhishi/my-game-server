package com.whk.loadconfig.convert;

import lombok.Getter;

@Getter
public enum Symbol {
    COLON(":"),

    DASH("-"),
    UNDER_SCOPE("_"),
    SLASH("/"),
    NUMBER_SIGN("#"),
    AND("&"),
    SEMICOLON(";"),
    COMMA(","),
    ;

    private final String separatorChars;

    Symbol(String separatorChars) {
        this.separatorChars = separatorChars;
    }

}