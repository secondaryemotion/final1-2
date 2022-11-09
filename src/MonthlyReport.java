
public class MonthlyReport {
    public int reportMonth;
    public ReportEntry[] reportEntries;

    MonthlyReport(String report, int month){
        String[] lines = report.replaceAll("\\r","").split("\\n");
        ReportEntry[] currentEntry = new ReportEntry[lines.length-1];
        for (int i = 1; i < lines.length; i++) {
            currentEntry[i - 1] = new ReportEntry(lines[i].split(","));
        }
        reportMonth = month;
        reportEntries = currentEntry;
    }
    public ReportEntry getEntry(int entry){
        if (reportEntries != null){
            return this.reportEntries[entry];
        } else {
            return null;
        }
    }

    public static long[] countExpensesAndIncome(MonthlyReport report){
        long totalExpense = 0;
        long totalIncome = 0;
        for (int i = 0; i < report.length(); i++){
            if (report.reportEntries[i].is_expense){
                totalExpense += report.reportEntries[i].getTotal();
            } else {
                totalIncome += report.reportEntries[i].getTotal();
            }
        }
        long[] values = {totalIncome, totalExpense};
        return values;
    }

    public static ReportEntry getMostProfitable(MonthlyReport report){
        long total = 0;
        int entry = -1;
        for (int i = 0; i < report.length(); i++){
            if (!report.reportEntries[i].is_expense){
                total = Math.max(total,report.reportEntries[i].getTotal());
                if (total == report.reportEntries[i].getTotal()){
                    entry = i;
                }
            }
        }
        return report.getEntry(entry);
    }

    public static ReportEntry getMostCostly(MonthlyReport report){
        long total = 0;
        int entry = -1;
        for (int i = 0; i < report.length(); i++){
            if (report.reportEntries[i].is_expense){
                total = Math.max(total,report.reportEntries[i].getTotal());
                if (total == report.reportEntries[i].getTotal()){
                    entry = i;
                }
            }
        }
        return report.getEntry(entry);
    }

    public int length() {
        return this.reportEntries.length;
    }
}
