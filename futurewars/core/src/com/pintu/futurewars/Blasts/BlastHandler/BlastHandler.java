package com.pintu.futurewars.Blasts.BlastHandler;

import com.pintu.futurewars.Blasts.Blast;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by hsahu on 8/10/2017.
 */

public class BlastHandler {

    List<Blast> blastList = null;
    Set<Blast> blastToBeRemovedSet = new HashSet<Blast>();

    public BlastHandler(List<Blast> bl){
        blastList = bl;
    }

    public void removeDestroyedBlast(){
        synchronized(blastList){
            for(Blast b: blastList) {
                if(b.destroyed) {
                    blastToBeRemovedSet.add(b);
                }
            }
            for(Blast b:blastToBeRemovedSet){
                blastList.remove(b);
            }
            blastToBeRemovedSet.clear();
        }
    }

    public void addBlast(Blast b){
        blastList.add(b);
    }
}
