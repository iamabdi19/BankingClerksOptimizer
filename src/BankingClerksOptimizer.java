import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;

//
public class BankingClerksOptimizer {
    // MinHeaps for customers in each work shift and unit (Commercial, Casual, Loans)
    static BankCustomerMinHeap workShift1, workShift2, workShift3;
    static BankCustomerMinHeap workShift1Commercial, workShift1casual, workShift1loans;
    static BankCustomerMinHeap workShift2Commercial, workShift2casual, workShift2loans;
    static BankCustomerMinHeap workShift3Commercial, workShift3casual, workShift3loans;

    // Maximum waiting times for different customer types
    static int commercialMaxWaitingTime;
    static int loansMaxWaitingTime;
    static int casualMaxWaitingTime;

    // Determines the minimum number of clerks needed
    static int minimumNumberOfClerks(BankCustomerMinHeap minHeap, int maxWaitingTime, int workShift) {
        if (minHeap.isEmpty()) {
            return 0;
        } else {
            return minclerkfinder(minHeap, maxWaitingTime, workShift);
        }
    }

    // Binary search algorithm to find the minimum number of clerks required
    public static int minclerkfinder(BankCustomerMinHeap minHeap, int maxWaitingTime, int workShift) {
        int minClerks = 0;
        int left = 1;
        int right = 100000;
        // binary search loop
        while (left <= right) {
            int clerks = left + (right - left) / 2;
            BankClerkMinHeap clerkMinheap = initialise(clerks, workShift);
            BankCustomerMinHeap CustomerMinHeap = copy(minHeap);
            Boolean isThereEnoughClerks = simulate(clerks, clerkMinheap, CustomerMinHeap, maxWaitingTime);
            if (isThereEnoughClerks) {
                minClerks = clerks;
                right = clerks - 1;
            } else {
                left = clerks + 1;
            }
        }
        return minClerks; // Return the minimum number of clerks found
    }

    // Simulates the working of clerks with customers to check if clerks are enough
    static boolean simulate(int clerks, BankClerkMinHeap clerksMinheap, BankCustomerMinHeap CustomerMinHeap,
            int maxWaitingTime) {
        while (!CustomerMinHeap.isEmpty()) {
            BankCustomer customer = CustomerMinHeap.top();
            CustomerMinHeap.removeMin();
            if (!clerksMinheap.top().availableTime.isBefore(customer.arrival)
                    && !clerksMinheap.top().availableTime.equals(customer.arrival)) {
                long minutes = Duration.between(customer.arrival, clerksMinheap.top().availableTime).toMinutes();
                LocalTime time = clerksMinheap.top().availableTime.plusMinutes((long) customer.processingTime);
                BankClerk clerk = new BankClerk(time.toString());
                clerksMinheap.removeTop();
                clerksMinheap.add(clerk);
                if (minutes > (long) maxWaitingTime) {
                    return false;
                }
            } else {
                LocalTime time = customer.arrival.plusMinutes((long) customer.processingTime);
                BankClerk clerk = new BankClerk(time.toString());
                clerksMinheap.removeTop();
                clerksMinheap.add(clerk);
            }
        }

        return true; // All customers served within max waiting time
    }

   static BankClerkMinHeap initialise(int clerks, int workShift) {
	    BankClerkMinHeap temp = new BankClerkMinHeap(clerks);
	    String time = determineworkShiftTime(workShift);

	    for (int i = 0; i < clerks; ++i) {
	        BankClerk c = new BankClerk(time);
	        temp.add(c);
	    }

	    return temp;
	}

	// Determines the start time of a work shift
    private static String determineworkShiftTime(int workShift) {
	    switch (workShift) {
	        case 1:
	            return "08:30";
	        case 2:
	            return "12:30";
	        default:
	            return "17:00";
	    }
	}


   public static String generateRandomTime(String startTime, String endTime) {
      SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm");
      Date start = null;
      Date end = null;

      try {
         start = dateFormat.parse(startTime);
         end = dateFormat.parse(endTime);
      } catch (Exception var11) {
         var11.printStackTrace();
      }

      long startTimeInMillis = start.getTime();
      long endTimeInMillis = end.getTime();
      long randomTime = startTimeInMillis + (long)(Math.random() * (double)(endTimeInMillis - startTimeInMillis));
      return dateFormat.format(new Date(randomTime));
   }

   // // Displays customer details for each queue
   public static void display() {
	  System.out.println("Time        | Processing Time  | Unit");
      System.out.println("workShift 1 Commercial customers queue");
      while(!workShift1Commercial.isEmpty()) {
    	  BankCustomer customer = workShift1Commercial.top();
    	    String time = String.format("%-12s", customer.arrival);
    	    String processingTime = String.format("%-17s", customer.processingTime);
    	    System.out.println(time + "| " + processingTime + "|  " + customer.unit);
    	    workShift1Commercial.removeMin();
      }


      System.out.println("workShift 1 Loans queue");
//      System.out.println("Time       | Processing Time | Unit");

      while (!workShift1loans.isEmpty()) {
          BankCustomer customer = workShift1loans.top(); // Assuming workShift1loans has a method to get the top customer
          String time = String.format("%-12s", customer.arrival); // Adjust the spacing for time
          String processingTime = String.format("%-17s", customer.processingTime); // Adjust the spacing for processing time

          System.out.println(time + "| " + processingTime + "| " + customer.unit);
          workShift1loans.removeMin();
      }

//      System.out.println("workShift 1 casual customers queue");

      System.out.println("workShift 1 Casual queue");
//      System.out.println("Time       | Processing Time | Unit");

      while (!workShift1casual.isEmpty()) {
          BankCustomer customer = workShift1casual.top(); // Assuming workShift1casual has a method to get the top customer
          String time = String.format("%-12s", customer.arrival); // Adjust the spacing for time
          String processingTime = String.format("%-17s", customer.processingTime); // Adjust the spacing for processing time

          System.out.println(time + "| " + processingTime + "| " + customer.unit);
          workShift1casual.removeMin();
      }


//      System.out.println("workShift 2 Commercial customers queue");

      System.out.println("workShift 2 Commercial customers queue");
//      System.out.println("Time       | Processing Time | Unit");

      while (!workShift2Commercial.isEmpty()) {
          BankCustomer customer = workShift2Commercial.top(); // Assuming workShift2Commercial has a method to get the top customer
          String time = String.format("%-12s", customer.arrival); // Adjust the spacing for time
          String processingTime = String.format("%-17s", customer.processingTime); // Adjust the spacing for processing time

          System.out.println(time + "| " + processingTime + "| " + customer.unit);
          workShift2Commercial.removeMin();
      }


//      System.out.println("workShift 2 loan customers queue");

      System.out.println("workShift 2 Loans queue");
//      System.out.println("Time       | Processing Time | Unit");

      while (!workShift2loans.isEmpty()) {
          BankCustomer customer = workShift2loans.top(); // Assuming workShift2loans has a method to get the top customer
          String time = String.format("%-12s", customer.arrival); // Adjust the spacing for time
          String processingTime = String.format("%-17s", customer.processingTime); // Adjust the spacing for processing time

          System.out.println(time + "| " + processingTime + "| " + customer.unit);
          workShift2loans.removeMin();
      }


//      System.out.println("workShift 2 casual customers queue");

      System.out.println("workShift 2 Casual queue");
//      System.out.println("Time       | Processing Time | Unit");

      while (!workShift2casual.isEmpty()) {
          BankCustomer customer = workShift2casual.top(); // Assuming workShift2casual has a method to get the top customer
          String time = String.format("%-12s", customer.arrival); // Adjust the spacing for time
          String processingTime = String.format("%-17s", customer.processingTime); // Adjust the spacing for processing time

          System.out.println(time + "| " + processingTime + "| " + customer.unit);
          workShift2casual.removeMin();
      }

//      System.out.println("workShift 3 Commercial customers queue");

      System.out.println("workShift 3 Commercial customers queue");
//      System.out.println("Time       | Processing Time | Unit");

      while (!workShift3Commercial.isEmpty()) {
          BankCustomer customer = workShift3Commercial.top(); // Assuming workShift3Commercial has a method to get the top customer
          String time = String.format("%-12s", customer.arrival); // Adjust the spacing for time
          String processingTime = String.format("%-17s", customer.processingTime); // Adjust the spacing for processing time

          System.out.println(time + "| " + processingTime + "| " + customer.unit);
          workShift3Commercial.removeMin();
      }



      System.out.println("workShift 3 Loans queue");
//      System.out.println("Time       | Processing Time | Unit");

      while (!workShift3loans.isEmpty()) {
          BankCustomer customer = workShift3loans.top(); // Assuming workShift3loans has a method to get the top customer
          String time = String.format("%-12s", customer.arrival); // Adjust the spacing for time
          String processingTime = String.format("%-17s", customer.processingTime); // Adjust the spacing for processing time

          System.out.println(time + "| " + processingTime + "| " + customer.unit);
          workShift3loans.removeMin();
      }


      System.out.println("workShift 3 Casual queue");
//      System.out.println("Time       | Processing Time | Unit");

      while (!workShift3casual.isEmpty()) {
          BankCustomer customer = workShift3casual.top(); // Assuming workShift3casual has a method to get the top customer
          String time = String.format("%-12s", customer.arrival); // Adjust the spacing for time
          String processingTime = String.format("%-17s", customer.processingTime); // Adjust the spacing for processing time

          System.out.println(time + "| " + processingTime + "| " + customer.unit);
          workShift3casual.removeMin();
      }


   }

   // Gets user choice
   private static int getUserChoice() {
      System.out.println("1. Generate random customer data");
      System.out.println("2. input customer data manually");
      System.out.print("Kindly choose an option (1 or 2): ");
      Scanner input = new Scanner(System.in);
      return input.nextInt();
   }

   // Gets input from user
   private static void getInputFromUser() {
	    Scanner input = new Scanner(System.in);
	    setMaxWaitingTimes(input);

	    System.out.println("\nEnter total customers for workShift 1:");
	    workShift1 = createCustomerMinHeap(input);

	    System.out.println("\nEnter total customers for workShift 2:");
	    workShift2 = createCustomerMinHeap(input);

	    System.out.println("\nEnter total customers for workShift 3:");
	    workShift3 = createCustomerMinHeap(input);

	    initializeMinHeaps();
	}

	private static void setMaxWaitingTimes(Scanner input) {
	    System.out.print("Enter Maximum Waiting Time for commercial, casual, and loans (separated by space): ");
	    commercialMaxWaitingTime = input.nextInt();
	    loansMaxWaitingTime = input.nextInt();
	    casualMaxWaitingTime = input.nextInt();
	}

	// Creates a customer min heap
    private static BankCustomerMinHeap createCustomerMinHeap(Scanner input) {
	    int total = input.nextInt();
	    System.out.println("In each line, kindly enter the following infos separated by spaces: arrival time (hh:mm), customer processing time (in minutes), and customer type (1 for commercial, 2 for casual, 3 for loans):");

	    BankCustomerMinHeap heap = new BankCustomerMinHeap(total);

	    for (int i = 0; i < total; ++i) {
	        BankCustomer c = createCustomer(input);
	        heap.insert(c);
	    }

	    return heap;
	}

	private static BankCustomer createCustomer(Scanner input) {
	    String arrival = input.next();
	    int timeNeeded = input.nextInt();
	    int unit = input.nextInt();

	    return new BankCustomer(arrival, timeNeeded, unit);
	}

    // Generates random customers
   public static void generateRandomCustomers(int totalCustomers) {
	    workShift1 = new BankCustomerMinHeap(totalCustomers);
	    workShift2 = new BankCustomerMinHeap(totalCustomers);
	    workShift3 = new BankCustomerMinHeap(totalCustomers);
	    commercialMaxWaitingTime = (int) Math.floor(Math.random() * 4.0D + 3.0D);
	    loansMaxWaitingTime = (int) Math.floor(Math.random() * 5.0D + 7.0D);
	    casualMaxWaitingTime = (int) Math.floor(Math.random() * 4.0D + 12.0D);

	    generateworkShiftCustomers(workShift1, "08:30", "12:00", 3, totalCustomers);
	    generateworkShiftCustomers(workShift2, "12:30", "16:30", 7, totalCustomers);
	    generateworkShiftCustomers(workShift3, "17:00", "22:00", 3, totalCustomers);

	    initializeMinHeaps();
	}

	// Generates random customers for a work shift
    private static void generateworkShiftCustomers(BankCustomerMinHeap workShift, String start, String end, int timeBase, int totalCustomers) {
	    for (int i = 0; i < totalCustomers; ++i) {
	        String arrival = generateRandomTime(start, end);
	        int timeNeeded = (int) Math.floor(Math.random() * 18.0D + timeBase);
	        int unit = (int) Math.floor(Math.random() * 3.0D + 1.0D);
	        BankCustomer c = new BankCustomer(arrival, timeNeeded, unit);
	        workShift.insert(c);
	    }
	}

   public static void initializeMinHeaps() {
	    workShift1Commercial = new BankCustomerMinHeap(workShift1.size);
	    workShift1casual = new BankCustomerMinHeap(workShift1.size);
	    workShift1loans = new BankCustomerMinHeap(workShift1.size);
	    workShift2Commercial = new BankCustomerMinHeap(workShift2.size);
	    workShift2casual = new BankCustomerMinHeap(workShift2.size);
	    workShift2loans = new BankCustomerMinHeap(workShift2.size);
	    workShift3Commercial = new BankCustomerMinHeap(workShift3.size);
	    workShift3casual = new BankCustomerMinHeap(workShift3.size);
	    workShift3loans = new BankCustomerMinHeap(workShift3.size);

	    processworkShiftQueue(workShift1, workShift1Commercial, workShift1casual, workShift1loans);
	    processworkShiftQueue(workShift2, workShift2Commercial, workShift2casual, workShift2loans);
	    processworkShiftQueue(workShift3, workShift3Commercial, workShift3casual, workShift3loans);
	}

	private static void processworkShiftQueue(BankCustomerMinHeap workShift, BankCustomerMinHeap commercialHeap, BankCustomerMinHeap casualHeap, BankCustomerMinHeap loansHeap) {
	    BankCustomer c;
	    while (!workShift.isEmpty()) {
	        c = workShift.removeMin();
	        classifyAndInsert(c, commercialHeap, casualHeap, loansHeap);
	    }
	}

	// Classifies customers and inserts them into the appropriate min heap
    private static void classifyAndInsert(BankCustomer customer, BankCustomerMinHeap commercialHeap, BankCustomerMinHeap casualHeap, BankCustomerMinHeap loansHeap) {
	    if (customer.unit == 1) {
	        commercialHeap.insert(customer);
	    } else if (customer.unit == 2) {
	        casualHeap.insert(customer);
	    } else if (customer.unit == 3) {
	        loansHeap.insert(customer);
	    }
	}

//CustomerMinHeap
	public static BankCustomerMinHeap copy(BankCustomerMinHeap CustminHeap) {
	    ArrayList<BankCustomer> temp = new ArrayList<>();
	    BankCustomerMinHeap cMinheap = new BankCustomerMinHeap(CustminHeap.size());

	    while(!CustminHeap.isEmpty()) {
	        BankCustomer c = CustminHeap.top();
	        CustminHeap.removeMin();
	        temp.add(c);
	        cMinheap.insert(c);
	    }

	    copyTempToOriginal(CustminHeap, temp);

	    return cMinheap;
	}

	private static void copyTempToOriginal(BankCustomerMinHeap CustminHeap, ArrayList<BankCustomer> temp) {
	    int i = 0;
	    while (i < temp.size()) {
	        CustminHeap.insert(temp.get(i));
	        i++;
	    }
	}

    public static void main(String[] args) throws CloneNotSupportedException {
        System.out.println("Feel welcomed!\n");
        System.out.println("workShift 1: 08:30 - 12:00");
        System.out.println("workShift 2: 12:30 - 16:30");
        System.out.println("workShift 3: 17:00 - 21:00\n");
        System.out.println("********************************************\n");
        int choice = getUserChoice();
        if (choice == 1) {
            System.out.print("Enter the number of customers for each work shift:");
            Scanner scan = new Scanner(System.in);
            int totalCustomers = scan.nextInt();
            generateRandomCustomers(totalCustomers);
            System.out.println("Generating random customers for each work shift...");
            System.out.println("Total Customers for each work shift = " + totalCustomers + " ");
            scan.close();
        } else if (choice == 2) {
            getInputFromUser();
        }
    
        System.out.println("\nMaximum waiting times for each customer type");
        System.out.println("******************************************************************\n");
        System.out.println("Max wait time for commercial is: " + commercialMaxWaitingTime + " minutes");
        System.out.println("Max wait time for loans is: " + loansMaxWaitingTime + " minutes");
        System.out.println("Max wait time for casual is: " + casualMaxWaitingTime + " minutes");
        System.out.println("\nMinimum clerks needed for each workShift and unit");
        System.out.println("******************************************************************\n");
        System.out.println("workShift 1 - Commercial: " + minimumNumberOfClerks(workShift1Commercial, commercialMaxWaitingTime, 1));
        System.out.println("workShift 1 - Loans: " + minimumNumberOfClerks(workShift1loans, loansMaxWaitingTime, 1));
        System.out.println("workShift 1 - Casual: " + minimumNumberOfClerks(workShift1casual, casualMaxWaitingTime, 1));
        System.out.println("workShift 2 - Commercial: " + minimumNumberOfClerks(workShift2Commercial, commercialMaxWaitingTime, 2));
        System.out.println("workShift 2 - Loans: "+ minimumNumberOfClerks(workShift2loans, loansMaxWaitingTime, 2));
        System.out.println("workShift 2 - Casual: " + minimumNumberOfClerks(workShift2casual, casualMaxWaitingTime, 2));
        System.out.println("workShift 3 - Commercial: " + minimumNumberOfClerks(workShift3Commercial, commercialMaxWaitingTime, 3));
        System.out.println("workShift 3 - Loans: " + minimumNumberOfClerks(workShift3loans, loansMaxWaitingTime, 3));
        System.out.println("workShift 3 - Casual: " + minimumNumberOfClerks(workShift3casual, casualMaxWaitingTime, 3));
        System.out.println("******************************************************************\n");
        System.out.println("Customer Details");
        System.out.println("******************************************************************\n");
        System.out.println("Arrival time (hh:mm)     Processing Time (minutes)      unit (1-3)");
        display();
    }    

}
