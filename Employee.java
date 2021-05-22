package auto_scheduler;

public class Employee{

	private double numHoursPerWeek;
	
	private double originalHours;

	private String name;

	private Shift[] availabilities;

	private Shift[] schedule;

	public Employee(String name, double hoursPerWeek) {
		availabilities = new Shift[7];
		schedule = new Shift[7];

		this.numHoursPerWeek = hoursPerWeek;
		this.originalHours = hoursPerWeek;

		this.name = name;

	}

	public String getName() {
		return name;
	}

	public double getHours() {
		return numHoursPerWeek;
	}
	
	public double getOriginalHours() {
		return originalHours;
	}

	public void setHours(double hours) {
		numHoursPerWeek = hours;
	}

	public String getAvailability(String day) {
		if(availabilities[dayConverter(day)] == null) {
			return "null";
		}
		if(dayConverter(day) != -1) {
			return availabilities[dayConverter(day)].getShift();
		} else {
			return "Error. Not a validate day, please enter a full day. Ex: Monday";
		}
	}

	public String getStandardAvailability(String day) {
		if(availabilities[dayConverter(day)] == null) {
			return "null";
		}
		if(dayConverter(day) != -1) {
			return availabilities[dayConverter(day)].getStandardShift();
		} else {
			return "Error. Not a validate day, please enter a full day. Ex: Monday";
		}
	}

	public void setAvailabilityShift(String day, int start, int end) {
		if(dayConverter(day) != -1) {
			availabilities[dayConverter(day)] = new Shift(start, end);
		} else {
			System.out.println("Error. Not a validate day, please enter a full day. Ex: Monday");
		}
	}

	public int getAvailabilityStart(String day) {
		if(dayConverter(day) != -1) {
			return availabilities[dayConverter(day)].getStart();
		} else {
			return 0;
		}
	}
	
	public int getAvailabilityStartMin(String day) {
		if(dayConverter(day) != -1) {
			return availabilities[dayConverter(day)].getStartMin();
		} else {
			return 0;
		}
	}
	
	public int getAvailabilityEnd(String day) {
		if(dayConverter(day) != -1) {
			return availabilities[dayConverter(day)].getEnd();
		} else {
			return 0;
		}
	}
	
	public int getAvailabilityEndMin(String day) {
		if(dayConverter(day) != -1) {
			return availabilities[dayConverter(day)].getStartMin();
		} else {
			return 0;
		}
	}
	
	

	public String getScheduleAvailability(String day) {
		if(schedule[dayConverter(day)] == null) {
			return "----";
		}
		if(dayConverter(day) != -1) {
			return schedule[dayConverter(day)].getShift();
		} else {
			return "Error. Not a validate day, please enter a full day. Ex: Monday";
		}
	}

	public String getStandardScheduleAvailability(String day) {
		if(schedule[dayConverter(day)] == null) {
			return "----";
		}
		if(dayConverter(day) != -1) {
			return schedule[dayConverter(day)].getStandardShift();
		} else {
			return "Error. Not a validate day, please enter a full day. Ex: Monday";
		}
		
	}
	
	public void setScheduleShift(String day, int start, int end) {
		if(dayConverter(day) != -1) {
			schedule[dayConverter(day)] = new Shift(start, end);
		} else {
			System.out.println("Error. Not a validate day, please enter a full day. Ex: Monday");
		}
	}
	
	public int dayConverter(String day) {
		day = day.toUpperCase();
		if (day.equals("MONDAY")) {
			return 0;
		} else if (day.equals("TUESDAY")) {
			return 1;
		} else if (day.equals("WEDNESDAY")) {
			return 2;
		} else if (day.equals("THURSDAY")) {
			return 3;
		} else if (day.equals("FRIDAY")) {
			return 4;
		} else if (day.equals("SATURDAY")) {
			return 5;
		} else if (day.equals("SUNDAY")) {
			return 6;
		} else {
			return -1;
		}
	}
	
	
	public int compareTo(Employee e, String day) {
		return (this.getAvailabilityEndMin(day) - this.getAvailabilityStart(day)) - (e.getAvailabilityEndMin(day) - e.getAvailabilityStart(day));
	}
}

	