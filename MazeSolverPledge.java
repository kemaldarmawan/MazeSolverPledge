
import lejos.robotics.subsumption.Arbitrator;
import lejos.robotics.subsumption.Behavior;

public class MazeSolverPledge {

	public static void main(String[] args) {
		Config.COUNTER = 0;
		
		Behavior moveForward = new Passive();
		Behavior turnLeft = new Active();
		
		Behavior []bList = {moveForward, turnLeft};
		Arbitrator arby = new Arbitrator(bList);
		arby.start();
	}

}
 