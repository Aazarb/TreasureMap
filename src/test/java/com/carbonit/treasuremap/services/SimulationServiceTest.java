package com.carbonit.treasuremap.services;

import com.carbonit.treasuremap.enums.BoxTypeEnum;
import com.carbonit.treasuremap.enums.DirectionEnum;
import com.carbonit.treasuremap.exceptions.UnknownInstructionException;
import com.carbonit.treasuremap.models.Adventurer;
import com.carbonit.treasuremap.models.Box;
import com.carbonit.treasuremap.models.TreasureMap;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class SimulationServiceTest {
    @Autowired
    private SimulationService simulationService;

    private Adventurer adventurer;
    private Box[][] grid;

    @BeforeEach
    void setUp() {
        this.adventurer = new Adventurer("Hugo", 0, 0, DirectionEnum.S);
        this.grid = new Box[5][5];
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                this.grid[i][j] = new Box(i, j, BoxTypeEnum.PLAIN);
            }
        }
    }

    @Test
    void goStraight_to_mountainBox() {
        Box mountainBox = new Box(0, 1, BoxTypeEnum.MOUNTAIN);
        this.grid[0][1] = mountainBox;
        simulationService.goStraight(grid, this.adventurer);
        assertEquals(0, this.adventurer.getX());
        assertEquals(0, this.adventurer.getY());
    }

    @Test
    void goStraight_to_treasureBox() {
        Box treasureBox = new Box(1, 1, 1, BoxTypeEnum.TREASURE);
        this.grid[1][1] = treasureBox;
        this.adventurer.setX(1);
        this.adventurer.setY(2);
        this.adventurer.setDirection(DirectionEnum.N);
        simulationService.goStraight(this.grid, this.adventurer);
        assertEquals(1, this.adventurer.getX());
        assertEquals(1, this.adventurer.getY());
        assertEquals(1, this.adventurer.getTreasureFound());
        assertEquals(0, grid[1][1].getTreasureNb());
        assertEquals(BoxTypeEnum.PLAIN, grid[1][1].getBoxType());
    }

    @Test
    void goStraight_to_treasurBox_with_multiples_treasures() {
        Box treasureBox = new Box(2, 2, 3, BoxTypeEnum.TREASURE);
        this.grid[2][2] = treasureBox;
        this.adventurer.setX(1);
        this.adventurer.setY(2);
        this.adventurer.setDirection(DirectionEnum.E);
        simulationService.goStraight(this.grid, this.adventurer);
        assertEquals(2, this.adventurer.getX());
        assertEquals(2, this.adventurer.getY());
        assertEquals(1, this.adventurer.getTreasureFound());
        assertEquals(2, grid[2][2].getTreasureNb());
        assertEquals(BoxTypeEnum.TREASURE, grid[2][2].getBoxType());
    }

    @Test
    void runSequence_AGD() {
        this.adventurer.setMovingSequence("AGD");
        TreasureMap treasureMap = new TreasureMap();
        treasureMap.setGrid(this.grid);
        simulationService.runSequence(this.adventurer, treasureMap);

        assertEquals(0, this.adventurer.getX());
        assertEquals(1, this.adventurer.getY());
    }

    @Test
    void runSequence_unknown_sequence() {
        this.adventurer.setMovingSequence("MLKFSD");
        TreasureMap treasureMap = new TreasureMap();
        treasureMap.setGrid(this.grid);

        try {
            simulationService.runSequence(this.adventurer, treasureMap);
        } catch (RuntimeException e) {

            assertEquals("Instruction non reconnue : [M] !", e.getMessage());
        }
    }

    @Test
    void runSequence_unknown_sequence_should_throw_RuntimeException() {
        this.adventurer.setMovingSequence("MLKFSD");
        TreasureMap treasureMap = new TreasureMap();
        treasureMap.setGrid(this.grid);

        assertThrows(RuntimeException.class, () -> simulationService.runSequence(this.adventurer, treasureMap));
    }

    @Test
    void turnLeft_from_north() {
        this.adventurer.setDirection(DirectionEnum.N);
        simulationService.turnLeft(adventurer);

        assertEquals(DirectionEnum.O, adventurer.getDirection());
    }


    @Test
    void turnLeft_from_west() {
        this.adventurer.setDirection(DirectionEnum.O);
        simulationService.turnLeft(this.adventurer);

        assertEquals(DirectionEnum.S, adventurer.getDirection());
    }

    @Test
    void turnLeft_from_south() {
        this.adventurer.setDirection(DirectionEnum.S);
        simulationService.turnLeft(this.adventurer);

        assertEquals(DirectionEnum.E, this.adventurer.getDirection());
    }

    @Test
    void turnLeft_from_east() {
        this.adventurer.setDirection(DirectionEnum.E);
        simulationService.turnLeft(this.adventurer);

        assertEquals(DirectionEnum.N, this.adventurer.getDirection());
    }

    @Test
    void turnRight_from_north() {
        this.adventurer.setDirection(DirectionEnum.N);
        simulationService.turnRight(adventurer);

        assertEquals(DirectionEnum.E, adventurer.getDirection());
    }

    @Test
    void turnRight_from_east() {
        this.adventurer.setDirection(DirectionEnum.E);
        simulationService.turnRight(adventurer);

        assertEquals(DirectionEnum.S, adventurer.getDirection());
    }

    @Test
    void turnRight_from_south() {
        this.adventurer.setDirection(DirectionEnum.S);
        simulationService.turnRight(adventurer);

        assertEquals(DirectionEnum.O, adventurer.getDirection());
    }

    @Test
    void turnRight_from_west() {
        this.adventurer.setDirection(DirectionEnum.O);
        simulationService.turnRight(adventurer);

        assertEquals(DirectionEnum.N, adventurer.getDirection());
    }

    @Test
    void getNextBox_from_north() {
        this.adventurer.setX(2);
        this.adventurer.setY(2);
        this.adventurer.setDirection(DirectionEnum.N);

        Box expected = this.grid[2][1];
        Box actual = this.simulationService.getNextBox(this.grid, this.adventurer);

        assertEquals(expected, actual);
    }

    @Test
    void getNextBox_from_south() {
        this.adventurer.setX(2);
        this.adventurer.setY(2);
        this.adventurer.setDirection(DirectionEnum.S);

        Box expected = this.grid[2][3];
        Box actual = this.simulationService.getNextBox(this.grid, this.adventurer);

        assertEquals(expected, actual);
    }

    @Test
    void getNextBox_from_east() {
        this.adventurer.setX(2);
        this.adventurer.setY(2);
        this.adventurer.setDirection(DirectionEnum.E);

        Box expected = this.grid[3][2];
        Box actual = this.simulationService.getNextBox(this.grid, this.adventurer);

        assertEquals(expected, actual);
    }

    @Test
    void getNextBox_from_west() {
        this.adventurer.setX(2);
        this.adventurer.setY(2);
        this.adventurer.setDirection(DirectionEnum.O);

        Box expected = this.grid[1][2];
        Box actual = this.simulationService.getNextBox(this.grid, this.adventurer);

        assertEquals(expected, actual);
    }

    @Test
    void getNextBox_from_north_out_of_grid_should_return_null() {
        this.adventurer.setX(0);
        this.adventurer.setY(0);
        this.adventurer.setDirection(DirectionEnum.N);

        Box actual = this.simulationService.getNextBox(this.grid, this.adventurer);

        assertNull(actual);
    }

    @Test
    void getNextBox_from_west_out_of_grid_should_return_null() {
        this.adventurer.setX(0);
        this.adventurer.setY(0);
        this.adventurer.setDirection(DirectionEnum.O);

        Box actual = this.simulationService.getNextBox(this.grid, this.adventurer);

        assertNull(actual);
    }

    @Test
    void getNextBox_from_south_out_of_grid_should_return_null() {
        this.adventurer.setX(4);
        this.adventurer.setY(4);
        this.adventurer.setDirection(DirectionEnum.S);

        Box actual = this.simulationService.getNextBox(this.grid, this.adventurer);

        assertNull(actual);
    }

    @Test
    void getNextBox_from_east_out_of_grid_should_return_null() {
        this.adventurer.setX(4);
        this.adventurer.setY(4);
        this.adventurer.setDirection(DirectionEnum.E);

        Box actual = this.simulationService.getNextBox(this.grid, this.adventurer);

        assertNull(actual);
    }
    @Test
    void runSequence_unknonwn_instruction_should_throw_UnknownInstructionException(){
        this.adventurer.setMovingSequence("POM");
        TreasureMap treasureMap = new TreasureMap();
        treasureMap.setGrid(this.grid);
        assertThrows(UnknownInstructionException.class,()->{simulationService.runSequence(this.adventurer, treasureMap);});

    }

}