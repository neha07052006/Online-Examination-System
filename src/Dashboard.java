
public class Dashboard {

    public static void showDashboard(String username) {

        String choice;

        while (true) {

            System.out.println("\n================================");
            System.out.println("       STUDENT DASHBOARD");
            System.out.println("================================");
            System.out.println("Welcome, " + username);
            System.out.println("--------------------------------");
            System.out.println("1. Start Exam");
            System.out.println("2. View Result");
            System.out.println("3. Logout");
            System.out.print("Enter your choice: ");

            choice = Main.sc.next();

            switch (choice) {

                case "1":
                    Exam.startExam(username);
                    break;

                case "2":
                    System.out.println("View Result Selected!");
                    Result.viewResult(username);
                    break;

                case "3":
                    System.out.println("Logged Out Successfully!");
                    return;

                default:
                    System.out.println("Invalid Choice!");
            }
        }
    }
}
