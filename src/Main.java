
import java.sql.*;
import java.util.Scanner;
public class Main {
    public static void main(String[] args)throws ClassNotFoundException , SQLException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            System.out.println(e.getMessage());
        }


        try {
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/my_databases", "root", "thug");
            Scanner sc = new Scanner(System.in);
            while (true) {
                System.out.println();
                System.out.println("1. Insert New Employee: ");
                System.out.println("2. Update Employee: ");
                System.out.println("3. Delete Employee: ");
                System.out.println("4. View All Employee: ");
                System.out.println("5. Search An Employee: ");
                System.out.println("0. Exit System: ");
                System.out.println("Enter your choice: ");
                int choice = sc.nextInt();
                switch (choice) {
                    case 1: {
                        addEmployee(con , sc);
                        break;
                    }
                    case 2: {
                        updateEmployee( con , sc);
                        break;
                    }
                    case 3: {
                        deleteEmployee( con , sc);
                        break;
                    }
                    case 4: {
                        viewEmployees( con);
                        break;
                    }
                    case 5: {
                        searchEmployee( con, sc);
                        break;
                    }
                    case 0: {
                        exit();
                        return;
                    }
                    default: {
                        System.out.println("Invalid input!!! \n TRY Again. ");
                    }
                }
            }
        }
        catch(InterruptedException e){
            System.out.println(e.getMessage());
        }
        catch (SQLException e) {
            System.out.println(e.getMessage());
        }

    }

        // Insert value

        static void addEmployee (Connection con, Scanner sc) throws SQLException {
            PreparedStatement ps = con.prepareStatement("insert into employee(em_id,emp_name,dept,contact_num,email) values(?,?,?,?,?); ");
            System.out.println("Enter the Employee's ID, NAME , DEPARTMENT , CONTACT NUMBER and EMAil one-by-one : ");
            int id = sc.nextInt();
            sc.nextLine();
            String name = sc.nextLine();
            String dept = sc.nextLine();
            int contact = sc.nextInt();
            sc.nextLine();
            String email = sc.nextLine();

            ps.setInt(1, id);
            ps.setString(2, name);
            ps.setString(3, dept);
            ps.setInt(4, contact);
            ps.setString(5, email);
            int RowsAffected = ps.executeUpdate();
            if (RowsAffected > 0) {
                System.out.println("Insertion Successfully!!! \n" + RowsAffected + " Rows Affected. ");
            } else {
                System.out.println("Insertion failed!!! ");
            }
            ps.close();
        }


        //display employees
        private static void viewEmployees (Connection con) throws SQLException {
            PreparedStatement ps = con.prepareStatement("select *from employee ;");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int eId = rs.getInt("em_id");
                String eName = rs.getString("emp_name");
                String eDepartment = rs.getString("dept");
                int eContactNum = rs.getInt("contact_num");
                String eEmail = rs.getString("email");
                System.out.println("ID: " + eId);
                System.out.println("Name: " + eName);
                System.out.println("Department: " + eDepartment);
                System.out.println("Contact Number: " + eContactNum);
                System.out.println("Email: " + eEmail);
                System.out.println("\n*******************\n");

            }
            rs.close();
            ps.close();
        }


        //update value
        private static void updateEmployee (Connection con, Scanner sc) throws SQLException {
            System.out.println("Enter the EMPLOYE_ID you want to update:");
            int id1=sc.nextInt();
            System.out.println("Enter the updated value of the employee in order(id,name,department,contact number,email): ");
            int id=sc.nextInt();
            sc.nextLine();
            String name=sc.nextLine();
            String dept=sc.nextLine();
            int contactNum=sc.nextInt();
            sc.nextLine();
            String email=sc.nextLine();

            PreparedStatement ps = con.prepareStatement("update employee set em_id=?,emp_name=?,dept=?,contact_num=?,email=? where em_id=?");
            ps.setInt(6,id1);
            ps.setInt(1, id);
            ps.setString(2,name);
            ps.setString(3,dept);
            ps.setInt(4,contactNum);
            ps.setString(5,email);

            int rowsAffected = ps.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Updation Successful!! \n" + rowsAffected + " Rows Affected. ");
            } else {
                System.out.println("Updation Failed!!!");
            }
            ps.close();
        }
        // DELETE Employee
        private static void deleteEmployee (Connection con, Scanner sc) throws SQLException {
            System.out.println("Enter the EmployeeID for DELETION! ");
            int id= sc.nextInt();
            PreparedStatement ps = con.prepareStatement("Delete from employee where em_id=?;");
            ps.setInt(1, id);
            int rowsAffected = ps.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Deletion Successful!! \n" + rowsAffected + " Rows Affected. ");
            } else {
                System.out.println("Deletion Failed!!!");
            }
            ps.close();
        }

        // search Employee
        private static void searchEmployee (Connection con, Scanner sc) throws SQLException {
            System.out.println("Enter the EmployeeID you're Looking for: ");
            int id = sc.nextInt();
            PreparedStatement ps = con.prepareStatement("select *from employee where em_id=?");
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                int eId = rs.getInt("em_id");
                String eName = rs.getString("emp_name");
                String eDepartment = rs.getString("dept");
                int eContactNum = rs.getInt("contact_num");
                String eEmail = rs.getString("email");
                System.out.println("ID: " + eId);
                System.out.println("Name: " + eName);
                System.out.println("Department: " + eDepartment);
                System.out.println("Contact Number: " + eContactNum);
                System.out.println("Email: " + eEmail);
            } else {
                System.out.println("Employees not found!! ");
            }
            rs.close();
            ps.close();
        }
        // exit system
        static void exit () throws InterruptedException {
            System.out.print("Exiting System");
            int i = 5;
            while (i != 0) {
                System.out.print(".");
                Thread.sleep(1000);
                i--;
            }
            System.out.println();
            System.out.println("ThankYou For Using Our System!!!");
        }


  }



