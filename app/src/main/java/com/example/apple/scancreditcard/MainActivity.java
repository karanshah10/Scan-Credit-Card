package com.example.apple.scancreditcard;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;

import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import io.card.payment.CardIOActivity;
import io.card.payment.CreditCard;

@EActivity
public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    @ViewById
    Button scan;
    @ViewById
    TextView result1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        scan.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
      //  IntentIntegrator integrator = new IntentIntegrator(this);
        //integrator.setDesiredBarcodeFormats(IntentIntegrator.ALL_CODE_TYPES);
        //integrator.setPrompt("Scan Credit Card");
        //integrator.setCameraId(0);
        //integrator.setBeepEnabled(false);
        //integrator.initiateScan();
        Intent scanIntent = new Intent(this, CardIOActivity.class);
        scanIntent.putExtra(CardIOActivity.EXTRA_REQUIRE_EXPIRY,true);
        scanIntent.putExtra(CardIOActivity.EXTRA_REQUIRE_CVV,false);
        scanIntent.putExtra(CardIOActivity.EXTRA_REQUIRE_POSTAL_CODE,false);
        startActivityForResult(scanIntent,1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1){
            String result;
            if(data!=null && data.hasExtra(CardIOActivity.EXTRA_SCAN_RESULT)){
                CreditCard scanResult = data.getParcelableExtra(CardIOActivity.EXTRA_SCAN_RESULT);
                Toast.makeText(this,"Carde Number:"+scanResult.getRedactedCardNumber(),Toast.LENGTH_LONG).show();
                if(scanResult.isExpiryValid()){
                    result1.setText(scanResult.getRedactedCardNumber());
             //       Toast.makeText(this,"Expiration Date:"+scanResult.expiryMonth,Toast.LENGTH_LONG).show();
                }
                if(scanResult.cvv != null){
                    Toast.makeText(this,"CVV Number :"+scanResult.cvv.length(),Toast.LENGTH_LONG).show();
                }
                if(scanResult.postalCode != null){
                    Toast.makeText(this,"Postal Code"+scanResult.postalCode,Toast.LENGTH_LONG).show();
                }
                else{
                    Toast.makeText(this,"Scan was Canceled",Toast.LENGTH_LONG).show();
                }
            }


        }
    }
}
