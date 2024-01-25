import java.util.*;

public class Relation {
    private Boolean empty;
    private ArrayList<String> columns;
    private String name;
    private int colNum;
    private int rowNum;
    private ArrayList<ArrayList<String>> tuples;
    public Boolean isEmpty(){return empty;}
    public String getName(){return name;}
    public ArrayList<String> getColumns(){return columns;}
    public ArrayList<ArrayList<String>> getTuples(){return tuples;}

    public int getColNum() {
        return colNum;
    }
    public int getRowNum(){
        return rowNum;
    }

    public Relation(){
        columns = new ArrayList<>();
        tuples = new ArrayList<>();
        colNum = 0;
        rowNum = 0;
        empty = true;
        name = "This is a place holder";
    }
    public Relation(String n, ArrayList<ArrayList<String>> data, ArrayList<String> cols){
        columns = cols;
        tuples = data;
        colNum = cols.size();
        rowNum = data.size();
        empty = false;
        name = n;
    }
    public void printRelation(){
        System.out.println(name+":");
        System.out.println(columns);
        for(int i=0;i<tuples.size();i++){
            System.out.println(tuples.get(i));
        }
    }
    public void clear(){
        columns = new ArrayList<>();
        tuples = new ArrayList<>();
        colNum = 0;
        rowNum = 0;
        empty = true;
        name = "";
    }
    public void getData(){
        String[] columnNamesTemp;
        Scanner input = new Scanner(System.in);
        String choice = "";
        System.out.println("Enter the relation name:");
        name = input.nextLine();
        while(true){
            System.out.println("Enter column names seperated by only commas and no spaces:");
            choice = input.nextLine();
            columnNamesTemp = choice.split(",");
            for(int i=0;i<columnNamesTemp.length-1;i++){
                System.out.print(columnNamesTemp[i]+", ");
            }
            System.out.print(columnNamesTemp[columnNamesTemp.length-1]+"\n");
            System.out.println("Are these column names correct? (Enter y or n)");
            choice = input.nextLine();
            if(choice.equals("y")){
                    columns.addAll(List.of(columnNamesTemp));
                    colNum = columnNamesTemp.length;
                    break;
            }
        }
        String[] tuple;
        while(true){
            System.out.println("Enter a new tuple separated by commas only, or type quit to exit:");
            choice = input.nextLine();
            if(choice.equals("quit")){
                break;
            }
            tuple = choice.split(",");
            if(tuple.length!=colNum){
                System.out.println("Tuples must have "+ colNum+" values");
            }else{
                tuples.add(new ArrayList<>());
                tuples.get(rowNum).addAll(List.of(tuple));
                rowNum++;
            }
        }
        empty = false;
    }
}
