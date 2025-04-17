package com.carbonit.treasuremap.models;

import java.util.Arrays;
import java.util.Objects;

public class TreasureMap {
    private int width;
    private int height;
    private Box[][] grid;
    private Adventurer adventurer;

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public Box[][] getGrid() {
        return grid;
    }

    public void setGrid(Box[][] grid) {
        this.grid = grid;
    }

    public Adventurer getAdventurer() {
        return adventurer;
    }

    public void setAdventurer(Adventurer adventurer) {
        this.adventurer = adventurer;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        TreasureMap that = (TreasureMap) o;
        return width == that.width && height == that.height && Objects.deepEquals(grid, that.grid) && Objects.equals(adventurer, that.adventurer);
    }

    @Override
    public int hashCode() {
        return Objects.hash(width, height, Arrays.deepHashCode(grid), adventurer);
    }
}
