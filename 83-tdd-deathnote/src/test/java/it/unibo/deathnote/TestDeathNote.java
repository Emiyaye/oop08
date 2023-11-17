package it.unibo.deathnote;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import it.unibo.deathnote.Impl.DeathNoteImpl;
import it.unibo.deathnote.api.DeathNote;

class TestDeathNote {

    private DeathNote light_iamagaY;

    @BeforeEach
    public void setUp(){
        this.light_iamagaY = new DeathNoteImpl();
    }

    @Test
    public void testSetUp(){
        assertNotEquals("", light_iamagaY);;
    }

    @Test
    public void noNegativeRules(){
        assertEquals("""
            The human whose name is written in this note shall die.
            """, light_iamagaY.getRule(1));
        try{
            light_iamagaY.getRule(-1);
            fail("Not encountered an IllegalArgumentException in noNegativeRules");
        } catch(IllegalArgumentException e){
            assertEquals("Rules cannot be negative", e.getMessage());
        }
    }

    @Test
    public void noEmptyRule(){
        for (int i = 1; i <= DeathNote.RULES.size(); i++){
            assertNotEquals("", light_iamagaY.getRule(i));
        }
    }

    @Test
    public void testKillPerson(){
        light_iamagaY.writeName("L");
        try{
            light_iamagaY.writeName("L");
            fail("Not encountered an IllegalArgumentException");
        } catch(IllegalArgumentException e){
            assertEquals("You cannot kill the guy twice", e.getMessage());
        }

        assertTrue(light_iamagaY.isNameWritten("L"));
        assertFalse(light_iamagaY.isNameWritten("Mellow"));

        try{
            light_iamagaY.writeName("");
            fail("Not encountered a IllegalStateException");
        } catch(IllegalStateException e){
            assertEquals("You cannot kill a no name", e.getMessage());
        }
        try{
            light_iamagaY.writeName(null);
            fail("Not encountered a IllegalStateException");
        }catch(IllegalStateException e){
            assertEquals("You cannot kll a null", e.getMessage());
        }
    }

    @Test
    public void testDeathCause() throws InterruptedException{
        light_iamagaY.writeName("Eren");
        light_iamagaY.writeDeathCause("Decapitated");
        assertEquals("Decapitated", light_iamagaY.getDeathCause("Eren"));

        light_iamagaY.writeName("pippo");
        Thread.sleep(41);
        assertFalse(light_iamagaY.writeDeathCause("Hit by a truck"));

        light_iamagaY.writeName("Armin");
        Thread.sleep(6100);
        try{
            light_iamagaY.writeDeathCause("Aging");
            fail("Not encountered an IllegalStateException");
        }catch(IllegalStateException e){
            assertEquals("Cannot write a death cause if not written the name first",
            e.getMessage());
        }
        assertEquals("Heart Attack", light_iamagaY.getDeathCause("Armin"));
        assertNotEquals("Aging", light_iamagaY.getDeathCause("Armin"));
        
        try{
            light_iamagaY.writeName("caio");
            light_iamagaY.writeDeathCause("");
            fail("Not encountered an IllegalStateException");
        }catch(IllegalStateException e){
            assertEquals("Cannot write a death cause that is empty",
            e.getMessage());
        }

        light_iamagaY.writeName("Sasha");
        assertEquals("", light_iamagaY.getDeathCause("Sasha"));
        light_iamagaY.writeDeathCause("ran for too long");
        assertNotEquals("", light_iamagaY.getDeathCause("Sasha"));
        assertEquals("ran for too long", light_iamagaY.getDeathCause("Sasha"));

        light_iamagaY.writeName("Bertolt");
        Thread.sleep(6100);
        try{
            light_iamagaY.writeDetails("Eaten by a no brain titan Armin");
            fail("Not encountered an IllegalStateException");
        }catch(IllegalStateException e){
            assertEquals("Cannot write a death detail if not written the name first", e.getMessage());
        }
        assertEquals("", light_iamagaY.getDeathDetails("Bertolt"));
    }
}