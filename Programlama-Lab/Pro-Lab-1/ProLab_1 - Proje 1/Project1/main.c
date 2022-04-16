#include <stdio.h>
#include <stdlib.h>

static int *adresList;
static int *anahtarList,diziBoyut=0;

void kayitEkle(FILE *dPtr);
int kayitBul(FILE *dataPtr,int silAnahtar);
void kayitSil(FILE *fPtr);
void kayitGuncelle(FILE *fPtr);
void veriDosyasiniGoster(FILE *fPtr);
void indexDosyasiOlustur(FILE *veriPtr);
void indexDosyasiSirala(FILE *indexPtr);
void indexDosyasiniGoster();
void indexDosyasiniSil();
void guncelleVeSil(FILE *dPtr,int girilenOgrNo,int girilenDersKodu,int girilenPuan);
void diziBoyutBul(FILE *fPtr);
int binarySearch(int *arr, int anahtar,int diziBoyut);
int menuSecim();

struct kayit
{
    int ogrNo;
    int dersKodu;
    int puan;
};
static struct kayit *ogrenci;

int main()
{
    FILE *dataPtr;

    int anaMenuSecim;

    if((dataPtr = fopen("dataFile.dat","rb+"))  == NULL)
    {
        printf("Veri Dosyasi olusturuldu.\nProgrami yeniden baslatin..\n\n");
        dataPtr = fopen( "dataFile.dat", "wb" );
    }
    else
    {
        while(( anaMenuSecim = menuSecim() )!=-1)
        {
            switch ( anaMenuSecim )
            {
            case 1:
                kayitEkle(dataPtr);
                break;
            case 2:
                kayitGuncelle(dataPtr);
                break;
            case 3:
                kayitSil(dataPtr);
                break;
            case 4:
                kayitBul(dataPtr,-1);
                break;
            case 5:
                indexDosyasiOlustur(dataPtr);
                printf("\n+ Index dosyasi basariyla olusturuldu!\n");
                break;
            case 6:
                veriDosyasiniGoster(dataPtr);
                break;
            case 7:
                indexDosyasiniGoster();
                break;
            case 8:
                indexDosyasiniSil();
                break;
            default:
                printf("- Gecersiz giris.");
                break;
            }

        }
    }
    fclose(dataPtr);
    return 0;
}

void diziBoyutBul(FILE *fPtr)
{
    int ogrNo,dersKodu,puan,indis=0;

    rewind(fPtr);
    while(!feof(fPtr))
    {
        fscanf(fPtr,"%d %d %d\n",&ogrNo,&dersKodu,&puan);
        indis++;
    }
    diziBoyut = indis;
}


void kayitEkle(FILE *dPtr)
{
    int ekleSonlandir;
    FILE *iPtr;
    struct kayit kullanici ;
    printf("Kac adet? : ");
    scanf("%d",&ekleSonlandir);


    while(ekleSonlandir>0)
    {
yukari:
        printf( "\nOgrenci No - Ders Kodu - Puan\n");
        scanf( "%d %d %d",&kullanici.ogrNo,&kullanici.dersKodu,&kullanici.puan);
        fseek(dPtr,0,SEEK_END);
        if(kullanici.dersKodu>5 || kullanici.dersKodu<1){
            printf("\nGecersiz ders kodu girdiniz! \n Lutfen [1,5] araliginda deger giriniz.");
            goto yukari;
        }

        fprintf(dPtr, "%d %d %d\n",
                kullanici.ogrNo,kullanici.dersKodu,kullanici.puan);
        ekleSonlandir--;

        if((iPtr = fopen("indexFile.txt","r+"))  != NULL)
        {
            indexDosyasiOlustur(dPtr);
        }
    }


}

void guncelleVeSil(FILE *dPtr,int girilenOgrNo,int girilenDersKodu,int girilenPuan)
{

    FILE *tempPtr;
    int ogrNo,puan,dersKodu,adres;
    tempPtr = fopen("temp.dat","wb");

    if(girilenPuan==-1) //Kayit Silme
    {
        rewind(dPtr);
        while(!feof(dPtr))
        {
            fscanf(dPtr,"%d %d %d\n",&ogrNo,&dersKodu,&puan);
            if(girilenDersKodu==dersKodu && girilenOgrNo==ogrNo)
            {
                continue;
            }
            else
            {
                fprintf(tempPtr,"%d %d %d\n",ogrNo,dersKodu,puan);
            }

        }
        fclose(dPtr);
        remove("dataFile.dat");
        fclose(tempPtr);
        rename("temp.dat","dataFile.dat");
        printf("\n+ Kayit silme islemi basariyla tamamlandi!");
        dPtr = fopen("dataFile.dat","rb");
        indexDosyasiOlustur(dPtr);

    }
    else //Kayit Guncelleme
    {
        rewind(dPtr);
        while(!feof(dPtr))
        {
            fscanf(dPtr,"%d %d %d\n",&ogrNo,&dersKodu,&puan);
            if(girilenDersKodu==dersKodu && girilenOgrNo==ogrNo)
            {
                fprintf(tempPtr,"%d %d %d\n",ogrNo,dersKodu,girilenPuan);
            }
            else
            {
                fprintf(tempPtr,"%d %d %d\n",ogrNo,dersKodu,puan);
            }

        }
        fclose(dPtr);
        remove("dataFile.dat");
        fclose(tempPtr);
        rename("temp.dat","dataFile.dat");
        printf("\n+ Kayit guncelleme islemi basariyla tamamlandi!");
        dPtr = fopen("dataFile.dat","rb");
        indexDosyasiOlustur(dPtr);
    }
}


void kayitSil(FILE *fPtr)
{
    int silNo,silDersKodu;
    FILE *indexPtr;
    if ( ( indexPtr = fopen( "indexFile.txt", "r" ) ) != NULL )
    {
yukari:
        printf( "\nSilmek istediginiz ogrenci no giriniz: " );
        scanf( "%d", &silNo );


        int kontrol= kayitBul(fPtr,silNo);
        if(kontrol==-1)
        {
            goto yukari;
        }

out:
        printf("\n+ Silmek istediginiz kaydin ders kodunu girin:");
        scanf("%d",&silDersKodu);

        if(silDersKodu>0 && silDersKodu<6)
        {
            guncelleVeSil(fPtr,silNo,silDersKodu,-1);
        }
        else
        {
            printf("\n- Gecersiz giris yaptiniz!"
                   "\n[1,5] Arasinda tabloda bulunan degerlerden girin.");
            goto out;
        }

    }
    else
    {
        printf("\n- Veri dosyasi indekslenmemis.Indeks dosyasi olusturun!\n");
    }
    fclose(indexPtr);

}


void kayitGuncelle(FILE *fPtr)
{
    int guncelleNo,guncelleDersKodu;
    FILE *indexPtr;
    if ( ( indexPtr = fopen( "indexFile.txt", "r" ) ) != NULL )
    {

yukari:
        printf( "\nGuncellemek istediginiz ogrencinin no giriniz : " );
        scanf( "%d", &guncelleNo);

        int kontrol= kayitBul(fPtr,guncelleNo);
        if(kontrol==-1)
        {
            goto yukari;
        }

out:
        printf("\n+ Guncellemek istediginiz kaydin ders kodunu girin:");
        scanf("%d",&guncelleDersKodu);

        if(guncelleDersKodu>0 && guncelleDersKodu<6)
        {
            int guncellePuan;
            printf("\nYeni puan degeri giriniz: ");
            scanf("%d",&guncellePuan);
            guncelleVeSil(fPtr,guncelleNo,guncelleDersKodu,guncellePuan);
        }
        else
        {
            printf("\n- Gecersiz giris yaptiniz!"
                   "\n[1,5] Arasinda tabloda bulunan degerlerden girin.");
            goto out;
        }

    }

    else
    {
        printf("\n- Veri dosyasi indekslenmemis.Indeks dosyasi olusturun!\n");
    }
    fclose(indexPtr);

}


int kayitBul(FILE *dataPtr,int silAnahtar)
{

    struct kayit *ptr;

    FILE *indexPtr;
    int anahtar,indis=0;
    int adres;

    if ( ( indexPtr = fopen( "indexFile.txt", "r" ) ) != NULL )
    {

        indexDosyasiOlustur(dataPtr);
        rewind(dataPtr);
        int sayac=0;

        int arananOgrNo;
        if(silAnahtar==-1)
        {
            printf("\nAradiginiz ogrencinin numarasini giriniz:");
            scanf("%d",&arananOgrNo);
        }
        else
        {
            arananOgrNo= silAnahtar;
        }

        int arananIndis = binarySearch(anahtarList,arananOgrNo,diziBoyut);

        if(arananIndis == -1)
        {
            printf("\n- Bu ogrenci numarasina ait kayit bulunamadi.");
            fclose(indexPtr);
            return -1;
        }
        else
        {
            int toplamDersSayisi=0;
            int ogrNo,dersKodu,puan;
            int adres;

            while(anahtarList[arananIndis-1]==arananOgrNo)
            {
                arananIndis--;

            }

            printf("\n\tOgrenci No\t\tDers Kodu     \tPuan");
            while(toplamDersSayisi<5)
            {
                ptr = (void*)adresList[arananIndis] ;

                printf( "\n\t%d\t\t%d\t\t%d",
                        ptr->ogrNo,
                        ptr->dersKodu,
                        ptr->puan);

                if(anahtarList[arananIndis+1] != arananOgrNo)
                    break;
                else
                {
                    arananIndis++;
                    toplamDersSayisi++;
                }

            }
        }

    }

    else
    {
        printf("\n- Veri dosyasi indekslenmemis.Indeks dosyasi olusturun!\n");
    }
    fclose(indexPtr);
    return 0;

}


void indexDosyasiOlustur(FILE *veriPtr)
{
    int ogrNo,dersKodu,puan,adres;
    FILE *indexPtr;

    diziBoyutBul(veriPtr);

    indexPtr = fopen( "indexFile.txt", "w" );
    diziBoyutBul(veriPtr);
    anahtarList = (int *)malloc(diziBoyut*sizeof(int));
    adresList = (int *)malloc(diziBoyut*sizeof(int));
    ogrenci= malloc(diziBoyut*sizeof(struct kayit));
    rewind(veriPtr);

    int sayac=0;
    while(!feof(veriPtr))
    {
        fscanf(veriPtr,"%d %d %d\n",&ogrenci[sayac].ogrNo,&ogrenci[sayac].dersKodu,&ogrenci[sayac].puan);
        anahtarList[sayac]=ogrenci[sayac].ogrNo;
        adresList[sayac]=(int)&ogrenci[sayac].ogrNo;
        sayac++;
    }

    fclose(indexPtr);
    indexDosyasiSirala(indexPtr);

}


void indexDosyasiSirala(FILE *indexPtr)
{
    int temp;
    int anahtar,adres;
    int *siraliOgrNoArray;

    siraliOgrNoArray = (int *)malloc(diziBoyut*sizeof(int));
    for(int i=0; i<diziBoyut; i++)
    {
        siraliOgrNoArray[i] = anahtarList[i];
    }


    for(int i=0; i<diziBoyut; i++)
    {
        temp=siraliOgrNoArray[i];
        if(siraliOgrNoArray[i]<siraliOgrNoArray[i-1])
        {
            for(int j=i-1; j>=0; j--)
            {
                if(temp<siraliOgrNoArray[j])
                {

                    siraliOgrNoArray[j+1]=siraliOgrNoArray[j];
                    siraliOgrNoArray[j]=temp;
                }
            }
        }
    }


    indexPtr = fopen("indexFile.txt","w+");
    for(int i=0; i<diziBoyut; i++)
    {
        for(int j=0; j<diziBoyut; j++)
        {
            if(siraliOgrNoArray[i] == anahtarList[j])
            {
                fprintf(indexPtr,"%d %d\n",siraliOgrNoArray[i],adresList[j]);
                anahtarList[j] = -1;
            }
        }
    }
    rewind(indexPtr);
    int sayac=0;

    while(!feof(indexPtr) && sayac<=diziBoyut-1)
    {
        fscanf(indexPtr,"%d %d\n",&anahtarList[sayac],&adresList[sayac]);
        sayac++;
    }
    fclose(indexPtr);
}


void indexDosyasiniGoster()
{
    FILE *iPtr;
    if ( ( iPtr = fopen( "indexFile.txt", "r" ) ) != NULL )
    {
        printf("\n\tAnahtar\t\tOffset\n");
        rewind( iPtr );
        while ( !feof( iPtr ) )
        {
            int ogrNo,adres;
            fscanf(iPtr,"%d %d\n",&ogrNo,&adres);
            printf( "\t%d\t\t%d\n",ogrNo,adres);
        }

    }
    else
    {
        printf("\n\n- Index dosyasi daha once olusturulmamis!\n");
    }
    fclose(iPtr);

}


void veriDosyasiniGoster(FILE *fPtr)
{

    printf("\n\tOgrenci No\tDers Kodu     \tPuan\n");
    rewind( fPtr );
    while ( !feof( fPtr ) )
    {
        int ogrNo,dersKodu,puan;
        fscanf(fPtr,"\n%d %d %d",&ogrNo,
               &dersKodu,&puan);
        if (!feof(fPtr))
        {
            printf( "\t%d\t\t%d\t\t%d\n",
                    ogrNo,
                    dersKodu,
                    puan);
        }

    }

}


void indexDosyasiniSil()
{
    FILE *indexPtr;

    if ( ( indexPtr = fopen( "indexFile.txt", "w" ) ) != NULL )
    {
        fclose(indexPtr);
        int kontrol = remove("indexFile.txt");
        if (kontrol==0)
            printf("\n\n+ Index dosyasi diskten basariyla silindi.\n");
    }
    else
        printf("\n\n- Index dosyasi daha once olusturulmamis!\n");
}


int binarySearch(int *arr, int anahtar,int diziBoyut)
{
    int sol = 0, sag = diziBoyut - 1;
    while (sol <= sag)
    {
        int orta = sol + (sag - sol) / 2;

        if (arr[orta] == anahtar)
            return orta;

        if (arr[orta] < anahtar)
            sol = orta + 1;

        else
            sag = orta - 1;
    }

    return -1;
}


int menuSecim()
{
    int secim;
    printf( "\nAna Menu\n"
            "'1' - Kayit Ekle\n"
            "'2' - Kayit Guncelleme\n"
            "'3' - Kayit Sil\n"
            "'4' - Kayit Bul\n"
            "'5' - Index Dosyasi olustur\n"
            "'6' - Veri Dosyasini Yazdir\n"
            "'7' - Index Dosyasini Yazdir\n"
            "'8' - Index Dosyasini Sil\n "
            "(Programi sonlandirmak icin '-1')\n " );
    scanf( "%d", &secim );
    return secim;
}
