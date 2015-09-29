package org.saarang.instieventsapp.Helper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by Ahammad on 29/09/15.
 */
public class SortResults implements Comparator<JSONObject> {
    /*
    * (non-Javadoc)
    *
    * @see java.util.Comparator#compare(java.lang.Object, java.lang.Object)
    * lhs- 1st message in the form of json object. rhs- 2nd message in the form
    * of json object.
    */
    @Override
    public int compare(JSONObject lhs, JSONObject rhs) {
        try {
            return lhs.getInt("score") < rhs.getInt("score") ? 1 : (lhs
                    .getInt("score") > rhs.getInt("score") ? -1 : 0);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return 0;

    }

    public static JSONArray getSortedList(JSONArray array) throws JSONException {
        List<JSONObject> list = new ArrayList<JSONObject>();
        for (int i = 0; i < array.length(); i++) {
            list.add(array.getJSONObject(i));
        }
        Collections.sort(list, new SortResults());

        JSONArray resultArray = new JSONArray(list);

        return resultArray;
    }

}