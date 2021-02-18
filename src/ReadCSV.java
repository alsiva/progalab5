import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.LocalDateTime;
import java.util.LinkedHashSet;
import java.util.Set;

class ReadCSV {
    public static Set<StudyGroup> readCSV(String filename) throws IOException, CsvValidationException {
        LinkedHashSet<StudyGroup> set = new LinkedHashSet<>();

        CSVReader reader = new CSVReader(new InputStreamReader(new FileInputStream(filename)));
        reader.skip(1); // skip line with header

        String[] line;
        while ((line = reader.readNext()) != null) {
            long id = Long.parseLong(line[0]);
            String name = line[1];

            float x = Float.parseFloat(line[2]);
            int y = Integer.parseInt(line[3]);
            Coordinates coordinates = new Coordinates(x, y);

            java.util.Date creationDate = new java.util.Date(); // todo: check format

            int studentCount = Integer.parseInt(line[5]);
            FormOfEducation formOfEducation = FormOfEducation.valueOf(line[6]);
            Semester semester = Semester.valueOf(line[7]);

            String adminName = line[8];
            //add birthday
            String birthdayAsString = line[9]; //2002-02-15T19:25:13.856 пример того как выглядит
            LocalDateTime birthday = LocalDateTime.parse(birthdayAsString);//скорее всего здесь будет ошибка но пока забьём на это

            String passportId = line[10];

            int xi = Integer.parseInt(line[11]);
            Integer yi = Integer.parseInt(line[12]);
            String locName = line[13];
            Location location = new Location(xi, yi, locName);

            Person groupAdmin = new Person(adminName, birthday, passportId, location);

            StudyGroup studyGroup = new StudyGroup(
                    id,
                    name,
                    coordinates,
                    creationDate,
                    studentCount,
                    formOfEducation,
                    semester,
                    groupAdmin
            );

            set.add(studyGroup);
        }

        return set;
    }
}
