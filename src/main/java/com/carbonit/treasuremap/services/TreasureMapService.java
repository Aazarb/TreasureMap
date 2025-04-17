package com.carbonit.treasuremap.services;

import com.carbonit.treasuremap.enums.BoxTypeEnum;
import com.carbonit.treasuremap.enums.DirectionEnum;
import com.carbonit.treasuremap.exceptions.NullBoxException;
import com.carbonit.treasuremap.exceptions.UnknownLineFormatException;
import com.carbonit.treasuremap.models.Adventurer;
import com.carbonit.treasuremap.models.Box;
import com.carbonit.treasuremap.models.TreasureMap;
import com.carbonit.treasuremap.utils.LineProcessingUtils;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TreasureMapService {

    public static final LineProcessingUtils lineProcessingUtils = new LineProcessingUtils();

    public TreasureMap decodeLine(TreasureMap treasureMap,String line){
        if(line.charAt(0) == '#'){
            return null;
        }

        char[] listeLigne = line.toCharArray();
        List<Integer> nombre = lineProcessingUtils.extractFiguresFromMapTreasureMountainLine(line);

        int x = nombre.get(0);
        int y = nombre.get(1);
        Box[][] existingGrid = null;
        if(treasureMap.getGrid() != null){
            existingGrid = treasureMap.getGrid();
        }

        switch(listeLigne[0]) {
            case 'C':
                treasureMap.setWidth(x);
                treasureMap.setHeight(y);
                Box[][] grid = gridInit(x, y);
                treasureMap.setGrid(grid);
                break;
            case 'M':
                Box mountainBox = new Box(x, y, BoxTypeEnum.MOUNTAIN);
                if (existingGrid[x][y] == null) {
                    throw new NullBoxException("La case de coordonnées (" + x + "," + y + ") est null");
                }
                existingGrid[x][y] = mountainBox;
                treasureMap.setGrid(existingGrid);
                break;
            case 'T':
                int treasureNb = nombre.get(2);
                Box treasure = new Box(x, y, BoxTypeEnum.TREASURE);
                treasure.setTreasureNb(treasureNb);

                if (existingGrid[x][y] == null) {
                    throw new NullBoxException("La case de coordonnées (" + x + "," + y + ") est null");
                }
                existingGrid[x][y] = treasure;
                treasureMap.setGrid(existingGrid);

                break;
            case 'A':
                List<String> adventurerInfo = lineProcessingUtils.extractAdventurerInfo(line);

                DirectionEnum direction = DirectionEnum.valueOf(adventurerInfo.get(1));
                Adventurer adventurer = new Adventurer(adventurerInfo.get(0), x, y, direction, adventurerInfo.get(2));

                treasureMap.setAdventurer(adventurer);
                break;
            default:
                throw new UnknownLineFormatException("Le format de ligne n'est pas reconnue");
        }
        return treasureMap;
    }

    public Box[][] gridInit(int x,int y){
        Box[][] grid = new Box[x][y];
        for (int i = 0; i < x; i++) {
            for (int j = 0; j < y; j++) {
                grid[i][j] = new Box(i, j, BoxTypeEnum.PLAIN);  // Type de case par défault
            }
        }
        return grid;
    }
}
