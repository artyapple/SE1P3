package implementation;


public class BefeuchtungN implements IBefeuchtungsZustand{
	
	private static BefeuchtungN objekt = null;
	
	public static BefeuchtungN getObjekt(){
		
		if(objekt == null){
			
			objekt = new BefeuchtungN();
			
		}
		
		return objekt;
	}

	@Override
	public void evaluate(FSMImplementation system) {
		
		system.getSignals().switchLampAOn();
		system.getHumidifier().sendSprayOn();		
			
	}

}

