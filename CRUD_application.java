import java.io.*;
import java.util.*;
class Task {
    int id;
    String description;
    public Task(int id, String description) {
        this.id = id;
        this.description = description;
    }
    @Override
    public String toString() {
        return id + "," + description;
    }
}
public class CRUD_application {
    private static final String FILE_NAME = "tasks.txt";
    private static List<Task> tasks = new ArrayList<>();
    public static void main(String[] args) {
        loadTasks(); // Load tasks from file at the start
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("\nTask Manager:");
            System.out.println("1. Add Task");
            System.out.println("2. View Tasks");
            System.out.println("3. Update Task");
            System.out.println("4. Delete Task");
            System.out.println("5. Exit");
            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline          
            switch (choice) {
                case 1 -> addTask(scanner);
                case 2 -> viewTasks();
                case 3 -> updateTask(scanner);
                case 4 -> deleteTask(scanner);
                case 5 -> {
                    saveTasks();
                    System.out.println("Exiting...");
                    System.exit(0);
                }
                default -> System.out.println("Invalid choice! Try again.");
            }
        }
    }
    private static void addTask(Scanner scanner) {
        System.out.print("Enter task description: ");
        String desc = scanner.nextLine();
        int id = tasks.size() + 1;
        tasks.add(new Task(id, desc));
        saveTasks();
        System.out.println("Task added successfully!");
    }
    private static void viewTasks() {
        if (tasks.isEmpty()) {
            System.out.println("No tasks available.");
            return;
        }
        System.out.println("\nTasks:");
        for (Task task : tasks) {
            System.out.println(task.id + ". " + task.description);
        }
    }
    private static void updateTask(Scanner scanner) {
        viewTasks();
        System.out.print("Enter task ID to update: ");
        int id = scanner.nextInt();
        scanner.nextLine(); // Consume newline   
        for (Task task : tasks) {
            if (task.id == id) {
                System.out.print("Enter new description: ");
                task.description = scanner.nextLine();
                saveTasks();
                System.out.println("Task updated!");
                return;
            }
        }
        System.out.println("Task not found.");
    }
    private static void deleteTask(Scanner scanner) {
        viewTasks();
        System.out.print("Enter task ID to delete: ");
        int id = scanner.nextInt();
        tasks.removeIf(task -> task.id == id);
        saveTasks();
        System.out.println("Task deleted!");
    }
    private static void loadTasks() {
        tasks.clear();
        try (BufferedReader br = new BufferedReader(new FileReader(FILE_NAME))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",", 2);
                tasks.add(new Task(Integer.parseInt(parts[0]), parts[1]));
            }
        } catch (IOException e) {
            System.out.println("No previous tasks found, starting fresh.");
        }
    }
    private static void saveTasks() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(FILE_NAME))) {
            for (Task task : tasks) {
                bw.write(task.toString());
                bw.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error saving tasks: " + e.getMessage());
        }
    }
}
