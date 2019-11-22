import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import java.lang.NumberFormatException;
import java.text.DecimalFormat;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;

/*
 * Split Bill Calculator
 * Team: Anime Geeks
 * Students: Jonathan Scarpelli, Ruben Yanez, Abigail Singh,
 * Tahmid Shahnia, Thomas Chen, Ahmed Moowad, Lawrence Williams
 * November 15, 2019
 * Program Description: Splits a Bill Evenly
 */
public class SplitBillCalculator extends Application {
  @Override
  public void start(Stage primaryStage) {
    // Creating Labels
    Text text1 = new Text("Subtotal:");
    Text text2 = new Text("Tax:");
    Text text3 = new Text("Tip:");
    Text text4 = new Text("Split:");

        final Spinner<Integer> spinner = new Spinner<Integer>();
        final int initialValue = 1;
        // Value factory.
        SpinnerValueFactory<Integer> valueFactory = //
                new SpinnerValueFactory.IntegerSpinnerValueFactory(1,
20, initialValue);
        spinner.setValueFactory(valueFactory);

    // Creating Text Input Fields
    TextField subtotal = new TextField();
    TextField tax = new TextField();
    TextField split = new TextField();

    // Creating Buttons
    Button buttonCalculate = new Button("Calculate");
    Button buttonClear = new Button("Clear");
    Button buttonExit = new Button("Exit");

    ComboBox tipComboBox = new ComboBox();
    tipComboBox.getItems().add("15");
    tipComboBox.getItems().add("17.5");
    tipComboBox.getItems().add("20");

    // Creating a Grid Pane
    GridPane gridPane = new GridPane();
    gridPane.setMinSize(450, 250);
    gridPane.setPadding(new Insets(10, 10, 10, 10));
    gridPane.setVgap(5);
    gridPane.setHgap(5);
    gridPane.setAlignment(Pos.CENTER);

    // Adding Grid Objects
    gridPane.add(text1, 0, 0);
    gridPane.add(subtotal, 1, 0);
    gridPane.add(text2, 0, 1);
    gridPane.add(tax, 1, 1);
    gridPane.add(text3, 0, 2);
    gridPane.add(tipComboBox, 1, 2);
    gridPane.add(text4, 0, 3);
    gridPane.add(spinner, 1, 3);
    gridPane.add(buttonCalculate, 3, 0);
    gridPane.add(buttonClear, 3, 1);
    gridPane.add(buttonExit, 3, 2);

    // Creating Results Output
    final Label label = new Label();
    label.setWrapText(true);
    GridPane.setConstraints(label, 0, 4);
    GridPane.setColumnSpan(label, 2);
    gridPane.getChildren().add(label);

    // Setting an Action for the Calculate button
    buttonCalculate.setOnAction(new EventHandler<ActionEvent>() {
    @Override
    public void handle(ActionEvent e) throws NumberFormatException {
      try {
        double subtotalValue;
        double taxValue;
        double tipValue;
        int splitValue;
        double total;
        DecimalFormat df = new DecimalFormat("#.00");
        df.setGroupingUsed(true);
        df.setGroupingSize(3);

        // Data Validation on Null Values
        if (subtotal.getText() != null && tax.getText() != null
          && spinner.getValue() != null && tipComboBox.getValue() != null) {
          subtotalValue = Double.parseDouble(subtotal.getText());
          taxValue = Double.parseDouble(tax.getText());
          String selected = tipComboBox.getValue().toString();
          tipValue = Double.parseDouble(selected);
          System.out.println("tip value: " + tipValue);
          splitValue = spinner.getValue();
          System.out.println("split value: " + splitValue);

          // Data Validation on Negative Values
          if (subtotalValue <= 0 || taxValue < 0 || splitValue < 1 ||
tipValue < 0) {
            label.setText("Values must be positive.");
          } else {
            total = total(subtotalValue, taxValue, tipValue);
            label.setText("Your total bill is $" + df.format(total)
            + " with " + tipValue + "% tip and split "
            + splitValue + " ways is $"
                + df.format(billPerPerson(total, splitValue)));
           }
        }else {
            label.setText("Please input valid numbers.");
          }
        } catch (NumberFormatException error) {
            label.setText("Please input valid numbers.");
          }
        }
    });

      // Setting an Action for the Clear button
      buttonClear.setOnAction(new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent e) {
          subtotal.clear();
          tax.clear();
          tipComboBox.setValue(null);
          split.clear();
          label.setText(null);
          spinner.getValueFactory().setValue(1);
        }
      });

      // Setting an Action for the Exit button
      buttonExit.setOnAction(new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent e) {
           primaryStage.close();
        }
      });

      // Creating a Scene and Adding to the Stage
      Scene scene = new Scene(gridPane);
      primaryStage.setTitle("Split Bill Calculator");
      primaryStage.setScene(scene);
      primaryStage.show();
  }

  // Calculates Total Bill Including Sales Tax
  public double total(double sub, double tax, double tip) {
    double damage = 0;

    if (tax > 0 && tax < 1) {
      damage += sub * tax;
      damage += sub * (tip / 100);
    } else {
      damage += sub * (tax / 100);
      damage += sub * (tip / 100);
    }
    return damage;
  }

  // Calculates How Much of the Bill Each Person Pays
  public double billPerPerson(double total, int people) {
    return total / people;
  }

  public static void main(String args[]) {
    launch(args);
  }
}
