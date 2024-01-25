import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class Menu {
    Scanner input = new Scanner(System.in);
    Relation r1;
    Relation r2;
    Relation r3;
    public Menu(){
        r1 = new Relation();
        r2 = new Relation();
        r3 = new Relation();
    }
    void menu(){
        String choice = "";

        while(true){
            System.out.print("What would you like to do?\n1. Enter new relation\n2. Preform operations\n3. Delete a relation\n4. Print Relations\n5. Syntax Guide\n6. Quit\n");
            choice = input.nextLine();  // Read user input
            if(choice.equals("1")){
                makeNewRelation();
            } else if (choice.equals("2")) {
                operationsMenu();
            } else if (choice.equals("3")) {
                deleteRelation();
            }else if(choice.equals("4")){
                printRelations();
            }else if(choice.equals("5")){
                syntaxGuide();
            }else if(choice.equals("6")){
                break;
            }else{
                System.out.println("Not a valid choice");
            }
        }
    }
    void syntaxGuide(){
        System.out.println("Syntax Guide:");
        System.out.println("Selection: use keyword select for selection, use >,<,>=,<= and = for conditionals. Eg. select Column>x(Relation)");
        System.out.println("Projection: use keyword project, separate desired columns by commas. Eg. project column1,column2(Relation)");
        System.out.println("Join: use keyword join for regular join, rjoin for right outer join, ljoin for left outer join and fjoin for full join. Eg. (Relation1)rjoin Relation1.column=Relation2.column(Relation2)");
        System.out.println("Set Operators: Use n for intersection, use u for union and - for minus. Eg. (Relation1)n(Relation2)");
    }
    void makeNewRelation(){
        if(r1.isEmpty()){
            r1.getData();
            r1.printRelation();
        }else if(r2.isEmpty()){
            r2.getData();
            r2.printRelation();
        }else{
            System.out.println("Both relations are full right now, enter 1 to delete a relation or 2 to cancel:");
            String choice = input.nextLine();
            if(choice.equals("1")){
                deleteRelation();
            }
        }
    }
    void deleteRelation(){
        String choice = "";
        if(r1.isEmpty()&& r2.isEmpty()){
            System.out.println("There are no relations to delete");
            return;
        }else if(r2.isEmpty()){
            System.out.print("What relation do you want to delete?\n1."+r1.getName()+"\n");
            choice = input.nextLine();
            if(choice.equals("1")){
                System.out.println("Relation "+r1.getName()+" deleted.");
                r1.clear();
            }else{
                System.out.println("Not a valid input, no relation deleted");
            }
        }else if(r1.isEmpty()){
            System.out.print("What relation do you want to delete?\n1."+r2.getName()+"\n");
            choice = input.nextLine();
            if(choice.equals("1")){
                System.out.println("Relation "+r2.getName()+" deleted.");
                r2.clear();
            }else{
                System.out.println("Not a valid input, no relation deleted");
            }
        }else{
            System.out.print("What relation do you want to delete?\n1."+r1.getName()+"\n2."+r2.getName()+"\n");
            choice = input.nextLine();
            if(choice.equals("1")){
                System.out.println("Relation "+r1.getName()+" deleted.");
                r1.clear();
            }else if(choice.equals("2")){
                System.out.println("Relation "+r2.getName()+" deleted.");
                r2.clear();
            }else{
                System.out.println("Not a valid input, no relation deleted");
            }
        }
    }
    void printRelations(){
        if(!r1.isEmpty()){
            r1.printRelation();
        }
        if(!r2.isEmpty()){
            r2.printRelation();
        }
        if(!r3.isEmpty()){
            r3.printRelation();
        }
    }
    void operationsMenu(){
        if(r1.isEmpty()&& r2.isEmpty()){
            System.out.println("You don't have any relations to preform operations on");
            return;
        }
        System.out.println("Enter your query or enter x to cancel: (refer to syntax guide for help)");
        String choice = input.nextLine();
        if(choice.equals("x")){
            return;
        }
        String [] parsing = choice.split("\\(");

        r3 = parseInputQuery(r3, parsing);
        if(r3!=null){
            r3.printRelation();
        }else{
            System.out.println("Operation could not be completed");
        }


        /*r3 = projection(r1, "id,name");
        r3.printRelation();
        r3 = selection(r3, "id", ">", "5");
        r3.printRelation();*/

    }
    boolean containsBinaryOperator(String s){
        if(s.contains("join")||s.contains("-")||s.contains("n")||s.contains("u")){return true;}
        return false;
    }
    String findColumnName(String s){
        ArrayList<String> cols1 = r1.getColumns();
        ArrayList<String> cols2 = r2.getColumns();
        for(String c: cols1){
            if(s.contains(c)){
                return c;
            }
        }
        for(String c: cols2){
            if(s.contains(c)){
                return c;
            }
        }
        return "";
    }
    String selectFindOperand(String s){
        if(s.contains(">=")){
            return ">=";
        }
        if(s.contains("<=")){
            return "<=";
        }
        if(s.contains(">")){
            return ">";
        }
        if(s.contains("<")){
            return "<";
        }
        if(s.contains("!=")){
            return "!=";
        }
        if(s.contains("=")){
            return "=";
        }
        return "";
    }
    String selectFindValue(String s){
        String [] g = s.split("[=><]");
        return g[g.length-1];
    }
    String getJoinType(String s){
        if(s.contains("fjoin")){
            return "lr";
        }else if(s.contains("ljoin")){
            return "l";
        }else if(s.contains("rjoin")){
            return "r";
        }
        return "";
    }
    String findColumnNames(String s){
        String[] cols = s.split(" ");
        return cols[cols.length-1];
    }
    String getJoinCondition(String s){
        String[] condition = s.split("join");
        if(condition.length>1){
            return condition[1];
        }else{
            return "";
        }

    }
    Relation parseInputQuery(Relation relation, String[] operations){

        if(operations.length==1 && operations[0].contains(r1.getName())){
            return r1;
        }else if (operations.length==1&& operations[0].contains(r2.getName())){
            return r2;
        }
        String curOperation = operations[0];
        String[] newOperations = new String[operations.length-1];
        for(int i=1;i<operations.length;i++){
            newOperations[i-1] = operations[i];
        }
        if(operations[0].equals("")){
            return parseInputQuery(relation,newOperations);
        }
        if(curOperation.contains("select")){
            String columnName = findColumnName(curOperation);
            String operand = selectFindOperand(curOperation);
            String val = selectFindValue(curOperation);
            return selection(parseInputQuery(relation,newOperations), columnName, operand, val);
        }else if(curOperation.contains("project")){
            String columnNames = findColumnNames(curOperation);
            return projection(parseInputQuery(relation,newOperations),columnNames);
        }else if(curOperation.contains("join")){
            String type = getJoinType(curOperation);
            String condition = getJoinCondition(curOperation);
            return join(parseInputQuery(relation, new String[]{curOperation}),parseInputQuery(relation,newOperations), type,condition);
        }else if(curOperation.contains(")n")){
            return intersection(parseInputQuery(relation, new String[]{curOperation}),parseInputQuery(relation,newOperations));
        }else if(curOperation.contains(")u")){
            return union(parseInputQuery(relation, new String[]{curOperation}),parseInputQuery(relation,newOperations));
        }else if(curOperation.contains(")-")){
            return minus(parseInputQuery(relation, new String[]{curOperation}),parseInputQuery(relation,newOperations));
        }else{
            System.out.println("Not valid operation");
            return null;
        }
    }

    Relation join(Relation relation1, Relation relation2, String type, String condition){
        String[] columnToJoin = condition.split("[.=]");
        System.out.println(Arrays.toString(columnToJoin));
        int index1 = 0;
        int index2 = 0;
        if(!(columnToJoin.length <3)){
            if(columnToJoin[0].contains(relation1.getName())){
                index1 = getIndexOfColumn(relation1.getColumns(),columnToJoin[1]);
                index2 = getIndexOfColumn(relation2.getColumns(),columnToJoin[3]);
            }else{
                index1 = getIndexOfColumn(relation1.getColumns(),columnToJoin[3]);
                index2 = getIndexOfColumn(relation2.getColumns(),columnToJoin[1]);
            }
        }
        if(index1==-1||index2==-1){
            return null;
        }
        ArrayList<ArrayList<String>> tuples1 = relation1.getTuples();
        ArrayList<ArrayList<String>> tuples2 = relation2.getTuples();
        ArrayList<String> columns1 = relation1.getColumns();
        ArrayList<String> columns2 = relation2.getColumns();
        ArrayList<String> newColumns = new ArrayList<>(columns1);
        newColumns.addAll(columns2);
        ArrayList<ArrayList<String>> newTuples = new ArrayList<>();
        for(ArrayList<String> tup1: tuples1){
            boolean match = false;
            for(ArrayList<String> tup2: tuples2){
                if(tup1.get(index1).equals(tup2.get(index2))){
                    ArrayList<String> newTuple = new ArrayList<>(tup1);
                    newTuple.addAll(tup2);
                    newTuples.add(newTuple);
                    match = true;
                }
            }
            if(type.contains("l")&&!match){
                ArrayList<String> newTuple = new ArrayList<>(tup1);
                for(int i=0;i<relation2.getColNum();i++){
                    newTuple.add("");
                }
                newTuples.add(newTuple);
            }
        }
        for(ArrayList<String> tup2: tuples2){
            boolean match = false;
            for(ArrayList<String> tup1: tuples1){
                if(tup1.get(index1).equals(tup2.get(index2))){
                    match = true;
                }
            }
            if(type.contains("r")&&!match){
                ArrayList<String> newTuple = new ArrayList<>();
                for(int i=0;i<relation1.getColNum();i++){
                    newTuple.add("");
                }
                newTuple.addAll(tup2);
                newTuples.add(newTuple);
            }
        }

        return new Relation("Output", newTuples, newColumns);
    }
    Boolean equivalentTuples(ArrayList<String> tuple1, ArrayList<String> tuple2){
        if(tuple1.size()!=tuple2.size()){return false;}
        for(int i=0;i<tuple1.size();i++){
            if(!tuple1.get(i).equals(tuple2.get(i))){return false;}
        }
        return true;
    }
    Relation minus(Relation relation1, Relation relation2){
        if(relation1.getColumns().size()!=relation2.getColumns().size()){return null;}
        ArrayList<String> columns = relation1.getColumns();
        ArrayList<ArrayList<String>> tuples1 = relation1.getTuples();
        ArrayList<ArrayList<String>> tuples2 = relation2.getTuples();
        ArrayList<ArrayList<String>> newTuples = new ArrayList<>();
        for(ArrayList<String> tup1: tuples1){
            boolean contained = false;
            for(ArrayList<String> tup2: tuples2){
                if(equivalentTuples(tup2,tup1)){
                    contained = true;
                }
            }
            if(!contained){newTuples.add(tup1);}
        }
        return new Relation("Output", newTuples,columns);
    }
    Relation union(Relation relation1, Relation relation2){
        if(relation1.getColumns().size()!=relation2.getColumns().size()){return null;}
        ArrayList<String> columns = relation1.getColumns();
        ArrayList<ArrayList<String>> tuples1 = relation1.getTuples();
        ArrayList<ArrayList<String>> tuples2 = relation2.getTuples();
        ArrayList<ArrayList<String>> newTuples = new ArrayList<>(tuples1);
        for(ArrayList<String> tup2: tuples2){
            boolean contained = false;
            for(ArrayList<String> tup: tuples1){
                if(equivalentTuples(tup2,tup)){
                    contained = true;
                }
            }
            if(!contained){newTuples.add(tup2);}
        }
        return new Relation("Output", newTuples,columns);
    }
    Relation intersection(Relation relation1, Relation relation2){
        if(relation1.getColumns().size()!=relation2.getColumns().size()){return null;}
        ArrayList<String> columns = relation1.getColumns();
        ArrayList<ArrayList<String>> tuples1 = relation1.getTuples();
        ArrayList<ArrayList<String>> tuples2 = relation2.getTuples();
        ArrayList<ArrayList<String>> newTuples = new ArrayList<>();
        for(ArrayList<String> tup1 : tuples1){
            for(ArrayList<String> tup2 : tuples2){
                if(equivalentTuples(tup1,tup2)){
                    newTuples.add(tup1);
                }
            }
        }
        return new Relation("Output", newTuples, columns);
    }
    Relation projection(Relation r, String columnNames){
        String [] returnColumns = columnNames.split(",");
        ArrayList<String> columns = r.getColumns();
        ArrayList<Integer> indices = new ArrayList<>();
        ArrayList<ArrayList<String>> oldTuples = r.getTuples();
        ArrayList<ArrayList<String>> newTuples = new ArrayList<>();
        for (String returnColumn : returnColumns) {
            int cur = getIndexOfColumn(columns, returnColumn);
            if (cur != -1) {
                indices.add(cur);
            }
        }
        ArrayList<String> newColumns = new ArrayList<>();
        for (Integer index : indices) {
            newColumns.add(columns.get(index));
        }
        for (ArrayList<String> oldTuple : oldTuples) {
            ArrayList<String> cur = new ArrayList<>();
            for (Integer index : indices) {
                cur.add(oldTuple.get(index));
            }
            newTuples.add(cur);
        }
        return new Relation("Output", newTuples, newColumns);
    }
    Relation selection(Relation r, String columnName, String operand, String value){
        ArrayList<String> columns = r.getColumns();
        ArrayList<ArrayList<String>> tuples = r.getTuples();
        ArrayList<ArrayList<String>> newTuples = new ArrayList<>();
        int index = getIndexOfColumn(columns,columnName);
        if(index == -1){return null;}
        Boolean truthVal;
        for(int i=0;i<tuples.size();i++){
            truthVal = evaluateBoolean(tuples.get(i).get(index),operand,value);
            if(truthVal){
                newTuples.add(tuples.get(i));
            }
        }
        return new Relation("Output", newTuples, columns);
    }
    Relation cartesianProduct(Relation relation1, Relation relation2){
        ArrayList<String> columns = new ArrayList<>();
        columns.addAll(relation1.getColumns());
        columns.addAll(relation2.getColumns());
        ArrayList<ArrayList<String>> newTuples = new ArrayList<>();
        ArrayList<ArrayList<String>> tuples1 = relation1.getTuples();
        ArrayList<ArrayList<String>> tuples2 = relation2.getTuples();
        for(ArrayList<String> tuple1 : tuples1){
            for(ArrayList<String> tuple2: tuples2){
                ArrayList<String> cur = new ArrayList<>();
                cur.addAll(tuple1);
                cur.addAll(tuple2);
                newTuples.add(cur);
            }
        }
        return new Relation("Output", newTuples, columns);
    }
    Boolean evaluateBoolean(String inputValue, String operand, String conditionValue){
        if(operand.equals("=")){
            return (inputValue.equals(conditionValue));
        } else if (operand.equals(">")) {
            return Integer.valueOf(inputValue)>Integer.valueOf(conditionValue);
        }else if (operand.equals(">=")) {
            return Integer.valueOf(inputValue)>=Integer.valueOf(conditionValue);
        }else if (operand.equals("<")) {
            return Integer.valueOf(inputValue)<Integer.valueOf(conditionValue);
        }else if (operand.equals("<=")) {
            return Integer.valueOf(inputValue)<=Integer.valueOf(conditionValue);
        }else if (operand.equals("!=")) {
            return !inputValue.equals(conditionValue);
        }else{
            return false;
        }
    }
    Integer getIndexOfColumn(ArrayList<String> columns, String columnName){
        for(int i=0;i<columns.size();i++){
            if(columns.get(i).equals(columnName)){
                return i;
            }
        }
        return -1;
    }

}
