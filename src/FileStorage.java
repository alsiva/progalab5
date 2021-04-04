import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import com.opencsv.exceptions.CsvValidationException;

import java.io.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * class responsible for reading/writing csv file
 */
class FileStorage {
    public Set<StudyGroup> readCSV(String filename) throws IOException, CsvValidationException, FailedToParseException {

        LinkedHashSet<StudyGroup> set = new LinkedHashSet<>();
        Set<Long> alreadyAddedIds = new HashSet<>();

        CSVReader reader = new CSVReader(new InputStreamReader(new FileInputStream(filename)));
        reader.skip(1); // skip line with header

        String[] line;
        while ((line = reader.readNext()) != null) {

            long id;
            try {
                id = Long.parseLong(line[0]);
            } catch (IllegalArgumentException e) {
                throw new FailedToParseException("Failed to read id: " + e.getMessage());
            }


            if (!alreadyAddedIds.add(id)) {
                System.err.println("Повторяющиеся id");
                System.exit(1);
            }

            String name = line[1];

            float x;
            try {
               x = Float.parseFloat(line[2]);
            } catch (IllegalArgumentException e) {
                throw new FailedToParseException("Failed to read x: " + e.getMessage());
            }

            int y;
            try {
                y = Integer.parseInt(line[3]);
            } catch (IllegalArgumentException e) {
                throw new FailedToParseException("Failed to read y: " + e.getMessage());
            }
            Coordinates coordinates = new Coordinates(x, y);

            java.util.Date creationDate = new java.util.Date();

            int studentCount;
            try {
                studentCount = Integer.parseInt(line[5]);
            } catch (IllegalArgumentException e) {
                throw new FailedToParseException("Failed to read students count: " + e.getMessage());
            }

            FormOfEducation formOfEducation;
            try {
                formOfEducation = FormOfEducation.valueOf(line[6]);
            } catch (IllegalArgumentException e) {
                throw new FailedToParseException("Failed to read formOfEducation: " + e.getMessage());
            }

            Semester semester;
            try {
                semester = Semester.valueOf(line[7]);
            } catch (IllegalArgumentException e) {
                throw new FailedToParseException("Failed to read semester: " + e.getMessage());
            }

            String adminName = line[8];

            LocalDate birthday;
            try {
                birthday = LocalDate.parse(line[9], CommandReader.BIRTHDAY_FORMATTER);
            } catch (IllegalArgumentException e) {
                throw new FailedToParseException("Failed to read birthday: " + e.getMessage());
            }

            String passportId = line[10];

            int xi;
            try {
                xi = Integer.parseInt(line[11]);
            } catch (IllegalArgumentException e) {
                throw new FailedToParseException("Failed to read x: " + e.getMessage());
            }

            Integer yi;
            try {
                yi = Integer.parseInt(line[12]);
            } catch (IllegalArgumentException e) {
                throw new FailedToParseException("Failed to read y: " + e.getMessage());
            }
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

    public void writeCsv(Set<StudyGroup> set, String filename) {
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

                line[9] = admin.getBirthday().format(CommandReader.BIRTHDAY_FORMATTER);

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
