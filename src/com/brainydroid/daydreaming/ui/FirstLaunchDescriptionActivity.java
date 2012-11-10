package com.brainydroid.daydreaming.ui;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.View;

import com.brainydroid.daydreaming.R;
import com.brainydroid.daydreaming.background.StatusManager;

public class FirstLaunchDescriptionActivity extends ActionBarActivity {

	private static String TAG = "FirstLaunchDescriptionActivity";

	private StatusManager status;

	@Override
	public void onCreate(Bundle savedInstanceState) {

		// Debug
		Log.d(TAG, "[fn] onCreate");

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_first_launch_description);

		status = StatusManager.getInstance(this);
	}

	@Override
	public void onStart() {

		// Debug
		Log.d(TAG, "[fn] onStart");

		super.onStart();
		checkFirstLaunch();
	}

	@Override
	public void onResume() {

		// Debug
		Log.d(TAG, "[fn] onResume");

		super.onResume();
	}

	@Override
	public void onBackPressed() {

		// Debug
		Log.d(TAG, "[fn] onBackPressed");

		super.onBackPressed();
		overridePendingTransition(R.anim.push_right_in, R.anim.push_right_out);
	}

	public void onClick_buttonNext(View view) {

		// Debug
		Log.d(TAG, "[fn] onClick_buttonNext");

		DialogFragment consentAlert = ConsentAlertDialogFragment.newInstance(
				R.string.consentAlert_title,
				R.string.consentAlert_text,
				R.string.consentAlert_button_notconsent,
				R.string.consentAlert_button_consent);
		consentAlert.show(getSupportFragmentManager(), "consentAlert");
	}

	private void launchProfileActivity() {

		// Debug
		Log.d(TAG, "[fn] launchProfileActivity");

		Intent intent = new Intent(this, FirstLaunchProfileActivity.class);
		intent.setFlags(Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
		startActivity(intent);
		overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
	}

	private void checkFirstLaunch() {

		// Debug
		Log.d(TAG, "[fn] checkFirstLaunch");

		if (status.isFirstLaunchCompleted() || status.isClearing()) {
			finish();
		}
	}

	public static class ConsentAlertDialogFragment extends DialogFragment {

		private static String TAG = "ConsentAlertDialogFragment";

		private StatusManager status;

		public static ConsentAlertDialogFragment newInstance(int title, int text,
				int negText, int posText) {

			// Debug
			Log.d(TAG, "[fn] newInstance");

			ConsentAlertDialogFragment frag = new ConsentAlertDialogFragment();
			Bundle args = new Bundle();
			args.putInt("title", title);
			args.putInt("text", text);
			args.putInt("negText", negText);
			args.putInt("posText", posText);
			frag.setArguments(args);
			return frag;
		}

		@Override
		public Dialog onCreateDialog(Bundle savedInstanceState) {

			// Debug
			Log.d(TAG, "[fn] onCreateDialog");

			status = StatusManager.getInstance(getActivity());

			int title = getArguments().getInt("title");
			int text = getArguments().getInt("text");
			int negText = getArguments().getInt("negText");
			int posText = getArguments().getInt("posText");

			AlertDialog.Builder alertSettings = new AlertDialog.Builder(getActivity())
			.setTitle(title)
			.setMessage(text)
			.setNegativeButton(negText,
					new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int whichButton) {
					dialog.cancel();
				}
			})
			.setPositiveButton(posText,
					new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int whichButton) {
					((FirstLaunchDescriptionActivity)getActivity()).launchProfileActivity();
				}
			}).setIcon(Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB ?
					R.drawable.ic_action_about_holo_light : R.drawable.ic_action_about_holo_dark);

			status.setFirstLaunchStarted();
			return alertSettings.create();
		}
	}
}