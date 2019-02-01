package com.example.lesson_1.walkcity;

public final class App extends android.app.Application {
    @Override
    public void onCreate() {
        super.onCreate();
        FontsOverride.setDefaultFont(this, "DEFAULT", "origa__.ttf");
        FontsOverride.setDefaultFont(this, "MONOSPACE", "origap.ttf");
        FontsOverride.setDefaultFont(this, "SERIF", "origa.ttf");
        FontsOverride.setDefaultFont(this, "SANS_SERIF", "origap.ttf");
    }
}