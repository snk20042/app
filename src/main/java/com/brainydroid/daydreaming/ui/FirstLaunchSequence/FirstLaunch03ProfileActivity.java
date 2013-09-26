package com.brainydroid.daydreaming.ui.FirstLaunchSequence;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import com.brainydroid.daydreaming.R;
import com.brainydroid.daydreaming.background.Logger;

import roboguice.inject.ContentView;
import roboguice.inject.InjectView;

/**
 * Activity at first launch
 * Asking a few questions about user
 *
 * In first launch sequence of apps
 *
 * Previous activity :  FirstLaunch02TermsActivity
 * This activity     :  FirstLaunch03ProfileActivity
 * Next activity     :  FirstLaunch04PersonalityQuestionnaireActivity
 *
 */
@ContentView(R.layout.activity_first_launch_profile)
public class FirstLaunch03ProfileActivity extends FirstLaunchActivity {

    private static String TAG = "FirstLaunch03ProfileActivity";

    public static String PROFILE_AGE = "profileAge";
    public static String PROFILE_GENDER = "profileGender";
    public static String PROFILE_EDUCATION = "profileEducation";


    public boolean GENDER_SPINNER_TOUCHED = false;
    public boolean AGE_SPINNER_TOUCHED = false;
    public boolean EDUCATION_SPINNER_TOUCHED = false;

    public SharedPreferences prefs;

    @InjectView(R.id.firstLaunchProfile_genderSpinner) Spinner genderSpinner;
    @InjectView(R.id.firstLaunchProfile_educationSpinner)   Spinner educationSpinner;
    @InjectView(R.id.firstLaunchProfile_ageSpinner) Spinner ageSpinner;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        Logger.v(TAG, "Creating");

        super.onCreate(savedInstanceState);
        prefs = getPreferences(MODE_PRIVATE);

        populate_spinners();
        setRobotofont(this);   // need to be done after spinners get populated
    }



    public void onClick_buttonNext(@SuppressWarnings("UnusedParameters") View view) {
        Logger.v(TAG, "Next button clicked");


        //Toast.makeText(this,Boolean.toString(checkForm()),Toast.LENGTH_SHORT).show();

        if (!checkForm()) {
            Logger.d(TAG, "Form check failed");
        } else {
            Logger.i(TAG, "Saving profile information to shared " +
                    "preferences_appsettings");

            SharedPreferences.Editor editor = prefs.edit();
            editor.putString(PROFILE_AGE, ageSpinner.getSelectedItem().toString());
            editor.putString(PROFILE_GENDER, genderSpinner.getSelectedItem().toString());
            editor.putString(PROFILE_EDUCATION, educationSpinner.getSelectedItem().toString());
            editor.commit();

            Logger.td(this, "{0}, {1}",
                    genderSpinner.getSelectedItem().toString(),
                    ageSpinner.getSelectedItem().toString());
            Logger.d(TAG, "Launching next activity");
            launchNextActivity(FirstLaunch04PersonalityQuestionnaireActivity.class);
        }
    }

    private boolean checkForm() {
        boolean b =   GENDER_SPINNER_TOUCHED && AGE_SPINNER_TOUCHED && EDUCATION_SPINNER_TOUCHED;
        if (!b){ Logger.d(TAG, "At least one untouched spinner");
            Toast.makeText(this,"Please answer all fields",Toast.LENGTH_SHORT).show();
        }
        return b;
    }

    /**
     * Creating adapters and listeners for each spinner
     */
    public void populate_spinners(){

        ArrayAdapter<CharSequence> adapter_gender = ArrayAdapter.createFromResource(this,R.array.genders, R.layout.spinner_layout);
        adapter_gender.setDropDownViewResource(R.layout.spinner_layout);
        genderSpinner.setAdapter(new MyGenderAdapter(getApplicationContext(), R.layout.spinner_layout_icon, R.array.genders));

        ArrayAdapter<CharSequence> adapter_education = ArrayAdapter.createFromResource(this,R.array.education, R.layout.spinner_layout);
        adapter_education.setDropDownViewResource(R.layout.spinner_layout);
        educationSpinner.setAdapter(new MyCustomAdapter(getApplicationContext(), R.layout.spinner_layout_icon, R.array.education));

        ArrayAdapter<CharSequence> adapter_age = ArrayAdapter.createFromResource(this, R.array.ages, R.layout.spinner_layout);
        adapter_age.setDropDownViewResource(R.layout.spinner_layout);
        ageSpinner.setAdapter(new MyCustomAdapter(getApplicationContext(), R.layout.spinner_layout_icon, R.array.ages));


        genderSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (i>0){GENDER_SPINNER_TOUCHED = true;} else {GENDER_SPINNER_TOUCHED = false;}
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {GENDER_SPINNER_TOUCHED = false;}
        });

        educationSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (i>0){EDUCATION_SPINNER_TOUCHED = true;} else {EDUCATION_SPINNER_TOUCHED = false;}
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {EDUCATION_SPINNER_TOUCHED = false;}
        });

        ageSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (i>0){AGE_SPINNER_TOUCHED = true;} else {AGE_SPINNER_TOUCHED = false;}
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) { AGE_SPINNER_TOUCHED = false;}
        });


    }

    /**
     * Special adapter
     * - to fill particular layout of gender (containing icons)
     * - to disable initial from possible selection
     */
    public class MyGenderAdapter extends ArrayAdapter<String>{


        /**
         * Constructor of adapter from string array
         * @param context
         * @param textViewResourceId
         * @param objects
         */
        public MyGenderAdapter(Context context, int textViewResourceId, String[] objects) {
            super(context, textViewResourceId, objects);
        }

        /**
         * Contructor of adapter from id of string array
         * @param context
         * @param textViewResourceId
         * @param stringArrayResourceId
         */
        public MyGenderAdapter(Context context, int textViewResourceId, int stringArrayResourceId) {
            super(context, textViewResourceId);
            String[] objects = getResources().getStringArray(stringArrayResourceId);
            for (String s : objects) {
                add(s);
            }

        }


        @Override
        public View getDropDownView(int position, View convertView,ViewGroup parent) {
            View v =  getCustomView(position, convertView, parent);
            if (position == 0){v.setVisibility(View.INVISIBLE);}
            return v;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            return getCustomView(position, convertView, parent);
        }

        public View getCustomView(int position, View convertView, ViewGroup parent) {

            LayoutInflater inflater=getLayoutInflater();
            View row=inflater.inflate(R.layout.spinner_layout_icon, parent, false);

            TextView label=(TextView)row.findViewById(R.id.spinnerTarget);
            TypedArray icons = getResources().obtainTypedArray(R.array.genders_icons);
            ImageView icon=(ImageView)row.findViewById(R.id.image);

            String s;
            Drawable gender_icon;

                s = getItem(position);
                gender_icon = icons.getDrawable(position);

            label.setText(s);
            icon.setImageDrawable(gender_icon);


            //label.setText(strings[position]);
            //icon.setImageResource(arr_images[position]);




            return row;
        }
    }

    /**
     * Special adapter with simple_spinner_layout, to disable initial from possible selection
     */
    public class MyCustomAdapter extends ArrayAdapter<String>{


        /**
         * Constructor of adapter from string array
         * @param context
         * @param textViewResourceId
         * @param objects
         */
        public MyCustomAdapter(Context context, int textViewResourceId, String[] objects) {
            super(context, textViewResourceId, objects);
        }

        /**
         * Contructor of adapter from id of string array
         * @param context
         * @param textViewResourceId
         * @param stringArrayResourceId
         */
        public MyCustomAdapter(Context context, int textViewResourceId, int stringArrayResourceId) {
            super(context, textViewResourceId);
            String[] objects = getResources().getStringArray(stringArrayResourceId);
            for (String s : objects) {
                add(s);
            }

        }


        @Override
        public View getDropDownView(int position, View convertView,ViewGroup parent) {
            View v =  getView(position, convertView, parent);
            if (position == 0){v.setVisibility(View.INVISIBLE);}
            return v;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            return getCustomView(position, convertView, parent);
        }

        public View getCustomView(int position, View convertView, ViewGroup parent) {

            LayoutInflater inflater=getLayoutInflater();
            View row=inflater.inflate(R.layout.spinner_layout, parent, false);

            TextView label=(TextView)row.findViewById(R.id.spinnerTarget);
            String s= getItem(position);
            label.setText(s);


            //label.setText(strings[position]);
            //icon.setImageResource(arr_images[position]);




            return row;
        }
    }

}


