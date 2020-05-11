package lieb.revoice.festival;

import java.io.InputStream;

import com.fazecast.jSerialComm.*;

public class AccelerometerJump {

	private char accelerometerStatus;
	private static SerialPort comPort;
	private static InputStream in;

	public AccelerometerJump() {
		comPort = SerialPort.getCommPort("COM3");
		comPort.openPort();
		comPort.setComPortTimeouts(SerialPort.TIMEOUT_READ_SEMI_BLOCKING, 0, 0);
		
		accelerometerStatus = '0';
	}

	public char getAccelerometerStatus() {
		try {			
			in = comPort.getInputStream();
			accelerometerStatus = (char) in.read();
			System.out.println(accelerometerStatus);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return accelerometerStatus;
	}
}
