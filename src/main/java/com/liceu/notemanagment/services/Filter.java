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
}
