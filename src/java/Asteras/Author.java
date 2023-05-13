/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Asteras;

/**
 *
 * @author Ηλίας
 */
public class Author  implements Comparable<Author>{
    private String name;
    private double score;
    private String field;

    public Author(String name, double score, String field) {
        this.name = name;
        this.score = score;
        this.field = field;
    }

    public String getName() {
        return name;
    }

    public double getScore() {
        return score;
    }

    public String getField() {
        return field;
    }

    @Override
    public int compareTo(Author o) {
        if( score - o.getScore() == 0){
            return 0;
        }
        else if ( score - o.getScore() < 0){
            return 1;
        }
        else{
            return -1;
        }
    }
}
