package com.carbonit.treasuremap.services;

import com.carbonit.treasuremap.enums.BoxTypeEnum;
import com.carbonit.treasuremap.enums.DirectionEnum;
import com.carbonit.treasuremap.exceptions.NullBoxException;
import com.carbonit.treasuremap.exceptions.UnknownLineFormat;
import com.carbonit.treasuremap.models.Adventurer;
import com.carbonit.treasuremap.models.Box;
import com.carbonit.treasuremap.models.TreasureMap;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class TreasureMapServiceTest {

    @Autowired
    private TreasureMapService treasureMapService;

    private TreasureMap treasureMap;

    @BeforeEach
    void setUp() {
        this.treasureMap = new TreasureMap();
    }

    @Test
    void decodeLine_when_comment_line_should_return_null() {
        assertNull(treasureMapService.decodeLine(this.treasureMap, "#comment"));
    }

    @Test
    void decodeLine_when_map_line_should_initialize_map_and_grid() {
        this.treasureMap.setWidth(8);
        this.treasureMap.setHeight(3);
        Box[][] grid = new Box[8][3];
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                grid[i][j] = new Box(i, j, BoxTypeEnum.PLAIN);
            }
        }
        this.treasureMap.setGrid(grid);

        TreasureMap result = treasureMapService.decodeLine(this.treasureMap, "C - 8 - 3");
        assertEquals(this.treasureMap.getWidth(), result.getWidth());
        assertEquals(this.treasureMap.getHeight(), result.getHeight());
        assertArrayEquals(this.treasureMap.getGrid(), result.getGrid());
    }

    @Test
    void decodeLine_valid_mountain_line() {
        this.treasureMap.setWidth(9);
        this.treasureMap.setHeight(9);
        Box[][] grid = new Box[9][9];
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                grid[i][j] = null;
            }
        }
        grid[2][2] = new Box(2, 2, BoxTypeEnum.MOUNTAIN);
        this.treasureMap.setGrid(grid);

        TreasureMap result = treasureMapService.decodeLine(this.treasureMap, "M - 2 - 2");

        assertTrue(result.getGrid()[2][2].isMountainBox());
        assertEquals(2, result.getGrid()[2][2].getX());
        assertEquals(2, result.getGrid()[2][2].getY());
    }

    @Test
    void decodeLine_when_treasureLine_should_place_treasure_in_grid() {
        this.treasureMap.setWidth(4);
        this.treasureMap.setHeight(7);
        Box[][] grid = new Box[4][7];
        for (int i = 0; i < 4; i++) {
            for (int j = 0 ; j < 7 ; j++) {
                grid[i][j] = null;
            }
        }
        Box treasure = new Box(3, 5, BoxTypeEnum.TREASURE);
        treasure.setTreasureNb(6);
        grid[3][5] = treasure;
        this.treasureMap.setGrid(grid);

        TreasureMap result = treasureMapService.decodeLine(this.treasureMap, "T - 3 - 5 - 6");

        assertTrue(result.getGrid()[3][5].isTreasureBox());
        assertEquals(6, result.getGrid()[3][5].getTreasureNb());
    }

    @Test
    void decodeLine_when_adventurer_line_should_add_adventurer_to_map() {

        this.treasureMap.setWidth(10);
        this.treasureMap.setHeight(10);
        Box[][] grid = new Box[10][10];
        this.treasureMap.setGrid(grid);

        Adventurer adventurer = new Adventurer("Bob", 5, 5, DirectionEnum.S, "AGAGA");
        this.treasureMap.setAdventurer(adventurer);

        TreasureMap result = treasureMapService.decodeLine(this.treasureMap, "A - Bob - 5 - 5 - S - AGAGA");

        Adventurer resultAdventurer = result.getAdventurer();
        assertEquals("Bob", resultAdventurer.getName());
        assertEquals(5, resultAdventurer.getX());
        assertEquals(5, resultAdventurer.getY());
        assertEquals(DirectionEnum.S, resultAdventurer.getDirection());
        assertEquals("AGAGA", resultAdventurer.getMovingSequence());
    }

    @Test
    void decodeLine_whenAdventurerLineWithExistingList_shouldAddToAdventurerList() {
        this.treasureMap.setWidth(5);
        this.treasureMap.setHeight(5);
        Box[][] grid = new Box[5][5];
        this.treasureMap.setGrid(grid);

        Adventurer adventurer = new Adventurer("Dylan", 3, 4, DirectionEnum.O, "GDA");
        this.treasureMap.setAdventurer(adventurer);

        TreasureMap result = treasureMapService.decodeLine(this.treasureMap, "A - Dylan - 3 - 4 - O - GDA");

        Adventurer resultAdventurer = result.getAdventurer();

        assertEquals("Dylan", resultAdventurer.getName());
        assertEquals(3, resultAdventurer.getX());
        assertEquals(4, resultAdventurer.getY());
        assertEquals(DirectionEnum.O, resultAdventurer.getDirection());
        assertEquals("GDA", resultAdventurer.getMovingSequence());
    }

    @Test
    void decodeLine_unkowwn_format_line_should_throw_UnknowFormatException() {
        this.treasureMap.setWidth(5);
        this.treasureMap.setHeight(5);
        Box[][] grid = new Box[5][5];
        this.treasureMap.setGrid(grid);

        assertThrows(UnknownLineFormat.class,()->{treasureMapService.decodeLine(this.treasureMap, "Y - 3 - 3 - P");});
    }

    @Test
    void decodeLine_mountainLine_with_null_Box_in_grid_should_throw_NullBoxException() {
        this.treasureMap.setWidth(5);
        this.treasureMap.setHeight(5);
        Box[][] grid = new Box[5][5];
        this.treasureMap.setGrid(grid);

        assertThrows(NullBoxException.class,()->{treasureMapService.decodeLine(this.treasureMap, "M - 1 - 1");});
    }

    @Test
    void decodeLine_treasureLine_with_null_Box_in_grid_should_throw_NullBoxException() {
        this.treasureMap.setWidth(5);
        this.treasureMap.setHeight(5);
        Box[][] grid = new Box[5][5];
        this.treasureMap.setGrid(grid);

        assertThrows(NullBoxException.class,()->{treasureMapService.decodeLine(this.treasureMap, "T - 2 - 1 - 2");});
    }
}
