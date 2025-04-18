# Carte aux trésors

Ce projet simule la quête de trésors par des aventuriers sur une carte, via une application Spring Boot.

## Utilisation rapide

1. Place le fichier d’entrée `carte.txt` dans le dossier :  
   `src/main/resources/`

2. **Lance simplement l’application Spring Boot** :
    - Depuis ton IDE : clique sur la classe `TreasuremapApplication`.
    - Ou en ligne de commande :
      ```sh
      ./mvnw spring-boot:run
      ```

3. Le résultat de la simulation du jeu de carte aux trésors s’affichera dans la console.  
   
## Format attendu pour `carte.txt`

C - 3 - 4  
M - 1 - 0  
M - 2 - 1  
T - 0 - 3 - 2  
T - 1 - 3 - 3  
A - Lara - 1 - 1 - S - AADADAGGA  


## Sortie dans la console :

************** Carte aux trésors carte.txt **************  
.   M   .  
.   .   M  
.   .   .  
A(Lara)   T(2)   .   

************** Sortie **************  
C - 3 - 4   
M - 1 - 0  
M - 2 - 1  
T - 1 - 3 - 2  
A - Lara - 0 - 3 - S - 3  