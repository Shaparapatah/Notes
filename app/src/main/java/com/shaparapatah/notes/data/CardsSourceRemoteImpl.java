package com.shaparapatah.notes.data;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.Source;

import java.util.ArrayList;
import java.util.List;

public class CardsSourceRemoteImpl implements CardSource {



    private static String CARDS_COLLECTION = "cards";
    private FirebaseFirestore store = FirebaseFirestore.getInstance();
    private CollectionReference collectionReference = store.collection(CARDS_COLLECTION);
    private List<CardData> cardsData = new ArrayList<CardData>();

    @Override
    public CardSource init(CardsSourceResponse cardsSourceResponse) {
        collectionReference.orderBy(CardDataTranslate.Fields.DATE, Query.Direction.DESCENDING).get(Source.SERVER)
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            cardsData = new ArrayList<CardData>();
                            for (QueryDocumentSnapshot docFields : task.getResult()) {
                                CardData cardData = CardDataTranslate.documentToCardData(docFields.getId(),
                                        docFields.getData());
                                cardsData.add(cardData);
                            }
                            cardsSourceResponse.initialized(CardsSourceRemoteImpl.this);
                        }
                    }
                });
        return this;
    }

    @Override
    public CardData getCardData(int position) {
        return cardsData.get(position);
    }

    @Override
    public int size() {
        return cardsData.size();
    }

    @Override
    public void deleteCardData(int position) {
        collectionReference.document(cardsData.get(position).getId()).delete().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {

            }
        });
    }

    @Override
    public void updateCardData(int position, CardData newCardData) {
        collectionReference.document(cardsData.get(position).getId())
                .update(CardDataTranslate.cardDataToDocument(newCardData));
    }

    @Override
    public void addCardData(CardData newCardData) {
        collectionReference.add(CardDataTranslate.cardDataToDocument(newCardData));
    }

    @Override
    public void clearCardData() {
        for (CardData cardData : cardsData) {
            collectionReference.document(cardData.getId()).delete();
        }
    }
}
