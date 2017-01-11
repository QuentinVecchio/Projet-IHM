package com.insa_lyon.restin.Views;

import android.graphics.Point;
import android.graphics.Rect;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetBehavior;
import android.support.v4.app.ListFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Display;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.ScrollView;
import android.widget.TextView;

import com.google.android.gms.vision.text.Text;
import com.insa_lyon.restin.Modeles.Avis;
import com.insa_lyon.restin.Modeles.DataSingleton;
import com.insa_lyon.restin.Modeles.Restaurant;
import com.insa_lyon.restin.R;

import java.util.Date;

public class AvisActivity extends AppCompatActivity {

    private Button buttonPublish;
    private RatingBar ratingBar;
    private EditText avisEditText;
    private ListView avisListView;
    private Restaurant restaurant;
    private LinearLayout bottomSheet;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_avis);

        this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        int position = this.getIntent().getIntExtra("restaurantIndex",-1);
        if(position != -1) {
            this.restaurant = DataSingleton.getInstance().getRestaurant(position);
            this.getSupportActionBar().setTitle("Avis - " + this.restaurant.getName());
        } else {
            this.restaurant = null;
        }

        avisListView = (ListView) findViewById(R.id.avisListView);
        AvisListViewAdapter avisListViewAdapter = new AvisListViewAdapter(this, restaurant.getAvis());
        avisListView.setAdapter(avisListViewAdapter);

        //bottom sheet insertion
        bottomSheet = (LinearLayout)findViewById(R.id.bottomSheet);
        bottomSheet.setOnClickListener(null);
        final BottomSheetBehavior bottomSheetBehavior = BottomSheetBehavior.from(bottomSheet);
        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
        bottomSheetBehavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {

            }

            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {
                float visibleBottomSheetHeight = (bottomSheet.getHeight() - bottomSheetBehavior.getPeekHeight()) * slideOffset+bottomSheetBehavior.getPeekHeight();

                //LayoutParams
                LinearLayout avisLinearLayout = (LinearLayout) findViewById(R.id.avisLinearLayout);
                android.view.ViewGroup.LayoutParams layoutParams = avisLinearLayout.getLayoutParams();

                //ScreenHeight
                Display display = getWindowManager().getDefaultDisplay();
                Point size = new Point();
                display.getSize(size);
                int screenHeight = size.y;

                //StatusBarHeight
                Rect rectangle = new Rect();
                Window window = getWindow();
                window.getDecorView().getWindowVisibleDisplayFrame(rectangle);
                int statusBarHeight = rectangle.top;

                //Updating MapViewLayoutHeight
                layoutParams.height = screenHeight - ((int)Math.ceil(visibleBottomSheetHeight)) - getSupportActionBar().getHeight() - statusBarHeight;
                avisLinearLayout.setLayoutParams(layoutParams);
            }


        });

        avisEditText = (EditText) findViewById(R.id.avisEditText);
        ratingBar = (RatingBar) findViewById(R.id.ratingBar);
        buttonPublish = (Button) findViewById(R.id.buttonPublish);

        avisEditText.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                view.getParent().requestDisallowInterceptTouchEvent(true);
                return false;
            }
        });

        buttonPublish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!avisEditText.getText().toString().replace(" ", "").replace("\n", "").isEmpty()) {
                    Avis avis = new Avis(avisEditText.getText().toString(), (double) ratingBar.getRating(), new Date());
                    restaurant.getAvis().add(0, avis);
                    ((BaseAdapter) avisListView.getAdapter()).notifyDataSetChanged();
                    avisEditText.setText("");
                    ratingBar.setRating(3);
                    avisListView.smoothScrollToPosition(0);
                }
            }
        });


        //Initialize the List size
        ViewGroup contentView = (ViewGroup)getWindow().getDecorView();
        contentView.post(new Runnable() {
            public void run() {
                //AvisViewLayoutParams
                LinearLayout layout = (LinearLayout)findViewById(R.id.avisLinearLayout);
                android.view.ViewGroup.LayoutParams layoutParams = layout.getLayoutParams();

                //ScreenHeight
                Display display = getWindowManager().getDefaultDisplay();
                Point size = new Point();
                display.getSize(size);
                int screenHeight = size.y;

                //StatusBarHeight
                Rect rectangle = new Rect();
                Window window = getWindow();
                window.getDecorView().getWindowVisibleDisplayFrame(rectangle);
                int statusBarHeight = rectangle.top;

                switch (bottomSheetBehavior.getState()) {
                    case BottomSheetBehavior.STATE_EXPANDED :
                        layoutParams.height = screenHeight - bottomSheet.getHeight() - statusBarHeight - getSupportActionBar().getHeight();
                        layout.setLayoutParams(layoutParams);
                        break;
                    case BottomSheetBehavior.STATE_COLLAPSED :
                        layoutParams.height = screenHeight - bottomSheetBehavior.getPeekHeight() - statusBarHeight - getSupportActionBar().getHeight();
                        layout.setLayoutParams(layoutParams);
                        break;
                }

            }
        });

        LinearLayout letAvisTitleLayout = (LinearLayout)findViewById(R.id.letAvisTitleLayout);
        letAvisTitleLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(bottomSheetBehavior.getState() == BottomSheetBehavior.STATE_COLLAPSED) {
                    bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                }
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
