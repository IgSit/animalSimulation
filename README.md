    Polska wersja poniżej.

# Evolution simulator
## Prospect
Project created during Object-Oriented Programming course - the point was to create a simulation of animals moving on a map that can eat grass and copulate to spread their genes further. Apart from visualising map on screen, stats panel was implemented - plots containing basic statistics (number of animals, average energy etc.).

More information can be found here: https://github.com/apohllo/obiektowe-lab/blob/master/proj1/Readme.md

## Project description

As brief as it can be:
1. 2d map contains two types of fields - either jungle or desert - where grass tufts and animals can appear (more grass can be found in jungle in comparison to desert).
2. Animals are described by their energy, orientation on the map (one of directions: N, NE, E, SE, ...) and genes (sequence of 0-7 numbers).
3. Animals have move preferences to move or rotate (their genes are responsible for that - for examble the more genes of type 0, the more likely the animal is to move forward).
4. Animals eat grass on the fields they are located (in case of multiple animals only the stronghest ones eat).
5. Animals can copulate if having enough energy, spreading their genes proportionally to their energy level (the stronger the animal is, the more genes it will pass to their descendant).
6. During each epoque new grass grow on the map.
7. We can track animals that have dominant genotypes - genotype means the most common genes in the sequence.
8. Plots showing statistics are shown next to the map.
9. Stats are written to file in CSV format.
10. In the app there are two types of map - bordered and borderless. When the map is borderless, the animal can pass the end of the map and show on the other side.
11. Both simulations can be independently started and paused.

After running the application (executing World.java) window with fields to enter simulation parameters is displayed. These parameters should be described:
 - **jungle ratio** - ratio of jungle to the whole map - value between 0 and 1
 - **plant profit energy** - value of added energy when eating grass
 -  **animal move energy** - cost of moving or rotating (charged once per epoque) 

<p align="center">
  <img src="https://github.com/IgSit/animalSimulation/blob/main/media/menu.png" />
</p>

After entering the parameters new window is shown:

<p align="center">
  <img src="https://github.com/IgSit/animalSimulation/blob/main/media/Animation.gif" />
</p>

App also creates two files: **borderedData.csv** and **nonBorderedData.csv** contaning stats info about simulation.

# Symulator ewolucyjny
## Prospekt
Projekt powstał w ramach kursu Programowania Obiektowego w ramach kierunku Informatyka - polegał on na stworzeniu symulacji zwierząt poruszających się po mapie, mogących
konsumować znajdującą się tam trawę oraz rozmnażać się ze sobą, przekazując geny. Oprócz samej wizualizacji mapy oraz zachowań zwierząt należało wykonać panel monitorujący
różne statystyki danej epoki - liczba zwierząt, średnia energia itd.

Szczegółowy opis projektu: https://github.com/apohllo/obiektowe-lab/blob/master/proj1/Readme.md

## Opis projektu

W punktach najkrócej jak się da:

1. Mamy mapę 2d, której pola mogą być albo dżunglą, albo pustynią, na których mogą znajdować się kępki trawy i zwierzęta (dżungla charakteryzuje się większym zagęszczeniem trawy
niż pustynia.
2. Zwierzęta charakteryzują się energią, orientacją na mapie (jeden z kierunków: N, NE, E, SE, ...) oraz genami (ciąg cyfr 0-7).
3. Zwierzęta mają preferencje poruszania się lub obracania, są za to odpowiedzialne ich geny - na przykład im więcej genów 0, tym chętniej zwierzak będzie poruszał się
do przodu.
4. Zwierzęta jedzą trawę na swoim polu (w przypadku kilku zwierząt na polu jedzą najsilniejsze).
5. Zwierzęta rozmnażają się, jeśli mają wystarczająco dużo energii, przekazując geny w stopniu proporcjonalnym do ich energii (im zwierzak jest silniejszy, tym
więcej genów przekaże potomstwu).
6. Podczas każdej epoki nowa trawa rośnie na mapie.
7. Mamy możliwość śledzenia na mapie zwierząt o dominującym genotypie - przez genotyp rozumiemy najczęściej występujące cyfry w genach zwierzaka.
8. Obok mapy znajdują się wykresy pokazujące różne statystyki dotyczące symulacji w kolejnych dniach.
9. Statystyki te są zapisywane do pliku w formacie CSV.
10. W aplikacji pojawiają się dwa rodzaje mapy - ograniczona i nie. Ograniczenie mapy polega na tym, że nie można przekroczyć krańca mapy, w wersji nieograniczonej zwierzak
pojawi się po jej drugiej stronie.
11. Symulacje dla obydwu map możemy niezależnie startować i pauzować.

Po otwarciu aplikacji (wykonanie World.java) wita nas okienko do wpisania parametrów symulacji, przy czym wymagają dodatkowego wyjaśnienia poniższe:
 - **jungle ratio** - stosunek wielkości dżungli do całej mapy - wartość z przedziału 0-1
 - **plant profit energy** - wartość energii, jaką otrzyma zwierzę, gdy zje trawę
 -  **animal move energy** - koszt poruszenia się lub wykonania obrotu przez zwierzaka (naliczany raz na epokę)

<p align="center">
  <img src="https://github.com/IgSit/animalSimulation/blob/main/media/menu.png" />
</p>

Po zatwierdzeniu parametrów otwiera się okno właściwe symulacji:

<p align="center">
  <img src="https://github.com/IgSit/animalSimulation/blob/main/media/Animation.gif" />
</p>

Program tworzy też dwa pliki: **borderedData.csv** oraz **nonBorderedData.csv** zawierające dane z obydwu symulacji.
