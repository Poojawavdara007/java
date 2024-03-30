import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

class Passenger {
    String name;
    String passportNumber;
    String gender;
    Double contactNo;
    int dob;

    public Passenger(String name, String passportNumber, String gender, Double contactNo, int dob) {
        this.name = name;
        this.passportNumber = passportNumber;
        this.gender = gender;
        this.contactNo = contactNo;
        this.dob = dob;
    }
}

class ReservationSystemBase {
    protected boolean[][] seats;
    protected int totalRows;
    protected int seatsPerRow;

    public ReservationSystemBase(int totalRows, int seatsPerRow) {
        this.totalRows = totalRows;
        this.seatsPerRow = seatsPerRow;
        seats = new boolean[totalRows][seatsPerRow];
        for (int i = 0; i < totalRows; i++) {
            for (int j = 0; j < seatsPerRow; j++) {
                seats[i][j] = false; // false indicates seat is available
            }
        }
    }

    public void displayAvailableSeats() {
        System.out.println("Available Seats:");
        System.out.print("    ");
        for (int j = 0; j < seatsPerRow; j++) {
            System.out.print("[" + (char) ('A' + j) + "] "); // Adding column labels with a space
        }
        System.out.println();
        for (int i = 0; i < totalRows; i++) {
            System.out.print((i + 1) + "   ");
            for (int j = 0; j < seatsPerRow; j++) {
                if (!seats[i][j]) {
                    System.out.print("[ ] "); // Space after each seat
                } else {
                    System.out.print("[X] "); // Space after each seat
                }
            }
            System.out.println();
        }
    }

    public boolean reserveSeat(int row, int seatNumber) {
        if (row < 1 || row > totalRows || seatNumber < 1 || seatNumber > seatsPerRow) {
            System.out.println("Invalid seat number");
            return false;
        }
        if (seats[row - 1][seatNumber - 1]) {
            System.out.println("Seat " + row + (char) ('A' + seatNumber - 1) + " is already reserved");
            return false;
        }
        seats[row - 1][seatNumber - 1] = true;
        System.out.println("Seat " + row + (char) ('A' + seatNumber - 1) + " reserved successfully");
        return true;
    }
}

class Flight {
    String flightNumber;
    String source;
    String destination;
    String departureTime;

    public Flight(String flightNumber, String source, String destination, String departureTime) {
        this.flightNumber = flightNumber;
        this.source = source;
        this.destination = destination;
        this.departureTime = departureTime;
    }
}

class FlightRepository {
    private ArrayList<Flight> flights;

    public FlightRepository() {
        flights = new ArrayList<>();

        flights.add(new Flight("FL001", "Delhi", "Mumbai", "2024-03-01 10:00"));
        flights.add(new Flight("FL002", "Mumbai", "Delhi", "2024-03-01 12:00"));
        flights.add(new Flight("FL003", "Bangalore", "Chennai", "2024-03-02 09:00"));
        flights.add(new Flight("FL004", "Kolkata", "Hyderabad", "2024-03-03 08:30"));
        flights.add(new Flight("FL005", "Hyderabad", "Kolkata", "2024-03-03 10:00"));
        flights.add(new Flight("FL006", "Chennai", "Bangalore", "2024-03-04 11:45"));
        flights.add(new Flight("FL007", "Pune", "Ahmedabad", "2024-03-04 14:00"));
        flights.add(new Flight("FL008", "Jaipur", "Lucknow", "2024-03-05 13:15"));
        flights.add(new Flight("FL009", "Lucknow", "Jaipur", "2024-03-05 15:30"));
        flights.add(new Flight("FL010", "Ahmedabad", "Pune", "2024-03-06 12:20"));
        flights.add(new Flight("FL011", "Chandigarh", "Goa", "2024-03-06 14:45"));
        flights.add(new Flight("FL012", "Goa", "Chandigarh", "2024-03-07 09:30"));
        flights.add(new Flight("FL013", "Kochi", "Bengaluru", "2024-03-07 11:00"));

        flights.add(new Flight("FL014", "Bengaluru", "Mumbai", "2024-03-08 08:15"));
        flights.add(new Flight("FL015", "Delhi", "Chennai", "2024-03-08 10:30"));
        flights.add(new Flight("FL016", "Hyderabad", "Kochi", "2024-03-09 12:45"));
        flights.add(new Flight("FL017", "Mumbai", "Kolkata", "2024-03-09 15:00"));
        flights.add(new Flight("FL018", "Chennai", "Delhi", "2024-03-10 17:15"));
        flights.add(new Flight("FL019", "Kolkata", "Bengaluru", "2024-03-10 19:30"));
    }

    // Method to search for flights based on source and destination
    public ArrayList<Flight> searchFlights(String source, String destination) {
        ArrayList<Flight> result = new ArrayList<>();
        for (Flight flight : flights) {
            if (flight.source.equalsIgnoreCase(source) && flight.destination.equalsIgnoreCase(destination)) {
                result.add(flight);
            }
        }
        return result;
    }
}

class User {
    private String username;
    private String password;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}

public class ReservationSystem extends ReservationSystemBase {
    private static ReservationSystem system = new ReservationSystem(10, 6);
    private static FlightRepository flightRepository = new FlightRepository();
    private static ArrayList<User> users = new ArrayList<>();

    public ReservationSystem(int totalRows, int seatsPerRow) {
        super(totalRows, seatsPerRow);
    }

    public void register(String username, String password) {
        // Check if username already exists
        for (User user : users) {
            if (user.getUsername().equals(username)) {
                System.out.println("Username already exists. Please choose a different one.");
                return;
            }
        }
        // Register user
        users.add(new User(username, password));
        System.out.println("User registered successfully.");
    }

    public void login(String username, String password) {
        // Check if username exists
        for (User user : users) {
            if (user.getUsername().equals(username)) {
                // Verify password
                if (user.getPassword().equals(password)) {
                    System.out.println("User logged in successfully.");
                    return;
                } else {
                    System.out.println("Incorrect password. Please try again.");
                    return;
                }
            }
        }
        System.out.println("Username not found. Please register first.");
    }

    public void addPassenger(String username, Passenger passenger) {
        // Check if username exists
        for (User user : users) {
            if (user.getUsername().equals(username)) {
                // Verify passenger details (e.g., passport number format, contact number
                // format, etc.)
                // For simplicity, let's assume all details are valid for now
                System.out.println("Passenger details added successfully.");
                return;
            }
        }
        System.out.println("Username not found. Please register first.");
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        ReservationSystem reservationSystem = new ReservationSystem(10, 6);
        boolean isLoggedIn = false;
        String loggedInUser = null;
        boolean ispassengerdetails=false;
        String passengerdetail = null; 

        while (true) {
            System.out.println("\n*** Flight Reservation System ***");
            System.out.println("1. Register");
            System.out.println("2. Search for Flights");
            System.out.println("3. Login");
            System.out.println("4. Make Reservations");
            System.out.println("5. Passenger Details");
            System.out.println("6. Exit");
            System.out.print("Enter your choice: ");

            try {
                int choice = scanner.nextInt();
                scanner.nextLine(); // Consume newline

                switch (choice) {
                    case 1: {
                        System.out.println("Welcome to Flight Reservation System - Register");
                        System.out.print("Enter username: ");
                        String regUsername = scanner.nextLine();
                        System.out.print("Enter password: ");
                        String regPassword = scanner.nextLine();
                        system.register(regUsername, regPassword);
                        break;
                    }
                    case 2: {
                        System.out.println("Search for Flights");
                        System.out.print("Enter source: ");
                        String source = scanner.nextLine();
                        System.out.print("Enter destination: ");
                        String destination = scanner.nextLine();
                        ArrayList<Flight> foundFlights = flightRepository.searchFlights(source, destination);
                        if (foundFlights.isEmpty()) {
                            System.out.println("No flights found for the given source and destination.");
                        } else {
                            System.out.println("Found Flights:");
                            for (Flight flight : foundFlights) {
                                System.out.println("Flight Number: " + flight.flightNumber);
                                System.out.println("Source: " + flight.source);
                                System.out.println("Destination: " + flight.destination);
                                System.out.println("Departure Time: " + flight.departureTime);
                                System.out.println();
                            }
                        }
                        break;
                    }
                    case 3: {
                        System.out.println("Welcome to Flight Reservation System - Login");
                        System.out.print("Enter username: ");
                        String loginUsername = scanner.nextLine();
                        System.out.print("Enter password: ");
                        String loginPassword = scanner.nextLine();
                        system.login(loginUsername, loginPassword);
                        isLoggedIn = true;
                        loggedInUser = loginUsername;
                        break;
                    }
                    case 4: {
                        if (!isLoggedIn ) {
                            System.out.println("Please login first.");
                            break;
                        }

                        if (!ispassengerdetails ) {
                            System.out.println("Please add passenger details first.");
                            break;
                        }

                        System.out.println("Welcome to Flight Reservation System - Make Reservation");
                        while (true) {
                            System.out.println("\n1. Display available seats");
                            System.out.println("2. Reserve a seat");
                            System.out.println("3. Exit");
                            System.out.print("Enter your choice: ");
                            int ch = scanner.nextInt();
                            switch (ch) {
                                case 1:
                                    reservationSystem.displayAvailableSeats();
                                    break;
                                case 2:    
                                    System.out.print("Enter the row number: ");
                                    int row = scanner.nextInt();
                                    System.out.print("Enter the seat number: ");
                                    int seatNumber = scanner.nextInt();
                                    boolean reserved = reservationSystem.reserveSeat(row, seatNumber);
                                    if (reserved) {
                                        System.out.println("Seat reserved successfully.");
                                    }
                                    break;
                                case 3:
                                    System.out.println("Thank you for using Flight Reservation System");
                                    scanner.close();
                                    System.exit(0);
                                    break;
                            }
                        }
                    }
                    case 5: {
                        if (!isLoggedIn) {
                            System.out.println("Please login first.");
                            break;
                        }

                        System.out.println(" Add Passenger Details");
                        System.out.print("Enter passenger name: ");
                        String passengerName = scanner.nextLine();

                        System.out.print("Enter passport number: ");
                        String passengerPassportNumber = scanner.nextLine();
                        System.out.println("Select gender:");
                        System.out.println("1. Male");
                        System.out.println("2. Female");
                        System.out.print("Enter the number corresponding to your choice: ");

                        int choic = scanner.nextInt();

                        // Clear the buffer
                        scanner.nextLine();

                        String gender;

                        // Validate the choice and assign the selected gender
                        switch (choic) {
                            case 1:
                                gender = "Male";
                                break;
                            case 2:
                                gender = "Female";
                                break;
                            default:
                                System.out.println("Invalid choice. Please select a valid option.");
                                scanner.close();
                                return;
                        }
                        System.out.println("Gender selected: " + gender);
                        
                        //contact
                        System.out.print("Enter contact number: ");
                        Double contactNo = scanner.nextDouble();
                        String contactStr = Double.toString(contactNo);
                        if (contactStr.length() == 10) {
                            System.out.println("Valid contact number: " + contactNo);
                        } else {
                            System.out.println("Invalid contact number. Please enter a 10-digit number.");
                        }
                        //birthdate
                        System.out.println("Enter date of birth:");

                        System.out.print("Year (YYYY): ");
                        int year = scanner.nextInt();
                
                        System.out.print("Month (MM): ");
                        int month = scanner.nextInt();
                
                        System.out.print("Day (DD): ");
                        int day = scanner.nextInt();
                
                        // Check if the input values are valid
                        if (isValidDate(year, month, day)) {
                            System.out.println("Date of birth: " + year + "-" + month + "-" + day);
                            // Proceed with the rest of your logic here
                        } else {
                            System.out.println("Invalid date. Please enter a valid date.");
                        }
                        
                        Passenger passenger = new Passenger(passengerName, passengerPassportNumber, gender, contactNo, year * 10000 + month * 100 + day);

                        system.addPassenger(loggedInUser, passenger);
                        ispassengerdetails = true;
                        passengerdetail = loggedInUser;
                        break;
                    }
                    case 6: {
                        System.out.println("Thank you for using Flight Reservation System");
                        scanner.close();
                        System.exit(0);
                        break;
                    }
                    default:
                        System.out.println("Invalid choice");
                        break;
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a valid number.");
                scanner.nextLine(); // Clear the input buffer
            }
        }
    }

    // Method to validate the input date
    public static boolean isValidDate(int year, int month, int day) {
        if (year < 0 || month < 1 || month > 12 || day < 1 || day > 31) {
            return false;
        }
        // Validate based on specific month lengths (not considering leap years for simplicity)
        if ((month == 4 || month == 6 || month == 9 || month == 11) && day > 30) {
            return false;
        } else if (month == 2) {
            if ((year % 4 == 0 && year % 100 != 0) || year % 400 == 0) {
                // Leap year
                return day <= 29;
            } else {
                // Non-leap year
                return day <= 28;
            }
        }
        return true;
    }
}
