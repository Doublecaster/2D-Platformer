package fi.majavapaja.game.art;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * Last update 14.3.2013 19:38
 * 
 * @author Sami
 *
 */

public class DrawableString {
	public static final int LEFT = 1;
	public static final int RIGHT = 2;
	public static final int CENTER = 3;
	public static final int ABSOLUTE_CENTER = 4;
	public static final int BOTTOM_LEFT = 5;

	private static Font defaultFont = new Font(Font.MONOSPACED, Font.PLAIN, 16);
	private static Color defaultColor = Color.BLACK;

	private String[] string;
	private Font font;
	private Color color;

	private FontMetrics fm;
	private int fontHeight;
	private int fontDescent;

	private int lineSpace = 2;

	public DrawableString(String string, Font font, Color color) {
		if (string == null) string = "";

		this.string = string.split("\n");
		this.font = font;
		this.color = color;

		if (font == null) this.font = defaultFont;
		if (color == null) this.color = defaultColor;

		BufferedImage img = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB);
		fm = img.createGraphics().getFontMetrics(this.font);

		fontHeight = fm.getHeight();
		fontDescent = fm.getDescent();
	}

	private void updateFontMetrics() {

		// fontWidth = fm.stringWidth(string);

	}

	public void draw(Graphics g, int x, int y, int alignment) {
		g.setFont(font);
		g.setColor(color);

		for (int i = 0; i < string.length; i++) {
			Point p = setupAlignment(x, y, alignment, string[i]);
			g.drawString(string[i], p.x, p.y + i * fontHeight + lineSpace);
		}
	}

	private Point setupAlignment(int x, int y, int alignment, String s) {
		int fontWidth = fm.stringWidth(s);

		if (alignment == CENTER) {
			x -= fontWidth / 2;
			y += fontHeight / 2;
		} else if (alignment == ABSOLUTE_CENTER) {
			x -= fontWidth / 2;
			y += fontHeight / 2 - fontDescent;
		} else if (alignment == RIGHT) {
			y += fontHeight / 2;
			x -= fontWidth;
		} else if(alignment == BOTTOM_LEFT){
			y -= fontHeight / 2;
		} else {
			y += fontHeight / 2;
		}
		return new Point(x, y);
	}

	public void setString(String newString) {
		if (newString == null) newString = "";
		this.string = newString.split("\n");
		updateFontMetrics();
	}

	public void changeFont(Font newFont) {
		font = newFont;
		if (font == null) this.font = defaultFont;
		updateFontMetrics();
	}

	public void changeColor(Color newColor) {
		color = newColor;
		if (color == null) this.color = defaultColor;
	}
}
