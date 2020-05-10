package lieb.revoice.festival;

import java.io.InputStream;
import java.io.OutputStream;

import com.fazecast.jSerialComm.*;

public class AccelerometerJump {

	public AccelerometerJump() {

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
}
