package edu.msoe.lab2;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.GridLayout;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.google.android.material.snackbar.Snackbar;

import java.io.IOException;
import java.lang.reflect.Array;
import java.text.DecimalFormat;
import java.util.ArrayList;

public class FirstFragment extends Fragment {
    final String AVG = "";
    final String DEAN = "You are on the Dean's List!";
    final String DEAN_HIGH = "You are on the Dean's List with High Honors!";
    final String PROBATION = "You will be placed on Academic Probation";
    boolean creditIssue = false;
    boolean courseIssue = false;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_first, container, false);
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        view.findViewById(R.id.help_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(FirstFragment.this.getContext());
                builder.setTitle("Formatting for each column:");
                builder.setMessage("Course Name: \nEnter a string\n\n# of Credits: \nEnter a positive number\n\nLetter Grade: \nEnter grade A, AB, B, BC, C, CD, D, or F");
                builder.setCancelable(false);
                builder.setNegativeButton("Exit", null);
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
                alertDialog.getWindow().setLayout(1000, 850);

            }
        });

        view.findViewById(R.id.button_first).setOnClickListener(new View.OnClickListener() {
            double numCredits = 0.0;
            String check;
            final EditText row0col1 = view.findViewById(R.id.row0col1);
            final EditText row1col1 = view.findViewById(R.id.row1col1);
            final EditText row2col1 = view.findViewById(R.id.row2col1);
            final EditText row3col1 = view.findViewById(R.id.row3col1);

            //col2 elements
            final EditText row0col2 = view.findViewById(R.id.row0col2);
            final EditText row1col2 = view.findViewById(R.id.row1col2);
            final EditText row2col2 = view.findViewById(R.id.row2col2);
            final EditText row3col2 = view.findViewById(R.id.row3col2);

            //col3 elements
            final EditText row0col3 = view.findViewById(R.id.row0col3);
            final EditText row1col3 = view.findViewById(R.id.row1col3);
            final EditText row2col3 = view.findViewById(R.id.row2col3);
            final EditText row3col3 = view.findViewById(R.id.row3col3);

            @Override
            public void onClick(View view) {
                try {
                    row2col2.onEditorAction(EditorInfo.IME_ACTION_DONE);
                    try {
                        //Calculating the total number of credits based on input
                        //for final calculation
                        numCredits = Double.parseDouble(row0col2.getText().toString())
                                + Double.parseDouble(row1col2.getText().toString())
                                + Double.parseDouble(row2col2.getText().toString())
                                + Double.parseDouble(row3col2.getText().toString());

                    } catch (NumberFormatException e) {
                        row2col2.onEditorAction(EditorInfo.IME_ACTION_DONE);
                        creditIssue = true;
                    }
                    //checking if the course name col has empty cells
                    ArrayList<EditText> col1 = new ArrayList<>();
                    col1.add(row0col1);
                    col1.add(row1col1);
                    col1.add(row2col1);
                    col1.add(row3col1);

                    checkCol1(col1);

                    DecimalFormat df = new DecimalFormat("0.0##");

                    //calls helper method to multiply the number of credits with the grade points earned
                    //finalGPA is the sum of all of the rows
                    double finalGPA = ((checkCell(row0col3, row0col2)
                            + checkCell(row1col3, row1col2)
                            + checkCell(row2col3, row2col2)
                            + checkCell(row3col3, row3col2)) / numCredits);

                    //makes keyboard disappear on button click
                    row3col2.onEditorAction(EditorInfo.IME_ACTION_DONE);

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
                    row2col2.onEditorAction(EditorInfo.IME_ACTION_DONE);
                    Snackbar.make(view, "Course information missing, please fill in all sections", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();

                } catch (NumberFormatException e) {
                    row2col2.onEditorAction(EditorInfo.IME_ACTION_DONE);
                    if (creditIssue) {
                        Snackbar.make(view, "Error in '# of credits' column, press HELP button for formatting information", Snackbar.LENGTH_LONG)
                                .setAction("Action", null).show();
                    } else {
                        Snackbar.make(view, "1: Formatting error in one or more sections, press HELP button for formatting information", Snackbar.LENGTH_LONG)
                                .setAction("Action", null).show();
                    }

                } catch (IOException e) {
                    row2col2.onEditorAction(EditorInfo.IME_ACTION_DONE);
                    Snackbar.make(view, "2: Formatting error in one or more sections, press help button for info", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                    e.printStackTrace();

                }
            }
        });

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
        if (!letter.getText().toString().matches("(?i)A|AB|B|BC|C|CD|D|F")) {
            if (letter.getText().toString().matches("")) {
                throw new NullPointerException("missing info");
            } else {
                throw new IOException("not valid letter grade");
            }
        }
        String letterGrade = letter.getText().toString();
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
        for (int i = 0; i < 4; i++) {
            current = col1.get(i).getText().toString();
            //if empty
            if (current.equals("")) {
                throw new NullPointerException("missing info");
            }


        }

    }

}