package main;

public class Student {
    private String name;
    private int ID;

    public Student(String name, int ID){
        this.name = name;
        this.ID = ID;
    }

    public int findNum (int index){
        if (index <0 || index >2){
            throw new IndexOutOfBoundsException("Index out of Bound: " + index);
        }
        return index;
    }

    @Override
    public String toString(){
        return this.name + " " + this.ID;
    }
    public static void main(String[] args) {
        System.out.println("Huanfu Test");
    }
}
