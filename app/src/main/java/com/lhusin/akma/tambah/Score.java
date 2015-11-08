package com.lhusin.akma.tambah;

import java.io.Serializable;
import java.util.Comparator;

/**
 * Created by HomePC on 6/11/2015.
 *  see: http://forum.codecall.net/topic/50071-making-a-simple-high-score-system
 */

public class Score  implements Serializable, Comparable<Score> {
    //Comparable - to sort a single property
    //Comparator - to sort multiple properties
    private int score;
    private String name;

    public int getScore() {
        return score;
    }

    public String getName() {
        return name;
    }

    public Score(String nama, int score) {
        this.score = score;
        this.name = nama;
    }

    @Override
    public int compareTo(Score score1) {
        return ((Integer)(score1.getScore())).compareTo(getScore());
    }

    public static Comparator<Score> NamaPemainComparator = new Comparator<Score>() {
        @Override
        public int compare(Score lhs, Score rhs) {
            String namaPemain1 = lhs.getName().toUpperCase();
            String namaPemain2 = rhs.getName().toUpperCase();

            //ascending order
            return namaPemain1.compareTo(namaPemain2);

            //descendng order
            // return namaPemain2.compareTo(namaPemain1);
        }
    };

}