import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class App {
	public static boolean transformacion(String t) {
		boolean bo = false;
		if(t.equals("true")) {
			return true;
		}
		return bo;
	}
	
    public static void main(String args[]){
    	
    	ArrayList<Buzon> buzones = new ArrayList<Buzon>();
    	ArrayList<Mensajero> mensajeros = new ArrayList<Mensajero>();
    	try {
    	      File myObj = new File("config.txt");
    	      Scanner myReader = new Scanner(myObj);
    	      int i = 1;
    	      while (myReader.hasNextLine()) {
    	        String data = myReader.nextLine();
    	        final String [] dataStr = data.split(" ");
				
				if(i<=4) {
					Buzon buzon = new Buzon(dataStr[0],Integer.parseInt(dataStr[1]));
					buzones.add(buzon);
				}else if(i==5) {
					Mensajero mensajero = new Mensajero(transformacion(dataStr[2]), transformacion(dataStr[3]),Integer.parseInt(dataStr[0]),buzones.get(3), buzones.get(0), Integer.parseInt(dataStr[1]));
					mensajeros.add(mensajero);
				}else if(i==6) {
					Mensajero mensajero = new Mensajero(transformacion(dataStr[2]), transformacion(dataStr[3]),Integer.parseInt(dataStr[0]),buzones.get(0), buzones.get(1), Integer.parseInt(dataStr[1]));
					mensajeros.add(mensajero);
				}else if(i==7) {
					Mensajero mensajero = new Mensajero(transformacion(dataStr[2]), transformacion(dataStr[3]),Integer.parseInt(dataStr[0]),buzones.get(1), buzones.get(2), Integer.parseInt(dataStr[1]));
					mensajeros.add(mensajero);
				}else if(i==8) {
					Mensajero mensajero = new Mensajero(transformacion(dataStr[2]), transformacion(dataStr[3]),Integer.parseInt(dataStr[0]),buzones.get(2), buzones.get(3), Integer.parseInt(dataStr[1]));
					mensajeros.add(mensajero);
				}
				i++;
				
    	      }
    	      myReader.close();
    	    } catch (FileNotFoundException e) {
    	      System.out.println("Ha ocurrido un error en la lectura");
    	      e.printStackTrace();
    	    }
    	  
    
    }
}