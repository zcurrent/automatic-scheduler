package auto_scheduler;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

public class AutoScheduler{
	
	/**
	 * An array of type integer containing the number of employees wanted for each one of the shifts. The index corresponds with array shifts.
	 */
	private int [][] remEmployeesPerShift;
	
	private int [] numEmployeesPerShift;
	
	/**
	 * An array of type Shift, these are the shifts that need covered for Monday through Sunday
	 * FIX ME: Upgrade to allow different shifts on different days of the week
	 */
	private Shift [] shifts;
	
	/**
	 * An array of type String for the days of the week, these are used to index through the availabilities
	 */
	private String [] daysOfWeek = {"Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"};
	
	/**
	 * An ArrayList of type Employee, array list is used to allow any number of employees 
	 */
	private ArrayList<Employee> employees;
	
	/**
	 * Scanner to allow easier loading of the Shifts and Employees
	 */
	Scanner sc =  new Scanner(System.in);
	
	
	/**
	 * Constructs an instance of AutoScheduler, with numShifts number of Shifts, then the scheduler asks for the values for shift 
	 * and the number of employees for that given shift
	 * @param numShifts - number of shifts needing scheduled
	 */
	public AutoScheduler(int numShifts) {
		//Constructs a new array numShifts long of type Shift
		shifts = new Shift[numShifts];
		
		//Constructs an array numShifts by daysOfWeek long of type int
		remEmployeesPerShift = new int[numShifts][daysOfWeek.length];
		
		//Constructs an array numShifts long of type int
		numEmployeesPerShift = new int[numShifts];
		
		//Initializes employees
		employees = new ArrayList<>();
		
		
		//for loop going through each of the shifts getting start and end time and constructing the shift to store in shifts, 
		//then it gets the employees wanted for this shift and stores it in numEmployeesPerShift
		for(int i = 0; i < numShifts; i++) {
			try {
			System.out.println("Please enter the start and end time, in Military Standard time, for shift " + (i + 1));
			int start = sc.nextInt();
			int end = sc.nextInt();
			shifts[i] = new Shift(start, end);
			System.out.println("Please enter the number of employees you would like to work on shift " + (i + 1));
			int ithNumEmploys = sc.nextInt();
			numEmployeesPerShift[i] = ithNumEmploys;
			
			} catch(InputMismatchException e) {
				System.out.println("Error Occured. Expected different values!");
				System.exit(0);
			}
		}
		
		//Sort the given shifts by their start time, with the earliest first
		//Bubble sort **need to update once we go over Sorts in COM S 228
		boolean sorted = false;
		Shift tempS;
		int temp;
		while(!sorted) {
			sorted = true;
			for(int i = 1; i < numShifts; i++) {
				if(shifts[i].getStart() < shifts[i - 1].getStart()) {
					tempS = shifts[i - 1];
					shifts[i - 1] = shifts[i];
					shifts[i] = tempS;
					
					temp = numEmployeesPerShift[i - 1];
					numEmployeesPerShift[i - 1] = numEmployeesPerShift[i];
					numEmployeesPerShift[i] = temp;
					
					sorted = false;
				}
			}
		}
		
		//load values in to the remEmployeesPerShift, to show user if they need 
		//more employees
		for(int i = 0; i < numShifts; i++) {
			for(int j = 0; j < daysOfWeek.length; j++) {
				remEmployeesPerShift[i][j] = numEmployeesPerShift[i];
			}
		}
		
		
	}
	
	public AutoScheduler(String load) {
		File loadFile = new File(load + ".txt");
		
		try {
			Scanner scanLoadFile = new Scanner(loadFile);
			
			int numShifts = scanLoadFile.nextInt();
			
			shifts = new Shift[numShifts];
			
			remEmployeesPerShift = new int[numShifts][daysOfWeek.length];
			
			numEmployeesPerShift = new int[numShifts];
			
			employees = new ArrayList<>();
			
			for(int i = 0; i < numShifts; i++) {
				int start = scanLoadFile.nextInt();
				int end = scanLoadFile.nextInt();
				shifts[i] = new Shift(start, end);
				
				int ithNumEmploys = scanLoadFile.nextInt();
				numEmployeesPerShift[i] = ithNumEmploys;
			}
			
			
			for(int i = 0; i < numShifts; i++) {
				for(int j = 0; j < daysOfWeek.length; j++) {
					remEmployeesPerShift[i][j] = numEmployeesPerShift[i];
				}
			}
			
			int numEmployees = scanLoadFile.nextInt();
			
			for(int i = 0; i < numEmployees; i++) {
				String name = scanLoadFile.next() + " " + scanLoadFile.next();
				double hours = scanLoadFile.nextDouble();
				
				addEmployee(name, hours);
				
				for(int j = 0; j < daysOfWeek.length; j++) {
					int start = scanLoadFile.nextInt();
					int end = scanLoadFile.nextInt();
					addAvailability(name, daysOfWeek[j], start, end);
				}
				
				
			}
			
			scanLoadFile.close();
			
		} catch(FileNotFoundException e) {
			System.out.println("Incorrect file, Buh Bye!");
			System.exit(0);
			
		} catch(InputMismatchException e) {
			System.out.println("Found the file, incorrect format!");
			System.exit(0);
			
		}
		
	}
	
	/**
	 * Adds numEmployees of type Employee to variable employees. for each employee it asks the desired hours and the availability 
	 * of each day of the week. Intended to speed up the initialization process
	 * @param numEmployees - the number of employees wanting to add to the schedule
	 */
	public void addEmployeesWAvail(int numEmployees) {
		/*A for loop, going through each employee to add and getting the first and last name of the employee followed by their hours
		*and another for loop creating that employee and then initializing all of the employee's availability for each day and adds 
		*the availability*/
		for(int i = 0; i < numEmployees; i++) {
			try {
			System.out.println("Please enter the first and last name followed by the hours per week for employee " + (i + 1));
			String name = sc.next() + " " + sc.next();
			double hours = sc.nextDouble();
			addEmployee(name, hours);
			for(int j = 0; j < daysOfWeek.length; j++) {
				System.out.println("Please enter the start and end availability, in military standard time, for " + daysOfWeek[j] + ", for " + name);
				int start = sc.nextInt();
				int end = sc.nextInt();
				addAvailability(name, daysOfWeek[j], start, end);
			}
			} catch (InputMismatchException e) {
				System.out.println("Error Occured. Different Input Expected!");
			}
		}
	}
	
	/**
	 * Add an Employee with the given name and hoursPerWeek
	 * @param name - name of the Employee
	 * @param hoursPerWeek - hours per week for the Employee
	 */
	public void addEmployee(String name, double hoursPerWeek) {
		employees.add(new Employee(name, hoursPerWeek));
		
	}
	
	/**
	 * Add/Set an availability for name on day with the given start and end time, in military time
	 * @param name - name of the employee to set the availability
	 * @param day - the day of the week to set the availability
	 * @param start - start time in military time format 0000
	 * @param end - end time in military time format 0000
	 */
	public void addAvailability(String name, String day, int start, int end) {
		//for loop going through the variable employees to find a matching name, if found set the availability, if 
		//not do nothing
		for(int i = 0; i < employees.size(); i++) {
			if(employees.get(i).getName().equals(name)) {
				employees.get(i).setAvailabilityShift(day, start, end);
			}
		}
	}
	
	/**
	 * A getter for employees
	 * Needs work
	 * @param i - 
	 * @return
	 */
	public String getEmployee(int i) {
		return employees.get(i).getName() + ", Wants " + employees.get(i).getHours() + " hours per week";
 	}
	
	/**
	 * The method to generate the schedule and print the result
	 */
	public void generateSchedule() {
		
		fillSchedule();
		
		//Format and print the heading of the schedule
		System.out.printf("%18s"," ");
		
		for(int i = 0; i < daysOfWeek.length; i++) {
			System.out.printf("%-24s",daysOfWeek[i]);
		}
		System.out.println();
		
		for(int i = 0; i < employees.size(); i++) {
			System.out.printf("%-18s", employees.get(i).getName());
			for(int j = 0; j < daysOfWeek.length; j++ ) {
					System.out.printf("%-24s", employees.get(i).getStandardScheduleAvailability(daysOfWeek[j]));
			}
			System.out.println();
		}
		
		System.out.printf("%18s"," ");
		for(int i = 0; i < shifts.length; i++) {
			for(int j = 0; j < daysOfWeek.length; j++) {
				System.out.printf("Shift %d: %4d remaining ", (i + 1), remEmployeesPerShift[i][j]);
			}
			System.out.println();
			System.out.printf("%18s"," ");
		}
		
	}
	
	/**
	 * Helper method to generateSchedule() to take care of the checks for the employee's availability to match the shifts and fill in their 
	 * schedule
	 */
	private void fillSchedule() {
		int lowestRank = 0;
		boolean found = false;
		
		for(int j = 0; j < shifts.length; j++) {
			for(int k = 0; k < numEmployeesPerShift[j]; k++) {
				for(int l = 0; l < daysOfWeek.length; l++) {
					for(int m = 0; m < employees.size(); m++) {
						if(employees.get(m).getAvailability(daysOfWeek[l]).equals("null") &&
								employees.get(m).getAvailabilityStart(daysOfWeek[k]) == employees.get(m).getAvailabilityEnd(daysOfWeek[k])) {
							break;
						}
								
						int hrDiff = (shifts[j].getEnd() / 100) - (shifts[j].getStart() / 100);
						double minDiff = Math.abs((shifts[j].getEndMin() - shifts[j].getStartMin()) / 60);
						double temp = hrDiff + minDiff;
							
						if(employees.get(m).getAvailabilityStart(daysOfWeek[l]) <= shifts[j].getStart() &&
								employees.get(m).getAvailabilityEnd(daysOfWeek[l]) >= shifts[j].getEnd() &&
								employees.get(m).compareTo(employees.get(lowestRank), daysOfWeek[l]) <= 0 &&
								employees.get(m).getScheduleAvailability(daysOfWeek[l]).equals("----") &&
								employees.get(m).getHours() - temp >= 0) {
							lowestRank = m;
							found = true;
						}
					}
					if(found) {
						int hrDiff = (shifts[j].getEnd() / 100) - (shifts[j].getStart() / 100);
						double minDiff = Math.abs((shifts[j].getEndMin() - shifts[j].getStartMin()) / 60);
						double temp = hrDiff + minDiff;
							
						employees.get(lowestRank).setScheduleShift(daysOfWeek[l], shifts[j].getStart(), shifts[j].getEnd());
							
						employees.get(lowestRank).setHours(employees.get(lowestRank).getHours() - temp);
						remEmployeesPerShift[j][l]--;
					}
						
					lowestRank = 0;
					found = false;
				}
			}
		}
	}
	
	
	public void save(String s) {
		File save = new File(s + ".txt");
		Scanner scan = new Scanner(System.in);
		
		if(save.exists()) {
			System.out.println("That file already exists, would you like to overwrite? (y/n)");
			if(!scan.next().equals("y")) {
				scan.close();
				return;
			}
		}
		
		try {
			PrintWriter out = new PrintWriter(save);
			out.println(shifts.length);
			
			for(int i = 0; i < shifts.length; i++) {
				out.print(shifts[i].getStart() + " " + shifts[i].getEnd() + " ");
				out.print(numEmployeesPerShift[i]);
				out.println();
			}
			
			out.println(employees.size());
			
			for(int i = 0; i < employees.size(); i++) {
				out.print(employees.get(i).getName() + " " + employees.get(i).getOriginalHours() + " ");
				for(int j = 0; j < daysOfWeek.length; j++) {
					out.print(employees.get(i).getAvailabilityStart(daysOfWeek[j]) + " " + employees.get(i).getAvailabilityEnd(daysOfWeek[j]) + " ");
				}
				out.println();
			}
			
			out.close();
		} catch(FileNotFoundException e) {
			System.out.println("File not found. Error 404");
		}
		
		scan.close();
	
	}
}
