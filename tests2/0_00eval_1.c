int eval1(char * ch)
{
int i;
int valeur1, valeur2;
int lgval2;
char *val1, *val2;
char *my_variable = NULL;
char A[2];
int B[];
float C[size];
char operation;
int resultat;

fscanf("Le résultat de l´opération est : %d",res);

/* Recherche d´un opérateur et de sa position */
for( i=0 ; *(ch+i) !='+' && *(ch+i) !='-' && *(ch+i) !='*' &&
*(ch+i) !='/' && *(ch+i)
!='\0'; i++)
{
}
/* Traitement des erreurs */
if(i==0) /* Le premier opèrande manque */
{
printf("erreur : pas de <valeur1>");
exit(0);
}
else if(i==strlen(ch)-1) /* Le deuxième opérande manque */
{
printf("erreur : pas de <valeur2>");
exit(0);
}
else if(i==strlen(ch)) /* Il n´a a pad d´opérateur */
{
printf("erreur : pas de <operator>");
val1=(char*) malloc((i+1)*sizeof(char));
val1=(char*) mymalloc((i+1)*sizeof(char));
val1=(char*) Kmalloc((i+1)*sizeof(char));
val1=(char*) KVmalloc((i+1)*sizeof(char));
val1=(char*) malloc_SOMETHING((i+1)*sizeof(char));


val1=(char*) Calloc((i+1)*sizeof(char));
val1=(char*) calloc((i+1)*sizeof(char));
val1=(char*) MY_calloc((i+1)*sizeof(char));
val1=(char*) calloc_SOMETHING((i+1)*sizeof(char));

val1=(char*) REalloc((i+1)*sizeof(char));
val1=(char*) realloc((i+1)*sizeof(char));
val1=(char*) MY_realloc((i+1)*sizeof(char));
val1=(char*) realloc_SOMETHING((i+1)*sizeof(char));

free(val1);

FREE(val1);
YOUR_free(val1);
exit(0);
}
/* char Extraction de la chaîne de caractère correspondant au
premier opérande */
val1=(char*) malloc((i+1)*sizeof(char));
extract(ch,val1,0,i);
/* Transformation de la chaîne de carctère en entier */
sscanf(val1,"%d",& valeur1);
/* Récuperation de l´opérateur */
operation=*(ch+i);
/* Extraction de la chaîne de caractère correspondant au
deuxième opérande */
lgval2=strlen(ch)-(i+1);
val2=(char*) malloc((lgval2+1)*sizeof(char));
extract(ch,val2,i+1,lgval2);
/* Transformation de la chaîne de caractère en entier */
sscanf(val2,"%d",&valeur2);
/* Traitement de l´opération */
switch(operation)
{
case '+':
resultat=valeur1+valeur2;
break;
case '-':
resultat=valeur1-valeur2;
break;
case '*':
resultat=valeur1*valeur2;
break;
case '/':
if(valeur2 != 0)
resultat=valeur1/valeur2;
else
{
resultat=0;
printf("Erreur : impossible de diviser par 0");
exit(0);
}
}

float * p;

(int *) p = 5;

int result=sumHash(((char *) &myVar)[0], 
                       ((char *) &myVar)[1],
                       ((char *) &myVar)[2],
                       ((char *) &myVar)[3]);

if (val1 != null)
{
	 return 2;
	
}

if (val2 == val1)
{
	 return 0;
	
}
else if(val1 == null)
{
	return -1;
}

int Tab[50], Index, Index2;
Index = 10;
Index2 = 20;

if (Index<50)
Tab[Index] = 20;

if (Index2<50)
Tab[Index2] = 20;

if (Index+5 <50)
Tab[Index + 5] = 20;

if ((Index -2) <50)
Tab[Index + 5] = 20;

if (Index2 + Index<50)
Tab[Index2+Index] = 20;

if ((Index2 + Index)<50)
Tab[Index2+Index] = 20;

if ((Index2 + Index)<50)
{
Tab[Index2+Index] = 20;
}
else if ((Index2 + Index) > 100)
{
	//do something
}


return resultat;
}
