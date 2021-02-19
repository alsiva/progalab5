import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import com.opencsv.exceptions.CsvValidationException;

import java.io.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.LinkedHashSet;
import java.util.Set;

class FileStorage {
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
            LocalDate birthday = LocalDate.parse(line[9], Application.BIRTHDAY_FORMATTER);

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

    public static void writeCsv(Set<StudyGroup> set, String filename) {
        try {
            CSVWriter writer = new CSVWriter(new PrintWriter(filename));

            for (StudyGroup studyGroup: set) {
                String[] line = new String[15];

                line[0] = studyGroup.getId().toString();
                line[1] = studyGroup.getName();

                Coordinates coordinates = studyGroup.getCoordinates();
                float x = coordinates.getX();
                int y = coordinates.getY();
                line[2] = Float.toString(x);
                line[3] = Integer.toString(y);

                java.util.Date creationDate = studyGroup.getCreationDate();
                String creationDateAsStr = creationDate.toString();
                line[4] = creationDateAsStr;

                int studentsCount = studyGroup.getStudentsCount();
                line[5] = Integer.toString(studentsCount);

                FormOfEducation formOfEducation = studyGroup.getFormOfEducation();
                line[6] = formOfEducation.toString();

                Semester semester = studyGroup.getSemesterEnum();
                line[7] = semester.toString();

                Person admin = studyGroup.getGroupAdmin();
                String adminName = admin.getName();
                line[8] = adminName;

                line[9] = admin.getBirthday().format(Application.BIRTHDAY_FORMATTER);

                String passportID = admin.getPassportID();
                line[10] = passportID;

                Location location = admin.getLocation();
                line[11] = location.toString();

                int xL = location.getX();
                line[12] = Integer.toString(xL);

                int yL = location.getY();
                line[13] = Integer.toString(yL);

                String locName = location.getLocationName();
                line[14] = locName;

                writer.writeNext(line);
            }

            writer.close();

        } catch (FileNotFoundException e) {
            System.err.println("Unable to save file");
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
