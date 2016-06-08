package implementation;

import test.PumpStub;
import test.TimerStub;
import boundaryclasses.IGate;
import boundaryclasses.IHumidifier;
import boundaryclasses.IHumiditySensor;
import boundaryclasses.IManualControl;
import boundaryclasses.IOpticalSignals;
import boundaryclasses.IPump;
import boundaryclasses.ITimer;
import fsm.IFSM;

public class FSMImplementation implements IFSM {
	private IBefeuchtungsZustand state;
	private IPump pumpA;
	private IPump pumpB;
	private IGate gate;
	private IOpticalSignals signals;
	private IHumiditySensor sensor;
	private IHumidifier humidifier;
	private IManualControl operatorPanel;
	private ITimer timer;
	private final double upperBound;
	private final double lowerBound;
	//private boolean err;
	double feuchtigkeit;

	public FSMImplementation(IPump pumpA, IPump pumpB, IGate gate, IOpticalSignals signals, IHumidifier humidifier,
			IHumiditySensor sensor, IManualControl operatorPanel, ITimer timer) {
		this.state = BefeuchtungOk.getObjekt();
		this.pumpA = pumpA;
		this.pumpB = pumpB;
		this.gate = gate;
		this.signals = signals;
		this.sensor = sensor;
		this.humidifier = humidifier;
		this.operatorPanel = operatorPanel;
		this.timer = timer;
		upperBound = 60;
		lowerBound = 20;
	}
	
	private void updateState(){
		
		
		
		if(state == ErrorZustand.getObjekt()) {
			
			//this
						
		} else {
			//feuchtigkeit = sensor.getHumidity();			
			
			if (feuchtigkeit < lowerBound){
				
				state = BefeuchtungN.getObjekt();
				
			} else if (feuchtigkeit <= upperBound && feuchtigkeit >= lowerBound){
				
				state = BefeuchtungOk.getObjekt();
				
			} else {
				
				state = BefeuchtungH.getObjekt();
			}
		}
		
	}

	@Override
	public void evaluate() {
		for (int i = 0; i <= 10; i++){
		updateState();
		state.evaluate(this);
		feuchtigkeit = Math.random()*100;		
		}
		
		/*switch (state) {
		case HumidityOkay:
			// externalAction();
			updateState();
			sensor.getHumidity();
			break;

		case GateOpen:
			signals.switchLampBOn();
			
			if (err){
				state = FSMState.Error;
				}
			else {
				state =FSMState.HumidityOkay;
			}
			gate.sendOpenGate();
			while (gate.receivedGateClosed()) {
				System.out.println("Gate open: in progress...");
			}
			signals.switchLampBOff();
			break;
		// Trockung
		case GateClose:
			signals.switchLampBOn();
			gate.sendCloseGate();
			while (gate.receivedGateOpen()) {
				System.out.println("Gate close: in progress...");
			}
			signals.switchLampBOff();
			state = FSMState.Drying;
			break;
		case Drying:
			startPumps(pumpA, pumpB, timer); // activate Pump A and B
			if (pumpsActivated(pumpA, pumpB, timer)) {
				System.out.println("f");
				while (sensor.getHumidity() > upperBound) {
					System.out.println("Drying in progress...");
				} // wait
				err = false;
				System.out.println("err is false");
			} else {
				err = true;
				System.out.println("err is true");
			}
			pumpA.sendDeactivate();
			pumpB.sendDeactivate();
			state = FSMState.GateOpen;
			break;
		// Befeuchtung
		case Humidification:
			signals.switchLampAOn();
			humidifier.sendSprayOn();
			humidifier.sendSprayOff();
			signals.switchLampAOff();
			state = FSMState.HumidityOkay;
			break;
		// Fehlerzustand
		case Error:
			System.out.println("Waiting for manual control...");
			if (operatorPanel.receivedAcknowledgement()) {
				System.out.println("Manual controll in progress...");
				state = FSMState.HumidityOkay;
				err = false;
			}
			break;
		default:
			throw new NullPointerException();
		}

	}

	private void updateState() {
		if ((state != FSMState.Error)) {
			double currentHum = sensor.getHumidity();
			if (currentHum > upperBound) {
				state = FSMState.GateClose;
			} else if (currentHum < lowerBound) {
				state = FSMState.Humidification;
			} else if (currentHum >= lowerBound && currentHum <= upperBound) {
				state = FSMState.HumidityOkay;
			}
		}*/
	}

	/**
	 * Activate timer & 2 Pumps
	 */
	private static void startPumps(IPump pump1, IPump pump2, ITimer timer) {
		timer.startTime(5);
		pump1.sendActivate();
		pump2.sendActivate();
	}

	/**
	 * Check 2 Pumps
	 * 
	 * @return true - Pumps are activated, otherwise false
	 */
	private static boolean pumpsActivated(IPump pump1, IPump pump2, ITimer timer) {
		while (!timer.isTimerExpired()) {
			if (pump1.receivedActivated() && pump2.receivedActivated()) {
				return true;
			}
		}
		return false;
	}

	/*public implementation.FSMState getCurrentState() {
		return state;
	}*/
	
	public boolean startPumps() {
		timer.startTime(5);
		pumpA.sendActivate();
		pumpB.sendActivate();
				
		while(!timer.isTimerExpired()){
			
			if (pumpA.receivedActivated() && pumpB.receivedActivated()){
				
				return true;
				
			}
			
		}
		
		return false;
	}

	public IHumidifier getHumidifier(){
		
		return humidifier;
	}
	
	public IOpticalSignals getSignals(){
		
		return signals;
	}
	
	public IGate getGate(){
		
		return gate;
	}	
	
	public ITimer getTimer(){
		
		return timer;
	}
	
	public IPump getPumpA(){
		
		return pumpA;
	}
	
	public IPump getPumpB(){
		
		return pumpB;
	}
	
	public IHumiditySensor getSensor(){
		
		return sensor;
	}
	
	public double getSensorUpperBound(){
		
		return upperBound;
	}
	
	public void setState(IBefeuchtungsZustand state){
		
		this.state = state;
	}	
	
}
