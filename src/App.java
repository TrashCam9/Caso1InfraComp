import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Scanner;

public class App {
	private static ArrayList<Buzon> buzones;
	private static ArrayList<Mensajero> mensajeros;
	private static int limiteMensajes;
	public static boolean transformacion(String t) {
		boolean bo = false;
		if(t.equals("true")) {
			return true;
		}
		return bo;
	}

	public static void proceed() throws Exception{
		LinkedList<String> mensajesATransmitir = new LinkedList<String>();
		Scanner objScanner = new Scanner(System.in);
    	System.out.println("Ingrese el numero de mensajes que quiere enviar: ");
		Integer nMensajes = objScanner.nextInt();
		if (nMensajes>limiteMensajes){
			objScanner.close();
			throw new Exception("El numero total de mensajes no debe exceder la suma del cupo de los buzones");
		}else{
			objScanner.nextLine();
			for (int i = 0; i <nMensajes; i++){
				System.out.println("Ingrese el mensaje: ");
				mensajesATransmitir.add(objScanner.nextLine());
			}
			objScanner.close();
			mensajeros.get(0).transmitir(mensajesATransmitir);
			mensajeros.get(0).start();
			mensajeros.get(1).start();
			mensajeros.get(2).start();
			mensajeros.get(3).start();

		}
	}
	
    public static void main(String args[]){
    	
    	buzones = new ArrayList<Buzon>();
    	mensajeros = new ArrayList<Mensajero>();
		limiteMensajes = 0;
    	try {
    	      File myObj = new File("config.txt");
    	      Scanner myReader = new Scanner(myObj);
    	      int i = 1;
    	      while (myReader.hasNextLine()) {
    	        String data = myReader.nextLine();
    	        final String [] dataStr = data.split(" ");
				
				if(i<=4) {
					Buzon buzon = new Buzon(dataStr[0],Integer.parseInt(dataStr[1]));
					limiteMensajes += Integer.parseInt(dataStr[1]);
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
			  proceed();
		} catch (FileNotFoundException e) {
			System.out.println("Ha ocurrido un error en la lectura");
			e.printStackTrace();
		} catch (Exception e){

		}
    
    }
}