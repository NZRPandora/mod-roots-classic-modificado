/*
 * Decompiled with CFR 0.152.
 */
package elucent.rootsclassic.client.screen;

import elucent.rootsclassic.util.RootsUtil;

public class ScreenTextInstance {
    private String line;
    private float x;
    private float y;
    private int color;
    private boolean shadow = true;

    public ScreenTextInstance(String line, float x, float y, int color, boolean shadow) {
        this.setLine(line);
        this.setX(x);
        this.setY(y);
        this.setColor(color);
        this.setShadow(shadow);
    }

    public ScreenTextInstance(String line, float x, float y, int color) {
        this(line, x, y, color, true);
    }

    public ScreenTextInstance(String line, float x, float y) {
        this(line, x, y, RootsUtil.intColor(255, 255, 255));
    }

    public String getLine() {
        return this.line;
    }

    public void setLine(String line) {
        this.line = line;
    }

    public float getX() {
        return this.x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return this.y;
    }

    public void setY(float y) {
        this.y = y;
    }

    public int getColor() {
        return this.color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public boolean isShadow() {
        return this.shadow;
    }

    public void setShadow(boolean shadow) {
        this.shadow = shadow;
    }
}

