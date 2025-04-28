package edu.pui.peerEvaluation.PeerEvaluationApplication.brightSpaceSCVParser;

import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.opencsv.CSVReader;
import edu.pui.peerEvaluation.PeerEvaluationApplication.orm.project.Project;
import edu.pui.peerEvaluation.PeerEvaluationApplication.orm.student.Student;

@Service
public class BrightSpaceCSVParser {

    private static final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(BrightSpaceCSVParser.class);

    public List<CSVData> parseDataFromCSV(MultipartFile csvFile) throws Exception {
        final List<CSVData> csvDataList = new ArrayList<>();
        final CSVReader reader = new CSVReader(new InputStreamReader(csvFile.getInputStream()));
        CSVProjectData csvProjectData = new CSVProjectData();
        String projectName = "";
    
        String[] headers = reader.readNext();
        if (headers == null) {
            reader.close();
            throw new Exception("CSV file is empty");
        }
    
        int subtotalNumeratorIndex = -1;
        int subtotalDenominatorIndex = -1;
    
        // Find indices and project name
        for (int i = 0; i < headers.length; i++) {
            if (headers[i].endsWith("Subtotal Numerator")) {
                subtotalNumeratorIndex = i;
                projectName = headers[i].substring(0, headers[i].indexOf("Subtotal Numerator")).trim();
            } else if (headers[i].endsWith("Subtotal Denominator")) {
                subtotalDenominatorIndex = i;
            }
        }
        if (subtotalNumeratorIndex == -1 || subtotalDenominatorIndex == -1) {
            projectName = headers[5].substring(0, headers[5].indexOf("Points Grade")).trim();
            // Note: maybe also handle the case where headers[5] isn't valid
        }
    
        // Get full project name
        String fullProjectName = headers[5];
        csvProjectData.setFullProjectName(fullProjectName);
        csvProjectData.setProjectName(projectName);
    
        // Read first data row separately for numerator and denominator
        String[] nextLine = reader.readNext();
        if (nextLine != null) {
            csvProjectData.setProjectNumerator(Integer.parseInt(nextLine[subtotalNumeratorIndex]));
            csvProjectData.setProjectDenominator(Integer.parseInt(nextLine[subtotalDenominatorIndex]));
    
            // Also add the first student record
            CSVData firstData = new CSVData();
            firstData.setPuid(nextLine[0]);
            firstData.setFirstName(nextLine[1]);
            firstData.setLastName(nextLine[2]);
            firstData.setStudentEmail(nextLine[3]);
            firstData.setLabGroup(nextLine[4]);
            firstData.setCsvProjectData(csvProjectData);
            csvDataList.add(firstData);
        }
    
        // Now continue reading the rest
        while ((nextLine = reader.readNext()) != null) {
            CSVData data = new CSVData();
            data.setPuid(nextLine[0]);
            data.setFirstName(nextLine[1]);
            data.setLastName(nextLine[2]);
            data.setStudentEmail(nextLine[3]);
            data.setLabGroup(nextLine[4]);
            data.setCsvProjectData(csvProjectData);
            csvDataList.add(data);
        }
    
        reader.close();
        return csvDataList;
    }
    
    public List<CSVDataDTO> transformData(List<CSVData> csvDataList) {
        return csvDataList.stream().map(csvData -> {

            CSVDataDTO dto = new CSVDataDTO();

            Student student = new Student();
            Project project = new Project();

            project.setProjectName(csvData.getCsvProjectData().getProjectName());
            project.setPointsWorth(csvData.getCsvProjectData().getProjectDenominator());
            project.setFullProjectName(csvData.getCsvProjectData().getFullProjectName());
            student.setPuid(csvData.getPuid().replace("#", ""));
            student.setStudentName(csvData.getFirstName() + " " + csvData.getLastName());
            student.setStudentEmail(csvData.getStudentEmail());

            dto.setGroup(csvData.getLabGroup());
            dto.setProject(project);
            dto.setStudent(student);
            dto.setStudentGrade(csvData.getCsvProjectData().getProjectNumerator());
            return dto;
        }).collect(Collectors.toList());
    }

    

}
