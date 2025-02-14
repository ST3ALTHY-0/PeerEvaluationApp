package edu.pui.peerEvaluation.Peerevualuationapplication.brightSpaceSCVParser;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class brightSpaceCSVParser {

    public CSVDataDTO parseDataFromCSV(MultipartFile csvFile) throws Exception{
        CSVDataDTO csvData = new CSVDataDTO();

        return csvData;
    }
    
}
