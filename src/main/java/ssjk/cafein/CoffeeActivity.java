package ssjk.cafein;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

/**
 * Created by 장소영 on 2016-11-08.
 */
public class CoffeeActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coffee);

        Button btn_toCafe = (Button) findViewById(R.id. btn_toCafe);
        Button btn_Americano = (Button) findViewById(R.id.btn_Americano);
        Button btn_Cafe_Latte = (Button) findViewById(R.id.btn_Cafe_Latte);
        Button btn_Cappucino = (Button) findViewById(R.id.btn_Cappucino);
        Button btn_Cafe_Mocha = (Button) findViewById(R.id.btn_Cafe_Mocha);
        Button btn_Caramel_Machiatto = (Button) findViewById(R.id.btn_Caramel_Machiatto);
        Button btn_Espresso = (Button) findViewById(R.id.btn_Espresso);
        Button btn_Vanila_Latte = (Button) findViewById(R.id.btn_Vanila_Latte);
        Button btn_Green_Latte = (Button) findViewById(R.id.btn_Green_Latte);
        Button btn_Hazelnut_Latte = (Button) findViewById(R.id.btn_Hazelnut_Latte);
        Button btn_Chocolate_Latte = (Button) findViewById(R.id.btn_Chocolate_Latte);
        Button btn_Camomile = (Button) findViewById(R.id.btn_Camomile);
        Button btn_Peppermint = (Button) findViewById(R.id.btn_Peppermint);
        Button btn_Grapefruit = (Button) findViewById(R.id.btn_Grapefruit);
        Button btn_Earl_Grey = (Button) findViewById(R.id.btn_Earl_Grey);
        Button btn_Lemon = (Button) findViewById(R.id.btn_Lemon);
        Button btn_Citron = (Button) findViewById(R.id.btn_Citron);
        Button btn_English_Breakfast = (Button) findViewById(R.id.btn_English_Breakfast);
        Button btn_Jasmine = (Button) findViewById(R.id.btn_Jasmine);
        Button btn_Rooibos = (Button) findViewById(R.id.btn_Rooibos);
        Button btn_Rosemary = (Button) findViewById(R.id.btn_Rosemary);
        Button btn_it_peach = (Button) findViewById(R.id.btn_it_peach);
        Button btn_it_lemon = (Button) findViewById(R.id.btn_it_lemon);
        Button btn_Lemonade = (Button) findViewById(R.id.btn_Lemonade);
        Button btn_Grapefruit_ade = (Button) findViewById(R.id.btn_Grapefruit_ade);
        Button btn_P_Y_Smoothie = (Button) findViewById(R.id.btn_P_Y_Smoothie);
        Button btn_search = (Button) findViewById(R.id.btn_search);

        View.OnClickListener listener = new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent;
                int buttonid = v.getId();
                if (buttonid == R.id. btn_toCafe){
                    finish();
                }
                else if (buttonid > 0){
                    intent = new Intent(CoffeeActivity.this, CoffeeList.class);
                    switch (buttonid){
                        case R.id. btn_Americano:
                            intent.putExtra("CoffeeName","아메리카노");
                            break;
                        case R.id. btn_Cafe_Latte:
                            intent.putExtra("CoffeeName","카페라떼");
                            break;
                        case R.id. btn_Cappucino:
                            intent.putExtra("CoffeeName","카푸치노");
                            break;
                        case R.id. btn_Cafe_Mocha:
                            intent.putExtra("CoffeeName","카페모카");
                            break;
                        case R.id. btn_Caramel_Machiatto:
                            intent.putExtra("CoffeeName","카라멜마끼야또");
                            break;
                        case R.id. btn_Espresso:
                            intent.putExtra("CoffeeName","에스프레소");
                            break;
                        case R.id. btn_Vanila_Latte:
                            intent.putExtra("CoffeeName","바닐라라떼");
                            break;
                        case R.id. btn_Green_Latte:
                            intent.putExtra("CoffeeName","녹차라떼");
                            break;
                        case R.id. btn_Hazelnut_Latte:
                            intent.putExtra("CoffeeName","헤이즐넛라떼");
                            break;
                        case R.id. btn_Chocolate_Latte:
                            intent.putExtra("CoffeeName","초코라떼");
                            break;
                        case R.id. btn_Camomile:
                            intent.putExtra("CoffeeName","카모마일");
                            break;
                        case R.id. btn_Peppermint:
                            intent.putExtra("CoffeeName","페퍼민트");
                            break;
                        case R.id. btn_Grapefruit:
                            intent.putExtra("CoffeeName","자몽");
                            intent.putExtra("DrinkType","TEA");
                            break;
                        case R.id. btn_Earl_Grey:
                            intent.putExtra("CoffeeName","얼그레이");
                            break;
                        case R.id. btn_Lemon:
                            intent.putExtra("CoffeeName","레몬");
                            break;
                        case R.id. btn_Citron:
                            intent.putExtra("CoffeeName","유자");
                            intent.putExtra("DrinkType","TEA");
                            break;
                        case R.id. btn_English_Breakfast:
                            intent.putExtra("CoffeeName","잉글리쉬블랙퍼스트");
                            break;
                        case R.id. btn_Jasmine:
                            intent.putExtra("CoffeeName","쟈스민");
                            break;
                        case R.id. btn_Rooibos:
                            intent.putExtra("CoffeeName","루이보스");
                            break;
                        case R.id. btn_Rosemary:
                            intent.putExtra("CoffeeName","로즈마리");
                            break;
                        case R.id. btn_it_peach:
                            intent.putExtra("CoffeeName","아이스티 복숭아");
                            break;
                        case R.id. btn_it_lemon:
                            intent.putExtra("CoffeeName","아이스티 레몬");
                            break;
                        case R.id. btn_Lemonade:
                            intent.putExtra("CoffeeName","레몬에이드");
                            break;
                        case R.id. btn_Grapefruit_ade:
                            intent.putExtra("CoffeeName","자몽에이드");
                            break;
                        case R.id. btn_P_Y_Smoothie:
                            intent.putExtra("CoffeeName","플레인요거트 스무디");
                            break;
                    }
                    startActivity(intent);
                }

            }
        };

        btn_toCafe.setOnClickListener(listener);
        btn_Americano.setOnClickListener(listener);
        btn_Cafe_Latte.setOnClickListener(listener);
        btn_Cappucino.setOnClickListener(listener);
        btn_Cafe_Mocha.setOnClickListener(listener);
        btn_Caramel_Machiatto.setOnClickListener(listener);
        btn_Espresso.setOnClickListener(listener);
        btn_Vanila_Latte.setOnClickListener(listener);
        btn_Green_Latte.setOnClickListener(listener);
        btn_Hazelnut_Latte.setOnClickListener(listener);
        btn_Chocolate_Latte.setOnClickListener(listener);
        btn_Camomile.setOnClickListener(listener);
        btn_Peppermint.setOnClickListener(listener);
        btn_Grapefruit.setOnClickListener(listener);
        btn_Earl_Grey.setOnClickListener(listener);
        btn_Lemon.setOnClickListener(listener);
        btn_Citron.setOnClickListener(listener);
        btn_English_Breakfast.setOnClickListener(listener);
        btn_Jasmine.setOnClickListener(listener);
        btn_Rooibos.setOnClickListener(listener);
        btn_Rosemary.setOnClickListener(listener);
        btn_it_peach.setOnClickListener(listener);
        btn_it_lemon.setOnClickListener(listener);
        btn_Lemonade.setOnClickListener(listener);
        btn_Grapefruit_ade.setOnClickListener(listener);
        btn_P_Y_Smoothie.setOnClickListener(listener);
        btn_search.setOnClickListener(listener);




    }
}
