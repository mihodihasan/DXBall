package com.example.picci.piccidx;

/**
 * Created by picci on 9/4/16.
 */
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;

class GameView extends View{

    int level;
    //public static int LIFE;
    //public static boolean newLife;
    public static boolean GAMEOVER;
    //Paint paint;
    float brickX=0, brickY=0;
    int score=0;
    Canvas canvas;
    // Ball ball;
    //Bar bar;
    //float barWidth;
    //float ballSpeed;
    float barSpeed;
    boolean barMoveLeft;
    boolean leftPos,rightPos,first;
    public static int checkWidth=0;
    Paint paint;
    Bar bar;
    Ball ball;
    float xT;
    float x=0,y=0, radius=0;
    boolean firstTime=true;

    ArrayList<Bricks> bricks=new ArrayList<Bricks>();

    public GameView(Context context) {
        super(context);
        paint=new Paint();
        level = 1;
        //LIFE = 3;
        first = true;
        GAMEOVER = false;
        //newLife = true;
        //barMoveLeft = false;
        //barSpeed = 4;
        bar = new Bar();
        ball = new Ball();
        setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (ball.isBallAlive()) {
                    xT = event.getX();
                    if (xT < v.getWidth() / 2 && bar.getBarLeft() > 0) {
                        bar.setBarLeft(bar.getBarLeft() - 10);
                        xT = -10;
                    } else if (xT >= v.getWidth() / 2 && bar.getBarRight() < v.getWidth()) {
                        bar.setBarLeft(bar.getBarLeft() + 10);
                        xT = -10;
                    }
                }
                return true;
            }
        });
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (firstTime) {
            firstTime = false;
            bar.setBar(canvas);
            ball.setBall(canvas, bar);
        }
        canvas.drawRGB(255, 255, 255);
        paint.setColor(Color.BLACK);
        paint.setStyle(Paint.Style.FILL);
        ball.drawBall(canvas, paint);
        bar.drawBar(canvas, paint);
        ball.nextPos(canvas, bar);
        invalidate();
        this.canvas = canvas;
        int col;
        brickX = canvas.getWidth() / 7;
        brickY = (canvas.getHeight() / 15) * 2;
        super.onDraw(canvas);
        if (first) {
            first = false;
            if (level == 1) {
                for (int i = 0; i < 15; i++) {

                    if (brickX >= canvas.getWidth() - (canvas.getWidth() / 7) * 2) {
                        brickX = canvas.getWidth() / 7;
                        brickY += canvas.getHeight() / 15;
                    }
                    if (i % 2 == 0)
                        col = Color.CYAN;
                    else
                        col = Color.BLUE;
                    bricks.add(new Bricks(brickX, brickY, brickX + canvas.getWidth() / 7, brickY + canvas.getHeight() / 15, col));
                    brickX += canvas.getWidth() / 7;
                }
            }
            /*else if (level == 2) {
                for (int i = 0; i < 19; i++) {

                    if (brickX >= canvas.getWidth() - canvas.getWidth() / 7) {
                        brickX = 0;
                        brickY += (canvas.getHeight() / 15) * 2;
                    }
                    if (i % 2 == 0)
                        col = Color.GREEN;
                    else
                        col = Color.YELLOW;
                    bricks.add(new Bricks(brickX, brickY, brickX + canvas.getWidth() / 7, brickY + canvas.getHeight() / 15, col));
                    brickX += (canvas.getWidth() / 7) * 2;
                }
            } else if (level == 3) {
                for (int i = 0; i < 15; i++) {

                    if (brickX >= canvas.getWidth() - (canvas.getWidth() / 7) * 2) {
                        brickX = canvas.getWidth() / 7;
                        brickY += canvas.getHeight() / 15;
                    }
                    if (i % 2 == 0)
                        col = Color.GREEN;
                    else
                        col = Color.CYAN;
                    bricks.add(new Bricks(brickX, brickY, brickX + canvas.getWidth() / 7, brickY + canvas.getHeight() / 15, col));
                    brickX += canvas.getWidth() / 7;
                }

                brickX = 0;
                brickY = (canvas.getHeight() / 15) * 8;
                col = Color.RED;

                for (int i = 0; i < 14; i++) {
                    if (brickX >= canvas.getWidth() - canvas.getWidth() / 7) {
                        brickX = 0;
                        brickY += canvas.getHeight() / 15;
                    }
                    bricks.add(new Bricks(brickX, brickY, brickX + canvas.getWidth() / 7, brickY + canvas.getHeight() / 15, col));
                    brickX += canvas.getWidth() / 7;
                }
            }*/
            else
                GAMEOVER = true;
            Log.d("jjjj", "fff");
        }

        paint.setTextSize(30);
        paint.setFakeBoldText(true);
        canvas.drawText("Score: " + score, 20, 40, paint);

        paint.setTextSize(30);
        paint.setFakeBoldText(true);
        canvas.drawText("LEVEL " + level, canvas.getWidth() / 2 - 60, 40, paint);

        //new bar
        for(int i=0;i<bricks.size();i++){
            canvas.drawRect(bricks.get(i).getLeft(),bricks.get(i).getTop(),bricks.get(i).getRight(),bricks.get(i).getBottom(),bricks.get(i).getPaint());
        }
        if(GAMEOVER){
            paint.setColor(Color.RED);
            canvas.drawRect(0, 0, canvas.getWidth(), canvas.getHeight(), paint);

            paint.setColor(Color.RED);
            paint.setTextSize(50);
            paint.setFakeBoldText(true);
            canvas.drawText("GAME OVER",canvas.getWidth()/2-110,canvas.getHeight()/2,paint);
            canvas.drawText("FINAL SCORE: "+score,canvas.getWidth()/2-150,canvas.getHeight()/2+60,paint);
            GAMEOVER = false;
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            ((MainActivity)getContext()).finish();
        }
        this.ballBrickCollision(bricks, ball, canvas);
        //ball.ballBoundaryCheck(canvas);

    }

    public void ballBrickCollision(ArrayList<Bricks> br,Ball ball,Canvas canvas){

        for(int i=0;i<br.size();i++) {
            if (((ball.getY() - ball.getRadius()) <= br.get(i).getBottom()) && ((ball.getY() + ball.getRadius()) >= br.get(i).getTop()) && ((ball.getX()) >= br.get(i).getLeft()) && ((ball.getX()) <= br.get(i).getRight())) {
                br.remove(i);
                score+=1;
                ball.setyStep(-(ball.getyStep()));
            }
            else if(((ball.getY()) <= br.get(i).getBottom()) && ((ball.getY()) >= br.get(i).getTop()) && ((ball.getX() + ball.getRadius()) >= br.get(i).getLeft()) && ((ball.getX() - ball.getRadius()) <= br.get(i).getRight())) {
                br.remove(i);
                score+=1;
                ball.setxStep(-(ball.getxStep()));
            }

        }

    }
}