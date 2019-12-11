package agh.cs.project1;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

/**
 * Genotype of the animal
 *
 * @author Jacek N.
 */
public class Genotype {
    private static final Integer GENOTYPE_LENGTH = 32;
    private final Integer[] genes;

    /**
     * Generates random genotype with at least one gene of each kind,
     */
    Genotype(){
        Random r = new Random();
        this.genes = new Integer[GENOTYPE_LENGTH];
        for(int i=0; i<8; i++){
            this.genes[i]=i;
        }
        for(int i=8; i<this.genes.length; i++){
            this.genes[i] = r.nextInt(8);
        }
        Arrays.sort(genes);
    }

    /**
     * Generates genotype base on array of integers
     *
     * @param g array of genes (integers)
     */
    Genotype(Integer[] g){
        this.genes = Arrays.copyOf(g, GENOTYPE_LENGTH);
        Arrays.sort(genes);
    }

    /**
     *
     * @return string containing all genes
     */
    public String toString(){
        return Arrays.toString(this.genes);
    }

    /**
     * Merge two genotypes creating new genotype based on rules
     * defined in project description
     *
     * @param other genotype to merge with
     * @return merged genotype
     */
    Genotype merge(Genotype other){
        Random r = new Random();
        Integer[] indexes =new Integer[3];
        indexes[0] = 0;
        indexes[1] = r.nextInt(GENOTYPE_LENGTH-1)+1; //Get index random from 1 to GEN_LEN-1
        do{
            indexes[2] = r.nextInt(GENOTYPE_LENGTH-1)+1;
        }
        while(indexes[1].equals(indexes[2]));
        Arrays.sort(indexes);

        Genotype first = this;
        Genotype second = other;
        if(r.nextBoolean()){
            first = other;
            second = this;
        }
        int secondGenotypePartNumber = r.nextInt(indexes.length);
        Integer[][] newGenotypeParts = new Integer[indexes.length][];

        ArrayList<Integer> newGenes = new ArrayList<>();
        for(int i=0; i< newGenotypeParts.length; i++){
            Genotype from = secondGenotypePartNumber==i ? second : first;
            int begin = indexes[i];
            int end;
            if(i+1 >= indexes.length) end = GENOTYPE_LENGTH;
            else end = indexes[i+1];
            newGenotypeParts[i] = Arrays.copyOfRange(from.genes, begin, end);
        }
        for(Integer[] part : newGenotypeParts){
            newGenes.addAll(Arrays.asList(part));
        }

        Integer[] newGenesArray = new Integer[newGenes.size()];
        newGenesArray = newGenes.toArray(newGenesArray);
        Arrays.sort(newGenesArray);
        this.fixGenes(newGenesArray); //fixes genes to meet the rules
        return new Genotype(newGenesArray);
    }

    /**
     *
     * @return random gene from array of genes
     */
    public Integer getRandomGene(){
        Random r = new Random();
        return this.genes[r.nextInt(GENOTYPE_LENGTH)];
    }

    /**
     * Fix genotype according to rules defined
     * in project description
     *
     * @param genes array of genes to fix
     */
    private void fixGenes(Integer[] genes){
        for(Integer i=0; i<8; i++){
            boolean found = false;
            for(Integer gene : genes){
                if (gene.equals(i)) {
                    found = true;
                    break;
                }
            }
            if(!found){
                forceAddGene(genes, i);
            }
        }
    }

    /**
     * Replace random gene in genotype which
     * has at least two occurrences with given one
     *
     * @param genes array of genes we want to add gene in
     * @param gene_no number of gene to add
     */
    private void forceAddGene(Integer[] genes, Integer gene_no){
        Random r = new Random();
        int idx;
        do{
            idx = r.nextInt(GENOTYPE_LENGTH);
        }while(countGenes(genes, genes[idx]) <= 1);
        genes[idx] = gene_no;
        Arrays.sort(genes);
    }

    /**
     *
     * @param genes array of genes
     * @param gene_no gene number to count
     * @return number of occurrences of given gene in genes array
     */
    private int countGenes(Integer[] genes, Integer gene_no){
        int res=0;
        for(Integer g : genes){
            if(g.equals(gene_no)) res++;
        }
        return res;
    }

}
