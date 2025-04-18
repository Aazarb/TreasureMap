package com.carbonit.treasuremap.services;

import com.carbonit.treasuremap.enums.BoxTypeEnum;
import com.carbonit.treasuremap.models.Adventurer;
import com.carbonit.treasuremap.models.Box;
import com.carbonit.treasuremap.models.TreasureMap;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class OutputMapService {

    public String drawMap(TreasureMap treasureMap) {
        Box[][] grid = treasureMap.getGrid();
        int rows = grid.length;     // Nombre de lignes (axe vertical)
        int columns = grid[0].length; // Nombre de colonnes (axe horizontal)

        // Initialisation de la carte en considérant ses cases comme des PLAINES
        String[][] drawnMap = drawPlainMap(columns, rows);

        // Remplir les montagnes et trésors en transposant les coordonnées
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                Box currentCase = grid[i][j];

                if (currentCase.isMountainBox()) {
                    drawnMap[j][i] = "M";  // Transposition: [j][i] au lieu de [i][j]
                } else if (currentCase.isTreasureBox()) {
                    drawnMap[j][i] = "T(" + currentCase.getTreasureNb() + ")";  // Transposition
                }
            }
        }
        // Ajouter les aventuriers avec transposition
        if(treasureMap.getAdventurer() != null) {
            Adventurer adventurer = treasureMap.getAdventurer();

                int adventurerRow = adventurer.getX();
                int adventurerColumn = adventurer.getY();

                // Transposition des coordonnées
                drawnMap[adventurerColumn][adventurerRow] = "A(" + adventurer.getName() + ")";
        }

        return getStringDrawnMap(columns, rows, drawnMap);
    }

    private static String getStringDrawnMap(int columns, int rows, String[][] drawnMap) {

        // Construction de la string finale
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < columns; i++) {  // Parcourir d'abord les colonnes (qui deviennent lignes)
            for (int j = 0; j < rows; j++) {  // Puis les lignes (qui deviennent colonnes)
                stringBuilder.append(drawnMap[i][j]);
                if (j < rows - 1) {
                    stringBuilder.append("   ");
                }
            }
            if (i < columns - 1) {
                stringBuilder.append("\n");
            }
        }
        return stringBuilder.toString();
    }

    private static String[][] drawPlainMap(int columns, int rows) {

        // Créer une matrice transposée pour l'affichage

        String[][] plainMap = new String[columns][rows];  // Inverser dimensions pour la transposition

        // Dessiner toutes les cases comme des plaines
        for (int i = 0; i < columns; i++) {
            for (int j = 0; j < rows; j++) {
                plainMap[i][j] = ".";
            }
        }
        return plainMap;
    }

    public String writingMap(TreasureMap treasureMap) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("\n");

        List<Box> listTreasureBoxes = Arrays.stream(treasureMap.getGrid())
                .flatMap(Arrays::stream)                             // Aplati chaque ligne en un flux de Box (Box[])
                .filter(boxItem -> boxItem.getBoxType() == BoxTypeEnum.TREASURE)
                .toList();


        List<Box> listMountainBoxes = Arrays.stream(treasureMap.getGrid())
                .flatMap(Arrays::stream)
                .filter(boxItem -> boxItem.getBoxType() == BoxTypeEnum.MOUNTAIN)
                .toList();

        stringBuilder.append("C - ").append(treasureMap.getWidth()).append(" - ").append(treasureMap.getHeight()).append("\n");
        for( Box mountainBox : listMountainBoxes){
            stringBuilder.append("M - ").append(mountainBox.getX()).append(" - ").append(mountainBox.getY()).append("\n");
        }

        for( Box tresor : listTreasureBoxes){
            stringBuilder.append("T - ").append(tresor.getX()).append(" - ").append(tresor.getY()).append(" - ").append(tresor.getTreasureNb()).append("\n");
        }
        if(treasureMap.getAdventurer() != null) {
            Adventurer adventurer = treasureMap.getAdventurer();
            stringBuilder.append("A - ").append(adventurer.getName()).append(" - ").append(adventurer.getX())
                    .append(" - ").append(adventurer.getY()).append(" - ").append(adventurer.getDirection().toString())
                    .append(" - ").append(adventurer.getTreasureFound()).append("\n");
        }

        return stringBuilder.toString().trim();
    }
}
