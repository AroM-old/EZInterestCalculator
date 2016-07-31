/*
 * Copyright (C) 2016 Algenis Romero
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file 
 * except in compliance with the License. You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the 
 * License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, 
 * either express or implied. See the License for the specific language governing permissions and 
 * limitations under the License.
 */
package com.romero.ezinterestcalculator;

import android.app.Activity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;

import static com.romero.ezinterestcalculator.R.id.amount;
import static com.romero.ezinterestcalculator.R.id.years;

public class EZInterestCalculatorActivity extends Activity {

    private EditText amountOfMoney;
    private EditText interestRate;

    private TextView yearsTerm;
    private TextView summaryMessage;

    private Button calculateInterest;
    private SeekBar yearsBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sicalculator);

        // inflating the id to show in the app.
        amountOfMoney = (EditText) findViewById(amount);
        interestRate = (EditText) findViewById(R.id.interest);


        yearsTerm = (TextView) findViewById(years);
        summaryMessage = (TextView) findViewById(R.id.summaryMessage);

        yearsBar = (SeekBar) findViewById(R.id.seekBar);
        calculateInterest = (Button) findViewById(R.id.calculate_btn);


        //Defining the seek bar for update the years and show it in the text view.
        yearsTerm.setText(yearsBar.getProgress() + " Year(s)");
        yearsBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            int progress = 0;

            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                progress = i;
                yearsTerm.setText(String.valueOf(progress) + " Year(s)");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                //yearsTerm.setText(yearsBar.getProgress() + " Year(s)");

            }
        });

        // Button listener for calculate the interest rate.
        calculateInterest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String amountOfMoneyCheck = amountOfMoney.getText().toString();
                String interestRateCheck = interestRate.getText().toString();
                if (TextUtils.isEmpty(amountOfMoneyCheck)) {
                    amountOfMoney.setError("This item cannot be empty");
                    return;
                }

                if (TextUtils.isEmpty(interestRateCheck)){
                    interestRate.setError("This item cannot be empty");
                    return;
                }

                CalculateInterest();
            }
        });
    }

    //Class that make the calculation.
    public void CalculateInterest() {
        String amount = amountOfMoney.getText().toString();
        String rate = interestRate.getText().toString();
        int years = yearsBar.getProgress();

        double Amount = Double.parseDouble(amount);
        double Rate = Double.parseDouble(rate);

        double result; //variable initialization

        result = Amount * Rate / 100 * years; //Calculate the interest rate

        //String answer = Double.toString(result);

        summaryMessage.setText("The interest for $" + amount + " at a rate of " + Rate + "% for " +
                years + " year(s) is $" + String.format("%1.2f", result));
    }

    @Override
    protected void onPause() {
        yearsBar.setProgress(0);
        super.onPause();
    }
}
