package com.liceu.notemanagment.services;

public class Filter {
    public static String checkTypeFilter(String title, String initDate, String endDate) {
        if (title != null && initDate != null && endDate != null) {

            if (!title.equals("") && !initDate.equals("") && !endDate.equals("")) {
                return "filterAll";
            }

            if (!title.equals("") && initDate.equals("") && endDate.equals("")) {
                return "filterByTitle";
            }

            if (title.equals("") && !initDate.equals("") && !endDate.equals("")) {
                return "filterByDate";
            }

        }
        return null;
    }

    public static String getURLFilter(String typeNote, String title, String initDate, String endDate) {
        if (title != null && initDate != null && endDate != null) {

            if (!title.equals("") && !initDate.equals("") && !endDate.equals("")) {
                return String.format("&typeNote=%s&titleFilter=%s&noteStart=&s&noteEnd=&s", typeNote, title, initDate, endDate);
                //return "filterAll";
            }

            if (!title.equals("") && initDate.equals("") && endDate.equals("")) {
                return String.format("&typeNote=%s&titleFilter=%s&noteStart=&noteEnd=", typeNote, title);
            }

            if (title.equals("") && !initDate.equals("") && !endDate.equals("")) {
                return String.format("&typeNote=%s&titleFilter=&noteStart=%s&noteEnd=&s", typeNote, initDate, endDate);
            }

        }
        if (typeNote != null) {
            return String.format("&typeNote=%s&titleFilter=&noteStart=&noteEnd=", typeNote);
        }
        return null;
    }
}
