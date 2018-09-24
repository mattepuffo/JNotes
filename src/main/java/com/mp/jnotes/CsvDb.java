package com.mp.jnotes;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.CSVRecord;
import org.apache.commons.io.FilenameUtils;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CsvDb {

    public void create(String inputFile, String[] header, List<Nota> list) throws IOException {
        String ext = FilenameUtils.getExtension(inputFile);
        if (ext.isEmpty()) {
            inputFile += ".csv";
        }
        try (BufferedWriter bw = Files.newBufferedWriter(Paths.get(inputFile))) {
            try (CSVPrinter csvPrinter = new CSVPrinter(bw, CSVFormat.DEFAULT.withHeader(header))) {
                for (Nota n : list) {
                    csvPrinter.printRecord(n.getId(), n.getTitolo(), n.getGruppo(), n.getTesto(), n.getAggiunta(), n.getModifica());
                }
                csvPrinter.flush();
            }
        }
    }

    public List<Nota> read(String inputFile) throws IOException {
        List<Nota> list = new ArrayList<>();
        try (Reader reader = Files.newBufferedReader(Paths.get(inputFile))) {
            try (CSVParser csvParser = new CSVParser(reader,
                    CSVFormat.DEFAULT.
                            withFirstRecordAsHeader().
                            withIgnoreHeaderCase().
                            withTrim())) {
                for (CSVRecord csvRecord : csvParser) {
                    Nota n = new Nota();
                    n.setId(csvRecord.get("titolo").hashCode());
                    n.setTitolo(csvRecord.get("titolo"));
                    n.setGruppo(csvRecord.get("gruppo"));
                    n.setTesto(csvRecord.get("testo"));
                    n.setAggiunta(new Date().toString());
                    n.setModifica(new Date().toString());
                    list.add(n);
                }
            }
        }
        return list;
    }
}
