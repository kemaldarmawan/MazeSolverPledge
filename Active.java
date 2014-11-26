import lejos.nxt.Motor;
import lejos.nxt.SensorPort;
import lejos.nxt.UltrasonicSensor;
import lejos.robotics.navigation.DifferentialPilot;
import lejos.robotics.subsumption.Behavior;

public class Active implements Behavior{
	private boolean suppressed = false;
	private UltrasonicSensor uSSensor;
	private DifferentialPilot pilot;

	public Active(){
		uSSensor = new UltrasonicSensor(SensorPort.S3);
		pilot = new DifferentialPilot(Config.WHEELDIAMETER, Config.TRACKWIDTH, Motor.A, Motor.B);
	}
	
	@Override
	public void action() {
		suppressed = false;
		while(Config.counter != 0 && !suppressed){
			Thread.yield();
			
			System.out.println("counter: " + Config.counter);
			
			Motor.C.rotate(90);
			if(uSSensor.getDistance() > Config.BLOCK){
				System.out.println("BAMPUKI");
				Motor.C.rotate(-90);
				Config.counter += 1;
				pilot.rotate(120);
				pilot.travel(10);
				pilot.stop();
				continue;
			}
			
			Motor.C.rotate(-90);
			if(uSSensor.getDistance() > Config.BLOCK){
				System.out.println("KACE");
				pilot.travel(10);
				pilot.stop();
				continue;
			}
			Config.counter -= 1;
			
			System.out.println("CUKIMAI");
			pilot.rotate(-120);
		}
		
		pilot.stop();
	}

	@Override
	public void suppress() {
		suppressed = true;
	}

	@Override
	public boolean takeControl() {
		return Config.counter != 0;
	}
	
}
