/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mcscanx2band;

/**
 *
 * @author xiaofei
 */
public class Bed {
    private String id;
    private String chr;
    private int start;
    private int end;
    private char strand;
    
    public Bed(){
        this.chr = "";
        this.id = "";
        this.start = -1;
        this.end = -1;
        this.strand = '+';
    }
    public Bed(String id, String chr, int start, int end){
        this.id = id;
        this.start = start;
        this.end = end;
        this.chr = chr;
    }
    public Bed(String id, String chr, int s, int e, char strand){
        this.id = id;
        this.start = s;
        this.end = e;
        this.strand = strand;
        this.chr = chr;
    }
    
    public void setId(String id){
        this.id = id;
    }
    
    public void setChr(String chr){
        this.chr = chr;
    }
    
    public void setStart(int start){
        this.start = start;
    }
    
    public void setEnd(int end){
        this.end = end;
    }
    
    public void setStrand(char s){
        this.strand = s;
    }
    
    public String getId(){
        return this.id;
    }
    
    public String getChr(){
        return this.chr;
    }
    
    public int getStart(){
        return this.start;
    }
    
    public int getEnd(){
        return this.end;
    }
    
    public char getStrand(){
        return this.strand;
    }
}
