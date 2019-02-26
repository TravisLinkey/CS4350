import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Engine {
    DB_Connector db_connector;
    UI user_interface;

    Engine() {
        db_connector = new DB_Connector();
        user_interface = new UI();
        this.start_program();
    }
    private void start_program() {
        boolean is_finished = false;
        int ans=0;

        while(!is_finished) {
            user_interface.main_menu();
            ans = user_interface.get_valid_response(1,9);

            System.out.println("You chose: " + ans);

            switch (ans) {
                case 1:
                {
                    display_trip_schedules();
                    break;
                }
                case 2:
                    display_trip_stops();
                    break;
                case 3:
                    display_driver_schedule();
                    break;
                case 4:
                    edit_schedules();
                    break;
                case 5:
                    record_trip_schedule();
                    break;
                case 6:
                    add_driver();
                    break;
                case 7:
                    add_bus();
                    break;
                case 8:
                    delete_bus();
                    break;
                case 9:
                    is_finished=true;
                    break;
            }
        }
    }
    private void display_trip_schedules() {
        String start_location = user_interface.get_start_location();
        String destination = user_interface.get_destination();
        String date = user_interface.get_date();

        System.out.println("Checking trip schedule for:");
        System.out.println("Starting Location: " + start_location);
        System.out.println("Destination: " + destination);
        System.out.println("Date: " + date);

        Statement stmt = null;
        String query = "Select T.ScheduledStartTime, T.ScheduledArrivalTime, T.DriverName, T.BusID " +
                " From TripOffering T, Trip Tr " +
                " Where T.TripNumber = Tr.TripNumber " +
                " And T.Date = "+  "'" + date + "'" +
                " And Tr.StartLocationName = "+ "'" + start_location + "'"  +
                " And Tr.DestinationName = " + "'" + destination + "'";

        try {
            stmt = db_connector.connection.createStatement();
            ResultSet resultSet = stmt.executeQuery(query);

            while(resultSet.next())
            {
                String scheduled_start_time = resultSet.getString("ScheduledStartTime");
                String scheduled_arrival_time = resultSet.getString("ScheduledArrivalTime");
                String driver_name = resultSet.getString("DriverName");
                String bus_id = resultSet.getString("BusID");

                System.out.println("Scheduled Start Time: " + scheduled_start_time);
                System.out.println("Scheduled Arrival Time: " + scheduled_arrival_time);
                System.out.println("Driver Name: " + driver_name);
                System.out.println("Bus ID: " + bus_id);
            }

        } catch (SQLException e) {
            System.out.println(e);
        }
    }
    private void display_trip_stops() {
        int trip_number = user_interface.get_trip_number();

        System.out.println("Displaying trip stops for:");

        Statement stmt = null;
        String query = "Select TS.TripNumber, TS.StopNumber, TS.SequenceNumber, TS.DrivingTime" +
                " From Trip T, TripStopInfo TS" +
                " Where T.TripNumber = TS.TripNumber" +
                " And T.TripNumber =" + trip_number;

        try {
            stmt = db_connector.connection.createStatement();
            ResultSet resultSet = stmt.executeQuery(query);

            while(resultSet.next())
            {
                String TripNumber = resultSet.getString("TripNumber");
                String StopNumber = resultSet.getString("StopNumber");
                String SequenceNumber = resultSet.getString("SequenceNumber");
                String DrivingTime = resultSet.getString("DrivingTime");

                System.out.println("+-------------------------------------------+");
                System.out.println("|   Trip Number: " + TripNumber);
                System.out.println("|   Stop Number: " + StopNumber);
                System.out.println("|   Sequence Number: " + SequenceNumber);
                System.out.println("|   Driving Time: " + DrivingTime);
                System.out.println("+-------------------------------------------+");
                System.out.println();
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
    }
    private void display_driver_schedule() {
        String driver_name = user_interface.get_driver_name();

        System.out.println("Displaying Schedule for:");
        System.out.println(driver_name);
        System.out.println();

        Statement stmt = null;
        String query = "Select T.Date, T.TripNumber" +
                " From TripOffering T" +
                " Where T.DriverName = " + "'" + driver_name + "'";

        try {
            stmt = db_connector.connection.createStatement();
            ResultSet resultSet = stmt.executeQuery(query);

            while(resultSet.next())
            {
                String Date = resultSet.getString("Date");
                String TripNumber = resultSet.getString("TripNumber");

                System.out.println("+-------------------------------------------+");
                System.out.println("|   Trip Number: " + TripNumber);
                System.out.println("|   Date: " + Date);
                System.out.println("+-------------------------------------------+");
                System.out.println();
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
    }
    private void edit_schedules() {
        boolean is_done=false;

        while(!is_done) {
            user_interface.display_schedule_menu();
            int user_choice = user_interface.get_valid_response(1,5);

            switch (user_choice)
            {
                case 1:
                    delete_a_trip();
                    break;
                case 2:
                    trip_loop();
                    break;
                case 3:
                    change_trip_driver();
                    break;
                case 4:
                    change_bus();
                    break;
                case 5:
                    is_done = true;
                    break;
            }
        }
    }
    private void trip_loop() {
        boolean has_more=true;

        while(has_more) {
            add_a_trip();
            has_more = user_interface.ask_trip_offering();
        }
    }
    private void change_bus() {
        int trip_number = user_interface.get_trip_number();
        String date = user_interface.get_date();
        String scheduled_start_time = user_interface.get_scheduled_start_time();
        String bus_id = user_interface.get_bus_ID();

        System.out.println("Changing the bus for a Tip Offering:");
        System.out.println("Trip Number: " + trip_number);
        System.out.println("Date: " + date);
        System.out.println("Scheduled Start Time: " + scheduled_start_time);
        System.out.println("New Bus ID: " + bus_id);

        Statement stmt = null;
        String query = "UPDATE TripOffering" +
                " SET BusID = " + "'" + bus_id + "'" +
                " WHERE TripNumber = " + "'" + trip_number + "'" +
                " AND Date = " + "'" + date  + "'" +
                " AND ScheduledStartTime = " + "'" + scheduled_start_time + "'" ;

        try {
            stmt = db_connector.connection.createStatement();
            stmt.executeUpdate(query);
            System.out.println("Changing the bus for a Trip Offering:");
        } catch (SQLException e) {
            System.out.println(e);
        }

    }
    private void change_trip_driver() {
        int trip_number = user_interface.get_trip_number();
        String date = user_interface.get_date();
        String scheduled_start_time = user_interface.get_scheduled_start_time();
        String driver_name = user_interface.get_driver_name();

        System.out.println("Changing Driver for Trip Offering");
        System.out.println("Trip Number: " + trip_number);
        System.out.println("Date: " + date);
        System.out.println("Scheduled Start Time: " + scheduled_start_time);
        System.out.println("New Driver Name: " + driver_name);

        Statement stmt = null;
        String query = "UPDATE TripOffering" +
                " SET DriverName = " + "'" + driver_name + "'" +
                " WHERE TripNumber = " + "'" + trip_number + "'" +
                " AND Date = " + "'" + date  + "'" +
                " AND ScheduledStartTime = " + "'" + scheduled_start_time + "'" ;
        try {
            stmt = db_connector.connection.createStatement();
            stmt.executeUpdate(query);
            System.out.println("Changing Driver for Trip Offering");
        } catch (SQLException e) {
            System.out.println(e);
        }

    }
    private void add_a_trip() {
        int trip_number = user_interface.get_trip_number();
        String date = user_interface.get_date();
        String scheduled_start_time = user_interface.get_scheduled_start_time();
        String scheduled_arrival_time = user_interface.get_scheduled_arrival_time();
        String driver_name = user_interface.get_driver_name();
        String bus_id = user_interface.get_bus_ID();

        System.out.println("Adding a trip:");
        System.out.println("Trip Number: " + trip_number);
        System.out.println("Date: " + date);
        System.out.println("Scheduled Start Time: " + scheduled_start_time);
        System.out.println("Scheduled Arrival Time: " + scheduled_arrival_time);
        System.out.println("Driver Name: " + driver_name);
        System.out.println("Bus ID: " + bus_id);

        Statement stmt = null;
        String query = "Insert Into TripOffering Values(" + "'" + trip_number + "'" +
                "," + "'" + date + "'" +
                "," + "'" + scheduled_start_time + "'" +
                "," + "'" + scheduled_arrival_time + "'" +
                "," + "'" + driver_name + "'" +
                "," + "'" + bus_id + "'" + ")";

        try {
            stmt = db_connector.connection.createStatement();
            stmt.executeUpdate(query);
            System.out.println("Adding a Trip Offering record");
        } catch (SQLException e) {
            System.out.println(e);
        }
    }
    private void delete_a_trip() {
        int trip_number = user_interface.get_trip_number();
        String date = user_interface.get_date();
        String scheduled_start_time = user_interface.get_scheduled_start_time();

        System.out.println("Deleting a trip:");
        System.out.println("Trip Number: " + trip_number);
        System.out.println("Date: " + date);
        System.out.println("Scheduled Start Time: " + scheduled_start_time);

        Statement stmt = null;
        String query = "DELETE From TripOffering WHERE TripNumber =" + "'" + trip_number + "'" +
                " AND Date =" + "'" + date + "'" +
                " AND ScheduledStartTime =" + "'" + scheduled_start_time + "'";

        try {
            stmt = db_connector.connection.createStatement();
            stmt.executeUpdate(query);
            System.out.println("Deleting record from Trip Offering Table");
        } catch (SQLException e) {
            System.out.println(e);
        }
    }
    private void record_trip_schedule() {
        String date = user_interface.get_date();
        int trip_number = user_interface.get_trip_number();
        int stop_number = user_interface.get_stop_number();
        String scheduled_start_time = user_interface.get_scheduled_start_time();
        String scheduled_arrival_time = user_interface.get_scheduled_arrival_time();
        String actual_start_time = user_interface.get_actual_start_time();
        String actual_arrival_time = user_interface.get_actual_arrival_time();
        int num_passangers_in = user_interface.get_num_passangers_in();
        int num_passangers_out = user_interface.get_num_passangers_out();

        System.out.println("Recording trip schedule:");
        System.out.println("Date: " + date);
        System.out.println("Trip Number: " + trip_number);
        System.out.println("Stop Number: " + stop_number);
        System.out.println("Scheduled Start Time: " + scheduled_start_time);
        System.out.println("Scheduled Arrival Time: " + scheduled_arrival_time);
        System.out.println("Actual Start Time: " + actual_start_time);
        System.out.println("Actual Arrival Time: " + actual_arrival_time);
        System.out.println("Number of Passangers In: " + num_passangers_in);
        System.out.println("Number of Passangers Out: " + num_passangers_out);

        Statement stmt = null;
        String query = "Insert Into ActualTripStopInfo Values(" + "'" + trip_number + "'" + ","
                + "'" + date + "'" + ","
                + "'" + scheduled_start_time + "'" + ","
                + "'" + stop_number + "'" + ","
                + "'" + scheduled_arrival_time + "'" + ","
                + "'" + actual_start_time + "'" + ","
                + "'" + actual_arrival_time + "'" + ","
                + "'" + num_passangers_in + "'" + ","
                + "'" + num_passangers_out + "'" + ")";

        try {
            stmt = db_connector.connection.createStatement();
            stmt.executeUpdate(query);
            System.out.println("Recording new trip complete. ");
        } catch (SQLException e) {
            System.out.println(e);
        }
    }
    private void add_driver() {
        String driver_name = user_interface.get_driver_name();
        String driver_phone_number = user_interface.get_driver_phone_number();

        System.out.println("Creating new Driver:");
        System.out.println("Name: " + driver_name);
        System.out.println("Phone Number: " + driver_phone_number);


        Statement stmt = null;
        String query = "INSERT INTO Driver Values(" + "'" + driver_name + "'" + ", " + "'" + driver_phone_number + "'" + ")";

        try {
            stmt = db_connector.connection.createStatement();
            stmt.executeUpdate(query);
            System.out.println("Inserted record into Driver Table");
        } catch (SQLException e) {
            System.out.println(e);
        }
    }
    private void add_bus() {
        String bus_ID = user_interface.get_bus_ID();
        String model = user_interface.get_bus_model();
        int year = user_interface.get_bus_year();

        System.out.println("Creating new Bus:");
        System.out.println("Bus ID: " + bus_ID);
        System.out.println("Model: " + model);
        System.out.println("Year: " + year);


        Statement stmt = null;
        String query = "INSERT INTO Bus Values(" + "'" + bus_ID + "'" + ", "
                + "'" + model + "'" + ", "
                +  year +")";

        try {
            stmt = db_connector.connection.createStatement();
            stmt.executeUpdate(query);
            System.out.println("Inserted record into Bus Table");
        } catch (SQLException e) {
            System.out.println(e);
        }
    }
    private void delete_bus() {
        String bus_ID = user_interface.get_bus_ID();

        System.out.println("Deleting Bus:");
        System.out.println("Bus ID: " + bus_ID);


        Statement stmt = null;
        String query = "DELETE FROM Bus WHERE BusID =" + "'" + bus_ID + "'";

        try {
            stmt = db_connector.connection.createStatement();
            stmt.executeUpdate(query);
            System.out.println("Deleted record from Bus Table");
        } catch (SQLException e) {
            System.out.println(e);
        }
    }
}
