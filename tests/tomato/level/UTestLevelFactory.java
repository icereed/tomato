package tomato.level;

import static org.junit.Assert.*;

import org.junit.Test;

public class UTestLevelFactory {

	@Test
	public void test_getLevelById() {
		Level level = LevelFactory.getLevelById(LevelFactory.level1);

		assertNotNull(level);
		assertTrue("There were no walls added by the factory.", level.getWalls().size() > 0);

	}

}
