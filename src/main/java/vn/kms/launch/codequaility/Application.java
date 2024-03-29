package vn.kms.launch.codequaility;

import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.InputStream;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Application {
    private static final Set<String> VALID_STATE_CODES = new HashSet<>(
            Arrays.asList("AL", "AK", "AS", "AZ", "AR", "CA", "CO", "CT", "DE", "DC", "FL", "GA", "GU", "HI", "ID", "IL", "IN", "IA", "KS", "KY",
                    "LA", "ME", "MD", "MH", "MA", "MI", "FM", "MN", "MS", "MO", "MT", "NE", "NV", "NH", "NJ", "NM", "NY", "NC", "ND", "MP", "OH",
                    "OK", "OR", "PW", "PA", "PR", "RI", "SC", "SD", "TN", "TX", "UT", "VT", "VA", "VI", "WA", "WV", "WI", "WY"));
    private static int YEAR_OF_REPORT = 2016;
    private static final Map<String, String> REPORT_HEADERS_BY_REPORT_NAMES = new HashMap<String, String>() {
        {
            put("contact-per-state", "state_code\tnumber_of_contact\r\n");
            put("contact-per-age-group", "group\tnumber_of_contact\tpercentage_of_contact\r\n");
        }
    };

    public static void main(String[] args) throws Exception {
        /*Map<String, Integer> field_error_counts = new HashMap<>();
        Map reports = loadFileAndConvertToEntityAndValidateEntityAndStoreEntityAndReturnReports(field_error_counts);

        // 5. Store reports
        for (Object object : reports.keySet()) {
            String reportName = (String) object;
            Writer writer = new FileWriter("etc/" + reportName + ".tab");

            // write header
            writer.write(REPORT_HEADERS_BY_REPORT_NAMES.get(reportName));

            Map<String, Integer> report = (Map<String, Integer>) reports.get(reportName);
            if (reportName.equals("contact-per-age-group")) {
                int total = 0;
                for (Integer v : report.values()) {
                    total += v;
                }
                for (String item : report.keySet()) {
                    writer.write(item + "\t" + report.get(item) + "\t" + (int) ((report.get(item) * 100.0f) / total) + "%\r\n");
                }
            } else {
                for (String item : report.keySet()) {
                    writer.write(item + "\t" + report.get(item) + "\r\n");
                }
            }
            writer.flush();
            System.out.println("Generated report " + "etc/" + reportName + ".tab");
        }*/
        
        System.out.println(checkDate("01/00/1900"));
    }

    /**
     * @return array of total-lines, blank-lines, invalid-lines, valid-entities,
     *         invalid-entities, total-errors
     */
    private static Map loadFileAndConvertToEntityAndValidateEntityAndStoreEntityAndReturnReports(Map<String, Integer> counts) throws Exception {
        // 1. Load data from file using Stream API 
//        InputStream is = new FileInputStream("etc/contacts.tab");
//        char[] buff = new char[100000]; // guest file size is not greater than
//                                        // 100000 chars
//        int b; // read one character
//        int c = 0; // count total characters in file
//        while ((b = is.read()) != -1) {
//            buff[c] = (char) b;
//            c++;
//        }

//        String s = new String(buff, 0, c); // all data from file load to string
        List<String> lines;
        try ( Stream<String> stream = Files.lines(Paths.get("etc/big-contacts.tab"))) {
            lines = stream.collect(Collectors.toList());
            
        }
       
        
        
        
        
                                           // s
        //String[] lines = s.split("\r"); // get all lines
        List<Contact> allContacts = new ArrayList<>();
        for (int i = 0; i < lines.size(); i++) {
            // debug
            // System.out.println(lines[i]);

            if (i == 0) { // ignore header line
                continue;
            }

            // count blank line by trimming space characters and then check
            // length
            if (lines.get(i).trim().length() == 0) {
                continue;
            }

            String[] data = lines.get(i).split("\t"); // get data of a line

            if (data.length != 14) { // invalid line format
                continue;
            }

            // TODO: I will use refection & annotations to build contact object
            // later
            Contact contact = new Contact();

            try {
                // first column is ID
                // FIXME: Don't forget to change this code if first column is
                // not ID
                contact.setId(Integer.parseInt(data[0]));
            } catch (Exception ex) {
                // log error
                System.out.println("It is not ID format");
                continue;
            }

            // FIXME: need to change this code if the order of column is change
            contact.setFirstName(data[1]); // first_name
            contact.setLastName(data[2]); // last_name
            contact.setDateOfBirth(data[4]); // day_of_birth
            contact.setAddress(data[5]); // city
            contact.setCity(data[6]); // city
            contact.setState(data[8]); // state
            contact.setZipCode(data[9]); // zip
            contact.setMobilePhone(data[10]); // phone1
            contact.setEmail(data[12]); // email

            if (contact != null) {
                allContacts.add(contact);
            } else {
                // for some reason, I think contact object may be null (I am not
                // sure, but I want to log for sure!!!)
                System.out.println("Contact is null, don't know why!!!");
            }
        }

        // 2. Validate contact data
        // TODO: I will use refection & annotations to validate contact data
        // later
        Map<Integer, Map<String, String>> invalidContacts = new HashMap<>();
        for (Contact contact : allContacts) {
            Map<String, String> errors = new HashMap<>();
            if (contact.getFirstName().trim().length() == 0) {
                errors.put("firstName", "is empty");
                add_FieldERROR(counts, "firstName");
            }
            if (contact.getFirstName().length() > 10) {
                errors.put("firstName", "'" + contact.getFirstName() + "''s length is over 10");
                add_FieldERROR(counts, "firstName");
            }
            if (contact.getLastName().trim().length() == 0) {
                errors.put("lastName", "is empty");
                add_FieldERROR(counts, "lastName");
            }
            if (contact.getLastName().length() > 10) {
                errors.put("lastName", "'" + contact.getLastName() + "''s length is over 10");
                add_FieldERROR(counts, "lastName");
            }
            if (contact.getDateOfBirth() == null || contact.getDateOfBirth().trim().length() != 10 || !checkDate(contact.getDateOfBirth())) {
                
                errors.put("day_of_birth", "'" + contact.getDateOfBirth() + "' is invalid");
                add_FieldERROR(counts, "day_of_birth");
            }
            if (contact.getAddress().length() > 20) {
                errors.put("address", "'" + contact.getAddress() + "''s length is over 20");
                add_FieldERROR(counts, "address");
            }
            if (contact.getCity().length() > 15) {
                errors.put("city", "'" + contact.getCity() + "''s length is over 15");
                add_FieldERROR(counts, "city");
            }
            if (!VALID_STATE_CODES.contains(contact.getState())) {
                errors.put("state", "'" + contact.getState() + "' is incorrect state code");
                add_FieldERROR(counts, "state");
            }
            if (!contact.getZipCode().matches("^\\d{4,5}$")) {
                errors.put("zipCode", "'" + contact.getZipCode() + "' is not four or five digits");
                add_FieldERROR(counts, "zipCode");
            }
            if (!contact.getMobilePhone().matches("^\\d{3}\\-\\d{3}\\-\\d{4}$")) {
                errors.put("mobilePhone", "'" + contact.getMobilePhone() + "' is invalid format XXX-XXX-XXXX");
                add_FieldERROR(counts, "mobilePhone");
            }
            if (!contact.getEmail().matches("^.+@.+\\..+$")) {
                errors.put("email", "'" + contact.getEmail() + "' is invalid email format");
                add_FieldERROR(counts, "email");
            }

            if (!errors.isEmpty()) {
                invalidContacts.put(contact.getId(), errors);
            } else { // populate other fields from raw fields
                System.out.println(contact.getDateOfBirth());
                contact.setAge(preciseCalculateAgeByYear(contact.getDateOfBirth())); // age
            }
        }

        // 3. Sort contact by zipcode
        // TODO: need to change this code if we use other field to sort the list
        for (int i = 0; i < allContacts.size(); i++) {
            for (int j = allContacts.size() - 1; j > i; j--) {
                Contact contact_a = allContacts.get(i);
                Contact contact_b = allContacts.get(j);
                if (!invalidContacts.containsKey(contact_a.getId()) && !invalidContacts.containsKey(contact_b.getId())) {
                    int zip_a = Integer.parseInt(contact_a.getZipCode());
                    int zip_b = Integer.parseInt(contact_b.getZipCode());
                    if (zip_a > zip_b) {
                        allContacts.set(i, contact_b);
                        allContacts.set(j, contact_a);
                    }
                }
            }
        }

        // 4. Store valid data
        Writer writer1 = new FileWriter("etc/valid-contacts.tab");
        // write header
        writer1.write("id\tfirst_name\tlast_name\tday_of_birth\taddress\tcity\tstate\tzip_code\tmobile_phone\temail\r\n");
        for (Contact contact : allContacts) {
            if (!invalidContacts.containsKey(contact.getId())) {
                writer1.write(contact.toLine() + "\r\n");
            }
        }
        writer1.flush();

        // 5. Generate contact per state report
        Map reports = new HashMap<>();
        Map<String, Integer> r_contact_per_state = new HashMap<>();
        Map<String, Integer> r_contact_per_age_group = new HashMap<>();
        for (Contact contact : allContacts) {
            if (!invalidContacts.containsKey(contact.getId())) {
                int state_count = 0;
                if (r_contact_per_state.containsKey(contact.getState())) {
                    state_count = r_contact_per_state.get(contact.getState());
                }
                r_contact_per_state.put(contact.getState(), state_count + 1);

                int age_group_count = 0;
                if (r_contact_per_age_group.containsKey(calculate_age_group(contact.getAge()))) {
                    age_group_count = r_contact_per_age_group.get(calculate_age_group(contact.getAge()));
                }
                r_contact_per_age_group.put(calculate_age_group(contact.getAge()), age_group_count + 1);
            }
        }

        reports.put("contact-per-state", r_contact_per_state);
        reports.put("contact-per-age-group", r_contact_per_age_group);

        return reports;
    }

    private static void add_FieldERROR(Map<String, Integer> counts, String field_name) {
        Integer count = counts.get(field_name);
        if (count == null) {
            count = new Integer(0);
        }
        count = count + 1;
        counts.put(field_name, count);
    }

    /**
     * Calculate the age of contact by year. It's not accurate but acceptable
     * 
     * @param dateOfBirth
     * @return
     */
    public static int preciseCalculateAgeByYear(String dateOfBirth) {
        
        long age = ChronoUnit.YEARS.between(LocalDate.parse(dateOfBirth,
               DateTimeFormatter.ofPattern("MM/dd/yyyy")), LocalDate.now());

        return (int) age;
    }

    private static String calculate_age_group(int age) {
        if (age <= 9) {
            return "Children";
        } else if (age < 19) {
            return "Adolescent";
        } else if (age <= 45) {
            return "Adult";
        } else if (age <= 60) {
            return "Middle age";
        } else {

            return "Senior";
        }
    }
    
    public static boolean checkDate(String date) {
        SimpleDateFormat df = new SimpleDateFormat("mm/dd/yyyy", Locale.FRENCH);
        try {
            df.setLenient(false);
            df.parse(date);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
