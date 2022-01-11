package me.tyler.portal;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class Portal {

    private final Gson GSON = new GsonBuilder()
            .create();

    public Portal() {
        System.out.println("debug");
    }
}
