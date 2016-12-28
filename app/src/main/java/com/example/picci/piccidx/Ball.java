package com.example.picci.piccidx;

/**
 * Created by picci on 9/4/16.
 */
import android.graphics.Canvas;
import android.graphics.Paint;

public class Ball {
    private boolean ballAlive = true;


    public boolean isBallAlive() {
        return ballAlive;
    }

    public void setBallAlive(boolean ballAlive) {
        this.ballAlive = ballAlive;
    }

    private float x,y,radius=30;

    private float xStep = 10;
    private float yStep = -10;

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

    public float getX() {

        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getRadius() {

        return radius;
    }
    public float getxStep() {
        return xStep;
    }

    public float getyStep() {
        return yStep;
    }


    public void setxStep(float xStep) {
        this.xStep = xStep;
    }

    public void setyStep(float yStep) {
        this.yStep = yStep;
    }

    public void setRadius(float radius) {
        this.radius = radius;
    }

    public void setBall(Canvas canvas,Bar bar) {
        float barMid = (bar.getBarRight()-bar.getBarLeft())/2;
        x = bar.getBarLeft()+barMid;
        y = bar.getBarTop()-radius;
    }

    public void drawBall(Canvas canvas, Paint paint){
        canvas.drawCircle(x,y,radius,paint);
    }

    public void nextPos(Canvas canvas, Bar bar) {
        if (x + radius >= canvas.getWidth() || (x + radius == bar.getBarLeft() && y >= bar.getBarTop() && y <= bar.getBarBottom()) && xStep > 0) {
            //x incrasing
            xStep = -xStep;
        } else if (y <= radius) {
            // y decreasing
            yStep = -yStep;
        } else if (x <= radius) {
            //x dereceasing
            xStep = -xStep;
        } else if (y + radius > bar.getBarTop() && x > bar.getBarLeft() && x < bar.getBarRight()) {
            // y increasing
            yStep = -yStep;
        } /*else if (y > bar.getBarBottom() && y <= canvas.getHeight()) {
            xStep = 0;
            yStep = 0;
            ballAlive = false;
        }*/
        x += xStep;
        y += yStep;
    }

}

