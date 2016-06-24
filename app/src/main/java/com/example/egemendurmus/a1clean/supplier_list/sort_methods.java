package com.example.egemendurmus.a1clean.supplier_list;

import java.util.Comparator;

/**
 * Created by egemen.durmus on 22/06/16.
 */
public class sort_methods {



    public static Comparator<Data> totalComparator = new Comparator<Data>() {

        @Override
        public int compare(Data lhs, Data rhs) {
            // TODO Auto-generated method stub



            String s1 = lhs.getmprice();
            String s2 = rhs.getmprice();


            Double p1=Double.parseDouble(s1);
            Double p2=Double.parseDouble(s2);



            return p1.compareTo(p2);
        }
    };


    public static Comparator<Data> totalComparator_desc = new Comparator<Data>() {

        @Override
        public int compare(Data lhs, Data rhs) {
            // TODO Auto-generated method stub



            String s1 = lhs.getmprice();
            String s2 = rhs.getmprice();


            Double p1=Double.parseDouble(s1);
            Double p2=Double.parseDouble(s2);



            return p2.compareTo(p1);
        }
    };




    public static Comparator<Data> rating_compare_asc = new Comparator<Data>() {

        @Override
        public int compare(Data lhs, Data rhs) {
            // TODO Auto-generated method stub



            String s1 = lhs.getmrating();
            String s2 = rhs.getmrating();


            Double p1=Double.parseDouble(s1);
            Double p2=Double.parseDouble(s2);



            return p1.compareTo(p2);
        }
    };



    public static Comparator<Data> rating_compare_desc = new Comparator<Data>() {

        @Override
        public int compare(Data lhs, Data rhs) {
            // TODO Auto-generated method stub



            String s1 = lhs.getmrating();
            String s2 = rhs.getmrating();

            if(s1.equals("null")| s2.equals("null")){
                s1="0.0";
                s2="0.0";
            }


            Double p1=Double.parseDouble(s1);
            Double p2=Double.parseDouble(s2);



            return p2.compareTo(p1);
        }
    };


    public static Comparator<Data> alphabet_compare_asc = new Comparator<Data>() {

        @Override
        public int compare(Data lhs, Data rhs) {
            // TODO Auto-generated method stub



            String s1 = lhs.getmTitle().toUpperCase();
            String s2 = rhs.getmrating().toUpperCase();


            return s1.compareTo(s2);
        }
    };


    public static Comparator<Data> alphabet_compare_desc = new Comparator<Data>() {

        @Override
        public int compare(Data lhs, Data rhs) {
            // TODO Auto-generated method stub



            String s1 = lhs.getmTitle().toUpperCase();
            String s2 = rhs.getmrating().toUpperCase();


            return s2.compareTo(s1);
        }
    };



}