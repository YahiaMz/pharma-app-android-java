package com.aplication.onlinepharmacy.Others;

public class ApiUrls {

        private static String MAIN_URL = "http://192.168.1.39:5000/";
        public static String CATEGORIES = MAIN_URL + "categories/";
        public static String CATEGORIES_IMAGES = CATEGORIES + "images/";
        public static String PRODUCTS = MAIN_URL + "products/";
        public static String PRODUCTS_TO = MAIN_URL + "products/to/";
        public static String CART_OF_USER = MAIN_URL + "carts/OfUser/";
        public static String ADD_TO_CART = MAIN_URL + "carts/add";
        public static String REMOVE_CART_ITEM = MAIN_URL + "carts/";
        public  static  String PRODUCT_IMAGES = PRODUCTS + "images/";
        public  static  String USER_ADDRESSES = MAIN_URL + "user-address/";
        public static  String ORDER_CART_ITEMS = MAIN_URL + "orders/OfUser/";
        public static  String ORDERS_OF_USER = MAIN_URL + "orders/ofUser/";
        public static String USER_LOGIN = MAIN_URL + "users/login";
        public static String USER_LIKE = MAIN_URL + "users/like";
        public static String USER_FAVOURITE_PRODUCTS = MAIN_URL + "users/";
        public static  String CREATE_USER_ADDRESS = MAIN_URL+"user-address";


}
