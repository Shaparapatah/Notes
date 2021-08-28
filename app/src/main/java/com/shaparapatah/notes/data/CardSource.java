package com.shaparapatah.notes.data;

public interface CardSource {
    CardSource init(CardsSourceResponse cardsSourceResponse);

    int size();

    CardData getCardData(int position);
    void deleteCardData(int position);
    void updateCardData(int position, CardData newCardData);
    void addCardData(CardData newCardData);
    void clearCardData();

}
