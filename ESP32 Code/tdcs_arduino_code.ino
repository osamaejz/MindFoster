//This example code is in the Public Domain (or CC0 licensed, at your option.)
//By Evandro Copercini - 2018
//
//This example creates a bridge between Serial and Classical Bluetooth (SPP)
//and also demonstrate that SerialBT have the same functionalities of a normal Serial

#include "BluetoothSerial.h"

#if !defined(CONFIG_BT_ENABLED) || !defined(CONFIG_BLUEDROID_ENABLED)
#error Bluetooth is not enabled! Please run `make menuconfig` to and enable it
#endif

#define stim_05  32
#define stim_10  33
#define stim_15  25
#define stim_20  26  
#define status_led  2



BluetoothSerial SerialBT;
int incoming_Value = 0;
void setup() {
  Serial.begin(9600);
  SerialBT.begin("ESP32test"); //Bluetooth device name
  Serial.println("The device started, now you can pair it with bluetooth!");

  pinMode(stim_05,OUTPUT);
  pinMode(stim_10,OUTPUT);
  pinMode(stim_15,OUTPUT);
  pinMode(stim_20,OUTPUT);
  pinMode(status_led,OUTPUT);

  digitalWrite(status_led,LOW);
  digitalWrite(stim_05,LOW);
  digitalWrite(stim_10,LOW);
  digitalWrite(stim_15,LOW);
  digitalWrite(stim_20,LOW);
}

void loop() {
  
  if (Serial.available()) {
    SerialBT.write(Serial.read());
  }
  
  if (SerialBT.available() ) {
    incoming_Value = SerialBT.read();
    Serial.println(incoming_Value);

    if (incoming_Value == 49 ){
      digitalWrite(stim_05,HIGH);
      digitalWrite(stim_10,LOW);
      digitalWrite(stim_15,LOW);
      digitalWrite(stim_20,LOW);
      Serial.println("0.5mA Stimulation");
    }
    else if (incoming_Value == 50){
      digitalWrite(stim_05,HIGH);
      digitalWrite(stim_10,HIGH);
      digitalWrite(stim_15,LOW);
      digitalWrite(stim_20,LOW);
      Serial.println("1.0mA Stimulation");
    }
   else if (incoming_Value == 51){
      digitalWrite(stim_05,HIGH);
      digitalWrite(stim_10,HIGH);
      digitalWrite(stim_15,HIGH);
      digitalWrite(stim_20,LOW);
      Serial.println("1.5mA Stimulation");  
      }
   else if (incoming_Value == 52){
      digitalWrite(stim_05,HIGH);
      digitalWrite(stim_10,HIGH);
      digitalWrite(stim_15,HIGH);
      digitalWrite(stim_20,HIGH);
      Serial.println("2mA Stimulation");
    }
    else if(incoming_Value == 53){
      digitalWrite(status_led,HIGH);
      Serial.println("LEd on");
    }
    else if (incoming_Value == 54){
      digitalWrite(stim_05,LOW);
      digitalWrite(stim_10,LOW);
      digitalWrite(stim_15,LOW);
      digitalWrite(stim_20,LOW);
      Serial.println("Stimulation ended");
    }
    
  }
 
  
    
}
