package com.example.zootypers.ui;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.zootypers.R;
import com.example.zootypers.core.MultiLeaderBoardModel;

public class PostGameScreenMulti extends PostGameScreen {

	String username;

	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// Get & display background
		setContentView(R.layout.activity_pregame_selection_multi);
		Drawable background = ((ImageButton) 
		findViewById(getIntent().getIntExtra("bg", 0))).getDrawable();

		setContentView(R.layout.activity_post_game_screen_multi);
		findViewById(R.id.postgame_layout).setBackground(background);

		// Get and display score
		score = getIntent().getIntExtra("score", 0);
		TextView finalScore = (TextView) findViewById(R.id.final_score);
		finalScore.setText(score.toString());

		// Display result of game
		TextView resultMessage = (TextView) findViewById(R.id.game_result);
		if (getIntent().getBooleanExtra("discon", false)) {
			// Opponent disconnected
			resultMessage.setText("Your Opponent Disconnected.");
			resultMessage.setTextSize(20);
			TextView opp = (TextView) findViewById(R.id.opp_final_score_text);
			opp.setText("");
		} else {
			Integer oppScore = getIntent().getIntExtra("oppScore", 0);
			TextView oppFinalScore = (TextView) findViewById(R.id.opp_final_score);
			oppFinalScore.setText(oppScore.toString());

			int result = getIntent().getIntExtra("result", 0);

			if (result == 1) {
				resultMessage.setText("You Won!");
			} else if (result == 0) {
				resultMessage.setText("You Tied!");
			} else {
				resultMessage.setText("You Lost.");
			}
		}

		username = getIntent().getStringExtra("username");
	}
	@Override
	public final void saveScore(final View view) {
		MultiLeaderBoardModel sl = new MultiLeaderBoardModel(username);
    	sl.addEntry(score);
		final String title = "Saved Score";
		final String message = "Your score has been successfully saved!";
		buildAlertDialog(title, message);
	}

	@Override
	public final void goToPreGameSelection(final View view) {
		Intent intent = new Intent(this, PreGameSelectionMulti.class);
		intent.putExtra("username", username);
		startActivity(intent);
	}

    
    // TODO remove repetition from title page / options
	/**
	 * builds an AlertDialog popup with the given title and message
	 * @param title String representing title of the AlertDialog popup
	 * @param message String representing the message of the AlertDialog
	 * popup
	 */
	private void buildAlertDialog(String title, String message) {
		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);

		// set title
		alertDialogBuilder.setTitle(title);

		// set dialog message
		alertDialogBuilder
		.setMessage(message)
		.setCancelable(false)
		.setPositiveButton("Close", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int id) {
				// if this button is clicked, close the dialog box
				dialog.cancel();
			}
		});

		// create alert dialog
		AlertDialog alertDialog = alertDialogBuilder.create();

		// show the message
		alertDialog.show();
	}


}