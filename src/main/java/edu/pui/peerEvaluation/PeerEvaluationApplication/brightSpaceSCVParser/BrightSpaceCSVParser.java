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

    public List<CSVData> parseDataFromCSV(MultipartFile csvFile) throws Exception {
        final List<CSVData> csvDataList = new ArrayList<>();
        final CSVReader reader = new CSVReader(new InputStreamReader(csvFile.getInputStream()));
        String projectName = "";

        String[] headers = reader.readNext();

        int subtotalNumeratorIndex = -1;
        int subtotalDenominatorIndex = -1;

        // find the indexs of subtotal grades, becuase the header title changes based on
        // the project name
        for (int i = 0; i < headers.length; i++) {
            if (headers[i].endsWith("Subtotal Numerator")) {
                subtotalNumeratorIndex = i;
                // find the project name
                projectName = headers[i].substring(0, headers[i].indexOf("Subtotal Numerator")).trim();

            } else if (headers[i].endsWith("Subtotal Denominator")) {
                subtotalDenominatorIndex = i;
            }
        }
        if (subtotalNumeratorIndex == -1 || subtotalDenominatorIndex == -1) {
            reader.close();
            throw new Exception("Required columns not found in CSV");
        }

        // set the CSVData with the indexs of the headers (csv must be a certain way)
        String[] nextLine;
        while ((nextLine = reader.readNext()) != null) {
            CSVData data = new CSVData();
            data.setPuid(nextLine[0]);
            data.setFirstName(nextLine[1]);
            data.setLastName(nextLine[2]);
            data.setStudentEmail(nextLine[3]);
            data.setLabGroup(nextLine[4]);
            data.setProjectNumerator(Integer.parseInt(nextLine[subtotalNumeratorIndex]));
            data.setProjectDenominator(Integer.parseInt(nextLine[subtotalDenominatorIndex]));

            data.setProjectName(projectName);
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

            project.setProjectName(csvData.getProjectName());
            project.setPointsWorth(csvData.getProjectDenominator());
            student.setPuid(csvData.getPuid().replace("#", ""));
            student.setStudentName(csvData.getFirstName() + " " + csvData.getLastName());
            student.setStudentEmail(csvData.getStudentEmail());

            dto.setGroup(csvData.getLabGroup());
            dto.setProject(project);
            dto.setStudent(student);
            dto.setStudentGrade(csvData.getProjectNumerator());
            return dto;
        }).collect(Collectors.toList());
    }

    

}
