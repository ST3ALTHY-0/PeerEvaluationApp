package edu.saveDataToCsv;

import com.opencsv.CSVWriter;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.util.List;

@Service
public class SaveDataToCSV {

    public byte[] generateCSV(List<String[]> data) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        OutputStreamWriter outputStreamWriter = new OutputStreamWriter(byteArrayOutputStream, StandardCharsets.UTF_8);
        CSVWriter csvWriter = new CSVWriter(outputStreamWriter);

        for (String[] record : data) {
            csvWriter.writeNext(record);
        }

        csvWriter.flush();
        csvWriter.close();

        return byteArrayOutputStream.toByteArray();
    }

}
