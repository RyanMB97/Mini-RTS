import java.awt.Color;
import java.awt.Graphics;

public class Entity extends EntityBehaviors {
	Entity[] surEnt;

	// Positioning
	int x, y, ID;
	public static int width = 16, height = 16;
	int tileX, tileY;

	// Other information
	public static int thresholdForConversion = 3;

	// Colors of the entities
	Color IDZero = Color.BLACK;
	Color IDOne = Color.BLUE;

	public Entity(int x, int y, int ID) {
		this.x = x;
		this.y = y;
		this.ID = ID;

		this.tileX = x / width;
		this.tileY = y / height;
	}

	public void tick() {
		surEnt = viewSurroundingEntities(this, Game.allEntities);
		if (entitiesOfOppositeID(this, surEnt) >= thresholdForConversion) {
			if (ID == 0) {
				ID = 1;
			} else if (ID == 1) {
				ID = 0;
			}
		}
	}

	public void render(Graphics g) {
		// Set color based on what the ID of the Entity and draw the entity with a filled in rectangle of the corresponding color
		if (ID == 0) {
			g.setColor(IDZero);
		} else {
			g.setColor(IDOne);
		}
		g.fillRect(x, y, width, height);
		// Outline for easier distinction between different Entities
		g.setColor(Color.BLACK);
		g.drawRect(x, y, width - 1, height - 1);
	}
}