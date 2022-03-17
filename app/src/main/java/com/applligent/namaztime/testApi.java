//package com.applligent.namaztime;
//
//import androidx.appcompat.app.AppCompatActivity;
//
//import org.json.JSONArray;
//import org.json.JSONException;
//import org.json.JSONObject;
//
//public class testApi extends AppCompatActivity {
//
//    //TODO remove
//
    //    String jsonData = "{\n" +
//            "  \"code\": 200,\n" +
//            "  \"status\": \"OK\",\n" +
//            "  \"data\": [\n" +
//            "    {\n" +
//            "      \"timings\": {\n" +
//            "        \"Fajr\": \"05:34 (IST)\",\n" +
//            "        \"Sunrise\": \"06:49 (IST)\",\n" +
//            "        \"Dhuhr\": \"12:39 (IST)\",\n" +
//            "        \"Asr\": \"16:00 (IST)\",\n" +
//            "        \"Sunset\": \"18:30 (IST)\",\n" +
//            "        \"Maghrib\": \"18:30 (IST)\",\n" +
//            "        \"Isha\": \"19:45 (IST)\",\n" +
//            "        \"Imsak\": \"05:24 (IST)\",\n" +
//            "        \"Midnight\": \"00:39 (IST)\"\n" +
//            "      },\n" +
//            "      \"date\": {\n" +
//            "        \"readable\": \"01 Mar 2022\",\n" +
//            "        \"timestamp\": \"1646105461\",\n" +
//            "        \"gregorian\": {\n" +
//            "          \"date\": \"01-03-2022\",\n" +
//            "          \"format\": \"DD-MM-YYYY\",\n" +
//            "          \"day\": \"01\",\n" +
//            "          \"weekday\": {\n" +
//            "            \"en\": \"Tuesday\"\n" +
//            "          },\n" +
//            "          \"month\": {\n" +
//            "            \"number\": 3,\n" +
//            "            \"en\": \"March\"\n" +
//            "          },\n" +
//            "          \"year\": \"2022\",\n" +
//            "          \"designation\": {\n" +
//            "            \"abbreviated\": \"AD\",\n" +
//            "            \"expanded\": \"Anno Domini\"\n" +
//            "          }\n" +
//            "        },\n" +
//            "        \"hijri\": {\n" +
//            "          \"date\": \"27-07-1443\",\n" +
//            "          \"format\": \"DD-MM-YYYY\",\n" +
//            "          \"day\": \"27\",\n" +
//            "          \"weekday\": {\n" +
//            "            \"en\": \"Al Thalaata\",\n" +
//            "            \"ar\": \"الثلاثاء\"\n" +
//            "          },\n" +
//            "          \"month\": {\n" +
//            "            \"number\": 7,\n" +
//            "            \"en\": \"Rajab\",\n" +
//            "            \"ar\": \"رَجَب\"\n" +
//            "          },\n" +
//            "          \"year\": \"1443\",\n" +
//            "          \"designation\": {\n" +
//            "            \"abbreviated\": \"AH\",\n" +
//            "            \"expanded\": \"Anno Hegirae\"\n" +
//            "          },\n" +
//            "          \"holidays\": [\n" +
//            "            \"Lailat-ul-Miraj\"\n" +
//            "          ]\n" +
//            "        }\n" +
//            "      },\n" +
//            "      \"meta\": {\n" +
//            "        \"latitude\": 23.1793013,\n" +
//            "        \"longitude\": 75.7849097,\n" +
//            "        \"timezone\": \"Asia/Calcutta\",\n" +
//            "        \"method\": {\n" +
//            "          \"id\": 1,\n" +
//            "          \"name\": \"University of Islamic Sciences, Karachi\",\n" +
//            "          \"params\": {\n" +
//            "            \"Fajr\": 18,\n" +
//            "            \"Isha\": 18\n" +
//            "          },\n" +
//            "          \"location\": {\n" +
//            "            \"latitude\": 24.8614622,\n" +
//            "            \"longitude\": 67.0099388\n" +
//            "          }\n" +
//            "        },\n" +
//            "        \"latitudeAdjustmentMethod\": \"ANGLE_BASED\",\n" +
//            "        \"midnightMode\": \"STANDARD\",\n" +
//            "        \"school\": \"STANDARD\",\n" +
//            "        \"offset\": {\n" +
//            "          \"Imsak\": 0,\n" +
//            "          \"Fajr\": 0,\n" +
//            "          \"Sunrise\": 0,\n" +
//            "          \"Dhuhr\": 0,\n" +
//            "          \"Asr\": 0,\n" +
//            "          \"Maghrib\": 0,\n" +
//            "          \"Sunset\": 0,\n" +
//            "          \"Isha\": 0,\n" +
//            "          \"Midnight\": 0\n" +
//            "        }\n" +
//            "      }\n" +
//            "    },\n" +
//            "    {\n" +
//            "      \"timings\": {\n" +
//            "        \"Fajr\": \"05:33 (IST)\",\n" +
//            "        \"Sunrise\": \"06:48 (IST)\",\n" +
//            "        \"Dhuhr\": \"12:39 (IST)\",\n" +
//            "        \"Asr\": \"16:00 (IST)\",\n" +
//            "        \"Sunset\": \"18:30 (IST)\",\n" +
//            "        \"Maghrib\": \"18:30 (IST)\",\n" +
//            "        \"Isha\": \"19:45 (IST)\",\n" +
//            "        \"Imsak\": \"05:23 (IST)\",\n" +
//            "        \"Midnight\": \"00:39 (IST)\"\n" +
//            "      },\n" +
//            "      \"date\": {\n" +
//            "        \"readable\": \"02 Mar 2022\",\n" +
//            "        \"timestamp\": \"1646191861\",\n" +
//            "        \"gregorian\": {\n" +
//            "          \"date\": \"02-03-2022\",\n" +
//            "          \"format\": \"DD-MM-YYYY\",\n" +
//            "          \"day\": \"02\",\n" +
//            "          \"weekday\": {\n" +
//            "            \"en\": \"Wednesday\"\n" +
//            "          },\n" +
//            "          \"month\": {\n" +
//            "            \"number\": 3,\n" +
//            "            \"en\": \"March\"\n" +
//            "          },\n" +
//            "          \"year\": \"2022\",\n" +
//            "          \"designation\": {\n" +
//            "            \"abbreviated\": \"AD\",\n" +
//            "            \"expanded\": \"Anno Domini\"\n" +
//            "          }\n" +
//            "        },\n" +
//            "        \"hijri\": {\n" +
//            "          \"date\": \"28-07-1443\",\n" +
//            "          \"format\": \"DD-MM-YYYY\",\n" +
//            "          \"day\": \"28\",\n" +
//            "          \"weekday\": {\n" +
//            "            \"en\": \"Al Arba'a\",\n" +
//            "            \"ar\": \"الاربعاء\"\n" +
//            "          },\n" +
//            "          \"month\": {\n" +
//            "            \"number\": 7,\n" +
//            "            \"en\": \"Rajab\",\n" +
//            "            \"ar\": \"رَجَب\"\n" +
//            "          },\n" +
//            "          \"year\": \"1443\",\n" +
//            "          \"designation\": {\n" +
//            "            \"abbreviated\": \"AH\",\n" +
//            "            \"expanded\": \"Anno Hegirae\"\n" +
//            "          },\n" +
//            "          \"holidays\": []\n" +
//            "        }\n" +
//            "      },\n" +
//            "      \"meta\": {\n" +
//            "        \"latitude\": 23.1793013,\n" +
//            "        \"longitude\": 75.7849097,\n" +
//            "        \"timezone\": \"Asia/Calcutta\",\n" +
//            "        \"method\": {\n" +
//            "          \"id\": 1,\n" +
//            "          \"name\": \"University of Islamic Sciences, Karachi\",\n" +
//            "          \"params\": {\n" +
//            "            \"Fajr\": 18,\n" +
//            "            \"Isha\": 18\n" +
//            "          },\n" +
//            "          \"location\": {\n" +
//            "            \"latitude\": 24.8614622,\n" +
//            "            \"longitude\": 67.0099388\n" +
//            "          }\n" +
//            "        },\n" +
//            "        \"latitudeAdjustmentMethod\": \"ANGLE_BASED\",\n" +
//            "        \"midnightMode\": \"STANDARD\",\n" +
//            "        \"school\": \"STANDARD\",\n" +
//            "        \"offset\": {\n" +
//            "          \"Imsak\": 0,\n" +
//            "          \"Fajr\": 0,\n" +
//            "          \"Sunrise\": 0,\n" +
//            "          \"Dhuhr\": 0,\n" +
//            "          \"Asr\": 0,\n" +
//            "          \"Maghrib\": 0,\n" +
//            "          \"Sunset\": 0,\n" +
//            "          \"Isha\": 0,\n" +
//            "          \"Midnight\": 0\n" +
//            "        }\n" +
//            "      }\n" +
//            "    }\n" +
//            "  ]\n" +
//            "}";
//
//
//        try {
//        JSONObject jsonObject = new JSONObject(jsonData);
//        JSONArray jsonArray = jsonObject.getJSONArray("data");
//        for (int i=0; i<jsonArray.length();i++){
//            JSONObject jsonObject1 = jsonArray.getJSONObject(i);
//            JSONObject timingObject = jsonObject1.getJSONObject("timings");
//
//            System.out.println("MY_DATA_IS Fajr "+timingObject.getString("Fajr"));
//            System.out.println("MY_DATA_IS Dhuhr "+timingObject.getString("Dhuhr"));
//            System.out.println("MY_DATA_IS Asr "+timingObject.getString("Asr"));
//            System.out.println("MY_DATA_IS Maghrib "+timingObject.getString("Maghrib"));
//            System.out.println("MY_DATA_IS Isha "+timingObject.getString("Isha"));
//
//        }
//
//
//
//
//    } catch (JSONException e) {
//        e.printStackTrace();
//    }
//
//
//    //TODO remove
//}
