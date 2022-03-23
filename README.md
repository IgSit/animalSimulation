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

After running the application (executing World.java) window with fields to enter simulation parameters is displayed. Following parameters should be explained:
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
