package edu.msoe.lab2;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatAutoCompleteTextView;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.google.android.material.snackbar.Snackbar;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;

import static androidx.core.content.ContextCompat.getSystemService;

public class FirstFragment extends Fragment {
    final String AVG = "";
    final String DEAN = "You are on the Dean's List!";
    final String DEAN_HIGH = "You are on the Dean's List with High Honors!";
    final String PROBATION = "You will be placed on Academic Probation";
    boolean creditIssue = false;
    AutoCompleteTextView autoTextView;
    String[] course_array = {""};
    double finalGPA;


    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_first, container, false);

    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle savedInstanceState){
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putBundle("BUNDY", savedInstanceState);
        savedInstanceState.putDouble("finalGpa",finalGPA);


    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(savedInstanceState!=null){

            savedInstanceState.getBundle("BUNDY");
            savedInstanceState.getDouble("finalGpa");
        }
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        try {
            course_array = getResources().getStringArray(R.array.known_courses_array);
            ArrayAdapter<String> adapter = new ArrayAdapter<>
                    (view.getContext(), android.R.layout.select_dialog_item, course_array);

            //setting up the autocomplete for the first column
            auto(view.findViewById(R.id.row0col1), adapter);
            auto(view.findViewById(R.id.row1col1), adapter);
            auto(view.findViewById(R.id.row2col1), adapter);
            auto(view.findViewById(R.id.row3col1), adapter);
            auto(view.findViewById(R.id.row4col1), adapter);
            auto(view.findViewById(R.id.row5col1), adapter);
            auto(view.findViewById(R.id.row6col1), adapter);
            auto(view.findViewById(R.id.row7col1), adapter);


        } catch (Exception e) {
            e.printStackTrace();
        }

        //logic for help button
        view.findViewById(R.id.help_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(FirstFragment.this.getContext());
                builder.setTitle("Formatting for each column:");
                builder.setMessage("Course Name: \nEnter a string\n\n# of Credits: \nEnter a positive number\n\nLetter Grade: " +
                        "\nEnter grade A, AB, B, BC, C, CD, D, or F\n\n*Press FAB to add courses (up to 8)");
                builder.setCancelable(false);
                builder.setNegativeButton("Exit", null);
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
                alertDialog.getWindow().setLayout(1000, 1000);

            }
        });

        //logic for FAB to add courses
        view.findViewById(R.id.floatingActionButton).setOnClickListener(new View.OnClickListener() {
            final TextView row4col0 = view.findViewById(R.id.row4col0);
            final TextView row5col0 = view.findViewById(R.id.row5col0);
            final TextView row6col0 = view.findViewById(R.id.row6col0);
            final TextView row7col0 = view.findViewById(R.id.row7col0);

            final EditText row4col1 = view.findViewById(R.id.row4col1);
            final EditText row5col1 = view.findViewById(R.id.row5col1);
            final EditText row6col1 = view.findViewById(R.id.row6col1);
            final EditText row7col1 = view.findViewById(R.id.row7col1);

            final EditText row4col2 = view.findViewById(R.id.row4col2);
            final EditText row5col2 = view.findViewById(R.id.row5col2);
            final EditText row6col2 = view.findViewById(R.id.row6col2);
            final EditText row7col2 = view.findViewById(R.id.row7col2);

            //col3 elements
            final EditText row4col3 = view.findViewById(R.id.row4col3);
            final EditText row5col3 = view.findViewById(R.id.row5col3);
            final EditText row6col3 = view.findViewById(R.id.row6col3);
            final EditText row7col3 = view.findViewById(R.id.row7col3);

            final ScrollView scrollView = view.findViewById(R.id.scrollView2);

            @Override
            public void onClick(View view) {
                //if the fourth column is not showing then show it
                if (!row4col1.isShown()) {
                    row4col0.setVisibility(View.VISIBLE);
                    row4col1.setVisibility(View.VISIBLE);
                    row4col2.setVisibility(View.VISIBLE);
                    row4col3.setVisibility(View.VISIBLE);

                } else if (!row5col1.isShown()) {
                    row5col0.setVisibility(View.VISIBLE);
                    row5col1.setVisibility(View.VISIBLE);
                    row5col2.setVisibility(View.VISIBLE);
                    row5col3.setVisibility(View.VISIBLE);

                } else if (!row6col1.isShown()) {
                    row6col0.setVisibility(View.VISIBLE);
                    row6col1.setVisibility(View.VISIBLE);
                    row6col2.setVisibility(View.VISIBLE);
                    row6col3.setVisibility(View.VISIBLE);


                } else if (!row7col1.isShown()) {
                    row7col0.setVisibility(View.VISIBLE);
                    row7col1.setVisibility(View.VISIBLE);
                    row7col2.setVisibility(View.VISIBLE);
                    row7col3.setVisibility(View.VISIBLE);

                } else {
                    Snackbar.make(view, "Maximum courses is 8", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }
            }
        });

        //logic for fab to remove courses
        view.findViewById(R.id.floatingActionButton2).setOnClickListener(new View.OnClickListener() {
            final TextView row4col0 = view.findViewById(R.id.row4col0);
            final TextView row5col0 = view.findViewById(R.id.row5col0);
            final TextView row6col0 = view.findViewById(R.id.row6col0);
            final TextView row7col0 = view.findViewById(R.id.row7col0);

            final EditText row4col1 = view.findViewById(R.id.row4col1);
            final EditText row5col1 = view.findViewById(R.id.row5col1);
            final EditText row6col1 = view.findViewById(R.id.row6col1);
            final EditText row7col1 = view.findViewById(R.id.row7col1);

            final EditText row4col2 = view.findViewById(R.id.row4col2);
            final EditText row5col2 = view.findViewById(R.id.row5col2);
            final EditText row6col2 = view.findViewById(R.id.row6col2);
            final EditText row7col2 = view.findViewById(R.id.row7col2);

            //col3 elements
            final EditText row4col3 = view.findViewById(R.id.row4col3);
            final EditText row5col3 = view.findViewById(R.id.row5col3);
            final EditText row6col3 = view.findViewById(R.id.row6col3);
            final EditText row7col3 = view.findViewById(R.id.row7col3);

            @Override
            public void onClick(View v) {
                if (row7col1.isShown()) {
                    row7col0.setVisibility(View.INVISIBLE);
                    row7col1.setVisibility(View.INVISIBLE);
                    row7col2.setVisibility(View.INVISIBLE);
                    row7col3.setVisibility(View.INVISIBLE);

                } else if (row6col1.isShown()) {
                    row6col0.setVisibility(View.INVISIBLE);
                    row6col1.setVisibility(View.INVISIBLE);
                    row6col2.setVisibility(View.INVISIBLE);
                    row6col3.setVisibility(View.INVISIBLE);

                } else if (row5col1.isShown()) {
                    row5col0.setVisibility(View.INVISIBLE);
                    row5col1.setVisibility(View.INVISIBLE);
                    row5col2.setVisibility(View.INVISIBLE);
                    row5col3.setVisibility(View.INVISIBLE);

                } else if (row4col1.isShown()) {
                    row4col0.setVisibility(View.INVISIBLE);
                    row4col1.setVisibility(View.INVISIBLE);
                    row4col2.setVisibility(View.INVISIBLE);
                    row4col3.setVisibility(View.INVISIBLE);

                } else {
                    Snackbar.make(view, "Minimum courses is 4", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }
            }
        });

        try {
            //logic fo calculation listener for col 2 and 3
            TextWatcher textWatcher = new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    calculation_logic(view);
                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            };

            //Adding the listener to all cells in columns 2 and 3
            ((EditText) (view.findViewById(R.id.row0col2))).addTextChangedListener(textWatcher);
            ((EditText) (view.findViewById(R.id.row1col2))).addTextChangedListener(textWatcher);
            ((EditText) (view.findViewById(R.id.row2col2))).addTextChangedListener(textWatcher);
            ((EditText) (view.findViewById(R.id.row3col2))).addTextChangedListener(textWatcher);
            ((EditText) (view.findViewById(R.id.row4col2))).addTextChangedListener(textWatcher);
            ((EditText) (view.findViewById(R.id.row5col2))).addTextChangedListener(textWatcher);
            ((EditText) (view.findViewById(R.id.row6col2))).addTextChangedListener(textWatcher);
            ((EditText) (view.findViewById(R.id.row7col2))).addTextChangedListener(textWatcher);

            ((EditText) (view.findViewById(R.id.row0col3))).addTextChangedListener(textWatcher);
            ((EditText) (view.findViewById(R.id.row1col3))).addTextChangedListener(textWatcher);
            ((EditText) (view.findViewById(R.id.row2col3))).addTextChangedListener(textWatcher);
            ((EditText) (view.findViewById(R.id.row3col3))).addTextChangedListener(textWatcher);
            ((EditText) (view.findViewById(R.id.row4col3))).addTextChangedListener(textWatcher);
            ((EditText) (view.findViewById(R.id.row5col3))).addTextChangedListener(textWatcher);
            ((EditText) (view.findViewById(R.id.row6col3))).addTextChangedListener(textWatcher);
            ((EditText) (view.findViewById(R.id.row7col3))).addTextChangedListener(textWatcher);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * Helper method to carry out the gpa calculations, set the snackbar messages
     * and take care exception handling
     *
     * @param view the current view, used to gain acces to the EditText elements
     */
    public void calculation_logic(View view) {
        double numCredits;
        ArrayList<EditText> col2 = new ArrayList<>();
        ArrayList<EditText> col3 = new ArrayList<>();

        final TextView row4col0 = view.findViewById(R.id.row4col0);
        final TextView row5col0 = view.findViewById(R.id.row5col0);
        final TextView row6col0 = view.findViewById(R.id.row6col0);
        final TextView row7col0 = view.findViewById(R.id.row7col0);

        final EditText row0col1 = view.findViewById(R.id.row0col1);
        final EditText row1col1 = view.findViewById(R.id.row1col1);
        final EditText row2col1 = view.findViewById(R.id.row2col1);
        final EditText row3col1 = view.findViewById(R.id.row3col1);

        final EditText row4col1 = view.findViewById(R.id.row4col1);
        final EditText row5col1 = view.findViewById(R.id.row5col1);
        final EditText row6col1 = view.findViewById(R.id.row6col1);
        final EditText row7col1 = view.findViewById(R.id.row7col1);

        //col2 elements
        col2.add(view.findViewById(R.id.row0col2));
        col2.add(view.findViewById(R.id.row1col2));
        col2.add(view.findViewById(R.id.row2col2));
        col2.add(view.findViewById(R.id.row3col2));
        col2.add(view.findViewById(R.id.row4col2));
        col2.add(view.findViewById(R.id.row5col2));
        col2.add(view.findViewById(R.id.row6col2));
        col2.add(view.findViewById(R.id.row7col2));

        //col3 elements
        col3.add(view.findViewById(R.id.row0col3));
        col3.add(view.findViewById(R.id.row1col3));
        col3.add(view.findViewById(R.id.row2col3));
        col3.add(view.findViewById(R.id.row3col3));
        col3.add(view.findViewById(R.id.row4col3));
        col3.add(view.findViewById(R.id.row5col3));
        col3.add(view.findViewById(R.id.row6col3));
        col3.add(view.findViewById(R.id.row7col3));


        try {
            hideSoftKeyboard(view.getContext(), view);
            try {
                //Calculating the total number of credits based on input
                //for final calculation
                numCredits = calculate_num_credits(col2);

            } catch (NumberFormatException e) {
                //if there is an issue with the credits column sets a boolean
                hideSoftKeyboard(view.getContext(), view);
                creditIssue = true;
                throw new NumberFormatException("credits issue");

            }
            //checking if the course name col has empty cells
            ArrayList<EditText> col1 = new ArrayList<>();
            col1.add(row0col1);
            col1.add(row1col1);
            col1.add(row2col1);
            col1.add(row3col1);
            col1.add(row4col1);
            col1.add(row5col1);
            col1.add(row6col1);
            col1.add(row7col1);

            checkCol1(col1);


            DecimalFormat df = new DecimalFormat("0.0##");

            //calls helper method to multiply the number of credits with the grade points earned
            //finalGPA is the sum of all of the rows
            finalGPA = 0;
            for (int i = 0; i < col2.size(); i++) {
                EditText editText1 = col2.get(i);
                EditText editText2 = col2.get(i);

                if ((editText1 != null) && (editText2 != null)) {
                    if (editText1.isShown() && editText2.isShown()) {
                        finalGPA += checkCell(col3.get(i),
                                col2.get(i));
                    }
                }
            }
            finalGPA = finalGPA / numCredits;

            //makes keyboard disappear on button click
            hideSoftKeyboard(view.getContext(), view);

            //handling separate messages based on gpa
            String gpa_message;
            if (finalGPA < 2.0) gpa_message = PROBATION;
            else if (finalGPA >= 3.7) gpa_message = DEAN_HIGH;
            else if (finalGPA > 3.2 && finalGPA < 3.7) gpa_message = DEAN;
            else gpa_message = AVG;

            //final gpa message
            Snackbar.make(view, "GPA: " + df.format(finalGPA) + "\n" + gpa_message, Snackbar.LENGTH_LONG)
                    .setAction("Action", null).setDuration(5000).show();

        } catch (NullPointerException e) {
            hideSoftKeyboard(view.getContext(), view);
            Snackbar.make(view, "Course information missing, please fill in all sections", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
            e.printStackTrace();

        } catch (NumberFormatException e) {
            hideSoftKeyboard(view.getContext(), view);

            //textView.onEditorAction(EditorInfo.IME_ACTION_DONE);
            if (creditIssue) {
                Snackbar.make(view, "Error in '# of credits' column, press HELP button for formatting information", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            } else {
                Snackbar.make(view, "1: Formatting error in one or more sections, press HELP button for formatting information", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }

        } catch (IOException e) {
            //textView.onEditorAction(EditorInfo.IME_ACTION_DONE);
            hideSoftKeyboard(view.getContext(), view);
            Snackbar.make(view, "2: Formatting error in one or more sections, press help button for info", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
            e.printStackTrace();

        }

    }

    //Method to hide keyboard borrowed from Robert Mirabelle ar rmirabelle.medium.com
    public void hideSoftKeyboard(Context context, View view) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Activity.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    /**
     * Helper method to calculate the total number of credits entered by the user
     *
     * @param column2 passes in an array of all of the column 2 entries
     * @return double representing the total number of credits
     */
    private double calculate_num_credits(ArrayList<EditText> column2) {
        double final_credits = 0;
        EditText editText;
        for (int i = 0; i < column2.size(); i++) {
            editText = column2.get(i);
            if (editText != null) {
                if (editText.isShown()) {
                    final_credits += Double.parseDouble(column2.get(i).getText().toString());
                }
            }
        }
        System.out.println(final_credits);

        return final_credits;
    }

    /**
     * Helper method to implement the autocomplete functiopnality
     *
     * @param autoTextView the  desired auto-completing text view
     * @param adapter      the adapter containing an array of strings
     */
    private void auto(AutoCompleteTextView autoTextView, ArrayAdapter<String> adapter) {
        autoTextView.setThreshold(1); //will start working from first character
        autoTextView.setAdapter(adapter);
    }

    /**
     * Helper method to check for invalid input and complete the multiplication of the number
     * of credits and the grade points earned for the associated letter grade for one
     * individual row.
     *
     * @param letter  EditText representing the rows letter grade input
     * @param credits EditText representing the rows credit number input
     * @throws IOException           Thrown if the letter grade input is invalid
     * @throws NumberFormatException Thrown if number of credits entered is negative
     */
    double checkCell(EditText letter, EditText credits) throws IOException, NumberFormatException, NullPointerException {
        if (letter.getText() == null) {
            throw new NullPointerException("letter grade column is null");

        }
        if (!letter.getText().toString().matches("(?i)A|AB|B|BC|C|CD|D|F")) {
            if (letter.getText().toString().matches("")) {
                throw new NullPointerException("missing info in col3");
            } else {
                throw new IOException("not valid letter grade");
            }
        }

        String letterGrade = letter.getText().toString();
        System.out.println(letterGrade);
        double grade_points_earned = 0.0;

        //checking to make sure letter grade input is valid, then assigning grade points based on letter
        if (letterGrade.equalsIgnoreCase("A")) {
            grade_points_earned = 4.0;
        } else if (letterGrade.equalsIgnoreCase("AB")) {
            grade_points_earned = 3.5;
        } else if (letterGrade.equalsIgnoreCase("B")) {
            grade_points_earned = 3.0;
        } else if (letterGrade.equalsIgnoreCase("BC")) {
            grade_points_earned = 2.5;
        } else if (letterGrade.equalsIgnoreCase("C")) {
            grade_points_earned = 2.0;
        } else if (letterGrade.equalsIgnoreCase("CD")) {
            grade_points_earned = 1.5;
        } else if (letterGrade.equalsIgnoreCase("D")) {
            grade_points_earned = 1.0;
        } else if (letterGrade.equalsIgnoreCase("F")) {
            grade_points_earned = 0.0;
        }

        //if input for credit is negative, throws exception
        double credit = Double.parseDouble(credits.getText().toString());
        if (credit < 0) throw new NumberFormatException("negative");

        //this point means the credit value is valid
        creditIssue = false;
        return credit * grade_points_earned;
    }

    /**
     * Helper method to check for missing information in column 1, separate
     * method because the only possible error is pointing to empty cell
     *
     * @param col1 ArrayList of EditText components representing column 1
     * @throws NullPointerException thrown if any of the cells in col1 are empty
     */
    void checkCol1(ArrayList<EditText> col1) throws NullPointerException {
        String current;
        EditText editText;
        for (int i = 0; i < col1.size(); i++) {
            editText = col1.get(i);
            if (editText != null && editText.isShown()) {
                current = col1.get(i).getText().toString();
                //if empty
                if (current.equals("")) {
                    throw new NullPointerException("missing info in col1");
                }

            }


        }

    }

}