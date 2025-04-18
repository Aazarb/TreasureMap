package com.carbonit.treasuremap;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class TreasuremapApplication {
    private static final Logger logger = LoggerFactory.getLogger(TreasuremapApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(TreasuremapApplication.class, args);

        // Initialisation des services
        FileOpener fileOpener = new FileOpener();
        InputFileReaderService inputFileReaderService = new InputFileReaderService(fileOpener);
        SimulationService simulationService = new SimulationService();
        OutputMapService outputMapService = new OutputMapService();

        List<String> lines = new ArrayList<>();

        // Définition de la carte à ouvrir
        String fileName = "carte.txt";

        // Lecture de la carte
        try {
            lines = inputFileReaderService.getAllLines(fileName);
        } catch (IOException e) {
            throw new RuntimeException("Une erreur s'est produite lors de la lecture du fichier "+fileName+": " + e);
        }
        logger.info("************** Lecture du fichier {} **************", fileName);

        //Initialisation de l'object carte au trésors pour le décode des lignes
        TreasureMapService treasureMapService = new TreasureMapService();
        TreasureMap treasureMap = new TreasureMap();

        //Affichage de chaque ligne puis decode de celle-ci
        for (String line : lines) {
            logger.info(line);
            treasureMap = treasureMapService.decodeLine(treasureMap, line);
        }

        logger.info("************** Carte aux trésors {} **************", fileName);

        if (logger.isInfoEnabled()) {
            logger.info(outputMapService.drawMap(treasureMap));
        }

        //Simulation du mouvement de l'aventurier
        simulationService.runSequence(treasureMap.getAdventurer(), treasureMap);

        logger.info("************** Sortie **************");

		if(logger.isInfoEnabled()){
			logger.info(outputMapService.writingMap(treasureMap));
			logger.info(outputMapService.drawMap(treasureMap));
		}
    }
}
