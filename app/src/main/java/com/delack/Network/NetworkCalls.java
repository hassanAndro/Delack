package com.delack.Network;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.delack.Adapter.FilesDataAdapter;
import com.delack.FilesListingView;
import com.delack.Model.FileModel;
import com.delack.Model.UserModel;
import com.delack.R;
import com.delack.utils.Constants;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Hassan on 9/22/2016.
 */

public class NetworkCalls {

    public static void tokenUrl(final Context context, String code) {
        final ProgressDialog progressDialog = new ProgressDialog(context,
                R.style.AppTheme_Dark_Dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Loading...");
        progressDialog.show();
        Log.e("Token", "url " + Constants.tokenUrl + code);
        StringRequest loginRequest = new StringRequest(Request.Method.GET, Constants.tokenUrl + code,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        Log.e("response", "" + response);
                        ArrayList<UserModel> userModels = new ArrayList<UserModel>();
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            UserModel model = new UserModel();
                            model.setAccess_token(jsonObject.getString("access_token"));
                            model.setUser_id(jsonObject.getString("user_id"));
                            model.setTeam_name(jsonObject.getString("team_name"));
                            model.setTeam_id(jsonObject.getString("team_id"));


                            userModels.add(model);

                            String token = null, user_id = null;

                            for (UserModel model1 : userModels) {
                                token = model1.getAccess_token();
                                user_id = model1.getUser_id();

                            }

                            progressDialog.dismiss();

                            NetworkCalls.files(context, token, user_id);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressDialog.dismiss();
                        Constants.toast(context, "Error : " + error);
                    }
                }) {

        };

        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(loginRequest);
        loginRequest.setRetryPolicy(new DefaultRetryPolicy(
                100000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
    }

    public static void files(final Context context, final String token, String userId) {
        final ProgressDialog progressDialog = new ProgressDialog(context,
                R.style.AppTheme_Dark_Dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Loading...");
        progressDialog.show();
        String url = "https://slack.com/api/files.list?token=" + token + "&user=" + userId;
        Log.e("Url", " " + url);
        StringRequest loginRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        ArrayList<FileModel> fileArray = new ArrayList<>();

                        Log.e("response", "Token :" + response);
                        progressDialog.dismiss();
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            JSONArray jsonArray = jsonObject.getJSONArray("files");
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject object = jsonArray.getJSONObject(i);
                                FileModel model = new FileModel();

                                model.setId(object.getString("id"));
                                model.setName(object.getString("name"));
                                model.setFileType(object.getString("filetype"));
                                if (object.getString("filetype").equals("png") || object.getString("filetype").equals("jpg")) {

                                    model.setImage(object.getString("thumb_360"));
                                } else {

                                    model.setImage("http://p6.zdassets.com/hc/settings_assets/138842/200037786/INjc39i0w4yrXUCyAenSYg-help-center-unfurl-image.png");
                                }

                                fileArray.add(model);

                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        if (fileArray != null && !fileArray.isEmpty()) {

                            FilesListingView.adapter = new FilesDataAdapter(context, fileArray);
                            RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(context, 1);
                            FilesListingView.recyclerView.setLayoutManager(mLayoutManager);
                            FilesListingView.recyclerView.setItemAnimator(new DefaultItemAnimator());
                            FilesListingView.recyclerView.setAdapter(FilesListingView.adapter);
                            FilesListingView.delete.setVisibility(View.VISIBLE);
                            Constants.token = token;

                        } else {
                            Constants.toast(context, "Hurry! no files to delete.");
                        }


                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressDialog.dismiss();
                        Constants.toast(context, "Error : " + error);
                    }
                }) {

        };

        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(loginRequest);
        loginRequest.setRetryPolicy(new DefaultRetryPolicy(
                100000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
    }


    public static void delete(final Context context, final String token, final ArrayList<String> ids) {
        final ProgressDialog progressDialog = new ProgressDialog(context,
                R.style.AppTheme_Dark_Dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Loading...");
        progressDialog.show();

        for (int i = 0; i < ids.size(); i++) {
            String url = "https://slack.com/api/files.delete?token=" + token + "&file=" + ids.get(i);
            Log.e("Url", " " + url);
            final int finalI = i;
            StringRequest loginRequest = new StringRequest(Request.Method.GET, url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {

                            Log.e("response", "Delete :" + response);
                            if (ids.get(finalI).equals(ids.size())) {
                                progressDialog.dismiss();
                                FilesListingView.adapter.notifyDataSetChanged();
                                Constants.toast(context, "Hurry! All files deleted.");
                            }
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            progressDialog.dismiss();
                            Constants.toast(context, "Error : " + error);
                        }
                    }) {

            };

            RequestQueue requestQueue = Volley.newRequestQueue(context);
            requestQueue.add(loginRequest);
            loginRequest.setRetryPolicy(new DefaultRetryPolicy(
                    100000,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        }
    }


}
