import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

public class ToDoListApp {
    private static ArrayList<Task> taskList = new ArrayList<>();
    private static int taskIdCounter = 1; // For generating new IDs
    private static List<Integer> availableIds = new ArrayList<>(); // List for reused IDs

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("To-Do List Application");
            System.out.println("1. Add Task");
            System.out.println("2. List Tasks");
            System.out.println("3. Mark Task as Completed");
            System.out.println("4. Remove Task");
            System.out.println("5. Exit");
            System.out.print("Enter your choice: ");

            int choice;
            // Input validation for choice
            try {
                choice = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a number between 1 and 5.");
                continue;
            }

            switch (choice) {
                case 1:
                    addTask(scanner);
                    break;
                case 2:
                    listTasks();
                    break;
                case 3:
                    markTaskAsCompleted(scanner);
                    break;
                case 4:
                    removeTask(scanner);
                    break;
                case 5:
                    System.out.println("Goodbye!");
                    scanner.close();
                    System.exit(0);
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private static void addTask(Scanner scanner) {
        System.out.print("Enter the task description: ");
        String description = scanner.nextLine();
        
        // Optional: Validate the description
        if (description.trim().isEmpty()) {
            System.out.println("Task description cannot be empty. Please try again.");
            return;
        }

        int taskId = getNextTaskId(); // Get the next available task ID
        Task task = new Task(taskId, description);
        taskList.add(task);
        System.out.println("Task added successfully!");
    }

    private static int getNextTaskId() {
        if (!availableIds.isEmpty()) {
            // Reuse an available ID if there is one
            return availableIds.remove(availableIds.size() - 1);
        }
        // Otherwise, return the current counter and increment it
        return taskIdCounter++;
    }

    private static void listTasks() {
        if (taskList.isEmpty()) {
            System.out.println("No tasks to display.");
        } else {
            System.out.println("Tasks:");
            for (Task task : taskList) {
                System.out.println(task);
            }
        }
    }

    private static void markTaskAsCompleted(Scanner scanner) {
        System.out.print("Enter the task ID to mark as completed: ");
        int taskId;
        // Input validation for task ID
        try {
            taskId = Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("Invalid input. Please enter a valid task ID.");
            return;
        }

        for (Task task : taskList) {
            if (task.getId() == taskId) {
                task.setCompleted(true);
                System.out.println("Task marked as completed.");
                return;
            }
        }
        System.out.println("Task not found.");
    }

    private static void removeTask(Scanner scanner) {
        System.out.print("Enter the task ID to remove: ");
        int taskId;
        // Input validation for task ID
        try {
            taskId = Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("Invalid input. Please enter a valid task ID.");
            return;
        }

        Iterator<Task> iterator = taskList.iterator();
        while (iterator.hasNext()) {
            Task task = iterator.next();
            if (task.getId() == taskId) {
                iterator.remove();
                availableIds.add(taskId); // Add the ID to the available list for reuse
                System.out.println("Task removed.");
                return;
            }
        }
        System.out.println("Task not found.");
    }
}

class Task {
    private int id;
    private String description;
    private boolean completed;

    public Task(int id, String description) {
        this.id = id;
        this.description = description;
        this.completed = false;
    }

    public int getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    @Override
    public String toString() {
        return id + ". [" + (completed ? "X" : " ") + "] " + description;
    }
}
