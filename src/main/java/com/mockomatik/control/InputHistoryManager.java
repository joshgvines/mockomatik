package com.mockomatik.control;

import java.util.ArrayList;
import java.util.List;

public class InputHistoryManager {

    private List<String> historyList = new ArrayList<>();

    InputHistoryManager() {

    }

    void addToHistory(String commandEntry) {
        historyList.add(" " + commandEntry);
    }

    void displayHistory() {
        System.out.println("History:");
        for (String commandEntry : historyList) {
            System.out.println(commandEntry);
        }
    }

}
