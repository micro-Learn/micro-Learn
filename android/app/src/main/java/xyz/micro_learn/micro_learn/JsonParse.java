package xyz.micro_learn.micro_learn;
import org.json.*;


/*  -->AUTHOR: ALEXANDER T. SCHEINER<--
    -->CREATED: 4/12/2019<--

    -->PARSES A SINGLE JSON OBJECTS IN AN INSTANCED FASHION<--

    -->DESIGNED FOR MICRO-LEARN<--

    =============================
    -->DO NOT DISTRIBUTE!!!!!!<--
    =============================
 */

public class JsonParse {

    //Main data object
    JSONObject json;

    //Initializer. Takes in a string, returns a new instance of JsonParse
    public JsonParse(String jsonInput){
        try {

            //Create a new JSON Object from our string
            json = new JSONObject(jsonInput);

        }catch(Exception ex){}

        return;
    }

    //Get a single string. Takes in a string to find the value of.
    public String GetString(String getThis){
        try {

            //Return the value of the object
            return json.getString(getThis);
        }catch(Exception ex){}

        //Shit broke
        return "Lookup Failed!";
    }

    //Gets a single string. Takes in a string to find a group, an index in the group, and a string to find the value of.
    public String GetString(String inThis, int index, String getThis){
        try {

            //Create an object from an index and whatnot
            JSONObject jsonObject = json.getJSONArray(inThis).getJSONObject(index);

            //Assign return value
            String returnValue = jsonObject.getString(getThis);

            //Return the value of the object
            return returnValue;

        }catch(Exception ex){}

        //Shit broke
        String failed = "Lookup Failed";
        return failed;
    }

    //Gets an array of strings. Takes in a string to find a group, an index in the group, and a string to find the value of.
    public String[] GetStrings(String inThis, int index, String getThis){
        try {

            //Get the object of the what not index and whatnot
            JSONObject jsonObject = json.getJSONArray(inThis).getJSONObject(index);

            //Get the list of matching things in that object
            JSONArray jsonArray = jsonObject.getJSONArray(getThis);

            //Blah blah blah loop shit
            int count = jsonArray.length();
            String[] returnValue = new String[count];

            for(int i = 0; i<count; i++){

                //our return value
                returnValue[i] = jsonArray.getString(i);
            }

            //we done boss
            return returnValue;

        }catch(Exception ex){}

        //Shit broke, in AN ARRAY!
        String[] failed = {"Lookup Failed"};
        return failed;
    }

    //Gets an array of strings. Takes in a string to find a specific parent object, and a string to find the value of.
    public String[] GetStrings(String inThis, String getThis){
        try {

            //Get the object
            JSONObject jsonObject = json.getJSONObject(inThis);

            //Find the list of things in said object
            JSONArray jsonArray = jsonObject.getJSONArray(getThis);

            //Loopity loop da loop
            int count = jsonArray.length();
            String[] returnValue = new String[count];

            for(int i = 0; i<count; i++){

                //Build the list of values
                returnValue[i] = jsonArray.getString(i);
            }

            //We done here
            return returnValue;

        }catch(Exception ex){}

        //Shit broke
        String[] failed = {"Lookup Failed"};
        return failed;
    }

    //Gets an array of strings. Takes in a string to get the list of values of.
    public String[] GetStrings(String getThis){
        try {

            //The array of shit
            JSONArray jsonArray = json.getJSONArray(getThis);

            //Woop woop. MORE LOOPS!
            int count = jsonArray.length();
            String[] returnValue = new String[count];

            for(int i = 0; i<count; i++){

                //Build the array of values
                returnValue[i] = jsonArray.getString(i);
            }

            //And done.
            return returnValue;

        }catch(Exception ex){}

        //More shit broke. GOD, WHY DOES EVERYTHING BREAK?
        String[] failed = {"Lookup Failed"};
        return failed;
    }

}
