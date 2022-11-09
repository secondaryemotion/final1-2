public class MonthEntry {
    public long expense;
    public long income;

    public void setValue(long value, boolean is_expense){
        if (is_expense){
            this.expense = value;
        } else {
            this.income = value;
        }
    }
}
