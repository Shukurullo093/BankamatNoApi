package Bankamat;

import java.sql.*;
import java.util.Date;
import java.util.Scanner;

public class Db_connection {
    private  String url ="jdbc:postgresql://localhost:5432/Bankamat";
    private String dbsuperuser="postgres";
    private String dbpassword="2104";

    public void preparedStatement_insertuser(Foydalanuvchilar foydalanuvchilar){
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(url,dbsuperuser,dbpassword);
            Statement statement  = connection.createStatement();
            String check = "select * from foydalanuvchilar1 where p_raqam="+foydalanuvchilar.getPlastik_raqam();
            ResultSet resultSet = statement.executeQuery(check);
            if (resultSet.next()){
                System.out.println("# Siz kiritgan karta allaqachon bazada mavjud!!!");
            }
            else {
                String query="insert into foydalanuvchilar1(p_raqam,kod,summa) values(?,?,?);";
                PreparedStatement preparedStatement=connection.prepareStatement(query);
                preparedStatement.setDouble(1,foydalanuvchilar.getPlastik_raqam());
                preparedStatement.setInt(2,foydalanuvchilar.getPin_kod());
                preparedStatement.setInt(3,foydalanuvchilar.getSumma());
                preparedStatement.executeUpdate();
                System.out.println("# Karta muvaffaqiyatli qo`shildi!!!");
                connection.close();
                preparedStatement.close();
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void Parol_tahrirlash(Double pla_raqam){
        try {
            Connection connection = DriverManager.getConnection(url,dbsuperuser,dbpassword);
            Statement statement  = connection.createStatement();
            String check = "select * from foydalanuvchilar1 where p_raqam="+pla_raqam;
            ResultSet resultSet = statement.executeQuery(check);
            if (resultSet.next()){
                String pl_raqam=resultSet.getString("p_raqam");
                String kod=resultSet.getString(2);
                Scanner in=new Scanner(System.in);
                System.out.print("# Pin kod : ");
                String kod1=in.next();
                if(kod.equals(kod1)){
                    String summa=resultSet.getString("summa");
                    System.out.print("# Yangi kod kiriting : ");
                    String y_kod=in.next();
                    System.out.print("# Kodni takror kiriting : ");
                    String ty_kod=in.next();
                    if (y_kod.equals(ty_kod))
                    {
                        kod=y_kod;
                        String query = "update foydalanuvchilar1 set p_raqam='"+pl_raqam+"', kod='"+kod+"', summa='"+summa+"'  where p_raqam="+pl_raqam;
                        statement.execute(query);
                        System.out.println("# Muvaffaqiyatli yangilandi!!!");
                    }
                    else {
                        System.out.println("# Takror parol xato kiritildi!!!");
                    }
                }
                else{
                    System.out.println("# Pin kod xato kiritildi!!!");
                }
            }
            else {
                System.out.println("# Siz kiritgan plastik raqam mavjud emas!!!");
            }
            connection.close();
            statement.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void Pul_yechish(Double pl_raqam){
        try {
            Connection connection = DriverManager.getConnection(url,dbsuperuser,dbpassword);
            Statement statement  = connection.createStatement();
            String check = "select * from foydalanuvchilar1 where p_raqam="+pl_raqam;
            ResultSet resultSet = statement.executeQuery(check);
            if (resultSet.next()){
                String pr=resultSet.getString("p_raqam");
                String kod=resultSet.getString("kod");
                Scanner in=new Scanner(System.in);
                System.out.print("# Pin kodni kiriting : ");
                String pin=in.next();
                if (kod.equals(pin)){
                    String summa=resultSet.getString("summa");
                    System.out.print("# Chiqariladigan summani kiriting : ");
                    String chiq_summa=in.next();
                    int s= Integer.parseInt(summa);
                    int s1= Integer.parseInt(chiq_summa);
                    if (s1%1000==0){
                        if(s1<s){
                            s=s-s1;
                            String query = "update foydalanuvchilar1 set p_raqam='"+pr+"', kod='"+kod+"', summa='"+s+"'  where p_raqam="+pr;
                            statement.execute(query);
                            System.out.println("\n# Pul yechildi!!!");
                            Date date=new Date();
                            System.out.println("# Karta raqami :\t"+pr+"\n# Yechilgan vaqt :\t"+date+"\n# Chiqarilgan summa :\t"+s1+"\n# Kartada qoldiq :\t"+s);
                        }
                        else {
                            System.out.println("# Hisobingizda mablag` yetarli emas!!!");
                        }
                    }
                    else {
                        System.out.println("# Chiqariladigan summa formati xato!!!");
                    }
                }
                else {
                    System.out.println("# Pin kod xato kitildi");
                }
            }
            else {
                System.out.println("Siz kiritgan karta raqam mavjud emas!!!");
            }
            connection.close();
            statement.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void Otkazma(Double k_raqam){
        try {
            Connection connection = DriverManager.getConnection(url,dbsuperuser,dbpassword);
            Statement statement  = connection.createStatement();
            String check = "select * from foydalanuvchilar1 where p_raqam="+k_raqam;
            ResultSet resultSet = statement.executeQuery(check);
            if (resultSet.next()){
                String plastik_raqam=resultSet.getString("p_raqam");
                String kod=resultSet.getString("kod");
                String summa=resultSet.getString("summa");

                Scanner in=new Scanner(System.in);
                System.out.print("# Pin kodni kiriting : ");
                String pin=in.next();
                if (kod.equals(pin)){
                    System.out.print("# Pul o`tkaziladigan karta raqamini kiriting : ");
                    double oraqam= in.nextDouble();
                    String check1 = "select * from foydalanuvchilar1 where p_raqam="+oraqam;
                    ResultSet resultSet1 = statement.executeQuery(check1);
                    if (resultSet1.next()){
                        String okraqam=resultSet1.getString("p_raqam");
                        String okkod=resultSet1.getString("kod");
                        String oksumma=resultSet1.getString("summa");

                        System.out.print("# Otkaziladigan summani kiriting : ");
                        String chiq_summa=in.next();
                        int s= Integer.parseInt(summa);
                        int s1= Integer.parseInt(chiq_summa);
                        int s2= Integer.parseInt(oksumma);
                        if(s1<s){
                            s=s-s1;
                            s2=s2+s1;
                            String query = "update foydalanuvchilar1 set p_raqam='"+plastik_raqam+"', kod='"+kod+"', summa='"+s+"'  where p_raqam="+plastik_raqam;
                            statement.execute(query);
                            String query1 = "update foydalanuvchilar1 set p_raqam='"+okraqam+"', kod='"+okkod+"', summa='"+s2+"'  where p_raqam="+okraqam;
                            statement.execute(query1);
                            String query2="insert into tranzaksiya(karta_1,karta_2,summa,vaqt)" +
                                    " values('"+plastik_raqam+"','"+okraqam+"','"+s1+"','now()')";
                            statement.execute(query2);
                            System.out.println("\n# Pul o`tkazildi!!!");
                            Date date=new Date();
                            System.out.println("# Karta raqam : \t"+plastik_raqam+"\n# Qabul qiluvchi : \t"+okraqam+"\n# Summa : "+s1+"\n# Vaqt : "+date);
                        }
                        else {
                            System.out.println("# Hisobingizda mablag` yetarli emas!!!");
                        }
                    }
                    else {
                        System.out.println("# Bunday karta mavjud emas!!!");
                    }
                }
                else {
                    System.out.println("# Pin kod xato kitildi");
                }
            }
            else {
                System.out.println("Siz kiritgan karta raqam mavjud emas!!!");
            }
            connection.close();
            statement.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void Hisob(Double k_raqam){
        try {
            Connection connection = DriverManager.getConnection(url,dbsuperuser,dbpassword);
            Statement statement  = connection.createStatement();
            String check = "select * from foydalanuvchilar1 where p_raqam="+k_raqam;
            ResultSet resultSet = statement.executeQuery(check);
            if (resultSet.next()){
                String kod=resultSet.getString("kod");
                Scanner in=new Scanner(System.in);
                System.out.print("# Pin kodni kiriting :");
                String pkod=in.next();
                if (pkod.equals(kod)){
                    String summa=resultSet.getString("summa");
                    System.out.println("# Kartadagi pul miqdori \n# "+summa+" so`m");
                }
                else {
                    System.out.println("# Pin kodni xato kiritdingiz!!!");
                }
            }
            else {
                System.out.println("Siz kiritgan karta raqami mavjud emas!!!");
            }
            connection.close();
            statement.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void Tarix(Double k_raqami){
        try {
            Connection connection = DriverManager.getConnection(url,dbsuperuser,dbpassword);
            Statement statement  = connection.createStatement();
            String check = "select * from tranzaksiya where karta_1="+k_raqami;
            ResultSet resultSet = statement.executeQuery(check);
            System.out.println("# Kartadan yuborilgan pullar\n#########################################");
            System.out.println("#  Karta raqami \t#  Summa \t#  Vaqt");
            while (resultSet.next()){
                String karta_2=resultSet.getString("karta_2");
                String summa=resultSet.getString("summa");
                String vaqt=resultSet.getString("vaqt");
                System.out.println("# "+karta_2+" \t# "+summa+" \t# "+vaqt);
            }
            String check1 = "select * from tranzaksiya where karta_2="+k_raqami;
            ResultSet resultSet1 = statement.executeQuery(check1);
            System.out.println("\n# Kartaga qabul qilingan pullar\n#########################################");
            System.out.println("#  Karta raqami \t#  Summa \t#  Vaqt");
            while (resultSet1.next()){
                String karta_2=resultSet1.getString("karta_1");
                String summa=resultSet1.getString("summa");
                String vaqt=resultSet1.getString("vaqt");
                System.out.println("# "+karta_2+" \t# "+summa+" \t# "+vaqt);
            }
            connection.close();
            statement.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
