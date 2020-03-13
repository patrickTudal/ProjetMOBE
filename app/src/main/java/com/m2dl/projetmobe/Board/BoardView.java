package com.m2dl.projetmobe.Board;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.hardware.SensorManager;
import android.os.Handler;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.m2dl.projetmobe.Enum.DirectionEnum;
import com.m2dl.projetmobe.Snake.Node;
import com.m2dl.projetmobe.Snake.Snake;

import java.util.LinkedList;
import java.util.Random;

@SuppressLint("DrawAllocation")
public class BoardView extends View {
	public static final int heightNum = 40;
	public static final int widthNum = 20;
	private static final int speedNum = 400;

	public interface GameOverListener {
		void onGameOver();
	}

	private GameOverListener gameOverListener;


	private static final int TIMER_APPLE = 30;
	private int counterApple;

	private Snake snake;
	private Node apple;

	private Paint paint;
	private Handler customHandler;
	private Handler appleHandler;

	public long score = 0L;

	private int width;
	private int height;
	
	public int speed;

	public DirectionEnum directionEnum;
 	private Node[][] board;
	//Collection<Node> board = new LinkedHashSet<Node>();
	boolean boardCreated = false;

	private SensorManager sensorManager;

    public BoardView(Context context, AttributeSet attrs) {
        super(context, attrs);
        counterApple = 0;
        customHandler = new Handler();
        appleHandler = new Handler();
        paint = new Paint();
        directionEnum = DirectionEnum.RIGHT;
        board = new Node[widthNum][heightNum];
    }

	private Runnable updateTimerThread = new Runnable() {
		public void run() {
			snake.move(directionEnum);
			invalidate();
		}
	};

    public void setGameOverListener(GameOverListener listener) {
        this.gameOverListener = listener;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (snake != null && (snake.isNodeOnBody(snake.getHead()) || hasTouchedWall() )) {
            gameOverListener.onGameOver();
        } else {
            if (snake != null && apple != null && apple.equals(snake.getHead())) {
                Log.i("Apple", "Apple eaten by the snake");
                snake.increaseSize(directionEnum);
                apple = null;
            }
            initGame(canvas);
			createApple();
            printSnake(canvas);
            printscore(canvas);
            printApple(canvas);
            printWalls(canvas);
            postDeleyed();
        }
    }

    private void postDeleyed() {
        customHandler.postDelayed(updateTimerThread, speed);
    }

    private void initGame(Canvas canvas) {
        if (!boardCreated) {
            initBoard(canvas);
            initSnake(widthNum, heightNum);
            boardCreated = true;
        }
    }

    private void printscore(Canvas canvas) {
        paint.setAntiAlias(true);
        paint.setTextSize(20);
        paint.setColor(Color.RED);
        canvas.drawText("Score :" + (score++), 15, 15, paint);
    }

    private void printSnake(Canvas canvas) {
		/*Log.i("Snake", "Snake size");
		Log.i("Snake", Integer.toString(snake.getBody().size()));*/

//		for (Node node : snake.getBody()) {
//			Log.w("Snake", node.toString());
//		}

        try {
            paint.setStrokeWidth(10);


            for (Node node : snake.getBody()) {
                paint.setColor(node.getColor());
                if (node.equals(snake.getHead())){
                    snake.getHead().setColor(Color.BLACK);
                }
                else if (node.equals(snake.getTail())){
                    snake.getTail().setColor(Color.BLUE);
                }
                else {
                    node.setColor(Color.GREEN);
                }
                Node nodeBoard = board[node.getRow()][node.getColumn()];
                if (nodeBoard != null) {
                    Rect rect = nodeBoard.getRect();
                    canvas.drawRect(rect, paint);
                }

//				if (board.contains(node)) {
//					Iterator<Node> iterator = board.iterator();
//					while (iterator.hasNext()) {
//						Node next = iterator.next();
//						if (node.equals(next)) {
//							canvas.drawRect(next.getRect(), paint);
//						}
//					}
//				}
            }


        } catch (Exception e) {
            // TODO: handle exception
            Log.e("erro:", e.getMessage());
        }
    }

    private void printApple(Canvas canvas) {
        if (apple != null) {
            paint.setColor(Color.RED);
            paint.setStrokeWidth(10);
            canvas.drawRect(apple.getRect(), paint);
        }
    }

    private void initBoard(Canvas canvas) {

        speed = speedNum;

        int endWidth = (canvas.getWidth());
        int endHeight = (canvas.getHeight());

        width = (canvas.getWidth() / widthNum);
        height = (canvas.getHeight() / heightNum);

        int leftX = 5;
        int topY = 5;
        int rightX = width;
        int bottomY = height;

        boolean conditionX = true;
        boolean conditionY = true;

        int x = 0;
        int y = 0;
        while (conditionY) {

            while (conditionX) {
                Rect rect = new Rect(leftX, topY, rightX, bottomY);
                Node node = new Node(x, y, rect);
                board[x][y] = node;

//				board.add(node);

                leftX = leftX + width + 1;
                rightX = rightX + width + 1;
                conditionX = (leftX < (endWidth - width));
                x++;
            }
            x = 0;
            y++;

            leftX = 5;
            rightX = width;
            conditionX = true;

            topY = topY + height + 1;
            bottomY = bottomY + height + 1;

            conditionY = (topY < (endHeight - height));
        }
    }

    private void initSnake(int x, int y) {
        snake = new Snake(x, y);
        LinkedList<Node> body = new LinkedList<Node>();

        for (int i = 10; i < 15; i++) {
            body.add(new Node(8, i, null));
        }
        snake.setBody(body);
    }

    private void createApple() {
        Log.i("Apple", "Apple creation");
        if(apple==null) {
			Random random = new Random();
			apple = new Node(random.nextInt(widthNum - 3) , random.nextInt(heightNum - 3) , null);
			while (snake.isNodeOnBody(apple) || isWall(apple)) {
				apple = new Node(random.nextInt(widthNum - 3), random.nextInt(heightNum - 3), null);
			}
			Log.i("Apple", "Apple created at " + apple.getColumn() + " " + apple.getRow());
			int appleRow = width * apple.getRow();
			int appleHeight = height * apple.getColumn();
			apple.setRect(new Rect(appleRow, appleHeight, appleRow + width, appleHeight + height));
		}
    }

    public void printWalls(Canvas canvas) {
        for (int row = 0; row < widthNum - 1; row++) {
            for (int col = 0; col < heightNum - 1; col++) {
                Node nodeBoard = board[row][col];
                if (nodeBoard != null && isWall(nodeBoard)) {
                    paint.setColor(Color.BLACK);
                    Rect rect = nodeBoard.getRect();
                    canvas.drawRect(rect, paint);
                }
            }
        }

    }

    private boolean isWall(Node node) {
        return (node.getColumn() == 0 || node.getColumn() == heightNum - 2 ||
                node.getRow() == 0 || node.getRow() == widthNum - 2);
    }

    private boolean hasTouchedWall() {
        return isWall(snake.getHead());
    }


}