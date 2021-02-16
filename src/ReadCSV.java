import com.opencsv.CSVReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.LinkedHashSet;

class ReadCSV {

    public ReadCSV(LinkedHashSet<StudyGroup> set){
        try {
            CSVReader reader = new CSVReader(new InputStreamReader(new FileInputStream("source.csv")));
            String[] line;
            while ((line = reader.readNext()) != null) {

                StudyGroup studyGroup = new StudyGroup();
                set.add(studyGroup);

                studyGroup.setId(Long.parseLong(line[0]));
                studyGroup.setName(line[1]);

                float x = Float.parseFloat(line[2]);
                int y = Integer.parseInt(line[3]);
                studyGroup.setCoordinates(x, y);

                java.util.Date creationDate = new java.util.Date();//some somneniya here
                studyGroup.setCreationDate(creationDate);

                studyGroup.setStudentsCount(Integer.parseInt(line[5]));
                studyGroup.setFormOfEducation(FormOfEducation.valueOf(line[6]));
                studyGroup.setSemesterEnum(Semester.valueOf(line[7]));

                String adminName = line[8];
                //add birthday
                String birthdayAsString = line[9]; //2002-02-15T19:25:13.856 пример того как выглядит
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
                LocalDateTime dateTime = LocalDateTime.parse(birthdayAsString, formatter);//скорее всего здесь будет ошибка но пока забьём на это

                String passportId = line[10];

                int xi = Integer.parseInt(line[11]);
                Integer yi = Integer.parseInt(line[12]);
                String locName = line[13];
                Location location = new Location(xi, yi, locName);

                studyGroup.setGroupAdmin(adminName, dateTime, passportId, location);

            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
