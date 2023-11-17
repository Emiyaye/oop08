package it.unibo.deathnote.Impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import it.unibo.deathnote.api.DeathNote;

public class DeathNoteImpl implements DeathNote{

    final static private int DEATH_DETAILS_POSITION = 1;
    final static private int DEATH_CAUSE_POSITION= 0;
    final static private int DEATH_CAUSE_LIMIT = 40;
    final static private int DEATH_DETAILS_LIMIT = 6040;
    final static private List<String> INITAL_DEATH_CAUSE_DETAILS = new ArrayList<>();
    final static private String DEAFAULT_DEATH = "Heart Attack";
    final static private String EMPTY = "";
    final static private Map<Integer, String> RULES = new HashMap<>();
    final private Map<String, List<String>> deathNote = new HashMap<>();
    private String cachedName;
    private long writeNameTime;

    public DeathNoteImpl(){
        for (int i = 1; i <= DeathNote.RULES.size(); i++){
            RULES.put(i, DeathNote.RULES.get(i - 1));
        }
        INITAL_DEATH_CAUSE_DETAILS.add(DEATH_CAUSE_POSITION, EMPTY);
        INITAL_DEATH_CAUSE_DETAILS.add(DEATH_DETAILS_POSITION, EMPTY);
    }

    @Override
    public String getRule(int ruleNumber) {
        if (ruleNumber <= 0 || ruleNumber > RULES.size()){
            throw new IllegalArgumentException("Rule" + ruleNumber + " does not exist");
        }
        return RULES.get(ruleNumber);
    }

    @Override
    public void writeName(String name) {
        if (name == null){
            throw new NullPointerException("You cannot kill a null");
        }
        if (isNameWritten(name)){
            throw new IllegalArgumentException("You cannot kill the guy twice");
        }
        this.deathNote.put(name, new ArrayList<String>(INITAL_DEATH_CAUSE_DETAILS));
        this.cachedName = name;
        this.writeNameTime = System.currentTimeMillis();
    }

    @Override
    public boolean writeDeathCause(String cause) {
        if (cause == null || this.cachedName == null || cause == EMPTY){
            throw new IllegalStateException("Cannot write a death cause that is empty");
        }
        final long currTime = System.currentTimeMillis();
        if (currTime - this.writeNameTime < DEATH_CAUSE_LIMIT){
            this.deathNote.get(this.cachedName).set(DEATH_CAUSE_POSITION, cause);
            this.writeNameTime = currTime;
            return true;
        }
        if (currTime - this.writeNameTime > DEATH_DETAILS_LIMIT){
            this.cachedName = null;
        }
        return false;
    }

    @Override
    public boolean writeDetails(String details) {
        if (details == null || this.cachedName == null || details == EMPTY){
            throw new IllegalStateException("Cannot write a death details that is empty");
        }
        final long currTime = System.currentTimeMillis();
        if (currTime - this.writeNameTime < DEATH_DETAILS_LIMIT){
            this.deathNote.get(this.cachedName).set(DEATH_DETAILS_POSITION, details);
            return true;
        }
        if (currTime - this.writeNameTime > DEATH_DETAILS_LIMIT){
            this.cachedName = null;
        }
        return false;
    }

    @Override
    public String getDeathCause(String name) {
        if (!this.deathNote.containsKey(name)){
            throw new IllegalArgumentException("Cannot find the person");
        }
        if (this.deathNote.get(name).get(DEATH_CAUSE_POSITION) == EMPTY && 
        (this.cachedName != name || 
        System.currentTimeMillis() - this.writeNameTime > DEATH_DETAILS_LIMIT)){
            this.deathNote.get(name).set(DEATH_CAUSE_POSITION, DEAFAULT_DEATH);
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
