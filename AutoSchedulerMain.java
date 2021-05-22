package auto_scheduler;

import java.util.Scanner;

public class AutoSchedulerMain {
	public static void main(String [] args) {
		String [] daysOfWeek = {"Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"};
		
		Scanner sc = new Scanner(System.in);
		
		boolean startMenu = true;
		
		AutoScheduler mySchedule = null;
		
		int choice;
		
		while(startMenu) {
			System.out.println("Welcome to the Auto Scheduler 1.0!!!");
			System.out.println("Please choose from the following options. (Please enter the corresponding number)");
			System.out.println();
			
			System.out.println("1. Create a New File.");
			System.out.println("2. Load File");
			System.out.println("3. Exit");
			
			choice = sc.nextInt();
			
			if(choice == 1) {
				System.out.println("How many shifts would you like to schedule?");
				int numShifts = sc.nextInt();
				mySchedule = new AutoScheduler(numShifts);
				startMenu = false;
				
			} else if(choice == 2) {
				System.out.println("What is the name of the file you would like to open?");
				String loadFile = sc.next();
				mySchedule = new AutoScheduler(loadFile);
				startMenu = false;
				
			} else if (choice == 3) {
				System.exit(0);
			}
		}
		
		boolean mainMenu = true;
		
		while(mainMenu) {
			
			System.out.println("What would you like to do next?");
			System.out.println();
			
			System.out.println("1. Add Employee(s)");
			System.out.println("2. Edit Employee Availability");
			System.out.println("3. Remove Employee");
			System.out.println("4. Generate New Schedule");
			System.out.println("5. Save");
			System.out.println("6. Exit");
			
			choice = sc.nextInt();

			if(choice == 1) {
				System.out.println("How many employees would you like to add?");
				choice = sc.nextInt();
				mySchedule.addEmployeesWAvail(choice);
				
			} else if(choice == 2) {
				System.out.println("Which employee would you like to edit? (Enter first and last name)");
				String name = sc.next() + " " + sc.next();
				
				for(int j = 0; j < daysOfWeek.length; j++) {
					System.out.println("Please enter the start and end availability, in military standard time, for " + daysOfWeek[j] + ", for " + name);
					int start = sc.nextInt();
					int end = sc.nextInt();
					mySchedule.addAvailability(name, daysOfWeek[j], start, end);
				}
				
			} else if(choice == 3) {
				System.out.println("Under Construction!");
				
			} else if(choice == 4) {
				mySchedule.generateSchedule();
				
			} else if(choice == 5) {
				System.out.println("What would you like to name your file?");
				String file = sc.next();
				mySchedule.save(file);
				sc = new Scanner(System.in);
				
			} else if(choice == 6) {
				System.exit(0);
			}
			
			
		}
		
		sc.close();
		
	}
}
