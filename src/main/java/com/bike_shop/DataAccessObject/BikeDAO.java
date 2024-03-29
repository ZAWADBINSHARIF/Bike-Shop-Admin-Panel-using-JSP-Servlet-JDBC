package com.bike_shop.DataAccessObject;

import com.bike_shop.bike_shop.Bike;
import com.bike_shop.connectionProvider.ConnectionProvider;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class BikeDAO {


    public static List<Bike> getAllBikes() {
        List<Bike> allBikesList = null;
        try {
            Connection connection = ConnectionProvider.createConnection();
            String query = "SELECT * FROM bikes";

            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();

            allBikesList = new ArrayList<>();

            while (resultSet.next()) {

                allBikesList.add(
                        new Bike(
                        resultSet.getInt("id"),
                        resultSet.getString("bike_name"),
                        resultSet.getString("description"),
                        resultSet.getString("company"),
                        resultSet.getFloat("engine_power"),
                        resultSet.getFloat("price"),
                        resultSet.getString("img")
                ));
            }

            System.out.println(allBikesList.getFirst());

            connection.close();
            
        } catch (Exception error) {
            System.out.println(error.getMessage());
        }

        return allBikesList;
    }

    public static boolean insertBike(Bike bike) {
        try {
            Connection connection = ConnectionProvider.createConnection();
            String query = "INSERT INTO bikes(bike_name, company, description, engine_power, price, img) VALUES(?,?,?,?,?,?)";

            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, bike.bike_name());
            statement.setString(2, bike.company());
            statement.setString(3, bike.description());
            statement.setFloat(4, bike.engine_power());
            statement.setFloat(5, bike.price());
            statement.setString(6, bike.image());

            statement.executeUpdate();
            connection.close();
            return true;
        } catch (Exception error) {
            System.out.println(error.getMessage());

            return false;
        }
    }

    public static boolean deleteBike(int id) {
        String deleteQuery = "DELETE FROM bikes WHERE id=?";
        Connection connection = ConnectionProvider.createConnection();

        try (PreparedStatement statement = connection.prepareStatement(deleteQuery)){
            statement.setInt(1, id);

            statement.executeUpdate();

            connection.close();
            return true;
        } catch (Exception error) {
            System.out.println(error.getMessage());

            return false;
        }
    }

}
