package com.carbonit.treasuremap.services;

import com.carbonit.treasuremap.enums.BoxTypeEnum;
import com.carbonit.treasuremap.enums.DirectionEnum;
import com.carbonit.treasuremap.exceptions.UnknownInstructionException;
import com.carbonit.treasuremap.models.Adventurer;
import com.carbonit.treasuremap.models.Box;
import com.carbonit.treasuremap.models.TreasureMap;
import org.springframework.stereotype.Service;

@Service
public class SimulationService {

    public Box getNextBox(Box[][] grid, Adventurer a){
        int newX = a.getX();
        int newY = a.getY();

        switch (a.getDirection()){
            case O -> newX -= 1;
            case E -> newX += 1;
            case N -> newY -= 1;
            case S -> newY += 1;
        }

        if (newX >= 0 && newX < grid.length &&
                newY >= 0 && newY < grid[0].length){
            return grid[newX][newY];
        }
        return null;
    }

    public void goStraight(Box[][] grid, Adventurer av){
        Box nextBox = getNextBox(grid,av);

        // Cas d'une case montage ou case null
        if (nextBox == null || nextBox.isMountainBox()) {
            return;
        }
        // Cas de la Case trésor
        if (nextBox.isTreasureBox() && nextBox.getTreasureNb() > 0) {
            av.setTreasureFound(av.getTreasureFound() + 1);
            nextBox.setTreasureNb(nextBox.getTreasureNb() - 1);
            if(nextBox.getTreasureNb() == 0){
                nextBox.setBoxType(BoxTypeEnum.PLAIN);
                grid[nextBox.getX()][nextBox.getY()]= nextBox;
            }
        }

        // Mise à jour de la position de l'aventurier
        av.setX(nextBox.getX());
        av.setY(nextBox.getY());
    }
    public void turnLeft(Adventurer av) {
        switch (av.getDirection()) {
            case N: av.setDirection(DirectionEnum.O); break;
            case O: av.setDirection(DirectionEnum.S); break;
            case S: av.setDirection(DirectionEnum.E); break;
            case E: av.setDirection(DirectionEnum.N); break;
        }
    }
    public void turnRight(Adventurer av) {
        switch (av.getDirection()) {
            case N: av.setDirection(DirectionEnum.E); break;
            case O: av.setDirection(DirectionEnum.N); break;
            case S: av.setDirection(DirectionEnum.O); break;
            case E: av.setDirection(DirectionEnum.S); break;
        }
    }

    public void runSequence(Adventurer adventurer, TreasureMap treasureMap) {
        Box[][] grid = treasureMap.getGrid();


        for (char instruction : adventurer.getMovingSequence().toCharArray()) {
            switch (instruction) {
                case 'A':
                    goStraight(grid, adventurer);
                    treasureMap.setGrid(grid);
                    break;

                case 'G':
                    this.turnLeft(adventurer);
                    break;

                case 'D':
                    this.turnRight(adventurer);
                    break;
                default:
                    throw new UnknownInstructionException("Instruction non reconnue : [" + instruction + "] !");
            }
        }

    }
}