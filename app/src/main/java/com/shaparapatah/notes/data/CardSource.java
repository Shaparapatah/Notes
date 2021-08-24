package com.shaparapatah.notes.data;

public interface CardSource {
    int size();

    CardData getCardData(int position);

}
