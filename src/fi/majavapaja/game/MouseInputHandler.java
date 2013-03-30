package fi.majavapaja.game;

import java.awt.Point;
import java.awt.event.*;

public class MouseInputHandler implements MouseListener, MouseMotionListener, MouseWheelListener {

	private static MouseInputHandler input = new MouseInputHandler();

	public static MouseInputHandler getInstance() {
		return input;
	}

	public boolean mouseOne;
	public boolean mouseTwo;
	public boolean mouseThree;

	private Point point = new Point(0, 0);

	@Override
	public void mousePressed(MouseEvent e) {
		if (e.getButton() == MouseEvent.BUTTON1) mouseOne = true;
		if (e.getButton() == MouseEvent.BUTTON2) mouseThree = true;
		if (e.getButton() == MouseEvent.BUTTON3) mouseTwo = true;
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		if (e.getButton() == MouseEvent.BUTTON1) mouseOne = false;
		if (e.getButton() == MouseEvent.BUTTON2) mouseThree = false;
		if (e.getButton() == MouseEvent.BUTTON3) mouseTwo = false;
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		point = e.getPoint();
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		point = e.getPoint();
	}

	public Point getPoint() {
		return point;
	}

	public int wheelMovement;

	@Override
	public void mouseWheelMoved(MouseWheelEvent e) {
		wheelMovement = e.getWheelRotation();
	}

	@Override
	public void mouseEntered(MouseEvent e) {}

	@Override
	public void mouseExited(MouseEvent e) {}

	@Override
	public void mouseClicked(MouseEvent e) {}

}
