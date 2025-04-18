package com.carbonit.treasuremap.services;

import com.carbonit.treasuremap.enums.BoxTypeEnum;
import com.carbonit.treasuremap.enums.DirectionEnum;
import com.carbonit.treasuremap.models.Adventurer;
import com.carbonit.treasuremap.models.Box;
import com.carbonit.treasuremap.models.TreasureMap;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class OutputMapServiceTest {
    @Autowired
    private OutputMapService outputService;
    private TreasureMap treasureMap;

    @BeforeEach
    void setUp() {
        this.treasureMap = new TreasureMap();
        Box[][] grid = new Box[5][5];
        this.treasureMap.setWidth(5);
        this.treasureMap.setHeight(5);
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                grid[i][j] = new Box(i,j, BoxTypeEnum.PLAIN);
            }
        }
        this.treasureMap.setGrid(grid);
    }


    @Test
    void drawMap_with_plain_boxes_only() {
        String expected = ".   .   .   .   .\n.   .   .   .   .\n.   .   .   .   .\n.   .   .   .   .\n.   .   .   .   .";
        assertEquals(expected,outputService.drawMap(this.treasureMap));
    }

    @Test
    void drawMap_with_plain_boxes_and_adventurer() {
        Adventurer adventurer = new Adventurer("Max",0, 0, DirectionEnum.S);
        this.treasureMap.setAdventurer(adventurer);
        String expected = "A(Max)   .   .   .   .\n.   .   .   .   .\n.   .   .   .   .\n.   .   .   .   .\n.   .   .   .   .";
        assertEquals(expected,outputService.drawMap(this.treasureMap));
    }

    @Test
    void drawMap_with_plain_boxes_treasure_box_and_adventurer() {
        Adventurer adventurer = new Adventurer("Max",0, 0, DirectionEnum.S);
        this.treasureMap.setAdventurer(adventurer);
        Box treasureBox = new Box(0,4,3,BoxTypeEnum.TREASURE);
        Box[][] grid = this.treasureMap.getGrid();
        grid[0][4] = treasureBox;
        this.treasureMap.setGrid(grid);
        this.treasureMap.setAdventurer(adventurer);

        String expected = "A(Max)   .   .   .   .\n.   .   .   .   .\n.   .   .   .   .\n.   .   .   .   .\nT(3)   .   .   .   .";
        assertEquals(expected,outputService.drawMap(this.treasureMap));
    }

    @Test
    void drawMap_with_plain_boxes_mountain_box_and_adventurer() {
        Adventurer adventurer = new Adventurer("Max",0, 0, DirectionEnum.S);
        this.treasureMap.setAdventurer(adventurer);
        Box mountainBox = new Box(2,2,BoxTypeEnum.MOUNTAIN);
        Box[][] grid = this.treasureMap.getGrid();
        grid[2][2] = mountainBox;
        this.treasureMap.setGrid(grid);
        this.treasureMap.setAdventurer(adventurer);

        String expected = "A(Max)   .   .   .   .\n.   .   .   .   .\n.   .   M   .   .\n.   .   .   .   .\n.   .   .   .   .";
        assertEquals(expected,outputService.drawMap(this.treasureMap));
    }

    @Test
    void drawMap_complete_map(){
        Adventurer adventurer = new Adventurer("Max",0, 0, DirectionEnum.S);
        this.treasureMap.setAdventurer(adventurer);
        Box mountainBox = new Box(1,4,BoxTypeEnum.MOUNTAIN);
        Box treasureBox = new Box(2,4,2,BoxTypeEnum.TREASURE);
        Box[][] grid = this.treasureMap.getGrid();
        grid[1][4] = mountainBox;
        grid[2][4] = treasureBox;
        this.treasureMap.setGrid(grid);
        this.treasureMap.setAdventurer(adventurer);

        String expected = "A(Max)   .   .   .   .\n.   .   .   .   .\n.   .   .   .   .\n.   .   .   .   .\n.   M   T(2)   .   .";
        assertEquals(expected,outputService.drawMap(this.treasureMap));
    }

    @Test
    void writingMap_with_plain_boxes_only() {
        String expected = "C - 5 - 5";
        assertEquals(expected,outputService.writingMap(this.treasureMap));
    }
    @Test
    void writingMap_with_plain_boxes_and_adventurer() {
        Adventurer adventurer = new Adventurer("Max",0, 0, DirectionEnum.S);
        this.treasureMap.setAdventurer(adventurer);

        String expected = "C - 5 - 5\nA - Max - 0 - 0 - S - 0";
        assertEquals(expected,outputService.writingMap(this.treasureMap));
    }

    @Test
    void writingMap_with_plain_boxes_treasure_box_and_adventurer() {
        Adventurer adventurer = new Adventurer("Max",0, 0, DirectionEnum.S);
        this.treasureMap.setAdventurer(adventurer);

        Box treasureBox = new Box(0,4,3,BoxTypeEnum.TREASURE);
        Box[][] grid = this.treasureMap.getGrid();
        grid[0][4] = treasureBox;
        this.treasureMap.setGrid(grid);
        this.treasureMap.setAdventurer(adventurer);

        String expected = "C - 5 - 5\nT - 0 - 4 - 3\nA - Max - 0 - 0 - S - 0";
        assertEquals(expected,outputService.writingMap(this.treasureMap));
    }

    @Test
    void writingMap_with_plain_boxes_mountain_box_and_adventurer() {
        Adventurer adventurer = new Adventurer("Max",3, 1, DirectionEnum.E);
        this.treasureMap.setAdventurer(adventurer);

        Box mountainBox = new Box(2,2,BoxTypeEnum.MOUNTAIN);
        Box[][] grid = this.treasureMap.getGrid();
        grid[2][2] = mountainBox;
        this.treasureMap.setGrid(grid);
        this.treasureMap.setAdventurer(adventurer);

        String expected = "C - 5 - 5\nM - 2 - 2\nA - Max - 3 - 1 - E - 0";
        assertEquals(expected,outputService.writingMap(this.treasureMap));
    }

    @Test
    void writingMap_complete_map() {
        Adventurer adventurer = new Adventurer("Lucie",4, 3, DirectionEnum.O);
        adventurer.setTreasureFound(3);
        this.treasureMap.setAdventurer(adventurer);

        Box mountainBox = new Box(1,2,BoxTypeEnum.MOUNTAIN);
        Box treasureBox = new Box(2,4,1,BoxTypeEnum.TREASURE);
        Box[][] grid = this.treasureMap.getGrid();
        grid[1][2] = mountainBox;
        grid[2][4] = treasureBox;
        this.treasureMap.setGrid(grid);
        this.treasureMap.setAdventurer(adventurer);

        String expected = "C - 5 - 5\nM - 1 - 2\nT - 2 - 4 - 1\nA - Lucie - 4 - 3 - O - 3";
        assertEquals(expected,outputService.writingMap(this.treasureMap));
    }
}