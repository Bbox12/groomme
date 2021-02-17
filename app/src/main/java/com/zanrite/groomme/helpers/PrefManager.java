package com.zanrite.groomme.helpers;

import android.content.Context;
import android.content.SharedPreferences;

import java.sql.SQLTransactionRollbackException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by parag on 12/01/17.
 */
public class PrefManager {
    public static final String KEY_MOBILE = "mobilenos";
    public static final String KEY_IMEI = "imei";
    private static final String NEW_VERSION = "Newversion";
    // Shared preferences file name
    private static final String PREF_NAME = "Groom";
    // All Shared Preferences Keys
    private static final String KEY_IS_WAITING_FOR_SMS = "IsWaitingForSms";
    private static final String KEY_IS_LOGGED_IN = "isLoggedIn";
    private static final String NEW_UNIQUE_RIDE = "Ride";
    private static final String NEW_PROFILE = "profile";
    private static final String NEW_PROFILE1 = "profile";
    private static final String NEW_REVIEW = "review";
    private static final String NEW_NAME = "name";
    private static final String CANCEL_1 = "c1";
    private static final String CANCEL_2 = "c2";
    private static final String CANCEL_3 = "c3";
    private static final String CANCEL_4 = "c4";
    private static final String CANCEL_5 = "c5";
    private static final String REGID = "regId";
    private static final String KEY_IS_SHARE = "isshare";
    private static final String KEY_DROP_AT = "DROP";
    private static final String KEY_PICK_UP = "PICK";
    private static final String KEY_DROP_LAT = "dlat";
    private static final String KEY_DROP_LONG = "dlong";
    private static final String KEY_PICK_LAT = "plat";
    private static final String KEY_PICK_LONG = "plong";
    private static final String DRIVER_PHN = "dphn";
    private static final String Ride_ID = "dcided";
    private static final String SPHONE = "sphn";
    private static final String NEW_IMP = "imp";
    private static final String PREF1 = "pref1";
    private static final String PREF2 = "pref2";
    private static final String PREF3 = "pref3";
    private static final String PREF4 = "pref4";
    private static final String iPREF1 = "ipref1";
    private static final String iPREF2 = "ipref2";
    private static final String iPREF3 = "ipref3";
    private static final String iPREF4 = "ipref4";
    private static final String COUNT = "count";
    private static final String FOODITEMS = "Fooditems";
    private static final String FOODMONEY = "FoodMoney";
    private static final String SERVICEID = "FoodMoney";
    private static final String FOOD_LIST = "foodlist";
    private static final String CANTEENS_LIST = "canteenlist";
    private static final String CID = "cid";
    private static final String CID1 = "cid1";
    private static final String CANTEEN = "canteen";
    private static final String DELE = "setDelivery";
    private static final String SETRIDE = "setRide";
    private static final String CHARGE = "charge";
    private static final String RIDE = "ride";
    private static final String CANTEENS_FILTERS = "filters";
    private static final String NEW_FAVOURITE = "fav";
    private static final String KEY_COST = "cost";
    private static final String OTP = "OTP";
    private static final String CARTYPE = "CAR";
    public static final String KEY_NAME = "salonname";
    public static final String KEY_WHO = "whoishe";
    public static final String KEY_MOBILE2 ="mob2" ;
    public static final String KEY_NAME2 ="name2" ;
    private static final String KEY_IS_LOGGED_IN2 ="log2" ;
    private static final String SLOCATION = "location";
    private static final String SLATLONG = "latlong";
    private static final String STIMINGS = "timings";
    private static final String RESPOSIBILITY ="RESPONSIBILITY" ;
    private static final String MO = "mo";
    private static final String ORDERID = "ORDERID";
    private static final String NEW_GOTRIDE = "gotbooking";
    private static final String ACTUALTIME = "actualtime";
    private static final String CANCELLATIONCHARGE = "cancellationcharge";
    private static final String ISRUNNING = "isrunningsalon";
    private static final String USERPHNO ="UPPHNE" ;
    private static final String RUNNINGPARLOURMOBILE = "runnungparlour";
    private static final String HEADERIMAGE = "headerimage";
    private static final String TOTAL = "total";
    private static final String PAYMODE = "paymode";
    private static final String REFERRALCODE = "referalcode";
    private static final String DISTANCE = "minidis";
    private static final String PWD = "PWD";
    private static final String YOUTUBE = "youtube";
    private static final String YIUTUBECATEGIRY ="youtubecategory";
    private static final String YIUTUBETITLE ="youtubetitle";
    private static final String SEX = "seex";
    private static final String LADIES = "ladiiies";
    SharedPreferences pref;
    SharedPreferences.Editor editor;
    Context _context;
    int PRIVATE_MODE = 0;
    private static final String _no_of_seats = "nos";
    private static final String ID1 = "ID1";
    private static final String ID2 = "ID2";
    private static final String ID3 = "ID3";
    private static final String ID4 = "ID4";
    private static final String ID5 = "ID5";
    private ArrayList<String> arrPackage= new ArrayList<>();
    private static final String noOfItems="noOfItems";
    private static final  String iRate1="r1";
    private static final  String iRate2="r2";
    private static final  String iRate3="r3";
    private static final  String iRate4="r4";
    private static final  String cNAme="cname";
    private static final  String cAddress="caddress";
    private static final  String cPhoto="cphoto";
    private static final String cDiscount = "cd";
    private static final  String cEMAIL="cemail";
    private static final  String cPackaging="cp";
    private static final  String cLess="cless";
    private static final  String cMore="cmore";
    private static final String KEY_DROP_AT1 = "DROP1";
    private static final String KEY_PICK_UP1 = "PICK1";
    private static final String KEY_DROP_LAT1 = "dlat1";
    private static final String KEY_DROP_LONG1 = "dlong1";
    private static final String KEY_PICK_LAT1= "plat1";
    private static final String KEY_PICK_LONG1 = "plong1";
    private static final String KEY_DISTANCE = "dist";
    private static final String KEY_Weight="weights";

    public PrefManager(Context context) {
        this._context = context;
        pref = _context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        editor = pref.edit();
    }

    public void clearSession() {
        editor.clear();
        editor.commit();
    }


    public int getLocation() {
        return pref.getInt(SLOCATION, 0);
    }

    public void setLocation(int imp) {
        editor.putInt(SLOCATION, imp);
        editor.commit();
    }
    public int getLatLong() {
        return pref.getInt(SLATLONG, 0);
    }

    public void setLatLong(int imp) {
        editor.putInt(SLATLONG, imp);
        editor.commit();
    }
    public int getTimings() {
        return pref.getInt(STIMINGS, 0);
    }

    public void setTimings(int imp) {
        editor.putInt(STIMINGS, imp);
        editor.commit();
    }
    public String getAddress() {
        return pref.getString(cAddress, null);
    }

    public void setAddress(String imp) {
        editor.putString(cAddress, imp);
        editor.commit();
    }

    public String getPhoto() {
        return pref.getString(cPhoto, null);
    }

    public void setPhoto(String imp) {
        editor.putString(cPhoto, imp);
        editor.commit();
    }

    public String getparlour_pin() {
        return pref.getString(NEW_FAVOURITE, null);
    }

    public void setparlour_pin(String ride) {
        editor.putString(NEW_FAVOURITE, ride);
        editor.commit();
    }

    public String getEmail() {
        return pref.getString(cEMAIL, null);
    }

    public void setEmail(String imp) {
        editor.putString(cEMAIL, imp);
        editor.commit();
    }


    public String getCity() {
        return pref.getString(CARTYPE, null);
    }

    public void setCity(String imp) {
        editor.putString(CARTYPE, imp);
        editor.commit();
    }

    public String getparlour_registration() {
        return pref.getString(cPackaging, null);
    }

    public void setparlour_registration(String imp) {
        editor.putString(cPackaging, imp);
        editor.commit();
    }
    public String getservice_location() {
        return pref.getString(cLess, null);
    }

    public void setservice_location(String imp) {
        editor.putString(cLess, imp);
        editor.commit();
    }

    public int getResposibility() {
        return pref.getInt(RESPOSIBILITY, 0);
    }

    public void setResponsibility(int imp) {
        editor.putInt(RESPOSIBILITY, imp);
        editor.commit();
    }

    public int getisSechedule() {
        return pref.getInt(cMore, 0);
    }

    public void setisSechedule(int imp) {
        editor.putInt(cMore, imp);
        editor.commit();
    }

    public int getisVerified() {
        return pref.getInt(SETRIDE, 0);
    }

    public void setisVerified(int imp) {
        editor.putInt(SETRIDE, imp);
        editor.commit();
    }

    public int getisSpecialist() {
        return pref.getInt(RIDE, 0);
    }

    public void setisSpecialist(int imp) {
        editor.putInt(RIDE, imp);
        editor.commit();
    }
    public int getisLocation() {
        return pref.getInt(CID1, 0);
    }

    public void setisLocation(int imp) {
        editor.putInt(CID1, imp);
        editor.commit();
    }
    public int getisServiceAt() {
        return pref.getInt(CID, 0);
    }

    public void setisServiceAt(int imp) {
        editor.putInt(CID, imp);
        editor.commit();
    }

    public int getDelivery() {
        return pref.getInt(DELE, 0);
    }

    public void setDelivery(int imp) {
        editor.putInt(DELE, imp);
        editor.commit();
    }

    public int get_food_items() {
        return pref.getInt(FOODITEMS, 0);
    }

    public void set_food_items(int imp) {
        editor.putInt(FOODITEMS, imp);
        editor.commit();
    }

    public void setCanteen(String regId) {
        editor.putString(CANTEEN, regId);
        editor.commit();
    }

    public String getCanteen() {
        return pref.getString(CANTEEN, null);
    }

    public void setFilters(ArrayList<String>foods) {
        Set<String> set = new HashSet<String>();
        if(foods!=null) {
            set.addAll(foods);
        }else{
            set.clear();

        }
        editor.putStringSet(CANTEENS_FILTERS, set);
        editor.apply();

    }

    public Set<String> getFilters() {
        return pref.getStringSet(CANTEENS_FILTERS,null);
    }


    public void packagesharedPreferences(ArrayList<String>foods) {
        Set<String> set = new HashSet<String>();
        if(foods!=null) {
            set.addAll(foods);
        }else{
            set.clear();

        }
        editor.putStringSet(FOOD_LIST, set);
        editor.apply();

    }

    public Set<String> get_packagesharedPreferences() {
        return pref.getStringSet(FOOD_LIST,null);
    }


    public void setnoOfItems(ArrayList<String>foods) {
        Set<String> set = new HashSet<String>();
        if(foods!=null) {
            set.addAll(foods);
        }else{
            set.clear();

        }
        editor.putStringSet(noOfItems, set);
        editor.apply();

    }

    public Set<String> getnoOfItems() {
        return pref.getStringSet(noOfItems,null);
    }


    public int getServiceID() {
        return pref.getInt(SERVICEID, 0);
    }

    public void setServiceID(int imp) {
        editor.putInt(SERVICEID, imp);
        editor.commit();
    }

    public String getPrimaryimage() {
        return pref.getString(_no_of_seats, null);
    }

    public void setPrimaryimage(String imp) {
        editor.putString(_no_of_seats, imp);
        editor.commit();
    }

    public int getCount() {
        return pref.getInt(COUNT, 0);
    }

    public void setCount(int imp) {
        editor.putInt(COUNT, imp);
        editor.commit();
    }


    public void setSalonPhone(String mobio) {
        editor.putString(SPHONE, mobio);
        editor.commit();
    }

    public String getSalonPhone() {
        return pref.getString(SPHONE, null);
    }


    public void setDate(String dc) {
        editor.putString(Ride_ID, dc);
        editor.commit();
    }

    public String getDate() {
        return pref.getString(Ride_ID, null);
    }



    public void setReview(float review) {
        editor.putFloat(NEW_REVIEW, review);
        editor.commit();
    }

    public float getReview() {
        return pref.getFloat(NEW_REVIEW, 0);
    }



    public void setRegID(String regId) {
        editor.putString(REGID, regId);
        editor.commit();
    }

    public String getRegID() {
        return pref.getString(REGID, null);
    }

    public void setTime(String image) {
        editor.putString(CANCEL_1, image);
        editor.commit();
    }

    public String getTime() {
        return pref.getString(CANCEL_1, null);
    }


    public void setPrimaryservice(String image) {
        editor.putString(CANCEL_2, image);
        editor.commit();
    }

    public String getPrimaryservice() {
        return pref.getString(CANCEL_2, null);
    }

    public void setSecondaryservice(String image) {
        editor.putString(CANCEL_3, image);
        editor.commit();
    }

    public String getSecondaryservice() {
        return pref.getString(CANCEL_3, null);
    }

    public void setService(String image) {
        editor.putString(CANCEL_4, image);
        editor.commit();
    }

    public String getService() {
        return pref.getString(CANCEL_4, null);
    }

    public void setServiceAt(int image) {
        editor.putInt(CANCEL_5, image);
        editor.commit();
    }

    public int getServiceAt() {
        return pref.getInt(CANCEL_5, 0);
    }

    public void setName(String profile) {
        editor.putString(NEW_NAME, profile);
        editor.commit();
    }

    public String getName() {
        return pref.getString(NEW_NAME, null);
    }

    public void setSpecialistID(int ride) {
        editor.putInt(NEW_UNIQUE_RIDE, ride);
        editor.commit();
    }

    public int getSpecialistID() {
        return pref.getInt(NEW_UNIQUE_RIDE, 0);
    }

    public void setProfile(String profile) {
        editor.putString(NEW_PROFILE, profile);
        editor.commit();
    }

    public String getProfile() {
        return pref.getString(NEW_PROFILE, null);
    }

    public void setProfile1(String profile) {
        editor.putString(NEW_PROFILE1, profile);
        editor.commit();
    }

    public String getProfile1() {
        return pref.getString(NEW_PROFILE1, null);
    }


    public void setIsWaitingForSms(boolean isWaiting) {
        editor.putBoolean(KEY_IS_WAITING_FOR_SMS, isWaiting);
        editor.commit();
    }




    public boolean isLoggedIn() {
        return pref.getBoolean(KEY_IS_LOGGED_IN, false);
    }



    public HashMap<String, String> getUserDetails() {
        HashMap<String, String> profile = new HashMap<>();
        profile.put(KEY_NAME, pref.getString(KEY_NAME, null));
        profile.put(KEY_MOBILE, pref.getString(KEY_MOBILE, null));
        return profile;
    }

    public void createLogin(String mobio, String name) {
        editor.putString(KEY_MOBILE, mobio);
        editor.putString(KEY_NAME, name);
        editor.putBoolean(KEY_IS_LOGGED_IN, true);
        editor.commit();
    }

    public void createLogin2(String mobio, String name) {
        editor.putString(KEY_MOBILE2, mobio);
        editor.putString(KEY_NAME2, name);
        editor.putBoolean(KEY_IS_LOGGED_IN2, true);
        editor.commit();
    }
    public HashMap<String, String> getUserDetails2() {
        HashMap<String, String> profile = new HashMap<>();
        profile.put(KEY_NAME2, pref.getString(KEY_NAME2, null));
        profile.put(KEY_MOBILE2, pref.getString(KEY_MOBILE2, null));
        return profile;
    }

    public int getNewVersion() {
        return pref.getInt(NEW_VERSION, 0);
    }

    public void setNewVersion(int version) {
        editor.putInt(NEW_VERSION, version);
        editor.commit();
    }

    public int getImp() {
        return pref.getInt(NEW_IMP, 0);
    }

    public void setImp(int imp) {
        editor.putInt(NEW_IMP, imp);
        editor.commit();
    }

    public void deleteState() {
        editor.clear().commit();
    }



    public int getisShare() {
        return pref.getInt(KEY_IS_SHARE, 0);
    }

    public void setisShare(int _share) {
        editor.putInt(KEY_IS_SHARE, _share);
        editor.commit();
    }
    public String getDropLat() {
        return pref.getString(KEY_DROP_LAT, null);
    }

    public void setDropLat(String rate) {
        editor.putString(KEY_DROP_LAT, rate);
        editor.commit();

    }

    public String getDropLong() {
        return pref.getString(KEY_DROP_LONG, null);
    }

    public void setDropLong(String rate) {
        editor.putString(KEY_DROP_LONG, rate);
        editor.commit();
    }

    public void setDropAt(String drop) {
        editor.putString(KEY_DROP_AT, drop);
        editor.commit();
    }

    public String getDropAt() {
        return pref.getString(KEY_DROP_AT, null);
    }

    public void setPickAt(String drop) {
        editor.putString(KEY_PICK_UP, drop);
        editor.commit();
    }

    public String getPickAt() {
        return pref.getString(KEY_PICK_UP, null);
    }
    public String getPickLat() {
        return pref.getString(KEY_PICK_LAT, null);
    }

    public void setPickLat(String rate) {
        editor.putString(KEY_PICK_LAT, rate);
        editor.commit();

    }

    public String getPickLong() {
        return pref.getString(KEY_PICK_LONG, null);
    }

    public void setPickLong(String rate) {
        editor.putString(KEY_PICK_LONG, rate);
        editor.commit();
    }


    public void setPref1(String regId) {
        editor.putString(PREF1, regId);
        editor.commit();
    }

    public String getPref1() {
        return pref.getString(PREF1, null);
    }

    public void setPref2(String regId) {
        editor.putString(PREF2, regId);
        editor.commit();
    }

    public String getPref2() {
        return pref.getString(PREF2, null);
    }

    public void setPref3(String regId) {
        editor.putString(PREF3, regId);
        editor.commit();
    }

    public String getPref3() {
        return pref.getString(PREF3, null);
    }

    public void setPref4(String regId) {
        editor.putString(PREF4, regId);
        editor.commit();
    }

    public String getPref4() {
        return pref.getString(PREF4, null);
    }

    public void setiPref1(String regId) {
        editor.putString(iPREF1, regId);
        editor.commit();
    }

    public String getiPref1() {
        return pref.getString(iPREF1, null);
    }

    public void setiPref2(String regId) {
        editor.putString(iPREF2, regId);
        editor.commit();
    }

    public String getiPref2() {
        return pref.getString(iPREF2, null);
    }

    public void setiPref3(String regId) {
        editor.putString(iPREF3, regId);
        editor.commit();
    }

    public String getiPref3() {
        return pref.getString(iPREF3, null);
    }

    public void setiPref4(String regId) {
        editor.putString(iPREF4, regId);
        editor.commit();
    }

    public String getiPref4() {
        return pref.getString(iPREF4, null);
    }


    public float getiRate1() {
        return pref.getFloat(iRate1, 0);
    }

    public void setiRate1(float regId) {
        editor.putFloat(iRate1, regId);
        editor.commit();
    }

    public float getiRate2() {
        return pref.getFloat(iRate2, 0);
    }

    public void setiRate2(float regId) {
        editor.putFloat(iRate2, regId);
        editor.commit();
    }

    public float getiRate3() {
        return pref.getFloat(iRate3, 0);
    }

    public void setiRate3(float regId) {
        editor.putFloat(iRate3, regId);
        editor.commit();
    }

    public float getiRate4() {
        return pref.getFloat(iRate4, 0);
    }

    public void setiRate4(float regId) {
        editor.putFloat(iRate4, regId);
        editor.commit();
    }

    public int getID1() {
        return pref.getInt(ID1, 0);
    }

    public void setID1(int regId) {
        editor.putInt(ID1, regId);
        editor.commit();
    }
    public int getID2() {
        return pref.getInt(ID2, 0);
    }

    public void setID2(int regId) {
        editor.putInt(ID2, regId);
        editor.commit();
    }

    public int getID3() {
        return pref.getInt(ID3, 0);
    }

    public void setID3(int regId) {
        editor.putInt(ID3, regId);
        editor.commit();
    }
    public int getID4() {
        return pref.getInt(ID4, 0);
    }

    public void setID4(int regId) {
        editor.putInt(ID4, regId);
        editor.commit();
    }

    public int getCharge() {
        return pref.getInt(CHARGE, 0);
    }

    public void setCharge(int regId) {
        editor.putInt(CHARGE, regId);
        editor.commit();
    }

    public String getDis() {
        return pref.getString(KEY_DISTANCE, null);
    }

    public void setDis(String rate) {
        editor.putString(KEY_DISTANCE, rate);
        editor.commit();

    }

    public String getparlour_locality() {
        return pref.getString(KEY_COST, null);
    }

    public void setparlour_locality(String rate) {
        editor.putString(KEY_COST, rate);
        editor.commit();

    }


    public String getDropLat1() {
        return pref.getString(KEY_DROP_LAT1, null);
    }

    public void setDropLat1(String rate) {
        editor.putString(KEY_DROP_LAT1, rate);
        editor.commit();

    }

    public String getDropLong1() {
        return pref.getString(KEY_DROP_LONG1, null);
    }

    public void setDropLong1(String rate) {
        editor.putString(KEY_DROP_LONG1, rate);
        editor.commit();
    }

    public void setDropAt1(String drop) {
        editor.putString(KEY_DROP_AT1, drop);
        editor.commit();
    }

    public String getDropAt1() {
        return pref.getString(KEY_DROP_AT1, null);
    }

    public void setPickAt1(String drop) {
        editor.putString(KEY_PICK_UP1, drop);
        editor.commit();
    }

    public String getPickAt1() {
        return pref.getString(KEY_PICK_UP1, null);
    }
    public String getPickLat1() {
        return pref.getString(KEY_PICK_LAT1, null);
    }

    public void setPickLat1(String rate) {
        editor.putString(KEY_PICK_LAT1, rate);
        editor.commit();

    }

    public String getPickLong1() {
        return pref.getString(KEY_PICK_LONG1, null);
    }

    public void setPickLong1(String rate) {
        editor.putString(KEY_PICK_LONG1, rate);
        editor.commit();
    }
    public String getparlour_about() {
        return pref.getString(KEY_Weight, null);
    }

    public void setparlour_about(String rate) {
        editor.putString(KEY_Weight, rate);
        editor.commit();

    }


    public int getFinalServiceID() {
        return pref.getInt(OTP, 0);
    }
    public void setFinalServiceID(int imp) {
        editor.putInt(OTP, imp);
        editor.commit();
    }
    public float get_food_money() {
        return pref.getFloat(FOODMONEY, 0);
    }

    public void set_food_money(float imp) {
        editor.putFloat(FOODMONEY, imp);
        editor.commit();
    }



    public Float getcDiscount() {
        return pref.getFloat(cDiscount, 0);
    }

    public void setcDiscount(float imp) {
        editor.putFloat(cDiscount, imp);
        editor.commit();
    }
    public int getMinimumOrder() {
        return pref.getInt(MO, 0);
    }

    public void setMinimumOrder(int imp) {
        editor.putInt(MO, imp);
        editor.commit();
    }

    public void setOrderID(String orderID) {
        editor.putString(ORDERID, orderID);
        editor.commit();
    }
    public String getOrderID() {
        return pref.getString(ORDERID, null);
    }

    public int getGoTRide() {
        return pref.getInt(NEW_GOTRIDE, 0);
    }

    public void setGoTRide(int imp) {
        editor.putInt(NEW_GOTRIDE, imp);
        editor.commit();
    }

    public void setActualTime(String actualTime) {
        editor.putString(ACTUALTIME, actualTime);
        editor.commit();
    }
    public String getActualTime() {
        return pref.getString(ACTUALTIME, null);
    }

    public void setCancellationCharge(String actualTime) {
        editor.putString(CANCELLATIONCHARGE, actualTime);
        editor.commit();
    }
    public String getCancellationCharge() {
        return pref.getString(CANCELLATIONCHARGE, null);
    }

    public void setisRunning(int otp) {
        editor.putInt(ISRUNNING, otp);
        editor.commit();
    }
    public int getisRunning() {
        return pref.getInt(ISRUNNING, 0);
    }

    public void setUserPhoneNo(String actualTime) {
        editor.putString(USERPHNO, actualTime);
        editor.commit();
    }
    public String getUserPhoneNo() {
        return pref.getString(USERPHNO, null);
    }

    public void setRunnungParlourMobile(String parlour_mobile) {
        editor.putString(RUNNINGPARLOURMOBILE, parlour_mobile);
        editor.commit();
    }
    public String getRunnungParlourMobile() {
        return pref.getString(RUNNINGPARLOURMOBILE, null);
    }

    public void setHeaderImage(String s) {
        editor.putString(HEADERIMAGE, s);
        editor.commit();
    }
    public String getHeaderImage() {
        return pref.getString(HEADERIMAGE, null);
    }

    public void setTotal(String total) {
        editor.putString(TOTAL, total);
        editor.commit();
    }
    public String getTotal() {
        return pref.getString(TOTAL, null);
    }

    public void setPaymentMode(int paymentmode) {
        editor.putInt(PAYMODE, paymentmode);
        editor.commit();
    }
    public int getPaymentMode() {
        return pref.getInt(PAYMODE, 0);
    }

    public String getReferalCode() {
        return pref.getString(REFERRALCODE, null);
    }
    public void setReferalCode(String paymentmode) {
        editor.putString(REFERRALCODE, paymentmode);
        editor.commit();
    }


    public void setDistance(float distance) {
        editor.putFloat(DISTANCE, distance);
        editor.commit();
    }
    public float getDistance() {
        return pref.getFloat(DISTANCE, 0);
    }

    public void setPWD(String toString) {
        editor.putString(PWD, toString);
        editor.commit();
    }

    public String getPWD() {
        return pref.getString(PWD, null);
    }

    public void setYoutube(String youtube) {
        editor.putString(YOUTUBE, youtube);
        editor.commit();
    }
    public String getYoutube() {
        return pref.getString(YOUTUBE, null);
    }

    public void setYoutubeCategory(int youtubecategory) {
        editor.putInt(YIUTUBECATEGIRY, youtubecategory);
        editor.commit();
    }
    public int getYoutubeCategory() {
        return pref.getInt(YIUTUBECATEGIRY, 0);
    }

    public void setYoutubeTitile(String titleYoutube) {
        editor.putString(YIUTUBETITLE, titleYoutube);
        editor.commit();
    }
    public String getYoutubeTitile() {
        return pref.getString(YIUTUBETITLE, null);
    }

    public void setSex(int sex) {
        editor.putInt(SEX, sex);
        editor.commit();
    }
    public int getSex() {
        return pref.getInt(SEX, 0);
    }

    public void setLadies(int sex) {
        editor.putInt(LADIES, sex);
        editor.commit();
    }
    public int getLadies() {
        return pref.getInt(LADIES, 0);
    }
}