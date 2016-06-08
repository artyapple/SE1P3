package implementation;

import java.util.Scanner;

public class ErrorZustand implements IBefeuchtungsZustand {
	
	Scanner sc;
	private static ErrorZustand objekt = null;
	
	boolean error = true;
		
	public static ErrorZustand getObjekt(){
				
		if(objekt == null){
			
			objekt = new ErrorZustand();
			
		}
		
		return objekt;
	}

	@Override
	public void evaluate(FSMImplementation system) {
		System.out.println("Einen Knopf drücken bitte um vom Errorzustand wegzukommen");
		sc = new Scanner(System.in);
		String s;
		s = sc.nextLine();
		while(error){
			s = sc.nextLine();			
			if(s != null){
				
				error = false;
				
			}
		}//while
		
	}
		
}
