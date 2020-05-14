package lieb.revoice.festival;

import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.io.InputStream;

import com.fazecast.jSerialComm.*;

import de.gurkenlabs.litiengine.IUpdateable;

public class AccelerometerJump implements IUpdateable{

	private static AccelerometerJump instance;
	private static SerialPort comPort;
	private InputStream in;
	private char previousAcStatus;
	private char currentAcStatus;	
	private static Robot robo;
	
	public AccelerometerJump() {
		SerialPort.getCommPorts();
		comPort = SerialPort.getCommPort("COM3");
		comPort.openPort();
		comPort.setComPortTimeouts(SerialPort.TIMEOUT_READ_SEMI_BLOCKING, 0, 0);
		in = comPort.getInputStream();
		
		previousAcStatus = '0';
		currentAcStatus = '0';
		try {
			robo = new Robot();
		}
		catch (Exception exp) 
	    {
			exp.printStackTrace();
	    }	
	}

	public static AccelerometerJump instance() {
		if (instance == null) {
			instance = new AccelerometerJump();
		}

		return instance;
	}
	
	public static void main(String[] args) {
		SerialPort.getCommPorts();
		SerialPort comPort = SerialPort.getCommPort("COM3");
		comPort.openPort();
		comPort.setComPortTimeouts(SerialPort.TIMEOUT_READ_SEMI_BLOCKING, 0, 0);
		InputStream in = comPort.getInputStream();
		try {
			for (int j = 0; j < 1000; ++j) {
				System.out.print((char) in.read());
				System.out.println();
			}
			in.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		comPort.closePort();
	}
	
	@Override
	public void update() {
		
		previousAcStatus = currentAcStatus;
		
		try {
			currentAcStatus = (char) in.read();
			System.out.println(currentAcStatus);
		} catch (Exception e) {
			e.printStackTrace();
		}	
		
		if(previousAcStatus == '0' && currentAcStatus == '1') {
			//robo.keyPress(KeyEvent.VK_SPACE);
        	//robo.keyRelease(KeyEvent.VK_SPACE);
			System.out.println("JUMPED.");
		}
		
	}
}
