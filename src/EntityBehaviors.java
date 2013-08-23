public class EntityBehaviors {

	public static void viewSurroundingEntities(Entity currentEntity, Entity[][] allEntities) {
		// 0 = top, 1 = top-right, 2 = right, continuing clockwise to 7 (top-left)
		Entity[] surroundingEntities = new Entity[8];
		int curX = currentEntity.tileX;
		int curY = currentEntity.tileY;

		// Assigning any entities that surround the current one to the "surroundingEntities" corresponding to key above
		if (curX - 1 >= 0 && curY - 1 >= 0) {
			if (allEntities[curX][curY - 1] != null)
				surroundingEntities[0] = allEntities[curX][curY - 1];
			if (allEntities[curX + 1][curY - 1] != null)
				surroundingEntities[1] = allEntities[curX + 1][curY - 1];
			if (allEntities[curX + 1][curY] != null)
				surroundingEntities[2] = allEntities[curX + 1][curY];
			if (allEntities[curX + 1][curY + 1] != null)
				surroundingEntities[3] = allEntities[curX + 1][curY + 1];
			if (allEntities[curX][curY + 1] != null)
				surroundingEntities[4] = allEntities[curX][curY + 1];
			if (allEntities[curX - 1][curY + 1] != null)
				surroundingEntities[5] = allEntities[curX - 1][curY + 1];
			if (allEntities[curX - 1][curY] != null)
				surroundingEntities[6] = allEntities[curX - 1][curY];
			if (allEntities[curX - 1][curY - 1] != null)
				surroundingEntities[7] = allEntities[curX - 1][curY - 1];
		}

		currentEntity.surEnt = surroundingEntities;
	}

	public static int entitiesOfOppositeID(Entity currentEntity, Entity[] surroundingEntities) {
		int surFoes = 0;

		for (Entity e : surroundingEntities) {
			if (e != null) {
				if (e.ID != currentEntity.ID) {
					surFoes++;
				}
			}
		}
		return surFoes;
	}

}