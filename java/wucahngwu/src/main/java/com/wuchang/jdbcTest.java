package com.wuchang;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.sql.*;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Created by lenovo on 2018/1/26.
 */
public class jdbcTest {

    private static final String URL="jdbc:mysql://192.168.126.133:3306/world";
    private static final String USERNAME="root";
    private static final String PASSWORD="root";
    private static Connection connection=null;

    static {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            connection= DriverManager.getConnection(URL,USERNAME,PASSWORD);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }




    public static List<Map<String,Object>> getObjext(Connection connection, String sql){
       List<Map<String,Object>> maps =new LinkedList<>();
        PreparedStatement preparedStatement=null;
        try {
            preparedStatement =connection.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();
            ResultSetMetaData metaData = resultSet.getMetaData();
            int columnCount = metaData.getColumnCount();
            while (resultSet.next()){
                 Map<String,Object> map =new HashMap<>();
                for (int i = 1; i <=columnCount ; i++) {
                    map.put(metaData.getColumnLabel(i),resultSet.getObject(i));
                }
                maps.add(map);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return maps;
    }
    public static Connection getConnection(){
        return  connection;
    }


    public static void close(AutoCloseable autoCloseable){
        if(autoCloseable !=null){
            try {
                autoCloseable.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) throws JsonProcessingException {
         String sql ="SELECT * FROM sutdent";
        List<Map<String, Object>> objext = getObjext(getConnection(), sql);
        close(getConnection());
        ObjectMapper objectMapper =new ObjectMapper();
        String s = objectMapper.writeValueAsString(objext);
        System.out.println(s);


    }

}
