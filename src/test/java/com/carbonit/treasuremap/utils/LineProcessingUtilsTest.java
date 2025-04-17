package com.carbonit.treasuremap.utils;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class LineProcessingUtilsTest {
    LineProcessingUtils utils = new LineProcessingUtils();

    @Test
    void should_extract_two_numbers_from_mountain_line(){
        String line = "M - 2 - 3";
        List<Integer> result = utils.extractFiguresFromMapTreasureMountainLine(line);
        assertEquals(List.of(2,3), result,"Doit extraire les deux coordonnées d'une ligne montagne");
    }

    @Test
    void should_extract_three_numbers_from_treasure_line(){
        String line = "T - 1 - 4 - 2";
        List<Integer> result = utils.extractFiguresFromMapTreasureMountainLine(line);
        assertEquals(List.of(1,4,2), result,"Doit extraire les deux coordonnées d'une ligne trésor et le nombre de trésors");
    }

    @Test
    void should_return_empty_list(){
        String line = "M - x - y";
        List<Integer> result = utils.extractFiguresFromMapTreasureMountainLine(line);
        assertTrue(result.isEmpty(),"Doit retourner une liste vide");
    }

    @Test
    void should_handle_extra_space(){
        String line = "M - 5   -     9";
        List<Integer> result = utils.extractFiguresFromMapTreasureMountainLine(line);
        assertEquals(List.of(5,9), result,"\"Doit ignorer les espaces supplémentaires et extraire les nombres\"");
    }

    @Test
    void shouldParseAventurierLineCorrectly() {
        String line = "A - Alice - 1 - 3 - N - AGDAG";
        List<String> result = utils.extractAdventurerInfo(line);
        assertEquals(List.of("Alice", "N", "AGDAG"), result, "Doit extraire le nom, l’orientation et les mouvements");
    }

    @Test
    void shouldTrimSpacesInAventurierLine() {
        String line = "A  -   Bob   - 0 - 1 - E - GAGG";
        List<String> result = utils.extractAdventurerInfo(line);
        assertEquals(List.of("Bob", "E", "GAGG"), result, "Doit gérer les espaces superflus autour des champs");
    }

    @Test
    void shouldReturnEmptyListForInvalidAventurierLine() {
        String line = "A - Carl - one - 1 - S - AGAGA";
        List<String> result = utils.extractAdventurerInfo(line);
        assertTrue(result.isEmpty(), "Doit retourner une liste vide pour un format incorrect");
    }

    @Test
    void shouldReturnEmptyListForMissingFields() {
        String line = "A - Dave - 1 - 1 - D";
        List<String> result = utils.extractAdventurerInfo(line);
        assertTrue(result.isEmpty(), "Doit retourner une liste vide si des champs manquent");
    }

}