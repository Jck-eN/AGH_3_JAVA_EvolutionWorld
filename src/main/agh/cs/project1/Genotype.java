package agh.cs.project1;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class Genotype {
    private static final Integer GENOTYPE_LENGTH = 32;
    private final Integer[] genes;

    public Genotype(){
        Random r = new Random();
        genes = new Integer[GENOTYPE_LENGTH];
        for(int i=0; i<genes.length; i++){
            genes[i] = r.nextInt(8);
        }
        Arrays.sort(genes);
    }

    Genotype(Integer[] g){
        genes = Arrays.copyOf(g, GENOTYPE_LENGTH);
        Arrays.sort(genes);
    }

    public String toString(){
        return Arrays.toString(this.genes);
    }

    public Genotype merge(Genotype other){
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
        Integer secondGenotypePartNumber = r.nextInt(indexes.length);
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
        this.fixGenes(newGenesArray);
        return new Genotype(newGenesArray);
    }

    public Integer getRandomGene(){
        Random r = new Random();
        return this.genes[r.nextInt(GENOTYPE_LENGTH)];
    }

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

    private void forceAddGene(Integer[] genes, Integer gene_no){
        Random r = new Random();
        Integer idx;
        do{
            idx = r.nextInt(GENOTYPE_LENGTH);
        }while(countGenes(genes, genes[idx]) <= 1);
        genes[idx] = gene_no;
        Arrays.sort(genes);
    }

    private int countGenes(Integer[] genes, Integer gene_no){
        int res=0;
        for(Integer g : genes){
            if(g.equals(gene_no)) res++;
        }
        return res;
    }

}
