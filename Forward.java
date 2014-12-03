import lejos.nxt.LightSensor;
import lejos.nxt.Motor;
import lejos.nxt.SensorPort;
import lejos.nxt.TouchSensor;
import lejos.robotics.subsumption.Behavior;


public class Forward implements Behavior{
	private boolean suppressed = false;
	private LightSensor light;
	private TouchSensor touch;
	private int val;
	
	float speedB = 350f;
	float speedC = 350f;
	
	public Forward(SensorPort port){
		light = new LightSensor(port);
		touch = new TouchSensor(SensorPort.S2);
	}
	
	@Override
	public void action() {
		System.out.println("forward");
		
		suppressed = false;
		Motor.A.forward();
		Motor.B.forward();
		
		while(!touch.isPressed() && !suppressed){
			Motor.A.forward();
			Motor.B.forward();
			
			Motor.A.setSpeed(speedB);
			Motor.B.setSpeed(speedC);
			
			System.out.println(light.readValue());
			
			val = light.readValue();
			
			if(val<27){
				System.out.println("right ");
				//speedB = 100;
				speedB = 500;
				speedC = 0;
			}
			else if(val>=27&&val<32){
				System.out.println("little right");
				//speedB = 400;
				speedB = 300;
				speedC = 0;
			}
			else if(val>=32&&val<37){
				System.out.println("little left ");
				speedB = 0;
				speedC = 300;
			}
			else if(val>=37){
				System.out.println("left ");
				speedB = 0;
				speedC = 500;
			}
			
			/*
			if(light.readValue()>=28&&light.readValue()<32){
				System.out.println("little right");
				//speedB = 400;
				speedB = 300;
				speedC = 0;
			}
			else if(light.readValue()<28){
				System.out.println("right ");
				//speedB = 100;
				speedB = 500;
				speedC = 0;
			}
			else if(light.readValue()>=32&&light.readValue()<37){
				System.out.println("little left ");
				speedB = 0;
				speedC = 300;
			}
			else if(light.readValue()>=37){
				System.out.println("left ");
				speedB = 0;
				speedC = 500;
			}
			*/
			
			Thread.yield();
		}
		
		Motor.A.stop();
		Motor.B.stop();
		Config.COUNTER = 0;
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void suppress() {
		suppressed = true;
	}

	@Override
	public boolean takeControl() {
		return true;
	}

}
