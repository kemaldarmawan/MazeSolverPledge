
import lejos.nxt.SensorPort;
import lejos.robotics.subsumption.Arbitrator;
import lejos.robotics.subsumption.Behavior;

public class MazeSolverPledge {

	public static void main(String[] args) {
		Config.COUNTER = 1;
		
		Behavior moveForward = new Passive();
		Behavior turnLeft = new Active();
		Behavior lineTracer = new Forward(SensorPort.S1);
		
		Behavior []bList = {lineTracer,moveForward, turnLeft};
		Arbitrator arby = new Arbitrator(bList);
		arby.start();
	}

}
 