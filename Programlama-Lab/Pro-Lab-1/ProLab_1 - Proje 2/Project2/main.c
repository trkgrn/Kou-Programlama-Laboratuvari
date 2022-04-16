#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <ctype.h>

#define kucukHarf_INDEX(c) ((int)c - (int)'a')
#define buyukHarf_INDEX(c) ( 26 + (int)c - (int)'A')

static char **suffixArr;

struct Agac
{
    struct Agac *yaprak[53];
    char harf[1];
    int dallanmaSayisi;
};

struct Agac *dugumOlustur(void)
{
    struct Agac *iter = NULL;

    iter = (struct Agac *)malloc(sizeof(struct Agac));

    if (iter)
    {
        int i;
		iter->dallanmaSayisi=0;
        for (i = 0; i < 53; i++)
            iter->yaprak[i] = NULL;
    }

    return iter;
}

void tekrarEdenEnUzunKatar(struct Agac *root,int suffixDiziBoyut){

	int a;
	char enUzun[30];
	int yaprakSayisi;

for(a=0;a<suffixDiziBoyut;a++){//
	int i;

    int strBoyut = strlen(suffixArr[a]);
    int index;

    char temp[strBoyut];
    temp[0]='\0';
    struct Agac *iter = root;

	for (i = 0; i < strBoyut; i++)
    {

        char ch1 ;
        ch1 = suffixArr[a][i];

        if(ch1 == '$')
        {
            index = 52;
        }
        else if(islower(ch1)!=0)
        {
            index = kucukHarf_INDEX(ch1);
        }
        else
        {
            index = buyukHarf_INDEX(ch1);
        }

        if (iter->yaprak[index]->dallanmaSayisi>1){

			temp[i] = ch1;
			temp[i+1]= '\0';

			int tempBoyut = strlen(temp);
			int enUzunBoyut= strlen(enUzun);

			if(tempBoyut>enUzunBoyut){

				strcpy(enUzun,temp);

				yaprakSayisi = iter->yaprak[index]->dallanmaSayisi;
			}

		}

		else {
			 break;
		}
       	 iter = iter->yaprak[index];

    }



	}//

	printf("\n\nEn Uzun Tekrar Eden Katar: %s",enUzun);
	printf("\nTekrar Sayisi: %d\n",yaprakSayisi);


}

void enCokTekrarEdenKatar(struct Agac *root,int suffixDiziBoyut){
		int a;
	char enCokTekrar[30];
	int yaprakSayisi=0;

for(a=0;a<suffixDiziBoyut;a++){//
	int i;

    int strBoyut = strlen(suffixArr[a]);
    int index;

    char temp[strBoyut];
    temp[0]='\0';
    struct Agac *iter = root;

	for (i = 0; i < strBoyut; i++)
    {
        char ch1 ;
        ch1 = suffixArr[a][i];

        if(ch1 == '$')
        {
            index = 52;
        }
        else if(islower(ch1)!=0)
        {
            index = kucukHarf_INDEX(ch1);
        }
        else
        {
            index = buyukHarf_INDEX(ch1);
        }

        if (iter->yaprak[index]->dallanmaSayisi>1
		&& (iter->yaprak[index]->dallanmaSayisi) >= yaprakSayisi){

			temp[i] = ch1;
			temp[i+1]= '\0';

				strcpy(enCokTekrar,temp);
				yaprakSayisi = iter->yaprak[index]->dallanmaSayisi;


		}

		else {
			 break;
		}
       	 iter = iter->yaprak[index];

    }

	}//

	printf("\n\nEn Cok Tekrar Eden Katar: %s",enCokTekrar);
	printf("\nTekrar Sayisi: %d\n",yaprakSayisi);

}

void agacOlustur(struct Agac *root, const char *str)
{
    int i;
    int strBoyut = strlen(str);
    int index;

    struct Agac *iter = root;

    for (i = 0; i < strBoyut; i++)
    {
        char ch1 ;
        ch1 = str[i];

        if(ch1 == '$')
        {
            index = 52;
        }
        else if(islower(ch1)!=0)
        {
            index = kucukHarf_INDEX(ch1);
        }
        else
        {
            index = buyukHarf_INDEX(ch1);
        }



        if (!iter->yaprak[index])
        {
            iter->yaprak[index] = dugumOlustur();
            iter = iter->yaprak[index];
            iter->harf[0] = str[i];
            iter->dallanmaSayisi++;
        }
		else{
			  iter = iter->yaprak[index];
        iter->harf[0] = str[i];
        iter->dallanmaSayisi++;
		}

    }

}


int katarBul(struct Agac *root){

    int i;
    char str[30];
    printf("\nString giriniz: ");
    scanf("%s",str);
    int strBoyut = strlen(str);
    int index;
    char temp[strBoyut];
    struct Agac *iter = root;

	    for (i = 0; i < strBoyut; i++)
    {

        char ch1 ;
        ch1 = str[i];

        if(ch1 == '$')
        {
            index = 52;
        }
        else if(islower(ch1)!=0)
        {
            index = kucukHarf_INDEX(ch1);
        }
        else
        {
            index = buyukHarf_INDEX(ch1);
        }

        if (!iter->yaprak[index]){
        	printf("- Girilen katar bulunamadi!");
			return 0;
		}
		else {
			temp[i]= ch1;
			temp[i+1]= '\0';
		}

       	 iter = iter->yaprak[index];

    }
	printf("\n+Girilen katar bulundu! \nTekrar Sayisi: %d",iter->dallanmaSayisi);
						return 0;
				}


int agacOlusturulabilirMi(char *str,int karar)
{
    int i,j;

    suffixArr= (char**)malloc(strlen(str)*sizeof(char*));
    char prefixArr[strlen(str)][strlen(str)];

    for(i=0; i<=strlen(str)-1; i++)
    {
        int k=0;
        for(j=i; j<=strlen(str)-1; j++)
        {
            if(i==j)
                suffixArr[i] = (char*)malloc((strlen(str)-i)*sizeof(char));

            suffixArr[i][k] = str[j];
            k++;
            if(j==strlen(str)-1)
            {
                suffixArr[i][k] = '\0';
            }
        }

    }
    printf("\n");
    for(i=0; i<=strlen(str)-1; i++)
    {
        int k=0;
        for(j=0; j<=i; j++)
        {

            prefixArr[i][k] = str[j];
            k++;
            if(j==i)
            {
                prefixArr[i][k] = '\0';
            }
        }

    }

    int kontrol;
if (karar==1){
	    for(i=1; i<strlen(str); i++)
    {
        for(j=0; j<strlen(str)-1; j++)
        {
            kontrol = strcmp(suffixArr[i],prefixArr[j]);
            if(kontrol==0)
            {
                printf("\n\n- Bu string ile agac olusturulamaz."
				"\n Lutfen stringin sonunda '$' ekleyiniz.");
                return -1;
            }
        }
    }

    if(kontrol!=0)
    {
        printf("\n\n+ Bu dizgi ile agac olusturulabilir.");
    }

}

}


int menuSecim()
{
    int secim;
    printf( "\nMenu\n"
            "'1' -  s katari icin sonek agaci olusturulabilir mi?\n"
            "'2' -  Klavyeden girilen p katari s katari icinde geciyor mu?\n Geciyor ise kac kez tekrar ediyor ?\n"
            "'3' -  Sonek agaci olusturulan bir s katari icinde tekrar eden en uzun altkatar nedir,\n kac kez tekrar etmektedir?\n"
            "'4' -  Sonek agaci olusturulan bir s katari icinde en cok tekrar eden altkatar nedir,\n kac kez tekrar etmektedir?\n"
            "(Programi sonlandirmak icin '-1')\n " );
    scanf( "%d", &secim );
    return secim;
}


int main()
{
    char str[1000];
    FILE *dPtr;

    if((dPtr = fopen("strFile.txt","rb+"))  == NULL){
    	printf("- String dosyasi olusturulmamis!");
    	return 0;
	}
	else {
		    rewind(dPtr);
    fscanf(dPtr,"%s",str);
    printf("Okunan String:%s",str);

	}
    fclose(dPtr);

	int anaMenuSecim;
    int boyut ;
    boyut= strlen(str);

    struct Agac *root = dugumOlustur();
	agacOlusturulabilirMi(str,0);
    int i;
    for (i = 0; i < boyut; i++)
    {
        agacOlustur(root, suffixArr[i]);
    }

            while(( anaMenuSecim = menuSecim() )!=-1)
        {
            switch ( anaMenuSecim )
            {
            case 1:
                if(agacOlusturulabilirMi(str,1)==-1){
                	return 0;
				}
				else
                	break;
            case 2:
                katarBul(root);
                break;
            case 3:
                tekrarEdenEnUzunKatar(root,boyut);
                break;
            case 4:
                enCokTekrarEdenKatar(root,boyut);
                break;
            default:
                printf("- Gecersiz giris.");
                break;
            }

        }

    return 0;
}
