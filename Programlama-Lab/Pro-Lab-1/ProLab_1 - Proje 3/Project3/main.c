#include <stdio.h>
#include <stdlib.h> 

struct inis_Sirasi
{
    int oncelikID;
    int ucakID;
    int talepSaati;
    int inisSaati;
    int gecikmeSayisi;
    struct inis_Sirasi*next;
};
static int saatler[25];

struct inis_Sirasi *root = NULL;
struct inis_Sirasi *son = NULL;


struct kalkis_Sirasi
{
    int oncelikID;
    int ucakID;
    int talepSaati;
    int inisSaati;
    int gecikmeSayisi;
    int kalkisSaati;
    struct kalkis_Sirasi *next;
};
struct kalkis_Sirasi *front = NULL;
struct kalkis_Sirasi *end = NULL;

void inisSirasiDuzenle(struct inis_Sirasi *hedefBack,struct inis_Sirasi *hedef,struct inis_Sirasi *iter)
{
    struct inis_Sirasi *sonraki;
    sonraki = iter->next;
    // printf("HedefBack: %d\n",iter->talepSaati);

    if(hedef->talepSaati!=iter->talepSaati)
    {
        //Hedefin talep ettigi yeni saat bos ise orada kal
        printf("\n+ %d ID'li ucaga saat %d icin inis izni verilmistir. (Gecikme sayisi: %d)\n",hedef->ucakID,hedef->talepSaati,hedef->gecikmeSayisi);
        return;
    }//Hedefin talep ettigi yeni saat bos ise orada kal

    else
    {

        //Hedefin talep ettigi yeni saat dolu ise
        if(hedef->oncelikID<iter->oncelikID)
        {
            //Hedef daha oncelikli ise
            if(iter->gecikmeSayisi==3)
            {
                //Dolu olan saatin gecikmesi 3 ise daha gecikemez
                hedef->gecikmeSayisi++;
                if(hedef->gecikmeSayisi<=3)
                {
                    //Gecikme siniri asilmamissa
                    hedef->next=iter->next;
                    iter->next=hedef;
                    hedefBack->next= iter;
                    hedef->talepSaati++;
                    inisSirasiDuzenle(iter,iter->next,iter->next->next);
                    return;
                }//Gecikme siniri asilmamissa
                else
                {
                    printf("\nDaha fazla geciktirme yapilamadigi icin;\ninis iptal edilip ucak Sabiha Gokcen Havalimanina yonlendirilmistir.");
                    printf("\nIptal edilen ucak ID: %d\n",hedef->ucakID);
                    hedefBack->next=iter;
                    free(hedef);
                    return;
                }

            }//Dolu olan saatin gecikmesi 3 ise daha gecikemez
            else
            {
                iter->gecikmeSayisi++;
                iter->talepSaati++;
                printf("\n+ %d ID'li ucaga saat %d icin inis izni verilmistir. (Gecikme sayisi: %d)\n",hedef->ucakID,hedef->talepSaati,hedef->gecikmeSayisi);
                inisSirasiDuzenle(hedef,iter,iter->next);
                return;
            }
        }//Hedef daha oncelikli ise
        else if(hedef->oncelikID>iter->oncelikID)
        {
            //iter daha oncelikli ise

            if(iter->next==NULL)//Sona denk geliyodur
            {
                hedef->next=NULL;
                son=hedef;
                iter->next =hedef;
                hedefBack->next=iter;
            }
            else
            {
                hedef->next=iter->next;
                iter->next =hedef;
                hedefBack->next=iter;
            }

            if(hedef->gecikmeSayisi==3)
            {
                //sabihaya
                printf("\nDaha fazla geciktirme yapilamadigi icin;\ninis iptal edilip ucak Sabiha Gokcen Havalimanina yonlendirilmistir.");
                printf("\nIptal edilen ucak ID: %d\n",hedef->ucakID);
                if(son==hedef)
                {
                    son=iter;
                    iter->next = NULL;
                    free(hedef);
                    return;
                }
                iter->next=hedef->next;
                free(hedef);
                return;
            }
            else if (hedef->gecikmeSayisi<3)
            {

                hedef->gecikmeSayisi++;
                hedef->talepSaati++;
                inisSirasiDuzenle(iter,hedef,hedef->next);
            }

        }//iter daha oncelikli ise
        else
        {
            //Oncelikler Esit ise
            if(iter->gecikmeSayisi==3)
            {
                hedef->gecikmeSayisi++;
                if(hedef->gecikmeSayisi<=3)
                {
                    hedef->talepSaati++;
                    hedef->next=iter->next;
                    iter->next=hedef;
                    hedefBack->next=iter;
                    inisSirasiDuzenle(iter,iter->next,iter->next->next);

                    return;
                }
                else
                {
                    printf("\nDaha fazla geciktirme yapilamadigi icin;\ninis iptal edilip ucak Sabiha Gokcen Havalimanina yonlendirilmistir.");
                    printf("\nIptal edilen ucak ID: %d\n",hedef->ucakID);
                    if(son==hedef)
                    {
                        son=iter;
                        iter->next = NULL;
                        free(hedef);
                        return;
                    }
                    iter->next=hedef->next;
                    free(hedef);
                    return;
                }
            }

            if(hedef->ucakID>iter->ucakID)
            {
                //Onceligi ucakID kucuk olana verme (itere ver)


                if(iter->next==NULL)//Sona denk geliyodur
                {
                    hedef->next=NULL;
                    son=hedef;
                    iter->next =hedef;
                    hedefBack->next=iter;
                }
                else
                {
                    hedef->next=iter->next;
                    iter->next =hedef;
                    hedefBack->next=iter;
                }

                if(hedef->gecikmeSayisi==3)
                {
                    //sabihaya
                    printf("\nDaha fazla geciktirme yapilamadigi icin;\ninis iptal edilip ucak Sabiha Gokcen Havalimanina yonlendirilmistir.");
                    printf("\nIptal edilen ucak ID: %d\n",hedef->ucakID);
                    if(son==hedef)
                    {
                        son=iter;
                        iter->next = NULL;
                        free(hedef);
                        return;
                    }
                    iter->next=hedef->next;
                    free(hedef);
                    return;
                }
                else if (hedef->gecikmeSayisi<3)
                {

                    hedef->gecikmeSayisi++;
                    hedef->talepSaati++;
                    inisSirasiDuzenle(iter,hedef,hedef->next);
                }


            }//Onceligi ucakID kucuk olana verme (itere ver)

            else if (hedef->ucakID<iter->ucakID)
            {
                //Onceligi ucakID kucuk olana verme (hedefe ver)
                if(iter->gecikmeSayisi==3)
                {
                    //Dolu olan saatin gecikmesi 3 ise daha gecikemez
                    hedef->gecikmeSayisi++;
                    if(hedef->gecikmeSayisi<=3)
                    {
                        //Gecikme siniri asilmamissa
                        hedef->talepSaati++;
                        inisSirasiDuzenle(hedefBack,hedef,sonraki);
                    }//Gecikme siniri asilmamissa
                    else
                    {
                        printf("\nDaha fazla geciktirme yapilamadigi icin;\ninis iptal edilip ucak Sabiha Gokcen Havalimanina yonlendirilmistir.");
                        printf("\nIptal edilen ucak ID: %d",hedef->ucakID);
                        hedefBack->next=iter;
                        free(hedef);
                        return;
                    }

                }//Dolu olan saatin gecikmesi 3 ise daha gecikemez

                else if(iter->gecikmeSayisi<=3)
                {
                    iter->gecikmeSayisi++;
                    iter->talepSaati++;
                    if(iter->next==NULL)
                    {
                        //Eger sonda ise dur
                        return;
                    }//Eger sonda ise dur
                    else
                    {
                        hedefBack = hedef;
                        hedef = hedef->next;
                        inisSirasiDuzenle(hedefBack,hedef,hedef->next);
                    }

                }

            }//Onceligi ucakID kucuk olana verme (hedefe ver)


        }//Oncelikler Esit ise

    }//Hedefin talep ettigi yeni saat dolu ise



}


void inisSirasinaEkle(int oncelikID,int ucakID,int talepSaati)
{
    struct inis_Sirasi *ucak = (struct inis_Sirasi*)malloc(sizeof(struct inis_Sirasi));
    ucak->oncelikID = oncelikID;
    ucak->ucakID = ucakID;
    ucak->talepSaati= talepSaati;
    ucak->gecikmeSayisi =0;
    ucak->next = NULL;

    if(root==NULL)
    {
        root = (struct inis_Sirasi*)malloc(sizeof(struct inis_Sirasi));
        root = ucak;
        son = root;
        printf("\n+ %d ID'li ucaga saat %d icin inis izni verilmistir.\n",ucak->ucakID,ucak->talepSaati);
        return;
    }

    else
    {

        struct inis_Sirasi *iter;
        iter = root;
        struct inis_Sirasi *iterBack=(struct inis_Sirasi*)malloc(sizeof(struct inis_Sirasi)); // iteratorun gosterdigi degerden oncekini tutar
        iterBack->next = root;
        while(iter!=NULL)
        {
            if (root->talepSaati > ucak->talepSaati)
            {
                //Basa ekleme durumu
                ucak->next= root;
                root = ucak;
                printf("\n+ %d ID'li ucaga saat %d icin inis izni verilmistir.\n",ucak->ucakID,ucak->talepSaati);
                break;
            }//Basa ekleme durumu

            if(ucak->talepSaati > son->talepSaati)
            {
                // En sona ekleme durumu
                son->next = ucak;
                son = son ->next;
                ucak->next = NULL;
                printf("\n+ %d ID'li ucaga saat %d icin inis izni verilmistir.\n",ucak->ucakID,ucak->talepSaati);
                break;
            }// En sona ekleme durumu

            if(iter->talepSaati < ucak->talepSaati &&
                    iter->next->talepSaati > ucak->talepSaati)
            {
                //Araya Ekleme durumu
                ucak->next=iter->next;
                iter->next=ucak;
                printf("\n+ %d ID'li ucaga saat %d icin inis izni verilmistir.\n",ucak->ucakID,ucak->talepSaati);
                break;
            }//Araya Ekleme durumu

            if(iter->talepSaati == ucak->talepSaati &&
                    iter->oncelikID > ucak->oncelikID &&
                    iter->gecikmeSayisi<=3)
            {
                // Ayni saat talep edilen
                // Eklenen Ucak Daha Oncelikli ise

                if(iter->gecikmeSayisi==3)
                {
                    //Eger listeye daha once eklenmis ucak 3 kez ertelenmisse
                    ucak->gecikmeSayisi++;
                    ucak->talepSaati++;
                    ucak->next=iter->next;
                    iter->next=ucak;
                    inisSirasiDuzenle(iter,iter->next,iter->next->next);
                    break;
                }//Eger listeye daha once eklenmis ucak 3 kez ertelenmisse

                iter->gecikmeSayisi++;
                iterBack->next = ucak;
                ucak->next = iter;
                if (root->talepSaati==ucak->talepSaati)
                {
                    // Eger ayni saat olma durumu en basa denk geliyorsa
                    root = ucak;
                    iter->talepSaati++;
                    inisSirasiDuzenle(root,iter,iter->next);
                    printf("\n+ %d ID'li ucaga saat %d icin inis izni verilmistir.\n",ucak->ucakID,ucak->talepSaati);
                    break;
                } // Eger ayni saat olma durumu en basa denk geliyorsa
                iter->talepSaati++;
                inisSirasiDuzenle(iterBack,iter,iter->next);
                printf("\n+ %d ID'li ucaga saat %d icin inis izni verilmistir.\n",ucak->ucakID,ucak->talepSaati);
                break;

                // Ayni saat talep edilen
                // Eklenen Ucak Daha Oncelikli ise
            }

            else if (iter->talepSaati == ucak->talepSaati &&
                     iter->oncelikID < ucak->oncelikID &&
                     iter->gecikmeSayisi<=3)
            {
                // Ayni saat talep edilen
                // Eklenen Ucak Daha az Oncelikli ise

                ucak->next=iter->next;
                iter->next=ucak;
                ucak->gecikmeSayisi++;
                ucak->talepSaati++;
                iterBack = iter;
                iter=iter->next;

                inisSirasiDuzenle(iterBack,iter,iter->next);
                break;//kodu tamamlamadigim icin buraya break koymam lazim

            }// Ayni saat talep edilen
            // Eklenen Ucak Daha az Oncelikli ise

            else if(iter->talepSaati == ucak->talepSaati &&
                    iter->oncelikID == ucak->oncelikID &&
                    iter->gecikmeSayisi<=3)
            {
                //Ayni saat talep edilen
                //Oncelikler Esit ise

                if(ucak->ucakID>iter->ucakID)
                {
                    //iter daha oncelikli ise
                    ucak->next=iter->next;
                    iter->next=ucak;
                    ucak->gecikmeSayisi++;
                    ucak->talepSaati++;
                    iterBack=iter;
                    iter=iter->next;

                    inisSirasiDuzenle(iterBack,iter,iter->next);
                }//iter daha oncelikli ise

            }//Ayni saat talep edilen
            //Oncelikler Esit ise

            iter = iter->next;
            iterBack = iterBack->next;
        }

    }



}


void kalkisSirasinaEkle(struct inis_Sirasi *select)
{
    struct kalkis_Sirasi *ucak = (struct kalkis_Sirasi*)malloc(sizeof(struct kalkis_Sirasi));
    ucak->oncelikID = select->oncelikID;
    ucak->ucakID = select->ucakID;
    ucak->talepSaati = select->talepSaati;
    ucak->gecikmeSayisi = select->gecikmeSayisi;
    ucak->inisSaati = select->inisSaati;
    ucak->kalkisSaati= select->inisSaati + 1;
    ucak->next = NULL;

    if(front==NULL)
    {
        front = ucak;
        end = front;
        printf("\n+ %d ID'li ucaga saat %d icin kalkis izni verilmistir.\n",ucak->ucakID,ucak->kalkisSaati);
        return;
    }
    else
    {
     end->next = ucak;
     end = ucak;
     printf("\n+ %d ID'li ucaga saat %d icin kalkis izni verilmistir.\n",ucak->ucakID,ucak->kalkisSaati);
     return;
    }
}


int main(int argc, char *argv[])
{
    FILE *fPtr=fopen("input.txt","r+");
    int oncelikId,ucakId,talepSaati;

    while(!feof(fPtr))
    {
        fscanf(fPtr,"%d %d %d\n",&oncelikId,&ucakId,&talepSaati);
        inisSirasinaEkle(oncelikId,ucakId,talepSaati);
    }

    struct inis_Sirasi *yaz;
    yaz = root;
    FILE *oPtr;
    oPtr=fopen("output.txt","w+");
    rewind(oPtr);
    struct kalkis_Sirasi *iter;

    while(yaz!=NULL)
    {
    	yaz->inisSaati = yaz->talepSaati;
    	yaz->talepSaati = yaz->talepSaati - yaz->gecikmeSayisi;

		if(yaz->inisSaati!=24)
        {
        	kalkisSirasinaEkle(yaz);
        	iter=end;
        	fprintf(oPtr,"%d %d %d %d %d %d\n",iter->oncelikID,iter->ucakID,
                 iter->talepSaati,iter->inisSaati,
                 iter->gecikmeSayisi,iter->kalkisSaati);
		}
		else
        {
            printf("\n- Kalkis pisti doldugu icin %d ID'li ucaga kalkis izni verilememistir.",yaz->ucakID);
        }
		yaz = yaz->next;
    }
    
yaz=root;
printf ("\n\n********************************** INIS SIRASI **********************************\n");
printf("\nOncelik ID\t Ucak ID\t Talep Saati\t Inis Saati\t Gecikme Sayisi");
while(yaz!=NULL)
{
	printf("\n%5d\t\t %5d\t\t %5d\t\t %5d\t\t %5d\t\t",yaz->oncelikID,yaz->ucakID,yaz->talepSaati,yaz->inisSaati,yaz->gecikmeSayisi);
	yaz = yaz->next;
}

iter=front;
printf ("\n\n************************************** KALKIS SIRASI **************************************\n");
printf("\nOncelik ID\t Ucak ID\t Talep Saati\t Inis Saati\t Kalkis Saati\t Gecikme Sayisi");
while(iter!=NULL)
{
	printf("\n%5d\t\t %5d\t\t %5d\t\t %5d\t\t %5d\t\t %5d",iter->oncelikID,iter->ucakID,iter->talepSaati,iter->inisSaati,iter->kalkisSaati,iter->gecikmeSayisi);
	iter = iter->next;
}



    return 0;
}
