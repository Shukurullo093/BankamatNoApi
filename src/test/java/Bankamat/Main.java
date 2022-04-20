package Bankamat;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner value=new Scanner(System.in);
        boolean holat=true;
        int tanlov;
        Db_connection dbConnection=new Db_connection();
        while (holat){
            System.out.print("######### MENU #########\n# 1.Foydalanuvchi qo`shish\n# 2.Parolni tahrirlash\n# 3.Hisobdagi mablag`\n# 4.Pul yechish\n# 5.O`tkazma\n# 6.Tranzaksiya tarixi\n# 7.Chiqish\n\n# Tanlov ==>");
            tanlov = value.nextInt();
            System.out.println();

            switch (tanlov){
                case 1:
                    value=new Scanner(System.in);
                    System.out.println("## Foydalanuvchi malumotlari");
                    System.out.print("# Plastik raqam kiriting: ");
                    Double pl_raqam= value.nextDouble();
                    String k= String.valueOf(pl_raqam);
                    if (k.length()-4==16){
                        System.out.print("# Plastik kodini kiriting: ");
                        int pl_kod= value.nextInt();
                        String p= String.valueOf(pl_kod);
                        if (p.length()==4){
                            System.out.print("# Summani kiriting: ");
                            int pl_summa= value.nextInt();
                            Foydalanuvchilar foydalanuvchilar = new Foydalanuvchilar(pl_raqam,pl_kod,pl_summa);
                            dbConnection.preparedStatement_insertuser(foydalanuvchilar);
                            System.out.println();
                        }
                        else {
                            System.out.println("# Pin kod 4 xonali son bo`lsin!!!");
                        }
                    }
                    else {
                        System.out.println("# Karta raqami 16 ta raqamdan iborat bo`lsin!!!"+ k.length());
                    }
                    break;
                case 2:
                    value=new Scanner(System.in);
                    System.out.println("# Parol tahrirlash");
                    System.out.print("# Plastik raqami : ");
                    Double pla_raqam=value.nextDouble();
                    dbConnection.Parol_tahrirlash(pla_raqam);
                    System.out.println();
                    break;
                case 3:
                    value=new Scanner(System.in);
                    System.out.println("# Kartadagi mablag`");
                    System.out.print("# Plastik raqami : ");
                    Double pla_raqam3=value.nextDouble();
                    dbConnection.Hisob(pla_raqam3);
                    System.out.println();
                    break;
                case 4:
                    value=new Scanner(System.in);
                    System.out.println("# Pul yechish");
                    System.out.print("# Plastik raqami : ");
                    Double pla_raqam1=value.nextDouble();
                    dbConnection.Pul_yechish(pla_raqam1);
                    System.out.println();
                    break;
                case 5:
                    value=new Scanner(System.in);
                    System.out.println("# Pul otkazish");
                    System.out.print("# Plastik raqami : ");
                    Double pla_raqam2=value.nextDouble();
                    dbConnection.Otkazma(pla_raqam2);
                    System.out.println();
                    break;
                case 6:
                    value=new Scanner(System.in);
                    System.out.println("# Tranzaksiya tarixi");
                    System.out.print("# Plastik raqami : ");
                    Double pla_raqam4=value.nextDouble();
                    dbConnection.Tarix(pla_raqam4);
                    System.out.println();
                    break;
                case 7:
                    System.out.println("# Dastur yakunlandi");
                    holat=false;
                    break;
                default:
                    System.out.println("# Xato menu tanlandi!!!");
                    break;
            }
        }
    }
}
