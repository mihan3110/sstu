package sstu.lora.dao;

import org.springframework.ui.Model;
import sstu.lora.domain.DevInfo;

import java.lang.reflect.InvocationTargetException;
import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class DbConnection {
    public static Connection initConnection() {
        try {
            Class.forName("org.sqlite.JDBC").getDeclaredConstructor().newInstance();
            String url = "jdbc:sqlite:C:/Users/Mike/Desktop/Smart City/solve/work/w/sstu/server.db";
            Connection con = DriverManager.getConnection(url);
            return con;
        } catch (SQLException | ClassNotFoundException | NoSuchMethodException | IllegalAccessException | InstantiationException | InvocationTargetException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Map<Long, String> getSpaces(Long userId) throws SQLException {
        Connection con = initConnection();
        Map<Long, String> result = new HashMap<Long, String>();
        try {
            PreparedStatement st = con.prepareStatement("select sp.idspace, sp.name from space sp, userspaces ud where ud.iduser=? and sp.idspace=ud.idspace");
            st.setLong(1, userId);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {

                Long idspace = rs.getLong(1);
                String name = rs.getString(2);
                result.put(idspace, name);

            }
            con.close();
            return result;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return result;
    }

    public static ArrayList<DevInfo> getDevices(Long userId, Long idspace) throws SQLException {
        Connection con = initConnection();
        ArrayList<DevInfo> list = new ArrayList<DevInfo>();
        try {

            PreparedStatement st = con.prepareStatement("select ud.deveui, dev.devname, ud.address from userdevices ud, devices dev where iduser=? and idspace=? and ud.deveui=dev.deveui");
            st.setLong(1, userId);
            st.setLong(2, idspace);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                String adr[];
                String deveui = rs.getString(1);
                String name = rs.getString(2);
                String address = rs.getString(3);
                DevInfo devInfo = new DevInfo(name, deveui, address);

                list.add(devInfo);

            }
            con.close();
            return list;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public static String getDevName(String devui) throws SQLException {
        ArrayList<String> devData = new ArrayList<String>();
        Connection con = initConnection();
        String devname = "неопознан";
        try {
            PreparedStatement st = con.prepareStatement("select devname from devices where deveui = ?");
            st.setString(1, devui);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                devname = rs.getString(1);
            }

            con.close();
            return devname;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return devname;
    }

    public static ArrayList<String> getData(String devui) throws SQLException {
        ArrayList<String> devData = new ArrayList<String>();
        Connection con = initConnection();

        try {
            PreparedStatement st = con.prepareStatement("select data from rawdata where deveui = ?");
            //   ResultSet rs = statement.executeQuery("select data from rawdata where deveui = '"+devui + "'");
            st.setString(1, devui);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {

                byte[] bytes = rs.getBytes(1);
                if (null != bytes) {
                    devData.add(Arrays.toString(bytes).replaceAll("\\[", "").replaceAll("]", "").replaceAll(" ", ""));
                }
            }

            con.close();
            return devData;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return devData;
    }


    public static void addSpace(Long userid, String spaceName) {
        try {
            Connection con = initConnection();
            PreparedStatement pstmt = con.prepareStatement("INSERT INTO space (name) values (?)");
            pstmt.setString(1, spaceName);
            pstmt.execute();
            PreparedStatement psmt1 = con.prepareStatement("INSERT INTO userspaces (iduser, idspace) values (?, (select idspace from space where name = ?))");
            psmt1.setLong(1, userid);
            psmt1.setString(2, spaceName);
            psmt1.execute();
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public static void addDevice(Long userid, Long idspace, String deveui, String address) {
        try {
            Connection con = initConnection();
            PreparedStatement psmt = con.prepareStatement("INSERT INTO userdevices (iduser, deveui, idspace, address) values (?, ?, ?, ?)");
            psmt.setLong(1, userid);
            psmt.setString(2, deveui);
            psmt.setLong(3, idspace);
            psmt.setString(4, address);
            psmt.execute();
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public static boolean checkdevice(Long userid, String deveui) {
        boolean result = false;
        try {
            Connection con = initConnection();
            PreparedStatement pstmt = con.prepareStatement("select deveui from devices where deveui = ? and not EXISTS(select deveui from userdevices where deveui=? and iduser=?)");
            pstmt.setString(1, deveui);
            pstmt.setString(2, deveui);
            pstmt.setLong(3, userid);

            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                result = true;
            }
            con.close();
            return result;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static boolean checkpace(String spaceName, Long iduser) {
        boolean result = false;
        try {
            Connection con = initConnection();
            PreparedStatement pstmt = con.prepareStatement("select idspace from userspaces where iduser = ? and idspace = (select idspace from space where name = ?)");
            pstmt.setLong(1, iduser);
            pstmt.setString(2, spaceName);
            ResultSet rs = pstmt.executeQuery();
            if (!rs.next()) {
                result = true;
            }
            con.close();
            return result;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static void deleteSpace(Long spaceId) {
        try {
            Connection con = initConnection();
            PreparedStatement pstmt = con.prepareStatement("DELETE FROM userspaces where idspace = ?");
            PreparedStatement pstmt1 = con.prepareStatement("DELETE FROM userdevices where idspace =  ?");
            PreparedStatement pstmt2 = con.prepareStatement("DELETE FROM space where idspace = ?");
            pstmt.setLong(1, spaceId);
            pstmt1.setLong(1, spaceId);
            pstmt2.setLong(1, spaceId);
            pstmt.execute();
            pstmt1.execute();
            pstmt2.execute();
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void deleteDevice(String deveui, Long iduser) {
        try {
            Connection con = initConnection();
            PreparedStatement pstmt = con.prepareStatement("DELETE FROM userdevices where deveui = ? and iduser = ?");
            pstmt.setString(1, deveui);
            pstmt.setLong(2, iduser);
            pstmt.execute();
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static Map<String, String> getAllDevices() throws SQLException {
        Connection con = initConnection();
        Map<String, String> result = new HashMap<String, String>();
        try {

            PreparedStatement st = con.prepareStatement("select deveui, devname from devices");

            ResultSet rs = st.executeQuery();
            while (rs.next()) {

                String deveui = rs.getString(1);
                String name = rs.getString(2);
                if (null != deveui && null != name) {
                    result.put(name, deveui);
                }
            }
            con.close();
            return result;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return result;
    }


    public static void prepareData() {

        try {
            Connection con = initConnection();
            PreparedStatement psmt = con.prepareStatement("select rawcount from rawcount");
            ResultSet rs = psmt.executeQuery();
            Integer countOld = rs.getInt(1);

            PreparedStatement psmt1 = con.prepareStatement("select count(*) from rawdata");
            ResultSet rs1 = psmt1.executeQuery();
            Integer countNew = rs1.getInt(1);
            Integer div = countNew - countOld;

            if (div>0){
                PreparedStatement psmt2 = con.prepareStatement("UPDATE rawcount SET rawcount = ?");
                psmt2.setInt(1,countNew);
                psmt2.executeQuery();

                    PreparedStatement psmt3 =  con.prepareStatement("insert into scheduler select deveui, data from rawdata order by time desc limit ?");
                    psmt3.setInt(1, div);
                    ResultSet rs3 = psmt3.executeQuery();
            }
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static ArrayList<String> sendDataToWS() {
        try {
            Connection con = initConnection();
            PreparedStatement pstmt4 = con.prepareStatement("select sc.data, ud.address from userdevices ud, scheduler sc where sc.deveui=ud.deveui and ud.address is not null and sc.data is not null");
            ArrayList<String> result = new ArrayList<String>();
            ResultSet rs4 = pstmt4.executeQuery();
            while (rs4.next()) {

                byte[] bytes = rs4.getBytes(1);
                String addr1 = rs4.getString(2);
                if (null != bytes) {

                    String addr2 = (Arrays.toString(bytes).replaceAll("\\[", "").replaceAll("]", "").replaceAll(" ", ""));
                    result.add(addr1+"?data="+addr2);
                }
            }
            PreparedStatement pstmt5 = con.prepareStatement("delete from scheduler");
            pstmt5.execute();
            con.close();
            return  result;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

}
