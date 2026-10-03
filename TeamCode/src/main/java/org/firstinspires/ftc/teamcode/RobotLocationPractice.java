package org.firstinspires.ftc.teamcode;

public class RobotLocationPractice {
    double angle;
    double X;
    double Y;

    public RobotLocationPractice(double angle){
        this.angle = angle;
    }

    public double getHeading(){
        double angle = this.angle;
        while(angle > 180){
            angle -= 360;
        }
        while(angle <= -180){
            angle += 360;
        }
        return angle;
    }

    public void turnRobot(double angleChange){
        angle += angleChange;
    }

    public void setAngle(double angle){
        this.angle = angle;
    }

    public double getAngle(){
        return angle;
    }

    public void changeX(double change){
        X += change;
    }
    public void setX(double X){
        this.X = X;
    }

    public double getX(){
        return this.X;
    }

    public void setY(double Y){
        this.Y = Y;
    }
    public void changeY(double change){
        Y += change;
    }

    public double getY(){
        return this.Y;
    }
}
