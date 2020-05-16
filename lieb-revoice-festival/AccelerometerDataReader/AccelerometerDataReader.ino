/*Hardware hookup:
  Arduino --------------- MMA8452Q Breakout
    3.3V  ---------------     3.3V
    GND   ---------------     GND
  SDA (A4) --\/330 Ohm\/--    SDA
  SCL (A5) --\/330 Ohm\/--    SCL

  The MMA8452Q is a 3.3V max sensor, so you'll need to do some
  level-shifting between the Arduino and the breakout. Series
  resistors on the SDA and SCL lines should do the trick.*/

/* See this :https://learn.sparkfun.com/tutorials/mma8452q-accelerometer-breakout-hookup-guide/all */

#include <Wire.h>;
#include "SparkFun_MMA8452Q.h"; // Accelerometer library: http://librarymanager/All#SparkFun_MMA8452Q

MMA8452Q accel;
double previousAcStatus;
double acStatus;
unsigned long previousMillis = 0;
const long interval = 300;

void setup() {
  Serial.begin(9600);
  Wire.begin();

  if (accel.begin() == false) {
    Serial.println("Not Connected. Please check connections and read the hookup guide.");
    while (1);
  }

  previousAcStatus = 0;
  acStatus = 0;
}

void loop() { // Need to substiture the serial.prints for textfile.prints
  if (accel.available()) {      // Wait for new data from accelerometer
    // Acceleration of x, y, and z directions in g units
    /*Serial.print(accel.getCalculatedX(), 3);
      Serial.print("\t");
      Serial.print(accel.getCalculatedY(), 3);
      Serial.print("\t");*/
    previousAcStatus = acStatus;

    unsigned long currentMillis = millis();

    if (currentMillis - previousMillis >= interval) {
      previousMillis = currentMillis;

      acStatus = abs(accel.getCalculatedZ());

      /*Serial.print(previousAcStatus, 2);
        Serial.print("\t");
        Serial.print(acStatus, 2);
        Serial.println();*/

      if (previousAcStatus < 0.5 && acStatus > 0.6) {
        Serial.print('1');
      }
      else {
        Serial.print('0');
      }
    }
  }

}
