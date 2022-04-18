#include <LiquidCrystal.h>
#include <Keypad.h>

#define LM35 A0
#define FlameSensor A4
#define buzzer 22
#define RED 24
#define GREEN 25
#define LAMP 23
#define PIR 7
#define SATIR 4
#define SUTUN 3


char buton[SATIR][SUTUN] =
{
    {'1','2','3'},
    {'4','5','6'},
    {'7','8','9'},
    {'*','0','#'}
};
byte satir_PIN[SATIR] = {14, 15, 16, 17};
byte sutun_PIN[SUTUN] = {18, 19, 20};
Keypad keypad = Keypad (makeKeymap(buton),satir_PIN,sutun_PIN,SATIR,SUTUN);
char pass[4] = {'0','1','2','3'};
char * sifre = pass;
int durum=0;

float Temperature;
float analogDeger;

LiquidCrystal lcd(40, 41, 42, 43, 44, 45);
LiquidCrystal lcd2(38, 39, 42, 43, 44, 45);


void sifreDogru()
{
    digitalWrite(GREEN, HIGH);
    digitalWrite(RED, LOW);
    lcd.clear();
    lcd.setCursor(0,0);
    lcd.print("**DOGRU SIFRE**");
    lcd.setCursor(4,1);
    lcd.println("              ");
    int sayac=0;

    delay(100);
    lcd.clear();
    for(sayac=5; sayac>=0; sayac--)
    {
        lcd.setCursor(0,0);
        lcd.print(sayac);
        delay(125);
    }
    digitalWrite(GREEN,LOW);
    lcd.setCursor(0,0);
    lcd.print("KILIT YENIDEN       ");
    lcd.setCursor(0,1);
    lcd.print("AKTIF EDILDI");
    delay(200);
    lcd.clear();
    anaEkran();
}

void sifreHatali()
{
    lcd.clear();
    lcd.setCursor(0,0);
    lcd.print("**YANLIS SIFRE**");
    digitalWrite(RED,HIGH);
    lcd.setCursor(0,1);
    lcd.print(" Yeniden Dene ");
    delay(200);
    lcd.clear();
    digitalWrite(RED,LOW);
    anaEkran();
}

void anaEkran()
{

    lcd.setCursor(0,0);
    lcd.println("    SIFREYI        ");
    lcd.setCursor(0,1);
    lcd.println("    GIRINIZ        ");
}

void setup()
{
    lcd.begin(16, 2);
    lcd2.begin(16,2);
    pinMode(FlameSensor, INPUT_PULLUP);
    pinMode(PIR,INPUT_PULLUP);
    pinMode(LM35, INPUT);
    pinMode(RED, OUTPUT);
    pinMode(GREEN, OUTPUT);
    pinMode(LAMP, OUTPUT);
    pinMode(buzzer, OUTPUT);

    anaEkran();
    delay(300);
    lcd.clear();
}

void loop()
{
    char key = keypad.getKey();
    int i;

    int Flame_durum = digitalRead(FlameSensor);
    int PIR_durum = digitalRead(PIR);
    
    analogDeger = analogRead(LM35);
    analogDeger = (analogDeger/1023.0)*5000;
    Temperature = analogDeger/10;

    if(durum==0)
        anaEkran();

    if(key!=NO_KEY)
    {
        lcd.clear();
        lcd.setCursor(0,0);
        lcd.print("      SIFRE");
        lcd.setCursor(7,1);
        lcd.print(" ");
        lcd.setCursor(7,1);
        for(i=0; i<=durum; ++i)
        {
            lcd.print("*");
        }

        if(key == sifre[durum])
        {
            durum++;
            if(durum==4)
            {
                sifreDogru();
                durum=0;
            }
        }
        else
        {
            sifreHatali();
            durum = 0;
        }
    }

    if(Temperature>30)
    {
        lcd2.setCursor(0,0);
        lcd2.print("SICAKLIK ARTTI  ");
        lcd2.setCursor(0,1);

        lcd2.print(Temperature);
        lcd2.print(" \'C");
    }
    else if(Temperature<20)
    {
        lcd2.setCursor(0,0);
        lcd2.print("SICAKLIK AZALDI ");
        lcd2.setCursor(0,1);
        lcd2.print(Temperature);
        lcd2.print(" \'C");
    }
    else
    {
        lcd2.setCursor(0,0);
        lcd2.print("SICAKLIK NORMAL ");
        lcd2.setCursor(0,1);
        lcd2.print(Temperature);
        lcd2.print(" \'C");
    }

    if(PIR_durum==HIGH)
    {
        digitalWrite(LAMP, HIGH);
       

    }
    else if(PIR_durum==LOW)
    {
        digitalWrite(LAMP, LOW);
    }

    if(Flame_durum==HIGH)
    {
        digitalWrite(buzzer, HIGH);
      

    }
    else if(Flame_durum==LOW)
    {
        digitalWrite(buzzer, LOW);
       
    }
}
