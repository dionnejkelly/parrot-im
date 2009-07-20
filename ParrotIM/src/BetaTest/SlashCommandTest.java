package BetaTest;
import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import controller.slashCommand.SlashCommand;


public class SlashCommandTest {
	private SlashCommand sc1;
	private SlashCommand sc2;
	@Before
	public void setUp() throws Exception {
		sc1 = new SlashCommand();
	}

	@After
	public void tearDown() throws Exception {
		sc1 = null;
		sc2 = null;
	}

	@Test
	public void testSlashCommand() {
		// Constructor not implemented yet
		fail("Not yet implemented");
	}

	@Test
	public void testSlashCommandMainController() {
		// Later on will be implemented
		fail("Not yet implemented");
	}

	@Test
	public void testIsSlashCommand() {
		assertFalse(sc1.isSlashCommand("Hello"));
		assertFalse(sc1.isSlashCommand(""));
		assertTrue(sc1.isSlashCommand("/online"));
		assertTrue(sc1.isSlashCommand("/ONLINE"));
		assertTrue(sc1.isSlashCommand("/busy"));
		assertTrue(sc1.isSlashCommand("/away from home"));
		assertTrue(sc1.isSlashCommand("/"));
		assertTrue(sc1.isSlashCommand("/bored"));
		assertFalse(sc1.isSlashCommand("online/"));
		assertTrue(sc1.isSlashCommand("//"));
	}

}
