package it.unibo.deathnote.Impl;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import it.unibo.deathnote.api.DeathNote;

public class DeathNoteImpl implements DeathNote{

    final private static int DEATH_DETAILS_POSITION = 1;
    final private static int DEATH_CAUSE_POSITION= 0;
    final private static int DEATH_CAUSE_LIMIT = 40;
    final private static int DEATH_DETAILS_LIMIT = 6040;
    final static private List<String> INITAL_DEATH_CAUSE_DETAILS = new ArrayList<>();
    final static private String DEAFAULT_DEATH = "Heart Attack";
    final static private String EMPTY = "";
    final static private Map<Integer, String> RULES = new HashMap<>();
    final private Map<String, List<String>> deathNote = new HashMap<>();
    private String cachedName;
    private long writeTime;

    public DeathNoteImpl(){
        for (int i = 1; i <= DeathNote.RULES.size(); i++){
            RULES.put(i, DeathNote.RULES.get(i - 1));
        }
        INITAL_DEATH_CAUSE_DETAILS.add(DEATH_CAUSE_POSITION, EMPTY);
        INITAL_DEATH_CAUSE_DETAILS.add(DEATH_DETAILS_POSITION, EMPTY);
    }

    @Override
    public String getRule(int ruleNumber) {
        if (ruleNumber <= 0){
            throw new IllegalArgumentException("Rules cannot be negative");
        }
        return RULES.get(ruleNumber);
    }

    private boolean deathCauseStillEmpty(String name){
        return (name != this.cachedName && deathNote.get(this.cachedName).get(DEATH_CAUSE_POSITION) == EMPTY);
    }

    @Override
    public void writeName(String name) {
        if(deathCauseStillEmpty(name)){
            deathNote.get(this.cachedName).set(DEATH_CAUSE_POSITION, DEAFAULT_DEATH);
        }
        if (name == null){
            throw new NullPointerException("You cannot kill a null");
        }
        if (isNameWritten(name)){
            throw new IllegalArgumentException("You cannot kill the guy twice");
        }
        this.deathNote.put(name, new ArrayList<String> (INITAL_DEATH_CAUSE_DETAILS));
        this.cachedName = name;
        this.writeTime = System.currentTimeMillis();
    }

    @Override
    public boolean writeDeathCause(String cause) {
        final long currTime = System.currentTimeMillis();
        if (currTime - this.writeTime < DEATH_CAUSE_LIMIT){
            this.deathNote.get(this.cachedName).set(DEATH_CAUSE_POSITION, cause);
            return true;
        }
        if ()
        return true;
    }

    @Override
    public boolean writeDetails(String details) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'writeDetails'");
    }

    @Override
    public String getDeathCause(String name) {
        if (!this.deathNote.containsKey(name)){
            throw new IllegalArgumentException("Cannot find the person");
        }
        return this.deathNote.get(name).get(DEATH_CAUSE_POSITION);
    }

    @Override
    public String getDeathDetails(String name) {
        if (!this.deathNote.containsKey(name)){
            throw new IllegalArgumentException("Cannot find the person");
        }
        return this.deathNote.get(name).get(DEATH_DETAILS_POSITION);
    }

    @Override
    public boolean isNameWritten(String name) {
        return this.deathNote.containsKey(name);
    }
    
}
