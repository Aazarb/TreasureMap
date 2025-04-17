package com.carbonit.treasuremap.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LineProcessingUtils {
    public List<Integer> extractFiguresFromMapTreasureMountainLine(String mapLine) {

        // Motif regex qui reconnaît une suite d’un ou plusieurs chiffres
        Pattern pattern = Pattern.compile("\\d+");
        Matcher matcher = pattern.matcher(mapLine);

        List<Integer> figures = new ArrayList<>();
        while (matcher.find()) {
            figures.add(Integer.parseInt(matcher.group()));
        }
        return figures;
    }

    public List<String> extractAdventurerInfo(String line){
        line = line.trim();

        // Motif regex pour reconnaître une ligne d’aventurier : commence par 'A', capture le nom, l’orientation (lettre),
        // et la séquence de mouvements (A, G, D), ignore les coordonnées, gère les espaces et tirets optionnels entre les champs
        Pattern pattern = Pattern.compile("^A\\s*-\\s*(.+)\\s*-\\s*\\d+\\s*-\\s*\\d+\\s*-\\s*([A-Z])\\s*-\\s*([AGD]+)\\s*$");
        Matcher matcher = pattern.matcher(line);

        List<String> adventurerInfo = new ArrayList<>();

        if(matcher.find()){
            adventurerInfo.add(matcher.group(1).trim()); // Nom de l'aventurier
            adventurerInfo.add(matcher.group(2).trim()); // Orientation
            adventurerInfo.add(matcher.group(3).trim()); // Sequence de mouvements
        }

        return adventurerInfo;
    }
}
