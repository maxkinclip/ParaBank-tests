package data;


import com.github.javafaker.Faker;
import tests.api.models.RegistrationRequestModel;

import java.util.Locale;
import java.util.Random;

public class TestDataGenerator {
    private static final Faker faker = new Faker(new Locale("en-US"));
    private static final Random random = new Random();

    public static RegistrationRequestModel generateValidUser() {
        String firstName = generateEnglishFirstName();
        String lastName = generateEnglishLastName();
        String username = generateUsername(firstName, lastName);
        String password = faker.internet().password(8, 12);

        return RegistrationRequestModel.builder()
                .firstName(firstName)
                .lastName(lastName)
                .address(generateAddress())
                .city(generateCity())
                .state(generateState())
                .zipCode(generateZipCode())
                .phoneNumber(generatePhoneNumber())
                .ssnNumber(generateSsnNumber())
                .userName(username)
                .password(password)
                .passwordValidation(password)
                .build();
    }

    public static RegistrationRequestModel failedRegistrationMismatchedPasswords() {
        String firstName = generateEnglishFirstName();
        String lastName = generateEnglishLastName();
        String username = generateUsername(firstName, lastName);
        String password = faker.internet().password(8, 12);
        String invalidPassword = faker.internet().password(12,15);

        return RegistrationRequestModel.builder()
                .firstName(firstName)
                .lastName(lastName)
                .address(generateAddress())
                .city(generateCity())
                .state(generateState())
                .zipCode(generateZipCode())
                .phoneNumber(generatePhoneNumber())
                .ssnNumber(generateSsnNumber())
                .userName(username)
                .password(password)
                .passwordValidation(invalidPassword)
                .build();
    }



    private static String generateEnglishFirstName() {
        String[] englishNames = {"Antony", "George", "Dave", "Dominic", "Andrew", "Alex",
                "Max", "Neil", "Michael", "Steven", "Edgar", "Matthew"};

        String englishFirstName = englishNames[(new Random()).nextInt(englishNames.length)];
        String firstName = englishFirstName;
        if (firstName.length() > 15) {
            firstName = firstName.substring(0, 15);
        }

        return firstName;
    }

    private static String generateEnglishLastName() {
        String[] englishLastNames = {"Smith", "Foster", "Boyle", "Grant", "Grace", "Murphy",
                "Adams", "Gallagher", "White", "Clark", "Chase", "Fisher"};

        String englishLastName = englishLastNames[(new Random()).nextInt(englishLastNames.length)];
        String lastName = englishLastName;
        if (lastName.length() > 33) {
            lastName = lastName.substring(0, 33);
        }

        return lastName;
    }

    public static String generateValidEmail() {
        Faker englishFaker = new Faker(new Locale("en-US"));

        String username = englishFaker.name().username();
        String[] domains = {".com"};
        String domain = domains[random.nextInt(domains.length)];
        return username + "@example" + domain;
    }

    public static String generateAddress() {
        return faker.address().streetAddress();
    }

    public static String generateCity() {
        return faker.address().city();
    }

    public static String generateState() {
        return faker.address().state();
    }


    public static String generateZipCode() {
        return faker.address().zipCode();
    }


    public static String generatePhoneNumber() {
        String[] countryCodes = {"7", "8"};
        String prefix = countryCodes[random.nextInt(countryCodes.length)];
        String[] mobilePrefixes = {"900", "901", "902", "903", "904", "905", "906", "909",
                "910", "911", "912", "913", "914", "915", "916", "917", "919"};

        String mobilePrefix = mobilePrefixes[random.nextInt(mobilePrefixes.length)];
        String remainingDigits = faker.number().digits(7);

        return prefix + mobilePrefix + remainingDigits;
    }

    public static String generateSsnNumber() {
        return String.valueOf(faker.number().numberBetween(100000000, 999999999));
    }

    public static String generateUsername(String firstName, String lastName) {
        int year = faker.number().numberBetween(1930, 2030);
        return (firstName + "." + lastName + year)
                .replaceAll("[^A-Za-zА-Яа-я0-9.]", "")
                .toLowerCase();
    }





}
