package com.shaparapatah.notes;

public interface CardSource {
    int size();

    CardData getCardData(int position);

}
