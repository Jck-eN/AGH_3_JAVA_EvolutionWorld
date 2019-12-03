package agh.cs.project1;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class GenotypeTest {

    Genotype g1,g2,g3, g4, g5, g7, g8;
    @Before
    public void init() {
        Integer[] g1arr = {1,2,3,3,4,6,5,7,7,7,0,0,2,5,3,5,4,5,4,7,3,4,5,6,6,7,7,7,7,7,1,1};
        Integer[] g2arr = {1,2,3,3,4,6,5,7,7,7,0,0,0,0,3,5,4,5,4,7,3,4,5,6,6,7,7,7,7,7,1,0};
        Integer[] g3arr = {0,2,3,3,0,6,5,7,7,7,0,0,0,0,3,5,0,5,4,7,3,0,5,6,6,7,7,7,7,7,0,0};
        Integer[] g4arr = {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0};
        Integer[] g5arr = {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0};
        Integer[] g7arr = {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1};
        Integer[] g8arr = {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1};

        g1 = new Genotype(g1arr);
        g2 = new Genotype(g2arr);
        g3 = new Genotype(g3arr);
        g4 = new Genotype(g4arr);
        g5 = new Genotype(g5arr);
        g8 = new Genotype(g8arr);
        g7 = new Genotype(g7arr);
    }


    @Test
    public void toStringTest() {
        Assert.assertEquals(g1.toString(), "[0, 0, 1, 1, 1, 2, 2, 3, 3, 3, 3, 4, 4, 4, 4, 5, 5, 5, 5, 5, 6, 6, 6, 7, 7, 7, 7, 7, 7, 7, 7, 7]");
        Assert.assertEquals(g2.toString(), "[0, 0, 0, 0, 0, 1, 1, 2, 3, 3, 3, 3, 4, 4, 4, 4, 5, 5, 5, 5, 6, 6, 6, 7, 7, 7, 7, 7, 7, 7, 7, 7]");
        Assert.assertEquals(g3.toString(), "[0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 2, 3, 3, 3, 3, 4, 5, 5, 5, 5, 6, 6, 6, 7, 7, 7, 7, 7, 7, 7, 7, 7]");
    }

    @Test
    public void mergeTest() {
        Genotype g6 = g4.merge(g5);
        Assert.assertEquals(g6.toString(),"[0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 2, 3, 4, 5, 6, 7]");
        Assert.assertEquals(g7.merge(g8).toString(),"[0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 2, 3, 4, 5, 6, 7]");
        Assert.assertEquals(g6.merge(g5).toString(),"[0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 2, 3, 4, 5, 6, 7]");
    }
}