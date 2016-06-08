package implementation;

public class BefeuchtungOk implements IBefeuchtungsZustand {

	private static BefeuchtungOk objekt = null;
	
	public static BefeuchtungOk getObjekt() {

		if (objekt == null) {
			objekt = new BefeuchtungOk();
		}

		return objekt;
	}

	@Override
	public void evaluate(FSMImplementation system) {

		if (system.getHumidifier().isHumidifierActivated()) {
			system.getHumidifier().sendSprayOff();
			system.getSignals().switchLampAOff();
		}
	}

}