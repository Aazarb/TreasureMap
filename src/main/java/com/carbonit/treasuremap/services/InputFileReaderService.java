package com.carbonit.treasuremap.services;

import com.carbonit.treasuremap.exceptions.EmptyFileException;
import com.carbonit.treasuremap.exceptions.MissingFileException;
import com.carbonit.treasuremap.utils.FileOpener;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class InputFileReaderService {
    private FileOpener fileOpener;

    public InputFileReaderService(FileOpener opener) {
        this.fileOpener = opener;
    }


    public List<String> getAllLines(String fileName) throws IOException {
        List<String> lines = new ArrayList<>();
        int emptyLines = 0;
        int numberLines = 0;
        if (fileName == null) {
            throw new MissingFileException("Le nom du fichier est null");
        }

        try (BufferedReader bufferedReader = this.fileOpener.getBufferedReader(fileName)) {
            String line;
            while (((line = bufferedReader.readLine()) != null)) {
                if (line.isBlank()) {
                    emptyLines++;
                    numberLines++;
                } else {
                    lines.add(line.trim());
                    numberLines++;
                }
            }
            if (emptyLines == numberLines) {
                throw new EmptyFileException("Le fichier " + fileName + " est vide");
            }
            return lines;
        }

    }
}