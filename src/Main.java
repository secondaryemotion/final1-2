import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Scanner;

public class Main {
    public static MonthlyReport[] allMonthlyReports;
    public static YearlyReport yearlyReport;
    public static int monthsCount = 3;
    static String[] months = {"","January","February","March","April","May","June","July", "August",
            "September", "October", "November", "December"};

    public static void main(String[] args){
        Scanner scanner = new Scanner(System.in);
        printMenu();
        int userInput = scanner.nextInt();
        while (userInput != 0){
            switch (userInput){
                case 1: {
                    readMonthlyReports();
                    printMenu();
                    break;
                }
                case 2: {
                    readYearlyReport();
                    printMenu();
                    break;
                }
                case 3: {
                    compareReports();
                    printMenu();
                    break;
                }
                case 4: {
                    showMonthReportsStats();
                    printMenu();
                    break;
                }
                case 5: {
                    showYearReportStats();
                    printMenu();
                    break;
                }
                default: {
                    System.out.println("Please, choose one of the options");
                    printMenu();
                    break;
                }
            }
            userInput = scanner.nextInt();
        }
        System.out.println("Exiting...");
    }

    public static void printMenu(){
        System.out.println("Choose an option by entering a number:");
        System.out.println("1 - Read all monthly reports");
        System.out.println("2 - Read yearly report");
        System.out.println("3 - Check reports");
        System.out.println("4 - Show monthly stats");
        System.out.println("5 - Show yearly stats");
        System.out.println("0 - Exit");
    }

    public static void readMonthlyReports(){
        String monthPathSample = "csv/m.2021";
        allMonthlyReports = new MonthlyReport[monthsCount+1];
        for (int i = 1; i <= monthsCount; i++){
            long startTime = System.currentTimeMillis();
            String monthNumber = (i < 10) ? ("0" + i) : Integer.toString(i);
            String monthReportFile = readFile(monthPathSample + monthNumber + ".csv");
            if (monthReportFile != null) {
                allMonthlyReports[i] = new MonthlyReport(monthReportFile,i);

            } else {
                break;
            }
            long endTime = System.currentTimeMillis();
            System.out.println("Month report " + i + " read in " + (endTime - startTime) + " ms");
            //System.out.println("100th entry: " + allMonthlyReports[i - 1].getEntry(99).printEntry());
        }

    }

    public static void readYearlyReport(){
        String yearPath = "csv/y.2021.csv";
        String yearReportFile = readFile(yearPath);
        if (yearReportFile != null) {
            long startTime = System.currentTimeMillis();
            yearlyReport = new YearlyReport(yearReportFile);
            long endTime = System.currentTimeMillis();
            System.out.println("Year report read in " + (endTime - startTime) + " ms");
            System.out.println(yearlyReport.printMonthEntry(2));
        }

    }

    public static void compareReports(){
        if (allMonthlyReports == null || yearlyReport == null){
            System.out.println("Please, read the reports again.");
        } else {
            for (int i = 1; i <= monthsCount; i++){
                long[] monthStats = MonthlyReport.countExpensesAndIncome(allMonthlyReports[i]);
                if (yearlyReport.getMonthEntry(i).expense == monthStats[0] && yearlyReport.getMonthEntry(i).income == monthStats[1]){
                    System.out.println("Everything is fine with month " + i + " reports.");
                } else {
                    System.out.println("Please, re-check your reports for month " + i);
                    System.out.println("Expenses in yearly report are " + yearlyReport.getMonthEntry(i).expense + ", but they are " + monthStats[0]);
                    System.out.println("Incomes in yearly report are " + yearlyReport.getMonthEntry(i).income + ", but they are " + monthStats[1]);
                }
            }
        }
    }

    public static void showMonthReportsStats() {
        if (allMonthlyReports == null) {
            System.out.println("Please, read the reports again.");
        } else {
            for (int i = 1; i <= monthsCount; i++){
                System.out.println(months[i] + " stats:");
                System.out.println("Most profitable sell: " + MonthlyReport.getMostProfitable(allMonthlyReports[i]).item_name + " " + MonthlyReport.getMostProfitable(allMonthlyReports[i]).getTotal());
                System.out.println("Most costly expense: " + MonthlyReport.getMostCostly(allMonthlyReports[i]).item_name + " " + MonthlyReport.getMostCostly(allMonthlyReports[i]).getTotal());
            }
        }
    }

    public static void showYearReportStats() {
        if (yearlyReport == null) {
            System.out.println("Please, read the report again.");
        } else {
            System.out.println("Year 2021 stats:");
            long totalExpense = 0;
            long totalIncome = 0;
            for (int i = 1; i <= monthsCount; i++){
                System.out.println("Month " + i + " balance:" + (yearlyReport.getMonthEntry(i).income-yearlyReport.getMonthEntry(i).expense));
                totalIncome += yearlyReport.getMonthEntry(i).income;
                totalExpense += yearlyReport.getMonthEntry(i).expense;
            }
            System.out.println("Expenses median " + totalExpense/monthsCount);
            System.out.println("Income median " + totalIncome/monthsCount);
        }
    }

    public static String readFile(String path){
        try {
            return Files.readString(Path.of(path));
        } catch (IOException E) {
            System.out.println("There is no such file. Please, check again.");
            return null;
        }
    }


}

