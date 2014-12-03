
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
		while(Config.COUNTER != 0 && !suppressed){
			Thread.yield();
			
			System.out.println("counter: " + Config.COUNTER);
			
			Motor.C.rotate(-90);
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			if(uSSensor.getDistance() > Config.BLOCK){
				System.out.println("KANAN");
				Motor.C.rotate(90);
				Config.COUNTER += 1;
				pilot.rotate(90);
				try {
					Thread.sleep(500);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				pilot.stop();
				pilot.travel(10);
				pilot.stop();
				continue;
			}
			
			Motor.C.rotate(90);
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			if(uSSensor.getDistance() > Config.BLOCK){
				System.out.println("LURUS");
				pilot.travel(10);
				pilot.stop();
				continue;
			}
			Config.COUNTER -= 1;
			
			System.out.println("KIRI");
			pilot.stop();
			pilot.rotate(-90);
			pilot.stop();
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
		pilot.stop();
	}

	@Override
	public void suppress() {
		suppressed = true;
	}

	@Override
	public boolean takeControl() {
		return Config.COUNTER < 0;
	}
	
}
