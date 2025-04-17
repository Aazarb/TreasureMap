package com.carbonit.treasuremap.models;

import com.carbonit.treasuremap.enums.BoxTypeEnum;

import java.util.Objects;

public class Box {
    private int x;
    private int y;
    private BoxTypeEnum boxType;
    private int treasureNb;

    public Box(int x, int y, int treasureNb, BoxTypeEnum boxType) {
        this.x = x;
        this.y = y;
        this.boxType = boxType;
        this.treasureNb = treasureNb;
    }

    public Box(int x, int y, BoxTypeEnum boxType) {
        this.x = x;
        this.y = y;
        this.boxType = boxType;
        this.treasureNb = 0;
    }

    public boolean isMountainBox(){
        return BoxTypeEnum.MOUNTAIN.equals(this.boxType);
    }

    public boolean isPlainBox(){
        return BoxTypeEnum.PLAIN.equals(this.boxType);
    }

    public boolean isTreasureBox(){
        return BoxTypeEnum.TREASURE.equals(this.boxType);
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }


    public BoxTypeEnum getBoxType() {
        return boxType;
    }

    public void setBoxType(BoxTypeEnum boxType) {
        this.boxType = boxType;
    }

    public int getTreasureNb() {
        return treasureNb;
    }

    public void setTreasureNb(int treasureNb) {
        this.treasureNb = treasureNb;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Box box = (Box) o;
        return x == box.x && y == box.y && treasureNb == box.treasureNb && boxType == box.boxType;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y, boxType, treasureNb);
    }
}
