/*


CAB Booking System using JAVA (Terminal based Interaction)


*/


import java.io.*;
import java.util.*;

public class CabBookingSystem {
    private static final double FARE_PER_KM_MINI = 10.0;
    private static final double FARE_PER_KM_SUV = 15.0;
    private static final double FARE_PER_KM_SEDAN = 12.0;
    private static final String CREDENTIALS_FILE = "credentials.txt";
    private static final String BOOKING_DETAILS_FILE = "booking_details.txt";

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Random random = new Random();

        // To Load user credentials from file
        Map<String, String> userCredentials = loadCredentials();

        // Main Menu
        while (true) {
            System.out.println("\n==================================");
            System.out.println("|        CAB BOOKING SYSTEM       |");
            System.out.println("==================================");

            System.out.println("\n        Main Menu");
            System.out.println("1. Create an account");
            System.out.println("2. Log in");
            System.out.println("3. Exit");
            System.out.println("----------------------------------");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); 
            switch (choice) {
                case 1:
                    createAccount(scanner, userCredentials);
                    break;
                case 2:
                    boolean isAuthenticated = login(scanner, userCredentials);
                    if (isAuthenticated) {
                        System.out.println("\n----------------------------------");
                        System.out.println("|        Login successful!        |");
                        System.out.println("----------------------------------");
                        
                        // Continue with cab booking system
                        bookCab(scanner, random, userCredentials);
                    } else {
                        System.out.println("\n----------------------------------");
                        System.out.println("|  Login failed. Invalid credentials. |");
                        System.out.println("----------------------------------");
                    }
                    break;
                case 3:
                    System.out.println("\n----------------------------------");
                    System.out.println("|           Exiting...            |");
                    System.out.println("----------------------------------");
                    scanner.close(); // Close the scanner before exiting
                    return;
                default:
                    System.out.println("\n----------------------------------");
                    System.out.println("|   Invalid choice. Please try again.  |");
                    System.out.println("----------------------------------");
            }
        }
    }

    private static Map<String, String> loadCredentials() {
        Map<String, String> credentials = new HashMap<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(CREDENTIALS_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                credentials.put(parts[0], parts[1]);
            }
        } catch (IOException e) {
            System.out.println("Error loading credentials: " + e.getMessage());
        }
        return credentials;
    }

    private static void createAccount(Scanner scanner, Map<String, String> userCredentials) {
        System.out.println("\n==================================");
        System.out.println("|          Create Account         |");
        System.out.println("==================================");
        System.out.print("Enter username: ");
        String username = scanner.nextLine();
        System.out.print("Enter password: ");
        String password = scanner.nextLine();

        // Check if username already exists
        if (userCredentials.containsKey(username)) {
            System.out.println("\n----------------------------------");
            System.out.println("|  Username already exists. Please choose a different username.  |");
            System.out.println("----------------------------------");
            return;
        }

        // Store the username and password in the map
        userCredentials.put(username, password);
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(CREDENTIALS_FILE, true))) {
            writer.write(username + "," + password);
            writer.newLine();
        } catch (IOException e) {
            System.out.println("Error writing credentials: " + e.getMessage());
        }
        System.out.println("\n----------------------------------");
        System.out.println("|   Account created successfully!  |");
        System.out.println("----------------------------------");
    }

    private static boolean login(Scanner scanner, Map<String, String> userCredentials) {
        System.out.println("\n==================================");
        System.out.println("|             Log in              |");
        System.out.println("==================================");
        System.out.print("Enter username: ");
        String username = scanner.nextLine();
        System.out.print("Enter password: ");
        String password = scanner.nextLine();

        // Check if the provided username exists and the password matches
        return userCredentials.containsKey(username) && userCredentials.get(username).equals(password);
    }

    private static void bookCab(Scanner scanner, Random random, Map<String, String> userCredentials) {
        // Cab Booking System
        System.out.println("\n==================================");
        System.out.println("|        Cab Booking System      |");
        System.out.println("==================================");

        // Choose vehicle type
        System.out.println("Choose your vehicle type: ");
        System.out.println("1. Mini (Ritz, Swift, Indica)");
        System.out.println("2. SUV/MUV (Innova Crysta, Ertiga, Marazzo) ");
        System.out.println("3. Sedan (Swift Dzire, Etios, Xcent)");
        System.out.println("----------------------------------");
        System.out.print("Enter your choice: ");
        int vehicleTypeChoice = scanner.nextInt();
        scanner.nextLine(); // Consume newline character

        double farePerKm;
        String vehicleType;
        String[] vehicleModels = null;
        switch (vehicleTypeChoice) {
            case 1:
                farePerKm = FARE_PER_KM_MINI;
                vehicleType = "Mini";
                vehicleModels = new String[]{"Ritz", "Swift", "Indica"};
                break;
            case 2:
                farePerKm = FARE_PER_KM_SUV;
                vehicleType = "SUV";
                vehicleModels = new String[]{"Innova Crysta", "Ertiga", "Marazzo","Carens"};
                break;
            case 3:
                farePerKm = FARE_PER_KM_SEDAN;
                vehicleType = "Sedan";
                vehicleModels = new String[]{"Swift Dzire", "Etios", "Xcent","Nissan Sunny"};
                break;
            default:
                System.out.println("\n----------------------------------");
                System.out.println("|   Invalid choice. Choosing Mini by default.  |");
                System.out.println("----------------------------------");
                farePerKm = FARE_PER_KM_MINI;
                vehicleType = "Mini";
                vehicleModels = new String[]{"Ritz", "Swift", "Indica"};
        }

        // Randomly select a vehicle model
        String vehicleModel = vehicleModels[random.nextInt(vehicleModels.length)];

        // Ask for pickup location
        System.out.println("\n==================================");
        System.out.println("|       Enter Pickup Location    |");
        System.out.println("==================================");
        System.out.print("Pickup location: ");
        String pickupLocation = scanner.nextLine();

        // Ask for destination
        System.out.println("\n==================================");
        System.out.println("|         Enter Destination       |");
        System.out.println("==================================");
        System.out.print("Destination: ");
        String destination = scanner.nextLine();

        // Ask for approximate distance
        System.out.println("\n==================================");
        System.out.println("|  Enter Approximate Distance (in km) |");
        System.out.println("==================================");
        System.out.print("Approximate distance between pickup and drop location (in km): ");
        double distance = scanner.nextDouble();
        scanner.nextLine(); // Consume newline character

        // Calculate total fare
        double totalFare = distance * farePerKm;

        // Display fare bill and vehicle details
        System.out.println("\n==================================");
        System.out.println("|            Fare Bill            |");
        System.out.println("==================================");
        System.out.println("Your fare for booking a " + vehicleType + " (" + vehicleModel + ") to " + destination + " is: $" + totalFare);

        // Simulate searching for nearby cabs and assigning a driver
        String[] drivers = {"Rocky", "Adheera", "Varma", "Rocky", "Sharmila"};
        String[] vehicleNumbers = {"KA03LP2563", "KA52LM3945", "KA09TM5698", "TN52KL3698"};
        int driverIndex = random.nextInt(drivers.length);
        String driverName = drivers[driverIndex];
        String vehicleNumber = vehicleNumbers[driverIndex];

        System.out.println("\n==================================");
        System.out.println("|        Driver Information       |");
        System.out.println("==================================");
        System.out.println("Driver assigned: " + driverName);
        System.out.println("Vehicle Number: " + vehicleNumber);

        // Ask for confirmation
        System.out.println("\n==================================");
        System.out.println("|       Confirm Booking?         |");
        System.out.println("==================================");
        System.out.print("Confirm booking? (yes/no): ");
        String confirmation = scanner.nextLine();

        if (confirmation.equalsIgnoreCase("yes")) {
            System.out.println("\n----------------------------------");
            System.out.println("|  Booking confirmed! Your " + vehicleType + " (" + vehicleModel + ") with driver " + driverName + " will arrive shortly.  |");
            System.out.println("----------------------------------");

            // Ask if the driver has arrived
            System.out.println("\n==================================");
            System.out.println("|  Has the driver arrived?       |");
            System.out.println("==================================");
            System.out.print("Has the driver arrived? (yes/no): ");
            String driverArrived = scanner.nextLine();

            if (driverArrived.equalsIgnoreCase("yes")) {
                System.out.println("\n----------------------------------");
                System.out.println("|  Enjoy your ride!              |");
                //System.out.println("|  \"Life is a journey, not a destination.\"  |");
                //System.out.println("|        - Ralph Waldo Emerson    |");
                System.out.println("----------------------------------");

                // Ask if the customer has reached the destination
                System.out.println("\n==================================");
                System.out.println("|   Have you reached the destination?  |");
                System.out.println("==================================");
                System.out.print("Have you reached the destination? (yes/no): ");
                String reachedDestination = scanner.nextLine();

                if (reachedDestination.equalsIgnoreCase("yes")) {
                    System.out.println("\n==================================");
                    System.out.println("|      Feedback and Rating       |");
                    System.out.println("==================================");
                    System.out.print("Rate your ride experience from 1 to 5 (5 being excellent): ");
                    int rating = scanner.nextInt();
                    scanner.nextLine(); // Consume newline character
                    System.out.println("\n----------------------------------");
                    System.out.println("|  Thank you for your feedback!  |");
                    System.out.println("----------------------------------");

                    // Append booking details and feedback to booking details file
                    try (BufferedWriter writer = new BufferedWriter(new FileWriter(BOOKING_DETAILS_FILE, true))) {
                        writer.write("Booking Details:\n");
                        writer.write("User: " + userCredentials.keySet().iterator().next() + "\n");
                        writer.write("Vehicle Type: " + vehicleType + "\n");
                        writer.write("Vehicle Model: " + vehicleModel + "\n");
                        writer.write("Driver: " + driverName + "\n");
                        writer.write("Destination: " + destination + "\n");
                        writer.write("Total Fare: $" + totalFare + "\n");
                        writer.write("Feedback: " + rating + "\n\n");
                    } catch (IOException e) {
                        System.out.println("Error writing booking details: " + e.getMessage());
                    }

                } else {
                    System.out.println("\n================================================");
                    System.out.println("|                 SOS Emergency!                 |");
                    System.out.println("|  Emergency services will get in touch with you |");
                    System.out.println("=================================================");
                }
            } else {
                // Connect to driver via random number
                String driverPhoneNumber = "+91" +""+ (random.nextInt(900000000) + 1000000000);
                System.out.println("\n----------------------------------");
                System.out.println("|  Connecting you to the driver at " + driverPhoneNumber + "   |");
                System.out.println("----------------------------------");
            }
        } else {
            System.out.println("\n----------------------------------");
            System.out.println("|    Booking canceled. Thank you!  |");
            System.out.println("------------------------------------");
        }
    }
}
