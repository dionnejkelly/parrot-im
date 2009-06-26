package test;

import static org.junit.Assert.*;

import model.dataType.tempData.ProfileTempData;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class ProfileTempDataTest {
	private ProfileTempData pd1;
	private ProfileTempData pd2;
	@Before
	public void setUp() throws Exception {
		pd1 = new ProfileTempData("Forrest","Gump",true);
		
	}

	@After
	public void tearDown() throws Exception {
		pd1 = null;
		pd2 = null;
	}

	@Test
	public void testProfileTempData() {
		pd2 = new ProfileTempData("Action","SavingRayan",false);
		assertSame("Action",pd2.getName());
		assertSame("SavingRayan",pd2.getPassword());
		assertFalse(pd2.isDefaultProfile());
	}

	@Test
	public void testSetName() {
		String expected = "Tom";
		pd1.setName(expected);
		assertSame(expected,pd1.getName());
	}

	@Test
	public void testGetName() {
		String expected = "Forrest";
		assertSame(expected,pd1.getName());
	}

	@Test
	public void testSetPassword() {
		String expected = "Hanks";
		pd1.setPassword(expected);
		assertSame(expected,pd1.getPassword());
	}

	@Test
	public void testGetPassword() {
		String expected = "Gump";
		assertSame(expected,pd1.getPassword());
	}

	@Test
	public void testSetDefaultProfile() {
		pd1.setDefaultProfile(false);
		assertFalse(pd1.isDefaultProfile());
		pd1.setDefaultProfile(true);
		assertTrue(pd1.isDefaultProfile());
	}

	@Test
	public void testIsDefaultProfile() {
		assertTrue(pd1.isDefaultProfile());
		pd1.setDefaultProfile(false);
		assertFalse(pd1.isDefaultProfile());
	}

}
