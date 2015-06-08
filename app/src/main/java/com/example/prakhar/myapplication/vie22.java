package com.example.prakhar.myapplication;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;

/**
 * Created by Prakhar on 6/4/2015.
 */
public class vie22 extends View {
    boolean yesSelected = false;
    boolean CheckPolygonCollision(Polygon poly1 , Polygon poly2)
    {
        Collision collision = new Collision();

        boolean detectColl= false;
        for(int i=0;i<poly1.points.size();i++)
        {


            Point p1,p2;
            p1 =  poly1.points.get((i) % poly1.points.size());

            p2 = poly1.points.get((i+1) % poly1.points.size());


            for(int j=0;j<poly2.points.size();j++)
            {
                Point q1,q2;
                q1 =  poly2.points.get((j) % poly2.points.size());

                q2 = poly2.points.get((j + 1) % poly2.points.size());


                detectColl = collision.CheckLineIntersction(p1, p2, q1, q2);

                Log.d("detectColl " , detectColl + " ");

if(detectColl)
    return true;
            }

        }


        return detectColl;
    }

    class Polygon
    {

int polyId=0;
        void setSELECTED()
        {
            for(Polygon pol : polygons)
            pol.SELECTED = false;

            SELECTED = true;
            local_selected = polyId;

            yesSelected = true;

        }
        void deselectAll()
        {
            for(Polygon pol : polygons)
                pol.SELECTED = false;

            yesSelected = false;
            local_selected = -1;

        }


        Point Centre;

        Point findCenter()
        {



            int minX=0,minY=0,maxX=0,maxY=0;


           minX = points.get(0).x;
            minY = points.get(0).y;

            maxX = points.get(0).x;
            maxY = points.get(0).y;

            for(Point p : points)
            {

                if( p.x < minX )
                    minX = p.x;

                if(p.y < minY)
                    minY = p.y;

                if( p.x > maxX )
                    maxX = p.x;

                if(p.y > maxY)
                    maxY = p.y;

            }


            int cntrX = (maxX+minX) / 2;
            int cntrY = (maxY+minY) / 2;
//cntrX += originX;
       //     cntrY += originY;
            Point centre = new Point(cntrX, cntrY);

this.Centre = centre ;
            return  centre;

        }

boolean SELECTED = false;



        ArrayList<Point> points;
        Polygon( ArrayList<Point> points,int Id)

        {
polyId = Id;

            this.points = new ArrayList<Point>(points);

Centre = findCenter();

Log.d("CENTRE IS " , Centre.x + " " + Centre.y);
            CNTR++;
    }


        void  translateXY(int bx, int by)
        {
            Point CentreBU = new Point(Centre);
           ArrayList<Point> backup=new ArrayList<Point>();

            for(Point p : points)
            {

                backup.add(new Point(p));




                p.x += bx;
                p.y += by;




            }


            Centre.x += bx;
            Centre.y += by;
            Collision colisn = new Collision();
            boolean collides = false;
            int collider = -1;

            for(Polygon all_polygon : polygons)
            {collider++;

if(this.hashCode()!=all_polygon.hashCode())
                    collides = CheckPolygonCollision(this, all_polygon);



                if(collides)
                {

                  Log.d("COOLIDES BITCH" ,"DAS " + collider);
                    break;

                }


            }



            if(collides)
            {



                points = new ArrayList<>(backup);
Centre =                CentreBU;
            }
            else {


                drawPolygons();
                invalidate();
            }
        }


float initial_rotataion = 0;
        void rotateBy(int x, int y,int X,int Y , int scX,int scY)
        {

float angle1 = (y-Centre.y)/(x-Centre.x);
            float angle2 = (-Centre.y)/(x-Centre.x);
int a = x- Centre.x;
            int b = y-Centre.y;
float degree = (float) ((b/a)*180/Math.PI);

degree -= initial_rotataion;

            initial_rotataion =degree;
            for(Point point : points)
            {






            int    newX = (int) (Centre.x + (point.x-Centre.x)*Math.cos(degree) - (point.y-Centre.y)*Math.sin(degree));

               int newY = (int) (Centre.y + (point.x-Centre.x)*Math.sin(degree) + (point.y-Centre.y)*Math.cos(degree));


                point.x = newX;
                point.y=newY;

            }

drawPolygons();
            invalidate();
        }

    }

    void init()
    {

        canvas = new Canvas(bmp);
        originX = 0;originY=0;
        int diff = 0;
        polygons = new ArrayList<Polygon>();
        for(int i=0;i<1;i++)
        {

            ArrayList<Point> points = new ArrayList<Point>();


            points.add(new Point(0,0));
            points.add(new Point(100,00));
            points.add(new Point(100,100));
            points.add(new Point( 150,  200));

            points.add(new Point( 0,  100));


            Polygon poly = new Polygon(points,i);
            poly.translateXY((int)(diff ), (int)(diff ));
            //   poly.rotateBy(4
            // 5);
            diff+=200;
            //         translateBy(200 ,200);


            polygons.add(poly);

        }

    }

    public vie22(Context context) {
        super(context);
     init();
    }

    public vie22(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public vie22(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }







    int X_down=0;
    int Y_down=0;


boolean SELECTED = false;


    private float scaleFactor = 1;


float scaleX=1;
    float scaleY=1;
    int local_selected = -1;
    @Override
    public boolean onTouchEvent(MotionEvent event) {



      int action =  event.getAction();
        boolean idSelected = false;
        switch (action)

        {

            case MotionEvent.ACTION_DOWN:



Log.d("locall", " "+ local_selected);
                X_down = (int) event.getX(0);
                Y_down = (int) event.getY(0);

                Solver solver = new Solver();
                int i = -1;

                for(Polygon poly : polygons ) {
                    i++;

                    Point point = new Point((int)((event.getX(0)/scaleX) - originX) ,(int)((event.getY(0)/scaleY) - originY) );

                    int[] px = new int[poly.points.size()];
                    int[] py = new int[poly.points.size()];
                    for(int j=0;j<poly.points.size(); j++)
                    {
                        px[j] = poly.points.get(j).x;
                        py[j] = poly.points.get(j).y;

                    }

                    Polygon2 polygn = new Polygon2(px , py ,poly.points.size());

                    boolean result =     polygn.contains(point.x , point.y);
                    Log.d( "Checked on2 poly "+ i , result + " " + poly.polyId  );

if(result) {



    poly.setSELECTED();

    idSelected =true;
     local_selected = i - 1;
    if(local_selected!=-1)
    {

        Log.d("alreadyselected ", " " + local_selected);
    }
    drawPolygons();
    break;

}
                else
{


}


                }


                idSelected =false;
           polygons.get(0).deselectAll();

                break;

            case MotionEvent.ACTION_MOVE:




                int selected =local_selected;
                int cnt = 0;







int x_local,y_local;

                x_local = (int) event.getX(0);
                y_local = (int) event.getY(0);







                if(yesSelected) {
                    Log.d("SELECTED ", selected + " " + SELECTED);

                   polygons.get(selected).translateXY((int) ((x_local - X_down) / scaleX), (int) ((y_local - Y_down) / scaleY));

         //           polygons.get(selected).rotateBy(x_local, y_local, X_down,Y_down,scaleX,scaleY);


                }
                else
                {
if(event.getPointerCount()>=1)
                    translateBy(x_local - X_down, y_local-Y_down);
                }

                X_down = (int) event.getX(0);
                Y_down = (int) event.getY(0);

                break;

            case MotionEvent.ACTION_UP:

                X_down =0;
                Y_down =0;


                drawPolygons();
                break;
            case MotionEvent.ACTION_CANCEL:

                X_down =0;
                Y_down =0;


                break;
        }

if(1==1)
    return  true;


























        Log.d("moved to ", (event.getX(0) - originX) + " " + (event.getY(0) - originY));


        Solver solver = new Solver();
int i = 0;

        for(Polygon poly : polygons ) {
            i++;

            Point point = new Point((int)(event.getX(0) - originX) ,(int)(event.getY(0) - originY) );

            int[] px = new int[poly.points.size()];
            int[] py = new int[poly.points.size()];
            for(int j=0;j<poly.points.size(); j++)
            {
                px[j] = poly.points.get(j).x;
                py[j] = poly.points.get(j).y;

            }

            Polygon2 polygn = new Polygon2(px , py ,poly.points.size());

        boolean result =     polygn.contains((int)( event.getX(0) - originX )  , (int)( event.getY(0) - originY )   );
Log.d( "Checked on poly "+ i , result + " " + point.x+ " " + point.y  );
        }

for(Polygon po : polygons)
{



    for(Polygon qo : polygons)
    {


        boolean result =  CheckPolygonCollision(po, qo);

      Log.d("checkcollision",result  + " ")  ;

    }

}
        return super.onTouchEvent(event);



    }
class Solver

{

    void CheckDetection(Point pnt,Polygon polygon)
    {
        int INF = 10000;

        int x = pnt.x;
        int y = pnt.y;

        int detection = 0;

        for(Point point : polygon.points)
        {
            int tX = point.x;
            int tY = point.y;







        }

    }

    boolean onSegment(Point p, Point q , Point r)
    {

        if (q.x <= Math.max(p.x, r.x) && q.x >=Math. min(p.x, r.x) &&
                q.y <=Math. max(p.y, r.y) && q.y >=Math. min(p.y, r.y))
            return true;
        return false;

    }
    // To find orientation of ordered triplet (p, q, r).
// The function returns following values
// 0 --> p, q and r are colinear
// 1 --> Clockwise
// 2 --> Counterclockwise
    int orientation(Point p, Point q, Point r)
    {
        int val = (q.y - p.y) * (r.x - q.x) -
                (q.x - p.x) * (r.y - q.y);

        if (val == 0) return 0;  // colinear
        return (val > 0)? 1: 2; // clock or counterclock wise
    }


    // The function that returns true if line segment 'p1q1'
// and 'p2q2' intersect.
    boolean doIntersect(Point p1, Point q1, Point p2, Point q2)
    {
        // Find the four orientations needed for general and
        // special cases
        int o1 = orientation(p1, q1, p2);
        int o2 = orientation(p1, q1, q2);
        int o3 = orientation(p2, q2, p1);
        int o4 = orientation(p2, q2, q1);

        // General case
        if (o1 != o2 && o3 != o4)
            return true;

        // Special Cases
        // p1, q1 and p2 are colinear and p2 lies on segment p1q1
        if (o1 == 0 && onSegment(p1, p2, q1)) return true;

        // p1, q1 and p2 are colinear and q2 lies on segment p1q1
        if (o2 == 0 && onSegment(p1, q2, q1)) return true;

        // p2, q2 and p1 are colinear and p1 lies on segment p2q2
        if (o3 == 0 && onSegment(p2, p1, q2)) return true;

        // p2, q2 and q1 are colinear and q1 lies on segment p2q2
        if (o4 == 0 && onSegment(p2, q1, q2)) return true;

        return false; // Doesn't fall in any of the above cases
    }

// Returns true if the point p lies inside the polygon[] with n vertices


    // Returns true if the point p lies inside the polygon[] with n vertices
    boolean isInside(ArrayList<Point>  polygon ,Point p)
    {


        int n = polygon.size();
        // There must be at least 3 vertices in polygon[]
        if (n < 3)  return false;
int INF = 10000;
        // Create a point for line segment from p to infinite

        Point extreme =new Point(INF, p.y);


        // Count intersections of the above line with sides of polygon
        int count = 0, i = 0;
        do
        {
            int next = (i+1)%n;


            Log.d("checkexc" , p.x + " " + p.y + " " + polygon.get(i).x+ " " + polygon.get(i).y);

            // Check if the line segment from 'p' to 'extreme' intersects
            // with the line segment from 'polygon[i]' to 'polygon[next]'
            if (doIntersect(polygon.get(i),polygon.get(next), p, extreme))
            {
                // If the point 'p' is colinear with line segment 'i-next',
                // then check if it lies on segment. If it lies, return true,
                // otherwise false




                if (orientation(polygon.get(i), p,polygon.get(i)) == 0)
                    return onSegment(polygon.get(i), p,polygon.get(next));

                count++;
            }
            i = next;
        } while (i != 0);

        // Return true if count is odd, false otherwise

        int result = count%2;

        if(result==0)
            return false;
        else
        return true;  // Same as (count%2 == 1)
    }

}







    int originX=0;
    int originY=0;
    ArrayList<Polygon> polygons;
Canvas canvas=new Canvas();
    Bitmap.Config conf = Bitmap.Config.ARGB_8888; // see other conf types
    Bitmap bmp = Bitmap.createBitmap(MainActivity.WIDTH,MainActivity.HEIGHT, conf);


    @Override
    protected void onDraw(Canvas C) {
        super.onDraw(C);










Paint p = new Paint();

        drawPolygons();

C.drawBitmap(bmp,0,0,p);





    }


  public  void scaleBy(float x , float y , float a , float b)
    {

        scaleY = 1;scaleX=1;




invalidate();

    }




void translateBy(int x ,int y)
{



    Log.d("current scale ", getScaleX() + " ");

    canvas.translate(x, y);







    originX += x;
    originY += y;

invalidate(   );

}

  void  drawPolygons()
    {

        canvas.save();

canvas.drawColor(Color.WHITE);










        Paint paint = new Paint();



        for(Polygon poly : polygons)
        {




            paint.setStyle(Paint.Style.FILL);
            paint.setColor(Color.parseColor("#ffffff"));
            Path   path  = new Path();
            int x= 0;
            for(Point p : poly.points)
            {

                if(x==0)
                {
                    path.moveTo(p.x , p.y);
                    Log.d("DAS", "DAS move"  + p.x);
                    x=1;
                }
                else
                {
                    Log.d("DAS", "DAS"  + p.x);
                    path.lineTo(p.x, p.y);

                }


            }
            path.close();
            canvas.drawPath(path, paint);

if(poly.SELECTED) {
    Log.d("FASFAS","gdsg");
    paint.setColor(Color.parseColor("#FFBAFF3B"));
}
            else

    paint.setColor(Color.parseColor("#000000"));


            paint.setStyle(Paint.Style.STROKE);
            paint.setStrokeWidth(10);
            canvas.drawPath(path, paint);

            paint.setColor(Color.parseColor("#000000"));
            canvas.drawCircle(poly.Centre.x, poly.Centre.y, 10, paint);


        }






        paint.setColor(Color.parseColor("#000000"));
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(4);



      int   minX=MminX;
        int minY=MminX;
        int maxX=MminX;
        int maxY=MminX;




        boolean chkr = false;

if(polygons.size()>0)
{



    minX=polygons.get(0).points.get(0).x;
     minY=polygons.get(0).points.get(0).y;
     maxX=polygons.get(0).points.get(0).x;
     maxY=polygons.get(0).points.get(0).y;
    chkr = true;
}





        for(Polygon poli : polygons)
        {
ArrayList<Point> points =poli.points;





            for(Point p : points)
            {
                chkr = true;

                if( p.x < minX )
                    minX = p.x;

                if(p.y < minY)
                    minY = p.y;

                if( p.x > maxX )
                    maxX = p.x;

                if(p.y > maxY)
                    maxY = p.y;





            }

        }



        if(chkr) {

            MminX = minX;
            MminY=minX;
            MmaxX = minY;
            MmaxY = minY;


            for(Polygon poli : polygons) {
                ArrayList<Point> points = poli.points;


                for (Point p : points) {

                    {

                        paint.setTextSize(30);
                        canvas.drawText("Size", p.x, maxY + 90, paint);
                        canvas.drawText("Size", p.x,  minY-90+30, paint);


                    }

                }
            }


            Log.d("boundary", " " + minX + " " + minY);
           canvas.drawRect(new Rect(minX - 60, minY - 60, maxX + 60, maxY + 60), paint);
MminX = minX;
            MminY = minY;
            MmaxX = maxX;
            MmaxY = maxY;


           // canvas.rotate((float) (90), 50, 50);
          //  paint.setTextSize(36);
        //    canvas.drawText("PRAKHARPRAKHARPRAKHARPRAKHARPRAKHARPRAKHAR", 650, 350, paint);


//            canvas.save();
//
//            float py = this.getHeight()/2.0f;
//            float px = this.getWidth()/2.0f;
//            Log.d("testUD", String.format("w: %d h: %d ", this.getWidth(), this.getHeight()));
//            Log.d("testUD", String.format("w: %f h: %f ", py, px));
//
//         canvas.drawText("PRAKHAR" ,30,30, paint);
//            canvas.rotate(180, px, py);




        }





    }

    int MminX=0;
    int MminY=0;
    int MmaxX=0;
    int MmaxY=0;
boolean iAmScaling = false;
int CNTR = 0;
  public  void addPolygon()
    {

        ArrayList<Point> poi = new ArrayList<Point>();

      poi.add(new Point(MminX,MmaxY));
        poi.add(new Point(MminX+300, MmaxY));
        poi.add(new Point(MminX +300,MmaxY+300));

        poi.add(new Point(MminX ,MmaxY+300));



        polygons.add(new Polygon(poi,CNTR));
        drawPolygons();
        invalidate();

    }

}
