package agh.ics.oop;

public class Genes {
    private final int[] genes;
    private final int length;

    public Genes(int length) {  // constructor for non-born Animal
        this.length = length;
        genes = generateGenes();
    }

    public Genes(Animal father, Animal mother) {
        Genes fatherGenes = father.getGenes();
        Genes motherGenes = mother.getGenes();
        int fatherEnergy = father.getEnergy();
        int motherEnergy = mother.getEnergy();
        length = fatherGenes.length;
        genes = generateGenes(fatherEnergy, motherEnergy, fatherGenes, motherGenes);
    }

    public int getRandomGene() {
        int index = (int) (Math.random() * length);
        return genes[index];
    }

    private int[] generateGenes() {
        int[] sequence = new int[length];
        for (int i = 0; i < length; i++) {
            sequence[i] = (int) (Math.random() * 8);
        }
        return sequence;
    }

    private int[] generateGenes(int fatherEnergy, int motherEnergy, Genes fatherGenes, Genes motherGenes) {
        int splitIndex = calculateSplitIndex(fatherEnergy, motherEnergy);
        Side side = chooseSide();
        int [] sequence = new int[fatherGenes.length];

        if (side.equals(Side.LEFT)) {
            System.arraycopy(fatherGenes.genes, 0, sequence, 0, splitIndex);
            System.arraycopy(motherGenes.genes, splitIndex, sequence, splitIndex, length - splitIndex);
        }
        else {
            System.arraycopy(fatherGenes.genes, length - splitIndex, sequence, 0, splitIndex);
            System.arraycopy(motherGenes.genes, 0, sequence, splitIndex, length - splitIndex);
        }
        return sequence;
    }

    private int calculateSplitIndex(int fatherEnergy, int motherEnergy) {
        return (int) ( ( (double) fatherEnergy / (fatherEnergy + motherEnergy) ) * length );
    }

    private Side chooseSide() {
        int x =(int) (Math.random() * 2);
        if (x == 0) {
            return Side.LEFT;
        }
        return Side.RIGHT;
    }
}
