// Manas Kumar Rai (W1607801)
package Helper;
import Models.*;
//import com.sun.codemodel.internal.JStatement;
import UI.Users;

import java.sql.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
// DB Helper is called upon whenever we need to establish a connection to the database as well as populate it through SQL commands or for GUI display
public class DBHelper {
    public  Connection connection;
    public DBHelper(){

    }
    public void DBConnect() {
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver").newInstance();
        } catch (Exception e) {
            System.err.println("Unable to load driver.");
            e.printStackTrace();
        }
        try {
            connection = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "xe", "xe");
        }catch (Exception e){
            System.err.println("Unable to connect");
            e.printStackTrace();
        }
    }

    public void DBClose(){
        try{
            connection.close();
        }
        catch (Exception e){
            System.err.println("Unable to close db");
            e.printStackTrace();
        }
    }

    public void TruncateUsers(){
        try {
            PreparedStatement statement = connection.prepareStatement("DROP TABLE USERS");
            statement.execute();
            statement.close();

        }catch (SQLException se){
            System.err.println("Query error: SQL Exception");
            se.printStackTrace();
        }catch (Exception e){
            System.err.println("Query error ");
            e.printStackTrace();
        }
    }

    public void TruncateBusiness(){
        try {
            PreparedStatement statement = connection.prepareStatement("TRUNCATE TABLE BUSINESS");
            statement.execute();
            statement.close();
            

            PreparedStatement statement1 = connection.prepareStatement("TRUNCATE TABLE BUSINESS_HOURS");
            statement1.execute();
            statement1.close();

            PreparedStatement statement2= connection.prepareStatement("TRUNCATE TABLE BUSINESS_CAT");
            statement2.execute();
            statement2.close();

            PreparedStatement statement3 = connection.prepareStatement("TRUNCATE TABLE BUSINESS_SUBCAT");
            statement3.execute();
            statement3.close();

            PreparedStatement statement4 = connection.prepareStatement("TRUNCATE TABLE BUSINESS_ATTR");
            statement4.execute();
            statement4.close();


        }catch (SQLException se){
            System.err.println("Query error: SQL Exception");
            se.printStackTrace();
        }catch (Exception e){
            System.err.println("Query error ");
            e.printStackTrace();
        }
    }

    public void CreateUsersTable(){
        try {
            PreparedStatement st = connection.prepareStatement("CREATE TABLE Users(userId VARCHAR2(50) primary key, name VARCHAR2(50), yelpingSince date,  votesFunny int, votesUseful int, votesCool int, review_count int , fans int, averageStars float, totalVotes int )");
            st.execute();
            st.close();
        }catch (SQLException se){
            System.err.println("Query error: SQL Exception");
            se.printStackTrace();
        }catch (Exception e){
            System.err.println("Query error ");
            e.printStackTrace();
        }
    }

    public void InsertUsers(List<User> users){
        PreparedStatement statement;
        try{
            statement = connection.prepareStatement("INSERT INTO USERS (userId , name , yelpingSince,  votesFunny, votesUseful, votesCool, review_count , fans, averageStars,totalVotes ) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?,?)");

            for(User user: users){
                statement.setString(1,user.getUserId());
                statement.setString(2,user.getName());
                SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM");
                java.util.Date date = sdf1.parse(user.getYelpingSince());
                java.sql.Date sqlYelpSince = new java.sql.Date(date.getTime());
                statement.setDate(3, sqlYelpSince);
                statement.setInt(4,user.getVotesFunny());
                statement.setInt(5,user.getVotesUseful());
                statement.setInt(6,user.getVotesCool());
                statement.setInt(7,user.getReviewCount());
                statement.setInt(8,user.getFans());
                statement.setFloat(9, user.getAverageStars());
                statement.setInt(10, user.getVotesFunny() + user.getVotesCool() + user.getVotesUseful());
                statement.addBatch();
            }
            statement.executeBatch();
            statement.close();

            System.out.println("rows inserted ");

        }catch (Exception e){
            System.err.println("Query error");
            e.printStackTrace();
        }
    }

    public void TruncateUserFriends(){
        try {
            PreparedStatement statement = connection.prepareStatement("DROP TABLE UserFriends");
            statement.execute();
            statement.close();

        }catch (SQLException se){
            System.err.println("Query error: SQL Exception");
            se.printStackTrace();
        }catch (Exception e){
            System.err.println("Query error ");
            e.printStackTrace();
        }
    }

    public void CreateUserFriendsTable(){
        try{
            PreparedStatement st = connection.prepareStatement("CREATE TABLE UserFriends(userId VARCHAR2(50), friend_id VARCHAR2(50))");
            st.execute();
            st.close();
        }catch (SQLException se){
            System.err.println("Query error: SQL Exception");
            se.printStackTrace();
        }catch (Exception e){
            System.err.println("Query error ");
            e.printStackTrace();
        }
    }

    public void addUserFriends(List<User> users){
        try{
            PreparedStatement statement = connection.prepareStatement("INSERT INTO UserFriends (userId , friend_id ) VALUES (?, ?)");

            for(User user: users){
                for(Friends friend: user.getFriends()){
                    statement.setString(1,user.getUserId());
                    statement.setString(2, friend.getUserId());
                    statement.addBatch();
                }
            }
            statement.executeBatch();

            System.out.println("rows inserted ");

        }catch (Exception e){
            System.err.println("Query error");
            e.printStackTrace();
        }
    }

    public void TruncateReviewsTable(){
        try {
            PreparedStatement statement = connection.prepareStatement("DROP TABLE Reviews");
            statement.execute();
            statement.close();
        }catch (SQLException se){
            System.err.println("Query error: SQL Exception");
            se.printStackTrace();
        }catch (Exception e){
            System.err.println("Query error ");
            e.printStackTrace();
        }
    }

    public void CreateReviewsTable(){
        try{
            PreparedStatement st = connection.prepareStatement("CREATE TABLE Reviews(reviewId VARCHAR2(50) primary key , stars int check (stars between 1 and 5), publishDate date , text VARCHAR2(4000) , businessId VARCHAR2(50) , userId VARCHAR2(50),  votesFunny int, votes_cool int, votes_useful int)");
            st.execute();
            st.close();
        }catch (SQLException se){
            System.err.println("Query error: SQL Exception");
            se.printStackTrace();
        }catch (Exception e){
            System.err.println("Query error ");
            e.printStackTrace();
        }
    }

    public void addReviews(List<Reviews> reviews){
        try{
            PreparedStatement statement = connection.prepareStatement("INSERT INTO REVIEWS (reviewId, stars, publishDate, text, businessId, userId,  votesFunny, votes_cool, votes_useful ) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)");
            for(Reviews review: reviews){
                statement.setString(1,review.getReviewId());
                statement.setInt(2,review.getStars());
                SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM");
                java.util.Date date = sdf1.parse(review.getDate());
                java.sql.Date publishDate = new java.sql.Date(date.getTime());
                statement.setDate(3, publishDate);
                statement.setString(4, review.getText());
                statement.setString(5, review.getBusinessId());
                statement.setString(6, review.getUserId());
                statement.setInt(7, review.getVotesFunny());
                statement.setInt(8, review.getVotesCool());
                statement.setInt(9, review.getVotesUseful());
                statement.executeUpdate();
                //statement.addBatch();
            }
            //statement.executeBatch();
            System.out.println("rows inserted for reviews table");

        }catch (Exception e){
        e.printStackTrace();
        }
    }

    public void addBusiness(List<Business> businesses){
        try{
            PreparedStatement statement = connection.prepareStatement("INSERT INTO  BUSINESS(businessId, name, address, city, state, review_count, stars) VALUES (?, ?, ?, ?, ?, ?, ?)");
            PreparedStatement statement1 = connection.prepareStatement("INSERT  INTO BUSINESS_HOURS(businessId, day, from_time, to_time) VALUES(?, ?, ?, ?)");
            PreparedStatement statement2 = connection.prepareStatement("INSERT  INTO BUSINESS_CAT(businessId, category) VALUES(?, ?)");
            PreparedStatement statement3 = connection.prepareStatement("INSERT INTO BUSINESS_SUBCAT(businessid, subcategory)  VALUES(?,?)");
            PreparedStatement statement4 = connection.prepareStatement("INSERT INTO BUSINESS_ATTR(businessId, attr_name , attr_value)  VALUES(?,?,?)");

            for(Business business: businesses){
                statement.setString(1,business.getBusinessId());
                statement.setString(2,business.getName());
                statement.setString(3,business.getFullAddress());
                statement.setString(4,business.getCity());
                statement.setString(5,business.getState());
                statement.setInt(6,business.getReview_count());
                statement.setFloat(7, business.getStars());
                statement.addBatch();

                List<Hours> hrs = business.getHrs();
                for(Hours hr: hrs){
                    statement1.setString(1, business.getBusinessId());
                    statement1.setString(2, hr.getDay());

                    DateFormat formatter = new SimpleDateFormat("hh:mm");//2015-05-11 18:26:55
                    java.util.Date openObj = (java.util.Date)formatter.parse(hr.getOpen_time());
                    java.sql.Timestamp openTime = new java.sql.Timestamp(openObj.getTime());
                    java.util.Date closeObj = (java.util.Date)formatter.parse(hr.getClose_time());
                    java.sql.Timestamp closeTime = new java.sql.Timestamp(closeObj.getTime());

                    statement1.setTimestamp(3, openTime);
                    statement1.setTimestamp(4, closeTime);
                    statement1.addBatch();
                }

                List<BusinessCategories> bcats = business.getCategories();
                for(BusinessCategories bcat: bcats){
                    statement2.setString(1, bcat.getBusinessId());
                    statement2.setString(2, bcat.getCategoryName());
                    statement2.addBatch();
                }

                List<BusinessSubCategories> bsubcats = business.getSubCategories();
                for(BusinessSubCategories bsubcat: bsubcats){
                    statement3.setString(1, bsubcat.getBusinessId());
                    statement3.setString(2, bsubcat.getSubCategoryName());
                    statement3.addBatch();
                }

                List<Attribute> attrs = business.getAttr();
                for(Attribute attr : attrs){
                    statement4.setString(1, attr.getBusinessId());
                    statement4.setString(2, attr.getAttribute_name());
                    statement4.setString(3, attr.getAttribute_value());
                    statement4.addBatch();
                }

            }
            statement.executeBatch();
            statement1.executeBatch();
            statement2.executeBatch();
            statement3.executeBatch();
            statement4.executeBatch();
            System.out.println("rows inserted for business table");

        }catch (SQLException se){
            System.err.println("Query error: SQL Exception");
            se.printStackTrace();
        }catch (Exception e){
            System.err.println("Query error ");
            e.printStackTrace();
        }
    }

    public Map< String, Object > getAttributes(ArrayList<String> selectedSubCategories, ArrayList<String> selectedCategories, String condition){
        ArrayList<Attribute> attrs = new ArrayList<Attribute>();
        Map< String, Object > hm = new HashMap< String, Object >();
        try {
            String query = "";
            if(condition == "AND"){
                String subq = "(SELECT businessId FROM BUSINESS_CAT WHERE  category = '"+selectedCategories.get(0)+"')";
                if(selectedCategories.size() > 1){
                    for(int i=1; i<selectedCategories.size(); i++){
                        subq += " INTERSECT (SELECT businessId FROM BUSINESS_CAT WHERE  category = '"+selectedCategories.get(i) +"' )";
                    }
                }
                query = "SELECT DISTINCT ba.attr_name FROM BUSINESS_ATTR ba JOIN BUSINESS_SUBCAT bs on bs.BUSINESSID = ba.BUSINESSID JOIN BUSINESS_CAT BC on ba.BUSINESSID = BC.BUSINESSID WHERE ba.businessId IN ( "+subq+" )";

            }else {

                query = "SELECT DISTINCT ba.attr_name FROM BUSINESS_ATTR ba JOIN BUSINESS_SUBCAT bs on bs.BUSINESSID = ba.BUSINESSID JOIN BUSINESS_CAT BC on ba.BUSINESSID = BC.BUSINESSID WHERE bc.category = ";
                query += "'" + selectedCategories.get(0) + "' ";

                if (selectedCategories.size() > 1) {
                    for (int i = 1; i < selectedCategories.size(); i++) {
                        query += condition + " bc.category = '" + selectedCategories.get(i) + "' ";
                    }
                }

            }

            query += " AND bs.subcategory = '" + selectedSubCategories.get(0) + "' ";
            if (selectedSubCategories.size() > 1) {
                for (int i = 1; i < selectedSubCategories.size(); i++) {
                    query += condition + " bs.subcategory = '" + selectedSubCategories.get(i) + "' ";
                }
            }
            System.out.println(query);
            hm.put( "Query", query );
            
            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet rs = statement.executeQuery();
            while (rs.next()){
                System.out.println(rs.getString(1));
                Attribute attribute = new Attribute();
                attribute.setAttribute_name(rs.getString(1));
                attrs.add(attribute);
            }
            
        }catch (SQLException se){
            System.err.println("Query error: SQL Exception");
            se.printStackTrace();
        }catch (Exception e){
            System.err.println("Query error ");
            e.printStackTrace();
        }
        
        System.out.println("*******RESULT SET COUNT**********" + attrs.size() );
        hm.put( "resultSet", attrs );
        return hm;
    }
    
    public  Map< String, Object> getSubCategories(ArrayList<String> selectedCategories, String condition){
        ArrayList<BusinessSubCategories> subs = new ArrayList<BusinessSubCategories>();
        Map< String, Object > hm = new HashMap< String, Object >();
        try {
            String query;
            if(condition == "AND"){
                String subq = "(SELECT businessId FROM BUSINESS_CAT WHERE  category = '"+selectedCategories.get(0)+"')";
                if(selectedCategories.size() > 1){
                    for(int i=1; i<selectedCategories.size(); i++){
                        subq += " INTERSECT (SELECT businessId FROM BUSINESS_CAT WHERE  category = '"+selectedCategories.get(i) +"' )";
                    }
                }
                query = "SELECT DISTINCT bs.subcategory FROM BUSINESS_SUBCAT bs JOIN BUSINESS_CAT bc on bs.BUSINESSID = bc.BUSINESSID  WHERE bs.businessId IN ( "+subq+" )";

            }else{

                 query = "SELECT DISTINCT bs.subcategory FROM BUSINESS_SUBCAT bs JOIN BUSINESS_CAT bc on bs.BUSINESSID = bc.BUSINESSID  WHERE bc.CATEGORY = ";
                query += "'"+selectedCategories.get(0) +"' ";
                if(selectedCategories.size() > 1){
                    for(int i=1; i<selectedCategories.size(); i++){
                        query += condition+" bc.category = '"+selectedCategories.get(i) +"' ";
                    }
                }
            }
            System.out.println(query);
            hm.put( "Query",  query);

            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet rs = statement.executeQuery();
            while (rs.next()){
                System.out.println(rs.getString(1));
                BusinessSubCategories sub = new BusinessSubCategories();
                sub.setSubCategoryName(rs.getString(1));
                subs.add(sub);
            }
        }catch (SQLException se){
            System.err.println("Query error: SQL Exception");
            se.printStackTrace();
        }catch (Exception e){
            System.err.println("Query error ");
            e.printStackTrace();
        }
        hm.put( "resultSet" , subs );
        System.out.println("*******RESULT SET COUNT**********" +  subs.size() );

        return hm;
    }

    public Map< String, Object >  getAllCategories(){
        ArrayList<BusinessCategories> cats = new ArrayList<BusinessCategories>();
        Map< String, Object > hm = new HashMap< String, Object>();
        hm.put( "Query",  "SELECT DISTINCT category FROM BUSINESS_CAT  ORDER BY category");
          try {
            PreparedStatement statement = connection.prepareStatement("SELECT DISTINCT category FROM BUSINESS_CAT  ORDER BY category");
            ResultSet rs = statement.executeQuery();
            while (rs.next()){
                System.out.println(rs.getString(1));
                BusinessCategories cat = new BusinessCategories();
                cat.setCategoryName(rs.getString(1));
                cats.add(cat);
            }
        }catch (SQLException se){
            System.err.println("Query error: SQL Exception");
            se.printStackTrace();
        }catch (Exception e){
            System.err.println("Query error ");
            e.printStackTrace();
        }
       hm.put( "resultSet" , cats );
       System.out.println("*******RESULT SET COUNT**********" +  cats.size() );

       return hm;
    }

    public Map< String, Object > queryBusinessByCategory(ArrayList<String> selectedCategories, String condition, Map<String, Object> allInputValues){
        ArrayList<Business> businesses = new ArrayList<Business>();
        Map< String, Object > hm = new HashMap< String, Object >();
        String subQuery = generateReviewsSubQuery(allInputValues);
        String query;
        if(condition == "AND"){
        String subq = "SELECT businessId FROM BUSINESS_CAT WHERE  category = '"+selectedCategories.get(0)+"'";
            if(selectedCategories.size() > 1){
                for(int i=1; i<selectedCategories.size(); i++){
                    subq += " INTERSECT SELECT businessId FROM BUSINESS_CAT WHERE  category = '"+selectedCategories.get(i) +"' ";
                }
            }
            query = "SELECT DISTINCT b.businessId, b.name, b.city, b.state, b.stars  FROM BUSINESS b JOIN BUSINESS_CAT bc on bc.BUSINESSID = b.BUSINESSID JOIN REVIEWS r ON b.businessId = r.BUSINESSID  WHERE b.businessId IN ( "+subq+" )";

        }else{
            query = "SELECT DISTINCT b.businessId, b.name, b.city, b.state, b.stars  FROM BUSINESS b JOIN BUSINESS_CAT bc on bc.BUSINESSID = b.BUSINESSID JOIN REVIEWS r ON b.businessId = r.BUSINESSID  WHERE bc.category = ";
            query += "'"+selectedCategories.get(0) +"' ";
            if(selectedCategories.size() > 1){
                for(int i=1; i<selectedCategories.size(); i++){
                    query += condition+" bc.category = '"+selectedCategories.get(i) +"' ";
                }
            }
        }
        System.out.println(query + subQuery);
         query = query + subQuery;
        hm.put( "Query", query );
        try {
            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet rs = statement.executeQuery();
            while (rs.next()){
                Business b = new Business();
                b.setBusinessId(rs.getString(1));
                b.setName(rs.getString(2));
                b.setCity(rs.getString(3));
                b.setState(rs.getString(4));
                b.setStars(rs.getFloat(5));
                businesses.add(b);
            }
        }catch (SQLException se){
            System.err.println("Query error: SQL Exception");
            se.printStackTrace();
        }catch (Exception e){
            System.err.println("Query error ");
            e.printStackTrace();
        }
        hm.put( "resultSet",businesses );
        System.out.println("*******RESULT SET COUNT**********" +  businesses.size() );

        return hm;
    }

    public Map< String, Object > queryBusinessByCategorySubCategory(ArrayList<String> selectedCategories, ArrayList<String> selectedSubCategories, String condition, Map<String, Object> allInputValues){
    	Map< String, Object > hm = new HashMap< String, Object>();
        String subQuery = generateReviewsSubQuery(allInputValues);
        ArrayList<Business> businesses = new ArrayList<Business>();
        hm.put( "resultSet", businesses);
        String query = "";
        if(condition == "AND"){
            String subq = "(SELECT businessId FROM BUSINESS_CAT WHERE  category = '"+selectedCategories.get(0)+"')";
            if(selectedCategories.size() > 1){
                for(int i=1; i<selectedCategories.size(); i++){
                    subq += " INTERSECT (SELECT businessId FROM BUSINESS_CAT WHERE  category = '"+selectedCategories.get(i) +"' )";
                }
            }
            query = "SELECT DISTINCT b.businessId, b.name, b.city, b.state, b.stars  FROM BUSINESS b JOIN BUSINESS_CAT bc on bc.BUSINESSID = b.BUSINESSID  JOIN BUSINESS_SUBCAT bs ON bs.BUSINESSID = b.BUSINESSID JOIN REVIEWS r ON b.businessId = r.BUSINESSID WHERE  b.businessId IN ( "+subq+" )";

        }else {
             query = "SELECT DISTINCT b.businessId, b.name, b.city, b.state, b.stars  FROM BUSINESS b JOIN BUSINESS_CAT bc on bc.BUSINESSID = b.BUSINESSID  JOIN BUSINESS_SUBCAT bs ON bs.BUSINESSID = b.BUSINESSID JOIN REVIEWS r ON b.businessId = r.BUSINESSID WHERE bc.category = ";
            query += "'" + selectedCategories.get(0) + "' ";
            if (selectedCategories.size() > 1) {
                for (int i = 1; i < selectedCategories.size(); i++) {
                    query += condition + " bc.category = '" + selectedCategories.get(i) + "' ";
                }
            }

        }
        query += " AND bs.subcategory = '" + selectedSubCategories.get(0) + "' ";
        if (selectedSubCategories.size() > 1) {
            for (int i = 1; i < selectedSubCategories.size(); i++) {
                query += condition + " bs.subcategory = '" + selectedSubCategories.get(i) + "' ";
            }
        }
        query = query + subQuery;
        hm.put( "Query", query);
        System.out.println(query + subQuery);
        try {
            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet rs = statement.executeQuery();
            while (rs.next()){
                Business b = new Business();
                b.setBusinessId(rs.getString(1));
                b.setName(rs.getString(2));
                b.setCity(rs.getString(3));
                b.setState(rs.getString(4));
                b.setStars(rs.getFloat(5));
                businesses.add(b);
            }
        }catch (SQLException se){
            System.err.println("Query error: SQL Exception");
            se.printStackTrace();
        }catch (Exception e){
            System.err.println("Query error ");
            e.printStackTrace();
        }
        System.out.println("*******RESULT SET COUNT**********" +  businesses.size() );

        return hm;
    }

    public Map< String, Object > queryBusiness(ArrayList<String> selectedAttributes, ArrayList<String> selectedCategories, ArrayList<String> selectedSubCategories, String condition, Map<String, Object> allInputValues){
        ArrayList<Business> businesses = new ArrayList<Business>();
        Map< String, Object > hm = new HashMap< String, Object>();
        String subQuery = generateReviewsSubQuery(allInputValues);
        hm.put("resultSet", businesses);
        try {
        	
            String query = "SELECT DISTINCT b.businessId, b.name, b.city, b.state, b.stars  FROM BUSINESS b JOIN BUSINESS_ATTR ba on b.BUSINESSID = ba.BUSINESSID   JOIN BUSINESS_CAT bc on bc.BUSINESSID = b.BUSINESSID  JOIN BUSINESS_SUBCAT bs ON bs.BUSINESSID = b.BUSINESSID  JOIN REVIEWS r ON b.businessId = r.BUSINESSID WHERE ba.attr_name = ";
            query += "'"+selectedAttributes.get(0) +"' ";
            if(selectedAttributes.size() > 1){
                for(int i=1; i<selectedAttributes.size(); i++){
                    query += condition+"  ba.attr_name = '"+selectedAttributes.get(i) +"' ";
                }
            }
            query += " AND bc.category = '"+selectedCategories.get(0) +"' ";
            if(selectedCategories.size() > 1){
                for(int i=1; i<selectedCategories.size(); i++){
                    query += condition+" bc.category = '"+selectedCategories.get(i) +"' ";
                }
            }
            query += " AND bs.subcategory = '"+selectedSubCategories.get(0)+"' ";
            if(selectedSubCategories.size() > 1){
                for(int i=1; i<selectedSubCategories.size(); i++){
                    query += condition+" bs.subcategory = '"+selectedSubCategories.get(i) +"' ";
                }
            }
            System.out.println(query+subQuery);
            query = query + subQuery;
            hm.put("Query", query);	
            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet rs = statement.executeQuery();
            while (rs.next()){
                Business b = new Business();
                b.setBusinessId(rs.getString(1));
                b.setName(rs.getString(2));
                b.setCity(rs.getString(3));
                b.setState(rs.getString(4));
                b.setStars(rs.getFloat(5));
                businesses.add(b);
            }
        }catch (SQLException se){
            System.err.println("Query error: SQL Exception");
            se.printStackTrace();
        }catch (Exception e){
            System.err.println("Query error ");
            e.printStackTrace();
        }
        System.out.println("*******RESULT SET COUNT**********" +  businesses.size() );

        return hm;
    }


    public Map< String, Object> advancedQueryBusiness(ArrayList<String> selectedCategories, ArrayList<String> selectedSubCategories, ArrayList<String> selectedAttributes, String city, String state, String day, String from, String to, String condition, Map<String, Object> allInputValues){
        ArrayList<Business> businesses = new ArrayList<Business>();
        Map< String, Object > hm = new HashMap< String, Object>();
        String subQuery = generateReviewsSubQuery(allInputValues);
        hm.put("resultSet", businesses);
        try {
            String query = "SELECT DISTINCT b.businessId, b.name, b.city, b.state, b.stars  FROM BUSINESS b  LEFT JOIN BUSINESS_ATTR ba ON b.BUSINESSID = ba.BUSINESSID LEFT JOIN BUSINESS_CAT bc on bc.BUSINESSID = b.BUSINESSID  LEFT JOIN BUSINESS_SUBCAT bs ON bs.BUSINESSID = b.BUSINESSID LEFT JOIN BUSINESS_HOURS bh ON b.BUSINESSID = bh.BUSINESSID JOIN REVIEWS r ON b.businessId = r.BUSINESSID  WHERE bc.category =  ";
            query += " '"+selectedCategories.get(0) +"' ";
            if(selectedCategories.size() > 1){
                for(int i=1; i<selectedCategories.size(); i++){
                    query += condition+" bc.category = '"+selectedCategories.get(i) +"' ";
                }
            }
            query += " AND ba.attr_name =  '"+selectedAttributes.get(0) +"' ";
            if(selectedAttributes.size() > 1){
                for(int i=1; i<selectedAttributes.size(); i++){
                    query += condition+"  ba.attr_name = '"+selectedAttributes.get(i) +"' ";
                }
            }

            query += " AND bs.subcategory = '"+selectedSubCategories.get(0)+"' ";
            if(selectedSubCategories.size() > 1){
                for(int i=1; i<selectedSubCategories.size(); i++){
                    query += condition+" bs.subcategory = '"+selectedSubCategories.get(i) +"' ";
                }
            }

            if(city.length() > 0 && city!= null && !city.isEmpty()){
                query += " AND b.city  LIKE  '%"+ city+"%' ";
            }
            if(state.length() > 0 && state!= null && !state.isEmpty()){
                query += "AND b.state  LIKE  '%"+ state+"%' ";
            }
            if(day.length() > 0 && day != null && !day.isEmpty()){
                query += "AND bh.day = '"+day+"'";
            }
            if(from.length() > 0 && from != null && !from.isEmpty()){
                String fromDate = "1970-01-01 "+from+":00";
                query += "AND from_time >= to_timestamp('"+fromDate+"', 'yyyy-mm-dd hh24:mi:ss')";
            }
            if(to.length() > 0 && to != null && !to.isEmpty()){
                String toDate = "1970-01-01 "+to+":00";
                query += "AND to_time <= to_timestamp('"+toDate+"', 'yyyy-mm-dd hh24:mi:ss')";
            }
            query = query + subQuery;
            System.out.println(query + subQuery);
            hm.put("Query",query);
            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet rs = statement.executeQuery();
            while (rs.next()){
                Business b = new Business();
                b.setBusinessId(rs.getString(1));
                b.setName(rs.getString(2));
                b.setCity(rs.getString(3));
                b.setState(rs.getString(4));
                b.setStars(rs.getFloat(5));
                businesses.add(b);
            }
        }catch (SQLException se){
            System.err.println("Query error: SQL Exception");
            se.printStackTrace();
        }catch (Exception e){
            System.err.println("Query error ");
            e.printStackTrace();
        }
        System.out.println("*******RESULT SET COUNT**********" +  businesses.size() );

        return hm;
    }

    public Map< String, Object > getReviews(String businessId, Map<String, Object> allInputValues){
        ArrayList<Reviews> reviews = new ArrayList<Reviews>();
       Map< String, Object > hm = new HashMap< String, Object >();
       hm.put( "resultSet", reviews);
        String query = generateReviewsQuery( businessId,allInputValues );
        hm.put( "Query", query );
        System.out.println( query );
        try {
            //String query = "SELECT r.publishDate, r.stars, r.text, u.name, r.VOTES_USEFUL + r.votes_useful +r.votesfunny FROM  REVIEWS r JOIN Users u ON u.userId = r.userId WHERE  r.BUSINESSID = '"+businessId+"'" ;
            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet rs = statement.executeQuery();
            while (rs.next()){
                Reviews r = new Reviews();
                r.setDate(rs.getDate(1).toString());
                r.setStars(rs.getInt(2));
                r.setText(rs.getString(3));
                r.setUserId(rs.getString(4));
                r.setVotesUseful(rs.getInt(5));
                reviews.add(r);
            }

        }catch (SQLException se){
            System.err.println("Query error: SQL Exception");
            se.printStackTrace();
        }catch (Exception e){
            System.err.println("Query error ");
            e.printStackTrace();
        }
        System.out.println("*******RESULT SET COUNT**********" +  reviews.size() );

        return hm;
    }
    
    private String generateReviewsQuery(String businessId, Map<String, Object> allInputValues) {
    	StringBuilder query = new StringBuilder( "SELECT r.publishDate, r.stars, r.text, u.name,  r.VOTES_USEFUL + r.votes_useful +r.votesfunny FROM  REVIEWS r JOIN Users u ON u.userId = r.userId WHERE  r.BUSINESSID = '"+businessId+"'" );
    	for (Map.Entry<String, Object> entry : allInputValues.entrySet() )
		{
			String key = (String) entry.getKey();

			switch( key  )
			{
				case "fromDate" :   	query.append( " " + allInputValues.get("andOR") + " " );
										query.append( "r.PUBLISHDATE >=").append( "'" + allInputValues.get("fromDate") + "'" ).append( " AND r.PUBLISHDATE <=").append( "'" + allInputValues.get("toDate") + "'" );
								   	    break;
										
				case "starsValue" :     query.append( " " + allInputValues.get("andOR") + " " );
										query.append( "r.stars ").append( " " + allInputValues.get("starOperator") + " " ).append( " " + allInputValues.get("starsValue") + " ");
										break;
				case "votesValue" :  query.append( " " + allInputValues.get("andOR") + " " );
										query.append( "( r.VOTES_USEFUL + r.votes_useful +r.votesfunny )  ").append( " " + allInputValues.get("votesOperator") + " " ).append( " " + allInputValues.get("votesValue") + " ");
										break;
			}
		}
		
	return query.toString();
	}
    
    private String generateReviewsSubQuery(Map<String, Object> allInputValues) {
    	StringBuilder query = new StringBuilder( "");
    	for (Map.Entry<String, Object> entry : allInputValues.entrySet() )
		{
			String key = (String) entry.getKey();

			switch( key  )
			{
				case "fromDate" :   	query.append( " " + allInputValues.get("andOR") + " " );
										query.append( "r.PUBLISHDATE >=").append( "'" + allInputValues.get("fromDate") + "'" ).append( " AND r.PUBLISHDATE <=").append( "'" + allInputValues.get("toDate") + "'" );
								   	    break;
										
				case "starsValue" :     query.append( " " + allInputValues.get("andOR") + " " );
										query.append( "r.stars ").append( " " + allInputValues.get("starOperator") + " " ).append( " " + allInputValues.get("starsValue") + " ");
										break;
				case "votesValue" :  query.append( " " + allInputValues.get("andOR") + " " );
										query.append( "r.VOTES_USEFUL ").append( " " + allInputValues.get("votesOperator") + " " ).append( " " + allInputValues.get("votesValue") + " ");
										break;
			}
		}
		
	return query.toString();
	}
    
	public  Map< String, Object > getUsers(Map<String, Object> map){
       Map< String, Object > hm = new HashMap< String, Object >();
       String generatedQuery =  generateUserQuery( map );
       List< User > users = new ArrayList< User >();
        try {
            String query = generateUserQuery( map ) ;
            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet rs = statement.executeQuery();
            while (rs.next()){
            	User user = new User();
            	user.setUserId( rs.getString(1) );
                user.setName( rs.getString(2) );
                user.setYelpingSince( rs.getDate(3).toString() );
                user.setAverageStars(rs.getFloat(4));
               // user.setAverageStars(rs.getDate(4).toString() );
                users.add(user);
            }
            
        }catch (SQLException se){
            System.err.println("Query error: SQL Exception");
            se.printStackTrace();
        }catch (Exception e){
            System.err.println("Query error ");
            e.printStackTrace();
        }
        hm.put("list", users);
        hm.put("generatedQuery", generatedQuery);
        System.out.println("*******RESULT SET COUNT**********" +  users.size() );

        return hm;
    }
	private String generateUserQuery( Map< String, Object > map) {
		boolean isFirst = true;
		StringBuilder query = new StringBuilder();
		query.append( "Select USERID,name,yelpingsince,averagestars from users where ");
		for (Map.Entry<String, Object> entry : map.entrySet() )
		{
			String key = (String) entry.getKey();

			switch( key  )
			{
				case "reviewCount" :   if( !isFirst && key.compareTo("andOR") != 0 )
										query.append( " " + map.get("andOR") + " " );
									   
										query.append( "REVIEW_COUNT ").append( " " + map.get("reviewCountOperator") + " " ).append( " " + map.get("reviewCount"));
										isFirst = false;
									   break;
										
				case "avgStars" :      if( !isFirst && key.compareTo("andOR") != 0 )
										 query.append( " " + map.get("andOR") + " " );
										query.append( "AVERAGESTARS ").append( " " + map.get("averageStarsOperator") + " " ).append( " " + map.get("avgStars") + " ");
										isFirst = false;
										break;
				case "numberOfVotes" : if( !isFirst && key.compareTo("andOR") != 0 )
										 query.append( " " + map.get("andOR") + " " );
										query.append( "TOTALVOTES ").append( " " + map.get("numberOfVotesOperator") + " " ).append( " " + map.get("numberOfVotes") + " ");
										isFirst = false;
										break;
				case "memberSince" : if( !isFirst && key.compareTo("andOR") != 0 )
					 					query.append( " " + map.get("andOR") + " " );
										query.append( "YELPINGSINCE ").append( ">=" ).append( " '" + map.get("memberSince") + "' ");
										isFirst = false;
										break;
							
										
//				case "numberOfVotes" : 
//										break;
										
			}
		}
		
		if( map.get( "numberOfFriends" ) != null  )
		{
			if( !isFirst )
			{
				query.append( " " + map.get("andOR") + " " );
				query.append( "userId In( Select userId from userFriends group by userId having count(userfriends.friend_id) ").append( " " + map.get("numberOfFriendsOperator") + " " ).append( " " + map.get("numberOfFriends") + " )");
			}
			else
				query.append( "userId In( Select userId from userFriends group by userId having count(userfriends.friend_id) ").append( " " + map.get("numberOfFriendsOperator") + " " ).append( " " + map.get("numberOfFriends") + " )");
		}
	if( query.toString().endsWith("where") )
		return "Select USERID,name,yelpingsince,averagestars from users";
	return query.toString();
	}
	public ArrayList<Reviews> getReviewsByUserId(String userId) {
	    ArrayList<Reviews> reviews = new ArrayList<Reviews>();
        try {
            String query = "SELECT r.publishDate, r.stars, r.text, b.NAME,  r.VOTES_USEFUL FROM  REVIEWS r JOIN business b ON r.BUSINESSID = b.BUSINESSID WHERE  r.userId = '"+userId+"'" ;
            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet rs = statement.executeQuery();
            while (rs.next()){
                Reviews r = new Reviews();
                r.setDate(rs.getDate(1).toString());
                r.setStars(rs.getInt(2));
                r.setText(rs.getString(3));
                r.setBusinessId(rs.getString(4));
                r.setVotesUseful(rs.getInt(5));
                reviews.add(r);
            }

        }catch (SQLException se){
            System.err.println("Query error: SQL Exception");
            se.printStackTrace();
        }catch (Exception e){
            System.err.println("Query error ");
            e.printStackTrace();
        }
        return reviews;
    }

}
