
import java.util.Scanner;

public class Main {

    public static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {

        int choice;

        do {
            System.out.println("\n===== ONLINE EXAMINATION SYSTEM =====");
            System.out.println("1. Student Registration");
            System.out.println("2. Student Login");
            System.out.println("3. Exit");
            System.out.print("Enter your choice: ");

            choice = sc.nextInt();

            switch (choice) {

                case 1:
                    StudentRegistration.registerStudent();
                    break;

                case 2:
                    if (Login.login()) {
                        Dashboard.showDashboard(Login.getLoggedInUsername());
                    }
                    break;

                case 3:
                    System.out.println("Thank You for Using Our System!");
                    break;

                default:
                    System.out.println("Invalid Choice!");
            }

        } while (choice != 3);

        
    }
}
