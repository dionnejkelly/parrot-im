package BetaTest;
import static org.junit.Assert.*;

import model.Model;
import model.enumerations.ServerType;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import controller.MainController;
import controller.services.BadConnectionException;
import controller.slashCommand.SlashCommand;


public class SlashCommandTest {
	private SlashCommand sc1;
	private SlashCommand sc2;
	@Before
	public void setUp() throws Exception {
//		sc1 = new SlashCommand();
		
		MainController c = new MainController(new Model());
		try {
			c.loginAsGuest(ServerType.GOOGLE_TALK, "cmpt275testing@gmail.com", "abcdefghi");
			
			//SlashCommand cmd = new SlashCommand (c);
			sc1 = new SlashCommand(c);
		} catch (BadConnectionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@After
	public void tearDown() throws Exception {
		sc1 = null;
		sc2 = null;
	}

	
/*
	@Test
	public void testSlashCommandMainController() {
		// Later on will be implemented
		fail("Not yet implemented");
	}
*/
	@Test
	public void testIsSlashCommand() {
		assertFalse(sc1.isSlashCommand("Hello"));
		assertFalse(sc1.isSlashCommand(""));
		assertTrue(sc1.isSlashCommand("/online"));
		assertTrue(sc1.isSlashCommand("/ONLINE"));
		assertTrue(sc1.isSlashCommand("/busy"));
		assertFalse(sc1.isSlashCommand("/rakan"));
		assertTrue(sc1.isSlashCommand("/not available"));
		assertFalse(sc1.isSlashCommand("/Not me"));
		assertTrue(sc1.isSlashCommand("/be right back"));
		assertFalse(sc1.isSlashCommand("/Rakan Mohammed Alkheliwi"));
		assertFalse(sc1.isSlashCommand("/away from home")); // It returns true
		assertTrue(sc1.isSlashCommand("/not to be disturbed"));
		assertFalse(sc1.isSlashCommand("/should this be true"));
		assertTrue(sc1.isSlashCommand("/"));
		assertTrue(sc1.isSlashCommand("/bored"));
		assertFalse(sc1.isSlashCommand("online/"));
		assertTrue(sc1.isSlashCommand("//"));
	}

}
