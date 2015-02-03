package de.lette;

import android.graphics.drawable.Drawable;

public class NavigationItem {
    private String mText;
    private Drawable mDrawable;
    
    public NavigationItem(String text) {
        mText = text;
    }

    public NavigationItem(String text, Drawable drawable) {
        this(text);
        mDrawable = drawable;
    }

    public String getText() {
        return mText;
    }

    public void setText(String text) {
        mText = text;
    }

    public Drawable getDrawable() {
        return mDrawable;
    }

    public void setDrawable(Drawable drawable) {
        mDrawable = drawable;
    }
}
