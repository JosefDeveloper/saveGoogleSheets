package com.josefdev.savegooglesheets.utils;

import com.josefdev.savegooglesheets.models.GoogleSheetsResponse;
import com.josefdev.savegooglesheets.models.IGoogleSheets;

public class Common {
    public static String BASE_URL = "https://script.google.com/macros/s/AKfycbxGedH2kA6oKVXZfqnxpI1GYUN64bhOtcUXHlPgCOrRzrkLe1hmemYw4CLyxcA055gfvg/";
    public static String GOOGLE_SHEET_ID = "1iMiaZyx7zKYY-2wncLKm-WSQllWYKBxG_qpx3R3j9pE";
    public static String SHEET_NAME = "personas";

    public static IGoogleSheets iGSGetMethodClient(String baseUrl) {
        return GoogleSheetsResponse.getClientGetMethod(baseUrl)
                .create(IGoogleSheets.class);
    }
}
