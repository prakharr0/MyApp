package com.rathiworks.ecommerce;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class AdminCategoryActivity extends AppCompatActivity {

    private ImageView tShirts,sports,femaleDresses,sweaters;
    private ImageView glasses,hats,purses,shoes;
    private ImageView headphone,laptops,watches,phones;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_category);

        tShirts = (ImageView) findViewById(R.id.t_shirts);
        //categories being sold.
        tShirts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminCategoryActivity.this,  AdminAddNewProductActivity.class);
                intent.putExtra("CATEGORY", "tShirts");
                startActivity(intent);
            }
        });

        sports = (ImageView) findViewById(R.id.sports_tshirt);
        //categories being sold.
        sports.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminCategoryActivity.this,  AdminAddNewProductActivity.class);
                intent.putExtra("CATEGORY", "sports");
                startActivity(intent);
            }
        });

        femaleDresses = (ImageView) findViewById(R.id.female_dresses);
        //categories being sold.
        femaleDresses.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminCategoryActivity.this,  AdminAddNewProductActivity.class);
                intent.putExtra("CATEGORY", "femaleDresses");
                startActivity(intent);
            }
        });

        sweaters = (ImageView) findViewById(R.id.sweaters);
        //categories being sold.
        sweaters.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminCategoryActivity.this,  AdminAddNewProductActivity.class);
                intent.putExtra("CATEGORY", "sweaters");
                startActivity(intent);
            }
        });

        glasses = (ImageView) findViewById(R.id.glasses);
        //categories being sold.
        glasses.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminCategoryActivity.this,  AdminAddNewProductActivity.class);
                intent.putExtra("CATEGORY", "glasses");
                startActivity(intent);
            }
        });

        hats = (ImageView) findViewById(R.id.hats);
        //categories being sold.
        hats.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminCategoryActivity.this,  AdminAddNewProductActivity.class);
                intent.putExtra("CATEGORY", "hats");
                startActivity(intent);
            }
        });

        purses = (ImageView) findViewById(R.id.purses_bags_wallets);
        //categories being sold.
        purses.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminCategoryActivity.this,  AdminAddNewProductActivity.class);
                intent.putExtra("CATEGORY", "purses");
                startActivity(intent);
            }
        });

        shoes = (ImageView) findViewById(R.id.shoes);
        //categories being sold.
        shoes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminCategoryActivity.this,  AdminAddNewProductActivity.class);
                intent.putExtra("CATEGORY", "shoes");
                startActivity(intent);
            }
        });

        headphone = (ImageView) findViewById(R.id.headphones);
        //categories being sold.
        headphone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminCategoryActivity.this,  AdminAddNewProductActivity.class);
                intent.putExtra("CATEGORY", "headphones");
                startActivity(intent);
            }
        });

        laptops = (ImageView) findViewById(R.id.laptops);
        //categories being sold.
        laptops.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminCategoryActivity.this,  AdminAddNewProductActivity.class);
                intent.putExtra("CATEGORY", "laptops");
                startActivity(intent);
            }
        });

        watches = (ImageView) findViewById(R.id.watches);
        //categories being sold.
        watches.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminCategoryActivity.this,  AdminAddNewProductActivity.class);
                intent.putExtra("CATEGORY", "watches");
                startActivity(intent);
            }
        });

        phones = (ImageView) findViewById(R.id.mobiles);
        //categories being sold.
        phones.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminCategoryActivity.this,  AdminAddNewProductActivity.class);
                intent.putExtra("CATEGORY", "phones");
                startActivity(intent);
            }
        });
    }
}