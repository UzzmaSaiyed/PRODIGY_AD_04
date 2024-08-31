package com.example.tictactoe;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;

public class TicTacToeBoard extends View {
    private final int boardColor;
    private final int XColor;
    private final int OColor;
    private final int WinningLineColor;


    private boolean winningline = false;
    private int cellsize  =getWidth()/3;

    private final Paint paint = new Paint();

    private final GameLogic game;

    public TicTacToeBoard(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        game = new GameLogic();

        TypedArray a = context.getTheme().obtainStyledAttributes(attrs,R.styleable.TicTacToeBoard,0,0);

        try {
             boardColor = a.getInteger(R.styleable.TicTacToeBoard_boardColor,0);
             XColor = a.getInteger(R.styleable.TicTacToeBoard_XColor,0);
             OColor = a.getInteger(R.styleable.TicTacToeBoard_OColor,0);
             WinningLineColor = a.getInteger(R.styleable.TicTacToeBoard_WinningLineColor,0);

        }finally {
            a.recycle();
        }
    }

    @Override
    protected void onMeasure(int width,int height)
    {
        super.onMeasure(width,height);
        int dimension = Math.min(getMeasuredWidth(),getMeasuredHeight());
        cellsize = dimension/3;
        setMeasuredDimension(dimension, dimension);
    }

    @Override
    protected void onDraw(Canvas canvas)
    {
        paint.setStyle(Paint.Style.STROKE);
        paint.setAntiAlias(true);
        drawGameBoard(canvas);
//        drawX(canvas,1,1);
//        drawO(canvas,2,2);

        drawMarkers(canvas);
        if(winningline)
        {
            paint.setColor(WinningLineColor);
            drawWinningLine(canvas);
        }



    }

    @Override
    public boolean onTouchEvent(MotionEvent event)
    {
        float x = event.getX();
        float y = event.getY();

        int action = event.getAction();
        if(action == MotionEvent.ACTION_DOWN)
        {
            int row = (int) Math.ceil(y/cellsize);
            int col = (int) Math.ceil(x/cellsize);

            if(!winningline)
            {
                if(game.updateGameBoard(row,col))
                {
                    invalidate();
                    if(game.winnerCheck() && (game.tie==false))
                    {
                        winningline = true;
                        invalidate();
                    }

                    if(game.getPlayer()%2 ==0)
                    {
                        game.setPlayer(game.getPlayer()-1);
                    }
                    else
                    {
                        game.setPlayer(game.getPlayer()+1);
                    }
                }
            }


            invalidate();
            return true;
        }
        return false;
    }

    private void drawGameBoard(Canvas canvas )
    {
        paint.setColor(boardColor);
        paint.setStrokeWidth(16);
        for (int c=1;c<3;c++)
        {
            canvas.drawLine(cellsize*c,0,cellsize*c,canvas.getWidth(),paint);
        }

        for (int r=1;r<3;r++)
        {
            canvas.drawLine(0,cellsize*r,canvas.getWidth(),cellsize*r,paint);

        }
    }

    private void drawMarkers(Canvas canvas)
    {
        for(int r=0;r<3;r++)
        {
            for(int c=0;c<3;c++)
            {
                if(game.getGameBoard()[r][c]!=0)
                {
                    if(game.getGameBoard()[r][c] == 1)
                    {
                        drawX(canvas,r,c);
                    }
                    else
                    {
                        drawO(canvas,r,c);
                    }
                }
            }
        }

    }

    private void drawX(Canvas canvas,int row, int col)
    {
        paint.setColor(XColor);

        canvas.drawLine((float) ((col+1)*cellsize-cellsize*0.2),(float)(row*cellsize+cellsize*0.2),(float)(col*cellsize+cellsize*0.2),(float)((row+1)*cellsize-cellsize*0.2),paint);
        canvas.drawLine((float)(col*cellsize+cellsize*0.2),(float)(row*cellsize+cellsize*0.2),(float)((col+1)*cellsize-cellsize*0.2),(float)((row+1)*cellsize-cellsize*0.2),paint);

    }

    private void drawO(Canvas canvas,int row, int col)
    {
        paint.setColor(OColor);
        canvas.drawOval((float)(col*cellsize+cellsize*0.2),(float)(row*cellsize+cellsize*0.2),(float)(((col*cellsize+cellsize)-cellsize*0.2)),(float)(((row*cellsize+cellsize)-cellsize*0.2)),paint);
    }

    private void drawHorizontalLine(Canvas canvas,int row,int col)
    {
        canvas.drawLine(col,row*cellsize+ (float) cellsize /2,cellsize*3,row*cellsize + (float) cellsize /2,paint);
    }

    private void drawVerticalLine(Canvas canvas,int row,int col)
    {
        canvas.drawLine(col*cellsize+ (float) cellsize /2,row,col*cellsize+ (float) cellsize /2,cellsize*3,paint);
    }

    private void drawDiagonalLinePositive(Canvas canvas)
    {
        canvas.drawLine(0,cellsize*3,cellsize*3,0,paint);
    }

    private void drawDiagonalLineNegative(Canvas canvas)
    {
        canvas.drawLine(0,0,cellsize*3,cellsize*3,paint);
    }

    private void drawWinningLine(Canvas canvas)
    {
        int row = game.getWinType()[0];
        int col = game.getWinType()[1];

        switch (game.getWinType()[2])
        {
            case 1:
                drawHorizontalLine(canvas,row,col);
                break;
            case 2:
                drawVerticalLine(canvas,row,col);
                break;
            case 3:
                drawDiagonalLineNegative(canvas);
                break;
            case 4:
                drawDiagonalLinePositive(canvas);
                break;
        }
    }

    public void setUpGame(Button playAgain, Button home, TextView playerTurn, String[] name)
    {
        game.setPlayAgain(playAgain);
        game.setHome(home);
        game.setPlayerTurn(playerTurn);
        game.setPlayername(name);

    }

    public void resetGame()
    {
        game.resetGame();
        winningline = false;
    }




}

