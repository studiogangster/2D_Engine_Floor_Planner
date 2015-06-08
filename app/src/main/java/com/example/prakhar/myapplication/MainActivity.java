package com.example.prakhar.myapplication;

import android.graphics.Matrix;
import android.graphics.Point;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends ActionBarActivity

{Button rot;

    boolean capVisible =false;

    void tost(final int id , boolean vie)
    {

        if(vie) {


            captn.setText("Room " + (id + 1) + " selected");
            Vcaption.setVisibility(View.VISIBLE);

            rot.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {


                    rotate(id);
                }
            });


        }
        else
        {

            Vcaption.setVisibility(View.INVISIBLE);

        }

      //  Toast.makeText(this , "Room " + (id+1) + " selected" ,1000).show();




    }

    Button btn,dl;

   public static int WIDTH=0 ;

    public static int HEIGHT =0;
    LinearLayout Vcaption;
TextView captn;

    ScaleGestureDetector scaleGestureDetector;
    vie view;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        WIDTH =getResources().getDisplayMetrics().widthPixels;
        HEIGHT =getResources().getDisplayMetrics().heightPixels;


        setContentView(R.layout.parent);


        Vcaption = (LinearLayout) findViewById(R.id.captionV);
        captn = (TextView) findViewById(R.id.caption);
 view = (vie) findViewById(R.id.vvv);

btn = (Button) findViewById(R.id.btn);
        rot = (Button) findViewById(R.id.rot);
        dl = (Button) findViewById(R.id.del);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View ds) {
                view.addPolygon2();
                //      view.scaleBy(0.5f,0.5f,0,0);
          //      view.scaleBy(1.2f,1.2f,0,0);
            }
        });
        dl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View ds) {
                // view.remove();
                view.addPolygon();
                //    view.scaleBy(2,2,0,0);
          //      view.scaleBy(0.8f,0.8f,0,0);
            }
        });


       // view.translateBy(500, 500);


        view.setOnTouchListener(new View.OnTouchListener() {

            ScaleGestureDetector det = new ScaleGestureDetector(MainActivity.this, new ScaleGestureDetector.OnScaleGestureListener() {
                @Override
                public boolean onScale(ScaleGestureDetector scaleGestureDetector) {
                    float factr = scaleGestureDetector.getScaleFactor();
                    Log.d("SCALING onScale", "" + factr);
view.scaleBy(factr, factr, scaleGestureDetector.getScaleFactor(), scaleGestureDetector.getScaleFactor() );


                    return true;
                }

                @Override
                public boolean onScaleBegin(ScaleGestureDetector scaleGestureDetector) {
                    Log.d("SCALING onScaleBegin", "" + 0);


                    view.iAmScaling = true;
            view.deselectAll();
                    return true;
                }

                @Override
                public void onScaleEnd(ScaleGestureDetector scaleGestureDetector) {
                    Log.d("SCALING onScaleEnd", "" + 0);
                    view.iAmScaling = false;
                }
            });

            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {

                if (motionEvent.getPointerCount() > 1)
                    det.onTouchEvent(motionEvent);
                return false;
            }








        });



//view.scaleBy(1.5f, 1.5f, 0, 0);
//        view.scaleBy(1.5f,1.5f,0,0);
//        view.scaleBy(1.5f,1.5f,0,0);
     //   view.scaleBy(2.5f,2.5f,0,0);
       // view.scaleBy(0.5f, 0.5f, 0, 0);

   //     view.scaleBy(2.5f, 2.5f, 0, 0);




//        view.setOnTouchListener(new View.OnTouchListener() {
//            private float startX;
//            private float startY;
//
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//                switch (event.getAction()) {
//                    case MotionEvent.ACTION_DOWN:
//                        startX = event.getX();
//                        startY = event.getY();
//                        break;
//                    case MotionEvent.ACTION_UP: {
//                        float endX = event.getX();
//                        float endY = event.getY();
//                        if (isAClick(startX, endX, startY, endY)) {
//                            return false;// WE HAVE A CLICK!!
//                        }
//                        break;
//                    }
//                }
//
//                return true; //specific to my project
//            }
//        });


    }
int id_selected = 0;
    private void rotate(int id_selected) {


view.rotate(id_selected);

    }

    private boolean isAClick(float startX, float endX, float startY, float endY) {
        float differenceX = Math.abs(startX - endX);
        float differenceY = Math.abs(startY - endY);
        if (differenceX > 1000/* =5 */ || differenceY >1000) {
            return false;
        }
        return true;
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
