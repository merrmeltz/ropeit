import static org.junit.Assert.*;

import java.awt.Color;
import java.util.Date;

import javax.swing.ImageIcon;

import org.junit.Test;


public class TestCases {

	@Test
	public void testPhysicsCalculator() {
		Shot firstShot = new Shot(true, "date",new Club("3 iron", Color.black),new Position(0,0));
		DataReader dReader = new DataReader();
		firstShot.setAccelerations(dReader.getData(1));
		PhysicsCalculator calc = new PhysicsCalculator(firstShot);// the physicsCalc methods now take a Shot
		calc.setDistanceOfPin(33);
		double[] expected = {0, 0};
		double[] result = calc.getXZCoordinates(0);
		assertEquals(36.22, calc.getDistance(), 0.05); //the 35 is a distance we got from MatLab running Vernier raw data
		assertEquals(27.16, calc.getSpeed(), 0.05);
		assertEquals(3.6, calc.getMaxHeight(), 0.05);
		assertEquals(1.15, calc.gettFlight(), 0.05);
		assertEquals(36.2, calc.getRoundEDistance(), 1);
		assertEquals(33.1, calc.getRoundMDistance(), 1);
		assertEquals(60.8, calc.getRoundESpeed(), 1);
		assertEquals(97.8, calc.getRoundMSpeed(), 1);
		assertEquals(3.6, calc.getRoundEHeight(), 1);
		assertEquals(3.9, calc.getRoundMHeight(), 1);
		assertEquals(15.92, calc.getLaunchAngle(), 0.05);
		assertEquals(15.9, calc.getRoundLaunchAngle(), 1);
		assertEquals(33, calc.getDistanceOfPin(), 0);
		assertEquals(3.22, calc.getDistanceToPin(), 0.05);
		assertEquals(3.2, calc.getRoundEDistanceToPin(), 1);
		assertEquals(2.9, calc.getRoundMDistanceToPin(), 1);
		assertArrayEquals(expected, result, 0.05);
		
	}
	
	
	@Test
	public void testShot() {
		Club club = new Club("3 iron",Color.black);
		Club club2 = new Club("9 iron",Color.black);
		Shot aShot = new Shot(true, "2-5-0001",club, new Position(1,2));
		
		assertEquals(club, aShot.getClubType());
		aShot.setClubType(club2);
		assertEquals(club2, aShot.getClubType()); //test setting club type
		assertEquals("2-5-0001", aShot.getDate());
		aShot.setDate("2-4-0001");
		assertEquals("2-4-0001", aShot.getDate()); // test setDate
		assertEquals(true, aShot.getMode());
		assertEquals(1, aShot.getPosition().getX(),0.0001);
		assertEquals(2, aShot.getPosition().getY(),0.0001);
	}
	
	@Test
	public void testPosition(){
		Position origin = new Position(0,0);
		Position posn1 = new Position(3,4);
		assertEquals(5, origin.getDistance(posn1),0.001);
				
	}
	
	@Test
	public void testTrophy() {
		
		Trophy aTrophy = new Trophy("Name: Test Award", "Info: Fake", "Requirement: Not much.", 1, true);
		
		assertEquals("Name: Test Award", aTrophy.getName());
		assertEquals("Info: Fake", aTrophy.getInfo());
		assertEquals("Requirement: Not much.", aTrophy.getRequirements());
		assertEquals(1, aTrophy.getValue(),0.001);
		assertEquals(true, aTrophy.isObtained());
		
	}
	
	@Test
	public void testGetFirstDate() throws InterruptedException{
		Session session = new Session();
		Date date = new Date();
		Shot shot1 = new Shot(false, ""+date.toString(),RopeItProGUI.getCurrentClubType(),new Position(0,0));
		Thread.sleep(1000);
		Date date2 = new Date();
		Shot shot2 = new Shot(false, ""+date2.toString(),RopeItProGUI.getCurrentClubType(),new Position(0,0));
		Thread.sleep(1000);
		Date date3 = new Date();
		Shot shot3 = new Shot(false, ""+date3.toString(),RopeItProGUI.getCurrentClubType(),new Position(0,0));
		Shot first = session.getFirstShot();
		assertNull(first); // Nothing has been added to the session yet
		session.getSessionShots().add(shot1);
		first = session.getFirstShot(); 
		assertEquals(first,shot1); // make sure getFirstShot is working
		assertEquals(first.getDate(),date.toString()); // make sure the Date getters are working
		session.getSessionShots().add(shot2);
		first = session.getFirstShot(); 
		assertEquals(first,shot1); // make sure that after you add another shot, first is still the same
		assertEquals(first.getDate(),date.toString());
		session.getSessionShots().add(shot3);
		first = session.getFirstShot(); 
		assertEquals(first,shot1); // make sure that after you add another shot, first is still the same
		assertEquals(first.getDate(),date.toString());
	}
	

}
