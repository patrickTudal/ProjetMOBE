package com.m2dl.projetmobe.Snake;

import com.m2dl.projetmobe.Enum.DirectionEnum;

import java.util.LinkedList;

public class Snake {

	private LinkedList<Node> body;
	private int endRow;
	private int endColumn;
	private DirectionEnum directionEnum;
	
	public Snake(int maxRows, int maxColumns) {
		this.endRow = maxRows;
		this.endColumn = maxColumns;
		this.setBody(new LinkedList<Node>());
		this.directionEnum = DirectionEnum.RIGHT;
		
		
	}

	public void move(DirectionEnum directionEnum) {

		if (!validadeDirection(directionEnum)) {
			return;
		}

		Node head = getBody().getLast();

		for (int i = 0; i < body.size() - 1; i++) {
			Node nexNode = body.get(i + 1);
			Node node = body.get(i);
			node.setRow(nexNode.getRow());
			node.setColumn(nexNode.getColumn());
		}
		
		moveHead(directionEnum, head);
		
		this.directionEnum = directionEnum;
	}
	
	public boolean verify(int row, int column) {
		for (Node node : this.getBody()) {
			if (node.getRow() == row && node.getColumn() == column) {
				return true;
			}
		}
		return false;
	}
	
	private void moveHead(DirectionEnum directionEnum, Node node) {
		if (directionEnum.equals(DirectionEnum.LEFT)) {

			if (node.getColumn() == 0) {
				node.setColumn(endColumn);
			} else {
				node.setColumn(node.getColumn() - 1);
			}

		} else if (directionEnum.equals(DirectionEnum.RIGHT)) {

			if (node.getColumn() == endColumn) {
				node.setColumn(0);
			} else {
				node.setColumn(node.getColumn() + 1);
			}

		} else if (directionEnum.equals(DirectionEnum.UP)) {

			if (node.getRow() == 0) {
				node.setRow(endRow - 1);
			} else {
				node.setRow(node.getRow() - 1);
			}

		} else if (directionEnum.equals(DirectionEnum.DOWN)) {

			if (node.getRow() == endRow) {
				node.setRow(0);
			} else {
				node.setRow(node.getRow() + 1);
			}

		}
	}

	private boolean validadeDirection(DirectionEnum directionEnum) {
		if (directionEnum.equals(DirectionEnum.LEFT)
				&& this.directionEnum.equals(DirectionEnum.RIGHT)) {
			return false;
		}
		if (directionEnum.equals(DirectionEnum.RIGHT)
				&& this.directionEnum.equals(DirectionEnum.LEFT)) {
			return false;
		}
		if (directionEnum.equals(DirectionEnum.UP)
				&& this.directionEnum.equals(DirectionEnum.DOWN)) {
			return false;
		}
		if (directionEnum.equals(DirectionEnum.DOWN)
				&& this.directionEnum.equals(DirectionEnum.LEFT)) {
			return false;
		}
		return true;
	}

	public LinkedList<Node> getBody() {
		return body;
	}

	public void setBody(LinkedList<Node> body) {
		this.body = body;
	}

}
