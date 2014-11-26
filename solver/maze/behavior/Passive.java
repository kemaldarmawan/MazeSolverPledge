package solver.maze.behavior;
import solver.maze.main.Config;
import lejos.nxt.Motor;
import lejos.nxt.SensorPort;
import lejos.nxt.UltrasonicSensor;
import lejos.robotics.navigation.DifferentialPilot;
import lejos.robotics.subsumption.Behavior;

public class Passive implements Behavior {
	private boolean suppressed = false;
	private UltrasonicSensor uSSensor;
	private DifferentialPilot pilot;

	public Passive() {
		uSSensor = new UltrasonicSensor(SensorPort.S3);
		pilot = new DifferentialPilot(Config.WHEELDIAMETER, Config.TRACKWIDTH, Motor.A, Motor.B);
	}

	@Override
	public void action() {
		suppressed = false;
		pilot.forward();

		while (uSSensor.getDistance() > Config.BLOCK && !suppressed) {
			Thread.yield();
		}
		
		// belok kiri pertama masih kurang.
		pilot.rotate(-120);
		Config.COUNTER = -1;
	}

	@Override
	public void suppress() {
		suppressed = true;
	}

	@Override
	public boolean takeControl() {
		return Config.COUNTER == 0;
	}

}
