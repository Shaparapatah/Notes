package com.shaparapatah.notes.data;

import com.google.firebase.Timestamp;

import java.util.HashMap;
import java.util.Map;

public class CardDataTranslate {
    public static class Fields {

        public final static String DATE = "date";
        public final static String TITLE = "title";
        public final static String DESCRIPTION = "description";

    }

    public static CardData documentToCardData(String id, Map<String, Object> doc) {
        CardData answer = new CardData(
                (String) doc.get(Fields.TITLE),
                (String) doc.get(Fields.DESCRIPTION),
                ((Timestamp) doc.get(Fields.DATE)).toDate());
        answer.setId(id);
        return answer;
    }

    public static Map<String, Object> cardDataToDocument(CardData cardData) {
        Map<String, Object> answer = new HashMap<>();
        answer.put(Fields.TITLE, cardData.getListNote());
        answer.put(Fields.DESCRIPTION, cardData.getListTodo());
        answer.put(Fields.DATE, cardData.getDate());
        return answer;
    }

}
