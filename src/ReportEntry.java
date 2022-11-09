public class ReportEntry {
    public String item_name;
    public boolean is_expense;
    public int quantity;
    public int sum_of_one;

    public ReportEntry(String[] line){
        item_name = line[0];
        is_expense = Boolean.parseBoolean(line[1]);
        quantity = Integer.parseInt(line[2]);
        sum_of_one = Integer.parseInt(line[3]); //check for carriage return
    }

    public String printEntry(){
        return item_name + "," + is_expense + "," + quantity + "," + sum_of_one; //who said it should be human-readable
    }

    public long getTotal(){
        return quantity*sum_of_one;
    }
}
