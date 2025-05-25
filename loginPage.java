
package StudentManagement;


import javax.swing.*;
import java.sql.*;
import java.awt.event.*;
import java.awt.*;

public class loginPage extends JFrame implements ActionListener 
{
    JFrame f1;
    JLabel L1,L2,L3,L4,L5,L6;
    Choice ch1;
    JTextField tx1,tx2;
    JPasswordField p1,p2;
    JButton b1,b2;
    
    loginPage(){
        // Frame making.
        f1=new JFrame("Login Page");
        f1.setBackground(Color.WHITE);
        f1.setLayout(null);
        
        L1=new JLabel();
        L1.setBounds(0,0,750,450);
        L1.setLayout(null);
        
        // Image 
        ImageIcon img = new ImageIcon(ClassLoader.getSystemResource("StudentManagement/Images/login.jpg"));
        Image i1=img.getImage().getScaledInstance(750, 450, Image.SCALE_SMOOTH);
        ImageIcon img1 = new ImageIcon(i1);
        L1.setIcon(img1);
        
        // Login
        L2=new JLabel("Login Account");
        L2.setBounds(260,152,490,50);
        L2.setFont(new Font("Aerial",Font.CENTER_BASELINE,30));
        L2.setBackground(Color.BLACK);
        L2.setForeground(Color.MAGENTA);
        L1.add(L2);
        f1.add(L1);
        
        L3=new JLabel("Account     :");
        L3.setBounds(240,210,150,30);
        L3.setFont(new Font("Aerial",Font.BOLD,20));
        L3.setForeground(Color.RED);
        L1.add(L3);

        // User Choice
        ch1=new Choice();
        ch1.add("Admin");
        ch1.add("Student");
        ch1.setBounds(410,210,150,30);
        ch1.setFont(new Font("Aerial",Font.BOLD,15));
        L1.add(ch1);
        
        
        L4=new JLabel("Username   :");
        L4.setBounds(240,240,150,30);
        L4.setFont(new Font("Aerial",Font.BOLD,20));
        L4.setForeground(Color.red);
        L1.add(L4);
        
        // Password
        tx1=new JTextField();
        tx1.setBounds(410,240,150,30);
        tx1.setFont(new Font("Aerial",Font.BOLD,15));
        L1.add(tx1);
        
        L5=new JLabel("Password   :");
        L5.setBounds(240,270,150,30);
        L5.setFont(new Font("Aerial",Font.BOLD,20));
        L5.setForeground(Color.red);
        L1.add(L5);
        
        p1=new JPasswordField();
        p1.setBounds(410,270,150,30);
        p1.setFont(new Font("Aerial",Font.BOLD,15));
        L1.add(p1);
        
        // Login and Back Button
        b1=new JButton("Login       ");
        b1.setBounds(240,320,150,30);
        b1.setFont(new Font("Aerial",Font.CENTER_BASELINE,15));
        b1.setBackground(Color.blue);
        b1.setForeground(Color.white);
        b1.addActionListener(this);
        L1.add(b1);
        
        b2=new JButton("Back       ");
        b2.setBounds(410,320,150,30);
        b2.setFont(new Font("Aerial",Font.CENTER_BASELINE,15));
        b2.setBackground(Color.blue);
        b2.setForeground(Color.white);
        b2.addActionListener(this);
        L1.add(b2);
        
        f1.setVisible(true);
        f1.setSize(750,450);
        f1.setLocation(300, 100);
        
        
        
        
    }
    public void actionPerformed(ActionEvent ae ){
        if(ae.getSource()==b1){
            try{
                 ConnectionClass obj=new ConnectionClass(); 
                 String Username = tx1.getText();
                 String Password = p1.getText();
                 String Account = ch1.getSelectedItem();
                 
                 String q="select * from admin where Usename ='"+Username+"' and Password ='"+Password+"'  ";
                 ResultSet rest = obj.stm.executeQuery(q);
                 if(rest.next()){
                     if(Account.equals("Admin")){
                    new AdminHomepage(Account).setVisible(true);
                   //  System.out.println("Login Succesful:");
                     }else if(Account.equals("Student")){
                   // new Student(Account).setVisible(true);
                     }
                 }
                 else{
                     JOptionPane.showMessageDialog(null,"You have entered wrong password or username:");
                     f1.setVisible(false);
                     f1.setVisible(true);
                 }
                
            }
            catch(Exception e){
                e.printStackTrace();
                
            }
            
        }
        if(ae.getSource()==b2){
            f1.setVisible(false);
        }
    }
    public static void main(String args[]){
        new loginPage();
        
    }

}
 
    

