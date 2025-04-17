package com.carbonit.treasuremap.services;

import com.carbonit.treasuremap.exceptions.EmptyFileException;
import com.carbonit.treasuremap.exceptions.MissingFileException;
import com.carbonit.treasuremap.utils.FileOpener;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@SpringBootTest
class InputFileReaderServiceTest {
    @Autowired
    private InputFileReaderService inputFileReaderService;


    @Test
    void should_return_error_message_when_filename_is_null() {
        String fileName = null;

        try {
            inputFileReaderService.getAllLines(fileName);
        } catch (Exception e) {
            assertEquals("Le nom du fichier est null", e.getMessage());
        }
    }

    @Test
    void should_throw_IOException_when_filename_is_null() {
        String fileName = null;
        assertThrows(MissingFileException.class,()-> {inputFileReaderService.getAllLines(fileName);});
    }

    @Test
    void should_return_valid_lines_when_file_has_valid_lines() {
        String fileName = "fichier_valide.txt";
        FileOpener fileOpenerMock = mock(FileOpener.class);
        BufferedReader bufferedReaderMock = new BufferedReader(new StringReader("ligne1\nligne2"));

        try{
            when(fileOpenerMock.getBufferedReader(fileName)).thenReturn(bufferedReaderMock);
        } catch (IOException e) {
            fail("Préparation du test échoué : IOException");
        }
        this.inputFileReaderService = new InputFileReaderService(fileOpenerMock);

        List<String> result = null;
        try{
            result = this.inputFileReaderService.getAllLines(fileName);
        } catch (Exception e) {
            fail("La methode getAllLines a levé une exception : "+e.getMessage());
        }

        assertNotNull(result);
        assertEquals(2,result.size());
        assertEquals("ligne1",result.get(0));
        assertEquals("ligne2",result.get(1));
    }

    @Test
    void should_throw_EmptyFileException_when_file_is_empty() {
        String fileName = "fichier_vide.txt";
        FileOpener fileOpenerMock = mock(FileOpener.class);
        BufferedReader bufferedReaderMock = new BufferedReader(new StringReader("\n "));

        try{
            when(fileOpenerMock.getBufferedReader(fileName)).thenReturn(bufferedReaderMock);
        } catch (IOException e) {
            fail("Préparation du test échoué : IOException");
        }
        this.inputFileReaderService = new InputFileReaderService(fileOpenerMock);
        assertThrows(EmptyFileException.class,()->{this.inputFileReaderService.getAllLines(fileName);});

    }
}