package com.shaparapatah.notes.observer;

import com.shaparapatah.notes.data.CardData;

public interface Observer {
    void updateState(CardData cardData);
}
