public class YearlyReport {
    public MonthEntry[] yearlyReport;

    YearlyReport(String report){
        String[] lines = report.replaceAll("\\r","").split("\\n");
        MonthEntry[] currentReport = new MonthEntry[lines.length/2+1];
        for (int i = 1; i < lines.length; i++){
            String[] line = lines[i].split(",");
            int month = Integer.parseInt(line[0]);
            if (currentReport[month] == null){
                currentReport[month] = new MonthEntry();
            }
            currentReport[month].setValue(Long.parseLong(line[1]),Boolean.parseBoolean(line[2]));
        }
        yearlyReport = currentReport;
    }

    public String printMonthEntry(int month){
        return "Expense is " + yearlyReport[month].expense + ". Income is " + yearlyReport[month].income;
    }

    public MonthEntry getMonthEntry(int month){
        return this.yearlyReport[month];
    }
}
