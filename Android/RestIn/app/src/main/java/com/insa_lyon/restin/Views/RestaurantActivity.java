package com.insa_lyon.restin.Views;

import android.content.Intent;
import android.graphics.Color;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import com.google.android.gms.vision.text.Text;
import com.insa_lyon.restin.Modeles.Avis;
import com.insa_lyon.restin.Modeles.DataSingleton;
import com.insa_lyon.restin.Modeles.Menu;
import com.insa_lyon.restin.Modeles.MenuMatin;
import com.insa_lyon.restin.Modeles.Restaurant;
import com.insa_lyon.restin.R;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.GridLabelRenderer;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;
import java.util.TreeMap;
import java.util.Vector;

public class RestaurantActivity extends AppCompatActivity {

    private Restaurant restaurant;

    private final int COLOR = 0xFFD92727;

    private ViewPager viewPagerMenu = null;
    private ViewPager viewPagerGraph = null;

    private View vueMenuMidi = null;
    private View vueMenuSoir = null;
    private View vueMenuMatin = null;

    private View vueGraphMatin = null;
    private View vueGraphMidi = null;
    private View vueGraphSoir = null;


    private Button buttonMenuMidi = null;
    private Button buttonMenuSoir = null;
    private Button buttonMenuMatin = null;


    private Button buttonGraphMidi = null;
    private Button buttonGraphSoir = null;
    private Button buttonGraphMatin = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant);
        this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        int position = this.getIntent().getIntExtra("restaurantIndex", -1);
        if (position != -1) {
            this.restaurant = DataSingleton.getInstance().getRestaurant(position);
            this.getSupportActionBar().setTitle(this.restaurant.getName());
        } else {
            this.restaurant = null;
        }

        this.viewPagerMenu = (ViewPager)findViewById(R.id.viewPageMenu);
        this.viewPagerGraph = (ViewPager) findViewById(R.id.viewGraph);

        Vector<View> pagesMenu = new Vector<>();
        Vector<View> pagesGraph= new Vector<>();

        Vector<String> titles1 = new Vector<>();
        Vector<String> titles2 = new Vector<>();

        addViewAccordingToMenus(pagesMenu, titles1);
        addViewAccordingToGraph(pagesGraph, titles2);
        changeOnClickMenuButton();
        changeOnClickGraphButton();


        this.viewPagerMenu.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                focusOnMenuButton(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        this.viewPagerGraph.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                focusOnGraphButton(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        focusOnMenuButton(this.viewPagerMenu.getCurrentItem());
        focusOnGraphButton(this.viewPagerMenu.getCurrentItem());
        setRatingBar();
        setAvis();
        setPrixOnView();

        if(!this.restaurant.getAvis().isEmpty()) {
            Button buttonAvis = (Button) findViewById(R.id.avisButton);
            buttonAvis.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(RestaurantActivity.this, AvisActivity.class);
                    intent.putExtra("restaurantIndex", DataSingleton.getInstance().getRestaurantPosition(restaurant));
                    RestaurantActivity.this.startActivity(intent);
                }
            });
        }



    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void addViewAccordingToMenus(Vector<View> pagesViewMenu, Vector<String> titles1) {

        boolean menuPresent = false;

        Menu menuMidi = restaurant.getMenuMidi();
        Menu menuSoir = restaurant.getMenuSoir();
        MenuMatin menuMatin = restaurant.getMenuMatin();

        if (menuMatin != null) {
            this.vueMenuMatin = getLayoutInflater().inflate(R.layout.breakfast_layout, null);
            remplirMenuMatin(menuMatin);
            pagesViewMenu.add(vueMenuMatin);
            buttonMenuMatin = (Button) findViewById(R.id.matinButton);
            menuPresent = true;
        }

        if(menuMidi != null) {
            this.vueMenuMidi = getLayoutInflater().inflate(R.layout.lunch_layout, null);
            remplirMenuMidi(menuMidi, R.id.entranceContainerMidi, R.id.dishContainerMidi,
                    R.id.dessertContainerMidi);
            pagesViewMenu.add(vueMenuMidi);
            buttonMenuMidi = (Button) findViewById(R.id.midiButton);
            menuPresent = true;
        }

        if (menuSoir != null) {
            this.vueMenuSoir = getLayoutInflater().inflate(R.layout.dinner_layout, null);
            remplirMenuSoir(menuSoir, R.id.entranceContainerSoir, R.id.dishContainerSoir,
                    R.id.dessertContainerSoir);
            pagesViewMenu.add(vueMenuSoir);
            buttonMenuSoir = (Button) findViewById(R.id.soirButton);
            menuPresent = true;
        }

        if(!menuPresent) {
            TextView viewNoMenu = new TextView(this);
            viewNoMenu.setText("Le restaurant demandé n'a pas de menu pour ce jour.");
            viewNoMenu.setGravity(Gravity.CENTER_HORIZONTAL & Gravity.CENTER_HORIZONTAL);
            pagesViewMenu.add(viewNoMenu);
        }

        MenuAdapter menuAdapter = new MenuAdapter(this,pagesViewMenu,titles1);
        this.viewPagerMenu.setAdapter(menuAdapter);

    }

    /**
     * Permet de récupérer les données puis de les ajouter aux bons endroits du menu
     * @param menuMidi : menu du midi que l'on traite
     * @param entranceContainer : conteneur des entrées
     * @param dishContainer : conteneur des plats
     * @param dessertContainer : conteneur des desserts
     */
    private void remplirMenuMidi(Menu menuMidi, int entranceContainer, int dishContainer, int dessertContainer) {
        ArrayList<String> entrees = menuMidi.getEntrees();
        ArrayList<String> plats = menuMidi.getPlats();
        ArrayList<String> desserts = menuMidi.getDesserts();

        // Récupération des containers
        LinearLayout layoutEntrees = (LinearLayout) vueMenuMidi.findViewById(entranceContainer);
        LinearLayout layoutPlats = (LinearLayout) vueMenuMidi.findViewById(dishContainer);
        LinearLayout layoutDesserts = (LinearLayout) vueMenuMidi.findViewById(dessertContainer);

        recupererDonnees(entrees, plats, desserts, layoutEntrees, layoutPlats, layoutDesserts);
    }

    /**
     * Permet de récupérer les données puis de les ajouter aux bons endroits du menu
     * @param menuSoir : menu du soir que l'on traite
     * @param entranceContainer : conteneur des entrées
     * @param dishContainer : conteneur des plats
     * @param dessertContainer : conteneur des desserts
     */
    private void remplirMenuSoir(Menu menuSoir, int entranceContainer, int dishContainer, int dessertContainer) {
        ArrayList<String> entrees = menuSoir.getEntrees();
        ArrayList<String> plats = menuSoir.getPlats();
        ArrayList<String> desserts = menuSoir.getDesserts();

        // Récupération des containers
        LinearLayout layoutEntrees = (LinearLayout) vueMenuSoir.findViewById(entranceContainer);
        LinearLayout layoutPlats = (LinearLayout) vueMenuSoir.findViewById(dishContainer);
        LinearLayout layoutDesserts = (LinearLayout) vueMenuSoir.findViewById(dessertContainer);

        recupererDonnees(entrees, plats, desserts, layoutEntrees, layoutPlats, layoutDesserts);
    }

    /**
     * Remplit le menu du matin
     * @param menuMatin : Menu du matin à traiter
     */
    private void remplirMenuMatin(MenuMatin menuMatin) {
        ArrayList<String> produits = menuMatin.getProduits();
        ArrayList<String> boissons = menuMatin.getBoissons();

        //Récupération des containers
        LinearLayout layoutProduits = (LinearLayout) vueMenuMatin.findViewById(R.id.productCont);
        LinearLayout layoutBoissons = (LinearLayout) vueMenuMatin.findViewById(R.id.drinkContainer);

        layoutBoissons.removeAllViews();
        layoutProduits.removeAllViews();
        // Remplissage des produits
        if(produits== null) {
            remplirLigne(layoutProduits, "-");
        } else {
            for(String p : produits) {
                remplirLigne(layoutProduits, p);
            }
        }

        // Remplissage des boissons
        if (boissons== null) {
            remplirLigne(layoutBoissons, "-");
        } else {
            for(String b : boissons) {
                remplirLigne(layoutBoissons, b);
            }
        }
    }

    private void recupererDonnees(ArrayList<String> entrees, ArrayList<String> plats,
                                  ArrayList<String> desserts, LinearLayout layoutEntrees,
                                  LinearLayout layoutPlats, LinearLayout layoutDesserts) {

        layoutEntrees.removeAllViews();
        layoutPlats.removeAllViews();
        layoutDesserts.removeAllViews();

        // Remplissage des entrées
        if(entrees== null) {
            remplirLigne(layoutEntrees, "-");
        } else {
            for(String e : entrees) {
                remplirLigne(layoutEntrees, e);
            }
        }

        // Remplissage des plats
        if (plats== null) {
            remplirLigne(layoutPlats, "-");
        } else {
            for(String p : plats) {
                remplirLigne(layoutPlats, p);
            }
        }

        // Remplissage des desserts
        if(desserts== null) {
            remplirLigne(layoutDesserts, "-");
        } else {
            for(String d : desserts) {
                remplirLigne(layoutDesserts, d);
            }
        }
    }

    private void remplirLigne(LinearLayout layout, String content) {
        TextView view = new TextView(this);
        view.setText(content);
        view.setGravity(Gravity.CENTER_HORIZONTAL | Gravity.CENTER_VERTICAL);
        layout.addView(view);
    }

    private void changeOnClickMenuButton() {
        if(this.buttonMenuMatin == null) {
            findViewById(R.id.matinButton).setVisibility(View.GONE);
        } else {
            this.buttonMenuMatin.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    viewPagerMenu.setCurrentItem(0);
                }
            });
        }
        if(this.buttonMenuMidi == null) {
            findViewById(R.id.midiButton).setVisibility(View.GONE);
        } else {
            this.viewPagerMenu.setCurrentItem(1);
            this.buttonMenuMidi.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    viewPagerMenu.setCurrentItem(1);
                }
            });
        }
        if(this.buttonMenuSoir == null) {
            findViewById(R.id.soirButton).setVisibility(View.GONE);
        } else {
            this.buttonMenuSoir.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    viewPagerMenu.setCurrentItem(2);
                }
            });
        }
    }

    private void focusOnMenuButton(int currentItem) {

        switch (currentItem) {
            case 0 :
                if(buttonMenuMatin != null ) {
                    selectButton(buttonMenuMatin);
                    if(buttonMenuSoir != null) {
                        unselectButton(buttonMenuSoir);
                    }
                    if(buttonMenuMidi != null) {
                        unselectButton(buttonMenuMidi);
                    }
                } else if (buttonMenuMidi != null) {
                    selectButton(buttonMenuMidi);
                    if(buttonMenuSoir != null) {
                        unselectButton(buttonMenuSoir);
                    }
                } else {
                    selectButton(buttonMenuSoir);
                }
                break;
            case 1 :
                if (buttonMenuMidi != null) {
                    selectButton(buttonMenuMidi);
                    unselectButton(buttonMenuMatin);
                    if(buttonMenuSoir != null) {
                        unselectButton(buttonMenuSoir);
                    }
                } else {
                    selectButton(buttonMenuSoir);
                    unselectButton(buttonMenuMatin);
                }
                break;
            case 2 :
                selectButton(buttonMenuSoir);
                unselectButton(buttonMenuMatin);
                unselectButton(buttonMenuMidi);
                break;
            default :
                break;
        }
    }

    private void selectButton(Button buttonToSelect) {
        buttonToSelect.setTextColor(COLOR );
        buttonToSelect.setBackgroundResource(R.drawable.menu_button_shape);
    }

    private void unselectButton(Button buttonToUnselect) {
        buttonToUnselect.setTextColor(Color.BLACK);
        buttonToUnselect.setBackgroundColor(View.INVISIBLE);
    }

    private void setRatingBar() {
        RatingBar ratingBar = (RatingBar) findViewById(R.id.ratingBarRestau);
        ratingBar.setRating((float)this.restaurant.getMoyenneNote());
        /** TODO : faire check si y a des avis et afficher/cacher kes bons trucs**/
    }

    private void setAvis() {
        TextView avisAverage = (TextView) findViewById(R.id.avisAverage);
        if(!this.restaurant.getAvis().isEmpty()) {
            int nbAvis = this.restaurant.getAvis().size();
            avisAverage.setText("Note moyenne ("+ nbAvis + " avis) : ");
            TextView lastAvisTitle = (TextView) findViewById(R.id.lastAvisTitle);
            Avis avisToShow = this.restaurant.getAvis().get(nbAvis - 1);
            DateFormat dateFormat = new SimpleDateFormat("dd/MM/yy", Locale.FRENCH);
            DateFormat dateFormatTime = new SimpleDateFormat("HH:mm", Locale.FRENCH);
            lastAvisTitle.setText("Dernier avis : " + dateFormat.format(avisToShow.getDate())
                    + " à " + dateFormatTime.format(avisToShow.getDate()));
            TextView avisText = (TextView) findViewById(R.id.avisContainer);
            avisText.setText(avisToShow.getAvis());
            RatingBar avisRatingBar = (RatingBar) findViewById(R.id.ratingBarAvis);
            avisRatingBar.setRating((float)avisToShow.getNote());
        } else {
            /** TODO : OKOKOKOKO AF FAIRE */
        }
    }

    private void addViewAccordingToGraph(Vector<View> pagesViewGraph, Vector<String> titles) {

        TreeMap<Double,Integer> affluenceMatin = restaurant.getAffluenceMatin();
        TreeMap<Double,Integer> affluenceMidi = restaurant.getAffluenceMidi();
        TreeMap<Double,Integer> affluenceSoir = restaurant.getAffluenceSoir();

        if (affluenceMatin != null) {
            this.vueGraphMatin = getLayoutInflater().inflate(R.layout.graph_morning, null);
            remplirGraph(affluenceMatin, vueGraphMatin, R.id.graphMorningView);
            pagesViewGraph.add(vueGraphMatin);
            buttonGraphMatin = (Button) findViewById(R.id.matinButtonGraph);
        }

        if(affluenceMidi != null) {
            this.vueGraphMidi = getLayoutInflater().inflate(R.layout.graph_midday, null);
            remplirGraph(affluenceMidi, vueGraphMidi, R.id.graphMiddayView);
            pagesViewGraph.add(vueGraphMidi);
            buttonGraphMidi = (Button) findViewById(R.id.midiButtonGraph);
        }

        if (affluenceSoir != null) {
            this.vueGraphSoir = getLayoutInflater().inflate(R.layout.graph_evening, null);
            remplirGraph(affluenceSoir, vueGraphSoir, R.id.graphEveningView);
            pagesViewGraph.add(vueGraphSoir);
            buttonGraphSoir = (Button) findViewById(R.id.soirButtonGraph);
        }

        GraphAdpater graphAdapter = new GraphAdpater(this,pagesViewGraph,titles);
        this.viewPagerGraph.setAdapter(graphAdapter);

    }

    private void remplirGraph(TreeMap<Double, Integer> affluenceList, View vuePagerGraph,
                              Integer idOfGraph) {
        GraphView graph = (GraphView) vuePagerGraph.findViewById(idOfGraph);
        DataPoint[] pointsData =  new DataPoint[affluenceList.size()];
        int i=0;
        for(Double hour : affluenceList.keySet()) {
            pointsData[i] = new DataPoint(hour, affluenceList.get(hour));
            i++;
        }
        LineGraphSeries<DataPoint> series = new LineGraphSeries<>(pointsData);
        graph.removeAllSeries();

        GridLabelRenderer gridLabel = graph.getGridLabelRenderer();
        gridLabel.setVerticalAxisTitle("Minutes");
        gridLabel.setHorizontalAxisTitle("Heure ");
        gridLabel.setPadding(32);
        graph.getViewport().setMinX(affluenceList.firstKey());
        graph.getViewport().setMaxX(affluenceList.lastKey()+0.1);

        graph.getViewport().setMinY(0.0);
        graph.getViewport().setMaxY(getMaxTemps(affluenceList));
        graph.getViewport().setYAxisBoundsManual(true);
        graph.getViewport().setXAxisBoundsManual(true);
        series.setColor(COLOR);
        graph.addSeries(series);
    }

    private double getMaxTemps(TreeMap<Double, Integer> affluenceList) {
        Integer max = 0;
        for(Double hour : affluenceList.keySet()) {
            if(max < affluenceList.get(hour)) {
                max = affluenceList.get(hour);
            }
        }
        return max;
    }

    private void changeOnClickGraphButton() {
        if(this.buttonGraphMatin == null) {
            findViewById(R.id.matinButtonGraph).setVisibility(View.GONE);
        } else {
            this.buttonGraphMatin.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    viewPagerGraph.setCurrentItem(0);
                }
            });
        }
        if(this.buttonGraphMidi == null) {
            findViewById(R.id.midiButtonGraph).setVisibility(View.GONE);
        } else {
            this.viewPagerGraph.setCurrentItem(1);
            this.buttonGraphMidi.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    viewPagerGraph.setCurrentItem(1);
                }
            });
        }
        if(this.buttonGraphSoir == null) {
            findViewById(R.id.soirButtonGraph).setVisibility(View.GONE);
        } else {
            this.buttonGraphSoir.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    viewPagerGraph.setCurrentItem(2);
                }
            });
        }
    }

    private void focusOnGraphButton(int currentItem) {

        switch (currentItem) {
            case 0 :
                if(buttonGraphMatin != null ) {
                    selectButton(buttonGraphMatin);
                    if(buttonGraphSoir != null) {
                        unselectButton(buttonGraphSoir);
                    }
                    if(buttonGraphMidi != null) {
                        unselectButton(buttonGraphMidi);
                    }
                } else if (buttonGraphMidi != null) {
                    selectButton(buttonGraphMidi);
                    if(buttonGraphSoir != null) {
                        unselectButton(buttonGraphSoir);
                    }
                } else {
                    selectButton(buttonGraphSoir);
                }
                break;
            case 1 :
                if (buttonGraphMidi != null) {
                    selectButton(buttonGraphMidi);
                    unselectButton(buttonGraphMatin);
                    if(buttonGraphSoir != null) {
                        unselectButton(buttonGraphSoir);
                    }
                } else {
                    selectButton(buttonGraphSoir);
                    unselectButton(buttonGraphMatin);
                }
                break;
            case 2 :
                selectButton(buttonGraphSoir);
                unselectButton(buttonGraphMatin);
                unselectButton(buttonGraphMidi);
                break;
            default :
                break;
        }
    }

    private void setPrixOnView() {
        TextView viewPrix = (TextView) findViewById(R.id.prixView);
        viewPrix.setText("Prix moyen : " + String.valueOf(restaurant.getPrixMoyen()) + "€");
    }

}