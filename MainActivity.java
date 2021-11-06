package com.ivanivsky.quizapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.content.SharedPreferences.Editor;

import androidx.appcompat.app.AppCompatActivity;

import android.service.controls.actions.BooleanAction;
import android.text.Editable;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    /*** Global variables to track the user's answers to the questions.
     I'm pretty sure these shouldn't be global variables, but I don't know
     how to pass these along, especially since the user is capable of changing their
     answer until they hit the Submit button.
     My guess is that the answers could be stored somewhere, like Shared Preferences, but
     I don't know how to do that yet. I gave it a try but found myself spending too much
     time trying to figure it out.
     */

    int totalScore;
    boolean isHands;
    boolean isShins;
    boolean isKnees;
    boolean isElbows;
    boolean isHead;
    boolean isRaj;
    boolean isThaphe;
    boolean isLumpinee;
    int kickScore = 0;
    int artScore = 0;
    int ritualScore = 0;
    int popularityScore = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        totalScore = 0;
    }

    /**
     * @param score - the score passed in from each of the evaluation methods.
     *              - Each method passes in a score of either 0 (incorrect) or 1 (correct)
     *              - The total score gets calculated here.
     */

    private void calculateScore(int score) {
        totalScore = score + totalScore;
        Log.v("totalscore", "Total score: " + totalScore);
    }

    /**
     * @param country - this is the country that was provided as an answer
     *                - converted it to all caps before string comparison to accommodate user supplied differences
     *                when it comes to capitalization.
     */

    private void evaluateQuestionCountry(String country) {
        int scoreQuestion = 0;
        String answer = "THAILAND";
        String countryCaps = country.toUpperCase();
        boolean compareStrings = countryCaps.equals(answer);
        if (compareStrings) {
            scoreQuestion = 1;
        } else {
            scoreQuestion = 0;
        }

        // Takes the score (either 1 (correct) or 0 (incorrect)) and passes it to
        // calculateScore to include it in the final score.
        calculateScore(scoreQuestion);
    }

    /*** Evaluates if the answer provided to the grappling question is correct or incorrect,
     and assigns a score accordingly. It then passes that score to calculateScore
     which calculates the total score.
     */

    private void evaluateQuestionGrappling(String grappling) {
        int scoreQuestion = 0;
        String answer = "CLINCH";
        String grapplingCaps = grappling.toUpperCase();
        boolean compareStrings = grapplingCaps.equals(answer);
        if (compareStrings) {
            scoreQuestion = 1;
        } else {
            scoreQuestion = 0;
        }
        calculateScore(scoreQuestion);
    }

    /***
     *
     * @param view - takes in the RadioButton view when clicked.
     * - Assigns a score of 0 if the answer is incorrect.
     * - Assigns a score of 1 if the answer is correct.
     * - Stores the answer in a global variable that's
     */

    public void onRadioButtonClickedKicks(View view) {
        int score = 0;
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch (view.getId()) {
            case R.id.radio_questionmark:
                if (checked)
                    score = 0;
                break;
            case R.id.radio_switch:
                if (checked)
                    score = 0;
                break;
            case R.id.radio_teep:
                if (checked)
                    score = 1;
                break;
        }

        /***
         Stores the score in global variable kickScore, so that it can be accessed when the user
         hits the Submit button.. Probably should be stored in
         Shared Preferences or something, but not sure how to do that yet
         */

        kickScore = score;
    }

    /*** This was needed to separate the onClick method and the resulting score that can be
     accessed when the Submit button is clicked.
     There has to be a better way to do this but it works.
     */

    private void evaluateQuestionKicking() {
        // Sends the score to calculateScore which calculates the total score.
        calculateScore(kickScore);
    }

    /**
     * @param view The next several methods take in a checkbox view that's used to
     *             evaluate the question about which body parts are used.
     *             The boolean values are then stored in global variables that are
     *             then accessed by the evaluateQuestionBodyParts method which
     *             evaluates the results when the Submit button is clicked.
     */

    public void checkHands(View view) {
        CheckBox handsCheckbox = findViewById(R.id.checkbox_hands);
        isHands = handsCheckbox.isChecked();
    }

    public void checkShins(View view) {
        CheckBox shinsCheckbox = findViewById(R.id.checkbox_shins);
        isShins = shinsCheckbox.isChecked();
    }

    public void checkKnees(View view) {
        CheckBox kneesCheckbox = findViewById(R.id.checkbox_knees);
        isKnees = kneesCheckbox.isChecked();
    }

    public void checkElbows(View view) {
        CheckBox elbowsCheckbox = findViewById(R.id.checkbox_elbows);
        isElbows = elbowsCheckbox.isChecked();
    }

    public void checkHead(View view) {
        CheckBox headCheckbox = findViewById(R.id.checkbox_head);
        isHead = headCheckbox.isChecked();
    }

    /**
     * When the Submit button is clicked, this method gets called to evaluate the answer
     * to the question about body parts. It then calculates the points and then passes that
     * along to calculateScore which calculates the total score.
     */

    public void evaluateQuestionBodyParts() {
        int points = 0;
        if (isElbows && isHands && !isHead && isKnees && isShins) {
            points = 1;
        } else {
            points = 0;
        }

        calculateScore(points);
    }

    /**
     * @param view - responds to the radio buttons related to the question about Muay Thai
     *             being referred to as a kind of art.
     *             If the user selects the right radio button the score = 1. Otherwise, the
     *             score is 0.
     *             The score is then preserved in a global variable called artScore.
     */

    public void onRadioButtonClickedArt(View view) {
        int score = 0;
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch (view.getId()) {
            case R.id.radio_eightlimbs:
                if (checked)
                    score = 1;
                break;
            case R.id.radio_mortalcombat:
                if (checked)
                    score = 0;
                break;
            case R.id.radio_crushingblows:
                if (checked)
                    score = 0;
                break;
        }

        artScore = score;

    }

    /***
     * evaluateQuestionArt is called when the user hits the submit button.
     * It grabs the score from the global variable artScore and then passes it to
     * calculateScore where the total score is calculated.
     */

    private void evaluateQuestionArt() {
        int score = artScore;
        calculateScore(score);
    }

    /***
     *
     * @param view Responds to the user answering the question related to the pre-fight ritual
     *             It assigns a score of 1 for the correct score, and 0 for the
     *             incorrect score.
     *             It then stores the score in a global variable called ritualScore.
     */

    public void onRadioButtonClickedRitual(View view) {
        int score = 0;
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch (view.getId()) {
            case R.id.radio_thaifu:
                if (checked)
                    score = 0;
                break;
            case R.id.radio_wutang:
                if (checked)
                    score = 0;
                break;
            case R.id.radio_waikru:
                if (checked)
                    score = 1;
                break;
        }
        ritualScore = score;

    }

    /***
     * Called when the user clicks the submit button.
     * Grabs the global variable ritualScore and passes the score to calculateScore which calculates
     * the total score.
     */

    private void evaluateQuestionRitual() {
        int score = ritualScore;
        calculateScore(score);
    }

    /***
     * Responds to the user answering the question related to the sport that
     * contributed to Muay Thai popularity.
     * It assigns a score of 1 for the correct score, and 0 for the
     * incorrect score.
     * It then stores the score in the global variable popularityScore.
     */

    public void onRadioButtonClickedPopularity(View view) {
        int score = 0;
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch (view.getId()) {
            case R.id.radio_boxing:
                if (checked)
                    score = 0;
                break;
            case R.id.radio_jiujitsu:
                if (checked)
                    score = 0;
                break;
            case R.id.radio_mma:
                if (checked)
                    score = 1;
                break;
        }
        popularityScore = score;
    }

    /***
     * Is called when the user selects the submit button. It grabs global variable and
     * then sends the score to calculateScore where the total score is calculated.
     */

    private void evaluateQuestionPopularity() {
        int score = popularityScore;
        calculateScore(score);
    }

    /***
     *
     * @param view The next three methods respond to the user selecting the checkboxes
     *             related to the popular Muay Thai stadiums.
     *
     *             The boolean values are stored in global variables which are used
     *             when the user hits the submit button.
     */

    public void checkRaj(View view) {
        CheckBox rajCheckbox = findViewById(R.id.checkbox_rajadamnern);
        isRaj = rajCheckbox.isChecked();
    }

    public void checkThaphae(View view) {
        CheckBox thaphaeCheckbox = findViewById(R.id.checkbox_thaphae);
        isThaphe = thaphaeCheckbox.isChecked();
    }

    public void checkLumpinee(View view) {
        CheckBox lumpineeCheckbox = findViewById(R.id.checkbox_lumpinee);
        isLumpinee = lumpineeCheckbox.isChecked();
    }

    /***
     * Is called when the user hits the submit button. It grabs the boolean global variables
     * for the checkbox selections. It then performs some logic to determine if the user
     * checked the correct boxes.
     * If the user selected all the right boxes, the score is 1. Otherwise, it's 0.
     * All three boxes are correct, so all need to be selected for the answer to be correct.
     * It then calls calculateScore which calculates the total score.
     */

    private void evaluateQuestionStadiums() {
        int points = 0;
        if (isRaj && isThaphe && isLumpinee) {
            points = 1;
        } else {
            points = 0;
        }
        calculateScore(points);
    }

    /***
     * Displays a toast message telling the user their score and their grade.
     */

    private void displayScore() {
        String grade;

        if (totalScore <= 4) {
            grade = "unsatisfactory.";
        } else if (totalScore <= 6) {
            grade = "satisfactory.";
        } else {
            grade = "great!";
        }

        String score = Integer.toString(totalScore);
        String text = "Your score is " + score + " out of 8, which is " + grade;

        Toast.makeText(this, text, Toast.LENGTH_LONG).show();
    }

    /***
     * Sets the totalScore to 0.
     */

    private void resetQuiz() {
        totalScore = 0;
    }

    /***
     *
     * @param view  Responds to the user selecting the submit button.
     *              This is where all the evaluation methods are called and the points
     *              begin to get assessed and totaled.
     */

    public void submitAnswers(View view) {

        /**
         * Gets answer for country question and passes it to evaluateQuestionCountry for analysis.
         */

        EditText countryEditable = findViewById(R.id.question_country);
        String country = countryEditable.getText().toString();
        evaluateQuestionCountry(country);

        evaluateQuestionKicking();

        evaluateQuestionBodyParts();

        evaluateQuestionArt();

        /**
         * Gets answer for grappling question and passes it to evaluateQuestionGrappling for analysis.
         */

        EditText grapplingEditable = findViewById(R.id.question_grappling);
        String grappling = grapplingEditable.getText().toString();
        evaluateQuestionGrappling(grappling);

        evaluateQuestionRitual();

        evaluateQuestionPopularity();

        evaluateQuestionStadiums();

        displayScore();

        resetQuiz();
    }
}