#include <stdio.h>
#include <string.h>
#include <ctype.h>
#include <stdlib.h>
#include <time.h>

void sunum(int arr[], int size)
{
    printf("\n");
    for (int i = 0; i < size; i++)
    {
        printf("%d ", arr[i]);
    }

    for (int i = 0; i < size; i++)
    {
        for (int j = 0; j < size; j++)
        {
            printf("%d ", arr[i] + arr[j]);
        }
    }

}


void yazbigO(char a)
{
    if(a=='1')
        printf("O(1)");
    else if(a=='2')
        printf("O(logN)");
    else if(a=='3')
        printf("O(N)");
    else if(a=='4')
        printf("O(NlogN)");
    else if(a=='5')
        printf("O(N^a)");
    else if(a=='6')
        printf("O(a^N)");
    else if(a=='7')
        printf("O(N!)");
}


char fonkFor(char pt[])
{
    int i=0;
    char *ptr=pt;
    if(strstr(ptr,">")!=NULL)
    {
        ptr=strstr(ptr,">");
        if(isdigit(ptr[1])||isdigit(ptr[2])||isdigit(ptr[3])||isdigit(ptr[4]))
        {
            return '1';///o(1)
        }
        else
        {
            while(1)
            {
                if(ptr[i]=='*'||ptr[i]=='%')
                {
                    return '2';///o(logn)
                }
                if(ptr[i]=='+'||ptr[i]=='-')
                    return '3';///o(n)
                i++;
            }
        }
    }
    else if(strstr(ptr,"<")!=NULL)
    {
        ptr=strstr(ptr,"<");
        if(isdigit(ptr[1])||isdigit(ptr[2])||isdigit(ptr[3])||isdigit(ptr[4]))
        {
            return '1';///o(1)
        }
        else
        {
            while(1)
            {
                if(ptr[i]=='*'||ptr[i]=='%')
                {
                    return '2';///o(logn)
                }
                if(ptr[i]=='+'||ptr[i]=='-')
                    return '3';///o(n)
                i++;
            }
        }
    }
}


char fonkWhile(char pt[])
{
    int i=0;
    char *ptr=pt;
    if(strstr(ptr,">")!=NULL)
    {
        ptr=strstr(ptr,">");
        if(isdigit(ptr[1])||isdigit(ptr[2])||isdigit(ptr[3])||isdigit(ptr[4]))
        {
            return '1';///o(1)
        }
        else
        {
            return 'd';///big o'nun sabit olmadýðý anlamýna gelir.'d':sorgulamaya devam anlamýna gelir.
        }
    }
    else if(strstr(ptr,"<")!=NULL)
    {
        ptr=strstr(ptr,"<");
        if(isdigit(ptr[1])||isdigit(ptr[2])||isdigit(ptr[3])||isdigit(ptr[4]))
        {
            return '1';///o(1)
        }
        else
        {
            return 'd';///big o'nun sabit olmadýðý anlamýna gelir.'d':sorgulamaya devam anlamýna gelir.
        }
    }
}


void bigOHesapla()
{

    char bigO[20],kume[40];
    int sayac1=0,sayac2=0;

    FILE *dosya=fopen("sunumKod.txt","r+");

    if(dosya==NULL)
        printf("- sunumKod.txt dosyasi olusturulmamis!");
    char ptr[60];
    while(fgets(ptr,60,dosya))
    {
        if(strstr(ptr,"for")!=NULL) ///for için
        {
            kume[sayac2++]='{';
            bigO[sayac1++]=fonkFor(ptr);
        }
        else if(strstr(ptr,"while")!=NULL) ///while için
        {
            kume[sayac2++]='{';
            if(fonkWhile(ptr)=='d')
            {
                while(fgets(ptr,60,dosya))
                {
                    if(strstr(ptr,"}")==NULL)
                    {
                        if(strstr(ptr,"++")!=NULL||strstr(ptr,"--")!=NULL)
                        {
                            bigO[sayac1++]='3';
                            break;
                        }
                    }
                    else
                    {
                        bigO[sayac1++]='2';
                        break;
                    }
                }
            }
            else
            {
                bigO[sayac1++]='1';
            }
        }
        else if(strstr(ptr,"do")!=NULL) ///do için
        {
            kume[sayac2++]='{';
            int temp=0;
            char a;
            while(1)
            {

                if(strstr(ptr,"++")!=NULL||strstr(ptr,"--")!=NULL)
                {
                    temp=1;
                }
                else if(strstr(ptr,"while")!=NULL)
                {
                    kume[sayac2++]='}';
                    a=fonkWhile(ptr);
                    if(a=='1')
                    {
                        bigO[sayac1++]='1';
                    }
                    else if(a=='d'&&temp==0)
                    {
                        bigO[sayac1++]='2';
                    }
                    else if(a=='d'&&temp==1)
                    {
                        bigO[sayac1++]='3';
                    }
                    break;
                }
                fgets(ptr,60,dosya);
            }
        }
        else if(strstr(ptr,"}")!=NULL) ///küme için
        {
            kume[sayac2++]='}';
        }
        else if((strstr(ptr,"int")!=NULL||strstr(ptr,"char")!=NULL||strstr(ptr,"float")!=NULL||strstr(ptr,"double")!=NULL)&&(strstr(ptr,"(")!=NULL&&strstr(ptr,"main")==NULL&&strstr(ptr,"printf")==NULL))
        {
            char *ptr2;
            while(1)
            {
                if(strstr(ptr,"return")!=NULL&&strstr(ptr,"(")!=NULL&&strstr(ptr,")")!=NULL)
                {
                    ptr2=strstr(ptr,"(");
                    if(isalpha(ptr2[1]))
                    {
                        printf("zaman karmasikligi:O(n)'dir.");
                        break;
                    }
                    else
                    {
                        printf("zaman karmasikligi:O(1)'dir.");
                        break;
                    }
                }
                fgets(ptr,60,dosya);
            }
        }

    }
    fclose(dosya);
    kume[39]='.';
    char zamanKarmasikligi[50];
    yazbigO(bigO[0]);
    int k,i;
    for(i=1; kume[i]=='{'||kume[i]=='}'; i++)
    {
        if(kume[i]=='{')
        {
            printf(" x ");
            yazbigO(bigO[i]);
        }
        else if(kume[i]=='}')
        {
            if(kume[i+1]=='{')
            {
                printf(" + ");
                continue;
            }
            else
                continue;
        }
    }

}


int veriTipiBoyut(char *str)
{
    int index;
    char veriTipi[255];
    strcpy(veriTipi,str);

    char veriTipiListesi[7][10] =
    {
        "void","char","int","float","double","long","short"
    };
    int boyutlar[7] = {0,2,4,4,8,4,2};

    for(int i = 0; i<7; i++)
    {
        int kontrol = strcmp(veriTipiListesi[i],veriTipi);
        if(kontrol==0)
        {
            index = i;
            return boyutlar[index];
        }
    }
    return -1;
}

char * hesapla(char satir[255],char veriTipi[20])
{
    char boyut2[100]="";
    int toplam=0;

    if(strstr(satir,"[")!=NULL && strstr(satir,"]") != NULL)
    {
        // Eger degisken bir dizi belirtiyor ise
        int sayacSol = 0;
        int sayacSag = 0;
        int dBoyut = 0;

        for(int i=0; i<255; i++)
        {
            if(satir[i]=='[')
                sayacSol++;
            else if (satir[i]==']')
                sayacSag++;
            else if (satir[i]=='\0')
                break;
        }
        dBoyut = sayacSag;
        char ayraclar[2] = "[]";
        char *diziBoyut = strtok(satir," ");
        int veriBoyut = veriTipiBoyut(satir);
        char tip[20];
        if(veriBoyut!=-1)
            strcpy(tip,satir);

        diziBoyut = strtok(NULL,"[");
        diziBoyut = strtok(NULL,"]");
        int icerikToplami = 1;
        int sayac =0;
        int usSayaci=0;
        while(diziBoyut!=NULL)
        {
            int icerik;

            if(dBoyut==1)
            {
                char diziIcerik[20];
                sscanf(satir,"%*[^[][%[^]]",diziIcerik);
                icerik = atoi(diziIcerik);
            }
            else
            {
                icerik = atoi(diziBoyut);
                if (icerik>0)
                    icerikToplami = icerikToplami*icerik;
                else if (icerik==0)
                    usSayaci++;


            }
            sayac++;

            if(icerik>0 && (strstr(satir,"int")!=NULL || strstr(veriTipi,"int")!=NULL) && dBoyut == 1  )
                toplam = toplam + icerik*4; // int ise
            else if (icerik<=0 && (strstr(satir,"int")!=NULL || strstr(veriTipi,"int")!=NULL) && dBoyut == 1 && sayac<2)
                sprintf(boyut2,"%s+%s",boyut2,"4*(I)^1");
            else if(icerik>0 && (strstr(tip,"int")!=NULL || strstr(veriTipi,"int")!=NULL) && dBoyut > 1 && sayac == dBoyut  )
                toplam = toplam + icerikToplami*4; // multi dimensional ise
            else if (icerik<=0 && (strstr(tip,"int")!=NULL || strstr(veriTipi,"int")!=NULL) &&sayac == dBoyut && dBoyut!=1)
                sprintf(boyut2,"%s+%s%d",boyut2,"4*(I)^",usSayaci);


            else if(icerik>0 && (strstr(satir,"byte")!=NULL || strstr(veriTipi,"byte")!=NULL) && dBoyut == 1)
                toplam = toplam + icerik*1; // byte ise
            else if (icerik<=0 && (strstr(satir,"byte")!=NULL || strstr(veriTipi,"byte")!=NULL)&& dBoyut == 1&& sayac<2)
                sprintf(boyut2,"%s+%s",boyut2,"1*(B)^1");
            else if(icerik>0 && (strstr(tip,"byte")!=NULL || strstr(veriTipi,"byte")!=NULL) && dBoyut > 1 && sayac == dBoyut  )
                toplam = toplam + icerikToplami*1; // multi dimensional ise
            else if (icerik<=0 && (strstr(tip,"byte")!=NULL || strstr(veriTipi,"byte")!=NULL) &&sayac == dBoyut  )
                sprintf(boyut2,"%s+%s%d",boyut2,"1*(B)^",usSayaci);


            else if(icerik>0 && (strstr(satir,"char")!=NULL || strstr(veriTipi,"char")!=NULL) && dBoyut == 1)
                toplam = toplam + icerik*2; // char ise
            else if (icerik<=0 && (strstr(satir,"char")!=NULL || strstr(veriTipi,"char")!=NULL)&& dBoyut == 1&& sayac<2)
                sprintf(boyut2,"%s+%s",boyut2,"2*(C)^1");
            else if(icerik>0 && (strstr(tip,"char")!=NULL || strstr(veriTipi,"char")!=NULL) && dBoyut > 1 && sayac == dBoyut  )
                toplam = toplam + icerikToplami*2; // multi dimensional ise
            else if (icerik<=0 && (strstr(tip,"char")!=NULL || strstr(veriTipi,"char")!=NULL) &&sayac == dBoyut  )
                sprintf(boyut2,"%s+%s%d",boyut2,"2*(C)^",usSayaci);

            else if(icerik>0 && (strstr(satir,"short")!=NULL || strstr(veriTipi,"short")!=NULL)&& dBoyut == 1)
                toplam = toplam + icerik*2; // short ise
            else if (icerik<=0 && (strstr(satir,"short")!=NULL || strstr(veriTipi,"short")!=NULL)&& dBoyut == 1&& sayac<2)
                sprintf(boyut2,"%s+%s",boyut2,"2*(S)^1");
            else if(icerik>0 && (strstr(tip,"short")!=NULL || strstr(veriTipi,"short")!=NULL) && dBoyut > 1 && sayac == dBoyut  )
                toplam = toplam + icerikToplami*2; // multi dimensional ise
            else if (icerik<=0 && (strstr(tip,"short")!=NULL || strstr(veriTipi,"short")!=NULL) &&sayac == dBoyut  )
                sprintf(boyut2,"%s+%s%d",boyut2,"2*(S)^",usSayaci);

            else if(icerik>0 && (strstr(satir,"long")!=NULL || strstr(veriTipi,"long")!=NULL) && dBoyut == 1)
                toplam = toplam + icerik*4; // long ise
            else if (icerik<=0 && (strstr(satir,"long")!=NULL || strstr(veriTipi,"long")!=NULL)&& dBoyut == 1&& sayac<2)
                sprintf(boyut2,"%s+%s",boyut2,"4*(L)^1");
            else if(icerik>0 && (strstr(tip,"long")!=NULL || strstr(veriTipi,"long")!=NULL) && dBoyut > 1 && sayac == dBoyut  )
                toplam = toplam + icerikToplami*4; // multi dimensional ise
            else if (icerik<=0 && (strstr(tip,"long")!=NULL || strstr(veriTipi,"long")!=NULL) &&sayac == dBoyut  )
                sprintf(boyut2,"%s+%s%d",boyut2,"4*(L)^",usSayaci);

            else if(icerik>0 && (strstr(satir,"float")!=NULL || strstr(veriTipi,"float")!=NULL) && dBoyut == 1)
                toplam = toplam + icerik*4; // float ise
            else if (icerik<=0 && (strstr(satir,"float")!=NULL || strstr(veriTipi,"float")!=NULL)&& dBoyut == 1&& sayac<2)
                sprintf(boyut2,"%s+%s",boyut2,"*(F)^1");
            else if(icerik>0 && (strstr(tip,"float")!=NULL || strstr(veriTipi,"float")!=NULL) && dBoyut > 1 && sayac == dBoyut  )
                toplam = toplam + icerikToplami*4; // multi dimensional ise
            else if (icerik<=0 && (strstr(tip,"float")!=NULL || strstr(veriTipi,"float")!=NULL) &&sayac == dBoyut  )
                sprintf(boyut2,"%s+%s%d",boyut2,"4*(F)^",usSayaci);

            else if(icerik>0 && (strstr(satir,"double")!=NULL || strstr(veriTipi,"double")!=NULL) && dBoyut == 1)
                toplam = toplam + icerik*8; // double ise
            else if (icerik<=0 && (strstr(satir,"double")!=NULL || strstr(veriTipi,"double")!=NULL)&& dBoyut == 1&& sayac<2)
                sprintf(boyut2,"%s+%s",boyut2,"8*(D)^1");
            else if(icerik>0 && (strstr(tip,"double")!=NULL || strstr(veriTipi,"double")!=NULL) && dBoyut > 1 && sayac == dBoyut  )
                toplam = toplam + icerikToplami*8; // multi dimensional ise
            else if (icerik<=0 && (strstr(tip,"double")!=NULL || strstr(veriTipi,"double")!=NULL) &&sayac == dBoyut  )
                sprintf(boyut2,"%s+%s%d",boyut2,"8*(D)^",usSayaci);

            diziBoyut = strtok(NULL,ayraclar);

        }
    }
    else
    {
        if( strstr(satir,"int")!=NULL || strstr(veriTipi,"int")!=NULL )
            toplam = toplam + 4;
        else if(strstr(satir,"byte")!=NULL || strstr(veriTipi,"byte")!=NULL)
            toplam = toplam + 1;
        else if(strstr(satir,"char")!=NULL || strstr(veriTipi,"char")!=NULL)
            toplam = toplam + 2;
        else if(strstr(satir,"short")!=NULL || strstr(veriTipi,"short")!=NULL)
            toplam = toplam + 2;
        else if(strstr(satir,"long")!=NULL || strstr(veriTipi,"long")!=NULL)
            toplam = toplam + 4;
        else if(strstr(satir,"float")!=NULL || strstr(veriTipi,"float")!=NULL)
            toplam = toplam + 4;
        else if(strstr(satir,"double")!=NULL || strstr(veriTipi,"double")!=NULL)
            toplam = toplam + 8;
    }

    sprintf(boyut2, "%s+%d",boyut2,toplam);

    char * ptr = boyut2;
    return ptr;
}

char* fonkisyonMu(char satir[255])
{
    char *kontrol = strstr(satir,"(");
    char *kontrol2 = strstr(satir,")");
    char *kontrol3 = strtok(satir," ");
    char *kontrol4 = strstr(satir,";");
    char *kontrol5 = strstr(satir,"\"");
    char *kontrol6 = strstr(satir,"%");
    int tipIndex = veriTipiBoyut(kontrol3);

    if (kontrol != NULL && kontrol2 != NULL &&
            tipIndex != -1 && kontrol4 == NULL &&
            kontrol5 == NULL && kontrol6 == NULL)
        return kontrol3;
    else
    {
        return NULL;
    }
}


char* fonksHesap(char str[255],char * fonkTipi)
{
    char boyut[50]="";
    int toplam =0;
    char *boyutPtr = boyut;
    toplam = toplam + veriTipiBoyut(fonkTipi); // Return type'a gore ekleme yapar
    char *ptr = strtok(str,"(");
    ptr = strtok(NULL,")");
    while(ptr != NULL)
    {
        char strParca[100];
        strcpy(strParca,ptr);
        char *kontrol = strstr(strParca,",");

        if(kontrol != NULL)
        {
            kontrol = strtok(strParca,",");

            while(kontrol != NULL)
            {

                sprintf(boyut,"%s+%s",boyut,hesapla(kontrol,""));

                kontrol = strtok(NULL, ",");
            }
        }
        else
        {
            // Bir degisken tanimliysa
            //  printf("ptr parca: %s\n",ptr);
            kontrol = strtok(strParca,")");
            sprintf(boyut,"%s+%s",boyut,hesapla(kontrol,""));
            sprintf(boyut, "%s+%d",boyut,toplam);
            return boyutPtr;
        }

        ptr = strtok(NULL, ")");
    }

    sprintf(boyut, "%s+%d",boyut,toplam);
    return boyutPtr;
}

int satirKontrol(char satir[255])
{
    char *kontrol = strstr(satir,"(");
    char *kontrol2 = strstr(satir,")");

    char *kontrol4 = strstr(satir,";");
    char *kontrol5 = strstr(satir,"for");
    char *kontrol6 = strstr(satir,"printf");

    char *kontrol7 = strtok(satir," ");
    int tipBoyut = veriTipiBoyut(kontrol7);

    if(tipBoyut!=-1 && kontrol4!=NULL
            && kontrol6==NULL && kontrol5==NULL)
        return 1; // satirda sadece degisken init ediliyorsa
    else if (kontrol5!=NULL && kontrol!=NULL && kontrol2!=NULL
             && kontrol6==NULL)
        return 2; // for satirinda init ediliyorsa


    return -1;
}

void yerKarmasikligi()
{
    FILE *dPtr;
    char yerKarmasiklikToplam [20][50];
    int yktIndex = 0;

    if((dPtr = fopen("sunumKod.txt","rb+"))  == NULL)
    {
        printf("- sunumKod.txt dosyasi olusturulmamis!");
        return;
    }
    else
    {
        char satir[255];
        rewind(dPtr);

        while(fgets(satir, 255, dPtr))
        {
            char yedekSatir[255];
            char *yedekSatir2;
            yedekSatir2 = strdup(satir);
            strcpy(yedekSatir,satir);
            char * fonksTipi = fonkisyonMu(yedekSatir);
            // Cekilen satir fonksiyon ise tipini doner
            // Degilse NULL doner.
            int kontrolSatir = satirKontrol(yedekSatir2);

            if(fonksTipi!=NULL) // Eger fonks initialize ediliyorsa
            {
                strcpy(yerKarmasiklikToplam[yktIndex],fonksHesap(satir, fonksTipi));
                yktIndex++;
                continue;
            }
            else if (kontrolSatir == 1) // sadece degisken init ediliyorsa
            {

                char boyut2[100]="";
                int veriTipi;
                char *kontrol = strstr(satir,",");
                char *kontrol2 = strstr(satir,"};");

                if(kontrol== NULL || kontrol2!=NULL)
                {
                    // birden fazla init yoksa

                    strcpy(yerKarmasiklikToplam[yktIndex],hesapla(satir,""));
                    yktIndex++;
                    continue;
                }
                else if (kontrol!=NULL)
                {
                    // birden fazla init varsa

                    char *ptr = strtok(satir," ");
                    char tip[20] ="";
                    strcpy(tip,ptr);
                    int sayac = 0;

                    ptr = strtok(NULL,";");
                    char *ptr2 = strtok(ptr,",");

                    while(ptr2!=NULL)
                    {
                        sprintf(boyut2,"%s+%s",boyut2,hesapla(ptr2,tip));

                        ptr2 = strtok(NULL,",");

                    }

                    strcpy(yerKarmasiklikToplam[yktIndex],boyut2);
                    yktIndex++;
                    continue;
                }
            }
            else if (kontrolSatir==2)
            {
                // for satirinda init ediliyorsa
                char boyut2[100]="";
                char *ptr = strtok(satir,"(");
                ptr = strtok(NULL,";");
                sprintf(boyut2,"%s+%s",boyut2,hesapla(ptr,""));

                strcpy(yerKarmasiklikToplam[yktIndex],boyut2);
                yktIndex++;
                continue;

            }
        }

    }

    fclose(dPtr);
    char toplam2[100]="";
    for(int i=0; i<yktIndex; i++)
        sprintf(toplam2,"%s%s",toplam2,yerKarmasiklikToplam[i]);

    int tempSayi=0;
    char tempStr[10];
    char temp2[100];
    int kontrol=-1;
    strcpy(temp2,toplam2);

    char genelToplam[100] ="";
    int sayilarToplami=0;
    char *tempPtr = strtok(temp2,"+");
    while(tempPtr!=NULL)
    {
        tempSayi = atoi(tempPtr);
        sprintf(tempStr,"%d",tempSayi);
        kontrol = strcmp(tempPtr,tempStr);
        if(kontrol==0)
            sayilarToplami = sayilarToplami + tempSayi;
        else
            sprintf(genelToplam,"%s + %s",genelToplam,tempPtr);

        tempPtr = strtok(NULL,"+");

    }
    sprintf(genelToplam,"%s + %d",genelToplam,sayilarToplami);
    char genelToplam2[100] ="";
    strcpy(genelToplam2,genelToplam);
    char *ptrGT = strtok(genelToplam2,"+");
    int gecici=0;
    int derece=0;
    while(ptrGT!=NULL)
    {
        if ( strstr(ptrGT,"^") != NULL )
        {
            gecici = atoi(ptrGT+sizeof(ptrGT)+3);
            if(gecici>=derece)
                derece = gecici;
        }
        ptrGT = strtok(NULL,"+");
    }

    genelToplam[1] = ' ';

    if(derece>0)
        printf("\n+ Yer karmasikligi: O(N^%d) =%s",derece,genelToplam);
    else
        printf("\n+ Yer karmasikligi: O(1) =%s",genelToplam);
}


void calismaSuresi()
{
    double gecenZaman = 0.0;


    int dizi[10] = {1,2,3,4,5,6,7,8,9,10};


    clock_t baslangic = clock();
    // Sistemin calismaya basladiktan itibaren gecen zamani getirir.


    sunum(dizi,10);


    clock_t son = clock();

    gecenZaman = (double) (son-baslangic) / CLOCKS_PER_SEC;
    // Gecen zamanin saniye cinsinden double bi degiskene kaydi
    printf("\n+ Kullanilan kodun calisma suresi %.10f saniye..",gecenZaman);
}


int menuSecim()
{
    int secim;
    printf( "\nAna Menu\n"
            "'1' - Big O - Zaman Karmasikligi Hesabi\n"
            "'2' - Big O - Hafiza Karmasikligi Hesabi\n"
            "'3' - Calisma suresi\n"
            "(Programi sonlandirmak icin '-1')\n " );
    scanf( "%d", &secim );
    return secim;
}


int main()
{
    int anaMenuSecim;
    while(( anaMenuSecim = menuSecim() )!=-1)
    {
        switch ( anaMenuSecim )
        {
        case 1:
            bigOHesapla();
            break;
        case 2:
            yerKarmasikligi();
            break;
        case 3:
            calismaSuresi();
            break;
        default:
            printf("- Gecersiz giris.");
            break;
        }
    }

    return 0;
}
