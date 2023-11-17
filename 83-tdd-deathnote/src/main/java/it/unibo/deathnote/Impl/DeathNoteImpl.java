package it.unibo.deathnote.Impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import it.unibo.deathnote.api.DeathNote;

public class DeathNoteImpl implements DeathNote{

    final private static int DEATH_DETAILS = 1;
    final private static int DEATH_CAUSE = 0;
    final static private List<String> INITAL_DEATH_CAUSE_DETAILS = new ArrayList<>();
    final static private String EMPTY = "";
    final static private Map<Integer, String> RULES = new HashMap<>();
    final private Map<String, ArrayList<String>> deathNote = new HashMap<>();

    public DeathNoteImpl(){
        for (int i = 1; i <= DeathNote.RULES.size(); i++){
            RULES.put(i, DeathNote.RULES.get(i - 1));
        }
        INITAL_DEATH_CAUSE_DETAILS.add(DEATH_CAUSE, EMPTY);
        INITAL_DEATH_CAUSE_DETAILS.add(DEATH_DETAILS, EMPTY);
    }

    @Override
    public String getRule(int ruleNumber) {
        if (ruleNumber <= 0){
            throw new IllegalArgumentException("Rules cannot be negative");
        }
        return RULES.get(ruleNumber);
    }

    @Override
    public void writeName(String name) {
        if (name == null){
            throw new NullPointerException("You cannot kill a null");
        }
        if (!isNameWritten(name)){
            throw new IllegalArgumentException("You cannot kill the guy twice");
        }
        deathNote.put(name, new ArrayList<String> (INITAL_DEATH_CAUSE_DETAILS));
    }

    @Override
    public boolean writeDeathCause(String cause) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'writeDeathCause'");
    }

    @Override
    public boolean writeDetails(String details) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'writeDetails'");
    }

    @Override
    public String getDeathCause(String name) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getDeathCause'");
    }

    @Override
    public String getDeathDetails(String name) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getDeathDetails'");
    }

    @Override
    public boolean isNameWritten(String name) {
        return deathNote.containsKey(name);
    }
    
}
