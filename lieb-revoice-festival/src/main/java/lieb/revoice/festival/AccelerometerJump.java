package lieb.revoice.festival;

import com.fazecast.jSerialComm.*;

import de.gurkenlabs.litiengine.IUpdateable;

public class AccelerometerJump implements IUpdateable {

	private static AccelerometerJump instance;
	private static SerialPort comPort;
	private char currentAcStatus;

	public AccelerometerJump() {
		SerialPort.getCommPorts();
		comPort = SerialPort.getCommPort("COM3");
		comPort.openPort();
		currentAcStatus = '0';		
	}

	public static AccelerometerJump instance() {
		if (instance == null) {
			instance = new AccelerometerJump();
		}

		return instance;
	}

	@Override
	public void update() {

		try {
			if (comPort.bytesAvailable() == 0) {
				
			} else {
				byte[] readBuffer = new byte[comPort.bytesAvailable()];
				int numRead = comPort.readBytes(readBuffer, readBuffer.length);
				currentAcStatus = (char) readBuffer[numRead -1];
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		if (currentAcStatus == '1') {			
			System.out.print("JUMPED.");
			currentAcStatus = '0';
		}

	}
}
