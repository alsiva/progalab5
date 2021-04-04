import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import com.opencsv.exceptions.CsvValidationException;

import java.io.*;
import java.time.LocalDate;
import java.util.*;

/**
 * class responsible for reading/writing csv file
 */
class FileStorage {
    protected Map<CSVField, Integer> headers = new HashMap<>();

    class LineEntry {
        private final String[] line;

        LineEntry(String[] line) {
            this.line = line;
        }

        public String readField(CSVField field) {
            Integer i = headers.get(field);
            if (i == null) {
                throw new IllegalArgumentException("Field " + field + " does not exits");
            }

            return line[i];
        }
    }

    private LineEntry readLineEntry(CSVReader reader) throws IOException, CsvValidationException {
        String[] line = reader.readNext();
        if (line == null) {
            return null;
        }

        return new LineEntry(line);
    }

    private Map<CSVField, Integer> readHeaders(CSVReader reader) throws IOException, CsvValidationException {
        Map<CSVField, Integer> headers = new HashMap<>();

        // reading the header
        String[] line = reader.readNext();
        for (int i = 0; i < line.length; i++) {
            String fieldName = line[i];
            CSVField field = CSVField.getFieldByName(fieldName);
            if (field == null) {
                throw new IllegalArgumentException("unknown field: " + fieldName);
            }

            headers.put(field, i);
        }

        if (headers.size() != CSVField.values().length) {
            throw new IllegalArgumentException("not all fields are presented in csv file");
        }

        return headers;
    }

    public Set<StudyGroup> readCSV(String filename) throws IOException, CsvValidationException, FailedToParseException {

        LinkedHashSet<StudyGroup> set = new LinkedHashSet<>();
        Set<Long> alreadyAddedIds = new HashSet<>();

        CSVReader reader = new CSVReader(new InputStreamReader(new FileInputStream(filename)));

        headers = readHeaders(reader);

        LineEntry lineEntry;
        while ((lineEntry = readLineEntry(reader)) != null) {
            long id = StudyGroup.readId(lineEntry.readField(CSVField.ID));

            if (!alreadyAddedIds.add(id)) {
                System.err.println("id " + id + " already exists; skipping");
                continue;
            }

            String name = StudyGroup.readName(lineEntry.readField(CSVField.NAME));

            float x = Coordinates.readX(lineEntry.readField(CSVField.COORDINATE_X));
            int y = Coordinates.readY(lineEntry.readField(CSVField.COORDINATE_Y));

            Coordinates coordinates = new Coordinates(x, y);

            java.util.Date creationDate = new java.util.Date();

            int studentCount = StudyGroup.readStudentsCount(lineEntry.readField(CSVField.STUDENTS_COUNT));

            FormOfEducation formOfEducation = StudyGroup.readFormOfEducation(lineEntry.readField(CSVField.FORM_OF_EDUCATION));

            Semester semester = StudyGroup.readSemester(lineEntry.readField(CSVField.SEMESTER));

            Person groupAdmin = readGroupAdmin(lineEntry);

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

    private Person readGroupAdmin(LineEntry lineEntry) throws FailedToParseException {
        String adminName = lineEntry.readField(CSVField.GROUP_ADMIN_NAME);
        if (adminName.isEmpty()) {
            throw new FailedToParseException("admin name could not be null in a file");
        }

        LocalDate birthday = Person.readAdminBirthday(lineEntry.readField(CSVField.GROUP_ADMIN_BIRTHDAY));

        String passportId = Person.readPassportID(lineEntry.readField(CSVField.GROUP_ADMIN_PASSPORT_ID));
        Location location = readLocation(lineEntry);

        return new Person(adminName, birthday, passportId, location);
    }

    private Location readLocation(LineEntry lineEntry) throws FailedToParseException {
        Integer adminLocationX = Location.readX(lineEntry.readField(CSVField.GROUP_ADMIN_LOCATION_X));
        if (adminLocationX == null) {
            throw new FailedToParseException("admin location x could not be skipped in file");
        }

        int adminLocationY = Location.readY(lineEntry.readField(CSVField.GROUP_ADMIN_LOCATION_Y));

        String locName = lineEntry.readField(CSVField.GROUP_ADMIN_LOCATION_NAME);

        return new Location(adminLocationX, adminLocationY, locName);
    }

    class LineWriter {
        private final String[] line = new String[CSVField.values().length];

        void writeField(CSVField field, String fieldValue) {
            Integer i = headers.get(field);
            if (i == null) {
                throw new IllegalArgumentException("Field " + field + " does not exits");
            }

            line[i] = fieldValue;
        }

        public String[] getLine() {
            return line;
        }
    }

    public void writeCsv(Set<StudyGroup> set, String filename) {
        try {
            CSVWriter writer = new CSVWriter(new PrintWriter(filename));

            for (StudyGroup studyGroup: set) {
                LineWriter lineWriter = new LineWriter();

                lineWriter.writeField(CSVField.ID, studyGroup.getId().toString());
                lineWriter.writeField(CSVField.NAME, studyGroup.getName());

                Coordinates coordinates = studyGroup.getCoordinates();
                float x = coordinates.getX();
                int y = coordinates.getY();
                lineWriter.writeField(CSVField.COORDINATE_X, Float.toString(x));
                lineWriter.writeField(CSVField.COORDINATE_Y, Integer.toString(y));

                java.util.Date creationDate = studyGroup.getCreationDate();
                String creationDateAsStr = creationDate.toString();
                lineWriter.writeField(CSVField.CREATION_DATE, creationDateAsStr);

                int studentsCount = studyGroup.getStudentsCount();
                lineWriter.writeField(CSVField.STUDENTS_COUNT, Integer.toString(studentsCount));

                FormOfEducation formOfEducation = studyGroup.getFormOfEducation();
                lineWriter.writeField(CSVField.FORM_OF_EDUCATION, formOfEducation.toString());

                Semester semester = studyGroup.getSemesterEnum();
                lineWriter.writeField(CSVField.SEMESTER, semester.toString());

                Person admin = studyGroup.getGroupAdmin();
                String adminName = admin.getName();
                lineWriter.writeField(CSVField.GROUP_ADMIN_NAME, adminName);

                lineWriter.writeField(CSVField.GROUP_ADMIN_BIRTHDAY, admin.getBirthday().format(CommandReader.BIRTHDAY_FORMATTER));

                String passportID = admin.getPassportID();
                lineWriter.writeField(CSVField.GROUP_ADMIN_PASSPORT_ID, passportID);

                Location location = admin.getLocation();

                int xL = location.getX();
                lineWriter.writeField(CSVField.GROUP_ADMIN_LOCATION_X, Integer.toString(xL));

                int yL = location.getY();
                lineWriter.writeField(CSVField.GROUP_ADMIN_LOCATION_Y, Integer.toString(yL));

                String locName = location.getLocationName();
                lineWriter.writeField(CSVField.GROUP_ADMIN_LOCATION_NAME, locName);

                writer.writeNext(lineWriter.getLine());
            }

            writer.close();

        } catch (FileNotFoundException e) {
            System.err.println("Unable to save file");
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
