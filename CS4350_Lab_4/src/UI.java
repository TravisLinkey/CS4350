import java.util.Scanner;

public class UI {
    Scanner input = new Scanner(System.in);

    void main_menu() {
        System.out.println();
        System.out.println("+-------------------------------------------------------------+");
        System.out.println("+                       Main Menu                             +");
        System.out.println("+-------------------------------------------------------------+");
        System.out.println("|    [1] Display Trip Schedules       [5] Record a Trip       |");
        System.out.println("|    [2] Display Trip Stops           [6] Add a driver        |");
        System.out.println("|    [3] Display Driver Schedule      [7] Add a bus           |");
        System.out.println("|    [4] Edit Schedules               [8] Delete a bus        |");
        System.out.println("|                                                             |");
        System.out.println("|    [9] Quit                                                 |");
        System.out.println("+-------------------------------------------------------------+");
    }
    public void display_schedule_menu() {
        System.out.println();
        System.out.println("+-------------------------------+");
        System.out.println("+       Schedule Menu           +");
        System.out.println("+-------------------------------+");
        System.out.println("|    [1] Delete a Trip          |");
        System.out.println("|    [2] Add a Trip             |");
        System.out.println("|    [3] Change Trip Driver     |");
        System.out.println("|    [4] Change Bus             |");
        System.out.println("|                               |");
        System.out.println("|    [5] Quit                   |");
        System.out.println("+-------------------------------+");
    }
    public int get_valid_response(int low, int high) {
        boolean is_valid=false;
        int ans=0;

        while(!is_valid) {
            String answer = input.nextLine();
            ans = Integer.parseInt(answer);

            if(ans >= low && ans <= high)
            {
                is_valid = true;
                break;
            }
            System.out.println("Invalid input. Must be an integer in the range: [" + low + "-" + high + "]");
        }

        return ans;
    }
    public int get_bus_year() {
        int bus_year=0;
        boolean year_valid = false;
        System.out.println("Please input the year of the bus:");

        while(!year_valid) {
            bus_year = get_valid_response(1900,2020);
            if(bus_year > 1900 && bus_year < 2020)
                year_valid = true;
        }
        input.nextLine();

        return bus_year;
    }
    public int get_trip_number() {
        int trip_number=0;
        boolean is_valid=false;

        System.out.println("Please input a Trip Number:");
        trip_number = get_valid_response(1,100);

        return trip_number;
    }
    public int get_stop_number() {
        int stop_number=0;
        System.out.println("Please input the stop number:");
        stop_number = get_valid_response(1,100);

        return stop_number;
    }
    public int get_num_passangers_in() {
        int stop_number=0;
        System.out.println("Please input the number of passangers in:");
        stop_number = get_valid_response(1,300);

        return stop_number;
    }
    public int get_num_passangers_out() {
        int stop_number=0;
        System.out.println("Please input the number of passangers out:");
        stop_number = get_valid_response(1,300);

        return stop_number;
    }
    public String get_start_location() {
        String start_location = null;
        System.out.println("Please enter a starting location:");
        start_location = input.nextLine();

        return start_location;
    }
    public String get_destination() {
        String destination = null;
        System.out.println("Please enter a destination:");
        destination = input.nextLine();

        return destination;
    }
    public String get_date() {
        String date = null;
        System.out.println("Please enter a date:");
        date = input.nextLine();

        return date;
    }
    public String get_driver_name() {
        String name = null;
        System.out.println("Please enter a Driver name: ");
        name = input.nextLine();

        return name;
    }
    public String get_driver_phone_number() {
        String phone_number = null;
        System.out.println("Please enter the Driver's phone number: ");
        phone_number = input.nextLine();

        return phone_number;
    }
    public String get_bus_ID() {
        String bus_ID = null;
        System.out.println("Please input the BusID:");
        bus_ID = input.nextLine();

        return bus_ID;
    }
    public String get_bus_model() {
        String bus_model = null;
        System.out.println("Please input Bus Model:");
        bus_model = input.nextLine();

        return bus_model;
    }
    public String get_scheduled_start_time() {
        String scheduled_start = null;
        System.out.println("Please input the scheduled start time: YYYY-MM-DD HH-MM-SS");
        scheduled_start = input.nextLine();

        return scheduled_start;
    }
    public String get_scheduled_arrival_time() {
        String scheduled_start = null;
        System.out.println("Please input the scheduled arrival time: YYYY-MM-DD HH-MM-SS");
        scheduled_start = input.nextLine();

        return scheduled_start;
    }
    public String get_actual_start_time() {
        String scheduled_start = null;
        System.out.println("Please input the actual start time: YYYY-MM-DD HH-MM-SS");
        scheduled_start = input.nextLine();

        return scheduled_start;
    }
    public String get_actual_arrival_time() {
        String scheduled_start = null;
        System.out.println("Please input the actual arrival time: YYYY-MM-DD HH-MM-SS");
        scheduled_start = input.nextLine();

        return scheduled_start;
    }
    public boolean ask_trip_offering() {
        System.out.println("Would you like to input another Trip Offering?");
        System.out.println("    [1] Yes");
        System.out.println("    [0] No");
        int ans = get_valid_response(0,1);
        if(ans == 1)
            return true;
        else
            return false;
    }
}
