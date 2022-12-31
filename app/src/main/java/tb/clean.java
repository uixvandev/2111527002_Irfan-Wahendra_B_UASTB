package tb;

public class clean {
    //constructor clean
    public clean(){
    }
    public static void clearScreen() {  
        System.out.print("\033[H\033[2J");  
        System.out.flush();  
    }  
}
