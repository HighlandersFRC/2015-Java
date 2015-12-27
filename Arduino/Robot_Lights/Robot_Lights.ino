#include "FastLED.h"

// How many leds in your strip?
#define NUM_LEDS 80
#define COLOR_ORDER GRB
#define CHIPSET WS2812B

// For led chips like Neopixels, which have a data line, ground, and power, you just
// need to define DATA_PIN.  For led chipsets that are SPI based (four wires - data, clock,
// ground, and power), like the LPD8806, define both DATA_PIN and CLOCK_PIN



#define DATA_PIN 4
#define CLOCK_PIN 13

// Inputs for light patterns | DIO

int lightPin1 = 10;
int lightPin2 = 11;
int lightPin3 = 12;

bool light1;
bool light2;
bool light3;



//digitalRead(lightPin1);



#define BRIGHTNESS 200
#define FRAMES_PER_SECOND 60

// Define the array of leds
CRGB leds[NUM_LEDS];
int end_led = 80;


int last = 0;
int first = 20;

int last2 = 40;
int first2 = 60;

void setup() {

  pinMode(lightPin1, INPUT);
  pinMode(lightPin2, INPUT);
  pinMode(lightPin3, INPUT);


  Serial.begin(9600);
  // FastLED.addLeds<NEOPIXEL, DATA_PIN>(leds, NUM_LEDS);
  //FastLED.addLeds<APA102, DATA_PIN, CLOCK_PIN>(leds, NUM_LEDS);
  FastLED.addLeds<WS2801, DATA_PIN, CLOCK_PIN>(leds, NUM_LEDS);
  //FastLED.addLeds<WS2812B, LED_PIN, COLOR_ORDER > (leds, NUM_LEDS).setCorrection( TypicalLEDStrip );

  //Make everything Red
  for (byte i = 0; i < NUM_LEDS; i++) {
    leds[i].red = 255;
  }
  FastLED.show();

}


void loop() {

  light1 = digitalRead(lightPin1);
  light2 = digitalRead(lightPin2);
  light3 = digitalRead(lightPin3);


  if (!light1 && !light2 && !light3) {
    loopAround(255, 0, 0); // red
  } else if (light1 && !light2 && !light3) {
    loopAround(0, 255, 0); // green
  } else if (!light1 && light2 && !light3) {
    loopAround(0, 0, 255); // blue
  } else if (light1 && light2 && !light3) {
    // Do something
  } else if (!light1 && !light2 && light3) {
    // Do something
  } else if (light1 && !light2 && light3) {
    // Do something
  } else if (light1 && light2 && !light3) {
    // Do something
  } else {
    // Do something
  }

  // Color loop
  //loopAround(255, 0, 0); // red


  //rainbow();
  //writeColor(0,0,255);
}



void rainbow() {
  Serial.write("Red");
  writeColor(255, 0, 0);
  delay(0);

  Serial.write("Orange");
  fadeColor(255, 128, 0);
  writeColor(255, 128, 0);
  delay(0);

  Serial.write("Yellow");
  fadeColor(255, 255, 0);
  writeColor(255, 255, 0);
  delay(0);

  Serial.write("Green");
  fadeColor(0, 255, 0);
  writeColor(0, 255, 0);
  delay(0);

  Serial.write("Blue");
  fadeColor(0, 0, 255);
  writeColor(0, 0, 255);
  delay(0);

  Serial.write("Violet");
  fadeColor(255, 0, 255);
  writeColor(255, 0, 255);
  delay(0);

  Serial.write("Red");
  fadeColor(255, 0, 0);

}

void loopAround(int r, int g, int b) {

  for (int i = last; i < first; i++) {
    leds[i % NUM_LEDS].red = 0;
    leds[i % NUM_LEDS].green = 0;
    leds[i % NUM_LEDS].blue = 0;
  }

  for (int i = last2; i < first2; i++) {
    leds[i % NUM_LEDS].red = 0;
    leds[i % NUM_LEDS].green = 0;
    leds[i % NUM_LEDS].blue = 0;
  }

  leds[last % NUM_LEDS].red = r; // 255 for red
  leds[last2 % NUM_LEDS].red = r; // 255 for red
  leds[last % NUM_LEDS].green = g;
  leds[last2 % NUM_LEDS].green = g;
  leds[last % NUM_LEDS].blue = b;
  leds[last2 % NUM_LEDS].blue = b;
  last++;
  last2++;

  first++;
  first2++;

  if (last % 10000 == 0) {
    last = 0;
    first = 20;
    last2 = 40;
    first2 = 60;
  }

  delay(4);
  FastLED.show();

}

void redChase() {

  for (byte i = 0; i < NUM_LEDS; i++) {
    leds[i].red = 255;
  }
  FastLED.show();

}
void fadeColor(byte red, byte green, byte blue) {
  int fadeTicks = 15;
  int redChange = -(leds[5].red - red) / fadeTicks;
  int blueChange = -(leds[5].blue - blue) / fadeTicks;
  int greenChange = -(leds[5].green - green) / fadeTicks;
  for (int i = 0; i < fadeTicks; i++) {
    writeColor(leds[i].red + redChange, leds[i].green + greenChange,
               leds[i].blue + blueChange);
    delay(0);
    FastLED.show();
  }
}
void writeColor(int red, int green, int blue) {
  for (int i = 0; i < NUM_LEDS; i++) {
    leds[i].red = red;
    leds[i].green = green;
    leds[i].blue = blue;
  }
  FastLED.show();

}

void writeColor(byte red, byte green, byte blue, byte index) {
  leds[index].red = red;
  leds[index].green = green;
  leds[index].blue = blue;

  FastLED.show();
}





void clearChain() {

  for (int i = 0; i < NUM_LEDS; i++) {
    leds[i].red = 0;
    leds[i].green = 0;
    leds[i].blue = 0;

  }
  FastLED.show();
}

void chase() {

  // First slide the led in one direction
  for (int i = 0; i < NUM_LEDS; i++) {
    // Set the i'th led to
    leds[i] = CRGB::Blue;
    leds[NUM_LEDS - i] = CRGB::Blue;
    // Show the leds
    FastLED.show();
    // now that we've shown the leds, reset the i'th led to black
    leds[i] = CRGB::Black;
    leds[NUM_LEDS - i] = CRGB::Black;
    delay(50);

    // Wait a little bit before we loop around and do it again
  }
  for (int i = 0; i < NUM_LEDS; i++) {
    // Set the i'th led to
    leds[NUM_LEDS - i] = CRGB::Blue;
    leds[i] = CRGB::Blue;
    // Show the leds
    FastLED.show();
    // now that we've shown the leds, reset the i'th led to black
    leds[i] = CRGB::Black;
    leds[NUM_LEDS - i] = CRGB::Black;
    delay(50);


    // Wait a little bit before we loop around and do it again
  }
}
