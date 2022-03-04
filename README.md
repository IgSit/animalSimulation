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
