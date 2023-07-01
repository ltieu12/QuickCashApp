package com.voidstudio.quickcashreg.PayPal;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import com.paypal.android.sdk.payments.PayPalConfiguration;
import com.paypal.android.sdk.payments.PayPalPayment;
import com.paypal.android.sdk.payments.PayPalService;
import com.paypal.android.sdk.payments.PaymentActivity;
import com.paypal.android.sdk.payments.PaymentConfirmation;
import com.voidstudio.quickcashreg.InAppActivityEmployer;
import com.voidstudio.quickcashreg.R;
import com.voidstudio.quickcashreg.management.EmployerContractManager;
import com.voidstudio.quickcashreg.management.IContractManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import users.Employer;

public class PayPalActivity extends AppCompatActivity {
    ActivityResultLauncher activityResultLauncher;

    private static final int PAYPAL_REQUEST_CODE = 555;
    private static PayPalConfiguration config;
    Button payButton;
    EditText editAmount;
    TextView paymentConfirm;
    ListView paymentListView;

    String paidAmount = "";
    public Employer employer = InAppActivityEmployer.employer;
    private IContractManager employerContractManager;
    private ArrayList<String> paymentList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.paypal_activity);
        config = new PayPalConfiguration().environment(PayPalConfiguration.ENVIRONMENT_SANDBOX).clientId(Config.PAYPAL_CLIENT_ID);
        employerContractManager = new EmployerContractManager(employer);


        editAmount = findViewById(R.id.paymentAmount);
        payButton = findViewById(R.id.payButton);
        paymentConfirm = findViewById(R.id.paymentInfo);
        paymentListView = findViewById(R.id.payList);
        paymentList =  employerContractManager.getPaymentList();
        setUpList(paymentList);

        initializeActivityLauncher();

        payButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                processPayment();
            }
        });
    }

    private void setUpList(ArrayList<String> payList)
    {
        setAdapter(payList);
    }
    private void setAdapter(List<String> payList)
    {
        PaySearchAdapter adapter = new PaySearchAdapter(getApplicationContext(), 0, payList);
        paymentListView.setAdapter(adapter);
    }

    private void initializeActivityLauncher() {
        // Initialize result launcher
        activityResultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
            @Override
            public void onActivityResult(ActivityResult result) {
                if (result.getResultCode() == RESULT_OK) {
                    PaymentConfirmation confirmation = result.getData().getParcelableExtra(PaymentActivity.EXTRA_RESULT_CONFIRMATION);
                    if (confirmation != null) {
                        try {
                            // Getting the payment details
                            String paymentDetails = confirmation.toJSONObject().toString(4);
                            // on below line we are extracting json response and displaying it in a text view.
                            JSONObject payObj = new JSONObject(paymentDetails);
                            String payID = payObj.getJSONObject("response").getString("id");
                            String state = payObj.getJSONObject("response").getString("state");
                            paymentConfirm.setText("Payment " + state + "\n with payment id is " + payID + "\nBalance: " + employer.getBalance());
                        } catch (JSONException e) {
                            // handling json exception on below line
                            Log.e("Error", "an extremely unlikely failure occurred: ", e);
                        }
                    }

                    //Toast.makeText(this, "Cancel", Toast.LENGTH_SHORT).show();
                } else if (result.getResultCode() == PaymentActivity.RESULT_EXTRAS_INVALID){
                    Log.d(TAG,"Launcher Result Invalid");
                } else if (result.getResultCode() == Activity.RESULT_CANCELED) {
                    Log.d(TAG, "Launcher Result Cancelled");
                }
            }
        });
    }

    private void processPayment() {
        paidAmount = editAmount.getText().toString();
        employer.makePayment((double)(Double.parseDouble(paidAmount)));
        PayPalPayment payPalPayment = new PayPalPayment(new BigDecimal(String.valueOf(paidAmount)), "CAD", "Employee Payment", PayPalPayment.PAYMENT_INTENT_SALE);
        Intent intent = new Intent(this, PaymentActivity.class);
        intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION, config);
        intent.putExtra(PaymentActivity.EXTRA_PAYMENT, payPalPayment);
        activityResultLauncher.launch(intent);
    }

}
