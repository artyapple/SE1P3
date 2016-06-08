package implementation;


public class BefeuchtungH implements IBefeuchtungsZustand{
	
	private static BefeuchtungH objekt = null;
	
	public static BefeuchtungH getObjekt(){
		
		if(objekt == null){
			
			objekt = new BefeuchtungH();
			
		}
		
		return objekt;
	}

	@Override
	public void evaluate(FSMImplementation system) {
		
		system.getSignals().switchLampBOn();
		system.getGate().sendCloseGate();
		
		/*Warten, bis Tor geschlossen */
		
		while(!system.getGate().receivedGateClosed()){
			
		}
		
		system.getSignals().switchLampBOff();
		if (system.startPumps()){
			
			while(system.getSensor().getHumidity() > system.getSensorUpperBound()){
				
			}
			
			system.getPumpA().sendDeactivate();
			system.getPumpB().sendDeactivate();
			system.getSignals().switchLampBOn();
			system.getGate().sendOpenGate();
			
			while(!system.getGate().receivedGateOpen()){
				
				
			}
			
			system.getSignals().switchLampBOff();
			system.setState(BefeuchtungOk.getObjekt());			
			
		} else{
			
			system.getPumpA().sendDeactivate();
			system.getPumpB().sendDeactivate();
			system.getGate().sendOpenGate();
			
			system.setState(ErrorZustand.getObjekt());
			
		}		
		
	}
	
}
