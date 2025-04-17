package com.carbonit.treasuremap.models;

import com.carbonit.treasuremap.enums.DirectionEnum;

import java.util.Objects;

public class Adventurer {
    private String name;
    private int x;
    private int y;
    private DirectionEnum direction;
    private String movingSequence;
    private int treasureFound;

    public Adventurer(String name, int x, int y, DirectionEnum direction, String movingSequence) {
        this.name = name;
        this.x = x;
        this.y = y;
        this.direction = direction;
        this.movingSequence = movingSequence;
        this.treasureFound = 0;
    }

    public Adventurer(String name, int x, int y, DirectionEnum direction) {
        this.name = name;
        this.x = x;
        this.y = y;
        this.direction = direction;
        this.movingSequence = null;
        this.treasureFound = 0;
    }

    public String getName() {
        return name;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public DirectionEnum getDirection() {
        return direction;
    }

    public void setDirection(DirectionEnum direction) {
        this.direction = direction;
    }

    public String getMovingSequence() {
        return movingSequence;
    }

    public void setMovingSequence(String movingSequence) {
        this.movingSequence = movingSequence;
    }

    public int getTreasureFound() {
        return treasureFound;
    }

    public void setTreasureFound(int treasureFound) {
        this.treasureFound = treasureFound;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Adventurer that = (Adventurer) o;
        return x == that.x && y == that.y && treasureFound == that.treasureFound && Objects.equals(name, that.name) && direction == that.direction && Objects.equals(movingSequence, that.movingSequence);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, x, y, direction, movingSequence, treasureFound);
    }
}
