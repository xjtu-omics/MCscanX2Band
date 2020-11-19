/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mcscanx2band;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author xiaofei
 */
public class MCscanX2Band {
    
    public static void collinearyToCircusBand(String bedFile, String collinearyFile, String outFile){
        HashMap<String, Bed> theBedData = new HashMap<>();
        String line = "";
        try {
            BufferedReader brBed = new BufferedReader(new FileReader(bedFile));
            while((line = brBed.readLine()) != null){
                String[] theEles = line.split("\t");
                String chr = theEles[0];
                int start = new Integer(theEles[1]);
                int end = new Integer(theEles[2]);
                String id = theEles[3];
                String strand = theEles[4];
                
                Bed oneBed = new Bed(id, chr, start, end);
                theBedData.put(id, oneBed);
            }
            brBed.close();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(MCscanX2Band.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(MCscanX2Band.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        try {
            BufferedReader brCol = new BufferedReader(new FileReader(collinearyFile));
            BufferedWriter bwColBloc = new BufferedWriter(new FileWriter(outFile));
            
            line = "";
            int blockStart1 = 0;
            int blockEnd1 = 0;
            String chr1 = "";
            int blockStart2 = 0;
            int blockEnd2 = 0;
            String chr2 = "";
            float score = 0.0f;
            
            boolean isPlus = true;
            int number = 0;
            while((line = brCol.readLine()) != null){
                if(line.startsWith("## Alignment")){
                    // one block
                    if(blockStart1 > 0){
                        if(!(chr1.contains("unplaced-scaffold") || chr1.contains("ups") || chr2.contains("unplaced-scaffold") || chr2.contains("ups"))){
                            bwColBloc.write(chr1 + "\t" + blockStart1 + "\t" + blockEnd1 + "\t" + 
                                    chr2 + "\t" + blockStart2 + "\t" + blockEnd2 + "\t" + score + "\t" + isPlus + "\n");
                        }
                    }
                    score = new Float(line.substring(line.indexOf("score=") + "score=".length(), 
                                line.indexOf(" e_value")));
                    String[] eles = line.split(" ");
                    String chrs = eles[eles.length - 2];
                    String oneChr1 = chrs.substring(0, chrs.indexOf("&"));
                    String oneChr2 = chrs.substring(chrs.indexOf("&") + 1);
                    chr1 = oneChr1;
                    chr2 = oneChr2;
                    String sign = eles[eles.length - 1];
                    number = 0;
                    isPlus = true;
                    if(sign.equals("minus")){
                        isPlus = false;
                    }
                }
                if(line.startsWith("#")){
                    continue;
                }else{
                    number++;
                    String[] theEles = line.split("\t");
                    String gene1 = theEles[1];
                    String gene2 = theEles[2];
                    
                    if(!theBedData.containsKey(gene1) || !theBedData.containsKey(gene2)){
                        continue;
                    }
                    if(number == 1){
                        blockStart1 = theBedData.get(gene1).getStart();
                        if(isPlus){
                             blockStart2 = theBedData.get(gene2).getStart();
                        }else{
                             blockStart2 = theBedData.get(gene2).getEnd();
                        }
                    }else{
                        blockEnd1 = theBedData.get(gene1).getEnd();
                        if(isPlus){
                            blockEnd2 = theBedData.get(gene2).getEnd();
                        }else{
                            blockEnd2 = theBedData.get(gene2).getStart();
                        }
                    }
                }
            }
            if(!(chr1.contains("unplaced-scaffold") || chr1.contains("ups") || chr2.contains("unplaced-scaffold") || chr2.contains("ups"))){
                bwColBloc.write(chr1 + "\t" + blockStart1 + "\t" + blockEnd1 + "\t" + 
                        chr2 + "\t" + blockStart2 + "\t" + blockEnd2 + "\t" + score + "\t" + isPlus + "\n");
            }
            bwColBloc.flush();
            bwColBloc.close();
            brCol.close();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(MCscanX2Band.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(MCscanX2Band.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    public static void collinearyToCircusBand(String bedFile, String bedFile2, String collinearyFile, String outFile){
        HashMap<String, Bed> theBedData = new HashMap<>();
        String line = "";
        try {
            String[] theBedFiles = {bedFile, bedFile2};
            for(String oneBedFile : theBedFiles){
                BufferedReader brBed = new BufferedReader(new FileReader(oneBedFile));
                while((line = brBed.readLine()) != null){
                    String[] theEles = line.split("\t");
                    String chr = theEles[0];
                    int start = new Integer(theEles[1]);
                    int end = new Integer(theEles[2]);
                    String id = theEles[3];
                    String strand = theEles[4];

                    Bed oneBed = new Bed(id, chr, start, end);
                    theBedData.put(id, oneBed);
                }
                brBed.close();
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(MCscanX2Band.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(MCscanX2Band.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        try {
            BufferedReader brCol = new BufferedReader(new FileReader(collinearyFile));
            BufferedWriter bwColBloc = new BufferedWriter(new FileWriter(outFile));
            
            line = "";
            int blockStart1 = 0;
            int blockEnd1 = 0;
            String chr1 = "";
            int blockStart2 = 0;
            int blockEnd2 = 0;
            String chr2 = "";
            float score = 0.0f;
            
            boolean isPlus = true;
            int number = 0;
            while((line = brCol.readLine()) != null){
                if(line.startsWith("## Alignment")){
                    // one block
                    if(blockStart1 > 0){
                        if(!(chr1.contains("unplaced-scaffold") || chr1.contains("ups") || chr2.contains("unplaced-scaffold") || chr2.contains("ups"))){
                            bwColBloc.write(chr1 + "\t" + blockStart1 + "\t" + blockEnd1 + "\t" + 
                                    chr2 + "\t" + blockStart2 + "\t" + blockEnd2 + "\t" + score + "\t" + isPlus + "\n");
                        }
                    }
                    score = new Float(line.substring(line.indexOf("score=") + "score=".length(), 
                                line.indexOf(" e_value")));
                    String[] eles = line.split(" ");
                    String chrs = eles[eles.length - 2];
                    String oneChr1 = chrs.substring(0, chrs.indexOf("&"));
                    String oneChr2 = chrs.substring(chrs.indexOf("&") + 1);
                    chr1 = oneChr1;
                    chr2 = oneChr2;
                    String sign = eles[eles.length - 1];
                    number = 0;
                    isPlus = true;
                    if(sign.equals("minus")){
                        isPlus = false;
                    }
                }
                if(line.startsWith("#")){
                    continue;
                }else{
                    number++;
                    String[] theEles = line.split("\t");
                    String gene1 = theEles[1];
                    String gene2 = theEles[2];
                    
                    if(!theBedData.containsKey(gene1) || !theBedData.containsKey(gene2)){
                        continue;
                    }
                    if(number == 1){
                        blockStart1 = theBedData.get(gene1).getStart();
                        if(isPlus){
                             blockStart2 = theBedData.get(gene2).getStart();
                        }else{
                             blockStart2 = theBedData.get(gene2).getEnd();
                        }
                    }else{
                        blockEnd1 = theBedData.get(gene1).getEnd();
                        if(isPlus){
                            blockEnd2 = theBedData.get(gene2).getEnd();
                        }else{
                            blockEnd2 = theBedData.get(gene2).getStart();
                        }
                    }
                }
            }
            if(!(chr1.contains("unplaced-scaffold") || chr1.contains("ups") || chr2.contains("unplaced-scaffold") || chr2.contains("ups"))){
                bwColBloc.write(chr1 + "\t" + blockStart1 + "\t" + blockEnd1 + "\t" + 
                        chr2 + "\t" + blockStart2 + "\t" + blockEnd2 + "\t" + score + "\t" + isPlus + "\n");
            }
            bwColBloc.flush();
            bwColBloc.close();
            brCol.close();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(MCscanX2Band.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(MCscanX2Band.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        System.out.println("Usage: \n" + 
                "For self collinearity: java -jar MCscanX2Band.jar -bed species.bed -c collinary_file -out outfile \n" + 
                "For two species collinearity: java -jar MCscanX2Band.jar -bed1 species1.bed -bed2 species2.bed -c collinary_file -out outfile");
        
        if(args.length < 3){
            System.err.println("parameter error");
            System.exit(1);
        }
        
        String bed1 = "";
        String bed2 = "";
        String coll = "";
        String outfile = "";
        for(int i = 0; i < args.length; ++i){
            if(args[i].equals("-bed") || args[i].equals("-bed1")){
                bed1 = args[i + 1];
                i++;
            } else if(args[i].equals("-bed2")){
                bed2 = args[i+1];
                i++;
            } else if(args[i].equals("-c")){
                coll = args[i + 1];
                i++;
            } else if(args[i].equals("-out")){
                outfile = args[i + 1];
                i++;
            }else{
                System.err.println("parameter error");
                System.exit(1);
            }
            
        }
        if(!bed2.equals("")){
            MCscanX2Band.collinearyToCircusBand(bed1, bed2, coll, outfile);
        }else{
            MCscanX2Band.collinearyToCircusBand(bed1, coll, outfile);
        }
    }
    
}
