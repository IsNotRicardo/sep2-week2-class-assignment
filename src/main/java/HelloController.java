import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.text.MessageFormat;
import java.util.Arrays;
import java.util.Locale;
import java.util.ResourceBundle;

public class HelloController {
    private static final String BUNDLE_BASE_NAME = "messages";
    private ResourceBundle messages;
    private final Locale[] supportedLocales = {
            new Locale.Builder().setLanguage("en").setRegion("US").build(),
            new Locale.Builder().setLanguage("fr").setRegion("FR").build(),
            new Locale.Builder().setLanguage("ja").setRegion("JP").build(),
            new Locale.Builder().setLanguage("fa").setRegion("IR").build()
    };

    @FXML
    public Label madeByLabel;
    @FXML
    public Label languageLabel;
    @FXML
    public ComboBox<String> languageComboBox;
    @FXML
    public Label distanceLabel;
    @FXML
    public TextField distanceField;
    @FXML
    public Label fuelLabel;
    @FXML
    public TextField fuelField;
    @FXML
    public Button calculateButton;
    @FXML
    public Label resultLabel;

    @FXML
    public void initialize() {
        Arrays.stream(supportedLocales).forEach(language -> {
            String languageName = language.getDisplayLanguage(language);
            languageComboBox.getItems().add(languageName);
        });
        languageComboBox.addEventHandler(ActionEvent.ACTION, event -> changeLanguage());
        Platform.runLater(() -> languageComboBox.setValue("English"));
    }

    @FXML
    public void onCalculateClick() {
        try {
            double distance = Double.parseDouble(distanceField.getText());
            double fuel = Double.parseDouble(fuelField.getText());
            double result = fuel / distance * 100;
            resultLabel.setText(MessageFormat.format(messages.getString("result.label"), String.format("%.2f", result)));
        } catch (NumberFormatException e) {
            resultLabel.setText(messages.getString("invalid.input"));
        }
    }

    private void loadText() {
        madeByLabel.setText(MessageFormat.format(messages.getString("madeBy.label"), "Ricardo de Sousa"));
        languageLabel.setText(messages.getString("language.label"));
        distanceLabel.setText(messages.getString("distance.label"));
        fuelLabel.setText(messages.getString("fuel.label"));
        calculateButton.setText(messages.getString("calculate.button"));
        resultLabel.setText(messages.getString("result.sample"));
    }

    private void changeLanguage() {
        String language = languageComboBox.getValue();
        Locale newLocale = null;

        for (Locale locale : supportedLocales) {
            if (locale.getDisplayLanguage(locale).equals(language)) {
                newLocale = locale;
                break;
            }
        }

        if (newLocale == null) {
            resultLabel.setText("Unsupported language");
            return;
        }

        messages = ResourceBundle.getBundle(BUNDLE_BASE_NAME, newLocale);
        loadText();
    }
}
