package com.example.prakhar.myapplication;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.EmbossMaskFilter;
import android.graphics.MaskFilter;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.Region;
import android.graphics.drawable.ShapeDrawable;
import android.os.Handler;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;

import android.view.View;

import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * Created by Prakhar on 6/4/2015.
 */
public class vie extends View {
    void deselectAll()
    {
        for(Polygon pol : polygons)
            pol.SELECTED = false;

        yesSelected = false;
        local_selected = -1;

    }
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
    boolean CheckPolygonCollisionIgnoreVerte(Polygon poly1 , Polygon poly2,int VERTY , Point POINTY)
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

        int VERTEX = -1;

        boolean checkVertexCollision(Point point)
        {
int u = 0;
            for(Point all : points)
            {
               float delX =Math.abs( point.x - all.x);
                float delY = Math.abs(point.y - all.y);


              if(delX<50/scaleX)
              {
                  if(delY<50/scaleY)
                  {
                      Log.d("vertex " + u + " polygon" + polyId , "" + point.x + " " + all.x + " "+point.y +" "+ all.y);
                      VERTEX = u;
                      return true ;
                  }
              }
u++;
            }

            VERTEX = -1;
            return false ;
        }

int polyId=0;
        void setSELECTED()
        {
            for(Polygon pol : polygons)
            pol.SELECTED = false;

            SELECTED = true;
            local_selected = polyId;

            yesSelected = true;

        }



        Point Centre;

        public Point centroid()  {
            double centroidX = 0, centroidY = 0;

            for(Point knot : points) {
                centroidX += knot.x;
                centroidY += knot.y;
            }



           Point CN =  new Point((int)(centroidX / points.size()),(int)( centroidY / points.size()));
         //   Centre.x = CN.x;
         //   Centre.y = CN.y;
            return  CN;

        }

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

        ArrayList<Point> backup_points;

        Polygon( ArrayList<Point> points,int Id)

        {
polyId = Id;

            this.points = new ArrayList<Point>(points);
            backup_points = new ArrayList<Point>(points);


Centre = findCenter();

Log.d("CENTRE IS " , Centre.x + " " + Centre.y);
            CNTR++;
    }

        void  translateXYPoint(int bx, int by)
        {
            Point CentreBU = new Point(Centre);
            Point backup=new Point();

            Point p = points.get(VERTEX);
            {

                backup=new Point(p);




                p.x = bx;
                p.y = by;




            }


            Centre =    findCenter();
            Collision colisn = new Collision();
            boolean collides = false;
            int collider = -1;

            for(Polygon all_polygon : polygons)
            {
                collider++;

                if(this.hashCode()!=all_polygon.hashCode())
                    collides = CheckPolygonCollision(this, all_polygon);



                if(collides)
                {

                    Log.d("COOLIDES BITCH" ,"DAS " + collider);
                    break;

                }


            }

            Point lp = points.get(VERTEX);
            if(!collides)

            {

                Collision clsn = new Collision();
                int a ;
                a = Math.abs(VERTEX%points.size());

                Point pp = points.get(a);

                ArrayList<Point>  rem = new ArrayList<Point>();
                for(int i=1;i<points.size();i++)
                {
                    int b =Math.abs((i+a)%points.size());
                    rem.add(points.get(b));
                }

                for(int i=0;i<rem.size()-1;i++)
                {
                    Point p1 = rem.get(i);
                    Point p2 = rem.get(i+1);


                    //    Log.d("COLLIS VERT" , " "+Math.sqrt(( Math.pow(pp.x-p1.x,2) +Math.pow(p2.y-pp.y,2)  ))+ " " + (Math.sqrt(( Math.pow(p2.x-p1.x,2) +Math.pow(p2.y-p1.y,2)  ))) );
                    if(Math.sqrt(( Math.pow(pp.x-p1.x,2) +Math.pow(pp.y-p1.y,2)  )) +(Math.sqrt(( Math.pow(pp.x-p2.x,2) +Math.pow(pp.y-p2.y,2)  ))) == (Math.sqrt(( Math.pow(p2.x-p1.x,2) +Math.pow(p2.y-p1.y,2)  ))))
                    {
                        Log.d("COLLIS VERT" , " "+pp.x );
//collides = true;
                        break;

                    }



                }


            }



            if(collides)
            {



                points.set(VERTEX , backup);
                Centre =                CentreBU;
            }
            else {


//                drawPolygons();
                invalidate();
            }
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

            backup_points = new ArrayList<Point>(points);


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
                backup_points = new ArrayList<>(backup);

Centre =                CentreBU;
            }
            else {


//                drawPolygons();
                invalidate();
            }
        }


        double initial_rotataion = 0;


        void rotateBy(double degree)
        {

            ArrayList<Point> backup = (ArrayList<Point>) (points).clone();


ArrayList<Point> lop = new ArrayList<Point>();

            for(Point point : backup_points)
            {



Log.d("WHile Rotating", " " + Centre.x + " " + Centre.y + " " + (initial_rotataion*180/Math.PI));

//lop.add(point);
                int    newX = (int) (Centre.x + (point.x-Centre.x)*Math.cos(initial_rotataion) - (point.y-Centre.y)*Math.sin(initial_rotataion));

                int newY = (int) (Centre.y + (point.x-Centre.x)*Math.sin(initial_rotataion) + (point.y-Centre.y)*Math.cos(initial_rotataion));

                lop.add(new Point(newX,newY));
           //     point.x = newX;
           //     point.y=newY;

            }

            Collision colisn = new Collision();
            boolean collides = false;
            int collider = -1;

            for(Polygon all_polygon : polygons)
            {collider++;

                if(this.hashCode()!=all_polygon.hashCode())
                    collides = CheckPolygonCollision(this, all_polygon);



                if(collides)
                {


                    break;

                }


            }



            if(collides)
            {


                Log.d("COOLIDES BITCH" ,"DAS " + collider);

           //     this.points = new ArrayList<>(backup_points);

            //    this.backup_points = new ArrayList<>(backup_points);


                invalidate();

                //Centre =                CentreBU;
            }
            else {
                initial_rotataion+= degree;
points = lop;
//                drawPolygons();
                invalidate();
            }


//drawPolygons();
           // invalidate();
        }

        void rotateBy(int x, int y,int X,int Y , int scX,int scY)
        {

float angle1 = (y-Centre.y)/(x-Centre.x);
            float angle2 = (-Centre.y)/(x-Centre.x);
int a = x- Centre.x;
            int b = y-Centre.y;
float degree = (float) ((b/a)*180/Math.PI/4);

degree -= initial_rotataion;

            initial_rotataion =degree;
            for(Point point : points)
            {






            int    newX = (int) (Centre.x + (point.x-Centre.x)*Math.cos(degree) - (point.y-Centre.y)*Math.sin(degree));

               int newY = (int) (Centre.y + (point.x-Centre.x)*Math.sin(degree) + (point.y-Centre.y)*Math.cos(degree));


                point.x = newX;
                point.y=newY;

            }

//drawPolygons();
            invalidate();
        }

    }
MainActivity activity;
    void init(Context con)
    {

        canvas = new Canvas(bmp);
        originX = 0;originY=0;
        int diff = 0;
        polygons = new ArrayList<Polygon>();
        for(int i=0;i<1;i++)
        {

            ArrayList<Point> points = new ArrayList<Point>();


            points.add(new Point(MminX,MmaxY));
            points.add(new Point(MminX+600, MmaxY));
            points.add(new Point(MminX +600,MmaxY+600));

            points.add(new Point(MminX ,MmaxY+600));



    //        polygons.add(new Polygon(points,CNTR));

       //     polygons.get(0).deselectAll();


            Polygon poly = new Polygon(points,i);
            poly.translateXY((int)(diff ), (int)(diff ));
            //   poly.rotateBy(4
            // 5);
            diff+=200;
            //         translateBy(200 ,200);


            polygons.add(poly);

          //  addPolygon();

        }
activity = (MainActivity) con;

        paint.setFlags(Paint.ANTI_ALIAS_FLAG);
        p.setFlags(Paint.ANTI_ALIAS_FLAG);



      //  MaskFilter filter = new EmbossMaskFilter(new float[]{0f, -1.0f, 0.5f}, 0.8f, 15f, 1f);
      //  paint.setMaskFilter(filter);
      //  p.setMaskFilter(filter);
    }
Context con;
    public vie(Context context) {
        super(context);
     init(context);
    }

    public vie(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public vie(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }







    int X_down=0;
    int Y_down=0;


boolean SELECTED = false;


    private float scaleFactor = 1;


float scaleX=1;
    float scaleY=1;
    int local_selected = -1;

    boolean tupac = true;
int tupacLoc = 0;
    @Override
    public boolean onTouchEvent(MotionEvent event) {

if(iAmScaling)
    return true;

      int action =  event.getAction();
        boolean idSelected = false;
        switch (action)

        {

            case MotionEvent.ACTION_DOWN:
                tupac  = true;

                Point point = new Point((int)((event.getX(0)/scaleX) - originX) ,(int)((event.getY(0)/scaleY) - originY) );
                Log.d("fukra " , "" + ((int)((event.getX(0)/scaleX) - originX)) +" " +((int)(event.getY(0)/scaleY) - originY));

                if(local_selected!=-1)
{
    Log.d("locall", " "+ local_selected);
//    Point point = new Point((int)((event.getX(0)/scaleX) - originX) ,(int)((event.getY(0)/scaleY) - originY) );

    Polygon pou = polygons.get(local_selected);
    if(pou.checkVertexCollision(point))
    tupac  = false;
    tupacLoc = local_selected;


}


                X_down = (int) event.getX(0);
                Y_down = (int) event.getY(0);


//                Point point = new Point((int)((event.getX(0)/scaleX) - originX) ,(int)((event.getY(0)/scaleY) - originY) );
boolean fook = false;

                boolean result2 = false;

                for(Polygon poly : polygons ) {

                    {
                        Point point2 = new Point((int)((event.getX(0)/scaleX) - originX-MminX) ,(int)((event.getY(0)/scaleY) -MminY- originY) );

                        int[] npx = new int[poly.points.size()];
                        int[] npy = new int[poly.points.size()];
                        Point[] noobs = new Point[poly.points.size()];

                        for(int j=0;j<poly.points.size(); j++)
                        {
                            npx[j] = poly.points.get(j).x-MminX;
                            npy[j] = poly.points.get(j).y-MminY;
                            noobs[j] = new Point(npx [j], npy[j]);


                        }
                        Polygon2 polygn2 = new Polygon2(npx , npy ,poly.points.size());

                        result2 =   polygn2.containsss( noobs ,point2  );


                        //   boolean result2 =     polygn2.contains((point.x- MminX) ,point.y-MminY);
                        Log.d("COLLIDES OR NOT", " " + result2);
                        for (Point pl : noobs)
                        {
                            Log.d("VERTICES ARE:", " " +( pl.x )+" " + (pl.y));

                        }
                        Log.d("VERTICES ARE: TOUCHY ", " " + (point2.x) +" " + (point2.y));

                        result2 =   polygn2.containsss( noobs ,point2  );


                        //   boolean result2 =     polygn2.contains((point.x- MminX) ,point.y-MminY);
                        Log.d("VERTICES C", " " + result2);


                    }

//                    int[] px = new int[poly.points.size()];
//                    int[] py = new int[poly.points.size()];
//                    for(int j=0;j<poly.points.size(); j++)
//                    {
//                        px[j] = poly.points.get(j).x;
//                        py[j] = poly.points.get(j).y;
//
//                    }
//
//                    Polygon2 polygn = new Polygon2(px , py ,poly.points.size());
//
//                    boolean result =     polygn.contains(point.x , point.y);
//
//
//                    Log.d( "Checked on2 poly " , result + " " + poly.polyId  );

if(result2) {



    poly.setSELECTED();
activity.tost(poly.polyId,true);

    fook = true;
//    drawPolygons();
    invalidate();
    break;

}
                else
{
    activity.tost(poly.polyId ,  false);

}


                }


                if(!fook)
          deselectAll();

                break;

            case MotionEvent.ACTION_MOVE:




                int selected =local_selected;
                int cnt = 0;







int x_local,y_local;

                x_local = (int) event.getX(0);
                y_local = (int) event.getY(0);



                if(!tupac)
                {
                    Log.d("VERTECYSDSA","DAS");

                    polygons.get(tupacLoc).translateXYPoint((int) ((event.getX(0) / scaleX) - originX), (int) ((event.getY(0) / scaleY) - originY));
polygons.get(tupacLoc).setSELECTED();
                    break;
                }



                if(local_selected!=-1) {
                    Log.d("SELECTED ", selected + " " + local_selected);

                   polygons.get(local_selected).translateXY((int) ((x_local - X_down) / scaleX), (int) ((y_local - Y_down) / scaleY));

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


//                drawPolygons();
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

    Paint p = new Paint();

    @Override
    protected void onDraw(Canvas C) {
        super.onDraw(C);


p.setFlags(Paint.ANTI_ALIAS_FLAG);







        drawPolygons();

C.drawBitmap(bmp,0,0,p);





    }


  public  void scaleBy(float x , float y , float a , float b)
    {



        scaleY *= x;scaleX*=y;
//if(scaleX>1&&scaleY>1 )
//         {
//            originX = (int) ((originY / scaleX));
//            originY = (int) ((originY / scaleY));
//        }
//        else
//{
//    originX = (int) ((originY * scaleX));
//    originY = (int) ((originY * scaleY));
//
//}


// canvas.translate(scaleX, scaleY);
a = originX;
        b = originY;
Log.d("scalinghdas", originX + " " + originY);
     //   translateBy((int) (-originX ), (int)(-originY));



        canvas.translate(-originX,- originY);







        originX += -originX;
        originY += -originY;

        canvas.scale(x, y,0,0);


        Point point =polygons.get(0).points.get(1);
//
        canvas.translate(a,b);
//
//
//
//
//
//
//
        originX +=(a);
       originY +=(b);

//         drawPolygons();

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
    Paint paint = new Paint();
  void  drawPolygons()
    {



canvas.drawColor(Color.WHITE);












int rg = 0;

        for(Polygon poly : polygons)
        {




            paint.setStyle(Paint.Style.FILL);
            paint.setColor(Color.parseColor("#FFFFF6F4"));
            Path   path  = new Path();
            int x= 0;
            for(Point p : poly.points)
            {

                if(x==0)
                {
                    path.moveTo(p.x , p.y);
                    Log.d("DAS", "DAS move"  + p.x);
                    x=1;
                } else
                {
                    Log.d("DAS", "DAS"  + p.x);
                    path.lineTo(p.x, p.y);

                }


            }
            path.close();
            canvas.drawPath(path, paint);

if(poly.SELECTED ) {



    Log.d("FASFAS","gdsg");
    paint.setColor(Color.parseColor("#FFC7DAE6"));




}
else if(!tupac)
{
    if(rg==tupacLoc)
        paint.setColor(Color.parseColor("#FFC7DAE6"));
    else
        paint.setColor(Color.parseColor("#FFE9C1AD"));




}
            else

    paint.setColor(Color.parseColor("#FFE9C1AD"));


            paint.setStyle(Paint.Style.STROKE);
            paint.setStrokeWidth(50);


            canvas.drawPath(path, paint);


            if(poly.SELECTED) {
                paint.setColor(Color.parseColor("#000000"));
                paint.setStrokeWidth(05);
                Matrix matrix = new Matrix();

//matrix.setScale(1.2f,1.2f);
           //     path.transform(matrix);
                canvas.drawPath(path, paint);

              //  canvas.drawTextOnPath("Prakhar", path, 20, 20, paint);
paint.setTextAlign(Paint.Align.CENTER);

                paint.setTextSize(40);
                paint.setStrokeWidth(4);
                paint.setColor(Color.parseColor("#FF5E5E5E"));
for(int i=0;i<poly.points.size();i++)
{
    Point a  = poly.points.get(i);
    Point b  = poly.points.get((i+1)%poly.points.size());


    Path ph = new Path();
    ph.moveTo(a.x, a.y);
    ph.lineTo(b.x, b.y);
   String dist =  String.format("%.02f",Math.sqrt(Math.pow(b.x - a.x, 2) + Math.pow(b.y - a.y, 2)));
    canvas.drawTextOnPath(""+dist, ph , 0,-60,paint);

    ph.offset(0, -60, ph);
    canvas.drawPath(ph , paint);

    float DIST = 60f;
    double SIN = (a.y/Math.sqrt((Math.pow(a.x,2)  +Math.pow(a.y,2) )));
    double COS = (a.x/Math.sqrt((Math.pow(a.x,2)  +Math.pow(a.y,2) )));


    Point A = new Point(a);
    Point B = new Point(b);




        double delAx = (DIST)*COS;


    double delAy = (DIST)*SIN;
try {
    double SLOPE = (B.y-A.y)/(B.x-A.x);
    Log.d("SLope1 is " , SLOPE + " ");
}
catch (Exception e)
{ // Log.d("SLope1 is " ,  "INFINITY ");

}




    SIN = (b.y/Math.sqrt((Math.pow(b.x,2)  +Math.pow(b.y,2) )));
    COS = (b.x/Math.sqrt((Math.pow(b.x,2)  +Math.pow(b.y,2) )));
    double delBx =(DIST)*COS;


    double delBy = (DIST)*SIN;
    try {
        double SLOPE = delBy/delBx;
       // Log.d("SLope2 is " , SLOPE + " ");
    }
    catch (Exception e)
    {
        //Log.d("SLope2 is " ,  "INFINITY ");

    }

    if(b.x>a.x)
    {

        A.y -=delAy;
        B.y -= delBy;

        A.x += delAx;
        B.x += delBx;

    }
    else
    {
        A.y +=delAy;
        B.y += delBy;

        A.x -= delAx;
        B.x -= delBx;
    }

A.x -= delAx;
    B.x -=delBx;

    A.y += delAy;
    B.y += delBy;
//float m = (b.y-a.y)/(b.x-a.x);


  //  canvas.drawLine(a.x,a.y ,a.x+(100*m),a.y+(100-m),paint);

Path plk = new Path();
    plk.moveTo(a.x, a.y);
    plk.lineTo(b.x, b.y);

plk.offset(-(float) delBx, -(float) delBy, plk);

//canvas.drawPath(plk , paint);



}

            }


            paint.setColor(Color.parseColor("#000000"));
         //   canvas.drawCircle(poly.Centre.x, poly.Centre.y, 10, paint);

            rg++;
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

                        paint.setTextSize(50);
                        paint.setTextAlign(Paint.Align.CENTER);
                        canvas.drawText("" + (p.x - minX), p.x, maxY + 100 + 50 + 50, paint);
                        canvas.drawText("" + (p.x - minX), p.x, minY - 100 - 50, paint);
                        canvas.drawLine(p.x, minY - 100 - 30, p.x, minY - 100 + 30, paint);




                        canvas.drawLine(p.x, maxY + 100 - 30, p.x, maxY + 100 + 30, paint);


                        canvas.drawLine(minX - 100 + 30, p.y, minX - 100 - 30, p.y, paint);

                        canvas.drawLine(maxX + 100 - 30, p.y, maxX + 100 + 30, p.y, paint);

                       // paint.setTextAlign(Paint.Align.RIGHT);
                        canvas.drawText("" + (p.y - minY), maxX + 100 + 50 + 50, p.y, paint);

                        //paint.setTextAlign(Paint.Align.LEFT);
                        canvas.drawText("" + (p.y - minY), minX - 100 - 50 - 50, p.y, paint);

                        Path pp = new Path();
                  pp.moveTo(minX,minY);
                        pp.lineTo(maxX,maxY);
                        pp.close();


                    }

                }
            }


            Log.d("boundary", " " + minX + " " + minY);
      //     canvas.drawRect(new Rect(minX - 60, minY - 60, maxX + 60, maxY + 60), paint);
MminX = minX;
            MminY = minY;
            MmaxX = maxX;
            MmaxY = maxY;


            canvas.drawLine(MminX-100+20,MminY-100,MmaxX+100-20,MminY-100 , paint);

            canvas.drawLine(MminX-100+20,MmaxY+100,MmaxX+100-20,MmaxY+100 , paint);


            canvas.drawLine(MminX-100,MminY-100+20,MminX-100,MmaxY+100-20 , paint);
            canvas.drawLine(MmaxX+100,MminY-100+20,MmaxX+100,MmaxY+100-20 , paint);

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

    Handler  handler = new Handler();

     void rotate(final int id_selected) {
         rotate(id_selected);







    }

  public  void addPolygon()
    {

        ArrayList<Point> poi = new ArrayList<Point>();

        int offest = 0;

      poi.add(new Point(MminX, MmaxY + offest));
        poi.add(new Point(MminX + 600, MmaxY + offest));
        poi.add(new Point(MminX + 600, MmaxY + 600 + offest));

        poi.add(new Point(MminX ,MmaxY+600+offest));

Polygon pooo = new Polygon(poi, CNTR);

  //      pooo.translateXY(-originX,-originY);
      //  pooo.rotateBy(Math.PI/2);
      //  pooo.rotateBy(Math.PI/2);
        polygons.add(pooo);



    deselectAll();

        translateBy((int) (-originX + 0 + (-0)), (int) (-originY - MmaxY));


    //    drawPolygons();
     //   invalidate();

    }

    public  void addPolygon2()
    {  int offest = 0;

        ArrayList<Point> poi = new ArrayList<Point>();

        poi.add(new Point(MminX,MmaxY));
        poi.add(new Point(MminX+600, MmaxY));
        poi.add(new Point(MminX+600, MmaxY+300));
        poi.add(new Point(MminX+400, MmaxY+300));
        poi.add(new Point(MminX+400, MmaxY+800));
        poi.add(new Point(MminX+200, MmaxY+800));
        poi.add(new Point(MminX+200, MmaxY+300));
        poi.add(new Point(MminX +0,MmaxY+300));

     //   poi.add(new Point(MminX ,MmaxY+300));

        Polygon    pooo   = new Polygon(poi, CNTR);
     //   pooo.rotateBy(Math.PI/2);
     //   pooo.rotateBy(Math.PI/2);
     //   pooo.rotateBy(Math.PI);
        polygons.add(pooo);

deselectAll();

        translateBy((int) (-originX + 0 + (-0)), (int) (-originY - MmaxY));



//        drawPolygons();
     //   invalidate();

    }

  public  void remove()
    {
        if(local_selected!=-1)
        {
int x = local_selected;
        deselectAll();
            polygons.remove(x);

        }
//        drawPolygons();
        invalidate();

    }

}
