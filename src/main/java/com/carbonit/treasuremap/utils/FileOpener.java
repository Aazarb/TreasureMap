package com.carbonit.treasuremap.utils;

import com.carbonit.treasuremap.exceptions.MissingFileException;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

@Component
public class FileOpener {

    public BufferedReader getBufferedReader(String fileName) throws IOException {
        ClassPathResource classPathResource = new ClassPathResource(fileName);
        if(!classPathResource.exists()){
            throw new MissingFileException("Le fichier " + fileName + " est introuvable");
        }
        return new BufferedReader(new FileReader(classPathResource.getFile()));
    }
}
