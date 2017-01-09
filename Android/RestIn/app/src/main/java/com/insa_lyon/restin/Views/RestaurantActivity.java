package com.insa_lyon.restin.Views;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.insa_lyon.restin.Modeles.DataSingleton;
import com.insa_lyon.restin.Modeles.Menu;
import com.insa_lyon.restin.Modeles.MenuMatin;
import com.insa_lyon.restin.Modeles.Restaurant;
import com.insa_lyon.restin.R;

import java.util.ArrayList;

public class RestaurantActivity extends AppCompatActivity {

    private Restaurant restaurant;

    private TabLayout tabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant);
        this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        int position = this.getIntent().getIntExtra("restaurantIndex",-1);
        if(position != -1) {
            this.restaurant = DataSingleton.getInstance().getRestaurant(position);
            this.getSupportActionBar().setTitle(this.restaurant.getName());
        } else {
            this.restaurant = null;
        }


        TabLayout tabLayout = (TabLayout) findViewById(R.id.menuTab);
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        final ViewPager viewPager = (ViewPager) findViewById(R.id.pager);
        final MenuAdapter adapter = new MenuAdapter
                (getSupportFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
                showMenu(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        showMenuInit(viewPager);
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


    private void showMenuInit(ViewPager viewPager) {
        LinearLayout menuLayout = (LinearLayout) findViewById(R.id.menuLayoutError);

        if(this.restaurant == null) {
            remplirLigne(menuLayout, "Le restaurant demandé n'existe pas ou " +
                    "une erreur est survenue.");
        } else {
            // Récupération des menus
            Menu menuMidi = restaurant.getMenuMidi();
            Menu menuSoir = restaurant.getMenuSoir();
            MenuMatin menuMatin = restaurant.getMenuMatin();

            // Récupération des containers correspondant aux menus
            LinearLayout menuSoirL = (LinearLayout) findViewById(R.id.menuLayoutSoir);
            LinearLayout menuMatinL = (LinearLayout) findViewById(R.id.menuLayoutMatin);
            LinearLayout menuMidiL = (LinearLayout) findViewById(R.id.menuLayoutMidi);

            // Affichage initial (on privilégie le menu du midi)
            if(menuMidi != null) {
                remplirMenuMidi(menuMidiL, menuMidi, menuSoirL, menuMatinL);
                viewPager.setCurrentItem(0);
            } else if(menuSoir != null) {
                remplirMenuSoir(menuSoirL, menuSoir, menuMidiL, menuMatinL);
                viewPager.setCurrentItem(1);
            } else if(menuMatin != null) {
                remplirMenuMatin(menuMatinL, menuMatin, menuMidiL, menuSoirL);
                viewPager.setCurrentItem(2);
            } else {
                remplirLigne(menuLayout, "Le restaurant demandé n'a pas de menu pour aujourd'hui.");
            }
        }
    }

    private void remplirMenuMidi(LinearLayout menuToShow, Menu menu, LinearLayout otherMenu1,
                                 LinearLayout otherMenu2) {

        otherMenu1.setVisibility(View.GONE);
        otherMenu2.setVisibility(View.GONE);

        LinearLayout menuLayoutError = (LinearLayout) findViewById(R.id.menuLayoutError);
        menuLayoutError.removeAllViews();

        if(menu == null) {
            menuToShow.setVisibility(View.GONE);
            remplirLigne(menuLayoutError, "Il n'y a pas de menu pour l'option choisie.");
            return;
        }

        menuToShow.setVisibility(View.VISIBLE);

        ArrayList<String> entrees = menu.getEntrees();
        ArrayList<String> plats = menu.getPlats();
        ArrayList<String> desserts = menu.getDesserts();


        // Récupération des containers
        LinearLayout layoutEntrees = (LinearLayout) findViewById(R.id.entranceContainerMidi);
        LinearLayout layoutPlats = (LinearLayout) findViewById(R.id.dishContainerMidi);
        LinearLayout layoutDesserts = (LinearLayout) findViewById(R.id.dessertContainerMidi);

        recupererDonnees(entrees, plats, desserts, layoutEntrees, layoutPlats, layoutDesserts);
    }

    private void remplirMenuSoir(LinearLayout menuToShow, Menu menu, LinearLayout otherMenu1,
                                 LinearLayout otherMenu2) {

        otherMenu1.setVisibility(View.GONE);
        otherMenu2.setVisibility(View.GONE);

        LinearLayout menuLayoutError = (LinearLayout) findViewById(R.id.menuLayoutError);
        menuLayoutError.removeAllViews();

        if(menu == null) {
            menuToShow.setVisibility(View.GONE);
            remplirLigne(menuLayoutError, "Il n'y a pas de menu pour l'option choisie.");
            return;
        }

        menuToShow.setVisibility(View.VISIBLE);

        ArrayList<String> entrees = menu.getEntrees();
        ArrayList<String> plats = menu.getPlats();
        ArrayList<String> desserts = menu.getDesserts();

        // Récupération des containers
        LinearLayout layoutEntrees = (LinearLayout) findViewById(R.id.entranceContainerSoir);
        LinearLayout layoutPlats = (LinearLayout) findViewById(R.id.dishContainerSoir);
        LinearLayout layoutDesserts = (LinearLayout) findViewById(R.id.dessertContainerSoir);

        recupererDonnees(entrees, plats, desserts, layoutEntrees, layoutPlats, layoutDesserts);
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

    private void remplirMenuMatin(LinearLayout menuToShow, MenuMatin menuMatin,
                                  LinearLayout otherMenu1, LinearLayout otherMenu2) {
        otherMenu1.setVisibility(View.GONE);
        otherMenu2.setVisibility(View.GONE);

        LinearLayout menuLayoutError = (LinearLayout) findViewById(R.id.menuLayoutError);
        menuLayoutError.removeAllViews();

        if(menuMatin == null) {
            menuToShow.setVisibility(View.GONE);
            remplirLigne(menuLayoutError, "Il n'y a pas de menu pour l'option choisie.");
            return;
        }

        menuToShow.setVisibility(View.VISIBLE);

        ArrayList<String> produits = menuMatin.getProduits();
        ArrayList<String> boissons = menuMatin.getBoissons();

        //Récupération des containers
        LinearLayout layoutProduits = (LinearLayout) findViewById(R.id.productCont);
        LinearLayout layoutBoissons = (LinearLayout) findViewById(R.id.drinkContainer);

        layoutBoissons.removeAllViews();
        layoutProduits.removeAllViews();


        remplirLigne(layoutBoissons, produits.get(0));

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

    private void showMenu(int optionChoisie) {
        // Récupération des menus
        Menu menuMidi = restaurant.getMenuMidi();
        Menu menuSoir = restaurant.getMenuSoir();
        MenuMatin menuMatin = restaurant.getMenuMatin();

        // Récupération des containers correspondant aux menus
        LinearLayout menuSoirL = (LinearLayout) findViewById(R.id.menuLayoutSoir);
        LinearLayout menuMatinL = (LinearLayout) findViewById(R.id.menuLayoutMatin);
        LinearLayout menuMidiL = (LinearLayout) findViewById(R.id.menuLayoutMidi);

        switch (optionChoisie) {
            case 0 :
                remplirMenuMidi(menuMidiL, menuMidi, menuSoirL, menuMatinL);
                break;
            case 1 :
                remplirMenuSoir(menuSoirL, menuSoir, menuMidiL, menuMatinL);
                break;
            case 2 :
                remplirMenuMatin(menuMatinL, menuMatin, menuMidiL, menuSoirL);
                break;
            default :
                break;
        }

    }
}