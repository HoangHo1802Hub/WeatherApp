import org.json.simple.JSONObject;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class WeatherAppGUI extends JFrame {
    private JSONObject weatherData;
    public WeatherAppGUI() {
        // set title
        setTitle("Weather App");

        // configure gui to end the program process once it has been closed
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        // set the size of gui
        setSize(450, 650);

        // load gui at the center of the screen
        setLocationRelativeTo(null);

        // make our layout null
        setLayout(null);

        // prevent event resize
        setResizable(false);

        initComponents();

    }

    public void initComponents(){
//       search text
        JTextField searchText = new JTextField();

//      set size
        searchText.setBounds(15,15,351, 45);

//      change font style and size
        searchText.setFont(new Font("Dialog", Font.PLAIN, 24));

        add(searchText);

//      search button
        JButton btnSearch = new JButton(loadImage("src/assets/search.png"));

//      change cursor
        btnSearch.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btnSearch.setBounds(375, 13, 47, 45);

        add(btnSearch);

        JLabel weatherImage = new JLabel(loadImage("src/assets/cloudy.png"));
        weatherImage.setBounds(0, 125, 450 ,217);
        add(weatherImage);

//      temperature text
        JLabel temperatureText = new JLabel("10 C");
        temperatureText.setBounds(0, 350, 450 ,54);
        temperatureText.setFont(new Font("Dialog", Font.BOLD, 48));
        temperatureText.setHorizontalAlignment(SwingConstants.CENTER);
        add(temperatureText);

//      weather condition description
        JLabel weatherConditionDes = new JLabel("Cloudy");
        weatherConditionDes.setBounds(0, 405, 450, 36);
        weatherConditionDes.setFont(new Font("Dialog", Font.PLAIN, 32));
        weatherConditionDes.setHorizontalAlignment(SwingConstants.CENTER);
        add(weatherConditionDes);

//      humidity image
        JLabel humidityImage = new JLabel(loadImage("src/assets/humidity.png"));
        humidityImage.setBounds(15, 500, 74, 66);
        add(humidityImage);

//      humidity text
        JLabel humidityText = new JLabel("<html><b>Humidity</b> 100%</html>");
        humidityText.setBounds(90, 500, 85, 55);
        humidityText.setFont(new Font("Dialog", Font.PLAIN, 16));
        add(humidityText);

//      windspeed image
        JLabel windSpeedImage = new JLabel(loadImage("src/assets/windspeed.png"));
        windSpeedImage.setBounds(220, 500, 74, 66);
        add(windSpeedImage);

//      windspeed text
        JLabel windSpeedText = new JLabel("<html><b>Windspeed</b> 15km/h</html>");
        windSpeedText.setBounds(310, 500, 85, 55);
        windSpeedText.setFont(new Font("Dialog", Font.PLAIN, 16));
        add(windSpeedText);

        btnSearch.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String userInput = searchText.getText().trim();

                if (userInput.replaceAll("\\s", "").length() <= 0) {
                    return;
                }

                weatherData = WeatherApp.getWeatherData(userInput);
//                update gui

//                update image
                String weatherCondition = (String) weatherData.get("weather_condition");

                switch (weatherCondition) {
                    case "Clear" -> {
                        weatherImage.setIcon(loadImage("src/assets/clear.png"));
                    }
                    case "Cloudy" -> {
                        weatherImage.setIcon(loadImage("src/assets/cloudy.png"));
                    }
                    case "Rain" -> {
                        weatherImage.setIcon(loadImage("src/assets/rain.png"));
                    }
                    case "Snow" -> {
                        weatherImage.setIcon(loadImage("src/assets/snow.png"));
                    }
                }

                double temperature = (double) weatherData.get("temperature");
                temperatureText.setText(String.valueOf(temperature) + " C");

                weatherConditionDes.setText(weatherCondition);

                long humidity = (long) weatherData.get("humidity");
                humidityText.setText("<html><b>Humidity</b>" + humidity + "%</html>");

                double windspeed = (double) weatherData.get("windspeed");
                windSpeedText.setText("<html><b>Windspeed</b>" + windspeed + "km/h</html>");

            }
        });
    }

    private ImageIcon loadImage(String resourcesPath) {
        try {
            BufferedImage img = ImageIO.read(new File(resourcesPath));
            return new ImageIcon(img);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
